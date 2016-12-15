package com.colinaardsma.tfbps.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colinaardsma.tfbps.models.OttoneuOldSchoolLeague;
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
		// YAHOO ROTO LEAGUES
		// batter
		List<UserBatterSGP> userBatterYahooRotoSGPPlayers = userBatterSGPDao.findByUser(user);
		List<YahooRotoLeague> batterYahooRotoLeagues = new ArrayList<YahooRotoLeague>();
		for (UserBatterSGP player : userBatterYahooRotoSGPPlayers) {
			if (player.getYahooRotoLeague() != null) {
				YahooRotoLeague league = player.getYahooRotoLeague();
				if (!batterYahooRotoLeagues.contains(league)) {
					batterYahooRotoLeagues.add(league);
				}
			}
		}
		
		// pitcher
		List<UserPitcherSGP> userPitcherYahooRotoSGPPlayers = userPitcherSGPDao.findByUser(user);
		List<YahooRotoLeague> pitcherYahooRotoLeagues = new ArrayList<YahooRotoLeague>();
		for (UserPitcherSGP player : userPitcherYahooRotoSGPPlayers) {
			if (player.getYahooRotoLeague() != null) {
				YahooRotoLeague league = player.getYahooRotoLeague();
				if (!pitcherYahooRotoLeagues.contains(league)) {
					pitcherYahooRotoLeagues.add(league);
				}
			}
		}

		// OTTONEU OLD SCHOOL LEAGUES
		// batter
		List<UserBatterSGP> userBatterOttoneuOldSchoolSGPPlayers = userBatterSGPDao.findByUser(user);
		List<OttoneuOldSchoolLeague> batterOttoneuOldSchoolLeagues = new ArrayList<OttoneuOldSchoolLeague>();
		for (UserBatterSGP player : userBatterOttoneuOldSchoolSGPPlayers) {
			if (player.getOttoneuOldSchoolLeague() != null) {
				OttoneuOldSchoolLeague league = player.getOttoneuOldSchoolLeague();
				if (!batterOttoneuOldSchoolLeagues.contains(league)) {
					batterOttoneuOldSchoolLeagues.add(league);
				}
			}
		}
		
		// pitcher
		List<UserPitcherSGP> userPitcherOttoneuOldSchoolSGPPlayers = userPitcherSGPDao.findByUser(user);
		List<OttoneuOldSchoolLeague> pitcherOttoneuOldSchoolLeagues = new ArrayList<OttoneuOldSchoolLeague>();
		for (UserPitcherSGP player : userPitcherOttoneuOldSchoolSGPPlayers) {
			if (player.getOttoneuOldSchoolLeague() != null) {
				OttoneuOldSchoolLeague league = player.getOttoneuOldSchoolLeague();
				if (!pitcherOttoneuOldSchoolLeagues.contains(league)) {
					pitcherOttoneuOldSchoolLeagues.add(league);
				}
			}
		}
		
    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
        model.addAttribute("batterYahooRotoLeagues", batterYahooRotoLeagues);
        model.addAttribute("pitcherYahooRotoLeagues", pitcherYahooRotoLeagues);
        model.addAttribute("batterOttoneuOldSchoolLeagues", batterOttoneuOldSchoolLeagues);
        model.addAttribute("pitcherOttoneuOldSchoolLeagues", pitcherOttoneuOldSchoolLeagues);
  	
    	return "useraccount";
    }

}
