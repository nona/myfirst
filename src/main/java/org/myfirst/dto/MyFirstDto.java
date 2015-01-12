package org.myfirst.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class MyFirstDto implements Serializable, Comparable<MyFirstDto> {

	private static final long serialVersionUID = 7704749462929251097L;

	private Long id;
	
	private String description;
	
	private String visibility;
	
	private Set<ThingDto> tags;
	
	private TreeSet<CommentDto> comments;
	
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

	public Set<CommentDto> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDto> comments) {
		this.comments = new TreeSet<CommentDto> (comments);
	}

	@Override
	public int compareTo(MyFirstDto o) {
		if (this.id.compareTo(o.id) != 0) {
			return o.date.compareTo(this.date);
		}
		return 0;
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
		MyFirstDto other = (MyFirstDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
