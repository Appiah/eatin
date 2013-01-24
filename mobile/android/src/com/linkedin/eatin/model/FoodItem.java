package com.linkedin.eatin.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.linkedin.eatin.utility.PostHelper;
import com.linkedin.eatin.utility.Updateable;

import android.util.Log;

public class FoodItem implements Updateable {
	private Integer id;
	private String name;
	private Integer numRatings;
	private Integer numLikes;
	private Caterer caterer;
	
	private int bufferedVote;
	private int bufferedNumVote;
	
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
		vote(1);
	}
	
	public void downvote() {
		vote(0);
	}
	
	protected void vote(int num) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("foodId", id);
		data.put("isUpVote", num == 1);
		
		new PostHelper(data, this);
		bufferedVote += num;
		bufferedNumVote ++;
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
	
	@Override
	public void update(String results) {
		numLikes += bufferedVote;
		numRatings += bufferedNumVote;
		bufferedVote = 0;
		bufferedNumVote = 0;
		caterer.updateRatings();
	}
}
