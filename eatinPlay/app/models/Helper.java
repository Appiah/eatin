package models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class Helper {
	
	static Connection connection;
	static SimpleDateFormat formatter;
	static SimpleDateFormat dt;
	
	public Helper (Connection c) {
		connection = c;
		formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		dt = new SimpleDateFormat("yyyy-MM-dd");
	}


	public static void populateDB() {
		
//		for (int i = 0; i< 5; i++) {
//			for (int j = 0; j < 5; j++) {
//				addComment(i, "OMG! Vegetarian food is soooooooooo goood");
//				changeRating(1, true);
//			}
//		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				Statement statement;
				try {
					statement = connection.createStatement();
					int day = 21 + i;
					int id = (i*3)+j;
					//String sql = "insert into meal(id, catererid, date, category) "+
					//		"values (" + id + ", " + 1 + ", " + "'2013/01/" + day + "', " + j+ ");";
					String sql = "insert into foodItems(id, name, rating, numratings, mealid) values (" +
							id + ", " + "'food " + id + "', " + 
							"20, 30, " + id + ")";
					//System.out.println(sql);
					statement.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public static void addComment(int caterer_id, String Message) {
		
		Date date = new Date(System.currentTimeMillis());
		try {
			Statement statement = connection.createStatement();
			String sql = "insert into comments (CatererID, Date, Message) "+
					"values (" + caterer_id + ", '" + formatter.format(date) + "', '" + Message + "');";
			System.out.println(sql);
			statement.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

		
	public static void changeRating(int food_id, boolean upVote) {
		
		try {
			Statement statement = connection.createStatement();
			String sql = "select Rating, NumRatings from foodItems where ID = '" + food_id + "';";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			int rating = result.getInt(1); 
			int num = result.getInt(2);
			if (upVote) {
				rating++; 
			}
			num++;
			
			String update = "update foodItems set Rating = " + rating + ", NumRatings = " + num + ";";  
			statement.executeUpdate(update);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public Menu getWeeksMenu() {
		Menu = new Menu();
		Date date = new Date(System.currentTimeMillis());
		//System.out.println(GetDayFromDate(date));
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		if (GetDayFromDate(date) == 7) {
			calendar1.add(Calendar.DAY_OF_YEAR, 2);
			date = calendar1.getTime();
		
		}
		else if (GetDayFromDate(date) == 1) {
			calendar1.add(Calendar.DAY_OF_YEAR, 1);
			date = calendar1.getTime();
		}
		//date.setTime(0);
		
		//System.out.println(date);
		
		
		int curDay = GetDayFromDate(date); 
		
		for (int i = 2; i < 7; i++) {
			int diff = i - curDay ;
			//System.out.println(diff);
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DAY_OF_YEAR, diff);

			Meal[] meals = getMenuByDate(calendar.getTime());
			//System.out.println(date);
			DailyMenu dm = new DailyMenu(meals[0].caterer_id, meals[1].caterer_id, meals, connection);
			menu[i-2] = dm;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String json ="";
		try {
			json = mapper.writeValueAsString(menu);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(json);
		return menu;
	}
	
	public Meal[] getMenuByDate(Date date) {
		Meal[] meals = new Meal[3];
		try {
			Statement statement = connection.createStatement();
			Statement statement1 = connection.createStatement();
			Statement statement2 = connection.createStatement();
			String sql = "select foodItems.ID, Name, CatererID, Rating, NumRatings from foodItems, meal "+
					"where meal.ID = foodItems.MealID" +
					" and Date = '" +  dt.format(date) + 
					"' and Category = ";
			//System.out.println(sql);
			
			String sqlIndian = sql + "0;";
			String sqlVeg = sql + "2";
			String sqlDaily = sql + "1";
			
			
			ResultSet r_indian = statement.executeQuery(sqlIndian);
			ResultSet r_veg = statement1.executeQuery(sqlVeg);
			ResultSet r_daily = statement2.executeQuery(sqlDaily);
			
			r_indian.next();
			r_daily.next();
			r_veg.next();
			
			Meal daily = new Meal(r_daily.getInt(3), date, Category.Daily);
			Meal indian = new Meal(r_indian.getInt(3), date, Category.Indian);
			Meal veg = new Meal(r_veg.getInt(3), date, Category.Vegetarian);

			do {
				Dish d_indian = new Dish(r_indian.getString(2), r_indian.getInt(1), r_indian.getInt(5), r_indian.getInt(4));
				indian.addDish(d_indian);
			} while (r_indian.next());
			
			do {
				Dish d_daily = new Dish(r_daily.getString(2), r_daily.getInt(1), r_daily.getInt(5), r_daily.getInt(4));
				daily.addDish(d_daily);
			} while (r_daily.next());
			
			do {
				Dish d_veg = new Dish(r_veg.getString(2), r_veg.getInt(1), r_veg.getInt(5), r_veg.getInt(4));
				veg.addDish(d_veg);
			} while (r_veg.next());
			meals[0] = indian;
			meals[1] = daily;
			meals[2] = veg;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return meals;
	}
	
	public ArrayList<Comment> getComments(int caterer_id, int numComments) {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		String json;
		try {
			Statement statement = connection.createStatement();
			String sql = "select Message, CatererID, Date from comments order by Date desc;";
			ResultSet result = statement.executeQuery(sql);
			int i = 0;
			while (result.next() && i < numComments) {
				Comment c = new Comment(result.getString(1), new Date(result.getTimestamp(3).getTime()), result.getInt(2));
				comments.add(c);
				i++;
			}
			
			ObjectMapper mapper = new ObjectMapper();
			
			json = mapper.writeValueAsString(comments);
			
			//System.out.println(json);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}

	public int getNumComments(int caterer_id) {
		try {
			Statement statement = connection.createStatement();
			String sql = "select count(*) from comments where catererid = " + caterer_id + ";";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getInt(1); 
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}

	public int getNumPositiveRatings(int caterer_id) {
		try {
			Statement statement = connection.createStatement();
			String sql = "select sum(rating) from meal, foodItems, caterer " +
				"where caterer.id = meal.id " + 
				"and meal.id = foodItems.id " +
				"and caterer.id = " + caterer_id + ";";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getInt(1); 
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public int getNumRatings(int caterer_id) {
		try {
			Statement statement = connection.createStatement();
			String sql = "select sum(numRatings) from meal, foodItems, caterer " +
				"where caterer.id = meal.id " + 
				"and meal.id = foodItems.id " +
				"and caterer.id = " + caterer_id + ";";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getInt(1); 
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public enum Category {
		Indian, Daily, Vegetarian
	}

	
	public int getNumPositiveRatingsForDish(int dish_id) {
		try {
			Statement statement = connection.createStatement();
			String sql = "select sum(rating) from foodItems " +
				"where id = " + dish_id + ";";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getInt(1); 
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public int getNumRatingsForDish(int dish_id) {
		try {
			Statement statement = connection.createStatement();
			String sql = "select sum(numRatings) from foodItems " +
				"where id = " + dish_id + ";";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getInt(1); 
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	

	

	
	public int GetDayFromDate(Date date) {  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);  
	        return calendar.get(Calendar.DAY_OF_WEEK);   
	} 
}
