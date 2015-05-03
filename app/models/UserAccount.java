package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.PathBindable;

import com.avaje.ebean.Page;

import controllers.Application;

@Entity
public class UserAccount extends Model implements PathBindable<UserAccount> {
	@Id
	public Long id;
	@Constraints.Required
	public String email = "truongcan93@yahoo.com.vn";
	@Constraints.Required
	public String password = "12020400";
	public String description;
	@Constraints.Required
	public String name;
	@OneToMany(mappedBy = "user")
	public List<Danh_Gia> danhgia = new ArrayList<Danh_Gia>();
	@ManyToOne
	public Tag tag;
	public boolean ok = false;
	public int khoa;
	public Long chucvu;
	public boolean dieukien = false;
	@ManyToMany
	public List<Message> homthu = new ArrayList<Message>();
	@ManyToOne
	public Detai detai;
	@ManyToOne
	public Detai chuyenmon;
	public Long cmon;
	@OneToMany(mappedBy = "duochuongdan")
	public List<UserAccount> huongdan = new ArrayList<UserAccount>();
	@ManyToOne
	public UserAccount duochuongdan;

	// them-17-4
	@Formats.DateTime(pattern = "dd/MM/yyyy")
	public Date date;

	public int sdt;

	public String chucdanh;
	public String noicongtac;

	public UserAccount() {
		super();
	}

	public UserAccount(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public static Finder<Long, UserAccount> finder = new Finder<Long, UserAccount>(
			Long.class, UserAccount.class);

	public static UserAccount authenticate(String email, String password) {
		return finder.where().eq("email", email).eq("password", password)
				.findUnique();
	}

	public static List<UserAccount> findAll() {
		return finder.all();
	}

	public static UserAccount findByName(String name) {
		return finder.where().eq("name", name).findUnique();
	}

	public static UserAccount findByEmail(String email) {
		return finder.where().eq("email", email).findUnique();
	}

	@Override
	public UserAccount bind(String key, String value) {
		// TODO Auto-generated method stub
		return findByName(value);
	}

	@Override
	public String unbind(String arg0) {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String javascriptUnbind() {
		// TODO Auto-generated method stub
		return name;
	}

	public static Page<UserAccount> find(int page) {
		return finder.where().orderBy("id asc").findPagingList(10)
				.setFetchAhead(false).getPage(page);

	}

	public static Page<UserAccount> findByOK(int page) {
		return finder.where().eq("ok", true + "").findPagingList(10)
				.setFetchAhead(false).getPage(page);
	}

	public void setOk() {
		List<Danh_Gia> list = Danh_Gia.findByUser(this);
		int tong = 0;
		if (list.size() >= 5) {
			for (Danh_Gia dg : list) {
				tong += dg.diem;
			}
			tong = tong / (list.size());
			if (tong >= 5)
				this.ok = true;
			else
				this.ok = false;
		} else {
			this.ok = false;
		}
//		this.ok=true;
	}

	public boolean getOK() {
		return this.ok;
	}

	public static UserAccount findById(Long id) {
		return finder.byId(id);
	}

	public static Page<UserAccount> find_gv(int page, Long mChuyenmon) {
		return finder.where().eq("cmon", mChuyenmon).orderBy("id asc")
				.findPagingList(10).setFetchAhead(false).getPage(page);
	}

	public static Page<UserAccount> find_sv_gv(int page, UserAccount user) {
		return finder.where().eq("duochuongdan", user).orderBy("id asc")
				.findPagingList(10).setFetchAhead(false).getPage(page);
	}

	public boolean getCheck_dangky() {
		return Application.dangky_check;
	}

	public static List<Message> findmsg(UserAccount user) {
		return user.homthu;
	}

	public static List<Danh_Gia> finddg(UserAccount user) {
		return user.danhgia;
	}

	public boolean getCheck() {
		return Application.dangky_check;
	}

}
