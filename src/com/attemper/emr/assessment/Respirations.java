package com.attemper.emr.assessment;

public class Respirations {
	private boolean noDistress;
	private boolean labored;
	private boolean shortnessOfBreath;
	
	public boolean isNoDistress() {
		return noDistress;
	}
	public void setNoDistress(boolean noDistress) {
		this.noDistress = noDistress;
	}
	public boolean isLabored() {
		return labored;
	}
	public void setLabored(boolean labored) {
		this.labored = labored;
	}
	public boolean isShortnessOfBreath() {
		return shortnessOfBreath;
	}
	public void setShortnessOfBreath(boolean shortnessOfBreath) {
		this.shortnessOfBreath = shortnessOfBreath;
	}
}
