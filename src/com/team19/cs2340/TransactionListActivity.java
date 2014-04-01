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

import com.team19.cs2340.finance.FinanceDataException;
import com.team19.cs2340.finance.FinanceDataServiceFactory;
import com.team19.cs2340.finance.IAccount;
import com.team19.cs2340.finance.IFinanceDataService;
import com.team19.cs2340.finance.ITransaction;
import com.team19.cs2340.user.IUser;

/**
 * Activity to display a list of transactions for a given account.
 *
 */
public class TransactionListActivity extends Activity {
    /**
     * FinanceDataService to retrieve transaction data from.
     */
    IFinanceDataService fds = null;
    /**
     * The account whose transactions are to be shown.
     */
    IAccount account;
    /**
     * The user whose account's transactions are being displayed.
     */
    IUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        Intent intent = getIntent();
        account = (IAccount) intent.getSerializableExtra("account");
        user = (IUser) intent.getSerializableExtra("user");

        fds = FinanceDataServiceFactory.createFinanceDataService(this);

        TextView fullName = (TextView) findViewById(R.id.account_name);
        fullName.setText(account.getFullName());

        TextView balance = (TextView) findViewById(R.id.account_balance);

        try {
            List<ITransaction> transactions = fds.getTransactions(account);
            Collections.sort(transactions, new Comparator<ITransaction>() {
                @Override
                public int compare(ITransaction t1, ITransaction t2) {
                    int effective = (int) (t2.getEffectiveTimestamp() - t1
                            .getEffectiveTimestamp());
                    if (effective == 0) {
                        int added = (int) (t2.getAddedTimestamp() - t1
                                .getAddedTimestamp());
                        return added;
                    } else {
                        return effective;
                    }
                }
            });
            BigDecimal sum = account.getBalance();
            for (ITransaction trans : transactions) {
                sum = sum.add(trans.getAmount());
            }
            NumberFormat format = NumberFormat.getCurrencyInstance();
            balance.setText(format.format(sum.doubleValue()));
            ArrayAdapter<ITransaction> adapter = new TransactionListAdapter(
                    this, R.layout.activity_transaction_list, transactions);

            ListView listView = (ListView) findViewById(R.id.transaction_list);
            listView.setAdapter(adapter);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (FinanceDataException e) {
            e.printStackTrace();
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transaction_list, menu);
        return true;
    }

    @Override
    protected void onResume() {
        // do update stuff here
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
                Intent goUp = new Intent(this, HomeScreenActivity.class);
                goUp.putExtra("user", user);
                NavUtils.navigateUpTo(this, goUp);
                return true;
            case R.id.action_add_transaction:
                Intent intent = new Intent(this, TransactionCreationActivity.class);
                intent.putExtra("account", account);
                intent.putExtra("user", user);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * ArrayAdapter subclass to display the list of transactions.
     *
     */
    private static class TransactionListAdapter extends
            ArrayAdapter<ITransaction> {

        /**
         * Instantiates a TransactionListAdapter.
         * 
         * @param context a context
         * @param resource the resource ID for the listview row
         * @param objects an array of objects to populate the list
         */
        public TransactionListAdapter(Context context, int resource,
                List<ITransaction> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ITransaction transaction = getItem(position);

            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.transaction_list_item,
                    parent, false);

            // set category
            TextView category = (TextView) rowView
                    .findViewById(R.id.transaction_category);
            category.setText(transaction.getCategory());

            // set reason
            TextView reason = (TextView) rowView
                    .findViewById(R.id.transaction_reason);
            reason.setText(transaction.getReason());

            // set amount
            TextView amount = (TextView) rowView
                    .findViewById(R.id.transaction_amount);
            if (transaction.getType() == ITransaction.TransactionType.WITHDRAWAL) {
                amount.setTextColor(Color.RED);
            } else {
                amount.setTextColor(Color.parseColor("#00AA00"));
            }

            NumberFormat format = NumberFormat.getCurrencyInstance();
            amount.setText(format.format(transaction.getAmount().doubleValue()));

            // set date
            TextView date = (TextView) rowView
                    .findViewById(R.id.transaction_date);
            DateFormat dformat = DateFormat.getDateInstance();
            String formatedDate = dformat.format(transaction
                    .getEffectiveTimestamp());
            date.setText(formatedDate);

            return rowView;
        }

    }

}
