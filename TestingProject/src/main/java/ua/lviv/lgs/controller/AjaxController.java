package ua.lviv.lgs.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import ua.lviv.lgs.dto.QuestionDTO;
import ua.lviv.lgs.dto.TestQuestionDTO;
import ua.lviv.lgs.dto.UserDTO;
import ua.lviv.lgs.dto.UserForPaginationDTO;
import ua.lviv.lgs.entity.Groups;
import ua.lviv.lgs.entity.Questions;
import ua.lviv.lgs.entity.Themes;
import ua.lviv.lgs.entity.Users;
import ua.lviv.lgs.jsonview.Views;
import ua.lviv.lgs.service.AnswersService;
import ua.lviv.lgs.service.GroupsService;
import ua.lviv.lgs.service.QuestionsService;
import ua.lviv.lgs.service.ResultsService;
import ua.lviv.lgs.service.ThemesService;
import ua.lviv.lgs.service.UsersService;

@RestController
public class AjaxController {

	@Autowired
	UsersService usersService;
	@Autowired
	QuestionsService questionSevice;
	@Autowired
	GroupsService groupsService;
	@Autowired
	AnswersService answerService;
	@Autowired
	ThemesService themeService;
	@Autowired
	ResultsService resultsService;
	// Logger for ResultController Class
	private static Logger log = Logger.getLogger(AjaxController.class);
	

	/**
	 * This method searching users in DB by input value input value should be
	 * start of name or second name of user in database
	 * 
	 * @param searchString
	 *            - string in input element and changing every time when add or
	 *            deleted new symbol
	 * 
	 * 
	 * @return list of found users
	 *
	 */

	@JsonView(Views.Public.class)
	@RequestMapping(value = "question_by_theme_{page}", method = RequestMethod.POST)
	public @ResponseBody List<QuestionDTO> gerQuestionsViaAJAX(@RequestBody String searchObject, @PathVariable int page) {
		JSONObject object = new JSONObject(searchObject);
		List<QuestionDTO> questions = questionSevice.findQuestionsByThemeAndPage(Integer.parseInt(object.getString("themeId")), page);
		return questions;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value = "user/search", method = RequestMethod.POST)
	public @ResponseBody List<String> getSearchResultViaAjax(@RequestBody String searchObject) {
		JSONObject object = new JSONObject(searchObject);
		List<String> users = usersService.getUserbyNameAndRole(object.getString("search"), object.getString("roleId"));
		return users;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value = "user/search/{currentPage}", method = RequestMethod.POST)
	public @ResponseBody UserForPaginationDTO getSearchResultViaAjax(@RequestBody String searchObject, @PathVariable int currentPage) {
		JSONObject object = new JSONObject(searchObject);
		List<Users> users = usersService.getUserbyNameAndRole(object.getString("search"), object.getString("roleId"), currentPage);
		Integer listSize = usersService.getUserbyNameAndRoleCount(object.getString("search"), object.getString("roleId"));
		UserForPaginationDTO dtoObj = new UserForPaginationDTO(listSize, usersService.ITEMS_PER_PAGE, users);
		return dtoObj;
	}

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/course_themes", method = RequestMethod.POST)
	public @ResponseBody List<Themes> findAllCourseThemes(@RequestBody String courseId) {
		List<Themes> themes = themeService.findAllByCourse(Integer.parseInt(courseId));
		return themes;
	}

//	@JsonView(Views.Public.class)
//	@RequestMapping(value = "/group/addUser", method = RequestMethod.POST)
//	public List<Groups> addUserToGroup(@RequestBody String searchObject) {
//		JSONObject object = new JSONObject(searchObject);
//		List<Groups> groups = groupsService.findByName(object.getString("groupName").trim());
//		return groups;
//	}

	@SuppressWarnings("unchecked")
	@JsonView(Views.Public.class)
	@RequestMapping(value = "tests/test/nextQuestion", method = RequestMethod.POST)
	
	public @ResponseBody TestQuestionDTO getNextQuestion(HttpSession httpSession,
			@RequestParam("checkedAnswers") String[] checkedAnswers,
			@RequestParam("nextQuestionNumber") String nextQuestionNumber,
			@RequestParam("thisQuestionNumber") String thisQuestionNumber) {
		System.out.println("before "+httpSession.getLastAccessedTime());
		int current = Integer.parseInt(thisQuestionNumber);
		int next = Integer.parseInt(nextQuestionNumber);
		System.out.println("current " + current);
		System.out.println("next " + next);
		TestQuestionDTO thisTestQuestion = (TestQuestionDTO) ((List<TestQuestionDTO>) httpSession
				.getAttribute("testQuestions")).get(current);
		TestQuestionDTO nextTestQuestion = (TestQuestionDTO) ((List<TestQuestionDTO>) httpSession
				.getAttribute("testQuestions")).get(next);
		int checkedAnswersId[] = new int[checkedAnswers.length];
		if (checkedAnswers.length != 0) {
			for (int j = 0; j < checkedAnswers.length; j++) {
				checkedAnswersId[j] = Integer.parseInt(checkedAnswers[j]);
			}
			thisTestQuestion.setCheckedAnswersId(checkedAnswersId);
		}
		Date d = new Date();
		
		System.out.println("System.nanotime="+d.getTime());
		System.out.println("after "+httpSession.getLastAccessedTime());
		return nextTestQuestion;
	}

	@RequestMapping(value = "questions/saveQuestion", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody int saveQuestion(@RequestParam(value = "questionId",required = false) Integer questionId,
			@RequestParam("questionText") String questionText,
			@RequestParam(value = "questionPicture", required = false) MultipartFile questionPicture,
			@RequestParam("themeId") int themeId, @RequestParam("complexity") int complexity,
			@RequestParam("isMultipleAnswers") boolean isMultipleAnswers) {
		Questions q;
		System.out.println(questionId);
		if (questionId == null) {
			q = questionSevice.add(questionText, questionPicture, themeId, complexity, isMultipleAnswers);
			System.out.println("question added");
		} else {
			q = questionSevice.edit(questionId, questionText, questionPicture, themeId, complexity, isMultipleAnswers);
			System.out.println("question edited");
		}

		System.out.println(q);
		return q.getId();
	}

	@RequestMapping(value = "questions/saveAnswer", method = RequestMethod.POST)
	@Transactional
	public void createNewAnswer(@RequestParam(value = "answerId",required = false) Integer answerId,
			@RequestParam("answerText") String answerText,
			@RequestParam(value = "answerPicture", required = false) MultipartFile answerPicture,
			@RequestParam("answerStatus") boolean answerStatus, @RequestParam("questionId") int questionId) {
		if (answerId == null || answerId.intValue()==0) {
			answerService.add(answerText, answerPicture, answerStatus, questionId);
			System.out.println("answer addddded");
		} else {
			answerService.edit(answerId, answerText, answerPicture, answerStatus, questionId);
			System.out.println("answer eddidtted");
		}
		
	}

	@RequestMapping(value = "questions/deleteQuestion", method = RequestMethod.POST)
	@Transactional
	public void deleteQuestion(@RequestParam(value = "questionId") int questionId) {
		questionSevice.delete(questionId);
	}

	@RequestMapping(value = "questions/deleteAnswer", method = RequestMethod.POST)
	@Transactional
	public void deleteAnswer(@RequestParam(value = "answerId") int answerId) {
		answerService.delete(answerId);
		System.out.println("answer deleted");
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER", "ROLE_STUDENT" })
	@RequestMapping(value = "/result/saveResultComment", method = RequestMethod.POST)
	public @ResponseBody String saveResultComment(@RequestBody String searchObject, Principal principal) {
		JSONObject object = new JSONObject(searchObject);
		String message = resultsService.saveComment(object.getInt("resultId"), principal.getName(),
				object.getString("comment"));
		log.info("[INFO] User with id="+principal.getName()+" add coment " +object.getString("comment")+ "]");
		return message;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER", "ROLE_STUDENT" })
	@RequestMapping(value = "/group/uploadTeachers", method = RequestMethod.POST)
	public @ResponseBody List<UserDTO> uploadTeachers(HttpServletRequest request) {
		String[] myJsonData = request.getParameterValues("courseIds[]");
		List<Integer> ids = new ArrayList<Integer>();
		if (myJsonData != null) {
			for (String courseId : myJsonData) {
				if (courseId != null && !courseId.equals("")) {
					try {
						Integer i = Integer.parseInt(courseId);
						ids.add(i);
					} catch (NumberFormatException e) {
						System.out.println("AjaxController : uploadTeachers" + e);
					}
				}
			}
		}
		List<UserDTO> users = usersService.findTeachersByCoursesIds(ids);
		return users;
	}

}
