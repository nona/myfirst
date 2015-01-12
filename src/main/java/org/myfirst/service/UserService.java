package org.myfirst.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.myfirst.domain.Comment;
import org.myfirst.domain.MyFirst;
import org.myfirst.domain.User;
import org.myfirst.repository.CommentRepository;
import org.myfirst.repository.MyFirstRepository;
import org.myfirst.repository.RoleRepository;
import org.myfirst.repository.UserRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private MyFirstRepository myFirstRepository;
	
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
	
	public List<User> readAll() {
		List<User> users = new ArrayList<User>();
		
		EndResult<User> results = userRepository.findAll();
		for (User r: results) {
			users.add(r);
		}
		
		return users;
	}
	
	public HashSet<User> searchFor(String input) {
		HashSet<User> result = new HashSet<User>();
		EndResult<User> found;
		found = userRepository.findAllByPropertyValue("username", input);
		for (User u: found) {
			result.add(u);
		}
		found = userRepository.findAllByPropertyValue("firstName", input);
		for (User u: found) {
			result.add(u);
		}
		found = userRepository.findAllByPropertyValue("middleName", input);
		for (User u: found) {
			result.add(u);
		}
		found = userRepository.findAllByPropertyValue("lastName", input);
		for (User u: found) {
			result.add(u);
		}
		found = userRepository.findAllByPropertyValue("email", input);
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
		Transaction tx = graphDb.beginTx();
		try {
			System.out.println("deleting...");
			userRepository.delete(id);
			tx.success(); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tx.finish();
		}
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

	public User addComment(String loggedUser, String comment,
			String thingId) {
		User user = this.findUserByUsername(loggedUser);
		System.out.println(">>> user:" + user + " " + user.getUsername());
		Comment c = new Comment(user, comment);
		commentRepository.save(c);
		MyFirst first = myFirstRepository.findOne(new Long(thingId));
		System.out.println(">>> first: " + first.getTitle());
		first.addComment(c);
		myFirstRepository.save(first);
		System.out.println("first saved!");
		return null;
	}
}
