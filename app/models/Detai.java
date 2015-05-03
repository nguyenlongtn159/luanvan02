package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.avaje.ebean.Page;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.mvc.PathBindable;

@Entity
public class Detai extends Model implements PathBindable<Detai> {

	@Id
	public Long id;
	@Constraints.Required
	public String name;
	@OneToMany(mappedBy = "detai")
	public List<UserAccount> Students;
	@OneToMany(mappedBy = "chuyenmon")
	public List<UserAccount> Teacher;

	public Detai(String name) {
		super();
		this.name = name;
	}

	public Detai() {
		super();
	}

	public static Finder<Long, Detai> finder = new Finder<Long, Detai>(
			Long.class, Detai.class);

	public static List<Detai> findAll() {
		return finder.all();
	}

	public static Detai findByName(String name) {
		return finder.where().eq("name", name).findUnique();
	}

	public static Detai findById(Long id) {
		return finder.byId(id);
	}

	public static Page<Detai> find(int page) {
		return finder.where().orderBy("id asc").findPagingList(10)
				.setFetchAhead(false).getPage(page);

	}

	@Override
	public Detai bind(String key, String value) {
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

}
