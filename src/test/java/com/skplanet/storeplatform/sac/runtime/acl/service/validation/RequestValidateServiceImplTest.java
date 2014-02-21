package com.skplanet.storeplatform.sac.runtime.acl.service.validation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.util.AclUtils;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

public class RequestValidateServiceImplTest {

	private ValidateServiceImpl validateService;

	@Before
	public void setUp() {
		this.validateService = new ValidateServiceImpl();
	}

	@Test
	public void testValidateHeaders() {
		HttpHeaders headers = new HttpHeaders();

		try {
			this.validateService.validateHeaders(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0001", e.getErrorInfo().getCode());
		}

		headers.setAuthKey("kasdlkjfsladk;jfskl;adjfk;lasfj");

		try {
			this.validateService.validateHeaders(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0001", e.getErrorInfo().getCode());
		}

		headers.setSystemId("S01-01002");
		headers.setInterfaceId("I01000001");

		try {
			this.validateService.validateHeaders(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0001", e.getErrorInfo().getCode());
		}

		headers.setGuid("asdfsadsadfsadfsadfwaef");

		this.validateService.validateHeaders(headers);
	}

	@Test(expected=StorePlatformException.class)
	public void testValidateTimestampForException() throws InterruptedException {

		HttpHeaders headers = new HttpHeaders();
		headers.setTimestamp(AclUtils.getTimestamp() + "");
		this.validateService.validateTimestamp(headers); // Success

		Thread.sleep(11000); // Wait 11 sec (Timeout = 10 sec)
		try {
			this.validateService.validateTimestamp(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0002", e.getErrorInfo().getCode());
			throw e;
		}
	}

}
