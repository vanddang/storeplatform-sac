/**
 *
 */
package com.skplanet.storeplatform.sac.runtime.extend;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;

@RunWith(MockitoJUnitRunner.class)
public class SacServiceUrlSearcherTest {

	@InjectMocks
	private SacServiceUrlSearcher searcher;

	@Mock
	private AclDataAccessService service;

	private final String innerServletHost = "http://localhost";
	private final String innerServletPath = "/internal";
	private final String externalBaseUrl = "http://localhost:8210";

	@Before
	public void before() {
		this.searcher.setInnerServletHost(this.innerServletHost);
		this.searcher.setInnerServletPath(this.innerServletPath);
		this.searcher.setExternalBaseUrl(this.externalBaseUrl);
		this.searcher.init();

		Properties properties = new Properties();
		properties.setProperty("/other/search/v1", "/nate/search");

		this.searcher.setProperties(properties);
	}

	@Test
	public void testSearchForInternal() {
		String interfaceId = "I99000001";
		Interface intf = new Interface(interfaceId);
		intf.setBypassYn("N");
		when(this.service.selectInterfaceById(interfaceId)).thenReturn(intf);

		String contextPath = "";
	    String requestURI = "/example/sample/detail";
	    String queryString = "no=1";
	    this.setRequestContextHolder(contextPath, requestURI, queryString);

	    String expectedUrl = String.format("%s:8010%s%s%s?%s", this.innerServletHost, this.innerServletPath, contextPath, requestURI, queryString);
	    System.out.println("# expectedUrl : " + expectedUrl);

		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put(CommonConstants.HEADER_INTERFACE_ID, interfaceId);
		String actualUrl = this.searcher.search(headerMap);
		System.out.println("# actualUrl : " + actualUrl);

		assertEquals(expectedUrl, actualUrl);
	}

	@Test
	public void testSearchForBypass() {
		String interfaceId = "I04000001";
		Interface intf = new Interface(interfaceId);
		intf.setBypassYn("Y");
		when(this.service.selectInterfaceById(interfaceId)).thenReturn(intf);

	    String contextPath = "";
	    String requestURI = "/other/search/v1";
	    String queryString = "q=skplanet";
	    this.setRequestContextHolder(contextPath, requestURI, queryString);

	    String expectedUrl = this.externalBaseUrl + "/nate/search?q=skplanet";
	    System.out.println("# expectedUrl : " + expectedUrl);

		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put(CommonConstants.HEADER_INTERFACE_ID, interfaceId);
		String serviceUrl = this.searcher.search(headerMap);
		System.out.println("# actualUrl : " + serviceUrl);

		assertEquals(expectedUrl, serviceUrl);
	}

	private void setRequestContextHolder(String contextPath, String requestURI, String queryString) {
	    MockHttpServletRequest request = new MockHttpServletRequest();
	    request.setContextPath(contextPath);
	    request.setRequestURI(requestURI);
	    request.setQueryString(queryString);
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}


}
