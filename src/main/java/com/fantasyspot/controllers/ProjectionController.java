package com.fantasyspot.controllers;

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

import com.fantasyspot.models.FPProjBatter;
import com.fantasyspot.models.FPProjPitcher;
import com.fantasyspot.models.OttoneuOldSchoolLeague;
import com.fantasyspot.models.SteamerProjBatter;
import com.fantasyspot.models.SteamerProjPitcher;
import com.fantasyspot.models.User;
import com.fantasyspot.models.UserCustomRankingsB;
import com.fantasyspot.models.UserCustomRankingsP;
import com.fantasyspot.models.YahooRotoLeague;
import com.fantasyspot.models.dao.FPProjBatterDao;
import com.fantasyspot.models.dao.FPProjPitcherDao;
import com.fantasyspot.models.dao.OttoneuOldSchoolLeagueDao;
import com.fantasyspot.models.dao.SteamerProjBatterDao;
import com.fantasyspot.models.dao.SteamerProjPitcherDao;
import com.fantasyspot.models.dao.UserCustomRankingsBDao;
import com.fantasyspot.models.dao.UserCustomRankingsPDao;
import com.fantasyspot.models.dao.YahooRotoLeagueDao;

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
	
	@Autowired
	SteamerProjBatterDao steamerProjBatterDao;
	
	@Autowired
	SteamerProjPitcherDao steamerProjPitcherDao;
	
	// FANTASY PROS
	// BATTER
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

	// PITCHER
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
	
	// STEAMER
	// BATTER
	@RequestMapping(value = "/steamerprojb")
    public String steamerProjB(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// populate html table
		List<SteamerProjBatter> players = steamerProjBatterDao.findAllByOrderByOpsTotalSGPDesc();
		
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
	
	// PITCHER
	@RequestMapping(value = "/steamerprojp")
    public String steamerProjP(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// populate html table
		List<SteamerProjPitcher> players = steamerProjPitcherDao.findAllByOrderBySgpDesc();
		
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
	
	// YAHOO ROTO LEAGUE
	// BATTER
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
			FPProjBatter batter = userBatter.getFpProjBatter();
			
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

	// PITCHER
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
			FPProjPitcher pitcher = userPitcher.getFpProjPitcher();
			
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
	
	// OTTONEU OLD SCHOOL LEAGUE
	// BATTER
	@RequestMapping(value = "/user_ottoneu_old_school_steamerprojb", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/user_ottoneu_old_school_steamerprojb", method = RequestMethod.POST)
    public String userottoneuoldschoolfpprojb(Model model, HttpServletRequest request){
		// check for user in session
				String currentUser = this.getUsernameFromSession(request);
				User user = this.getUserFromSession(request);
				
				// get league and leaguekey from user selection in get
				String leagueKey = request.getParameter("league");
				OttoneuOldSchoolLeague league = ottoneuOldSchoolLeagueDao.findByLeagueKey(leagueKey);
				
				// pull player list
				List<SteamerProjBatter> batters = steamerProjBatterDao.findAllByOrderByAvgTotalSGPDesc();
				
				// if hist sgp has not been calculated for this league/user then calculate, otherwise continue
				if (userCustomRankingsBDao.findByUserAndOttoneuOldSchoolLeague(user, league).size() == 0) {
					for (SteamerProjBatter batter : batters) {
						UserCustomRankingsB userBatterSGP = new UserCustomRankingsB(batter, league, user);
						userCustomRankingsBDao.save(userBatterSGP);
					}
				}
				
				List<UserCustomRankingsB> userBatterList = userCustomRankingsBDao.findByUserAndOttoneuOldSchoolLeague(user, league);

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
				List<SteamerProjBatter> customBatterRankings = new ArrayList<SteamerProjBatter>();
				
				for (UserCustomRankingsB userBatter : userBatterList) {
					SteamerProjBatter batter = userBatter.getSteamerProjBatter();
					
					String name = batter.getName();
					String team = batter.getTeam();
//					String pos = batter.getPos();
					String playerId = batter.getPlayerId();
					int g = batter.getG();
					int pa = batter.getPa();
					int hbp = batter.getHbp();
					int cs = batter.getCs();
					double woba = batter.getWoba();
					double wrcPlus = batter.getWrcPlus();
					double bsr = batter.getBsr();
					double fld = batter.getFld();
					double offWar = batter.getOffWar();
					double defWar = batter.getDefWar();
					double war = batter.getWar();
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
					
					SteamerProjBatter customBatter = new SteamerProjBatter(name, team, playerId, g, pa, ab, h, dbl, tpl, hr, r, rbi, bb, k, hbp, sb, cs, avg, obp, slg, ops, woba, wrcPlus, bsr, fld, offWar, defWar, war, category);

					customBatter.setAvgTotalSGP(customSGP);
					customBatter.setAvgTotalAAV(customAAV);
					customBatterRankings.add(customBatter);
				}
				
				// get date of last data pull
				Date lastPullDate = batters.get(0).getCreated();
				// set category of data
				String category = "batter";
				String leagueType = "Ottoneu Old School";
						
		    	model.addAttribute("currentUser", currentUser);
				model.addAttribute("players", customBatterRankings);
				model.addAttribute("lastPullDate", lastPullDate);
				model.addAttribute("leagueType", leagueType);
				model.addAttribute("category", category);
		        model.addAttribute("user", user);

		        return "projections";
    }

	// PITCHER
	@RequestMapping(value = "/user_ottoneu_old_school_steamerprojp", method = RequestMethod.GET)
	public String userottoneuoldschoolsteamprojpform(Model model, HttpServletRequest request){
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
	
	@RequestMapping(value = "/user_ottoneu_old_school_steamerprojp", method = RequestMethod.POST)
    public String userottoneuoldschoolsteamerprojp(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		
		// get league and leaguekey from user selection in get
		String leagueKey = request.getParameter("league");
		OttoneuOldSchoolLeague league = ottoneuOldSchoolLeagueDao.findByLeagueKey(leagueKey);
		
		// pull player list
		List<SteamerProjPitcher> pitchers = steamerProjPitcherDao.findAllByOrderBySgpDesc();
		
		// if histsgp has not been calculated for this league/user then calculate, otherwise continue
		if (userCustomRankingsPDao.findByUserAndOttoneuOldSchoolLeague(user, league).size() == 0) {
			for (SteamerProjPitcher pitcher : pitchers) {
				UserCustomRankingsP userPitcherSGP = new UserCustomRankingsP(pitcher, league, user);
				userCustomRankingsPDao.save(userPitcherSGP);
			}
		}
		
		List<UserCustomRankingsP> userPitcherList = userCustomRankingsPDao.findByUserAndOttoneuOldSchoolLeague(user, league);

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
		List<SteamerProjPitcher> customPitcherRankings = new ArrayList<SteamerProjPitcher>();
		
		for (UserCustomRankingsP userPitcher : userPitcherList) {
			SteamerProjPitcher pitcher = userPitcher.getSteamerProjPitcher();
			
			String name = pitcher.getName();
			String team = pitcher.getTeam();
			String playerId = pitcher.getPlayerId();
			double ip = pitcher.getIp();
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
			double kNine = pitcher.getKNine();
			double bbNine = pitcher.getBbNine();
			double fip = pitcher.getFip();
			double war = pitcher.getWar();
			double raNineWAR = pitcher.getRaNineWAR();
			String category = pitcher.getCategory();
			UserCustomRankingsP userPitcherSGP = userCustomRankingsPDao.findBySteamerProjPitcherAndUserAndOttoneuOldSchoolLeague(pitcher, user, league);
			double customSGP = userPitcherSGP.getHistSGP();
			BigDecimal customAAV = userPitcherSGP.getHistAAV();

			SteamerProjPitcher sgpPitcher = new SteamerProjPitcher(name, team, playerId, ip, k, w, sv, era, whip, er, h, bb, hr, g, gs, l, kNine, bbNine, fip, war, raNineWAR, category);
			sgpPitcher.setSgp(customSGP);
			sgpPitcher.setAav(customAAV);
			customPitcherRankings.add(sgpPitcher);
		}
				
		// get date of last data pull
		Date lastPullDate = pitchers.get(0).getCreated();
		// set category of data
		String category = "pitcher";
		String leagueType = "Ottoneu Old School";
				
    	model.addAttribute("currentUser", currentUser);
		model.addAttribute("players", customPitcherRankings);
		model.addAttribute("lastPullDate", lastPullDate);
		model.addAttribute("leagueType", leagueType);
		model.addAttribute("category", category);
        model.addAttribute("user", user);

        return "projections";
    }
	
}