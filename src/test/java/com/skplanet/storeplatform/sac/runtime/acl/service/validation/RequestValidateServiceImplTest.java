package com.skplanet.storeplatform.sac.runtime.acl.service.validation;

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
import com.skplanet.storeplatform.sac.runtime.acl.util.AclUtils;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.acl.vo.InterfaceStatus;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidateServiceImplTest {

	@InjectMocks
	private RequestValidateServiceImpl validatService;

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

	@Test
	public void testValidateInterface() {
		String id = "I01000001";
		Interface obj = new Interface();
		obj.setInterfaceId(id);
		obj.setUrl("/member/user/createByMdn/v1");
		obj.setStatus(InterfaceStatus.AVAILABLE);

		when(this.dbAccessMock.selectInterfaceById(id)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setRequestUrl("/member/user/createByMdn/v1");

		this.validatService.validateInterface(headers);

		verify(this.dbAccessMock).selectInterfaceById(id);
	}

	@Test(expected=StorePlatformException.class)
	public void testValidateInterfaceForNonexistentInterfaceID() {
		String id = "I01000002";
		when(this.dbAccessMock.selectInterfaceById(id)).thenReturn(null);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000002");
		headers.setRequestUrl("/member/user/createByAgreement/v1");

		try {
			this.validatService.validateInterface(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0003", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.dbAccessMock).selectInterfaceById(id);
		}
	}

	@Test(expected=StorePlatformException.class)
	public void testValidateInterfaceForWrongUrl() {
		String id = "I01000001";
		Interface obj = new Interface();
		obj.setInterfaceId(id);
		obj.setUrl("/member/user/createByMdn/v1");

		when(this.dbAccessMock.selectInterfaceById(id)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setRequestUrl("/abcde");

		try {
			this.validatService.validateInterface(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0004", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.dbAccessMock).selectInterfaceById(id);
		}
	}

	@Test(expected=StorePlatformException.class)
	public void testValidateInterfaceForUnavailableInterface() {
		String id = "I01000001";
		Interface obj = new Interface();
		obj.setInterfaceId(id);
		obj.setUrl("/member/user/createByMdn/v1");
		obj.setStatus(InterfaceStatus.UNAVAILABLE);

		when(this.dbAccessMock.selectInterfaceById(id)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setRequestUrl("/abcde");

		try {
			this.validatService.validateInterface(headers);
		} catch (StorePlatformException e) {
			assertEquals("SAC_CMN_0004", e.getErrorInfo().getCode());
			throw e;
		} finally {
			verify(this.dbAccessMock).selectInterfaceById(id);
		}
	}

}
