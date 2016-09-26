package ua.lviv.lgs.controller;

import java.security.Principal;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.lviv.lgs.dto.ThemeDTO;
import ua.lviv.lgs.entity.Questions;
import ua.lviv.lgs.service.QuestionsService;
import ua.lviv.lgs.service.TestsService;
import ua.lviv.lgs.service.ThemesService;
import ua.lviv.lgs.service.UsersService;

@Controller
public class QuestionController {

	@Autowired
	QuestionsService questionSevice;
	@Autowired
	ThemesService themesSevice;
	@Autowired
	UsersService usersService;
	@Autowired
	TestsService testsService;

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/new_question_theme{themeId}", method = RequestMethod.GET)
	public String creatingQuestion(Model model, Principal principal, @PathVariable int themeId) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			ThemeDTO theme = themesSevice.findById(themeId);
			if (theme == null) {
				return "404";
			} else {
				model.addAttribute("theme", theme);
				return "questions-new";
			}
		}
	}

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/new_question", method = RequestMethod.POST)
	public String addingQuestion() {
		System.out.println("okidoki");
		return "questions-new";
	}

	// // @Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	// @RequestMapping(value = "/delete_question_id{id}", method =
	// RequestMethod.GET)
	// public String deleteQuestion(Model model, Principal principal,
	// @PathVariable int id) {
	// if (principal == null) {
	// // return "redirect:/loginpage";
	// } // else {
	// //model.addAttribute("themeId", themeId);
	// // }
	//
	// return "questions-new";
	// }

	// @Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@Transactional
	@RequestMapping(value = "/edit_question_id{id}", method = RequestMethod.GET)
	public String editQuestion(Model model, Principal principal, @PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			Questions question = questionSevice.findById(id);
			if (question == null) {
				return "404";
			} else {
				Hibernate.initialize(question.getAnswers());
				int themeId= question.getTheme().getId();
				ThemeDTO theme = themesSevice.findById(themeId);
				model.addAttribute("theme", theme);
				model.addAttribute("question", question);
				return "questions-edit";
			}
		}
	}
	
	@Transactional
	@RequestMapping(value = "/view_question_id{id}", method = RequestMethod.GET)
	public String viewQuestion(Model model, Principal principal, @PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			Questions question = questionSevice.findById(id);
			if (question == null) {
				return "404";
			} else {
				Hibernate.initialize(question.getAnswers());
				int themeId= question.getTheme().getId();
				ThemeDTO theme = themesSevice.findById(themeId);
				model.addAttribute("theme", theme);
				model.addAttribute("question", question);
				return "questions-view";
			}
		}
	}

	// @Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	// @RequestMapping(value = "/create_question", method = RequestMethod.POST)
	// public String addQuestion(){}

}
