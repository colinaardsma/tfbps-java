package com.fantasyspot.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fantasyspot.models.OttoneuOldSchoolLeague;
import com.fantasyspot.models.User;
import com.fantasyspot.models.YahooRotoLeague;
import com.fantasyspot.models.dao.KeeperCostsDao;
import com.fantasyspot.models.dao.OttoneuOldSchoolLeagueDao;
import com.fantasyspot.models.dao.UserCustomRankingsBDao;
import com.fantasyspot.models.dao.UserCustomRankingsPDao;
import com.fantasyspot.models.dao.UserDao;
import com.fantasyspot.models.dao.YahooRotoLeagueDao;

@Controller
public class UserAccountController extends AbstractController {
	
	@Autowired
	UserDao userdao;

	@Autowired
	YahooRotoLeagueDao yahooRotoLeagueDao;
	
	@Autowired
	OttoneuOldSchoolLeagueDao ottoneuOldSchoolLeagueDao;
	
	@Autowired
	UserCustomRankingsBDao userCustomRankingsBDao;
	
	@Autowired
	UserCustomRankingsPDao userCustomRankingsPDao;
	
	@Autowired
	KeeperCostsDao keeperCostsDao;
	
    @RequestMapping(value = "/useraccount")
    public String useraccount(HttpServletRequest request, Model model) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// create list of user's leagues that already have custom SGPs
		// YAHOO ROTO LEAGUES
		List<YahooRotoLeague> userYahooRotoLeagues = yahooRotoLeagueDao.findByUsers(user);
		
		List<YahooRotoLeague> batterYahooRotoLeagues = new ArrayList<YahooRotoLeague>();
		List<YahooRotoLeague> pitcherYahooRotoLeagues = new ArrayList<YahooRotoLeague>();
		List<YahooRotoLeague> keeperYahooRotoLeagues = new ArrayList<YahooRotoLeague>();

		for (YahooRotoLeague league : userYahooRotoLeagues) {
			if (!userCustomRankingsPDao.findByUserAndYahooRotoLeague(user, league).isEmpty()) {
				pitcherYahooRotoLeagues.add(league);
			}
			if (!userCustomRankingsBDao.findByUserAndYahooRotoLeague(user, league).isEmpty()) {
				batterYahooRotoLeagues.add(league);
			}
			if (!keeperCostsDao.findByUserAndYahooRotoLeague(user, league).isEmpty()) {
				keeperYahooRotoLeagues.add(league);
			}
		}
		
		// OTTONEU OLD SCHOOL LEAGUES
		List<OttoneuOldSchoolLeague> userOttoneuOldSchoolLeagues = ottoneuOldSchoolLeagueDao.findByUsers(user);
		
		List<OttoneuOldSchoolLeague> batterOttoneuOldSchoolLeagues = new ArrayList<OttoneuOldSchoolLeague>();
		List<OttoneuOldSchoolLeague> pitcherOttoneuOldSchoolLeagues = new ArrayList<OttoneuOldSchoolLeague>();
		List<OttoneuOldSchoolLeague> keeperOttoneuOldSchoolLeagues = new ArrayList<OttoneuOldSchoolLeague>();

		for (OttoneuOldSchoolLeague league : userOttoneuOldSchoolLeagues) {
			if (!userCustomRankingsPDao.findByUserAndOttoneuOldSchoolLeague(user, league).isEmpty()) {
				pitcherOttoneuOldSchoolLeagues.add(league);
			}
			if (!userCustomRankingsBDao.findByUserAndOttoneuOldSchoolLeague(user, league).isEmpty()) {
				batterOttoneuOldSchoolLeagues.add(league);
			}
			if (!keeperCostsDao.findByUserAndOttoneuOldSchoolLeague(user, league).isEmpty()) {
				keeperOttoneuOldSchoolLeagues.add(league);
			}
		}
		
    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
        model.addAttribute("batterYahooRotoLeagues", batterYahooRotoLeagues);
        model.addAttribute("pitcherYahooRotoLeagues", pitcherYahooRotoLeagues);
        model.addAttribute("keeperYahooRotoLeagues", keeperYahooRotoLeagues);
        model.addAttribute("batterOttoneuOldSchoolLeagues", batterOttoneuOldSchoolLeagues);
        model.addAttribute("pitcherOttoneuOldSchoolLeagues", pitcherOttoneuOldSchoolLeagues);
  	
    	return "useraccount";
    }

}
