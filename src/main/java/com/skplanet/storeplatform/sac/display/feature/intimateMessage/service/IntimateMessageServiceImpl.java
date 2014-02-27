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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.IntimateMessage;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.feature.intimateMessage.vo.IntimateMessageDefault;

/**
 * DownloadComic Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
public class IntimateMessageServiceImpl implements IntimateMessageService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DeviceSCI deviceSCI;

	@Override
	public IntimateMessageSacRes searchIntimateMessageList(SacRequestHeader requestHeader,
			IntimateMessageSacReq messageReq) {
		IntimateMessageSacRes intimateMessageRes = new IntimateMessageSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String userKey = messageReq.getUserKey();
		String deviceKey = messageReq.getDeviceKey();

		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[searchIntimateMessageList] userKey : {}", userKey);
		this.logger.debug("[searchIntimateMessageList] deviceId : {}", deviceKey);
		this.logger.debug("----------------------------------------------------------------");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(userKey)) {
			throw new StorePlatformException("SAC_DSP_0002", "userKey", userKey);
		}
		if (StringUtils.isEmpty(deviceKey)) {
			throw new StorePlatformException("SAC_DSP_0002", "deviceKey", deviceKey);
		}
		// offset Default 값 세팅
		if (messageReq.getOffset() == null) {
			messageReq.setOffset(1);
		}
		// count Default 값 세팅
		if (messageReq.getCount() == null) {
			messageReq.setCount(20);
		}

		// 페이징 계산
		messageReq.setCount(messageReq.getOffset() + messageReq.getCount() - 1);

		// 헤더정보 세팅
		messageReq.setTenantId(requestHeader.getTenantHeader().getTenantId());

		// 기기변경 이력 조회를 위한 생성자
		ChangedDeviceHistorySacReq deviceReq = null;
		ChangedDeviceHistorySacRes deviceRes = null;

		try {
			deviceReq = new ChangedDeviceHistorySacReq();
			deviceReq.setUserKey(userKey);
			deviceReq.setDeviceKey(deviceKey);

			// 기기변경 이력 조회
			deviceRes = this.deviceSCI.searchChangedDeviceHistory(deviceReq);
		} catch (Exception ex) {
			// 기기변경 이력 조회 연동 중 오류가 발생하였습니다.
			throw new StorePlatformException("SAC_DSP_1003", ex);
		}

		if (deviceRes != null) {
			// 기기변경 여부 세팅
			messageReq.setDeviceChangeFlag(deviceRes.getIsChanged());

			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("[searchIntimateMessageList] deviceChangeFlag : {}", messageReq.getDeviceChangeFlag());
			this.logger.debug("----------------------------------------------------------------");

			// Intimate Message 조회
			List<IntimateMessageDefault> resultList = this.commonDAO.queryForList(
					"IntimateMessage.selectIntimateMessageList", messageReq, IntimateMessageDefault.class);

			if (resultList != null && !resultList.isEmpty()) {
				IntimateMessageDefault messageDefault = new IntimateMessageDefault();

				IntimateMessage intimateMessage = null;
				Identifier identifier = null;
				Title title = null;
				Title subTitle = null;
				Url url = null;
				Date date = null;
				Source source = null;

				List<Identifier> identifierList = null;
				List<Source> sourceList = null;
				List<IntimateMessage> intimateMessageList = new ArrayList<IntimateMessage>();

				for (int i = 0; i < resultList.size(); i++) {
					messageDefault = resultList.get(i);
					intimateMessage = new IntimateMessage();

					// ID 정보
					identifier = new Identifier();
					identifierList = new ArrayList<Identifier>();
					identifier.setType(messageDefault.getMsgTypeCd());
					identifier.setText(messageDefault.getMsgId());
					identifierList.add(identifier);
					intimateMessage.setIdentifierList(identifierList);

					// 메인 제목
					title = new Title();
					title.setText(messageDefault.getMainMsg());
					title.setColor(messageDefault.getMainColor());
					intimateMessage.setTitle(title);

					// 하위 제목
					subTitle = new Title();
					subTitle.setText(messageDefault.getInfrMsg());
					subTitle.setColor(messageDefault.getInfrColor());
					intimateMessage.setSubTitle(subTitle);

					// 오퍼링 타입
					if ("url".equals(messageDefault.getOfrTypeCd())) {
						url = new Url();
						date = new Date();

						date.setType(DisplayConstants.DP_DATE_REG);
						date.setText(messageDefault.getRegDt());
						url.setDate(date);
						url.setText(messageDefault.getOfrDesc());
						intimateMessage.setUrl(url);
					} else if ("themeRecomm".equals(messageDefault.getOfrTypeCd())) {
						intimateMessage.setThemeRecommId(messageDefault.getOfrDesc());
					} else if ("appCodi".equals(messageDefault.getOfrTypeCd())) {
						intimateMessage.setAppCodi(messageDefault.getOfrDesc());
					} else if ("purchaseHistory".equals(messageDefault.getOfrTypeCd())) {
						intimateMessage.setPurchaseHistory("purchaseHistory");
					}

					// 배경 이미지
					if (StringUtils.isNotEmpty(messageDefault.getGnbImgPath())) {
						source = new Source();
						sourceList = new ArrayList<Source>();
						source.setMediaType(DisplayCommonUtil.getMimeType(messageDefault.getGnbImgPath()));
						source.setType(DisplayConstants.DP_SOURCE_TYPE_GNB_BG);
						source.setUrl(messageDefault.getGnbImgPath());
						sourceList.add(source);
					}

					// 아이콘 이미지
					if (StringUtils.isNotEmpty(messageDefault.getBiImgPath())) {
						if (sourceList == null) {
							sourceList = new ArrayList<Source>();
						}
						source = new Source();
						source.setMediaType(DisplayCommonUtil.getMimeType(messageDefault.getBiImgPath()));
						source.setType(DisplayConstants.DP_SOURCE_TYPE_GNB_ICON);
						source.setUrl(messageDefault.getBiImgPath());
						sourceList.add(source);
					}

					if (sourceList != null) {
						intimateMessage.setSourceList(sourceList);
						sourceList = null;
					}

					intimateMessageList.add(intimateMessage);
				}

				commonResponse.setTotalCount(messageDefault.getTotalCount());
				intimateMessageRes.setCommonResponse(commonResponse);
				intimateMessageRes.setIntimateMessageList(intimateMessageList);
			} else {
				intimateMessageRes.setCommonResponse(commonResponse);
			}
		}

		return intimateMessageRes;
	}
}
