package com.skplanet.storeplatform.sac.runtime.bypass.service;

import static org.junit.Assert.assertEquals;
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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.web.bind.BypassResponseErrorHandler;
import com.skplanet.storeplatform.framework.web.client.BypassStoreplatformRestTemplate;

public class BypassInvokerTest {

	private static BypassInvoker invoker;
	
	@BeforeClass
	public static void beforeClass() {
		BypassStoreplatformRestTemplate restTemplate = new BypassStoreplatformRestTemplate();
		restTemplate.setComponentId("SAC");
		restTemplate.setErrorHandler(new BypassResponseErrorHandler());
		
		invoker = new BypassInvoker();
		invoker.setRestTemplate(restTemplate);
	}
	
	@Test
	public void invokeSucessResultCode() throws URISyntaxException {
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
	
	@Test
	public void invokeFailResultCode() throws URISyntaxException {
		UriComponents uriCompnents = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("qa-ec-store.sungsu.skplanet.com")
				.path("/example/sample/error")
				.build();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Accept", "*/*");
		requestHeaders.set("Content-Type", "application/json;charset=UTF-8");

		ResponseEntity<String> response = invoker.invoke(uriCompnents.toUri(), HttpMethod.GET, requestHeaders, null, new MockHttpServletResponse());
		System.out.println("# response : " + response);
		String body = response.getBody();
		assertTrue(StringUtils.contains(body, "EC_0001"));
	}
	
	@Test(expected = StorePlatformException.class)
	public void invokeNotFound() throws URISyntaxException {
		UriComponents uriCompnents = UriComponentsBuilder.newInstance()
				.scheme("http")
				.host("qa-ec-store.sungsu.skplanet.com")
				.path("/example/sample/error1")
				.build();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Accept", "*/*");
		requestHeaders.set("Content-Type", "application/json;charset=UTF-8");

		try {
			ResponseEntity<String> response = invoker.invoke(uriCompnents.toUri(), HttpMethod.GET, requestHeaders, null, new MockHttpServletResponse());
			System.out.println("# response : " + response);
		} catch (StorePlatformException e) {
			assertEquals("SYS_ERROR_BYPASS", e.getCode());
			throw e;
		}
	}

}
