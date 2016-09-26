package ua.lviv.lgs.service;

import java.util.List;

import ua.lviv.lgs.dto.CourseDTO;
import ua.lviv.lgs.dto.GroupDTO;
import ua.lviv.lgs.dto.UserDTO;
import ua.lviv.lgs.entity.Courses;
import ua.lviv.lgs.entity.Groups;

public interface GroupsService {
	
	void add(String name, int courseId);

	void edit(int id, String name, String courseIdArray,int teacherId);

	public List<CourseDTO> findCourses(int id);
	
	public List<Courses> findGroupCourses(int id);
	
	void delete(int id);

	List<GroupDTO> findAll();

	public UserDTO findGroupTeacher(int id);
	
	GroupDTO findById(int id);
	
	Groups findGroupById(int id);
	
//	public List<Groups> findByName(String name);
	
	public List<GroupDTO> findGroupByUserId(int id);
	
	public Boolean isGroupNameUnique(String name);
}
