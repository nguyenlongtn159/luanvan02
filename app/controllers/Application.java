package controllers;

import static play.data.Form.form;

//import com.sun.org.apache.xpath.internal.operations.Bool;

import models.UserAccount;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	public static boolean dangky_check = false;

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static class Login {
		public String email;
		public String password;
	}

	public static Result login() {
		return ok(views.html.login.render(form(Login.class)));
	}
	public static Result error() {
		return ok(views.html.error.render());
	}
	public static Result logout() {

		 session().clear();
	return ok(views.html.logout.render());
		}


	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		String email = loginForm.get().email;
		String password = loginForm.get().password;

		session().clear();
		UserAccount user = UserAccount.authenticate(email, password);
		if (user == null) {
			
			return ok(views.html.error.render());
		} else {
			String tag = user.tag.name;
			session("email", email);
			session("name", user.name);
			session("tag", tag);
			switch (tag) {
			case "Sinh viên": {
				return redirect(routes.Students.details(user));
			}
			case "Giảng viên": {
				return redirect(routes.Teachers.details(user));
			}
			case "Giáo vụ": {
				return redirect(routes.Managers.details(user));
			}
			}
		}
		return TODO;
	}

	public static void setDangky(boolean value) {
		dangky_check = value;
	}
}
