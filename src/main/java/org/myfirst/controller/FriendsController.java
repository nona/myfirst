package org.myfirst.controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.myfirst.domain.Thing;
import org.myfirst.domain.User;
import org.myfirst.dto.Mapper;
import org.myfirst.dto.UserDto;
import org.myfirst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FriendsController {

	@Autowired
	private UserService service;
	
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
}
