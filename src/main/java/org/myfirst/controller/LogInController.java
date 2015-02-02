package org.myfirst.controller;

import javax.servlet.http.HttpServletRequest;

import org.myfirst.domain.Role;
import org.myfirst.domain.User;
import org.myfirst.dto.Mapper;
import org.myfirst.dto.UserDto;
import org.myfirst.service.DBPopulatorService;
import org.myfirst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/login")
@SessionAttributes("loggedUser")
public class LogInController {

	
	@Autowired
	private UserService service;
	
	@Autowired
	private DBPopulatorService dbService;
	
	private UserDto loggedUser;
	
	@RequestMapping
	public String getLoginPage() {
		populateDB(false);
		cleanDB(false);
		return "login";
	}
	
	@RequestMapping(value="/signin")
	public @ResponseBody String signIn(@RequestParam String email, 
			@RequestParam String password, ModelMap model) {
		User loggedUser = service.verifyUser(email, password);
		if (loggedUser == null) {
			model.addAttribute("error", "Error occured!");
			return "login";
		}
		UserDto userDto = new UserDto();
		userDto = Mapper.map(loggedUser, 1, false);
		model.addAttribute("loggedUser", userDto);
	    return "home";
	}
	
	@RequestMapping(value="/register")
	public @ResponseBody String register(@RequestParam String username, 
			@RequestParam String email,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String password,
			@RequestParam String repeatPassword,
			HttpServletRequest request) {
		
		if (password.compareTo(repeatPassword) != 0) {
			return "login";
		}
		
		User newUser = new User();
		
		newUser.setEmail(email);
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		Role newRole = new Role();
		newRole.setRole(2);
		newUser.setRole(newRole);
		
		UserDto newUserDto = Mapper.map(service.register(newUser), 1, false);
		request.getSession().setAttribute("loggedUser", newUserDto);
		return "home";
	}
	
	private void populateDB(boolean populateDB) {
		if (populateDB) {
			dbService.cleanDb();
			dbService.populateDatabase();
		}
	}
	
	private void cleanDB(boolean cleanDB) {
		if (cleanDB) {
			dbService.cleanDb();
		}
	}
}
