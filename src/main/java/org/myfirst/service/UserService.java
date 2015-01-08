package org.myfirst.service;

import java.util.ArrayList;
import java.util.List;

import org.myfirst.domain.User;
import org.myfirst.repository.RoleRepository;
import org.myfirst.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public User create(User user) {
		User existingUser = userRepository.findByUsername(user.getUsername());
		
		if (existingUser != null) {
			throw new RuntimeException("Record already exists!");
		}
		
		user.getRole().setUser(user);
		return userRepository.save(user);
	}
	
	public User register(User user) {
		User existingUser = userRepository.findByEmail(user.getEmail());
		
		if (existingUser != null) {
			throw new RuntimeException("Record already exists!");
		}
		return userRepository.save(user);
	}
	
	public User verifyUser(String email, String password) {
		User user = userRepository.findByEmail(email);
		
		if (user == null || !user.getPassword().equals(password)) {
			return null;
		}

		return user;
	}
	
	public User read(User user) {
		return user;
	}
	
	public List<User> readAll() {
		List<User> users = new ArrayList<User>();
		
		EndResult<User> results = userRepository.findAll();
		for (User r: results) {
			users.add(r);
		}
		
		return users;
	}
	
	public List<User> searchFor(String input) {
		List<User> users = new ArrayList<User>();
		User user = userRepository.findByUsername(input);
		if (user != null) {
			users.add(user);
		}
		user = userRepository.findByPropertyValue("firstName", input);
		if (user != null) {
			users.add(user);
		}
		user = userRepository.findByPropertyValue("middleName", input);
		if (user != null) {
			users.add(user);
		}
		user = userRepository.findByPropertyValue("lastName", input);
		if (user != null) {
			users.add(user);
		}
		user = userRepository.findByEmail(input);
		if (user != null) {
			users.add(user);
		}
		
		return users;
	}
	
	public User findUserByUsername(String input) {
		return userRepository.findByUsername(input);
	}
	
	public User update(User user) {
		User existingUser = userRepository.findByUsername(user.getUsername());
		
		if (existingUser == null) {
			return null;
		}
		
		existingUser.setFirstName(user.getFirstName());
		existingUser.setMiddleName(user.getMiddleName());
		existingUser.setLastName(user.getLastName());
		existingUser.setCountry(user.getCountry());
		existingUser.setDateOfBirth(user.getDateOfBirth());
		existingUser.setMediaIndex(user.getMediaIndex());
		existingUser.setProfilePhotoLink(user.getProfilePhotoLink());

		return userRepository.save(existingUser);
	}
	
	public User changePassword(User user) {
		User existingUser = userRepository.findByUsername(user.getUsername());
		
		if (existingUser == null) {
			return null;
		}
		
		existingUser.setPassword(user.getPassword());

		return userRepository.save(existingUser);
	}
	
	public Boolean delete(User user) {
		User existingUser = userRepository.findByUsername(user.getUsername());
		
		if (existingUser == null) {
			return false;
		}
		
		userRepository.delete(existingUser);
		return true;
	}
	
	public User follow(User followed, String username) {
		User user = userRepository.findByUsername(username);
		
	    user.follow(followed);
		
		return userRepository.save(user);
	}
	
	public User unfollow(User followed, String username) {
		User user = userRepository.findByUsername(username);
		
	    user.unfollow(followed);
		
		return userRepository.save(user);
	}
}
