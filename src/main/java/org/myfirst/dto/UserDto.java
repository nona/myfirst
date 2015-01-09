package org.myfirst.dto;

import java.io.Serializable;
import java.util.Set;

public class UserDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5633749305613445433L;
	private Long id;
	private String email;
	private String firstName;
	private String middleName;
	private String lastName;
	private String username;
	private String country;
	private String gender;
	private String dateOfBirth;
	private Integer role;
	private Set<ThingDto> interests;
	private Set<MyFirstDto> firstThings;
	private Set<UserDto> following;
	private Set<UserDto> followers;
	private Integer firstThingsCount;
	private Integer followingCount;
	private Integer followersCount;
	private String profilePhotoLink;
	private boolean isFollowing;
	private boolean isFollowingMe;
	private long mediaIndex;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Set<ThingDto> getInterests() {
		return interests;
	}
	public void setInterests(Set<ThingDto> interests) {
		this.interests = interests;
	}
	public Set<UserDto> getFollowing() {
		return following;
	}
	public void setFollowing(Set<UserDto> following) {
		this.following = following;
	}
	public Set<UserDto> getFollowers() {
		return followers;
	}
	public void setFollowers(Set<UserDto> followers) {
		this.followers = followers;
	}
	public Integer getFirstThingsCount() {
		return firstThingsCount;
	}
	public void setFirstThingsCount(Integer firstThingsCount) {
		this.firstThingsCount = firstThingsCount;
	}
	public Integer getFollowingCount() {
		return followingCount;
	}
	public void setFollowingCount(Integer followingCount) {
		this.followingCount = followingCount;
	}
	public Integer getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
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
	public Set<MyFirstDto> getFirstThings() {
		return firstThings;
	}
	public void setFirstThings(Set<MyFirstDto> firstThings) {
		this.firstThings = firstThings;
	}
	public boolean isFollowing() {
		return isFollowing;
	}
	public void setFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}
	public boolean isFollowingMe() {
		return isFollowingMe;
	}
	public void setFollowingMe(boolean isFollowingMe) {
		this.isFollowingMe = isFollowingMe;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		UserDto other = (UserDto) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	
	
}
