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
	@Fetch @EndNode private FirstThing firstThing;
	private String date;
	
	public UserFirstThingRelationship () {
		
	}

	public UserFirstThingRelationship(User user, FirstThing firstThing) {
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserFirstThingRelationship other = (UserFirstThingRelationship) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
