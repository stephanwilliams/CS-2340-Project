package com.team19.cs2340;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.team19.cs2340.user.IUser;
import com.team19.cs2340.user.UserAccountService;

public class LogInActivity extends Activity {
	private UserAccountService uas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		// Show the Up button in the action bar.
		setupActionBar();
		uas = new UserAccountService(this);
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void onLogin(View v) {
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText("");
		
		EditText usernameInput = (EditText) findViewById(R.id.usernameInput);
    	String username = usernameInput.getText().toString();
		EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
    	String password = passwordInput.getText().toString();
    	
    	IUser user = uas.authenticateUser(username, password);
    	if (user == null) {
    		passwordInput.setText("");    
    		
    		textView1.setText("Invalid login");
    	}
    	else {
    		passwordInput.setText("");
	    	if (user.getAccountType().equals(IUser.AccountType.ADMIN)) {
	    		
	    	} else {
	    		// ...
	    	}
	    	
	    	//When admin implemented, put this in else!
	    	Intent intent = new Intent(this, HomeScreenActivity.class);
    		intent.putExtra("user", user);
        	startActivity(intent);
    	}
	}

}
