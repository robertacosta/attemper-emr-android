package com.attemper.emr.assessment.android;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;

import android.os.Parcel;
import android.os.Parcelable;

import com.attemper.emr.assessment.Abdomen;
import com.attemper.emr.assessment.Assessment;
import com.attemper.emr.assessment.Breakdown;
import com.attemper.emr.assessment.BreathSounds;
import com.attemper.emr.assessment.Cardio;
import com.attemper.emr.assessment.Cough;
import com.attemper.emr.assessment.Edema;
import com.attemper.emr.assessment.Gastrointestinal;
import com.attemper.emr.assessment.Incision;
import com.attemper.emr.assessment.MuscleStrength;
import com.attemper.emr.assessment.Neurological;
import com.attemper.emr.assessment.Pulse;
import com.attemper.emr.assessment.Pupil;
import com.attemper.emr.assessment.Respirations;
import com.attemper.emr.assessment.Respiratory;
import com.attemper.emr.assessment.Skin;
import com.attemper.emr.assessment.Temperature;
import com.attemper.emr.assessment.Urine;
import com.attemper.emr.assessment.hateoas.AssessmentResource;

public class ParcelableAssessment implements Parcelable {
	private AssessmentResource assessmentResource;

	public AssessmentResource getAssessmentResource() {
		return assessmentResource;
	}

	public void setAssessmentResource(AssessmentResource assessmentResource) {
		this.assessmentResource = assessmentResource;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(assessmentResource.getId().getHref());  // Resource ID
		
		// Assessment basic info
		Assessment assessment = assessmentResource.getContent();
		dest.writeString(assessment.getId());
		dest.writeString(assessment.getDate());
		dest.writeString(assessment.getTime());
		
		// Neurological
		Neurological neurological = assessment.getNeurological();
		dest.writeString(neurological.getLevelOfConsciousness());
		dest.writeString(neurological.getOrientation());
		
		Pupil rightPupil = neurological.getRightPupil();
		dest.writeInt(rightPupil.getSize());
		dest.writeString(rightPupil.getReaction());
		
		Pupil leftPupil = neurological.getLeftPupil();
		dest.writeInt(leftPupil.getSize());
		dest.writeString(leftPupil.getReaction());
		
		MuscleStrength upperExtremity = neurological.getUpperExtremity();
		dest.writeString(upperExtremity.getLeft());
		dest.writeString(upperExtremity.getRight());
		
		MuscleStrength lowerExtremity = neurological.getLowerExtremity();
		dest.writeString(lowerExtremity.getLeft());
		dest.writeString(lowerExtremity.getRight());
		
		// Respiratory
		Respiratory respiratory = assessment.getRespiratory();
		dest.writeString(respiratory.getChestExcursion());
		
		BreathSounds leftBreathSounds = respiratory.getLeftBreathSounds();
		dest.writeString(leftBreathSounds.getAnterior());
		dest.writeString(leftBreathSounds.getPosterior());
		
		BreathSounds rightBreathSounds = respiratory.getRightBreathSounds();
		dest.writeString(rightBreathSounds.getAnterior());
		dest.writeString(rightBreathSounds.getPosterior());
		
		Respirations respirations = respiratory.getRespirations();
		dest.writeInt(respirations.isNoDistress() ? 1 : 0);
		dest.writeInt(respirations.isLabored() ? 1 : 0);
		dest.writeInt(respirations.isShortnessOfBreath() ? 1 : 0);
		
		Cough cough = respiratory.getCough();
		dest.writeInt(cough.isPresent() ? 1 : 0);
		dest.writeInt(cough.isProductive() ? 1 : 0);
		
		// Cardio
		Cardio cardio = assessment.getCardio();
		dest.writeString(cardio.getCapillaryRefill());
		dest.writeInt(cardio.isJvd() ? 1 : 0);
		dest.writeString(cardio.getRhythm());
		
		Pulse radialPulse = cardio.getRadialPulse();
		dest.writeString(radialPulse.getLeft());
		dest.writeString(radialPulse.getRight());
		
		Pulse pedalPulse = cardio.getPedalPulse();
		dest.writeString(pedalPulse.getLeft());
		dest.writeString(pedalPulse.getRight());
		
		Temperature temperature = cardio.getTemperature();
		dest.writeInt(temperature.isWarm() ? 1 : 0);
		dest.writeInt(temperature.isClammy() ? 1 : 0);
		dest.writeInt(temperature.isDiaphoretic() ? 1 : 0);
		
		Edema rightArm = cardio.getRightArm();
		dest.writeInt(rightArm.getLevel());
		dest.writeInt(rightArm.isPitting() ? 1 : 0);
		
		Edema leftArm = cardio.getLeftArm();
		dest.writeInt(leftArm.getLevel());
		dest.writeInt(leftArm.isPitting() ? 1 : 0);
		
		Edema rightLeg = cardio.getRightLeg();
		dest.writeInt(rightLeg.getLevel());
		dest.writeInt(rightLeg.isPitting() ? 1 : 0);
		
		Edema leftLeg = cardio.getLeftLeg();
		dest.writeInt(leftLeg.getLevel());
		dest.writeInt(leftLeg.isPitting() ? 1 : 0);
		
		// Gastrointestinal
		Gastrointestinal gastrointestinal = assessment.getGastrointestinal();
		dest.writeString(gastrointestinal.getBowelSounds());
		dest.writeString(gastrointestinal.getTurgor());
		dest.writeString(gastrointestinal.getSkin());
		dest.writeInt(gastrointestinal.isStoolContinent() ? 1 : 0);
		dest.writeInt(gastrointestinal.isNausea() ? 1 : 0);
		dest.writeInt(gastrointestinal.isEmesis() ? 1 : 0);
		
		Abdomen abdomen = gastrointestinal.getAbdomen();
		dest.writeString(abdomen.getFeeling());
		dest.writeInt(abdomen.isDistended() ? 1 : 0);
		
		Urine urine = gastrointestinal.getUrine();
		dest.writeString(urine.getColor());
		dest.writeString(urine.getCharacter());
		dest.writeInt(urine.isFoleyCatheter() ? 1 : 0);
		
		// Skin -- TODO: Add skin
//		Skin skin = assessment.getSkin();
//		dest.writeTypedList(skin.getIncisions());
//		dest.writeTypedList(skin.getBreakdowns());
	}
	
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<ParcelableAssessment> CREATOR = new Parcelable.Creator<ParcelableAssessment>() {
        public ParcelableAssessment createFromParcel(Parcel in) {
            return new ParcelableAssessment(in);
        }

        public ParcelableAssessment[] newArray(int size) {
            return new ParcelableAssessment[size];
        }
    };
    
    public ParcelableAssessment(AssessmentResource assessmentResource) {
    	this.assessmentResource = assessmentResource;
    }

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private ParcelableAssessment(Parcel in) {
    	String resourceHref = in.readString();
    	Link resourceLink = new Link(resourceHref);
    	
    	Assessment assessment = new Assessment();
    	assessment.setId(in.readString());
    	assessment.setDate(in.readString());
    	assessment.setTime(in.readString());
    	
    	Neurological neurological = new Neurological();
    	neurological.setLevelOfConsciousness(in.readString());
    	neurological.setOrientation(in.readString());
    	
    	Pupil rightPupil = new Pupil();
    	rightPupil.setSize(in.readInt());
    	rightPupil.setReaction(in.readString());
    	neurological.setRightPupil(rightPupil);
    	
    	Pupil leftPupil = new Pupil();
    	leftPupil.setSize(in.readInt());
    	leftPupil.setReaction(in.readString());
    	neurological.setLeftPupil(leftPupil);
    	
    	MuscleStrength upperExtremity = new MuscleStrength();
    	upperExtremity.setLeft(in.readString());
    	upperExtremity.setRight(in.readString());
    	neurological.setUpperExtremity(upperExtremity);
    	
    	MuscleStrength lowerExtremity = new MuscleStrength();
    	lowerExtremity.setLeft(in.readString());
    	lowerExtremity.setRight(in.readString());
    	neurological.setLowerExtremity(lowerExtremity);
    	assessment.setNeurological(neurological);
    	
    	Respiratory respiratory = new Respiratory();
    	respiratory.setChestExcursion(in.readString());
    	
    	BreathSounds leftBreathSounds = new BreathSounds();
    	leftBreathSounds.setAnterior(in.readString());
    	leftBreathSounds.setPosterior(in.readString());
    	respiratory.setLeftBreathSounds(leftBreathSounds);
    	
    	BreathSounds rightBreathSounds = new BreathSounds();
    	rightBreathSounds.setAnterior(in.readString());
    	rightBreathSounds.setPosterior(in.readString());
    	respiratory.setRightBreathSounds(rightBreathSounds);
    	
    	Respirations respirations = new Respirations();
    	respirations.setNoDistress(in.readInt() == 1);
    	respirations.setLabored(in.readInt() == 1);
    	respirations.setShortnessOfBreath(in.readInt() == 1);
    	respiratory.setRespirations(respirations);
    	
    	Cough cough = new Cough();
    	cough.setPresent(in.readInt() == 1);
    	cough.setProductive(in.readInt() == 1);
    	respiratory.setCough(cough);
    	assessment.setRespiratory(respiratory);
    	
    	Cardio cardio = new Cardio();
    	cardio.setCapillaryRefill(in.readString());
    	cardio.setJvd(in.readInt() == 1);
    	cardio.setRhythm(in.readString());
    	
    	Pulse radialPulse = new Pulse();
    	radialPulse.setLeft(in.readString());
    	radialPulse.setRight(in.readString());
    	cardio.setRadialPulse(radialPulse);
    	
    	Pulse pedalPulse = new Pulse();
    	pedalPulse.setLeft(in.readString());
    	pedalPulse.setRight(in.readString());
    	cardio.setPedalPulse(pedalPulse);
    	
    	Temperature temperature = new Temperature();
    	temperature.setWarm(in.readInt() == 1);
    	temperature.setClammy(in.readInt() == 1);
    	temperature.setDiaphoretic(in.readInt() == 1);
    	cardio.setTemperature(temperature);
    	
    	Edema rightArm = new Edema();
    	rightArm.setLevel(in.readInt());
    	rightArm.setPitting(in.readInt() == 1);
    	cardio.setRightArm(rightArm);
    	
    	Edema leftArm = new Edema();
    	leftArm.setLevel(in.readInt());
    	leftArm.setPitting(in.readInt() == 1);
    	cardio.setLeftArm(leftArm);
    	
    	Edema rightLeg = new Edema();
    	rightLeg.setLevel(in.readInt());
    	rightLeg.setPitting(in.readInt() == 1);
    	cardio.setRightLeg(rightLeg);
    	
    	Edema leftLeg = new Edema();
    	leftLeg.setLevel(in.readInt());
    	leftLeg.setPitting(in.readInt() == 1);
    	cardio.setLeftLeg(leftLeg);
    	assessment.setCardio(cardio);
    	
    	Gastrointestinal gastrointestinal = new Gastrointestinal();
    	gastrointestinal.setBowelSounds(in.readString());
    	gastrointestinal.setTurgor(in.readString());
    	gastrointestinal.setSkin(in.readString());
    	gastrointestinal.setStoolContinent(in.readInt() == 1);
    	gastrointestinal.setNausea(in.readInt() == 1);
    	gastrointestinal.setEmesis(in.readInt() == 1);
    	
    	Abdomen abdomen = new Abdomen();
    	abdomen.setFeeling(in.readString());
    	abdomen.setDistended(in.readInt() == 1);
    	gastrointestinal.setAbdomen(abdomen);
    	
    	Urine urine = new Urine();
    	urine.setColor(in.readString());
    	urine.setCharacter(in.readString());
    	urine.setFoleyCatheter(in.readInt() == 1);
    	gastrointestinal.setUrine(urine);
    	assessment.setGastrointestinal(gastrointestinal);
    	
    	// TODO: Add skin
//    	Skin skin = new Skin();
//    	List<Incision> incisions = new ArrayList<Incision>();
//    	in.readTypedList(incisions, Incision.CREATOR);
//    	skin.setIncisions(incisions);
//    	
//    	List<Breakdown> breakdowns = new ArrayList<Breakdown>();
//    	in.readTypedList(breakdowns, Breakdown.CREATOR);
//    	skin.setBreakdowns(breakdowns);
//    	assessment.setSkin(skin);
    	
    	assessmentResource = new AssessmentResource(assessment, resourceLink);
    }
}
