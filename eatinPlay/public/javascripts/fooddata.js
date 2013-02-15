
// need to include this in the main HTML page
//  it is used for the $.getJSON function
//<script src="http://code.jquery.com/jquery-latest.js"></script>

/*
var foodData*/
/*
var dataLoaded = false;

function fetchData{
	*/ /*  gets the food data from a database */
	/*if (!dataLoaded) {
		jQuery.getJSON("../server/foodData.json", 
			function (data) {
				foodData = data;
			}
	}
}
*/

function getFoodDataHTML(day, foodType) {

	//fetchData();

	var url = "../server/foodData.json";
	var htmlTagPrefix


	if (foodType == "veg") {
		htmlTagPrefix = "veg-";
	} else if (foodType == "daily") {
		htmlTagPrefix = "daliy-";
	} else if (foodType == "indian") {
		htmlTagPrefix = "indian-";
	} else {
		/*  ERROR - wrong value passed */
	}

	/*jQuery.getJSON("../server/foodData.json", 
			function (data) {
				
				jQuery.each

			}*/

	var output = "";

	jQuery.getJSON(url).done(function(data) {
    	console.log(data);

    	var dayNum = Date.getDay() - 1;
    	// set the day to the closest week day in the current week (if Saturday or Sunday)
    	if (dayNum == -1) {
    		dayNum = 0;
    	} else if (dayNum == 5) {
    		dayNum = 4;
    	}

    	output += "<div class=" + htmlTagPrefix + "day-title>" +
    					data.weeklyMenu[0].day[dayNum].dayName +
    					"</div>";

    	var mealInfo
	 	if (foodType == "veg") {
			mealInfo = data.weeklyMenu[0].day[dayNum].food.vegetarian;
		} else if (foodType == "catering") {
			mealInfo = data.weeklyMenu[0].day[dayNum].food.catering;
		} else if (foodType == "indian") {
			mealInfo = data.weeklyMenu[0].day[dayNum].food.indian;
		} else {
			/*  ERROR - wrong value passed */
		}

    	jQuery.each(mealInfo.foodItems, function(i,item){
    		var numPositive = (item.numRating + item.rating) * 0.5 - (item.numRating - item.rating);
    		var numNegative = item.numRating - numPositive;

    		output += "<section>" + item.name  + "<br/>Number Positive " + numPositive + " Number Negative " + numNegative + "</section>";
		});

	});

	document.getElementById("placeholder").innerHTML=output;

}