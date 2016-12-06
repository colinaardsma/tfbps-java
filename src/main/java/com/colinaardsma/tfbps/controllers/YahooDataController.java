package com.colinaardsma.tfbps.controllers;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.dao.UserDao;
import com.colinaardsma.tfbps.models.util.YahooOAuth;

import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
public class YahooDataController extends AbstractController {
	
	@Autowired
	UserDao userdao;
	
	private static String oauth_token_secret;
	
//	@RequestMapping(value = "/useryahooleagues", method = RequestMethod.GET)
//	public String useryahooleaguesform() {
//		return "yahooleaguelookup";
//	}
	
	// https://developer.yahoo.com/fantasysports/guide/ResourcesAndCollections.html
	
	@RequestMapping(value = "/useryahooleagues")
    public String useryahooleagues(Model model, HttpServletRequest request) {
		
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User yahooUser = userDao.findByUserName(currentUser);
//		String yahooGUID = yahooUser.getYahooGUID();
		String yahooGUID = "PP5K6WYOIYQL4ZWJAYTN2ZQ5CU";
//		String yahooGUID = "POC4MNFOIBH3MFVDI6TBJJFGLE";
//		String yahooGUID = "QRCBPZJIL2KBT7OLD6X2DXUD2Y";

		Document doc;
		try {
//			String profileURL = "https://fantasysports.yahooapis.com/fantasy/v2/user/" + yahooGUID + "/profile";
//			String profileURL = "https://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games;game_keys=mlb/teams";
//			String profileURL = "https://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games/teams";
			String profileURL = "https://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games/leagues";
//			String profileURL = "https://profiles.sports.yahoo.com/user/" + yahooGUID;
			String userLeagues = YahooOAuth.oauthGetRequest(profileURL, yahooUser);
			String userTeams = YahooOAuth.oauthGetRequest(profileURL, yahooUser);
//			String profileURL = "https://profiles.sports.yahoo.com/user/PP5K6WYOIYQL4ZWJAYTN2ZQ5CU";

			// use XML DOM here
			Document doc = 
			
			
//			// parse xml data from string returned
//			int index = userLeagues.indexOf("oauth_token=") + "oauth_token=".length();
//			oauth_token = oauth_response_token.substring(index, oauth_response_token.indexOf("&",index));
//			index = userLeagues.indexOf("oauth_token_secret=") + "oauth_token_secret=".length();
//			oauth_token_secret = oauth_response_token.substring(index, oauth_response_token.indexOf("&",index));
//			index = userLeagues.indexOf("oauth_expires_in=") + "oauth_expires_in=".length();
//			oauth_expires_in = oauth_response_token.substring(index, oauth_response_token.indexOf("&",index));
//			index = userLeagues.indexOf("xoauth_request_auth_url=") + "xoauth_request_auth_url=".length();
//			xoauth_request_auth_url = URLDecoder.decode(oauth_response_token.substring(index, oauth_response_token.indexOf("&",index)),"UTF-8");
//			index = userLeagues.indexOf("oauth_callback_confirmed=") + "oauth_callback_confirmed=".length();
//			oauth_callback_confirmed = oauth_response_token.substring(index, oauth_response_token.length());
//
//			// print values to log
//			System.out.println("oauth_token=" + oauth_token);
//			System.out.println("oauth_token_secret=" + oauth_token_secret);
//			System.out.println("oauth_expires_in=" + oauth_expires_in);
//			System.out.println("xoauth_request_auth_url=" + xoauth_request_auth_url);
//			System.out.println("oauth_callback_confirmed=" + oauth_callback_confirmed);

			
			
			
			
//			doc = Jsoup.parse(authorizedURL);
//			System.out.println("Connecting to: " + profileURL);
//			boolean baseball = false;
//			String leagueURL = null;
//			String leagueKey = null;
//			String leagueName = null;
//			String teamName = null;
//			String year = null;
//			ArrayList<String> leagues = new ArrayList<String>();
//			System.out.println(doc.outerHtml().toString());
//			
//			for (Element div : doc.select("div[class=archivegroup-bd]")) {
//				System.out.println("In div");
////				System.out.println(div.html().toString());
//
//				for (Element table : div.select("table")) {
//					System.out.println("In table");
////					System.out.println(table.outerHtml().toString());
//
//					for (Element tr : table.select("tr")) {
//						System.out.println("In tr");
////						System.out.println(tr.outerHtml().toString());
//						Elements tds = tr.select("td");
//						
////						Elements tds = tr.children();
//
//						if (tds.isEmpty()) { // Header <tr> with only <th>s
//							continue;
//						}
//
//						if (tds.get(0).ownText().equals("Baseball")) {
//							System.out.println("Found Baseball"); // this works, implement on down
//							baseball = true;								
//						}
//						if (baseball == true) {
//							year = tds.get(1).ownText();
//							System.out.println("League Year: " + year);
//							teamName = tds.get(3).ownText();
//							System.out.println("Team Name: " + teamName);
//							leagueName = tds.get(4).ownText();
//							System.out.println("League Name: " + leagueName);
////							System.out.println(tds.toArray().toString());
////							for (int i = 0; i < tds.size(); i++) {
////								leagues.add(tds.get(i).toString());
////							}
////							System.out.println(leagues);
////							System.out.println("Team Name: " + tds.get(4).html(html));
//							Elements a = tr.select("a");
////							if (a.isEmpty()) {
////								continue;
////							}
//							leagueURL = a.get(0).attr("href");
//							System.out.println("League URL: " + leagueURL);
//							leagueKey = leagueURL.substring(leagueURL.lastIndexOf("/",leagueURL.length())); 
//							System.out.println("League Key: " + leagueKey);
//							
//							
//							
//
////							for (Element a : tds.get(4).select("a")) {
////								System.out.println("a");
////								leagueURL = a.attr("href");
////								System.out.println("League URL: " + leagueURL);
////								leagueKey = leagueURL.substring(leagueURL.lastIndexOf("/",leagueURL.length())); 
////								System.out.println("League Key: " + leagueKey);
////							}
//							baseball = false;
//						}
//						leagues.add(year);
//						leagues.add(leagueName);
//						leagues.add(leagueKey);
//						leagues.add(leagueURL);
//					}
//				}
//			}
//
//
////						for (Element td : tr.select("td")) {
////							System.out.println(leagues.add(td.outerHtml().toString()));
////							
////							if (td.ownText().equals("game")) {
////								System.out.println("Found Baseball"); // this works, implement on down
////								baseball = true;
////							}
////							if (baseball == true) {
////								if (td.hasClass("league")) {
////									System.out.println("Found League");
////									leagueName = td.ownText();
////									System.out.println("League Name: " + leagueName);
////									
////									for (Element a : td.select("a")) {
////										leagueURL = a.attr("href");
////										System.out.println("League URL: " + leagueURL);
////										leagueKey = leagueURL.substring(leagueURL.lastIndexOf("/",leagueURL.length())); 
////										System.out.println("League Key: " + leagueKey);
////
////										leagues.add(leagueURL);
////										leagues.add(leagueKey);
////										leagues.add(leagueName);
////									}
////								}
////								if (td.hasClass("year") || td.hasClass("year sorted selected")) {
////									year = td.ownText();
////									leagues.add(year);
////								}
////
////								baseball = false;
////							}
////						}
////					
////					}
////				}
////			}
//					
//						
////						Elements tds = row.select("td");
////						for (int i = 0; i < tds.size(); i++) {
////							if(tds.get(i).hasClass("game")) {
////								if (tds.get(i).text().equals("Baseball")) {
////									System.out.println("Found Baseball"); // this works, implement on down
////									baseball = true;
////								}
////							}
////							if (baseball = true) {
////								if (tds.get(i).hasClass("league")) {
////									System.out.println("Found League");
////									leagueName = tds.get(i).ownText();
////									System.out.println("League Name: " + leagueName);
////									
////									Element a = tds.get(i).select("a");
////									
////
////									leagueURL = tds.get(i).absUrl("href");
////									System.out.println("League URL: " + leagueURL);
////
////									leagueKey = leagueURL.substring(leagueURL.lastIndexOf("/",leagueURL.length())); 
////
////									leagues.add(leagueURL);
////									leagues.add(leagueKey);
////									leagues.add(leagueName);
////									leagues.add(year);
////									baseball = false;
////									System.out.println("League URL: " + leagueURL);
////									System.out.println("League Key: " + leagueKey);
////									System.out.println("League Name: " + leagueName);
////									System.out.println("Year: " + year);
////									System.out.println("League List: " + leagues);
////								}
////							}
////						}
//					
//							
//////						System.out.println("In row");
////						for (Element td : tds.select("td")) {
//////							System.out.println("In td");
////							if (td.hasClass("game")) {
////								if (td.ownText().equals("Baseball")) {
////									baseball = true;
////									System.out.println("Found Baseball");
////								}
////							}
////							if (baseball == true) {
////								if (td.hasClass("league")) {
////									leagueURL = td.select("a[href]").attr("abs:href");
////									// parse leagueKey from leagueURL string
////									leagueKey = leagueURL.substring(leagueURL.lastIndexOf("/",leagueURL.length())); 
////									leagueName = td.ownText();
////								}
////								if (td.hasClass("year") || td.hasClass("year sorted selected")) {
////									year = td.ownText();
////								}
////								leagues.add(leagueURL);
////								leagues.add(leagueKey);
////								leagues.add(leagueName);
////								leagues.add(year);
////								baseball = false;
////								System.out.println("League URL: " + leagueURL);
////								System.out.println("League Key: " + leagueKey);
////								System.out.println("League Name: " + leagueName);
////								System.out.println("Year: " + year);
////
////								System.out.println("League List: " + leagues);
////							}
////						}
//						
//						
////						if (tds.isEmpty()) { // Header <tr> with only <th>s
////							continue;
////						}
////						Elements a = row.select("a");
////						if (a.isEmpty()) {
////							continue;
////						}
////						Elements small = row.select("small");
////						if (small.isEmpty()) {
////							continue;
////						}
////						String[] posPull = small.get(0).text().split(" - ");
////						String pos;
////						String team;
////						if (posPull.length > 1) { // if team is listed
////							posPull[1] = posPull[1].replaceAll("[,]", "/");
////							pos = posPull[1].replaceAll("[)(]", "");
////							team = a.get(1).text();
////						} else { // if not team listed
////							pos = posPull[0].replaceAll("[)(]", "");
////							team = "FA";
////						}
////						String name = a.get(0).text();
////						int ab = Integer.parseInt(tds.get(1).text());
////						int r = Integer.parseInt(tds.get(2).text());
////						int hr = Integer.parseInt(tds.get(3).text());
////						int rbi = Integer.parseInt(tds.get(4).text());
////						int sb = Integer.parseInt(tds.get(5).text());
////						double avg = Double.parseDouble(tds.get(6).text());
////						double obp = Double.parseDouble(tds.get(7).text());
////						int h = Integer.parseInt(tds.get(8).text());
////						int dbl = Integer.parseInt(tds.get(9).text());
////						int tpl = Integer.parseInt(tds.get(10).text());
////						int bb = Integer.parseInt(tds.get(11).text());
////						int k = Integer.parseInt(tds.get(12).text());
////						double slg = Double.parseDouble(tds.get(13).text());
////						double ops = Double.parseDouble(tds.get(14).text());
////
////						FPProjBatter batter = new FPProjBatter(name, team, pos, ab, r, hr, rbi, sb, avg, obp, h, dbl, tpl, bb, k, slg, ops, "fpprojb");
////						fpProjBatterDao.save(batter);
////					}
////				}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		return "useryahooleagues";
	}
	
}
