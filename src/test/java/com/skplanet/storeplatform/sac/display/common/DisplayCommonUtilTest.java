package com.skplanet.storeplatform.sac.display.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class DisplayCommonUtilTest {
	
	@Test
	public void getMimeType() {
		String src = "xxx.jpg";
		String mimeType = DisplayCommonUtil.getMimeType(src);
		System.out.println("mimeType="+mimeType);
		assertEquals("image/jpeg", mimeType);
		
		src = "xxx.mp4";
		mimeType = DisplayCommonUtil.getMimeType(src);
		System.out.println("mimeType="+mimeType);
		assertEquals("video/mp4", mimeType);
	}
	@Test
	public void getOsVer() {
		String os = "Android/4.0.4";
		//String result = "4.0";
		String osVer = DisplayCommonUtil.getOsVer(os);
		System.out.println("os="+os+", osVer="+osVer);
		assertEquals(osVer, "4.0");
	}
	
	@Test
	public void getOsVer_nullOrEmptyCheck() {
		String os = "";
		String osVer = DisplayCommonUtil.getOsVer(os);
		System.out.println("os="+os+", osVer="+osVer);
		assertEquals(osVer, osVer);
		
		os = null;
		osVer = DisplayCommonUtil.getOsVer(os);
		System.out.println("os="+os+", osVer="+osVer);
		assertEquals(osVer, os);
	}

	@Test
	public void getOsVer_2() {
		String os = "Android/";
		//String result = "4.0";
		String osVer = DisplayCommonUtil.getOsVer(os);
		assertEquals(osVer, os);
	}
}
