package org.myfirst.domain;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class MyFirst {

	@GraphId
	private Long id;
	
	private String title;
	
	private String description;
	
	private String image;
	
	private String visibility; //private, following, public
	
	@Fetch @RelatedTo(type = "IS_TAGGED_WITH")
	private Set<Thing> tags;
	
	@Fetch @RelatedTo(type = "HAS_COMMENT")
	private Set<Comment> comments;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Set<Thing> getTags() {
		return tags;
	}
	public void setTags(Set<Thing> tags) {
		this.tags = tags;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
    @Override
    public int hashCode() {
        return (title != null? title.hashCode() : 1) + (description != null ? description.hashCode()*31 : 31);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) { 
            return false;
        }
        MyFirst other = (MyFirst) obj;
        return  id.equals(other.id);
            
    }
	public Set<Comment> getComments() {
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
    
    public void addComment(Comment comment) {
    	comments.add(comment);
    }
    
    public void removeComment(Comment comment) {
    	comments.remove(comment);
    }
	
}
