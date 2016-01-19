/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.related.service;

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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarProductSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * Similar Product Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 10. 24. Updated by : SP Tek 백승현
 */
@Service
public class SimilarProductServiceImpl implements SimilarProductService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private DisplayCommonService displayCommonService;

	/**
	 * 
	 * <pre>
	 * 유사 상품 리스트 조회.
	 * 상품 ID(기준 상품ID)로 유사 상품을 TB_DP_SMLR_PROD_MANG.SCORE 컬럼의 값이 높은 순으로 정렬하여 조회한다.
	 * ISF 연동(DailyISFSimilarProdMain Batch) 데이터 참조
	 * </pre>
	 * 
	 * @param requestVO
	 *            SimilarProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return SimilarProductSacRes
	 */
	@Override
	public SimilarProductSacRes searchSimilarProductList(SimilarProductSacReq requestVO, SacRequestHeader requestHeader) {

		// 헤더 값 세팅
		this.log.debug("헤더 값 세팅");
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setLangCd(requestHeader.getTenantHeader().getLangCd());
		requestVO.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());
		requestVO.setMmDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		// 요청 값 세팅
		this.log.debug("요청 값 세팅");
		requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
		requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);
		
		//요청 Parameter에 조회시 제외 ID 값이 존재하면 제외 ID값 중 + 기호를 제외하고 배열의 형태로 Parameter 재생성한다. 		
		if (!StringUtils.isEmpty(requestVO.getExceptId())) {
			requestVO.setArrayExceptId(StringUtils.split(requestVO.getExceptId(), "+"));
		}

		SimilarProductSacRes similarProductSacRes = new SimilarProductSacRes(); // 이 상품과 유사 상품 조회 API의 응답 규격 위한 최종 변수(상품의 meta 정보 포함)
		CommonResponse commonResponse = new CommonResponse();
		Map<String, Object> reqMap = new HashMap<String, Object>(); // Meta 조회를 위한 요청 Parameter를 담을 Map 변수
		MetaInfo retMetaInfo = null; // Meta 조회 데이터를 담을 변수
		Product product = null; // 상품 변수

		List<ProductBasicInfo> similarProductList = new ArrayList<ProductBasicInfo>(); // TB_DP_SMLR_PROD_MANG(유사 상품)에서 조회한 상품 기본정보 List 변수
		List<Product> productList = null; // 상품 List

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(requestHeader.getDeviceHeader().getModel());

		//단말 지원정보가 존재하면
		if (supportDevice != null) {
			requestVO.setEbookSprtYn(supportDevice.getEbookSprtYn()); // eBook 상품 지원여부
			requestVO.setComicSprtYn(supportDevice.getComicSprtYn()); // Comic 상품 지원여부
			requestVO.setMusicSprtYn(supportDevice.getMusicSprtYn()); // 음악 상품 지원여부
			requestVO.setVideoDrmSprtYn(supportDevice.getVideoDrmSprtYn()); // VOD 상품 DRM 지원여부
			requestVO.setSdVideoSprtYn(supportDevice.getSdVideoSprtYn()); // VOD 상품 SD 지원여부			

			// 요청 상품 ID로 19+상품여부 조회 (Y : 19+ 상품 , N : 19+가 아닌 상품)
			// 조회된 상품이 19+ 상품이면 유사 상품 조회시 19+상품을 포함하여 유사 상품들을 조회
			// 19+상품이 아닌경우 유사 상품 조회시 19+상품을 제외하고 유사 상품들을 조회
			String plus19Yn = this.getPlus19Yn(requestVO.getProductId()); 

			requestVO.setPlus19Yn(plus19Yn); // 19+상품 여부
			
			// 이 상품과 유사 상품 조회 - TB_DP_SMLR_PROD_MANG.SCORE 컬럼의 값이 높은 순으로 정렬된 Data
			this.log.debug("이 상품과 유사 상품 조회");
			similarProductList = this.commonDAO.queryForList("SimilarProduct.selectSimilarProductList", requestVO,	ProductBasicInfo.class);
		}

		if (!similarProductList.isEmpty()) {
			productList = new ArrayList<Product>();

			reqMap.put("tenantHeader", requestHeader.getTenantHeader());
			reqMap.put("deviceHeader", requestHeader.getDeviceHeader());
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING); // 현재 판매중인 상품만 조회함

			for (ProductBasicInfo productBasicInfo : similarProductList) {

				reqMap.put("productBasicInfo", productBasicInfo);

				if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap); // Ebook Meta 정보 조회
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo); 
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_COMIC_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD); 
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap); // Comic Meta 정보 조회
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_MOVIE_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD); 
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap); // 영화 Meta 정보 조회
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_TV_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);  // TV/방송 Meta 정보 조회
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_MUSIC_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.commonDAO.queryForObject("RelatedProduct.selectMusicMetaInfo", reqMap,MetaInfo.class); // 뮤직 메타
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
						productList.add(product);
					}
				} else {
					reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap); // App Meta 정보 조회
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
						productList.add(product);
					}
				}
			}
			commonResponse.setTotalCount(similarProductList.get(0).getTotalCount());
			similarProductSacRes.setProductList(productList);
		} else {
			commonResponse.setTotalCount(0);
		}
		this.log.debug("이 상품과 유사 상품 조회 결과 : " + commonResponse.getTotalCount() + "건");
		similarProductSacRes.setCommonResponse(commonResponse);
		return similarProductSacRes;
	}

	/**
	 * 
	 * <pre>
	 * 유사 상품 리스트 조회 V2.
	 * 상품 ID(기준 상품ID)로 유사 상품을 TB_DP_SMLR_PROD_MANG.SCORE 컬럼의 값이 높은 순으로 정렬하여 조회한다.
	 * ISF 연동(DailyISFSimilarProdMain Batch) 데이터 참조
	 * </pre>
	 * 
	 * @param requestVO
	 *            SimilarProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return SimilarProductSacRes
	 */
	@Override
	public SimilarProductSacRes searchSimilarProductListV2(SimilarProductSacReq requestVO,
			SacRequestHeader requestHeader) {

		// 헤더 값 세팅
		this.log.debug("헤더 값 세팅");
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setLangCd(requestHeader.getTenantHeader().getLangCd());
		requestVO.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());
		requestVO.setMmDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		// 요청 값 세팅
		this.log.debug("요청 값 세팅");
		requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
		requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);
		
		//요청 Parameter에 조회시 제외 ID 값이 존재하면 제외 ID값 중 + 기호를 제외하고 배열의 형태로 Parameter 재생성한다.
		if (!StringUtils.isEmpty(requestVO.getExceptId())) {
			requestVO.setArrayExceptId(StringUtils.split(requestVO.getExceptId(), "+"));
		}

		// prodGradeCd(상품 이용등급코드) 배열로 변경
		if (!StringUtils.isEmpty(requestVO.getProdGradeCd())) {
			String[] prodGradeCdArr = StringUtils.split(requestVO.getProdGradeCd(), "+");
			requestVO.setProdGradeCdArr(prodGradeCdArr);
		}

		SimilarProductSacRes similarProductSacRes = new SimilarProductSacRes(); // 이 상품과 유사 상품 조회 API의 응답 규격 위한 최종 변수(상품의 meta 정보 포함)
		CommonResponse commonResponse = new CommonResponse();
		Map<String, Object> reqMap = new HashMap<String, Object>(); // Meta 조회를 위한 요청 Parameter를 담을 Map 변수
		MetaInfo retMetaInfo = null; // Meta 조회 데이터를 담을 변수
		Product product = null; // 상품 변수

		List<ProductBasicInfo> similarProductList = new ArrayList<ProductBasicInfo>(); // TB_DP_SMLR_PROD_MANG(유사 상품)에서 조회한 상품 기본정보 List 변수
		List<Product> productList = null; // 상품 List 변수

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(requestHeader.getDeviceHeader().getModel());

		// 단말 지원정보가 존재 하면
		if (supportDevice != null) {
			requestVO.setEbookSprtYn(supportDevice.getEbookSprtYn()); // eBook 상품 지원여부
			requestVO.setComicSprtYn(supportDevice.getComicSprtYn()); // Comic 상품 지원여부
			requestVO.setMusicSprtYn(supportDevice.getMusicSprtYn()); // 음악 상품 지원여부
			requestVO.setVideoDrmSprtYn(supportDevice.getVideoDrmSprtYn()); // VOD 상품 DRM 지원여부
			requestVO.setSdVideoSprtYn(supportDevice.getSdVideoSprtYn()); // VOD 상품 SD 지원여부
			
			// 요청 상품 ID로 19+상품여부 조회 (Y : 19+ 상품 , N : 19+가 아닌 상품)
			// 조회된 상품이 19+ 상품이면 유사 상품 조회시 19+상품을 포함하여 유사 상품들을 조회
			// 19+상품이 아닌경우 유사 상품 조회시 19+상품을 제외하고 유사 상품들을 조회
			String plus19Yn = this.getPlus19Yn(requestVO.getProductId());

			requestVO.setPlus19Yn(plus19Yn); // 19+상품 여부

			// 이 상품과 유사 상품 조회 V2 - TB_DP_SMLR_PROD_MANG.SCORE 컬럼의 값이 높은 순으로 정렬된 Data
			this.log.debug("이 상품과 유사 상품 조회 V2");
			similarProductList = this.commonDAO.queryForList("SimilarProduct.selectSimilarProductListV2", requestVO, ProductBasicInfo.class);

		}

		if (!similarProductList.isEmpty()) {
			productList = new ArrayList<Product>();

			reqMap.put("tenantHeader", requestHeader.getTenantHeader());
			reqMap.put("deviceHeader", requestHeader.getDeviceHeader());
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING); // 현재 판매중인 상품만 조회함

			for (ProductBasicInfo productBasicInfo : similarProductList) {

				reqMap.put("productBasicInfo", productBasicInfo);

				if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap); // Ebook Meta 정보 조회
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_COMIC_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap); // Comic Meta 정보 조회
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_MOVIE_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap); // 영화 Meta 정보 조회
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_TV_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap); // TV/방송 Meta 정보 조회
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_MUSIC_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.commonDAO.queryForObject("RelatedProduct.selectMusicMetaInfo", reqMap, MetaInfo.class); // 뮤직 메타
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
						productList.add(product);
					}
				} else {
					reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap); // App Meta 정보 조회
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
						productList.add(product);
					}
				}
			}
			commonResponse.setTotalCount(similarProductList.get(0).getTotalCount());
			similarProductSacRes.setProductList(productList);
		} else {
			commonResponse.setTotalCount(0);
		}
		this.log.debug("이 상품과 유사 상품 조회 결과 : " + commonResponse.getTotalCount() + "건");
		similarProductSacRes.setCommonResponse(commonResponse);
		return similarProductSacRes;
	}

	
	/**
	 * 
	 * <pre>
	 * 19+ 상품 여부 조회.
	 * </pre>
	 * 
	 * @param prodId
	 * @return prodGrdExtraYn
	 */
	public String getPlus19Yn(String prodId) {
		String prodGrdExtraYn = (String) this.commonDAO.queryForObject("SimilarProduct.getPlus19Yn", prodId);

		return prodGrdExtraYn;
	}
}
