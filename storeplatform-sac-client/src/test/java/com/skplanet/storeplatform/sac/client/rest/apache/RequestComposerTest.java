package com.skplanet.storeplatform.sac.client.rest.apache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;

import com.skplanet.storeplatform.sac.client.rest.constant.SacRestClientConstants;

public class RequestComposerTest {

	@Test
	public void testInjectHeaders() {
		String url = "http://dev-store.sungsu.skplanet.com/system/monitoring/request";
		HttpUriRequest request = new HttpGet(url);

		String authKey = "S00321ad36d260abe5e19278ca2595da28a";
		String secret = "478c0446290f2fb48c8342c06bc0026b";
		String tenantId = "S00"; // Non-specific
		String systemId = "S00-01001"; // EC
		String interfaceId = "I00000001"; // Basic Monitoring

		RequestComposer.injectHeaders(request, authKey, secret, tenantId, systemId, interfaceId);

		for (Header h : request.getAllHeaders()) {
			System.out.println(h);
		}
		assertEquals(authKey, request.getHeaders(SacRestClientConstants.HEADER_AUTH_KEY)[0].getValue());
		assertEquals(tenantId, request.getHeaders(SacRestClientConstants.HEADER_TENANT_ID)[0].getValue());
		assertEquals(systemId, request.getHeaders(SacRestClientConstants.HEADER_SYSTEM_ID)[0].getValue());
		assertEquals(interfaceId, request.getHeaders(SacRestClientConstants.HEADER_INTERFACE_ID)[0].getValue());

		assertTrue(StringUtils.isNotBlank(request.getHeaders(SacRestClientConstants.HEADER_GUID)[0].getValue()));
		assertTrue(StringUtils.isNotBlank(request.getHeaders(SacRestClientConstants.HEADER_AUTH_TIMESTAMP)[0].getValue()));
		assertTrue(StringUtils.isNotBlank(request.getHeaders(SacRestClientConstants.HEADER_AUTH_NONCE)[0].getValue()));
		assertTrue(StringUtils.isNotBlank(request.getHeaders(SacRestClientConstants.HEADER_AUTH_SIGNATURE)[0].getValue()));
	}

	@Test
	public void testInjectHeadersWithoutSecret() {
		String url = "http://dev-store.sungsu.skplanet.com/system/monitoring/request";
		HttpUriRequest request = new HttpGet(url);

		String authKey = "S00321ad36d260abe5e19278ca2595da28a";
		String secret = null;
		String tenantId = "S00"; // Non-specific
		String systemId = "S00-01001"; // EC
		String interfaceId = "I00000001"; // Basic Monitoring

		RequestComposer.injectHeaders(request, authKey, secret, tenantId, systemId, interfaceId);

		for (Header h : request.getAllHeaders()) {
			System.out.println(h);
		}
		assertEquals(authKey, request.getHeaders(SacRestClientConstants.HEADER_AUTH_KEY)[0].getValue());
		assertEquals(tenantId, request.getHeaders(SacRestClientConstants.HEADER_TENANT_ID)[0].getValue());
		assertEquals(systemId, request.getHeaders(SacRestClientConstants.HEADER_SYSTEM_ID)[0].getValue());
		assertEquals(interfaceId, request.getHeaders(SacRestClientConstants.HEADER_INTERFACE_ID)[0].getValue());

		assertTrue(StringUtils.isNotBlank(request.getHeaders(SacRestClientConstants.HEADER_GUID)[0].getValue()));
		assertTrue(StringUtils.isNotBlank(request.getHeaders(SacRestClientConstants.HEADER_AUTH_TIMESTAMP)[0].getValue()));
		assertTrue(StringUtils.isNotBlank(request.getHeaders(SacRestClientConstants.HEADER_AUTH_NONCE)[0].getValue()));
		assertTrue(request.getHeaders(SacRestClientConstants.HEADER_AUTH_SIGNATURE).length == 0);
	}

}
