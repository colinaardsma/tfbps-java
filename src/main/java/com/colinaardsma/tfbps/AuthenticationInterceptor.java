package com.colinaardsma.tfbps;

import java.io.IOException;
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
    	
        // basic user access list
        List<String> basicAuthPages = Arrays.asList("/fpprojb", "/fpprojp", "/blog", "/welcome");
        
        // commish user access list
        List<String> commishAuthPages = Arrays.asList("/test");

        // blogger user access list
        List<String> bloggerAuthPages = Arrays.asList("/blog/new_post", "/blog/modify", "/blog/delete", "/blog/archive"); // add re for individual posts and user posts
        
        // power-user user access list
        List<String> puAuthPages = Arrays.asList("/fpprojbdatapull", "/fpprojpdatapull"); // change to re
        
        // admin access list
        List<String> adminAuthPages = Arrays.asList("/admin");
        
        // TODO - this is not done correctly, fix 
        // expand access groups to hold lower levels as needed
        // admin
        adminAuthPages.addAll(basicAuthPages);
        adminAuthPages.addAll(commishAuthPages);
        adminAuthPages.addAll(bloggerAuthPages);
        adminAuthPages.addAll(puAuthPages);
        
        // power-user
        puAuthPages.addAll(basicAuthPages);
        puAuthPages.addAll(commishAuthPages);
        puAuthPages.addAll(puAuthPages);

        // blogger
        puAuthPages.addAll(basicAuthPages);

        // commish
        puAuthPages.addAll(basicAuthPages);


        Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userKey);
        User user = userDao.findByUid(userId);
        String userGroup = user.getuserGroup();

        
        // verify user access group
        if (basicAuthPages.contains(request.getRequestURI())) {
        	if (userGroup != "basic") {
                response.sendRedirect("/");
            	return false;
        	}
        } else if (commishAuthPages.contains(request.getRequestURI())) {
        	if (userGroup != "commish") {
                response.sendRedirect("/");
            	return false;
        	}
        } else if (bloggerAuthPages.contains(request.getRequestURI())) {
        	if (userGroup != "blogger") {
                response.sendRedirect("/");
            	return false;
        	}
        } else if (puAuthPages.contains(request.getRequestURI())) {
        	if (userGroup != "power-user") {
                response.sendRedirect("/");
            	return false;
        	}
        } else if (adminAuthPages.contains(request.getRequestURI())) {
        	if (userGroup != "admin") {
                response.sendRedirect("/");
            	return false;
        	}
        } 

        return true;
    }

}