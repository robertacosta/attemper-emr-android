package com.attemper.emr.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.attemper.emr.R;
import com.attemper.emr.patient.Patient;
import com.attemper.emr.patient.hateoas.PatientResource;

public class PatientListArrayAdapter extends ArrayAdapter<PatientResource> {
	private final Context context;
	private final PatientResource[] values;

	public PatientListArrayAdapter(Context context, PatientResource[] values) {
	    super(context, R.layout.list_item_search_result, values);
	    this.context = context;
	    this.values = values;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PatientHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_item_search_result, parent, false);
            
            holder = new PatientHolder();
            holder.txtLastName = (TextView)row.findViewById(R.id.txtListItemLastName);
            holder.txtFirstName = (TextView)row.findViewById(R.id.txtListItemFirstName);
            holder.txtBirthdate = (TextView)row.findViewById(R.id.txtListItemBirthdate);
            
            row.setTag(holder);
        }
        else
        {
            holder = (PatientHolder)row.getTag();
        }
        
        PatientResource patientResource = values[position];
        Patient patient = patientResource.getContent();
        holder.txtLastName.setText(patient.getLastName());
        holder.txtFirstName.setText(patient.getFirstName());
        holder.txtBirthdate.setText(patient.getBirthdate());
        
        return row;
    }
    
    static class PatientHolder
    {
    	TextView txtLastName;
        TextView txtFirstName;
        TextView txtBirthdate;
    }
}
