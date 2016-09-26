package ua.lviv.lgs.entity;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

import ua.lviv.lgs.jsonview.Views;

@Entity
public class Groups {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "group_id")
	@JsonView(Views.Public.class)
	private int id;
	
	@Column(name = "group_name", nullable = false, unique = true)
	@JsonView(Views.Public.class)
	private String name;

	@Column(name = "group_teacherId", nullable = true, unique = false)
	private Integer teacherId;
	
	@Column(name = "course_time", nullable = true)
	@Temporal(TemporalType.TIME)
	private Date time;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "users_groups", joinColumns = @JoinColumn(name = "groups_id") , inverseJoinColumns = @JoinColumn(name = "users_id") )
	private List<Users> users;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "groups_courses", joinColumns = @JoinColumn(name = "groups_id") , inverseJoinColumns = @JoinColumn(name = "courses_id") )
	private List<Courses> courses;

	public Groups() {
	}

	public Groups(String name, Courses course) {
		this.name = name;
		courses = new ArrayList<Courses>();
		courses.add(course);
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

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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

	public List<Courses> getCourses() {
		return courses;
	}

	public void setCourse(Courses course) {
		courses.add(course);
	}

	public void setCourses(List<Courses> courses) {
		this.courses = courses;
	}
}
