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

import java.security.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.service.common.AclDataAccessService;
import com.skplanet.storeplatform.sac.runtime.acl.util.HmacSha1Util;
import com.skplanet.storeplatform.sac.runtime.acl.util.SacAuthUtil;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKey;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthType;
import com.skplanet.storeplatform.sac.runtime.acl.vo.HttpHeaders;
import com.skplanet.storeplatform.sac.runtime.acl.vo.TenantStatus;

/**
 *
 * 인증
 *
 * Updated on : 2014. 2.105. Updated by : 임근대, SK 플래닛.
 */
// @Service
public class AuthenticationServiceImpl implements AuthenticateService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	// @Autowired
	private AclDataAccessService service;


	/**
	 * 인증 처리
	 * TODO : 개발 중..
	 * @param headers
	 * 			Http Header
	 */
	@Override
	public void authenticate(HttpHeaders headers) {
		String pAuthKey = headers.getAuthKey();

		// 1. AuthKey 로 Tenant 정보 조회
		AuthKey authKeyInfo = this.service.selectAuthKey(pAuthKey);
		logger.debug("authKeyInfo={}", authKeyInfo);


		// 2. TENANT 상태 체크
		String tenantStatusCd = authKeyInfo.getTenantStatusCd();

		if(!tenantStatusCd.equals(TenantStatus.AVAILABLE.getCode())) {
			throw new StorePlatformException("SAC_CMN_0031");
		}

		// 3. authTypeCd 값에 따라 인증 처리
		//  KEY : AuthKey, Secret 값으로 MAC 인증
		// 	IP : System IP 체크
		boolean isValid = false;
		String authTypeCd  = authKeyInfo.getAuthTypeCd();
		if(authTypeCd.equals(AuthType.KEY.name())) {
			String requestUri = headers.getRequestUrl();
			String authKey = headers.getAuthKey();
			String timestamp = headers.getTimestamp();
			String nonce = headers.getNonce();
			String signature = headers.getSignature();
			//MAC 인증
			try {
				logger.debug("timestamp={}, nonce={}", timestamp, nonce);
				String data = SacAuthUtil.getMessageForAuth(requestUri, authKey, timestamp, nonce);
				String newSignature = HmacSha1Util.getSignature(data, authKeyInfo.getSecret());
				logger.debug("signature={}, newSignature={}", signature, newSignature);
				if(newSignature.equals(signature)) {
					isValid = true;
				}
			} catch (SignatureException e) {
				logger.error(e.getMessage());
				isValid = false;
			}

			if(!isValid) {
				throw new StorePlatformException("SAC_CMN_0032");
			}
		} else if(authTypeCd.equals(AuthType.IP.name())) {
			//TODO: IP 체크
			isValid = true;
			if(!isValid) {
				throw new StorePlatformException("SAC_CMN_0033");
			}
		}

	}
}
