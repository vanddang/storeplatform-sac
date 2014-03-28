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
	
}
