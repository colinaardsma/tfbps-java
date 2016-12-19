package com.colinaardsma.tfbps.models.util;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;

import com.colinaardsma.tfbps.models.User;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthUtil;

import oauth.signpost.OAuth;

public class YahooOAuth {
	
	// declare oauth variables
	private static final String consumer_key = "dj0yJmk9YWE1SnlhV0lUbndoJmQ9WVdrOU9FUmhUelV6TkdVbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1lMQ--";
	private static final String consumer_secret = "55d6606ea0bec9a1468d3ea01bbf1c9991dbf93f";

	public static String getRequestToken () throws IOException {

		final String requestURL = "https://api.login.yahoo.com/oauth/v2/get_request_token";
		
		// generate nonce (number to be used once)
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");		
		String nonce = uuid_string; // any relatively random alphanumeric string will work here
		
		// remaining parameters
		String timestamp = System.currentTimeMillis()/1000L +"";
		String method = "GET";
		String signature_method = "HMAC-SHA1";
		String version = "1.0";
		String lang_pref = "en-us";
		String callback = "http://localhost:8080/yahoolinkaccount";
		
		// build map of parameters for oauth normalization
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("oauth_callback", callback);
		paramMap.put("oauth_consumer_key", consumer_key);
		paramMap.put("oauth_nonce", nonce);
		paramMap.put("oauth_signature_method", signature_method);
		paramMap.put("oauth_timestamp", timestamp);
		paramMap.put("oauth_version", version);
		paramMap.put("xoauth_lang_pref", lang_pref);
		
		String oauth_signature = null;
		
		// calculate HMAC-SHA1 hex value for oauth_signature
		try {
			String key = OAuth.percentEncode(consumer_secret) + "&";
			String base_string = OAuthUtil.getSignatureBaseString(requestURL, method, paramMap);

//			System.out.println(key);
//			System.out.println(base_string);
			
		    byte[] keyBytes = key.getBytes();
		    SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

		    Mac mac = Mac.getInstance("HmacSHA1");

		    mac.init(secretKey);

		    byte[] text = base_string.getBytes();

			oauth_signature = new String(Base64.encodeBase64(mac.doFinal(text))).trim();
			oauth_signature = OAuth.percentEncode(oauth_signature);
//			System.out.println(oauth_signature);
			
		} catch (OAuthException | NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}

		callback = OAuth.percentEncode(callback); // encode callback url for inclusion in query string
		String params = "oauth_callback=" + callback + "&oauth_consumer_key=" + consumer_key + "&oauth_nonce=" + nonce + "&oauth_signature_method=" + signature_method + "&oauth_timestamp=" + timestamp + "&oauth_version=" + version + "&xoauth_lang_pref=" + lang_pref + "&oauth_signature=" + oauth_signature;
				
		// create an HttpsURLConnection and add some headers
		URL url = new URL(requestURL + "?" + params);
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
//			System.out.println(oauth_response_token);
		}
		finally {
			urlConnection.disconnect();
		}
		
		return oauth_response_token;

	}
	
	// https://developer.yahoo.com/oauth/guide/oauth-refreshaccesstoken.html
	// https://developer.yahoo.com/oauth/guide/oauth-make-request.html
	// https://dev.twitter.com/oauth/overview/authorizing-requests
	
	public static String getAccessToken(String oauth_verifier, String oauth_token, String oauth_token_secret) throws IOException {
		
		final String tokenURL = "https://api.login.yahoo.com/oauth/v2/get_token";
		
		// generate nonce (number to be used once)
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");		
		String nonce = uuid_string; // any relatively random alphanumeric string will work here
		
		String timestamp = System.currentTimeMillis()/1000L +"";
		String method = "GET";
		String signature_method = "HMAC-SHA1";
		String version = "1.0";
		
		// build map of parameters for oauth normalization
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("oauth_consumer_key", consumer_key);
		paramMap.put("oauth_nonce", nonce);
		paramMap.put("oauth_signature_method", signature_method);
		paramMap.put("oauth_timestamp", timestamp);
		paramMap.put("oauth_token", oauth_token);
		paramMap.put("oauth_verifier", oauth_verifier);
		paramMap.put("oauth_version", version);
		
		String oauth_signature = null;
		
		// calculate HMAC-SHA1 hex value for oauth_signature
		try {
			String key = OAuth.percentEncode(consumer_secret) + "&" + OAuth.percentEncode(oauth_token_secret);
			String base_string = OAuthUtil.getSignatureBaseString(tokenURL, method, paramMap);

//			System.out.println(key);
//			System.out.println(base_string);
			
		    byte[] keyBytes = key.getBytes();
		    SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

		    Mac mac = Mac.getInstance("HmacSHA1");

		    mac.init(secretKey);

		    byte[] text = base_string.getBytes();

			oauth_signature = new String(Base64.encodeBase64(mac.doFinal(text))).trim();
//			System.out.println(oauth_signature);
			oauth_signature = OAuth.percentEncode(oauth_signature);
//			System.out.println(oauth_signature);
			
		} catch (OAuthException | NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}

		String params = "oauth_consumer_key=" + consumer_key + "&oauth_nonce=" + nonce + "&oauth_signature_method=" + signature_method + "&oauth_timestamp=" + timestamp + "&oauth_token=" + oauth_token + "&oauth_verifier=" + oauth_verifier + "&oauth_version=" + version + "&oauth_signature=" + oauth_signature;

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
//			System.out.println(access_token);
		}
		finally {
			urlConnection.disconnect();
		}
						
		return access_token;

	}
	
	public static String refreshAccessToken(String oauth_access_token, String oauth_session_handle, String oauth_access_token_secret) throws IOException {
		
		final String tokenURL = "https://api.login.yahoo.com/oauth/v2/get_token";
		
		// generate nonce (number to be used once)
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");		
		String nonce = uuid_string; // any relatively random alphanumeric string will work here
		
		String timestamp = System.currentTimeMillis()/1000L +"";
		String method = "GET";
		String signature_method = "HMAC-SHA1";
		String version = "1.0";
		
		// build map of parameters for oauth normalization
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("oauth_consumer_key", consumer_key);
		paramMap.put("oauth_nonce", nonce);
		paramMap.put("oauth_session_handle", oauth_session_handle);
		paramMap.put("oauth_signature_method", signature_method);
		paramMap.put("oauth_timestamp", timestamp);
		paramMap.put("oauth_token", OAuth.percentDecode(oauth_access_token));
		paramMap.put("oauth_version", version);
		
		String oauth_signature = null;
		
		// calculate HMAC-SHA1 hex value for oauth_signature
		try {
			String key = OAuth.percentEncode(consumer_secret) + "&" + OAuth.percentEncode(oauth_access_token_secret);
			String base_string = OAuthUtil.getSignatureBaseString(tokenURL, method, paramMap);

//			System.out.println(key);
//			System.out.println(base_string);
			
		    byte[] keyBytes = key.getBytes();
		    SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

		    Mac mac = Mac.getInstance("HmacSHA1");

		    mac.init(secretKey);

		    byte[] text = base_string.getBytes();

			oauth_signature = new String(Base64.encodeBase64(mac.doFinal(text))).trim();
//			System.out.println(oauth_signature);
			oauth_signature = OAuth.percentEncode(oauth_signature);
//			System.out.println(oauth_signature);
			
		} catch (OAuthException | NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}

		String params = "oauth_consumer_key=" + consumer_key + "&oauth_nonce=" + nonce + "&oauth_session_handle=" + oauth_session_handle + "&oauth_signature_method=" + signature_method + "&oauth_timestamp=" + timestamp + "&oauth_token=" + oauth_access_token + "&oauth_version=" + version + "&oauth_signature=" + oauth_signature;

		
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
//			System.out.println(access_token);
		}
		finally {
			urlConnection.disconnect();
		}
		
		return access_token;

	}
	
	public static String oauthGetRequest(String requestURL, User user) throws IOException {
		
		// generate nonce (number to be used once)
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");		
		String nonce = uuid_string; // any relatively random alphanumeric string will work here
		
		String timestamp = System.currentTimeMillis()/1000L +"";
		String method = "GET";
		String signature_method = "HMAC-SHA1";
		String version = "1.0";
		String oauth_access_token = user.getYahooOAuthAccessToken();
		String oauth_session_handle = user.getYahooOAuthSessionHandle();
		String oauth_access_token_secret = user.getYahooOAuthTokenSecret();
		
		// build map of parameters for oauth normalization
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("oauth_consumer_key", consumer_key);
		paramMap.put("oauth_nonce", nonce);
		paramMap.put("oauth_session_handle", oauth_session_handle);
		paramMap.put("oauth_signature_method", signature_method);
		paramMap.put("oauth_timestamp", timestamp);
		paramMap.put("oauth_token", OAuth.percentDecode(oauth_access_token));
		paramMap.put("oauth_version", version);
		
		String oauth_signature = null;
		
		// calculate HMAC-SHA1 hex value for oauth_signature
		try {
			String key = OAuth.percentEncode(consumer_secret) + "&" + OAuth.percentEncode(oauth_access_token_secret);
			String base_string = OAuthUtil.getSignatureBaseString(requestURL, method, paramMap);

//			System.out.println(key);
//			System.out.println(base_string);
			
		    byte[] keyBytes = key.getBytes();
		    SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

		    Mac mac = Mac.getInstance("HmacSHA1");

		    mac.init(secretKey);

		    byte[] text = base_string.getBytes();

			oauth_signature = new String(Base64.encodeBase64(mac.doFinal(text))).trim();
//			System.out.println(oauth_signature);
			oauth_signature = OAuth.percentEncode(oauth_signature);
//			System.out.println(oauth_signature);
			
		} catch (OAuthException | NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}

		String params = "oauth_consumer_key=" + consumer_key + "&oauth_nonce=" + nonce + "&oauth_session_handle=" + oauth_session_handle + "&oauth_signature_method=" + signature_method + "&oauth_timestamp=" + timestamp + "&oauth_token=" + oauth_access_token + "&oauth_version=" + version + "&oauth_signature=" + oauth_signature;

		
		// create an HttpsURLConnection and add some headers
		URL url = new URL(requestURL + "?" + params);
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();		

		// send the request and read the output
		String html = new String();
		try {
			System.out.println("Connecting to: " + url.toString());
			System.out.println("Response: " + urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			Scanner scanner = new Scanner(in,"UTF-8");
			html = scanner.useDelimiter("\\A").next();;
			scanner.close();
//			System.out.println(html);
		}
		finally {
			urlConnection.disconnect();
		}
		
		return html;

	}

}
