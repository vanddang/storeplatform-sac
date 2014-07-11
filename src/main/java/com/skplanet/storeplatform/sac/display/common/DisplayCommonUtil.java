package com.skplanet.storeplatform.sac.display.common;

import org.apache.commons.lang3.StringUtils;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

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

	/**
	 * <pre>
	 * OS verion 추출 메소드(HeaderInfo)
	 * Ex) Android/4.0.4 -> 4.0 .
	 * </pre>
	 * 
	 * @param osVer
	 *            osVer
	 * @return String
	 */
	public static String getOsVer(String osVer) {
		if (StringUtils.isEmpty(osVer))
			return osVer;

		String osVersion = osVer;
		String[] temp = osVer.trim().split("/");
		if (temp.length >= 2) {
			osVersion = temp[1];
			String osVersionOrginal = osVersion;
			String[] osVersionTemp = StringUtils.split(osVersionOrginal, ".");
			if (osVersionTemp.length == 3) {
				osVersion = osVersionTemp[0] + "." + osVersionTemp[1];
			}
		}
		return osVersion;
	}

	/**
	 * <pre>
	 * Updated By  : 2014.07.11 이석희
	 * OS verion 추출 메소드(Reqeust Parameter)
	 * Ex) 4.0.4 -> 4.0 .
	 * </pre>
	 * 
	 * @param osVer
	 *            osVer
	 * @return String
	 */
	public static String getOsVerReq(String osVer) {
		if (StringUtils.isEmpty(osVer))
			return osVer;

		String osVersion = osVer;
		String[] osVersionTemp = StringUtils.split(osVersion, ".");
		if (osVersionTemp.length == 3) {
			osVersion = osVersionTemp[0] + "." + osVersionTemp[1];
		}

		return osVersion;
	}

	/**
	 * Date타입에 날짜 기간 데이터를 정의해준다.
	 * 
	 * @param tp
	 * @param period
	 * @param unit
	 * @return
	 */
	public static Date makeDateUsagePeriod(String tp, Integer period, String unit) {
		if (tp == null || tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) {
			return null;
		}

		String unitName;

		if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_MINUTE)) {
			unitName = "minute";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_HOUR)) {
			unitName = "hour";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_DAY)) {
			unitName = "day";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_MONTH)) {
			unitName = "month";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_YEAR)) {
			unitName = "year";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_DAY)) {
			unitName = "limit/day";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_MONTH)) {
			unitName = "limit/month";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_YEAR)) {
			unitName = "limit/year";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_CALENDAR)) {
			unitName = "calendar";
		} else if (tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) {
			return new Date(DisplayConstants.DP_DATE_TYPE_USE_PERIOD, "unlimit");
		} else
			return null;

		return new Date(DisplayConstants.DP_DATE_TYPE_USE_PERIOD, unitName + "/" + period, unit);
	}
}
