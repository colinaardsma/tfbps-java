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
    	
    	// logged out user access list
        List<String> outAuthPages = Arrays.asList("/login", "/register", "/logout", "/");
        // basic user access list
        List<String> basicAuthPages = Arrays.asList("/fpprojb", "/fpprojp", "/blog");
        // commish user access list
        
        // blogger user access list
        
        // power-user user access list
        
        // admin access list
        List<String> adminAuthPages = Arrays.asList("/admin");
        adminAuthPages.addAll(basicAuthPages);

        Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userKey);
        User user = userDao.findByUid(userId);
        String userGroup = user.getuserGroup();

        
//        // require sign-in for all but outAuthPages
//        if ( !outAuthPages.contains(request.getRequestURI()) ) {
//            if (userId == null) {
//                response.sendRedirect("/login");
//                return false;
//            }
//            // If no ID present in session, redirect to login
//            if (user == null) {
//                response.sendRedirect("/login");
//                return false;
//            }
//        }
        
        // verify user access group
        if (adminAuthPages.contains(request.getRequestURI())) {
        	if (userGroup != "admin") {
                response.sendRedirect("/");
            	return false;
        	}
        }

        return true;
    }

}