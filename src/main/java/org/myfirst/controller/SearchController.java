package org.myfirst.controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.myfirst.domain.Thing;
import org.myfirst.domain.User;
import org.myfirst.dto.Mapper;
import org.myfirst.dto.UserDto;
import org.myfirst.service.ThingService;
import org.myfirst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ThingService thingService;
	
	
    @RequestMapping(value = "/search", method = RequestMethod.GET, headers = "Accept=text/html")
    public String findRecords(Model model, @RequestParam("q") String query) {
        if (query != null && !query.isEmpty()) {
        	Page<Thing> things = thingService.searchFor(query, new PageRequest(0, 50));
            model.addAttribute("things", Mapper.mapThingsList(things.getContent()));
        	Set<UserDto> users = Mapper.mapUsersSet(userService.searchFor(query), 1);
            model.addAttribute("users", users);
            model.addAttribute("query", query);
        } else {
            model.addAttribute("users", Collections.emptyList());
        }
        model.addAttribute("query", query);
        return "/results";
    }
    
    @RequestMapping(value = "/addFriend", method = RequestMethod.GET, headers = "Accept=text/html")
    public String addFriend(Model model, @RequestParam("username") String username, HttpServletRequest request) {
		String loggedUser = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		
		User followed = userService.findUserByUsername(username);
		
		UserDto existingUserDto = Mapper.map(userService.follow(followed, loggedUser), 1);
		request.getSession().setAttribute("loggedUser", existingUserDto);
        return "/profile/" + username;
    }
    
}
