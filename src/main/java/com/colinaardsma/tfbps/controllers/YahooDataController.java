package com.colinaardsma.tfbps.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
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

import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.YahooRotoLeague;
import com.colinaardsma.tfbps.models.YahooRotoTeam;
import com.colinaardsma.tfbps.models.dao.UserDao;
import com.colinaardsma.tfbps.models.dao.YahooRotoLeagueDao;
import com.colinaardsma.tfbps.models.dao.YahooRotoTeamDao;
import com.colinaardsma.tfbps.models.util.SGPMultCalc;
import com.colinaardsma.tfbps.models.util.YahooOAuth;

@Controller
public class YahooDataController extends AbstractController {

	@Autowired
	UserDao userdao;

	@Autowired
	YahooRotoLeagueDao yahooRotoLeagueDao;
	
	@Autowired
	YahooRotoTeamDao yahooRotoTeamDao;

	// https://developer.yahoo.com/fantasysports/guide/ResourcesAndCollections.html

	@RequestMapping(value = "/useryahooleagues", method = RequestMethod.GET)
	public String useryahooleaguesform(Model model, HttpServletRequest request) {

		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User yahooUser = userDao.findByUserName(currentUser);

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
			checkOAuthExpiration(yahooUser); // make sure authorization is still valid, if not renew
			userLeagues = YahooOAuth.oauthGetRequest(leaguesURL, yahooUser);
//			userTeams = YahooOAuth.oauthGetRequest(teamsURL, yahooUser); // use if/when team names need to be added
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
		
		return "useryahooleagues";
	}
	
	@RequestMapping(value = "/useryahooleagues", method = RequestMethod.POST)
	public String useryahooleagues(Model model, HttpServletRequest request) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User yahooUser = userDao.findByUserName(currentUser);

		// league variables
		String leagueKey = request.getParameter("league");
		String leagueHistory = request.getParameter("leagueHistory");
		String xmlData = null;
		String leagueName = null;
		String leagueURL = null;
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

		List<YahooRotoLeague> linkedLeagues = new ArrayList<YahooRotoLeague>();

		leagueHistory = (leagueHistory == null) ? "false" : "true";

		if (leagueHistory.equals("true")) {
			String py = request.getParameter("prevyears");
			//			System.out.println("Previous Years String: " + py);
			prevYears = Integer.parseInt(py);
			//			System.out.println("Previous Years int: " + prevYears);
		}

		do {
			String url = "https://fantasysports.yahooapis.com/fantasy/v2/leagues;league_keys=" + leagueKey + "/standings";

			try {
				checkOAuthExpiration(yahooUser); // make sure authorization is still valid, if not renew
				xmlData = YahooOAuth.oauthGetRequest(url, yahooUser);
//				System.out.println(xmlData);

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
//						leagueScoringType = leagueElement.getElementsByTagName("scoring_type").item(0).getTextContent();
						prevYearKey = leagueElement.getElementsByTagName("renew").item(0).getTextContent().replace("_", ".l.");
//						System.out.println("Previous Year Key: " + prevYearKey);
						nextYearKey = leagueElement.getElementsByTagName("renewed").item(0).getTextContent().replace("_", ".l.");
						season = Integer.parseInt(leagueElement.getElementsByTagName("season").item(0).getTextContent());

						// if league does not already exist create it
						if (yahooRotoLeagueDao.findByLeagueKey(leagueKey) == null) {
							YahooRotoLeague newLeague = new YahooRotoLeague(leagueKey, leagueName, leagueURL, teamCount, season);

							// add current user to list of league managers
							List<User> managers = new ArrayList<User>();
							managers.add(yahooUser);
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
							} else {
								yahooRotoLeagueDao.save(newLeague);
							}


							linkedLeagues.add(newLeague);

							// if league exists add previous year key as required
						} else {
							YahooRotoLeague existingLeague = yahooRotoLeagueDao.findByLeagueKey(leagueKey);

							// add current user to list of league managers
							List<User> managers = existingLeague.getUsers();
							if (!managers.contains(yahooUser)) {
								managers.add(yahooUser);
								existingLeague.setUsers(managers);
							}

							// link to previous year only if user requested
							if (prevYears >= 0) {
								existingLeague.setPreviousYearKey(prevYearKey);
							}
							if (yahooRotoLeagueDao.findByLeagueKey(prevYearKey) != null) {
								existingLeague.setPreviousYearUID(yahooRotoLeagueDao.findByLeagueKey(prevYearKey).getUid());
							} else if (yahooRotoLeagueDao.findByLeagueKey(nextYearKey) != null) {
								YahooRotoLeague nextYearLeague = yahooRotoLeagueDao.findByLeagueKey(nextYearKey);
								nextYearLeague.setPreviousYearKey(leagueKey);
								yahooRotoLeagueDao.save(nextYearLeague);
							}

							yahooRotoLeagueDao.save(existingLeague);

							linkedLeagues.add(existingLeague);

						}
					}
				}

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
//						System.out.println("GOT TEAM DATA");

						// check to see if this user exists, if so skip to next
						if (yahooRotoTeamDao.findByTeamKey(teamKey) != null) {
							YahooRotoTeam existingTeam = yahooRotoTeamDao.findByTeamKey(teamKey);
							if (existingTeam.getTeamGUID().equals(yahooUser.getYahooGUID())) {
								existingTeam.setUser(yahooUser);
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
//								// hab
//								case 60 : habStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
//								break;
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
//								// avg
//								case 3 : avgStats = Double.parseDouble(statElement.getElementsByTagName("value").item(0).getTextContent());
//								break;
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
//								// hab
//								case 60 : habPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
//								break;
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
//								// avg
//								case 3 : avgPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
//								break;
								// ops
								case 55 : opsPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
								break;
//								// ip
//								case 50 : ipPoints = Double.parseDouble(pointElement.getElementsByTagName("value").item(0).getTextContent());
//								break;
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
					
					if (newTeam.getTeamGUID().equals(yahooUser.getYahooGUID())) {
						newTeam.setUser(yahooUser);
					}
					newTeam.setYahooRotoLeague(yahooRotoLeagueDao.findByLeagueKey(leagueKey));
					yahooRotoTeamDao.save(newTeam);
				}
				
				// calculate league SGP and save to db
				YahooRotoLeague league = yahooRotoLeagueDao.findByLeagueKey(leagueKey); // pull league to add and save data
				List<YahooRotoTeam> teams = yahooRotoTeamDao.findByLeagueKey(leagueKey); // pull list of teams in leagye
				// add data
				league.setRSGPMult(SGPMultCalc.calcRSGPMult(teams));
				league.setHrSGPMult(SGPMultCalc.calcHrSGPMult(teams));
				league.setRbiSGPMult(SGPMultCalc.calcRbiSGPMult(teams));
				league.setSbSGPMult(SGPMultCalc.calcSbSGPMult(teams));
				league.setOpsSGPMult(SGPMultCalc.calcOpsSGPMult(teams));
				league.setWSGPMult(SGPMultCalc.calcWSGPMult(teams));
				league.setSvSGPMult(SGPMultCalc.calcSvSGPMult(teams));
				league.setKSGPMult(SGPMultCalc.calcKSGPMult(teams));
				league.setEraSGPMult(SGPMultCalc.calcEraSGPMult(teams));
				league.setWhipSGPMult(SGPMultCalc.calcWhipSGPMult(teams));
	
				yahooRotoLeagueDao.save(league); // save
				
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
			
			leagueKey = prevYearKey;
			prevYears -= 1;
			
		} while (prevYears >= 0);
		
		// calculate historical sgp for each year (change value in years variable to change the number of years)
		for (int i = 0; i < linkedLeagues.size(); i++) {
			YahooRotoLeague league = yahooRotoLeagueDao.findByLeagueKey(linkedLeagues.get(i).getLeagueKey());
			int years = 3;
			List<YahooRotoLeague> leagueHist = new ArrayList<YahooRotoLeague>();
			// add league plus 2 years prior to new leagueHist list (total of 3 years)
			for (int j = i; ((j < i + years) && (j < linkedLeagues.size())); j++) {
				leagueHist.add(linkedLeagues.get(j));
			}
			league.calcHistSGPs(leagueHist); // calculate historical SGPs
			yahooRotoLeagueDao.save(league); // save
		}

		model.addAttribute("linkedLeagues", linkedLeagues);
//		model.addAttribute("leagueName", leagueName);
//		model.addAttribute("leagueURL", leagueURL);
//		model.addAttribute("leagueScoringType", leagueScoringType);
		model.addAttribute("currentUser", currentUser);

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


}
