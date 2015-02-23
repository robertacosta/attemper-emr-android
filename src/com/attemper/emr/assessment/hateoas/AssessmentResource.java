package com.attemper.emr.assessment.hateoas;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import com.attemper.emr.assessment.Assessment;

public class AssessmentResource extends Resource<Assessment> {
	public AssessmentResource() {
		super(new Assessment());
	}
 
	public AssessmentResource(final Assessment content, final Iterable<Link> links) {
		super(content, links);
	}
 
	public AssessmentResource(final Assessment content, final Link... links) {
		super(content, links);
	}
}
