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
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideThemeSacReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.appguide.vo.Appguide;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

/**
 * App guide 테마 추천 메인 Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 03. 05. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class AppguideThemeMainServiceImpl implements AppguideThemeMainService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private int totalCount = 0;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private AppInfoGenerator appInfoGenerator;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private CommonMetaInfoGenerator commonMetaInfoGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.AppguideVersionServiceImpl#searchThemeRecommendMain(AppguideSacReq
	 * requestVO, SacRequestHeader requestHeader)
	 */
	@Override
	public AppguideSacRes searchThemeRecommendMain(AppguideThemeSacReq requestVO, SacRequestHeader requestHeader)
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

		int start = 1;
		int end = 20;

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

			List<Appguide> themeMainList = this.commonDAO.queryForList("Appguide.Theme.getThemeRecommendList", mapReq,
					Appguide.class);
			if (themeMainList == null || themeMainList.isEmpty()) {
				throw new StorePlatformException("SAC_DSP_0009");
			}

			List<String> themeIdList = new ArrayList<String>();
			for (Appguide t : themeMainList) {
				themeIdList.add(t.getThemeId());
			}
			mapReq.put("themeIdList", themeIdList);

			List<Appguide> themeProductList = this.commonDAO.queryForList(
					"Appguide.Theme.getThemeRecommendProductList", mapReq, Appguide.class);
			if (themeProductList == null || themeProductList.isEmpty()) {
				throw new StorePlatformException("SAC_DSP_0009");
			}
			productList = this.makeProductList(themeMainList, themeProductList);
		}

		commonResponse.setTotalCount(this.totalCount);
		responseVO.setCommonResponse(commonResponse);
		responseVO.setProductList(productList);
		return responseVO;
	}

	private List<Product> makeProductList(List<Appguide> themeMainList, List<Appguide> themeProductList) {

		List<Product> mainListVO = new ArrayList<Product>();
		List<Product> sublistVO = new ArrayList<Product>();

		for (Appguide main : themeMainList) {

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

			for (Appguide mapper : themeProductList) {
				if (main.getThemeId().equals(mapper.getThemeId())) {

					MetaInfo metaInfo = this.setMetaInfo(mapper);

					// 테마별 상품 정보
					Product product = new Product();
					Title title = new Title();
					App app = new App();
					Rights rights = new Rights();
					Source source = new Source();

					// Response VO를 만들기위한 생성자
					List<Source> sourceList = new ArrayList<Source>();

					title.setText(mapper.getProdNm());
					product.setTitle(title);

					if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 앱 타입일 경우
						product.setIdentifierList(this.appInfoGenerator.generateIdentifierList(metaInfo));
						product.setMenuList(this.appInfoGenerator.generateMenuList(metaInfo));
					} else {
						product.setIdentifierList(this.commonMetaInfoGenerator.generateIdentifierList(metaInfo));
						product.setMenuList(this.commonMetaInfoGenerator.generateMenuList(metaInfo));
					}

					/*
					 * Rights grade
					 */
					rights.setGrade(mapper.getProdGrdCd());
					product.setRights(rights);

					source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
					source.setMediaType(DisplayCommonUtil.getMimeType(mapper.getFilePos()));
					source.setUrl(mapper.getFilePos());
					sourceList.add(source);
					product.setSourceList(sourceList);

					// 상품 SVC_GRP_CD 조회
					// DP000203 : 멀티미디어
					// DP000206 : Tstore 쇼핑
					// DP000205 : 소셜쇼핑
					// DP000204 : 폰꾸미기
					// DP000201 : 애플리캐이션
					if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 앱 타입일 경우
						app.setAid(mapper.getAid());
						app.setPackageName(mapper.getApkPkg());
						app.setVersionCode(mapper.getApkVerCd());
						app.setVersion(mapper.getProdVer()); // 확인 필요
						product.setApp(app);
					} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 멀티미디어
						if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(mapper.getTopMenuId())
								|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // 영화/방송
						} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(mapper.getTopMenuId())
								|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // Ebook /
																										  // Comic
						} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(mapper.getTopMenuId())) { // 음원 상품의 경우
						}
					} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(mapper.getSvcGrpCd())) { // 쇼핑
					}

					sublistVO.add(product);
				} // end of if
			} // end of for
			theme.setSubProductList(sublistVO);
			mainListVO.add(theme);
			sublistVO = new ArrayList<Product>();

		} // end of for

		return mainListVO;
	}

	private MetaInfo setMetaInfo(Appguide mapper) {

		MetaInfo metaInfo = new MetaInfo();

		// set Indentifier list informations
		metaInfo.setContentsTypeCd(mapper.getContentsTypeCd());
		metaInfo.setProdId(mapper.getProdId());
		metaInfo.setPartProdId(mapper.getPartProdId());
		metaInfo.setTopMenuId(mapper.getTopMenuId());

		// set menu list
		if (!StringUtils.isNullOrEmpty(mapper.getTopMenuId())) {
			metaInfo.setTopMenuId(mapper.getTopMenuId());
			metaInfo.setTopMenuNm(mapper.getTopMenuNm());
		}
		if (!StringUtils.isNullOrEmpty(mapper.getMenuId())) {
			metaInfo.setMenuId(mapper.getMenuId());
			metaInfo.setMenuNm(mapper.getMenuNm());
		}
		if (!StringUtils.isNullOrEmpty(mapper.getMetaClsfCd())) {
			metaInfo.setMetaClsfCd(mapper.getMetaClsfCd());
		}

		return metaInfo;

	}
}
