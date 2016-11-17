package com.colinaardsma.tfbps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.colinaardsma.tfbps.controllers.AbstractController;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.dao.UserDao;


public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    	
        // basic user page list
        List<String> basicPages = Arrays.asList("/fpprojb", "/fpprojp", "/blog", "/welcome");
        // basic user group access list
        List<String> basicAuthList = Arrays.asList("admin", "power-user", "blogger", "commissioner", "basic");
      
        // commish user page list
        List<String> commishPages = Arrays.asList("/test"); // confirm user group once this is populated with actual pages
        // commish user group access list
        List<String> commishAuthList = Arrays.asList("admin", "power-user", "commissioner");
        
        // blogger user page list
        List<String> bloggerPages = Arrays.asList("/blog/new_post", "/blog/modify", "/blog/delete", "/blog/archive"); // add re for individual posts and user posts // confirm user group once this is populated with working pages
        // blogger user group access list
        List<String> bloggerAuthList = Arrays.asList("admin", "power-user", "blogger");
        
        // power-user user page list
        List<String> puPages = Arrays.asList("/fpprojbdatapull", "/fpprojpdatapull"); // change to re
        // power-user user group access list
        List<String> puAuthList = Arrays.asList("admin", "power-user");
        
        // admin page list
        List<String> adminPages = Arrays.asList("/admin");
        // admin user group access list
        List<String> adminAuthList = Arrays.asList("admin");

        // all authorized pages list
        List<String> allPages = new ArrayList<String>();
        allPages.addAll(basicPages);
        allPages.addAll(bloggerPages);
        allPages.addAll(commishPages);
        allPages.addAll(puPages);
        allPages.addAll(adminPages);

        // if requesting page available to logged off users: carry on
        if (!allPages.contains(request.getRequestURI())) {
        	return true;
        }
        
        // if requesting page that requires login and no user in session: redirect to login
        Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userKey);
        if (userId == null) {
       		response.sendRedirect("/login");
       		return false;
        }
        
        // get user group
       	User user = userDao.findByUid(userId);
       	String userGroup = user.getUserGroup();

       	// check page request against user group
       	if (basicPages.contains(request.getRequestURI()) && !basicAuthList.contains(userGroup)) {
        	response.sendRedirect("/login");
        	return false;
        } else if (commishPages.contains(request.getRequestURI()) && !commishAuthList.contains(userGroup)) {
        	response.sendRedirect("/login");
        	return false;
        } else if (bloggerPages.contains(request.getRequestURI()) && !bloggerAuthList.contains(userGroup)) {
        	response.sendRedirect("/login");
        	return false;
        } else if (puPages.contains(request.getRequestURI()) && !puAuthList.contains(userGroup)) {
        	response.sendRedirect("/login");
        	return false;
        } else if (adminPages.contains(request.getRequestURI()) && !adminAuthList.contains(userGroup))  {
        	response.sendRedirect("/login");
        	return false;
        } 

        return true;
    }
    
}