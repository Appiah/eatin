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

import play.libs.Json;

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
    return ok(main.render(foodcategory.render("daily"), foodcategory.render("vegetarian"), foodcategory.render("indian")));    
  }

  public static Result getData () {

    try {
      setupConnection();
    } catch (ClassNotFoundException e) {
      return internalServerError("Where is your MySQL JDBC Driver?" + '\n' + e.toString());
    } catch (SQLException e) {
      return internalServerError("Connection Failed! Check output console" + '\n' +  e.toString());
    }

    return ok();
    /*https://github.com/playframework/Play20/wiki/Javajson*/
    /*-----------------------------------------------------*/

    /// POPULATE CLASSES FROM DB


    /// SEND JSON (with current week)

  }

  

  public static Result vote() {
    //final JsonNode values = RequestBody().asJson();// request().body().asJson();
/*
	final Object values = request().body().asJson();

	Integer rating = values

    Integer rating = values.get("rating");
    Integer foodId = values.get("foodId");

    addVote (foodId, rating);
    */
    return ok();
  }


  public static Result comment() {
    /*final sdf values = RequestBody().asJson();//request().body().asJson();

    String message = values.get("message");
    Integer catererId = values.get("catererId");
    // TODO add date and person

    addComment (catererId, message);
    */
    return ok();
  }



}
