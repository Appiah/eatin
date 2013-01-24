package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Menu {
	private Integer id;
	private Date date;
	private Caterer[] caterers;
	private List<FoodItem>[] foodItems;
	
	public Integer getId() { return id;}
	public Date getDate() { return date; }
	public List<FoodItem>[] getFoodItems() { return foodItems; }
	
	public void setId(Integer id) { this.id = id; }
	public void setDate(Date date) { this.date = date; }
	public void setFoodItems(List<FoodItem>[] foodItems) { this.foodItems = foodItems; }
	
	public Caterer getCaterer(int menuId) { return caterers[menuId]; }
	public void setCaterer(int day, Caterer caterer) { this.caterers[day] = caterer; }
	
	@SuppressWarnings("unchecked")
	public Menu(Date date) {
		super();
		this.date = (Date) date.clone();
		this.foodItems = (List<FoodItem>[]) new List<?>[Constants.NUM_CAT];
		this.caterers = new Caterer[Constants.NUM_CAT];
		
		for (int i = 0; i < Constants.NUM_CAT; i++)
			this.foodItems[i] = new ArrayList<FoodItem>();
	}
	
	public void addFoodItem(int menuId, FoodItem item) {
		this.foodItems[menuId].add(item);
		
		if (getCaterer(menuId) == null)
			setCaterer(menuId, item.getCaterer());
		getCaterer(menuId).addFoodItem(item);
	}
	
	public List<FoodItem> getFoodItems(int menuId) {
		return this.foodItems[menuId];
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> toJSON(){
		HashMap<String, Object> mapper = new HashMap<String, Object>();
		HashMap<String, Object> menus = new HashMap<String, Object>();

		List<Object>[] listOfItems = (List<Object>[]) new List<?>[Constants.NUM_CAT];


		for (int i = 0; i < foodItems.length; i++) {
			listOfItems[i] = new ArrayList<Object>();

			for (FoodItem fi : foodItems[i])
				listOfItems[i].add(fi.toJSON());

			listOfItems[i].add(caterers[i].toJSON());
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("foodItems", listOfItems[i]);		

			//if (i == Constants.CAT_CATER) {
			m.put("catererInfo", caterers[i].toJSON());

			

			if (i == Constants.CAT_CATER) {
				menus.put("vegetarian", m);
			} else if (i == Constants.CAT_INDIAN) {
				menus.put("indian", m);
			} else {
				menus.put("daily", m);
			}

		}

//		HashMap<String, Object> m = new HashMap<String, Object>();
//		m.put("foodItems", listOfItems[Constants.CAT_CATER]);
//		m.put

//		menus.put("daily", listOfItems[Constants.CAT_CATER]);
//		menus.put("indian", listOfItems[Constants.CAT_INDIAN]);
//		menus.put("vegetarian", listOfItems[Constants.CAT_VEGGIE]);
//		mapper.put("foodItems", menus);
		//mapper.put("day", menus);

		return menus;//mapper;
	}
}
