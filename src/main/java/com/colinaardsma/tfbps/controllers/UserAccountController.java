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
import com.colinaardsma.tfbps.models.YahooRotoLeague;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;
import com.colinaardsma.tfbps.models.dao.FPProjPitcherDao;
import com.colinaardsma.tfbps.models.dao.KeeperCostsDao;
import com.colinaardsma.tfbps.models.dao.OttoneuOldSchoolLeagueDao;
import com.colinaardsma.tfbps.models.dao.UserCustomRankingsBDao;
import com.colinaardsma.tfbps.models.dao.UserCustomRankingsPDao;
import com.colinaardsma.tfbps.models.dao.UserDao;
import com.colinaardsma.tfbps.models.dao.YahooRotoLeagueDao;

@Controller
public class UserAccountController extends AbstractController {
	
	@Autowired
	UserDao userdao;

	@Autowired
	FPProjBatterDao fpProjBatterDao;
	
	@Autowired
	FPProjPitcherDao fpProjPitcherDao;
	
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



//		List<UserCustomRankingsB> userBatterYahooRotoCustomPlayers = userCustomRankingsBDao.findByUser(user);
//		List<YahooRotoLeague> batterYahooRotoLeagues = new ArrayList<YahooRotoLeague>();
//		for (UserCustomRankingsB player : userBatterYahooRotoCustomPlayers) {
//			if (player.getYahooRotoLeague() != null) {
//				YahooRotoLeague league = player.getYahooRotoLeague();
//				if (!batterYahooRotoLeagues.contains(league)) {
//					batterYahooRotoLeagues.add(league);
//				}
//			}
//		}
		
//		List<UserCustomRankingsP> userPitcherYahooRotoCustomPlayers = userCustomRankingsPDao.findByUser(user);
//		List<YahooRotoLeague> pitcherYahooRotoLeagues = new ArrayList<YahooRotoLeague>();
//		for (UserCustomRankingsP player : userPitcherYahooRotoCustomPlayers) {
//			if (player.getYahooRotoLeague() != null) {
//				YahooRotoLeague league = player.getYahooRotoLeague();
//				if (!pitcherYahooRotoLeagues.contains(league)) {
//					pitcherYahooRotoLeagues.add(league);
//				}
//			}
//		}
		

		// OTTONEU OLD SCHOOL LEAGUES
		List<OttoneuOldSchoolLeague> userOttoneuOldSchoolLeagues = ottoneuOldSchoolLeagueDao.findByUsers(user);
		
		// batter
		List<OttoneuOldSchoolLeague> batterOttoneuOldSchoolLeagues = new ArrayList<OttoneuOldSchoolLeague>();
		for (OttoneuOldSchoolLeague league : userOttoneuOldSchoolLeagues) {
			if (!userCustomRankingsBDao.findByUserAndOttoneuOldSchoolLeague(user, league).isEmpty()) {
				batterOttoneuOldSchoolLeagues.add(league);
			}
		}
//		List<UserCustomRankingsB> userBatterOttoneuOldSchoolCustomPlayers = userCustomRankingsBDao.findByUser(user);
//		for (int i = 0; i < userBatterOttoneuOldSchoolCustomPlayers.size(); i++) {
////		for (UserCustomRankingsB player : userBatterOttoneuOldSchoolCustomPlayers) {
//			if (player.getOttoneuOldSchoolLeague() != null) {
//				OttoneuOldSchoolLeague league = player.getOttoneuOldSchoolLeague();
//				if (!batterOttoneuOldSchoolLeagues.contains(league)) {
//					batterOttoneuOldSchoolLeagues.add(league);
//				}
//			}
//		}
		
		// pitcher
		List<OttoneuOldSchoolLeague> pitcherOttoneuOldSchoolLeagues = new ArrayList<OttoneuOldSchoolLeague>();
		for (OttoneuOldSchoolLeague league : userOttoneuOldSchoolLeagues) {
			if (!userCustomRankingsBDao.findByUserAndOttoneuOldSchoolLeague(user, league).isEmpty()) {
				batterOttoneuOldSchoolLeagues.add(league);
			}
		}
//		List<UserCustomRankingsP> userPitcherOttoneuOldSchoolCustomPlayers = userCustomRankingsPDao.findByUser(user);
//		List<OttoneuOldSchoolLeague> pitcherOttoneuOldSchoolLeagues = new ArrayList<OttoneuOldSchoolLeague>();
//		for (UserCustomRankingsP player : userPitcherOttoneuOldSchoolCustomPlayers) {
//			if (player.getOttoneuOldSchoolLeague() != null) {
//				OttoneuOldSchoolLeague league = player.getOttoneuOldSchoolLeague();
//				if (!pitcherOttoneuOldSchoolLeagues.contains(league)) {
//					pitcherOttoneuOldSchoolLeagues.add(league);
//				}
//			}
//		}
		
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
