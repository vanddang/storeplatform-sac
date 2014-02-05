package com.skplanet.storeplatform.sac.runtime.acl.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AclUtilsTest {

	@Test
	public void testIsTimeOut() throws Exception {
		String timestamp;
		// Past (1234567890 -> 2009-02-14 08:31:30)
		timestamp = "1234567890";
		assertFalse(AclUtils.isTimeOut(timestamp));
		// Future (1577836800 -> 2020-01-01 00:00:00)
		timestamp = "1577836800";
		assertFalse(AclUtils.isTimeOut(timestamp));

		// Current Time
		timestamp = System.currentTimeMillis() / 1000 + "";
		assertTrue(AclUtils.isTimeOut(timestamp));
		// Wait 5 secs
		Thread.sleep(5000);
		assertTrue(AclUtils.isTimeOut(timestamp));
		// Wait 5 more secs (10 sec)
		Thread.sleep(5000);
		assertTrue(AclUtils.isTimeOut(timestamp));
		// Wait 5 more secs (15 sec)
		Thread.sleep(5000);
		assertFalse(AclUtils.isTimeOut(timestamp));
	}

}
