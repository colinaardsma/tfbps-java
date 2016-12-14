package com.colinaardsma.tfbps.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.UserBatterSGP;
import com.colinaardsma.tfbps.models.UserPitcherSGP;
import com.colinaardsma.tfbps.models.YahooRotoLeague;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;
import com.colinaardsma.tfbps.models.dao.FPProjPitcherDao;
import com.colinaardsma.tfbps.models.dao.UserBatterSGPDao;
import com.colinaardsma.tfbps.models.dao.UserDao;
import com.colinaardsma.tfbps.models.dao.UserPitcherSGPDao;

@Controller
public class UserAccountController extends AbstractController {
	
	@Autowired
	UserDao userdao;

	@Autowired
	FPProjBatterDao fpProjBatterDao;
	
	@Autowired
	FPProjPitcherDao fpProjPitcherDao;
	
	@Autowired
	UserBatterSGPDao userBatterSGPDao;
	
	@Autowired
	UserPitcherSGPDao userPitcherSGPDao;
	
    @RequestMapping(value = "/useraccount")
    public String useraccount(HttpServletRequest request, Model model) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// create list of user's leagues that already have custom SGPs
		// batter
		List<UserBatterSGP> userBatterSGPPlayers = userBatterSGPDao.findByUser(user);
		List<YahooRotoLeague> batterLeagues = new ArrayList<YahooRotoLeague>();
		for (UserBatterSGP player : userBatterSGPPlayers) {
			YahooRotoLeague league = player.getYahooRotoLeague();
			if (!batterLeagues.contains(league)) {
				batterLeagues.add(league);
			}
		}
		
		// pitcher
		List<UserPitcherSGP> userPitcherSGPPlayers = userPitcherSGPDao.findByUser(user);
		List<YahooRotoLeague> pitcherLeagues = new ArrayList<YahooRotoLeague>();
		for (UserPitcherSGP player : userPitcherSGPPlayers) {
			YahooRotoLeague league = player.getYahooRotoLeague();
			if (!pitcherLeagues.contains(league)) {
				pitcherLeagues.add(league);
			}
		}

		
    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
        model.addAttribute("batterLeagues", batterLeagues);
        model.addAttribute("pitcherLeagues", pitcherLeagues);
   	
    	return "useraccount";
    }

}
