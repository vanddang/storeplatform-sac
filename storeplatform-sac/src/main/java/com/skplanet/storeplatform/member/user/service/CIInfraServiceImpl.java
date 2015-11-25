/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.user.service;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraDetailUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraDetailUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraListUserKeyRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraListUserKeyResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraSearchUserInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraSearchUserInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraUserInfo;

/**
 * CI Infra API 서비스 구현체
 * 
 * Updated on : 2014. 10. 8. Updated by : 반범진, SK 플래닛.
 */
@Service
public class CIInfraServiceImpl implements CIInfraService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CIInfraServiceImpl.class);

	@Autowired
	@Qualifier("scMember")
	private CommonDAO commonDAO;

	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	@Override
	public CIInfraSearchUserInfoResponse searchUserInfo(CIInfraSearchUserInfoRequest request) {

		CIInfraUserInfo userInfo = this.commonDAO.queryForObject("CIInfra.getUserKeyInfo", request,
				CIInfraUserInfo.class);

		if (userInfo == null || StringUtils.isBlank(userInfo.getUserKey()))
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		CIInfraSearchUserInfoResponse response = new CIInfraSearchUserInfoResponse();
		response.setUserKey(userInfo.getUserKey());
		response.setCiInfraUserInfo(userInfo);

		return response;
	}

	@Override
	public CIInfraListUserKeyResponse listUserKey(CIInfraListUserKeyRequest request) {

		ArrayList<CIInfraUserInfo> list = (ArrayList<CIInfraUserInfo>) this.commonDAO.queryForList(
				"CIInfra.getListUserKey", request, CIInfraUserInfo.class);

		if (list.size() == 0)
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		CIInfraListUserKeyResponse response = new CIInfraListUserKeyResponse();
		response.setCiInfraUserInfoList(list);

		return response;
	}

	@Override
	public CIInfraDetailUserResponse detail(CIInfraDetailUserRequest request) {

		CIInfraUserInfo userDetailInfo = this.commonDAO.queryForObject("CIInfra.getUserDetailInfo", request,
				CIInfraUserInfo.class);

		if (userDetailInfo == null || StringUtils.isBlank(userDetailInfo.getUserKey()))
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		CIInfraDetailUserResponse response = new CIInfraDetailUserResponse();
		response.setUserKey(userDetailInfo.getUserKey());
		response.setCiInfraUserInfo(userDetailInfo);

		return response;
	}

	/**
	 * <pre>
	 *  메시지 프로퍼티에서 메시지 참조.
	 * </pre>
	 * 
	 * @param code
	 *            fail 코드
	 * @param fail
	 *            에러메세지
	 * @return String 결과 메세지
	 */
	private String getMessage(String code, String fail) {
		String msg = this.messageSourceAccessor.getMessage(code, null, fail, LocaleContextHolder.getLocale());
		LOGGER.debug(msg);
		return msg;
	}

}
