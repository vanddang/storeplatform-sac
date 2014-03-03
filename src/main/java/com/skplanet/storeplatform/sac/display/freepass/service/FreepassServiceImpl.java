/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.freepass.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassSeriesReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassSpecificReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.SeriespassListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.AutoPay;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Coupon;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.freepass.vo.FreepassProdMap;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * Freepass Service (CoreStoreBusiness)
 * 
 * Updated on : 2014. 2. 7. Updated by : 서영배, GTSOFT.
 */
@Service
public class FreepassServiceImpl implements FreepassService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	HistoryInternalSCI historyInternalSCI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.freepass.service.FreepassService#searchFreepassList(com.skplanet.storeplatform
	 * .sac.client.display.vo.freepass.FreepassListReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public FreepassListRes searchFreepassList(FreepassListReq req, SacRequestHeader header) {
		// TODO Auto-generated method stub

		// 공통 응답 변수 선언
		FreepassListRes responseVO = null;
		CommonResponse commonResponse = new CommonResponse();

		Coupon coupon = null;
		List<Coupon> couponList = new ArrayList<Coupon>();

		Map<String, Object> reqMap = new HashMap<String, Object>();
		List<ProductBasicInfo> productBasicInfoList = null;
		MetaInfo retMetaInfo = null;

		if (StringUtil.nvl(req.getDummy(), "").equals("")) {

			// 정액제 상품 목록 조회
			req.setTenantId(header.getTenantHeader().getTenantId());
			req.setLangCd(header.getTenantHeader().getLangCd());
			req.setDeviceModelCd(header.getDeviceHeader().getModel());
			req.setBannerImageCd(DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
			req.setThumbnailImageCd(DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
			req.setProdStatusCd(DisplayConstants.DP_PASS_SALE_STAT_ING);
			req.setStandardModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

			// 시작점 ROW Default 세팅
			if (req.getOffset() == 0) {
				req.setOffset(1);
			}
			// 페이지당 노출될 ROW 개수 Default 세팅
			if (req.getCount() == 0) {
				req.setCount(20);
			}

			if (StringUtils.isEmpty(req.getKind()))
				throw new StorePlatformException("SAC_DSP_0003", "kind", req.getKind());

			// 페이지당 노출될 ROW 개수 Default 세팅
			if ("All".equals(req.getKind())) {
				req.setKind("");
			}

			// '+'로 연결 된 상품등급코드를 배열로 전달
			if (StringUtils.isNotEmpty(req.getTopMenuId())) {
				try {
					// String[] arrTopMenuId = URLDecoder.decode(req.getTopMenuId(), "UTF-8").split("//+");
					String[] arrTopMenuId = StringUtils.split(req.getTopMenuId(), "+");
					req.setArrTopMenuId(arrTopMenuId);
				} catch (Exception e) {
					throw new StorePlatformException("SAC_DSP_0003", "topMenuId", req.getTopMenuId());
				}
			}

			productBasicInfoList = this.commonDAO.queryForList("Freepass.selectFreepassList", req,
					ProductBasicInfo.class);

			if (productBasicInfoList == null)
				throw new StorePlatformException("SAC_DSP_0009");

			// 정액제 상품 메타 조회
			if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
				reqMap.put("tenantHeader", header.getTenantHeader());
				reqMap.put("deviceHeader", header.getDeviceHeader());
				reqMap.put("bannerImageCd", DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
				reqMap.put("thumbnailImageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);

				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					retMetaInfo = this.metaInfoService.getFreepassMetaInfo(reqMap);
					coupon = this.responseInfoGenerateFacade.generateFreepassProduct(retMetaInfo);
					couponList.add(coupon);
					commonResponse.setTotalCount(productBasicInfo.getTotalCount());
				}
			}

		} else {
			couponList = this.getDummyCoupon();
			commonResponse.setTotalCount(couponList.size());
		}
		responseVO = new FreepassListRes();
		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.freepass.service.FreepassService#searchFreepassDetail(com.skplanet.
	 * storeplatform.sac.client.display.vo.freepass.FreepassListReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public FreepassDetailRes searchFreepassDetail(FreepassDetailReq req, SacRequestHeader header) {
		// TODO Auto-generated method stub

		// 공통 응답 변수 선언
		FreepassDetailRes responseVO = new FreepassDetailRes();
		CommonResponse commonResponse = new CommonResponse();

		Coupon coupon = null;
		Product product = null;
		List<FreepassProdMap> mapList = null;
		List<Product> productList = new ArrayList<Product>();

		Map<String, Object> reqMap = new HashMap<String, Object>();
		ProductBasicInfo productBasicInfo = new ProductBasicInfo();
		MetaInfo retMetaInfo = null;

		if (StringUtil.nvl(req.getDummy(), "").equals("")) {
			// 정액제 상품 상세 조회
			req.setTenantId(header.getTenantHeader().getTenantId());
			req.setLangCd(header.getTenantHeader().getLangCd());
			req.setDeviceModelCd(header.getDeviceHeader().getModel());
			req.setBannerImageCd(DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
			req.setThumbnailImageCd(DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
			req.setProdStatusCd(DisplayConstants.DP_PASS_SALE_STAT_ING);
			req.setStandardModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

			// 시작점 ROW Default 세팅
			if (req.getOffset() == 0) {
				req.setOffset(1);
			}
			// 페이지당 노출될 ROW 개수 Default 세팅
			if (req.getCount() == 0) {
				req.setCount(20);
			}

			if (StringUtils.isEmpty(req.getProductId()))
				throw new StorePlatformException("SAC_DSP_0003", "productId", req.getProductId());

			retMetaInfo = this.commonDAO.queryForObject("Freepass.selectFreepassDetail", req, MetaInfo.class);

			if (retMetaInfo == null)
				throw new StorePlatformException("SAC_DSP_0005", req.getProductId(), req.getProductId());

			// 상품 상태 조회 - 판매중,판매중지,판매종료가 아니면 노출 안함
			if (!DisplayConstants.DP_PASS_SALE_STAT_STOP.equals(retMetaInfo.getProdStatusCd())
					&& !DisplayConstants.DP_PASS_SALE_STAT_RESTRIC.equals(retMetaInfo.getProdStatusCd())
					&& !DisplayConstants.DP_PASS_SALE_STAT_ING.equals(retMetaInfo.getProdStatusCd())) {
				throw new StorePlatformException("SAC_DSP_0011", retMetaInfo.getProdStatusCd(),
						retMetaInfo.getProdStatusCd());
			}

			// 구매 여부 조회
			if (!StringUtils.isEmpty(req.getUserKey())) { // userKey가 있을 경우만
				HistoryListSacInRes historyListSacRes = this.getPrchsInfo(req, retMetaInfo);

				// 구매가 있을 경우 : 판매중지,판매중,팬매종료는 노출함
				if (historyListSacRes == null || historyListSacRes.getTotalCnt() <= 0) {
					if (DisplayConstants.DP_PASS_SALE_STAT_STOP.equals(retMetaInfo.getProdStatusCd())
							|| DisplayConstants.DP_PASS_SALE_STAT_RESTRIC.equals(retMetaInfo.getProdStatusCd())) {
						throw new StorePlatformException("SAC_DSP_0011", retMetaInfo.getProdStatusCd(),
								retMetaInfo.getProdStatusCd());
					}
				}
			}

			coupon = this.responseInfoGenerateFacade.generateFreepassProduct(retMetaInfo);

			mapList = this.commonDAO.queryForList("Freepass.selectFreepassMapProduct", req, FreepassProdMap.class);

			reqMap.put("tenantHeader", header.getTenantHeader());
			reqMap.put("deviceHeader", header.getDeviceHeader());
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

			for (FreepassProdMap prodMap : mapList) {

				productBasicInfo.setProdId(prodMap.getPartProdId());
				productBasicInfo.setTenantId(header.getTenantHeader().getTenantId());
				productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
				reqMap.put("productBasicInfo", productBasicInfo);

				commonResponse.setTotalCount(prodMap.getTotalCount());

				if ("DP13".equals(retMetaInfo.getTopMenuId())) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
					product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
				} else if ("DP14".equals(retMetaInfo.getTopMenuId())) {
					reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
					product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
				} else if ("DP17".equals(retMetaInfo.getTopMenuId())) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
					product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
				} else if ("DP18".equals(retMetaInfo.getTopMenuId())) {
					reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
					product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
				}
				productList.add(product);

			}

		} else {

			reqMap.put("tenantHeader", header.getTenantHeader());
			reqMap.put("deviceHeader", header.getDeviceHeader());
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
			reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			String[] prodIdList = { "H000043398", "H000043398", "H000043398" };

			for (int i = 0; i < prodIdList.length; i++) {
				productBasicInfo.setProdId(prodIdList[i]);
				productBasicInfo.setTenantId("S01");
				productBasicInfo.setContentsTypeCd("PD002501");
				reqMap.put("productBasicInfo", productBasicInfo);
				retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
				product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
				productList.add(product);
			}

			List<Coupon> couponList;
			couponList = this.getDummyCoupon();
			coupon = couponList.get(0);

			commonResponse = new CommonResponse();
			commonResponse.setTotalCount(productList.size());
		}

		responseVO.setCommonResponse(commonResponse);
		responseVO.setCoupon(coupon);
		responseVO.setProductList(productList);

		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.freepass.service.FreepassService#searchSeriesPassList(com.skplanet.
	 * storeplatform.sac.client.display.vo.freepass.FreepassSeriesReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public SeriespassListRes searchSeriesPassList(FreepassSeriesReq req, SacRequestHeader header) {
		// TODO Auto-generated method stub

		// 공통 응답 변수 선언
		SeriespassListRes responseVO = new SeriespassListRes();
		CommonResponse commonResponse = new CommonResponse();

		Coupon coupon = null;
		Product product = null;
		List<Product> productList = new ArrayList<Product>();

		Map<String, Object> reqMapC = new HashMap<String, Object>();
		Map<String, Object> reqMapP = new HashMap<String, Object>();
		List<ProductBasicInfo> couponBasicInfoList = null;
		ProductBasicInfo productBasicInfo = null;
		MetaInfo retMetaInfo = null;
		this.log.debug("1111");
		if (StringUtil.nvl(req.getDummy(), "").equals("")) {

			// 정액제 상품 목록 조회
			req.setTenantId(header.getTenantHeader().getTenantId());
			req.setLangCd(header.getTenantHeader().getLangCd());
			req.setDeviceModelCd(header.getDeviceHeader().getModel());
			req.setBannerImageCd(DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
			req.setThumbnailImageCd(DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
			req.setProdStatusCd(DisplayConstants.DP_PASS_SALE_STAT_ING);
			req.setStandardModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
			req.setKind("OR004302");

			// 시작점 ROW Default 세팅
			if (req.getOffset() == 0) {
				req.setOffset(1);
			}
			// 페이지당 노출될 ROW 개수 Default 세팅
			if (req.getCount() == 0) {
				req.setCount(20);
			}

			couponBasicInfoList = this.commonDAO.queryForList("Freepass.selectFreepassSeries", req,
					ProductBasicInfo.class);

			if (couponBasicInfoList == null)
				throw new StorePlatformException("SAC_DSP_0009");

			// 정액제 상품 메타 조회
			if (couponBasicInfoList != null && couponBasicInfoList.size() > 0) {
				reqMapC.put("tenantHeader", header.getTenantHeader());
				reqMapC.put("deviceHeader", header.getDeviceHeader());
				reqMapC.put("bannerImageCd", DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
				reqMapC.put("thumbnailImageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);

				reqMapP.put("tenantHeader", header.getTenantHeader());
				reqMapP.put("deviceHeader", header.getDeviceHeader());
				reqMapP.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

				for (ProductBasicInfo couponBasicInfo : couponBasicInfoList) {
					reqMapC.put("productBasicInfo", couponBasicInfo);
					retMetaInfo = this.metaInfoService.getFreepassMetaInfo(reqMapC);
					coupon = this.responseInfoGenerateFacade.generateFreepassProduct(retMetaInfo);

					productBasicInfo = new ProductBasicInfo();
					productBasicInfo.setProdId(couponBasicInfo.getPartProdId());
					productBasicInfo.setTenantId(header.getTenantHeader().getTenantId());
					productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
					reqMapP.put("productBasicInfo", productBasicInfo);

					commonResponse.setTotalCount(couponBasicInfo.getTotalCount());
					// 상품메타 정보 조회
					if ("DP13".equals(couponBasicInfo.getTopMenuId())) {
						reqMapP.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMapP);
						product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
					} else if ("DP14".equals(couponBasicInfo.getTopMenuId())) {
						reqMapP.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMapP);
						product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
					} else if ("DP17".equals(couponBasicInfo.getTopMenuId())) {
						reqMapP.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMapP);
						product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
					} else if ("DP18".equals(couponBasicInfo.getTopMenuId())) {
						reqMapP.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMapP);
						product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
					}
					product.setCoupon(coupon);
					productList.add(product);
				}
			}

		} else {
			// 더미용 데이터 제공
			reqMapP.put("tenantHeader", header.getTenantHeader());
			reqMapP.put("deviceHeader", header.getDeviceHeader());
			reqMapP.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
			reqMapP.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			String[] prodIdList = { "H000043398", "H000043398", "H000043398" };

			List<Coupon> couponList = this.getDummyCoupon();
			coupon = couponList.get(0);

			productBasicInfo = new ProductBasicInfo();
			for (int i = 0; i < prodIdList.length; i++) {
				productBasicInfo.setProdId(prodIdList[i]);
				productBasicInfo.setTenantId("S01");
				productBasicInfo.setContentsTypeCd("PD002501");
				reqMapP.put("productBasicInfo", productBasicInfo);
				retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMapP);
				product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
				product.setCoupon(coupon);
				productList.add(product);
			}

			commonResponse.setTotalCount(couponList.size());

		}

		responseVO.setCommonResponse(commonResponse);
		responseVO.setProductList(productList);

		return responseVO;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.freepass.service.FreepassService#searchFreepassListByChannel(com.skplanet
	 * .storeplatform.sac.client.display.vo.freepass.FreepassSpecificReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public FreepassListRes searchFreepassListByChannel(FreepassSpecificReq req, SacRequestHeader header) {
		// 특정 상품에 적용할 자유 이용권 조회
		FreepassListRes responseVO = null;
		CommonResponse commonResponse = new CommonResponse();

		Coupon coupon = null;
		List<Coupon> couponList = new ArrayList<Coupon>();

		Map<String, Object> reqMap = new HashMap<String, Object>();
		List<ProductBasicInfo> productBasicInfoList = null;
		MetaInfo retMetaInfo = null;

		if (StringUtil.nvl(req.getDummy(), "").equals("")) {

			// 정액제 상품 목록 조회
			req.setTenantId(header.getTenantHeader().getTenantId());
			req.setLangCd(header.getTenantHeader().getLangCd());
			req.setDeviceModelCd(header.getDeviceHeader().getModel());
			req.setBannerImageCd(DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
			req.setThumbnailImageCd(DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
			req.setProdStatusCd(DisplayConstants.DP_PASS_SALE_STAT_ING);
			req.setStandardModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
			req.setProdRshpCd(DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
			req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(header.getTenantHeader().getTenantId())) {
				throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
			}
			if (StringUtils.isEmpty(req.getProductId())) {
				throw new StorePlatformException("SAC_DSP_0002", "productId", req.getProductId());
			}

			// 시작점 ROW Default 세팅
			if (req.getOffset() == 0) {
				req.setOffset(1);
			}
			// 페이지당 노출될 ROW 개수 Default 세팅
			if (req.getCount() == 0) {
				req.setCount(20);
			}

			// '+'로 연결 된 상품등급코드를 배열로 전달
			if (StringUtils.isNotEmpty(req.getKind())) {
				try {

					// String[] arrKind = URLDecoder.decode(req.getKind(), "UTF-8").split("//+");
					String[] arrKind = StringUtils.split(req.getKind(), "+");
					req.setArrKind(arrKind);
				} catch (Exception e) {
					throw new StorePlatformException("SAC_DSP_0003", "kind", req.getKind());
				}
			}

			if (StringUtils.equalsIgnoreCase(req.getKind(), "All")) {
				req.setKind("");
			}
			productBasicInfoList = this.commonDAO.queryForList("Freepass.searchFreepassListByChannel", req,
					ProductBasicInfo.class);

			// 정액제 상품 메타 조회
			if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
				reqMap.put("tenantHeader", header.getTenantHeader());
				reqMap.put("deviceHeader", header.getDeviceHeader());
				reqMap.put("bannerImageCd", DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
				reqMap.put("thumbnailImageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);

				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					retMetaInfo = this.metaInfoService.getFreepassMetaInfo(reqMap);
					coupon = this.responseInfoGenerateFacade.generateFreepassProduct(retMetaInfo);
					couponList.add(coupon);
					commonResponse.setTotalCount(productBasicInfo.getTotalCount());
				}
			}

		} else {
			couponList = this.getDummyCoupon();
			commonResponse.setTotalCount(couponList.size());
		}
		responseVO = new FreepassListRes();
		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		return responseVO;
	}

	/**
	 * <pre>
	 * 임시 더미용.
	 * </pre>
	 * 
	 * @return List
	 * 
	 */
	public List<Coupon> getDummyCoupon() {
		// TODO Auto-generated method stub
		Identifier identifier;
		Title title;

		Source source;
		Price price;

		Coupon coupon;
		AutoPay autoPay;
		Date date;

		// Response VO를 만들기위한 생성자
		// List<Product> productList = new ArrayList<Product>();

		List<Source> sourceList;
		List<Identifier> identifierList;
		List<Coupon> couponList = new ArrayList<Coupon>();

		for (int i = 0; i < 3; i++) {
			// 상품ID
			identifier = new Identifier();
			title = new Title();

			source = new Source();
			price = new Price();

			coupon = new Coupon();
			autoPay = new AutoPay();
			date = new Date();

			// Response VO를 만들기위한 생성자

			sourceList = new ArrayList<Source>();

			identifierList = new ArrayList<Identifier>();

			identifier.setType("freepass");
			identifier.setText("FR9010117" + i);
			identifierList.add(identifier);

			coupon.setKind("OR004301");
			coupon.setCouponExplain("특선영화 30일 자유이용권");

			price.setText(6500);

			autoPay.setType("auto");

			date.setType("duration/usagePeriod");
			date.setText("2013-10-06 14:22:51/2015-02-06 14:22:51");

			title.setText("특선영화 30일 자유이용권");
			title.setAlias("특선영화 30일 자유이용권");

			source.setType("banner");
			source.setUrl("/data4/img/original/0000258524_DP000101.png");
			sourceList.add(source);

			source = new Source();
			source.setType("thumnail");
			source.setUrl("/data4/img/original/0000258524_DP000101.png");
			sourceList.add(source);

			coupon.setAutopay(autoPay);
			coupon.setDate(date);
			coupon.setPrice(price);
			coupon.setSourceList(sourceList);
			coupon.setTitle(title);
			coupon.setIdentifierList(identifierList);

			couponList.add(coupon);
		}
		return couponList;
	}

	/**
	 * <pre>
	 * 기구매 체크.
	 * </pre>
	 * 
	 * @param req
	 *            FreepassDetailReq
	 * @param metaInfo
	 *            MetaInfo
	 * @return HistoryCountSacInRes
	 * 
	 */
	public HistoryListSacInRes getPrchsInfo(FreepassDetailReq req, MetaInfo metaInfo) {

		// 구매내역 조회를 위한 생성자
		ProductListSacIn productListSacIn = new ProductListSacIn();
		List<ProductListSacIn> productList = new ArrayList<ProductListSacIn>();
		HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
		HistoryListSacInRes historyListSacRes;

		try {
			// 정액제 상품ID
			productListSacIn.setProdId(metaInfo.getProdId());
			productList.add(productListSacIn);

			historyListSacReq.setTenantId(req.getTenantId());
			historyListSacReq.setUserKey(req.getUserKey());
			historyListSacReq.setDeviceKey(req.getDeviceKey());
			historyListSacReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
			historyListSacReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
			historyListSacReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
			historyListSacReq.setEndDt(metaInfo.getSysDate());
			historyListSacReq.setProductList(productList);

			// 구매내역 조회 실행
			//historyCountSacRes = this.historyInternalSCI.searchHistoryCount(historyCountSacReq);
			historyListSacRes = this.historyInternalSCI.searchHistoryList(historyListSacReq);
		} catch (Exception ex) {
			throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
		}
		return historyListSacRes;
	}

}
