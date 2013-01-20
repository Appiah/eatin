package com.linkedin.eatin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Menu {
	private Integer id;
	private Date date;
	private Caterer[] caterer;
	private List<FoodItem>[] foodItems;
	
	public Integer getId() { return id;}
	public Date getDate() { return date; }
	public List<FoodItem>[] getFoodItems() { return foodItems; }
	
	public void setId(Integer id) { this.id = id; }
	public void setDate(Date date) { this.date = date; }
	public void setFoodItems(List<FoodItem>[] foodItems) { this.foodItems = foodItems; }
	
	public Caterer getCaterer(int menuId) { return caterer[menuId]; }
	public void setCaterer(int day, Caterer caterer) { this.caterer[day] = caterer; }
	
	@SuppressWarnings("unchecked")
	public Menu(Integer id, Date date) {
		super();
		this.id = id;
		this.date = date;
		this.foodItems = (List<FoodItem>[]) new List<?>[Constants.NUM_CAT];
		this.caterer = new Caterer[Constants.NUM_CAT];
		
		for (int i = 0; i < Constants.NUM_CAT; i++)
			this.foodItems[i] = new ArrayList<FoodItem>();
	}
	
	public void addFoodItem(int menuId, FoodItem item) {
		this.foodItems[menuId].add(item);
		
		if (getCaterer(menuId) == null)
			setCaterer(menuId, item.getCaterer());
	}
	
	public List<FoodItem> getFoodItems(int menuId) {
		return this.foodItems[menuId];
	}
}
