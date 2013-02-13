package com.linkedin.eatin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.linkedin.eatin.utility.Constants;

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
	
	public Menu(Integer id, Date date) {
		super();
		this.id = id;
		this.date = date;
		
		init();
	}
	
	private Menu() {
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
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
	
	public static Menu fromJSON(JSONObject json) throws JSONException {
		Menu m = new Menu();

		m.setDate(new Date(json.getLong("date")));
//		m.setId(json.getInt("id"));
		
		JSONObject [] menulist = new JSONObject[Constants.NUM_CAT];
		menulist[Constants.CAT_CATER] = json.getJSONObject("daily");
		menulist[Constants.CAT_INDIAN] = json.getJSONObject("indian");
		menulist[Constants.CAT_VEGGIE] = json.getJSONObject("vegetarian");
		
		for (int i = 0; i < menulist.length; i++) {
			JSONObject menu = menulist[i];
			JSONArray fis = menu.getJSONArray("foodItems");
			
			Caterer c = Caterer.fromJSON(menu.getJSONObject("catererInfo"));
			
			for (int j = 0; j < fis.length(); j++) {
				FoodItem fi = FoodItem.fromJSON(fis.getJSONObject(j));
				fi.setCaterer(c);
				m.addFoodItem(i, fi);
			}
		}
		
		return m;
	}
}
