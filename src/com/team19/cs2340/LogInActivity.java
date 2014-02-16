package com.team19.cs2340;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class LogInActivity extends Activity {
	private User admin = new User();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		// Show the Up button in the action bar.
		setupActionBar();
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
	
	
	public void onLogin() {
		EditText editText = (EditText) findViewById(R.string.username);
    	String username = editText.getText().toString();
		EditText editText2 = (EditText) findViewById(R.string.password);
    	String pass = editText2.getText().toString();
    	
    	User newUser = new User(username, pass);
    	if (isAdmin(newUser)) {
    		editText.setText("Correct!");
    		editText2.setText("Correct!");
    	}
    	else {
    		editText.setText("Incorrect!");
    		editText2.setText("Incorrect!");    		
    	}
	}

	private boolean isAdmin(User user) {
		return (user.getPass() == admin.getPass() && user.getName() == admin.getName());
	}
}
