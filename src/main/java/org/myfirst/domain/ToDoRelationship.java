package org.myfirst.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "WANTS_TO_DO")
public class ToDoRelationship {

	@GraphId private Long id;
	@Fetch @StartNode private User user;
	@Fetch @EndNode private FirstThing firstThing;
	private String date;
	
	
	public ToDoRelationship () {
		
	}
	
	public ToDoRelationship(User user, FirstThing firstThing, String dueDate) {
		super();
		this.user = user;
		this.firstThing = firstThing;
		this.date = dueDate;
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

	public FirstThing getFirstThing() {
		return firstThing;
	}

	public void setFirstThing(FirstThing firstThing) {
		this.firstThing = firstThing;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
