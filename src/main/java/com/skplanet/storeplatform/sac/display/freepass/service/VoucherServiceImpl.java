/*
] * Copyright (c) 2013 SK planet.
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
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherListReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherListRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherSpecificReq;
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
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;
import com.skplanet.storeplatform.sac.display.freepass.vo.VoucherProdMap;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * Voucher Service (CoreStoreBusiness)
 * 
 * Updated on : 2015. 4. 30. Updated by : 이태균, IS PLUS.
 */
@Service
public class VoucherServiceImpl implements VoucherService {

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
	 * @see com.skplanet.storeplatform.sac.display.freepass.service.VoucherService
	 * #searchVoucherList(com.skplanet.storeplatform .sac.client.display.vo.freepass.VoucherListReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public VoucherListRes searchVoucherList(VoucherListReq req, SacRequestHeader header) {
		// TODO Auto-generated method stub

		// 공통 응답 변수 선언
		VoucherListRes responseVO = null;
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
				// String[] arrTopMenuId = URLDecoder.decode(req.getTopMenuId(),
				// "UTF-8").split("//+");
				String[] arrTopMenuId = StringUtils.split(req.getTopMenuId(), "+");
				req.setArrTopMenuId(arrTopMenuId);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_DSP_0003", "topMenuId", req.getTopMenuId());
			}
		}

		// 파라미터 유효값 체크( 19+ 상품여부)
		if (StringUtils.isNotEmpty(req.getProdGrdExtraCd())) {
			String prodGrdExtraCd = req.getProdGrdExtraCd();
			if (!"PD020601".equals(prodGrdExtraCd)) {
				this.log.debug("----------------------------------------------------------------");
				this.log.debug("유효하지않은 19+ 상품여부 코드 : " + prodGrdExtraCd);
				this.log.debug("----------------------------------------------------------------");
				throw new StorePlatformException("SAC_DSP_0003", "prodGrdExtraCd", prodGrdExtraCd);
			}
		}

		// '+'로 연결 된 이용등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getCmpxProdGradeCd())) {
			try {
				String[] arrayCmpxProdGradeCd = req.getCmpxProdGradeCd().split("\\+");
				// String[] arrayCmpxProdGradeCd = URLDecoder.decode(req.getCmpxProdGradeCd(), "UTF-8").split("\\+");
				for (int i = 0; i < arrayCmpxProdGradeCd.length; i++) {
					if (StringUtils.isNotEmpty(arrayCmpxProdGradeCd[i])) {
						if (!"PD004401".equals(arrayCmpxProdGradeCd[i]) && !"PD004402".equals(arrayCmpxProdGradeCd[i])
								&& !"PD004403".equals(arrayCmpxProdGradeCd[i])
								&& !"PD004404".equals(arrayCmpxProdGradeCd[i])) {
							this.log.debug("----------------------------------------------------------------");
							this.log.debug("유효하지않은 이용권 이용등급 코드 : " + arrayCmpxProdGradeCd[i]);
							this.log.debug("----------------------------------------------------------------");
							throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 cmpxProdGradeCd",
									arrayCmpxProdGradeCd[i]);
						}
					}
				}
				req.setArrayCmpxProdGradeCd(arrayCmpxProdGradeCd);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_DSP_0003", "cmpxProdGradeCd", req.getCmpxProdGradeCd());
			}
		}

		productBasicInfoList = this.commonDAO.queryForList("Voucher.selectVoucherList", req, ProductBasicInfo.class);

		if (productBasicInfoList == null)
			throw new StorePlatformException("SAC_DSP_0009");

		// 정액제 상품 메타 조회
		if (productBasicInfoList.size() > 0) {
			reqMap.put("tenantHeader", header.getTenantHeader());
			reqMap.put("deviceHeader", header.getDeviceHeader());
			reqMap.put("bannerImageCd", DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
			reqMap.put("thumbnailImageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
			reqMap.put("ebookThumbnailImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				reqMap.put("productBasicInfo", productBasicInfo);
				retMetaInfo = this.metaInfoService.getVoucherMetaInfo(reqMap);
				coupon = this.responseInfoGenerateFacade.generateVoucherProduct(retMetaInfo);
				couponList.add(coupon);
				commonResponse.setTotalCount(productBasicInfo.getTotalCount());
			}
		}

		responseVO = new VoucherListRes();
		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.freepass.service.VoucherService#searchVoucherDetail(com.skplanet.storeplatform
	 * .sac.client.display.vo.freepass.VoucherDetailReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public VoucherDetailRes searchVoucherDetail(VoucherDetailReq req, SacRequestHeader header) {

		// 공통 응답 변수 선언
		VoucherDetailRes responseVO = new VoucherDetailRes();
		CommonResponse commonResponse = new CommonResponse();

		Coupon coupon = null;
		List<Coupon> couponList = new ArrayList<Coupon>();
		Product product = null;
		List<Product> productList = new ArrayList<Product>();

		Map<String, Object> reqMap = new HashMap<String, Object>();
		ProductBasicInfo productBasicInfo = new ProductBasicInfo();
		List<VoucherProdMap> mapList = null;
		List<MetaInfo> retMetaInfoList = null;
		int minusCount = 0;

		// 이용권 상품 상세 조회
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setBannerImageCd(DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
		req.setThumbnailImageCd(DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
		req.setEbookThumbnailImageCd(DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
		req.setProdStatusCd(DisplayConstants.DP_PASS_SALE_STAT_ING);
		req.setStandardModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		// 파라미터 유효값 체크
		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i]) && !"PD004404".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}

		// '+'로 연결 된 이용등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getCmpxProdGradeCd())) {
			try {
				String[] arrayCmpxProdGradeCd = req.getCmpxProdGradeCd().split("\\+");
				for (int i = 0; i < arrayCmpxProdGradeCd.length; i++) {
					if (StringUtils.isNotEmpty(arrayCmpxProdGradeCd[i])) {
						if (!"PD004401".equals(arrayCmpxProdGradeCd[i]) && !"PD004402".equals(arrayCmpxProdGradeCd[i])
								&& !"PD004403".equals(arrayCmpxProdGradeCd[i])
								&& !"PD004404".equals(arrayCmpxProdGradeCd[i])) {
							this.log.debug("----------------------------------------------------------------");
							this.log.debug("유효하지않은 이용권 이용등급 코드 : " + arrayCmpxProdGradeCd[i]);
							this.log.debug("----------------------------------------------------------------");
							throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 cmpxProdGradeCd",
									arrayCmpxProdGradeCd[i]);
						}
					}
				}
				req.setArrayCmpxProdGradeCd(arrayCmpxProdGradeCd);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_DSP_0003", "cmpxProdGradeCd", req.getTopMenuId());
			}
		}

		// 시작점 ROW Default 세팅
		if (req.getOffset() == 0) {
			req.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (req.getCount() == 0) {
			req.setCount(20);
		}

		// CMPX_PROD_GRP_CD 알아오기
		String cmpxProdGrpCd = (String) this.commonDAO.queryForObject("Voucher.selectCmpxProdGrpCd", req);
		req.setCmpxProdGrpCd(cmpxProdGrpCd);

		// 정액제 상품 조회
		retMetaInfoList = this.commonDAO.queryForList("Voucher.selectVoucherDetail", req, MetaInfo.class);

		if (retMetaInfoList == null)
			throw new StorePlatformException("SAC_DSP_0009", req.getProductId(), req.getProductId());

		if (retMetaInfoList.size() > 0) {

			for (MetaInfo metaInfo : retMetaInfoList) {

				boolean saveFlag = false; // Coupon List에 저장여부

				// 상품 상태 조회 - 판매대기, 판매종료는 노출안함 ( 판매중,판매중지,판매금지는 노출함 )
				if (!DisplayConstants.DP_PASS_SALE_STAT_STOP.equals(metaInfo.getProdStatusCd())
						&& !DisplayConstants.DP_PASS_SALE_STAT_RESTRIC.equals(metaInfo.getProdStatusCd())
						&& !DisplayConstants.DP_PASS_SALE_STAT_ING.equals(metaInfo.getProdStatusCd())) {
					saveFlag = false;
				} else {
					saveFlag = true;
				}

				// userKey가 있을 경우만
				/*
				 * if (!StringUtils.isEmpty(req.getUserKey())) { // 구매여부 조회 boolean purchaseYn =
				 * this.displayCommonService.checkPurchase(req.getTenantId(), req.getUserKey(), req.getDeviceKey(),
				 * req.getProductId());
				 * 
				 * // 구매가 없을경우 : 판매중지, 판매종료는 노출안함 if (!purchaseYn) { if
				 * (DisplayConstants.DP_PASS_SALE_STAT_STOP.equals(metaInfo.getProdStatusCd()) ||
				 * DisplayConstants.DP_PASS_SALE_STAT_RESTRIC.equals(metaInfo.getProdStatusCd())) {
				 * 
				 * saveFlag = false; if ("Y".equals(metaInfo.getRequestProduct())) { // throw new
				 * StorePlatformException("SAC_DSP_0011", metaInfo.getProdStatusCd(), // metaInfo.getProdStatusCd()); }
				 * } else { // 판매중인 상품만 내린다. saveFlag = true; } } }
				 */

				// 조합
				if (saveFlag) {

					coupon = this.responseInfoGenerateFacade.generateVoucherProduct(metaInfo);

					// 티멤버십 DC 정보
					TmembershipDcInfo info = this.displayCommonService.getTmembershipDcRateForMenu(header
							.getTenantHeader().getTenantId(), metaInfo.getTopMenuId());
					List<Point> pointList = this.commonGenerator.generatePoint(info);
					// Tstore멤버십 적립율 정보
					// 정액제 패스/시리즈 패스만 조회
					if ((StringUtils.equals(DisplayConstants.FIXRATE_PROD_TYPE_VOD_FIXRATE,
							metaInfo.getCmpxProdClsfCd()) || StringUtils.equals(
							DisplayConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS, metaInfo.getCmpxProdClsfCd()))
							&& StringUtils.isNotEmpty(req.getUserKey())) {
						// 회원등급 조회
						GradeInfoSac userGradeInfo = this.displayCommonService.getUserGrade(req.getUserKey());
						if (userGradeInfo != null) {
							if (pointList == null)
								pointList = new ArrayList<Point>();
							String userGrade = userGradeInfo.getUserGradeCd();
							MileageInfo mileageInfo = this.benefitService.getMileageInfo(req.getTenantId(),
									metaInfo.getTopMenuId(), req.getChannelId(), metaInfo.getProdAmt());
							mileageInfo = this.benefitService.checkFreeProduct(mileageInfo, metaInfo.getProdAmt());
							pointList.addAll(this.metaInfoGenerator.generateMileage(mileageInfo, userGrade));
						}
					}
					coupon.setPointList(pointList);
					couponList.add(coupon);

					// 요청한 상품에대해서만 상품을 조회한다.
					if ("Y".equals(metaInfo.getRequestProduct())) {
						mapList = this.commonDAO.queryForList("Voucher.selectVoucherMapProduct", req,
								VoucherProdMap.class);

						reqMap.put("tenantHeader", header.getTenantHeader());
						reqMap.put("deviceHeader", header.getDeviceHeader());
						reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

						for (VoucherProdMap prodMap : mapList) {
							productBasicInfo.setProdId(prodMap.getPartProdId());
							productBasicInfo.setTenantId(header.getTenantHeader().getTenantId());
							productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
							reqMap.put("productBasicInfo", productBasicInfo);

							commonResponse.setTotalCount(prodMap.getTotalCount());

							if ("DP13".equals(prodMap.getTopMenuId())) {
								reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
								metaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
								if (metaInfo == null) {
									minusCount += 1;
									continue;
								} else
									product = this.responseInfoGenerateFacade.generateEbookProduct(metaInfo);
							} else if ("DP14".equals(prodMap.getTopMenuId())) {
								reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
								metaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
								if (metaInfo == null) {
									minusCount += 1;
									continue;
								} else
									product = this.responseInfoGenerateFacade.generateComicProduct(metaInfo);
							} else if ("DP17".equals(prodMap.getTopMenuId())) {
								reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
								metaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
								if (metaInfo == null) {
									minusCount += 1;
									continue;
								} else
									product = this.responseInfoGenerateFacade.generateBroadcastProduct(metaInfo);
							} else if ("DP18".equals(prodMap.getTopMenuId())) {
								reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
								metaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
								if (metaInfo == null) {
									minusCount += 1;
									continue;
								} else
									product = this.responseInfoGenerateFacade.generateMovieProduct(metaInfo);
							}
							product.setStatus(prodMap.getIconClsfCd());
							productList.add(product);
						} // for
					} // if
				} // if
			} // for
		}
		// coupon.setRequestProduct(); <<

		commonResponse.setTotalCount(commonResponse.getTotalCount() - minusCount);

		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		responseVO.setProductList(productList);

		return responseVO;
	}

	/**
	 * <pre>
	 * 특정 상품이 적용된 이용권 조회.
	 * </pre>
	 * 
	 * @param req
	 *            VoucherSpecificReq
	 * @param header
	 *            SacRequestHeader
	 * @return VoucherListRes VoucherListRes
	 */

	@Override
	public VoucherListRes searchVoucherSpecific(VoucherSpecificReq req, SacRequestHeader header) {
		// 특정 상품에 적용할 자유 이용권 조회
		VoucherListRes responseVO = null;
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

		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i]) && !"PD004404".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}
		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getProdGradeCd())) {
			String[] arrayProdGradeCd = req.getProdGradeCd().split("\\+");
			req.setArrayProdGradeCd(arrayProdGradeCd);
		}

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

				// String[] arrKind = URLDecoder.decode(req.getKind(),
				// "UTF-8").split("//+");
				String[] arrKind = StringUtils.split(req.getKind(), "+");
				req.setArrKind(arrKind);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_DSP_0003", "kind", req.getKind());
			}
		}

		if (StringUtils.equalsIgnoreCase(req.getKind(), "All")) {
			req.setKind("");
		}
		if (StringUtils.isEmpty(req.getProdGradeExtraCd())) {
			req.setProdGradeExtraCd(null);
		}else if (StringUtils.isNotEmpty(req.getProdGradeExtraCd())){
			if (!StringUtils.equalsIgnoreCase(req.getProdGradeExtraCd(), "PD020601")) {
				throw new StorePlatformException("SAC_DSP_0003", "prodGradeExtraCd", req.getProdGradeExtraCd());
			}
		}
		
		
		productBasicInfoList = this.commonDAO
				.queryForList("Voucher.searchVoucherSpecific", req, ProductBasicInfo.class);
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

			if (prodIdList.size() > 0) { // 판매 중지가 있는 상품에 대해서만 기구매 체크를 해야함
				try {
					res = this.displayCommonService.checkPurchaseList(header.getTenantHeader().getTenantId(),
							req.getUserKey(), req.getDeviceKey(), prodIdList);
				} catch (StorePlatformException e) {
					// ignore : 구매 연동 오류 발생해도 상세 조회는 오류 없도록 처리. 구매 연동오류는 VOC 로
					// 처리한다.
					res = new ExistenceListRes();
					res.setExistenceListRes(new ArrayList<ExistenceRes>());
				}
			}

			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				prodIdList.add(productBasicInfo.getProdId());
				if (productBasicInfo.getProdStatusCd().equals(DisplayConstants.DP_PASS_SALE_STAT_ING)) { // 판매중이면
																										 // 정상적으로
					totalCnt++;
					reqMap.put("productBasicInfo", productBasicInfo);
					retMetaInfo = this.metaInfoService.getVoucherMetaInfo(reqMap);
					coupon = this.responseInfoGenerateFacade.generateVoucherProduct(retMetaInfo);
					couponList.add(coupon);
					commonResponse.setTotalCount(totalCnt);
				} else {
					// 기구매 여부 조회
					for (ExistenceRes existenceRes : res.getExistenceListRes()) {
						// this.log.info("existenceRes.getProdId():::::" +
						// existenceRes.getProdId());
						if (existenceRes.getProdId().equals(productBasicInfo.getProdId())) {
							purchaseYn = true;
						}
					}
					this.log.info("구매 여부:purchaseYn=>" + purchaseYn);
					if (purchaseYn) {
						totalCnt++;
						reqMap.put("productBasicInfo", productBasicInfo);
						retMetaInfo = this.metaInfoService.getVoucherMetaInfo(reqMap);
						coupon = this.responseInfoGenerateFacade.generateVoucherProduct(retMetaInfo);
						couponList.add(coupon);
						commonResponse.setTotalCount(totalCnt);
					}
				}
			}
		}

		responseVO = new VoucherListRes();
		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		return responseVO;
	}
}
