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
import android.widget.EditText;
import android.widget.Toast;

import com.attemper.emr.acl.model.Principle;
import com.attemper.emr.authorized.model.UpdatePasswordModel;

public class ChangePassword extends Activity {

	public static final String PREFS_NAME = "HeartbeatPrefs";
	
	private String username;
	private String password;
	private String newPassword;
	private long userID;
	
	private EditText mPassword;
	private EditText mPasswordConfirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
	    username = settings.getString("username", "");
	    password = settings.getString("password", "");
	    userID = settings.getLong("userid", 0L);
	    
	    mPassword = (EditText)findViewById(R.id.txtPassword);
		mPasswordConfirm = (EditText)findViewById(R.id.txtPasswordConfirm);
	    
	    final Button btnSubmit = (Button) findViewById(R.id.btnChangePassword);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Reset errors.
				mPassword.setError(null);
				mPasswordConfirm.setError(null);
				
				UpdatePasswordModel changePassword = new UpdatePasswordModel();
				changePassword.setUserId(userID);
				
            	String passwordNew = mPassword.getText().toString();
            	String passwordConfirm = mPasswordConfirm.getText().toString();
            	
            	if(passwordNew.compareTo(password) == 0) {
            		mPassword.setError("Password must not be the same as the old password.");
            		mPassword.requestFocus();
            		return;
            	}
            	
            	if(passwordNew.compareTo(passwordConfirm) == 0) {
            		changePassword.setPassword(passwordConfirm);
            		newPassword = passwordConfirm;
            		new ChangePasswordTask(getApplicationContext()).execute(changePassword);
            	} else {
            		mPasswordConfirm.setError("Password entries did not match");
            		mPasswordConfirm.requestFocus();
            	}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class ChangePasswordTask extends AsyncTask<UpdatePasswordModel, Void, Principle> {

		private final Context context;

		ChangePasswordTask(Context context) {
			this.context = context;
		}

		@Override
		protected Principle doInBackground(UpdatePasswordModel... model) {			
			final String url = "https://jbossews-projectemr.rhcloud.com/emr/authorized/password";
        	
			// Set the username and password for creating a Basic Auth request
        	HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
        	HttpHeaders requestHeaders = new HttpHeaders();
        	
        	requestHeaders.setContentType(new MediaType("application","json"));
        	requestHeaders.setAuthorization(authHeader);
        	HttpEntity<UpdatePasswordModel> requestEntity = new HttpEntity<UpdatePasswordModel>(model[0], requestHeaders);
        	
        	// Create a new RestTemplate instance
        	RestTemplate restTemplate = new RestTemplate();

        	// Add the String message converter
        	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        	
        	try {
        	    // Make the HTTP GET request to the Basic Auth protected URL
        		 ResponseEntity<Principle> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Principle.class);
        	    if(response.getStatusCode() == HttpStatus.OK) {
        	    	return response.getBody();
        	    }
        	} catch (HttpClientErrorException e) {
        	    Log.e("ChangePassword", e.getLocalizedMessage(), e);
        	    // Handle 401 Unauthorized response
        	    if(e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
	        	    Intent intent = new Intent(context, LoginActivity.class);
	    			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	        startActivity(intent);
        	    }
        	} catch (SecurityException e) {
        		Log.e("ChangePassword", e.getLocalizedMessage(), e);
        	}
			
			return null;
		}

		@Override
		protected void onPostExecute(final Principle principle) {
			if (principle != null) {
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("password", newPassword);
				editor.commit();
				
				Intent intent = new Intent(context, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(intent);
			} else {
				Toast toast = Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG);
	        	toast.show();
			}
		}
	}
}
