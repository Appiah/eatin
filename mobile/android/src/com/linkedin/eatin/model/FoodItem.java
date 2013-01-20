package com.linkedin.eatin.model;

public class FoodItem {
	private Integer id;
	private String name;
	private Integer numRating;
	private Double rating;
	private Caterer caterer;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public Integer getNumRating() { return numRating; }
	public Double getRating() { return rating; }
	public Caterer getCaterer() { return caterer; }
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setNumRating(Integer numRating) { this.numRating = numRating; }
	public void setRating(Double rating) { this.rating = rating; }
	public void setCaterer(Caterer caterer) { this.caterer = caterer; }
	
	public FoodItem(Integer id, String name, Integer numRating, Double rating,
			Caterer caterer) {
		super();
		this.id = id;
		this.name = name;
		this.numRating = numRating;
		this.rating = rating;
		this.caterer = caterer;
	}
	
	public String getRatingColor() {
		Double ratio = getRating();
		Integer g = (int)((Math.min(ratio, 0.5) * 2) * 255);
		Integer r = (int)((Math.min(1-ratio, 0.5) * 2) * 255);
		
		return "#" + padString(Integer.toHexString(r)) + padString(Integer.toHexString(g)) + "00"; 
	}
	
	private String padString(String str) {
		return str.length() == 1? "0" + str : str;
	}
}
