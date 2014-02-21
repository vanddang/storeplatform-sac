package com.skplanet.storeplatform.sac.runtime.acl.service.validation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.util.AclUtils;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidateServiceImplTest {

	@InjectMocks
	private ValidateServiceImpl validatService;

	@Mock
	private AclDataAccessService dbAccessMock;

	@Test
	public void testValidateHeaders() {
		HttpHeaders headers = new HttpHeaders();

		try {
			this.validatService.validateHeaders(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0001", e.getErrorInfo().getCode());
		}

		headers.setAuthKey("kasdlkjfsladk;jfskl;adjfk;lasfj");

		try {
			this.validatService.validateHeaders(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0001", e.getErrorInfo().getCode());
		}

		headers.setSystemId("S01-01002");
		headers.setInterfaceId("I01000001");

		try {
			this.validatService.validateHeaders(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0001", e.getErrorInfo().getCode());
		}

		headers.setGuid("asdfsadsadfsadfsadfwaef");

		this.validatService.validateHeaders(headers);
	}

	@Test(expected=StorePlatformException.class)
	public void testValidateTimestampForException() throws InterruptedException {

		HttpHeaders headers = new HttpHeaders();
		headers.setTimestamp(AclUtils.getTimestamp() + "");
		this.validatService.validateTimestamp(headers); // Success

		Thread.sleep(11000); // Wait 11 sec (Timeout = 10 sec)
		try {
			this.validatService.validateTimestamp(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0002", e.getErrorInfo().getCode());
			throw e;
		}
	}

}
