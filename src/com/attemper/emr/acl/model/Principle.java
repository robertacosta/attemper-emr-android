package com.attemper.emr.acl.model;

import java.util.HashSet;
import java.util.Set;

public class Principle {

	private Long id;

	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private String password;
	
	private String jobTitle;
	
	private String role;
	
	private String email;
	
	private String lastPasswordChange;
	
	private Boolean enabled;
	
	private Set<String> patientIds = new HashSet<String>();
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<String> getPatientIds() {
		return patientIds;
	}
	public void setPatientIds(Set<String> patientIds) {
		this.patientIds = patientIds;
	}
	public String getLastPasswordChange() {
		return lastPasswordChange;
	}
	public void setLastPasswordChange(String lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
