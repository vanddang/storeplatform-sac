package com.skplanet.storeplatform.sac.runtime.acl.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.service.validation.RequestValidateService;
import com.skplanet.storeplatform.sac.runtime.acl.util.AclUtils;

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
		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put(CommonConstants.HEADER_INTERFACE_ID, "I01000001");
		headerMap.put(CommonConstants.HEADER_HTTP_REQUEST_URL, "/member/user/createByMdn/v1");
		headerMap.put(CommonConstants.HEADER_AUTH_TIMESTAMP, AclUtils.getTimestamp() + "");

		doThrow(new StorePlatformException("SAC_CMN_001")).when(this.requestValidateMock).validateInterface(headerMap);
		try {
			this.aclService.validate(headerMap);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_001", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.requestValidateMock).validateInterface(headerMap);
		}
	}

}
