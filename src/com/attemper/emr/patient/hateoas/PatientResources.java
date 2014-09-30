package com.attemper.emr.patient.hateoas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

import com.attemper.emr.patient.Patient;

public class PatientResources extends Resources<PatientResource> {
	public PatientResources() {
	}
 
	public PatientResources(final Iterable<PatientResource> content, final Iterable<Link> links) {
		super(content, links);
	}
 
	public PatientResources(final Iterable<PatientResource> content, final Link... links) {
		super(content, links);
	}
 
	public List<Patient> unwrap() {
		Collection<PatientResource> resources = getContent();
		List<Patient> patients = new ArrayList<Patient>(resources.size());
 
		for (PatientResource resource : resources) {
			patients.add(resource.getContent());
		}
 
		return patients;
	}
}
