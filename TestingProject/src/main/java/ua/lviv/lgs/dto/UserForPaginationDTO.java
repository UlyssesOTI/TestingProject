package ua.lviv.lgs.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import ua.lviv.lgs.entity.Users;
import ua.lviv.lgs.jsonview.Views;

public class UserForPaginationDTO {
	
	@JsonView(Views.Public.class)
	private int paginationPageCount;
	
	@JsonView(Views.Public.class)
	private List<Users> users;

	public UserForPaginationDTO(int listSize, int usersPerPage, List<Users> users) {
		super();
		if (listSize % usersPerPage == 0) {
			this.paginationPageCount  = listSize / usersPerPage;
		}else {
			this.paginationPageCount  = listSize / usersPerPage + 1;
		}
		this.users = users;
		
	}

	public int getPaginationPageCount() {
		return paginationPageCount;
	}

	public void setPaginationPageCount(int paginationPageCount) {
		this.paginationPageCount = paginationPageCount;
	}

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}
}
