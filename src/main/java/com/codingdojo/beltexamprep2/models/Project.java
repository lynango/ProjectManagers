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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="projects")
public class Project {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
				
		@NotEmpty(message="Title is required")
		@Size(min=4, max=60, message="Please provide a title between 4-60 characters")
		private String title;
				
		@NotEmpty(message="Description is required")
		@Size(min = 5, max = 250, message="Please provide a description between 5-250 characters")
		private String description;
		
		@NotNull(message="Please select a due date for the project")
		@Future
		@DateTimeFormat(pattern="yyyy-MM-dd")
		private Date dueDate;
				
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
	   
	    @OneToMany(mappedBy="project",fetch=FetchType.LAZY,cascade=CascadeType.ALL) 
		 private List<Task> tasks;
	    
	    @ManyToOne(fetch=FetchType.LAZY)
	    @JoinColumn(name="lead_id")
	    private User lead;
		
		@ManyToMany(fetch = FetchType.LAZY)
		@JoinTable(
		     name = "users_projects", 
		     joinColumns = @JoinColumn(name = "project_id"), 
		     inverseJoinColumns = @JoinColumn(name = "user_id"))
			private List<User> users;
	
// Constructor
		
	public Project() {		
	}
	
// Getters and Setters	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public User getLead() {
		return lead;
	}

	public void setLead(User lead) {
		this.lead = lead;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}	
}
