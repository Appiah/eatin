Date.prototype.getMonthName = function() {
	var m = ['January','February','March','April','May','June','July',
	         'August','September','October','November','December'];
	return m[this.getMonth()];
}

Date.prototype.getDayName = function() {
	var d = ['Sunday','Monday','Tuesday','Wednesday',
	         'Thursday','Friday','Saturday'];
	return d[this.getDay()];
}

Date.prototype.format = function() {
	var d = new Date();
	var curr_date = d.getDate();
	var curr_month = d.getMonth();
	var curr_year = d.getFullYear();
	return (curr_date + "/" + curr_month + "/" + curr_year);
}