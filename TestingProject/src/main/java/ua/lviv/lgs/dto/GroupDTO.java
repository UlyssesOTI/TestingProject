package ua.lviv.lgs.dto;

import java.util.Date;
import java.util.List;

import ua.lviv.lgs.entity.Courses;

public class GroupDTO {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		GroupDTO other = (GroupDTO) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	private int id;
	
	private String name;
	
	private int teacherId;
	
	private Date time;
	
	
	private List<UserDTO> users;
	
	private List<Courses> courses;

	public GroupDTO(int id, String name){
		this.id = id;
		this.name = name;
	}
	public GroupDTO(int id, String name, List<Courses> courses) {
		super();
		this.id = id;
		this.name = name;
		this.courses = courses;
	}
	public GroupDTO(int id, String name, int teacherId,List<Courses> courses,List<UserDTO> users) {
		super();
		this.id = id;
		this.name = name;
		this.teacherId = teacherId;
		this.courses = courses;
		this.users = users;
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

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public List<Courses> getCourses() {
		return courses;
	}

	public void setCourses(List<Courses> courses) {
		this.courses = courses;
	}
	
	
}
