/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.user.sci;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.member.client.user.sci.CIInfraSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraDetailUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraDetailUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraListUserKeyRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraListUserKeyResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraSearchUserInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CIInfraSearchUserInfoResponse;
import com.skplanet.storeplatform.member.user.service.CIInfraService;

/**
 * CI Infra Controller
 * 
 * Updated on : 2014. 10. 8. Updated by : 반범진, SK 플래닛.
 */
@LocalSCI
public class CIInfraSCIController implements CIInfraSCI {

	private static final Logger LOGGER = LoggerFactory.getLogger(CIInfraSCIController.class);

	@Autowired
	private CIInfraService service;

	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	@Override
	public CIInfraSearchUserInfoResponse searchUserInfo(CIInfraSearchUserInfoRequest request) {

		// 필수 파라메터 체크
		if (StringUtils.isBlank(request.getImSvcNo()) || StringUtils.isBlank(request.getTrxNo())) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}
		CIInfraSearchUserInfoResponse response = this.service.searchUserInfo(request);

		return response;
	}

	@Override
	public CIInfraListUserKeyResponse listUserKey(CIInfraListUserKeyRequest request) {

		// 필수 파라메터 체크
		if (StringUtils.isBlank(request.getSearchType()) || StringUtils.isBlank(request.getSearchDay())) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 검색 요청 키값 체크(N : 신규, U : 정보변경, S : 탈퇴)
		if (!StringUtils.equalsIgnoreCase(request.getSearchType(), "N")
				&& !StringUtils.equalsIgnoreCase(request.getSearchType(), "U")
				&& !StringUtils.equalsIgnoreCase(request.getSearchType(), "S")) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
		}
		CIInfraListUserKeyResponse response = this.service.listUserKey(request);

		return response;
	}

	@Override
	public CIInfraDetailUserResponse detail(CIInfraDetailUserRequest request) {

		// 필수 파라메터 체크
		if (StringUtils.isBlank(request.getKeyType()) || StringUtils.isBlank(request.getKey())) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		// 검색 요청 키값 체크(N : 신규, U : 정보변경, S : 탈퇴)
		if (!StringUtils.equalsIgnoreCase(request.getKeyType(), "K01")
				&& !StringUtils.equalsIgnoreCase(request.getKeyType(), "K02")
				&& !StringUtils.equalsIgnoreCase(request.getKeyType(), "K03")) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.wrongKeyType", ""));
		}

		CIInfraDetailUserResponse response = this.service.detail(request);

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
