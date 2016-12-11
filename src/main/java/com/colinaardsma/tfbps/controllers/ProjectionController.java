package com.colinaardsma.tfbps.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.FPProjPitcher;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.YahooRotoLeague;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;
import com.colinaardsma.tfbps.models.dao.FPProjPitcherDao;
import com.colinaardsma.tfbps.models.dao.YahooRotoLeagueDao;

@Controller
public class ProjectionController extends AbstractController {

	@Autowired
	FPProjBatterDao fpProjBatterDao;
	
	@Autowired
	FPProjPitcherDao fpProjPitcherDao;
	
	@Autowired
	YahooRotoLeagueDao yahooRotoLeagueDao;
	
	@RequestMapping(value = "/fpprojb")
    public String fpprojb(Model model, HttpServletRequest request){
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		
		// populate html table
		List<FPProjBatter> players = fpProjBatterDao.findAllByOrderBySgpDesc();
		
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
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		
		// populate html table
		List<FPProjPitcher> players = fpProjPitcherDao.findAllByOrderBySgpDesc();
		
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

	@RequestMapping(value = "/user_fpprojb")
    public String userfpprojb(Model model, HttpServletRequest request){
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
//		User user = userDao.findByUserName(currentUser);
		
		// populate html table
		List<FPProjBatter> players = fpProjBatterDao.findAllByOrderBySgpDesc();
		
		// get date of last data pull
		Date lastPullDate = players.get(0).getCreated();
		// set category of data
		String category = "batter";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", players);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("category", category);

        return "user_projections";
    }

	@RequestMapping(value = "/user_fpprojp", method = RequestMethod.GET)
	public String userfpprojpform(Model model, HttpServletRequest request){
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = userDao.findByUserName(currentUser);
		
		// get list of user's leagues
		List<YahooRotoLeague> leagues = yahooRotoLeagueDao.findByUsers_uid(user.getUid());
	
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("leagues", leagues);

        return "user_projection_selection";
	}
	
	@RequestMapping(value = "/user_fpprojp", method = RequestMethod.POST)
    public String userfpprojp(Model model, HttpServletRequest request){
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
//		User user = userDao.findByUserName(currentUser);
		
		String leagueKey = request.getParameter("league");
		YahooRotoLeague league = yahooRotoLeagueDao.findByLeagueKey(leagueKey);
		
		// populate html table
		List<FPProjPitcher> players = fpProjPitcherDao.findAllByOrderBySgpDesc();
		List<Double> userSGP = new ArrayList<Double>();
		for (FPProjPitcher player: players) {
			double playerSGP = player.calcLeagueHistSGP(league.getWHistSGPMult(), league.getSvHistSGPMult(), league.getKHistSGPMult(), league.getEraHistSGPMult(), league.getWhipHistSGPMult());
			userSGP.add(playerSGP);
		}
//		Double[] userSGPs = 
		
		// get date of last data pull
		Date lastPullDate = players.get(0).getCreated();
		// set category of data
		String category = "pitcher";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", players);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("category", category);
		model.addAttribute("userSGP", userSGP.toArray());

        return "user_projections";
    }
	
}
