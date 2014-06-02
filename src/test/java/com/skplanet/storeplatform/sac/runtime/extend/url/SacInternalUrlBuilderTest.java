package com.skplanet.storeplatform.sac.runtime.extend.url;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SacInternalUrlBuilderTest {

	@InjectMocks
	private SacInternalUrlBuilder builder;

	private final String innerServletHost = "http://localhost";
	private final String innerServletPath = "/internal";

	@Before
	public void setUp() {
		this.builder.setInnerServletHost(this.innerServletHost);
		this.builder.setInnerServletPath(this.innerServletPath);
		this.builder.init();
	}

	@Test
	public void test() {
		String requestURI = "/example/sample/detail";
		String contextPath = "";
		String serviceUrl = this.builder.buildUrl(requestURI, contextPath).build(false).toUriString();
		System.out.println("# serviceUrl : " + serviceUrl);
		String expectedUrl = String.format("%s:8010%s%s%s", this.innerServletHost, this.innerServletPath, contextPath, requestURI);
		assertEquals(expectedUrl, serviceUrl);
	}


}
