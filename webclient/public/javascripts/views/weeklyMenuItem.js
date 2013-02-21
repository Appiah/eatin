var WeeklyMenuItem = Backbone.View.extend({
	initialize: function() {
		this.data = this.options.data;
		this.render();
	},
	
	render: function() {
		var self = this;
		
		var renderType = function(_key, _title) {
			var foodsData = self.data[_key];
			var $element = $($.trim(_.template($("#tSideFoodList").html(), {})));
			var $foodList = $element.find("[data-tid='foodList']");
			var $foodItemTemplate = $element.find("[data-tid='foodItemTemplate']");
			
			$element.find("[data-tid='categoryName']").html(_title);
			$element.find("[data-tid='catererName']").html(foodsData.catererInfo.caterer);
			$element.find("[data-tid='catererType']").html(foodsData.catererInfo.foodType);
			$element.find("[data-tid='likeBar']").html();
			$element.find("[data-tid='commentLink']").click(function(_e) {
				
			});
			$foodItemTemplate.detach();
			
			for(var j = 0; j < foodsData.foodItems.length; j++) {
				var food = foodsData.foodItems[j];
				var $foodItem = $foodItemTemplate.clone();
				$foodItem.find("[data-tid='ratingBall']");
				$foodItem.find("[data-tid='foodName']").html(food.name);
				$foodItem.find("[data-tid='upvoteBtn']").click(function(_e) {
					
				});
				$foodItem.find("[data-tid='downvoteBtn']").click(function(_e) {
					
				});
				$foodList.append($foodItem);
			}
			
			self.$el.append($element);
		}
		
		renderType("daily", "Daily Catering");
		renderType("indian", "Indian");
		renderType("vegetarian", "Vegetarian");
	}
});