package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Message;
import models.Tag;
import models.UserAccount;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Page;

@Security.Authenticated(Mng_Security.class)
public class Managers extends Controller {
	public static final Form<UserAccount> userForm = Form
			.form(UserAccount.class);
	public static final Form<Message> msgForm = Form.form(Message.class);

	public static Result details(UserAccount user) {
		if (user == null) {
			return notFound("không tìn thấy Account: " + user.name);
		}

		return ok(views.html.localdetails.render(user));
	}

	public static Result list(Integer page, UserAccount user) {
		Page<UserAccount> hodle = UserAccount.find(page);
		return ok(views.html.list.render(hodle, user));
	}

	public static Result list_complete_user(Integer page, UserAccount user) {
		List<UserAccount> list = UserAccount.findAll();
		for (int i = 0; i < list.size(); i++) {
//			UserAccount acc = list.get(i);
			// list.get(i).setOk();
			list.get(i).setOk();
			list.get(i).update();
		}
		Page<UserAccount> hodle = UserAccount.findByOK(page);
		return ok(views.html.complete.render(hodle, user));
	}

	public static Result reSearch() {
		return ok();
	}

	public static Result show(UserAccount name, UserAccount name1) {
		if (name1 == null)
			return notFound("Không tìm thấy");
		Form<UserAccount> filldedForm = userForm.fill(name1);
		return ok(views.html.details.render(filldedForm, name));
	}

	public static Result newMessage(UserAccount user) {
		return ok(views.html.localtb.render(msgForm, user));
	}

	public static Result save(UserAccount name) {
		Form<UserAccount> boundForm = userForm.bindFromRequest();
		if (boundForm.hasErrors()) {
			flash("error", "Please correct the form below.");
			return badRequest(views.html.details.render(boundForm, name));
		}
		UserAccount account = boundForm.get();
		Tag value;
		if (account.tag.id != null) {
			value = Tag.findById(account.tag.id);
			account.tag = value;
			// account.chucvu = account.tag.id;
			account.chucvu = value.id;
		}
		if (account.id == null) {
			UserAccount user = new UserAccount();
			user.name = account.name;
			user.email = account.email;
			user.password = account.password;
			user.tag = account.tag;
			user.date = account.date;
			user.sdt = account.sdt;
			user.chucdanh = account.chucdanh;
			user.noicongtac = account.noicongtac;
			user.description = account.description;
			user.dieukien = account.dieukien;
			user.save();
		} else {
			UserAccount modles = UserAccount.findById(account.id);
			modles.name = account.name;
			modles.email = account.email;
			modles.password = account.password;
			modles.tag = account.tag;
			modles.date = account.date;
			modles.sdt = account.sdt;
			modles.chucdanh = account.chucdanh;
			modles.noicongtac = account.noicongtac;
			modles.description = account.description;
			modles.dieukien = account.dieukien;
			modles.update();
		}
		flash("succes", String.format("Successfully added list"));
		return redirect(routes.Managers.list(0, name));
	}

	public static Result newAccount(UserAccount name) {
		return ok(views.html.details.render(userForm, name));
	}

	public static Result delete(String email, UserAccount user) {
		final UserAccount user1 = UserAccount.findByEmail(email);
		if (user1 == null) {
			return notFound(String.format("User %s does not exists.", email));
		}
		user1.delete();
		return redirect(routes.Managers.list(0, user));
	}

	public static Result Start() {
		if (Application.dangky_check)
			Application.setDangky(false);
		else
			Application.setDangky(true);
		return ok();
	}

	public static Result gui(UserAccount user) {
		Form<Message> boundForm = msgForm.bindFromRequest();
		if (boundForm.hasErrors()) {
			flash("error", "Please correct the form below.");
			return badRequest(views.html.localtb.render(msgForm, user));
		}
		Message message = boundForm.get();
		Message msg = new Message();
		msg.tieude = message.tieude;
		msg.noidung = message.noidung;
		Date today = new Date(System.currentTimeMillis());
		String ngaygui = new SimpleDateFormat("dd/MM/yyyy").format(today);
		msg.ngaygui = ngaygui;
		msg.nguoigui = "Đào tạo";
		msg.email = user.email;
		msg.save();
		List<UserAccount> list = UserAccount.findAll();
		for (UserAccount tg : list) {
			tg.homthu.add(0, Message.findById(msg.id));
			tg.update();
		}
		return redirect(routes.Managers.newMessage(user));
	}

}
