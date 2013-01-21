package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;

public class Caterer {
	public String name;
	public String type;
	public int id;
	public String image_url;
	
	public Caterer (String n, String t, int i, String iu) {
		name = n;
		type = t;
		id = i;
		image_url = iu;
	}
	
	public Caterer(Connection connection, int i) {
		try {
			Statement statement = connection.createStatement();
			String sql = "select Name, Type, ImgURL from caterer where ID = " + i + ";";
			ResultSet result = statement.executeQuery(sql);
			result.next();
			name = result.getString(1);
			type = result.getString(2);
			id = i;
			image_url = result.getString(3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}