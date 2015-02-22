package com.attemper.emr.assessment;

public class Respiratory {
	private BreathSounds leftBreathSounds;
	private BreathSounds rightBreathSounds;
	private Respirations respirations;
	private String chestExcursion;
	private Cough cough;
	
	public BreathSounds getLeftBreathSounds() {
		return leftBreathSounds;
	}
	public void setLeftBreathSounds(BreathSounds leftBreathSounds) {
		this.leftBreathSounds = leftBreathSounds;
	}
	public BreathSounds getRightBreathSounds() {
		return rightBreathSounds;
	}
	public void setRightBreathSounds(BreathSounds rightBreathSounds) {
		this.rightBreathSounds = rightBreathSounds;
	}
	public Respirations getRespirations() {
		return respirations;
	}
	public void setRespirations(Respirations respirations) {
		this.respirations = respirations;
	}
	public String getChestExcursion() {
		return chestExcursion;
	}
	public void setChestExcursion(String chestExcursion) {
		this.chestExcursion = chestExcursion;
	}
	public Cough getCough() {
		return cough;
	}
	public void setCough(Cough cough) {
		this.cough = cough;
	}
}
