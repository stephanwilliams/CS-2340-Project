package com.team19.cs2340;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class DialogDatePicker extends EditText {
	
	Calendar calendar = null;
	
	public DialogDatePicker(final Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.setInputType(0);
		
		calendar = GregorianCalendar.getInstance();
		
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog datePickerDialog = new DatePickerDialog(
						context,
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								calendar.set(Calendar.YEAR, year);
								calendar.set(Calendar.MONTH, monthOfYear);
								calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
								refreshDisplay();
							}
						},
						calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH));
				datePickerDialog.show();
			}
		});
		
		refreshDisplay();
	}
	
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		refreshDisplay();
	}
	
	public Calendar getCalendar() {
		return this.calendar;
	}
	
	private void refreshDisplay() {
		if (calendar == null) calendar = GregorianCalendar.getInstance();
		this.setText((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR));
	}
}