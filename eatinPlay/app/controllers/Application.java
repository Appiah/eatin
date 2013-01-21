package controllers;

import play.*;
import play.mvc.*;
import  models.*;

import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import views.html.*;

public class Application extends Controller {

  static Connection connection = null;
  
  public static Result index() {

    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Where is your MySQL JDBC Driver?");
      e.printStackTrace();

      return ok("Where is your MySQL JDBC Driver?");
      //return;
    }

    try {
      connection = DriverManager.getConnection(
          "jdbc:mysql://lhuang-ld.linkedin.biz:3306/EatIn", "root", "eatin");

    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      return ok("Connection Failed! Check output console" + '\n' +  e.toString());

      //return;
    }

    return ok(main.render(displayDailyCatering(), displayVegetarian(), displayIndian()));
  }

  public static play.api.templates.Html displayDailyCatering() {

    Helper helper = new Helper(connection);    
    DailyMenu[] meals =  helper.getWeeksMenu();
    
    int dayOfWeek = 0;

    Date date = new Date(System.currentTimeMillis());
    //System.out.println(GetDayFromDate(date));
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date);
    if (helper.GetDayFromDate(date) == 7 || helper.GetDayFromDate(date) == 1) {
      dayOfWeek = 0;
    } else {
      dayOfWeek = helper.GetDayFromDate(date) - 1;
    }

    String[] names = new String[meals[dayOfWeek].meals[1].dishes.size()];// {"ace", "boom", "crew", "dog", "eon"};  
    int[] votes = new int[meals[dayOfWeek].meals[1].dishes.size()];  //{1, 2, 3, 4, 5};  
    int[] pos =  new int[meals[dayOfWeek].meals[1].dishes.size()];//  {0, 1, 3, 3, 7};  
    int[] ids = new int[meals[dayOfWeek].meals[1].dishes.size()]; //{10, 20, 30, 40, 50};  
    int[] red =  new int[meals[dayOfWeek].meals[1].dishes.size()];//  {0, 1, 3, 3, 7};  
    int[] green = new int[meals[dayOfWeek].meals[1].dishes.size()]; //{10, 20, 30, 40, 50};  
    int[] redBar =  new int[meals[dayOfWeek].meals[1].dishes.size()];//  {0, 1, 3, 3, 7};  
    int[] greenBar = new int[meals[dayOfWeek].meals[1].dishes.size()]; //{10, 20, 30, 40, 50};  


    for (int i = 0; i < meals[dayOfWeek].meals[1].dishes.size(); i++) {
      names[i] = meals[dayOfWeek].meals[1].dishes.get(i).name;
      ids[i] = meals[dayOfWeek].meals[1].dishes.get(i).id;
      votes[i] = helper.getNumRatingsForDish(meals[dayOfWeek].meals[1].dishes.get(i).id);
      pos[i] = helper.getNumPositiveRatingsForDish(meals[dayOfWeek].meals[1].dishes.get(i).id);
      red[i] = (votes[i] - pos[i])*255 / votes[i];
      green[i] = pos[i] * 255 / votes[i];
      redBar[i] = (votes[i] - pos[i]) * 100 / votes[i] + 1;
      greenBar[i] = pos[i] * 100 / votes[i];
    }

  	return foodcategory.render("Daily Catering", meals[dayOfWeek].dv_caterer.name, helper.getNumRatings(meals[dayOfWeek].dv_caterer.id), helper.getNumComments(meals[dayOfWeek].dv_caterer.id), names, votes, pos, ids, red, green, redBar, greenBar );
  }
  

  public static play.api.templates.Html displayIndian() {
    

    Helper helper = new Helper(connection);    
    DailyMenu[] meals =  helper.getWeeksMenu();
    
    int dayOfWeek = 0;

    Date date = new Date(System.currentTimeMillis());
    //System.out.println(GetDayFromDate(date));
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date);
    if (helper.GetDayFromDate(date) == 7 || helper.GetDayFromDate(date) == 1) {
      dayOfWeek = 0;
    } else {
      dayOfWeek = helper.GetDayFromDate(date) - 1;
    }

    String[] names = new String[meals[dayOfWeek].meals[0].dishes.size()];// {"ace", "boom", "crew", "dog", "eon"};  
    int[] votes = new int[meals[dayOfWeek].meals[0].dishes.size()];  //{1, 2, 3, 4, 5};  
    int[] pos =  new int[meals[dayOfWeek].meals[0].dishes.size()];//  {0, 1, 3, 3, 7};  
    int[] ids = new int[meals[dayOfWeek].meals[0].dishes.size()]; //{10, 20, 30, 40, 50};  
    int[] red =  new int[meals[dayOfWeek].meals[0].dishes.size()];//  {0, 1, 3, 3, 7};  
    int[] green = new int[meals[dayOfWeek].meals[0].dishes.size()]; //{10, 20, 30, 40, 50};  
    int[] redBar =  new int[meals[dayOfWeek].meals[0].dishes.size()];//  {0, 1, 3, 3, 7};  
    int[] greenBar = new int[meals[dayOfWeek].meals[0].dishes.size()]; //{10, 20, 30, 40, 50};  


    for (int i = 0; i < meals[dayOfWeek].meals[0].dishes.size(); i++) {
      names[i] = meals[dayOfWeek].meals[0].dishes.get(i).name;
      ids[i] = meals[dayOfWeek].meals[0].dishes.get(i).id;
      votes[i] = helper.getNumRatingsForDish(meals[dayOfWeek].meals[0].dishes.get(i).id);
      pos[i] = helper.getNumPositiveRatingsForDish(meals[dayOfWeek].meals[0].dishes.get(i).id);
      red[i] = (votes[i] - pos[i]) *255 / votes[i];
      green[i] = pos[i]*255/votes[i];
      redBar[i] = (votes[i] - pos[i]) * 100 / votes[i] + 1;
      greenBar[i] = pos[i] * 100 / votes[i];
    }

    return foodcategory.render("Indian", meals[dayOfWeek].dv_caterer.name, helper.getNumRatings(meals[dayOfWeek].indian_caterer.id),  helper.getNumComments(meals[dayOfWeek].indian_caterer.id), names, votes, pos, ids, red, green, redBar, greenBar );
  }

  public static play.api.templates.Html displayVegetarian() {

    Helper helper = new Helper(connection);    
    DailyMenu[] meals =  helper.getWeeksMenu();
    
    int dayOfWeek = 0;

    Date date = new Date(System.currentTimeMillis());
    //System.out.println(GetDayFromDate(date));
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date);
    if (helper.GetDayFromDate(date) == 7 || helper.GetDayFromDate(date) == 1) {
      dayOfWeek = 0;
    } else {
      dayOfWeek = helper.GetDayFromDate(date) - 1;
    }

    String[] names = new String[meals[dayOfWeek].meals[2].dishes.size()];// {"ace", "boom", "crew", "dog", "eon"};  
    int[] votes = new int[meals[dayOfWeek].meals[2].dishes.size()];  //{1, 2, 3, 4, 5};  
    int[] pos =  new int[meals[dayOfWeek].meals[2].dishes.size()];//  {0, 1, 3, 3, 7};  
    int[] ids = new int[meals[dayOfWeek].meals[2].dishes.size()]; //{10, 20, 30, 40, 50};  
    int[] red =  new int[meals[dayOfWeek].meals[2].dishes.size()];//  {0, 1, 3, 3, 7};  
    int[] green = new int[meals[dayOfWeek].meals[2].dishes.size()]; //{10, 20, 30, 40, 50};  
    int[] redBar =  new int[meals[dayOfWeek].meals[2].dishes.size()];//  {0, 1, 3, 3, 7};  
    int[] greenBar = new int[meals[dayOfWeek].meals[2].dishes.size()]; //{10, 20, 30, 40, 50};  


    for (int i = 0; i < meals[dayOfWeek].meals[2].dishes.size(); i++) {
      names[i] = meals[dayOfWeek].meals[2].dishes.get(i).name;
      ids[i] = meals[dayOfWeek].meals[2].dishes.get(i).id;
      votes[i] = helper.getNumRatingsForDish(meals[dayOfWeek].meals[2].dishes.get(i).id);
      pos[i] = helper.getNumPositiveRatingsForDish(meals[dayOfWeek].meals[2].dishes.get(i).id);
      red[i] = (votes[i] - pos[i]) *255 / votes[i];
      green[i] = pos[i]*255/votes[i];
      redBar[i] = (votes[i] - pos[i]) * 100 / votes[i] + 1;
      greenBar[i] = pos[i] * 100 / votes[i];
    }

    return foodcategory.render("Vegetarian", meals[dayOfWeek].dv_caterer.name, helper.getNumRatings(meals[dayOfWeek].dv_caterer.id),  helper.getNumComments(meals[dayOfWeek].dv_caterer.id), names, votes, pos, ids, red, green, redBar, greenBar );
  }

  public static Result upVote(String id) {
    
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Where is your MySQL JDBC Driver?");
      e.printStackTrace();

      return ok("Where is your MySQL JDBC Driver?");
      //return;
    }

    try {
      connection = DriverManager.getConnection(
          "jdbc:mysql://lhuang-ld.linkedin.biz:3306/EatIn", "root", "eatin");

    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      return ok("Connection Failed! Check output console" + '\n' +  e.toString());

      //return;
    }
    
    Helper helper = new Helper(connection);

    helper.changeRating(Integer.parseInt(id), true);

    return ok(main.render(displayDailyCatering(), displayVegetarian(), displayIndian()));
  }

public static Result downVote(String id) {
    
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Where is your MySQL JDBC Driver?");
      e.printStackTrace();

      return ok("Where is your MySQL JDBC Driver?");
      //return;
    }

    try {
      connection = DriverManager.getConnection(
          "jdbc:mysql://lhuang-ld.linkedin.biz:3306/EatIn", "root", "eatin");

    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      return ok("Connection Failed! Check output console" + '\n' +  e.toString());

      //return;
    }
    
    Helper helper = new Helper(connection);

    helper.changeRating(Integer.parseInt(id), false);

    return ok(main.render(displayDailyCatering(), displayVegetarian(), displayIndian()));
  }


}