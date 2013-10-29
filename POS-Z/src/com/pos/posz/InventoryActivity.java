package com.pos.posz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.example.pos_z.R;
import com.example.pos_z.R.id;
import com.example.pos_z.R.layout;

import android.R.anim;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class InventoryActivity extends Activity {

	private DBclass myDb;
	private ListView inventoryView;
	private Button add;
	private Button back;
	private Button checkAll;
	private Button remove;
	private SimpleAdapter sAdap;
	private List<HashMap<String, String>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);

		myDb = new DBclass(this);
		add = (Button) findViewById(R.id.Addbutton);
		back = (Button) findViewById(R.id.Backbutton);
		checkAll = (Button) findViewById(R.id.CheckAllButton);
		remove = (Button) findViewById(R.id.RemoveButton);

		list = myDb.SelectAllData();

		inventoryView = (ListView) findViewById(R.id.inventoryView);

		// ArrayAdapter<ItemInventory> adapter = new
		// ArrayAdapter<ItemInventory>(
		// this, android.R.layout.simple_expandable_list_item_1, list);

		sAdap = new SimpleAdapter(InventoryActivity.this, list,
				R.layout.activity_column, new String[] { "productID", "name",
						"price", "" }, new int[] { R.id.ColproductId,
						R.id.ColName, R.id.ColPrice, R.id.ColcheckBox });

		inventoryView.setAdapter(sAdap);

		add.setOnClickListener(new Add());
		back.setOnClickListener(new Back());

		inventoryView.setOnItemClickListener(new ClickItem());
		checkAll.setOnClickListener(new checkAll());
		remove.setOnClickListener(new remove());
	}

	private class remove implements OnClickListener {

		@Override
		public void onClick(View v) {
			int count = inventoryView.getAdapter().getCount();
			//List<String> ids = new ArrayList<String>();
			for (int i = 0; i < count; i ++) {
				LinearLayout itemLayout = (LinearLayout) inventoryView
						.getChildAt(i);
				CheckBox checkBox = (CheckBox) itemLayout
						.findViewById(R.id.ColcheckBox);
				if (checkBox.isChecked()) {
					//ids.add(list.get(i).get("productID"));
					myDb.DeleteData(Integer.parseInt(list.get(i).get("productID")));
				}
			}
			Intent newActivity = new Intent(InventoryActivity.this,
					InventoryActivity.class);
			startActivity(newActivity);
			//Log.d("wtffff", ids + "");
		}

	}

	private class checkAll implements OnClickListener {

		@Override
		public void onClick(View v) {
			int count = inventoryView.getAdapter().getCount();
			for (int i = 0; i < count; i++) {
				LinearLayout itemLayout = (LinearLayout) inventoryView
						.getChildAt(i);
				CheckBox checkBox = (CheckBox) itemLayout
						.findViewById(R.id.ColcheckBox);
				checkBox.setChecked(true);
			}

		}

	}

	public class ClickItem implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

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
					Intent NewActivity = new Intent(InventoryActivity.this,
							AddActivity.class);
					startActivity(NewActivity);
					finish();

					overridePendingTransition(R.layout.mainfadein,
							R.layout.mainfadeout);

				}
			}, 500);

		}

	}
}
