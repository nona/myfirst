package org.myfirst.domain;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult
public interface UserResult {
	
	@ResultColumn("friend")
	String getUser();
	
	@ResultColumn("mutualFriends")
	int getMutualFriends();
}
