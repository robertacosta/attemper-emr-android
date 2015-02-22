package com.attemper.emr.assessment;

public class Temperature {
	private boolean warm;  // False if cool
	private boolean clammy;
	private boolean diaphoretic;
	
	public boolean isWarm() {
		return warm;
	}
	public void setWarm(boolean warm) {
		this.warm = warm;
	}
	public boolean isClammy() {
		return clammy;
	}
	public void setClammy(boolean clammy) {
		this.clammy = clammy;
	}
	public boolean isDiaphoretic() {
		return diaphoretic;
	}
	public void setDiaphoretic(boolean diaphoretic) {
		this.diaphoretic = diaphoretic;
	}
}
