package org.myfirst.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.myfirst.domain.MyFirst;
import org.myfirst.domain.Thing;
import org.myfirst.domain.User;
import org.myfirst.repository.MyFirstRepository;
import org.myfirst.repository.ThingRepository;
import org.myfirst.repository.UserRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Service;

@Service
public class ThingService {
	
	@Autowired ThingRepository thingRepository;
	
	@Autowired UserRepository userRepository;
	
	@Autowired MyFirstRepository myFirstRepository;
	
	@Autowired private GraphDatabaseService graphDb;
	
	public List<Thing> findAllThings() {
		List<Thing> Things = new ArrayList<Thing>();
		
		EndResult<Thing> results = thingRepository.findAll();
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
	public User addNewFirstThingByUser(MyFirst first, String username, boolean isImageAdded) {
		User user = userRepository.findByUsername(username);
		myFirstRepository.save(first);
	    user.didForFirstTime(first);
	    if(isImageAdded) {
	    	user.incrementMediaIndex();
	    }
		return userRepository.save(user);
	}
	
	public void deleteFirstThing(Long id) {
		Transaction tx = graphDb.beginTx();
		try {
			System.out.println("deleting...");
			myFirstRepository.delete(id);
			tx.success(); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tx.finish();
		}
	}
	
	public MyFirst findMyFirstThingById(Long id) {
		return myFirstRepository.findOne(id);
	}
	
	public User removeFirstThingFromUser(MyFirst firstThing, String username) {
		User user = userRepository.findByUsername(username);
		System.out.println("name=== " + user.getFirstName());
		Long id = firstThing.getId();
		System.out.println("id==== " + id);
	    user.removeFirstThing(firstThing);
	    User result = userRepository.save(user);
	    System.out.println("removed!!!!");
		deleteFirstThing(id);
		return result;
	}
	
	public EndResult<MyFirst> findAllFirstThings() {
		return myFirstRepository.findAll();
	}

}
