package com.colinaardsma.tfbps.models.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;


/**
 * @author David Hardtke
 * @author xyz
 * Simple HTTPS Request implementation
 */
public class StHttpRequest {

private static final Logger log = Logger.getLogger(StHttpRequest.class);

    private String responseBody = "";

    private OAuthConsumer consumer = null;

    /** Default Constructor */
    public StHttpRequest() { }

    public StHttpRequest(OAuthConsumer consumer) {
        this.consumer = consumer;
    }

    public HttpsURLConnection getConnection(String url)
    throws IOException,
        OAuthMessageSignerException,
        OAuthExpectationFailedException,
        OAuthCommunicationException
    {
     try {
             URL u = new URL(url);

             HttpsURLConnection uc = (HttpsURLConnection) u.openConnection();

             if (consumer != null) {
                 try {
                     log.info("Signing the oAuth consumer");
                     consumer.sign(uc);

                 } catch (OAuthMessageSignerException e) {
                     log.error("Error signing the consumer", e);
                     throw e;

                 } catch (OAuthExpectationFailedException e) {
                 log.error("Error signing the consumer", e);
                 throw e;

                 } catch (OAuthCommunicationException e) {
                 log.error("Error signing the consumer", e);
                 throw e;
                 }
                 uc.connect();
             }
             return uc;
     } catch (IOException e) {
     log.error("Error signing the consumer", e);
     throw e;
     }
    }

    /**
     * Sends an HTTP GET request to a url
     *
     * @param url the url
     * @return - HTTP response code
     */
    public int sendGetRequest(String url)
    throws IOException,
    OAuthMessageSignerException,
    OAuthExpectationFailedException,
    OAuthCommunicationException {


        int responseCode = 500;
        try {
            HttpsURLConnection uc = getConnection(url);

            responseCode = uc.getResponseCode();

            if(200 == responseCode || 401 == responseCode || 404 == responseCode){
                BufferedReader rd = new BufferedReader(new InputStreamReader(responseCode==200?uc.getInputStream():uc.getErrorStream()));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                setResponseBody(sb.toString());
            }
         } catch (MalformedURLException ex) {
            throw new IOException( url + " is not valid");
        } catch (IOException ie) {
            throw new IOException("IO Exception " + ie.getMessage());
        }
        return responseCode;
    }


    /**
     * Return the Response body
     * @return String
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * Setter
     * @param responseBody
     */
    public void setResponseBody(String responseBody) {
        if (null != responseBody) {
            this.responseBody = responseBody;
        }
    }

    /**
     * Set the oAuth consumer
     * @param consumer
     */
    public void setOAuthConsumer(OAuthConsumer consumer) {
        this.consumer = consumer;
    }
    
    
    ///////
    
    public HttpsURLConnection postConnection(String url, String query)
    throws IOException,
        OAuthMessageSignerException,
        OAuthExpectationFailedException,
        OAuthCommunicationException
    {
     try {
             URL u = new URL(url);

             HttpsURLConnection uc = (HttpsURLConnection) u.openConnection();
             
             // already connected error, somewhere in here?
             

             if (consumer != null) {
                 try {
                     log.info("Signing the oAuth consumer");
                     consumer.sign(uc);

                 } catch (OAuthMessageSignerException e) {
                     log.error("Error signing the consumer", e);
                     throw e;

                 } catch (OAuthExpectationFailedException e) {
                 log.error("Error signing the consumer", e);
                 throw e;

                 } catch (OAuthCommunicationException e) {
                 log.error("Error signing the consumer", e);
                 throw e;
                 }
                 uc.connect();
             }
             return uc;
     } catch (IOException e) {
     log.error("Error signing the consumer", e);
     throw e;
     }
    }
     public int sendPostRequest(String url, String query)
    		    throws IOException,
    		    OAuthMessageSignerException,
    		    OAuthExpectationFailedException,
    		    OAuthCommunicationException {


    		        int responseCode = 500;
    		        try {
    		            HttpsURLConnection uc = getConnection(url);
    		             // already connected error, somewhere in here?

    		             byte[] postData       = query.getBytes( StandardCharsets.UTF_8 );
    		             int    postDataLength = postData.length;

    		             uc.setDoOutput( true );
    		             uc.setInstanceFollowRedirects( false );
    		             uc.setRequestMethod( "POST" );
    		             uc.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
    		             uc.setRequestProperty( "charset", "utf-8");
    		             uc.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
    		             uc.setUseCaches( false );
    		  
    		             try( DataOutputStream wr = new DataOutputStream( uc.getOutputStream())) {
     		                wr.write( postData );
     		             }


    		            responseCode = uc.getResponseCode();

    		            if(200 == responseCode || 401 == responseCode || 404 == responseCode){
    		                BufferedReader rd = new BufferedReader(new InputStreamReader(responseCode==200?uc.getInputStream():uc.getErrorStream()));
    		                StringBuffer sb = new StringBuffer();
    		                String line;
    		                while ((line = rd.readLine()) != null) {
    		                    sb.append(line);
    		                }
    		                rd.close();
    		                setResponseBody(sb.toString());
    		            }
    		         } catch (MalformedURLException ex) {
    		            throw new IOException( url + " is not valid");
    		        } catch (IOException ie) {
    		            throw new IOException("IO Exception " + ie.getMessage());
    		        }
    		        return responseCode;
    		    }

     



    
    
    
}