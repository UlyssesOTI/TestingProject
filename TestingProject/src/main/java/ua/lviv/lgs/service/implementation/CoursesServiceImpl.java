package ua.lviv.lgs.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.lviv.lgs.controller.CourseController;
import ua.lviv.lgs.dao.CoursesDao;
import ua.lviv.lgs.dto.CourseDTO;
import ua.lviv.lgs.dto.GroupDTO;
import ua.lviv.lgs.entity.Courses;
import ua.lviv.lgs.entity.Groups;
import ua.lviv.lgs.entity.Users;
import ua.lviv.lgs.service.CoursesService;
import ua.lviv.lgs.service.GroupsService;
import ua.lviv.lgs.service.UsersService;

@Service
public class CoursesServiceImpl implements CoursesService {
	@Autowired
	private CoursesDao coursesDao;
	@Autowired
	private UsersService userService;
	@Autowired
	private GroupsService groupService;
	// Logger for CoursesServiceImpl Class
	private static Logger log = Logger.getLogger(CoursesServiceImpl.class);
		
	@Transactional
	public void add(String name) {
		if (name != null) {
			Courses course = new Courses(name);
			coursesDao.save(course);
		}

	}

	@Transactional
	public void edit(int id, String name) {
		if (id != 0) {
			Courses course = coursesDao.findOne(id);
			if (name != null) {
				course.setName(name);
			}
			coursesDao.save(course);
		}
	}

	@Transactional
	public boolean delete(int id) {
		Courses course = coursesDao.findOne(id);
		if(course.getGroups().size()==0&&course.getTests().size()==0&&course.getUsers().size()==0&&course.getThems().size()==0){
			log.info("[INFO] Deleting Course id="+course.getId()+" name="+ course.getName()+"]");
			coursesDao.delete(id);
			return true;
		}else{
			return false;
		}
		
	}

	@Transactional
	public List<CourseDTO> findAll() {
		List<Courses> courses = coursesDao.findAll();
		List<CourseDTO> coursesDTO = new ArrayList<CourseDTO>();
		for (Courses course : courses) {
			CourseDTO courseDTO = new CourseDTO(course.getId(), course.getName());
			coursesDTO.add(courseDTO);
		}
		return coursesDTO;
	}

	@Transactional
	public List<CourseDTO> findAllWithGroups() {
		List<Courses> courses = coursesDao.findAll();
		List<CourseDTO> coursesDTO = new ArrayList<CourseDTO>();
		for (Courses course : courses) {
			CourseDTO courseDTO = new CourseDTO(course.getId(), course.getName());
			List<GroupDTO> groupsDTO = findCourseGroups(course.getId());
			courseDTO.setGroups(groupsDTO);
			coursesDTO.add(courseDTO);
		}
		return coursesDTO;
	}
	
	@Transactional
	public List<GroupDTO> findCourseGroups(int id) {
		try {
			Courses course = coursesDao.findOne(id);
			List<Groups> groups = course.getGroups();
			List<GroupDTO> groupsDTO = new ArrayList<GroupDTO>();
			for (Groups group : groups) {
				GroupDTO groupDTO = new GroupDTO(group.getId(), group.getName());
				groupsDTO.add(groupDTO);
			}
			return groupsDTO;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public List<CourseDTO> findUserCourses(int id) {
		List<Courses> courses = coursesDao.findByUserId(id);
		List<CourseDTO> coursesDTO = new ArrayList<CourseDTO>();
		if (courses != null) {
			for (Courses course : courses) {
				CourseDTO courseDTO = new CourseDTO(course.getId(), course.getName());
				List<GroupDTO> groups = findCourseGroups(course.getId());
				courseDTO.setGroups(groups);
				coursesDTO.add(courseDTO);
			}
			return coursesDTO;
		}else{
			List<Groups> groups = userService.findUserById(id).getGroups();
			List<Courses> studentCourses = new ArrayList<Courses>();
			for (Groups group : groups) {
				List<Courses> groupCourses = group.getCourses();
				for (Courses courses2 : groupCourses) {
					if(!studentCourses.contains(courses2)){
						studentCourses.add(courses2);
					}
				}
			}
			for (Courses studentCourse : studentCourses) {
				CourseDTO courseDTO = new CourseDTO(studentCourse.getId(), studentCourse.getName());
				List<GroupDTO> studentGroupsDTO = new ArrayList<GroupDTO>();
				for (Groups group : groups) {
					if(studentCourse.getGroups().contains(group)){
						GroupDTO studentGroupDTO = new GroupDTO(group.getId(), group.getName());
						if(!studentGroupsDTO.contains(studentGroupDTO)){
							studentGroupsDTO.add(studentGroupDTO);
						}
					}
				}
				courseDTO.setGroups(studentGroupsDTO);
				coursesDTO.add(courseDTO);
			}
			return coursesDTO;
		}
	}

	@Transactional
	public CourseDTO findById(int id) {
		try {
			Courses course = coursesDao.findOne(id);
			CourseDTO courseDTO = new CourseDTO(id, course.getName());
			return courseDTO;
		} catch (Exception e) {
		return null;
		}
		
	}

	@Transactional
	public List<Courses> findInnerCourses(int groupId) {
		List<Courses> innerCourses = new ArrayList<Courses>();
		if (userService.getTeacherByGroupId(groupId) != null) {
			List<Courses> teacherCourses = userService
					.getCoursesByTeacherId(userService.getTeacherByGroupId(groupId).getId());
			List<Courses> groupCourses = groupService.findGroupCourses(groupId);
			for (Courses courses : teacherCourses) {
				List<Groups> groups = courses.getGroups();
				if (groupCourses.contains(courses)) {
					innerCourses.add(courses);
				}
			}
			return innerCourses;
		} else {
			List<Courses> groupCourses = groupService.findGroupCourses(groupId);
			if(groupCourses == null){
				return null;
			}else{
				for (Courses courses : groupCourses) {
					List<Groups> groups = courses.getGroups();
				}
				return groupCourses;
			}
		}

	}

	@Transactional
	public List<CourseDTO> findAllCoursesForUser(int id) {
		Users currentUser = userService.findUserById(id);
		List<Courses> userCourses = currentUser.getCourses();
		List<CourseDTO> newList = new ArrayList<CourseDTO>();
		for (Courses courses : userCourses) {
			CourseDTO dtoCourseObj = new CourseDTO(courses.getId(), courses.getName());
			List<GroupDTO> groups = findCourseGroups(courses.getId());
			dtoCourseObj.setGroups(groups);
			newList.add(dtoCourseObj);
		}
		return newList;
	}

//	public Courses findOneById(int id) {
//		return coursesDao.findOne(id);
//	}

//	public List<Themes> findAllThemesByCourseId(int courseId) {
//		return coursesDao.findAllThemesByCourseId(courseId);
//	}

//	public List<Courses> findAllCourses() {
//		return coursesDao.findAll();
//	}

	public Boolean isCourseUnique(String course) {
		Integer courseId = coursesDao.findCourseIdByName(course);
		if(courseId == null){
			return true;
		}else{
			return false;
		}
	}

}
