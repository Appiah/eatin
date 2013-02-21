var WeeklyMenu = Backbone.View.extend({
	initialize: function() {
		this.data = this.options.data;
		this.render();
	},
	
	render: function() {
		var $menuItemTemplate = $($.trim(_.template($("#tSideMenuItem").html(), {})));
		var self = this;
		
		var buildEls = function(_menu) {
			var date = new Date(_menu.date);
			var $menuItem = $menuItemTemplate.clone();

			$menuItem.find("[data-tid='day']").html(date.getDayName());
			$menuItem.find("[data-tid='date']").html(date.format());

			var foodTypeList = $menuItem.find("[data-tid='foodTypeList']");
			new WeeklyMenuItem({
				el: foodTypeList,
				data: _menu
			});
			
			foodTypeList.pageFlipper({
				startEmpty: false
			});
			
			$menuItem.find("[data-tid='categoryCater']").click(function(_e) {
				foodTypeList.pageFlipper("flip", 0);
			});
			
			$menuItem.find("[data-tid='categoryIndian']").click(function(_e) {
				foodTypeList.pageFlipper("flip", 1);
			});
			
			$menuItem.find("[data-tid='categoryVege']").click(function(_e) {
				foodTypeList.pageFlipper("flip", 2);
			});
			
			self.$el.append($menuItem);
		};
		
		for(var i = 0; i < this.data.length; i++)
			buildEls(this.data[i]);
	}
});