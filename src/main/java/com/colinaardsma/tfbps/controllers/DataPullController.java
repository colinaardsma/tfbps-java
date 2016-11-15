package com.colinaardsma.tfbps.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.colinaardsma.tfbps.models.FPProjBatter;
import com.colinaardsma.tfbps.models.dao.FPProjBatterDao;

@Controller
public class DataPullController extends AbstractController {

	@Autowired
	FPProjBatterDao fpProjBatterDao;
	
	@RequestMapping(value = "/fpprojbdatapull")
    public String fpprojbdatapull(Model model, HttpServletRequest request){

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
					posPull[1] = posPull[1].replaceAll("[,]", "/");
					String pos = posPull[1].replaceAll("[)(]", "");
					String name = a.get(0).text();
					String team = a.get(1).text();
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

		//populate html table
		List<FPProjBatter> players = fpProjBatterDao.findAll();
		
		// need to figure out how to properly order
//        Integer userId = (Integer) request.getSession().getAttribute(AbstractController.userKey);
//        request.getSession().setAttribute(userKey, newUser.getUid());
//        
//        Session session = 
//		
//		Criteria crit = session.createCriteria(Cat.class);
//		crit.setMaxResults(50);
//		List cats = crit.list();
//		List cats = sess.createCriteria(players);
		
		model.addAttribute("players", players);
		
        return "fpprojb";
    }

}
