package org.myfirst.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

@NodeEntity
public class User {

	@GraphId
	private Long id;
	
	private String firstName;
	private String lastName;
	
	@Indexed
	private String username;
	private String password;
	
	@Indexed
	private String email;
	
	private String country;
	
	private String middleName;
	
	private String gender;
	
	private String dateOfBirth;
	
	@Fetch @RelatedTo(type = "FOLLOWS", direction = Direction.OUTGOING)
	private Set<User> follows;
	
	@Fetch @RelatedTo(type = "FOLLOWS", direction = Direction.INCOMING)
	private Set<User> followers;
	
	@Fetch @RelatedTo(type = "HAS_ROLE")
	private Role role;
	
	@Fetch @RelatedTo(type = "HAS_DONE")
	private Set<FirstThing> firstThings;
	
	@Fetch @RelatedToVia(type = "HAS_DONE")
	private Set<UserFirstThingRelationship> didForFirstTime;
	
	@Fetch @RelatedTo(type = "IS_INTERESTED_IN")
	private Set<Thing> interests;
	
	private String profilePhotoLink;
	
	//photos and videos will be saved on server with name: {user_id}_{user_mediaIndex}.{file_extension}
	private long mediaIndex;
	
	public User() {}
	
	public User(String email, String username, String password, String firstName, String lastName, Role role) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}
	
	public User(String email, String username, String firstName, String lastName, Role role) {
		this.email = email;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public User(String username) {
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    @Override
    public int hashCode() {
        return username.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return username.equals(other.username);
            
    }

	public Set<User> getFollows() {
		return follows;
	}

	public void setFollows(Set<User> follows) {
		this.follows = follows;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

//	public Set<User> getFriends() {
//		return friends;
//	}
//
//	public void setFriends(Set<User> friends) {
//		this.friends = friends;
//	}

	public Set<FirstThing> getFirstThings() {
		return firstThings;
	}

	public void setFirstThings(Set<FirstThing> firstThings) {
		this.firstThings = firstThings;
	}

	public Set<Thing> getInterests() {
		return interests;
	}

	public void setInterests(Set<Thing> interests) {
		this.interests = interests;
	}

    
    public void interestedIn(Thing thing) {
    	interests.add(thing);
    }
    
    public UserFirstThingRelationship didForFirstTime(FirstThing firstThing) {
    	System.out.println(">>> didForFirstTime " + firstThing.getTitle());
    	UserFirstThingRelationship first = new UserFirstThingRelationship(this, firstThing);
    	didForFirstTime.add(first);
    	for (UserFirstThingRelationship r: didForFirstTime) {
    		System.out.println(">>>> " + r.getFirstThing().getTitle());
    	}
    	return first;
    }

    public void notInterestedIn(Thing thing) {
    	interests.remove(thing);
    	for (Thing t: interests) {
    		System.out.println(">>."+t.getTag());
    	}
    }

    public void follow(User user) {
    	follows.add(user);
    }
    
    public void unfollow(User user) {
    	follows.remove(user);
    }

	public String getProfilePhotoLink() {
		return profilePhotoLink;
	}

	public void setProfilePhotoLink(String profilePhotoLink) {
		this.profilePhotoLink = profilePhotoLink;
	}

	public long getMediaIndex() {
		return mediaIndex;
	}

	public void setMediaIndex(long mediaIndex) {
		this.mediaIndex = mediaIndex;
	}
	
	public void incrementMediaIndex() {
		this.mediaIndex++;
	}

	public Set<UserFirstThingRelationship> getDidForFirstTime() {
		return didForFirstTime;
	}

	public void setDidForFirstTime(Set<UserFirstThingRelationship> didForFirstTime) {
		this.didForFirstTime = didForFirstTime;
	}

//	public Set<FollowRelationship> getFollowsRelationship() {
//		return followsRelationship;
//	}
//
//	public void setFollowsRelationship(Set<FollowRelationship> followsRelationship) {
//		this.followsRelationship = followsRelationship;
//	}
	
    public void removeFirstThing(FirstThing first) {
    	firstThings.remove(first);
    	for (UserFirstThingRelationship rel: didForFirstTime) {
    		if (rel.getFirstThing().getId().equals(first.getId())) {
    			didForFirstTime.remove(rel);
    		}
    	}
    }
    
    
}