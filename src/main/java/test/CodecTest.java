package com.hashfunction.main;

import static org.junit.Assert.*;

import org.junit.Test;

public class CodecTest {

	@Test
	public void testEncode() {
		String s = "324";
		String testcase;
		Codec codec = new Codec();
		testcase = codec.encode("manjunath.manikumar");
		assertEquals(testcase, s);
//		fail("manjunath.manikumar");
	}

//	@Test
//	public void testDecode() {
//		Codec dec = new Codec();
//		dec.decode("324");
//		fail("324");
//	}

	
}
