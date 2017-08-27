package com.hashfunction.main;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

public class Codec {
		    
	    HashMap<String,String> hashmap = new HashMap<String, String>();
	    int encNumber;
	    String url_string = "1";
	    String url_hex;
	    // Encodes a URL to a shortened URL.
	    public String encode(String longUrl) {
	        char aRR[] = longUrl.toCharArray();
	        for(int i=0;i<aRR.length-1;i++){
	            encNumber = (int)aRR[i];
	            //System.out.println(encNumber);
	            url_string.concat(Integer.toString(encNumber));
	                      }
	       byte[] myBytes = null;
		try {
			myBytes = url_string.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       url_hex = DatatypeConverter.printHexBinary(myBytes);
	        hashmap.put(url_hex,longUrl);
	        return url_hex;
	    }

	    // Decodes a shortened URL to its original URL.
	    public String decode(String shortUrl) {
	        return hashmap.get(shortUrl);
	    }
	

	// Your Codec object will be instantiated and called as such:
	// Codec codec = new Codec();
	// codec.decode(codec.encode(url));

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Codec codec = new Codec();
		System.out.println(codec.encode("manjunathmanikumar.com"));

	}

}
