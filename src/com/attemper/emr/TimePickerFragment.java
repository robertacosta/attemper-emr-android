package com.attemper.emr;

import java.util.Calendar;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment
	implements TimePickerDialog.OnTimeSetListener {
	
	OnTimeSelectedListener mCallback;
	
	public interface OnTimeSelectedListener {
		public void onTimeSelected(int viewId, String date);
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnTimeSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTimeSelectedListener");
        }
    }
	
	private static DateTimeFormatter formatter = DateTimeFormat.forPattern("H:m");
	int viewId;
	
	public TimePickerFragment(int viewId) {
		super();
		this.viewId = viewId;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default time in the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		// Create a new instance of DatePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute, true);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		LocalTime time = new LocalTime(hourOfDay, minute);
		mCallback.onTimeSelected(this.viewId, time.toString(formatter));
	}
	
	public static DateTimeFormatter getFormatter() {
		return formatter;
	}
}
