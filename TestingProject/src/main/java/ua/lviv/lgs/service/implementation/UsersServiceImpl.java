package ua.lviv.lgs.service.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.lviv.lgs.dao.CoursesDao;
import ua.lviv.lgs.dao.GroupsDao;
import ua.lviv.lgs.dao.RolesDao;
import ua.lviv.lgs.dao.TestsDao;
import ua.lviv.lgs.dao.ThemesDao;
import ua.lviv.lgs.dao.UsersDao;
import ua.lviv.lgs.dto.CourseDTO;
import ua.lviv.lgs.dto.GroupDTO;
import ua.lviv.lgs.dto.TestDTO;
import ua.lviv.lgs.dto.UserDTO;
import ua.lviv.lgs.entity.Courses;
import ua.lviv.lgs.entity.Groups;
import ua.lviv.lgs.entity.Tests;
import ua.lviv.lgs.entity.Themes;
import ua.lviv.lgs.entity.Users;
import ua.lviv.lgs.service.UsersService;

@Service("userDetailsService")
public class UsersServiceImpl implements UsersService, UserDetailsService {

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private RolesDao rolesDao;

	@Autowired
	private GroupsDao groupsDao;

	@Autowired
	private TestsDao testsDao;
	@Autowired
	private ThemesDao themesDao;

	@Autowired
	private CoursesDao coursesDao;

	private static Logger log = Logger.getLogger(UsersServiceImpl.class);
	
	@Transactional
	public boolean add(String name, String secondname, String mail, String phone, String password, int roleId,
			String groupId, String course) {
		groupId = groupId.trim();
		course = course.trim();
		List<Groups> group = new ArrayList<Groups>();
		if (groupId.length() >= 1) {
			String[] tempGroup = groupId.split(",");
			for (String string : tempGroup) {
				if(!string.trim().isEmpty()) {
					Groups gDao = groupsDao.findOne(Integer.parseInt(string));
					group.add(gDao);
				}}
		}

		BCryptPasswordEncoder encoder;
		Users user;
		try {
			encoder = new BCryptPasswordEncoder();
			user = new Users(name, secondname, mail, phone, encoder.encode(password), rolesDao.findOne(roleId));
			user.setRegistrationDate(new Date());
			user.setGroups(group);
			if (course.length() >= 1) {
				Courses searcherdOne = coursesDao.findOne(Integer.parseInt(course));
				user.setCourse(searcherdOne);
			}
			usersDao.save(user);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean editMyUser(int id, String name, String secondname, String email, String phone, String password,
			String currentPassword, String newPassword) {
		Users user = usersDao.findOne(id);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!newPassword.equalsIgnoreCase("") && !currentPassword.equalsIgnoreCase("")) {
			if (encoder.matches(currentPassword, user.getPassword())) {
				password = encoder.encode(newPassword);
				user.setPassword(password);
			} else {
				return false;
			}
		}
		if (!name.equalsIgnoreCase("")) {
			user.setName(name);
		}
		if (!secondname.equalsIgnoreCase("")) {
			user.setSecondname(secondname);
		}
		if (!email.equalsIgnoreCase("")) {
			user.setEmail(email);
		}
		if (!phone.equalsIgnoreCase("")) {
			user.setPhone(phone);
		}
		usersDao.save(user);
		return true;
	}

	@Transactional
	public void edit(int id, String name, String secondname, String email, String phone, String groupsId,
			int[] courseNamesArray) {
		Users user = usersDao.findOne(id);
		groupsId = groupsId.trim();

		List<Groups> group = new ArrayList<Groups>();
		if (groupsId.length() > 0) {
			String[] tempGroup = groupsId.split(",");
			for (String string : tempGroup) {
				Groups gDao = groupsDao.findOne(Integer.parseInt(string));
				group.add(gDao);
			}
		}

		List<Courses> course = new ArrayList<Courses>();
		if (courseNamesArray.length > 0) {
			for (int string : courseNamesArray) {
				Courses cDao = coursesDao.findOne(string);
				if (!course.contains(cDao)) {
					course.add(cDao);
				}
			}
		}

		if (!name.equalsIgnoreCase("")) {
			user.setName(name);
		}
		if (!secondname.equalsIgnoreCase("")) {
			user.setSecondname(secondname);
		}
		if (!email.equalsIgnoreCase("")) {
			user.setEmail(email);
		}
		if (!phone.equalsIgnoreCase("")) {
			user.setPhone(phone);
		}
		if (group != null) {
			user.setGroups(group);
		}
		if (course != null) {
			user.setCourses(course);
		}
		usersDao.save(user);

	}

	@Transactional
	public void delete(int id) {
		if (id != 0) {
			log.info("deleted user with id="+id);
			usersDao.delete(id);
		}
	}

	@Transactional
	public UserDTO userToDTO(Users user) {
		UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSecondname(), user.getPhone(),
				user.getEmail(), user.getRole().getId(), user.getRole().getName(), user.getPassword());
		return userDTO;

	}

//	@Transactional
//	public List<UserDTO> findAll() {
//		List<Users> users = usersDao.findAll();
//		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
//		for (Users user : users) {
//			UserDTO userDTO = userToDTO(user);
//			usersDTO.add(userDTO);
//		}
//		return usersDTO;
//	}
	
	@Transactional
	public List<UserDTO> findAllPaginated() {
		List<Users> users = usersDao.findUsersByRole228("", "", "%", new PageRequest(0, ITEMS_PER_PAGE));
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		for (Users user : users) {
			UserDTO userDTO = userToDTO(user);
			usersDTO.add(userDTO);
		}
		return usersDTO;
	}

	@Transactional
	public UserDTO findById(int id) {
		Users user = usersDao.findOne(id);
		UserDTO userDTO = null;
		try {
			userDTO = userToDTO(user);
			return userDTO;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Users user = null;
		try {
			user = usersDao.findByLogin(login);
		} catch (NoResultException e) {
			return null;
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
		return new User(String.valueOf(user.getId()), user.getPassword(), authorities);
	}

	/*@Transactional
	public List<Users> searchByNameOrSecondname(String name, String roleId) {
		return usersDao.findUsersByRole(name, roleId);
	}

	@Transactional
	public List<Users> searchByNameOrSecondname(String name, String surname, String roleId) {
		return usersDao.findUsersByRole(name, surname, roleId);
	}*/

//	@Transactional
//	public List<UserDTO> findAllByRole(String roleName) {
//		if (roleName != null) { // need to be fixed
//			List<Users> users = usersDao.findByRole(roleName);
//			List<UserDTO> usersDTO = new ArrayList<UserDTO>();
//			for (Users user : users) {
//				UserDTO userDTO = userToDTO(user);
//				usersDTO.add(userDTO);
//			}
//			return usersDTO;
//		}
//		return null;
//	}

//	@Transactional
//	public List<Users> findUsersByRole(String roleId) {
//		return usersDao.findUsersByRole(Integer.parseInt(roleId));
//	}

	@Transactional
	public Integer getUserbyNameAndRoleCount(String searchString, String roleId) {
		String splitedSearchString[];
		
		int usersList;
		
		splitedSearchString = searchString.split(" ");
		if (splitedSearchString.length > 1) {
			String searchWord1 = "";
			String searchWord2 = "";
			for (String tempString : splitedSearchString) {
				if (tempString.trim().length() != 0) {
					searchWord1 = searchWord2;
					searchWord2 = tempString.trim();

				}
			}
			usersList = usersDao.findUsersCountByRole(searchWord1, searchWord2, roleId);
		} else {
			usersList = usersDao.findUsersCountByRole(searchString.trim(), roleId);
		}

		return usersList;
	}
	
	@Transactional
	public List<Users> getUserbyNameAndRole(String searchString, String roleId, int curentPage) {
		String splitedSearchString[];

		splitedSearchString = searchString.split(" ");
		List<Users> usersList = new ArrayList<Users>(); // was null
		if (splitedSearchString.length > 1) {
			String searchWord1 = "";
			String searchWord2 = "";
			for (String tempString : splitedSearchString) {
				if (tempString.trim().length() != 0) {
					searchWord1 = searchWord2;
					searchWord2 = tempString.trim();
				}
			}
			usersList = usersDao.findUsersByRole228(searchWord1, searchWord2, roleId, new PageRequest(curentPage, ITEMS_PER_PAGE));
		} else {
			usersList = usersDao.findUsersByRole228(searchString.trim(), roleId, new PageRequest(curentPage, ITEMS_PER_PAGE));
		}
		return usersList;
	}

	@Transactional
	public List<UserDTO> findAllByGroupId(int groupId) {
		List<Users> students = usersDao.findUsersByGroupId(groupId);
		List<UserDTO> studDTO = new ArrayList<UserDTO>();
		for (Users user : students) {
			UserDTO userDTO = userToDTO(user);
			studDTO.add(userDTO);
		}
		return studDTO;
	}

	@Transactional
	public List<Courses> getCoursesByTeacherId(int teacherId) {
		return testsDao.findCoursesFromUsers(teacherId);
	}

	@Transactional
	public Users getTeacherByGroupId(int groupId) {
		return testsDao.findTeacherByGroupId(groupId);
	}

	@Transactional
	public Users findUserById(int userId) {
		Users user = usersDao.findOne(userId);
		// String role = user.getRole().getName(); // was
		Hibernate.initialize(user.getRole().getName()); // now
		return user;
	}

	@Transactional
	public String findUsersRole(int userId) {
		return usersDao.findOne(userId).getRole().getName();
	}

	@Transactional
	public boolean hasPrincipalAccessToTheGroup(int groupId, String principal) {
		boolean access = false;
		Groups group = groupsDao.findOne(groupId);
		if (principal.equalsIgnoreCase("admin")) {
			access = true;
		} else if (usersDao.findOne(Integer.parseInt(principal)).getRole().getId() == 1) {
			access = true;
		} else {
			int userId = Integer.parseInt(principal);
			if (usersDao.findOne(Integer.parseInt(principal)).getRole().getId() == 2) {
				if (group.getCourses() != null) {
					List<Integer> userList = usersDao.findUserCoursesId(Integer.parseInt(principal));
					for (Courses course : group.getCourses()) {
						if (userList.contains(course.getId())) {
							access = true;
							break;
						} else {
							access = false;
						}
					}
				} else {
					access = true;
				}
			} else {
				List<Users> users = group.getUsers();
				if (users != null) {
					for (Users user : users) {
						if (user.getId() == userId) {
							access = true;
						}
					}
				}
			}
		}
		return access;

	}

	@Transactional
	public boolean hasPrincipalAccessToTheTheme(int themeId, String principal) {
		boolean access = false;
		Themes theme = themesDao.findOne(themeId);
		if (principal.equalsIgnoreCase("admin")) {
			access = true;
		} else if (usersDao.findOne(Integer.parseInt(principal)).getRole().getId() == 1) {
			access = true;
		} else {
			List<Integer> userList = usersDao.findUserCoursesId(Integer.parseInt(principal));
			if (userList.contains(theme.getCourse().getId())) {
				access = true;
				return access;
			} else {
				access = false;
			}
		}
		return access;

	}
	@Transactional
	public boolean hasPrincipalAccessAllThemesOfCourse(int courseId, String principal) {
		boolean access = false;
		Courses course = coursesDao.findOne(courseId);
		if (principal.equalsIgnoreCase("admin")) {
			access = true;
		} else if (usersDao.findOne(Integer.parseInt(principal)).getRole().getId() == 1) {
			access = true;
		} else {
			List<Integer> userList = usersDao.findUserCoursesId(Integer.parseInt(principal));
			if (userList.contains(course.getId())) {
				access = true;
				return access;
			} else {
				access = false;
			}
		}
		return access;

	}

	@Transactional
	public boolean hasPrincipalAccessToViewUser(int userId, String principal) {
		boolean access = true;
		int viewerRoleId = usersDao.findOne(Integer.parseInt(principal)).getRole().getId();
		if (viewerRoleId == 3) {
			if (userId != Integer.parseInt(principal)) {
				if (viewerRoleId > findById(userId).getRoleId()) {
					access = false;
				} else {
					Users user = usersDao.findOne(userId);
					List<Groups> groups = user.getGroups();
					List<Integer> usersId;
					if (groups != null) {
						for (Groups group : groups) {
							usersId = usersDao.findUsersIdByGroupId(group.getId());
							if (usersId.contains(Integer.parseInt(principal))) {
								access = true;
								break;
							} else {
								access = false;
							}

						}
					}
				}
			}
		}
		return access;
	}

	@Transactional
	public boolean hasPrincipalAccessToEditUser(int userId, String principal) {
		boolean userRightsToEdit = false;
		int principalRole = findUserById(Integer.parseInt(principal)).getRole().getId();
		int userRole = findUserById(userId).getRole().getId();
		if (userRole > principalRole) {
			userRightsToEdit = true;
		}
		return userRightsToEdit;
	}

	@Transactional
	public boolean hasPrincipalAccessToCourse(int courseId, String principal) {
		boolean access = false;
		if (!(principal.equalsIgnoreCase("admin"))) {
			// if
			// (!(findById(Integer.parseInt(principal)).getRole().equalsIgnoreCase("manager")))
			// {
			if (!(findUserById(Integer.parseInt(principal)).getRole().getId() == 1)) {
				List<Courses> courses = coursesDao.findByUserId(Integer.parseInt(principal));
				if (courses != null) {
					for (Courses course : courses) {
						if (course.getId() == courseId) {
							access = true;
						}
					}
				}
			} else {
				access = true;
			}
		} else {
			access = true;
		}
		return access;

	}

	@Transactional
	public boolean hasPrincipalAccessToViewTest(int testId, String principal) {
		boolean access = true;
		Users user = usersDao.findOne(Integer.parseInt(principal));
		if (user.getRole().getId() == 2) {
			Tests test = testsDao.findOne(testId);
			int testCourseId = test.getCourse().getId();
			List<Courses> courses = user.getCourses();
			for (Courses course : courses) {
				if (course.getId() == testCourseId) {
					access = true;
					break;
				} else {
					access = false;
				}
			}
		}
		return access;
	}

	@Transactional
	public List<Integer> findTeacherIdByResultId(String resultId) {
		List<Integer> teacheIds = null;
		if (!resultId.isEmpty()) {
			try {
				int id = Integer.parseInt(resultId);
				teacheIds = usersDao.findTeacherIdByResultId(id);
			} catch (NumberFormatException e) {
			}
		}
		return teacheIds;
	}

	@Transactional
	public List<UserDTO> findTeachersByCoursesIds(List<Integer> idsList) {
		List<Users> teachers = new ArrayList<Users>();
		if (!idsList.isEmpty()) {
			teachers = usersDao.findTeachersByCoursesIds(idsList);
		}
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		for (Users user : teachers) {
			UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSecondname(), user.getPhone(),
					user.getEmail(), user.getRole().getId(), user.getRole().getName(), user.getPassword());
			usersDTO.add(userDTO);
		}
		return usersDTO;
	}

	public void add(String name, String secondname, String mail, String phone, String password) {
		Users u = new Users();
		u.setName(name);
		u.setSecondname(secondname);
		u.setEmail(mail);
		u.setPhone(phone);
		u.setPassword(password);
		usersDao.save(u);
	}

	@Transactional
	public List<CourseDTO> findTeacherCourses(int teacherId) {
		List<Courses> teacherCourses = findUserById(teacherId).getCourses();
		List<CourseDTO> teacherDtoCourses = new ArrayList<CourseDTO>();
		for (Courses courses : teacherCourses) {
			CourseDTO dtoCourse = new CourseDTO(courses.getId(), courses.getName());
			teacherDtoCourses.add(dtoCourse);
		}
		return teacherDtoCourses;
	}

	@Transactional
	public List<GroupDTO> findUserGroupsDto(int userId) {
		Users user = usersDao.findOne(userId);
		List<Groups> userGroups = user.getGroups();
		List<GroupDTO> userGroupsDto = new ArrayList<GroupDTO>();
		for (Groups group : userGroups) {
			GroupDTO groupDTO = new GroupDTO(group.getId(), group.getName());
			userGroupsDto.add(groupDTO);
		}
		return userGroupsDto;
	}

	@Transactional
	public List<TestDTO> findUsersTestDTO(int userId) {
		Users student = usersDao.findOne(userId);
		List<Tests> studentAvailableTests = student.getTests();
		List<TestDTO> studentTests = new ArrayList<TestDTO>();
		for (Tests test : studentAvailableTests) {
			TestDTO testDTO = new TestDTO(test.getId(), test.getName());
			studentTests.add(testDTO);
		}
		return studentTests;
	}

	@Transactional
	public Boolean isEmailUnique(String email) {
		Integer userId = usersDao.findUsersIdByEmail(email);
		if (userId == null) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public Boolean isPhoneUnique(String phone) {
		Integer userId = usersDao.findUsersIdByPhone(phone);
		if (userId == null) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public List<String> getUserbyNameAndRole(String searchString, String roleId) {
		String splitedSearchString[];
		List<String> usersList = new ArrayList<String>();
		splitedSearchString = searchString.split(" ");
		if (splitedSearchString.length > 1) {
			String searchWord1 = "";
			String searchWord2 = "";
			for (String tempString : splitedSearchString) {
				if (tempString.trim().length() != 0) {
					searchWord1 = searchWord2;
					searchWord2 = tempString.trim();
				}
			}
			usersList = usersDao.findUsersByRole(searchWord1, searchWord2, roleId);
		} else {
			usersList = usersDao.findUsersByRole(searchString.trim(), roleId);
		}
		return usersList;
	}

	@Transactional
	public Users findByEmail(String email) {
		return usersDao.findByEmail(email);
	}

	@Transactional
	public Integer findUsersIdByhashCode(String hashCode) {
		return usersDao.findUsersIdByHashSum(hashCode);
	}

	@Transactional
	public void changeHashSume(int usersId, String hashSume) {
		Users user = usersDao.findOne(usersId);
		user.setHashSume(hashSume);
		usersDao.save(user);
	}

	@Transactional
	public void changePass(int usersId, String pass) {
		Users user = usersDao.findOne(usersId);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(pass));
		usersDao.save(user);
	}

/*	public List<String> allFulename(String role) {
		return usersDao.findAllFulname(role);
	}*/
	
	

}
