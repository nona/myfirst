package org.myfirst.repository;

import java.util.List;
import java.util.Map;

import org.myfirst.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface UserRepository extends GraphRepository<User>  {
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
    @Query( "start user=node({0}) " +
            " match (user)-[:FOLLOWS*2..2]->(friendOfFriend) " +
            " where NOT (user)-[:FOLLOWS]->(friendOfFriend) " +
            " RETURN friendOfFriend.username as friend, COUNT(*) as mutualFriends" +
            " ORDER BY COUNT(*) DESC , friendOfFriend.username " +
            " limit 10" )
	List<Map<String, Object>> getFriendsRecommendation(User user);
    
}
