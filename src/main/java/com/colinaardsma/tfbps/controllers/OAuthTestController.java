package com.colinaardsma.tfbps.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.BasicConfigurator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.colinaardsma.tfbps.models.util.SignPostTest;

@Controller
public class OAuthTestController extends AbstractController {
	
	@RequestMapping(value = "/oauthtest")
    public String oauthtest(Model model, HttpServletRequest request) {
		
//		// check for user in session
//		String currentUser = this.getUsernameFromSession(request);

		
		BasicConfigurator.configure();
		String url = "https://fantasysports.yahooapis.com/fantasy/v2/leagues;league_keys=357.l.3091/standings";

		// general data variables
		String xmlData = new String();

		// variables to pass to html
		String leagueName = new String();
		String leagueURL = new String();
		String leagueScoringType = new String();
		
		try {
			// access yahoo api via oauth and return data based on url above
			SignPostTest signPostTest = new SignPostTest();
			xmlData = signPostTest.returnHttpData(url);
			
			// parse xml data
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
//			Document document = builder.parse(xmlData);
		    InputSource is = new InputSource(new StringReader(xmlData));
		    Document document = builder.parse(is);
			
			//Iterating through the nodes and extracting the data.
		    
		    NodeList leagueList = document.getElementsByTagName("league");
		    for (int i = 0; i < leagueList.getLength(); i++) {
		    	Node leagueNode = leagueList.item(i);
		    	if (leagueNode.getNodeType() == Node.ELEMENT_NODE) {
				    Element leagueElement = (Element) leagueNode;
					leagueName = leagueElement.getElementsByTagName("name").item(0).getTextContent();
					leagueURL = leagueElement.getElementsByTagName("url").item(0).getTextContent();
					leagueScoringType = leagueElement.getElementsByTagName("scoring_type").item(0).getTextContent();

		    	}
			    	
		    }
		    
		    
//		    NodeList nodeList = document.getDocumentElement().getChildNodes();
//
////		    for (int i = 0; i < nodeList.getLength(); i++) {
////
////		    	//We have encountered an <employee> tag.
////		    	Node qNode = nodeList.item(i);
////		    	//			      if (node instanceof Element) {
////		    	//			        Employee emp = new Employee(); // object
////		    	//			        emp.id = node.getAttributes().
////		    	//			            getNamedItem("id").getNodeValue();
////		    	if (qNode.getNodeName() == "query") {
////		    		NodeList qChildNodes = qNode.getChildNodes();
//		    		for (int j = 0; j < nodeList.getLength(); j++) {
//		    			Node rNode = nodeList.item(j);
//		    			if (rNode.getNodeName() == "results") {
//		    				NodeList rChildNodes = rNode.getChildNodes();
//		    				for (int k = 0; k < rChildNodes.getLength(); k++) {
//		    					Node lNode = rChildNodes.item(k);
//		    					if (lNode.getNodeName() == "league") {			      
//		    						NodeList lChildNodes = lNode.getChildNodes();
//		    						for (int l = 0; l < lChildNodes.getLength(); l++) {
//		    							Node nNode = lChildNodes.item(j);
//		    							if (nNode.getNodeName() == "name") {
//		    								leagueName = nNode.getLastChild().getTextContent().trim();
//		    							}
//		    						}
//		    					}
//		    				}
//		    			}
//		    		}
////		    	}
////		    }
			          
//			          //Identifying the child tag of employee encountered. 
//			          if (lcNode instanceof Element) {
//			            String content = lcNode.getLastChild().
//			                getTextContent().trim();
//			            switch (lcNode.getNodeName()) {
//			              case "firstName":
//			                emp.firstName = content;
//			                break;
//			              case "lastName":
//			                emp.lastName = content;
//			                break;
//			              case "location":
//			                emp.location = content;
//			                break;
//			            }		        
//			        empList.add(emp);
		    			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	 
	 

		
		
		
		



		
		model.addAttribute("leagueName", leagueName);
		model.addAttribute("leagueURL", leagueURL);
		model.addAttribute("leagueScoringType", leagueScoringType);

//    	model.addAttribute("currentUser", currentUser);

        return "oauthtest";
    }
	
	@RequestMapping(value = "/yahoouserlookup", method = RequestMethod.GET)
    public String yahoouserlookupform(Model model, HttpServletRequest request) {
		return "yahoouserlookup";
	}
		
	@RequestMapping(value = "/yahoouserlookup", method = RequestMethod.POST)
    public String yahoouserlookup(Model model, HttpServletRequest request) throws MalformedURLException {
		BasicConfigurator.configure();

        String yahooUsername = request.getParameter("yahooUsername");
        
		String url = "https://profiles.yahoo.com/v2/identities.handle(beuller43~yid)";
		

		// general data variables
		String xmlData = new String();

		// variables to pass to html
		String yahooGUID = new String();
		
		try {
//			// access public yahoo xml data
//            URL u = new URL(url);
//            URLConnection uc = u.openConnection();
//            
//            // read xml data
//            BufferedReader rd = new BufferedReader(new InputStreamReader(uc.getInputStream()));
//            StringBuffer sb = new StringBuffer();
//            String line;
//            while ((line = rd.readLine()) != null) {
//            	sb.append(line);
//            }
//            rd.close();
//            xmlData = sb.toString();

			// access yahoo api via oauth and return data based on url above
			SignPostTest signPostTest = new SignPostTest();
			xmlData = signPostTest.returnHttpData(url);

			
			// parse xml data
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource(new StringReader(xmlData));
		    Document document = builder.parse(is);
			
			// extract relevant data
		    yahooGUID = document.getElementsByTagName("guid").item(0).getTextContent();

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("yahooGUID", yahooGUID);

		return "yahoouserlookup";
	}


}
