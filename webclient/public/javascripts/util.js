var Utility = {
	getRatingColor: function(_foodItem) {
		if (!_foodItem.numRatings)
			return "#ccc";
		
		var ratio = _foodItem.numLikes/_foodItem.numRatings;
		var g = Math.round((Math.min(ratio, 0.5) * 2) * 255);
		var r = Math.round((Math.min(1-ratio, 0.5) * 2) * 255);
	
		var padString = function(_str) {
			return (_str.length == 1? "0" + _str : _str);
		}
	 
		return "#" + padString(r.toString(16)) + padString(g.toString(16)) + "00";
	}
};