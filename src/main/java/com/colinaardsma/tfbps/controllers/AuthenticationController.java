package com.colinaardsma.tfbps.controllers;

import javax.servlet.http.HttpServletRequest;

import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.util.PasswordHash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request, Model model){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}
		
    	model.addAttribute("currentUser", currentUser);
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String userName, String password, String confirmPassword, HttpServletRequest request, Model model) {

        // Perform some validation
        User existingUser = userDao.findByUserName(userName);
        if (existingUser != null) {
            return this.displayError(
                    "The username " + userName + " is taken. Please choose a different username", model);
        }
        else if (!password.equals(confirmPassword)) {
            return this.displayError("Passwords do not match. Try again.", model);
        }

        // Validation passed. Create and persist a new User entity
        User newUser = new User(userName, password);
        userDao.save(newUser);
        
        // set user in session
        request.getSession().setAttribute(userKey, newUser.getUid());

    	model.addAttribute("currentUser", userName);
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String userName, String password, HttpServletRequest request, Model model){

        User user = userDao.findByUserName(userName);

        // User is invalid
        if (user == null) {
            return this.displayError("Invalid username.", model);
        } else if (!PasswordHash.isValidPassword(password, user.getPassHash())) {
            return this.displayError("Invalid password.", model);
        }

        // User is valid; set in session
        request.getSession().setAttribute(userKey, user.getUid());

    	model.addAttribute("currentUser", userName);
        return "index";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "index";
    }

}