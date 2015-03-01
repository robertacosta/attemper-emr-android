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

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.attemper.emr.adapters.NursePatientSwipeListAdapter;
import com.attemper.emr.patient.Patient;
import com.attemper.emr.patient.android.ParcelablePatient;
import com.attemper.emr.patient.hateoas.PatientResource;
import com.daimajia.swipe.util.Attributes;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	private static String username;
	private static String password;
	private static long userID;
	
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
	    username = settings.getString("username", "");
	    password = settings.getString("password", "");
	    userID = settings.getLong("userid", 0L);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
//		switch (number) {
//		case 1:
//			mTitle = getString(R.string.title_section1);
//			break;
//		case 2:
//			mTitle = getString(R.string.title_section2);
//			break;
//		case 3:
//			mTitle = getString(R.string.title_section3);
//			break;
//		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		//actionBar.setTitle(mTitle);
		actionBar.setTitle(R.string.app_name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.add_patient_icon) {
			Intent addPatientIntent = new Intent(getApplicationContext(), AddPatientActivity.class);
			startActivity(addPatientIntent);
			return true;
		} else if (id == R.id.search_icon) {
			Intent searchPatientIntent = new Intent(getApplicationContext(), SearchActivity.class);
			startActivity(searchPatientIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			final ListView listview = (ListView) rootView.findViewById(R.id.lstPatients);
			listview.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?>adapter,View view, int position, long id){
					Patient patient = (Patient)adapter.getItemAtPosition(position);
					PatientResource patientResource = new PatientResource(patient, 
							new Link("https://jbossews-projectemr.rhcloud.com/emr/patient/" + patient.getId()));
					
					Intent intent = new Intent(view.getContext(), PatientDetailsActivity.class);
					intent.putExtra("patientResource", new ParcelablePatient(patientResource));
					startActivity(intent);
				}
			});
			
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
		
		@Override
		public void onResume() {
			super.onResume();
			new PatientHttpRequestTask(this.getActivity()).execute();
		}
		
		private class PatientHttpRequestTask extends AsyncTask<Void, Void, List<Patient>> {
			
			private Context context;

			public PatientHttpRequestTask(Context context) {
				this.context = context;
			}
			
	        @Override
	        protected List<Patient> doInBackground(Void... params) {
	        	String url = "https://jbossews-projectemr.rhcloud.com/emr/authorized/patients?userid={userId}";
	        	
	        	// Set the username and password for creating a Basic Auth request
	        	HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
	        	HttpHeaders requestHeaders = new HttpHeaders();
	        	
	        	requestHeaders.setContentType(new MediaType("application","hal+json"));
	        	requestHeaders.setAuthorization(authHeader);
	        	HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
	        	
	        	try {
	        	    // Make the HTTP GET request to the Basic Auth protected URL
	        		RestTemplate restTemplate = new RestTemplate();
	        		ParameterizedTypeReference<List<Patient>> typeRef = new ParameterizedTypeReference<List<Patient>>() {};
	            	ResponseEntity<List<Patient>> response = restTemplate.exchange(url, HttpMethod.GET, request, typeRef, userID);
	        	    if(response.getStatusCode() == HttpStatus.OK) {
	        	    	return response.getBody();
	        	    }
	        	} catch (HttpClientErrorException e) {
	        	    Log.e("MainActivity", e.getLocalizedMessage(), e);
	        	    // Handle 401 Unauthorized response
	        	} catch (SecurityException e) {
	        		Log.e("MainActivity", e.getLocalizedMessage(), e);
	        	}

	            return null;
	        }

	        @Override
	        protected void onPostExecute(List<Patient> results) {
	        	if(results != null && getView() != null) {
		        	ListView lv = (ListView) getView().findViewById(R.id.lstPatients);
		        	NursePatientSwipeListAdapter adapter = new NursePatientSwipeListAdapter(context, results);
		        	lv.setAdapter(adapter);
		        	adapter.setMode(Attributes.Mode.Single);
	        	}
	        }
	    }
	}
}
