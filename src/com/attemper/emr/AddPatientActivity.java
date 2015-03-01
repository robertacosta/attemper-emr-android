package com.attemper.emr;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.attemper.emr.DatePickerFragment.OnDateSelectedListener;
import com.attemper.emr.patient.Address;
import com.attemper.emr.patient.EmergencyContact;
import com.attemper.emr.patient.Insurance;
import com.attemper.emr.patient.Patient;
import com.attemper.emr.patient.PhoneNumber;

public class AddPatientActivity extends Activity 
	implements OnDateSelectedListener {
	
	private String username;
	private String password;
	
	DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
	PhoneNumberFormattingTextWatcher mPhoneWatcher = new PhoneNumberFormattingTextWatcher();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_patient);
		
		SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
	    username = settings.getString("username", "");
	    password = settings.getString("password", "");
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		final Button btnSubmit = (Button) findViewById(R.id.btnAddPatient);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			public int tryParse(String number) {
				try {
					return Integer.parseInt(number);
				} catch (NumberFormatException e) {
					return 0;
				}
			}
			
            public void onClick(View v) {
            	Patient patient = new Patient();
            	patient.setFirstName(((EditText)findViewById(R.id.txtFirstName)).getText().toString());
            	patient.setLastName(((EditText)findViewById(R.id.txtLastName)).getText().toString());
            	patient.setMiddleName(((EditText)findViewById(R.id.txtMiddleName)).getText().toString());
            	patient.setBirthdate(((EditText)findViewById(R.id.txtBirthdate)).getText().toString());
            	patient.setSocialSecurityNumber(((EditText)findViewById(R.id.txtSocialSecurity)).getText().toString());
  
            	patient.setHeight(tryParse(((EditText)findViewById(R.id.txtHeight)).getText().toString()));
            	patient.setWeight(tryParse(((EditText)findViewById(R.id.txtWeight)).getText().toString()));
            	patient.setPrimaryLanguage(((EditText)findViewById(R.id.txtPrimaryLanguage)).getText().toString());
            	patient.setHasWill(((CheckBox)findViewById(R.id.chkWill)).isChecked());
            	patient.setHasAdvancedDirective(((CheckBox)findViewById(R.id.chkAdvancedDirective)).isChecked());
            	patient.setLivesAt(((Spinner)findViewById(R.id.spnLivesAt)).getSelectedItem().toString());
            	patient.setStatusCode(((Spinner)findViewById(R.id.spnStatusCode)).getSelectedItem().toString());
            	patient.setHasFluShot(((CheckBox)findViewById(R.id.chkFlu)).isChecked());
            	patient.setFluShotDate(((EditText)findViewById(R.id.txtFluDate)).getText().toString());
            	patient.setHasPneumoniaShot(((CheckBox)findViewById(R.id.chkPneumonia)).isChecked());
            	patient.setPneumoniaShotDate(((EditText)findViewById(R.id.txtPneumoniaDate)).getText().toString());
            	
            	Address patientAddress = new Address();
            	patientAddress.setStreetAddress(((EditText)findViewById(R.id.txtAddressStreet)).getText().toString());
            	patientAddress.setStreetAddress2(((EditText)findViewById(R.id.txtAddressStreet2)).getText().toString());
            	patientAddress.setCity(((EditText)findViewById(R.id.txtAddressCity)).getText().toString());
            	patientAddress.setState(((EditText)findViewById(R.id.txtAddressState)).getText().toString());
            	patientAddress.setZipCode(tryParse(((EditText)findViewById(R.id.txtAddressZipCode)).getText().toString()));
            	patient.setAddress(patientAddress);
            	
            	PhoneNumber patientPhone = new PhoneNumber();
            	patientPhone.setNumber(((EditText)findViewById(R.id.txtPhoneNumber)).getText().toString());
            	patientPhone.setType(((Spinner)findViewById(R.id.spnPhoneType)).getSelectedItem().toString());
            	patient.setPhoneNumber(patientPhone);
            	
            	EmergencyContact emergencyContact = new EmergencyContact();
            	emergencyContact.setName(((EditText)findViewById(R.id.txtEmgName)).getText().toString());
            	emergencyContact.setPhoneNumber(((EditText)findViewById(R.id.txtEmgPhoneNumber)).getText().toString());
            	emergencyContact.setRelationship(((EditText)findViewById(R.id.txtEmgRelationship)).getText().toString());
            	patient.setEmergencyContact(emergencyContact);
            	
            	Insurance insurance = new Insurance();
            	insurance.setName(((EditText)findViewById(R.id.txtInsCompanyName)).getText().toString());
            	insurance.setPolicyNumber(((EditText)findViewById(R.id.txtInsPolicyNumber)).getText().toString());
            	insurance.setPhoneNumber(((EditText)findViewById(R.id.txtInsPhoneNumber)).getText().toString());
            	patient.setInsurance(insurance);
            	
            	new HttpRequestTask().execute(patient);
            }
        });
		
		EditText phoneNumber = (EditText)findViewById(R.id.txtPhoneNumber);
		phoneNumber.addTextChangedListener(mPhoneWatcher);
		
		EditText emgPhoneNumber = (EditText)findViewById(R.id.txtEmgPhoneNumber);
		emgPhoneNumber.addTextChangedListener(mPhoneWatcher);
		
		EditText insPhoneNumber = (EditText)findViewById(R.id.txtInsPhoneNumber);
		insPhoneNumber.addTextChangedListener(mPhoneWatcher);
		
		EditText birthdateField = (EditText)findViewById(R.id.txtBirthdate);
		birthdateField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					DialogFragment newFragment = new DatePickerFragment(R.id.txtBirthdate);
				    newFragment.show(getFragmentManager(), "datePicker");
				}
			}
		});
		
		EditText fluDateField = (EditText)findViewById(R.id.txtFluDate);
		fluDateField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					DialogFragment newFragment = new DatePickerFragment(R.id.txtFluDate);
				    newFragment.show(getFragmentManager(), "datePicker");
				}
			}
		});
		
		EditText pneumoniaDateField = (EditText)findViewById(R.id.txtPneumoniaDate);
		pneumoniaDateField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus) {
					DialogFragment newFragment = new DatePickerFragment(R.id.txtPneumoniaDate);
				    newFragment.show(getFragmentManager(), "datePicker");
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_patient, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_cancel) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class HttpRequestTask extends AsyncTask<Patient, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Patient... patient) {
        	final String url = "https://jbossews-projectemr.rhcloud.com/emr/patient";
        	
        	// Set the username and password for creating a Basic Auth request
        	HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
        	HttpHeaders requestHeaders = new HttpHeaders();
        	
        	requestHeaders.setContentType(new MediaType("application","json"));
        	requestHeaders.setAuthorization(authHeader);
        	HttpEntity<Patient> requestEntity = new HttpEntity<Patient>(patient[0], requestHeaders);

        	// Create a new RestTemplate instance
        	RestTemplate restTemplate = new RestTemplate();

        	// Add the String message converter
        	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        	try {
        	    // Make the HTTP GET request to the Basic Auth protected URL
        	    ResponseEntity<Patient> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Patient.class);
        	    if(response.getStatusCode() == HttpStatus.CREATED) {
        	    	return true;
        	    }
        	} catch (HttpClientErrorException e) {
        	    Log.e("AddPatientActivity", e.getLocalizedMessage(), e);
        	    // Handle 401 Unauthorized response
        	} catch (SecurityException e) {
        		Log.e("AddPatientActivity", e.getLocalizedMessage(), e);
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

	@Override
	public void onDateSelected(int viewId, String date) {
		((EditText)findViewById(viewId)).setText(date);
	}
}
