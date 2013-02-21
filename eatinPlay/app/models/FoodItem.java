package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

import com.linkedin.eatin.model.Comment;

public class FoodItem {
	public interface FIUpdateable {
		public void updateUpVote();
		public void updateDownVote();
	}
	
	private Integer id;
	private String name;
	private Integer numLikes;
	private Integer numRatings;
	private Caterer caterer;
	private Map<String, Integer> flags;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public Integer getNumRatings() { return numRatings; }
	public Integer getNumLikes() { return numLikes; }
	public Caterer getCaterer() { return caterer; }
	public Map<String, Integer> flags getFlags() { return flags; }
	
	
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setNumRating(Integer numRating) { this.numRatings = numRating; }
	public void setCaterer(Caterer caterer) { this.caterer = caterer; }
	
	public FoodItem(Integer id, String name, Integer numRating, Integer numLikes,
			Caterer caterer, Map<String, Integer> flags) {
		super();
		this.id = id;
		this.name = name;
		this.numRatings = numRating;
		this.numLikes = numLikes;
		this.caterer = caterer;
		this.flags = flags;
	}

	public HashMap<String, Object> toJSON(){
		HashMap<String, Object> mapper = new HashMap<String, Integer>();
		mapper.put("foodId", id);
		mapper.put("name", name);
		mapper.put("numLikes", numLikes);
		mapper.put("numRatings", numRatings);

		List<Object> listOfItems = new ArrayList<Object>();
		
		for (Map.Entry<String, Integer> e : flags ) {
			listOfItems.add( e.toJSON());
		}
		mapper.put("flags", listOfItems);
		return mapper;
	}
}
