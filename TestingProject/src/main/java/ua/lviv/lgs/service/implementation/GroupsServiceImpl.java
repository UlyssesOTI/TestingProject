package ua.lviv.lgs.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.lviv.lgs.controller.GroupController;
import ua.lviv.lgs.dao.CoursesDao;
import ua.lviv.lgs.dao.GroupsDao;
import ua.lviv.lgs.dao.UsersDao;
import ua.lviv.lgs.dto.CourseDTO;
import ua.lviv.lgs.dto.GroupDTO;
import ua.lviv.lgs.dto.UserDTO;
import ua.lviv.lgs.entity.Courses;
import ua.lviv.lgs.entity.Groups;
import ua.lviv.lgs.entity.Users;
import ua.lviv.lgs.service.GroupsService;

@Service
public class GroupsServiceImpl implements GroupsService {
	@Autowired
	private CoursesDao coursesDao;
	@Autowired
	private GroupsDao groupsDao;
	@Autowired
	private UsersDao usersDao;
	// Logger for GroupsServiceImpl Class
	private static Logger log = Logger.getLogger(GroupsServiceImpl.class);
		
	@Transactional
	public void add(String name, int courseId) {
		Groups group = new Groups(name, coursesDao.findOne(courseId));
		groupsDao.save(group);
	}

	@Transactional
	public void edit(int groupId, String name, String courseIdArray, int teacherId) {
		Groups group = groupsDao.findOne(groupId);
		if (name != null) {
			group.setName(name);
		}
		if (courseIdArray != null) {
			String[] temp = courseIdArray.split(",");
			List<Courses> userCourses = new ArrayList<Courses>();
			for (String string : temp) {
				int courseId = Integer.parseInt(string);
				Courses course = coursesDao.findOne(courseId);
				if (!userCourses.contains(course)) {
					userCourses.add(course);
				}
			}
			group.setCourses(userCourses);
		} else {
			group.setCourses(null);
		}
		if (teacherId > 0) {
			group.setTeacherId(teacherId);
		}
		groupsDao.save(group);
	}

	@Transactional
	public void delete(int id) {
		if (id != 0) {
			log.info("[INFO] Deleting Group id="+id+"]");
			groupsDao.delete(id);
		}
	}

	@Transactional
	public List<GroupDTO> findAll() {
		List<Groups> groups = groupsDao.findAll();
		List<GroupDTO> groupsDTO = new ArrayList<GroupDTO>();
		for (Groups group : groups) {
			GroupDTO groupDTO = new GroupDTO(group.getId(), group.getName(), group.getCourses());
			if (group.getTeacherId() != null) {
				groupDTO.setTeacherId(group.getTeacherId());
			}
			groupsDTO.add(groupDTO);
		}
		return groupsDTO;
	}

	@Transactional
	public GroupDTO findById(int id) {
		try {
			Groups group = groupsDao.findOne(id);
			List<Users> users = group.getUsers();
			List<UserDTO> usersDTO = new ArrayList<UserDTO>();
			for (Users user : users) {
				UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSecondname());
				usersDTO.add(userDTO);
			}
			GroupDTO groupDTO = new GroupDTO(id, group.getName());
			groupDTO.setUsers(usersDTO);
			if (group.getTeacherId() != null) {
				groupDTO.setTeacherId(group.getTeacherId());
			}
			if (!(group.getCourses().isEmpty())) {
				groupDTO.setCourses(group.getCourses());
			}
			return groupDTO;
		} catch (Exception e) {
			return null;
		}
	}

//	@Transactional
//	public List<Groups> findByName(String name) {
//		List<Groups> groups = groupsDao.findGroupsByName(name);
//		return groups;
//	}

	@Transactional
	public List<CourseDTO> findCourses(int id) {
		Groups group = groupsDao.findOne(id);
		try {
			List<Courses> courses = group.getCourses();
			List<CourseDTO> groupCourses = new ArrayList<CourseDTO>();
			for (Courses course : courses) {
				groupCourses.add(new CourseDTO(course.getId(), course.getName()));
			}
			return groupCourses;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public UserDTO findGroupTeacher(int groupId) {
		if (groupId != 0) {
			Groups group = groupsDao.findOne(groupId);
			if (group.getTeacherId() != null) {
				Users user = usersDao.findOne(group.getTeacherId());
				UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSecondname(), user.getPhone(),
						user.getEmail(), user.getRole().getId(), user.getRole().getName(), user.getPassword());
				return userDTO;
			}
		}
		return null;
	}

	@Transactional
	public List<Courses> findGroupCourses(int id) {
		try {
			List<Courses> courses = groupsDao.findOne(id).getCourses();
			if(courses.isEmpty()){
				return null;
			}else{
				return courses;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public Groups findGroupById(int id) {
		return groupsDao.findOne(id);
	}

	@Transactional
	public List<GroupDTO> findGroupByUserId(int id) {
		Users user = usersDao.findOne(id);
		List<Groups> groups = user.getGroups();
		List<GroupDTO> groupsDTO = new ArrayList<GroupDTO>();
		for (Groups group : groups) {
			List<Users> users = group.getUsers();
			List<UserDTO> usersDTO = new ArrayList<UserDTO>();
			for (Users tempUser : users) {
				UserDTO userDTO = new UserDTO(tempUser.getId(), tempUser.getName(), tempUser.getSecondname());
				usersDTO.add(userDTO);
			}
			GroupDTO groupDTO = new GroupDTO(group.getId(), group.getName());
			groupDTO.setUsers(usersDTO);
			groupsDTO.add(groupDTO);
		}

		return groupsDTO;
	}

	public Boolean isGroupNameUnique(String name) {
		Integer groupId = groupsDao.findGroupIdByName(name);
		if(groupId == null){
			return true;
		}else{
			return false;
		}
	}

}
