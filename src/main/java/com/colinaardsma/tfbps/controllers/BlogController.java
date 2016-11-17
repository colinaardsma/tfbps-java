package com.colinaardsma.tfbps.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;

@Controller
public class BlogController extends AbstractController {

	@Autowired
	FPProjBatterDao fpProjBatterDao;
	
	@RequestMapping(value = "/blog")
    public String fpprojb(Model model, HttpServletRequest request){
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}
		

		
   	
    	model.addAttribute("currentUser", currentUser);

        return "blog";
    }

}
