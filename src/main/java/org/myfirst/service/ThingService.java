package org.myfirst.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.myfirst.domain.FirstThing;
import org.myfirst.domain.Thing;
import org.myfirst.domain.User;
import org.myfirst.repository.CommentRepository;
import org.myfirst.repository.FirstThingRepository;
import org.myfirst.repository.ThingRepository;
import org.myfirst.repository.UserRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ThingService {
	
	@Autowired ThingRepository thingRepository;
	
	@Autowired UserRepository userRepository;
	
	@Autowired FirstThingRepository firstThingRepository;
	
	@Autowired CommentRepository commentRepository;
	
	@Autowired private GraphDatabaseService graphDb;
	
	public List<Thing> findAllThings() {
		List<Thing> Things = new ArrayList<Thing>();
		
		Result<Thing> results = thingRepository.findAll();
		for (Thing r: results) {
			Things.add(r);
		}
		
		return Things;
	}
	public Page<Thing> searchFor(String tag, PageRequest p) {
		return thingRepository.findByTagLike(tag, p);
	}
	
	public Thing addNewThing(Thing thing) {
		Thing existingThing = thingRepository.findByTag(thing.getTag());
		
		if (existingThing != null) {
			//TODO: be more gentle
			throw new RuntimeException("Record already exists!");
		}

		return thingRepository.save(thing);
	}
	
	public Set<Thing> addNewThingsByTags(String[] tags) {
		Set<Thing> result = new HashSet<Thing>();
		for (String tag: tags) {
			if (tag != null && tag.trim().length() >= 3) {
				Thing existingThing = thingRepository.findByTag(tag);
				
				if (existingThing == null) {
					existingThing = new Thing();
					existingThing.setTag(tag);
					existingThing = thingRepository.save(existingThing);
				}
				result.add(existingThing);
			}
		}
		return result;
	}
	
	public User addNewThingByUser(Thing thing, String username) {
		Thing existingThing = thingRepository.findByTag(thing.getTag());
		User user = userRepository.findByUsername(username);
		
		if (existingThing == null) {
			existingThing = thingRepository.save(thing);
		}
	    user.interestedIn(existingThing);
		
		return userRepository.save(user);
	}
	
	public User removeInterestFromUser(String interestTag, String username) {
		Thing existingThing = thingRepository.findByTag(interestTag);
		User user = userRepository.findByUsername(username);
		
	    user.notInterestedIn(existingThing);
		
		return userRepository.save(user);
	}
	public User addNewFirstThingByUser(FirstThing first, String username, boolean isImageAdded) {
		User user = userRepository.findByUsername(username);
		firstThingRepository.save(first);
	    user.didForFirstTime(first);
	    if(isImageAdded) {
	    	user.incrementMediaIndex();
	    }
		return userRepository.save(user);
	}
	
	public void deleteFirstThing(Long id) {
		
		try (Transaction tx = graphDb.beginTx()) {
			firstThingRepository.delete(id);
			tx.success(); 
		} 
	}
	
	public User addNewToDoByUser(FirstThing first, String username, String dueDate) {
		User user = userRepository.findByUsername(username);
		firstThingRepository.save(first);
	    user.addToDo(first, dueDate);
		return userRepository.save(user);
	}
	
	public void deleteToDo(Long id) {
		
		try (Transaction tx = graphDb.beginTx()) {
			firstThingRepository.delete(id);
			tx.success(); 
		} 
	}
	
	public void deleteAllComments() {
		
		try (Transaction tx = graphDb.beginTx()) {
			commentRepository.deleteAll();
			tx.success(); 
		} 
	}
	
	public FirstThing findFirstThingById(Long id) {
		return firstThingRepository.findOne(id);
	}
	
	public User removeFirstThingFromUser(FirstThing firstThing, String username) {
		User user = userRepository.findByUsername(username);
		Long id = firstThing.getId();
	    user.removeFirstThing(firstThing);
	    User result = userRepository.save(user);
		deleteFirstThing(id);
		return result;
	}
	
	public User removeToDoFromUser(FirstThing firstThing, String username) {
		User user = userRepository.findByUsername(username);
		Long id = firstThing.getId();
	    user.removeToDo(firstThing);
	    User result = userRepository.save(user);
		deleteToDo(id);
		return result;
	}
	
	@Transactional
	public Result<FirstThing> findAllFirstThings() {
		return firstThingRepository.findAll();
	}
	
	public User userNotInterestedInThing(String id, String username) {
		FirstThing first = this.findFirstThingById(new Long(id));
		User user = userRepository.findByUsername(username);
		user.addNotInterestedInThing(first);
		User result = userRepository.save(user);
		return result;
	}
	
	public User userAlreadyDoneThing(String id, String username) {
		FirstThing first = this.findFirstThingById(new Long(id));
		User user = userRepository.findByUsername(username);
		user.addAlreadyDone(first);
		User result = userRepository.save(user);
		return result;
	}

	public User userMarkedThingAsToDo(String id, String username) {
		FirstThing first = this.findFirstThingById(new Long(id));
		User user = userRepository.findByUsername(username);
		user.addMarkedAsToDo(first);
		User result = userRepository.save(user);
		return result;
	}
}
