/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.appzine.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineDetailSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineDetailSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Appzine;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.appzine.vo.AppzineDetail;

/**
 * AppzineDetailService 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
@Service
public class AppzineDetailServiceImpl implements AppzineDetailService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.MenuListService#searchMenuList(String tenantId, String
	 * systemId, String menuId)
	 */
	@Override
	public AppzineDetailSacRes searchAppzineDetail(AppzineDetailSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		// 헤더 값 세팅
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());

		CommonResponse commonResponse = new CommonResponse();
		AppzineDetailSacRes appzineDetailSacRes = new AppzineDetailSacRes();
		Appzine appzine = new Appzine();
		Date date;
		List<Date> dateList;
		Title title;

		// Appzine 상세정보 조회
		AppzineDetail appzineDetail = this.commonDAO.queryForObject("AppzineDetail.selectAppzineDetail", requestVO,
				AppzineDetail.class);

		if (appzineDetail != null) {

			appzine.setAppzineNumber(appzineDetail.getAppznNo());
			appzine.setAppzineVol(appzineDetail.getAppznVol());

			/*
			 * DateList
			 */
			dateList = new ArrayList<Date>();
			date = new Date();
			date.setType("date/issue");
			date.setText(appzineDetail.getIssuday());
			dateList.add(date);
			date = new Date();
			date.setType("date/reg");
			date.setText(appzineDetail.getRegDt());
			dateList.add(date);
			appzine.setDateList(dateList);

			/*
			 * Title
			 */
			title = new Title();
			title.setText(appzineDetail.getTitle());
			appzine.setTitle(title);

			/*
			 * ETC
			 */
			appzine.setBackgroundImagePath(appzineDetail.getBgImgPath());
			appzine.setThemeUpImage(appzineDetail.getExpoYn());
			appzine.setPopularTitleImage480(appzineDetail.getPopularTitlImg480());
			appzine.setPopularTitleImage800(appzineDetail.getPopularTitlImg800());
			appzine.setNewTitleImage480(appzineDetail.getNewTitlImg480());
			appzine.setNewTitleImage800(appzineDetail.getNewTitlImg800());
			appzine.setEventName(appzineDetail.getEventNm());
			appzine.setEventUrl(appzineDetail.getEventUrl());
			appzine.setEventImage480(appzineDetail.getEventImg480());
			appzine.setEventImage880(appzineDetail.getEventImg800());
			appzine.setTitleImage(appzineDetail.getTitleImg());

			appzineDetailSacRes.setAppzineDetail(appzine);
			commonResponse.setTotalCount(1);
		} else {
			commonResponse.setTotalCount(0);
		}

		appzineDetailSacRes.setCommonResponse(commonResponse);

		return appzineDetailSacRes;
	}

}
