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
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.GradeInfoSac;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Coupon;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;
import com.skplanet.storeplatform.sac.display.freepass.vo.FreepassProdMap;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
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

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private MemberBenefitService benefitService;

	@Autowired
	private CommonMetaInfoGenerator metaInfoGenerator;

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

		productBasicInfoList = this.commonDAO.queryForList("Freepass.selectFreepassList", req, ProductBasicInfo.class);

		if (productBasicInfoList == null)
			throw new StorePlatformException("SAC_DSP_0009");

		// 정액제 상품 메타 조회
		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
			reqMap.put("tenantHeader", header.getTenantHeader());
			reqMap.put("deviceHeader", header.getDeviceHeader());
			reqMap.put("bannerImageCd", DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
			reqMap.put("thumbnailImageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
			reqMap.put("ebookThumbnailImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				reqMap.put("productBasicInfo", productBasicInfo);
				retMetaInfo = this.metaInfoService.getFreepassMetaInfo(reqMap);
				coupon = this.responseInfoGenerateFacade.generateFreepassProduct(retMetaInfo);
				couponList.add(coupon);
				commonResponse.setTotalCount(productBasicInfo.getTotalCount());
			}
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

		// 정액제 상품 상세 조회
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setBannerImageCd(DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
		req.setThumbnailImageCd(DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
		req.setEbookThumbnailImageCd(DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
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

		retMetaInfo = this.commonDAO.queryForObject("Freepass.selectFreepassDetail", req, MetaInfo.class);

		if (retMetaInfo == null)
			throw new StorePlatformException("SAC_DSP_0009", req.getProductId(), req.getProductId());

		// 상품 상태 조회 - 판매중,판매중지,판매종료가 아니면 노출 안함
		if (!DisplayConstants.DP_PASS_SALE_STAT_STOP.equals(retMetaInfo.getProdStatusCd())
				&& !DisplayConstants.DP_PASS_SALE_STAT_RESTRIC.equals(retMetaInfo.getProdStatusCd())
				&& !DisplayConstants.DP_PASS_SALE_STAT_ING.equals(retMetaInfo.getProdStatusCd())) {
			throw new StorePlatformException("SAC_DSP_0011", retMetaInfo.getProdStatusCd(),
					retMetaInfo.getProdStatusCd());
		}

		// 구매 여부 조회
		if (!StringUtils.isEmpty(req.getUserKey())) { // userKey가 있을 경우만
			// 공통 메서드로 변경 20140424
			boolean purchaseYn = this.displayCommonService.checkPurchase(req.getTenantId(), req.getUserKey(),
					req.getDeviceKey(), req.getProductId());

			// 구매가 있을 경우 : 판매중지,판매중,판매종료는 노출함
			if (!purchaseYn) {
				if (DisplayConstants.DP_PASS_SALE_STAT_STOP.equals(retMetaInfo.getProdStatusCd())
						|| DisplayConstants.DP_PASS_SALE_STAT_RESTRIC.equals(retMetaInfo.getProdStatusCd())) {
					throw new StorePlatformException("SAC_DSP_0011", retMetaInfo.getProdStatusCd(),
							retMetaInfo.getProdStatusCd());
				}
			}
		}

		coupon = this.responseInfoGenerateFacade.generateFreepassProduct(retMetaInfo);

		// 티멤버십 DC 정보
		TmembershipDcInfo info = this.displayCommonService.getTmembershipDcRateForMenu(header.getTenantHeader()
				.getTenantId(), retMetaInfo.getTopMenuId());
		List<Point> pointList = this.commonGenerator.generatePoint(info);
		// Tstore멤버십 적립율 정보
		// 정액제 패스/시리즈 패스만 조회
		if ((StringUtils.equals(DisplayConstants.FIXRATE_PROD_TYPE_VOD_FIXRATE, retMetaInfo.getCmpxProdClsfCd()) || StringUtils
				.equals(DisplayConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS, retMetaInfo.getCmpxProdClsfCd()))
				&& StringUtils.isNotEmpty(req.getUserKey())) {
			// 회원등급 조회
			GradeInfoSac userGradeInfo = this.displayCommonService.getUserGrade(req.getUserKey());
			if (userGradeInfo != null) {
				if (pointList == null)
					pointList = new ArrayList<Point>();
				String userGrade = userGradeInfo.getUserGradeCd();
				MileageInfo mileageInfo = this.benefitService.getMileageInfo(req.getTenantId(),
						retMetaInfo.getTopMenuId(), req.getChannelId(), retMetaInfo.getProdAmt());
				pointList.addAll(this.metaInfoGenerator.generateMileage(mileageInfo, userGrade));
			}
		}
		coupon.setPointList(pointList);

		mapList = this.commonDAO.queryForList("Freepass.selectFreepassMapProduct", req, FreepassProdMap.class);

		reqMap.put("tenantHeader", header.getTenantHeader());
		reqMap.put("deviceHeader", header.getDeviceHeader());
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		int minusCount = 0;

		for (FreepassProdMap prodMap : mapList) {

			productBasicInfo.setProdId(prodMap.getPartProdId());
			productBasicInfo.setTenantId(header.getTenantHeader().getTenantId());
			productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
			reqMap.put("productBasicInfo", productBasicInfo);

			commonResponse.setTotalCount(prodMap.getTotalCount());

			if ("DP13".equals(prodMap.getTopMenuId())) {
				reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
				retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
				if (retMetaInfo == null) {
					minusCount += 1;
					continue;
				} else
					product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
			} else if ("DP14".equals(prodMap.getTopMenuId())) {
				reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
				retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
				if (retMetaInfo == null) {
					minusCount += 1;
					continue;
				} else
					product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
			} else if ("DP17".equals(prodMap.getTopMenuId())) {
				reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
				retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
				if (retMetaInfo == null) {
					minusCount += 1;
					continue;
				} else
					product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
			} else if ("DP18".equals(prodMap.getTopMenuId())) {
				reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
				retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
				if (retMetaInfo == null) {
					minusCount += 1;
					continue;
				} else
					product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
			}
			product.setStatus(prodMap.getIconClsfCd());
			productList.add(product);

		}
		commonResponse.setTotalCount(commonResponse.getTotalCount() - minusCount);

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

		// 정액제 상품 목록 조회
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setBannerImageCd(DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
		req.setThumbnailImageCd(DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
		req.setProdStatusCd(DisplayConstants.DP_PASS_SALE_STAT_ING);
		req.setChnlStatusCd(DisplayConstants.DP_SALE_STAT_ING);
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

		couponBasicInfoList = this.commonDAO.queryForList("Freepass.selectFreepassSeries", req, ProductBasicInfo.class);

		if (couponBasicInfoList == null)
			commonResponse.setTotalCount(0);

		// 정액제 상품 메타 조회
		if (couponBasicInfoList != null && couponBasicInfoList.size() > 0) {
			reqMapC.put("tenantHeader", header.getTenantHeader());
			reqMapC.put("deviceHeader", header.getDeviceHeader());
			reqMapC.put("bannerImageCd", DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
			reqMapC.put("thumbnailImageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
			reqMapC.put("ebookThumbnailImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
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
			reqMap.put("ebookThumbnailImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				reqMap.put("productBasicInfo", productBasicInfo);
				retMetaInfo = this.metaInfoService.getFreepassMetaInfo(reqMap);
				coupon = this.responseInfoGenerateFacade.generateFreepassProduct(retMetaInfo);
				couponList.add(coupon);
				commonResponse.setTotalCount(productBasicInfo.getTotalCount());
			}
		}

		responseVO = new FreepassListRes();
		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.freepass.service.FreepassService#searchFreepassListByChannelV2(com.skplanet
	 * .storeplatform.sac.client.display.vo.freepass.FreepassSpecificReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public FreepassListRes searchFreepassListByChannelV2(FreepassSpecificReq req, SacRequestHeader header) {
		// 특정 상품에 적용할 자유 이용권 조회
		FreepassListRes responseVO = null;
		CommonResponse commonResponse = new CommonResponse();

		Coupon coupon = null;
		List<Coupon> couponList = new ArrayList<Coupon>();

		Map<String, Object> reqMap = new HashMap<String, Object>();
		List<ProductBasicInfo> productBasicInfoList = null;
		MetaInfo retMetaInfo = null;
		ExistenceListRes res = null;
		List<String> prodIdList = new ArrayList<String>();
		boolean purchaseYn = false;
		// 정액제 상품 목록 조회
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setBannerImageCd(DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
		req.setThumbnailImageCd(DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
		req.setStandardModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
		req.setProdRshpCd(DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANY_PHONE_4MM);

		// 시작점 ROW Default 세팅
		if (req.getOffset() == 0) {
			req.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (req.getCount() == 0) {
			req.setCount(20);
		}

		if (StringUtils.isEmpty(req.getUserKey())) {
			throw new StorePlatformException("SAC_DSP_0002", "userKey", req.getUserKey());
		}

		if (StringUtils.isEmpty(req.getDeviceKey())) {
			throw new StorePlatformException("SAC_DSP_0002", "deviceKey", req.getDeviceKey());
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
		productBasicInfoList = this.commonDAO.queryForList("Freepass.searchFreepassListByChannelV2", req,
				ProductBasicInfo.class);
		int totalCnt = 0;
		// 정액제 상품 메타 조회
		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
			reqMap.put("tenantHeader", header.getTenantHeader());
			reqMap.put("deviceHeader", header.getDeviceHeader());
			reqMap.put("bannerImageCd", DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
			reqMap.put("thumbnailImageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
			reqMap.put("ebookThumbnailImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				// 판매중지는 기구매 체크대상
				if (productBasicInfo.getProdStatusCd().equals(DisplayConstants.DP_PASS_SALE_STAT_STOP)) {
					prodIdList.add(productBasicInfo.getProdId());
				}
			}
			try {
				res = this.displayCommonService.checkPurchaseList(header.getTenantHeader().getTenantId(),
						req.getUserKey(), req.getDeviceKey(), prodIdList);
			} catch (StorePlatformException e) {
				// ignore : 구매 연동 오류 발생해도 상세 조회는 오류 없도록 처리. 구매 연동오류는 VOC 로 처리한다.
				res = new ExistenceListRes();
				res.setExistenceListRes(new ArrayList<ExistenceRes>());
			}

			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				prodIdList.add(productBasicInfo.getProdId());
				if (productBasicInfo.getProdStatusCd().equals(DisplayConstants.DP_PASS_SALE_STAT_ING)) { // 판매중이면 정상적으로
					totalCnt++;
					reqMap.put("productBasicInfo", productBasicInfo);
					retMetaInfo = this.metaInfoService.getFreepassMetaInfo(reqMap);
					coupon = this.responseInfoGenerateFacade.generateFreepassProduct(retMetaInfo);
					couponList.add(coupon);
					commonResponse.setTotalCount(totalCnt);
				} else {
					// 기구매 여부 조회
					for (ExistenceRes existenceRes : res.getExistenceListRes()) {
						// this.log.info("existenceRes.getProdId():::::" + existenceRes.getProdId());
						if (existenceRes.getProdId().equals(productBasicInfo.getProdId())) {
							purchaseYn = true;
						}
					}
					this.log.info("구매 여부:purchaseYn=>" + purchaseYn);
					if (purchaseYn) {
						totalCnt++;
						reqMap.put("productBasicInfo", productBasicInfo);
						retMetaInfo = this.metaInfoService.getFreepassMetaInfo(reqMap);
						coupon = this.responseInfoGenerateFacade.generateFreepassProduct(retMetaInfo);
						couponList.add(coupon);
						commonResponse.setTotalCount(totalCnt);
					}
				}
			}
		}

		responseVO = new FreepassListRes();
		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		return responseVO;
	}

	/**
	 * 이용가능한 정액권목록 구매 연동.
	 * 
	 * @param req
	 *            req
	 * @return List<String>
	 */
	@Override
	public List<String> getAvailableFixrateProdIdList(PaymentInfoSacReq req) {
		List<String> availableFixrateProdIdList = new ArrayList<String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lang", req.getLangCd());
		paramMap.put("tenantId", req.getTenantId());
		paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		paramMap.put("prodId", req.getProdId());
		paramMap.put("deviceModelNo", "");
		availableFixrateProdIdList = this.commonDAO.queryForList("PaymentInfo.getAvailableFixrateProdIdList", paramMap,
				String.class);

		return availableFixrateProdIdList;
	}

	/**
	 * 정액권 구매 연동.
	 * 
	 * @param req
	 *            req
	 * @return List<PaymentInfo>
	 */
	@Override
	public List<PaymentInfo> getFreePassforPayment(PaymentInfoSacReq req) {
		List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();
		List<String> prodIdList = req.getProdIdList();
		List<String> exclusiveFixrateProdIdList = new ArrayList<String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// / 단말 지원 정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(req.getDeviceModelCd());

		paramMap.put("lang", req.getLangCd());
		paramMap.put("tenantId", req.getTenantId());
		paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		paramMap.put("deviceModelCd", req.getDeviceModelCd());
		paramMap.put("supportDevice", supportDevice);
		paramMap.put("dpAnyPhone4mm", DisplayConstants.DP_ANY_PHONE_4MM);
		PaymentInfo paymentInfo = null;
		for (int i = 0; i < prodIdList.size(); i++) {
			paramMap.put("prodId", prodIdList.get(i));
			paymentInfo = this.commonDAO.queryForObject("PaymentInfo.getFreePassMetaInfo", paramMap, PaymentInfo.class);
			if (paymentInfo != null) {
				paramMap.put("productId", paymentInfo.getProdId());
				exclusiveFixrateProdIdList = this.commonDAO.queryForList("PaymentInfo.getExclusiveFixrateProdIdList",
						paramMap, String.class);

				paymentInfo.setExclusiveFixrateProdIdList(exclusiveFixrateProdIdList);
			} else {
				throw new StorePlatformException("SAC_DSP_0009", "[정액권 상품 조회]" + prodIdList.get(0));
			}
			paymentInfoList.add(paymentInfo);
		}

		return paymentInfoList;
	}

}
