package org.myfirst.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.DefaultSessionAttributeStore;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/logout")
public class LogOutController {

	@RequestMapping
	public @ResponseBody ModelAndView logOut(WebRequest request, 
			DefaultSessionAttributeStore store, SessionStatus status) {
	    status.setComplete();
	    store.cleanupAttribute(request, "loggedUser");
	    return new ModelAndView("login");
	}
}
