package models;

import java.util.HashMap;

public class FoodItem {
	public interface FIUpdateable {
		public void updateUpVote();
		public void updateDownVote();
	}
	
	private Integer id;
	private String name;
	private Integer numLikes;
	private Integer numRatings;
	private Integer rating;
	private Caterer caterer;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public Integer getNumRatings() { return numRatings; }
	public Integer getNumLikes() { return (int) (rating * numRatings); }
	public Integer getRating() { return rating; }
	public Caterer getCaterer() { return caterer; }
	
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setNumRating(Integer numRating) { this.numRatings = numRating; }
	public void setRating(Integer rating) { this.rating = rating; }
	public void setCaterer(Caterer caterer) { this.caterer = caterer; }
	
	public FoodItem(Integer id, String name, Integer numRating, Integer rating,
			Caterer caterer) {
		super();
		this.id = id;
		this.name = name;
		this.numRatings = numRating;
		this.rating = rating;
		this.caterer = caterer;
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
