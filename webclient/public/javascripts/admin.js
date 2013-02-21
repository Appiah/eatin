function publishAlert()
{
	alert("Publishing the menu was a success! :)");
}

function removeCaterer(rowId)
{
	var row = document.getElementById(rowId);
	row.parentElement.removeChild(row); 
}