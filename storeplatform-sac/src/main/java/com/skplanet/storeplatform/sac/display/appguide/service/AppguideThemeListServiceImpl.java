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
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideThemeSacReq;
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
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;

/**
 * App guide 테마 추천 목록 Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 03. 06. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class AppguideThemeListServiceImpl implements AppguideThemeListService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	/**
	 * 
	 * <pre>
	 * 2.15.3 테마 추천 리스트.
	 * 앱 가이드 추천 테마 리스트를 조회한다.
	 * </pre>
	 * 
	 * @param requestVO
	 *            AppguideVersionSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return AppguideSacRes
	 */
	@Override
	public AppguideSacRes searchThemeRecommendList(AppguideThemeSacReq requestVO, SacRequestHeader requestHeader) {

		AppguideSacRes responseVO = new AppguideSacRes();
		CommonResponse commonResponse = new CommonResponse();

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		Map<String, Object> mapReq = new HashMap<String, Object>();
		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);
		mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);

		mapReq.put("listGrpCd", "TAP"); // 앱가이드 테마추천

		List<String> imageCodeList = new ArrayList<String>();
		imageCodeList.add(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD); // App Image Code(DP000101)
		imageCodeList.add(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD); // VOD Image Code(DP000127)
		imageCodeList.add(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD); // Ebook/Comic Image Code(DP006207)
		imageCodeList.add(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD); // Music Image Code(DP000162)
		imageCodeList.add(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD); // Shopping Image Code(DP0001A3)
		mapReq.put("imageCdList", imageCodeList);

		int start = 1;
		int end = 100;

		if (requestVO.getOffset() > 0) {
			start = requestVO.getOffset();
		}
		if (requestVO.getCount() > 0 && (requestVO.getOffset() + requestVO.getCount() - 1) >= start) {
			end = requestVO.getOffset() + requestVO.getCount() - 1;
		}
		mapReq.put("START_ROW", start);
		mapReq.put("END_ROW", end);

		List<Product> productList = new ArrayList<Product>();

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(deviceHeader.getModel());
		if (supportDevice != null) {
			mapReq.put("ebookSprtYn", supportDevice.getEbookSprtYn());
			mapReq.put("comicSprtYn", supportDevice.getComicSprtYn());
			mapReq.put("musicSprtYn", supportDevice.getMusicSprtYn());
			mapReq.put("videoDrmSprtYn", supportDevice.getVideoDrmSprtYn());
			mapReq.put("sdVideoSprtYn", supportDevice.getSdVideoSprtYn());
			mapReq.put("sclShpgSprtYn", supportDevice.getSclShpgSprtYn());

			// 추천 테마 ID 조회
			List<String> listIdList = this.commonDAO.queryForList("Appguide.Theme.getThemeRecommendMainList", mapReq, String.class);

			mapReq.put("listId", listIdList);

			// 추천 테마 ID의 리스트에 해당하는 테마정보를 조회한다 
			List<Appguide> themeList = this.commonDAO.queryForList("Appguide.Theme.getThemeRecommendList", mapReq, Appguide.class);
			if (themeList == null || themeList.isEmpty()) {
				commonResponse.setTotalCount(0);
				responseVO.setCommonResponse(commonResponse);

				return responseVO;
			} else {
				commonResponse.setTotalCount(themeList.get(0).getTotalCount());
				productList = this.makeProductList(themeList);
			}
		} else {
			commonResponse.setTotalCount(0);
		}

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
			theme.setIdentifier(themeId); //Indentifier 정보 set

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

			mainListVO.add(theme);
		} // end of for

		return mainListVO;
	}
}
