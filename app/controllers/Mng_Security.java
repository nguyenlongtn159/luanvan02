package controllers;

import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Context;

public class Mng_Security extends Security.Authenticator{
	
	@Override
	public String getUsername(Context arg0) {
		// TODO Auto-generated method stub
		String value=arg0.session().get("tag");
		if(value!=null&&value.equals("Giáo vụ")) return value;
		return null;
	}
	
	@Override
	public Result onUnauthorized(Context arg0) {
		// TODO Auto-generated method stub
		return redirect(routes.Application.login());
	}

}
