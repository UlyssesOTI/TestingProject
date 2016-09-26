package ua.lviv.lgs.dto;

import java.util.List;




public class CourseDTO {
	

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
		CourseDTO other = (CourseDTO) obj;
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
	

	String name;
	List<GroupDTO> groups;
	List<UserDTO> users;
	List<TestDTO> tests;
	List<ThemeDTO> themes;

	public List<ThemeDTO> getThemes() {
		return themes;
	}


	public void setThemes(List<ThemeDTO> themes) {
		this.themes = themes;
	}


	public CourseDTO(int id,String name) {
		super();
		this.id = id;
		this.name = name;
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

	public List<GroupDTO> getGroups() {
		return groups;
	}


	public void setGroups(List<GroupDTO> groups) {
		this.groups = groups;
	}
	


	public List<UserDTO> getUsers() {
		return users;
	}


	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}


	public List<TestDTO> getTests() {
		return tests;
	}

	public void setTests(List<TestDTO> tests) {
		this.tests = tests;
	}

	
	
	
}
