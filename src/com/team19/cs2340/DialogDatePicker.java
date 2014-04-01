package com.team19.cs2340;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 *  A date picker control that displays a popup to allow the
 *  user to easily select a date with a spinner.
 */
public class DialogDatePicker extends EditText {

    /**
     * The current date displayed by the DateDialogPicker.
     */
    Calendar calendar = null;

    /**
     * Instantiates a new DataDialogPicker.
     * 
     * @param context a context
     * @param attrs an attribute set
     */
    public DialogDatePicker(final Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setInputType(0);

        setCalendar(GregorianCalendar.getInstance());

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                }
            }
        });

        refreshDisplay();
    }

    /**
     * Displayes the date picker dialog.
     */
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                            int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        refreshDisplay();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * Sets the date displayed by the DateDialogPicker.
     * 
     * @param calendar the new date
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        if (this.calendar == null) {
            this.calendar = GregorianCalendar.getInstance();
        }
        this.calendar.set(Calendar.HOUR_OF_DAY, 0);
        this.calendar.set(Calendar.MINUTE, 0);
        this.calendar.set(Calendar.SECOND, 0);
        this.calendar.set(Calendar.MILLISECOND, 0);
        refreshDisplay();
    }

    /**
     * Get the current date selected.
     * 
     * @return the currently selected date.
     */
    public Calendar getCalendar() {
        return this.calendar;
    }

    /**
     * Refreshes the date displayed in the EditText.
     */
    private void refreshDisplay() {
        this.setText(DateFormat.getDateFormat(getContext()).format(calendar.getTime()));
    }
}