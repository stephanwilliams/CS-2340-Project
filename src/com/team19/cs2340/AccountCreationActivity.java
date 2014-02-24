package com.team19.cs2340;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AccountCreationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_creation);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_creation, menu);
		return true;
	}

}
