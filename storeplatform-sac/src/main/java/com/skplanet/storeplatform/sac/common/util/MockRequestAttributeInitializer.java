package com.skplanet.storeplatform.sac.common.util;

import javax.servlet.ServletRequestEvent;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.request.RequestContextListener;

public class MockRequestAttributeInitializer {

	public final static void init(String key, Object object) {
		RequestContextListener listener = new RequestContextListener();
		MockServletContext context = new MockServletContext();
		MockHttpServletRequest request = new MockHttpServletRequest(context);
		listener.requestInitialized(new ServletRequestEvent(context, request));
		request.setAttribute(key, object);
	}

}
