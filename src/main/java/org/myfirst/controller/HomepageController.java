package org.myfirst.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.myfirst.domain.FirstThing;
import org.myfirst.domain.Thing;
import org.myfirst.domain.User;
import org.myfirst.dto.Mapper;
import org.myfirst.dto.UserDto;
import org.myfirst.service.FTPFunctions;
import org.myfirst.service.ThingService;
import org.myfirst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomepageController {
	
	@Autowired
	private ThingService thingService;

	@Autowired
	private UserService userService;
	
	@RequestMapping("/home")
	public String getHomePage(HttpServletRequest request) {

		EndResult<FirstThing> m = thingService.findAllFirstThings();
		for (FirstThing t: m) {
			System.out.println(">>>" + t.getTitle() + " " + t.getDescription() + " " +t.getId());
		}
		
		List<User> u = userService.readAll();
		for (User t: u) {
			System.out.println(">>>" + t.getUsername() + " " + t.getFirstName() + " " +t.getId() + " " + t.getEmail());
			for (Thing ti: t.getInterests()) {
				System.out.println(">>>>>>>>" + ti.getTag());
			}
		}
//		thingService.deleteFirstThing((long)15);
//		thingService.deleteFirstThing((long)16);
//		thingService.deleteFirstThing((long)18);
//		thingService.deleteFirstThing((long)27);
		//thingService.deleteAllComments();
		//userService.deleteUserById((long)1);
//		userService.deleteUserById((long)3);
//		userService.deleteUserById((long)9);
//		userService.deleteUserById((long)59);
		UserDto loggedUser = (UserDto)request.getSession().getAttribute("loggedUser");
		if (loggedUser == null) {
			return "login";
		}
		return "home";
	}
	
	@RequestMapping("/myfirsts")
	public String getMyFirsts(HttpServletRequest request) {
		UserDto loggedUser = (UserDto)request.getSession().getAttribute("loggedUser");
		if (loggedUser == null) {
			return "login";
		}
		return "/myfirsts";
	}
	
	@RequestMapping("/profile/{username}")
	public String viewProfile(@PathVariable("username") String username, Model model, HttpServletRequest request) {
		UserDto loggedUser = (UserDto)request.getSession().getAttribute("loggedUser");
		if (loggedUser == null) {
			
			return "login";
		}
		UserDto toBeViewed = Mapper.map(userService.findUserByUsername(username), 1, false);
		if(loggedUser.getFollowing() != null && loggedUser.getFollowing().contains(toBeViewed)) {
			toBeViewed.setFollowing(true);
		} else {
			toBeViewed.setFollowing(false);
		}
		if (toBeViewed.getFollowing() != null && toBeViewed.getFollowing().contains(loggedUser)) {
			toBeViewed.setFollowingMe(true);
		} else {
			toBeViewed.setFollowingMe(false);
		}
		model.addAttribute("viewed", toBeViewed);
		return "/profile";
	}
	
	@RequestMapping("/*")
	public String getPage(HttpServletRequest request) {
		UserDto loggedUser = (UserDto)request.getSession().getAttribute("loggedUser");
		if (loggedUser == null) {
			return "redirect:login";
		}
		return "redirect:home";
	}
	
    @RequestMapping(value = "/addFirstThing")
    public String addFirstThing(Model model, @RequestParam("visibility") String visibility, 
    		@RequestParam("description") String description, @RequestParam("tags") String tags,
    		@RequestParam("title") String title, @RequestParam("file") MultipartFile file,
    		HttpServletRequest request) {

		String username = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		
		String[] tagsArray = tags.split(" |,|;|#");
		Set<Thing> things = thingService.addNewThingsByTags(tagsArray);
		boolean isImageAdded = false;
		String name = null;
		
		if (!file.isEmpty()) {
	    	User existingUser = userService.findUserByUsername(username);

	    	String extension = "";
	    	String fileName = file.getOriginalFilename();
	    	int i = fileName.lastIndexOf('.');
	    	if (i >= 0) {
	    	    extension = fileName.substring(i);
	    	}
	        name = existingUser.getId().toString() + "_" + existingUser.getMediaIndex() + extension;
			FTPFunctions ftp;
			try {
				ftp = new FTPFunctions("my1st.net", 21, "my1stne", "5I#%f8T$");
				ftp.uploadFTPFile(file, name, "/public_html/images/");
				ftp.disconnect();
				isImageAdded = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		FirstThing first = new FirstThing();
		first.setDescription(description);
		first.setVisibility(visibility);
		first.setTitle(title);
		first.setTags(things);
		if (isImageAdded) {
			first.setImage("http://my1st.net/images/" + name);
		}
		UserDto existingUserDto = Mapper.map(thingService.addNewFirstThingByUser(first, username, isImageAdded), 1, false);
		request.getSession().setAttribute("loggedUser", existingUserDto);
        return "/myfirsts";
    }
    
    @RequestMapping(value = "/deleteFirstThing")
    public String deleteFirstThing(Model model, @RequestParam("firstThingId") String firstThingId, 
    		HttpServletRequest request) {

		String username = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		
		FirstThing first = thingService.findMyFirstThingById(new Long(firstThingId));
		String link = first.getImage();
		
		if (first != null && link != null) {
			FTPFunctions ftp;
			try {
				ftp = new FTPFunctions("my1st.net", 21, "my1stne", "5I#%f8T$");
				ftp.deleteFTPFile("/public_html/images/" + link.substring(link.lastIndexOf("/") + 1));
				ftp.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		UserDto existingUserDto = Mapper.map(thingService.removeFirstThingFromUser(first, username), 1, false);
		request.getSession().setAttribute("loggedUser", existingUserDto);
        return "/myfirsts";
    }
	
}