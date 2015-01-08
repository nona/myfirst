package org.myfirst.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "FOLLOWS")
public class FollowRelationship {

	@GraphId private Long id;
	@Fetch @StartNode private User follower;
	
	@Fetch @EndNode private User followed;
	
	private String sinceDate;
	
	public FollowRelationship() {
		
	}

	public FollowRelationship(User follower, User followed) {
		this.follower = follower;
		this.followed = followed;
		this.sinceDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
	}

	public String getSinceDate() {
		return sinceDate;
	}

	public void setSinceDate(String sinceDate) {
		this.sinceDate = sinceDate;
	}

	public User getFollower() {
		return follower;
	}

	public void setFollower(User follower) {
		this.follower = follower;
	}

	public User getFollowed() {
		return followed;
	}

	public void setFollowed(User followed) {
		this.followed = followed;
	}
	
	
}
