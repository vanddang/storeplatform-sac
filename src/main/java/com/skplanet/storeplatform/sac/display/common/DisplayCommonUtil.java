package com.skplanet.storeplatform.sac.display.common;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
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

	/**
	 * <pre>
	 * OS verion 추출 메소드.
	 * Ex) Android/4.0.4 -> 4.0 .
	 * </pre>
	 * 
	 * @param osVer
	 *            osVer
	 * @return String
	 */
	public static String getOsVer(String osVer) {
		String[] temp = osVer.trim().split("/");

		String osVersion = temp[1];
		String osVersionOrginal = osVersion;
		String[] osVersionTemp = StringUtils.split(osVersionOrginal, ".");
		if (osVersionTemp.length == 3) {
			osVersion = osVersionTemp[0] + "." + osVersionTemp[1];
		}
		return osVersion;
	}

    public static Date makeDateUsagePeriod(String tp, Integer period) {
        if(tp == null || tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_NONE)) {
            return null;
        }

        String unitName;

        if(tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_MINUTE)) {
            unitName = "minute";
        }
        else if(tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_HOUR)) {
            unitName = "hour";
        }
        else if(tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_DAY)) {
            unitName = "day";
        }
        else if(tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_MONTH)) {
            unitName = "month";
        }
        else if(tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_YEAR)) {
            unitName = "year";
        }
        else if(tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_DAY)) {
            unitName = "limit/day";
        }
        else if(tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_MONTH)) {
            unitName = "limit/month";
        }
        else if(tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_LIMIT_YEAR)) {
            unitName = "limit/year";
        }
        else if(tp.equals(DisplayConstants.DP_USE_PERIOD_UNIT_CD_CALENDAR)) {
            unitName = "calendar";
        }
        else
            return null;

        return new Date(DisplayConstants.DP_DATE_TYPE_USE_PERIOD, unitName + "/" + period);
    }
}
