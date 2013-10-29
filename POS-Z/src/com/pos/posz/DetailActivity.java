package com.pos.posz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {

	private TextView tproductID;
	private TextView tName;
	private TextView tPrice;
	private TextView tCost;
	private TextView tQuantity;
	private Item item;
	private InventoryController inventcontrol;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		Button back = (Button) findViewById(R.id.Backbutton);
		back.setOnClickListener(new Back());
		Button edit = (Button) findViewById(R.id.EditButton);
		edit.setOnClickListener(new Edit());

		String productId = getIntent().getStringExtra("productID");

		inventcontrol = new InventoryController(this);
		showData(productId);
	}

	private void showData(String productId) {

		tproductID = (TextView) findViewById(R.id.txtProductId);
		tName = (TextView) findViewById(R.id.txtName);
		tPrice = (TextView) findViewById(R.id.txtPrice);
		tCost = (TextView) findViewById(R.id.txtCost);
		tQuantity = (TextView) findViewById(R.id.txtQuantity);

		item = inventcontrol.getItem(productId);
		if (item != null) {
			tproductID.setText(item.getProductid() + "");
			tName.setText(item.getItemDescription().getName() + "");
			tPrice.setText(item.getItemDescription().getPrice() + "");
			tCost.setText(item.getItemDescription().getCost() + "");
			tQuantity.setText(item.getQuantity() + "");
		}

	}

	public class Edit implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent editActivity = new Intent(DetailActivity.this,
					EditActivity.class);
			editActivity.putExtra("productID", tproductID.getText().toString());
			startActivity(editActivity);
		}

	}

	public class Back implements OnClickListener {

		@Override
		public void onClick(View v) {

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Intent NewActivity = new Intent(DetailActivity.this,
							InventoryActivity.class);
					startActivity(NewActivity);
					finish();

					overridePendingTransition(R.layout.mainfadein,
							R.layout.mainfadeout);

				}
			}, 500);

		}

	}

}
