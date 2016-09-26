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

import ua.lviv.lgs.dto.CourseDTO;
import ua.lviv.lgs.dto.QuestionDTO;
import ua.lviv.lgs.dto.ThemeDTO;
import ua.lviv.lgs.service.CoursesService;
import ua.lviv.lgs.service.QuestionsService;
import ua.lviv.lgs.service.ThemesService;
import ua.lviv.lgs.service.UsersService;

@Controller
public class ThemeController {
	@Autowired
	ThemesService themesService;
	@Autowired
	QuestionsService questionsService;
	@Autowired
	CoursesService coursesService;
	@Autowired
	UsersService usersService;
	private static Logger log = Logger.getLogger(ThemeController.class);

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/new_theme", method = RequestMethod.GET)
	public String creatingTheme(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else if (!(principal.getName().equalsIgnoreCase("admin"))) {
			if (usersService.findById(Integer.parseInt(principal.getName())).getRoleId() == 2) {
				List<CourseDTO> courses = coursesService.findUserCourses(Integer.parseInt(principal.getName()));
				model.addAttribute("courses", courses);
				log.info("[INFO] user with id="+principal.getName()+ " started creating new theme");
				return "themes-new";
			}
		}
		model.addAttribute("courses", coursesService.findAll());
		log.info("[INFO] user with id="+principal.getName()+ " started creating new theme");
		return "themes-new";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/new_theme", method = RequestMethod.POST)
	public String addingTheme(@RequestParam(value = "name") String name,
			@RequestParam(value = "courseId") int courseId, Principal principal) {
		themesService.add(name, courseId);
		log.info("[INFO] user with id="+principal.getName()+" created theme with name "+name+" in course with id="+courseId);
		return "redirect:/view_themes";
	}

	@SuppressWarnings("static-access")
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/view_theme_{id}", method = RequestMethod.GET)
	public String viewingTheme(Model model, Principal principal, @PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			ThemeDTO theme = themesService.findById(id);
			if (theme == null) {
				log.error("[ERROR] user with id="+principal.getName()+" tryed view theme with id="+id+" but faild (theme dosn't exist)");
				return "404";
			}
			boolean access = usersService.hasPrincipalAccessToTheTheme(id, principal.getName());
			if (access) {
				List<QuestionDTO> questions = questionsService.findQuestionsByThemeAndPage(id, 0);
				int questionCount = questionsService.findQuestionsCountByTheme(id);
				model.addAttribute("theme", theme).addAttribute("questions", questions).addAttribute("pageCount", questionsService.calculatepageCount(questionCount, questionsService.ITEMS_PER_PAGE ));
			} else {
				log.warn("[WARN] user with id="+principal.getName()+" tryed view theme with id="+id+" but faild (not enought rights)");
				return "403";
			}
		}
		return "themes-view";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/view_themes", method = RequestMethod.GET)
	public String viewingAllThemes(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else if (!(principal.getName().equalsIgnoreCase("admin"))) {
			if (usersService.findById(Integer.parseInt(principal.getName())).getRoleId() == 2) {
				List<CourseDTO> courses = coursesService.findUserCourses(Integer.parseInt(principal.getName()));
				model.addAttribute("courses", courses);
				return "themes-all";
			}
		}
		model.addAttribute("courses", coursesService.findAll());
		return "themes-all";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/view_themes_course{courseId}", method = RequestMethod.GET)
	public String viewingAllThemes(Model model, Principal principal, @PathVariable int courseId) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			CourseDTO course = coursesService.findById(courseId);
			if (course == null) {
				log.error("[ERROR] user with id="+principal.getName()+" trying view theme by courseId="+courseId+" but faild (course dosn't exist)");
				return "404";
			} else {
				boolean access = usersService.hasPrincipalAccessAllThemesOfCourse(courseId, principal.getName());
				if (access) {
					List<ThemeDTO> themes = themesService.findAllDtoByCourse(courseId);
					model.addAttribute("themes", themes).addAttribute("course", course);
					return "courses-viewThemes";
				} else {
					log.warn("[WARN] user with id="+principal.getName()+" trying view theme by courseId="+courseId+" but faild (not enought rights)");
					return "403";
				}
			}
		}
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/edit_theme_{id}", method = RequestMethod.GET)
	public String editingTheme(Model model, Principal principal, @PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			ThemeDTO theme = themesService.findById(id);
			if (theme == null) {
				log.error("[ERROR] user with id="+principal.getName()+" trying edit tneme with id="+id+" but faild (theme dosn't exist)");
				return "404";
			} else {
				if (!(principal.getName().equalsIgnoreCase("admin"))) {
					if (usersService.findById(Integer.parseInt(principal.getName())).getRoleId() == 2) {
						List<CourseDTO> courses = coursesService.findUserCourses(Integer.parseInt(principal.getName()));
						model.addAttribute("courses", courses);
						model.addAttribute("theme", theme);
						return "themes-edit";
					}
				}
				model.addAttribute("theme", theme).addAttribute("courses", coursesService.findAll());
				log.info("[INFO] user with id="+principal.getName()+" started edit theme with id="+id+", name: "+theme.getName()+", courseId="+theme.getCourseId());
				return "themes-edit";
			}
		}
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/edit_theme", method = RequestMethod.POST)
	public String updatingTheme(Model model, Principal principal, @RequestParam(value = "name") String name,
			@RequestParam(value = "id") int id, @RequestParam(value = "courseId") String courseId) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			log.info("[INFO] theme with id="+id+" was edited, new theme name: "+name+", new theme courseId="+courseId);
			themesService.edit(id, name, courseId);
		}
		return "redirect:/view_themes";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/delete_theme_{id}", method = RequestMethod.GET)
	public String deletingTheme(Principal principal, @PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			themesService.delete(id);
			log.info("[INFO] user with id="+principal.getName()+" deleting theme with id="+id);
		}
		return "redirect:/view_themes";
	}

}
