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
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineVolListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineVolListSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Appzine;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.appzine.vo.AppzineVolList;

/**
 * AppzineVolList Service 인터페이스(CoreStoreBusiness) 구현체.
 * 
 * Updated on : 2015. 8. 3. Updated by : 이태희.
 */
@Service
public class AppzineVolListServiceImpl implements AppzineVolListService {
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.appzine.service.AppzineVolListService#searchAppzineVolList(com.skplanet
	 * .storeplatform.sac.client.display.vo.appzine.AppzineVolListSacReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public AppzineVolListSacRes searchAppzineVolList(AppzineVolListSacReq requestVO, SacRequestHeader requestHeader) {
		// 요청값 세팅
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
		requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);

		CommonResponse commonResponse = new CommonResponse();
		AppzineVolListSacRes appzineVolListSacRes = new AppzineVolListSacRes();
		List<Appzine> appzineList = new ArrayList<Appzine>();
		Appzine appzine = null;
		Title title;

		// Appzine 회차별 목록 조회
		List<AppzineVolList> resultList = this.commonDAO.queryForList("AppzineVolList.selectAppzineVolList", requestVO,
				AppzineVolList.class);
		if (resultList != null && !resultList.isEmpty()) {
			for (AppzineVolList appzineVolList : resultList) {
				appzine = new Appzine();

				// Appzine 번호
				appzine.setAppzineNumber(appzineVolList.getAppznNo());
				// Appzine 호수
				appzine.setAppzineVol(appzineVolList.getAppznVol());
				// Appzine 제목
				title = new Title();
				title.setText(appzineVolList.getTitle());
				appzine.setTitle(title);
				appzineList.add(appzine);
			}
			appzineVolListSacRes.setAppzineVolList(appzineList);
			commonResponse.setTotalCount(resultList.get(0).getTotalCount());

		} else {
			commonResponse.setTotalCount(0);
		}

		appzineVolListSacRes.setCommonResponse(commonResponse);
		return appzineVolListSacRes;
	}
}
