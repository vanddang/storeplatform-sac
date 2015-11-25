/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.appguide.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideApprankingSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideApprankingSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * Updated on : 2014. 3. 3. Updated by : 이승훈, 엔텔스.
 */
@Service
public class AppguideApprankingServiceImpl implements AppguideApprankingService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.feature.category.service.AppguideApprankingService#searchVodList(com.skplanet
	 * .storeplatform.sac.client.display.vo.feature.category.AppguideApprankingReq)
	 */
	@Override
	public AppguideApprankingSacRes searchApprankingList(AppguideApprankingSacReq req, SacRequestHeader header) {

		AppguideApprankingSacRes res = new AppguideApprankingSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String listId = req.getListId();
		String b2bProd = req.getB2bProd();

		// 시작점 ROW Default 세팅
		if (req.getOffset() == null) {
			req.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (req.getCount() == null) {
			req.setCount(20);
		}

		// B2B 상품 구분 구분 기본 설정
		if (StringUtils.isEmpty(b2bProd) || b2bProd == null) {
			req.setB2bProd("A");
		}

		// 헤더값 세팅
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setImageCd("DP000101"); // App Image Code
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(req.getTenantId(), listId);

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			throw new StorePlatformException("SAC_DSP_0002", "stdDt", stdDt);
		} else {
			req.setStdDt(stdDt);
		}

		// 유/무료 여부 설정
		if (listId.equals("RNK000000009") || listId.equals("RNK000000010") || listId.equals("RNK000000014")) {
			// 무료 여부 설정 RNK000000009 : 랭킹_ 무료_일간, RNK000000010 : 랭킹_ 무료_주간, RNK000000014 : 랭킹_신규상품(최신)_무료
			req.setProdCharge("N");
		} else if (listId.equals("RNK000000012") || listId.equals("RNK000000013") || listId.equals("RNK000000015")) {
			// 유료 여부 설정 RNK000000012 : 랭킹_ 유료_일간, RNK000000013 : 랭킹_ 유료_주간, RNK000000015 : 랭킹_ 신규상품(최신)_유료
			req.setProdCharge("Y");
		} else {
			// RNK000000011 : 랭킹_ 누적
			req.setProdCharge("A"); // 전체 기본 설정
		}

		List<ProductBasicInfo> productBasicInfoList;

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(header.getDeviceHeader().getModel());

		if (supportDevice != null) {
			req.setEbookSprtYn(supportDevice.getEbookSprtYn());
			req.setComicSprtYn(supportDevice.getComicSprtYn());
			req.setMusicSprtYn(supportDevice.getMusicSprtYn());
			req.setVideoDrmSprtYn(supportDevice.getVideoDrmSprtYn());
			req.setSdVideoSprtYn(supportDevice.getSdVideoSprtYn());
		}

		// 추천 리스트 조회( Meta 정보 조회를 위한 쇼핑 및 일반 상품( 앱, VOD, EBook/Comic, Music ) 조회
		productBasicInfoList = this.commonDAO.queryForList("AppguideAppranking.selectApprankingList", req,	ProductBasicInfo.class);

		List<Product> productList = new ArrayList<Product>();

		// Meta DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", stdDt);
		reqMap.put("lang", tenantHeader.getLangCd());

		reqMap.put("svcGrpCd", DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD);
		// reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		// 추천 리스트 조회시에는 앱 상품까지 조회하지만, 메타정보 조회시 서비스 그룹코드(svcGrpCd)를 멀티미디어 그룹코드 값을 넣어주기 때문에 앱 상품은 조회되지 않는다.
		if (!productBasicInfoList.isEmpty()) {

			Product product = null;

			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {

				String topMenuId = productBasicInfo.getTopMenuId();
				String svcGrpCd = productBasicInfo.getSvcGrpCd();

				reqMap.put("productBasicInfo", productBasicInfo);
				reqMap.put("req", req);
				reqMap.put("tenantHeader", tenantHeader);
				reqMap.put("deviceHeader", deviceHeader);
				reqMap.put("lang", tenantHeader.getLangCd());

				product = new Product(); // 결과물

				MetaInfo retMetaInfo = null;

				// APP 상품의 경우
				if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
					reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
						productList.add(product);
					}

				} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 멀티미디어 타입일 경우
					// 영화/방송 상품의 경우
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
						retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);

						if (retMetaInfo != null) {
							if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
								product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
							} else {
								product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
							}
							productList.add(product);
						}
					} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의 경우
						reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
						if (retMetaInfo != null) {
							if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
								product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
								productList.add(product);
							} else {
								product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
								productList.add(product);
							}
						}

					} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 음원 상품의 경우
						reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getMusicMetaInfo(reqMap);
						if (retMetaInfo != null) {
							// product = this.responseInfoGenerateFacade.generateSpecificMusicProduct(metaInfo);
							product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
							productList.add(product);
						}

					} else if (DisplayConstants.DP_WEBTOON_TOP_MENU_ID.equals(topMenuId)) { // WEBTOON 상품의 경우
						reqMap.put("imageCd", DisplayConstants.DP_WEBTOON_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getWebtoonMetaInfo(reqMap);
						if (retMetaInfo != null) {
							product = this.responseInfoGenerateFacade.generateWebtoonProduct(retMetaInfo);
							productList.add(product);
						}

					}
				} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
					reqMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
						productList.add(product);
					}
				}
			}
			commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
		}
		return res;
	}

}
