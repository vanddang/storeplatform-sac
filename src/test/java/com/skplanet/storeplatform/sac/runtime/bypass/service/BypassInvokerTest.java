package com.skplanet.storeplatform.sac.runtime.bypass.service;

import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.web.bind.HttpOutputGatewayResponseErrorHandler;
import com.skplanet.storeplatform.framework.web.client.BypassStoreplatformRestTemplate;

public class BypassInvokerTest {

	private static BypassInvoker invoker;
	
	@BeforeClass
	public static void beforeClass() {
		BypassStoreplatformRestTemplate restTemplate = new BypassStoreplatformRestTemplate();
		restTemplate.setComponentId("SAC");
		restTemplate.setErrorHandler(new HttpOutputGatewayResponseErrorHandler());
		
		invoker = new BypassInvoker();
		invoker.setRestTemplate(restTemplate);
	}
	
	@Test
	public void testInvoke() throws URISyntaxException {
		UriComponents uriCompnents = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("qa-ec-store.sungsu.skplanet.com")
				.path("/example/sample/detail")
				.query("no=1&q=aaa%2Bbbb")
				.build();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Accept", "*/*");
		requestHeaders.set("Content-Type", "application/json;charset=UTF-8");

		ResponseEntity<String> response = invoker.invoke(uriCompnents.toUri(), HttpMethod.GET, requestHeaders, null, new MockHttpServletResponse());
		System.out.println("# response : " + response);
		String body = response.getBody();
		assertTrue(StringUtils.contains(body, "Sample 1"));
	}

}
