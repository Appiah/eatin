package models;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BaseData {
	private static BaseData model;
	
	public static BaseData getModel() {
		if (model == null)
			model = new BaseData();
		return model;
	}
	
	
	private List<Menu> menuList;
	private List<Caterer> catererList;
	
	public BaseData() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		menuList = new LinkedList<Menu>();
		catererList = new LinkedList<Caterer>();
	}
	
	public List<Menu> getMenuList() {
		return menuList;
	}
	
	public List<Caterer> getCatererList() {
		return catererList;
	}
	
	public boolean syncData() {
		return true;
	}
}
