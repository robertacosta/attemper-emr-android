package com.attemper.emr.patient.android;

import org.springframework.hateoas.Link;

import android.os.Parcel;
import android.os.Parcelable;

import com.attemper.emr.patient.Address;
import com.attemper.emr.patient.EmergencyContact;
import com.attemper.emr.patient.Insurance;
import com.attemper.emr.patient.Patient;
import com.attemper.emr.patient.PhoneNumber;
import com.attemper.emr.patient.hateoas.PatientResource;

public class ParcelablePatient implements Parcelable {
	
	private PatientResource patientResource;

	public PatientResource getPatientResource() {
		return patientResource;
	}

	public void setPatientResource(PatientResource patientResource) {
		this.patientResource = patientResource;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(patientResource.getId().getHref());  // Resource ID
		
		// Patient basic info
		Patient patient = patientResource.getContent();
		dest.writeString(patient.getId());
		dest.writeString(patient.getFirstName());
		dest.writeString(patient.getLastName());
		dest.writeString(patient.getMiddleName());
		dest.writeInt(patient.getHeight());
		dest.writeInt(patient.getWeight());
		dest.writeString(patient.getBirthdate());
		dest.writeString(patient.getSocialSecurityNumber());
		dest.writeString(patient.getPrimaryLanguage());
		dest.writeString(patient.getStatusCode());
		
		// Patient Address
		Address address = patient.getAddress();
		dest.writeString(address.getStreetAddress());
		dest.writeString(address.getStreetAddress2());
		dest.writeString(address.getCity());
		dest.writeString(address.getState());
		dest.writeInt(address.getZipCode());
		
		// Phone Number
		PhoneNumber phone = patient.getPhoneNumber();
		dest.writeString(phone.getNumber());
		dest.writeString(phone.getType());
		
		// Insurance
		Insurance insurance = patient.getInsurance();
		dest.writeString(insurance.getName());
		dest.writeString(insurance.getPhoneNumber());
		dest.writeString(insurance.getPolicyNumber());
		
		// Emergency Contact
		EmergencyContact contact = patient.getEmergencyContact();
		dest.writeString(contact.getName());
		dest.writeString(contact.getPhoneNumber());
		dest.writeString(contact.getRelationship());
		
		dest.writeString(patient.getLivesAt());
		dest.writeInt(patient.isHasWill() ? 1 : 0);
		dest.writeInt(patient.isHasAdvancedDirective() ? 1 : 0);
		dest.writeInt(patient.isHasFluShot() ? 1 : 0);
		dest.writeString(patient.getFluShotDate());
		dest.writeInt(patient.isHasPneumoniaShot() ? 1 : 0);
		dest.writeString(patient.getPneumoniaShotDate());
	}
	
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<ParcelablePatient> CREATOR = new Parcelable.Creator<ParcelablePatient>() {
        public ParcelablePatient createFromParcel(Parcel in) {
            return new ParcelablePatient(in);
        }

        public ParcelablePatient[] newArray(int size) {
            return new ParcelablePatient[size];
        }
    };
    
    public ParcelablePatient(PatientResource patientResource) {
    	this.patientResource = patientResource;
    }

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private ParcelablePatient(Parcel in) {
    	String resourceHref = in.readString();
    	Link resourceLink = new Link(resourceHref);
    	
    	Patient patient = new Patient();
    	patient.setId(in.readString());
    	patient.setFirstName(in.readString());
    	patient.setLastName(in.readString());
    	patient.setMiddleName(in.readString());
    	patient.setHeight(in.readInt());
    	patient.setWeight(in.readInt());
    	patient.setBirthdate(in.readString());
    	patient.setSocialSecurityNumber(in.readString());
    	patient.setPrimaryLanguage(in.readString());
    	patient.setStatusCode(in.readString());
    	
    	Address address = new Address();
    	address.setStreetAddress(in.readString());
    	address.setStreetAddress2(in.readString());
    	address.setCity(in.readString());
    	address.setState(in.readString());
    	address.setZipCode(in.readInt());
    	patient.setAddress(address);
    	
    	PhoneNumber phone = new PhoneNumber();
    	phone.setNumber(in.readString());
    	phone.setType(in.readString());
    	patient.setPhoneNumber(phone);
    	
    	Insurance insurance = new Insurance();
    	insurance.setName(in.readString());
    	insurance.setPhoneNumber(in.readString());
    	insurance.setPolicyNumber(in.readString());
    	patient.setInsurance(insurance);
    	
    	EmergencyContact contact = new EmergencyContact();
    	contact.setName(in.readString());
    	contact.setPhoneNumber(in.readString());
    	contact.setRelationship(in.readString());
    	patient.setEmergencyContact(contact);
    	
    	patient.setLivesAt(in.readString());
    	patient.setHasWill(in.readInt() == 1);
    	patient.setHasAdvancedDirective(in.readInt() == 1);
    	patient.setHasFluShot(in.readInt() == 1);
    	patient.setFluShotDate(in.readString());
    	patient.setHasPneumoniaShot(in.readInt() == 1);
    	patient.setPneumoniaShotDate(in.readString());
    	
    	patientResource = new PatientResource(patient, resourceLink);
    }
}
