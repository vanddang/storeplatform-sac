package com.skplanet.storeplatform.sac.runtime.bypass.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;

@RunWith(MockitoJUnitRunner.class)
public class BypassServiceImplTest {

	@InjectMocks
	BypassServiceImpl srvc;
	
	@Mock
	AclDataAccessService aclDataService;
	
	@Mock
	BypassUriBuilder uriBuilder;
	
	@Mock
	BypassInvoker bypassInvoker;
	
	@Test
	public void isBypass() {
		String interfaceId = "I99000001";
		Interface intf = new Interface();

		when(aclDataService.selectInterfaceById(interfaceId)).thenReturn(intf);
		
		intf.setBypassYn("Y");
		assertTrue(srvc.isBypass(interfaceId));
		
		intf.setBypassYn("N");
		assertFalse(srvc.isBypass(interfaceId));
		
		verify(aclDataService, times(2)).selectInterfaceById(interfaceId);
	}
	
	@Test
	public void bypass() throws URISyntaxException {
		String interfaceId = "I99000006";
		String tenantId = "S01";
		String method = "GET";
		String queryString = "no=1";
		String requestBody = null;

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set(CommonConstants.HEADER_INTERFACE_ID, interfaceId);
		requestHeaders.set(CommonConstants.HEADER_TENANT_ID, tenantId);
		
		UriComponents uriCompnents = 
				UriComponentsBuilder.fromUriString("http://qa-ec-store.sungsu.skplanet.com/example/sample/detail")
				.query(queryString)
				.build();
		URI uri = uriCompnents.toUri();
		
		when(uriBuilder.build(interfaceId, tenantId, queryString)).thenReturn(uri);

		HttpMethod httpMethod = HttpMethod.valueOf(method);
		HttpServletResponse response = new MockHttpServletResponse();
		when(bypassInvoker.invoke(uri, httpMethod, requestHeaders, requestBody, response)).thenReturn(mockResponse());
		
		ResponseEntity<String> responseEntity = srvc.bypass(method, queryString, requestHeaders, requestBody, response);
		System.out.println("# response : " + responseEntity);
		
		verify(uriBuilder).build(interfaceId, tenantId, queryString);
		verify(bypassInvoker).invoke(uri, httpMethod, requestHeaders, requestBody, response);
	}
	
	private ResponseEntity<String> mockResponse() {
		String body = "{\"no\" : 1, \"id\" : \"#1\", \"name\" : \"Sample 1\", \"description\" : \"Test Data\", \"date\" : 1437543980311}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json;charset=UTF-8");
		headers.set("x-sac-guid", "1437544892748-669b9314-c1a9-43cf-820d-dce8332c8b82");
		headers.set("x-sac-result-code", "SUCC");
		
		return new ResponseEntity<String>(body, headers, HttpStatus.OK);
	}

}
