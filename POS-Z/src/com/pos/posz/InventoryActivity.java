package com.pos.posz;

import java.util.HashMap;
import java.util.List;

import com.pos.posz.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class InventoryActivity extends Activity {

	private ListView inventoryView;
	private Button add;
	private Button back;
	private Button checkAll;
	private Button remove;
	private SimpleAdapter sAdap;
	private List<HashMap<String, String>> list;
	private EditText productId;
	private InventoryController inventcontrol;
	private Button scan;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);

		add = (Button) findViewById(R.id.Addbutton);
		back = (Button) findViewById(R.id.Backbutton);
		checkAll = (Button) findViewById(R.id.CheckAllButton);
		remove = (Button) findViewById(R.id.RemoveButton);
		productId = (EditText) findViewById(R.id.inputId);
		scan = (Button) findViewById(R.id.scanButton);

		add.setOnClickListener(new Add());
		back.setOnClickListener(new Back());

		inventcontrol = new InventoryController(this);

		showData();
		checkAll.setOnClickListener(new checkAll());
		remove.setOnClickListener(new remove());

		scan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (arg0.getId() == R.id.scanButton) {
					IntentIntegrator scanIntegrator = new IntentIntegrator(
							InventoryActivity.this);
					scanIntegrator.initiateScan();
				}

			}
		});

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

	public void showData() {
		List<Item> itemlist = inventcontrol.selectAll();

		list = inventcontrol.getListMapString(itemlist);

		inventoryView = (ListView) findViewById(R.id.inventoryView);

		sAdap = new SimpleAdapter(InventoryActivity.this, list,
				R.layout.activity_column, new String[] { "productID", "name",
				"price", "" }, new int[] { R.id.ColproductId,
				R.id.ColName, R.id.ColPrice, R.id.ColcheckBox });

		inventoryView.setClickable(true);
		inventoryView.setAdapter(sAdap);
		inventoryView.setOnItemClickListener(new ClickItem());
	}

	private class remove implements OnClickListener {

		final AlertDialog.Builder adb = new AlertDialog.Builder(
				InventoryActivity.this);

		@Override
		public void onClick(View v) {
			int count = inventoryView.getAdapter().getCount();
			int[] collect = new int[count];
			for (int i = 0; i < count; i++) {
				LinearLayout itemLayout = (LinearLayout) inventoryView
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
					inventcontrol.delete(list.get(i).get("productID"));
				Intent newActivity = new Intent(InventoryActivity.this,
						InventoryActivity.class);
				startActivity(newActivity);
			}
		}
	}

	private class checkAll implements OnClickListener {

		boolean check = true;
		@Override
		public void onClick(View v) {
			int count = inventoryView.getAdapter().getCount();
			if (check) {
				for (int i = 0; i < count; i++) {
					LinearLayout itemLayout = (LinearLayout) inventoryView
							.getChildAt(i);
					CheckBox checkBox = (CheckBox) itemLayout
							.findViewById(R.id.ColcheckBox);
					checkBox.setChecked(true);
					check = false;
				}
			}
			else{
				for (int i = 0; i < count; i++) {
					LinearLayout itemLayout = (LinearLayout) inventoryView
							.getChildAt(i);
					CheckBox checkBox = (CheckBox) itemLayout
							.findViewById(R.id.ColcheckBox);
					checkBox.setChecked(false);
					check = true;
				}
			}

		}

	}

	public class ClickItem implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {

			String productId = list.get(position).get("productID");
			String name = list.get(position).get("name");
			String price = list.get(position).get("price");
			String cost = list.get(position).get("cost");
			String quantity = list.get(position).get("quantity");

			Toast.makeText(InventoryActivity.this,
					productId + " " + name + " " + price, Toast.LENGTH_SHORT)
					.show();
			Intent newActivity = new Intent(InventoryActivity.this,
					DetailActivity.class);
			newActivity.putExtra("productID", productId);
			startActivity(newActivity);

		}

	}

	public class Back implements OnClickListener {

		@Override
		public void onClick(View v) {

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent NewActivity = new Intent(InventoryActivity.this,
							MainActivity.class);
					startActivity(NewActivity);
					finish();

					overridePendingTransition(R.layout.mainfadein,
							R.layout.mainfadeout);

				}
			}, 500);

		}

	}

	public class Add implements OnClickListener {

		@Override
		public void onClick(View v) {

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					if (productId.getText().length() == 0)
						return;

					if (inventcontrol.isExsits(productId.getText().toString())) {

						Intent EditActivity = new Intent(
								InventoryActivity.this, EditActivity.class);
						EditActivity.putExtra("productID", productId.getText()
								.toString());
						startActivity(EditActivity);

					} else {

						Intent AddActivity = new Intent(InventoryActivity.this,
								AddActivity.class);
						AddActivity.putExtra("productID", productId.getText()
								.toString());
						startActivity(AddActivity);

					}
					finish();

					overridePendingTransition(R.layout.mainfadein,
							R.layout.mainfadeout);

				}
			}, 500);

		}

	}

}
