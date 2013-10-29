package com.pos.posz;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

public class LoginActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//user
		TextView user = (TextView)findViewById(R.id.textUser);
		final EditText userIn = (EditText)findViewById(R.id.editUser);
		//pass
		TextView pass = (TextView)findViewById(R.id.textPass);
		final EditText passIn = (EditText)findViewById(R.id.editPass);

		final String cUser = "username";
		final String cPass = "password";
		final AlertDialog.Builder adb = new AlertDialog.Builder(this);
		// ButtonLogin
		final Button btn1 = (Button) findViewById(R.id.buttonLogin);
		// Perform action on click
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				if(userIn.getText().toString().equals(cUser)&&
//						passIn.getText().toString().equals(cPass)){
					//					 Open Form 2
					new Handler().postDelayed(new Runnable() {
						public void run() {
							Intent newActivity = new Intent(LoginActivity.this,MainActivity.class);
							startActivity(newActivity);
							finish();

							overridePendingTransition(R.layout.mainfadein,
									R.layout.mainfadeout);
						}
					}, 1500);
//				}else{
//					AlertDialog ad = adb.create();
//					ad.setMessage("Wrong username or password !");
//					ad.show();
//				}
			}
		});

	}

}