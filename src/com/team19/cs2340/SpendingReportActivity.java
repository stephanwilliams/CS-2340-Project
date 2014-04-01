package com.team19.cs2340;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.team19.cs2340.finance.FinanceDataServiceFactory;
import com.team19.cs2340.finance.IFinanceDataService;
import com.team19.cs2340.user.IUser;

/**
 *  Activity that allows users to view reports regarding their accounts.
 */
public class SpendingReportActivity extends Activity {
    /**
     * The FinanceDataService used to retrieve finance data.
     */
    private IFinanceDataService fds;
    /**
     * The user whose data is being displayed.
     */
    private IUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_report);
        // Show the Up button in the action bar.
        fds = FinanceDataServiceFactory.createFinanceDataService(this);

        Intent intent = getIntent();
        user = (IUser) intent.getSerializableExtra("user");

        Spinner spinner = (Spinner) findViewById(R.id.spendingType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.report_types,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        final DialogDatePicker startDate = (DialogDatePicker) findViewById(R.id.start_date);
        final DialogDatePicker endDate = (DialogDatePicker) findViewById(R.id.end_date);

        startDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (startDate.getCalendar().compareTo(endDate.getCalendar()) > 0) {
                    endDate.setCalendar((Calendar) startDate.getCalendar().clone());
                }
                refreshSpendingReport();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
            }
        });
        endDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (endDate.getCalendar().compareTo(startDate.getCalendar()) < 0) {
                    startDate.setCalendar((Calendar) endDate.getCalendar().clone());
                }
                refreshSpendingReport();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
            }
        });

        refreshSpendingReport();

        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *  ArrayAdapter for displaying the rows of the report.
     */
    private static class EntryListAdapter extends
            ArrayAdapter<Entry<String, BigDecimal>> {

        /**
         * Instantiates a new EntryListAdapter.
         * 
         * @param context a context
         * @param resource the resource ID of the row layout file
         * @param objects objects representing the rows of the table
         */
        public EntryListAdapter(Context context, int resource,
                List<Entry<String, BigDecimal>> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Entry<String, BigDecimal> entry = getItem(position);

            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.spending_report_list_item,
                    parent, false);

            TextView category = (TextView) rowView
                    .findViewById(R.id.spending_report_display_name);
            category.setText(entry.getKey());

            TextView amount = (TextView) rowView
                    .findViewById(R.id.spending_report_balance);

            NumberFormat format = NumberFormat.getCurrencyInstance();
            amount.setText(format.format(entry.getValue().doubleValue()));

            return rowView;
        }
    }

    /**
     * Refreshes the data in the list view.
     */
    public void refreshSpendingReport() {

        try {
            DialogDatePicker startDate = (DialogDatePicker) findViewById(R.id.start_date);
            Calendar startCal = startDate.getCalendar();

            DialogDatePicker endDate = (DialogDatePicker) findViewById(R.id.end_date);
            Calendar endCal = endDate.getCalendar();

            Map<String, BigDecimal> reportMap = fds.getCategorySpendingReport(
                    user, startCal.getTimeInMillis(), endCal.getTimeInMillis());
            // System.out.println(reportMap.keySet().size());
            // if (reportMap.keySet().size() < 1) {
            // int x = 1;
            // while (x<=1) {
            // x =0;
            // }
            // }
            List<Entry<String, BigDecimal>> reportMapArrays = new ArrayList<Map.Entry<String, BigDecimal>>(
                    reportMap.entrySet());

            ArrayAdapter<Entry<String, BigDecimal>> adapter = new EntryListAdapter(
                    this, R.layout.activity_spending_report, reportMapArrays);

            ListView listView = (ListView) findViewById(R.id.reportListView);
            listView.setAdapter(adapter);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
