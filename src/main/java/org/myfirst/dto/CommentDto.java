package org.myfirst.dto;

public class CommentDto implements Comparable<CommentDto> {

	private Long id;
	
	private String username;
	
	private String date;
	
	private String content;
	
	private String commentorProfilePic;

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

	public String getCommentorProfilePic() {
		return commentorProfilePic;
	}

	public void setCommentorProfilePic(String commentorProfilePic) {
		this.commentorProfilePic = commentorProfilePic;
	}

	@Override
	public int compareTo(CommentDto o) {
		if (this.id.compareTo(o.id) != 0) {
			return this.date.compareTo(o.date);
		}
		return 0;
	}
	
	
}
