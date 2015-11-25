package com.skplanet.storeplatform.sac.client.rest.apache;

import com.skplanet.storeplatform.sac.client.rest.util.SacRestFormattingUtils;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestRequest;
import com.skplanet.storeplatform.sac.client.rest.vo.SacRestScheme;

public class UrlFormatter {

	public static String formatUrl(SacRestScheme scheme, String host, String path, Object param) {
		String url;
		if (param == null) {
			url = SacRestFormattingUtils.formatUrl(scheme, host, path);
		} else {
			url = SacRestFormattingUtils.formatUrl(scheme, host, path, param);
		}
		return url;
	}

	public static String formatUrl(SacRestRequest request) {
		SacRestScheme scheme = request.getScheme();
		String host = request.getHost();
		String path = request.getPath();
		Object param = request.getParam();
		return formatUrl(scheme, host, path, param);
	}

}
