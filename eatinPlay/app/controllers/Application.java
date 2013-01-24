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

import static play.libs.Json.toJson;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

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
    Helper helper = new Helper(connection);

    Menu[] menus = helper.getWeeksMenu();

    // return day 2 (we only have data for day 2 at the moment)
    return ok(toJson(menus[2].toJSON()));
  }

  

  public static Result vote() {
    final JsonNode values = request().body().asJson();

    try {
      setupConnection();
    } catch (ClassNotFoundException e) {
      return internalServerError("Where is your MySQL JDBC Driver?" + '\n' + e.toString());
    } catch (SQLException e) {
      return internalServerError("Connection Failed! Check output console" + '\n' +  e.toString());
    }

    int foodId = values.get("foodId").getIntValue();
    int rating = values.get("rating").getIntValue();

    Helper helper = new Helper(connection);

    if (rating == 1) {
      helper.changeRating(foodId, true);
    } else {
      helper.changeRating(foodId, false);
    }

    return ok();
  }


  public static Result comment() {
    final JsonNode values = request().body().asJson();

    try {
      setupConnection();
    } catch (ClassNotFoundException e) {
      return internalServerError("Where is your MySQL JDBC Driver?" + '\n' + e.toString());
    } catch (SQLException e) {
      return internalServerError("Connection Failed! Check output console" + '\n' +  e.toString());
    }

    String message = values.get("message").getValueAsText();
    int catererId = values.get("catererId").getIntValue();

    Helper helper = new Helper(connection);

    helper.addComment (catererId, message);
    
    return ok();
  }

 /*public static String convertMapToJson(Map map)throws JSONException{
  
  JSONObject obj = new JSONObject();
  JSONObject main = new JSONObject();
  Set set = map.keySet();
  
  Iterator iter = set.iterator();

     while (iter.hasNext()) {
      String key = (String) iter.next();
   obj.accumulate(key, map.get(key));
     }
     main.accumulate("data",obj);
     
     return main.toString();
 }*/


}
