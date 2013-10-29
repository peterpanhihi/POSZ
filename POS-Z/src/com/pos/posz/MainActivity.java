package com.pos.posz;

import com.example.pos_z.R;
import com.example.pos_z.R.id;
import com.example.pos_z.R.layout;
import com.example.pos_z.R.menu;

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
	private DBclass myDb;
	private Button inventButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myDb = new DBclass(this);
		myDb.getWritableDatabase();

		inventButton = (Button) findViewById(R.id.Inventory);
		inventButton.setOnClickListener(new Inventory());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
