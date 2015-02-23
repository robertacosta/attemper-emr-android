package com.attemper.emr.assessment.hateoas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

import com.attemper.emr.assessment.Assessment;

public class AssessmentResources extends Resources<AssessmentResource> {
	public AssessmentResources() {
	}
 
	public AssessmentResources(final Iterable<AssessmentResource> content, final Iterable<Link> links) {
		super(content, links);
	}
 
	public AssessmentResources(final Iterable<AssessmentResource> content, final Link... links) {
		super(content, links);
	}
 
	public List<Assessment> unwrap() {
		Collection<AssessmentResource> resources = getContent();
		List<Assessment> assessments = new ArrayList<Assessment>(resources.size());
 
		for (AssessmentResource resource : resources) {
			assessments.add(resource.getContent());
		}
 
		return assessments;
	}
}
