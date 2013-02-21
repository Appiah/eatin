var TodayCommentItems = Backbone.View.extend({
	initialize: function() {
		this.data = this.options.data;
		this.render();
	},
	
	render: function() {
		var $commentItemTemplate = $($.trim(_.template($("#tCommentItem").html(), {})));
		var self = this;
		
		var buildItem = function(_data) {
			var $commentItem = $commentItemTemplate.clone();
	
			$commentItem.find("[data-tid='authorName']").html(_data.poster)
			$commentItem.find("[data-tid='postDate']").html(_data.postDate);
			$commentItem.find("[data-tid='commentBody']").html(_data.message);
			
			self.$el.append($commentItem);
		};
		
		for(var i = 0; i < this.data.length; i++)
			buildItem(this.data[i]);
	}
});