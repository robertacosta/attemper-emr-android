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
import android.content.Intent;
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

import com.attemper.emr.patient.Address;
import com.attemper.emr.patient.EmergencyContact;
import com.attemper.emr.patient.Insurance;
import com.attemper.emr.patient.Patient;
import com.attemper.emr.patient.PhoneNumber;
import com.attemper.emr.patient.android.ParcelablePatient;
import com.attemper.emr.patient.hateoas.PatientResource;

public class PatientDetailsActivity extends Activity {

	DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
	PhoneNumberFormattingTextWatcher mPhoneWatcher = new PhoneNumberFormattingTextWatcher();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_details);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		final ParcelablePatient parcelablePatient = (ParcelablePatient) getIntent().getParcelableExtra("patientResource");
		final PatientResource patientResource = parcelablePatient.getPatientResource();
		final Patient patient = patientResource.getContent();
		
		((EditText)findViewById(R.id.txtFirstName)).setText(patient.getFirstName());
		((EditText)findViewById(R.id.txtMiddleName)).setText(patient.getMiddleName());
		((EditText)findViewById(R.id.txtLastName)).setText(patient.getLastName());
		((EditText)findViewById(R.id.txtBirthdate)).setText(patient.getBirthdate());
		((EditText)findViewById(R.id.txtSocialSecurity)).setText(patient.getSocialSecurityNumber());
		((EditText)findViewById(R.id.txtHeight)).setText(String.valueOf(patient.getHeight()));
		((EditText)findViewById(R.id.txtWeight)).setText(String.valueOf(patient.getWeight()));
		((EditText)findViewById(R.id.txtPrimaryLanguage)).setText(patient.getPrimaryLanguage());
		((CheckBox)findViewById(R.id.chkWill)).setChecked(patient.isHasWill());
		((CheckBox)findViewById(R.id.chkAdvancedDirective)).setChecked(patient.isHasAdvancedDirective());
		for(int i = 0; i < getResources().getStringArray(R.array.LivesAtTypes).length; i++) {
			String s = getResources().getStringArray(R.array.LivesAtTypes)[i];
			if(s.compareTo(patient.getLivesAt()) == 0) {
				((Spinner)findViewById(R.id.spnLivesAt)).setSelection(i);
			}
			
		}
		
		for(int i = 0; i < getResources().getStringArray(R.array.CodeStatusTypes).length; i++) {
			String s = getResources().getStringArray(R.array.CodeStatusTypes)[i];
			if(s.compareTo(patient.getStatusCode()) == 0) {
				((Spinner)findViewById(R.id.spnStatusCode)).setSelection(i);
			}
			
		}

		((CheckBox)findViewById(R.id.chkFlu)).setChecked(patient.isHasFluShot());
		((EditText)findViewById(R.id.txtFluDate)).setText(patient.getFluShotDate());
		((CheckBox)findViewById(R.id.chkPneumonia)).setChecked(patient.isHasPneumoniaShot());
		((EditText)findViewById(R.id.txtPneumoniaDate)).setText(patient.getPneumoniaShotDate());
		
		((EditText)findViewById(R.id.txtAddressStreet)).setText(patient.getAddress().getStreetAddress());
		((EditText)findViewById(R.id.txtAddressStreet2)).setText(patient.getAddress().getStreetAddress2());
		((EditText)findViewById(R.id.txtAddressCity)).setText(patient.getAddress().getCity());
		((EditText)findViewById(R.id.txtAddressState)).setText(patient.getAddress().getState());
		((EditText)findViewById(R.id.txtAddressZipCode)).setText(String.valueOf(patient.getAddress().getZipCode()));
		
		((EditText)findViewById(R.id.txtPhoneNumber)).setText(patient.getPhoneNumber().getNumber());
		
		for(int i = 0; i < getResources().getStringArray(R.array.PhoneNumberTypes).length; i++) {
			String s = getResources().getStringArray(R.array.PhoneNumberTypes)[i];
			if(s.compareTo(patient.getPhoneNumber().getType()) == 0) {
				((Spinner)findViewById(R.id.spnPhoneType)).setSelection(i);
			}
			
		}
		
		((EditText)findViewById(R.id.txtEmgName)).setText(patient.getEmergencyContact().getName());
		((EditText)findViewById(R.id.txtEmgPhoneNumber)).setText(patient.getEmergencyContact().getPhoneNumber());
		((EditText)findViewById(R.id.txtEmgRelationship)).setText(patient.getEmergencyContact().getRelationship());
		
		((EditText)findViewById(R.id.txtInsCompanyName)).setText(patient.getInsurance().getName());
		((EditText)findViewById(R.id.txtInsPolicyNumber)).setText(patient.getInsurance().getPolicyNumber());
		((EditText)findViewById(R.id.txtInsPhoneNumber)).setText(patient.getInsurance().getPhoneNumber());
		
		final Button btnSubmit = (Button) findViewById(R.id.btnUpdatePatient);
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
            	
            	new HttpRequestTask(patientResource.getId().getHref()).execute(patient);
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
		getMenuInflater().inflate(R.menu.patient_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_cancel) {
			finish();
			return true;
		}
		if (id == R.id.action_assessment) {
			final ParcelablePatient parcelablePatient = (ParcelablePatient) getIntent().getParcelableExtra("patientResource");
			Intent intent = new Intent(getApplicationContext(), AssessmentManagement.class);
			intent.putExtra("patientResource", parcelablePatient);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class HttpRequestTask extends AsyncTask<Patient, Void, Boolean> {
		
		private String patientHref;
		
		public HttpRequestTask(String patientHref) {
			this.patientHref = patientHref;
		}
		
        @Override
        protected Boolean doInBackground(Patient... patient) {
        	final String url = patientHref;
        	
        	// Set the username and password for creating a Basic Auth request
        	HttpAuthentication authHeader = new HttpBasicAuthentication("racosta", "something");
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
        	    ResponseEntity<Patient> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Patient.class);
        	    if(response.getStatusCode() == HttpStatus.NO_CONTENT) {
        	    	return true;
        	    }
        	} catch (HttpClientErrorException e) {
        	    Log.e("PatientDetailsActivity", e.getLocalizedMessage(), e);
        	    // Handle 401 Unauthorized response
        	} catch (SecurityException e) {
        		Log.e("PatientDetailsActivity", e.getLocalizedMessage(), e);
        	}

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
        	if(success) {
	        	Toast toast = Toast.makeText(getApplicationContext(), "Patient Updated", Toast.LENGTH_LONG);
	        	toast.show();
	        	finish();
        	} else {
        		Toast toast = Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG);
	        	toast.show();
        	}
        }

    }
}
