package org.myfirst.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.myfirst.domain.FirstThing;
import org.myfirst.domain.Thing;
import org.myfirst.dto.Mapper;
import org.myfirst.dto.RecommendationDto;
import org.myfirst.dto.UserDto;
import org.myfirst.service.ThingService;
import org.myfirst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ToDoController {

	@Autowired
	private ThingService thingService;

	@Autowired
	private UserService userService;
	
	@RequestMapping("/todo")
	public String viewToDo(Model model, HttpServletRequest request) {
		UserDto loggedUser = (UserDto)request.getSession().getAttribute("loggedUser");
		if (loggedUser == null) {
			
			return "login";
		}
		List<RecommendationDto> recommendations = userService.getThingsRecommendation(loggedUser.getUsername());
		model.addAttribute("recommendations", recommendations);
		return "/todo";
	}
	
    @RequestMapping(value = "/addToDo")
    public String addToDo(Model model, @RequestParam("tags") String tags, @RequestParam("dueDate") String dueDate,
    		@RequestParam("title") String title, HttpServletRequest request) {

		String username = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		
		String[] tagsArray = tags.split(" |,|;|#");
		Set<Thing> things = thingService.addNewThingsByTags(tagsArray);

		FirstThing first = new FirstThing();
		first.setTitle(title);
		first.setTags(things);

		UserDto existingUserDto = Mapper.map(thingService.addNewToDoByUser(first, username, dueDate), 1, false);
		request.getSession().setAttribute("loggedUser", existingUserDto);
        return "redirect:todo";
    }
    
    @RequestMapping(value = "/deleteToDo")
    public String deleteToDo(Model model, @RequestParam("firstThingId") String firstThingId, 
    		HttpServletRequest request) {

		String username = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		
		FirstThing first = thingService.findFirstThingById(new Long(firstThingId));		
		
		UserDto existingUserDto = Mapper.map(thingService.removeToDoFromUser(first, username), 1, false);
		request.getSession().setAttribute("loggedUser", existingUserDto);
        return "redirect:todo";
    }
    
    @RequestMapping(value = "/removeRecommendation")
    public String removeRecommendation(Model model, @RequestParam("id") String id,
    		@RequestParam("action") String action, HttpServletRequest request) {
    	UserDto existingUserDto = null;
		String username = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		
		if ("add".equals(action)) {
			existingUserDto = Mapper.map(thingService.userMarkedThingAsToDo(id, username), 1, false);	
			FirstThing first = thingService.findFirstThingById(new Long(id));
			String tags = "";
			for (Thing tag: first.getTags()) {
				tags += tag.getTag() + " ";
			}
			model.addAttribute("title", first.getTitle());
			model.addAttribute("tags", tags);
		} else if ("notInterested".equals(action)) {		
			existingUserDto = Mapper.map(thingService.userNotInterestedInThing(id, username), 1, false);
		} else if ("done".equals(action)) {
			existingUserDto = Mapper.map(thingService.userAlreadyDoneThing(id, username), 1, false);
		}
		request.getSession().setAttribute("loggedUser", existingUserDto);
		List<RecommendationDto> recommendations = userService.getThingsRecommendation(username);
		model.addAttribute("recommendations", recommendations);
        return "/todo";
    }
}
