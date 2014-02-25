package com.team19.cs2340;

import java.math.BigDecimal;

import com.team19.cs2340.finance.FinanceDataException;
import com.team19.cs2340.finance.FinanceDataServiceFactory;
import com.team19.cs2340.finance.IFinanceDataService;
import com.team19.cs2340.user.IUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AccountCreationActivity extends Activity {
	private IFinanceDataService fds; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_creation);
		fds = FinanceDataServiceFactory.createFinanceDataService(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_creation, menu);
		return true;
	}
	
	
	
	public void onSubmit(View view) {
		EditText fullName = (EditText)findViewById(R.id.editText1);
		EditText displayName = (EditText)findViewById(R.id.editText2);
		EditText balance = (EditText)findViewById(R.id.editText3);
		EditText monthlyInterest = (EditText)findViewById(R.id.editText4);
		
		//TODO Implement checking for exceptions
		Intent intent = getIntent();
		try {
			fds.createAccount((IUser) intent.getSerializableExtra("user"),
					fullName.getText().toString(),
					displayName.getText().toString(),
					new BigDecimal(balance.getText().toString()),
					new BigDecimal(monthlyInterest.getText().toString()));
			
			Intent goUp = new Intent(this, HomeScreenActivity.class);
    		intent.putExtra("user", (IUser) intent.getSerializableExtra("user"));
			NavUtils.navigateUpTo(this, goUp);
		} catch (FinanceDataException e) {

		}		
	}
	
	public void onCancel(View view) {
		Intent intent = getIntent();
		Intent goUp = new Intent(this, HomeScreenActivity.class);
		intent.putExtra("user", (IUser) intent.getSerializableExtra("user"));
		NavUtils.navigateUpTo(this, goUp);
	}
}
