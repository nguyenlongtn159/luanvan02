package models;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Tag {

	private static List<Tag> tags = new LinkedList<Tag>();

	public static Finder<Long, Tag> find = new Finder<Long, Tag>(Long.class,
			Tag.class);

	public static Tag findById(Long id) {
		return find.byId(id);
	}

	@Id
	public Long id;
	@Constraints.Required
	public String name;
	@OneToMany(mappedBy = "tag")
	public List<UserAccount> user;

	public Tag() {
		// Left empty
	}

	public Tag(String name) {
		super();
		this.name = name;
	}

}