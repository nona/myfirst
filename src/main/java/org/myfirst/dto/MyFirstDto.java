package org.myfirst.dto;

import java.io.Serializable;
import java.util.Set;

public class MyFirstDto implements Serializable {

	private static final long serialVersionUID = 7704749462929251097L;

	private Long id;
	
	private String description;
	
	private String visibility;
	
	private Set<ThingDto> tags;
	
	private String image;
	
	private String title;
	
	private String date;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public Set<ThingDto> getTags() {
		return tags;
	}

	public void setTags(Set<ThingDto> tags) {
		this.tags = tags;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
