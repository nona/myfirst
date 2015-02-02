package org.myfirst.service;

import org.myfirst.domain.FirstThing;
import org.myfirst.domain.Role;
import org.myfirst.domain.Thing;
import org.myfirst.domain.User;
import org.myfirst.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DBPopulatorService {

    @Autowired
    Neo4jTemplate ctx;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ThingService thingService;
    @Autowired
    DBCleanerService cleanerService;
	
    @Transactional
    public void populateDatabase() {
        User ivan = new User("ivan@myfirst.com", "ivan", "1234567", "Ivan", "Ivanov", new Role(2), "http://my1st.net/images/0_0.jpg");
        ivan = userService.register(ivan);
        User peter = new User("peter@myfirst.com", "peter", "1234567", "Peter", "Ivanov", new Role(2), "http://my1st.net/images/2_0.jpg");
        peter = userService.register(peter);
        User georgy = new User("georgy@myfirst.com", "georgy", "1234567", "Georgy", "Ivanov", new Role(2), "http://my1st.net/images/4_0.jpg");
        georgy = userService.register(georgy);
        User maria = new User("maria@myfirst.com", "maria", "1234567", "Maria", "Petrova", new Role(2), "http://my1st.net/images/6_0.jpg");
        maria = userService.register(maria);
        User ana = new User("ana@myfirst.com", "ana", "1234567", "Ana", "Petrova", new Role(2), "http://my1st.net/images/8_0.jpg");
        ana = userService.register(ana);
        User ceca = new User("ceca@myfirst.com", "ceca", "1234567", "Ceca", "Meca", new Role(2), "http://my1st.net/images/10_0.jpg");
        ceca = userService.register(ceca);
        User alex = new User("alex@myfirst.com", "alex", "1234567", "alex", "Mentsel", new Role(2), "http://my1st.net/images/12_0.jpg");
        alex = userService.register(alex);
        User nona = new User("nona@myfirst.com", "nona", "1234567", "nona", "Mentsel", new Role(2), null);
        nona = userService.register(nona);
        
        userService.follow(maria, ivan);
        userService.follow(georgy, ivan);
        userService.follow(peter, georgy);
        userService.follow(ceca, georgy);
        userService.follow(ana, maria);
        userService.follow(alex, ana);
        
        FirstThing f1 = new FirstThing("Trip to Rome", "I visited the Colosseum", "http://i.telegraph.co.uk/multimedia/archive/02412/Colosseum_2412363b.jpg",
        		"public", thingService.addNewThingsByTags(new String[]{"rome", "trip", "colosseum"}));
        thingService.addNewFirstThingByUser(f1, "ivan", true);
        
        FirstThing f2 = new FirstThing("Trip to Paris", "The Eiffel Tower is smaller than I expected", "http://www.planetware.com/photos-large/F/eiffel-tower.jpg",
        		"public", thingService.addNewThingsByTags(new String[]{"paris", "trip", "eiffel-tower"}));
        thingService.addNewFirstThingByUser(f2, "alex", true);
        
        FirstThing f3 = new FirstThing("Sofia zoo", "I couldn't see the lion, because he was sleeping inside his cage. The monkey was making funny faces.", "http://www.thenumber1.com/Files/d57a3c4b-97b3-4517-bdcc-57a1ff76fd41.jpeg",
        		"public", thingService.addNewThingsByTags(new String[]{"zoo", "sofia", "lion", "monkey"}));
        thingService.addNewFirstThingByUser(f3, "ceca", true);
        
        FirstThing f4 = new FirstThing("I saw a panda", "Vienna zoo is a great place!", "http://wrs8a3ki8zth7aut28u4yi107g.wpengine.netdna-cdn.com/wp-content/uploads/sites/2/2013/03/Panda-.jpg",
        		"public", thingService.addNewThingsByTags(new String[]{"zoo", "vienna", "panda"}));
        thingService.addNewFirstThingByUser(f4, "maria", true);
        
        FirstThing f5 = new FirstThing("Scuba diving with a cat", "It is a lie that cats hate water :D", "http://www.swanagediving.co.uk/docs/scuba-cat-6.jpg",
        		"public", thingService.addNewThingsByTags(new String[]{"cat", "scuba-diving"}));
        thingService.addNewFirstThingByUser(f5, "georgy", true);
        
        FirstThing f6 = new FirstThing("Scuba diving", "What an adventure! I should do it again!", "http://www.diver.co.uk/images/scuba-diving.png",
        		"public", thingService.addNewThingsByTags(new String[]{"scuba-diving"}));
        thingService.addNewFirstThingByUser(f6, "peter", true);
        
        
    	FirstThing f7 = new FirstThing("Scuba diving with a tortoise", "A really big tortoise!", "http://www.tinybubblesscuba.com/wp-content/uploads/2012/06/Scooter-Diver-and-Turtle1.jpg",
        		"public", thingService.addNewThingsByTags(new String[]{"tortoise", "scuba-diving"}));
        thingService.addNewFirstThingByUser(f7, "georgy", true);
        
        FirstThing f8 = new FirstThing("Selfie with a monk seal", "Scuba diving offers great opportunities!", "http://bubblesbelowkauai.com/wp-content/uploads/home-websize11.jpg",
        		"public", thingService.addNewThingsByTags(new String[]{"monk-seal", "scuba-diving", "selfie"}));
        thingService.addNewFirstThingByUser(f8, "georgy", true);
        
        FirstThing f9 = new FirstThing("Neo4j project", "MyFirst. It is a social network for the first things in life.", "http://dab1nmslvvntp.cloudfront.net/wp-content/uploads/2014/05/1400324777logo.png",
        		"public", thingService.addNewThingsByTags(new String[]{"neo4j", "social-network"}));
        thingService.addNewFirstThingByUser(f9, "nona", true);
        
        FirstThing f10 = new FirstThing("Kiss with a dog", "Not very hygienic.", "http://my1st.net/images/9_6.jpg",
        		"public", thingService.addNewThingsByTags(new String[]{"dog", "kiss"}));
        thingService.addNewFirstThingByUser(f10, "ana", true);
        
        FirstThing f11 = new FirstThing("Exam in the university", "Ancient history of Rome.", "http://i.telegraph.co.uk/multimedia/archive/02480/education_exam1_2480847c.jpg",
        		"public", thingService.addNewThingsByTags(new String[]{"university", "exam", "history", "rome"}));
        thingService.addNewFirstThingByUser(f11, "ana", true);
        
        FirstThing f12 = new FirstThing("Parachute jump", "Obviously, my parachute has opened on time :)", "http://www.incrediblesnaps.com/wp-content/uploads/2013/11/parachute-photographs-1.gif",
        		"public", thingService.addNewThingsByTags(new String[]{"parachute", "jump"}));
        thingService.addNewFirstThingByUser(f12, "ivan", true);
        
        thingService.addNewThingByUser(new Thing("cars"), "alex");
        thingService.addNewThingByUser(new Thing("neo4j"), "alex");
        thingService.addNewThingByUser(new Thing("cars"), "nona");
        thingService.addNewThingByUser(new Thing("dog"), "ivan");
        thingService.addNewThingByUser(new Thing("tortoise"), "ana");
        thingService.addNewThingByUser(new Thing("trip"), "ana");
        
    }
    
    @Transactional
    public void cleanDb() {
        cleanerService.cleanDb();
    }
}
