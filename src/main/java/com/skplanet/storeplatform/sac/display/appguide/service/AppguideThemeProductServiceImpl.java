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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideThemeProdSacReq;
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
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * App guide 테마 추천별 상품 목록 조회 Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 03. 06. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class AppguideThemeProductServiceImpl implements AppguideThemeProductService {

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
	 * com.skplanet.storeplatform.sac.biz.product.service.AppguideThemeProductServiceImpl#searchThemeRecommendProductList
	 * (AppguideThemeProdSacReq requestVO, SacRequestHeader requestHeader)
	 */
	@Override
	public AppguideSacRes searchThemeRecommendProductList(AppguideThemeProdSacReq requestVO,
			SacRequestHeader requestHeader) throws StorePlatformException {

		AppguideSacRes responseVO = new AppguideSacRes();
		List<Product> productList = new ArrayList<Product>();

		CommonResponse commonResponse = new CommonResponse();

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		Map<String, Object> mapReq = new HashMap<String, Object>();
		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("deviceHeader", deviceHeader);
		mapReq.put("virtualDeviceModel", DisplayConstants.DP_ANY_PHONE_4MM);

		mapReq.put("themeId", requestVO.getThemeId()); // 앱가이드 테마추천ID
		/*
		 * 상품이용등급코드 추가 2014.08.21 이석희 , 아이에스플러스
		 */
		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(requestVO.getProdGradeCd())) {
			String[] arrayProdGradeCd = requestVO.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(requestVO.getProdGradeCd())) {
			String[] arrayProdGradeCd = requestVO.getProdGradeCd().split("\\+");
			mapReq.put("arrayProdGradeCd", arrayProdGradeCd);
		}

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

		Appguide theme = this.commonDAO.queryForObject("Appguide.Theme.getThemeRecommendInfo", mapReq, Appguide.class);
		if (theme == null) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		// 테마 정보
		Product themeProduct = new Product();
		Identifier themeId = new Identifier();
		themeId.setText(theme.getThemeId());
		themeId.setType("theme");
		themeProduct.setIdentifier(themeId);

		Title themeNm = new Title();
		themeNm.setText(theme.getThemeNm());
		themeProduct.setTitle(themeNm);
		themeProduct.setThemeType(theme.getThemeType());

		if (!StringUtils.isEmpty(theme.getThemeImg())) {
			List<Source> themeUrlList = new ArrayList<Source>();
			Source themeUrl = new Source();
			themeUrl.setUrl(theme.getThemeImg());
			themeUrlList.add(themeUrl);
			themeProduct.setSourceList(themeUrlList);
		}

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(deviceHeader.getModel());
		if (supportDevice != null) {
			mapReq.put("ebookSprtYn", supportDevice.getEbookSprtYn());
			mapReq.put("comicSprtYn", supportDevice.getComicSprtYn());
			mapReq.put("musicSprtYn", supportDevice.getMusicSprtYn());
			mapReq.put("videoDrmSprtYn", supportDevice.getVideoDrmSprtYn());
			mapReq.put("sdVideoSprtYn", supportDevice.getSdVideoSprtYn());
			mapReq.put("sclShpgSprtYn", supportDevice.getSclShpgSprtYn());

			// 상품 기본 정보 List 조회
			List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
					"Appguide.Theme.getBasicThemeRecommendProductList", mapReq, ProductBasicInfo.class);

			if (this.log.isDebugEnabled()) {
				this.log.debug("##### selected product basic info cnt : {}", productBasicInfoList.size());
			}
			if (!productBasicInfoList.isEmpty()) {
				commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());

				Product product = null;
				MetaInfo metaInfo = null;

				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("tenantHeader", tenantHeader);
				paramMap.put("deviceHeader", deviceHeader);
				paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING); // 판매중

				// Meta 정보 조회
				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {

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
							productList.add(product);
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
								productList.add(product);
							}
						} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
								|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의 경우

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
								productList.add(product);
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
								productList.add(product);
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
							productList.add(product);
						}
					}
				}
			} else
				commonResponse.setTotalCount(0);
		} else
			commonResponse.setTotalCount(0);

		if (this.log.isDebugEnabled()) {
			this.log.debug("product count : {}", productList.size());
		}

		themeProduct.setSubProductList(productList);

		responseVO.setCommonResponse(commonResponse);
		responseVO.setProduct(themeProduct);

		return responseVO;
	}
}
