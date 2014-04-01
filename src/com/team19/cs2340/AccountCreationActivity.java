package com.team19.cs2340;

import java.math.BigDecimal;

import com.team19.cs2340.finance.FinanceDataException;
import com.team19.cs2340.finance.FinanceDataServiceFactory;
import com.team19.cs2340.finance.IFinanceDataService;
import com.team19.cs2340.user.IUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Activity where the user can create a new account.
 */
public class AccountCreationActivity extends Activity {
    /**
     * FinanceDataService instance for modifying account data.
     */
    private IFinanceDataService fds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        fds = FinanceDataServiceFactory.createFinanceDataService(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account_creation, menu);
        return true;
    }

    /**
     * Method called when the submit button is clicked.
     * 
     * @param view the view that was clicked
     */
    public void onSubmit(View view) {
        EditText fullName = (EditText) findViewById(R.id.full_name);
        EditText displayName = (EditText) findViewById(R.id.display_name);
        EditText balance = (EditText) findViewById(R.id.balance);
        EditText monthlyInterest = (EditText) findViewById(R.id.monthly_interest);

        // TODO Implement checking for exceptions
        Intent intent = getIntent();
        try {
            fds.createAccount((IUser) intent.getSerializableExtra("user"),
                    fullName.getText().toString(),
                    displayName.getText().toString(),
                    new BigDecimal(balance.getText().toString()),
                    new BigDecimal(monthlyInterest.getText().toString()));

            Intent goUp = new Intent(this, HomeScreenActivity.class);
            goUp.putExtra("user", (IUser) intent.getSerializableExtra("user"));
            NavUtils.navigateUpTo(this, goUp);
        } catch (FinanceDataException e) {

        }
    }

    /**
     * Method called when the cancel button is clicked.
     * 
     * @param view the view that was clicked.
     */
    public void onCancel(View view) {
        Intent intent = getIntent();
        Intent goUp = new Intent(this, HomeScreenActivity.class);
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
                Intent goUp = new Intent(this, HomeScreenActivity.class);
                goUp.putExtra("user", (IUser) intent.getSerializableExtra("user"));
                NavUtils.navigateUpTo(this, goUp);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
