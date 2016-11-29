package com.colinaardsma.tfbps.models.util;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Scanner;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;

public class OAuthPost {
	
	// declare oauth variables
	private static final String consumer_key = "dj0yJmk9YWE1SnlhV0lUbndoJmQ9WVdrOU9FUmhUelV6TkdVbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1lMQ--";
	private static final String consumer_secret = "55d6606ea0bec9a1468d3ea01bbf1c9991dbf93f";

	public static String postConnection (String site, String query) throws IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

		//Create an HttpURLConnection and add some headers
		URL url = new URL(site + "?q=" + URLEncoder.encode(query, "UTF-8"));
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
		urlConnection.setRequestProperty("Accept", "application/xml");
		urlConnection.setRequestMethod("POST");
		urlConnection.setDoOutput(true);

		//Build the list of parameters
		HttpParameters params =  new HttpParameters();
		params.put("q", query);

		//Sign the request
		//The key to signing the POST fields is to add them as additional parameters, but already percent-encoded; and also to add the realm header.
		OAuthConsumer dealabsConsumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);
		HttpParameters doubleEncodedParams =  new HttpParameters();
		Iterator<String> iter = params.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			doubleEncodedParams.put(key, OAuth.percentEncode(params.getFirst(key)));
		}
		doubleEncodedParams.put("realm", site);
		dealabsConsumer.setAdditionalParameters(doubleEncodedParams);
		dealabsConsumer.sign(urlConnection);

		//Create the POST payload
		StringBuilder sb = new StringBuilder();
		iter = params.keySet().iterator();
		for (int i = 0; iter.hasNext(); i++) {
			String param = iter.next();
			if (i > 0) {
				sb.append("&");
			}
			sb.append(param);
			sb.append("=");
			sb.append(OAuth.percentEncode(params.getFirst(param)));
		}

		//Send the payload to the connection
		String formEncoded = sb.toString();
		OutputStreamWriter outputStreamWriter = null;
		try {
			outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
			outputStreamWriter.write(formEncoded);
		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
		}

		//Send the request and read the output
		String xmlData = new String();
		try {
			System.out.println("Connecting to: " + site + "?" + sb);
			System.out.println("Response: " + urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			Scanner scanner = new Scanner(in,"UTF-8");
			xmlData = scanner.useDelimiter("\\A").next();;
			scanner.close();
			System.out.println(xmlData);
		}
		finally {
			urlConnection.disconnect();
		}
		
		return xmlData;

	}

	public static String[] getRequestToken () throws IOException {

		final String loginURL = "https://api.login.yahoo.com/oauth/v2/get_request_token";
		
		// generate nonce (number to be used once)
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");		
		String nonce = uuid_string; // any relatively random alphanumeric string will work here
		
		String timestamp = System.currentTimeMillis()/1000L +"";

		String signature_method = "plaintext";
		String version = "1.0";
//		String lang_pref = "en-us";
		
		String callback = URLEncoder.encode("http://localhost:8080/yahoouserlookup", "UTF-8");
				
		String params = "oauth_consumer_key=" + consumer_key + "&oauth_nonce=" + nonce + "&oauth_signature_method=" + signature_method + "&oauth_signature=" + consumer_secret + "%26" + "&oauth_timestamp=" + timestamp + "&oauth_version=" + version + "&oauth_callback=" + callback;
		
		// create an HttpsURLConnection and add some headers
		URL url = new URL(loginURL + "?" + params);
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

		// send the request and read the output
		String oauth_response_token = new String();
		try {
			System.out.println("Connecting to: " + url.toString());
			System.out.println("Response: " + urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			Scanner scanner = new Scanner(in,"UTF-8");
			oauth_response_token = scanner.useDelimiter("\\A").next();;
			scanner.close();
			System.out.println(oauth_response_token);
		}
		finally {
			urlConnection.disconnect();
		}
		
		// parse oauth token values from string returned
		int index = oauth_response_token.indexOf("oauth_token=") + "oauth_token=".length();
		String oauth_token = oauth_response_token.substring(index, oauth_response_token.indexOf("&",index));
		index = oauth_response_token.indexOf("oauth_token_secret=") + "oauth_token_secret=".length();
		String oauth_token_secret = oauth_response_token.substring(index, oauth_response_token.indexOf("&",index));
		index = oauth_response_token.indexOf("oauth_expires_in=") + "oauth_expires_in=".length();
		String oauth_expires_in = oauth_response_token.substring(index, oauth_response_token.indexOf("&",index));
		index = oauth_response_token.indexOf("xoauth_request_auth_url=") + "xoauth_request_auth_url=".length();
		String xoauth_request_auth_url = URLDecoder.decode(oauth_response_token.substring(index, oauth_response_token.indexOf("&",index)),"UTF-8");
		index = oauth_response_token.indexOf("oauth_callback_confirmed=") + "oauth_callback_confirmed=".length();
		String oauth_callback_confirmed = oauth_response_token.substring(index, oauth_response_token.length());

		// print values to log
		System.out.println("oauth_token=" + oauth_token);
		System.out.println("oauth_token_secret=" + oauth_token_secret);
		System.out.println("oauth_expires_in=" + oauth_expires_in);
		System.out.println("xoauth_request_auth_url=" + xoauth_request_auth_url);
		System.out.println("oauth_callback_confirmed=" + oauth_callback_confirmed);
		
		String[] oauthURLandTokenSecret = {xoauth_request_auth_url, oauth_token_secret};
		
		return oauthURLandTokenSecret;

	}
	
	
	// https://developer.yahoo.com/oauth/guide/oauth-refreshaccesstoken.html
	// https://developer.yahoo.com/oauth/guide/oauth-make-request.html
	
	public static String getAccessToken(String oauth_verifier, String oauth_token, String oauth_token_secret) throws IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {
		final String tokenURL = "https://api.login.yahoo.com/oauth/v2/get_token";
		
		// generate nonce (number to be used once)
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");		
		String nonce = uuid_string; // any relatively random alphanumeric string will work here
		
		String timestamp = System.currentTimeMillis()/1000L +"";

		String signature_method = "plaintext";
		String version = "1.0";
//		String lang_pref = "en-us";
					
		String params = "oauth_consumer_key=" + consumer_key + "&oauth_signature_method=" + signature_method + "&oauth_version=" + version + "&oauth_verifier=" + oauth_verifier + "&oauth_token=" + oauth_token + "&oauth_timestamp=" + timestamp + "&oauth_nonce=" + nonce + "&oauth_signature=" + consumer_secret + "%26" + oauth_token_secret;

		// create an HttpsURLConnection and add some headers
		URL url = new URL(tokenURL + "?" + params);
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();		

		// send the request and read the output
		String access_token = new String();
		try {
			System.out.println("Connecting to: " + url.toString());
			System.out.println("Response: " + urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			Scanner scanner = new Scanner(in,"UTF-8");
			access_token = scanner.useDelimiter("\\A").next();;
			scanner.close();
			System.out.println(access_token);
		}
		finally {
			urlConnection.disconnect();
		}
		
		// parse oauth token values from string returned
		int index = access_token.indexOf("oauth_token=") + "oauth_token=".length();
		String oauth_access_token = access_token.substring(index, access_token.indexOf("&",index));
		index = access_token.indexOf("oauth_token_secret=") + "oauth_token_secret=".length();
		String oauth_access_token_secret = access_token.substring(index, access_token.indexOf("&",index));
		index = access_token.indexOf("oauth_expires_in=") + "oauth_expires_in=".length();
		String oauth_expires_in = access_token.substring(index, access_token.indexOf("&",index));
		index = access_token.indexOf("oauth_session_handle=") + "oauth_session_handle=".length();
		String oauth_session_handle = access_token.substring(index, access_token.indexOf("&",index));
		index = access_token.indexOf("oauth_authorization_expires_in=") + "oauth_authorization_expires_in=".length();
		String oauth_authorization_expires_in = access_token.substring(index, access_token.indexOf("&",index));
		index = access_token.indexOf("xoauth_yahoo_guid=") + "xoauth_yahoo_guid=".length();
		String xoauth_yahoo_guid = access_token.substring(index, access_token.length());

		// print values to log
		System.out.println("oauth_token=" + oauth_access_token);
		System.out.println("oauth_token_secret=" + oauth_access_token_secret);
		System.out.println("oauth_expires_in=" + oauth_expires_in);
		System.out.println("oauth_session_handle=" + oauth_session_handle);
		System.out.println("oauth_authorization_expires_in=" + oauth_authorization_expires_in);
		System.out.println("xoauth_yahoo_guid=" + xoauth_yahoo_guid);
				
		return access_token;

	}

}
