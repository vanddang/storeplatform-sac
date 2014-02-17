package com.skplanet.storeplatform.sac.runtime.acl.service.authentication;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKey;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthType;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import com.skplanet.storeplatform.sac.runtime.acl.vo.TenantStatus;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImplTest.class);

	@InjectMocks
	private AuthenticationServiceImpl authenticationService;

	@Mock
	private AclDataAccessService dbAccessMock;

	@Test
	public void authenticate_Tenant_Unavaliable() {
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		AuthKey obj = new AuthKey();
		obj.setTenantId("S01");
		obj.setTenantStatusCd(TenantStatus.UNAVAILABLE.getCode());
		obj.setAuthTypeCd(AuthType.KEY.name());

		when(this.dbAccessMock.selectAuthKey(authKey)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setAuthKey(authKey);
		headers.setRequestUrl("/member/user/createByMdn/v1");

		String errorCode = "";
		try {
			this.authenticationService.authenticate(headers);
		} catch(StorePlatformException e) {
			errorCode = e.getErrorInfo().getCode();
		}
		assertEquals("SAC_CMN_0031", errorCode);
	}

	@Test
	public void authenticate_Tenant_Avaliable_AuthType_MAC_성공() {
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		String secret = "25f9aabf90acf38aa2e6d0da49e9eee75";

		String requestUri = "/member/user/createByMdn/v1";


		AuthKey obj = new AuthKey();
		obj.setTenantId("S01");
		obj.setTenantStatusCd(TenantStatus.AVAILABLE.getCode());
		obj.setAuthTypeCd(AuthType.KEY.name());
		obj.setSecret(secret);

		when(this.dbAccessMock.selectAuthKey(authKey)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setAuthKey(authKey);
		headers.setRequestUrl(requestUri);
		headers.setTimestamp("1392659265");
		headers.setNonce("1392659265");
		headers.setSignature("ispGcmR2Ngi3XKBKjUQLKhU47AM=");

		this.authenticationService.authenticate(headers);

		//verify(this.dbAccessMock).selectAuthKey(authKey);
	}

	@Test
	public void authenticate_Tenant_Avaliable_AuthType_MAC_실패_Signature값_없음() {
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		String secret = "25f9aabf90acf38aa2e6d0da49e9eee75";

		String requestUri = "/member/user/createByMdn/v1";


		AuthKey obj = new AuthKey();
		obj.setTenantId("S01");
		obj.setTenantStatusCd(TenantStatus.AVAILABLE.getCode());
		obj.setAuthTypeCd(AuthType.KEY.name());
		obj.setSecret(secret);

		when(this.dbAccessMock.selectAuthKey(authKey)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setAuthKey(authKey);
		headers.setRequestUrl(requestUri);
		headers.setTimestamp("1392659261");
		headers.setNonce("1392659261");
		headers.setSignature("");


		String errorCode = "";
		try {
			this.authenticationService.authenticate(headers);
		} catch(StorePlatformException e) {
			//Signature Exception
			errorCode = e.getErrorInfo().getCode();
		}
		assertEquals("SAC_CMN_0032", errorCode);

		//verify(this.dbAccessMock).selectAuthKey(authKey);
	}
	@Test
	public void authenticate_Tenant_Avaliable_AuthType_MAC_실패1() {
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		String secret = "25f9aabf90acf38aa2e6d0da49e9eee75";

		String requestUri = "/member/user/createByMdn/v1";


		AuthKey obj = new AuthKey();
		obj.setTenantId("S01");
		obj.setTenantStatusCd(TenantStatus.AVAILABLE.getCode());
		obj.setAuthTypeCd(AuthType.KEY.name());
		obj.setSecret(secret);

		when(this.dbAccessMock.selectAuthKey(authKey)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setAuthKey(authKey);
		headers.setRequestUrl(requestUri);
		headers.setTimestamp("1392659261");
		headers.setNonce("1392659261");
		headers.setSignature("ispGcmR2Ngi3XKBKjUQLKhU47AM=");


		String errorCode = "";
		try {
			this.authenticationService.authenticate(headers);
		} catch(StorePlatformException e) {
			errorCode = e.getErrorInfo().getCode();
		}
		assertEquals("SAC_CMN_0032", errorCode);

		//verify(this.dbAccessMock).selectAuthKey(authKey);
	}
}
