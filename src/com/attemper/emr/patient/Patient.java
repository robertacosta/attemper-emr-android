package com.attemper.emr.patient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Patient {
	
	@JsonProperty("id")
	private String id;
	
	// Basics:
	private String firstName;
	private String lastName;
	private String middleName;
	private int height;
	private int weight;
	private String birthdate;
	private String socialSecurityNumber;
	private String primaryLanguage;
	private String statusCode;
	private Address address;
	private PhoneNumber phoneNumber;
	private Insurance insurance;
	private EmergencyContact emergencyContact;
	private String livesAt;
	private boolean hasWill;
	private boolean hasAdvancedDirective;
	private boolean hasFluShot;
	private String fluShotDate;
	private boolean hasPneumoniaShot;
	private String pneumoniaShotDate;
	
	// Birthday
	// Social Security Number
	// Address (Street Address, Apartment #, City, State, Zip, Country?)
	// Phone Number (Home, Mobile, Work)
	// Insurance (Company Name, Policy #, Contact Phone)
	// Primary Language (string)
	// Emergency Contact (Name, Phone Number, Relationship)
	// Lives at: Home (Alone) / Home (with Family) / Nursing Home / Assisted Living  (String)
	// Has: Will and/or Advanced Directive  (booleans)
	// Code Status (Full Code / Modified Code / DNR)
	// Has: Flu? With date  (only true if within 1 year)
	// Pneumonia?  With dates  (only true if within 5 years)
	// 
	// Allergies (Different tab)
	// List of allergies (in edit, shows an empty field (or pre populated with current) and click plus to add more)
	//
	// Isolation History (Different tab)
	// Show any current isolation on first tab under name
	// Isolations (Types - Contact / Droplet / Airborne, Description)
	//
	// Admitting (Diagnosis Title, Date - Show below patient name, Doctor)
	// 
	// Medical History (Different Tab)
	// See Admission Sheet
	//
	// Family History (Different Tab)
	// See Admission Sheet
	//
	// Social History (Different Tab)
	// See Admission Sheet
	//
	// Psych History (Different Tab)
	// Yes/No Depression, Yes/No Suicide Risk
	//
	// Fall History (Different Tab)
	// How many times in the past year?
	// Circumstances
	// Date of last fall (if this date is over 1 year old, show no last fall)
	
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	public EmergencyContact getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(EmergencyContact emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public String getLivesAt() {
		return livesAt;
	}

	public void setLivesAt(String livesAt) {
		this.livesAt = livesAt;
	}

	public boolean isHasWill() {
		return hasWill;
	}

	public void setHasWill(boolean hasWill) {
		this.hasWill = hasWill;
	}

	public boolean isHasAdvancedDirective() {
		return hasAdvancedDirective;
	}

	public void setHasAdvancedDirective(boolean hasAdvancedDirective) {
		this.hasAdvancedDirective = hasAdvancedDirective;
	}

	public boolean isHasFluShot() {
		return hasFluShot;
	}

	public void setHasFluShot(boolean hasFluShot) {
		this.hasFluShot = hasFluShot;
	}

	public String getFluShotDate() {
		return fluShotDate;
	}

	public void setFluShotDate(String fluShotDate) {
		this.fluShotDate = fluShotDate;
	}

	public boolean isHasPneumoniaShot() {
		return hasPneumoniaShot;
	}

	public void setHasPneumoniaShot(boolean hasPneumoniaShot) {
		this.hasPneumoniaShot = hasPneumoniaShot;
	}

	public String getPneumoniaShotDate() {
		return pneumoniaShotDate;
	}

	public void setPneumoniaShotDate(String pneumoniaShotDate) {
		this.pneumoniaShotDate = pneumoniaShotDate;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPrimaryLanguage() {
		return primaryLanguage;
	}

	public void setPrimaryLanguage(String primaryLanguage) {
		this.primaryLanguage = primaryLanguage;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}
}