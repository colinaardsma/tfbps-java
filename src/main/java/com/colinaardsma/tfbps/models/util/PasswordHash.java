package com.colinaardsma.tfbps.models.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.HmacUtils;

/**
 * Created by cbay on 5/15/15.
 *
 * Based on approach outlined at http://viralpatel.net/blogs/java-md5-hashing-salting-password/
 */
public class PasswordHash {

    private static final String salt = "34@5rethSDG$Gnetydk&rD676sGgjk8rnok7GS*DFGFsbtfz#cc";

    public static String getHash(String password){

        // Create a one-way hash of a password

        String hash = null;
        String saltedPassword = applySalt(password);

        if(null == password) return null;

        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(saltedPassword.getBytes(), 0, saltedPassword.length());

            //Converts message digest value in base 16 (hex)
            hash = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash;

    }

    private static String applySalt(String password) {
        return password + salt;
    }

    public static boolean isValidPassword(String password, String hash) {

        // Determine whether or not hash represents password
        String hashedPassword = getHash(password);
        return hashedPassword.equals(hash);

    }
    
    public static String hmacSha1(String value) {
    	return HmacUtils.hmacSha1Hex(salt, value);
//        try {
//            // Get an hmac_sha1 key from the raw key bytes
//            byte[] keyBytes = salt.getBytes();           
//            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
//
//            // Get an hmac_sha1 Mac instance and initialize with the signing key
//            Mac mac = Mac.getInstance("HmacSHA1");
//            mac.init(signingKey);
//
//            // Compute the hmac on input data bytes
//            byte[] rawHmac = mac.doFinal(value.getBytes());
//
//            // Convert raw bytes to Hex
//            byte[] hexBytes = new Hex().encode(rawHmac);
//
//            //  Covert array of Hex bytes to a String
//            return new String(hexBytes, "UTF-8");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

}
