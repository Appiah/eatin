var TodayFoodItems = Backbone.View.extend({
	initialize: function() {
		this.data = this.options.data;
		this.render();
	},
	
	render: function() {
		var $foodItemTemplate = $($.trim(_.template($("#tCenterFoodItem").html(), {})));
		var self = this;
		
		var buildItem = function(_data) {
			var $foodItem = $foodItemTemplate.clone();
	
			$foodItem.find("[data-tid='ratingBall']").css("background-color", Utility.getRatingColor(_data));
			$foodItem.find("[data-tid='foodName']").html(_data.name);
			$foodItem.find("[data-tid='numUpVotes']").html(_data.numLikes);
			$foodItem.find("[data-tid='numDownVotes']").html(_data.numRatings - _data.numLikes);
			
			$foodItem.find("[data-tid='upvoteBtn']").click(function(_e) {
			});
			
			$foodItem.find("[data-tid='downvoteBtn']").click(function(_e) {
			});
			
			var $allergyList = $foodItem.find("[data-tid='allergyList']");
			
			if(_data.allergies) {
				for(var i = 0; i < _data.allergies.length; i++)
					$allergyList.append("<li class='allergy'>" + _data.allergies[i] + "</li>");
			}
			
			self.$el.append($foodItem);
		};
		
		for(var i = 0; i < this.data.length; i++)
			buildItem(this.data[i]);
		
	}
});