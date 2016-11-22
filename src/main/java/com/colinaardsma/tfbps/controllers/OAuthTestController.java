package com.colinaardsma.tfbps.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.BasicConfigurator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colinaardsma.tfbps.models.util.SignPostTest;

@Controller
public class OAuthTestController extends AbstractController {
	
	@RequestMapping(value = "/oauthtest")
    public String oauthtest(Model model, HttpServletRequest request){
		
//		// check for user in session
//		String currentUser = this.getUsernameFromSession(request);

		
		BasicConfigurator.configure();
		int data = 0;

		try {

			SignPostTest signPostTest = new SignPostTest();

			data = signPostTest.returnHttpData();

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		model.addAttribute("data", data);
//    	model.addAttribute("currentUser", currentUser);

        return "oauthtest";
    }

}
