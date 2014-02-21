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
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.util.HmacSha1Util;
import com.skplanet.storeplatform.sac.runtime.acl.util.SacAuthUtil;
import com.skplanet.storeplatform.sac.runtime.acl.vo.*;
import com.skplanet.storeplatform.sac.runtime.acl.vo.System;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.Override;

/**
 *
 * 인증
 *
 * Updated on : 2014. 2.105. Updated by : 임근대, SK 플래닛.
 */
@Service
public class AuthenticateServiceImpl implements AuthenticateService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticateServiceImpl.class);

    @Autowired
	private AclDataAccessService dataAccessService;


	/**
	 * 인증 처리
	 * TODO : 개발 중..
	 * @param headers
	 * 			Http Header
	 */
	@Override
	public void authenticate(HttpHeaders headers) {
		String pAuthKey = headers.getAuthKey();

        // AuthKey
        // 1. AuthKey 정보 조회
        AuthKey authKeyInfo = this.dataAccessService.selectAuthKey(pAuthKey);
        logger.debug("authKeyInfo={}", authKeyInfo);
        checkAuthKeyInfo(authKeyInfo);

        // Tenant
        checkTenantInfo(authKeyInfo);

        // System
        System system = checkSystemInfo(headers);

        // 4. AuthKey 유형에 따라 분기 (상용키/테스트키)
        // 테스트키일 경우 인증 Pass
        // 사용키는 AuthType 에 따라 MAC/IP 인증
        if(authKeyInfo.getAuthKeyType() != null && authKeyInfo.getAuthKeyType() == AuthKeyType.PROD) {
            // 4.1 AuthType 에 따라 분기

            if(authKeyInfo.getAuthType() != null && authKeyInfo.getAuthType() == AuthType.IP) {
                // IP 인증
                authIp(system, headers);
            } else {
                // MAC 인증 (default)
                authMac(headers, authKeyInfo);
            }
        }

	}


    /**
     * AuthKey 정보 유효성 체크
     *
     * @param authKeyInfo
     *          인증키
     * @throws
     *      StorePlatformException
     *      <ul>
     *          <li>SAC_CMN_0031 : 인증키 정보 없음</li>
     *          <li>SAC_CMN_0032 : 인증키 정보가 유효하지 않음</li>
     *          <li>SAC_CMN_0033 : 인증키의 사용기한 만료</li>
     *      </ul>
     */
    @Override
    public void checkAuthKeyInfo(AuthKey authKeyInfo) throws StorePlatformException {
        // 1.1. AuthKey 데이터 존재 여부 체크
        if(authKeyInfo == null) {
            // 인증키 정보 없음
            throw new StorePlatformException("SAC_CMN_0031");
        }

        // 1.2. AuthKey 상태 체크
        if(!StringUtils.equals(AuthKeyStatus.AVAILABLE.name(), authKeyInfo.getStatusCd())) {
            // 인증키가 유효하지 않음
            throw new StorePlatformException("SAC_CMN_0032");
        }

        // 1.3. AuthKey 사용기간 체크
        if(!StringUtils.equals("Y", authKeyInfo.getUsableDateYn())) {
            // 기간 만료
            throw new StorePlatformException("SAC_CMN_0033");
        }
    }


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
    @Override
    public void checkTenantInfo(AuthKey authKeyInfo) {

        String tenantId = authKeyInfo.getTenantId();

        // 2. Tenant 정보 조회
        Tenant tenant = dataAccessService.selectTenant(tenantId);

        // 2.1. Tenant 데이터 존재 여부 체크
        if(tenant ==  null) {
			throw new StorePlatformException("SAC_CMN_0034");
        }

        // 2.2. Tenant 상태 체크
        if(tenant.getStatus() != TenantStatus.AVAILABLE) {
            //Tenant 상태가 유효하지 않음.
			throw new StorePlatformException("SAC_CMN_0031");
        }

    }


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
    @Override
    public System checkSystemInfo(HttpHeaders headers) {
        // 3. System 정보 조회
        System dbSystem = this.dataAccessService.selectSystem(headers.getSystemId());

        // 3.1. System 데이터 존재 여부 체크
        if(dbSystem == null || StringUtils.isEmpty(dbSystem.getSystemId())) {
            // System 정보가 없음
            throw new StorePlatformException("SAC_CMN_0036");
        }

        // 3.2. System 상태 체크
        if(dbSystem.getStatus() != SystemStatus.AVAILABLE) {
            // System 상태가 유효하지 않음.
            throw new StorePlatformException("SAC_CMN_0037");
        }

        return dbSystem;
    }

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
    @Override
    public void authMac(HttpHeaders headers, AuthKey authKeyInfo) throws StorePlatformException {
        // MAC 인증 (default)
        String requestUri = headers.getRequestUrl();
        String authKey = headers.getAuthKey();
        String timestamp = headers.getTimestamp();
        String nonce = headers.getNonce();
        String signature = headers.getSignature();
        try {
            logger.debug("timestamp={}, nonce={}", timestamp, nonce);
            String data = SacAuthUtil.getMessageForAuth(requestUri, authKey, timestamp, nonce);
            String newSignature = HmacSha1Util.getSignature(data, authKeyInfo.getSecret());
            if(!newSignature.equals(signature)) {
                logger.error("signature={}, newSignature={}", signature, newSignature);
                // 메시지 인증 코드가 유효하지 않습니다.
                throw new StorePlatformException("SAC_CMN_0038");
            }

        } catch (Exception e) {
            //SecurityException
            logger.error(e.getMessage());
            throw new StorePlatformException("SAC_CMN_0038");
        }
    }

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
    @Override
    public void authIp(System system, HttpHeaders headers) {
        //IP 인증
        String ip = ""; //TODO:headers.get ??
		system.setIp(ip);
        System dbSystem = this.dataAccessService.selectSystemByIp(system);
        if(StringUtils.isEmpty(dbSystem.getIp())) {
            throw new StorePlatformException("SAC_CMN_0039");
        }
    }


}
