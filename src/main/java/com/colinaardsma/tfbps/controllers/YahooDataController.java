package com.colinaardsma.tfbps.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.colinaardsma.tfbps.models.dao.UserDao;
import com.colinaardsma.tfbps.models.dao.YahooRotoLeagueDao;
import com.colinaardsma.tfbps.models.util.YahooOAuth;

@Controller
public class YahooDataController extends AbstractController {

	@Autowired
	UserDao userdao;

	@Autowired
	YahooRotoLeagueDao yahooRotoLeagueDao;

	//	@RequestMapping(value = "/useryahooleagues", method = RequestMethod.GET)
	//	public String useryahooleaguesform() {
	//		return "yahooleaguelookup";
	//	}

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
		
		String leagueKey = request.getParameter("league");
		String leagueHistory = request.getParameter("leagueHistory");
		String xmlData = null;
		String leagueName = null;
		String leagueURL = null;
		String leagueScoringType = null;
		String prevYearKey = null;
		String nextYearKey = null;
		int prevYears = 0;
		int season = 0;
		int teamCount = 0;
		
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
				xmlData = YahooOAuth.oauthGetRequest(url, yahooUser);
				System.out.println(xmlData);

				// parse xml data
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				InputSource is = new InputSource(new StringReader(xmlData));
				Document document = builder.parse(is);

				// iterate through the nodes and extract the data.	    
				NodeList leagueList = document.getElementsByTagName("league");
				for (int i = 0; i < leagueList.getLength(); i++) {
					Node leagueNode = leagueList.item(i);
					if (leagueNode.getNodeType() == Node.ELEMENT_NODE) {
						Element leagueElement = (Element) leagueNode;
						leagueName = leagueElement.getElementsByTagName("name").item(0).getTextContent();
						leagueURL = leagueElement.getElementsByTagName("url").item(0).getTextContent();
						teamCount = Integer.parseInt(leagueElement.getElementsByTagName("num_teams").item(0).getTextContent());
						leagueScoringType = leagueElement.getElementsByTagName("scoring_type").item(0).getTextContent();
						prevYearKey = leagueElement.getElementsByTagName("renew").item(0).getTextContent().replace("_", ".l.");
						System.out.println("Previous Year Key: " + prevYearKey);
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
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
			leagueKey = prevYearKey;
			prevYears -= 1;
		} while (prevYears >= 0);


		
		
		
		
		

		model.addAttribute("linkedLeagues", linkedLeagues);
//		model.addAttribute("leagueName", leagueName);
//		model.addAttribute("leagueURL", leagueURL);
//		model.addAttribute("leagueScoringType", leagueScoringType);
		model.addAttribute("currentUser", currentUser);

		return "useryahooleagues";
	}


}
