package com.codingdojo.beltexamprep2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.beltexamprep2.models.UserProject;

@Repository
public interface UserProjectRepository extends CrudRepository<UserProject, Long> {

}
