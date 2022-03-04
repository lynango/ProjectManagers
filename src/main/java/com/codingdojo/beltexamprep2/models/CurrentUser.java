package com.codingdojo.beltexamprep2.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CurrentUser {
    
		@NotEmpty(message="Email is required")
		@Email(message="Please provide a valid email address")
	    private String email;
	    
	    @NotEmpty(message="Password is required")
	    @Size(min = 8, max = 128, message="Password must be between 8 and 128 characters")
	    private String password;
    
    public CurrentUser() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
}
