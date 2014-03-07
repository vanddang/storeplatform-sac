package com.skplanet.storeplatform.sac.client.rest.apache;

import com.skplanet.storeplatform.sac.client.rest.util.SacRestFormattingUtils;

public class UrlFormatter {

	public static String formatUrl(String scheme, String host, String path, Object param) {
		String url;
		if (param == null) {
			url = SacRestFormattingUtils.formatUrl(scheme, host, path);
		} else {
			url = SacRestFormattingUtils.formatUrl(scheme, host, path, param);
		}
		return url;
	}

}
