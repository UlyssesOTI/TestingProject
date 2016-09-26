package ua.lviv.lgs.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.lviv.lgs.service.UsersService;

@Controller
public class HomeController {
	
	@Autowired
	UsersService userService;
	private static Logger log = Logger.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(Principal principal) {
		if (principal == null) {
			return "redirect:/loginpage";
		}else if(principal.getName().equalsIgnoreCase("admin")){
			log.info("[INFO] admin just logged");
			return "redirect:/admin_page";
		}
		String hashCode = null;
		userService.changeHashSume(Integer.parseInt(principal.getName()), hashCode);
		log.info("[INFO] User with id=" + principal.getName() +" just log in");
		return "redirect:/view_user_"+principal.getName();
	}
	
	@RequestMapping(value="/loginpageFail", method = RequestMethod.GET)
	public String loginFail (RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute("message", "Wrong login or password");
		return "redirect:/loginpage";
	}

	@RequestMapping(value="/loginpage", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	  @RequestMapping(value = "/admin_page", method = RequestMethod.GET)
	  public String adminPage( Principal principal) {
	   if (principal == null) {
	    return "redirect:/loginpage";
	   } 
	   return "admin";
	  }
}

