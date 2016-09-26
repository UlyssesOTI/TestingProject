package ua.lviv.lgs.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
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
import ua.lviv.lgs.dto.RoleDTO;
import ua.lviv.lgs.dto.TestDTO;
import ua.lviv.lgs.dto.UserDTO;
import ua.lviv.lgs.service.CoursesService;
import ua.lviv.lgs.service.GroupsService;
import ua.lviv.lgs.service.QuestionsService;
import ua.lviv.lgs.service.ResultsService;
import ua.lviv.lgs.service.RolesService;
import ua.lviv.lgs.service.ThemesService;
import ua.lviv.lgs.service.UsersService;

@Controller
public class UserController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private CoursesService courseService;
	@Autowired
	private GroupsService groupsService;
	@Autowired
	private ResultsService resultsService;
	@Autowired
	private ThemesService themeService;
	@Autowired 
	private QuestionsService questionService;
	// Logger for UserController Class
	private static Logger log = Logger.getLogger(UserController.class);
	
	/**
	 * this method fill data in new_user.jsp
	 * 
	 * @param model
	 *            - model in which we add attributes
	 * @param principal
	 *            - object, which represent logged user
	 * 
	 *            attribute rolesDTO - roles list attribute courses - list
	 *            courses
	 * 
	 * @return simple string
	 */

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/new_user", method = RequestMethod.GET)
	public String registeringNewUser(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			List<RoleDTO> rolesDTO = rolesService.findAll();
			if (principal.getName().equalsIgnoreCase("admin")) {
				model.addAttribute("rolesDTO", rolesDTO);
			} else {
				UserDTO userDTO = usersService.findById(Integer.parseInt(principal.getName()));
				Iterator<RoleDTO> iter = rolesDTO.iterator();
				while (iter.hasNext()) {
					RoleDTO role = iter.next();
					if (userDTO.getRole().equals(role.getName())) {
						iter.remove();
						model.addAttribute("rolesDTO", rolesDTO);
						break;
					} else {
						iter.remove();
					}
				}
			}
			List<CourseDTO> courses = courseService.findAll();
			List<GroupDTO> groups = groupsService.findAll();
			model.addAttribute("courses", courses).addAttribute("groups", groups);
		}
		log.info("[INFO] User with id="+principal.getName()+" sterted created new user");
		return "users-new";
	}

	/**
	 * this method grab all data from new_user.jsp and create new user in
	 * database
	 * 
	 * @param model
	 *            - model in which we add attributes
	 * @param name
	 *            - new user name
	 * @param secondname
	 *            - new user secondname
	 * @param mail
	 *            - new user email
	 * @param phone
	 *            - new user phone
	 * @param password
	 *            - new user password
	 * @param roleId
	 *            - new user role
	 * @param groupId
	 *            - new user group
	 * @param course
	 *            - new user course
	 * 
	 * @return simple string
	 */

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/new_user", method = RequestMethod.POST)
	public String creatingUser(Model model, Principal principal, RedirectAttributes redirectAttributes, @RequestParam(value = "name") String name,
			@RequestParam(value = "secondname") String secondname, @RequestParam(value = "mail") String mail,
			@RequestParam(value = "phone") String phone, @RequestParam(value = "password") String password,
			@RequestParam(value = "roleId") int roleId, @RequestParam(value = "groupId") String groupId,
			@RequestParam(value = "courseId") String course) {
		
		if(usersService.isEmailUnique(mail)){
			if(usersService.isPhoneUnique(phone)){
				usersService.add(name, secondname, mail, phone, password, roleId, groupId, course);
				log.info("[INFO] created user with email: " + mail +", name: "+name+" , secondname:"+secondname +" ,phone: " + phone+" ,roleId="+roleId+" ,courseId="+course+" ,grourId="+groupId);
				return "redirect:/view_users";
			}else{
				redirectAttributes.addFlashAttribute("phoneMessage", true);
				log.error("[ERROR] tryed to create user with phone like "+ phone + " what alredy exist in database");
				return "redirect:/new_user";
			}
		}else{
			redirectAttributes.addFlashAttribute("emailMessage", true);
			log.error("[ERROR] tryed to create user with email like " + mail + " what alredy exist in database");
			return "redirect:/new_user";
		}
	}

	/**
	 * this method fill all data to edit_my_info.jsp
	 * 
	 * @param model
	 *            - model in which we add attributes
	 * @param principal
	 *            - object, which represent logged user
	 * 
	 *            attribute userDTO - contains all data logged user
	 * 
	 * @return simple string
	 */

	@Secured({ "ROLE_MANAGER", "ROLE_TEACHER", "ROLE_STUDENT" })
	@RequestMapping(value = "/edit_my_info", method = RequestMethod.GET)
	public String editingOwnUser(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			UserDTO userDTO = usersService.findById(Integer.parseInt(principal.getName()));
			model.addAttribute("userDTO", userDTO);
			log.info("[INFO] user with id="+principal.getName()+"editing himself. Old value = name: "+userDTO.getName()+" ,secondname: "+userDTO.getSecondname()+" , email: "+userDTO.getEmail()+" ,phone: "+userDTO.getPhone());
		}
		return "users-editOwnUser";
	}

	/**
	 * this method grab all data from .jsp and if passwords are correct push
	 * their to database
	 * 
	 * @param model
	 *            - model in which we add attributes
	 * @param principal
	 *            - object, which represent logged user
	 * @param redirectAttributes
	 *            - return message is user enter wrong answer
	 * @param name
	 *            - new user name
	 * @param id
	 *            - edited user id
	 * @param secondname
	 *            - new user secondname
	 * @param email
	 *            - new user email
	 * @param phone
	 *            - new user phone
	 * @param password
	 *            - current user password
	 * @param currentPassword
	 *            - current user password
	 * @param newPassword
	 *            - new user password
	 * 
	 * @return simple string
	 */

	@Secured({ "ROLE_MANAGER", "ROLE_TEACHER", "ROLE_STUDENT" })
	@RequestMapping(value = "/edit_my_info", method = RequestMethod.POST)
	public String updatingOwnUser(Model model, Principal principal, RedirectAttributes redirectAttributes,
			@RequestParam(value = "name") String name, @RequestParam(value = "id") int id,
			@RequestParam(value = "secondname") String secondname, @RequestParam(value = "email") String email,
			@RequestParam(value = "phone") String phone, @RequestParam(value = "password") String password,
			@RequestParam(value = "currentPassword") String currentPassword,
			@RequestParam(value = "newPassword") String newPassword) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			boolean passwordCheck = usersService.editMyUser(id, name, secondname, email, phone, password,
					currentPassword, newPassword);
			if (!passwordCheck) {
				log.warn("[WARN] user with id=" + principal.getName() + " trying edit himself but faild his password");
				redirectAttributes.addFlashAttribute("message", true);
				return "redirect:/edit_my_info";
			}
		}
		log.info("[INFO] user with id="+principal.getName()+" editing himself. New value email: "+email+", phone: "+phone+", name: "+name+", secondname: "+secondname);
		return "redirect:/view_user_" + id;
	}

	/**
	 * this method fill data to edit_user.jsp
	 * 
	 * @param model
	 *            - model in which we add attributes
	 * @param principal
	 *            - object, which represent logged user
	 * @param id
	 *            - edited user id
	 * 
	 *            attribute courseDTO - it searched user are teacher, this
	 *            attribute contains List<CourseDTO> of all courses
	 * 
	 *            attribute userDTO - contains all data edited user
	 * 
	 *            attribute rolesDTO - contains List<RoleDRO> with all roles
	 * 
	 * @return - simple string
	 */

	@SuppressWarnings("unchecked")
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/edit_user_{id}", method = RequestMethod.GET)
	public String editingUser(Model model, Principal principal, @PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			if (!(principal.getName().equalsIgnoreCase("admin"))) {
				boolean access = usersService.hasPrincipalAccessToEditUser(id, principal.getName());
				if (!access) {
					log.warn("[WARN] user with id="+principal.getName()+" trying to edit user with id="+id+" but faild (havent acces)");
					return "403";
				}
			}
			UserDTO userDTO = usersService.findById(id);
			if(userDTO == null){
				log.error("[ERROR] user with id="+principal.getName()+" trying to edit not existing user");
				return "404";
			}else{

				if (userDTO.getRoleId() == 2) {
					List<CourseDTO> courseDTO = courseService.findAll();
					List<CourseDTO> teacherCourseDTO = usersService.findTeacherCourses(id);
					List<CourseDTO> uncheckedCourses = (List<CourseDTO>) themeService.findAllUnDuplicated(teacherCourseDTO, courseDTO);
					model.addAttribute("uncheckedCourses", uncheckedCourses).addAttribute("teacherCourses", teacherCourseDTO);
				}
				if(userDTO.getRoleId() == 3){
					List<GroupDTO> allGroups = groupsService.findAll();
					List<GroupDTO> studentGroups = usersService.findUserGroupsDto(userDTO.getId());
					List<GroupDTO> uncheckedGroups = (List<GroupDTO>) themeService.findAllUnDuplicated(studentGroups, allGroups);
					model.addAttribute("studentGroups",studentGroups).addAttribute("othersGroups", uncheckedGroups);
				}
				model.addAttribute("userDTO", userDTO);
			}
			log.info("[INFO] user with id="+principal.getName()+" edithing user with id="+id+" Old value = name: "+userDTO.getName()+", secondname: "+userDTO.getSecondname()+", email: "+userDTO.getEmail()+", phone: "+userDTO.getPhone()+", groups: "+userDTO.getGroups()+", courses: "+userDTO.getCourses());
			return "users-edit";
			}
	}

	/**
	 * this method grab all data from edit_user.jsp and change user data to
	 * this, new data
	 * 
	 * @param model
	 *            - model in which we add attributes
	 * @param name
	 *            - edited user name
	 * @param id
	 *            - edited user id
	 * @param secondname
	 *            - edited user secondname
	 * @param email
	 *            - edited user email
	 * @param phone
	 *            - edited user phone
	 * @param groupNamesArray
	 *            - array of user groups
	 * @param courseNamesArray
	 *            - array of users courses
	 * @return - simple string
	 */

	@RequestMapping(value = "/edit_user", method = RequestMethod.POST)
	public String updatingUser(Model model, Principal principal, RedirectAttributes redirectAttributes, @RequestParam(value = "name") String name,
			@RequestParam(value = "id") int id, @RequestParam(value = "secondname") String secondname,
			@RequestParam(value = "email") String email, @RequestParam(value = "phone") String phone,
			@RequestParam(value = "groupId") String groupNamesArray,
			@RequestParam(value = "courseId") int[] courseNamesArray, @RequestParam(value = "emailischange") String emailIsChange, @RequestParam(value = "phoneischange") String phoneIsChange) {
		
		Boolean emailChange = Boolean.parseBoolean(emailIsChange);
		Boolean phoneChange = Boolean.parseBoolean(phoneIsChange);
		boolean unUniqueEmail = false, unUniquePhone = false;
		if(emailChange){
			if(usersService.isEmailUnique(email)){
				unUniqueEmail = false;
			}else{
				unUniqueEmail = true;
			}
		}
		if(phoneChange){
			if(usersService.isPhoneUnique(phone)){
				unUniquePhone = false;
			}else{
				unUniquePhone = true;
			}
		}if(unUniqueEmail){
			log.warn("[WARN] user witn id="+principal.getName()+" trying edit user with id="+id+" but fails because email doesn't unique");
			redirectAttributes.addFlashAttribute("emailMessage", true);
			return "redirect:/edit_user_"+id;
		}else if(unUniquePhone){
			log.warn("[WARN] user with id="+principal.getName()+" trying edit user with id="+id+" but faild because phone number doesn't unique");
			redirectAttributes.addFlashAttribute("phoneMessage", true);
			return "redirect:/edit_user_"+id;
		}else{
			log.info("[INFO] user wih id="+principal.getName()+" edited user with id="+id+" New value = name: "+ name+", secondname: "+secondname+", email: "+email+", phone: "+phone+", groups: "+groupNamesArray+", courses: "+courseNamesArray);
			usersService.edit(id, name, secondname, email, phone, groupNamesArray, courseNamesArray);
			return "redirect:/view_user_" + id;
		}
	}

	/**
	 * this method delete user from database
	 * 
	 * @param principal
	 *            - object, which represent logged user
	 * @param id
	 *            - integer value which unique for every user
	 * @return - simple string
	 */

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/delete_user_{id}", method = RequestMethod.GET)
	public String deletingUser(Principal principal, @PathVariable int id) {
		if (principal == null) {
			return "redirect:/loginpage";
		} else {
			log.info("[INFO] user witn id="+principal.getName()+" deleting user with id="+id);
			usersService.delete(id);
		}
		return "redirect:/";
	}

	/**
	 * this method fill data to view_user.jsp
	 * 
	 * @param model
	 *            - model in which we add attributes
	 * @param principal
	 *            - object, which represent logged user
	 * @param userId
	 *            - ID of user, which page user want view
	 * 
	 *            attribute "principal" - if user isn't admin, this attribute
	 *            represent users id
	 * 
	 *            attribute "user" - represent searched user userDTO
	 * 
	 *            attribute "rights" - boolean parameter, which represent: have
	 *            logged user enough rights for edit searcher user
	 * 
	 * @return simple string
	 */

	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER", "ROLE_STUDENT" })
	@RequestMapping(value = "/view_user_{userId}", method = RequestMethod.GET)
	public String viewUser(Model model, Principal principal, @PathVariable int userId) {
		
		List<TestDTO> userTests = new ArrayList<TestDTO>();
		
		if (principal == null) {
			return "redirect:/loginpage";
		}
		// Права юзера
		Boolean userRightsToEdit = false;
		UserDTO user = usersService.findById(userId);
		if(user == null){
			log.error("[ERROR] user with id="+principal.getName()+" trying view user with id="+userId+" but faild (viewed user dosn't exist)");
			return "404";
		}else{

			if ((principal.getName().equalsIgnoreCase("admin")) || (usersService.findById(Integer.parseInt(principal.getName())).getRoleId() == 2) || (usersService.findById(Integer.parseInt(principal.getName())).getRoleId() == 1)) {
				userTests = usersService.findUsersTestDTO(user.getId());
			}
			if (!(principal.getName().equalsIgnoreCase("admin"))) {
				int principalId = Integer.parseInt(principal.getName());
				model.addAttribute("principal", principalId);
				userRightsToEdit = usersService.hasPrincipalAccessToEditUser(userId, principal.getName());
				if (Integer.parseInt(principal.getName()) == user.getId()) {
					userTests = usersService.findUsersTestDTO(user.getId());
				}
				boolean access = usersService.hasPrincipalAccessToViewUser(userId, principal.getName());
				if (!access) {
					log.error("[ERROR] user with id="+principal.getName()+" trying view user with id="+userId+" but faild (havn't acces)");
					return "403";
				}
			} else {
				userRightsToEdit = true;
			}
			if (user.getRole().equalsIgnoreCase("student")) {
				List<GroupDTO> groups = groupsService.findGroupByUserId(userId);
				model.addAttribute("userGroups", groups);
			} else if (user.getRole().equalsIgnoreCase("teacher")) {
				List<CourseDTO> courses = courseService.findUserCourses(userId);
				model.addAttribute("userCourses", courses);
			}

			if (userTests.size() != 0) {
				model.addAttribute("userTests", userTests);
			}
			model.addAttribute("user", user);
			model.addAttribute("userRightsToEdit", userRightsToEdit);
			model.addAttribute("AllTestResults", resultsService.findAllByUserId("" + userId, principal.getName()));
			return "users-view";
		}
	}
	
	

	/**
	 * this method represent page with search input, what we use for searching
	 * 
	 * @param model
	 *            - model in which we add attributes
	 * 
	 *            attribute "roles" - List <RolesDTO> with all roles in it
	 * 
	 *            attribute "users" - List <UsersDTO> with all users in it
	 * 
	 * @return - simple string
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TEACHER" })
	@RequestMapping(value = "/view_users", method = RequestMethod.GET)
	public String userPage(Model model, Principal principal) throws NumberFormatException, IOException {
		if (principal == null) {
			return "redirect:/loginpage";
		}
		model.addAttribute("users", usersService.findAllPaginated())
		.addAttribute("roles", rolesService.findAll())
		.addAttribute("pageCount", questionService.calculatepageCount(usersService.getUserbyNameAndRoleCount("", "%"), UsersService.ITEMS_PER_PAGE));
		return "users-all";
	}

}
