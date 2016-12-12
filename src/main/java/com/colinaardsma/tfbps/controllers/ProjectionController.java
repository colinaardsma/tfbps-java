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
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.UserBatterSGP;
import com.colinaardsma.tfbps.models.UserPitcherSGP;
import com.colinaardsma.tfbps.models.YahooRotoLeague;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;
import com.colinaardsma.tfbps.models.dao.FPProjPitcherDao;
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
	UserPitcherSGPDao userPitcherSGPDao;
	
	@Autowired
	UserBatterSGPDao userBatterSGPDao;
	
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

	@RequestMapping(value = "/user_fpprojb", method = RequestMethod.GET)
	public String userfpprojbform(Model model, HttpServletRequest request){
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = userDao.findByUserName(currentUser);
		
		// get list of user's leagues
		List<YahooRotoLeague> leagues = yahooRotoLeagueDao.findByUsers_uid(user.getUid());
		// set category of data
		String category = "batter";
	
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("leagues", leagues);
		model.addAttribute("category", category);


        return "user_projection_selection";
	}
	
	@RequestMapping(value = "/user_fpprojb", method = RequestMethod.POST)
    public String userfpprojb(Model model, HttpServletRequest request){
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = userDao.findByUserName(currentUser);
		
		// get league and leaguekey from user selection in get
		String leagueKey = request.getParameter("league");
		YahooRotoLeague league = yahooRotoLeagueDao.findByLeagueKey(leagueKey);
		
		// pull player list
		List<FPProjBatter> batters = fpProjBatterDao.findAllByOrderBySgpDesc();
		
		// if histsgp has not been calculated for this league/user then calculate, otherwise continue
		if (userBatterSGPDao.findByUserAndLeague(user, league).size() == 0) {
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
			UserBatterSGP userBatterSGP = userBatterSGPDao.findByBatterAndUserAndLeague(batter, user, league);
			double customSGP = userBatterSGP.getHistSGP();

			FPProjBatter sgpBatter = new FPProjBatter(name, team, pos, ab, r, hr, rbi, sb, avg, obp, h, dbl, tpl, bb, k, slg, ops, category);
			sgpBatter.setSgp(customSGP);			
			sgpBatters.add(sgpBatter);
		}
		
		// sort list by user's custom sgp calculation (desc)
		Collections.sort(sgpBatters, new Comparator<FPProjBatter>() {
			@Override
			public int compare(FPProjBatter p1, FPProjBatter p2) {
				if (p1.getSgp() < p2.getSgp()) return 1;
				if (p1.getSgp() > p2.getSgp()) return -1;
				return 0;
			}
		});
		
		// get date of last data pull
		Date lastPullDate = batters.get(0).getCreated();
		// set category of data
		String category = "batter";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", sgpBatters);
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
		// set category of data
		String category = "pitcher";
	
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("leagues", leagues);
		model.addAttribute("category", category);

        return "user_projection_selection";
	}
	
	@RequestMapping(value = "/user_fpprojp", method = RequestMethod.POST)
    public String userfpprojp(Model model, HttpServletRequest request){
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = userDao.findByUserName(currentUser);
		
		// get league and leaguekey from user selection in get
		String leagueKey = request.getParameter("league");
		YahooRotoLeague league = yahooRotoLeagueDao.findByLeagueKey(leagueKey);
		
		// pull player list
		List<FPProjPitcher> pitchers = fpProjPitcherDao.findAllByOrderBySgpDesc();
		
		// if histsgp has not been calculated for this league/user then calculate, otherwise continue
		if (userPitcherSGPDao.findByUserAndLeague(user, league).size() == 0) {
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
			UserPitcherSGP userPitcherSGP = userPitcherSGPDao.findByPitcherAndUserAndLeague(pitcher, user, league);
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
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", sgpPitchers);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("category", category);

        return "user_projections";
    }
	
}
