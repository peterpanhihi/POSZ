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

public class EditActivity extends Activity {

	private Button back;
	private TextView txtproductId;
	private EditText inputName;
	private EditText inputPrice;
	private EditText inputCost;
	private EditText inputQuantity;
	private Item item;
	private Button edit;
	private InventoryController inventcontrol;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		inventcontrol = new InventoryController(this);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		back = (Button) findViewById(R.id.Backbutton);
		back.setOnClickListener(new Back());
		edit = (Button) findViewById(R.id.EditButton);
		edit.setOnClickListener(new Edit());

		txtproductId = (TextView) findViewById(R.id.txtProductId);
		inputName = (EditText) findViewById(R.id.inputName);
		inputPrice = (EditText) findViewById(R.id.inputPrice);
		inputCost = (EditText) findViewById(R.id.inputCost);
		inputQuantity = (EditText) findViewById(R.id.inputQuantity);

		String productId = getIntent().getStringExtra("productID");
		txtproductId.setText(productId);
		showDetail(productId);

		Toast.makeText(EditActivity.this, productId, Toast.LENGTH_SHORT).show();

	}

	private class Edit implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (Update(txtproductId.getText().toString())) {

				Intent backActivity = new Intent(EditActivity.this,
						InventoryActivity.class);
				startActivity(backActivity);

			}
		}

	}

	private void showDetail(String productId) {

		item = inventcontrol.getItem(productId);

		if (item != null) {
			inputName.setText(item.getItemDescription().getName() + "");
			inputPrice.setText(item.getItemDescription().getPrice() + "");
			inputCost.setText(item.getItemDescription().getCost() + "");
			inputQuantity.setText(item.getQuantity() + "");
		}

	}

	public class Back implements OnClickListener {
		@Override
		public void onClick(View v) {

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent NewActivity = new Intent(EditActivity.this,
							InventoryActivity.class);
					startActivity(NewActivity);
					finish();

					overridePendingTransition(R.layout.mainfadein,
							R.layout.mainfadeout);

				}
			}, 500);
		}
	}

	public boolean Update(String productId) {

		if (inputName.getText().length() == 0) {
			inputName.requestFocus();
			return false;
		}

		if (inputPrice.getText().length() == 0) {
			inputPrice.requestFocus();
			return false;
		}

		if (inputCost.getText().length() == 0) {
			inputCost.requestFocus();
			return false;
		}

		if (inputQuantity.getText().length() == 0) {
			inputQuantity.requestFocus();
			return false;
		}

		if(inputPrice.getText().toString().equals("."))
			return false;
		if(inputCost.getText().toString().equals("."))
			return false;
		
		boolean editSuccess = inventcontrol.update(productId, inputName
				.getText().toString(), inputPrice.getText().toString(),
				inputCost.getText().toString(), inputQuantity.getText()
						.toString());

		if (!editSuccess) {
			return false;
		}
		Toast.makeText(EditActivity.this, "Update Data SuccessFully!!",
				Toast.LENGTH_SHORT).show();
		return true;

	}

}
