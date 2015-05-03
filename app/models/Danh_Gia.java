package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.PathBindable;

@Entity
public class Danh_Gia extends Model implements PathBindable<Danh_Gia> {

	@Id
	public Long id;
	public String time;
	@Constraints.Required
	public String decription;
	@Constraints.Required
	public String tieude;
	public UserAccount nguoidanhgia;
	@ManyToOne
	public UserAccount user;
	public String userid;
	@Constraints.Required
	public int diem;

	public Danh_Gia() {
		super();
	}

	public int getDiem() {
		return diem;
	}

	public String getTieude() {
		return tieude;
	}

	public void setTieude(String tieude) {
		this.tieude = tieude;
	}

	public void setDiem(int diem) {
		this.diem = diem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public UserAccount getNguoidanhgia() {
		return nguoidanhgia;
	}

	public void setNguoidanhgia(UserAccount nguoidanhgia) {
		this.nguoidanhgia = nguoidanhgia;
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

	private Danh_Gia(String tieude, int diem, String time, String decription, UserAccount nguoidanhgia,
			UserAccount user) {
		super();
		this.time = time;
		this.decription = decription;
		this.nguoidanhgia = nguoidanhgia;
		this.user = user;
		this.tieude=tieude;
		this.diem=diem;
	}

	public static Finder<Long, Danh_Gia> finder = new Finder<Long, Danh_Gia>(
			Long.class, Danh_Gia.class);

	public static Danh_Gia findById(Long id) {
		return finder.byId(id);
	}
	public static List<Danh_Gia> findByUser(UserAccount user){
		return finder.where()
				.eq("userid", UserAccount.findByEmail(user.email).email)
				.findList();
	}
	public static List<Danh_Gia> findAll(){
		return finder.all();
	}
	public static Danh_Gia findByTD(String tieude){
		return finder.where().eq("tieude", tieude).findUnique();
	}

	@Override
	public Danh_Gia bind(String key, String value) {
		// TODO Auto-generated method stub
        return findById(Long.parseLong(value));
//		return findByTD(value);
	}

	@Override
	public String javascriptUnbind() {
		// TODO Auto-generated method stub
		return id + "";
//		return tieude;
	}

	@Override
	public String unbind(String arg0) {
		// TODO Auto-generated method stub
		return id + "";
//		return tieude;
	}
}
