package com.team19.cs2340;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.team19.cs2340.finance.FinanceDataException;
import com.team19.cs2340.finance.FinanceDataServiceFactory;
import com.team19.cs2340.finance.IAccount;
import com.team19.cs2340.finance.IFinanceDataService;
import com.team19.cs2340.finance.ITransaction.TransactionType;

public class TransactionCreationActivity extends Activity {
	private IFinanceDataService fds; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_creation);
		fds = FinanceDataServiceFactory.createFinanceDataService(this);
		
		Spinner spinner = (Spinner)findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.transaction_types, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_creation, menu);
		return true;
	}
	
	public void onSubmit(View view) throws ParseException {
		Spinner type = (Spinner)findViewById(R.id.spinner1);
		EditText reason = (EditText)findViewById(R.id.editText2);
		EditText category = (EditText)findViewById(R.id.editText3);
		EditText amount = (EditText)findViewById(R.id.editText4);
		EditText date = (EditText)findViewById(R.id.editText5);
		
		
		
		TransactionType transactionType = TransactionType.values()[type.getSelectedItemPosition()];

		BigDecimal adjustedAmount = new BigDecimal(amount.getText().toString());
		if (transactionType == TransactionType.WITHDRAWAL) adjustedAmount = adjustedAmount.negate();
				
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date d = df.parse(date.getText().toString());
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(d);
	     
		Intent intent = getIntent();
		try {
			fds.createTransaction((IAccount) intent.getSerializableExtra("account"),
					calendar.getTimeInMillis(),
					transactionType,
					category.getText().toString(),
					adjustedAmount,
					reason.getText().toString());
					
			
			Intent goUp = new Intent(this, TransactionListActivity.class);
    		goUp.putExtra("account", (IAccount) intent.getSerializableExtra("account"));
			NavUtils.navigateUpTo(this, goUp);
		} catch (FinanceDataException e) {
			e.printStackTrace();
		}		
	}
	
	public void onCancel(View view) {
		Intent intent = getIntent();
		Intent goUp = new Intent(this, TransactionListActivity.class);
		goUp.putExtra("account", (IAccount) intent.getSerializableExtra("account"));
		NavUtils.navigateUpTo(this, goUp);
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
			Intent intent = getIntent();
			Intent goUp = new Intent(this, TransactionListActivity.class);
			goUp.putExtra("account", (IAccount) intent.getSerializableExtra("account"));
			NavUtils.navigateUpTo(this, goUp);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
