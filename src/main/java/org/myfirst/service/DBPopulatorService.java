package org.myfirst.service;

import org.myfirst.domain.Role;
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
    DBCleanerService cleanerService;
	
    @Transactional
    public void populateDatabase() {
        User ivan = new User("ivan@myfirst.com", "ivan", "1234567", "Ivan", "Ivanov", new Role(2));
        ivan = userService.register(ivan);
        User peter = new User("peter@myfirst.com", "peter", "1234567", "Peter", "Ivanov", new Role(2));
        peter = userService.register(peter);
        User georgy = new User("georgy@myfirst.com", "georgy", "1234567", "Georgy", "Ivanov", new Role(2));
        georgy = userService.register(georgy);
        User maria = new User("maria@myfirst.com", "maria", "1234567", "Maria", "Petrova", new Role(2));
        maria = userService.register(maria);
        User ana = new User("ana@myfirst.com", "ana", "1234567", "Ana", "Petrova", new Role(2));
        ana = userService.register(ana);
        User ceca = new User("ceca@myfirst.com", "ceca", "1234567", "Ceca", "Meca", new Role(2));
        ceca = userService.register(ceca);
        User alex = new User("alex@myfirst.com", "alex", "1234567", "alex", "Mentsel", new Role(2));
        alex = userService.register(alex);
        
        userService.follow(maria, ivan);
        userService.follow(georgy, ivan);
        userService.follow(peter, georgy);
        userService.follow(ceca, georgy);
        userService.follow(ana, maria);
        userService.follow(alex, ana);
    }
    
    @Transactional
    public void cleanDb() {
        cleanerService.cleanDb();
    }
}
