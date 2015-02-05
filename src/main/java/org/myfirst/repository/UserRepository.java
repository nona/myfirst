package org.myfirst.repository;

import java.util.List;
import java.util.Map;

import org.myfirst.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface UserRepository extends GraphRepository<User>  {
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
    @Query( "START user=node({0}) " +
            " MATCH (user)-[:FOLLOWS*2..2]->(friendOfFriend) " +
            " WHERE NOT (user)-[:FOLLOWS]->(friendOfFriend) " +
            " RETURN friendOfFriend.username AS friend, COUNT(*) AS mutualFriends" +
            " ORDER BY COUNT(*) DESC , friendOfFriend.username " +
            " LIMIT 10" )
	List<Map<String, Object>> getFriendsRecommendation(User user);
    
    @Query( "START user=node({0}) " +
            " MATCH (user:User)-[:IS_INTERESTED_IN|:IS_TAGGED_WITH|:HAS_DONE*1..2]->(tag:Thing)<-[:IS_INTERESTED_IN|:IS_TAGGED_WITH|:HAS_DONE*1..2]-(people:User), " +
            " (people)-[:HAS_DONE]->(newThing)-[:IS_TAGGED_WITH]->(newTag:Thing) " +
            " WHERE NOT user=people AND NOT (user)-[:IS_TAGGED_WITH|:NOT_INTERESTED_IN|:ALREADY_DONE|:MARKED_AS_TODO|:HAS_DONE*1..2]->(newTag:Thing) " +
            " RETURN people.username AS friend, id(newThing) AS firstThingID, newThing.title as title, COUNT(DISTINCT tag.tag) as mutualTags " +
            " ORDER BY mutualTags desc, friend " +
            " LIMIT 10" )
	List<Map<String, Object>> getThingsRecommendation(User user);
    
    @Query( "START user=node({0}) " +
            " MATCH " +
            " (user)-[:FOLLOWS]->(people), " +
            " (people)-[d:HAS_DONE]->(firstThing) " +
            " RETURN count(*) as count" )
	List<Map<String, Object>> getFollowingsPostsCount(User user);
    
    @Query( "START user=node({0}) " +
            " MATCH " +
            " (user)-[:FOLLOWS]->(people), " +
            " (people)-[d:HAS_DONE]->(firstThing) " +
            " RETURN id(firstThing) as id, d.date as date, people.username as username, people.profilePhotoLink as profilePhotoLink " +
            " ORDER BY d.date DESC " +
            " SKIP {1} " +
            " LIMIT {2}" )
	List<Map<String, Object>> getTimelinePosts(User user, int skipNumber, int itemsPerPage);
    
}
