package com.linkedin.eatin.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Caterer {
	private Integer id;
	private String name;
	private String imageUrl;
	private String foodType;
	private Integer numComments = 0;
	private Integer numLikes = 0;
	private Integer numRatings = 1;
	
	private List<FoodItem> foodList;
	private List<Comment> commentList;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getImageUrl() { return imageUrl; }
	public String getFoodType() { return foodType; }
	public Integer getNumLikes() { return numLikes; }
	public Integer getNumRatings() { return numRatings; }
	public Integer getNumComments() { return numComments; }
	public Double getLikeRatio() { return (double) numLikes / (double) numRatings; }
	public List<Comment> getCommentList() { return commentList; }
	
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
	public void setFoodType(String foodType) { this.foodType = foodType; }
	public void setNumLikes(Integer numLikes) { this.numLikes = numLikes; }
	public void setNumRatings(Integer numRatings) { this.numRatings = numRatings; }
	public void setNumComments(Integer numComments) { this.numComments = numComments; }
	
	public Caterer(Integer id, String name, String imageUrl, String foodType) {
		super();
		
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.foodType = foodType;
		
		this.foodList = new LinkedList<FoodItem>();
		this.commentList = new LinkedList<Comment>();
	}
	
	private Caterer() {	
		this.foodList = new LinkedList<FoodItem>();
		this.commentList = new LinkedList<Comment>();
	}
	
	public void addFoodItem(FoodItem item) {
		if (findFoodItem(item.getId()) == null) {
			foodList.add(item);
			updateRatings();
		}
	}
	
	public FoodItem findFoodItem(int id) {
		for (FoodItem item : foodList) {
			if (item.getId() == id)
				return item;
		}
		return null;
	}
	
	public void updateRatings() {
		numLikes = 0;
		numRatings = 0;
		for (FoodItem item : foodList) {
			numRatings += item.getNumRatings();
			numLikes += item.getNumLikes();
		}
	}
	
	public void addComment(String text) {
		commentList.add(new Comment(null, text, "Anonymous", new Date()));
	}
	
	public void addComment(Comment comment) {
		commentList.add(comment);
		numComments ++;
	}
	
	public static Caterer fromJSON(JSONObject json) throws JSONException {
		Caterer c = new Caterer();
		
		c.setNumLikes(json.getInt("numLikes"));
		c.setNumRatings(json.getInt("totalRatings"));
		c.setId(json.getInt("catererId"));
		c.setName(json.getString("caterer"));
		c.setFoodType(json.getString("foodType"));
		c.setImageUrl(json.getString("imageUrl"));
		c.setNumComments(json.getInt("numComments"));
		
		JSONArray comments = json.getJSONArray("comments");
		
		for (int i = 0; i < comments.length(); i++)
			c.addComment(Comment.fromJSON(comments.getJSONObject(i)));
					
		return c;
	}
}
