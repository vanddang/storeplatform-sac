/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature;

import com.skplanet.storeplatform.sac.api.util.StringUtil;

/**
 * 
 * 
 * Updated on : 2014. 1. 10. Updated by : 서영배, GTSOFT.
 */
public class FeatureConstant {

	public static String convertProdGrdCd(String prodGrdCd) {
		String tenantProdGrdCd = "";
		if ("PD004401".equals(prodGrdCd)) tenantProdGrdCd = "0";
		else if ("PD004402".equals(prodGrdCd)) tenantProdGrdCd = "1";
		else if ("PD004403".equals(prodGrdCd)) tenantProdGrdCd = "2";
		else if ("PD004404".equals(prodGrdCd)) tenantProdGrdCd = "4";
		
		return tenantProdGrdCd;
	}
	
	public static String convertAppSupport(String partParentClsfCd, String drmYn) {
		String appSupport = "";
		if ("Y".equals(drmYn)) appSupport = "drm|";
		else appSupport = "|";
		if ("PD012301".equals(partParentClsfCd)) appSupport = appSupport + "iab";
		
		return appSupport;
	}
	
	public static String convertProdVer(String verMajor, String verMinor) {
		String prodVer = "";
		prodVer = StringUtil.nvl(verMajor, "") + "." + StringUtil.nvl(verMinor, "");
		return prodVer;
	}
}
