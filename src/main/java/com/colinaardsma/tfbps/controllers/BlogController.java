package com.colinaardsma.tfbps.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colinaardsma.tfbps.models.Post;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.dao.PostDao;
import com.colinaardsma.tfbps.models.dao.UserDao;

@Controller
public class BlogController extends AbstractController {

	@Autowired // injects instance of interface listed below
	private UserDao userDao;
	
	@Autowired // injects instance of interface listed below
	private PostDao postDao;
	
	@RequestMapping(value = "/blog/bloggers")
	public String bloggerList(HttpServletRequest request, Model model){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// fetch users with blog posts and pass to template
		List<User> users = userDao.findAll();
		List<User> bloggers = new ArrayList<User>();
		for (User u : users) {
			if (u.getPosts().size() > 0) {
				bloggers.add(u);
			}
		}
		
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("bloggers", bloggers);
        model.addAttribute("user", user);
		
		return "bloggers";
	}
	
	@RequestMapping(value = "/blog")
	public String blog(HttpServletRequest request, Model model) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// fetch posts and pass to template
		List<Post> posts = postDao.findAllByOrderByCreatedDesc();
		
		// TODO - implement pagination
		
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("posts", posts);
        model.addAttribute("user", user);
		
		return "blog";
	}

	@RequestMapping(value = "/blog/archive")
	public String blogArchive(HttpServletRequest request, Model model) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// fetch posts and pass to template
		List<Post> posts = postDao.findAllByOrderByCreatedDesc();
		
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("posts", posts);
        model.addAttribute("user", user);
		
		return "blog";
	}

}
