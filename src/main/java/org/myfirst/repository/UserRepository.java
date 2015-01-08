package org.myfirst.repository;

import org.myfirst.domain.User;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface UserRepository extends GraphRepository<User>  {
	User findByUsername(String username);
	User findByEmail(String email);
}
