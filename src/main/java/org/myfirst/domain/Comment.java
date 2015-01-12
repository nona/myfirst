package org.myfirst.domain;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Comment {

	@GraphId
	private Long id;
	
	private String username;
	
	private String commentorPhoto;
	
	private String date;
	
	private String content;
	
	

	public Comment() {
	}

	public Comment(User user, String content) {
		super();
		this.username = user.getUsername();
		this.commentorPhoto = user.getProfilePhotoLink();
		this.content = content;
		this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		System.out.println("new comment created");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCommentorPhoto() {
		return commentorPhoto;
	}

	public void setCommentorPhoto(String commentorPhoto) {
		this.commentorPhoto = commentorPhoto;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
