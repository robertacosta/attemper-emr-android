package com.attemper.emr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddPatientActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_patient);
		
		final Button btnSubmit = (Button) findViewById(R.id.btnAddPatient);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Toast toast = Toast.makeText(getApplicationContext(), "Patient Added", Toast.LENGTH_LONG);
            	toast.show();
            	finish();
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
}
