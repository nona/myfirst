package org.myfirst.controller;

import javax.servlet.http.HttpServletRequest;

import org.myfirst.domain.Thing;
import org.myfirst.domain.User;
import org.myfirst.dto.Mapper;
import org.myfirst.dto.UserDto;
import org.myfirst.service.FTPFunctions;
import org.myfirst.service.ThingService;
import org.myfirst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="/myprofile")
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ThingService thingService;
	
	@RequestMapping
	public String getMyProfilePage() {

		return "myprofile";
	}
	
	@RequestMapping(value="/edit")
	public @ResponseBody String editProfile(
			@RequestParam String email,
			@RequestParam String firstName,
			@RequestParam String middleName,
			@RequestParam String lastName,
			@RequestParam String country,
			@RequestParam String dateOfBirth,
			HttpServletRequest request) {

		String username = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		User existingUser = new User();
		existingUser.setUsername(username);
		existingUser.setEmail(email);
		existingUser.setFirstName(firstName);
		existingUser.setMiddleName(middleName);
		existingUser.setLastName(lastName);
		existingUser.setDateOfBirth(dateOfBirth);
		existingUser.setCountry(country);
		
		UserDto existingUserDto = Mapper.map(userService.update(existingUser), 1);
		request.getSession().setAttribute("loggedUser", existingUserDto);
		return "home";
	}
	
	@RequestMapping(value="/photo")
	public String editProfilePicture(@RequestParam("file") MultipartFile file, 
			HttpServletRequest request){
		if (!file.isEmpty()) {
		    	String username = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		    	User existingUser = userService.findUserByUsername(username);

		    	String extension = "";
		    	String fileName = file.getOriginalFilename();
		    	int i = fileName.lastIndexOf('.');
		    	if (i >= 0) {
		    	    extension = fileName.substring(i);
		    	}
		        String name = existingUser.getId().toString() + "_" + existingUser.getMediaIndex() + extension;
				FTPFunctions ftp;
				try {
					ftp = new FTPFunctions("my1st.net", 21, "my1stne", "5I#%f8T$");
					String link = existingUser.getProfilePhotoLink();
					if (link == null || link.isEmpty()) {
						existingUser.incrementMediaIndex();
					} else {
						ftp.deleteFTPFile("/public_html/images/" + link.substring(link.lastIndexOf("/") + 1));
					}
					ftp.uploadFTPFile(file, name, "/public_html/images/");
					ftp.disconnect();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				existingUser.setProfilePhotoLink("http://my1st.net/images/" + name);

				UserDto existingUserDto = Mapper.map(userService.update(existingUser), 1);
				request.getSession().setAttribute("loggedUser", existingUserDto);
				
		        return "myprofile";
		} else {
		    return "myprofile";
		}
	}

	@RequestMapping(value="/password")
	public @ResponseBody String editPassword(@RequestParam String password,
			@RequestParam String repeatPassword,
			HttpServletRequest request) {
		
		if (!password.equals(repeatPassword)) {
			//TODO: add some error message
			return "myprofile";
		}

		String username = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		User existingUser = new User();
		existingUser.setUsername(username);
		existingUser.setPassword(password);
		
		UserDto existingUserDto = Mapper.map(userService.changePassword(existingUser), 1);
		request.getSession().setAttribute("loggedUser", existingUserDto);
		return "myprofile";
	}

	@RequestMapping(value="/interests")
	public @ResponseBody String addInterest(@RequestParam String newInterest,
			HttpServletRequest request) {
		String username = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		
		Thing newThing = new Thing();
		newThing.setTag(newInterest);
		
		UserDto existingUserDto = Mapper.map(thingService.addNewThingByUser(newThing, username), 1);
		request.getSession().setAttribute("loggedUser", existingUserDto);
		return "myprofile";
	}
	
	@RequestMapping(value="/removeInterest")
	public @ResponseBody String removeInterest(@RequestParam String removedInterest,
			HttpServletRequest request) {
		String username = ((UserDto)request.getSession().getAttribute("loggedUser")).getUsername();
		
		UserDto existingUserDto = Mapper.map(thingService.removeInterestFromUser(removedInterest, username), 1);
		request.getSession().setAttribute("loggedUser", existingUserDto);
		return "myprofile";
	}
	
}
