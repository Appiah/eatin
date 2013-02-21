var TodayMenuCategories = Backbone.View.extend({
	initialize: function() {
		this.data = this.options.data;
		this.render();
	},
	
	render: function() {
		var $menuTypeTemplate = $($.trim(_.template($("#tCenterMenuType").html(), {})));
		var self = this;
		
		var buildItem = function(_data, _name, _imageUrl) {
			var $menuType = $menuTypeTemplate.clone();
	
			$menuType.find("[data-tid='categoryIcon']").prop("src", _imageUrl);
			$menuType.find("[data-tid='categoryName']").html(_name);
			$menuType.find("[data-tid='catererName']").html(_data.catererInfo.caterer);
			$menuType.find("[data-tid='catererType']").html(_data.catererInfo.foodType);
			$menuType.find("[data-tid='numUpVotes']").html(_data.numLikes);
			$menuType.find("[data-tid='numDownVotes']").html(_data.numRatings - _data.numLikes);
			
			$menuType.find("[data-tid='upvoteBtn']").click(function(_e) {
			});
			
			$menuType.find("[data-tid='downvoteBtn']").click(function(_e) {
			});
			
			new TodayFoodItems({
				el: $menuType.find("[data-tid='foodList']"),
				data: _data.foodItems
			})
			
			self.$el.append($menuType);
		};
		
		buildItem(this.data.daily, "Catering", "assets/images/ic-cater.png");
		buildItem(this.data.indian, "Indian", "assets/images/ic-indian.png");
		buildItem(this.data.vegetarian, "Vegetarian", "assets/images/ic-vege.png");
	}
});