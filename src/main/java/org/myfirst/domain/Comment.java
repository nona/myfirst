package org.myfirst.domain;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Comment {

	@GraphId
	private Long id;
	
	private String date;
	
	private String content;
	
	@Fetch @RelatedTo(type = "COMMENTOR")
	private Set<User> commentor;
	

	public Comment() {
	}

	public Comment(String content) {
		super();
		this.content = content;
		this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<User> getCommentor() {
		return commentor;
	}

	public void setCommentor(Set<User> commentor) {
		this.commentor = commentor;
	}

	public void addCommentor(User commentor) {
		if (this.commentor == null) {
			this.commentor = new HashSet<User>();
		}
		this.commentor.add(commentor);
	}
	
	public User getSingleCommentor() {
		User singleCommentor = null;
		if (commentor != null && !commentor.isEmpty()) {
			singleCommentor = commentor.iterator().next();
		}
		return singleCommentor;
	}
	
}
