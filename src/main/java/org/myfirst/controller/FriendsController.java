package org.myfirst.controller;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.myfirst.domain.User;
import org.myfirst.dto.Mapper;
import org.myfirst.dto.UserDto;
import org.myfirst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FriendsController {

	@Autowired
	private UserService userService;
	
    @RequestMapping(value = "/following", method = RequestMethod.GET, headers = "Accept=text/html")
    public String findFollowing(Model model, HttpServletRequest request) {
    	UserDto user = (UserDto)request.getSession().getAttribute("loggedUser");
    	Set<UserDto> following = user.getFollowing();
        if (following != null && !following.isEmpty()) {
            model.addAttribute("users", following);
        } else {
            model.addAttribute("users", Collections.emptyList());
        }
        return "following";
    }
    
    @RequestMapping(value = "/followers", method = RequestMethod.GET, headers = "Accept=text/html")
    public String findFollowers(Model model, HttpServletRequest request) {
    	UserDto user = (UserDto)request.getSession().getAttribute("loggedUser");
    	Set<UserDto> followers = user.getFollowers();
        if (followers != null && !followers.isEmpty()) {
            model.addAttribute("users", followers);
        } else {
            model.addAttribute("users", Collections.emptyList());
        }
        return "followers";
    }
    
    @RequestMapping(value = "/addFriend", method = RequestMethod.GET, headers = "Accept=text/html")
    public String addFriend(Model model, @RequestParam("username") String username, HttpServletRequest request) {
		String loggedUser = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		
		User followed = userService.findUserByUsername(username);
		
		UserDto existingUserDto = Mapper.map(userService.follow(followed, loggedUser), 1, false);
		request.getSession().setAttribute("loggedUser", existingUserDto);
        return "redirect:/profile/" + username;
    }
    
    @RequestMapping(value = "/removeFriend", method = RequestMethod.GET, headers = "Accept=text/html")
    public String removeFriend(Model model, @RequestParam("username") String username, HttpServletRequest request) {
		String loggedUser = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		
		User followed = userService.findUserByUsername(username);
		
		UserDto existingUserDto = Mapper.map(userService.unfollow(followed, loggedUser), 1, false);
		request.getSession().setAttribute("loggedUser", existingUserDto);
        return "redirect:/profile/" + username;
    }
}
