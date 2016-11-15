package com.colinaardsma.tfbps.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.dao.UserDao;

@Controller
public class AdminController extends AbstractController {
	
	@Autowired
	UserDao userdao;
	
    @RequestMapping(value = "/admin")
    public String admin(HttpServletRequest request, Model model){
    	
    	// determine if user is part of the admin group
        Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userKey);
        User user = userDao.findByUid(userId);
        
//        // if not send back to index
//        if (user.getuserGroup() != "admin") {
//        	String error = "Unauthorized";
//        	model.addAttribute("error", error);
//        	return "redirect:index";
//        }
        
        // if so carry on
        return "admin";
    }

}
