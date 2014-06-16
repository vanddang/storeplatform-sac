package com.skplanet.storeplatform.sac.runtime.extend.url;

import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.sac.runtime.common.vo.Bypass;
import com.skplanet.storeplatform.sac.runtime.common.vo.Component;

public class SacExternalUrlBuilderUtils {

	public static UriComponentsBuilder buildUrl(Bypass bypass) {
		Component component = bypass.getComponent();
		String scheme = component.getProtocolNm();
		String host = component.getDomain();
		int port = component.getPort();
		String path = bypass.getPath();

		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
		builder.scheme(scheme).host(host).port(port).path(path);
		return builder;
	}

}
