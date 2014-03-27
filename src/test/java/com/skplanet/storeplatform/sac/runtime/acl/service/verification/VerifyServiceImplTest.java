package com.skplanet.storeplatform.sac.runtime.acl.service.verification;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

public class VerifyServiceImplTest {

	private static final long timestampWindow = 5 * 60; // 5 mins

	private VerifyServiceImpl validateService;

	@Before
	public void setUp() {
		this.validateService = new VerifyServiceImpl();
	}

	@Test
	public void testValidateHeaders() {
		HttpHeaders headers = new HttpHeaders();

		try {
			this.validateService.verifyHeaders(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0001", e.getErrorInfo().getCode());
		}

		headers.setAuthKey("kasdlkjfsladk;jfskl;adjfk;lasfj");

		try {
			this.validateService.verifyHeaders(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0001", e.getErrorInfo().getCode());
		}

		headers.setSystemId("S01-01002");
		headers.setInterfaceId("I01000001");

		try {
			this.validateService.verifyHeaders(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0001", e.getErrorInfo().getCode());
		}

		headers.setGuid("asdfsadsadfsadfsadfwaef");

		this.validateService.verifyHeaders(headers);
	}

	@Test
	public void testValidateTimestampForException() throws InterruptedException {
		HttpHeaders headers = new HttpHeaders();
		long timestamp = this.getTimestamp();
		System.out.println("# Timestamp : " + timestamp);
		long validTimestamp = timestamp - timestampWindow + 10;

		headers.setTimestamp(String.valueOf(validTimestamp));
		this.validateService.verifyTimestamp(headers); // Success

		long invalidTimestamp =  timestamp - timestampWindow - 10; // Exceeds the timeout.
		headers.setTimestamp(String.valueOf(invalidTimestamp));
		try {
			this.validateService.verifyTimestamp(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0002", e.getErrorInfo().getCode());
		}
	}

	private long getTimestamp() {
		return Math.round(System.currentTimeMillis() / 1000.0);
	}



}