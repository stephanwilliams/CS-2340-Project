package com.team19.cs2340;

import java.text.NumberFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.team19.cs2340.finance.FinanceDataServiceFactory;
import com.team19.cs2340.finance.IAccount;
import com.team19.cs2340.finance.IFinanceDataService;
import com.team19.cs2340.user.IUser;

public class HomeScreenActivity extends Activity {
	IFinanceDataService fds = null;
	IUser user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		// Show the Up button in the action bar.
		
		Intent intent = getIntent();
		user = (IUser) intent.getSerializableExtra("user");
		
		fds = FinanceDataServiceFactory.createFinanceDataService(this);
		
		try {
			List<IAccount> accounts = fds.getAccounts(user);
			ArrayAdapter<IAccount> adapter = new AccountListAdapter(this, R.layout.account_list_item, accounts);
			
			ListView listView = (ListView)findViewById(R.id.account_list);
			listView.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	protected void onResume() {
		//do update stuff here
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
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
		case R.id.action_add_account:
			Intent intent = new Intent(this, AccountCreationActivity.class);
    		intent.putExtra("user", user);
        	startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class AccountListAdapter extends ArrayAdapter<IAccount> {

		public AccountListAdapter(Context context, int resource,
				List<IAccount> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			IAccount account = getItem(position);

			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.account_list_item, parent, false);

			TextView accountDisplayName = (TextView)rowView.findViewById(R.id.account_display_name);
			accountDisplayName.setText(account.getDisplayName());
			
			TextView accountBalance = (TextView)rowView.findViewById(R.id.account_balance);
			NumberFormat format = NumberFormat.getCurrencyInstance();
			accountBalance.setText(format.format(account.getBalance().doubleValue()));

			return rowView;
		}
		
	}
}
