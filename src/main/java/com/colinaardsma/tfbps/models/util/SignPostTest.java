package com.colinaardsma.tfbps.models.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;


public class SignPostTest {

	private static final Logger log = Logger.getLogger(SignPostTest.class);

	// Please provide your consumer key here
	private static String consumer_key = "dj0yJmk9YWE1SnlhV0lUbndoJmQ9WVdrOU9FUmhUelV6TkdVbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1lMQ--";

	// Please provide your consumer secret here
	private static String consumer_secret = "55d6606ea0bec9a1468d3ea01bbf1c9991dbf93f";

	/** The HTTPS request object used for the connection */
	private static StHttpRequest httpsRequest = new StHttpRequest();
	private static StHttpRequest httpsPostRequest = new StHttpRequest();

	/** Encode Format */
	private static final String ENCODE_FORMAT = "UTF-8";

	private static final int HTTP_STATUS_OK = 200;

	
	public String returnHttpData(String url) throws UnsupportedEncodingException, Exception{

		if(this.isConsumerKeyExists() && this.isConsumerSecretExists()) {

			// Create oAuth Consumer
			OAuthConsumer consumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);

			// Set the HTTPS request correctly
			httpsRequest.setOAuthConsumer(consumer);

			try {
//				log.info("sending get request to: " + URLDecoder.decode(url, ENCODE_FORMAT));
				log.info("sending get request to: " + url);

				int responseCode = httpsRequest.sendGetRequest(url);

				// Send the request
				if(responseCode == HTTP_STATUS_OK) {
					log.info("Response ");
				} else {
					log.error("Error in response due to status code = " + responseCode);
				}
				log.info(httpsRequest.getResponseBody());

			} catch(UnsupportedEncodingException e) {
				log.error("Encoding/Decording error");
			} catch (IOException e) {
				log.error("Error with HTTP IO", e);
			} catch (Exception e) {
				log.error(httpsRequest.getResponseBody(), e);
				return null;
			}


		} else {
			log.error("Key/Secret does not exist");
		}
		
		// return xml data from url
		String xmlData = httpsRequest.getResponseBody();
		return xmlData;

	}

	private boolean isConsumerKeyExists() {
		if(consumer_key.isEmpty()) {
			log.error("Consumer Key is missing. Please provide the key");
			return false;
		}
		return true;
	}

	private boolean isConsumerSecretExists() {
		if(consumer_secret.isEmpty()) {
			log.error("Consumer Secret is missing. Please provide the key");
			return false;
		}
		return true;
	}

	
	
	////
	public String returnHttpPostData(String url, String query) throws UnsupportedEncodingException, Exception{

		if(this.isConsumerKeyExists() && this.isConsumerSecretExists()) {

			// Create oAuth Consumer
			OAuthConsumer consumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);

			// Set the HTTPS request correctly
			httpsPostRequest.setOAuthConsumer(consumer);

			try {
//				log.info("sending get request to: " + URLDecoder.decode(url, ENCODE_FORMAT));
				log.info("sending get request to: " + url + "?" + query);

				int responseCode = httpsPostRequest.sendPostRequest(url, query);

				// Send the request
				if(responseCode == HTTP_STATUS_OK) {
					log.info("Response ");
				} else {
					log.error("Error in response due to status code = " + responseCode);
				}
				log.info(httpsPostRequest.getResponseBody());

			} catch(UnsupportedEncodingException e) {
				log.error("Encoding/Decording error");
			} catch (IOException e) {
				log.error("Error with HTTP IO", e);
			} catch (Exception e) {
				log.error(httpsPostRequest.getResponseBody(), e);
				return null;
			}


		} else {
			log.error("Key/Secret does not exist");
		}
		
		// return xml data from url
		String xmlData = httpsPostRequest.getResponseBody();
		return xmlData;

	}


	
	
	
	
	
}
