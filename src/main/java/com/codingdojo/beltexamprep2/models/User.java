package com.codingdojo.beltexamprep2.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="users")
public class User {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@NotEmpty(message="First name is required - ")
		@Size(min = 2, max = 25, message="Please provide a first name between 2-25 characters - ")
		private String firstName;
		
		@NotEmpty(message="Last name is required - ")
		@Size(min = 2, max = 25, message="Please provide a last name between 2-25 characters - ")
		private String lastName;
		
		@NotEmpty(message="Email is required - ")
		@Email(message="Please provide a valid email address")
		private String email;
		
		@NotEmpty(message="Password is required - ")
		@Size(min = 8, max = 128, message="Please provide a password between 8-128 characters - ")
		private String password;
		
		@Transient
		@NotEmpty(message="Confirm password is required - ")
		@Size(min = 8, max = 128, message="The confirm password must match with the password inputed above")
		private String confirm;
		
		@Column(updatable=false)
	    @DateTimeFormat(pattern="yyyy-MM-dd")
	    private Date createdAt;
	    @DateTimeFormat(pattern="yyyy-MM-dd")
	    private Date updatedAt;
	    
	    @PrePersist
	    protected void onCreate() {
	    	this.createdAt=new Date();
	    }
	    
		@PreUpdate
		protected void onUpdate() {
		this.updatedAt=new Date();
	}
		
// Relationships
	   
	    @OneToMany(mappedBy="lead",fetch=FetchType.LAZY,cascade=CascadeType.ALL) 
	    private List<Project> leads;
		
	    @OneToMany(mappedBy="user",fetch=FetchType.LAZY,cascade=CascadeType.ALL) 
		 private List<Task> tasks;
	    
	    @ManyToMany(fetch = FetchType.LAZY)
		@JoinTable(
		     name = "users_projects", 
		     joinColumns = @JoinColumn(name = "user_id"), 
		     inverseJoinColumns = @JoinColumn(name = "project_id"))
			private List<Project> projects;
	    
// Constructor
	   
    public User() {
    	
    }

 // Getters and Setters
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Project> getLeads() {
		return leads;
	}

	public void setLeads(List<Project> leads) {
		this.leads = leads;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	} 
}
