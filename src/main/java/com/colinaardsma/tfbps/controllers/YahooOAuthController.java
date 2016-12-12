package com.colinaardsma.tfbps.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.colinaardsma.tfbps.models.User;
import com.colinaardsma.tfbps.models.dao.UserDao;
import com.colinaardsma.tfbps.models.util.YahooOAuth;

@Controller
public class YahooOAuthController extends AbstractController {
	
	@Autowired
	UserDao userdao;
	
	private static String oauth_token_secret;
	
	// https://developer.yahoo.com/fantasysports/guide/game-resource.html
	
	@RequestMapping(value = "/yahooleaguelookup", method = RequestMethod.GET)
	public String yahooleaguelookupform() {
		return "yahooleaguelookup";
	}
	
	@RequestMapping(value = "/yahooleaguelookup", method = RequestMethod.POST)
    public String yahooleaguelookup(Model model, HttpServletRequest request) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);
		User user = this.getUserFromSession(request);

		BasicConfigurator.configure();
		String league_key = request.getParameter("league_key");
		String leagueYear = request.getParameter("leagueYear");
		String prevYearKey = null;

		String url = "https://fantasysports.yahooapis.com/fantasy/v2/leagues;league_keys=" + leagueYear + ".l." + league_key + "/standings";
		
		// general data variables
		String xmlData = null;

		// variables to pass to html
		String leagueName = null;
		String leagueURL = null;
		String leagueScoringType = null;
		String error = null;

		try {
			xmlData = YahooOAuth.oauthGetRequest(url, user);
		} catch (IOException e1) {
			e1.printStackTrace();
			error = "Not authorized to view league or league does not exist.";
			model.addAttribute("error", error);
			model.addAttribute("league_key", league_key);
			model.addAttribute("leagueYear", leagueYear);
			return "yahooleaguelookup";
		}

		// parse xml data
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			//			Document document = builder.parse(xmlData);
			InputSource is = new InputSource(new StringReader(xmlData));
			Document document = builder.parse(is);

			// iterate through the nodes and extract the data.	    
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
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		model.addAttribute("leagueName", leagueName);
		model.addAttribute("leagueURL", leagueURL);
		model.addAttribute("leagueScoringType", leagueScoringType);
		model.addAttribute("prevYearKey", prevYearKey);
    	model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);

        return "yahooleaguelookup";
    }

	@RequestMapping(value = "/yahoolinkaccount")
	public String yahoolinkaccount(Model model, HttpServletRequest request, HttpServletResponse response) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);

		// oauth request token variables
		String xoauth_request_auth_url = null;
		String oauth_callback_confirmed = null;
		
		// oauth access token variables
		String oauth_access_token = null;
		String oauth_access_token_secret = null;
		String oauth_session_handle = request.getParameter("oauth_session_handle");
		String oauth_authorization_expires_in = null;
		String xoauth_yahoo_guid = null;
		
		// oauth multi-use variables
		String oauth_token = request.getParameter("oauth_token");
		String oauth_verifier = request.getParameter("oauth_verifier");
		String oauth_expires_in = null;

		// non-oauth variables
		String query = request.getQueryString();
		
		// when page is first loaded
		if (query == null) {
			try {
				String oauth_response_token = YahooOAuth.getRequestToken();
								
				// parse oauth token values from string returned
				int index = oauth_response_token.indexOf("oauth_token=") + "oauth_token=".length();
				oauth_token = oauth_response_token.substring(index, oauth_response_token.indexOf("&",index));
				index = oauth_response_token.indexOf("oauth_token_secret=") + "oauth_token_secret=".length();
				oauth_token_secret = oauth_response_token.substring(index, oauth_response_token.indexOf("&",index));
				index = oauth_response_token.indexOf("oauth_expires_in=") + "oauth_expires_in=".length();
				oauth_expires_in = oauth_response_token.substring(index, oauth_response_token.indexOf("&",index));
				index = oauth_response_token.indexOf("xoauth_request_auth_url=") + "xoauth_request_auth_url=".length();
				xoauth_request_auth_url = URLDecoder.decode(oauth_response_token.substring(index, oauth_response_token.indexOf("&",index)),"UTF-8");
				index = oauth_response_token.indexOf("oauth_callback_confirmed=") + "oauth_callback_confirmed=".length();
				oauth_callback_confirmed = oauth_response_token.substring(index, oauth_response_token.length());

				// print values to log
				System.out.println("oauth_token=" + oauth_token);
				System.out.println("oauth_token_secret=" + oauth_token_secret);
				System.out.println("oauth_expires_in=" + oauth_expires_in);
				System.out.println("xoauth_request_auth_url=" + xoauth_request_auth_url);
				System.out.println("oauth_callback_confirmed=" + oauth_callback_confirmed);
			
			} catch (IOException e) {
				e.printStackTrace();
			} 
			return "redirect:" + xoauth_request_auth_url;
		}
		
		// when redirected back to page after yahoo authorization
		try {
			String access_token = YahooOAuth.getAccessToken(oauth_verifier, oauth_token, oauth_token_secret);

			// parse oauth token values from string returned
			int index = access_token.indexOf("oauth_token=") + "oauth_token=".length();
			oauth_access_token = access_token.substring(index, access_token.indexOf("&",index));
			index = access_token.indexOf("oauth_token_secret=") + "oauth_token_secret=".length();
			oauth_access_token_secret = access_token.substring(index, access_token.indexOf("&",index));
			index = access_token.indexOf("oauth_expires_in=") + "oauth_expires_in=".length();
			oauth_expires_in = access_token.substring(index, access_token.indexOf("&",index));
			index = access_token.indexOf("oauth_session_handle=") + "oauth_session_handle=".length();
			oauth_session_handle = access_token.substring(index, access_token.indexOf("&",index));
			index = access_token.indexOf("oauth_authorization_expires_in=") + "oauth_authorization_expires_in=".length();
			oauth_authorization_expires_in = access_token.substring(index, access_token.indexOf("&",index));
			index = access_token.indexOf("xoauth_yahoo_guid=") + "xoauth_yahoo_guid=".length();
			xoauth_yahoo_guid = access_token.substring(index, access_token.length());

			// print values to log
			System.out.println("oauth_token=" + oauth_access_token);
			System.out.println("oauth_token_secret=" + oauth_access_token_secret);
			System.out.println("oauth_expires_in=" + oauth_expires_in);
			System.out.println("oauth_session_handle=" + oauth_session_handle);
			System.out.println("oauth_authorization_expires_in=" + oauth_authorization_expires_in);
			System.out.println("xoauth_yahoo_guid=" + xoauth_yahoo_guid);

			User yahooUser = userDao.findByUserName(currentUser);
			if (yahooUser.getYahooGUID() == null) {
				yahooUser.setYahooGUID(xoauth_yahoo_guid);
			}
			yahooUser.setYahooOAuthAccessToken(oauth_access_token);
			yahooUser.setYahooOAuthSessionHandle(oauth_session_handle);
			yahooUser.setYahooOAuthTokenSecret(oauth_access_token_secret);
	    	Date now = new Date(System.currentTimeMillis() + (Long.parseLong(oauth_expires_in) * 1000));
			yahooUser.setYahooOAuthTokenExpiration(now);
			userDao.save(yahooUser);

		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return "redirect:/closewindow";
	}
	
	@RequestMapping(value = "/yahoorefreshaccesstoken")
	public String yahooRefreshAccessToken(Model model, HttpServletRequest request, HttpServletResponse response) {
		// check for user in session
		String currentUser = this.getUsernameFromSession(request);

		String refreshUser = null;
		if (request.getParameter("refreshUsername") != null) {
			refreshUser = request.getParameter("refreshUsername");
		} else {
			refreshUser = currentUser;
		}
		
		// oauth access token variables
		String oauth_access_token = null;
		String oauth_access_token_secret = null;
		String oauth_session_handle = request.getParameter("oauth_session_handle");
		String oauth_authorization_expires_in = null;
		String xoauth_yahoo_guid = null;
		String oauth_expires_in = null;
		
		// get expired access token and session handle from user object
		User yahooUser = userDao.findByUserName(refreshUser);
		oauth_access_token = yahooUser.getYahooOAuthAccessToken();
		oauth_session_handle = yahooUser.getYahooOAuthSessionHandle();
		oauth_access_token_secret = yahooUser.getYahooOAuthTokenSecret();

		// refresh access token
		if (oauth_session_handle != null) {
			try {
				String access_token = YahooOAuth.refreshAccessToken(oauth_access_token, oauth_session_handle, oauth_access_token_secret);

				// parse oauth token values from string returned
				int index = access_token.indexOf("oauth_token=") + "oauth_token=".length();
				oauth_access_token = access_token.substring(index, access_token.indexOf("&",index));
				index = access_token.indexOf("oauth_token_secret=") + "oauth_token_secret=".length();
				oauth_access_token_secret = access_token.substring(index, access_token.indexOf("&",index));
				index = access_token.indexOf("oauth_expires_in=") + "oauth_expires_in=".length();
				oauth_expires_in = access_token.substring(index, access_token.indexOf("&",index));
				index = access_token.indexOf("oauth_session_handle=") + "oauth_session_handle=".length();
				oauth_session_handle = access_token.substring(index, access_token.indexOf("&",index));
				index = access_token.indexOf("oauth_authorization_expires_in=") + "oauth_authorization_expires_in=".length();
				oauth_authorization_expires_in = access_token.substring(index, access_token.indexOf("&",index));
				index = access_token.indexOf("xoauth_yahoo_guid=") + "xoauth_yahoo_guid=".length();
				xoauth_yahoo_guid = access_token.substring(index, access_token.length());

				// print values to log
				System.out.println("oauth_token=" + oauth_access_token);
				System.out.println("oauth_token_secret=" + oauth_access_token_secret);
				System.out.println("oauth_expires_in=" + oauth_expires_in);
				System.out.println("oauth_session_handle=" + oauth_session_handle);
				System.out.println("oauth_authorization_expires_in=" + oauth_authorization_expires_in);
				System.out.println("xoauth_yahoo_guid=" + xoauth_yahoo_guid);

				yahooUser.setYahooOAuthAccessToken(oauth_access_token);
				yahooUser.setYahooOAuthSessionHandle(oauth_session_handle);
				yahooUser.setYahooOAuthTokenSecret(oauth_access_token_secret);
		    	Date expiration = new Date(System.currentTimeMillis() + (Long.parseLong(oauth_expires_in) * 1000));
				yahooUser.setYahooOAuthTokenExpiration(expiration);
				userDao.save(yahooUser);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/closewindow";
	}

}
