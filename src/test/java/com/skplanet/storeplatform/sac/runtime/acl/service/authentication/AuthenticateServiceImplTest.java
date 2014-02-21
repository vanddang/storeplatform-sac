package com.skplanet.storeplatform.sac.runtime.acl.service.authentication;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKey;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKeyStatus;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthType;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class AuthenticateServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticateServiceImplTest.class);

    @Autowired
    private AclDataAccessService dbAccessService;

	@InjectMocks
	private AuthenticateServiceImpl authenticateService;

	@Mock
	private AclDataAccessService dbAccessMock;

    /**
     * AuthKey 조회 테스트
     */
    @Test
    public void authenticate_1_selectAuthKey() {
        String pAuthKey = "25f9aabf90acf38aa2e6d0da49e9eee75";

        AuthKey authKey = dbAccessService.selectAuthKey(pAuthKey);
        logger.debug("authKey={}", authKey);
    }

	@Test
	public void authenticate_1_1_AuthKey_NotExist() {
		String authKey = "NotExistAuthKey";
		AuthKey obj = new AuthKey();
		when(this.dbAccessMock.selectAuthKey(authKey)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setAuthKey(authKey);
		headers.setRequestUrl("/member/user/createByMdn/v1");

		String errorCode = "";
		try {
			this.authenticateService.authenticate(headers);
		} catch(StorePlatformException e) {
			errorCode = e.getErrorInfo().getCode();
		}
		assertEquals("SAC_CMN_0031", errorCode);
	}

    @Test
    public void authenticate_1_2_AuthKey_사용불가_상태() {
        String authKey = "UNAVAILABLE";

        AuthKey obj = new AuthKey();
		obj.setTenantId("S01");
		obj.setStatus(AuthKeyStatus.UNAVAILABLE);

        when(this.dbAccessMock.selectAuthKey(authKey)).thenReturn(obj);

        HttpHeaders headers = new HttpHeaders();
        headers.setInterfaceId("I01000001");
        headers.setAuthKey(authKey);
        headers.setRequestUrl("/member/user/createByMdn/v1");

        String errorCode = "";
        try {
            this.authenticateService.authenticate(headers);
        } catch(StorePlatformException e) {
            errorCode = e.getErrorInfo().getCode();
        }
        assertEquals("SAC_CMN_0032", errorCode);
    }

    @Test
    public void authenticate_1_3_AuthKey_사용기한_만료() {
        String authKey = "UNAVAILABLE";

        AuthKey obj = new AuthKey();
        obj.setTenantId("S01");
        obj.setStatus(AuthKeyStatus.UNAVAILABLE);

        when(this.dbAccessMock.selectAuthKey(authKey)).thenReturn(obj);

        HttpHeaders headers = new HttpHeaders();
        headers.setInterfaceId("I01000001");
        headers.setAuthKey(authKey);
        headers.setRequestUrl("/member/user/createByMdn/v1");

        String errorCode = "";
        try {
            this.authenticateService.authenticate(headers);
        } catch(StorePlatformException e) {
            errorCode = e.getErrorInfo().getCode();
        }
        assertEquals("SAC_CMN_0032", errorCode);
    }

	@Test
	public void authenticate_Tenant_MAC인증_성공() {
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		String secret = "25f9aabf90acf38aa2e6d0da49e9eee75";

		String requestUri = "/member/user/createByMdn/v1";

		AuthKey obj = new AuthKey();
		obj.setTenantId("S01");
		obj.setStatus(AuthKeyStatus.AVAILABLE);
		obj.setAuthType(AuthType.MAC);
		obj.setSecret(secret);

		when(this.dbAccessMock.selectAuthKey(authKey)).thenReturn(obj);

		HttpHeaders headers = new HttpHeaders();
		headers.setInterfaceId("I01000001");
		headers.setAuthKey(authKey);
		headers.setRequestUrl(requestUri);
		headers.setTimestamp("1392659265");
		headers.setNonce("1392659265");
		headers.setSignature("ispGcmR2Ngi3XKBKjUQLKhU47AM=");

		this.authenticateService.authenticate(headers);

		//verify(this.dbAccessMock).selectAuthKey(authKey);
	}

	@Test
	public void authenticate_Tenant_Avaliable_AuthType_MAC_실패_Signature값_없음() {
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		String secret = "25f9aabf90acf38aa2e6d0da49e9eee75";

		String requestUri = "/member/user/createByMdn/v1";


		AuthKey obj = new AuthKey();
		obj.setTenantId("S01");
		obj.setAuthType(AuthType.MAC);
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
			this.authenticateService.authenticate(headers);
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
		obj.setAuthType(AuthType.MAC);
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
			this.authenticateService.authenticate(headers);
		} catch(StorePlatformException e) {
			errorCode = e.getErrorInfo().getCode();
		}
		assertEquals("SAC_CMN_0032", errorCode);

		//verify(this.dbAccessMock).selectAuthKey(authKey);
	}

}
