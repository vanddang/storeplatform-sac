/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.flow.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.ErrorMessageBuilder;
import com.skplanet.storeplatform.sac.runtime.acl.service.AclService;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AclAuthKeyInfo;
import com.skplanet.storeplatform.sac.runtime.acl.vo.AclAuthInfo;
import com.skplanet.storeplatform.sac.runtime.flow.exception.AclException;
import com.skplanet.storeplatform.sac.runtime.flow.exception.code.AclErrorCode;
import com.skplanet.storeplatform.sac.runtime.flow.vo.HeaderInfo;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 26. Updated by : 김현일, 인크로스.
 */
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AclService aclService;

	@Value("#{propertiesForSac['skp.common.service.acl']}")
	private boolean aclYn;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.runtime.flow.service.AuthService#authentication(com.skplanet.storeplatform.sac
	 * .runtime.flow.vo.HeaderInfo)
	 */
	@Override
	public boolean authentication(@Header("headerInfo") HeaderInfo headerInfo) {

		if (!this.aclYn)
			return true;

		this.logger.info("메서드 실행 (authentication : " + headerInfo + ")");
		Map<String, String> params = new HashMap<String, String>();
		params.put("authKey", headerInfo.getAuthKey());
		AclAuthKeyInfo aclAuthKeyVO = this.aclService.searchAclAuthKey(params);
		if (aclAuthKeyVO == null) {
			throw new AclException(ErrorMessageBuilder.create().code(AclErrorCode.NULL_ARGUMENT.name())
					.arguments("authKeyVO", "null").build());
		}
		headerInfo.setSystemId(aclAuthKeyVO.getSystemId());
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.runtime.flow.service.AuthService#authorization(com.skplanet.storeplatform.sac.
	 * runtime.flow.vo.HeaderInfo)
	 */
	@Override
	public boolean authorization(@Header("headerInfo") HeaderInfo headerInfo) {

		if (!this.aclYn)
			return true;

		this.logger.info("메서드 실행 (authorization : " + headerInfo + ")");
		Map<String, String> params = new HashMap<String, String>();
		params.put("systemId", headerInfo.getSystemId());
		params.put("url", headerInfo.getPath());
		AclAuthInfo aclAuthVO = this.aclService.searchAclAuth(params);
		if (aclAuthVO == null) {
			throw new AclException(ErrorMessageBuilder.create().code(AclErrorCode.NULL_ARGUMENT.name())
					.arguments("authVO", "null").build());
		}
		return true;
	}
}
