package com.fantasyspot.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fantasyspot.models.User;
import com.fantasyspot.models.dao.UserDao;

@Controller
public class AdminController extends AbstractController {
	
	@Autowired
	UserDao userdao;
	
    @RequestMapping(value = "/admin")
    public String admin(String changeUsername, String authorization, HttpServletRequest request, Model model){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
    	// change user group
    	List<User> users = userDao.findAll();

		if (authorization != null && changeUsername != null) {
			User changeUser = userDao.findByUserName(changeUsername);
			changeUser.setUserGroup(authorization);
	        userDao.save(changeUser);
			String userConfirmation = changeUser.getUserName();
			String groupConfirmation = changeUser.getUserGroup();
			
			model.addAttribute("userConfirmation", userConfirmation);
			model.addAttribute("groupConfirmation", groupConfirmation);
		}
		
        model.addAttribute("users", users);
        model.addAttribute("user", user);
    	model.addAttribute("currentUser", currentUser);
        
        return "admin";
    }

}
