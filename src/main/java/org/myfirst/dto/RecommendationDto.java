package org.myfirst.dto;

import java.io.Serializable;

public class RecommendationDto implements Serializable{

	private static final long serialVersionUID = -9078702344401815775L;
	
	private Long id;
	
	private String title;
	
	private String username;


	public RecommendationDto() {
		super();
	}

	public RecommendationDto(Long id, String title, String username) {
		super();
		this.id = id;
		this.title = title;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
