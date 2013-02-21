package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

	def index = Action {
		Ok(views.html.index())
	}

	def orderIn = Action {
		Ok(views.html.orderin_index())	
	}

	def orderInAdminMain() = Action {
		Ok(views.html.orderin_admin_main());
	}

	def orderInAdminCaterer() = Action {
		Ok(views.html.orderin_admin_caterer());
	}

	def orderInCatererInfo() = Action {
		Ok(views.html.orderin_caterer_info());
	}

	def orderInCatererMain() = Action {
		Ok(views.html.orderin_caterer_main());
	}

	def orderInCatererMenu() = Action {
		Ok(views.html.orderin_caterer_menu());
	}
}
