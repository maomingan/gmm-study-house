package com.gmm.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

public class HashUtil {
	
	public static String getSHA2(String inputString){

		try{
			MessageDigest m = MessageDigest.getInstance("SHA-256");
			m.reset();
			m.update(inputString.getBytes("UTF-8"));
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			
			while (hashtext.length() < 64) {
	            hashtext = "0" + hashtext;
	        }
			
			return hashtext;
			
		}catch(Exception e) {
			return null;
		}
	}

	public static String getMD5(String inputString) {

		try{
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(inputString.getBytes("UTF-8"));
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);

			while (hashtext.length() < 32) {
	            hashtext = "0" + hashtext;
	        }
			
			return hashtext;
			
		}catch(Exception e) {
			return null;
		}
		
	}
	
	public static void main(String[] args) throws IOException {

		System.out.println(getMD5("522631199108210010"));
		System.out.println(getMD5("18767122586"));
		System.out.println();

	}
	
}
