package com.linkedin.eatin.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import play.libs.Json;
import java.util.HashMap;

public class Caterer {
	private Integer id;
	private String name;
	private Integer numLikes = 0;
	private Integer numRatings = 1;
	private String imageUrl;
	private String foodType;
	
	private List<FoodItem> foodList;
	private List<Comment> commentList;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public Integer getNumLikes() { return numLikes; }
	public Integer getNumRatings() { return numRatings; }
	public Double getLikeRatio() { return (double) numLikes / (double) numRatings; }
	public String getImageUrl() { return imageUrl; }
	public String getFoodType() { return foodType; }
	public List<Comment> getCommentList() { return commentList; }
	
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setNumLikes(Integer numLikes) { this.numLikes = numLikes; }
	public void setNumRatings(Integer numRatings) { this.numRatings = numRatings; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
	public void setFoodType(String foodType) { this.foodType = foodType; }
	
	public Caterer(Integer id, String name, String imageUrl, String foodType) {
		super();
		
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.foodType = foodType;
		
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

	public HashMap<String, Object> toJSON(){
		HashMap<String, Object> mapper = new HashMap<String, Object>();
		mapper.put("totalLikes", numLikes);
		mapper.put("totalRatings", totalRatings);
		mapper.put("catererId", id);
		mapper.put("caterer", name);
		mapper.put("foodType", foodType);
		mapper.put("imageUrl", imageUrl);

		List<Object> listOfItems = new List<Object>();
				
		for (int i = 0; i < commentList.size() ; i++ ) {
			listOfItems.add( commentList.get(i).toJSON() );
		}
		mapper.put("comments", listOfItems);

		return mapper;
	}
	
}
