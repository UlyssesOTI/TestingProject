package ua.lviv.lgs.service;

import java.util.List;

import ua.lviv.lgs.dto.CourseDTO;
import ua.lviv.lgs.dto.GroupDTO;
import ua.lviv.lgs.dto.TestDTO;
import ua.lviv.lgs.dto.UserDTO;
import ua.lviv.lgs.entity.Courses;
import ua.lviv.lgs.entity.Users;

public interface UsersService {
	
	public final static int ITEMS_PER_PAGE = 10;
	
	public boolean add(String name, String secondname, String mail, String phone, String password, int roleId, String groupName,  String course);

//	public void add(String name, String secondname, String mail, String phone, String password);

	public boolean editMyUser(int id, String name, String secondname, String email, String phone, String password,String currentPassword,String newPassword);
	
	public void edit(int id, String name, String secondname, String email, String phone,String groupNamesArray, int[] courseNamesArray);
	
	public void changeHashSume(int usersId, String hashSume);
	
	public void changePass(int usersId, String pass);
	
	public void delete(int id);

//	public List<UserDTO> findAll();
	
	public List<UserDTO> findAllPaginated();
	
//	public List<UserDTO> findAllByRole(String roleName);
	
	public List<UserDTO> findAllByGroupId(int id);
	
	public UserDTO findById(int id);
	
	/*public List<Users> searchByNameOrSecondname(String name, String roleId);
	
	public List<Users> searchByNameOrSecondname(String name, String secondname, String roleId);
	*/
//	public List<Users> findUsersByRole(String roleId);
	
	public Integer getUserbyNameAndRoleCount(String searchString, String roleId);
	
	public List<String> getUserbyNameAndRole(String searchString, String roleId);
	
	public List<Users> getUserbyNameAndRole(String searchString, String roleId, int curentPage);

	
	public List<Courses> getCoursesByTeacherId(int teacherId);
	
	public Users getTeacherByGroupId(int groupId);
	
	public Users findUserById(int id);
	
	public String findUsersRole(int id);
	
	public boolean hasPrincipalAccessToTheGroup(int groupId, String principal);
	
	public boolean hasPrincipalAccessToViewUser(int userId, String principal);
	
	public boolean hasPrincipalAccessToEditUser(int userId, String principal);
	
	public boolean hasPrincipalAccessToCourse(int courseId, String principal);
	
	public boolean hasPrincipalAccessToViewTest(int testId, String principal);
	
	public boolean hasPrincipalAccessToTheTheme(int themeId, String principal);
	
	public boolean hasPrincipalAccessAllThemesOfCourse(int courseId, String principal);
	
	public List<Integer> findTeacherIdByResultId(String resultId);
	
	public List<UserDTO> findTeachersByCoursesIds(List<Integer> ids);
	
	public List<CourseDTO> findTeacherCourses(int teacherId);
	
	public List<GroupDTO> findUserGroupsDto(int userId);
	
	public List<TestDTO> findUsersTestDTO(int userId);
	
	public Boolean isEmailUnique(String email);
	
	public Boolean isPhoneUnique(String phone);
	
	public Users findByEmail(String email);
	
//	public List<String> allFulename(String role);
	
	public Integer findUsersIdByhashCode(String hashCode);
}
