package com.skplanet.storeplatform.sac.runtime.acl.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.service.authentication.AuthenticateService;
import com.skplanet.storeplatform.sac.runtime.acl.service.authorization.AuthorizeService;
import com.skplanet.storeplatform.sac.runtime.acl.service.verification.VerifyService;
import com.skplanet.storeplatform.sac.runtime.acl.util.AclUtils;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;

@RunWith(MockitoJUnitRunner.class)
public class AclServiceTobeImplTest {

	@InjectMocks
	private AclService aclService;

	@Mock
	private VerifyService verifyMock;

	@Mock
	private AuthenticateService authenticateMock;

	@Mock
	private AuthorizeService authorizeMock;

	@Before
	public void setUp() throws Exception {
	}

	@Test(expected=StorePlatformException.class)
	public void testValidate() {
		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setRequestUrl("/member/user/createByMdn/v1");
		headers.setTimestamp(AclUtils.getTimestamp() + "");

		doThrow(new StorePlatformException("SAC_CMN_0001")).when(this.verifyMock).verifyHeaders(headers);
		try {
			this.aclService.validate(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0001", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.verifyMock).verifyHeaders(headers);
		}
	}

	@Test(expected=StorePlatformException.class)
	public void testAuthenticate() {
		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("IS01000001");
		headers.setAuthKey("S01c61c0cad59adc6e03069e71614a5a0a3");
		headers.setTimestamp("1392659261");
		headers.setNonce("1392659261");
		headers.setSignature("");

		doThrow(new StorePlatformException("SAC_CMN_0036")).when(this.authenticateMock).authenticate(headers);
		try {
			this.aclService.authenticate(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0036", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.authenticateMock).authenticate(headers);
		}
	}


	@Test(expected=StorePlatformException.class)
	public void testAuthorize() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAuthKey("S01c61c0cad59adc6e03069e71614a5a0a3");
		headers.setInterfaceId("IS01000001");
		headers.setRequestUrl("/member/user/createByMdn/v1");

		doNothing().when(this.authorizeMock).checkInterface(headers);
		doThrow(new StorePlatformException("SAC_CMN_0064")).when(this.authorizeMock).checkMapping(headers);
		try {
			this.aclService.authorize(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0064", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.authorizeMock).checkInterface(headers);
			verify(this.authorizeMock).checkMapping(headers);
		}
	}

}
