package org.myfirst.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.myfirst.domain.MyFirst;
import org.myfirst.domain.Thing;
import org.myfirst.domain.User;
import org.myfirst.domain.UserFirstThingRelationship;
import org.myfirst.dto.UserDto;

public class Mapper {

	public static UserDto map(User user, int depth) {
			UserDto dto = new UserDto();
			dto.setId(user.getId());
			dto.setUsername(user.getUsername());
			dto.setEmail(user.getEmail());
			dto.setFirstName(user.getFirstName());
			dto.setMiddleName(user.getMiddleName());
			dto.setLastName(user.getLastName());
			dto.setCountry(user.getCountry());
			dto.setDateOfBirth(user.getDateOfBirth());
			dto.setEmail(user.getEmail());
			dto.setInterests(mapThings(user.getInterests()));
			dto.setFirstThingsCount(user.getFirstThings() == null ? 0 : user.getFirstThings().size());
			dto.setFirstThings(mapFirstThings(user.getDidForFirstTime()));
			dto.setFollowers(mapUsersSet(user.getFollowers(), depth));
			dto.setFollowing(mapUsersSet(user.getFollows(), depth));
			dto.setRole(user.getRole() != null ? user.getRole().getRole() : 2);
			dto.setFollowingCount(user.getFollows().size());
			dto.setFollowersCount(user.getFollowers().size());
			dto.setProfilePhotoLink(user.getProfilePhotoLink());
			dto.setMediaIndex(user.getMediaIndex());
			return dto;
	}
	
	public static List<UserDto> map(List<User> users) {
		List<UserDto> dtos = new ArrayList<UserDto>();
		for (User user: users) {
			dtos.add(map(user, 2));
		}
		return dtos;
	}
	
	public static Set<UserDto> mapUsersSet(Set<User> users, int depth) {
		if (depth > 1) {
			return null;
		} else {
			Set<UserDto> dtos = new HashSet<UserDto>();
			for (User user: users) {
				dtos.add(map(user, 2));
			}
			return dtos;
		}
	}
	
	public static ThingDto map(Thing thing) {
		ThingDto dto = new ThingDto();
		dto.setId(thing.getId());
		dto.setTag(thing.getTag());
		return dto;
	}
	
	public static Set<ThingDto> mapThings(Set<Thing> set) {
		Set<ThingDto> dtos = new HashSet<ThingDto>();
		for (Thing thing: set) {
			dtos.add(map(thing));
		}
		return dtos;
	}
	
	public static Set<ThingDto> mapThingsList(List<Thing> set) {
		Set<ThingDto> dtos = new HashSet<ThingDto>();
		for (Thing thing: set) {
			dtos.add(map(thing));
		}
		return dtos;
	}
	
	public static Set<MyFirstDto> mapFirstThings(Set<UserFirstThingRelationship> set) {
		Set<MyFirstDto> dtos = new HashSet<MyFirstDto>();
		for (UserFirstThingRelationship first: set) {
			dtos.add(mapMyFirst(first));
		}
		return dtos;
	}
	
	public static MyFirstDto mapMyFirst(UserFirstThingRelationship firstRel) {
		MyFirstDto dto = new MyFirstDto();
		MyFirst first = firstRel.getFirstThing();
		dto.setId(first.getId());
		dto.setDescription(first.getDescription());
		dto.setVisibility(first.getVisibility());
		dto.setTags(mapThings(first.getTags()));
		dto.setTitle(first.getTitle());
		dto.setImage(first.getImage());
		dto.setDate(firstRel.getDate());
		return dto;
	}
}
