package org.myfirst.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.myfirst.domain.Comment;
import org.myfirst.domain.FirstThing;
import org.myfirst.domain.Thing;
import org.myfirst.domain.ToDoRelationship;
import org.myfirst.domain.User;
import org.myfirst.domain.UserFirstThingRelationship;
import org.myfirst.dto.UserDto;
import org.myfirst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
	
	@Autowired static UserService userService;

	public static UserDto map(User user, int depth, boolean followed) {
			UserDto dto = new UserDto();
			dto.setId(user.getId());
			dto.setUsername(user.getUsername());
			dto.setEmail(user.getEmail());
			dto.setFirstName(user.getFirstName());
			dto.setMiddleName(user.getMiddleName());
			dto.setLastName(user.getLastName());
			dto.setCountry(user.getCountry());
			dto.setGender(user.getGender());
			dto.setDateOfBirth(user.getDateOfBirth());
			dto.setEmail(user.getEmail());
			dto.setInterests(mapThings(user.getInterests()));
			dto.setFirstThingsCount(user.getFirstThings() == null ? 0 : user.getFirstThings().size());
			dto.setFirstThings(mapFirstThings(user.getDidForFirstTime()));
			dto.setToDos(mapToDos(user.getToDoRelationships()));
			dto.setFollowers(mapUsersSet(user.getFollowers(), depth, user.getFollows()));
			dto.setFollowing(mapUsersSet(user.getFollows(), depth, null));
			dto.setRole(user.getRole() != null ? user.getRole().getRole() : 2);
			dto.setFollowingCount(user.getFollows().size());
			dto.setFollowersCount(user.getFollowers().size());
			dto.setProfilePhotoLink(user.getProfilePhotoLink());
			dto.setMediaIndex(user.getMediaIndex());
			dto.setFollowing(followed);
			return dto;
	}
	
	public static List<UserDto> map(List<User> users) {
		List<UserDto> dtos = new ArrayList<UserDto>();
		for (User user: users) {
			dtos.add(map(user, 2, false));
		}
		return dtos;
	}
	
	public static Set<UserDto> mapUsersSet(Set<User> users, int depth, Set<User> following) {
		if (depth > 1) {
			return null;
		} else {
			Set<UserDto> dtos = new HashSet<UserDto>();
			for (User user: users) {
				if (following != null && following.size() > 0 && following.contains(user)) {
					System.out.println(">> following:: " + user.getUsername());
					dtos.add(map(user, 2, true));
				} else {
					System.out.println(">> not following:: " + user.getUsername());
					dtos.add(map(user, 2, false));
				}
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
	
	public static Set<FirstThingDto> mapFirstThings(Set<UserFirstThingRelationship> set) {
		Set<FirstThingDto> dtos = new HashSet<FirstThingDto>();
		for (UserFirstThingRelationship first: set) {
			dtos.add(mapMyFirst(first));
		}
		return dtos;
	}
	
	public static TreeSet<FirstThingDto> mapToDos(Set<ToDoRelationship> set) {
		TreeSet<FirstThingDto> dtos = new TreeSet<FirstThingDto>();
		for (ToDoRelationship first: set) {
			dtos.add(mapMyFirst(first));
		}
		return dtos;
	}
	
	public static FirstThingDto mapMyFirst(UserFirstThingRelationship firstRel) {
		FirstThingDto dto = new FirstThingDto();
		FirstThing first = firstRel.getFirstThing();
		dto.setId(first.getId());
		dto.setDescription(first.getDescription());
		dto.setVisibility(first.getVisibility());
		dto.setTags(mapThings(first.getTags()));
		dto.setTitle(first.getTitle());
		dto.setImage(first.getImage());
		dto.setDate(firstRel.getDate());
		dto.setComments(mapCommentSet(first.getComments()));
		return dto;
	}
	
	public static FirstThingDto mapMyFirst(ToDoRelationship firstRel) {
		FirstThingDto dto = new FirstThingDto();
		FirstThing first = firstRel.getFirstThing();
		dto.setId(first.getId());
		dto.setDescription(first.getDescription());
		dto.setVisibility(first.getVisibility());
		dto.setTags(mapThings(first.getTags()));
		dto.setTitle(first.getTitle());
		dto.setImage(first.getImage());
		dto.setDate(firstRel.getDate());
		dto.setComments(mapCommentSet(first.getComments()));
		return dto;
	}
	
	public static CommentDto mapComment(Comment comment) {
		CommentDto dto = new CommentDto();
		dto.setId(comment.getId());
		dto.setDate(comment.getDate());
		dto.setContent(comment.getContent());
		dto.setUsername(comment.getSingleCommentor().getUsername());
		dto.setCommentorProfilePic(comment.getSingleCommentor().getProfilePhotoLink());
		return dto;
	}
	
	public static Set<CommentDto> mapCommentSet(Set<Comment> comments) {
		Set<CommentDto> dtos = new HashSet<CommentDto>();
		for (Comment c: comments) {
			dtos.add(mapComment(c));
		}
		return dtos;
	}

}
