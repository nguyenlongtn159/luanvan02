package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.mvc.PathBindable;

@Entity
public class Message extends Model implements PathBindable<Message>{
	@Id
	public Long id;
	@Constraints.Required
	public String tieude;
	@Constraints.Required
	public String noidung;
	public String email;
	public String nguoigui;
	@ManyToMany(mappedBy = "homthu")
	public List<UserAccount> nguoinhan;

	public Message() {
		super();
	}

	public String ngaygui;

	public Message(String tieude, String noidung, String nguoigui,
			String ngaygui) {
		super();
		this.tieude = tieude;
		this.noidung = noidung;
		this.nguoigui = nguoigui;
		this.ngaygui = ngaygui;
	}

	public static Finder<Long, Message> finder = new Finder<Long, Message>(
			Long.class, Message.class);

	public static Message findById(Long id) {
		return finder.byId(id);
	}
	public static Message findByName(String name) {
		return finder.where().eq("tieude", name).findUnique();
	}

	@Override
	public Message bind(String key, String value) {
		// TODO Auto-generated method stub
		return findById(Long.parseLong(value));
	}

	@Override
	public String javascriptUnbind() {
		// TODO Auto-generated method stub
		return id+"";
	}

	@Override
	public String unbind(String arg0) {
		// TODO Auto-generated method stub
		return id+"";
	}

}
