package com.colinaardsma.tfbps.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.FPProjPitcher;
import com.colinaardsma.tfbps.models.KeeperCosts;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.YahooRotoLeague;
import com.colinaardsma.tfbps.models.YahooRotoTeam;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;
import com.colinaardsma.tfbps.models.dao.FPProjPitcherDao;
import com.colinaardsma.tfbps.models.dao.KeeperCostsDao;
import com.colinaardsma.tfbps.models.dao.UserDao;
import com.colinaardsma.tfbps.models.dao.YahooRotoLeagueDao;
import com.colinaardsma.tfbps.models.dao.YahooRotoTeamDao;
import com.colinaardsma.tfbps.models.util.SGPMultCalc;
import com.colinaardsma.tfbps.models.util.TeamNameNormalization;
import com.colinaardsma.tfbps.models.util.YahooOAuth;

@Controller
public class YahooDataController extends AbstractController {

	@Autowired
	UserDao userdao;

	@Autowired
	YahooRotoLeagueDao yahooRotoLeagueDao;
	
	@Autowired
	YahooRotoTeamDao yahooRotoTeamDao;
	
	@Autowired
	FPProjBatterDao fpProjBatterDao;
	
	@Autowired
	FPProjPitcherDao fpProjPitcherDao;
	
	@Autowired
	KeeperCostsDao keeperCostsDao;
	
	private List<YahooRotoLeague> linkedLeagues = new ArrayList<YahooRotoLeague>();


	// https://developer.yahoo.com/fantasysports/guide/ResourcesAndCollections.html

	@RequestMapping(value = "/closewindow")
	public String closewindow() {
		return "closewindow";
	}
	
	@RequestMapping(value = "/useryahooleagues", method = RequestMethod.GET)
	public String useryahooleaguesform(Model model, HttpServletRequest request) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);

//		String teamsURL = "https://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games/teams"; // use if/when team names need to be added
		String leaguesURL = "https://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games/leagues";
		String leagueURL = null;
		String leagueKey = null;
		String leagueName = null;
//		String teamName = null; // use if/when team names need to be added
		String year = null;
		ArrayList<String[]> leagues = new ArrayList<String[]>();
		String userLeagues = null;
//		String userTeams = null; // use if/when team names need to be added

		try {
			checkOAuthExpiration(user); // make sure authorization is still valid, if not renew
			userLeagues = YahooOAuth.oauthGetRequest(leaguesURL, user);
//			userTeams = YahooOAuth.oauthGetRequest(teamsURL, user); // use if/when team names need to be added
//			System.out.println("Leagues:\n\n" + userLeagues);
//			System.out.println("Teams:\n\n" + userTeams); // use if/when team names need to be added

			// parse xml data
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(userLeagues));
			Document document = builder.parse(is);

			// iterate through the nodes and extract the data.	    
			NodeList leagueList = document.getElementsByTagName("league");
			for (int i = 0; i < leagueList.getLength(); i++) {
				Node leagueNode = leagueList.item(i);
				if (leagueNode.getNodeType() == Node.ELEMENT_NODE) {
					Element leagueElement = (Element) leagueNode;
					if (leagueElement.getElementsByTagName("game_code").item(0).getTextContent().equals("mlb")) { // only pull baseball data
						leagueName = leagueElement.getElementsByTagName("name").item(0).getTextContent();
						leagueURL = leagueElement.getElementsByTagName("url").item(0).getTextContent();
						leagueKey = leagueElement.getElementsByTagName("league_key").item(0).getTextContent();
//						teamName = leagueElement.getElementsByTagName("scoring_type").item(0).getTextContent(); // value doesn't exist in league xml, would need to pull from teams xml and merge
						year = leagueElement.getElementsByTagName("season").item(0).getTextContent();

						// add data to list for entry into html drop down
						String[] league = {year, leagueName, leagueURL, leagueKey};
						leagues.add(league);
						
//						// print values to console
//						System.out.println("League Name: " + leagueName);
//						System.out.println("League URL: " + leagueURL);
//						System.out.println("League Ley: " + leagueKey);
//						System.out.println("Year: " + year);
					}
				}

			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		// sort ArrayList in descending order by year
		Collections.sort(leagues,new Comparator<String[]>() {
            public int compare(String[] leagueArray, String[] nextLeagueArray) {
                return nextLeagueArray[0].compareTo(leagueArray[0]); // sort in descending order (swap String[]s to sort by ascending)
            }
        });

		model.addAttribute("leagues", leagues);
    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
		
		return "useryahooleagues";
	}
	
	@RequestMapping(value = "/useryahooleagues", method = RequestMethod.POST)
	public String useryahooleagues(Model model, HttpServletRequest request) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);

		// league variables
		String leagueKey = request.getParameter("league");
		String leagueHistory = request.getParameter("leagueHistory");
		String xmlData = null;
		String leagueName = null;
		String leagueURL = null;
		int auctionBudget = -1;
		int totalSpent = 0;
		int oneDollarB = 0;
		int oneDollarP = 0;
		//		String leagueScoringType = null;
		String prevYearKey = null;
		String nextYearKey = null;
		int prevYears = 0;
		int season = 0;
		int teamCount = 0;

		// team variables
		String teamKey = null;
		String teamName = null;
		String teamURL = null;
		int teamFAABBalance = 0;
		int teamMoves = 0;
		int teamTrades = 0;
		String teamGUID = null;
		String teamManagerName = null;
		int rank = 0;
		double totalPoints = 0.0;
		double habStats = 0.0; // stat_id = 60
		int rStats = 0; // stat_id = 7
		int hrStats = 0; // stat_id = 12
		int rbiStats = 0; // stat_id = 13
		int sbStats = 0; // stat_id = 16
		double avgStats = 0.0; // stat_id = 3
		double opsStats = 0.0; // stat_id = 55
		double ipStats = 0; // stat_id = 50
		int wStats = 0; // stat_id = 28
		int svStats = 0; // stat_id = 32
		int kStats = 0; // stat_id = 42
		double eraStats = 0.0; // stat_id = 26
		double whipStats = 0.0; // stat_id = 27
		double habPoints = 0.0; // stat_id = 60
		double rPoints = 0.0; // stat_id = 7
		double hrPoints = 0.0; // stat_id = 12
		double rbiPoints = 0.0; // stat_id = 13
		double sbPoints = 0.0; // stat_id = 16
		double avgPoints = 0.0; // stat_id = 3
		double opsPoints = 0.0; // stat_id = 55
		double ipPoints = 0.0; // stat_id = 50
		double wPoints = 0.0; // stat_id = 28
		double svPoints = 0.0; // stat_id = 32
		double kPoints = 0.0; // stat_id = 42
		double eraPoints = 0.0; // stat_id = 26
		double whipPoints = 0.0; // stat_id = 27
		
		// player variables
		String playerKey = null;
		String firstName = null;
		String lastName = null;
		String fullName = null;
		String teamAbbr = null;

		leagueHistory = (leagueHistory == null) ? "false" : "true";

		if (leagueHistory.equals("true")) {
			String py = request.getParameter("prevyears");
			//			System.out.println("Previous Years String: " + py);
			prevYears = Integer.parseInt(py);
			//			System.out.println("Previous Years int: " + prevYears);
		}

		do {
			String standingsURL = "https://fantasysports.yahooapis.com/fantasy/v2/leagues;league_keys=" + leagueKey + "/standings";

			int leagueCounter = 0;
			int maxTries = 3;

			while (leagueCounter < maxTries) { // retry if yahoo throws and error

				try {
					checkOAuthExpiration(user); // make sure authorization is still valid, if not renew

					// fantasysports.leagues.standings
					xmlData = YahooOAuth.oauthGetRequest(standingsURL, user);
//					System.out.println(xmlData);

					// parse xml data
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					InputSource is = new InputSource(new StringReader(xmlData));
					Document document = builder.parse(is);

					// leagues
					// iterate through the nodes and extract the data.	    
					NodeList leagueList = document.getElementsByTagName("league");
					for (int i = 0; i < leagueList.getLength(); i++) {
						Node leagueNode = leagueList.item(i);
						if (leagueNode.getNodeType() == Node.ELEMENT_NODE) {
							Element leagueElement = (Element) leagueNode;						
							leagueName = leagueElement.getElementsByTagName("name").item(0).getTextContent();
							leagueURL = leagueElement.getElementsByTagName("url").item(0).getTextContent();
							teamCount = Integer.parseInt(leagueElement.getElementsByTagName("num_teams").item(0).getTextContent());
//							leagueScoringType = leagueElement.getElementsByTagName("scoring_type").item(0).getTextContent();
							prevYearKey = leagueElement.getElementsByTagName("renew").item(0).getTextContent().replace("_", ".l.");
//							System.out.println("Previous Year Key: " + prevYearKey);
							nextYearKey = leagueElement.getElementsByTagName("renewed").item(0).getTextContent().replace("_", ".l.");
							season = Integer.parseInt(leagueElement.getElementsByTagName("season").item(0).getTextContent());
							Node standingsNode = leagueElement.getElementsByTagName("standings").item(0);
							Element standingsElement = (Element) standingsNode;
							Node teamsNode = standingsElement.getElementsByTagName("teams").item(0);
							Element teamsElement = (Element) teamsNode;
							NodeList teamList = teamsElement.getElementsByTagName("team");
							Node teamNode = teamsElement.getElementsByTagName("team").item(0);
							Element teamElement = (Element) teamNode;
							if (teamElement.getElementsByTagName("auction_budget_total").item(0) != null) {
								auctionBudget = Integer.parseInt(teamElement.getElementsByTagName("auction_budget_total").item(0).getTextContent());

								for (int j = 0; j < teamList.getLength(); j++) {
									Node teamListNode = teamList.item(j);
									Element teamListElement = (Element) teamListNode;
									totalSpent += Integer.parseInt(teamListElement.getElementsByTagName("auction_budget_spent").item(0).getTextContent());
								}
							}
						}
					}

					// if league exists add previous year key as required
					if (yahooRotoLeagueDao.findByLeagueKey(leagueKey) != null) {
						YahooRotoLeague existingLeague = yahooRotoLeagueDao.findByLeagueKey(leagueKey);

						// add current user to list of league managers
						List<User> managers = existingLeague.getUsers();
						if (!managers.contains(user)) {
							managers.add(user);
							existingLeague.setUsers(managers);
						}

						// link to previous year only if user requested
						if (prevYears >= 0) {
							existingLeague.setPreviousYearKey(prevYearKey);
						}
						if (yahooRotoLeagueDao.findByLeagueKey(prevYearKey) != null) { // look for previous year's league and link to this league
							existingLeague.setPreviousYearUID(yahooRotoLeagueDao.findByLeagueKey(prevYearKey).getUid());
						} else if (yahooRotoLeagueDao.findByLeagueKey(nextYearKey) != null) { // look for next year's legue and link to this league
							YahooRotoLeague nextYearLeague = yahooRotoLeagueDao.findByLeagueKey(nextYearKey);
							nextYearLeague.setPreviousYearKey(leagueKey);
							yahooRotoLeagueDao.save(nextYearLeague);
						}

						yahooRotoLeagueDao.save(existingLeague);
						linkedLeagues.add(existingLeague);

						// if league does not already exist create it
					} else {
						YahooRotoLeague newLeague = new YahooRotoLeague(leagueKey, leagueName, leagueURL, teamCount, season);
						newLeague.setAuctionBudget(auctionBudget);
						newLeague.setTotalSpent(totalSpent);

						// add current user to list of league managers
						List<User> managers = new ArrayList<User>();
						managers.add(user);
						newLeague.setUsers(managers);

						// link to previous year only if user requested
						if (prevYears >= 0) {
							newLeague.setPreviousYearKey(prevYearKey);
							if (yahooRotoLeagueDao.findByLeagueKey(prevYearKey) != null) {
								newLeague.setPreviousYearUID(yahooRotoLeagueDao.findByLeagueKey(prevYearKey).getUid());
							}

							yahooRotoLeagueDao.save(newLeague);

							if (yahooRotoLeagueDao.findByLeagueKey(nextYearKey) != null) {
								YahooRotoLeague nextYearLeague = yahooRotoLeagueDao.findByLeagueKey(nextYearKey);
								nextYearLeague.setPreviousYearKey(leagueKey);
								nextYearLeague.setPreviousYearUID(yahooRotoLeagueDao.findByLeagueKey(leagueKey).getUid());
								yahooRotoLeagueDao.save(nextYearLeague);
							}
						} else { // if no request to link to previous years
							yahooRotoLeagueDao.save(newLeague);
						}

						linkedLeagues.add(newLeague);

						// teams
						// iterate through the nodes and extract the data.	    
						NodeList teamList = document.getElementsByTagName("team");
						for (int i = 0; i < teamList.getLength(); i++) {

							// team information
							Node teamNode = teamList.item(i);
							if (teamNode.getNodeType() == Node.ELEMENT_NODE) {
								Element teamElement = (Element) teamNode;
								teamKey = teamElement.getElementsByTagName("team_key").item(0).getTextContent();
								teamName = teamElement.getElementsByTagName("name").item(0).getTextContent();
								teamURL = teamElement.getElementsByTagName("url").item(0).getTextContent();
								teamFAABBalance = teamElement.getElementsByTagName("faab_balance").item(0) != null ? Integer.parseInt(teamElement.getElementsByTagName("faab_balance").item(0).getTextContent()) : -1;
								teamMoves = Integer.parseInt(teamElement.getElementsByTagName("number_of_moves").item(0).getTextContent());
								teamTrades = Integer.parseInt(teamElement.getElementsByTagName("number_of_trades").item(0).getTextContent());
								teamGUID = teamElement.getElementsByTagName("guid").item(0).getTextContent();
								teamManagerName = teamElement.getElementsByTagName("nickname").item(0).getTextContent().replace("_", ".l.");
//								System.out.println("GOT TEAM DATA");

								// check to see if this user exists, if so skip to next
								if (yahooRotoTeamDao.findByTeamKey(teamKey) != null) {
									YahooRotoTeam existingTeam = yahooRotoTeamDao.findByTeamKey(teamKey);
									if (existingTeam.getTeamGUID().equals(user.getYahooGUID())) {
										existingTeam.setUser(user);
									}
									continue;
								}

								// stat values
								Node teamStatNode = teamElement.getElementsByTagName("team_stats").item(0);
								Element teamStatElement = (Element) teamStatNode;
								Node statsNode = teamStatElement.getElementsByTagName("stats").item(0);
								Element statsElement = (Element) statsNode;
								NodeList statList = statsElement.getElementsByTagName("stat");
								for(int j = 0; j < statList.getLength(); j++) {
									Node statNode = statList.item(j);
									Element statElement = (Element) statNode;

									int statCategory = Integer.parseInt(statElement.getElementsByTagName("stat_id").item(0).getTextContent());

									switch (statCategory) {
//									// hab
//									case 60 : habStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
//									break;
									// r
									case 7 : rStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// hr
									case 12 : hrStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// rbi
									case 13 : rbiStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// sb
									case 16 : sbStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
									break;
//									// avg
//									case 3 : avgStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
//									break;
									// ops
									case 55 : opsStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// ip
									case 50 : ipStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// w
									case 28 : wStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// sv
									case 32 : svStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// k
									case 42 : kStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// era
									case 26 : eraStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// whip
									case 27 : whipStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
									break;

									default : break;
									}
								}
						
								// stat points
								Node teamPointNode = teamElement.getElementsByTagName("team_points").item(0);
								Element teamPointElement = (Element) teamPointNode;
								Node pointsNode = teamPointElement.getElementsByTagName("stats").item(0);
								Element pointsElement = (Element) pointsNode;
								NodeList pointList = pointsElement.getElementsByTagName("stat");
								for(int k = 0; k < pointList.getLength(); k++) {
									Node pointNode = pointList.item(k);
									Element pointElement = (Element) pointNode;

									int pointCategory = Integer.parseInt(pointElement.getElementsByTagName("stat_id").item(0).getTextContent());

									switch (pointCategory) {
//									// hab
//									case 60 : habPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
//									break;
									// r
									case 7 : rPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// hr
									case 12 : hrPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// rbi
									case 13 : rbiPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// sb
									case 16 : sbPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
									break;
//									// avg
//									case 3 : avgPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
//									break;
									// ops
									case 55 : opsPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
									break;
//									// ip
//									case 50 : ipPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
//									break;
									// w
									case 28 : wPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// sv
									case 32 : svPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// k
									case 42 : kPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// era
									case 26 : eraPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
									break;
									// whip
									case 27 : whipPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
									break;

									default : break;
									}
								}

								// stat points
								NodeList teamStandingList = teamElement.getElementsByTagName("team_standings");
								for(int l = 0; l < teamStandingList.getLength(); l++) {
									Node teamStandingNode = teamStandingList.item(l);
									if (teamStandingNode.getNodeType() == Node.ELEMENT_NODE) {
										Element teamStandingElement = (Element) teamStandingNode;
										rank = Integer.parseInt(teamStandingElement.getElementsByTagName("rank").item(0).getTextContent());
										totalPoints = Double.parseDouble(teamStandingElement.getElementsByTagName("points_for").item(0).getTextContent());
									}
								}
							}

							YahooRotoTeam newTeam = new YahooRotoTeam(teamKey, teamName, teamURL, teamFAABBalance, teamMoves, teamTrades, teamGUID, teamManagerName, leagueKey, habStats, 
									rStats, hrStats, rbiStats, sbStats, avgStats, opsStats, ipStats, wStats, svStats, kStats, eraStats, whipStats, habPoints, rPoints, hrPoints, rbiPoints, 
									sbPoints, avgPoints, opsPoints, ipPoints, wPoints, svPoints, kPoints, eraPoints, whipPoints, rank, totalPoints);

							if (newTeam.getTeamGUID().equals(user.getYahooGUID())) {
								newTeam.setUser(user);
							}
							newTeam.setYahooRotoLeague(yahooRotoLeagueDao.findByLeagueKey(leagueKey));
							yahooRotoTeamDao.save(newTeam);
						}
				
				
						// calculate # of batters and pitchers per league
						// fantasysports.leagues.settings
						String settingsURL = "https://fantasysports.yahooapis.com/fantasy/v2/leagues;league_keys=" + leagueKey + "/settings";

						xmlData = YahooOAuth.oauthGetRequest(settingsURL, user);
//						System.out.println(xmlData);

						// parse xml data
						factory = DocumentBuilderFactory.newInstance();
						builder = factory.newDocumentBuilder();
						is = new InputSource(new StringReader(xmlData));
						document = builder.parse(is);

						int teamBatters = 0;
						int teamPitchers = 0;
						int teamBench = 0;

						// iterate through the nodes and extract the data.
						Node settingsNode = document.getElementsByTagName("settings").item(0);
						if (settingsNode.getNodeType() == Node.ELEMENT_NODE) {
							Element settingsElement = (Element) settingsNode;
							Node rosterPosNode = settingsElement.getElementsByTagName("roster_positions").item(0);
							Element rosterPosElement = (Element) rosterPosNode;
							NodeList rosterPosList = rosterPosElement.getElementsByTagName("roster_position");
							for (int i = 0; i < rosterPosList.getLength(); i++) {
								Node rpNode = rosterPosList.item(i);
								Element rpElement = (Element) rpNode;

								if (rpElement.getElementsByTagName("position").item(0).getTextContent().equals("BN")) {
									teamBench += Integer.parseInt(rpElement.getElementsByTagName("count").item(0).getTextContent());
								} else if (rpElement.getElementsByTagName("position_type").item(0) != null) {

									String positionType = rpElement.getElementsByTagName("position_type").item(0).getTextContent();

									switch(positionType) {
									case "B" : teamBatters += Integer.parseInt(rpElement.getElementsByTagName("count").item(0).getTextContent());
									break;
									case "P" : teamPitchers += Integer.parseInt(rpElement.getElementsByTagName("count").item(0).getTextContent());
									break;
									}
								}
							}
						}
				
						YahooRotoLeague league = yahooRotoLeagueDao.findByLeagueKey(leagueKey);

						int counter = 0;
						// calculate % of money spent on batters and pitchers
						// fantasysports.draftresults
						if (auctionBudget != -1) {
							int currentYear = Calendar.getInstance().get(Calendar.YEAR);

							for (int i = 1; i <= league.getTeamCount(); i++) {
								teamKey = leagueKey + ".t." + i;
								YahooRotoTeam fantTeam = yahooRotoTeamDao.findByTeamKey(teamKey);

								String finalRosterURL = "https://fantasysports.yahooapis.com/fantasy/v2/teams;team_keys=" + teamKey + "/roster;date=" + currentYear + "-12-31";
								
								String finalRosterXmlData = YahooOAuth.oauthGetRequest(finalRosterURL, user);

								// parse xml data
								DocumentBuilderFactory finalRosterFactory = DocumentBuilderFactory.newInstance();
								DocumentBuilder finalRosterBuilder = finalRosterFactory.newDocumentBuilder();
								InputSource finalRosterIS = new InputSource(new StringReader(finalRosterXmlData));
								Document finalRosterDocument = finalRosterBuilder.parse(finalRosterIS);

								// iterate through the nodes and extract the data.
								NodeList playerNodeList = finalRosterDocument.getElementsByTagName("player");
								for (int j = 0; j < playerNodeList.getLength(); j++) {
									Node playerNode = playerNodeList.item(j);
									if (playerNode.getNodeType() == Node.ELEMENT_NODE) {
										Element playerElement = (Element) playerNode;

										// get playerKey
										playerKey = playerElement.getElementsByTagName("player_key").item(0).getTextContent();

										// get player name
										NodeList playerNameList = playerElement.getElementsByTagName("name");
										for (int k = 0; k < playerNameList.getLength(); k++) {
											Node playerNameNode = playerNameList.item(k);
											Element playerNameElement = (Element) playerNameNode;

											// extract player name
											firstName = playerNameElement.getElementsByTagName("ascii_first").item(0).getTextContent();
											lastName = playerNameElement.getElementsByTagName("ascii_last").item(0).getTextContent();
											fullName = firstName + " " + lastName;
											System.out.println("Player Name: " + "_" + fullName + "_");
											
											// get player team abbreviation
											teamAbbr = playerElement.getElementsByTagName("editorial_team_abbr").item(0).getTextContent().toUpperCase();
											teamAbbr = TeamNameNormalization.Normalize(teamAbbr); // normalize team name
											System.out.println("Team Abbr: " + "_" + teamAbbr + "_");
											
											// get player position type (B or P)
											String posType = playerElement.getElementsByTagName("position_type").item(0).getTextContent();

											if (fpProjBatterDao.findByNameAndTeam(fullName, teamAbbr) == null && fpProjPitcherDao.findByNameAndTeam(fullName, teamAbbr) == null) {
												KeeperCosts batterCost = new KeeperCosts(playerKey, league, fantTeam);
												keeperCostsDao.save(batterCost);
											} else {
												if (posType.equals("B")) {
													FPProjBatter batter = fpProjBatterDao.findByNameAndTeam(fullName, teamAbbr);
													System.out.println("FPProj Batter: " + batter.getName());
													KeeperCosts batterCost = new KeeperCosts(playerKey, batter, league, fantTeam);
													keeperCostsDao.save(batterCost);
													System.out.println("Keeper Batter: " + batterCost.getBatter().getName());
												} else {
													FPProjPitcher pitcher = fpProjPitcherDao.findByNameAndTeam(fullName, teamAbbr);
													System.out.println("FPProj Pitcher: " + pitcher.getName());
													KeeperCosts pitcherCost = new KeeperCosts(playerKey, pitcher, league, fantTeam);
													keeperCostsDao.save(pitcherCost);
													System.out.println("Keeper Pitcher: " + pitcherCost.getPitcher().getName());
												}
											}
										}
									}
								}
							}

							String drafResultsURL = "https://fantasysports.yahooapis.com/fantasy/v2/leagues;league_keys=" + leagueKey + "/draftresults";

							xmlData = YahooOAuth.oauthGetRequest(drafResultsURL, user);
//							System.out.println(xmlData);

							// parse xml data
							factory = DocumentBuilderFactory.newInstance();
							builder = factory.newDocumentBuilder();
							is = new InputSource(new StringReader(xmlData));
							document = builder.parse(is);

							int draftedB = 0;
							int draftedP = 0;
							int dollarsB = 0;
							int dollarsP = 0;

							// iterate through the nodes and extract the data.
							Node draftResultsNode = document.getElementsByTagName("draft_results").item(0);
							if (draftResultsNode.getNodeType() == Node.ELEMENT_NODE) {
								Element draftResultsElement = (Element) draftResultsNode;
								NodeList draftResultList = draftResultsElement.getElementsByTagName("draft_result");
								for (int i = 0; i < draftResultList.getLength(); i++) {
									Node draftResultNode = draftResultList.item(i);
									Element draftResultElement = (Element) draftResultNode;
									playerKey = draftResultElement.getElementsByTagName("player_key").item(0).getTextContent();
									teamKey = draftResultElement.getElementsByTagName("team_key").item(0).getTextContent();

									// add costs to final rosters
									// determine if player is batter or pitcher
									String playerURL = "https://fantasysports.yahooapis.com/fantasy/v2/players;player_keys=" + playerKey;

									int playerCounter = 0;
									int playerMaxTries = maxTries;
								
									while (playerCounter < playerMaxTries) { // retry if yahoo throws and error
										try {

											String playerXmlData = YahooOAuth.oauthGetRequest(playerURL, user);

											// parse xml data
											DocumentBuilderFactory playerFactory = DocumentBuilderFactory.newInstance();
											DocumentBuilder playerBuilder = playerFactory.newDocumentBuilder();
											InputSource playerIS = new InputSource(new StringReader(playerXmlData));
											Document playerDocument = playerBuilder.parse(playerIS);

											counter++;
											
											// extract player name and team
											firstName = playerDocument.getElementsByTagName("ascii_first").item(0).getTextContent();
											lastName = playerDocument.getElementsByTagName("ascii_last").item(0).getTextContent();
											fullName = firstName + " " + lastName;
											teamAbbr = playerDocument.getElementsByTagName("editorial_team_abbr").item(0).getTextContent().toUpperCase();
											
											// determine original draft team for player
											String draftTeamKey = draftResultElement.getElementsByTagName("team_key").item(0).getTextContent();

											// iterate through the nodes and extract the data.
											if (playerDocument.getElementsByTagName("position_type").item(0).getTextContent().equals("B")) {
												draftedB++;
												int cost = Integer.parseInt(draftResultElement.getElementsByTagName("cost").item(0).getTextContent());
												dollarsB += cost;
												if (cost == 1) {
													oneDollarB++;
												}
												// TODO: change cost increment to league custom $
												// TODO: custom FAAB rules
												if (fpProjBatterDao.findByNameAndTeam(fullName, teamAbbr) != null) { // see if player is in projections
													FPProjBatter batter = fpProjBatterDao.findByNameAndTeam(fullName, teamAbbr);
//													System.out.println("FPProjBatter: " + batter.getName());
													if (keeperCostsDao.findByBatterAndYahooRotoLeague(batter, league) != null) { // see if player is on a final roster
														KeeperCosts batterCost = keeperCostsDao.findByBatterAndYahooRotoLeague(batter, league);
//														System.out.println("KeeperBatter: " + batterCost.getPlayerKey());
														String finalTeamKey = batterCost.getYahooRotoTeam().getTeamKey();
														if (draftTeamKey.equals(finalTeamKey)) { // if player is still on original draft team then add $5 to cost and save
															batterCost.setCost(cost + 5);
														} else {
															batterCost.setCost(5);
														}
														keeperCostsDao.save(batterCost);
													}
												}
											} else if (playerDocument.getElementsByTagName("position_type").item(0).getTextContent().equals("P")) {
												draftedP++;
												int cost = Integer.parseInt(draftResultElement.getElementsByTagName("cost").item(0).getTextContent());
												dollarsP += cost;
												if (cost == 1) {
													oneDollarP++;
												}
												if (fpProjPitcherDao.findByNameAndTeam(fullName, teamAbbr) != null) { // see if player is in projections
													FPProjPitcher pitcher = fpProjPitcherDao.findByNameAndTeam(fullName, teamAbbr);
													if (keeperCostsDao.findByPitcherAndYahooRotoLeague(pitcher, league) != null) { // see if player is on a final roster
														KeeperCosts pitcherCost = keeperCostsDao.findByPitcherAndYahooRotoLeague(pitcher, league);
														String finalTeamKey = pitcherCost.getYahooRotoTeam().getTeamKey();
														if (draftTeamKey.equals(finalTeamKey)) { // if player is still on original draft team then add $5 to cost and save
															pitcherCost.setCost(cost + 5);
														} else {
															pitcherCost.setCost(5);
														}
														keeperCostsDao.save(pitcherCost);
													}
												}
										}
											playerCounter = playerMaxTries; // exit yahoo error while loop
										} catch (IOException e) { // if yahoo throws a 500 error count it as a B or P at whatever the cost was
											if (playerCounter < playerMaxTries) {
												continue;
											} else {
												e.printStackTrace();
												if (counter % 2 == 0) {
													draftedB++;
													int cost = Integer.parseInt(draftResultElement.getElementsByTagName("cost").item(0).getTextContent());
													dollarsB += cost;
													if (cost == 1) {
														oneDollarB++;
													}
												} else {
													draftedP++;
													int cost = Integer.parseInt(draftResultElement.getElementsByTagName("cost").item(0).getTextContent());
													dollarsP += cost;
													if (cost == 1) {
														oneDollarP++;
													}
												}
												counter++;
											}
										}
									}
								}
							}

							// calculate percentage of league budget spent on batters and pitchers
							double budgetPctB = new BigDecimal(dollarsB).divide(new BigDecimal(totalSpent), 4, RoundingMode.HALF_UP).doubleValue();
							double budgetPctP = new BigDecimal(dollarsP).divide(new BigDecimal(totalSpent), 4, RoundingMode.HALF_UP).doubleValue();

							league.setDraftedB(draftedB);
							league.setDraftedP(draftedP);
							league.setBudgetPctB(budgetPctB);
							league.setBudgetPctP(budgetPctP);
							league.setOneDollarB(oneDollarB);
							league.setOneDollarP(oneDollarP);
						}
						int teamRosterSize = teamBatters + teamPitchers + teamBench;

						league.setTeamBatters(teamBatters);
						league.setTeamPitchers(teamPitchers);
						league.setTeamBench(teamBench);
						league.setTeamRosterSize(teamRosterSize);

						// calculate league SGP and save to db
						List<YahooRotoTeam> teams = yahooRotoTeamDao.findByLeagueKey(leagueKey); // pull list of teams in league

						// add runs
						List<Integer> rs = new ArrayList<Integer>();
						for (YahooRotoTeam team : teams) {
							rs.add(team.getRStats());
						}
						Collections.sort(rs); // sort list from smallest to largest
						league.setRSGPMult(SGPMultCalc.calcRSGPMult(rs)); // calculate average and store in league

						// add hrs
						List<Integer> hrs = new ArrayList<Integer>();
						for (YahooRotoTeam team : teams) {
							hrs.add(team.getHrStats());
						}
						Collections.sort(hrs); // sort list from smallest to largest
						league.setHrSGPMult(SGPMultCalc.calcHrSGPMult(hrs)); // calculate average and store in league

						// add rbi
						List<Integer> rbis = new ArrayList<Integer>();
						for (YahooRotoTeam team : teams) {
							rbis.add(team.getRbiStats());
						}
						Collections.sort(rbis); // sort list from smallest to largest
						league.setRbiSGPMult(SGPMultCalc.calcRbiSGPMult(rbis)); // calculate average and store in league

						// add sb
						List<Integer> sbs = new ArrayList<Integer>();
						for (YahooRotoTeam team : teams) {
							sbs.add(team.getSbStats());
						}
						Collections.sort(sbs); // sort list from smallest to largest
						league.setSbSGPMult(SGPMultCalc.calcSbSGPMult(sbs)); // calculate average and store in league

						// add avg
						List<Double> opss = new ArrayList<Double>();
						for (YahooRotoTeam team : teams) {
							opss.add(team.getOpsStats());
						}
						Collections.sort(opss); // sort list from smallest to largest
						league.setOpsSGPMult(SGPMultCalc.calcOpsSGPMult(opss)); // calculate average and store in league

						// add wins
						List<Integer> ws = new ArrayList<Integer>();
						for (YahooRotoTeam team : teams) {
							ws.add(team.getWStats());
						}
						Collections.sort(ws); // sort list from smallest to largest
						league.setWSGPMult(SGPMultCalc.calcWSGPMult(ws)); // calculate average and store in league

						// add saves
						List<Integer> svs = new ArrayList<Integer>();
						for (YahooRotoTeam team : teams) {
							svs.add(team.getSvStats());
						}
						Collections.sort(svs); // sort list from smallest to largest
						league.setSvSGPMult(SGPMultCalc.calcSvSGPMult(svs)); // calculate average and store in league

						// add k
						List<Integer> ks = new ArrayList<Integer>();
						for (YahooRotoTeam team : teams) {
							ks.add(team.getKStats());
						}
						Collections.sort(ks); // sort list from smallest to largest
						league.setKSGPMult(SGPMultCalc.calcKSGPMult(ks)); // calculate average and store in league

						// add era
						List<Double> eras = new ArrayList<Double>();
						for (YahooRotoTeam team : teams) {
							eras.add(team.getEraStats());
						}
						Collections.sort(eras); // sort list from smallest to largest
						league.setEraSGPMult(SGPMultCalc.calcEraSGPMult(eras)); // calculate average and store in league

						// add whip
						List<Double> whips = new ArrayList<Double>();
						for (YahooRotoTeam team : teams) {
							whips.add(team.getWhipStats());
						}
						Collections.sort(whips); // sort list from smallest to largest
						league.setWhipSGPMult(SGPMultCalc.calcWhipSGPMult(whips)); // calculate average and store in league

						yahooRotoLeagueDao.save(league); // save
					}

					leagueCounter = maxTries; // exit yahoo error while loop
				} catch (IOException e) {
					if (leagueCounter < maxTries) {
						continue;
					} else {
						e.printStackTrace();
					}
				} catch (ParserConfigurationException | SAXException e1) {
					e1.printStackTrace();
				}
			}

			oneDollarB = 0;
			oneDollarP = 0;
			totalSpent = 0;
			auctionBudget = -1;
			leagueKey = prevYearKey;
			prevYears -= 1;

		} while (prevYears >= 0);
		
		// calculate historical sgp and aav for each year (change value in years variable to change the number of years)
		int years = 3;
		for (int i = 0; i < linkedLeagues.size(); i++) {
			YahooRotoLeague league = yahooRotoLeagueDao.findByLeagueKey(linkedLeagues.get(i).getLeagueKey());
			List<YahooRotoLeague> leagueHist = new ArrayList<YahooRotoLeague>();
			// add league plus 2 years prior to new leagueHist list (total of 3 years)
			for (int j = i; ((j < i + years) && (j < linkedLeagues.size())); j++) {
				leagueHist.add(linkedLeagues.get(j));
			}
			league.calcHistSGPs(leagueHist); // calculate historical SGPs
			league.calcHistAAVs(leagueHist); // calculate historical AAVs
			league.calcHistDollarPlayers(leagueHist); // calculate historical $1 players
			yahooRotoLeagueDao.save(league); // save
		}

		model.addAttribute("linkedLeagues", linkedLeagues);
//		model.addAttribute("leagueName", leagueName);
//		model.addAttribute("leagueURL", leagueURL);
//		model.addAttribute("leagueScoringType", leagueScoringType);
		model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);

		return "useryahooleagues";
	}
	
	public void checkOAuthExpiration(User user) {

		String oauth_access_token = user.getYahooOAuthAccessToken();
		String oauth_session_handle = user.getYahooOAuthSessionHandle();
		String oauth_access_token_secret = user.getYahooOAuthTokenSecret();
		Date oauth_expiration = user.getYahooOAuthTokenExpiration();
		String oauth_expires_in = null;
		String oauth_authorization_expires_in = null;
		String xoauth_yahoo_guid;

		// check to see if oauth token is still valid
		Date now = new Date(System.currentTimeMillis());
		if (oauth_expiration.before(now)) { // if expiration is before now then renew
			try {
				String access_token = YahooOAuth.refreshAccessToken(oauth_access_token, oauth_session_handle, oauth_access_token_secret);

				// parse oauth token values from string returned
				int index = access_token.indexOf("oauth_token=") + "oauth_token=".length();
				oauth_access_token = access_token.substring(index, access_token.indexOf("&",index));
				index = access_token.indexOf("oauth_token_secret=") + "oauth_token_secret=".length();
				oauth_access_token_secret = access_token.substring(index, access_token.indexOf("&",index));
				index = access_token.indexOf("oauth_expires_in=") + "oauth_expires_in=".length();
				oauth_expires_in = access_token.substring(index, access_token.indexOf("&",index));
				index = access_token.indexOf("oauth_session_handle=") + "oauth_session_handle=".length();
				oauth_session_handle = access_token.substring(index, access_token.indexOf("&",index));
				index = access_token.indexOf("oauth_authorization_expires_in=") + "oauth_authorization_expires_in=".length();
				oauth_authorization_expires_in = access_token.substring(index, access_token.indexOf("&",index));
				index = access_token.indexOf("xoauth_yahoo_guid=") + "xoauth_yahoo_guid=".length();
				xoauth_yahoo_guid = access_token.substring(index, access_token.length());

				// print values to log
				System.out.println("***RENEWING OAUTH TOKEN***");
				System.out.println("oauth_token=" + oauth_access_token);
				System.out.println("oauth_token_secret=" + oauth_access_token_secret);
				System.out.println("oauth_expires_in=" + oauth_expires_in);
				System.out.println("oauth_session_handle=" + oauth_session_handle);
				System.out.println("oauth_authorization_expires_in=" + oauth_authorization_expires_in);
				System.out.println("xoauth_yahoo_guid=" + xoauth_yahoo_guid);

				user.setYahooOAuthAccessToken(oauth_access_token);
				user.setYahooOAuthSessionHandle(oauth_session_handle);
				user.setYahooOAuthTokenSecret(oauth_access_token_secret);
				Date expiration = new Date(System.currentTimeMillis() + (Long.parseLong(oauth_expires_in) * 1000));
				user.setYahooOAuthTokenExpiration(expiration);
				userDao.save(user);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Document getDocument(String standingsURL, User user) throws IOException, ParserConfigurationException, SAXException {
		// fantasysports.leagues.standings
		String xmlData = YahooOAuth.oauthGetRequest(standingsURL, user);
//		System.out.println(xmlData);

		// parse xml data
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xmlData));
		Document document = builder.parse(is);

		return document;
	}
	
	public void getLeagues(User user, String leagueKey, String leagueHistory) {
		// league variables
		String xmlData = null;
		String leagueName = null;
		String leagueURL = null;
		int auctionBudget = -1;
		int totalSpent = 0;
		int oneDollarB = 0;
		int oneDollarP = 0;
		//		String leagueScoringType = null;
		String prevYearKey = null;
		String nextYearKey = null;
		int prevYears = 0;
		int season = 0;
		int teamCount = 0;
		
		// leagues
		// iterate through the nodes and extract the data.	    
		NodeList leagueList = document.getElementsByTagName("league");
		for (int i = 0; i < leagueList.getLength(); i++) {
			Node leagueNode = leagueList.item(i);
			if (leagueNode.getNodeType() == Node.ELEMENT_NODE) {
				Element leagueElement = (Element) leagueNode;						
				leagueName = leagueElement.getElementsByTagName("name").item(0).getTextContent();
				leagueURL = leagueElement.getElementsByTagName("url").item(0).getTextContent();
				teamCount = Integer.parseInt(leagueElement.getElementsByTagName("num_teams").item(0).getTextContent());
//				leagueScoringType = leagueElement.getElementsByTagName("scoring_type").item(0).getTextContent();
				prevYearKey = leagueElement.getElementsByTagName("renew").item(0).getTextContent().replace("_", ".l.");
//				System.out.println("Previous Year Key: " + prevYearKey);
				nextYearKey = leagueElement.getElementsByTagName("renewed").item(0).getTextContent().replace("_", ".l.");
				season = Integer.parseInt(leagueElement.getElementsByTagName("season").item(0).getTextContent());
				Node standingsNode = leagueElement.getElementsByTagName("standings").item(0);
				Element standingsElement = (Element) standingsNode;
				Node teamsNode = standingsElement.getElementsByTagName("teams").item(0);
				Element teamsElement = (Element) teamsNode;
				NodeList teamList = teamsElement.getElementsByTagName("team");
				Node teamNode = teamsElement.getElementsByTagName("team").item(0);
				Element teamElement = (Element) teamNode;
				if (teamElement.getElementsByTagName("auction_budget_total").item(0) != null) {
					auctionBudget = Integer.parseInt(teamElement.getElementsByTagName("auction_budget_total").item(0).getTextContent());

					for (int j = 0; j < teamList.getLength(); j++) {
						Node teamListNode = teamList.item(j);
						Element teamListElement = (Element) teamListNode;
						totalSpent += Integer.parseInt(teamListElement.getElementsByTagName("auction_budget_spent").item(0).getTextContent());
					}
				}
			}
		}

		// if league exists add previous year key as required
		if (yahooRotoLeagueDao.findByLeagueKey(leagueKey) != null) {
			YahooRotoLeague existingLeague = yahooRotoLeagueDao.findByLeagueKey(leagueKey);

			// add current user to list of league managers
			List<User> managers = existingLeague.getUsers();
			if (!managers.contains(user)) {
				managers.add(user);
				existingLeague.setUsers(managers);
			}

			// link to previous year only if user requested
			if (prevYears >= 0) {
				existingLeague.setPreviousYearKey(prevYearKey);
			}
			if (yahooRotoLeagueDao.findByLeagueKey(prevYearKey) != null) { // look for previous year's league and link to this league
				existingLeague.setPreviousYearUID(yahooRotoLeagueDao.findByLeagueKey(prevYearKey).getUid());
			} else if (yahooRotoLeagueDao.findByLeagueKey(nextYearKey) != null) { // look for next year's legue and link to this league
				YahooRotoLeague nextYearLeague = yahooRotoLeagueDao.findByLeagueKey(nextYearKey);
				nextYearLeague.setPreviousYearKey(leagueKey);
				yahooRotoLeagueDao.save(nextYearLeague);
			}

			yahooRotoLeagueDao.save(existingLeague);
			linkedLeagues.add(existingLeague);

			// if league does not already exist create it
		} else {
			YahooRotoLeague newLeague = new YahooRotoLeague(leagueKey, leagueName, leagueURL, teamCount, season);
			newLeague.setAuctionBudget(auctionBudget);
			newLeague.setTotalSpent(totalSpent);

			// add current user to list of league managers
			List<User> managers = new ArrayList<User>();
			managers.add(user);
			newLeague.setUsers(managers);

			// link to previous year only if user requested
			if (prevYears >= 0) {
				newLeague.setPreviousYearKey(prevYearKey);
				if (yahooRotoLeagueDao.findByLeagueKey(prevYearKey) != null) {
					newLeague.setPreviousYearUID(yahooRotoLeagueDao.findByLeagueKey(prevYearKey).getUid());
				}

				yahooRotoLeagueDao.save(newLeague);

				if (yahooRotoLeagueDao.findByLeagueKey(nextYearKey) != null) {
					YahooRotoLeague nextYearLeague = yahooRotoLeagueDao.findByLeagueKey(nextYearKey);
					nextYearLeague.setPreviousYearKey(leagueKey);
					nextYearLeague.setPreviousYearUID(yahooRotoLeagueDao.findByLeagueKey(leagueKey).getUid());
					yahooRotoLeagueDao.save(nextYearLeague);
				}
			} else { // if no request to link to previous years
				yahooRotoLeagueDao.save(newLeague);
			}

			linkedLeagues.add(newLeague);
		}
	}
	
	public void getTeams(User user, String leagueKey) {
		// team variables
		String teamKey = null;
		String teamName = null;
		String teamURL = null;
		int teamFAABBalance = 0;
		int teamMoves = 0;
		int teamTrades = 0;
		String teamGUID = null;
		String teamManagerName = null;
		int rank = 0;
		double totalPoints = 0.0;
		double habStats = 0.0; // stat_id = 60
		int rStats = 0; // stat_id = 7
		int hrStats = 0; // stat_id = 12
		int rbiStats = 0; // stat_id = 13
		int sbStats = 0; // stat_id = 16
		double avgStats = 0.0; // stat_id = 3
		double opsStats = 0.0; // stat_id = 55
		double ipStats = 0; // stat_id = 50
		int wStats = 0; // stat_id = 28
		int svStats = 0; // stat_id = 32
		int kStats = 0; // stat_id = 42
		double eraStats = 0.0; // stat_id = 26
		double whipStats = 0.0; // stat_id = 27
		double habPoints = 0.0; // stat_id = 60
		double rPoints = 0.0; // stat_id = 7
		double hrPoints = 0.0; // stat_id = 12
		double rbiPoints = 0.0; // stat_id = 13
		double sbPoints = 0.0; // stat_id = 16
		double avgPoints = 0.0; // stat_id = 3
		double opsPoints = 0.0; // stat_id = 55
		double ipPoints = 0.0; // stat_id = 50
		double wPoints = 0.0; // stat_id = 28
		double svPoints = 0.0; // stat_id = 32
		double kPoints = 0.0; // stat_id = 42
		double eraPoints = 0.0; // stat_id = 26
		double whipPoints = 0.0; // stat_id = 27

		// teams
		// iterate through the nodes and extract the data.	    
		NodeList teamList = document.getElementsByTagName("team");
		for (int i = 0; i < teamList.getLength(); i++) {

			// team information
			Node teamNode = teamList.item(i);
			if (teamNode.getNodeType() == Node.ELEMENT_NODE) {
				Element teamElement = (Element) teamNode;
				teamKey = teamElement.getElementsByTagName("team_key").item(0).getTextContent();
				teamName = teamElement.getElementsByTagName("name").item(0).getTextContent();
				teamURL = teamElement.getElementsByTagName("url").item(0).getTextContent();
				teamFAABBalance = teamElement.getElementsByTagName("faab_balance").item(0) != null ? Integer.parseInt(teamElement.getElementsByTagName("faab_balance").item(0).getTextContent()) : -1;
				teamMoves = Integer.parseInt(teamElement.getElementsByTagName("number_of_moves").item(0).getTextContent());
				teamTrades = Integer.parseInt(teamElement.getElementsByTagName("number_of_trades").item(0).getTextContent());
				teamGUID = teamElement.getElementsByTagName("guid").item(0).getTextContent();
				teamManagerName = teamElement.getElementsByTagName("nickname").item(0).getTextContent().replace("_", ".l.");
//				System.out.println("GOT TEAM DATA");

				// check to see if this user exists, if so skip to next
				if (yahooRotoTeamDao.findByTeamKey(teamKey) != null) {
					YahooRotoTeam existingTeam = yahooRotoTeamDao.findByTeamKey(teamKey);
					if (existingTeam.getTeamGUID().equals(user.getYahooGUID())) {
						existingTeam.setUser(user);
					}
					continue;
				}

				// stat values
				Node teamStatNode = teamElement.getElementsByTagName("team_stats").item(0);
				Element teamStatElement = (Element) teamStatNode;
				Node statsNode = teamStatElement.getElementsByTagName("stats").item(0);
				Element statsElement = (Element) statsNode;
				NodeList statList = statsElement.getElementsByTagName("stat");
				for(int j = 0; j < statList.getLength(); j++) {
					Node statNode = statList.item(j);
					Element statElement = (Element) statNode;

					int statCategory = Integer.parseInt(statElement.getElementsByTagName("stat_id").item(0).getTextContent());

					switch (statCategory) {
//					// hab
//					case 60 : habStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
//					break;
					// r
					case 7 : rStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// hr
					case 12 : hrStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// rbi
					case 13 : rbiStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// sb
					case 16 : sbStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
					break;
//					// avg
//					case 3 : avgStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
//					break;
					// ops
					case 55 : opsStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// ip
					case 50 : ipStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// w
					case 28 : wStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// sv
					case 32 : svStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// k
					case 42 : kStats = Integer.parseInt(statElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// era
					case 26 : eraStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// whip
					case 27 : whipStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
					break;

					default : break;
					}
				}
		
				// stat points
				Node teamPointNode = teamElement.getElementsByTagName("team_points").item(0);
				Element teamPointElement = (Element) teamPointNode;
				Node pointsNode = teamPointElement.getElementsByTagName("stats").item(0);
				Element pointsElement = (Element) pointsNode;
				NodeList pointList = pointsElement.getElementsByTagName("stat");
				for(int k = 0; k < pointList.getLength(); k++) {
					Node pointNode = pointList.item(k);
					Element pointElement = (Element) pointNode;

					int pointCategory = Integer.parseInt(pointElement.getElementsByTagName("stat_id").item(0).getTextContent());

					switch (pointCategory) {
//					// hab
//					case 60 : habPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
//					break;
					// r
					case 7 : rPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// hr
					case 12 : hrPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// rbi
					case 13 : rbiPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// sb
					case 16 : sbPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
					break;
//					// avg
//					case 3 : avgPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
//					break;
					// ops
					case 55 : opsPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
					break;
//					// ip
//					case 50 : ipPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
//					break;
					// w
					case 28 : wPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// sv
					case 32 : svPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// k
					case 42 : kPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// era
					case 26 : eraPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
					break;
					// whip
					case 27 : whipPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
					break;

					default : break;
					}
				}

				// stat points
				NodeList teamStandingList = teamElement.getElementsByTagName("team_standings");
				for(int l = 0; l < teamStandingList.getLength(); l++) {
					Node teamStandingNode = teamStandingList.item(l);
					if (teamStandingNode.getNodeType() == Node.ELEMENT_NODE) {
						Element teamStandingElement = (Element) teamStandingNode;
						rank = Integer.parseInt(teamStandingElement.getElementsByTagName("rank").item(0).getTextContent());
						totalPoints = Double.parseDouble(teamStandingElement.getElementsByTagName("points_for").item(0).getTextContent());
					}
				}
			}

			YahooRotoTeam newTeam = new YahooRotoTeam(teamKey, teamName, teamURL, teamFAABBalance, teamMoves, teamTrades, teamGUID, teamManagerName, leagueKey, habStats, 
					rStats, hrStats, rbiStats, sbStats, avgStats, opsStats, ipStats, wStats, svStats, kStats, eraStats, whipStats, habPoints, rPoints, hrPoints, rbiPoints, 
					sbPoints, avgPoints, opsPoints, ipPoints, wPoints, svPoints, kPoints, eraPoints, whipPoints, rank, totalPoints);

			if (newTeam.getTeamGUID().equals(user.getYahooGUID())) {
				newTeam.setUser(user);
			}
			newTeam.setYahooRotoLeague(yahooRotoLeagueDao.findByLeagueKey(leagueKey));
			yahooRotoTeamDao.save(newTeam);
		}
	}
	
	public void getPlayers() {
		// player variables
		String playerKey = null;
		String firstName = null;
		String lastName = null;
		String fullName = null;
		String teamAbbr = null;

		
	}

}
