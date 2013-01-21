package models;

import models.*;
import java.sql.Connection;

public class DailyMenu  {
	public Caterer indian_caterer;
	public Caterer dv_caterer;
	public Meal[] meals;
	
	public DailyMenu(int caterer_id, int caterer_id2, Meal[] m, Connection connection) {
		indian_caterer = new Caterer(connection, caterer_id);
		dv_caterer = new Caterer(connection, caterer_id2);
		meals = m;
	}

}