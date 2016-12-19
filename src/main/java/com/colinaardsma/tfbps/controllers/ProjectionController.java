package com.colinaardsma.tfbps.controllers;

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
import com.colinaardsma.tfbps.models.UserBatterSGP;
import com.colinaardsma.tfbps.models.UserPitcherSGP;
import com.colinaardsma.tfbps.models.YahooRotoLeague;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;
import com.colinaardsma.tfbps.models.dao.FPProjPitcherDao;
import com.colinaardsma.tfbps.models.dao.OttoneuOldSchoolLeagueDao;
import com.colinaardsma.tfbps.models.dao.UserBatterSGPDao;
import com.colinaardsma.tfbps.models.dao.UserPitcherSGPDao;
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
	UserPitcherSGPDao userPitcherSGPDao;
	
	@Autowired
	UserBatterSGPDao userBatterSGPDao;
	
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
		
		// if histsgp has not been calculated for this league/user then calculate, otherwise continue
		if (userBatterSGPDao.findByUserAndYahooRotoLeague(user, league).size() == 0) {
			for (FPProjBatter batter : batters) {
				UserBatterSGP userBatterSGP = new UserBatterSGP(batters, batter, league, user);
				userBatterSGPDao.save(userBatterSGP);
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
			UserBatterSGP userBatterSGP = userBatterSGPDao.findByBatterAndUserAndYahooRotoLeague(batter, user, league);
			double customSGP = userBatterSGP.getHistSGP();

			FPProjBatter sgpBatter = new FPProjBatter(name, team, pos, ab, r, hr, rbi, sb, avg, obp, h, dbl, tpl, bb, k, slg, ops, category);
			sgpBatter.setOpsTotalSGP(customSGP);			
			sgpBatters.add(sgpBatter);
		}
		
		// sort list by user's custom sgp calculation (desc)
		Collections.sort(sgpBatters, new Comparator<FPProjBatter>() {
			@Override
			public int compare(FPProjBatter b1, FPProjBatter b2) {
				if (b1.getOpsTotalSGP() < b2.getOpsTotalSGP()) return 1;
				if (b1.getOpsTotalSGP() > b2.getOpsTotalSGP()) return -1;
				return 0;
			}
		});
		
		if (league.getAuctionBudget() > -1) {
//			List<FPProjBatter> aavBatters = new ArrayList<FPProjBatter>();
			for (int i = 0; i < league.getHistDraftedB(); i++) {
				UserBatterSGP userBatterSGP = userBatterSGPDao.findByBatterAndUserAndYahooRotoLeague(sgpBatters.get(i), user, league);
				double customAAV = userBatterSGP.calcLeagueHistAAV(sgpBatters, sgpBatters.get(i), league);
				sgpBatters.get(i).setOpsTotalAAV(customAAV);
//				aavBatters.add(sgpBatters.get(i));

			}
		}
		
		// get date of last data pull
		Date lastPullDate = batters.get(0).getCreated();
		// set category of data
		String category = "batter";
		String leagueType = "Yahoo Roto";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", sgpBatters);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("leagueType", leagueType);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "user_projections";
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
		if (userPitcherSGPDao.findByUserAndYahooRotoLeague(user, league).size() == 0) {
			for (FPProjPitcher pitcher : pitchers) {
				UserPitcherSGP userPitcherSGP = new UserPitcherSGP(pitcher, league, user);
				userPitcherSGPDao.save(userPitcherSGP);
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
			UserPitcherSGP userPitcherSGP = userPitcherSGPDao.findByPitcherAndUserAndYahooRotoLeague(pitcher, user, league);
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
		String leagueType = "Yahoo Roto";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", sgpPitchers);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("leagueType", leagueType);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "user_projections";
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
		if (userBatterSGPDao.findByUserAndOttoneuOldSchoolLeague(user, league).size() == 0) {
			for (FPProjBatter batter : batters) {
				UserBatterSGP userBatterSGP = new UserBatterSGP(batter, league, user);
				userBatterSGPDao.save(userBatterSGP);
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
			UserBatterSGP userBatterSGP = userBatterSGPDao.findByBatterAndUserAndOttoneuOldSchoolLeague(batter, user, league);
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

        return "user_projections";
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
		if (userPitcherSGPDao.findByUserAndOttoneuOldSchoolLeague(user, league).size() == 0) {
			for (FPProjPitcher pitcher : pitchers) {
				UserPitcherSGP userPitcherSGP = new UserPitcherSGP(pitcher, league, user);
				userPitcherSGPDao.save(userPitcherSGP);
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
			UserPitcherSGP userPitcherSGP = userPitcherSGPDao.findByPitcherAndUserAndOttoneuOldSchoolLeague(pitcher, user, league);
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

        return "user_projections";
    }
	
}
