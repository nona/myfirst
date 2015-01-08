package org.myfirst.repository;

import org.myfirst.domain.Thing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface ThingRepository extends GraphRepository<Thing> {

	Thing findByTag(String tag);
	Page<Thing> findByTagLike(String title, Pageable page);
}
