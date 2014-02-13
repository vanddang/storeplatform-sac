package com.skplanet.storeplatform.sac.common.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public class SacRequestHeaderHolder {

	public final static SacRequestHeader getValue() {
		return (SacRequestHeader) RequestContextHolder.getRequestAttributes().getAttribute(
				SacRequestHeader.class.getName(), RequestAttributes.SCOPE_REQUEST);
	}

}
