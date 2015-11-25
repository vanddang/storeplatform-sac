package com.skplanet.storeplatform.sac.runtime.component;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.runtime.common.service.RoutingDataService;
import com.skplanet.storeplatform.sac.runtime.common.vo.Component;

@RunWith(MockitoJUnitRunner.class)
public class ComponentBaseUrlProviderImplTest {

	@InjectMocks
	private ComponentBaseUrlProviderImpl provider;
	
	@Mock
	private RoutingDataService dataSvc;
	
	@Before
	public void before() {
		SacRequestHeader sacRequestHeader = new SacRequestHeader();
		TenantHeader tenant = new TenantHeader();
		tenant.setTenantId("S02");
		sacRequestHeader.setTenantHeader(tenant);

		MockHttpServletRequest request = new MockHttpServletRequest();
		ServletRequestAttributes attributes = new ServletRequestAttributes(request);
		attributes.setAttribute(SacRequestHeader.class.getName(), sacRequestHeader, RequestAttributes.SCOPE_REQUEST);
		
		RequestContextHolder.setRequestAttributes(attributes);
	}
	
	@Test
	public void test() {
		Component mockComp = new Component();
		mockComp.setScheme("http");
		mockComp.setHost("qa-ec-ustore.sungsu.skplanet.com");
		mockComp.setPort(80);
		when(dataSvc.selectComponent("005", "S02")).thenReturn(mockComp);
		
		String baseUrl = provider.provideBaseUrl();
		System.out.printf("# baseUrl : %s", baseUrl);
		assertEquals("http://qa-ec-ustore.sungsu.skplanet.com:80", baseUrl);
	}

}
