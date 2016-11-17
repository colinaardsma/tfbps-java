package com.colinaardsma.tfbps.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;

@Controller
public class ProjectionController extends AbstractController {

	@Autowired
	FPProjBatterDao fpProjBatterDao;
	
	@RequestMapping(value = "/fpprojb")
    public String fpprojb(Model model, HttpServletRequest request){
		
		// populate html table
		List<FPProjBatter> players = fpProjBatterDao.findAll();
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}
		
		// need to figure out how to properly order
//      Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userKey);
//      request.getSession().setAttribute(userKey, newUser.getUid());
//      
//      Session session = 
//		
//		Criteria crit = session.createCriteria(Cat.class);
//		crit.setMaxResults(50);
//		List cats = crit.list();
//		List cats = sess.createCriteria(players);
		
   	
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", players);

        return "fpprojb";
    }

	@RequestMapping(value = "/fpprojp")
    public String fpprojp(Model model, HttpServletRequest request){
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}
		
    	model.addAttribute("currentUser", currentUser);

        return "fpprojp";
    }

	
}
