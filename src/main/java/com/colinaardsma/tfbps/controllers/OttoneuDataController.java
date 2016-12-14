package com.colinaardsma.tfbps.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.colinaardsma.tfbps.models.OttoneuOldSchoolLeague;
import com.colinaardsma.tfbps.models.OttoneuTeam;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.dao.OttoneuOldSchoolLeagueDao;
import com.colinaardsma.tfbps.models.dao.OttoneuTeamDao;
import com.colinaardsma.tfbps.models.dao.UserDao;

@Controller
public class OttoneuDataController extends AbstractController {

	@Autowired
	UserDao userdao;

	@Autowired
	OttoneuOldSchoolLeagueDao ottoneuOldSchoolLeagueDao;
	
	@Autowired
	OttoneuTeamDao ottoneuTeamDao;

	@RequestMapping(value = "/ottoneuleagueentry", method = RequestMethod.GET)
	public String ottoneuleagueentryform(Model model, HttpServletRequest request) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);

    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);

		return "ottoneuleagueentryform";
	}
	
	// http://www.smartfantasybaseball.com/e-book-using-standings-gain-points-to-rank-players-and-create-dollar-values/
	// https://ottoneu.fangraphs.com/642/rosterexport
	// https://jsoup.org/cookbook/extracting-data/selector-syntax
	@RequestMapping(value = "/ottoneuleagueentry", method = RequestMethod.POST)
    public String ottoneuleagueentry(Model model, HttpServletRequest request) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);

		// user defined variables
		int leagueNumber = Integer.parseInt(request.getParameter("leagueNumber"));
		int season = Integer.parseInt(request.getParameter("season"));
		
		// league variables
		String leagueName = null;
		String leagueURL = null;
		String leagueScoringStyle = null;
		
		// team variables
		int teamNumber = 0;
//		double teamKey = 0.0;
		String teamName = null;
		String teamURL = null;
		int teamBudget = 0; //teamUrl
//		int teamMoves = 0; // https://ottoneu.fangraphs.com/642/transactions
//		int teamTrades = 0; // https://ottoneu.fangraphs.com/642/transactions
		String teamManagerName = null; //teamUrl
		String leagueKey = leagueNumber + "." + season;
		
		List<OttoneuTeam> teamList = new ArrayList<OttoneuTeam>();
		
		// stats
		int rStats = 0;
		int hrStats = 0;
		int rbiStats = 0;
		int sbStats = 0;
		double avgStats = 0.0;
		double ipStats = 0.0;
		int wStats = 0;
		int svStats = 0;
		int kStats = 0;
		double eraStats = 0.0;
		double whipStats = 0.0;

		// points
		double rPoints = 0.0;
		double hrPoints = 0.0;
		double rbiPoints = 0.0;
		double sbPoints = 0.0;
		double avgPoints = 0.0;
		double wPoints = 0.0;
		double svPoints = 0.0;
		double kPoints = 0.0;
		double eraPoints = 0.0;
		double whipPoints = 0.0;
		double totalPoints = 0.0;

//		String prevYearKey = null;
		String notice = "League Created";
		
		// url variables
		String baseUrl = "http://ottoneu.fangraphs.com";
		String currentStandingsUrl = baseUrl + "/" + leagueNumber + "/standings";
		String seasonStandingsUrl = null;
		leagueURL = baseUrl + "/" + leagueNumber + "/home";
		String leagueOverviewUrl = baseUrl + "/" + leagueNumber + "/overview";
		
		// see if league already exists
		if (ottoneuOldSchoolLeagueDao.findByLeagueNumberAndSeason(leagueNumber, season) != null) {
			OttoneuOldSchoolLeague existingLeague = ottoneuOldSchoolLeagueDao.findByLeagueNumberAndSeason(leagueNumber, season);
			leagueName = existingLeague.getLeagueName();
			leagueURL = existingLeague.getLeagueURL();
			leagueScoringStyle = "Old School (5x5)";
			
			notice = "League Already Exists";
			teamList = ottoneuTeamDao.findByOttoneuOldSchoolLeague(existingLeague);
			
			model.addAttribute("teamList", teamList);
			model.addAttribute("notice", notice);
			model.addAttribute("leagueName", leagueName);
			model.addAttribute("leagueURL", leagueURL);
			model.addAttribute("leagueScoringStyle", leagueScoringStyle);
	    	model.addAttribute("currentUser", currentUser);
	        model.addAttribute("user", user);

	        return "ottoneuleagueentry";
	        }
				
		// check the current standings page for the link to each season's standings page
		try {
			Document currentStandings = Jsoup.connect(currentStandingsUrl).get();

			for (Element div : currentStandings.select("div[id=content]")) {
				for (Element p : div.select("p:containsOwn(Past Final Standings:)")) {
					Elements as = p.select("a");
					for (Element a: as) {
						if (a.ownText().equals(String.valueOf(season))) {
							seasonStandingsUrl = baseUrl + a.attr("href");
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// league
		// pull data from the season's league page
		try {
			Document leagueOverview = Jsoup.connect(leagueOverviewUrl).get();

			for (Element div : leagueOverview.select("div[id=content]")) {
				Elements h1s = div.select("h1");
				leagueName = h1s.get(0).text();
				for (Element table : div.select("table")) {
					for (Element tbody : table.select("tbody")) {
						Elements trs = tbody.select("tr");
						Elements tds = trs.get(4).select("td");
						leagueScoringStyle = tds.get(1).text();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		OttoneuOldSchoolLeague newLeague = new OttoneuOldSchoolLeague(leagueNumber, leagueName, leagueURL, season);
		List<User> newList = new ArrayList<User>();
		newList.add(user);
		newLeague.setUsers(newList);
		ottoneuOldSchoolLeagueDao.save(newLeague);

		// teams
		// pull data from the season's standings page
		try {
			Document seasonStandings = Jsoup.connect(seasonStandingsUrl).get();
		
			for (Element div : seasonStandings.select("div[id=content]")) {
				for (Element table : div.select("table[id=teamStatistics]")) {
					for (Element tbody : table.select("tbody")){
						for (Element tr : tbody.select("tr")) {
							Elements tds = tr.select("td");

							// team info
							teamName = tds.get(0).text();
							String href = tds.get(0).select("a").attr("href");
							teamURL = baseUrl + href;
							teamNumber = Integer.parseInt(href.substring(href.lastIndexOf("=") + 1));
							
							OttoneuTeam team = new OttoneuTeam(teamNumber, teamName, teamURL, leagueNumber, season, leagueKey);

							// stats
							rStats = Integer.parseInt(tds.get(1).text());
							hrStats = Integer.parseInt(tds.get(2).text());
							rbiStats = Integer.parseInt(tds.get(3).text());
							sbStats = Integer.parseInt(tds.get(4).text());
							avgStats = Double.parseDouble(tds.get(5).text());
							ipStats = Double.parseDouble(tds.get(6).text());
							wStats = Integer.parseInt(tds.get(7).text());
							svStats = Integer.parseInt(tds.get(8).text());
							kStats = Integer.parseInt(tds.get(9).text());
							eraStats = Double.parseDouble(tds.get(10).text());
							whipStats = Double.parseDouble(tds.get(11).text());
							
							team.setRStats(rStats);
							team.setHrStats(hrStats);
							team.setRbiStats(rbiStats);
							team.setSbStats(sbStats);
							team.setAvgStats(avgStats);
							team.setIpStats(ipStats);
							team.setWStats(wStats);
							team.setSvStats(svStats);
							team.setKStats(kStats);
							team.setEraStats(eraStats);
							team.setWhipStats(whipStats);
							
							team.setOttoneuOldSchoolLeague(newLeague);
							
							ottoneuTeamDao.save(team);							
						}
					}
				}

				for (Element table : div.select("table[id=teamRankings]")) {
					for (Element tbody : table.select("tbody")){
						for (Element tr : tbody.select("tr")) {
							Elements tds = tr.select("td");
							
							// team info
							String href = tds.get(0).select("a").attr("href");
							teamNumber = Integer.parseInt(href.substring(href.lastIndexOf("=") + 1));

							// points
							rPoints = Double.parseDouble(tds.get(1).text());
							hrPoints = Double.parseDouble(tds.get(2).text());
							rbiPoints = Double.parseDouble(tds.get(3).text());
							sbPoints = Double.parseDouble(tds.get(4).text());
							avgPoints = Double.parseDouble(tds.get(5).text());
							wPoints = Double.parseDouble(tds.get(6).text());
							svPoints = Double.parseDouble(tds.get(7).text());
							kPoints = Double.parseDouble(tds.get(8).text());
							eraPoints = Double.parseDouble(tds.get(9).text());
							whipPoints = Double.parseDouble(tds.get(10).text());
							totalPoints = Double.parseDouble(tds.get(11).text());

							OttoneuTeam team = ottoneuTeamDao.findByTeamNumber(teamNumber);

							team.setRPoints(rPoints);
							team.setHrPoints(hrPoints);
							team.setRbiPoints(rbiPoints);
							team.setSbPoints(sbPoints);
							team.setAvgPoints(avgPoints);
							team.setWPoints(wPoints);
							team.setSvPoints(svPoints);
							team.setKPoints(kPoints);
							team.setEraPoints(eraPoints);
							team.setWhipPoints(whipPoints);
							team.setTotalPoints(totalPoints);
							
							teamList.add(team);
							ottoneuTeamDao.save(team);							
						}
					}
				}
			}

			// add teamBudget, teamManagerName, and rank
			Collections.sort(teamList, new Comparator<OttoneuTeam>() { // sort list by user's custom sgp calculation (desc)
				@Override
				public int compare(OttoneuTeam t1, OttoneuTeam t2) {
					if (t1.getTotalPoints() < t2.getTotalPoints()) return 1;
					if (t1.getTotalPoints() > t2.getTotalPoints()) return -1;
					return 0;
				}
			});
			
			int rank = 12;
			
			for (OttoneuTeam team : teamList) {
				Document teamPage = Jsoup.connect(team.getTeamURL()).get();

				for (Element div : teamPage.select("div[id=left]")) {
					Elements h3s = div.select("h3");
					String owner = h3s.get(0).ownText();
					teamManagerName = owner.substring(owner.lastIndexOf(":") + 2);
					System.out.println("Manager Name: " + teamManagerName);

					for (Element h2 : div.select("h2[id=availableCap]")) {
						Elements spans = h2.select("span[class=green]");
						teamBudget = Integer.parseInt(spans.get(0).ownText().replace("$", ""));						
					}
				}
				OttoneuTeam savedTeam = ottoneuTeamDao.findByTeamNumber(team.getTeamNumber());
				savedTeam.setTeamManagerName(teamManagerName);
				savedTeam.setTeamBudget(teamBudget);
				savedTeam.setRank(rank);
				ottoneuTeamDao.save(savedTeam);
				
				rank--;
			}
			

//			// add teamMoves and teamTrades
//			for (OttoneuTeam team : teamList) {
//				Document teamPage = Jsoup.connect(teamURL).get();
//				
//				for (Element div : teamPage.select("div[id=left]")) {
//					Elements h3s = div.select("h3");
//					String owner = h3s.get(1).ownText();
//					teamManagerName = owner.substring(owner.lastIndexOf(":") + 1, owner.lastIndexOf("&"));
//					
//					for (Element h2 : div.select("h2[id=availableCap-mobile]")) {
//						for (Element span : h2.select("span[class=green]")) {
//							teamBudget = Integer.parseInt(span.ownText());
//						}
//					}
//				}
//				OttoneuTeam savedTeam = ottoneuTeamDao.findByTeamNumber(team.getTeamNumber());
//				savedTeam.setTeamManagerName(teamManagerName);
//				savedTeam.setTeamBudget(teamBudget);
//				ottoneuTeamDao.save(savedTeam);
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		model.addAttribute("teamList", teamList);
		model.addAttribute("notice", notice);
		model.addAttribute("leagueName", leagueName);
		model.addAttribute("leagueURL", leagueURL);
		model.addAttribute("leagueScoringStyle", leagueScoringStyle);
    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);

        return "ottoneuleagueentry";
    }
	
	@RequestMapping(value = "/linkottoneuteam", method = RequestMethod.POST)
	public String linkottoneuteam(Model model, HttpServletRequest request) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		int teamUid = Integer.parseInt(request.getParameter("team"));
		OttoneuTeam team = ottoneuTeamDao.findByUid(teamUid);
		
		// not getting any values in leaguenumber or season
		int leagueNumber = team.getLeagueNumber();
		int season = team.getSeason();
		System.out.println("leaguenumber: " + leagueNumber);
		System.out.println("seasn: " + season);
		team.setUser(user);
		ottoneuTeamDao.save(team);
		

		
		OttoneuOldSchoolLeague existingLeague = ottoneuOldSchoolLeagueDao.findByLeagueNumberAndSeason(leagueNumber, season);
		System.out.println("existing league: " + existingLeague);

		List<User> userList = new ArrayList<User>();
		if (existingLeague.getUsers() != null) {
			userList = existingLeague.getUsers();
			if (!userList.contains(user)) {
				userList.add(user);
				existingLeague.setUsers(userList);
				ottoneuOldSchoolLeagueDao.save(existingLeague);
			}
		} else {
			userList.add(user);
			existingLeague.setUsers(userList);
			ottoneuOldSchoolLeagueDao.save(existingLeague);
		}

    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);

		return "ottoneuleagueentry";
	}
	


}
