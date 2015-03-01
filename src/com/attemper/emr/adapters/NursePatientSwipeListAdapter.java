package com.attemper.emr.adapters;


import java.util.List;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.attemper.emr.R;
import com.attemper.emr.acl.model.Principle;
import com.attemper.emr.authorized.model.AddPatientModel;
import com.attemper.emr.patient.Patient;
import com.daimajia.swipe.adapters.ArraySwipeAdapter;

public class NursePatientSwipeListAdapter extends ArraySwipeAdapter<Patient> {
	
	private final Context context;
	private final List<Patient> values;

	public NursePatientSwipeListAdapter(Context context, List<Patient> values) {
	    super(context, R.layout.list_item_nurse_swipe, values);
	    this.context = context;
	    this.values = values;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PatientHolder holder = null;
        
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_item_nurse_swipe, parent, false);
            
            holder = new PatientHolder();
            holder.txtLastName = (TextView)row.findViewById(R.id.txtListItemLastName);
            holder.txtFirstName = (TextView)row.findViewById(R.id.txtListItemFirstName);
            holder.txtBirthdate = (TextView)row.findViewById(R.id.txtListItemBirthdate);
            holder.txtId = (TextView)row.findViewById(R.id.txtListItemId);
            row.setTag(holder);
        } else {
            holder = (PatientHolder)row.getTag();
        }
        
        final Patient patient = values.get(position);
        holder.txtLastName.setText(patient.getLastName());
        holder.txtFirstName.setText(patient.getFirstName());
        holder.txtBirthdate.setText(patient.getBirthdate());
        holder.txtId.setText(patient.getId());
        
        Button deleteButton = (Button)  row.findViewById(R.id.delete);
		deleteButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AddPatientModel model = new AddPatientModel();
            	model.setPatientId("https://jbossews-projectemr.rhcloud.com/emr/patient/" + patient.getId());
            	model.setUserId(3l); // TODO: Replace with actual ID
            	new RemovePatientHttpRequestTask(patient).execute(model);
			}
        });
        
        return row;
    }
    
    static class PatientHolder
    {
    	TextView txtLastName;
        TextView txtFirstName;
        TextView txtBirthdate;
        TextView txtId;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    
    private class RemovePatientHttpRequestTask extends AsyncTask<AddPatientModel, Void, Boolean> {
    	private Patient patient;
		
		public RemovePatientHttpRequestTask(Patient patient) {
			this.patient = patient;
		}
    	
        @Override
        protected Boolean doInBackground(AddPatientModel... model) {
        	final String url = "https://jbossews-projectemr.rhcloud.com/emr/authorized/patients";
        	
        	// Set the username and password for creating a Basic Auth request
        	HttpAuthentication authHeader = new HttpBasicAuthentication("racosta", "something");
        	HttpHeaders requestHeaders = new HttpHeaders();
        	
        	requestHeaders.setContentType(new MediaType("application","json"));
        	requestHeaders.setAuthorization(authHeader);
        	HttpEntity<AddPatientModel> requestEntity = new HttpEntity<AddPatientModel>(model[0], requestHeaders);

        	// Create a new RestTemplate instance
        	RestTemplate restTemplate = new RestTemplate();

        	// Add the String message converter
        	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        	try {
        	    // Make the HTTP GET request to the Basic Auth protected URL
        	    ResponseEntity<Principle> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Principle.class);
        	    if(response.getStatusCode() == HttpStatus.OK) {
        	    	return true;
        	    }
        	} catch (HttpClientErrorException e) {
        	    Log.e("NursePatientSwipeListAdapter", e.getLocalizedMessage(), e);
        	    // Handle 401 Unauthorized response
        	} catch (SecurityException e) {
        		Log.e("NursePatientSwipeListAdapter", e.getLocalizedMessage(), e);
        	} catch (ResourceAccessException e) {
        		Log.e("NursePatientSwipeListAdapter", e.getLocalizedMessage(), e);
        	}

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
        	if(success) {
	        	Toast toast = Toast.makeText(context, "Patient removed from list", Toast.LENGTH_LONG);
	        	toast.show();
	        	values.remove(patient);
	        	notifyDataSetChanged();
        	} else {
        		Toast toast = Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG);
	        	toast.show();
        	}
        }
    }
}