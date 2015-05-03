package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Detai;
import models.Message;
import models.UserAccount;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import com.avaje.ebean.Page;

@Security.Authenticated(Sv_Security.class)
public class Students extends Controller {
	public static final Form<UserAccount> userForm = Form
			.form(UserAccount.class);
	public static final Form<Message> msgForm = Form.form(Message.class);

	public static Result details(UserAccount user) {
		if (user == null) {
			return notFound("không tìn thấy 1" + user.name);
		}
		return ok(views.html.svdetails.render(user));
	}

	public static Result show(UserAccount name, UserAccount name1) {
		if (name1 == null)
			return notFound("Không tìm thấy 2");
		Form<UserAccount> filldedForm = userForm.fill(name1);
		return ok(views.html.svshow.render(filldedForm, name));
	}

	public static Result save(UserAccount name) {
		Form<UserAccount> boundForm = userForm.bindFromRequest();
		if (boundForm.hasErrors()) {
			flash("error", "Please correct the form below.");
			return badRequest(views.html.svshow.render(boundForm, name));
		}
		UserAccount account = boundForm.get();
		if (account.id == null) {

		}
		// account.save();
		else {
			System.out.println("chan");
			UserAccount model = UserAccount.findById(account.id);
			model.name = account.name;
			model.date = account.date;
			model.sdt = account.sdt;
			model.description = account.description;
			model.update();
		}

		flash("succes", String.format("Successfully added list"));
		return redirect(routes.Students.details(name));
	}

	public static Result newAccount(UserAccount name) {
		return ok(views.html.details.render(userForm, name));
	}

	public static Result list(Integer page, UserAccount user) {
		if (!user.dieukien)
			return notFound("Bạn chưa đủ điều kiện đăng ký đề tài");
		if (!Application.dangky_check && user.detai != null
				&& user.duochuongdan != null) {
			List<Message> msg = UserAccount.findmsg(user);
//			if (msg.isEmpty())
//				return notFound("12345");
			return ok(views.html.sv_tiendo.render(msg, user));
		}
		if (!Application.dangky_check)
			return notFound("Đã ngoài thời gian đăng ký và bảo vệ đề tài!");
		Page<Detai> hodle = Detai.find(page);
		return ok(views.html.sv_detai_all.render(hodle, user));
		// return TODO;
	}

	public static Result Dangky(UserAccount user, Detai mDetai) {
		if (user.detai != null && user.detai.id == mDetai.id) {
			user.detai = null;
			user.duochuongdan = null;
			user.update();
			return ok();
		}
		if (user.detai == null || user.detai != mDetai) {
			user.detai = mDetai;
			user.duochuongdan = null;
			user.update();
		}
		// return redirect(routes.Students.list(0, user));
		return ok();
	}

	public static Result gv_list(Integer page, UserAccount user, Long mChuyenmon) {
		Page<UserAccount> hodle = UserAccount.find_gv(page, mChuyenmon);
		return ok(views.html.sv_gv_all.render(hodle, user, mChuyenmon));
		// return ok();
	}

	public static Result Dangky_gv(UserAccount user, UserAccount giangvien,
			Long mChuyenmon) {
		if (user.detai.id == giangvien.chuyenmon.id) {
			user.duochuongdan = giangvien;
			user.update();
		}
		// return redirect(routes.Students.gv_list(0,user,mChuyenmon));
		return ok();
	}

	// Hàm này bỏ
	public static Result modelsList(UserAccount name) {
		List<Message> msg = UserAccount.findmsg(name);
		return ok();
	}

	public static Result newMessage(UserAccount user) {
		return ok(views.html.sv_talkto_gv.render(msgForm, user,
				user.duochuongdan));
	}

	public static Result show_msg(UserAccount name, Message msg) {
		return ok(views.html.msg_show.render(name, msg));
	}

	public static Result gui(UserAccount user, UserAccount name) {
		Form<Message> boundForm = msgForm.bindFromRequest();
		if (boundForm.hasErrors()) {
			flash("error", "Please correct the form below.");
			return badRequest(views.html.sv_talkto_gv.render(boundForm, user,
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
		msg.email=user.email;
		msg.save();
		List<UserAccount> list = UserAccount.findAll();
		for (UserAccount tg : list) {
			if (tg.email.equals(name.email)) {
				tg.homthu.add(0, Message.findById(msg.id));
				tg.update();
			}
		}
		return redirect(routes.Students.newMessage(user));
	}
}
