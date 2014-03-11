package com.team19.cs2340;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.team19.cs2340.finance.FinanceDataServiceFactory;
import com.team19.cs2340.finance.IAccount;
import com.team19.cs2340.finance.IFinanceDataService;
import com.team19.cs2340.finance.ITransaction;
import com.team19.cs2340.user.IUser;

public class SpendingReportActivity extends Activity {
	private IFinanceDataService fds; 
	private IUser user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spending_report);
		// Show the Up button in the action bar.
		fds = FinanceDataServiceFactory.createFinanceDataService(this);
		
		Intent intent = getIntent();
		user = (IUser)intent.getSerializableExtra("user");
		
		Spinner spinner = (Spinner)findViewById(R.id.spendingType);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.report_types, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(adapter);
		
		
		DialogDatePicker date1 = (DialogDatePicker)findViewById(R.id.datePicker1);
		DialogDatePicker date2 = (DialogDatePicker)findViewById(R.id.datePicker2);

		date1.addTextChangedListener(new TextWatcher(){
	       @Override
			public void afterTextChanged(Editable s) {
	            remakeEverything();
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	    }); 
		date2.addTextChangedListener(new TextWatcher(){
	       @Override
			public void afterTextChanged(Editable s) {
	            remakeEverything();
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	    }); 
		
		remakeEverything();
		
		setupActionBar();
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	/*
	 * Code to steal
	 * 
	 * Spinner spinner = (Spinner)findViewById(R.id.spinner1);
	 *	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.transaction_types, android.R.layout.simple_spinner_item);
	 *	adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
	 *	spinner.setAdapter(adapter);
	 * 
	 * Spinner type = (Spinner)findViewById(R.id.spinner1);
	 */
	
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
			Intent intent = getIntent();
			Intent goUp = new Intent(this, HomeScreenActivity.class);
    		goUp.putExtra("user", (IUser)intent.getSerializableExtra("user"));
			NavUtils.navigateUpTo(this, goUp);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private class EntryListAdapter extends ArrayAdapter<Entry<String, BigDecimal>> {

		public EntryListAdapter(Context context, int resource,
				List<Entry<String, BigDecimal>> objects) {
			super(context, resource, objects);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Entry<String, BigDecimal> entry = getItem(position);
			
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.spending_report_list_item, parent, false);
			
			TextView category = (TextView)rowView.findViewById(R.id.spending_report_display_name);
			category.setText(entry.getKey());
			
			TextView amount = (TextView)rowView.findViewById(R.id.spending_report_balance);
			
			NumberFormat format = NumberFormat.getCurrencyInstance();
			amount.setText(format.format(entry.getValue().doubleValue()));
			
			return rowView;
		}
	}
	
	public void remakeEverything() {
		
		try {
			DialogDatePicker date1 = (DialogDatePicker)findViewById(R.id.datePicker1);
			Calendar cal1 = date1.getCalendar();
			
			DialogDatePicker date2 = (DialogDatePicker)findViewById(R.id.datePicker2);
			Calendar cal2 = date2.getCalendar();
			
			Map<String, BigDecimal> reportMap = fds.getCategorySpendingReport(user, 
												cal1.getTimeInMillis(),
												cal2.getTimeInMillis());
			//System.out.println(reportMap.keySet().size());
			//if (reportMap.keySet().size() < 1) {
			//	int x = 1;
			//	while (x<=1) {
			//		x =0;
			//	}
			//}
			List<Entry<String, BigDecimal>> reportMapArrays = new ArrayList<Map.Entry<String,BigDecimal>>(reportMap.entrySet());
			
			ArrayAdapter<Entry<String, BigDecimal>> adapter = new EntryListAdapter(this, R.layout.activity_spending_report, reportMapArrays);
			
			ListView listView = (ListView)findViewById(R.id.reportListView);
			listView.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
