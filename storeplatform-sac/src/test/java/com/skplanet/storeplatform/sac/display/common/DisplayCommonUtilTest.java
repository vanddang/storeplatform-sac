package com.skplanet.storeplatform.sac.display.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
	public void extractOsVerTest() {
        List<String> list = Arrays.asList("Android/1.1", "1.2", "Android / 1.3", "2", "2.1", "2.2.1", "iPhone_5.! / 7.1");
        //String result = "4.0";
        for (String v : list) {
            String osVer = DisplayCommonUtil.extractOsVer(v);
            System.out.println("os=" + v + ", osVer=" + osVer);
        }
	}
	
	@Test
	public void getOsVer_nullOrEmptyCheck() {
		String os = "";
		String osVer = DisplayCommonUtil.extractOsVer(os);
		System.out.println("os="+os+", osVer="+osVer);
		assertEquals(osVer, osVer);
		
		os = null;
		osVer = DisplayCommonUtil.extractOsVer(os);
		System.out.println("os="+os+", osVer="+osVer);
		assertEquals(osVer, os);
	}

	@Test
	public void getOsVer_2() {
		String os = "Android/";
		//String result = "4.0";
		String osVer = DisplayCommonUtil.extractOsVer(os);
		assertEquals(osVer, os);
	}
}
