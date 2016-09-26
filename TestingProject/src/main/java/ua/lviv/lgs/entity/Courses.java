package ua.lviv.lgs.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Courses {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "course_id")
	private int id;
	
	@Column(name = "course_name", nullable = false, unique = true)
	private String name;
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "groups_courses", joinColumns = @JoinColumn(name = "courses_id") , inverseJoinColumns = @JoinColumn(name = "groups_id") )
	private List<Groups> groups;
	
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "course")
	private List<Tests> tests;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "users_courses", joinColumns = @JoinColumn(name = "courses_id") , inverseJoinColumns = @JoinColumn(name = "users_id") )
	private List<Users> users;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "course")
	private List<Themes> thems;
	
	public Courses() {
	}

	public Courses(String name) {
		this.name = name;
		 groups = new ArrayList<Groups>();
		 users = new ArrayList<Users>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}
	public void setGroup(Groups group) {
		groups.add(group);
	}
	

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}
	public void setUser(Users user) {
		users.add(user);
	}

	public List<Tests> getTests() {
		return tests;
	}

	public void setTests(List<Tests> tests) {
		this.tests = tests;
	}

	public List<Themes> getThems() {
		return thems;
	}

	public void setThems(List<Themes> thems) {
		this.thems = thems;
	}
	

}
