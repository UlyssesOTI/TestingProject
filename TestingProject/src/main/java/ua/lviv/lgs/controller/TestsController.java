package ua.lviv.lgs.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.lviv.lgs.dto.CourseDTO;
import ua.lviv.lgs.dto.TestDTO;
import ua.lviv.lgs.dto.TestQuestionDTO;
import ua.lviv.lgs.dto.ThemeDTO;
import ua.lviv.lgs.entity.Answers;
import ua.lviv.lgs.entity.Questions;
import ua.lviv.lgs.service.CoursesService;
import ua.lviv.lgs.service.QuestionsService;
import ua.lviv.lgs.service.ResultsService;
import ua.lviv.lgs.service.TestsService;
import ua.lviv.lgs.service.ThemesService;
import ua.lviv.lgs.service.UsersService;

@Controller
public class TestsController {
	@Autowired
	private QuestionsService questionSevice;
	@Autowired
	private UsersService usersService;
	@Autowired
	TestsService testsService;
	@Autowired
	ThemesService themesService;
	@Autowired
	CoursesService coursesService;
	@Autowired
	private ResultsService resultsService;
	
	private static Logger log = Logger.getLogger(TestsController.class);

	List<Questions> questionsList;

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/new_test", method = RequestMethod.GET)
	public String creatingTest(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else if (!(principal.getName().equalsIgnoreCase("admin"))) {
			if (usersService.findById(Integer.parseInt(principal.getName())).getRoleId() == 2) {
				List<CourseDTO> courses = coursesService.findUserCourses(Integer.parseInt(principal.getName()));
				model.addAttribute("courses", courses);
				log.info("[INFO] user with id="+principal.getName()+" started creating new thest");
				return "tests-new";
			}
		}
		List<CourseDTO> courses = coursesService.findAll();
		model.addAttribute("courses", courses);
		log.info("[INFO] user with id="+principal.getName()+" started creating new thest");
		return "tests-new";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/new_test", method = RequestMethod.POST)
	public String addingTest(@RequestParam(value = "name") String name, @RequestParam(value = "courseId") int courseId,
			@RequestParam(value = "time") int time,
			@RequestParam(value = "highGradeQuestionQuantity") int highGradeQuestionQuantity,
			@RequestParam(value = "middleGradeQuestionQuantity") int middleGradeQuestionQuantity,
			@RequestParam(value = "lowGradeQuestionQuantity") int lowGradeQuestionQuantity,
			@RequestParam(value = "themeId") String themeIdArray, RedirectAttributes redirectAttributes,
			Principal principal) {
		
		boolean prohibitionToAdd = testsService.questionQuntityValidation(name, time, highGradeQuestionQuantity,
				middleGradeQuestionQuantity, lowGradeQuestionQuantity, courseId, themeIdArray);
		if(!prohibitionToAdd){
			testsService.add(name, time, highGradeQuestionQuantity,
					middleGradeQuestionQuantity, lowGradeQuestionQuantity, courseId, themeIdArray);
		int id = testsService.findTestIdByName(name);
		log.info("[INFO] user with id="+principal.getName()+" created test with name: "+name+", courseId="+courseId+", time="+time+", themes: "+themeIdArray+", difficultQuestions="+highGradeQuestionQuantity+", mediumQuestions="+middleGradeQuestionQuantity+", easyQuestions="+lowGradeQuestionQuantity);
		return "redirect:/view_test_" + id;
		}
		redirectAttributes.addFlashAttribute("permissionToAdd", prohibitionToAdd);
		log.warn("[WARN] User with id="+principal.getName()+" tryed create test but faild (not enought question in database)");
		return "redirect:/new_test";

	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/view_test_{id}", method = RequestMethod.GET)
	public String viewingTest(Model model, Principal principal, @PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			TestDTO test = testsService.findDTOById(id);
			if (test == null) {
				log.error("[ERROR] user witn id="+principal.getName()+"tryed view test with id="+id+"but faild (thest dosn't exist)");
				return "404";
			} else {
				if (!(principal.getName().equalsIgnoreCase("admin"))) {
					boolean access = usersService.hasPrincipalAccessToViewTest(id, principal.getName());
					if (!access) {
						log.warn("[WARN] user witn id="+principal.getName()+"tryed view test with id="+id+"but faild (not enought rights)");
						return "403";
					}
				}
				List<ThemeDTO> themes = testsService.findTestThemesByTestId(id);
				model.addAttribute("test", test).addAttribute("themes", themes);
			}
			return "tests-view";
		}
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/view_tests", method = RequestMethod.GET)
	public String viewingAllTest(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else if (!(principal.getName().equalsIgnoreCase("admin"))) {
			if (usersService.findById(Integer.parseInt(principal.getName())).getRoleId() == 2) {
				List<CourseDTO> courses = coursesService.findUserCourses(Integer.parseInt(principal.getName()));
				model.addAttribute("courses", courses);
				return "tests-all";
			}
		}
		List<CourseDTO> courses = coursesService.findAll();
		model.addAttribute("courses", courses);
		return "tests-all";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/view_tests_course{courseId}", method = RequestMethod.GET)
	public String viewingAllTestsByCourse(Model model, Principal principal, @PathVariable int courseId) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			CourseDTO course = coursesService.findById(courseId);
			if (course == null) {
				log.error("[ERROR] user with id="+principal.getName()+" tryed view test by course with id="+courseId+" but faild (course dosn't exist)");
				return "404";
			} else {
				List<TestDTO> tests = testsService.findTestsByCourses(courseId);
				model.addAttribute("tests", tests).addAttribute("course", course);
				return "courses-viewTests";
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/edit_test_{id}", method = RequestMethod.GET)
	public String editingTest(Model model, Principal principal, @PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			TestDTO test = testsService.findDTOById(id);
			if (test == null) {
				log.error("[ERROR] user with id="+principal.getName()+" tryed edit test with id="+id+" but faild (test dosn't exist)");
				return "404";
			} else {
				if (!(principal.getName().equalsIgnoreCase("admin"))) {
					boolean access = usersService.hasPrincipalAccessToViewTest(id, principal.getName());
					if (!access) {
						log.warn("[WARN] user witn id="+principal.getName()+"tryed edit test with id="+id+"but faild (not enought rights)");
						return "403";
					}
				}
				List<ThemeDTO> testThemes = testsService.findTestThemesByTestId(id);
				List<CourseDTO> courses = coursesService.findAll();
				List<ThemeDTO> themes = themesService.findAllDtoByCourse(test.getCourseId());
				List<ThemeDTO> unDuplicatedThemes = (List<ThemeDTO>) themesService.findAllUnDuplicated(testThemes,
						themes);
				model.addAttribute("test", test).addAttribute("courses", courses)
						.addAttribute("themes", unDuplicatedThemes).addAttribute("testThemes", testThemes);
				log.info("[INFO] user with id="+principal.getName()+"edited test with id="+id+" Old value = name: "+test.getName()+", courseId="+test.getCourseId()+", time: "+test.getTime()+", difficultQuestions="+test.getHighGradeQuestionQuantity()+", middleQuestions="+test.getMiddleGradeQuestionQuantity()+", easyQuestions="+test.getLowGradeQuestionQuantity());
			}
		}
		return "tests-edit";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/edit_test", method = RequestMethod.POST)
	public String updatingTest(@RequestParam(value = "testId") int testId, @RequestParam(value = "name") String name,
			@RequestParam(value = "courseId") int courseId, @RequestParam(value = "time") int time,
			@RequestParam(value = "highGradeQuestionQuantity") int highGradeQuestionQuantity,
			@RequestParam(value = "middleGradeQuestionQuantity") int middleGradeQuestionQuantity,
			@RequestParam(value = "lowGradeQuestionQuantity") int lowGradeQuestionQuantity,
			@RequestParam(value = "themeId") String themeIdArray, RedirectAttributes redirectAttributes, Principal principal) {
		boolean prohibitionToEdit = testsService.questionQuntityValidation(name, time, highGradeQuestionQuantity,
				middleGradeQuestionQuantity, lowGradeQuestionQuantity, courseId, themeIdArray);
		if(!prohibitionToEdit){
			testsService.edit(testId, name, time, highGradeQuestionQuantity, middleGradeQuestionQuantity,
					lowGradeQuestionQuantity, courseId, themeIdArray);
			log.info("[INFO] User with id="+principal.getName()+" edited test with id="+testId+" New value = name: "+name+", courseId="+courseId+", time: "+time+", difficultQuestions="+highGradeQuestionQuantity+", middleQuestions="+middleGradeQuestionQuantity+", easyQuestions="+lowGradeQuestionQuantity);
			return "redirect:/view_test_" + testId;
		}
		redirectAttributes.addFlashAttribute("permissionToEdit", prohibitionToEdit);
		log.warn("[WARN] user with id="+principal.getName()+" tryed edit test with id="+testId+"but faild (not enought question in database)");
		return "redirect:/edit_test_"+testId;
		
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/delete_test_{id}", method = RequestMethod.GET)
	public String deletingUser(Principal principal, @PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			log.info("[INFO] user with id="+principal.getName()+" deleting test with id="+id);
			testsService.delete(id);
		}
		return "redirect:/";
	}

	@Transactional
	@Secured({ "ROLE_STUDENT" })
	@RequestMapping(value = "/tests/newTest_{testId}", method = RequestMethod.GET)
	public String startNewTest(HttpSession httpSession, @PathVariable(value = "testId") String testId,
			Principal principal) {

		if (principal == null) {
			return "redirect:/loginpage";
		}

		if (httpSession.getAttribute("testQuestions") != null) {
			return "tests-start";
		}

		int userId = Integer.parseInt(principal.getName());
		int testid = Integer.parseInt(testId);
		TestDTO test = testsService.findDTOById(testid);
		if (test == null) {
			log.error("[ERROR] user with id="+principal.getName()+" tryed start test with id="+testid+" but faild (test dosn't exist)");
			return "404";
		} else {
			httpSession.setAttribute("testTime", test.getTime());
			httpSession.setAttribute("testId", test.getId());
			List<Questions> questions = questionSevice.generateNewTest(testid, Integer.parseInt(principal.getName()));
//			if (questions == null) {
//				//what ????
//				return "403";
//			}
			List<TestQuestionDTO> testQuestionsList = new ArrayList<TestQuestionDTO>(questions.size());
			int questionNumber = 0;
			for (Questions questions2 : questions) {
				Hibernate.initialize(questions2.getAnswers());
				testQuestionsList.add(new TestQuestionDTO(questions2, questionNumber++));
			}
			System.out.println(testQuestionsList.size());
			httpSession.setAttribute("testQuestions", testQuestionsList);
			System.out.println(testQuestionsList);
			System.out.println("Controller newTest httpSession OK");
			testsService.removeTestsFromStudent(userId, testid);
			log.info("[INFO] user with id="+principal.getName()+" just started test with id="+testid);
			return "tests-start";
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tests/save_result", method = RequestMethod.POST)
	public String saveTestResult(HttpSession httpSession, Principal principal) {
		System.out.println("save resalts/principal==" + principal);
		List<TestQuestionDTO> testQuestions = (List<TestQuestionDTO>) httpSession.getAttribute("testQuestions");
		System.out.println("testQuestions======" + testQuestions);
		List<Questions> questions = new ArrayList<Questions>();
		List<Answers> answers = new ArrayList<Answers>();
		for (TestQuestionDTO tempQuestion : testQuestions) {
			questions.add(tempQuestion.getQuestion());

			List<Answers> tempAnswers = tempQuestion.getQuestion().getAnswers();
			int[] tempArr = tempQuestion.getCheckedAnswersId();

			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i] != 0) {
					answers.add(tempAnswers.get(i));
				}
			}
		}

		System.out.println("questions resalts" + questions);
		System.out.println("anwers resalts" + answers);
		double mark = resultsService.calculateTestResult(testQuestions);
		System.out.println("mark==" + mark);
		resultsService.add(mark, null, Integer.parseInt(principal.getName()),
				(Integer) httpSession.getAttribute("testId"), questions, answers);
		httpSession.removeAttribute("testTime");
		httpSession.removeAttribute("testId");
		httpSession.removeAttribute("testQuestions");
		log.info("[INFO] user with id="+principal.getName()+" just finish his test");
		return "redirect:/view_user_" + principal.getName();
	}

}