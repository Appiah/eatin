package com.linkedin.eatin;


public class Dish {
	public String name;
	public int id;
	public int num_ratings;
	public int rating;
	
	public Dish(String n, int i, int num, int r) {
		name=n;
		id=i;
		num_ratings=num;
		rating=r;
	}
}