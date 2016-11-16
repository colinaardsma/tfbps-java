package com.colinaardsma.tfbps.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.dao.UserDao;

@Controller
public class AdminController extends AbstractController {
	
	@Autowired
	UserDao userdao;
	
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(){
        return "admin";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String admin(HttpServletRequest request, Model model){
        List<User> users = userDao.findAll();
        
		// get user from session
    	User user = this.getUserFromSession(request);

    	model.addAttribute("user", user);
        model.addAttribute("users", users);
        
        return "admin";
    }

}
