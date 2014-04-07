package com.skplanet.storeplatform.sac.common.header.extractor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.context.request.NativeWebRequest;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKey;

/**
 * TenantExtractorImplTest
 *
 * Updated on : 2014. 4. 7.
 * Updated by : 서대영, SK 플래닛
 */
@RunWith(MockitoJUnitRunner.class)
public class TenantExtractorImplTest {

	@InjectMocks
	private TenantExtractorImpl extractor;

	@Mock
	private AclDataAccessService dbService;

	@Mock
	private NativeWebRequest request;

	@Test
	public void testExtractFromHeaders() {
		when(this.request.getHeader(CommonConstants.HEADER_TENANT_ID)).thenReturn("S09");
		when(this.request.getHeader(CommonConstants.HEADER_SYSTEM_ID)).thenReturn("S09-00001");

		TenantHeader tenant = this.extractor.extract(this.request);
		assertEquals("S09", tenant.getTenantId());
		assertEquals("S09-00001", tenant.getSystemId());

		verify(this.request).getHeader(CommonConstants.HEADER_TENANT_ID);
		verify(this.request).getHeader(CommonConstants.HEADER_SYSTEM_ID);
	}

	@Test
	public void testExtractFromDb() {
		AuthKey authKey = new AuthKey();
		authKey.setTenantId("S03");
		when(this.dbService.selectAuthKey(anyString())).thenReturn(authKey);

		when(this.request.getHeader(CommonConstants.HEADER_AUTH_KEY)).thenReturn("S033d8874b5d768d093e28fc7d82fffc1d2");
		when(this.request.getHeader(CommonConstants.HEADER_SYSTEM_ID)).thenReturn("S09-00001");

		TenantHeader tenant = this.extractor.extract(this.request);
		assertEquals("S03", tenant.getTenantId());
		assertEquals("S09-00001", tenant.getSystemId());

		verify(this.request).getHeader(CommonConstants.HEADER_TENANT_ID);
		verify(this.request).getHeader(CommonConstants.HEADER_SYSTEM_ID);
	}

}
