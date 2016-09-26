package ua.lviv.lgs.service;

import java.util.List;

import ua.lviv.lgs.dto.CourseDTO;
import ua.lviv.lgs.dto.GroupDTO;
import ua.lviv.lgs.entity.Courses;
import ua.lviv.lgs.entity.Themes;

public interface CoursesService {

	void add(String name);

	void edit(int id, String name);

	boolean delete(int id);

	List<CourseDTO> findAll();
	
//	List<Courses> findAllCourses();
	
	List<CourseDTO> findAllWithGroups();
	
	public List<GroupDTO> findCourseGroups(int id);

	public List<CourseDTO> findUserCourses(int id);

	CourseDTO findById(int id);
	
//	Courses findOneById(int id);
	
	List<Courses> findInnerCourses(int groupId);
	
//	List<Themes> findAllThemesByCourseId(int courseId);
	
	public Boolean isCourseUnique(String course);
	
}
