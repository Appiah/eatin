package com.linkedin.eatin.model;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.os.AsyncTask;

import com.linkedin.eatin.utility.DataSync;
import com.linkedin.eatin.utility.Updateable;

public class BaseData {
	private static BaseData model;
	private static final String serverUrl = "bhatton-ld.linkedin.biz:9000";
	
	private AsyncTask<?,?,?> syncTask;
	
	public static BaseData getModel() {
		if (model == null)
			model = new BaseData();
		return model;
	}
	
	
	private List<Menu> menuList;
	
	public BaseData() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		menuList = new LinkedList<Menu>();
		
		Caterer c0 = new Caterer(0, "Tanpopo", "temporary", "Japanese");
		Caterer c1 = new Caterer(1, "Sushi Boat", "temp", "Japanese");
		Caterer c2 = new Caterer(2, "X & Y", "temp", "Chinese");
		Caterer c3 = new Caterer(3, "Phat Cat", "temp", "Middle Eastern");
		Caterer c4 = new Caterer(4, "Curry in a Hurry", "temp", "Indian");
		
		for (int i = 0; i < 5; i++) {
			Menu m = new Menu(i, cal.getTime());
			
			m.addFoodItem(0, new FoodItem(0, "Cucumber", 210, 50, c0));
			m.addFoodItem(0, new FoodItem(1, "Eggs", 444, 25, c0));
			m.addFoodItem(0, new FoodItem(2, "Beef", 105, 44, c0));
			m.addFoodItem(0, new FoodItem(3, "Chicken", 295, 15, c0));
			m.addFoodItem(0, new FoodItem(4, "Lamb", 310, 19, c0));
			m.addFoodItem(0, new FoodItem(4, "Dog", 222, 25, c0));
			m.addFoodItem(0, new FoodItem(4, "Horse", 333, 40, c0));

			m.addFoodItem(1, new FoodItem(5, "Tomato", 205, 11, c1));
			m.addFoodItem(1, new FoodItem(6, "Salad", 166, 8, c1));

			m.addFoodItem(2, new FoodItem(7, "Curry", 1001, 1, c4));
			m.addFoodItem(2, new FoodItem(8, "Halal", 792, 88, c4));
			m.addFoodItem(2, new FoodItem(9, "Naan", 802, 98, c4));
			
			menuList.add(m);
			
			cal.add(Calendar.DATE, 1);
		}
	}
	
	public void addMenu(Menu menu) {
		this.menuList.add(menu);
	}
	
	public void clear() {
		this.menuList.clear();
	}
	
	public List<Menu> getMenuList() {
		return menuList;
	}
	
	public boolean startSyncData(Updateable context) {
		syncTask = (new DataSync(context)).execute(serverUrl);
		
		return true;
	}
}
