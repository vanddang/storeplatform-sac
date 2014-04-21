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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.util.HmacSha1Util;
import com.skplanet.storeplatform.sac.runtime.acl.util.SacAuthUtil;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKey;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKeyStatus;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKeyType;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthType;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import com.skplanet.storeplatform.sac.runtime.acl.vo.System;
import com.skplanet.storeplatform.sac.runtime.acl.vo.SystemStatus;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;
import com.skplanet.storeplatform.sac.runtime.acl.vo.TenantStatus;

/**
 *
 * 인증
 *
 * Updated on : 2014. 2.05. Updated by : 임근대, SK 플래닛.
 * Last Updated on : 2014. 4. 21. Updated by : 서대영, SK 플래닛.
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

		// 필수 헤더({x-sac-auth-key})값이 존재하지 않습니다.
		if (StringUtils.isBlank(pAuthKey))
			throw new StorePlatformException("SAC_CMN_0001", CommonConstants.HEADER_AUTH_KEY);

        // AuthKey
        // 1. AuthKey 정보 조회
        AuthKey authKeyInfo = this.dataAccessService.selectAuthKey(pAuthKey);
        logger.debug("authKeyInfo={}", authKeyInfo);
        this.checkAuthKeyInfo(authKeyInfo);

        // Tenant
        this.checkTenantInfo(headers, authKeyInfo);

        // System
        System system = this.checkSystemInfo(headers);

        // 4. AuthKey 유형에 따라 분기 (상용키/테스트키)
        // 테스트키일 경우 인증 Pass
        // 사용키는 AuthType 에 따라 MAC/IP 인증
        if(authKeyInfo.getAuthKeyType() != null && authKeyInfo.getAuthKeyType() == AuthKeyType.PROD) {
            // 4.1 AuthType 에 따라 분기

        	// GUID 검사
        	this.verifyGuid(headers);

        	// 요청시간 검사
        	this.verifyTimestamp(headers);

            if(authKeyInfo.getAuthType() != null && authKeyInfo.getAuthType() == AuthType.IP) {
                // IP 인증
                this.authIp(system, headers);
            } else {
                // MAC 인증 (default)
                this.authMac(headers, authKeyInfo);
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
    	String authKey = authKeyInfo == null ? "" : authKeyInfo.getAuthKey();

    	// 1.1. AuthKey 존재 여부 체크
        if(StringUtils.isBlank(authKey)) {
            throw new StorePlatformException("SAC_CMN_0031", authKey);
        }

        // 1.2. AuthKey 상태 체크
        if(authKeyInfo.getStatus() != AuthKeyStatus.AVAILABLE) {
            throw new StorePlatformException("SAC_CMN_0032", authKey);
        }

        // 1.3. AuthKey 사용기간 체크
        if(!StringUtils.equals("Y", authKeyInfo.getUsableDateYn())) {
            throw new StorePlatformException("SAC_CMN_0033", authKey);
        }
    }


    /**
     * Tenant 유효성 체크
     * @param headers
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
    public void checkTenantInfo(HttpHeaders headers, AuthKey authKeyInfo) {
    	String tenantId = headers.getTenantId();

		/* 나중에 적용 예정
		if (StringUtils.isBlank(tenantId))
			throw new StorePlatformException("SAC_CMN_0001", CommonConstants.HEADER_TENANT_ID);
		*/

    	// 헤더가 테넌트아이디가 없으면 인증키로 DB 조회 (임시 로직)
    	if (StringUtils.isBlank(tenantId)) {
    		tenantId = authKeyInfo.getTenantId();
    	}

        // 2. Tenant 정보 조회
        Tenant tenant = this.dataAccessService.selectTenant(tenantId);

        // 2.1. Tenant 데이터 존재 여부 체크
        if(tenant ==  null || StringUtils.isBlank(tenant.getTenantId())) {
			throw new StorePlatformException("SAC_CMN_0034", tenantId);
        }

        // 2.2. Tenant 상태 체크
        if(tenant.getStatus() != TenantStatus.AVAILABLE) {
			throw new StorePlatformException("SAC_CMN_0035", tenantId);
        }

    }


    /**
     * System 정보 유효성 체크
     * 어느 테넌트에도 속할 수 있는 Non Tenant 하위 시스템을 위해서 Tenant ID 없이 System ID만 체크함
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
    	String systemId = headers.getSystemId();

    	if (StringUtils.isBlank(systemId))
			throw new StorePlatformException("SAC_CMN_0001", CommonConstants.HEADER_SYSTEM_ID);

        // 3. System 정보 조회
        System dbSystem = this.dataAccessService.selectSystem(systemId);

        // 3.1. System 데이터 존재 여부 체크
        if(dbSystem == null || StringUtils.isBlank(dbSystem.getSystemId())) {
            // System 정보가 없음
            throw new StorePlatformException("SAC_CMN_0036", systemId);
        }

        // 3.2. System 상태 체크
        if(dbSystem.getStatus() != SystemStatus.AVAILABLE) {
            // System 상태가 유효하지 않음.
            throw new StorePlatformException("SAC_CMN_0037", systemId);
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
        String ip = headers.getRemoteHost(); //TODO:IP 정보 확인
		system.setIp(ip);
        System dbSystem = this.dataAccessService.selectSystemByIp(system);
        if(dbSystem == null || StringUtils.isEmpty(dbSystem.getIp())) {
            throw new StorePlatformException("SAC_CMN_0039");
        }
    }

	@Override
	public void verifyGuid(HttpHeaders header) {
		String guid = header.getGuid();

		// 필수 헤더({x-sac-guid})값이 존재하지 않습니다.
		if (StringUtils.isBlank(guid))
			throw new StorePlatformException("SAC_CMN_0001", CommonConstants.HEADER_GUID);
	}

    /**
     * Replay Attadck 여부 검사
     */
	@Override
	public void verifyTimestamp(HttpHeaders header) {
		String requestTimestamp = header.getTimestamp();

		// 필수 헤더({x-sac-auth-timestamp})값이 존재하지 않습니다.
    	if (StringUtils.isBlank(requestTimestamp))
			throw new StorePlatformException("SAC_CMN_0001", CommonConstants.HEADER_AUTH_TIMESTAMP);

		String nonce = header.getNonce();
		if (!SacAuthUtil.isValidTimestampAndNonce(requestTimestamp, nonce)) {
			throw new StorePlatformException("SAC_CMN_0002");
		}
	}


}
