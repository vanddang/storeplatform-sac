/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.appguide.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.appguide.vo.Appguide;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * App guide 테마 추천 목록 Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 03. 06. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class AppguideThemeListServiceImpl implements AppguideThemeListService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private int totalCount = 0;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.AppguideVersionServiceImpl#searchThemeRecommendMain(AppguideSacReq
	 * requestVO, SacRequestHeader requestHeader)
	 */
	@Override
	public AppguideSacRes searchThemeRecommendList(AppguideSacReq requestVO, SacRequestHeader requestHeader)
			throws StorePlatformException {

		AppguideSacRes responseVO = new AppguideSacRes();

		CommonResponse commonResponse = new CommonResponse();

		// String className = this.getClass().getName();

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		Map<String, Object> mapReq = new HashMap<String, Object>();
		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);
		mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);

		mapReq.put("listGrpCd", "TAP"); // 앱가이드 테마추천

		List<String> imageCodeList = new ArrayList<String>();
		imageCodeList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
		imageCodeList.add(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		mapReq.put("imageCdList", imageCodeList);
		mapReq.put("START_ROW", requestVO.getOffset());
		mapReq.put("END_ROW", (requestVO.getOffset() + requestVO.getCount() - 1));

		List<Appguide> themeList = this.commonDAO.queryForList("Appguide.Theme.getThemeRecommendList", mapReq,
				Appguide.class);
		if (themeList == null) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		List<Product> productList = this.makeProductList(themeList);

		commonResponse.setTotalCount(this.totalCount);
		responseVO.setCommonResponse(commonResponse);
		responseVO.setProductList(productList);

		return responseVO;
	}

	private List<Product> makeProductList(List<Appguide> themeList) {

		List<Product> mainListVO = new ArrayList<Product>();
		for (Appguide main : themeList) {
			// 테마 정보
			Product theme = new Product();
			Identifier themeId = new Identifier();
			themeId.setText(main.getThemeId());
			themeId.setType("theme");
			theme.setIdentifier(themeId);

			Title themeNm = new Title();
			themeNm.setText(main.getThemeNm());
			theme.setTitle(themeNm);
			theme.setThemeType(main.getThemeType());

			if (!StringUtils.isNullOrEmpty(main.getThemeImg())) {
				List<Source> themeUrlList = new ArrayList<Source>();
				Source themeUrl = new Source();
				themeUrl.setUrl(main.getThemeImg());
				themeUrlList.add(themeUrl);
				theme.setSourceList(themeUrlList);
			}

			this.totalCount = main.getTotalCount();

			mainListVO.add(theme);
		} // end of for

		return mainListVO;
	}
}
