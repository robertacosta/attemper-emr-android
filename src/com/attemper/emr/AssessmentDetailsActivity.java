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
import com.attemper.emr.assessment.android.ParcelableAssessment;
import com.attemper.emr.assessment.hateoas.AssessmentResource;
import com.attemper.emr.authorized.model.AssociateAssessmentModel;

public class AssessmentDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assessment_details);
		
		final ParcelableAssessment parcelableAssessment = (ParcelableAssessment) getIntent().getParcelableExtra("assessmentResource");
		final AssessmentResource assessmentResource = parcelableAssessment.getAssessmentResource();
		final Assessment assessment = assessmentResource.getContent();
		
		final String patientId = getIntent().getStringExtra("patientId");
		
		((EditText)findViewById(R.id.txtDate)).setText(assessment.getDate());
		((EditText)findViewById(R.id.txtTime)).setText(assessment.getTime());
		
		setSpinnerValue(assessment.getNeurological().getLevelOfConsciousness(), getResources().getStringArray(R.array.LevelOfConsciousness), R.id.spnLOC);

		setSpinnerValue(assessment.getNeurological().getOrientation(), getResources().getStringArray(R.array.Orientation), R.id.spnOrientation);
		
		String[] pupilSizes = getResources().getStringArray(R.array.PupilSize);
		String[] pupilReactions = getResources().getStringArray(R.array.PupilReaction);
		setSpinnerValue(String.valueOf(assessment.getNeurological().getRightPupil().getSize()), pupilSizes, R.id.spnRightEyeSize);
		setSpinnerValue(assessment.getNeurological().getRightPupil().getReaction(), pupilReactions, R.id.spnRightEyeReaction);
		setSpinnerValue(String.valueOf(assessment.getNeurological().getLeftPupil().getSize()), pupilSizes, R.id.spnLeftEyeSize);
		setSpinnerValue(assessment.getNeurological().getLeftPupil().getReaction(), pupilReactions, R.id.spnLeftEyeReaction);
		
		String[] muscleStrengths = getResources().getStringArray(R.array.MonitorStrength);
		setSpinnerValue(assessment.getNeurological().getUpperExtremity().getRight(), muscleStrengths, R.id.spnUERightMuscleStrength);
		setSpinnerValue(assessment.getNeurological().getUpperExtremity().getLeft(), muscleStrengths, R.id.spnUELeftMuscleStrength);
		setSpinnerValue(assessment.getNeurological().getLowerExtremity().getRight(), muscleStrengths, R.id.spnLERightMuscleStrength);
		setSpinnerValue(assessment.getNeurological().getLowerExtremity().getLeft(), muscleStrengths, R.id.spnLELeftMuscleStrength);
		
		String[] breathSounds = getResources().getStringArray(R.array.BreathSounds);
		setSpinnerValue(assessment.getRespiratory().getRightBreathSounds().getAnterior(), breathSounds, R.id.spnRightAnterior);
		setSpinnerValue(assessment.getRespiratory().getRightBreathSounds().getPosterior(), breathSounds, R.id.spnRightPosterior);
		setSpinnerValue(assessment.getRespiratory().getLeftBreathSounds().getAnterior(), breathSounds, R.id.spnLeftAnterior);
		setSpinnerValue(assessment.getRespiratory().getLeftBreathSounds().getPosterior(), breathSounds, R.id.spnLeftPosterior);
		
		((CheckBox)findViewById(R.id.chkLabored)).setChecked(assessment.getRespiratory().getRespirations().isLabored());
		((CheckBox)findViewById(R.id.chkNoDistress)).setChecked(assessment.getRespiratory().getRespirations().isNoDistress());
		((CheckBox)findViewById(R.id.chkShortnessOfBreath)).setChecked(assessment.getRespiratory().getRespirations().isShortnessOfBreath());
	
		setRadioValue(assessment.getRespiratory().getChestExcursion(), R.id.rgpChestExcursion);
		
		((CheckBox)findViewById(R.id.chkCough)).setChecked(assessment.getRespiratory().getCough().isPresent());
		((CheckBox)findViewById(R.id.chkCoughProductive)).setChecked(assessment.getRespiratory().getCough().isProductive());
		
		String[] pulses = getResources().getStringArray(R.array.Pulse);
		setSpinnerValue(assessment.getCardio().getRadialPulse().getRight(), pulses, R.id.spnRightRadialPulse);
		setSpinnerValue(assessment.getCardio().getRadialPulse().getLeft(), pulses, R.id.spnLeftRadialPulse);
		setSpinnerValue(assessment.getCardio().getPedalPulse().getRight(), pulses, R.id.spnRightPedalPulse);
		setSpinnerValue(assessment.getCardio().getPedalPulse().getLeft(), pulses, R.id.spnLeftPedalPulse);
		
		setRadioValue(assessment.getCardio().getCapillaryRefill(), R.id.rgpCapRefill);
		
		setRadioValue(assessment.getCardio().getTemperature().isWarm() ? "Warm" : "Cool", R.id.rgpSkin);
		((CheckBox)findViewById(R.id.chkClammy)).setChecked(assessment.getCardio().getTemperature().isClammy());
		((CheckBox)findViewById(R.id.chkDiaphoretic)).setChecked(assessment.getCardio().getTemperature().isDiaphoretic());
		
		((CheckBox)findViewById(R.id.chkRightArmPitting)).setChecked(assessment.getCardio().getRightArm().isPitting());
		((CheckBox)findViewById(R.id.chkLeftArmPitting)).setChecked(assessment.getCardio().getLeftArm().isPitting());
		((CheckBox)findViewById(R.id.chkRightLegPitting)).setChecked(assessment.getCardio().getRightLeg().isPitting());
		((CheckBox)findViewById(R.id.chkLeftLegPitting)).setChecked(assessment.getCardio().getLeftLeg().isPitting());
		String[] edemas = getResources().getStringArray(R.array.Edema);
		setSpinnerValue("+" + assessment.getCardio().getRightArm().getLevel(), edemas, R.id.spnRightArmEdema);
		setSpinnerValue("+" + assessment.getCardio().getLeftArm().getLevel(), edemas, R.id.spnLeftArmEdema);
		setSpinnerValue("+" + assessment.getCardio().getRightLeg().getLevel(), edemas, R.id.spnRightLegEdema);
		setSpinnerValue("+" + assessment.getCardio().getLeftLeg().getLevel(), edemas, R.id.spnLeftLegEdema);
	
		((CheckBox)findViewById(R.id.chkJVD)).setChecked(assessment.getCardio().isJvd());
		
		((EditText)findViewById(R.id.txtRhythm)).setText(assessment.getCardio().getRhythm());
		
		setRadioValue(assessment.getGastrointestinal().getBowelSounds(), R.id.rgpBowelSounds);
		
		setRadioValue(assessment.getGastrointestinal().getAbdomen().getFeeling(), R.id.rgpAbdomen);
		((CheckBox)findViewById(R.id.chkDistended)).setChecked(assessment.getGastrointestinal().getAbdomen().isDistended());
		
		setRadioValue(assessment.getGastrointestinal().getTurgor(), R.id.rgpTurgor);
		
		setRadioValue(assessment.getGastrointestinal().getSkin(), R.id.rgpSkinColor);
		
		String[] colors = getResources().getStringArray(R.array.UrineColor);
		String[] characters = getResources().getStringArray(R.array.UrineCharacter);
		setSpinnerValue(assessment.getGastrointestinal().getUrine().getColor(), colors, R.id.spnUrineColor);
		setSpinnerValue(assessment.getGastrointestinal().getUrine().getCharacter(), characters, R.id.spnUrineCharacter);
		((CheckBox)findViewById(R.id.chkUrineFoleyCatheter)).setChecked(assessment.getGastrointestinal().getUrine().isFoleyCatheter());
		
		((CheckBox)findViewById(R.id.chkNausea)).setChecked(assessment.getGastrointestinal().isNausea());
		((CheckBox)findViewById(R.id.chkEmesis)).setChecked(assessment.getGastrointestinal().isEmesis());
		
		final Button btnSubmit = (Button) findViewById(R.id.btnEditAssessment);
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
            	
            	new HttpRequestTask(assessmentResource.getId().getHref()).execute(assessment);
            }
        });
		
		final Button btnDelete = (Button) findViewById(R.id.btnDeleteAssessment);
		btnDelete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AssociateAssessmentModel assessmentModel = new AssociateAssessmentModel();
            	assessmentModel.setAssessment(assessment);
            	assessmentModel.setPatientId(patientId);
            	
            	new HttpDeleteRequestTask().execute(assessmentModel);
			}
		});
	}

	private void setRadioValue(String chestExcursion, int radioGroupId) {
		RadioGroup radioGroup = ((RadioGroup)findViewById(R.id.rgpChestExcursion));
		for(int i = 0; i < radioGroup.getChildCount(); i++) {
			RadioButton radioButton = ((RadioButton)radioGroup.getChildAt(i));
			if(radioButton.getText().toString().compareTo(chestExcursion) == 0) {
				radioButton.setSelected(true);
				break;
			}
		}
	}
	
	private void setSpinnerValue(String toSetValue, String[] possibleValues, int spinnerId) {
		for(int i = 0; i < possibleValues.length; i++) {
			if(possibleValues[i].compareTo(toSetValue) == 0) {
				((Spinner)findViewById(spinnerId)).setSelection(i);
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assessment_details, menu);
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
	
	private class HttpRequestTask extends AsyncTask<Assessment, Void, Boolean> {
		
		private String assessmentHref;
		
		public HttpRequestTask(String assessmentHref) {
			this.assessmentHref = assessmentHref;
		}
		
        @Override
        protected Boolean doInBackground(Assessment... assessment) {
        	final String url = assessmentHref;
        	
        	// Set the username and password for creating a Basic Auth request
        	HttpAuthentication authHeader = new HttpBasicAuthentication("racosta", "something");
        	HttpHeaders requestHeaders = new HttpHeaders();
        	
        	requestHeaders.setContentType(new MediaType("application","json"));
        	requestHeaders.setAuthorization(authHeader);
        	HttpEntity<Assessment> requestEntity = new HttpEntity<Assessment>(assessment[0], requestHeaders);

        	// Create a new RestTemplate instance
        	RestTemplate restTemplate = new RestTemplate();

        	// Add the String message converter
        	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        	try {
        	    // Make the HTTP GET request to the Basic Auth protected URL
        	    ResponseEntity<Assessment> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Assessment.class);
        	    if(response.getStatusCode() == HttpStatus.NO_CONTENT) {
        	    	return true;
        	    }
        	} catch (HttpClientErrorException e) {
        	    Log.e("AssessmentDetailsActivity", e.getLocalizedMessage(), e);
        	    // Handle 401 Unauthorized response
        	} catch (SecurityException e) {
        		Log.e("AssessmentDetailsActivity", e.getLocalizedMessage(), e);
        	}

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
        	if(success) {
	        	Toast toast = Toast.makeText(getApplicationContext(), "Assessment Updated", Toast.LENGTH_LONG);
	        	toast.show();
	        	finish();
        	} else {
        		Toast toast = Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG);
	        	toast.show();
        	}
        }
    }
	
	private class HttpDeleteRequestTask extends AsyncTask<AssociateAssessmentModel, Void, Boolean> {
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
        	    ResponseEntity<Assessment> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Assessment.class);
        	    if(response.getStatusCode() == HttpStatus.NO_CONTENT) {
        	    	return true;
        	    }
        	} catch (HttpClientErrorException e) {
        	    Log.e("AssessmentDetailsActivity", e.getLocalizedMessage(), e);
        	    // Handle 401 Unauthorized response
        	} catch (SecurityException e) {
        		Log.e("AssessmentDetailsActivity", e.getLocalizedMessage(), e);
        	}

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
        	if(success) {
	        	Toast toast = Toast.makeText(getApplicationContext(), "Assessment Deleted", Toast.LENGTH_LONG);
	        	toast.show();
	        	finish();
        	} else {
        		Toast toast = Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG);
	        	toast.show();
        	}
        }
    }
}
