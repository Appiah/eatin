package com.linkedin.eatin.model;

import java.util.List;

public class Model {
	private static Model model;
	
	public static Model getModel() {
		if (model == null)
			model = new Model();
		return model;
	}
	
	
	private List<Menu> menuList;
	
	public boolean syncData() {
		return true;
	}
}
