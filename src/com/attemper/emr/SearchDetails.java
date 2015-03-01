package com.attemper.emr;

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
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.attemper.emr.acl.model.Principle;
import com.attemper.emr.authorized.model.AddPatientModel;
import com.attemper.emr.patient.Patient;
import com.attemper.emr.patient.android.ParcelablePatient;
import com.attemper.emr.patient.hateoas.PatientResource;

public class SearchDetails extends Activity {
	
	private String username;
	private String password;
	private long userID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_details);
		
		SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
	    username = settings.getString("username", "");
	    password = settings.getString("password", "");
	    userID = settings.getLong("userid", 0L);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		final ParcelablePatient parcelablePatient = (ParcelablePatient) getIntent().getParcelableExtra("patientResource");
		final PatientResource patientResource = parcelablePatient.getPatientResource();
		final Patient patient = patientResource.getContent();
		
		((TextView)findViewById(R.id.txtSearchDetailFirstName)).setText(patient.getFirstName());
		((TextView)findViewById(R.id.txtSearchDetailMiddleName)).setText(patient.getMiddleName());
		((TextView)findViewById(R.id.txtSearchDetailLastName)).setText(patient.getLastName());
		((TextView)findViewById(R.id.txtSearchDetailBirthdate)).setText(patient.getBirthdate());
		((TextView)findViewById(R.id.txtSearchDetailSocialSecurity)).setText(patient.getSocialSecurityNumber());
		
		final Button btnSubmit = (Button) findViewById(R.id.btnSearchDetailAddPatient);
		btnSubmit.setOnClickListener(new View.OnClickListener() {			
            public void onClick(View v) {
            	AddPatientModel model = new AddPatientModel();
            	model.setPatientId(patientResource.getId().getHref());
            	model.setUserId(userID);
            	new HttpRequestTask(getApplicationContext()).execute(model);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		return super.onOptionsItemSelected(item);
	}
	
	private class HttpRequestTask extends AsyncTask<AddPatientModel, Void, Boolean> {
		
		private Context context;
		
		public HttpRequestTask(Context context) {
			this.context = context;
		}
		
        @Override
        protected Boolean doInBackground(AddPatientModel... model) {
        	final String url = "https://jbossews-projectemr.rhcloud.com/emr/authorized/patients";
        	
        	// Set the username and password for creating a Basic Auth request
        	HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
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
        	    ResponseEntity<Principle> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Principle.class);
        	    if(response.getStatusCode() == HttpStatus.CREATED) {
        	    	return true;
        	    }
        	} catch (HttpClientErrorException e) {
        	    Log.e("SearchDetails", e.getLocalizedMessage(), e);
        	    // Handle 401 Unauthorized response
        	    if(e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
	        	    Intent intent = new Intent(context, LoginActivity.class);
	    			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	        startActivity(intent);
        	    }
        	} catch (SecurityException e) {
        		Log.e("SearchDetails", e.getLocalizedMessage(), e);
        	}

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
        	if(success) {
	        	Toast toast = Toast.makeText(getApplicationContext(), "Patient Added", Toast.LENGTH_LONG);
	        	toast.show();
	        	finish();
        	} else {
        		Toast toast = Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG);
	        	toast.show();
        	}
        }
    }
}
