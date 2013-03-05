var TodayMenuCategories = Backbone.View.extend({
	initialize: function() {
		this.data = this.options.data;
		
		this.render();
	},
	
	render: function() {
		var $menuTypeTemplate = $($.trim(_.template($("#tCenterMenuType").html(), {})));
		var $menuButtonTemplate = $($.trim(_.template($("#tCenterMenuTypeBtn").html(), {})));
		var self = this;
		
		this.$menuList = this.$el.find("[data-tid='menuTypeList']");
		this.$menuNavList = this.$el.find("[data-tid='menuTypeNav']");
		
		var buildItem = function(_pgnum, _data, _name, _imageUrl) {
			var $menuType = $menuTypeTemplate.clone();
			var $menuButton = $menuButtonTemplate.clone();
	
			$menuButton.find("[data-tid='categoryIcon']").prop("src", _imageUrl);
			$menuButton.find("[data-tid='categoryName']").html(_name);
			$menuButton.find("[data-tid='catererName']").html(_data.catererInfo.caterer);
			$menuButton.find("[data-tid='catererType']").html(_data.catererInfo.foodType);
			$menuButton.find("[data-tid='numUpVotes']").html(_data.numLikes);
			$menuButton.find("[data-tid='numDownVotes']").html(_data.numRatings - _data.numLikes);
			
			$menuButton.click(function(_event) {
				self.$menuList.pageFlipper("flip", _pgnum);
			});
			
			// $menuButton.find("[data-tid='upvoteBtn']").click(function(_e) {
			// });
			
			// $menuButton.find("[data-tid='downvoteBtn']").click(function(_e) {
			// });
			
			new TodayFoodItems({
				el: $menuType.find("[data-tid='foodList']"),
				data: _data.foodItems
			})
			
			self.$menuNavList.append($menuButton);
			self.$menuList.append($menuType);
		};
		
		buildItem(0, this.data.daily, "Catering", "assets/images/ic-cater.png");
		buildItem(1, this.data.indian, "Indian", "assets/images/ic-indian.png");
		buildItem(2, this.data.vegetarian, "Vegetarian", "assets/images/ic-vege.png");
		
		self.$menuList.pageFlipper();
		
	}
});