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

  private static Connection connection = null;

  private enum FoodType { DAILY, VEGETARIAN, INDIAN }
  
  private static boolean setupConnection() throws ClassNotFoundException, SQLException{
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw e;
      //return internalServerError("Where is your MySQL JDBC Driver?" + '\n' + e.toString());
    }

    try {
      connection = DriverManager.getConnection(
          "jdbc:mysql://lhuang-ld.linkedin.biz:3306/EatIn", "root", "eatin");
    } catch (SQLException e) {
      throw e;
      //return internalServerError("Connection Failed! Check output console" + '\n' +  e.toString());
    }
    return true;
  }

  public static Result index() {

    try {
      setupConnection();
    } catch (ClassNotFoundException e) {
      return internalServerError("Where is your MySQL JDBC Driver?" + '\n' + e.toString());
    } catch (SQLException e) {
      return internalServerError("Connection Failed! Check output console" + '\n' +  e.toString());
    }
    
    Helper helper = new Helper(connection);  
    int dayOfWeek = 0;

    // Get date offset
    Date date = new Date(System.currentTimeMillis());
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date);
    if (helper.GetDayFromDate(date) == 7 || helper.GetDayFromDate(date) == 1) {
      dayOfWeek = 0;
    } else {
      dayOfWeek = helper.GetDayFromDate(date) - 1;
    }

    helper = null;

    //render(main.render(displayDailyCatering(dayOfWeek, FoodType.DAILY), displayVegetarian(dayOfWeek, FoodType.VEGETARIAN), displayIndian(dayOfWeek, FoodType.INDIAN)));
    return ok(main.render(displayCatering(dayOfWeek, FoodType.DAILY), displayCatering(dayOfWeek, FoodType.VEGETARIAN), displayCatering(dayOfWeek, FoodType.INDIAN)));  
        
  }

  public static play.api.templates.Html displayCatering(int dayOfWeek, FoodType foodType) {

    int foodIndexValue = 0;
    if (foodType == FoodType.DAILY) {
      foodIndexValue = 1;
    } else if (foodType == FoodType.INDIAN) {
      foodIndexValue = 0;
    } else if (foodType == FoodType.VEGETARIAN) {
      foodIndexValue = 2;
    }

    /*https://github.com/playframework/Play20/wiki/Javajson*/
    /*-----------------------------------------------------*/

    Helper helper = new Helper(connection);    
    DailyMenu[] meals =  helper.getWeeksMenu();
      
    String[] names = new String[meals[dayOfWeek].meals[foodIndexValue].dishes.size()]; 
    int[] votes = new int[meals[dayOfWeek].meals[foodIndexValue].dishes.size()]; 
    int[] pos =  new int[meals[dayOfWeek].meals[foodIndexValue].dishes.size()];
    int[] ids = new int[meals[dayOfWeek].meals[foodIndexValue].dishes.size()]; 

    for (int i = 0; i < meals[dayOfWeek].meals[foodIndexValue].dishes.size(); i++) {
      names[i] = meals[dayOfWeek].meals[foodIndexValue].dishes.get(i).name;
      ids[i] = meals[dayOfWeek].meals[foodIndexValue].dishes.get(i).id;
      votes[i] = helper.getNumRatingsForDish(meals[dayOfWeek].meals[foodIndexValue].dishes.get(i).id);
      pos[i] = helper.getNumPositiveRatingsForDish(meals[dayOfWeek].meals[foodIndexValue].dishes.get(i).id);
    }

    String foodTypeName = "";
    int catererID = meals[dayOfWeek].dv_caterer.id;

    if (foodType == FoodType.DAILY) {
      foodTypeName = "Daily Catering";
    } else if (foodType == FoodType.INDIAN) {
      foodTypeName = "Indian";
      catererID = meals[dayOfWeek].indian_caterer.id;
    } else if (foodType == FoodType.VEGETARIAN) {
      foodTypeName = "Vegetarian";
    }

  	return foodcategory.render(foodTypeName, meals[dayOfWeek].dv_caterer.name, helper.getNumRatings(catererID), helper.getNumPositiveRatings(catererID), helper.getNumComments(catererID), names, votes, pos, ids);
  }

  public static Result upVote(String id) {
    
    try {
      setupConnection();
    } catch (ClassNotFoundException e) {
      return internalServerError("Where is your MySQL JDBC Driver?" + '\n' + e.toString());
    } catch (SQLException e) {
      return internalServerError("Connection Failed! Check output console" + '\n' +  e.toString());
    }


    Helper helper = new Helper(connection);
    helper.changeRating(Integer.parseInt(id), true);
    int dayOfWeek = 0;

    // Get date offset
    Date date = new Date(System.currentTimeMillis());
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date);
    if (helper.GetDayFromDate(date) == 7 || helper.GetDayFromDate(date) == 1) {
      dayOfWeek = 0;
    } else {
      dayOfWeek = helper.GetDayFromDate(date) - 1;
    }

    helper = null;

    return ok(main.render(displayCatering(dayOfWeek, FoodType.DAILY), displayCatering(dayOfWeek, FoodType.VEGETARIAN), displayCatering(dayOfWeek, FoodType.INDIAN)));  
  }

  public static Result downVote(String id) {
    
    try {
      setupConnection();
    } catch (ClassNotFoundException e) {
      return internalServerError("Where is your MySQL JDBC Driver?" + '\n' + e.toString());
    } catch (SQLException e) {
      return internalServerError("Connection Failed! Check output console" + '\n' +  e.toString());
    }
    
    Helper helper = new Helper(connection);
    helper.changeRating(Integer.parseInt(id), false);
    int dayOfWeek = 0;

    // Get date offset
    Date date = new Date(System.currentTimeMillis());
    Calendar calendar1 = Calendar.getInstance();
    calendar1.setTime(date);
    if (helper.GetDayFromDate(date) == 7 || helper.GetDayFromDate(date) == 1) {
      dayOfWeek = 0;
    } else {
      dayOfWeek = helper.GetDayFromDate(date) - 1;
    }

    helper = null;


    return ok(main.render(displayCatering(dayOfWeek, FoodType.DAILY), displayCatering(dayOfWeek, FoodType.VEGETARIAN), displayCatering(dayOfWeek, FoodType.INDIAN)));  
  }


}