package com.attemper.emr;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.attemper.emr.adapters.AssessmentListArrayAdapter;
import com.attemper.emr.assessment.Assessment;
import com.attemper.emr.assessment.android.ParcelableAssessment;
import com.attemper.emr.assessment.hateoas.AssessmentResource;
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
		
		setTitle(String.format("%s %s Assessments", patient.getFirstName(), patient.getLastName()));
		
		final ListView listview = (ListView) findViewById(R.id.lstAssesssments);
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?>adapter,View view, int position, long id){
				Assessment assessment = (Assessment)adapter.getItemAtPosition(position);
				AssessmentResource assessmentResource = new AssessmentResource(assessment, 
						new Link("https://jbossews-projectemr.rhcloud.com/emr/assessment/" + assessment.getId()));
				
				Intent intent = new Intent(view.getContext(), AssessmentDetailsActivity.class);
				intent.putExtra("assessmentResource", new ParcelableAssessment(assessmentResource));
				startActivity(intent);
			}
		});
		
		final Button btnAddAssessment = (Button) findViewById(R.id.btnAddAssessment);
		btnAddAssessment.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent addAssessmentIntent = new Intent(v.getContext(), AddAssessmentActivity.class);
				startActivity(addAssessmentIntent);
			}
		});
		
		new AssessmentsHttpRequestTask(this).execute(patient.getId());
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
		if (id == R.id.action_return_home) {
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class AssessmentsHttpRequestTask extends AsyncTask<String, Void, List<Assessment>> {
		
		private Context context;
		
		public AssessmentsHttpRequestTask(Context context) {
			this.context = context;
		}
		
        @Override
        protected List<Assessment> doInBackground(String... params) {
        	String url = "https://jbossews-projectemr.rhcloud.com/emr/authorized/assessments?patientid={patientid}";
        	
        	// Set the username and password for creating a Basic Auth request
        	HttpAuthentication authHeader = new HttpBasicAuthentication("racosta", "something");
        	HttpHeaders requestHeaders = new HttpHeaders();
        	
        	requestHeaders.setContentType(new MediaType("application","hal+json"));
        	requestHeaders.setAuthorization(authHeader);
        	HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
        	
        	try {
        	    // Make the HTTP GET request to the Basic Auth protected URL
        		RestTemplate restTemplate = new RestTemplate();
        		ParameterizedTypeReference<List<Assessment>> typeRef = new ParameterizedTypeReference<List<Assessment>>() {};
            	ResponseEntity<List<Assessment>> response = restTemplate.exchange(url, HttpMethod.GET, request, typeRef, params[0]);
        	    if(response.getStatusCode() == HttpStatus.OK) {
        	    	return response.getBody();
        	    }
        	} catch (HttpClientErrorException e) {
        	    Log.e("AssessmentManagementActivity", e.getLocalizedMessage(), e);
        	    // Handle 401 Unauthorized response
        	} catch (SecurityException e) {
        		Log.e("AssessmentManagementActivity", e.getLocalizedMessage(), e);
        	}

            return null;
        }

        @Override
        protected void onPostExecute(List<Assessment> results) {
        	if(results != null) {
	        	ListView lv = (ListView) findViewById(R.id.lstAssesssments);
	        	Assessment[] array = results.toArray(new Assessment[results.size()]);
	        	AssessmentListArrayAdapter adapter = new AssessmentListArrayAdapter(context, array);
	        	if(lv.getHeaderViewsCount() < 1) {
		        	View header = (View)getLayoutInflater().inflate(R.layout.list_item_assessment, null);
		            lv.addHeaderView(header);
	        	}
	            lv.setAdapter(adapter);
        	}
        }
    }
}
