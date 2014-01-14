package com.skplanet.storeplatform.sac.display.common;

import org.apache.commons.lang3.StringUtils;

public class DisplayCommonUtil {

	public static String getMimeType(String filename) {
		String sMimeType = "";
		if (StringUtils.isNotEmpty(filename)) {
			int iLastIndex = filename.lastIndexOf(".");
			String sFileExtension = StringUtils.lowerCase(filename.substring(iLastIndex + 1));
			if (StringUtils.isNotEmpty(sFileExtension)) {
				if (sFileExtension.equals("gif")) {
					sMimeType = "image/gif";
				} else if (sFileExtension.equals("jpeg") || sFileExtension.equals("jpg")
						|| sFileExtension.equals("jpe")) {
					sMimeType = "image/jpeg";
				} else if (sFileExtension.equals("png")) {
					sMimeType = "image/png";
				} else if (sFileExtension.equals("mp4")) {
					sMimeType = "video/mp4";
				} else if (sFileExtension.equals("mpeg") || sFileExtension.equals("mpg")
						|| sFileExtension.equals("mpe")) {
					sMimeType = "video/mpeg";
				} else if (sFileExtension.equals("avi")) {
					sMimeType = "video/x-msvideo";
				} else if (sFileExtension.equals("asf") || sFileExtension.equals("asx")) {
					sMimeType = "video/x-ms-asf";
				} else if (sFileExtension.equals("wmv")) {
					sMimeType = "video/x-ms-wmv";
				} else if (sFileExtension.equals("wma")) {
					sMimeType = "audio/x-ms-wma";
				}
				return sMimeType;
			}

		}

		return sMimeType;
	}
}
