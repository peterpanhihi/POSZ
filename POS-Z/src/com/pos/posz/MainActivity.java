package com.pos.posz;

import com.pos.posz.R;
import com.pos.sale.Register;
import com.pos.sale.SaleActivity;
import com.pos.sale.Store;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	SQLiteDatabase db;
	private DBcontroller myDb;
	private Button inventButton;
	private Button saleButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myDb = new DBcontroller(this);
		myDb.getWritableDatabase();

		inventButton = (Button) findViewById(R.id.Inventory);
		inventButton.setOnClickListener(new Inventory());
		
		saleButton = (Button) findViewById(R.id.Sale);
		saleButton.setOnClickListener(new Sale());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private class Sale implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent NewActivity = new Intent(MainActivity.this, SaleActivity.class);
					startActivity(NewActivity);
					finish();
					
					overridePendingTransition(R.layout.mainfadein, R.layout.mainfadeout);

				}
			}, 500);
		}
		
	}
	
	private class Inventory implements OnClickListener {
		@Override
		public void onClick(View v) {

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent NewActivity = new Intent(MainActivity.this, InventoryActivity.class);
					startActivity(NewActivity);
					finish();
					
					overridePendingTransition(R.layout.mainfadein, R.layout.mainfadeout);

				}
			}, 500);

		}
	}

}
