package com.fantasyspot.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fantasyspot.models.FPProjBatter;
import com.fantasyspot.models.FPProjPitcher;
import com.fantasyspot.models.KeeperCosts;
import com.fantasyspot.models.User;
import com.fantasyspot.models.YahooRotoLeague;
import com.fantasyspot.models.dao.KeeperCostsDao;
import com.fantasyspot.models.dao.UserCustomRankingsBDao;
import com.fantasyspot.models.dao.UserCustomRankingsPDao;
import com.fantasyspot.models.dao.UserDao;
import com.fantasyspot.models.dao.YahooRotoLeagueDao;

@Controller
public class KeeperController extends AbstractController {
	@Autowired
	UserDao userdao;

	@Autowired
	YahooRotoLeagueDao yahooRotoLeagueDao;
	
	@Autowired
	KeeperCostsDao keeperCostsDao;
	
	@Autowired
	UserCustomRankingsBDao userCustomRankingsBDao;

	@Autowired
	UserCustomRankingsPDao userCustomRankingsPDao;

	@RequestMapping(value = "/keepers", method = RequestMethod.GET)
	public String keepersform(Model model, HttpServletRequest request) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		List<YahooRotoLeague> keeperLeagues = new ArrayList<YahooRotoLeague>();
		List<YahooRotoLeague> leagues = yahooRotoLeagueDao.findByUsers(user);
		for (YahooRotoLeague league : leagues) {
			if (keeperCostsDao.findByYahooRotoLeague(league) != null) {
				keeperLeagues.add(league);
			}			
		}
		
		model.addAttribute("keeperLeagues", keeperLeagues);
    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
				
		return "keepersform";
	}

	@RequestMapping(value = "/keepers", method = RequestMethod.POST)
	public String keepers(Model model, HttpServletRequest request) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		String leagueKey = request.getParameter("leagueKey");
		YahooRotoLeague league = yahooRotoLeagueDao.findByLeagueKey(leagueKey);

		List<KeeperCosts> potentialKeepers = keeperCostsDao.findByYahooRotoLeague(league);
		for (KeeperCosts pK : potentialKeepers) {
			String posType = pK.getPosType();
			if (posType.equals("B")) {
				FPProjBatter batter = pK.getBatter();
				if (userCustomRankingsBDao.findByFpProjBatter(batter) != null) {
					pK.setUserCustomRankingsB(userCustomRankingsBDao.findByFpProjBatter(batter));
				}
			} else {
				FPProjPitcher pitcher = pK.getPitcher();
				if (userCustomRankingsPDao.findByFpProjPitcher(pitcher) != null) {
					pK.setUserCustomRankingsP(userCustomRankingsPDao.findByFpProjPitcher(pitcher));
				}
			}
		}
		
		model.addAttribute("potentialKeepers", potentialKeepers);
    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
				
		return "keepers";
	}

}
