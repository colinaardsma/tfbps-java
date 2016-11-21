package com.colinaardsma.tfbps.models.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;

public class Oauth {
			// **** POTENTIAL CONFIGURATION STARTS HERE ****

			// NOTE: If you don't have the OAuth extension hooked into PHP, you may need
			//  to include it here.

			// MODIFY: Insert whichever URL you'd like to try below. By default, the
			//  following URL will try to pull out the NFL teams for the logged-in user
			String url = "http://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games;game_keys=nfl/teams";
			String scope = "test"; // $scope = 'test';

			// MODIFY: Insert your own consumer key and secret here!
			List<String> consumer_data = new ArrayList<String>(); // $consumer_data = array();
			String consumerKey = "dj0yJmk9YWE1SnlhV0lUbndoJmQ9WVdrOU9FUmhUelV6TkdVbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1lMQ--"; //consumer_data['test']['key'] = 'dj0yJmk9YWE1SnlhV0lUbndoJmQ9WVdrOU9FUmhUelV6TkdVbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD1lMQ--';
			String consumerSecret = "55d6606ea0bec9a1468d3ea01bbf1c9991dbf93f"; // consumer_data['test']['secret'] = '55d6606ea0bec9a1468d3ea01bbf1c9991dbf93f';


			// **** MAIN PROGRAM START HERE ****

//			$consumer_key = $consumer_data[$scope]['key'];
//			$consumer_secret = $consumer_data[$scope]['secret'];

			// By default, try to store token information in /tmp folder
//			PrintWriter token_file_name = new PrintWriter("/tmp/oauth_data_token_storage_" + consumerKey + ".out");

			String access_token = null;
			String access_secret = null;
			String access_session = null;
			String access_verifier = null;
			boolean store_access_token_data = false;

			String filePath = "/tmp/oauth_data_token_storage_" + consumerKey + ".out";
			PrintWriter token_file_name = new PrintWriter(filePath);
//			token_file_name.println(consumerKey);

			
			try (BufferedReader tok_fh = new BufferedReader(new FileReader(filePath))) {
				access_token = tok_fh.readLine();
				boolean invalid_file = false;

				// Get first line: access token
				if(access_token != null) {
					// Get next line: access secret
					//					for(int i = 0; i < 2; i++){
					//						tok_fh.readLine();
					//					}
					access_secret = tok_fh.readLine();
					if(access_secret != null) {
						// Get next line: access session handle
						access_session = tok_fh.readLine();
						if(access_session == null) {
							invalid_file = true;
						}
					} else {
						invalid_file = true;
					}
				} else {
					invalid_file = true;
				}

				String status;

				if(invalid_file) {
					status = "File did not seem to be formatted correctly -- needs 3 lines with access token, secret, and session handle.";
					access_token = null;
					access_secret = null;
					access_session = null;
				} else {
					status = "Got access token information!\nToken: ${access_token}\nSecret: ${access_secret}\nSession Handle: ${access_session}";
					access_token.trim();
					access_secret.trim();
					access_session.trim();
				}

			} catch(FileNotFoundException e) {
				status = "Couldn't open ${token_file_name}, assuming we need to get a new request token.\n";
				e.printStackTrace();
			} finally {
				token_file_name.close();
				tok_fh.close();
			}

			// 1. See if we have a stored access token/secret/session. If so, try to use
//			    that token.
			if(access_token) {
				
				
				Oauth2 oauth2 = new Oauth2.Builder(httpTransport, jsonFactory, credential).setApplicationName("YourAppName").build();
				Tokeninfo tokenInfo = oauth2.tokeninfo().setAccessToken(credential.getAccessToken()).execute();
				 
				return oauth2.userinfo().get().execute();
				
				DefaultOAuth2ClientContext oa = new DefaultOAuth2ClientContext(consumer_key, consumer_secret, OAUTH_SIG_METHOD_HMACSHA1, OAUTH_AUTH_TYPE_URI);
				OAuth o = new OAuth();
				
			  o = new OAuth( $consumer_key, $consumer_secret,
			                  OAUTH_SIG_METHOD_HMACSHA1, OAUTH_AUTH_TYPE_URI );
			  $o->enableDebug();

			  $auth_failure = false;

			  // Try to make request using stored token
			  try {
			    $o->setToken( $access_token, $access_secret );
			    if( $o->fetch( $url ) ) {
			      print "Got data from API:\n\n";
			      print $o->getLastResponse() . "\n\n";

			      print "Successful!\n";
			      exit;
			    } else {
			      print "Couldn'\t fetch\n";
			    }
			  } catch( OAuthException $e ) {
			    print 'Error: ' . $e->getMessage() . "\n";
			    print 'Error Code: ' . $e->getCode() . "\n";
			    print 'Response: ' . $e->lastResponse . "\n";

			    if( $e->getCode() == 401 ) {
			      $auth_failure = true;
			    }
			  }


			  // 2. If we get an auth error, try to refresh the token using the session.
			  if( $auth_failure ) {

			    try {
			      $response = $o->getAccessToken( 'https://api.login.yahoo.com/oauth/v2/get_token', $access_session, $access_verifier );
			    } catch( OAuthException $e ) {
			      print 'Error: ' . $e->getMessage() . "\n";
			      print 'Response: ' . $e->lastResponse . "\n";

			      $response = NULL;
			    }

			    print_r( $response );

			    if( $response ) {
			      $access_token = $response['oauth_token'];
			      $access_secret = $response['oauth_token_secret'];
			      $access_session = $response['oauth_session_handle'];
			      $store_access_token_data = true;

			      print "Was able to refresh access token:\n";
			      print " Token: ${access_token}\n";
			      print " Secret: ${access_secret}\n";
			      print " Session Handle: ${access_session}\n\n";

			    } else {

			      $access_token = NULL;
			      $access_secret = NULL;
			      $access_session = NULL;
			      print "Unable to refresh access token, will need to request a new one.\n";
			    }
			  }
			}

			// 3. If none of that worked, send the user to get a new token
			if( ! $access_token ) {

			  print "Better try to get a new access token.\n";
			  $o = new OAuth( $consumer_key, $consumer_secret,
			                  OAUTH_SIG_METHOD_HMACSHA1, OAUTH_AUTH_TYPE_URI );
			  $o->enableDebug();

			  $request_token = NULL;

			  try {
			    $response = $o->getRequestToken( "https://api.login.yahoo.com/oauth/v2/get_request_token", 'oob' );
			    $request_token = $response['oauth_token'];
			    $request_secret = $response['oauth_token_secret'];

			    print "Hey! Go to this URL and tell us the verifier you get at the end.\n";
			    print ' ' . $response['xoauth_request_auth_url'] . "\n";

			  } catch( OAuthException $e ) {
			    print $e->getMessage() . "\n";
			  }

			  // Wait for input, then try to use it to get a new access token.
			  if( $request_token && $request_secret ) {
			    print "Type the verifier and hit enter...\n";
			    $verifier = fgets( STDIN );
			    $verifier = rtrim( $verifier );

			    print "Here's the verifier you gave us: ${verifier}\n";

			    try {
			      $o->setToken( $request_token, $request_secret );
			      $response = $o->getAccessToken( 'https://api.login.yahoo.com/oauth/v2/get_token', NULL, $verifier );

			      print "Got it!\n";
			      $access_token = $response['oauth_token'];
			      $access_secret = $response['oauth_token_secret'];
			      $access_session = $response['oauth_session_handle'];
			      $store_access_token_data = true;
			      print " Token: ${access_token}\n";
			      print " Secret: ${access_secret}\n";
			      print " Session Handle: ${access_session}\n\n";


			    } catch( OAuthException $e ) {
			      print 'Error: ' . $e->getMessage() . "\n";
			      print 'Response: ' . $e->lastResponse . "\n";
			      print "Shoot, couldn't get the access token. :(\n";
			    }
			  }

			}

			if( $access_token ) {

			  // Try to make request using stored token
			  try {
			    $o->setToken( $access_token, $access_secret );
			    if( $o->fetch( $url ) ) {
			      print "Got data from API:\n\n";
			      print $o->getLastResponse() . "\n\n";

			      print "Successful!\n";
			    } else {
			      print "Couldn'\t fetch\n";
			    }
			  } catch( OAuthException $e ) {
			    print 'Error: ' . $e->getMessage() . "\n";
			    print 'Error Code: ' . $e->getCode() . "\n";
			    print 'Response: ' . $e->lastResponse . "\n";
			  }
			}

			// 4. Rewrite token information if necessary
			if( $store_access_token_data ) {

			  print "Looks like we need to store access token data! Doing that now.\n";

			  $tok_fh = fopen( $token_file_name, 'w' );
			  if( $tok_fh ) {
			    fwrite( $tok_fh, "${access_token}\n" );
			    fwrite( $tok_fh, "${access_secret}\n" );
			    fwrite( $tok_fh, "${access_session}\n" );

			    fclose( $tok_fh );
			  } else {
			    print "Hm, couldn't open file to write back access token information.\n";
			  }
			}

}
