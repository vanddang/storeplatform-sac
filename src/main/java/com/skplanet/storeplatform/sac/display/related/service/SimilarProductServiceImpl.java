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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
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
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private MemberBenefitService benefitService;

	/**
	 * 
	 * <pre>
	 * 유사 상품 리스트 조회.
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
		if (!StringUtils.isEmpty(requestVO.getExceptId())) {
			requestVO.setArrayExceptId(StringUtils.split(requestVO.getExceptId(), "+"));
		}

		SimilarProductSacRes similarProductSacRes = new SimilarProductSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		MetaInfo retMetaInfo = null;
		Product product = null;

		List<ProductBasicInfo> similarProductList = new ArrayList<ProductBasicInfo>();
		List<Product> productList = null;

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(requestHeader.getDeviceHeader()
				.getModel());

		if (supportDevice != null) {
			requestVO.setEbookSprtYn(supportDevice.getEbookSprtYn());
			requestVO.setComicSprtYn(supportDevice.getComicSprtYn());
			requestVO.setMusicSprtYn(supportDevice.getMusicSprtYn());
			requestVO.setVideoDrmSprtYn(supportDevice.getVideoDrmSprtYn());
			requestVO.setSdVideoSprtYn(supportDevice.getSdVideoSprtYn());

			String plus19Yn = this.getPlus19Yn(requestVO.getProductId());

			requestVO.setPlus19Yn(plus19Yn); // 19+상품 여부
			// 이 상품과 유사 상품 조회
			this.log.debug("이 상품과 유사 상품 조회");
			similarProductList = this.commonDAO.queryForList("SimilarProduct.selectSimilarProductList", requestVO,
					ProductBasicInfo.class);

		}

		if (!similarProductList.isEmpty()) {
			productList = new ArrayList<Product>();

			reqMap.put("tenantHeader", requestHeader.getTenantHeader());
			reqMap.put("deviceHeader", requestHeader.getDeviceHeader());
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

			for (ProductBasicInfo productBasicInfo : similarProductList) {

				reqMap.put("productBasicInfo", productBasicInfo);

				if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_COMIC_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_MOVIE_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_TV_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_MUSIC_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.commonDAO.queryForObject("RelatedProduct.selectMusicMetaInfo", reqMap,
							MetaInfo.class); // 뮤직 메타
					// retMetaInfo = this.metaInfoService.getMusicMetaInfo(reqMap); // 뮤직 공통 메타
					if (retMetaInfo != null) {
						// Tstore멤버십 적립율 정보
						MileageInfo mileageInfo = this.benefitService.getMileageInfo(requestHeader.getTenantHeader()
								.getTenantId(), retMetaInfo.getTopMenuId(), retMetaInfo.getProdId(), retMetaInfo
								.getProdAmt());
						List<Point> pointList = this.commonGenerator.generateMileage(mileageInfo);

						product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
						product.setPointList(pointList);
						product.setAccrual(this.commonGenerator.generateAccrual(retMetaInfo)); // 통계 건수 재정의
						product.setProductExplain(retMetaInfo.getProdBaseDesc()); // 상품 설명
						productList.add(product);
					}
				} else {
					reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);
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
		if (!StringUtils.isEmpty(requestVO.getExceptId())) {
			requestVO.setArrayExceptId(StringUtils.split(requestVO.getExceptId(), "+"));
		}

		// prodGradeCd 배열로 변경
		if (!StringUtils.isEmpty(requestVO.getProdGradeCd())) {
			String[] prodGradeCdArr = StringUtils.split(requestVO.getProdGradeCd(), "+");
			requestVO.setProdGradeCdArr(prodGradeCdArr);
		}

		SimilarProductSacRes similarProductSacRes = new SimilarProductSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		MetaInfo retMetaInfo = null;
		Product product = null;

		List<ProductBasicInfo> similarProductList = new ArrayList<ProductBasicInfo>();
		List<Product> productList = null;

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(requestHeader.getDeviceHeader()
				.getModel());

		if (supportDevice != null) {
			requestVO.setEbookSprtYn(supportDevice.getEbookSprtYn());
			requestVO.setComicSprtYn(supportDevice.getComicSprtYn());
			requestVO.setMusicSprtYn(supportDevice.getMusicSprtYn());
			requestVO.setVideoDrmSprtYn(supportDevice.getVideoDrmSprtYn());
			requestVO.setSdVideoSprtYn(supportDevice.getSdVideoSprtYn());
			String plus19Yn = this.getPlus19Yn(requestVO.getProductId());

			requestVO.setPlus19Yn(plus19Yn); // 19+상품 여부

			// 이 상품과 유사 상품 조회
			this.log.debug("이 상품과 유사 상품 조회");
			similarProductList = this.commonDAO.queryForList("SimilarProduct.selectSimilarProductListV2", requestVO,
					ProductBasicInfo.class);

		}

		if (!similarProductList.isEmpty()) {
			productList = new ArrayList<Product>();

			reqMap.put("tenantHeader", requestHeader.getTenantHeader());
			reqMap.put("deviceHeader", requestHeader.getDeviceHeader());
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

			for (ProductBasicInfo productBasicInfo : similarProductList) {

				reqMap.put("productBasicInfo", productBasicInfo);

				if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_EBOOK_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_COMIC_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_MOVIE_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_TV_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
					if (retMetaInfo != null) {
						product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
						productList.add(product);
					}
				} else if (productBasicInfo.getTopMenuId().equals(DisplayConstants.DP_MUSIC_TOP_MENU_ID)) {
					reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.commonDAO.queryForObject("RelatedProduct.selectMusicMetaInfo", reqMap,
							MetaInfo.class); // 뮤직 메타
					// retMetaInfo = this.metaInfoService.getMusicMetaInfo(reqMap); // 뮤직 공통 메타
					if (retMetaInfo != null) {
						// Tstore멤버십 적립율 정보
						MileageInfo mileageInfo = this.benefitService.getMileageInfo(requestHeader.getTenantHeader()
								.getTenantId(), retMetaInfo.getTopMenuId(), retMetaInfo.getProdId(), retMetaInfo
								.getProdAmt());
						List<Point> pointList = this.commonGenerator.generateMileage(mileageInfo);

						product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
						product.setPointList(pointList);
						product.setAccrual(this.commonGenerator.generateAccrual(retMetaInfo)); // 통계 건수 재정의
						product.setProductExplain(retMetaInfo.getProdBaseDesc()); // 상품 설명
						productList.add(product);
					}
				} else {
					reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);
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

	public String getPlus19Yn(String prodId) {
		String prodGrdExtraYn = (String) this.commonDAO.queryForObject("SimilarProduct.getPlus19Yn", prodId);

		return prodGrdExtraYn;
	}
}
