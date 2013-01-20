package com.linkedin.eatin;


public class DailyMenu {
	public Caterer indian_caterer;
	public Caterer dv_caterer;
	public Meal[] meals;
	
	public DailyMenu(int caterer_id, int caterer_id2, Meal[] m) {
		indian_caterer = new Caterer(caterer_id);
		dv_caterer = new Caterer(caterer_id2);
		meals = m;
	}

}