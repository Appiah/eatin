package models;

import java.util.ArrayList;
import java.util.Date;

import models.Helper.*;

public class Meal {
	public int caterer_id;
	public Date date;
	public ArrayList<Dish> dishes;
	public Category category;
	
	public Meal(int cid, Date d, Category c){
		caterer_id = cid;
		date = d;
		dishes = new ArrayList<Dish>();
		category = c;
	}
	
	public void addDish(Dish dish) {
		dishes.add(dish);
	}
}