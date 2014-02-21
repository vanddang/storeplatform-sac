package com.skplanet.storeplatform.sac.runtime.acl.service.verification;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.service.verification.VerifyServiceImpl;
import com.skplanet.storeplatform.sac.runtime.acl.util.AclUtils;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

public class RequestValidateServiceImplTest {

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

	@Test(expected=StorePlatformException.class)
	public void testValidateTimestampForException() throws InterruptedException {

		HttpHeaders headers = new HttpHeaders();
		headers.setTimestamp(AclUtils.getTimestamp() + "");
		this.validateService.verifyTimestamp(headers); // Success

		Thread.sleep(11000); // Wait 11 sec (Timeout = 10 sec)
		try {
			this.validateService.verifyTimestamp(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0002", e.getErrorInfo().getCode());
			throw e;
		}
	}

}
