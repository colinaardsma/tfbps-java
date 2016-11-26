package com.colinaardsma.tfbps.controllers;

import java.io.StringReader;

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

import com.colinaardsma.tfbps.models.util.OAuthPost;
import com.colinaardsma.tfbps.models.util.SignPostTest;

@Controller
public class YahooDataController extends AbstractController {
	
	// https://developer.yahoo.com/fantasysports/guide/game-resource.html
	
	@RequestMapping(value = "/oauthtest", method = RequestMethod.GET)
	public String oauthtestform() {
		return "yahooleaguelookup";
	}
	
	@RequestMapping(value = "/oauthtest", method = RequestMethod.POST)
    public String oauthtest(Model model, HttpServletRequest request) {
		
//		// check for user in session
//		String currentUser = this.getUsernameFromSession(request);

		
		BasicConfigurator.configure();
		String league_key = request.getParameter("league_key");
//		String leagueYear = "357"; // 357 = 2016
		String leagueYear = request.getParameter("leagueYear");
//		String league_key = "357.l.3091";
		String prevYearKey = null;

		String url = "https://fantasysports.yahooapis.com/fantasy/v2/leagues;league_keys=" + leagueYear + ".l." + league_key + "/standings";
//		String url = "https://fantasysports.yahooapis.com/fantasy/v2/leagues;league_keys=" + league_key + "/standings";
		
		// general data variables
		String xmlData = null;

		// variables to pass to html
		String leagueName = null;
		String leagueURL = null;
		String leagueScoringType = null;
		
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
					prevYearKey = leagueElement.getElementsByTagName("renew").item(0).getTextContent();

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
		model.addAttribute("prevYearKey", prevYearKey);

//    	model.addAttribute("currentUser", currentUser);

        return "yahooleaguelookup";
    }

//	@RequestMapping(value = "/oauthtest")
//	public String oauthtest(Model model, HttpServletRequest request) {
//		// this version (POST) is preferred to the GET version (above), but need to figure out how to allow individual user login via yahoo
////		String league_key = request.getParameter("league_key");		
//		String league_key = "357.l.3091";
//		String leagueName = new String();
//		String leagueURL = new String();
//		String leagueScoringType = new String();
//		String error = new String();
//		
//		try {
//			if (league_key != null) {
//
//				// yahoo query variables
////				https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20fantasysports.leagues.standings%20where%20league_key%3D'238.l.627060'&diagnostics=true
//				String url = "https://query.yahooapis.com/v1/yql";
//				String query = "select * from fantasysports.leagues.standings where league_key='357.l.3091'";
//
//				// set xml data string
//				String xmlData = OAuthPost.postConnection(url, query);
//
//				// parse xml data
//				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//				DocumentBuilder builder = factory.newDocumentBuilder();
//				InputSource is = new InputSource(new StringReader(xmlData));
//				Document document = builder.parse(is);
//
//				// extract relevant data
//				// iterate through the nodes and extract the data.	    
//			    NodeList leagueList = document.getElementsByTagName("league");
//			    for (int i = 0; i < leagueList.getLength(); i++) {
//			    	Node leagueNode = leagueList.item(i);
//			    	if (leagueNode.getNodeType() == Node.ELEMENT_NODE) {
//					    Element leagueElement = (Element) leagueNode;
//						leagueName = leagueElement.getElementsByTagName("name").item(0).getTextContent();
//						leagueURL = leagueElement.getElementsByTagName("url").item(0).getTextContent();
//						leagueScoringType = leagueElement.getElementsByTagName("scoring_type").item(0).getTextContent();
//
//			    	}	
//			    }
//			}
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//			error = "Yahoo! league key not found.";
//		} catch (IOException e2) {
//			e2.printStackTrace();
//			error = "Yahoo! league key not found.";
//		} catch (Exception e3) {
//			e3.printStackTrace();
//		}
//		
//		model.addAttribute("leagueName", leagueName);
//		model.addAttribute("leagueURL", leagueURL);
//		model.addAttribute("leagueScoringType", leagueScoringType);
//		model.addAttribute("error", error);
//			
//		return "oauthtest";
//	}
	
	@RequestMapping(value = "/yahoouserlookup")
	public String yahoouserlookup(Model model, HttpServletRequest request) {

		String yahooUsername = request.getParameter("yahooUsername");
		String yahooGUID = new String();
		String error = new String();
		
		try {
			if (yahooUsername != null) {

				// yahoo query variables
				String url = "https://query.yahooapis.com/v1/yql";
				String query = "select * from yahoo.identity where yid='" + yahooUsername + "'";

				// set xml data string
				String xmlData = OAuthPost.postConnection(url, query);

				// parse xml data
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				InputSource is = new InputSource(new StringReader(xmlData));
				Document document = builder.parse(is);

				// extract relevant data
				yahooGUID = document.getElementsByTagName("guid").item(0).getTextContent();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			error = "Yahoo! username not found.";
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		model.addAttribute("yahooGUID", yahooGUID);
		model.addAttribute("error", error);
			
		return "yahoouserlookup";
	}

}
