/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.service.authentication;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKey;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import com.skplanet.storeplatform.sac.runtime.acl.vo.System;
/**
 *
 * 인증
 *
 * Updated on : 2014. 2.105. Updated by : 임근대, SK 플래닛.
 */
public interface AuthenticateService {

	/**
	 * 인증 처리 구현
	 *
	 * @param header
	 */
	void authenticate(HttpHeaders header);

    /**
     * AuthKey 정보 유효성 체크
     *
     * @param authKeyInfo
     *          인증키
     * @throws
     *      com.skplanet.storeplatform.framework.core.exception.StorePlatformException
     *      <ul>
     *          <li>SAC_CMN_0031 : 인증키 정보 없음</li>
     *          <li>SAC_CMN_0032 : 인증키 정보가 유효하지 않음</li>
     *          <li>SAC_CMN_0033 : 인증키의 사용기한 만료</li>
     *      </ul>
     */
     void checkAuthKeyInfo(AuthKey authKeyInfo) throws StorePlatformException;

    /**
     * Tenant 유효성 체크
     * @param authKeyInfo
     *          AuthKey 정보
     * @throws
     *      StorePlatformException
     *      <ul>
     *          <li>SAC_CMN_0034 : 테넌트 정보 없음</li>
     *          <li>SAC_CMN_0035 : 테넌트 정보 유효하지 않음</li>
     *      </ul>
     */
     void checkTenantInfo(AuthKey authKeyInfo);

    /**
     * System 정보 유효성 체크
     * @param headers
     * @return System
     *          시스템 정보
     * @throws
     *      StorePlatformException
     *      <ul>
     *          <li>SAC_CMN_0036 : 시스템 정보 없음</li>
     *          <li>SAC_CMN_0037 : 시스템 정보가 유효하지 않음</li>
     *      </ul>
     */
     System checkSystemInfo(HttpHeaders headers);

    /**
     * MAC (Message Authentication Code) 인증
     * @param headers
     *          헤더 정보
     * @throws
     *      StorePlatformException
     *      <ul>
     *          <li>SAC_CMN_0038 : 메시지 인증 코드가 유효하지 않음</li>
     *      </ul>
     */
    void authMac(HttpHeaders headers, AuthKey authKeyInfo) throws StorePlatformException;

    /**
     * IP 인증
     * @param headers
     *          헤더 정보
     * @throws
     *      StorePlatformException
     *      <ul>
     *          <li>SAC_CMN_0039 : IP 정보가 유효하지 않음</li>
     *      </ul>
     */
    void authIp(System system, HttpHeaders headers);
}
