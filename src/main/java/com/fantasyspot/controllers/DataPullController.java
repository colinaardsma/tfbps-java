package com.fantasyspot.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

import com.fantasyspot.models.FPProjBatter;
import com.fantasyspot.models.FPProjPitcher;
import com.fantasyspot.models.SteamerProjBatter;
import com.fantasyspot.models.SteamerProjPitcher;
import com.fantasyspot.models.User;
import com.fantasyspot.models.dao.FPProjBatterDao;
import com.fantasyspot.models.dao.FPProjPitcherDao;
import com.fantasyspot.models.dao.SteamerProjBatterDao;
import com.fantasyspot.models.dao.SteamerProjPitcherDao;
import com.fantasyspot.models.util.TeamNameNormalization;

@Controller
public class DataPullController extends AbstractController {

	@Autowired
	FPProjBatterDao fpProjBatterDao;

	@Autowired
	FPProjPitcherDao fpProjPitcherDao;
	
	@Autowired
	SteamerProjBatterDao steamerProjBatterDao;
	
	@Autowired
	SteamerProjPitcherDao steamerProjPitcherDao;
	
	// FANTASY PROS
	// BATTER
	@RequestMapping(value = "/fpprojbdatapull")
    public String fpprojbdatapull(Model model, HttpServletRequest request){
		
		fpProjBatterDao.deleteAll();
		
		Document doc;
		try {
			doc = Jsoup.connect("https://www.fantasypros.com/mlb/projections/hitters.php").get();

			for (Element table : doc.select("table[id=data]")) {
				for (Element row : table.select("tr")) {
					Elements tds = row.select("td");
					if (tds.isEmpty()) { // Header <tr> with only <th>s
						continue;
					}
					Elements a = row.select("a");
					if (a.isEmpty()) {
						continue;
					}
					Elements small = row.select("small");
					if (small.isEmpty()) {
						continue;
					}
					String[] posPull = small.get(0).text().split(" - ");
					String pos;
					String team;
					if (posPull.length > 1) { // if team is listed
						posPull[1] = posPull[1].replaceAll("[,]", "/");
						pos = posPull[1].replaceAll("[)(]", "");
						team = a.get(1).text();
					} else { // if not team listed
						pos = posPull[0].replaceAll("[)(]", "");
						team = "FA";
					}
					team = TeamNameNormalization.Normalize(team); // normalize team name
					String name = a.get(0).text();
					int ab = Integer.parseInt(tds.get(1).text());
					int r = Integer.parseInt(tds.get(2).text());
					int hr = Integer.parseInt(tds.get(3).text());
					int rbi = Integer.parseInt(tds.get(4).text());
					int sb = Integer.parseInt(tds.get(5).text());
					double avg = Double.parseDouble(tds.get(6).text());
					double obp = Double.parseDouble(tds.get(7).text());
					int h = Integer.parseInt(tds.get(8).text());
					int dbl = Integer.parseInt(tds.get(9).text());
					int tpl = Integer.parseInt(tds.get(10).text());
					int bb = Integer.parseInt(tds.get(11).text());
					int k = Integer.parseInt(tds.get(12).text());
					double slg = Double.parseDouble(tds.get(13).text());
					double ops = Double.parseDouble(tds.get(14).text());

					FPProjBatter batter = new FPProjBatter(name, team, pos, ab, r, hr, rbi, sb, avg, obp, h, dbl, tpl, bb, k, slg, ops, "fpprojb");
					fpProjBatterDao.save(batter);
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// create lists to calculate aav
		List<FPProjBatter> opsBatterList = fpProjBatterDao.findAllByOrderByOpsTotalSGPDesc();
		List<FPProjBatter> avgBatterList = fpProjBatterDao.findAllByOrderByAvgTotalSGPDesc();
		
		// create second list to pass into aav calculation method
		List<FPProjBatter> batterList = new ArrayList<FPProjBatter>();
		batterList.addAll(opsBatterList);
		
		// calc ops aav and save
		for (FPProjBatter b : opsBatterList) {
			b.calcOpsAav(batterList);
			fpProjBatterDao.save(b);
		}
		
		// erase second list, add avg data, and pass into aav calculation method
		batterList = new ArrayList<FPProjBatter>();
		batterList.addAll(avgBatterList);
		
		// calc avg aav and save
		for (FPProjBatter b : avgBatterList) {
			b.calcAvgAav(batterList);
			fpProjBatterDao.save(b);
		}
				
		// calc FVAAz and save
		for (FPProjBatter b : avgBatterList) {
			b.calcFVAAz(batterList);
			fpProjBatterDao.save(b);
		}
		
		// create lists to calculate FVARz
		List<FPProjBatter> opsFVARzList = fpProjBatterDao.findAllByOrderByAvgFVAAzDesc();
		List<FPProjBatter> avgFVARzList = fpProjBatterDao.findAllByOrderByOpsFVAAzDesc();

		// erase second list, add avg data, and pass into aav calculation method
		batterList = new ArrayList<FPProjBatter>();
		batterList.addAll(opsFVARzList);
		
		// calc FVARz and save
		for (FPProjBatter batter : opsFVARzList) {
			batter.calcFVARz(batterList, "OPS");
			fpProjBatterDao.save(batter);
		}

		// erase second list, add avg data, and pass into aav calculation method
		batterList = new ArrayList<FPProjBatter>();
		batterList.addAll(avgFVARzList);
		
		// calc FVARz and save
		for (FPProjBatter batter : avgFVARzList) {
			batter.calcFVARz(batterList, "AVG");
			fpProjBatterDao.save(batter);
		}
		// copy these methods from fp to steamer
		// need to get POS data into steamer (cross reference?)
		
        return "redirect:fpprojb";
    }
	
	// PITCHER
	@RequestMapping(value = "/fpprojpdatapull")
    public String fpprojpdatapull(Model model, HttpServletRequest request){

		fpProjPitcherDao.deleteAll();

		Document doc;
		try {
			doc = Jsoup.connect("https://www.fantasypros.com/mlb/projections/pitchers.php").get();

			for (Element table : doc.select("table[id=data]")) {
				for (Element row : table.select("tr")) {
					Elements tds = row.select("td");
					if (tds.isEmpty()) { // Header <tr> with only <th>s
						continue;
					}
					Elements a = row.select("a");
					if (a.isEmpty()) {
						continue;
					}
					Elements small = row.select("small");
					if (small.isEmpty()) {
						continue;
					}
					String[] posPull = small.get(0).text().split(" - ");
					String pos;
					String team;
					if (posPull.length > 1) { // if team is listed
						posPull[1] = posPull[1].replaceAll("[,]", "/");
						pos = posPull[1].replaceAll("[)(]", "");
						team = a.get(1).text();
					} else { // if not team listed
						pos = posPull[0].replaceAll("[)(]", "");
						team = "FA";
					}
					team = TeamNameNormalization.Normalize(team); // normalize team name
					String name = a.get(0).text();
					int ip = Integer.parseInt(tds.get(1).text());
					int k = Integer.parseInt(tds.get(2).text());
					int w = Integer.parseInt(tds.get(3).text());
					int sv = Integer.parseInt(tds.get(4).text());
					double era = Double.parseDouble(tds.get(5).text());
					double whip = Double.parseDouble(tds.get(6).text());
					int er = Integer.parseInt(tds.get(7).text());
					int h = Integer.parseInt(tds.get(8).text());
					int bb = Integer.parseInt(tds.get(9).text());
					int hr = Integer.parseInt(tds.get(10).text());
					int g = Integer.parseInt(tds.get(11).text());
					int gs = Integer.parseInt(tds.get(12).text());
					int l = Integer.parseInt(tds.get(13).text());
					int cg = Integer.parseInt(tds.get(14).text());

					FPProjPitcher pitcher = new FPProjPitcher(name, team, pos, ip, k, w, sv, era, whip, er, h, bb, hr, g, gs, l, cg, "fpprojp");
					fpProjPitcherDao.save(pitcher);
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// create lists to calculate aav
		List<FPProjPitcher> pitcherList = fpProjPitcherDao.findAllByOrderBySgpDesc();
		
		// create second list to pass into aav calculation method
		List<FPProjPitcher> pList = new ArrayList<FPProjPitcher>();
		pList.addAll(pitcherList);
		
		// calc ops aav and save
		for (FPProjPitcher p : pitcherList) {
			p.calcAav(pList);
			fpProjPitcherDao.save(p);
		}
		
        return "redirect:fpprojp";
    }
	
	// STEAMER
	// BATTER
	@RequestMapping(value = "/steamerprojbdatapull", method = RequestMethod.GET)
	public String steamerProjBDataPullForm(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		String dataSet = "Steamer";
		String category = "batter";

    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
        model.addAttribute("dataSet", dataSet);
        model.addAttribute("category", category);

		return "csvdatapull";
	}
	
	@RequestMapping(value = "/steamerprojbdatapull", method = RequestMethod.POST)
	public String steamerProjBDataPull(Model model, HttpServletRequest request){
		
		String file = request.getParameter("file");
		String line = null;
		
		steamerProjBatterDao.deleteAll();

		try (BufferedReader br = new BufferedReader (new FileReader(file))) {
			while ((line = br.readLine()) != null) {
				String[] player = line.split(",");
				String name = player[0].replaceAll("\"", "");
				if (name.contains("Name")) { // if header row then skip
					continue;
				}
				int ab = Integer.parseInt(player[4].replaceAll("\"", ""));
				if (ab < 100) { // if ab is less than 100 then skip
					continue;
				}

				String team = player[1].replaceAll("\"", "");

				int g = Integer.parseInt(player[2].replaceAll("\"", ""));
				int pa = Integer.parseInt(player[3].replaceAll("\"", ""));
				int h = Integer.parseInt(player[5].replaceAll("\"", ""));
				int dbl = Integer.parseInt(player[6].replaceAll("\"", ""));
				int tpl = Integer.parseInt(player[7].replaceAll("\"", ""));
				int hr = Integer.parseInt(player[8].replaceAll("\"", ""));
				int r = Integer.parseInt(player[9].replaceAll("\"", ""));
				int rbi = Integer.parseInt(player[10].replaceAll("\"", ""));
				int bb = Integer.parseInt(player[11].replaceAll("\"", ""));
				int k = Integer.parseInt(player[12].replaceAll("\"", ""));
				int hbp = Integer.parseInt(player[13].replaceAll("\"", ""));
				int sb = Integer.parseInt(player[14].replaceAll("\"", ""));
				int cs = Integer.parseInt(player[15].replaceAll("\"", ""));
				double avg = Double.parseDouble(player[17].replaceAll("\"", ""));
				double obp = Double.parseDouble(player[18].replaceAll("\"", ""));
				double slg = Double.parseDouble(player[19].replaceAll("\"", ""));
				double ops = Double.parseDouble(player[20].replaceAll("\"", ""));
				double woba = Double.parseDouble(player[21].replaceAll("\"", ""));
				double wrcPlus = Double.parseDouble(player[23].replaceAll("\"", ""));
				double bsr = Double.parseDouble(player[24].replaceAll("\"", ""));
				double fld = Double.parseDouble(player[25].replaceAll("\"", ""));
				double offWar = Double.parseDouble(player[27].replaceAll("\"", ""));
				double defWar = Double.parseDouble(player[28].replaceAll("\"", ""));
				double war = Double.parseDouble(player[29].replaceAll("\"", ""));
				String playerId = player[30].replaceAll("\"", "");

				String category = "steamerprojbatter";

				SteamerProjBatter batter = new SteamerProjBatter(name, team, playerId, g, pa, ab, h, dbl, tpl, hr, r, rbi, bb, k, hbp, sb, cs, avg, obp, slg, ops, woba, wrcPlus, bsr, fld, offWar, defWar, war, category);
				steamerProjBatterDao.save(batter);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create lists to calculate aav
		List<SteamerProjBatter> opsBatterList = steamerProjBatterDao.findAllByOrderByOpsTotalSGPDesc();
		List<SteamerProjBatter> avgBatterList = steamerProjBatterDao.findAllByOrderByAvgTotalSGPDesc();
		
		// create second list to pass into aav calculation method
		List<SteamerProjBatter> batterList = new ArrayList<SteamerProjBatter>();
		batterList.addAll(opsBatterList);
		
		// calc ops aav and save
		for (SteamerProjBatter b : opsBatterList) {
			b.calcOpsAav(batterList);
			steamerProjBatterDao.save(b);
		}
		
		// erase second list, add avg data, and pass into aav calculation method
		batterList = new ArrayList<SteamerProjBatter>();
		batterList.addAll(avgBatterList);
		
		// calc avg aav and save
		for (SteamerProjBatter b : avgBatterList) {
			b.calcAvgAav(batterList);
			steamerProjBatterDao.save(b);
		}

		return "redirect:steamerprojb";
	}
	
	// PITCHER
	@RequestMapping(value = "/steamerprojpdatapull", method = RequestMethod.GET)
	public String steamerProjPDataPullForm(Model model, HttpServletRequest request){
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);
		String dataSet = "Steamer";
		String category = "pitcher";

    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
        model.addAttribute("dataSet", dataSet);
        model.addAttribute("category", category);

		return "csvdatapull";
	}
	
	@RequestMapping(value = "/steamerprojpdatapull", method = RequestMethod.POST)
	public String steamerProjPDataPull(Model model, HttpServletRequest request){
		
		String file = request.getParameter("file");
		String line = null;
		
		steamerProjPitcherDao.deleteAll();

		try (BufferedReader br = new BufferedReader (new FileReader(file))) {
			while ((line = br.readLine()) != null) {
				String[] player = line.split(",");
				String name = player[0].replaceAll("\"", "");
				if (name.contains("Name")) { // if header row then skip
					continue;
				}
				double ip = Double.parseDouble(player[8].replaceAll("\"", ""));
				if (ip < 40) { // if ab is less than 100 then skip
					continue;
				}

				String team = player[1].replaceAll("\"", "");

				int w = Integer.parseInt(player[2].replaceAll("\"", ""));
				int l = Integer.parseInt(player[3].replaceAll("\"", ""));
				double era = Double.parseDouble(player[4].replaceAll("\"", ""));
				int gs = Integer.parseInt(player[5].replaceAll("\"", ""));
				int g = Integer.parseInt(player[6].replaceAll("\"", ""));
				int sv = Integer.parseInt(player[7].replaceAll("\"", ""));
				int h = Integer.parseInt(player[9].replaceAll("\"", ""));
				int er = Integer.parseInt(player[10].replaceAll("\"", ""));
				int hr = Integer.parseInt(player[11].replaceAll("\"", ""));
				int k = Integer.parseInt(player[12].replaceAll("\"", ""));
				int bb = Integer.parseInt(player[13].replaceAll("\"", ""));
				double whip = Double.parseDouble(player[14].replaceAll("\"", ""));
				double kNine = Double.parseDouble(player[15].replaceAll("\"", ""));
				double bbNine = Double.parseDouble(player[16].replaceAll("\"", ""));
				double fip = Double.parseDouble(player[17].replaceAll("\"", ""));
				double war = Double.parseDouble(player[18].replaceAll("\"", ""));
				double raNineWAR = Double.parseDouble(player[19].replaceAll("\"", ""));
				String playerId = player[20].replaceAll("\"", "");

				String category = "steamerprojpitcher";

				SteamerProjPitcher pitcher = new SteamerProjPitcher(name, team, playerId, ip, k, w, sv, era, whip, er, h, bb, hr, g, gs, l, kNine, bbNine, fip, war, raNineWAR, category);
				steamerProjPitcherDao.save(pitcher);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create lists to calculate aav
		List<SteamerProjPitcher> pitcherList = steamerProjPitcherDao.findAllByOrderBySgpDesc();
		
		// create second list to pass into aav calculation method
		List<SteamerProjPitcher> pList = new ArrayList<SteamerProjPitcher>();
		pList.addAll(pitcherList);
		
		// calc ops aav and save
		for (SteamerProjPitcher b : pitcherList) {
			b.calcAav(pList);
			steamerProjPitcherDao.save(b);
		}
		
		// erase second list, add avg data, and pass into aav calculation method
		pList = new ArrayList<SteamerProjPitcher>();
		pList.addAll(pitcherList);
		
		// calc avg aav and save
		for (SteamerProjPitcher p : pitcherList) {
			p.calcAav(pList);
			steamerProjPitcherDao.save(p);
		}

		return "redirect:steamerprojp";
	}
	
}
