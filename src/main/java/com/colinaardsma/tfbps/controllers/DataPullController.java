package com.colinaardsma.tfbps.controllers;

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

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.FPProjPitcher;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;
import com.colinaardsma.tfbps.models.dao.FPProjPitcherDao;
import com.colinaardsma.tfbps.models.util.TeamNameNormalization;

@Controller
public class DataPullController extends AbstractController {

	@Autowired
	FPProjBatterDao fpProjBatterDao;

	@Autowired
	FPProjPitcherDao fpProjPitcherDao;
	
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

        return "redirect:fpprojb";
    }
	
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
	
}
