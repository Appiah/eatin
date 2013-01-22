package com.linkedin.eatin.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FoodItem {
	public interface FIUpdateable {
		public void updateUpVote();
		public void updateDownVote();
	}
	
	private Integer id;
	private String name;
	private Integer numRatings;
	private Integer numLikes;
	private Caterer caterer;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public Integer getNumRatings() { return numRatings; }
	public Integer getNumLikes() { return numLikes; }
	public Double getRating() { return (double) numLikes / (double) numRatings; }
	public Caterer getCaterer() { return caterer; }
	
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setNumRating(Integer numRating) { this.numRatings = numRating; }
	public void setNumLikes(Integer numLikes) { this.numLikes = numLikes; }
	public void setCaterer(Caterer caterer) { this.caterer = caterer; }
	
	public FoodItem(Integer id, String name, Integer numRatings, Integer numLikes,
			Caterer caterer) {
		super();
		this.id = id;
		this.name = name;
		this.numRatings = numRatings;
		this.numLikes = numLikes;
		this.caterer = caterer;
	}
	
	public FoodItem() {}
	
	public String getRatingColor() {
		Double ratio = getRating();
		Integer g = (int)((Math.min(ratio, 0.5) * 2) * 255);
		Integer r = (int)((Math.min(1-ratio, 0.5) * 2) * 255);
		
		String ret = "#" + padString(Integer.toHexString(r)) + padString(Integer.toHexString(g)) + "00";
		Log.d("Color", ret);
		return ret;
	}
	
	public void upvote() {
		numLikes ++;
		numRatings ++;
		caterer.updateRatings();
	}
	
	public void downvote() {
		numRatings ++;
		caterer.updateRatings();
	}
	
	private String padString(String str) {
		return str.length() == 1? "0" + str : str;
	}
	
	public static FoodItem fromJSON(JSONObject json) throws JSONException {
		FoodItem fi = new FoodItem();
		
		fi.setId(json.getInt("foodId"));
		fi.setName(json.getString("name"));
		fi.setNumLikes(json.getInt("numLikes"));
		fi.setNumRating(json.getInt("numRatings"));
		
		return fi;
	}
}
