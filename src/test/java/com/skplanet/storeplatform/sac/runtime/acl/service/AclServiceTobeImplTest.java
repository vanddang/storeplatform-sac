package com.skplanet.storeplatform.sac.runtime.acl.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.service.validation.RequestValidateService;
import com.skplanet.storeplatform.sac.runtime.acl.util.AclUtils;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

@RunWith(MockitoJUnitRunner.class)
public class AclServiceTobeImplTest {

	@InjectMocks
	private AclServiceTobeImpl aclService;

	@Mock
	private RequestValidateService requestValidateMock;

	@Before
	public void setUp() throws Exception {
	}

	@Test(expected=StorePlatformException.class)
	public void test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setRequestUrl("/member/user/createByMdn/v1");
		headers.setTimestamp(AclUtils.getTimestamp() + "");

		doThrow(new StorePlatformException("SAC_CMN_001")).when(this.requestValidateMock).validateInterface(headers);
		try {
			this.aclService.validate(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_001", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.requestValidateMock).validateInterface(headers);
		}
	}

}
