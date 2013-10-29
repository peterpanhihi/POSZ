package com.pos.sale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.pos.posz.DBcontroller;
import com.pos.posz.DetailActivity;
import com.pos.posz.InventoryCollection;
import com.pos.posz.MainActivity;
import com.pos.posz.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SaleActivity extends Activity{
	private Register register;
	private Context context = this;
	private ListView saleView;
	private SimpleAdapter sAdap;
	private List<HashMap<String, String>> list;
	private Button add;
	private Button back;
	private Button checkAll;
	private Button remove;
	private EditText productId;
	private EditText quantity;
	private Button scan;
	private DBcontroller myDb;
	private InventoryCollection inventcollection ;
	private AlertDialog.Builder adb;
	private TextView total;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale);

		adb = new AlertDialog.Builder(SaleActivity.this);

		myDb = new DBcontroller(this);
		inventcollection = new InventoryCollection(myDb);

		register = register.getInstance();
		register.setInvent(inventcollection);

		add = (Button) findViewById(R.id.Addbutton);
		back = (Button) findViewById(R.id.Backbutton);
		checkAll = (Button) findViewById(R.id.CheckAllButton);
		remove = (Button) findViewById(R.id.RemoveButton);
		productId = (EditText) findViewById(R.id.inputId);
		quantity = (EditText) findViewById(R.id.inputQuantity);
		scan = (Button) findViewById(R.id.scanButton);
		total = (TextView)findViewById(R.id.TotalPrice);

		add.setOnClickListener(new Add());
		back.setOnClickListener(new Back());
		scan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (arg0.getId() == R.id.scanButton) {
					IntentIntegrator scanIntegrator = new IntentIntegrator(
							SaleActivity.this);
					scanIntegrator.initiateScan();
				}

			}
		});
		checkAll.setOnClickListener(new checkAll());
		remove.setOnClickListener(new remove());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class Add implements OnClickListener {

		@Override
		public void onClick(View v) {

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					String id = productId.getText().toString();
					int qnty = Integer.parseInt(quantity.getText().toString());
					boolean status = true;
					if (productId.getText().length() == 0){
						productId.setText("");
						quantity.setText("");
						return;
					}else if(quantity.getText().length() == 0){
						status = register.addItem(id, 1);
					}else{
						status = register.addItem(id, qnty);
					}

					if(!status){
						adb.setTitle("Error");
						adb.setMessage("Product id "+id+" not found.");
						adb.setPositiveButton("OK", null);
						adb.show();
						productId.setText("");
						quantity.setText("");
						return;
					}

					showData();
					//finish();
					productId.setText("");
					quantity.setText("");
					overridePendingTransition(R.layout.mainfadein,
							R.layout.mainfadeout);

				}
			}, 500);

		}

	}

	public void showData() {
		total.setText(register.getTotal()+"");
		
		List<SalesLineItem> linelist = register.getList();

		list = getListMapString(linelist);

		saleView = (ListView) findViewById(R.id.saleView);

		sAdap = new SimpleAdapter(SaleActivity.this, list,
				R.layout.activity_salecolumn, new String[] { "productID", "name","quantity",
				"price", "" }, new int[] { R.id.ColproductId,
				R.id.ColName,R.id.ColQuantity, R.id.ColPrice, R.id.ColcheckBox });
		saleView.setClickable(true);
		saleView.setAdapter(sAdap);

		saleView.setOnItemClickListener(new ClickItem());

	}

	public class ClickItem implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {

			final String productId = list.get(position).get("productID");

			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.activity_prompts, null);

			adb.setView(promptsView);

			final EditText userInput = (EditText) promptsView
					.findViewById(R.id.editTextDialogUserInput);

			// set dialog message
			adb
			.setCancelable(false)
			.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					if(userInput.getText().toString().length()==0)return;
					else{register.setQnty(productId, Integer.parseInt(userInput.getText().toString()));
					showData();
					}
				}
			})
			.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}
			});

			// create alert dialog
			AlertDialog alertDialog = adb.create();

			// show it
			alertDialog.show();


		}

	}

	public List<HashMap<String, String>> getListMapString(List<SalesLineItem> linelist) {


		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> map;

		for (SalesLineItem lineitem : linelist) {
			map = new HashMap<String, String>();

			String name = lineitem.getProduct().getItemDescription().getName();
			String id = lineitem.getProductId();
			double price = lineitem.getProduct().getItemDescription().getPrice();
			int quan = lineitem.getQuantity();


			map.put("productID", id);
			map.put("name", name);
			map.put("quantity", quan + "");
			map.put("price", price + "");
			list.add(map);
		}
		return list;
	}

	private class remove implements OnClickListener {

		@Override
		public void onClick(View v) {
			int count = saleView.getAdapter().getCount();
			int[] collect = new int[count];
			for (int i = 0; i < count; i++) {
				LinearLayout itemLayout = (LinearLayout) saleView
						.getChildAt(i);
				CheckBox checkBox = (CheckBox) itemLayout
						.findViewById(R.id.ColcheckBox);
				if (checkBox.isChecked()) {
					collect[i]++;
				}
			}

			for (int i = 0; i < collect.length; i++) {
				if (collect[i] != 0) {
					adb.setTitle("Delete???");
					adb.setMessage("Are you sure to delete " + "selected Item "
							+ " ????");
					adb.setNegativeButton("Cancel", null);
					adb.setPositiveButton("OK", new OK(collect));
					adb.show();
					break;
				}
			}
		}
	}

	private class OK implements android.content.DialogInterface.OnClickListener {

		private int[] count;

		public OK(int[] count) {
			this.count = count;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {

			for (int i = 0; i < count.length; i++) {
				if (count[i] != 0)
					register.removeItem(list.get(i).get("productID"));
				Intent newActivity = new Intent(SaleActivity.this,
						SaleActivity.class);
				startActivity(newActivity);
			}
		}
	}

	private class checkAll implements OnClickListener {

		boolean check = true;
		@Override
		public void onClick(View v) {
			int count = saleView.getAdapter().getCount();
			if (check) {
				for (int i = 0; i < count; i++) {
					LinearLayout itemLayout = (LinearLayout) saleView
							.getChildAt(i);
					CheckBox checkBox = (CheckBox) itemLayout
							.findViewById(R.id.ColcheckBox);
					checkBox.setChecked(true);
					check = false;
				}
			}
			else{
				for (int i = 0; i < count; i++) {
					LinearLayout itemLayout = (LinearLayout) saleView
							.getChildAt(i);
					CheckBox checkBox = (CheckBox) itemLayout
							.findViewById(R.id.ColcheckBox);
					checkBox.setChecked(false);
					check = true;
				}
			}

		}

	}

	public class Back implements OnClickListener {

		@Override
		public void onClick(View v) {

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					
					register.endSale();
					
					Intent NewActivity = new Intent(SaleActivity.this,
							MainActivity.class);
					startActivity(NewActivity);
					finish();

					overridePendingTransition(R.layout.mainfadein,
							R.layout.mainfadeout);

				}
			}, 500);

		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, data);

		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();

			productId.setText(scanContent);

		} else {
			Toast.makeText(this, "No scan data received!!!", Toast.LENGTH_SHORT)
			.show();
		}

	}
}
