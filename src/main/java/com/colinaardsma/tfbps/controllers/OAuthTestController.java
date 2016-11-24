package com.colinaardsma.tfbps.controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
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
        
        String host = "query.yahooapis.com";
        String path = "/v1/public/yql";
		String baseURL = "https://query.yahooapis.com/v1/public/yql";
		String yql = "select * from yahoo.identity where yid='" + yahooUsername + "'";
		
//		String url = baseURL;
		
		
		// general data variables
		String xmlData = new String();

		// variables to pass to html
		String yahooGUID = new String();
		
		try {
			SignPostTest signPostTest = new SignPostTest();
			String url = "https://query.yahooapis.com/v1/public/yql";
			String query = "select * from yahoo.identity where yid='beuller43'";
			xmlData = signPostTest.returnHttpPostData(url, query);

			
			
			
//		    	String request = "http://api.search.yahoo.com/WebSearchService/V1/webSearch";
////
//// this will connection but not authenticate ////
//		        HttpClient client = new HttpClient();
//		        PostMethod method = new PostMethod(baseURL);
//		        
//		        method.addParameter("q","select * from yahoo.identity where yid='beuller43'");
//		        
//		        // Send POST request
//		        int statusCode = client.executeMethod(method);
//		        
//		        if (statusCode != HttpStatus.SC_OK) {
//		        	System.err.println("Method failed: " + method.getStatusLine());
//		        }
//		        InputStream rstream = null;
//		        
//		        // Get the response body
//		        rstream = method.getResponseBodyAsStream();
//		        
//		        // Process the response from Yahoo! Web Services
//		        BufferedReader br = new BufferedReader(new InputStreamReader(rstream));
//		        String line;
//		        while ((line = br.readLine()) != null) {
//		            System.out.println(line);
//		        }
//		        br.close();
////			
			
			
			
//			String urlParameters  = "q=select%20*%20from%20yahoo.identity%20where%20yid%3D'beuller43'";
//			byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
//			int    postDataLength = urlParameters.length();
//			String req        = baseURL + "?" + urlParameters;
//			URL    url            = new URL( req );
////			HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
////			conn.setDoOutput( true );
//////			conn.setInstanceFollowRedirects( false );
////			conn.setRequestMethod( "POST" );
////			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
//////			conn.setRequestProperty( "charset", "utf-8");
////			conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
//////			conn.setUseCaches( false );
//////			try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
//////			   wr.write( postData );
//////			}
//			InetAddress addr = InetAddress.getByName(host);
//			HttpsURLConnection conn= (HttpsURLConnection) url.openConnection();           
//			Socket socket = new Socket(addr, null);
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
//
//		    bw.write("POST " + path + " HTTP/1.0\r\n");
//
//		    bw.write("Content-Length: " + urlParameters.length() + "\r\n");
//
//		    bw.write("Content-Type: application/x-www-form-urlencoded\r\n");
//
//		    bw.write("\r\n"); 
//		// Send POST data string
//
//		    bw.write(urlParameters);
//
//		    bw.flush();
//			
//		 // Process the response from Yahoo! Web Services
//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//            bw.close();
//            br.close();
			
			
////			String request = "http://api.search.yahoo.com/WebSearchService/V1/webSearch";
////		    HttpClient client = new HttpClient();
////
////		    PostMethod method = new PostMethod(request);
////
////		    // Add POST parameters
////
////		    method.addParameter("appid","YahooDemo");
////
////		    method.addParameter("query","umbrella");
////
////		    method.addParameter("results","10");
////
////
////		    // Send POST request
////
////		    int statusCode = client.executeMethod(method);
////
////		    InputStream rstream = null;
////
////		    
////		    // Get the response body
////
////		    rstream = method.getResponseBodyAsStream();
////			PostMethod method = new PostMethod(request);
//			// access public yahoo xml data			 
//            URL u = new URL(url);
//            
//            URI uri = new URI(u.getProtocol(), u.getUserInfo(), u.getHost(), u.getPort(), u.getPath(), u.getQuery(), u.getRef());
////            byte[] data = URLEncoder.encode("select * from yahoo.identity where yid='beuller43'", "UTF-8").getBytes(StandardCharsets.UTF_8);
//
//            int data = URLEncoder.encode("q=select * from yahoo.identity where yid='beuller43'", "UTF-8").getBytes("UTF-8").length;
//            HttpsURLConnection uc = (HttpsURLConnection) uri.toURL().openConnection();
//
////            //add request header
//            uc.setRequestMethod("POST");
////            uc.setRequestProperty("User-Agent", "Mozilla/5.0");
//            uc.setRequestProperty("Content-Length", String.valueOf(data));
//
////            uc.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//            uc.setRequestProperty("Content-Type", "application/xml");
//////
//////            // Send post request
//////            uc.setDoOutput(true);
//////            OutputStream wr = uc.getOutputStream();
////////            wr.writeBytes(urlParameters);
//////            wr.flush();
//////            wr.close();
//////
//////            int responseCode = uc.getResponseCode();
////            uc.setRequestMethod("POST");
//////            uc.setFixedLengthStreamingMode(0);
////            uc.setChunkedStreamingMode(64);
//            uc.setDoOutput(true);
//            OutputStream os = uc.getOutputStream();
//            os.write(URLEncoder.encode("q=select * from yahoo.identity where yid='beuller43'", "UTF-8").getBytes());
            
            
//            // read xml data
//            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            StringBuffer sb = new StringBuffer();
//            String line;
//            while ((line = rd.readLine()) != null) {
//            	sb.append(line);
//            }
//            rd.close();
//            xmlData = sb.toString();
//
////			// access yahoo api via oauth and return data based on url above
////			SignPostTest signPostTest = new SignPostTest();
////			xmlData = signPostTest.returnHttpData(url);
//
//			
//			// parse xml data
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
//		    InputSource is = new InputSource(new StringReader(xmlData));
//		    Document document = builder.parse(is);
//			
//			// extract relevant data
//		    yahooGUID = document.getElementsByTagName("guid").item(0).getTextContent();
//
		} catch (Exception e) {
			e.printStackTrace();
		}
//
//		model.addAttribute("yahooGUID", yahooGUID);

		return "yahoouserlookup";
	}


}
