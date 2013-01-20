package com.linkedin.eatin.model;

public class Caterer {
	private Integer id;
	private String name;
	private Double avgRating = 0.5;
	private Integer numRatings = 1;
	private String imageUrl;
	private String foodType;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public Double getAvgRating() { return avgRating; }
	public Integer getNumRatings() { return numRatings; }
	public String getImageUrl() { return imageUrl; }
	public String getFoodType() { return foodType; }
	
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setAvgRating(Double avgRating) { this.avgRating = avgRating; }
	public void setNumRatings(Integer numRatings) { this.numRatings = numRatings; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
	public void setFoodType(String foodType) { this.foodType = foodType; }
	
	public Caterer(Integer id, String name, String imageUrl, String foodType) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.foodType = foodType;
	}
}
