package org.myfirst.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "HAS_DONE")
public class UserFirstThingRelationship {

	@GraphId private Long id;
	@Fetch @StartNode private User user;
	@Fetch @EndNode private MyFirst firstThing;
	private String date;
	
	public UserFirstThingRelationship () {
		
	}

	public UserFirstThingRelationship(User user, MyFirst firstThing) {
		super();
		this.user = user;
		this.firstThing = firstThing;
		this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
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

	public MyFirst getFirstThing() {
		return firstThing;
	}

	public void setFirstThing(MyFirst firstThing) {
		this.firstThing = firstThing;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
