package com.skplanet.storeplatform.sac.runtime.acl.service.authentication;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKey;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthType;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import com.skplanet.storeplatform.sac.runtime.acl.vo.TenantStatus;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceImplTest {

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

		this.authenticationService.authenticate(headers);

		verify(this.dbAccessMock).selectTenantByAuthKey(authKey);
	}

	@Test
	public void authenticate_Tenant_Avaliable_AuthType_MAC() {
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		AuthKey obj = new AuthKey();
		obj.setTenantId("S01");
		obj.setTenantStatusCd(TenantStatus.AVAILABLE.getCode());
		obj.setAuthTypeCd(AuthType.KEY.name());

		when(this.dbAccessMock.selectAuthKey(authKey)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setAuthKey(authKey);
		headers.setRequestUrl("/member/user/createByMdn/v1");

		this.authenticationService.authenticate(headers);

		verify(this.dbAccessMock).selectTenantByAuthKey(authKey);
	}
}
