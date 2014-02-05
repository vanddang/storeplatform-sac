package com.skplanet.storeplatform.sac.runtime.acl.service.validation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.util.AclUtils;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidateServiceImplTest {

	@InjectMocks
	private RequestValidateServiceImpl validatService;

	@Mock
	private InterfaceDbAccessService dbAccessMock;

	@Test
	public void testValidateInterface() {
		Interface in = new Interface("I01000001");
		Interface out = new Interface();
		out.setInterfaceId("I01000001");
		out.setUrl("/member/user/createByMdn/v1");

		when(this.dbAccessMock.select(in)).thenReturn(out);

		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put(CommonConstants.HEADER_INTERFACE_ID, "I01000001");
		headerMap.put(CommonConstants.HEADER_HTTP_REQUEST_URL, "/member/user/createByMdn/v1");
		this.validatService.validateInterface(headerMap);

		verify(this.dbAccessMock).select(in);
	}

	@Test(expected=StorePlatformException.class)
	public void testValidateInterfaceForInvalidInterfaceID() {
		Interface in = new Interface("I01000002");

		when(this.dbAccessMock.select(in)).thenReturn(null);

		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put(CommonConstants.HEADER_INTERFACE_ID, "I01000002");
		headerMap.put(CommonConstants.HEADER_HTTP_REQUEST_URL, "/member/user/createByAgreement/v1");
		try {
			this.validatService.validateInterface(headerMap);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0001", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.dbAccessMock).select(in);
		}
	}

	@Test(expected=StorePlatformException.class)
	public void testValidateInterfaceForWrongUrl() {
		Interface in = new Interface("I01000001");
		Interface out = new Interface();
		out.setInterfaceId("I01000001");
		out.setUrl("/member/user/createByMdn/v1");

		when(this.dbAccessMock.select(in)).thenReturn(out);

		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put(CommonConstants.HEADER_INTERFACE_ID, "I01000001");
		headerMap.put(CommonConstants.HEADER_HTTP_REQUEST_URL, "/abcde");
		try {
			this.validatService.validateInterface(headerMap);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0002", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.dbAccessMock).select(in);
		}
	}

	@Test
	public void testValidateTimestamp() {
		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put(CommonConstants.HEADER_AUTH_TIMESTAMP, AclUtils.getTimestamp() + "");
		this.validatService.validateTimestamp(headerMap);
	}

	@Test(expected=StorePlatformException.class)
	public void testValidateTimestampForException() throws InterruptedException {
		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put(CommonConstants.HEADER_AUTH_TIMESTAMP, AclUtils.getTimestamp() + "");
		Thread.sleep(11000); // Wait 11 sec (Timeout = 10 sec)
		try {
			this.validatService.validateTimestamp(headerMap);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0003", e.getErrorInfo().getCode());
			throw e;
		}
	}

}
