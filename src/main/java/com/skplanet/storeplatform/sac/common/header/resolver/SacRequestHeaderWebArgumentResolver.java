package com.skplanet.storeplatform.sac.common.header.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * SAC Custom Header 값들을 Spring Controller에서 쓰기 위한 WebArgumentResolver 구현체 (임시적으로 상위 규격서의 샘플 값들을 넣어놓았으며, 추후 ACL Flow가
 * 완성되면 보완한다.)
 * 
 * Updated on : 2014. 1. 15. Updated by : 서대영, SK 플래닛.
 */
public class SacRequestHeaderWebArgumentResolver implements WebArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		if (methodParameter.getParameterType().equals(SacRequestHeader.class)) {
			return RequestContextHolder.getRequestAttributes().getAttribute(SacRequestHeader.class.getName(),
					RequestAttributes.SCOPE_REQUEST);
		}

		return UNRESOLVED;
	}

}
