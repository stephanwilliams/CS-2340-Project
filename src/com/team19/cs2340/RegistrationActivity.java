package com.team19.cs2340;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.team19.cs2340.user.UserAccountService;

public class RegistrationActivity extends Activity {
	private UserAccountService uas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
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
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}
	
	public void registerUser(View v) {
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText("");
		
		EditText usernameInput = (EditText)findViewById(R.id.usernameInput);
		EditText passwordInput = (EditText)findViewById(R.id.passwordInput);
		EditText confirmPasswordInput = (EditText)findViewById(R.id.confirmPassword);
		
		String username = usernameInput.getText().toString();
		String passwordIn = passwordInput.getText().toString();
		String passwordConfirm = confirmPasswordInput.getText().toString();
		
		if(uas.userExists(username)){
			textView1.setText("Username already exists!");
		}
		else{
			if(passwordIn.equals(passwordConfirm)){
				uas.createUser(username, passwordIn);
				//Test Statement
				if(uas.authenticateUser(username, passwordIn) != null){
					textView1.setText("Account created!");	
				}
				else{
					textView1.setText("Account failed to be created!");
				}
			}
			else{
				passwordInput.setText("");				
	    		textView1.setText("Passwords do not match!");
			}
		}
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

}
