package com.attemper.emr.assessment;

public class Neurological {
	private String levelOfConsciousness;
	private String orientation;
	private Pupil rightPupil;
	private Pupil leftPupil;
	private MuscleStrength upperExtremity;
	private MuscleStrength lowerExtremity;
	
	public String getLevelOfConsciousness() {
		return levelOfConsciousness;
	}
	public void setLevelOfConsciousness(String levelOfConsciousness) {
		this.levelOfConsciousness = levelOfConsciousness;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public Pupil getRightPupil() {
		return rightPupil;
	}
	public void setRightPupil(Pupil rightPupil) {
		this.rightPupil = rightPupil;
	}
	public Pupil getLeftPupil() {
		return leftPupil;
	}
	public void setLeftPupil(Pupil leftPupil) {
		this.leftPupil = leftPupil;
	}
	public MuscleStrength getUpperExtremity() {
		return upperExtremity;
	}
	public void setUpperExtremity(MuscleStrength upperExtremity) {
		this.upperExtremity = upperExtremity;
	}
	public MuscleStrength getLowerExtremity() {
		return lowerExtremity;
	}
	public void setLowerExtremity(MuscleStrength lowerExtremity) {
		this.lowerExtremity = lowerExtremity;
	}
}