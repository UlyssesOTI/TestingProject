package ua.lviv.lgs.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ua.lviv.lgs.entity.Users;
import ua.lviv.lgs.service.MailSenderService;
import ua.lviv.lgs.service.UsersService;

@Controller
public class SenderController {

	private final String resetPasswordHeader = "Reset password";
	
	private final String beforeHref = "This email was generated authomaticaly \nFor change your password follow link \n";
	
	private final String ABSOLUTE_ADDRESS = "http://localhost:8080/TestingProject/restoringPass";
		
	@Autowired
	private MailSenderService mailSenderService;
	
	@Autowired
	private UsersService userService;
	
	private static Logger log = Logger.getLogger(SenderController.class);
	
	@RequestMapping(value = "/resetPass", method = RequestMethod.GET)
	public String goingToPage(){
			return "resetPass-inputEmail";	
	}
	
	@RequestMapping(value = "/resetPass", method = RequestMethod.POST)
	public String checkedEmail(Model modal, RedirectAttributes redirectAttributes, @RequestParam(value = "email") String email){
		
		if(userService.isEmailUnique(email)){
			redirectAttributes.addFlashAttribute("emailIsntExist", true);
			return "redirect:/resetPass";
		}else{
			Users user = userService.findByEmail(email);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String hashSumm = encoder.encode(user.getPhone());
			hashSumm = hashSumm.replace("/", "").replace(".", "").replace("@" , "");
			userService.changeHashSume(user.getId(), hashSumm);
			mailSenderService.sendMail(resetPasswordHeader, email, (beforeHref + ABSOLUTE_ADDRESS + hashSumm));
			redirectAttributes.addFlashAttribute("messageSended", true);
			log.info("[INFO] sended reset passwords message to email "+email);
			return "redirect:/resetPass";
		}
	}
	
	@RequestMapping(value = "/restoringPass{hashSume}", method = RequestMethod.GET)
	public String restoringPass(Model model, @PathVariable String hashSume, Principal principal){
		Integer usersId = userService.findUsersIdByhashCode(hashSume);
		if(usersId == null){
			return "404";
		}else{
			log.info("[INFO] user with id="+principal.getName()+" tryed enter restoringPass page");
			model.addAttribute("usersId", usersId);
			return "resetPass-newPass";	
		}
	}
	
	@RequestMapping(value = "/restoringPass", method = RequestMethod.POST)
	public String savingNewPass(@RequestParam(value="pass1")String newPassword, @RequestParam(value="userId")int userId){
		userService.changePass(userId, newPassword);
		log.info("[INFO] user with id="+userId+" just change his password");
		return "redirect:/loginpage";
	}
	
	
	
}
