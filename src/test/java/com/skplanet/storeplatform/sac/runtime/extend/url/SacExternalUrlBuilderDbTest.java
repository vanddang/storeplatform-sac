package com.skplanet.storeplatform.sac.runtime.extend.url;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.common.service.RoutingDataService;
import com.skplanet.storeplatform.sac.runtime.common.vo.Bypass;
import com.skplanet.storeplatform.sac.runtime.common.vo.Component;

@RunWith(MockitoJUnitRunner.class)
public class SacExternalUrlBuilderDbTest {

	@InjectMocks
	private SacExternalUrlBuilderDb builder;

	@Mock
	private RoutingDataService dataSvc;

	@Test
	public void testBuildUrl() {
		Interface intf = new Interface("I04000001");

		Bypass bypass = new Bypass("0050001");
		bypass.setPath("/nate/search");
		Component component = new Component("005");
		component.setProtocolNm("http");
		component.setDomain("localhost");
		component.setPort(8210);
		bypass.setComponent(component);

		when(this.dataSvc.selectBypassByInterface(intf)).thenReturn(bypass);

		UriComponentsBuilder bud = this.builder.buildUrl(null, "I04000001");
		String actual = bud.build().toString();
		String expected = "http://localhost:8210/nate/search";
		assertEquals(expected, actual);

		verify(this.dataSvc).selectBypassByInterface(intf);
	}

}
