package com.colinaardsma.tfbps.controllers;

import java.io.IOException;
import java.util.ArrayList;

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

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.dao.UserDao;

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
//			String profileURL = "https://profiles.sports.yahoo.com/user/" + yahooGUID;
			String profileURL = "http://profiles.sports.yahoo.com/user/PP5K6WYOIYQL4ZWJAYTN2ZQ5CU";

			doc = Jsoup.connect("http://profiles.sports.yahoo.com/user/PP5K6WYOIYQL4ZWJAYTN2ZQ5CU").get();
			System.out.println("Connecting to: " + profileURL);
			boolean baseball = false;
			String leagueURL = null;
			String leagueKey = null;
			String leagueName = null;
			String year = null;
			ArrayList<String> leagues = new ArrayList<String>();
			
			for (Element div : doc.select("div[class=archivegroup-bd]")) {
//				System.out.println("In div");
				for (Element table : div.select("table")) {
//					System.out.println("In table");
					for (Element row : table.select("tr")) {
						Elements tds = row.select("td");
						for (int i = 0; i < tds.size(); i++) {
							if (tds.get(i).text().equals("Baseball")) {
								System.out.println("Found Baseball"); // this works, implement on down
							}
						}
							
////						System.out.println("In row");
//						for (Element td : tds.select("td[class=game]")) {
//							System.out.println("In td");
//							if (td.hasClass("game")) {
//								if (td.text() == "Baseball") {
//									baseball = true;
//									System.out.println("Found Baseball");
//								}
//								if (baseball == true) {
//									if (td.hasClass("league")) {
//										leagueURL = td.select("a[href]").attr("abs:href");
//										// parse leagueKey from leagueURL string
//										leagueKey = leagueURL.substring(leagueURL.lastIndexOf("/",leagueURL.length())); 
//										leagueName = td.ownText();
//									}
//									if (td.hasClass("year") || td.hasClass("year sorted selected")) {
//										year = td.ownText();
//									}
//									leagues.add(leagueURL);
//									leagues.add(leagueKey);
//									leagues.add(leagueName);
//									leagues.add(year);
//									baseball = false;
//									System.out.println("League URL: " + leagueURL);
//									System.out.println("League Key: " + leagueKey);
//									System.out.println("League Name: " + leagueName);
//									System.out.println("Year: " + year);
//
//									System.out.println("League List: " + leagues);
//								}
//							}
//						}
						
						
//						if (tds.isEmpty()) { // Header <tr> with only <th>s
//							continue;
//						}
//						Elements a = row.select("a");
//						if (a.isEmpty()) {
//							continue;
//						}
//						Elements small = row.select("small");
//						if (small.isEmpty()) {
//							continue;
//						}
//						String[] posPull = small.get(0).text().split(" - ");
//						String pos;
//						String team;
//						if (posPull.length > 1) { // if team is listed
//							posPull[1] = posPull[1].replaceAll("[,]", "/");
//							pos = posPull[1].replaceAll("[)(]", "");
//							team = a.get(1).text();
//						} else { // if not team listed
//							pos = posPull[0].replaceAll("[)(]", "");
//							team = "FA";
//						}
//						String name = a.get(0).text();
//						int ab = Integer.parseInt(tds.get(1).text());
//						int r = Integer.parseInt(tds.get(2).text());
//						int hr = Integer.parseInt(tds.get(3).text());
//						int rbi = Integer.parseInt(tds.get(4).text());
//						int sb = Integer.parseInt(tds.get(5).text());
//						double avg = Double.parseDouble(tds.get(6).text());
//						double obp = Double.parseDouble(tds.get(7).text());
//						int h = Integer.parseInt(tds.get(8).text());
//						int dbl = Integer.parseInt(tds.get(9).text());
//						int tpl = Integer.parseInt(tds.get(10).text());
//						int bb = Integer.parseInt(tds.get(11).text());
//						int k = Integer.parseInt(tds.get(12).text());
//						double slg = Double.parseDouble(tds.get(13).text());
//						double ops = Double.parseDouble(tds.get(14).text());
//
//						FPProjBatter batter = new FPProjBatter(name, team, pos, ab, r, hr, rbi, sb, avg, obp, h, dbl, tpl, bb, k, slg, ops, "fpprojb");
//						fpProjBatterDao.save(batter);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "useryahooleagues";
	}
	
}
