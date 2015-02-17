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
public class SearchController {

	@Autowired
	private UserService userService;
	
	
    @RequestMapping(value = "/search", method = RequestMethod.GET, headers = "Accept=text/html")
    public String findRecords(Model model, @RequestParam("q") String query, HttpServletRequest request) {
        if (query != null && !query.isEmpty()) {
        	String username = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
        	User user = userService.findUserByUsername(username);
        	Set<UserDto> users = Mapper.mapUsersSet(userService.searchFor(query), 1, user.getFollows());
            model.addAttribute("users", users);
            model.addAttribute("seeker", user.getUsername());
            model.addAttribute("query", query);
        } else {
            model.addAttribute("users", Collections.emptyList());
        }
        model.addAttribute("query", query);
        return "/results";
    }
    
}
