package com.colinaardsma;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.colinaardsma.tfbps.models.util.SignPostTest;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TfbpsJavaApplicationTests {

	@Test
	public void FPProjBatterTest() {
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
					int triple = Integer.parseInt(tds.get(10).text());
					int bb = Integer.parseInt(tds.get(11).text());
					int k = Integer.parseInt(tds.get(12).text());
					double slg = Double.parseDouble(tds.get(13).text());
					double ops = Double.parseDouble(tds.get(14).text());

					assertEquals("no data?", name + "," + team + "," + pos + "," + ab + "," + r + "," + hr + "," + rbi + "," + sb + "," + avg + "," + obp + "," + h + "," + dbl + "," + triple + "," + bb + "," + k + "," + slg + "," + ops, "test");

				}

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	
	@Test
	public void FPProjPitcherTest() {
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

					assertEquals("no data?", name + "," + team + "," + pos + "," + ip + "," + k + "," + w + "," + sv + "," + era + "," + whip + "," + er + "," + h + "," + bb + "," + hr + "," + g + "," + gs + "," + l + "," + cg, "test");
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
	
//	@Test
//	public void OAuthTest() {
//
//		BasicConfigurator.configure();
//
//		try {
//
//			SignPostTest signPostTest = new SignPostTest();
//
//			signPostTest.returnHttpData();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

}
