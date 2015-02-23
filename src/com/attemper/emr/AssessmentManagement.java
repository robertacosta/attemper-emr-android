package com.attemper.emr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.attemper.emr.patient.Patient;
import com.attemper.emr.patient.android.ParcelablePatient;
import com.attemper.emr.patient.hateoas.PatientResource;

public class AssessmentManagement extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assessment_management);
		
		final ParcelablePatient parcelablePatient = (ParcelablePatient) getIntent().getParcelableExtra("patientResource");
		final PatientResource patientResource = parcelablePatient.getPatientResource();
		final Patient patient = patientResource.getContent();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assessment_management, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
