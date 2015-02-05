package org.myfirst.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.myfirst.domain.Comment;
import org.myfirst.domain.Constants;
import org.myfirst.domain.FirstThing;
import org.myfirst.domain.User;
import org.myfirst.dto.FirstThingDto;
import org.myfirst.dto.Mapper;
import org.myfirst.dto.RecommendationDto;
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
	
	@Autowired
	private ThingService thingService;
	
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
		FirstThing first = firstThingRepository.findOne(new Long(thingId));
		Comment c = new Comment(comment);
		c.addCommentor(user);
		commentRepository.save(c);
		first.addComment(c);
		firstThingRepository.save(first);
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
	
	public List<RecommendationDto> getThingsRecommendation(String username) {
		List<RecommendationDto> result = new ArrayList<RecommendationDto>();
		User user = findUserByUsername(username);
		List<Map<String, Object>> recommendationsList = userRepository.getThingsRecommendation(user);
		for (Map<String, Object> recommendations: recommendationsList) {
			RecommendationDto r = new RecommendationDto(
										(Long)recommendations.get("firstThingID"), 
										(String)recommendations.get("title"), 
										(String)recommendations.get("friend"));
			result.add(r);
		}
		return result;
	}
	
	public long getFollowingsPostsCount(String username) {
		long result = 0;
		User user = findUserByUsername(username);
		List<Map<String, Object>> resultList = userRepository.getFollowingsPostsCount(user);
		for (Map<String, Object> resMap: resultList) {
			result = (Long)resMap.get("count");
		}
		return result;
	}
	
	public int getNumberOfPages(String username) {
		return (int)Math.floor(getFollowingsPostsCount(username)/Constants.ITEMS_PER_PAGE);
	}
	
	public TreeSet<FirstThingDto> getTimelinePosts(String username, int page) {
		TreeSet<FirstThingDto> result = new TreeSet<FirstThingDto>();
		User user = findUserByUsername(username);
		List<Map<String, Object>> thingsList = userRepository.getTimelinePosts(user, (page - 1) * Constants.ITEMS_PER_PAGE, Constants.ITEMS_PER_PAGE);
		for (Map<String, Object> things: thingsList) {
			FirstThingDto t = new FirstThingDto();
			Long id = (Long)things.get("id"); 
			FirstThing first = thingService.findFirstThingById(id);
			t.setId(id);
			t.setDate((String)things.get("date"));
			t.setUsername((String)things.get("username"));
			t.setProfilePhotoLink((String)things.get("profilePhotoLink"));
			t.setImage(first.getImage());
			t.setTitle(first.getTitle());
			t.setTags(Mapper.mapThings(first.getTags()));
			t.setDescription(first.getDescription());
			t.setComments(Mapper.mapCommentSet(first.getComments()));
			result.add(t);
		}
		return result;
	}
}
