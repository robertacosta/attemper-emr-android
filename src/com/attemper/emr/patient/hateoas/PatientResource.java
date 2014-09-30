package com.attemper.emr.patient.hateoas;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import com.attemper.emr.patient.Patient;

public class PatientResource extends Resource<Patient> {
	public PatientResource() {
		super(new Patient());
	}
 
	public PatientResource(final Patient content, final Iterable<Link> links) {
		super(content, links);
	}
 
	public PatientResource(final Patient content, final Link... links) {
		super(content, links);
	}
}
