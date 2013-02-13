package com.linkedin.eatin.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

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
	
	private Comment() {}
	
	public static Comment fromJSON(JSONObject json) throws JSONException {
		Comment c = new Comment();
		
		c.setId(json.getLong("commentId"));
		c.setMessage(json.getString("message"));
		c.setPostDate(new Date(json.getInt("postDate")));
		c.setPoster(json.getString("poster"));
		
		return c;
	}
}
