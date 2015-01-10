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
	
	private String date;
	
	private String content;
	
	

	public Comment() {
	}

	public Comment(String username, String content) {
		super();
		this.username = username;
		this.content = content;
		this.date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
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
	
	
	
	
}
