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
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;

import ua.lviv.lgs.jsonview.Views;

@Entity
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	@JsonView(Views.Public.class)
	private int id;
	
	@Column(name = "user_name", nullable = false)
	@JsonView(Views.Public.class)
	private String name;
	
	@Column(name = "user_secondname", nullable = false)
	@JsonView(Views.Public.class)
	private String secondname;
	
	@Column(name = "user_email", nullable = false, unique = true)
	@JsonView(Views.Public.class)
	private String email;
	
	@Column(name = "user_phone", nullable = false, unique = true)
	@JsonView(Views.Public.class)
	private String phone;
	
	@Column(name = "user_password", nullable = false)
	private String password;
	
	@Column(name = "user_registration_date", nullable = true)
	private Date registrationDate;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JsonView(Views.Public.class)
	private Roles role;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "users_groups", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "groups_id"))
	private List<Groups> groups;
	
	@ManyToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@JoinTable(name="users_courses", joinColumns=@JoinColumn(name="users_id"), inverseJoinColumns=@JoinColumn(name="courses_id"))
	private List<Courses>courses;
	
	@ManyToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@JoinTable(name="users_tests", joinColumns=@JoinColumn(name="users_id"), inverseJoinColumns=@JoinColumn(name="tests_id"))
	@Column(name = "user_TestId", nullable = true)
	private List<Tests> tests;
	
	@Column(name = "user_hashSume", nullable = true)
	private String hashSume;
	
	public List<Tests> getTests() {
		return tests;
	}

	public void setTests(List<Tests> testId) {
		this.tests = testId;
	}

	public Users() {
	}

	public Users(String name, String secondname, String email, String phone, String password, Roles role) {
		this.name = name;
		this.secondname = secondname;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.role = role;
		courses = new ArrayList<Courses>();
		tests = new ArrayList<Tests>();
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

	public String getSecondname() {
		return secondname;
	}

	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
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
	
	public void setTest(Tests test) {
		tests.add(test);
	}

	public List<Courses> getCourses() {
		return courses;
	}

	public void setCourses(List<Courses> courses) {
		this.courses = courses;
	}
	public void setCourse(Courses course) {
		courses.add(course);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((secondname == null) ? 0 : secondname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (secondname == null) {
			if (other.secondname != null)
				return false;
		} else if (!secondname.equals(other.secondname))
			return false;
		return true;
	}

	public String getHashSume() {
		return hashSume;
	}

	public void setHashSume(String hashSume) {
		this.hashSume = hashSume;
	}
	
	

}
