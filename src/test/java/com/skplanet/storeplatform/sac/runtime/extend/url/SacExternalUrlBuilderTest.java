package com.skplanet.storeplatform.sac.runtime.extend.url;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.sac.runtime.common.vo.Bypass;
import com.skplanet.storeplatform.sac.runtime.common.vo.Component;

public class SacExternalUrlBuilderTest {

	@Test
	public void testBuildLocal() {
		Bypass bypass = new Bypass("0050001");
		bypass.setPath("/nate/search");

		Component component = new Component("005");
		component.setScheme("http");
		component.setHost("localhost");
		component.setPort(8210);
		bypass.setComponent(component);

		UriComponentsBuilder builder = SacExternalUrlBuilder.buildUrl(bypass);
		String actual = builder.build().toString();
		System.out.println("# testBuildLocal() :" + actual);
		String expected = "http://localhost:8210/nate/search";
		assertEquals(expected, actual);
	}

	@Test
	public void testBuildProd() {
		Bypass bypass = new Bypass("0050001");
		bypass.setPath("/nate/search");

		Component component = new Component("005");
		component.setScheme("http");
		component.setHost("ec.store.sungsu.skplanet.com");
		component.setPort(80);
		bypass.setComponent(component);

		UriComponentsBuilder builder = SacExternalUrlBuilder.buildUrl(bypass);
		String actual = builder.build().toString();
		System.out.println("# testBuildProd() :" + actual);
		String expected = "http://ec.store.sungsu.skplanet.com:80/nate/search";
		assertEquals(expected, actual);
	}

}
