package com.pos.posz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity {
	/*This is the attribute for back button*/
	private Button back;
	/*This is the attribute for add button*/
	private Button add;
	/*This is the text view for the product ID*/
	private TextView txtproductId;
	/* */
	private InventoryController inventcontrol;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		String productId = getIntent().getStringExtra("productID");
		txtproductId = (TextView) findViewById(R.id.txtProductId);
		txtproductId.setText(productId);

		back = (Button) findViewById(R.id.Backbutton);
		back.setOnClickListener(new Back());

		add = (Button) findViewById(R.id.Addbutton);
		add.setOnClickListener(new AddInventory());

		inventcontrol = new InventoryController(this);

	}

	public boolean saveData() {
		final EditText name = (EditText) findViewById(R.id.inputName);
		final EditText price = (EditText) findViewById(R.id.inputPrice);
		final EditText cost = (EditText) findViewById(R.id.inputCost);
		final EditText quantity = (EditText) findViewById(R.id.inputQuantity);

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

		if (quantity.getText().length() == 0) {
			quantity.requestFocus();
			return false;
		}
		
		if(price.getText().toString().equals("."))
			return false;
		if(cost.getText().toString().equals("."))
			return false;

		boolean addSuccess = inventcontrol.add(txtproductId.getText()
				.toString(), name.getText().toString(), price.getText()
				.toString(), cost.getText().toString(), quantity.getText()
				.toString());

		if (!addSuccess)
			return false;

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
			if (saveData()) {
				Intent newActivity = new Intent(AddActivity.this,
						InventoryActivity.class);
				startActivity(newActivity);
			} else {
				Toast.makeText(AddActivity.this, "Failed", Toast.LENGTH_SHORT)
						.show();
			}

		}

	}

}
