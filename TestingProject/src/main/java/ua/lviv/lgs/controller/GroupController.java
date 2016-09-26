package ua.lviv.lgs.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.lviv.lgs.dao.TestsDao;
import ua.lviv.lgs.dto.CourseDTO;
import ua.lviv.lgs.dto.GroupDTO;
import ua.lviv.lgs.dto.UserDTO;
import ua.lviv.lgs.entity.Courses;
import ua.lviv.lgs.entity.Tests;
import ua.lviv.lgs.entity.Users;
import ua.lviv.lgs.service.CoursesService;
import ua.lviv.lgs.service.GroupsService;
import ua.lviv.lgs.service.TestsService;
import ua.lviv.lgs.service.ThemesService;
import ua.lviv.lgs.service.UsersService;

@Controller
public class GroupController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private TestsService testsService;
	@Autowired
	private GroupsService groupsService;
	@Autowired
	private CoursesService coursesService;
	@Autowired
	private ThemesService themesService;
	// Logger for GroupController Class
	private static Logger log = Logger.getLogger(GroupController.class);

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/new_group", method = RequestMethod.GET)
	public String creatingGroup(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else if (!(principal.getName().equalsIgnoreCase("admin"))) {
			if (usersService.findById(Integer.parseInt(principal.getName())).getRoleId() == 2) {
				List<CourseDTO> courses = coursesService.findUserCourses(Integer.parseInt(principal.getName()));
				model.addAttribute("courses", courses);
				return "groups-new";
			}
		}
		List<CourseDTO> courses = coursesService.findAll();
		model.addAttribute("courses", courses);
		return "groups-new";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/new_group", method = RequestMethod.POST)
	public String addGroup(RedirectAttributes redirectAttributes, Principal principal,  @RequestParam(value = "name") String name, @RequestParam(value = "courseId") int courseId) {
		if(groupsService.isGroupNameUnique(name)){
			log.info("[INFO] User with id="+principal.getName()+" created new group with name=" + name + " and with course id="+courseId);
			groupsService.add(name, courseId);
			return "redirect:/view_courses";
		}else{
			redirectAttributes.addFlashAttribute("message", true);
			log.info("[INFO] User with id="+principal.getName()+" trying to create group with name=" + name + ", which is alredy exist in database");
			return "redirect:/new_group";
		}
		
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/view_group_{groupId}", method = RequestMethod.GET)
	public String viewGroup(Model model, Principal principal, @PathVariable int groupId) {
		if (principal == null) {
			return "redirect:/loginpage";
		}
		boolean access = usersService.hasPrincipalAccessToTheGroup(groupId, principal.getName());
		if (access) {
			List<Courses> innerCourses = coursesService.findInnerCourses(groupId);
			List<Tests> tests = new ArrayList<Tests>();
			if(innerCourses == null){
				log.error("[ERROR] User with id="+principal.getName()+" trying to view group, which does not exist");
				return "404";
			}else{
				for (Courses courses2 : innerCourses) {
					List<Tests> temproraryTests = testsService.findtestsByCourse(courses2.getId());
					tests.addAll(temproraryTests);
				}
				UserDTO teacher = new UserDTO();
				Integer teacherId = groupsService.findGroupById(groupId).getTeacherId();
				if (teacherId == null) {
				} else {
					teacher = groupsService.findGroupTeacher(groupId);
				}
				List<UserDTO> students = usersService.findAllByGroupId(groupId);
				model.addAttribute("students", students);
				List<CourseDTO> courses = groupsService.findCourses(groupId);
				GroupDTO group = groupsService.findById(groupId);
				model.addAttribute("group", group).addAttribute("teacher", teacher).addAttribute("courses", courses)
						.addAttribute("tests", tests);
			}
			
		} else {
			log.warn("[WARN] User with id="+principal.getName()+" trying to view group (But he has not access)");
			return "403";
		}
		return "groups-view";
	}

	@Secured({ "ROLE_STUDENT" })
	@RequestMapping(value = "/view_groups_{userId}", method = RequestMethod.GET)
	public String viewGroupsForStudents(Model model, Principal principal, @PathVariable int userId) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else if (Integer.parseInt(principal.getName()) != userId) {
			log.warn("[WARN] User with id="+principal.getName()+" trying to view groups list (But he has not access)");
			return "403";
		}
		List<GroupDTO> groups = groupsService.findGroupByUserId(userId);
		model.addAttribute("groups", groups);
		model.addAttribute("userId", userId);
		return "groups-allForStudent";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/edit_group_{groupId}", method = RequestMethod.GET)
	public String editingGroup(Model model, Principal principal, @PathVariable int groupId) {
		if (principal == null) {
			return "redirect:/loginpage";
		}else{
			GroupDTO group = groupsService.findById(groupId);
			if(group == null){
				log.error("[ERROR] User with id="+principal.getName()+" trying to edit group, which does not exist");
				return "404";
			}else{
				boolean access = usersService.hasPrincipalAccessToTheGroup(groupId, principal.getName());
				if (access) {
					List<CourseDTO> groupCourses = groupsService.findCourses(groupId);
					if(groupCourses == null){
						log.error("[ERROR] User with id="+principal.getName()+" trying to edit group without courses");
						return "404";
					}else{
						List<CourseDTO> courses = coursesService.findAll();
						List<CourseDTO> uncheckedCourses = (List<CourseDTO>) themesService.findAllUnDuplicated(groupCourses,
								courses);
						List<Integer> groupCoursesids = new ArrayList<Integer>();
						for (CourseDTO course : groupCourses) {
							groupCoursesids.add(course.getId());
						}
						List<UserDTO> teachers = usersService.findTeachersByCoursesIds(groupCoursesids);
							
							UserDTO teacher = new UserDTO();
							if (groupsService.findGroupTeacher(groupId) != null) {
								teacher = groupsService.findGroupTeacher(groupId);
							}
							model.addAttribute("group", group).addAttribute("courses", uncheckedCourses)
									.addAttribute("groupCourses", groupCourses).addAttribute("teachers", teachers)
									.addAttribute("teacher", teacher);
							log.info("[INFO] User with id="+principal.getName()+" trying to edit group [id="+groupId+ " name=" + group.getName() + "]");
					}
				}
				return "groups-edit";
			}
		}
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/edit_group", method = RequestMethod.POST)
	public String updatingGroup(Model model, Principal principal, RedirectAttributes redirectAttributes, @RequestParam("groupId") int groupId,
			@RequestParam(value = "name") String name, @RequestParam(value = "courseId") String courseIdArray,
			@RequestParam(value = "userId") int userId, @RequestParam(value = "groupnamechange") String groupNameIsChange) {
		
		Boolean isChange = Boolean.parseBoolean(groupNameIsChange);
		if(isChange){
			if(groupsService.isGroupNameUnique(name)){
				log.info("[INFO] User with id="+principal.getName()+" save group [id="+groupId+ " name=" + name + " with course [id ="+courseIdArray+"] teacher id="+userId+"]");
				groupsService.edit(groupId, name, courseIdArray, userId);
				return "redirect:/view_group_" + groupId;
			}else{
				redirectAttributes.addFlashAttribute("message", true);
				log.info("[INFO] User with id="+principal.getName()+" save group [id="+groupId+ " name=" + name + "]");
				return "redirect:/edit_group_"+groupId;
			}
		}else{
			//log.info("[INFO] User with id="+principal.getName()+" save group [id="+groupId+ " name=" + name + "] with course [id ="+courseIdArray+"] teacher id="+userId+"]");
			//groupsService.edit(groupId, name, courseIdArray, userId);
			return "redirect:/view_group_" + groupId;
		}
		
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/delete_group_{id}", method = RequestMethod.GET)
	public String deletingGroup(Principal principal, @PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			log.info("[INFO] User with id="+principal.getName()+" trying to delete groupe with id="+id+"]");
			groupsService.delete(id);
		}
		return "redirect:/view_courses";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/set_test_to_students", method = RequestMethod.POST)
	public String settingTestToStudents(Model model, @RequestParam(value = "checkbox") String checkbox,
			@RequestParam(value = "test") String test, Principal principal,
			@RequestParam(value = "groupId") String groupId) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			log.info("[INFO] User with id="+principal.getName()+" set test]");
			testsService.setTestsToStudents(checkbox, test);
		}

		return "redirect:/view_group_" + groupId;
	}

}
