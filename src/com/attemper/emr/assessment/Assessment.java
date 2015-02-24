package com.attemper.emr.assessment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Assessment {
	
	@JsonProperty("id")
	private String id;
	private String date;
	private String time;
	
	private Neurological neurological;
	private Respiratory respiratory;
	private Cardio cardio;
	private Gastrointestinal gastrointestinal;
	private Skin skin;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Neurological getNeurological() {
		return neurological;
	}
	public void setNeurological(Neurological neurological) {
		this.neurological = neurological;
	}
	public Respiratory getRespiratory() {
		return respiratory;
	}
	public void setRespiratory(Respiratory respiratory) {
		this.respiratory = respiratory;
	}
	public Cardio getCardio() {
		return cardio;
	}
	public void setCardio(Cardio cardio) {
		this.cardio = cardio;
	}
	public Gastrointestinal getGastrointestinal() {
		return gastrointestinal;
	}
	public void setGastrointestinal(Gastrointestinal gastrointestinal) {
		this.gastrointestinal = gastrointestinal;
	}
	public Skin getSkin() {
		return skin;
	}
	public void setSkin(Skin skin) {
		this.skin = skin;
	}
}
