package com.colinaardsma.tfbps.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.FPProjPitcher;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;
import com.colinaardsma.tfbps.models.dao.FPProjPitcherDao;

@Controller
public class ProjectionController extends AbstractController {

	@Autowired
	FPProjBatterDao fpProjBatterDao;
	
	@Autowired
	FPProjPitcherDao fpProjPitcherDao;
	
	@RequestMapping(value = "/fpprojb")
    public String fpprojb(Model model, HttpServletRequest request){
		
		// populate html table
		List<FPProjBatter> players = fpProjBatterDao.findAllByOrderBySgpDesc();
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}
		
		// get date of last data pull
		Date lastPullDate = players.get(0).getCreated();
		// set category of data
		String category = "batter";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", players);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("category", category);

        return "projections";
    }

	@RequestMapping(value = "/fpprojp")
    public String fpprojp(Model model, HttpServletRequest request){
		
		// populate html table
		List<FPProjPitcher> players = fpProjPitcherDao.findAllByOrderBySgpDesc();
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		if (currentUser == null) {
			return "index";
		}
		
		// get date of last data pull
		Date lastPullDate = players.get(0).getCreated();
		// set category of data
		String category = "pitcher";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", players);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("category", category);

        return "projections";
    }

	
}
