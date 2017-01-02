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
import com.colinaardsma.tfbps.models.OttoneuOldSchoolTeam;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.dao.OttoneuOldSchoolLeagueDao;
import com.colinaardsma.tfbps.models.dao.OttoneuOldSchoolTeamDao;
import com.colinaardsma.tfbps.models.dao.UserDao;
import com.colinaardsma.tfbps.models.util.SGPMultCalc;
import com.colinaardsma.tfbps.models.util.TeamNameNormalization;

@Controller
public class OttoneuDataController extends AbstractController {

	@Autowired
	UserDao userdao;

	@Autowired
	OttoneuOldSchoolLeagueDao ottoneuOldSchoolLeagueDao;
	
	@Autowired
	OttoneuOldSchoolTeamDao ottoneuTeamDao;

	@RequestMapping(value = "/ottoneuleagueentry", method = RequestMethod.GET)
	public String ottoneuleagueentryform(Model model, HttpServletRequest request) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);

    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);

		return "ottoneuleagueentryform";
	}
	
	// https://ottoneu.fangraphs.com/642/draftresults?season=2016
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
		String leagueHistory = request.getParameter("leagueHistory");
		int prevYears = 0;
		
		// league variables
		String leagueName = null;
		String leagueURL = null;
		String leagueScoringStyle = null;
		String prevYearKey = null;
		String nextYearKey = null;
		int leagueEstablishYear = 0;
		
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
		List<OttoneuOldSchoolTeam> teamList = new ArrayList<OttoneuOldSchoolTeam>();
		List<OttoneuOldSchoolTeam> teamMasterList = new ArrayList<OttoneuOldSchoolTeam>();

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
		
		List<OttoneuOldSchoolLeague> linkedLeagues = new ArrayList<OttoneuOldSchoolLeague>();

		leagueHistory = (leagueHistory == null) ? "false" : "true";

		if (leagueHistory.equals("true")) {
			prevYears = Integer.parseInt(request.getParameter("prevyears"));
//			System.out.println("Previous Years int: " + prevYears);
		}

		do {
			teamList.removeAll(teamList);
			prevYearKey = leagueNumber + "." + (season - 1);
			nextYearKey = leagueNumber + "." + (season + 1);

			// see if league already exists
			if (ottoneuOldSchoolLeagueDao.findByLeagueNumberAndSeason(leagueNumber, season) != null) {
				OttoneuOldSchoolLeague existingLeague = ottoneuOldSchoolLeagueDao.findByLeagueNumberAndSeason(leagueNumber, season);
				leagueName = existingLeague.getLeagueName();
				leagueURL = existingLeague.getLeagueURL();
				leagueScoringStyle = "Old School (5x5)";

				notice = "League Already Exists";
				teamList = ottoneuTeamDao.findByOttoneuOldSchoolLeague(existingLeague);

				// link to previous year only if user requested
				if (prevYears >= 0) {
					existingLeague.setPreviousYearKey(prevYearKey);
				}
				if (ottoneuOldSchoolLeagueDao.findByLeagueKey(prevYearKey) != null) {
					existingLeague.setPreviousYearUID(ottoneuOldSchoolLeagueDao.findByLeagueKey(prevYearKey).getUid());
				} else if (ottoneuOldSchoolLeagueDao.findByLeagueKey(nextYearKey) != null) {
					OttoneuOldSchoolLeague nextYearLeague = ottoneuOldSchoolLeagueDao.findByLeagueKey(nextYearKey);
					nextYearLeague.setPreviousYearKey(leagueKey);
					ottoneuOldSchoolLeagueDao.save(nextYearLeague);
				}

				ottoneuOldSchoolLeagueDao.save(existingLeague);

				linkedLeagues.add(existingLeague);
				
				continue;

			} else {

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
								Elements tdsEstablishmentYear = trs.get(0).select("td");
								String establishmentDate = tdsEstablishmentYear.get(1).text();
								leagueEstablishYear = Integer.parseInt(establishmentDate.substring(establishmentDate.lastIndexOf(",") + 2));
								Elements tdsScoringStyle = trs.get(4).select("td");
								leagueScoringStyle = tdsScoringStyle.get(1).text();
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (season >= leagueEstablishYear) {
					OttoneuOldSchoolLeague newLeague = new OttoneuOldSchoolLeague(leagueNumber, leagueName, leagueURL, season);

					// add current user to list of league managers
					List<User> managers = new ArrayList<User>();
					managers.add(user);
					newLeague.setUsers(managers);

					// link to previous year only if user requested
					if (prevYears >= 0) {
						newLeague.setPreviousYearKey(prevYearKey);
						if (ottoneuOldSchoolLeagueDao.findByLeagueKey(prevYearKey) != null) {
							newLeague.setPreviousYearUID(ottoneuOldSchoolLeagueDao.findByLeagueKey(prevYearKey).getUid());
						}

						ottoneuOldSchoolLeagueDao.save(newLeague);

						if (ottoneuOldSchoolLeagueDao.findByLeagueKey(nextYearKey) != null) {
							OttoneuOldSchoolLeague nextYearLeague = ottoneuOldSchoolLeagueDao.findByLeagueKey(nextYearKey);
							nextYearLeague.setPreviousYearKey(leagueKey);
							nextYearLeague.setPreviousYearUID(ottoneuOldSchoolLeagueDao.findByLeagueKey(leagueKey).getUid());
							ottoneuOldSchoolLeagueDao.save(newLeague);
						}
					} else {
						ottoneuOldSchoolLeagueDao.save(newLeague);
					}

					linkedLeagues.add(newLeague);

					System.out.println("Season Standings Url: " + seasonStandingsUrl);
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
										teamName = TeamNameNormalization.Normalize(teamName); // normalize team name

										String href = tds.get(0).select("a").attr("href");
										teamURL = baseUrl + href;
										teamNumber = Integer.parseInt(href.substring(href.lastIndexOf("=") + 1));

										OttoneuOldSchoolTeam team = new OttoneuOldSchoolTeam(teamNumber, teamName, teamURL, leagueNumber, season, leagueKey);

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

										OttoneuOldSchoolTeam team = ottoneuTeamDao.findByTeamNumberAndSeason(teamNumber, season);

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
						Collections.sort(teamList, new Comparator<OttoneuOldSchoolTeam>() { // sort list by user's custom sgp calculation (desc)
							@Override
							public int compare(OttoneuOldSchoolTeam t1, OttoneuOldSchoolTeam t2) {
								if (t1.getTotalPoints() < t2.getTotalPoints()) return 1;
								if (t1.getTotalPoints() > t2.getTotalPoints()) return -1;
								return 0;
							}
						});

						int rank = 12;

						for (OttoneuOldSchoolTeam team : teamList) {
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
							OttoneuOldSchoolTeam savedTeam = ottoneuTeamDao.findByTeamNumberAndSeason(team.getTeamNumber(), team.getSeason());
							savedTeam.setTeamManagerName(teamManagerName);
							savedTeam.setTeamBudget(teamBudget);
							savedTeam.setRank(rank);
							ottoneuTeamDao.save(savedTeam);

							rank--;
						}

						// calculate league SGP and save to db
						OttoneuOldSchoolLeague league = ottoneuOldSchoolLeagueDao.findByLeagueNumberAndSeason(leagueNumber, season); // pull league to add and save data
						List<OttoneuOldSchoolTeam> teams = ottoneuTeamDao.findByLeagueNumberAndSeason(leagueNumber, season); // pull list of teams in league

						// add runs
						List<Integer> rs = new ArrayList<Integer>();
						for (OttoneuOldSchoolTeam team : teams) {
							rs.add(team.getRStats());
						}
						Collections.sort(rs); // sort list from smallest to largest
						league.setRSGPMult(SGPMultCalc.calcRSGPMult(rs)); // calculate average and store in league

						// add hrs
						List<Integer> hrs = new ArrayList<Integer>();
						for (OttoneuOldSchoolTeam team : teams) {
							hrs.add(team.getHrStats());
						}
						Collections.sort(hrs); // sort list from smallest to largest
						league.setHrSGPMult(SGPMultCalc.calcHrSGPMult(hrs)); // calculate average and store in league

						// add rbi
						List<Integer> rbis = new ArrayList<Integer>();
						for (OttoneuOldSchoolTeam team : teams) {
							rbis.add(team.getRbiStats());
						}
						Collections.sort(rbis); // sort list from smallest to largest
						league.setRbiSGPMult(SGPMultCalc.calcRbiSGPMult(rbis)); // calculate average and store in league

						// add sb
						List<Integer> sbs = new ArrayList<Integer>();
						for (OttoneuOldSchoolTeam team : teams) {
							sbs.add(team.getSbStats());
						}
						Collections.sort(sbs); // sort list from smallest to largest
						league.setSbSGPMult(SGPMultCalc.calcSbSGPMult(sbs)); // calculate average and store in league

						// add avg
						List<Double> avgs = new ArrayList<Double>();
						for (OttoneuOldSchoolTeam team : teams) {
							avgs.add(team.getAvgStats());
						}
						Collections.sort(avgs); // sort list from smallest to largest
						league.setAvgSGPMult(SGPMultCalc.calcAvgSGPMult(avgs)); // calculate average and store in league

						// add wins
						List<Integer> ws = new ArrayList<Integer>();
						for (OttoneuOldSchoolTeam team : teams) {
							ws.add(team.getWStats());
						}
						Collections.sort(ws); // sort list from smallest to largest
						league.setWSGPMult(SGPMultCalc.calcWSGPMult(ws)); // calculate average and store in league

						// add saves
						List<Integer> svs = new ArrayList<Integer>();
						for (OttoneuOldSchoolTeam team : teams) {
							svs.add(team.getSvStats());
						}
						Collections.sort(svs); // sort list from smallest to largest
						league.setSvSGPMult(SGPMultCalc.calcSvSGPMult(svs)); // calculate average and store in league

						// add k
						List<Integer> ks = new ArrayList<Integer>();
						for (OttoneuOldSchoolTeam team : teams) {
							ks.add(team.getKStats());
						}
						Collections.sort(ks); // sort list from smallest to largest
						league.setKSGPMult(SGPMultCalc.calcKSGPMult(ks)); // calculate average and store in league

						// add era
						List<Double> eras = new ArrayList<Double>();
						for (OttoneuOldSchoolTeam team : teams) {
							eras.add(team.getEraStats());
						}
						Collections.sort(eras); // sort list from smallest to largest
						league.setEraSGPMult(SGPMultCalc.calcEraSGPMult(eras)); // calculate average and store in league

						// add whip
						List<Double> whips = new ArrayList<Double>();
						for (OttoneuOldSchoolTeam team : teams) {
							whips.add(team.getWhipStats());
						}
						Collections.sort(whips); // sort list from smallest to largest
						league.setWhipSGPMult(SGPMultCalc.calcWhipSGPMult(whips)); // calculate average and store in league

						ottoneuOldSchoolLeagueDao.save(league); // save

//						// add teamMoves and teamTrades
//						for (OttoneuTeam team : teamList) {
//							Document teamPage = Jsoup.connect(teamURL).get();
//							
//							for (Element div : teamPage.select("div[id=left]")) {
//							Elements h3s = div.select("h3");
//								String owner = h3s.get(1).ownText();
//								teamManagerName = owner.substring(owner.lastIndexOf(":") + 1, owner.lastIndexOf("&"));
//						
//								for (Element h2 : div.select("h2[id=availableCap-mobile]")) {
//									for (Element span : h2.select("span[class=green]")) {
//										teamBudget = Integer.parseInt(span.ownText());
//									}
//								}
//							}
//							OttoneuTeam savedTeam = ottoneuTeamDao.findByTeamNumber(team.getTeamNumber());
//							savedTeam.setTeamManagerName(teamManagerName);
//							savedTeam.setTeamBudget(teamBudget);
//							ottoneuTeamDao.save(savedTeam);
//						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				leagueKey = leagueNumber + "." + (season - 1);
				season--;
				prevYears--;
			}
			
			if (teamMasterList.isEmpty()) {
				teamMasterList.addAll(teamList);
			}
						
		} while (prevYears >= 0);
		
		// calculate historical sgp for each year (change value in years variable to change the number of years)
		for (int i = 0; i < linkedLeagues.size(); i++) {
			OttoneuOldSchoolLeague league = ottoneuOldSchoolLeagueDao.findByLeagueKey(linkedLeagues.get(i).getLeagueKey());
			int years = 3;
			List<OttoneuOldSchoolLeague> leagueHist = new ArrayList<OttoneuOldSchoolLeague>();
			// add league plus 2 years prior to new leagueHist list (total of 3 years)
			for (int j = i; ((j < i + years) && (j < linkedLeagues.size())); j++) {
				leagueHist.add(linkedLeagues.get(j));
			}
			league.calcHistSGPs(leagueHist); // calculate historical SGPs
			ottoneuOldSchoolLeagueDao.save(league); // save
		}

		model.addAttribute("seasonStandingsUrl", seasonStandingsUrl);
		model.addAttribute("teamList", teamMasterList);
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
		OttoneuOldSchoolTeam team = ottoneuTeamDao.findByUid(teamUid);
		
		// not getting any values in leaguenumber or season
		int leagueNumber = team.getLeagueNumber();
		int season = team.getSeason();
		System.out.println("leaguenumber: " + leagueNumber);
		System.out.println("season: " + season);
		team.setUser(user);
		ottoneuTeamDao.save(team);
		
		OttoneuOldSchoolLeague existingLeague = ottoneuOldSchoolLeagueDao.findByLeagueNumberAndSeason(leagueNumber, season);

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
