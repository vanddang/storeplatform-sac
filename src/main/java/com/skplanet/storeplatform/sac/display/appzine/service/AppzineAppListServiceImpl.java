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
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appzine.AppzineAppListSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Appzine;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.appzine.vo.AppzineAppList;

/**
 * AppzineAppListService 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 02. 10. Updated by : 유시혁.
 */
@Service
@Transactional
public class AppzineAppListServiceImpl implements AppzineAppListService {

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
	public AppzineAppListSacRes searchAppzineAppList(AppzineAppListSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		// 요청값 세팅
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
		requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(requestVO.getAppType())) {
			throw new StorePlatformException("SAC_DSP_0002", "appType", requestVO.getAppType());
		} else if (StringUtils.isEmpty(requestVO.getAppznNo())) {
			throw new StorePlatformException("SAC_DSP_0002", "appznNo", requestVO.getAppznNo());
		}

		CommonResponse commonResponse = new CommonResponse();
		AppzineAppListSacRes appzineAppListSacRes = new AppzineAppListSacRes();

		List<AppzineAppList> resultList = this.commonDAO.queryForList("AppzineAppList.selectAppzineAppList", requestVO,
				AppzineAppList.class);
		if (resultList != null) {

			AppzineAppList appzineAppList = null;
			Appzine appzine = null;
			List<Appzine> appzineList = new ArrayList<Appzine>();

			Iterator<AppzineAppList> iterator = resultList.iterator();
			while (iterator.hasNext()) {
				appzineAppList = iterator.next();

				appzine = new Appzine();
				appzine.setSeq(appzineAppList.getSeq());
				appzine.setAppzineNumber(appzineAppList.getAppznNo());
				appzine.setAppType(appzineAppList.getAppType());
				appzine.setAppOrder(appzineAppList.getAppOrd());
				appzine.setContentsTitle(appzineAppList.getContentsTitl());
				appzine.setAppProductId(appzineAppList.getAppPid());
				appzine.setAppUrl(appzineAppList.getAppUrl());
				appzine.setImage480(appzineAppList.getImg480());
				appzine.setImage800(appzineAppList.getImg800());

				appzineList.add(appzine);
			}
			appzineAppListSacRes.setAppzineAppList(appzineList);
			commonResponse.setTotalCount(resultList.get(0).getTotalCount());
			appzineAppListSacRes.setCommonResponse(commonResponse);

		}
		return appzineAppListSacRes;
	}

}
