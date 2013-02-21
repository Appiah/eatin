var WeeklyMenu = Backbone.View.extend({
	initialize: function() {
		this.data = this.options.data;
		this.render();
	},
	
	render: function() {
		var $menuItemTemplate = $($.trim(_.template($("#tSideMenuItem").html(), {})));
		
		for(var i = 0; i < this.data.length; i++) {
			var menu = this.data[i]
			var date = new Date(menu.date);
			var $menuItem = $menuItemTemplate.clone();

			$menuItem.find("[data-tid='day']").html(date.getDayName());
			$menuItem.find("[data-tid='date']").html(date.format());

			new WeeklyMenuItem({
				el: $menuItem.find("[data-tid='foodTypeList']"),
				data: menu
			});
			
			this.$el.append($menuItem);
		}
	}
});