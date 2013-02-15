package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {
	static Connection connection;
	static SimpleDateFormat formatter;
	static SimpleDateFormat dt;

	public Helper (Connection c) {
		connection = c;
		formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		dt = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	public static boolean addComment(int catererId, String Message) {
		try {
			Date date = new Date();
			Statement statement = connection.createStatement();
			String sql = "insert into comments (CatererID, Date, Message) "+
					"values (" + catererId + ", '" + formatter.format(date) + "', '" + Message + "');";
			System.out.println(sql);
			statement.executeUpdate(sql);

			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public static boolean changeRating(int foodId, boolean isUpVote) {
		try {
			String sql = "update foodItems set " 
					+ (isUpVote? "Rating = Rating + 1, " : "") 
					+ "NumRatings = NumRatings + 1 where ID = ?;";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setInt(1, foodId);
			
			pst.execute();
			
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Caterer getCatererById(int catererId) {
		try {
			String sql = "select * from caterer where ID = ?;";
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setInt(1, catererId);
			ResultSet rs = pst.executeQuery();
			rs.next();
			
			Caterer caterer = new Caterer(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(3), 
					rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
			
			return caterer;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void AddCaterer(String name, String imageUrl, String foodType,
			String contact, String email, String phoneNumber, String address, String comments) {
		
		try {
			String sql = "select max(ID) from caterer;";
			PreparedStatement pst = connection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			rs.next();
			int nextID = rs.getInt(1) + 1;
			
			sql = "insert into caterer (ID, Name, Type, ImgURL, MainContact, Email, Phone, Address, Comments) "+
					"values ("
					+ nextID + ", "
					+ "'" + name + "', "
					+ "'" + foodType + "', "
					+ "'" + imageUrl + "', "
					+ "'" + contact + "', "
					+ "'" + email + "', "
					+ "'" + phoneNumber + "', "
					+ "'" + address + "', "
					+ "'" + comments + "');";
			
			pst = connection.prepareStatement(sql);
			pst.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public Menu[] getWeeksMenu() {
		Menu[] menus = new Menu[5];
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		
		int diff = calendar.get(Calendar.DAY_OF_WEEK);
		
		if (diff == 7 || diff == 1)
			calendar.add(Calendar.DAY_OF_YEAR, (9 - diff) % 7);
		else
			calendar.add(Calendar.DAY_OF_YEAR, -diff + 1);

		for (int i = 0; i < 5; i++) {
			menus[i] = getMenuByDate(calendar.getTime());
			
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		return menus;
	}

	public Menu getMenuByDate(Date date) {
		try {
			Statement statement = connection.createStatement();
			Statement statement1 = connection.createStatement();
			Statement statement2 = connection.createStatement();
			String sql = "select foodItems.ID, Name, CatererID, Rating, NumRatings from foodItems, meal "+
					"where meal.ID = foodItems.MealID " +
					"and Date = '" +  dt.format(date) + 
					"' and Category = ";

			String sqlIndian = sql + "0;";
			String sqlVeg = sql + "2";
			String sqlDaily = sql + "1";

			ResultSet rsIndian = statement.executeQuery(sqlIndian);
			ResultSet rsVeg = statement1.executeQuery(sqlVeg);
			ResultSet rsDaily = statement2.executeQuery(sqlDaily);

			rsIndian.next();
			rsDaily.next();
			rsVeg.next();

			Menu menu = new Menu(date);
			Map<Integer, Caterer> catererMap = new HashMap<Integer, Caterer>();
			Integer caterId = -1;
			Caterer cater = null;

			do {
				caterId = rsIndian.getInt(3);
				if (catererMap.get(caterId) == null) {
					cater = getCatererById(caterId);
					catererMap.put(caterId, cater);
				}
				menu.addFoodItem(Constants.CAT_INDIAN, new FoodItem(rsIndian.getInt(1), rsIndian.getString(2), rsIndian.getInt(5), rsIndian.getInt(4), cater));
			} while (rsIndian.next());

			do {
				caterId = rsDaily.getInt(3);
				if (catererMap.get(caterId) == null) {
					cater = getCatererById(caterId);
					catererMap.put(caterId, cater);
				}
				menu.addFoodItem(Constants.CAT_CATER, new FoodItem(rsDaily.getInt(1), rsDaily.getString(2), rsDaily.getInt(5), rsDaily.getInt(4), cater));
			} while (rsDaily.next());

			do {
				caterId = rsVeg.getInt(3);
				if (catererMap.get(caterId) == null) {
					cater = getCatererById(caterId);
					catererMap.put(caterId, cater);
				}
				menu.addFoodItem(Constants.CAT_VEGGIE, new FoodItem(rsVeg.getInt(1), rsVeg.getString(2), rsVeg.getInt(5), rsVeg.getInt(4), cater));
			} while (rsVeg.next());

			return menu;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Comment> getComments(int catererId, int numComments) {
		List<Comment> comments = new ArrayList<Comment>();

		try {
			Statement statement = connection.createStatement();
			String sql = "select Message, CatererID, Date from comments order by Date desc;";
			ResultSet result = statement.executeQuery(sql);
			int i = 0;
			while (result.next() && i++ < numComments)
				comments.add(new Comment(result.getLong(2), result.getString(1), "Anonymous", new Date(result.getTimestamp(3).getTime())));

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return comments;
	}

	public int getNumComments(int catererId) {
		try {
			Statement statement = connection.createStatement();
			String sql = "select count(*) from comments where catererid = " + catererId + ";";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			
			return result.getInt(1); 
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int getNumPositiveRatings(int catererId) {
		try {
			Statement statement = connection.createStatement();
			String sql = "select sum(rating) from meal, foodItems, caterer " +
					"where caterer.id = meal.id " + 
					"and meal.id = foodItems.id " +
					"and caterer.id = " + catererId + ";";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getInt(1); 

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int getNumRatings(int catererId) {
		try {
			Statement statement = connection.createStatement();
			String sql = "select sum(numRatings) from meal, foodItems, caterer " +
					"where caterer.id = meal.id " + 
					"and meal.id = foodItems.id " +
					"and caterer.id = " + catererId + ";";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getInt(1); 

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int getNumPositiveRatingsForFoodItem(int foodItemId) {
		try {
			Statement statement = connection.createStatement();
			String sql = "select sum(rating) from foodItems " +
					"where id = " + foodItemId + ";";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			
			return result.getInt(1); 
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int getNumRatingsForFoodItem(int foodItemId) {
		try {
			Statement statement = connection.createStatement();
			String sql = "select sum(numRatings) from foodItems " +
					"where id = " + foodItemId + ";";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			return result.getInt(1); 

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
