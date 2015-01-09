package org.myfirst.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "IS_INTERESTED_IN")
public class InterestedRelationship {
	
	@GraphId private Long id;
	@Fetch @StartNode private User user;
	@Fetch @EndNode private Thing thing;
	
	public InterestedRelationship () {
		
	}
	
	public InterestedRelationship (User u, Thing t) {
		this.user = u;
		this.thing = t;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Thing getThing() {
		return thing;
	}
	public void setThing(Thing thing) {
		this.thing = thing;
	}


	
}
