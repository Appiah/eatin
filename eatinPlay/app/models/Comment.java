package com.linkedin.eatin.model;

import java.util.HashMap;
import java.util.Date;

public class Comment {
	private Long id;
	private String message;
	private String poster;
	private Date postDate;
	
	public Long getId() { return id; }
	public String getMessage() { return message; }
	public String getPoster() { return poster; }
	public Date getPostDate() { return postDate; }
	public void setId(Long id) { this.id = id; }
	public void setMessage(String message) { this.message = message; }
	public void setPoster(String poster) { this.poster = poster; }
	public void setPostDate(Date postDate) { this.postDate = postDate; }
	
	public Comment(Long id, String message, String poster, Date postDate) {
		super();
		this.id = id;
		this.message = message;
		this.poster = poster;
		this.postDate = postDate;
	}

	public HashMap<String,Object> toJSON(){
		HashMap<String,Object> mapper = new HashMap<String,Object>();
		mapper.put("commentId", id);
		mapper.put("message", message);
		mapper.put("poster", poster);
		mapper.put("postDate", postDate);

		return mapper;
	}
}
