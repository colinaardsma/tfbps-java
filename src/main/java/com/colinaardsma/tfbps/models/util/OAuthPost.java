package com.colinaardsma.tfbps.models.util;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;

public class OAuthPost {
	


	
	public static String postConnection (String site, String query) throws IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

	      final String consumer_key = "dj0yJmk9YWE1SnlhV0lUbndoJmQ9WVdrOU9FUmhUelV6TkdVbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1lMQ--";
	      final String consumer_secret = "55d6606ea0bec9a1468d3ea01bbf1c9991dbf93f";

	      
	        //Create an HttpURLConnection and add some headers
	      URL url = new URL(site + "?" + URLEncoder.encode(query, "UTF-8"));
	     HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
	     urlConnection.setRequestProperty("Accept", "application/json");
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
	        String formEncoded = sb.toString();;
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
	           System.out.println("Response: " + urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
	          InputStream in = new BufferedInputStream(urlConnection.getInputStream());
	           String inputStreamString = new Scanner(in,"UTF-8").useDelimiter("\\A").next();
	            System.out.println(inputStreamString);
	            
//	            BufferedReader rd = new BufferedReader(new InputStreamReader(responseCode==200?uc.getInputStream():uc.getErrorStream()));
//                StringBuffer sb = new StringBuffer();
//                String line;
//                while ((line = rd.readLine()) != null) {
//                    sb.append(line);
//                }
//                rd.close();
//                setResponseBody(sb.toString());
	    		xmlData = inputStreamString;

	      }
	       finally {
	           urlConnection.disconnect();
	           
	     }
   		return xmlData;
	 
	}
}
