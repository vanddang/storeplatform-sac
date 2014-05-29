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

import org.apache.commons.beanutils.BeanUtils;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.appguide.vo.AppguideMain;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * App guide 테마 추천 메인 Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 03. 05. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class AppguideThemeMainServiceImpl implements AppguideThemeMainService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

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

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		Map<String, Object> mapReq = new HashMap<String, Object>();
		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);
		mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);

		mapReq.put("listGrpCd", "TAP"); // 앱가이드 테마추천

		List<Product> themeList = new ArrayList<Product>();

		int productCnt = 0;

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(deviceHeader.getModel());
		if (supportDevice != null) {
			mapReq.put("ebookSprtYn", supportDevice.getEbookSprtYn());
			mapReq.put("comicSprtYn", supportDevice.getComicSprtYn());
			mapReq.put("musicSprtYn", supportDevice.getMusicSprtYn());
			mapReq.put("videoDrmSprtYn", supportDevice.getVideoDrmSprtYn());
			mapReq.put("sdVideoSprtYn", supportDevice.getSdVideoSprtYn());
			mapReq.put("sclShpgSprtYn", supportDevice.getSclShpgSprtYn());

			List<String> listIdList = this.commonDAO.queryForList("Appguide.Theme.getThemeRecommendMainList", mapReq,
					String.class);

			mapReq.put("listId", listIdList);

			List<AppguideMain> themeMainList = this.commonDAO.queryForList(
					"Appguide.Theme.getBasicThemeRecommendMainProductList", mapReq, AppguideMain.class);
			if (themeMainList == null || themeMainList.isEmpty()) {
				throw new StorePlatformException("SAC_DSP_0009");
			}

			commonResponse.setTotalCount(themeMainList.get(0).getTotalCount());

			// 테마 정보
			int count = 0;
			int total = themeMainList.size();
			String beforeThemeId = "";
			Product theme = new Product();
			List<Product> productListA = new ArrayList<Product>(); // 테마추천별 상품 리스트
			List<Product> productListB = new ArrayList<Product>(); // 테마추천별 상품 리스트
			for (AppguideMain main : themeMainList) {

				count++;

				Product currTheme = new Product();
				Identifier themeId = new Identifier();
				themeId.setText(main.getThemeId());
				themeId.setType("theme");
				currTheme.setIdentifier(themeId);

				Title themeNm = new Title();
				themeNm.setText(main.getThemeNm());
				currTheme.setTitle(themeNm);
				currTheme.setThemeType(main.getThemeType());

				if (!StringUtils.isNullOrEmpty(main.getThemeImg())) {
					List<Source> themeUrlList = new ArrayList<Source>();
					Source themeUrl = new Source();
					themeUrl.setUrl(main.getThemeImg());
					themeUrlList.add(themeUrl);
					currTheme.setSourceList(themeUrlList);
				}

				ProductBasicInfo productBasicInfo = new ProductBasicInfo();
				productBasicInfo.setContentsTypeCd(main.getContentsTypeCd());
				productBasicInfo.setExpoOrd(main.getExpoOrd());
				productBasicInfo.setMenuId(main.getMenuId());
				productBasicInfo.setMetaClsfCd(main.getMetaClsfCd());
				productBasicInfo.setPartProdId(main.getPartProdId());
				productBasicInfo.setProdId(main.getProdId());
				productBasicInfo.setSvcGrpCd(main.getSvcGrpCd());
				productBasicInfo.setTopMenuId(main.getTopMenuId());

				Product product = null;
				MetaInfo metaInfo = null;

				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("tenantHeader", tenantHeader);
				paramMap.put("deviceHeader", deviceHeader);
				paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING); // 판매중

				// Meta 정보 조회
				String topMenuId = productBasicInfo.getTopMenuId(); // 탑메뉴
				String svcGrpCd = productBasicInfo.getSvcGrpCd(); // 서비스 그룹 코드
				paramMap.put("productBasicInfo", productBasicInfo);

				if (this.log.isDebugEnabled()) {
					this.log.debug("##### Top Menu Id : {}", topMenuId);
					this.log.debug("##### Service Group Cd : {}", svcGrpCd);
				}
				// 상품 SVC_GRP_CD 조회
				// DP000203 : 멀티미디어
				// DP000206 : Tstore 쇼핑
				// DP000205 : 소셜쇼핑
				// DP000204 : 폰꾸미기
				// DP000201 : 애플리캐이션
				// APP 상품의 경우
				if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
					paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					if (this.log.isDebugEnabled()) {
						this.log.debug("##### Search for app  meta info product");
					}
					metaInfo = this.metaInfoService.getAppMetaInfo(paramMap);
					if (metaInfo != null) {
						product = this.responseInfoGenerateFacade.generateAppProduct(metaInfo);
						productListA.add(product);
					}

				} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 멀티미디어 타입일 경우
					// 영화/방송 상품의 경우
					paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
						if (this.log.isDebugEnabled()) {
							this.log.debug("##### Search for Vod  meta info product");
						}
						metaInfo = this.metaInfoService.getVODMetaInfo(paramMap);
						if (metaInfo != null) {
							if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
								product = this.responseInfoGenerateFacade.generateMovieProduct(metaInfo);
							} else {
								product = this.responseInfoGenerateFacade.generateBroadcastProduct(metaInfo);
							}
							productListA.add(product);
						}
					} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의
																						  // 경우

						paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

						if (this.log.isDebugEnabled()) {
							this.log.debug("##### Search for EbookComic specific product");
						}
						metaInfo = this.metaInfoService.getEbookComicMetaInfo(paramMap);
						if (metaInfo != null) {
							if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
								product = this.responseInfoGenerateFacade.generateEbookProduct(metaInfo);
							} else {
								product = this.responseInfoGenerateFacade.generateComicProduct(metaInfo);
							}
							productListA.add(product);
						}
					} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 음원 상품의 경우

						paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
						paramMap.put("contentTypeCd", DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);

						if (this.log.isDebugEnabled()) {
							this.log.debug("##### Search for music meta info product");
						}
						metaInfo = this.metaInfoService.getMusicMetaInfo(paramMap);
						if (metaInfo != null) {
							product = this.responseInfoGenerateFacade.generateMusicProduct(metaInfo);
							productListA.add(product);
						}
					}
				} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
					paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
					paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

					if (this.log.isDebugEnabled()) {
						this.log.debug("##### Search for Shopping  meta info product");
					}
					metaInfo = this.metaInfoService.getShoppingMetaInfo(paramMap);
					if (metaInfo != null) {
						product = this.responseInfoGenerateFacade.generateShoppingProduct(metaInfo);
						productListA.add(product);
					}
				}

				if (count == 1 || productCnt == 1) { // 최초
					theme = new Product();

					beforeThemeId = main.getThemeId();
					productCnt++;

					try {
						BeanUtils.copyProperties(theme, currTheme);
					} catch (Exception e) {
					}
					continue;
				} else if (beforeThemeId.equals(main.getThemeId())) { // 이전 테마 아이디와 현재 테마 아이디가 동일한 경우
					if (count == total) {
						try {
							productListB = new ArrayList<Product>();
							productListB.addAll(productListA.subList(0, productCnt));

							theme.setSubProductList(productListA);
							themeList.add(theme);
						} catch (Exception e) {
						}
					} else {
						beforeThemeId = main.getThemeId();
						productCnt++;
						continue;
					}
				} else if (!beforeThemeId.equals(main.getThemeId())) { // 이전 테마 아이디와 현재 테마 아이디가 동일하지 않은 경우
					beforeThemeId = main.getThemeId();
					try {
						productListB = new ArrayList<Product>();
						productListB.addAll(productListA.subList(0, productCnt));
						productListA.removeAll(productListB);

						theme.setSubProductList(productListB);
						themeList.add(theme);

						productCnt = 1;

					} catch (Exception e) {
					}
				} else { // 예외 상황
					throw new StorePlatformException("SAC_DSP_9999");
				}
			} // end of for loop
		} else {
			commonResponse.setTotalCount(0);
		}

		responseVO.setCommonResponse(commonResponse);
		responseVO.setProductList(themeList);
		return responseVO;
	}
}
