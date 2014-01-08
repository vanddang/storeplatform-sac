/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.external.idp;

import com.skplanet.storeplatform.sac.external.idp.constant.IDPManagerConstants;

/**
 * IDPManager
 * 
 * Updated on : 2013. 12. 30. Updated by : Jeon.ByungYoul, SK planet.
 */
public class IDPManager implements IDPManagerConstants {

	/** OMP 서비스 도메인 */
	public static String OMP_SERVICE_DOMAIN = "www.omp.com";
	/** OMP 서비스 URL */
	public static String OMP_SERVICE_URL = "omp.dev.service.url";
	/** OMP 서비스 URL (HTTPS) */
	public static String OMP_SERVICE_URL_HTTPS = "omp.dev.service.url.https";
	/** OMP 서비스 URL IP */
	public static String OMP_SERVICE_URL_IP = "omp.dev.service.url.ip";

	/** IDP 요청 도메인 (HTTP) */
	public static String IDP_REQUEST_URL = "http://idp.innoace.com";
	/** IDP 요청 도메인 (HTTPS) */
	public static String IDP_REQUEST_URL_HTTPS = "https://idp.innoace.com"; //

	/** IDP에 등록한 OMP Association Key */
	public static String IDP_REQ_OMP_ASSOC_KEY = "6b0cc48e477f066b7ef353a4f9e8b756";
	/** IDP로 부터 발급된 Service ID */
	public static String IDP_REQ_OMP_SERVICE_ID = "OMP10000";
	/** IDP 인증 후 서비스 리턴 URL */

}
