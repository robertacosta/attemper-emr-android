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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.attemper.emr.assessment.Abdomen;
import com.attemper.emr.assessment.Assessment;
import com.attemper.emr.assessment.BreathSounds;
import com.attemper.emr.assessment.Cardio;
import com.attemper.emr.assessment.Cough;
import com.attemper.emr.assessment.Edema;
import com.attemper.emr.assessment.Gastrointestinal;
import com.attemper.emr.assessment.MuscleStrength;
import com.attemper.emr.assessment.Neurological;
import com.attemper.emr.assessment.Pulse;
import com.attemper.emr.assessment.Pupil;
import com.attemper.emr.assessment.Respirations;
import com.attemper.emr.assessment.Respiratory;
import com.attemper.emr.assessment.Temperature;
import com.attemper.emr.assessment.Urine;
import com.attemper.emr.authorized.model.AssociateAssessmentModel;

public class AddAssessmentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_assessment);
		
		final String patientId = getIntent().getStringExtra("patientId");

		final Button btnSubmit = (Button) findViewById(R.id.btnAddAssessment);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			public int tryParse(String number) {
				try {
					return Integer.parseInt(number);
				} catch (NumberFormatException e) {
					return 0;
				}
			}
			
            public void onClick(View v) {
            	Assessment assessment = new Assessment();
            	assessment.setDate(((EditText)findViewById(R.id.txtDate)).getText().toString());
            	assessment.setTime(((EditText)findViewById(R.id.txtTime)).getText().toString());
            	
            	Neurological neurological = new Neurological();
            	neurological.setLevelOfConsciousness(((Spinner)findViewById(R.id.spnLOC)).getSelectedItem().toString());
            	neurological.setOrientation(((Spinner)findViewById(R.id.spnOrientation)).getSelectedItem().toString());
            	
            	Pupil rightEye = new Pupil();
            	rightEye.setSize(tryParse(((Spinner)findViewById(R.id.spnRightEyeSize)).getSelectedItem().toString()));
            	rightEye.setReaction(((Spinner)findViewById(R.id.spnRightEyeReaction)).getSelectedItem().toString());
            	neurological.setRightPupil(rightEye);
            	
            	Pupil leftEye = new Pupil();
            	leftEye.setSize(tryParse(((Spinner)findViewById(R.id.spnLeftEyeSize)).getSelectedItem().toString()));
            	leftEye.setReaction(((Spinner)findViewById(R.id.spnLeftEyeReaction)).getSelectedItem().toString());
            	neurological.setLeftPupil(leftEye);
            	
            	MuscleStrength upperExtremity = new MuscleStrength();
            	upperExtremity.setRight(((Spinner)findViewById(R.id.spnUERightMuscleStrength)).getSelectedItem().toString());
            	upperExtremity.setLeft(((Spinner)findViewById(R.id.spnUELeftMuscleStrength)).getSelectedItem().toString());
            	neurological.setUpperExtremity(upperExtremity);
            	
            	MuscleStrength lowerExtremity = new MuscleStrength();
            	lowerExtremity.setRight(((Spinner)findViewById(R.id.spnLERightMuscleStrength)).getSelectedItem().toString());
            	lowerExtremity.setLeft(((Spinner)findViewById(R.id.spnLELeftMuscleStrength)).getSelectedItem().toString());
            	neurological.setUpperExtremity(lowerExtremity);
            	assessment.setNeurological(neurological);
            	
            	Respiratory respiratory = new Respiratory();
            	BreathSounds rightBreathSounds = new BreathSounds();
            	rightBreathSounds.setAnterior(((Spinner)findViewById(R.id.spnRightAnterior)).getSelectedItem().toString());
            	rightBreathSounds.setPosterior(((Spinner)findViewById(R.id.spnRightPosterior)).getSelectedItem().toString());
            	respiratory.setRightBreathSounds(rightBreathSounds);
            	
            	BreathSounds leftBreathSounds = new BreathSounds();
            	leftBreathSounds.setAnterior(((Spinner)findViewById(R.id.spnLeftAnterior)).getSelectedItem().toString());
            	leftBreathSounds.setPosterior(((Spinner)findViewById(R.id.spnLeftPosterior)).getSelectedItem().toString());
            	respiratory.setLeftBreathSounds(leftBreathSounds);
            	
            	Respirations respirations = new Respirations();
            	respirations.setLabored(((CheckBox)findViewById(R.id.chkLabored)).isChecked());
            	respirations.setNoDistress(((CheckBox)findViewById(R.id.chkNoDistress)).isChecked());
            	respirations.setShortnessOfBreath(((CheckBox)findViewById(R.id.chkShortnessOfBreath)).isChecked());
            	respiratory.setRespirations(respirations);
            	RadioGroup chestExcursion = (RadioGroup)findViewById(R.id.rgpChestExcursion);
            	respiratory.setChestExcursion(((RadioButton)findViewById(chestExcursion.getCheckedRadioButtonId())).getText().toString());
            	
            	Cough cough = new Cough();
            	cough.setPresent(((CheckBox)findViewById(R.id.chkCough)).isChecked());
            	cough.setProductive(((CheckBox)findViewById(R.id.chkCoughProductive)).isChecked());
            	respiratory.setCough(cough);
            	assessment.setRespiratory(respiratory);
            	
            	Cardio cardio = new Cardio();
            	Pulse radialPulse = new Pulse();
            	radialPulse.setRight(((Spinner)findViewById(R.id.spnRightRadialPulse)).getSelectedItem().toString());
            	radialPulse.setLeft(((Spinner)findViewById(R.id.spnLeftRadialPulse)).getSelectedItem().toString());
            	cardio.setRadialPulse(radialPulse);
            	
            	Pulse pedalPulse = new Pulse();
            	pedalPulse.setRight(((Spinner)findViewById(R.id.spnRightPedalPulse)).getSelectedItem().toString());
            	pedalPulse.setLeft(((Spinner)findViewById(R.id.spnLeftPedalPulse)).getSelectedItem().toString());
            	cardio.setPedalPulse(pedalPulse);
            	
            	RadioGroup rgpCapillaryRefill = (RadioGroup)findViewById(R.id.rgpCapRefill);
            	cardio.setCapillaryRefill(((RadioButton)findViewById(rgpCapillaryRefill.getCheckedRadioButtonId())).getText().toString());
            	
            	Temperature temperature = new Temperature();
            	RadioGroup rgpTemperature = (RadioGroup)findViewById(R.id.rgpSkin);
            	temperature.setWarm(((RadioButton)findViewById(rgpTemperature.getCheckedRadioButtonId())).getText().toString().compareTo("Warm") == 0 ? true : false);
            	temperature.setClammy(((CheckBox)findViewById(R.id.chkClammy)).isChecked());
            	temperature.setDiaphoretic(((CheckBox)findViewById(R.id.chkDiaphoretic)).isChecked());
            	cardio.setTemperature(temperature);
            	
            	Edema rightArmEdema = new Edema();
            	rightArmEdema.setPitting(((CheckBox)findViewById(R.id.chkRightArmPitting)).isChecked());
            	rightArmEdema.setLevel(tryParse(((Spinner)findViewById(R.id.spnRightArmEdema)).getSelectedItem().toString()));
            	cardio.setRightArm(rightArmEdema);
            	
            	Edema leftArmEdema = new Edema();
            	leftArmEdema.setPitting(((CheckBox)findViewById(R.id.chkLeftArmPitting)).isChecked());
            	leftArmEdema.setLevel(tryParse(((Spinner)findViewById(R.id.spnLeftArmEdema)).getSelectedItem().toString()));
            	cardio.setLeftArm(leftArmEdema);
            	
            	Edema rigthLegEdema = new Edema();
            	rigthLegEdema.setPitting(((CheckBox)findViewById(R.id.chkRightLegPitting)).isChecked());
            	rigthLegEdema.setLevel(tryParse(((Spinner)findViewById(R.id.spnRightLegEdema)).getSelectedItem().toString()));
            	cardio.setRightLeg(rigthLegEdema);
            	
            	Edema leftLegEdema = new Edema();
            	leftLegEdema.setPitting(((CheckBox)findViewById(R.id.chkLeftLegPitting)).isChecked());
            	leftLegEdema.setLevel(tryParse(((Spinner)findViewById(R.id.spnLeftLegEdema)).getSelectedItem().toString()));
            	cardio.setLeftLeg(leftLegEdema);
            	
            	cardio.setJvd(((CheckBox)findViewById(R.id.chkJVD)).isChecked());
            	
            	cardio.setRhythm(((EditText)findViewById(R.id.txtRhythm)).getText().toString());
            	assessment.setCardio(cardio);
            	
            	Gastrointestinal gastrointestinal = new Gastrointestinal();
            	RadioGroup rgpBowelSounds = (RadioGroup)findViewById(R.id.rgpBowelSounds);
            	gastrointestinal.setBowelSounds(((RadioButton)findViewById(rgpBowelSounds.getCheckedRadioButtonId())).getText().toString());
            	
            	Abdomen abdomen = new Abdomen();
            	RadioGroup rgpAbdomen = (RadioGroup)findViewById(R.id.rgpAbdomen);
            	abdomen.setFeeling(((RadioButton)findViewById(rgpAbdomen.getCheckedRadioButtonId())).getText().toString());
            	abdomen.setDistended(((CheckBox)findViewById(R.id.chkDistended)).isChecked());
            	gastrointestinal.setAbdomen(abdomen);
            	
            	RadioGroup rgpTurgor = (RadioGroup)findViewById(R.id.rgpTurgor);
            	gastrointestinal.setTurgor(((RadioButton)findViewById(rgpTurgor.getCheckedRadioButtonId())).getText().toString());
            	
            	RadioGroup rgpSkinColor = (RadioGroup)findViewById(R.id.rgpSkinColor);
            	gastrointestinal.setSkin(((RadioButton)findViewById(rgpSkinColor.getCheckedRadioButtonId())).getText().toString());
            	
            	Urine urine = new Urine();
            	urine.setColor(((Spinner)findViewById(R.id.spnUrineColor)).getSelectedItem().toString());
            	urine.setCharacter(((Spinner)findViewById(R.id.spnUrineCharacter)).getSelectedItem().toString());
            	urine.setFoleyCatheter(((CheckBox)findViewById(R.id.chkUrineFoleyCatheter)).isChecked());
            	gastrointestinal.setUrine(urine);
            	gastrointestinal.setNausea(((CheckBox)findViewById(R.id.chkNausea)).isChecked());
            	gastrointestinal.setEmesis(((CheckBox)findViewById(R.id.chkEmesis)).isChecked());
            	assessment.setGastrointestinal(gastrointestinal);
            	
            	AssociateAssessmentModel assessmentModel = new AssociateAssessmentModel();
            	assessmentModel.setAssessment(assessment);
            	assessmentModel.setPatientId(patientId);
            	
            	new HttpRequestTask().execute(assessmentModel);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_assessment, menu);
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
	
	private class HttpRequestTask extends AsyncTask<AssociateAssessmentModel, Void, Boolean> {
        @Override
        protected Boolean doInBackground(AssociateAssessmentModel... model) {
        	final String url = "https://jbossews-projectemr.rhcloud.com/emr/authorized/assessment";
        	
        	// Set the username and password for creating a Basic Auth request
        	HttpAuthentication authHeader = new HttpBasicAuthentication("racosta", "something");
        	HttpHeaders requestHeaders = new HttpHeaders();
        	
        	requestHeaders.setContentType(new MediaType("application","json"));
        	requestHeaders.setAuthorization(authHeader);
        	HttpEntity<AssociateAssessmentModel> requestEntity = new HttpEntity<AssociateAssessmentModel>(model[0], requestHeaders);

        	// Create a new RestTemplate instance
        	RestTemplate restTemplate = new RestTemplate();

        	// Add the String message converter
        	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        	try {
        	    // Make the HTTP GET request to the Basic Auth protected URL
        	    ResponseEntity<Assessment> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Assessment.class);
        	    if(response.getStatusCode() == HttpStatus.CREATED) {
        	    	return true;
        	    }
        	} catch (HttpClientErrorException e) {
        	    Log.e("AddAssessmentActivity", e.getLocalizedMessage(), e);
        	    // Handle 401 Unauthorized response
        	} catch (SecurityException e) {
        		Log.e("AddAssessmentActivity", e.getLocalizedMessage(), e);
        	}

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
        	if(success) {
	        	Toast toast = Toast.makeText(getApplicationContext(), "Assessment Added", Toast.LENGTH_LONG);
	        	toast.show();
	        	finish();
        	} else {
        		Toast toast = Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG);
	        	toast.show();
        	}
        }

    }
}
