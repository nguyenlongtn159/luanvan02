package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Danh_Gia;
import models.Message;
import models.UserAccount;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.avaje.ebean.Page;

@Security.Authenticated(Gv_Security.class)
public class Teachers extends Controller {

	public static final Form<UserAccount> userForm = Form
			.form(UserAccount.class);
	public static final Form<Message> msgForm = Form.form(Message.class);
	public static final Form<Danh_Gia> dgForm = Form.form(Danh_Gia.class);

	public static Result details(UserAccount user) {
		if (user == null) {
			return notFound("không tìn thấy 1" + user.name);
		}
		return ok(views.html.gvdetails.render(user));
	}

	public static Result list(Integer page, UserAccount user) {
		Page<UserAccount> hodle = UserAccount.find_sv_gv(page, user);
		return ok(views.html.hiddenlist.render(hodle, user));
	}

	public static Result reSearch() {
		return ok();
	}

	public static Result show(UserAccount name, UserAccount name1) {
		if (name1 == null)
			return notFound("Không tìm thấy 2");
		Form<UserAccount> filldedForm = userForm.fill(name1);
		return ok(views.html.gvshow.render(filldedForm, name));
	}

	public static Result save(UserAccount name) {
		Form<UserAccount> boundForm = userForm.bindFromRequest();
		if (boundForm.hasErrors()) {
			flash("error", "Please correct the form below.");
			return badRequest(views.html.gvshow.render(boundForm, name));

		} else {
			UserAccount account = boundForm.get();

			UserAccount modles = UserAccount.findById(account.id);
			modles.name = account.name;
			modles.date = account.date;
			modles.sdt = account.sdt;
			modles.chucdanh = account.chucdanh;
			modles.noicongtac = account.noicongtac;
			modles.description = account.description;
			modles.update();
		}
		flash("succes", String.format("Successfully added list"));
		return redirect(routes.Teachers.details(name));

	}

	public static Result gv_all_msg(UserAccount user) {
		List<Message> msg = UserAccount.findmsg(user);
		// if (msg.isEmpty())
		// return notFound("12345");
		return ok(views.html.gv_all_message.render(msg, user));
	}

	public static Result gv_danhgia(UserAccount user, UserAccount name) {
		List<Danh_Gia> dg = Danh_Gia.findByUser(name);
		return ok(views.html.list_danhgia.render(dg, user, name));
	}

	public static Result show_msg(UserAccount name, Message msg) {
		return ok(views.html.msg_show.render(name, msg));
	}

	public static Result newMessage(UserAccount user, String email) {
		return ok(views.html.gv_talkto_sv.render(msgForm, user,
				UserAccount.findByEmail(email)));
	}

	public static Result newDg(UserAccount user, UserAccount name) {
		return ok(views.html.dg_form.render(dgForm, user, name));
	}

	public static Result showDg(UserAccount user, UserAccount name, Danh_Gia dg) {
		if (name == null)
			return notFound("Không tìm thấy");
		Form<Danh_Gia> fillForm = dgForm.fill(dg);
		return ok(views.html.dg_form.render(fillForm, user, name));
	}

	public static Result gui(UserAccount user, UserAccount name) {
		Form<Message> boundForm = msgForm.bindFromRequest();
		if (boundForm.hasErrors()) {
			flash("error", "Please correct the form below.");
			return badRequest(views.html.gv_talkto_sv.render(boundForm, user,
					name));
		}
		Message message = boundForm.get();
		Message msg = new Message();
		msg.tieude = message.tieude;
		msg.noidung = message.noidung;
		Date today = new Date(System.currentTimeMillis());
		String ngaygui = new SimpleDateFormat("dd/MM/yyyy").format(today);
		msg.ngaygui = ngaygui;
		msg.nguoigui = user.name;
		msg.email = user.email;
		msg.save();
		List<UserAccount> list = UserAccount.findAll();
		for (UserAccount tg : list) {
			if (tg.email.equals(name.email)) {
				tg.homthu.add(0, Message.findById(msg.id));
				tg.update();
			}
		}
		return redirect(routes.Teachers.newMessage(user, name.email));
		// return ok();
	}

	public static Result save_dg(UserAccount user, UserAccount name) {
		Form<Danh_Gia> boundForm = dgForm.bindFromRequest();
		if (boundForm.hasErrors()) {
			flash("error", "Please correct the form below.");
			return badRequest(views.html.dg_form.render(boundForm, user, name));
		}
		Danh_Gia danhgia = boundForm.get();
		if (danhgia.id == null) {
			Danh_Gia dg = new Danh_Gia();
			dg.tieude = danhgia.tieude;
			List<Danh_Gia> list = Danh_Gia.findByUser(UserAccount
					.findByEmail(name.email));
			if (!list.isEmpty()) {
				for (Danh_Gia tg : list) {
					if (tg.tieude.equals(danhgia.tieude))
						return notFound("Tiêu đề đã tồn tại");
				}
			}
			dg.decription = danhgia.decription;
			if (danhgia.diem > 10)
				return notFound("Điểm đánh giá phải nhỏ hơn 10");
			dg.diem = danhgia.diem;
			Date today = new Date(System.currentTimeMillis());
			String ngaygui = new SimpleDateFormat("dd/MM/yyyy").format(today);
			dg.time = ngaygui;
			dg.nguoidanhgia = user;
			dg.user = UserAccount.findByEmail(name.email);
			dg.userid = name.email;
			dg.save();
		} else {
            Danh_Gia mn=Danh_Gia.findById(danhgia.id);
//			Danh_Gia Dg=new Danh_Gia();
//            mn.tieude = danhgia.tieude;
            mn.tieude="da thay doi";
//            Dg.decription = danhgia.decription;
//			if (danhgia.diem > 10)
//				return notFound("Điểm đánh giá phải nhỏ hơn 10");
//			Dg.diem = danhgia.diem;
//			Date today = new Date(System.currentTimeMillis());
//			String ngaygui = new SimpleDateFormat("dd/MM/yyyy").format(today);
//			Dg.time = ngaygui;
//			Dg.nguoidanhgia = user;
//			Dg.user = UserAccount.findByEmail(name.email);
//			Dg.userid = name.email;
			mn.update();
			return notFound(mn.id+"");
		}
		// List<UserAccount> list = UserAccount.findAll();
		// for (UserAccount tg : list) {
		// if (tg.email.equals(name.email)) {
		// tg.danhgia.add(0, Danh_Gia.findById(dg.id));
		// tg.update();
		// }
		// }
		// name.danhgia.add(0, Danh_Gia.findById(dg.id));
		// name.update();
		flash("succes", String.format("Successfully added list"));
		return redirect(routes.Teachers.newDg(user, name));
	}
}
