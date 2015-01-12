package com.skplanet.storeplatform.sac.display.common;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				} else if (sFileExtension.equals("tns")) {
					sMimeType = "image/tns";
				} else if (sFileExtension.equals("tmz")) {
					sMimeType = "binary/tmz";
				} else if (sFileExtension.equals("epub")) {
					sMimeType = "application/epub+zip";
				} else if (sFileExtension.equals("ipub")) {
					sMimeType = "application/ipub+zip";
				} else if (sFileExtension.equals("zip")) {
					sMimeType = "application/zip";
				} else {
					sMimeType = "x-planet-application/" + sFileExtension;
				}
				return sMimeType;
			}

		}

		return sMimeType;
	}

	/**
	 * <pre>
	 * OS verion 추출 메소드(HeaderInfo)
     * 프로비저닝을 위한 버전 정보는 "majVer.minVer" 형태로 DB에 존재하기 때문에 입력 문자열을 가공하기 위한 것이다.
	 * Ex) Android/4.0.4 -> 4.0 .
	 * </pre>
	 *
	 * @param osVer 운영체제 정보 문자열
	 * @return majVer.minVer 형태의 문자열. 읽을 수 없는 형태인 경우 0.0 출력
	 */
    private static Pattern RX_EXTRACT_VERSION = Pattern.compile("(?:(?:.+)\\s?/\\s?)?(\\d+)(?:\\.(\\d+))?(?:\\.\\d+)?");
	public static String extractOsVer(String osVer) {
		if (StringUtils.isEmpty(osVer))
			return "0.0";

        int majVer = 0, minVer = 0;
        Matcher m = RX_EXTRACT_VERSION.matcher(osVer);
        if(m.matches()) {
            majVer = NumberUtils.toInt(m.group(1));
            minVer = NumberUtils.toInt(m.group(2));
        }

		return majVer + "." + minVer;
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
