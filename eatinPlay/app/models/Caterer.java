package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Caterer {
	private Integer id;
	private String name;
	private Integer numLikes = 0;
	private Integer numRatings = 1;
	private String imageUrl;
	private String foodType;
	private String comments;
	private String contact;
	private String address;
	private String phoneNumber;
	private String email;
	
	private List<FoodItem> foodList;
	private List<Comment> commentList;
	
	public Integer getId() { return id; }
	public String getName() { return name; }
	public Integer getNumLikes() { return numLikes; }
	public Integer getNumRatings() { return numRatings; }
	public Double getLikeRatio() { return (double) numLikes / (double) numRatings; }
	public String getImageUrl() { return imageUrl; }
	public String getComment() { return comments; }
	public String getContact() { return contact; }
	public String getAddress() { return address; }
	public String getPhoneNumber() { return phoneNumber; }
	public String getEmail() { return email; }
	public String getFoodType() { return foodType; }
	public List<Comment> getCommentList() { return commentList; }
	
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setNumLikes(Integer numLikes) { this.numLikes = numLikes; }
	public void setNumRatings(Integer numRatings) { this.numRatings = numRatings; }
	public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
	public void setFoodType(String foodType) { this.foodType = foodType; }
	
	public Caterer(Integer id, String name, String imageUrl, String foodType,
			String contact, String email, String phoneNumber, String address, String comments) {
		super();
		
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.foodType = foodType;
		this.comments = comments;
		this.contact = contact;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		
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
	
	public void addComments(List<Comment> comments) {
		commentList.addAll(comments);
	}

	public HashMap<String, Object> toJSON(){
		HashMap<String, Object> mapper = new HashMap<String, Object>();
		mapper.put("totalLikes", numLikes);
		mapper.put("totalRatings", numRatings);
		mapper.put("catererId", id);
		mapper.put("caterer", name);
		mapper.put("foodType", foodType);
		mapper.put("imageUrl", imageUrl);
		mapper.put("comment", comments);
		mapper.put("contact", contact);
		mapper.put("address", address);
		mapper.put("email", email);
		mapper.put("phoneNumber", phoneNumber);

		List<Object> listOfItems = new ArrayList<Object>();
		
		for (int i = 0; i < commentList.size() ; i++ ) {
			listOfItems.add( commentList.get(i).toJSON() );
		}
		mapper.put("comments", listOfItems);
		
		return mapper;
	}
	
}
