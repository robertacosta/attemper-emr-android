package com.attemper.emr.assessment;

public class Gastrointestinal {
	private String bowelSounds;
	private String turgor;
	private Abdomen abdomen;
	private String skin;
	private Urine urine;
	private boolean stoolContinent;
	private boolean nausea;
	private boolean emesis;
	
	public String getBowelSounds() {
		return bowelSounds;
	}
	public void setBowelSounds(String bowelSounds) {
		this.bowelSounds = bowelSounds;
	}
	public String getTurgor() {
		return turgor;
	}
	public void setTurgor(String turgor) {
		this.turgor = turgor;
	}
	public Abdomen getAbdomen() {
		return abdomen;
	}
	public void setAbdomen(Abdomen abdomen) {
		this.abdomen = abdomen;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public Urine getUrine() {
		return urine;
	}
	public void setUrine(Urine urine) {
		this.urine = urine;
	}
	public boolean isStoolContinent() {
		return stoolContinent;
	}
	public void setStoolContinent(boolean stoolContinent) {
		this.stoolContinent = stoolContinent;
	}
	public boolean isNausea() {
		return nausea;
	}
	public void setNausea(boolean nausea) {
		this.nausea = nausea;
	}
	public boolean isEmesis() {
		return emesis;
	}
	public void setEmesis(boolean emesis) {
		this.emesis = emesis;
	}
}
