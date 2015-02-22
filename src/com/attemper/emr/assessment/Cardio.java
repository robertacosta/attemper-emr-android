package com.attemper.emr.assessment;

public class Cardio {
	private Pulse radialPulse;
	private Pulse pedalPulse;
	private String capillaryRefill;
	private Temperature temperature;
	private Edema rightArm;
	private Edema leftArm;
	private Edema rightLeg;
	private Edema leftLeg;
	private boolean jvd;
	private String rhythm;
	
	public Pulse getRadialPulse() {
		return radialPulse;
	}
	public void setRadialPulse(Pulse radialPulse) {
		this.radialPulse = radialPulse;
	}
	public Pulse getPedalPulse() {
		return pedalPulse;
	}
	public void setPedalPulse(Pulse pedalPulse) {
		this.pedalPulse = pedalPulse;
	}
	public String getCapillaryRefill() {
		return capillaryRefill;
	}
	public void setCapillaryRefill(String capillaryRefill) {
		this.capillaryRefill = capillaryRefill;
	}
	public Temperature getTemperature() {
		return temperature;
	}
	public void setTemperature(Temperature temperature) {
		this.temperature = temperature;
	}
	public Edema getRightArm() {
		return rightArm;
	}
	public void setRightArm(Edema rightArm) {
		this.rightArm = rightArm;
	}
	public Edema getLeftArm() {
		return leftArm;
	}
	public void setLeftArm(Edema leftArm) {
		this.leftArm = leftArm;
	}
	public Edema getRightLeg() {
		return rightLeg;
	}
	public void setRightLeg(Edema rightLeg) {
		this.rightLeg = rightLeg;
	}
	public Edema getLeftLeg() {
		return leftLeg;
	}
	public void setLeftLeg(Edema leftLeg) {
		this.leftLeg = leftLeg;
	}
	public boolean isJvd() {
		return jvd;
	}
	public void setJvd(boolean jvd) {
		this.jvd = jvd;
	}
	public String getRhythm() {
		return rhythm;
	}
	public void setRhythm(String rhythm) {
		this.rhythm = rhythm;
	}
}
