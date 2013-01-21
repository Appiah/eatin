package models;

import java.util.Date;

public class Comment {
	
	public String text;
	public Date date_time;
	public int caterer_id;
	
	public Comment(String t,  Date dt, int ci ) {
		text = t;
		date_time = dt;
		caterer_id = ci;
	}
}