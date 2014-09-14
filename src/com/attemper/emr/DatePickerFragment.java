package com.attemper.emr;

import java.util.Calendar;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment
	implements DatePickerDialog.OnDateSetListener {
	
	OnDateSelectedListener mCallback;
	
	public interface OnDateSelectedListener {
		public void onDateSelected(int viewId, String date);
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnDateSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDateSelectedListener");
        }
    }
	
	DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
	int viewId;
	
	public DatePickerFragment(int viewId) {
		super();
		this.viewId = viewId;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		LocalDate date = new LocalDate(year, month + 1, day);
		mCallback.onDateSelected(this.viewId, date.toString(formatter));
	}
}
