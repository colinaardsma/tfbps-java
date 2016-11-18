package com.colinaardsma.tfbps.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.colinaardsma.tfbps.models.Post;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.dao.PostDao;
import com.colinaardsma.tfbps.models.dao.UserDao;

@Controller
public class PostController extends AbstractController {

	@Autowired // injects instance of interface listed below
	private UserDao userDao;
	
	@Autowired // injects instance of interface listed below
	private PostDao postDao;
	
	@RequestMapping(value = "/blog/new_post", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/new_post", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}

		// get author (user) from session
        Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userKey);
        User author = userDao.findByUid(userId);

        String title = request.getParameter("title");
        String body = request.getParameter("body");
  
        // make sure post is valid (has title and body)
        // TODO - allow linebreaks
  		String error = "Both Title and Body are Required!";
        if (title == "" || body == "") {
        	model.addAttribute("error", error);
        	return "newpost";
        }
        
        // save post in db
        Post post = new Post(title, body, author);
        postDao.save(post);
        
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("author", author);
		model.addAttribute("title", title);
		model.addAttribute("body", body);
        
		// route to permalink
        String username = author.getUserName();
        int uid = post.getUid();
        
		return "redirect:/blog/" + username + "/" + uid;		
	}
	
	@RequestMapping(value = "/blog/modify")
	public String modify(HttpServletRequest request, Model model) {
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}

		// fetch posts and pass to template
		List<Post> posts = postDao.findAll();
		
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("posts", posts);
		
		return "modify";
	}
	
	@RequestMapping(value = "/blog/{username}/{uid}/edit", method = RequestMethod.GET)
	public String modifyPostGet(@PathVariable String username, @PathVariable int uid, HttpServletRequest request, Model model) {
	
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}

		// retrieve single post based on uid in permalink
		Post post = postDao.findByUid(uid);
		
		String title = post.getTitle();
		String body = post.getBody();
		

		model.addAttribute("edit", true);
		model.addAttribute("title", title);
		model.addAttribute("body", body);
    	model.addAttribute("currentUser", currentUser);
		
		return "newpost";
	}

	@RequestMapping(value = "/blog/{username}/{uid}/edit", method = RequestMethod.POST)
	public String modifyPostPost(@PathVariable String username, @PathVariable int uid, HttpServletRequest request, Model model) {
	
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}

		// retrieve single post based on uid in permalink
		Post post = postDao.findByUid(uid);
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		
        // make sure post is valid (has title and body)
        // TODO - allow linebreaks
  		String error = "Both Title and Body are Required!";
        if (title == "" || body == "") {
        	model.addAttribute("error", error);
        	return "newpost";
        }

        // update post and save in db
		post.setTitle(title);
		post.setBody(body);
        postDao.save(post);
		
    	model.addAttribute("currentUser", currentUser);
		
		return "redirect:/blog/" + username + "/" + uid;	
	}

	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, HttpServletRequest request, Model model) {
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}

		// retrieve single post based on uid in permalink
		Post post = postDao.findByUid(uid);
		
        String title = post.getTitle();
        String body = post.getBody();
        Date created = post.getCreated();
        
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("author", username);
		model.addAttribute("title", title);
		model.addAttribute("body", body);
		model.addAttribute("created", created);
		model.addAttribute("uid", uid);
		
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, HttpServletRequest request, Model model) {
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}

		// get user uid
		User user = userDao.findByUserName(username);
		int uid = user.getUid();
		
		// get user's posts
		List<Post> posts = postDao.findByAuthor_uid(uid);
        
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
}
