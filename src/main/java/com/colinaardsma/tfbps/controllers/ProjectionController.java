package com.colinaardsma.tfbps.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.colinaardsma.tfbps.models.OttoneuOldSchoolLeague;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.UserCustomRankingsB;
import com.colinaardsma.tfbps.models.UserCustomRankingsP;
import com.colinaardsma.tfbps.models.YahooRotoLeague;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;
import com.colinaardsma.tfbps.models.dao.FPProjPitcherDao;
import com.colinaardsma.tfbps.models.dao.OttoneuOldSchoolLeagueDao;
import com.colinaardsma.tfbps.models.dao.UserCustomRankingsBDao;
import com.colinaardsma.tfbps.models.dao.UserCustomRankingsPDao;
import com.colinaardsma.tfbps.models.dao.YahooRotoLeagueDao;

@Controller
public class ProjectionController extends AbstractController {

	@Autowired
	FPProjBatterDao fpProjBatterDao;
	
	@Autowired
	FPProjPitcherDao fpProjPitcherDao;
	
	@Autowired
	YahooRotoLeagueDao yahooRotoLeagueDao;
	
	@Autowired
	OttoneuOldSchoolLeagueDao ottoneuOldSchoolLeagueDao;
	
	@Autowired
	UserCustomRankingsPDao userCustomRankingsPDao;
	
	@Autowired
	UserCustomRankingsBDao userCustomRankingsBDao;
	
	@RequestMapping(value = "/fpprojb")
    public String fpprojb(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// populate html table
		List<FPProjBatter> players = fpProjBatterDao.findAllByOrderByOpsTotalSGPDesc();
		
		// get date of last data pull
		Date lastPullDate = players.get(0).getCreated();
		// set category of data
		String category = "batter";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", players);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "projections";
    }

	@RequestMapping(value = "/fpprojp")
    public String fpprojp(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
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
        model.addAttribute("user", user);

        return "projections";
    }

	// YAHOO ROTO LEAGUE BATTER
	@RequestMapping(value = "/user_yahoo_roto_fpprojb", method = RequestMethod.GET)
	public String useryahoorotofpprojbform(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// get list of user's leagues
		List<YahooRotoLeague> leagues = yahooRotoLeagueDao.findByUsers_uid(user.getUid());
		// set category of data
		String category = "batter";
		String leagueType = "Yahoo Roto";
	
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("leagues", leagues);
		model.addAttribute("leagueType", leagueType);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "user_projection_selection";
	}
	
	@RequestMapping(value = "/user_yahoo_roto_fpprojb", method = RequestMethod.POST)
    public String useryahoorotofpprojb(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// get league and leaguekey from user selection in get
		String leagueKey = request.getParameter("league");
		YahooRotoLeague league = yahooRotoLeagueDao.findByLeagueKey(leagueKey);
		
		// pull player list
		List<FPProjBatter> batters = fpProjBatterDao.findAllByOrderByOpsTotalSGPDesc();
		
		// if hist sgp has not been calculated for this league/user then calculate, otherwise continue
		if (userCustomRankingsBDao.findByUserAndYahooRotoLeague(user, league).size() == 0) {
			for (FPProjBatter batter : batters) {
				UserCustomRankingsB userBatterSGP = new UserCustomRankingsB(batter, league, user);
				userCustomRankingsBDao.save(userBatterSGP);
			}
		}
		
		List<UserCustomRankingsB> userBatterList = userCustomRankingsBDao.findByUserAndYahooRotoLeague(user, league);

		// sort list by user's custom sgp calculation (desc)
		Collections.sort(userBatterList, new Comparator<UserCustomRankingsB>() {
			@Override
			public int compare(UserCustomRankingsB b1, UserCustomRankingsB b2) {
				if (b1.getHistSGP() < b2.getHistSGP()) return 1;
				if (b1.getHistSGP() > b2.getHistSGP()) return -1;
				return 0;
			}
		});
		
		// create secondary list for use in AAV calculation
		List<UserCustomRankingsB> userCustomList = new ArrayList<UserCustomRankingsB>();
		userCustomList.addAll(userBatterList);
		
		for (UserCustomRankingsB userBatter : userBatterList) {
			userBatter.calcLeagueHistAAV(userCustomList, league);
			userCustomRankingsBDao.save(userBatter);
		}
		
		// create and populate list with players and user's custom sgp
		List<FPProjBatter> customBatterRankings = new ArrayList<FPProjBatter>();
		
		for (UserCustomRankingsB userBatter : userBatterList) {
			FPProjBatter batter = userBatter.getBatter();
			
			String name = batter.getName();
			String team = batter.getTeam();
			String pos = batter.getPos();
			int ab = batter.getAb();
			int r = batter.getR();
			int hr = batter.getHr();
			int rbi = batter.getRbi();
			int sb = batter.getSb();
			double avg = batter.getAvg();
			double obp = batter.getObp();
			int h = batter.getH();
			int dbl = batter.getDbl();
			int tpl = batter.getTpl();
			int bb = batter.getBb();
			int k = batter.getK();
			double slg = batter.getSlg();
			double ops = batter.getOps();
			String category = batter.getCategory();
			double customSGP = userBatter.getHistSGP();
			BigDecimal customAAV = userBatter.getHistAAV();
			
			FPProjBatter customBatter = new FPProjBatter(name, team, pos, ab, r, hr, rbi, sb, avg, obp, h, dbl, tpl, bb, k, slg, ops, category);
			customBatter.setOpsTotalSGP(customSGP);
			customBatter.setOpsTotalAAV(customAAV);
			customBatterRankings.add(customBatter);
		}
		
		// get date of last data pull
		Date lastPullDate = batters.get(0).getCreated();
		// set category of data
		String category = "batter";
		String leagueType = "Yahoo Roto";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", customBatterRankings);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("leagueType", leagueType);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "projections";
    }

	// YAHOO ROTO LEAGUE PITCHER
	@RequestMapping(value = "/user_yahoo_roto_fpprojp", method = RequestMethod.GET)
	public String useryahoorotofpprojpform(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// get list of user's leagues
		List<YahooRotoLeague> leagues = yahooRotoLeagueDao.findByUsers_uid(user.getUid());
		// set category of data
		String category = "pitcher";
		String leagueType = "Yahoo Roto";
	
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("leagues", leagues);
		model.addAttribute("leagueType", leagueType);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "user_projection_selection";
	}
	
	@RequestMapping(value = "/user_yahoo_roto_fpprojp", method = RequestMethod.POST)
    public String useryahoorotofpprojp(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// get league and leaguekey from user selection in get
		String leagueKey = request.getParameter("league");
		YahooRotoLeague league = yahooRotoLeagueDao.findByLeagueKey(leagueKey);
		
		// pull player list
		List<FPProjPitcher> pitchers = fpProjPitcherDao.findAllByOrderBySgpDesc();
		
		// if histsgp has not been calculated for this league/user then calculate, otherwise continue
		if (userCustomRankingsPDao.findByUserAndYahooRotoLeague(user, league).size() == 0) {
			for (FPProjPitcher pitcher : pitchers) {
				UserCustomRankingsP userPitcherSGP = new UserCustomRankingsP(pitcher, league, user);
				userCustomRankingsPDao.save(userPitcherSGP);
			}
		}

		// create and populate list with players and user's custom sgp
		List<UserCustomRankingsP> userPitcherList = userCustomRankingsPDao.findByUserAndYahooRotoLeague(user, league);
		
		// sort list by user's custom sgp calculation (desc)
		Collections.sort(userPitcherList, new Comparator<UserCustomRankingsP>() {
			@Override
			public int compare(UserCustomRankingsP p1, UserCustomRankingsP p2) {
				if (p1.getHistSGP() < p2.getHistSGP()) return 1;
				if (p1.getHistSGP() > p2.getHistSGP()) return -1;
				return 0;
			}
		});
		
		// create secondary list for use in AAV calculation
		List<UserCustomRankingsP> userCustomList = new ArrayList<UserCustomRankingsP>();
		userCustomList.addAll(userPitcherList);
		
		for (UserCustomRankingsP userPitcher : userPitcherList) {
			userPitcher.calcLeagueHistAAV(userCustomList, league);
			userCustomRankingsPDao.save(userPitcher);
		}
		
		// create and populate list with players and user's custom sgp
		List<FPProjPitcher> customPitcherRankings = new ArrayList<FPProjPitcher>();
		
		for (UserCustomRankingsP userPitcher : userPitcherList) {
			FPProjPitcher pitcher = userPitcher.getPitcher();
			
			String name = pitcher.getName();
			String team = pitcher.getTeam();
			String pos = pitcher.getPos();
			int ip = pitcher.getIp();
			int k = pitcher.getK();
			int w = pitcher.getW();
			int sv = pitcher.getSv();
			double era = pitcher.getEra();
			double whip = pitcher.getWhip();
			int er = pitcher.getEr();
			int h = pitcher.getH();
			int bb = pitcher.getBb();
			int hr = pitcher.getHr();
			int g = pitcher.getG();
			int gs = pitcher.getGs();
			int l = pitcher.getL();
			int cg = pitcher.getCg();
			String category = pitcher.getCategory();
			double customSGP = userPitcher.getHistSGP();
			BigDecimal customAAV = userPitcher.getHistAAV();

			FPProjPitcher customPitcher = new FPProjPitcher(name, team, pos, ip, k, w, sv, era, whip, er, h, bb, hr, g, gs, l, cg, category);
			customPitcher.setSgp(customSGP);
			customPitcher.setAav(customAAV);
			customPitcherRankings.add(customPitcher);
		}
		
		// get date of last data pull
		Date lastPullDate = pitchers.get(0).getCreated();
		// set category of data
		String category = "pitcher";
		String leagueType = "Yahoo Roto";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", customPitcherRankings);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("leagueType", leagueType);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "projections";
    }
	
	// OTTONEU OLD SCHOOL LEAGUE BATTER
	@RequestMapping(value = "/user_ottoneu_old_school_fpprojb", method = RequestMethod.GET)
	public String userottoneuoldschoolfpprojbform(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// get list of user's leagues
		List<OttoneuOldSchoolLeague> leagues = ottoneuOldSchoolLeagueDao.findByUsers_uid(user.getUid());
		// set category of data
		String category = "batter";
		String leagueType = "Ottoneu Old School";
	
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("leagues", leagues);
		model.addAttribute("leagueType", leagueType);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "user_projection_selection";
	}
	
	@RequestMapping(value = "/user_ottoneu_old_school_fpprojb", method = RequestMethod.POST)
    public String userottoneuoldschoolfpprojb(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// get league and leaguekey from user selection in get
		String leagueKey = request.getParameter("league");
		OttoneuOldSchoolLeague league = ottoneuOldSchoolLeagueDao.findByLeagueKey(leagueKey);
		
		// pull player list
		List<FPProjBatter> batters = fpProjBatterDao.findAllByOrderByOpsTotalSGPDesc();
		
		// if histsgp has not been calculated for this league/user then calculate, otherwise continue
		if (userCustomRankingsBDao.findByUserAndOttoneuOldSchoolLeague(user, league).size() == 0) {
			for (FPProjBatter batter : batters) {
				UserCustomRankingsB userBatterSGP = new UserCustomRankingsB(batter, league, user);
				userCustomRankingsBDao.save(userBatterSGP);
			}
		}

		// create and populate list with players and user's custom sgp
		List<FPProjBatter> sgpBatters = new ArrayList<FPProjBatter>();
		
		for (FPProjBatter batter : batters) {
			String name = batter.getName();
			String team = batter.getTeam();
			String pos = batter.getPos();
			int ab = batter.getAb();
			int r = batter.getR();
			int hr = batter.getHr();
			int rbi = batter.getRbi();
			int sb = batter.getSb();
			double avg = batter.getAvg();
			double obp = batter.getObp();
			int h = batter.getH();
			int dbl = batter.getDbl();
			int tpl = batter.getTpl();
			int bb = batter.getBb();
			int k = batter.getK();
			double slg = batter.getSlg();
			double ops = batter.getOps();
			String category = batter.getCategory();
			UserCustomRankingsB userBatterSGP = userCustomRankingsBDao.findByBatterAndUserAndOttoneuOldSchoolLeague(batter, user, league);
			double customSGP = userBatterSGP.getHistSGP();

			FPProjBatter sgpBatter = new FPProjBatter(name, team, pos, ab, r, hr, rbi, sb, avg, obp, h, dbl, tpl, bb, k, slg, ops, category);
			sgpBatter.setAvgTotalSGP(customSGP);
			sgpBatters.add(sgpBatter);
		}
		
		// sort list by user's custom sgp calculation (desc)
		Collections.sort(sgpBatters, new Comparator<FPProjBatter>() {
			@Override
			public int compare(FPProjBatter b1, FPProjBatter b2) {
				if (b1.getAvgTotalSGP() < b2.getAvgTotalSGP()) return 1;
				if (b1.getAvgTotalSGP() > b2.getAvgTotalSGP()) return -1;
				return 0;
			}
		});
		
		// get date of last data pull
		Date lastPullDate = batters.get(0).getCreated();
		// set category of data
		String category = "batter";
		String leagueType = "Ottoneu Old School";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", sgpBatters);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("leagueType", leagueType);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "projections";
    }

	// OTTONEU OLD SCHOOL PITCHER
	@RequestMapping(value = "/user_ottoneu_old_school_fpprojp", method = RequestMethod.GET)
	public String userottoneuoldschoolfpprojpform(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// get list of user's leagues
		List<OttoneuOldSchoolLeague> leagues = ottoneuOldSchoolLeagueDao.findByUsers_uid(user.getUid());
		// set category of data
		String category = "pitcher";
		String leagueType = "Ottoneu Old School";
	
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("leagues", leagues);
		model.addAttribute("leagueType", leagueType);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "user_projection_selection";
	}
	
	@RequestMapping(value = "/user_ottoneu_old_school_fpprojp", method = RequestMethod.POST)
    public String userottoneuoldschoolfpprojp(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// get league and leaguekey from user selection in get
		String leagueKey = request.getParameter("league");
		OttoneuOldSchoolLeague league = ottoneuOldSchoolLeagueDao.findByLeagueKey(leagueKey);
		
		// pull player list
		List<FPProjPitcher> pitchers = fpProjPitcherDao.findAllByOrderBySgpDesc();
		
		// if histsgp has not been calculated for this league/user then calculate, otherwise continue
		if (userCustomRankingsPDao.findByUserAndOttoneuOldSchoolLeague(user, league).size() == 0) {
			for (FPProjPitcher pitcher : pitchers) {
				UserCustomRankingsP userPitcherSGP = new UserCustomRankingsP(pitcher, league, user);
				userCustomRankingsPDao.save(userPitcherSGP);
			}
		}

		// create and populate list with players and user's custom sgp
		List<FPProjPitcher> sgpPitchers = new ArrayList<FPProjPitcher>();
		
		for (FPProjPitcher pitcher : pitchers) {
			String name = pitcher.getName();
			String team = pitcher.getTeam();
			String pos = pitcher.getPos();
			int ip = pitcher.getIp();
			int k = pitcher.getK();
			int w = pitcher.getW();
			int sv = pitcher.getSv();
			double era = pitcher.getEra();
			double whip = pitcher.getWhip();
			int er = pitcher.getEr();
			int h = pitcher.getH();
			int bb = pitcher.getBb();
			int hr = pitcher.getHr();
			int g = pitcher.getG();
			int gs = pitcher.getGs();
			int l = pitcher.getL();
			int cg = pitcher.getCg();
			String category = pitcher.getCategory();
			UserCustomRankingsP userPitcherSGP = userCustomRankingsPDao.findByPitcherAndUserAndOttoneuOldSchoolLeague(pitcher, user, league);
			double customSGP = userPitcherSGP.getHistSGP();

			FPProjPitcher sgpPitcher = new FPProjPitcher(name, team, pos, ip, k, w, sv, era, whip, er, h, bb, hr, g, gs, l, cg, category);
			sgpPitcher.setSgp(customSGP);			
			sgpPitchers.add(sgpPitcher);
		}
		
		// sort list by user's custom sgp calculation (desc)
		Collections.sort(sgpPitchers, new Comparator<FPProjPitcher>() {
			@Override
			public int compare(FPProjPitcher p1, FPProjPitcher p2) {
				if (p1.getSgp() < p2.getSgp()) return 1;
				if (p1.getSgp() > p2.getSgp()) return -1;
				return 0;
			}
		});
		
		// get date of last data pull
		Date lastPullDate = pitchers.get(0).getCreated();
		// set category of data
		String category = "pitcher";
		String leagueType = "Ottoneu Old School";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", sgpPitchers);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("leagueType", leagueType);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "projections";
    }
	
}
