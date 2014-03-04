package com.team19.cs2340;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import java.text.DateFormat;
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
import com.team19.cs2340.transaction.ITransaction;

public class TransactionListActivity extends Activity {
	IFinanceDataService fds = null;
	IAccount account;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_list);
		
		Intent intent = getIntent();
		account = (IAccount) intent.getSerializableExtra("account");
		
		fds = FinanceDataServiceFactory.createFinanceDataService(this);
		
		try {
			List<ITransaction> transactions = fds.getTransactions(account);
			ArrayAdapter<ITransaction> adapter = new TransactionListAdapter(this, R.layout.transaction_list, transactions);
			
			ListView listView = (ListView)findViewById(R.id.transaction_list);
			listView.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setupActionBar();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_list, menu);
		return true;
	}
	
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	protected void onResume() {
		//do update stuff here
		super.onResume();
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
		case R.id.action_add_transaction:
			Intent intent = new Intent(this, AccountCreationActivity.class);
    		intent.putExtra("account", account);
        	startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class TransactionListAdapter extends ArrayAdapter<ITransaction> {

		public TransactionListAdapter(Context context, int resource,
				List<ITransaction> objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ITransaction transaction = getItem(position);

			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.transaction_list_item, parent, false);
			
			TextView transactionDisplayName = (TextView)rowView.findViewById(R.id.transaction_display_name);
			transactionDisplayName.setText(transaction.getDisplayName());
			
			TextView transactionDate = (TextView)rowView.findViewById(R.id.transaction_date);
			DateFormat format = DateFormat.getDateInstance();
			String date = format.format(transaction.getDate());
			transactionDate.setText(date);

			return rowView;
		}
		
	}

}

