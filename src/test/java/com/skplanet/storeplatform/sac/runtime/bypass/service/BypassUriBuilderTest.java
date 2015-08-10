package com.skplanet.storeplatform.sac.runtime.bypass.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.sac.runtime.extend.url.SacExternalUrlService;

@RunWith(MockitoJUnitRunner.class)
public class BypassUriBuilderTest {

	@InjectMocks
	BypassUriBuilder bypassUriBuilder;
	
	@Mock
	SacExternalUrlService urlSrvc;
	
	@Test
	public void test() {
		String interfaceId = "I04000001";
		String tenantId = "S01";
		String bypassUrl = "http://localhost:8210/nate/search";
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(bypassUrl);

		when(urlSrvc.buildUrl(null, interfaceId, tenantId)).thenReturn(uriComponentsBuilder);

		String queryString = "category=game&query=tstore";
		URI uri = bypassUriBuilder.build(interfaceId, tenantId, queryString);
		
		String expected = bypassUrl + "?" + queryString;
		String actual = uri.toString();
		assertEquals(expected, actual);
		
		verify(urlSrvc).buildUrl(null, interfaceId, tenantId);
	}

}
