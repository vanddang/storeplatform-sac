/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.intimateMessage.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * DownloadComic Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
@Transactional
public class IntimateMessageServiceImpl implements IntimateMessageService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public IntimateMessageSacRes searchIntimateMessageList(SacRequestHeader requestHeader,
			IntimateMessageSacReq intimateMessageReq) {
		IntimateMessageSacRes intimateMessageRes = new IntimateMessageSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String userKey = intimateMessageReq.getUserKey();
		String deviceIdType = intimateMessageReq.getDeviceIdType();
		String deviceId = intimateMessageReq.getDeviceId();

		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[searchIntimateMessageList] productId : {}", userKey);
		this.logger.debug("[searchIntimateMessageList] deviceKey : {}", deviceIdType);
		this.logger.debug("[searchIntimateMessageList] userKey : {}", deviceId);
		this.logger.debug("----------------------------------------------------------------");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(userKey)) {
			throw new StorePlatformException("SAC_DSP_0002", "userKey", userKey);
		}
		if (StringUtils.isEmpty(deviceIdType)) {
			throw new StorePlatformException("SAC_DSP_0002", "deviceIdType", deviceIdType);
		}
		if (StringUtils.isEmpty(deviceId)) {
			throw new StorePlatformException("SAC_DSP_0002", "deviceId", deviceId);
		}
		// 기기ID유형 유효값 체크
		if (!"msisdn".equals(deviceIdType) && !"mac".equals(deviceIdType)) {
			throw new StorePlatformException("SAC_DSP_0003", "deviceIdType", deviceIdType);
		}

		// 헤더정보 세팅
		intimateMessageReq.setTenantId(requestHeader.getTenantHeader().getTenantId());

		List<MetaInfo> intimateMessageList = this.commonDAO.queryForList("IntimateMessage.selectIntimateMessageList",
				intimateMessageReq, MetaInfo.class);

		if (!intimateMessageList.isEmpty()) {

		} else {
			intimateMessageRes.setCommonResponse(commonResponse);
		}

		return intimateMessageRes;
	}
}
