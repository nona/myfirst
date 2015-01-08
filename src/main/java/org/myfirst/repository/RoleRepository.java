package org.myfirst.repository;

import org.myfirst.domain.Role;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface RoleRepository extends GraphRepository<Role>  {
	
}
