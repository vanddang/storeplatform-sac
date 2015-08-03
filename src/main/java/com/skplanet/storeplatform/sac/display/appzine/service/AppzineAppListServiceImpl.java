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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Appzine;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.appzine.vo.AppzineAppList;

/**
 * AppzineAppListService 인터페이스(CoreStoreBusiness) 구현체.
 * 
 * Updated on : 2015. 8. 3. Updated by : 이태희.
 */
@Service
public class AppzineAppListServiceImpl implements AppzineAppListService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.appzine.service.AppzineAppListService#searchAppzineAppList(com.skplanet
	 * .storeplatform.sac.client.display.vo.appzine.AppzineAppListSacReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public AppzineAppListSacRes searchAppzineAppList(AppzineAppListSacReq requestVO, SacRequestHeader requestHeader) {
		// 요청값 세팅
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
		requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);

		CommonResponse commonResponse = new CommonResponse();
		AppzineAppListSacRes appzineAppListSacRes = new AppzineAppListSacRes();
		Appzine appzine = null;
		List<Appzine> appzineList = new ArrayList<Appzine>();

		// Appzine 앱 목록 조회
		List<AppzineAppList> resultList = this.commonDAO.queryForList("AppzineAppList.selectAppzineAppList", requestVO,
				AppzineAppList.class);

		if (resultList != null && !resultList.isEmpty()) {
			for (AppzineAppList appzineAppList : resultList) {
				appzine = new Appzine();

				// Appzine 기타정보
				appzine.setSeq(appzineAppList.getSeq());
				appzine.setAppzineNumber(appzineAppList.getAppznNo());
				appzine.setAppType(appzineAppList.getAppType());
				appzine.setAppOrder(appzineAppList.getAppOrd());
				appzine.setContentsTitle(appzineAppList.getContentsTitl());
				appzine.setAppProductId(appzineAppList.getAppPid());
				appzine.setAppUrl(appzineAppList.getAppUrl());
				appzine.setImage480(appzineAppList.getImg480());
				appzine.setImage800(appzineAppList.getImg800());
				appzine.setTitleImage(appzineAppList.getTitleImg());
				appzine.setThemeUpImage(appzineAppList.getThemeUpImg());
				appzineList.add(appzine);
			}
			appzineAppListSacRes.setAppzineAppList(appzineList);
			commonResponse.setTotalCount(resultList.get(0).getTotalCount());
		} else {
			commonResponse.setTotalCount(0);
		}

		appzineAppListSacRes.setCommonResponse(commonResponse);
		return appzineAppListSacRes;
	}
}
