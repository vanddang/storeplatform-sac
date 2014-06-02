/**
 *
 */
package com.skplanet.storeplatform.sac.runtime.extend;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.extend.url.SacExternalUrlBuilder;
import com.skplanet.storeplatform.sac.runtime.extend.url.SacInternalUrlBuilder;

@RunWith(MockitoJUnitRunner.class)
public class SacServiceUrlSearcherTest {

	@InjectMocks
	private SacServiceUrlSearcher searcher;

	@Mock
	private AclDataAccessService dataService;

	@Mock
	private SacExternalUrlBuilder extUrlBuilder;

	@Mock
	private SacInternalUrlBuilder intUrlBuilder;

	@Before
	public void before() {
	}

	@Test
	public void testSearchForInternal() {
		String interfaceId = "I99000001";
		Interface intf = new Interface(interfaceId);
		intf.setBypassYn("N");
		when(this.dataService.selectInterfaceById(interfaceId)).thenReturn(intf);

		String contextPath = "";
	    String requestURI = "/example/sample/detail";
	    String queryString = "no=1";
	    this.setRequestContextHolder(contextPath, requestURI, queryString);

	    UriComponentsBuilder builder = UriComponentsBuilder
	    		.fromHttpUrl("http://localhost")
	    		.port(8010)
				.path(contextPath)
				.path("/internal")
				.path(requestURI);
	    when(this.intUrlBuilder.buildUrl(requestURI, contextPath)).thenReturn(builder);

		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put(CommonConstants.HEADER_INTERFACE_ID, interfaceId);
		String actualUrl = this.searcher.search(headerMap);
		System.out.println("# actualUrl : " + actualUrl);

		assertEquals(builder.build().toUriString(), actualUrl);
	}

	@Test
	public void testSearchForBypass() {
		String interfaceId = "I04000001";
		Interface intf = new Interface(interfaceId);
		intf.setBypassYn("Y");
		when(this.dataService.selectInterfaceById(interfaceId)).thenReturn(intf);

	    String contextPath = "";
	    String requestURI = "/other/search/v1";
	    String queryString = "q=skplanet";
	    this.setRequestContextHolder(contextPath, requestURI, queryString);

	    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8210").path("/nate/search");
	    when(this.extUrlBuilder.buildUrl(requestURI)).thenReturn(builder);

		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put(CommonConstants.HEADER_INTERFACE_ID, interfaceId);
		String serviceUrl = this.searcher.search(headerMap);
		System.out.println("# serviceUrl : " + serviceUrl);

		assertEquals(builder.build().toUriString(), serviceUrl);
	}

	private void setRequestContextHolder(String contextPath, String requestURI, String queryString) {
	    MockHttpServletRequest request = new MockHttpServletRequest();
	    request.setContextPath(contextPath);
	    request.setRequestURI(requestURI);
	    request.setQueryString(queryString);
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}


}
