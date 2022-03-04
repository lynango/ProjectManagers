package com.codingdojo.beltexamprep2.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.beltexamprep2.models.Project;
import com.codingdojo.beltexamprep2.models.User;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {
	List<Project> findAll();
	List<Project> findAllByUsersContaining(User user);
	List<Project> findByUsersNotContains(User user);
}