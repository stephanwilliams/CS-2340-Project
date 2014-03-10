package com.team19.cs2340;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.team19.cs2340.finance.ITransaction;

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
		
		TextView fullName = (TextView)findViewById(R.id.textView1);
		fullName.setText(account.getFullName());
		
		TextView balance = (TextView)findViewById(R.id.textView2);
		
		
		try {
			List<ITransaction> transactions = fds.getTransactions(account);
			Collections.sort(transactions,
				new Comparator<ITransaction>() {
					@Override
					public int compare(ITransaction t1, ITransaction t2) {
						int effective =  (int) (t2.getEffectiveTimestamp() - t1.getEffectiveTimestamp());
						System.out.println("COMPARE " + t1.getReason() + " AND " + t2.getReason());
						System.out.println("COMAPRED TRS " + effective);
						if (effective == 0) {
							System.out.println(t2.getAddedTimestamp());
							System.out.println(t2.getEffectiveTimestamp());
							int added = (int) (t2.getAddedTimestamp() - t1.getAddedTimestamp());
							System.out.println("ADDED " + added);
							return added;
						}
						else return effective;
					}
				}
			);
			BigDecimal sum = account.getBalance();
			for (ITransaction trans : transactions) {
				BigDecimal mult = BigDecimal.ONE;
				if (trans.getType().equals(ITransaction.TransactionType.WITHDRAWAL)) mult = mult.negate();
				sum = sum.add(trans.getAmount().multiply(mult));
			}
			NumberFormat format = NumberFormat.getCurrencyInstance();
			balance.setText(format.format(sum.doubleValue()));
			ArrayAdapter<ITransaction> adapter = new TransactionListAdapter(this, R.layout.activity_transaction_list, transactions);
			
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
			Intent intnt = getIntent();
			Intent goUp = new Intent(this, HomeScreenActivity.class);
    		goUp.putExtra("user", intnt.getSerializableExtra("user"));
			NavUtils.navigateUpTo(this, goUp);
			return true;
		case R.id.action_add_transaction:
			Intent intent = new Intent(this, TransactionCreationActivity.class);
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
			
			TextView category = (TextView)rowView.findViewById(R.id.transaction_category);
			category.setText(transaction.getCategory());
			
			TextView date = (TextView)rowView.findViewById(R.id.transaction_date);
			DateFormat dformat = DateFormat.getDateInstance();
			String formatedDate = dformat.format(transaction.getEffectiveTimestamp());
			date.setText(formatedDate);
			
			TextView amount = (TextView)rowView.findViewById(R.id.transaction_amount);
			if (transaction.getType() == ITransaction.TransactionType.WITHDRAWAL) {
				amount.setTextColor(Color.RED);
			} else { 
				amount.setTextColor(Color.parseColor("#00AA00"));
			}
			
			NumberFormat format = NumberFormat.getCurrencyInstance();
			amount.setText(format.format(transaction.getAmount().doubleValue()));
			
			TextView reason = (TextView)rowView.findViewById(R.id.transaction_reason);
			reason.setText(transaction.getReason());
			
			return rowView;
		}
		
	}

}

