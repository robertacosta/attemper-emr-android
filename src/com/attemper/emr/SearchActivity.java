package com.attemper.emr;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.attemper.emr.adapters.PatientListArrayAdapter;
import com.attemper.emr.config.HateosRestClient;
import com.attemper.emr.patient.hateoas.PatientResource;
import com.attemper.emr.patient.hateoas.PatientResources;

public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		final Context context = this;
		final Button btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String lastName = ((EditText)findViewById(R.id.txtLastNameSearch)).getText().toString();
            	String firstName = ((EditText)findViewById(R.id.txtFirstNameSearch)).getText().toString();
            	new SearchHttpRequestTask(context).execute(lastName, firstName);
            }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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
	
	private class SearchHttpRequestTask extends AsyncTask<String, Void, PatientResources> {
		
		private Context context;
		private HateosRestClient restClient = new HateosRestClient();
		
		public SearchHttpRequestTask(Context context) {
			this.context = context;
		}
		
        @Override
        protected PatientResources doInBackground(String... params) {
        	String url = "https://jbossews-projectemr.rhcloud.com/emr/patient/search/findByFirstNameAndLastName?last_name={lastName}&first_name={firstName}";
        	if(params[1].isEmpty()) {
        		url = "https://jbossews-projectemr.rhcloud.com/emr/patient/search/findByLastName?last_name={lastName}";
        	}
        	
        	// Set the username and password for creating a Basic Auth request
        	HttpAuthentication authHeader = new HttpBasicAuthentication("racosta", "something");
        	HttpHeaders requestHeaders = new HttpHeaders();
        	
        	requestHeaders.setContentType(new MediaType("application","hal+json"));
        	requestHeaders.setAuthorization(authHeader);
        	HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
        	
//        	ObjectMapper mapper = new ObjectMapper();
//        	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        	mapper.registerModule(new Jackson2HalModule());
//        	mapper.setHandlerInstantiator(new HalHandlerInstantiator(new AnnotationRelProvider(), null));
//
//        	MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        	converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
//        	converter.setObjectMapper(mapper);
//        	
//        	// Create a new RestTemplate instance
//        	RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
        	
        	try {        		
        		// TRY : Resources<Resource<Patient>>
        		
        	    // Make the HTTP GET request to the Basic Auth protected URL
            	ResponseEntity<PatientResources> response = restClient.restTemplate().exchange(url, HttpMethod.GET, request, PatientResources.class, params[0], params[1]);
        	    if(response.getStatusCode() == HttpStatus.OK) {
        	    	return response.getBody();
        	    }
        	} catch (HttpClientErrorException e) {
        	    Log.e("SearchActivity", e.getLocalizedMessage(), e);
        	    // Handle 401 Unauthorized response
        	} catch (SecurityException e) {
        		Log.e("SearchActivity", e.getLocalizedMessage(), e);
        	}

            return null;
        }

        @Override
        protected void onPostExecute(PatientResources results) {
        	if(results != null) {
	        	ListView lv = (ListView) findViewById(R.id.lstSearchResults);
	        	PatientResource[] array = results.getContent().toArray(new PatientResource[results.getContent().size()]);
	        	PatientListArrayAdapter adapter = new PatientListArrayAdapter(context, array);
	        	View header = (View)getLayoutInflater().inflate(R.layout.list_item_search_result, null);
	            lv.addHeaderView(header);
	            lv.setAdapter(adapter); 
        	}
        }

    }
}
