package ua.lviv.lgs.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import ua.lviv.lgs.jsonview.Views;

public class UserDTO {
	public UserDTO() {
		super();
	}

	@JsonView(Views.Public.class)
	int id;
	@JsonView(Views.Public.class)
	String name;
	@JsonView(Views.Public.class)
	String secondname;
	@JsonView(Views.Public.class)
	String password;
	@JsonView(Views.Public.class)	
	String phone;
	@JsonView(Views.Public.class)
	String email;
	@JsonView(Views.Public.class)
	int roleId;
	@JsonView(Views.Public.class)
	String role;
	@JsonView(Views.Public.class)
	List<String> groups;
	@JsonView(Views.Public.class)
	List<CourseDTO> courses;
	
	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
	public List<CourseDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseDTO> courses) {
		this.courses = courses;
	}

	public UserDTO(int id,String name, String secondname) {
		super();
		this.id = id;
		this.setName(name);
		this.setSecondname(secondname);
	}
	public UserDTO(int id,String name, String secondname, String phone, String email, int roleId,String role,String password) {
		super();
		this.id = id;
		this.setName(name);
		this.setSecondname(secondname);
		
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.roleId = roleId;
		this.setRole(role);
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
		String tempName = name.toLowerCase();
		char firstChar = (char)(tempName.charAt(0)-32);
		StringBuilder builder = new StringBuilder(tempName.substring(1));
		builder.insert(0, firstChar);
		this.name = builder.toString();
	}
	public String getSecondname() {
		return secondname;
	}

	public void setSecondname( String secondname) {
		String tempSecondname = secondname.toLowerCase();
		char firstChar = (char)(tempSecondname.charAt(0)-32);
		StringBuilder builder = new StringBuilder(tempSecondname.substring(1));
		builder.insert(0, firstChar);
		this.secondname = builder.toString();
	}
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		String tempRoleString = role.substring(5).toLowerCase();
		this.role = tempRoleString.replace(tempRoleString.charAt(0), (char) (tempRoleString.charAt(0)-32));
	}

	
	
	
}
