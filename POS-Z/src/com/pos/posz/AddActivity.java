package com.pos.posz;

import com.example.pos_z.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends Activity {

	private Button back;
	private Button add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		back = (Button) findViewById(R.id.Backbutton);
		back.setOnClickListener(new Back());

		add = (Button) findViewById(R.id.Addbutton);
		add.setOnClickListener(new AddInventory());

	}

	public boolean SaveData() {
		final EditText productId = (EditText) findViewById(R.id.inputId);
		final EditText name = (EditText) findViewById(R.id.inputName);
		final EditText price = (EditText) findViewById(R.id.inputPrice);
		final EditText cost = (EditText) findViewById(R.id.inputCost);

		final DBclass myDb = new DBclass(this);

		if (productId.getText().length() == 0) {
			productId.requestFocus();
			return false;
		}

		if (name.getText().length() == 0) {
			name.requestFocus();
			return false;
		}

		if (price.getText().length() == 0) {
			price.requestFocus();
			return false;
		}

		if (cost.getText().length() == 0) {
			cost.requestFocus();
			return false;
		}

		String[] arrData = myDb.SelectData(Integer.parseInt(productId.getText()
				.toString()));
		if (arrData != null) {
			productId.requestFocus();
			return false;
		}

		long saveStatus = myDb.insertData(Integer.parseInt(productId.getText()
				.toString()), name.getText().toString(), Integer.parseInt(cost
				.getText().toString()), Integer.parseInt(price.getText()
				.toString()));

		if (saveStatus == -1) {
			return false;
		}
		if (saveStatus == -2) {
			Toast.makeText(AddActivity.this, "Already Exists!!!",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		Toast.makeText(AddActivity.this, "Add Item Successfully!!",
				Toast.LENGTH_SHORT).show();

		return true;

	}

	public class Back implements OnClickListener {

		@Override
		public void onClick(View v) {

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent NewActivity = new Intent(AddActivity.this,
							InventoryActivity.class);
					startActivity(NewActivity);
					finish();

					overridePendingTransition(R.layout.mainfadein,
							R.layout.mainfadeout);

				}
			}, 500);

		}

	}

	public class AddInventory implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (SaveData()) {
				Intent newActivity = new Intent(AddActivity.this,
						InventoryActivity.class);
				startActivity(newActivity);
			}

		}

	}

}
