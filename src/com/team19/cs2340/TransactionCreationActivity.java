package com.team19.cs2340;

import java.math.BigDecimal;
import java.util.Calendar;

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
import android.widget.TextView;

import com.team19.cs2340.finance.FinanceDataException;
import com.team19.cs2340.finance.FinanceDataServiceFactory;
import com.team19.cs2340.finance.IAccount;
import com.team19.cs2340.finance.IFinanceDataService;
import com.team19.cs2340.finance.ITransaction.TransactionType;
import com.team19.cs2340.user.IUser;

/**
 * Screen for users to add a new transaction to an account.
 */
public class TransactionCreationActivity extends Activity {
    /**
     * The IFinanceDataService instance used to fetch finance data.
     */
    private IFinanceDataService fds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_creation);
        fds = FinanceDataServiceFactory.createFinanceDataService(this);

        Spinner transactionType = (Spinner) findViewById(R.id.transaction_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.transaction_types,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        transactionType.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transaction_creation, menu);
        return true;
    }

    /**
     * Method called when the submit button is clicked.
     * 
     * @param view the view that was clicked
     */
    public void onSubmit(View view) {
        Spinner type = (Spinner) findViewById(R.id.transaction_type);
        EditText reason = (EditText) findViewById(R.id.reason);
        EditText category = (EditText) findViewById(R.id.category);
        EditText amount = (EditText) findViewById(R.id.amount);
        DialogDatePicker transactionDate = (DialogDatePicker) findViewById(R.id.transaction_date);

        Calendar calendar = transactionDate.getCalendar();

        Intent intent = getIntent();

        try {

            TextView textView1 = (TextView) findViewById(R.id.textView1);
            if (amount.getText().toString().equals("")) {
                textView1.setText("Not a Valid Amount");
                throw new FinanceDataException("Not a Valid Amount");
            }
            if (reason.getText().toString().equals("")) {
                textView1.setText("Not a Valid Reason");
                throw new FinanceDataException("Not a Valid Reason");
            }
            if (category.getText().toString().equals("")) {
                textView1.setText("Not a Valid Category");
                throw new FinanceDataException("Not a Valid Category");
            }

            TransactionType transactionType = TransactionType.values()[type
                    .getSelectedItemPosition()];

            BigDecimal adjustedAmount = new BigDecimal(amount.getText()
                    .toString());
            if (transactionType == TransactionType.WITHDRAWAL) {
                adjustedAmount = adjustedAmount.negate();
            }

            fds.createTransaction((IAccount) intent
                    .getSerializableExtra("account"), calendar
                    .getTimeInMillis(), transactionType, category.getText()
                    .toString(), adjustedAmount, reason.getText().toString());

            Intent goUp = new Intent(this, TransactionListActivity.class);
            goUp.putExtra("account",
                    (IAccount) intent.getSerializableExtra("account"));
            goUp.putExtra("user", (IUser) intent.getSerializableExtra("user"));
            NavUtils.navigateUpTo(this, goUp);
        } catch (FinanceDataException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method called when the cancel button is clicked.
     * 
     * @param view the view that was clicked
     */
    public void onCancel(View view) {
        Intent intent = getIntent();
        Intent goUp = new Intent(this, TransactionListActivity.class);
        goUp.putExtra("account",
                (IAccount) intent.getSerializableExtra("account"));
        goUp.putExtra("user", (IUser) intent.getSerializableExtra("user"));
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
                goUp.putExtra("account",
                        (IAccount) intent.getSerializableExtra("account"));
                goUp.putExtra("user", (IUser) intent.getSerializableExtra("user"));
                NavUtils.navigateUpTo(this, goUp);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
