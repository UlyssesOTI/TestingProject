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

import ua.lviv.lgs.dto.TestResultViewDTO;
import ua.lviv.lgs.service.ResultsService;
import ua.lviv.lgs.service.TestsService;
import ua.lviv.lgs.service.UsersService;

@Controller
public class ResultController {

	@Autowired
	TestsService testsService;
	@Autowired
	ResultsService resultsService;
	@Autowired
	UsersService usersService;
	// Logger for ResultController Class
	private static Logger log = Logger.getLogger(ResultController.class);

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER", "ROLE_STUDENT" })
	@RequestMapping(value = "user/testResult_{resultId}", method = RequestMethod.GET)
	public String getTestReults(@PathVariable String resultId ,Principal principal,Model model) {
		if (principal == null) {
			return "redirect:/loginpage";
		}		
		
		List<TestResultViewDTO> userResults = resultsService.userTestResultView(resultId,principal.getName());	
		if(userResults.size()==0){
			log.error("[ERROR] User with id="+principal.getName()+" trying to view test result (But result list is empty)");
			return "404";
		}else{
			String comment =  resultsService.findById(Integer.parseInt(resultId)).getComment();
			String editComment = "false";
			if(!principal.getName().equals("admin")){
				List<Integer> teacheIds = usersService.findTeacherIdByResultId(resultId);
			    if(!teacheIds.isEmpty() && teacheIds.contains(Integer.parseInt(principal.getName()))){
			    	editComment = "true";    
			    }
			}
			if(comment!=null){
				String[] lines = comment.split("\r\n|\r|\n");
				model.addAttribute("rows", lines.length);
			}else{
				model.addAttribute("rows", 0);
			}
			int role = 0;
			if(!principal.getName().equals("admin")){
				try {
					int id = Integer.parseInt(principal.getName());
					role = usersService.findById(id).getRoleId();
				} catch (NumberFormatException e) {
					System.out.println(e);
				}
			}
			
			
			//log.info("[INFO] User with id="+principal.getName()+" trying to edit comment " +comment+ "]");
			model.
			addAttribute("userResults",userResults).
			addAttribute("role",role).
			addAttribute("comment",comment).
			addAttribute("editComment",editComment).
			addAttribute("resultId",resultId);
			return "tests-userResult";
		}
			  
	}

}
