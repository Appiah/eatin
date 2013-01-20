package com.linkedin.eatin.model;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Model {
	private static Model model;
	
	public static Model getModel() {
		if (model == null)
			model = new Model();
		return model;
	}
	
	
	private List<Menu> menuList;
	private List<Caterer> catererList;
	
	public Model() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		menuList = new LinkedList<Menu>();
		catererList = new LinkedList<Caterer>();
		
		catererList.add(new Caterer(0, "Tanpopo", "temporary", "Japanese"));
		catererList.add(new Caterer(1, "Sushi Boat", "temp", "Japanese"));
		catererList.add(new Caterer(2, "X & Y", "temp", "Chinese"));
		catererList.add(new Caterer(3, "Phat Cat", "temp", "Middle Eastern"));
		catererList.add(new Caterer(4, "Curry in a Hurry", "temp", "Indian"));
		
		for (int i = 0; i < 5; i++) {
			Menu m = new Menu(i, cal.getTime());
			
			m.addFoodItem(0, new FoodItem(0, "Cucumber", 210, 0.8, catererList.get(0)));
			m.addFoodItem(0, new FoodItem(1, "Eggs", 444, 0.2, catererList.get(0)));
			m.addFoodItem(0, new FoodItem(2, "Beef", 105, 0.44, catererList.get(0)));
			m.addFoodItem(0, new FoodItem(3, "Chicken", 295, 0.15, catererList.get(0)));
			m.addFoodItem(0, new FoodItem(4, "Lamb", 310, 0.98, catererList.get(0)));
			m.addFoodItem(0, new FoodItem(4, "Dog", 222, 0.4, catererList.get(0)));
			m.addFoodItem(0, new FoodItem(4, "Horse", 333, 0.71, catererList.get(0)));

			m.addFoodItem(1, new FoodItem(5, "Tomato", 205, 0.6, catererList.get(1)));
			m.addFoodItem(1, new FoodItem(6, "Salad", 166, 0.72, catererList.get(1)));

			m.addFoodItem(2, new FoodItem(7, "Curry", 1001, 1.0, catererList.get(4)));
			m.addFoodItem(2, new FoodItem(8, "Halal", 792, 0.81, catererList.get(4)));
			m.addFoodItem(2, new FoodItem(9, "Naan", 802, 0.02, catererList.get(4)));
			
			menuList.add(m);
			
			cal.add(Calendar.DATE, 1);
		}
	}
	
	public List<Menu> getMenuList() {
		return menuList;
	}
	
	public List<Caterer> getCatererList() {
		return catererList;
	}
	
	public boolean syncData() {
		return true;
	}
}
