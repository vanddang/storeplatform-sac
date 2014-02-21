package com.skplanet.storeplatform.sac.runtime.acl.service.authorization;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.acl.vo.InterfaceStatus;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizeServiceImplTest {

	@InjectMocks
	private AuthorizeServiceImpl authorizeService;

	@Mock
	private AclDataAccessService dbAccessMock;

	@Test
	public void testCheckInterface() {
		String id = "I01000001";
		Interface obj = new Interface();
		obj.setInterfaceId(id);
		obj.setUrl("/member/user/createByMdn/v1");
		obj.setStatus(InterfaceStatus.AVAILABLE);

		when(this.dbAccessMock.selectInterfaceById(id)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setRequestUrl("/member/user/createByMdn/v1");

		this.authorizeService.checkInterface(headers);

		verify(this.dbAccessMock).selectInterfaceById(id);
	}

	@Test(expected=StorePlatformException.class)
	public void testCheckInterfaceForNonexistentInterfaceID() {
		String id = "I01000002";
		when(this.dbAccessMock.selectInterfaceById(id)).thenReturn(null);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000002");
		headers.setRequestUrl("/member/user/createByAgreement/v1");

		try {
			this.authorizeService.checkInterface(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0061", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.dbAccessMock).selectInterfaceById(id);
		}
	}

	@Test(expected=StorePlatformException.class)
	public void testCheckInterfaceForUnavailableInterface() {
		String id = "I01000001";
		Interface obj = new Interface();
		obj.setInterfaceId(id);
		obj.setUrl("/member/user/createByMdn/v1");
		obj.setStatus(InterfaceStatus.UNAVAILABLE);

		when(this.dbAccessMock.selectInterfaceById(id)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		obj.setUrl("/member/user/createByMdn/v1");

		try {
			this.authorizeService.checkInterface(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0062", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.dbAccessMock).selectInterfaceById(id);
		}
	}

	@Test(expected=StorePlatformException.class)
	public void testCheckInterfaceForWrongUrl() {
		String id = "I01000001";
		Interface obj = new Interface();
		obj.setInterfaceId(id);
		obj.setUrl("/member/user/createByMdn/v1");
		obj.setStatus(InterfaceStatus.AVAILABLE);

		when(this.dbAccessMock.selectInterfaceById(id)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setRequestUrl("/abcde");

		try {
			this.authorizeService.checkInterface(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0063", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.dbAccessMock).selectInterfaceById(id);
		}
	}

	@Test
	public void testCheckMapping() {
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		String interfaceId = "I01000001";

		when(this.dbAccessMock.selectUsableInterface(authKey, interfaceId)).thenReturn(interfaceId);

		HttpHeaders headers = new HttpHeaders();
		headers.setAuthKey(authKey);
		headers.setInterfaceId(interfaceId);

		this.authorizeService.checkMapping(headers);

		verify(this.dbAccessMock).selectUsableInterface(authKey, interfaceId);
	}

	@Test(expected=StorePlatformException.class)
	public void testCheckMappingWithoutAuthorization() {
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		String interfaceId = "I01000001";

		when(this.dbAccessMock.selectUsableInterface(authKey, interfaceId)).thenReturn(null);

		HttpHeaders headers = new HttpHeaders();
		headers.setAuthKey(authKey);
		headers.setInterfaceId(interfaceId);

		try {
			this.authorizeService.checkMapping(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0064", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.dbAccessMock).selectUsableInterface(authKey, interfaceId);
		}

	}

}
