package com.fantasyspot.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.fantasyspot.models.User;
import com.fantasyspot.models.dao.UserDao;


public abstract class AbstractController {

    @Autowired
    protected UserDao userDao;

    private static final String errorHTML = "error";
    private static final String error = "message";

    public static final String userKey = "user_id";

    public String displayError(String message, Model model) {
        model.addAttribute(error, message);
        return errorHTML;
    }

    public User getUserFromSession(HttpServletRequest request){
        int userId = (int) request.getSession().getAttribute(userKey);
        return userDao.findByUid(userId);
    }
    
    public String getUsernameFromSession(HttpServletRequest request) {
    	User user;
    	String username;
    	try {
    		user = this.getUserFromSession(request);
    		username = user.getUserName();
    	} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
    	}
    	return username;
    }

}
