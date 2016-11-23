package com.colinaardsma.tfbps.controllers;

import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.BasicConfigurator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
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

		String xmlData = new String();
		String leagueName = new String();
		
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
		    NodeList nodeList = document.getDocumentElement().getChildNodes();

//		    for (int i = 0; i < nodeList.getLength(); i++) {
//
//		    	//We have encountered an <employee> tag.
//		    	Node qNode = nodeList.item(i);
//		    	//			      if (node instanceof Element) {
//		    	//			        Employee emp = new Employee(); // object
//		    	//			        emp.id = node.getAttributes().
//		    	//			            getNamedItem("id").getNodeValue();
//		    	if (qNode.getNodeName() == "query") {
//		    		NodeList qChildNodes = qNode.getChildNodes();
		    		for (int j = 0; j < nodeList.getLength(); j++) {
		    			Node rNode = nodeList.item(j);
		    			if (rNode.getNodeName() == "results") {
		    				NodeList rChildNodes = rNode.getChildNodes();
		    				for (int k = 0; k < rChildNodes.getLength(); k++) {
		    					Node lNode = rChildNodes.item(k);
		    					if (lNode.getNodeName() == "league") {			      
		    						NodeList lChildNodes = lNode.getChildNodes();
		    						for (int l = 0; l < lChildNodes.getLength(); l++) {
		    							Node nNode = lChildNodes.item(j);
		    							if (nNode.getNodeName() == "name") {
		    								leagueName = nNode.getLastChild().getTextContent().trim();
		    							}
		    						}
		    					}
		    				}
		    			}
		    		}
//		    	}
//		    }
			          
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
		
		
	 
	 

		
		
		
		



		
		model.addAttribute("data", leagueName);
//    	model.addAttribute("currentUser", currentUser);

        return "oauthtest";
    }

}
