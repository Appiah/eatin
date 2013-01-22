package com.linkedin.eatin.model;

import android.util.Log;

public class FoodItem {
	public interface FIUpdateable {
		public void updateUpVote();
		public void updateDownVote();
	}
	
	private Integer id;
	private String name;
	private Integer numRatings;
	private Double rating;
	private Caterer caterer;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public Integer getNumRatings() { return numRatings; }
	public Integer getNumLikes() { return (int) (rating * numRatings); }
	public Double getRating() { return rating; }
	public Caterer getCaterer() { return caterer; }
	
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setNumRating(Integer numRating) { this.numRatings = numRating; }
	public void setRating(Double rating) { this.rating = rating; }
	public void setCaterer(Caterer caterer) { this.caterer = caterer; }
	
	public FoodItem(Integer id, String name, Integer numRating, Double rating,
			Caterer caterer) {
		super();
		this.id = id;
		this.name = name;
		this.numRatings = numRating;
		this.rating = rating;
		this.caterer = caterer;
	}
	
	public String getRatingColor() {
		Double ratio = getRating();
		Integer g = (int)((Math.min(ratio, 0.5) * 2) * 255);
		Integer r = (int)((Math.min(1-ratio, 0.5) * 2) * 255);
		
		String ret = "#" + padString(Integer.toHexString(r)) + padString(Integer.toHexString(g)) + "00";
		Log.d("Color", ret);
		return ret;
	}
	
	public void upvote() {
		rating = (double)(getNumLikes() + 1) / (double)(numRatings + 1);
		numRatings ++;
		caterer.updateRatings();
	}
	
	public void downvote() {
		rating = (double) getNumLikes() / (double)(numRatings + 1);
		numRatings ++;
		caterer.updateRatings();
	}
	
	private String padString(String str) {
		return str.length() == 1? "0" + str : str;
	}

	public HashMap<String, Object> toJSON(){
		HashMap<String, Object> mapper = new HashMap<String, Object>();
		mapper.put("foodId", id);
		mapper.put("name", name);
		mapper.put("numLikes", numLikes);
		mapper.put("numRatings", numRatings);

		return mapper;
	}
}
