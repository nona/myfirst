package org.myfirst.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.myfirst.domain.FirstThing;
import org.myfirst.domain.Role;
import org.myfirst.domain.Thing;
import org.myfirst.domain.User;
import org.myfirst.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/testApplicationContext.xml"})
public class TestService {
	
	private static final String NON_EXISTENT_USERNAME = "nonexistent";
	private static final String USERNAME_1 = "username1";
	private static final String NAME_1 = "Name1";
	private static final String LAST_NAME_1 = "LastName1";
	private static final String EMAIL_1 = "email1@myfirst.com";
	private static final String PASSWORD_1 = "1234567";
	private static final String USERNAME_2 = "username2";
	private static final String NAME_2 = "Name2";
	private static final String LAST_NAME_2 = "LastName2";
	private static final String EMAIL_2 = "email2@myfirst.com";
	private static final String PASSWORD_2 = "7654321";
	private static final String TITLE_1 = "title1";
	private static final String DESCRIPTION_1 = "description1";
	private static final String TITLE_2 = "title2";
	private static final String DESCRIPTION_2 = "description2";
	private static final String INTEREST_1 = "interest1";
	private static final String INTEREST_2 = "interest2";
	private static final String INTEREST_3 = "interest3";
	private static final String DATE_1 = "2015-03-28";
	private static final int ROLE_ID = 2;
    
	@Autowired
	DBPopulatorService populator;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ThingService thingService;
	
	@Autowired
	UserService userService;
	
	@Test
	@Transactional
	public void testPopulate() {
		populator.populateDatabase();
	}
	
    @Test
    @Transactional
    public void testFindUserByUsername() {
        User user = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user);
        User retrievedUser = userRepository.findByUsername(USERNAME_1);
        assertNotNull(retrievedUser);
        assertEquals(USERNAME_1, retrievedUser.getUsername());
    }
    
    @Test
    @Transactional
    public void testFindUserByNonexistentUsername() {
        User retrievedUser = userRepository.findByUsername(NON_EXISTENT_USERNAME);
        assertNull(retrievedUser);
    }
    
    @Test
    @Transactional
    public void testFindUserByEmail() {
    	User user = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user);
        User retrievedUser = userRepository.findByEmail(EMAIL_1);
        assertNotNull(retrievedUser);
        assertEquals(EMAIL_1, retrievedUser.getEmail());
    }
    
    @Test
    @Transactional
    public void testUserFirstThingRelationshipAdded() {
    	User user = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user);

        FirstThing f1 = new FirstThing(TITLE_1, DESCRIPTION_1, null, "public", 
        		thingService.addNewThingsByTags(new String[]{"tag1"}));
        user = thingService.addNewFirstThingByUser(f1, USERNAME_1, false);
        
        FirstThing f2 = new FirstThing(TITLE_2, DESCRIPTION_2, null, "public", 
        		thingService.addNewThingsByTags(new String[]{"tag2"}));
        user = thingService.addNewFirstThingByUser(f2, USERNAME_1, false);
        
        assertNotNull(user.getFirstThings());
        assertEquals(2, user.getDidForFirstTime().size());
    }
    
    @Test
    @Transactional
    public void testUserFirstThingRelationshipRemoved() {
    	User user = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user);

        FirstThing f1 = new FirstThing(TITLE_1, DESCRIPTION_1, null, "public", 
        		thingService.addNewThingsByTags(new String[]{"tag1"}));
        user = thingService.addNewFirstThingByUser(f1, USERNAME_1, false);
        
        f1 = user.getFirstThings().iterator().next();
        
        user = thingService.removeFirstThingFromUser(f1, USERNAME_1);
        
        assertEquals(0, user.getDidForFirstTime().size());
    }
    
    @Test
    @Transactional
    public void testAddingInterests() {
    	User user = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user);

        user = thingService.addNewThingByUser(new Thing(INTEREST_1), USERNAME_1);
        user = thingService.addNewThingByUser(new Thing(INTEREST_2), USERNAME_1);
        user = thingService.addNewThingByUser(new Thing(INTEREST_3), USERNAME_1);
        
        assertNotNull(user.getInterests());
        assertEquals(3, user.getInterests().size());
    }
    
    @Test
    @Transactional
    public void testRemoveInterest() {
    	User user = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user);

        user = thingService.addNewThingByUser(new Thing(INTEREST_1), USERNAME_1);
        
        user = thingService.removeInterestFromUser(INTEREST_1, USERNAME_1);
        
        assertEquals(0, user.getInterests().size());
    }
    
    @Test
    @Transactional
    public void testFollow() {
    	User user1 = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user1);
        
        User user2 = new User(EMAIL_2, USERNAME_2, PASSWORD_2, NAME_2, LAST_NAME_2, new Role(ROLE_ID), null);
        userRepository.save(user2);

        user1 = userService.follow(user2, user1);
        
        assertNotNull(user1.getFollows());
        assertEquals(1, user1.getFollows().size());
        assertEquals(USERNAME_2, user1.getFollows().iterator().next().getUsername());
    }
    
    @Test
    @Transactional
    public void testUnfollow() {
    	User user1 = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user1);
        
        User user2 = new User(EMAIL_2, USERNAME_2, PASSWORD_2, NAME_2, LAST_NAME_2, new Role(ROLE_ID), null);
        userRepository.save(user2);

        user1 = userService.follow(user2, user1);

        user1 = userService.unfollow(user2, USERNAME_1);
        
        assertNotNull(user1.getFollows());
        assertEquals(0, user1.getFollows().size());
    }
    
    @Test
    @Transactional
    public void testToDoRelationshipAdded() {
    	User user = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user);

        FirstThing f1 = new FirstThing(TITLE_1, DESCRIPTION_1, null, "public", 
        		thingService.addNewThingsByTags(new String[]{"tag1"}));
        user = thingService.addNewToDoByUser(f1, USERNAME_1, DATE_1);
        
        assertNotNull(user.getToDoRelationships());
        assertEquals(1, user.getToDoRelationships().size());
        
        assertNotNull(user.getToDos());
        assertEquals(1, user.getToDos().size());
    }
    
    @Test
    @Transactional
    public void testToDoRelationshipRemoved() {
    	User user = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user);

        FirstThing f1 = new FirstThing(TITLE_1, DESCRIPTION_1, null, "public", 
        		thingService.addNewThingsByTags(new String[]{"tag1"}));
        user = thingService.addNewToDoByUser(f1, USERNAME_1, DATE_1);
        
        f1 = user.getToDos().iterator().next();
        
        user = thingService.removeToDoFromUser(f1, USERNAME_1);
        
        assertEquals(0, user.getToDoRelationships().size());
        assertEquals(0, user.getToDos().size());
    }
    
    @Test
    @Transactional
    public void testChangePassword() {
    	User user = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user);
        
        user.setPassword(PASSWORD_2);
        user = userService.changePassword(user);
        
        assertEquals(PASSWORD_2, user.getPassword());
    }
    
    @Test
    @Transactional
    public void testLogin() {
    	User user = new User(EMAIL_1, USERNAME_1, PASSWORD_1, NAME_1, LAST_NAME_1, new Role(ROLE_ID), null);
        userRepository.save(user);
        
        User retrievedUser = userService.verifyUser(EMAIL_1, PASSWORD_1);
        
        assertNotNull(retrievedUser);
        assertEquals(user.getUsername(), retrievedUser.getUsername());
    }
}
