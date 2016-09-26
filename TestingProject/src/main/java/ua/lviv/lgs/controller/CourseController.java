package ua.lviv.lgs.controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.lviv.lgs.dto.CourseDTO;
import ua.lviv.lgs.dto.GroupDTO;
import ua.lviv.lgs.service.CoursesService;
import ua.lviv.lgs.service.UsersService;

@Controller
public class CourseController {
	@Autowired
	private CoursesService coursesService;
	@Autowired
	private UsersService usersService;
	// Logger for CourseController Class
	private static Logger log = Logger.getLogger(CourseController.class);

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER" })
	@RequestMapping(value = "/new_course", method = RequestMethod.GET)
	public String creatingCourse(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/loginpage";
		}
		//log.info("User with id="+principal.getName()+" creating new course");
		return "courses-new";
	}

	@SuppressWarnings("finally")
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER" })
	@RequestMapping(value = "/new_course", method = RequestMethod.POST)
	public String addingCourse(Model model, Principal principal, RedirectAttributes redirectAttributes, @RequestParam("name") String name) {
		try{
		name = name.trim();
		if(coursesService.isCourseUnique(name)){
			coursesService.add(name);
			log.info("[INFO] User with id="+principal.getName()+" created new course with name=" + name);
			return "redirect:/view_courses";
		}else{
			redirectAttributes.addFlashAttribute("message", true);
			log.info("[INFO] User with id="+principal.getName()+" trying to create course with name=" + name + ", which is alredy exist in database");
			return "redirect:/new_course";
		}
		} catch(Exception e) {
			e.printStackTrace();
			log.fatal("[FATAL] User with id="+principal.getName()+" created new course with name=" + name);
			log.fatal("[FATAL] " + e.getMessage());
		} finally {
			redirectAttributes.addFlashAttribute("message", true);
			return "redirect:/new_course";
		}
	
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER" })
	@RequestMapping(value = "/edit_course_{courseId}", method = RequestMethod.GET)
	public String editingCourse(Model model, Principal principal, @PathVariable int courseId) {
		if (principal == null) {
			return "redirect:/loginpage";
		}
		CourseDTO course = coursesService.findById(courseId);
		if(course == null){
			log.error("[ERROR] User with id="+principal.getName()+" trying to edit course, which does not exist");
			return "404";
		}else{
			boolean access = usersService.hasPrincipalAccessToCourse(courseId, principal.getName());
			if (!access) {
				log.warn("[WARN] User with id="+principal.getName()+" trying to edit course with id="+courseId+"(But he has not access)");
				return "403";
			} else {
				model.addAttribute("course", course);
				log.info("[INFO] User with id="+principal.getName()+" trying to edit course [id="+courseId+ " name=" + course.getName() + "]");
			}
			return "courses-edit";
		}
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/edit_course", method = RequestMethod.POST)
	public String updatingCourse(Model model, Principal principal, RedirectAttributes redirectAttributes, @RequestParam("courseId") int courseId,
			@RequestParam(value = "name") String name, @RequestParam(value = "coursechange") String coursechange) {
		name = name.trim();
		Boolean courseChange = Boolean.parseBoolean(coursechange);
		if(courseChange){
			if(coursesService.isCourseUnique(name)){
				coursesService.edit(courseId, name);
				log.info("[INFO] User with id="+principal.getName()+" save course [id="+courseId+ " name=" + name + "]");
				return "redirect:/view_course_" + courseId;
			}else{
				redirectAttributes.addFlashAttribute("message", true);
				log.info("[INFO] User with id="+principal.getName()+" save course [id="+courseId+ " name=" + name + "]");
				return "redirect:/edit_course_"+courseId;
			}
		}else{
			log.info("[INFO] User with id="+principal.getName()+" save course [id="+courseId+ " name=" + name + "]");
			return "redirect:/view_course_" + courseId;
		}
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/view_courses", method = RequestMethod.GET)
	public String viewAllCourses(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			int userRole;
			if (principal.getName().equalsIgnoreCase("admin")) {
				userRole = 0;
			} else {
				userRole = usersService.findUserById(Integer.parseInt(principal.getName())).getId();
			}
			if (userRole==0 || userRole==1) {
				List<CourseDTO> courses = coursesService.findAllWithGroups();
				model.addAttribute("courses", courses);

			} else {
				List<CourseDTO> courses = coursesService.findUserCourses(Integer.parseInt(principal.getName()));
				model.addAttribute("courses", courses);
			}
		}
		return "courses-all";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/view_course_{courseId}", method = RequestMethod.GET)
	public String viewCourse(Model model, Principal principal, @PathVariable int courseId) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			CourseDTO course = coursesService.findById(courseId);
			if(course == null){
				log.error("[ERROR] User with id="+principal.getName()+" trying to view course, which does not exist");
				return "404";
			}else{
				if (!principal.getName().equalsIgnoreCase("admin")) {
				}
					boolean access = usersService.hasPrincipalAccessToCourse(courseId, principal.getName());
					if (!access) {
						log.warn("[WARN] User with id="+principal.getName()+" trying to view course (But he has not access)");
						return "403";
					}
				}
				List<GroupDTO> groups = coursesService.findCourseGroups(courseId);
				model.addAttribute("course", course).addAttribute("groups", groups);

				return "courses-viewGroups";
			}
			
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/delete_course_{id}", method = RequestMethod.GET)
	public String deletingCourse(Principal principal, RedirectAttributes redirectAttributes,@PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			log.info("[INFO] User with id="+principal.getName()+" trying to delete course with id="+id+"]");
			boolean permissionToDelete = coursesService.delete(id);
			if(!permissionToDelete){
				log.info("[INFO] User with id="+principal.getName()+" trying to delete course with id="+id+" (But Course groups is not empty)]");
				redirectAttributes.addFlashAttribute("message", true);
				return "redirect:/view_course_" + id;
			}
		}
		return "redirect:/view_courses";
	}
}
