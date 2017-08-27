package com.hashfunction.main;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

public class Codec {
		    
	    HashMap<String,String> hashmap = new HashMap<>();
	    int encNumber;
	    StringBuilder url_string = new StringBuilder();
	    String url_hex;
	    // Encodes a URL to a shortened URL.
	    public String encode(String longUrl) {
	        char aRR[] = longUrl.toCharArray();
	        for(int i=0;i<aRR.length;i++){
	            encNumber = (int)aRR[i];
	            //	System.out.println(encNumber);
	            url_string.append(Integer.toString(encNumber));
	           // System.out.println(url_string.toString());
	                      }
	       byte[] myBytes = new byte[128];
		try {
			myBytes = url_string.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	       url_hex = DatatypeConverter.printHexBinary(myBytes);
	       char [] url_hex_array = url_hex.toCharArray();
	       long sum =0;
	       for (int j=0;j<url_hex_array.length;j++)
	       {
	    	   sum = sum + Character.getNumericValue(url_hex_array[j]);
	       }
	       
	       
	        hashmap.put(Long.toString(sum),longUrl);
	        	        return Long.toString(sum);
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
		System.out.println(codec.encode("manjunath.manikumar"));

	}

}
