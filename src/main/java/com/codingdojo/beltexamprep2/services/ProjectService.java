package com.codingdojo.beltexamprep2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.beltexamprep2.models.Project;
import com.codingdojo.beltexamprep2.models.User;
import com.codingdojo.beltexamprep2.repositories.ProjectRepository;
import com.codingdojo.beltexamprep2.repositories.UserRepository;

@Service
public class ProjectService {
	
	private final ProjectRepository projectRepo;
	private final UserRepository userRepo;
	public ProjectService(ProjectRepository projectRepo, UserRepository userRepo) {
		this.projectRepo = projectRepo;
		this.userRepo = userRepo;

	}
	
//	Retrieves a specific project

	public Project findOneById(Long id) {
		Optional<Project> thisProject=projectRepo.findById(id);
		if (thisProject.isPresent()) {
			return thisProject.get();
		}
		return null;
	}
	
//	Allows a new project to be created and saved

	public Project createProject(Project project) {
		return projectRepo.save(project);
	}	
	
//	Allows a project to be edited and saved

	public Project updateProject(Project project) {
		Optional<Project> isProject=projectRepo.findById(project.getId());
		if (isProject.isPresent()) {
			Project edit=isProject.get();
			edit.setTitle(project.getTitle());
			edit.setDescription(project.getDescription());
			edit.setDueDate(project.getDueDate());
			projectRepo.save(edit);
			return edit;
		}
		else return null;
	}

//	Retrieves all projects that the user has not joined
	
	public List<Project> findOtherProjects(Long id){
	Optional<User> thisUser=userRepo.findById(id);
	if (thisUser!=null) {
		return projectRepo.findByUsersNotContains(thisUser.get());
		}
		else return null;
			
		}
			
//	Retrieves all projects that the user has joined

	public List<Project> findMyProjects(Long id){
		Optional<User> thisUser=userRepo.findById(id);
		if (thisUser.isPresent()) {
			return projectRepo.findAllByUsersContaining(thisUser.get());
			}
			else return null;				
			}
	
//	Allows a user to join a specific project
	
	public Project addUser(Project project, Long id) {
		
		User newUser=project.getUsers().get(0);
		Project thisProject= this.findOneById(id);
		if (thisProject!=null) {
			thisProject.getUsers().add(newUser);
			projectRepo.save(thisProject);			
			return thisProject;	
		}
		else return null;
	}
	
//	Allows a user to leave a specific project
	
	public Project removeUser(Project project,Long id) {
		User removeUser=project.getUsers().get(0);
		Project thisProject= this.findOneById(id);
		if (thisProject!=null) {
			thisProject.getUsers().remove(removeUser);
			projectRepo.save(thisProject);			
			return thisProject;	
		}
		else return null;
	}
	
//	Deletes a project

	public void deleteProject(Long id) {
		projectRepo.deleteById(id);
	 }
}
