package org.myfirst.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.myfirst.domain.Comment;
import org.myfirst.domain.FirstThing;
import org.myfirst.domain.User;
import org.myfirst.repository.CommentRepository;
import org.myfirst.repository.FirstThingRepository;
import org.myfirst.repository.RoleRepository;
import org.myfirst.repository.UserRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private FirstThingRepository firstThingRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired private GraphDatabaseService graphDb;
	
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
	
	@Transactional
	public List<User> readAll() {
		List<User> users = new ArrayList<User>();
		
		Result<User> results = userRepository.findAll();
		for (User r: results) {
			users.add(r);
		}
		
		return users;
	}
	
	public HashSet<User> searchFor(String input) {
		HashSet<User> result = new HashSet<User>();
		Result<User> found;
		found = userRepository.findAllBySchemaPropertyValue("username", input);
		for (User u: found) {
			result.add(u);
		}
		found = userRepository.findAllBySchemaPropertyValue("firstName", input);
		for (User u: found) {
			result.add(u);
		}
		found = userRepository.findAllBySchemaPropertyValue("middleName", input);
		for (User u: found) {
			result.add(u);
		}
		found = userRepository.findAllBySchemaPropertyValue("lastName", input);
		for (User u: found) {
			result.add(u);
		}
		found = userRepository.findAllBySchemaPropertyValue("email", input);
		for (User u: found) {
			result.add(u);
		}
		
		return result;
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
	
	public void deleteUserById(Long id) {

		try (Transaction tx = graphDb.beginTx()) {
			System.out.println("deleting...");
			userRepository.delete(id);
			tx.success(); 
		} 
	}
	
	public User follow(User followed, User user) {
		
		return follow(followed, user.getUsername());
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

	public void addComment(String loggedUser, String comment,
			String thingId) {
		User user = this.findUserByUsername(loggedUser);
		System.out.println(">>> user:" + user + " " + user.getUsername());
		FirstThing first = firstThingRepository.findOne(new Long(thingId));
		System.out.println(">>> first: " + first.getTitle());
		Comment c = new Comment(comment);
		c.addCommentor(user);
		commentRepository.save(c);
		first.addComment(c);
		firstThingRepository.save(first);
		System.out.println("first saved!");
	}
	
	public List<User> getFriendsRecommendation(String username) {
		List<User> result = new ArrayList<User>();
		User user = findUserByUsername(username);
		List<Map<String, Object>> recommendationsList = userRepository.getFriendsRecommendation(user);
		for (Map<String, Object> recommendations: recommendationsList) {
			User u = findUserByUsername((String)recommendations.get("friend"));
			result.add(u);
		}
		return result;
	}
	
	public List<FirstThing> getThingsRecommendation(String username) {
		List<FirstThing> result = new ArrayList<FirstThing>();
		User user = findUserByUsername(username);
		List<Map<String, Object>> recommendationsList = userRepository.getThingsRecommendation(user);
		for (Map<String, Object> recommendations: recommendationsList) {
			System.out.println(">>> first thing id=" + recommendations.get("firstThingID"));
			System.out.println(">>> friend=" + recommendations.get("friend"));
			System.out.println(">>> mutualTags=" + recommendations.get("mutualTags"));
			FirstThing first = firstThingRepository.findOne((Long)recommendations.get("firstThingID"));
			result.add(first);
		}
		return result;
	}
}
