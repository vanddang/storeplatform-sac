package com.skplanet.storeplatform.sac.runtime.extend.url;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SacExternalUrlBuilderTest {

	@InjectMocks
	private SacExternalUrlBuilder builder;

	private final String externalBaseUrl = "http://localhost:8210";

	@Before
	public void setUp() {
		this.builder.setExternalBaseUrl(this.externalBaseUrl);

		Properties properties = new Properties();
		properties.setProperty("/other/search/v1", "/nate/search");

		this.builder.setProperties(properties);
	}

	@Test
	public void test() {
		String requestURI = "/other/search/v1";
		String serviceUrl = this.builder.buildUrl(requestURI).build(false).toUriString();
		System.out.println("# serviceUrl : " + serviceUrl);
		String expectedUrl = this.externalBaseUrl + "/nate/search";
		assertEquals(expectedUrl, serviceUrl);
	}

}
