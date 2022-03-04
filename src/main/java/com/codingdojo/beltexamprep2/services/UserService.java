package com.codingdojo.beltexamprep2.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.beltexamprep2.models.CurrentUser;
import com.codingdojo.beltexamprep2.models.User;
import com.codingdojo.beltexamprep2.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepo;
	public UserService(UserRepository userRepo) {
		this.userRepo=userRepo;
	}
		
//	Registration

	public User register(User newUser, BindingResult result) {
		
		String emailEntered=newUser.getEmail();
		String passwordEntered=newUser.getPassword();
		String confirmEntered=newUser.getConfirm();
				
		Optional<User> isUser=userRepo.findByEmail(emailEntered);
		if (isUser.isPresent()) {
			result.rejectValue("email","Taken","This email is already registered");
		}
		if (!passwordEntered.equals(confirmEntered)) {
			result.rejectValue("confirm","Matches","The Confirm Password must match Password");
		}
		
		if (result.hasErrors()) {
			return null;
		}
		else {
			String hashed = BCrypt.hashpw(passwordEntered, BCrypt.gensalt());
			newUser.setPassword(hashed);
			userRepo.save(newUser);
			return newUser;
		}
		
	}
	
	
//	Login

	public User login(CurrentUser newLoginObject,BindingResult result) {
		
		String emailEntered=newLoginObject.getEmail();
		String passwordEntered=newLoginObject.getPassword();
		
		Optional<User> isUser=userRepo.findByEmail(emailEntered);		
		if (!isUser.isPresent()) {
			result.rejectValue("email", "None", "Invalid Login/Password!");
			return null;
			}
		if(!BCrypt.checkpw(passwordEntered, isUser.get().getPassword())) {
		    result.rejectValue("password", "Matches", "Invalid Login/Password!");
		    return null;
		}

		else {
			User checkUser=isUser.get();
				return checkUser;
		
		}
	}
	
//	Find user by Id
	
	public User findUserById(Long id) {
		Optional<User> optionalUser=userRepo.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}
		else return null;
	}
}
