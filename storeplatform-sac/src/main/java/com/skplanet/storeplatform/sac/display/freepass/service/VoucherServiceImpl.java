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

import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
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
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherDetailResV2;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherListReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherListRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherSpecificReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.GradeInfoSac;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Coupon;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Lists;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;
import com.skplanet.storeplatform.sac.display.feature.list.vo.DisplayListCriteria;
import com.skplanet.storeplatform.sac.display.feature.list.vo.DisplayListFromDB;
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
	 * 2.4.8.1. 이용권 상품 상품 조회.
	 * 
	 * @see com.skplanet.storeplatform.sac.display.freepass.service.VoucherService
	 * #searchVoucherList(com.skplanet.storeplatform .sac.client.display.vo.freepass.VoucherListReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public VoucherListRes searchVoucherList(VoucherListReq req, SacRequestHeader header) {

		// 공통 응답 변수 선언
		VoucherListRes responseVO = null;
		CommonResponse commonResponse = new CommonResponse();

		Coupon coupon = null;
		List<Coupon> couponList = new ArrayList<Coupon>();

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

		// 이용권 종류가 "ALL"일 경우 빈값으로 셋팅 (All : 전체, OR004301 : 정액권 (VOD/이북/코믹),	OR004302 : 시리즈 패스(VOD/이북/코믹), OR004305 : 게임 캐시 정액제)
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

		// 19+ 상품여부 파라미터가 없으면 "N"으로 기본 셋팅
		if (StringUtils.isEmpty(req.getPlus19Yn())) {
			req.setPlus19Yn("N");
		}

		if (!"Y".equals(req.getPlus19Yn()) && !"N".equals(req.getPlus19Yn())) {
			throw new StorePlatformException("SAC_DSP_0003", "plus19Yn", req.getPlus19Yn());
		}

		// 이용등급 코드 : '+'로 연결 된 이용등급코드를 배열로 전달
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

		// ###################################################################################
		// 이용권 목록 조회
		// ###################################################################################
		productBasicInfoList = this.commonDAO.queryForList("Voucher.selectVoucherList", req, ProductBasicInfo.class);
		// ###################################################################################

		if (productBasicInfoList == null)
			throw new StorePlatformException("SAC_DSP_0009");

		// 이용권 목록 Meta / Cache 조회
		if (productBasicInfoList.size() > 0) {

			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {

				// #############################################################################
				// 이용권 목록 Meta / Cache 조회
				// #############################################################################
				retMetaInfo = this.metaInfoService.getVoucherMetaInfo(productBasicInfo.getProdId());
				// #############################################################################

				// Generate
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
	 * 2.4.8.2. 이용권 상품 상세 조회.
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
		Product product = null;
		List<Coupon> couponList = new ArrayList<Coupon>();
		List<Product> productList = new ArrayList<Product>();

		Map<String, Object> reqMap = new HashMap<String, Object>();
		ProductBasicInfo productBasicInfo = new ProductBasicInfo();
		List<VoucherProdMap> mapList = null;
		List<MetaInfo> retMetaInfoList = null;
		int minusCount = 0;

		// 파라미터 셋팅
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setBannerImageCd(DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
		req.setThumbnailImageCd(DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
		req.setEbookThumbnailImageCd(DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
		req.setProdStatusCd(DisplayConstants.DP_PASS_SALE_STAT_ING);
		req.setStandardModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		// 상품등급코드 : '+'로 연결 된 상품등급코드를 배열로 전달
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

		// 이용등급코드 : '+'로 연결 된 이용등급코드를 배열로 전달
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

		// CMPX_PROD_GRP_CD(복합상품 그룹코드), Plus19Yn 알아오기
		MetaInfo beforeInfo = (MetaInfo) this.commonDAO.queryForObject("Voucher.selectCmpxProdGrpCd",
				req.getProductId());
		if (null != beforeInfo) {
			req.setCmpxProdGrpCd(beforeInfo.getCmpxProdGrpCd());
			req.setPlus19Yn(beforeInfo.getPlus19Yn());
		} else {
			req.setPlus19Yn("N");
		}

		// ###################################################################################
		// 이용권 상세 조회 (복합상품 그룹코드가 있으면 그룹으로 조회)
		// ###################################################################################
		retMetaInfoList = this.commonDAO.queryForList("Voucher.selectVoucherDetail", req, MetaInfo.class);
		// ###################################################################################

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
                            // TODO topMenuId -> menuId 작업 안함
							MileageInfo mileageInfo = this.benefitService.getMileageInfo(req.getTenantId(),
									metaInfo.getTopMenuId(), req.getChannelId(), metaInfo.getProdAmt());
							mileageInfo = this.benefitService.checkFreeProduct(mileageInfo, metaInfo.getProdAmt());
							pointList.addAll(this.metaInfoGenerator.generateMileage(mileageInfo, userGrade));
						}
					}
					coupon.setPointList(pointList);
					// 2016.01.05 단말 Provisioning 변경 김형식 추가
					coupon.setIsDeviceSupported(this.commonSupportDeviceVocher(header, metaInfo.getTopMenuId()));
					couponList.add(coupon);

					// 요청한 이용권의 포함된 상품에 대해서만 상품을 조회한다.
					if ("Y".equals(metaInfo.getRequestProduct())) {

						// ###########################################################################
						// 이용권의 포함된 상품
						// ###########################################################################
						mapList = this.commonDAO.queryForList("Voucher.selectVoucherMapProduct", req,
								VoucherProdMap.class);
						// ###########################################################################

						for (VoucherProdMap prodMap : mapList) {

							commonResponse.setTotalCount( prodMap.getTotalCount() );

							// Ebook
							if ("DP13".equals(prodMap.getTopMenuId())) {
								metaInfo = this.metaInfoService.getEbookComicMetaInfo( prodMap.getPartProdId() );
								if (metaInfo == null) {
									minusCount += 1;
									continue;
								} else
									product = this.responseInfoGenerateFacade.generateEbookProduct(metaInfo);

							// 코믹
							} else if ("DP14".equals(prodMap.getTopMenuId())) {
								metaInfo = this.metaInfoService.getEbookComicMetaInfo( prodMap.getPartProdId() );
								if (metaInfo == null) {
									minusCount += 1;
									continue;
								} else
									product = this.responseInfoGenerateFacade.generateComicProduct(metaInfo);

							// 영화
							} else if ("DP17".equals(prodMap.getTopMenuId())) {
								metaInfo = this.metaInfoService.getVODMetaInfo( prodMap.getPartProdId() );
								if (metaInfo == null) {
									minusCount += 1;
									continue;
								} else
									product = this.responseInfoGenerateFacade.generateMovieProduct(metaInfo);

							// TV 방송
							} else if ("DP18".equals(prodMap.getTopMenuId())) {
								metaInfo = this.metaInfoService.getVODMetaInfo( prodMap.getPartProdId() );
								if (metaInfo == null) {
									minusCount += 1;
									continue;
								} else
									product = this.responseInfoGenerateFacade.generateBroadcastProduct(metaInfo);
							}
							product.setStatus(prodMap.getIconClsfCd());
							// product.getRights().setPlus19Yn(prodMap.getPlus19Yn());
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

	
	/*
	 * 2.4.8.5. 이용권 상품 상세 조회(V2)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.freepass.service.VoucherService#searchVoucherDetailV2(com.skplanet.storeplatform
	 * .sac.client.display.vo.freepass.VoucherDetailReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public VoucherDetailResV2 searchVoucherDetailV2(VoucherDetailReq req, SacRequestHeader header) {
		
		// 공통 응답 변수 선언
		VoucherDetailResV2 responseVO = new VoucherDetailResV2();
		CommonResponse commonResponse = new CommonResponse();
		Coupon coupon = null;
		List<Coupon> couponList = new ArrayList<Coupon>();
		
		List<MetaInfo> retMetaInfoList = null;
		int totalCount = 0;
		
		// 파라미터 셋팅
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setBannerImageCd(DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
		req.setThumbnailImageCd(DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
		req.setEbookThumbnailImageCd(DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
		req.setProdStatusCd(DisplayConstants.DP_PASS_SALE_STAT_ING);
		req.setStandardModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
		
		// 이용등급코드 : '+'로 연결 된 이용등급코드를 배열로 전달
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
	
		// CMPX_PROD_GRP_CD(복합상품 그룹코드), Plus19Yn 알아오기
		MetaInfo beforeInfo = (MetaInfo) this.commonDAO.queryForObject("Voucher.selectCmpxProdGrpCd", req.getProductId());
		if (null != beforeInfo) {
			req.setCmpxProdGrpCd(beforeInfo.getCmpxProdGrpCd());
			req.setPlus19Yn(beforeInfo.getPlus19Yn());
		} else {
			req.setPlus19Yn("N");
		}
		
		// ###################################################################################
		// 이용권 상세 조회 (복합상품 그룹코드가 있으면 그룹으로 조회)
		// ###################################################################################
		retMetaInfoList = this.commonDAO.queryForList("Voucher.selectVoucherDetail", req, MetaInfo.class);
		// ###################################################################################
		
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
				
				// 조합
				if (saveFlag) {
					
					coupon = this.responseInfoGenerateFacade.generateVoucherProduct(metaInfo);
					totalCount++;
					
					// 티멤버십 DC 정보
					TmembershipDcInfo info = this.displayCommonService.getTmembershipDcRateForMenu(header.getTenantHeader().getTenantId(), metaInfo.getTopMenuId());
					List<Point> pointList = this.commonGenerator.generatePoint(info);

					// 정액제 패스/시리즈 패스만 조회
					if ((StringUtils.equals(DisplayConstants.FIXRATE_PROD_TYPE_VOD_FIXRATE, metaInfo.getCmpxProdClsfCd()) 
							|| StringUtils.equals(DisplayConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS, metaInfo.getCmpxProdClsfCd())) && StringUtils.isNotEmpty(req.getUserKey())) {

						// 회원등급 조회
						GradeInfoSac userGradeInfo = this.displayCommonService.getUserGrade(req.getUserKey());
						if (userGradeInfo != null) {
							if (pointList == null)
								pointList = new ArrayList<Point>();
							String userGrade = userGradeInfo.getUserGradeCd();
							// TODO topMenuId -> menuId 작업 안함
							MileageInfo mileageInfo = this.benefitService.getMileageInfo(req.getTenantId(), metaInfo.getTopMenuId(), req.getChannelId(), metaInfo.getProdAmt());

							// Tstore멤버십 적립율 정보
							mileageInfo = this.benefitService.checkFreeProduct(mileageInfo, metaInfo.getProdAmt());
							pointList.addAll(this.metaInfoGenerator.generateMileage(mileageInfo, userGrade));
						}
					}

					coupon.setPointList(pointList);
					// 2016.01.05 단말 Provisioning 변경 김형식 추가
					coupon.setIsDeviceSupported(this.commonSupportDeviceVocher(header, metaInfo.getTopMenuId()));
					// LIST_ID가 존재할 경우
					if(!StringUtils.isEmpty(metaInfo.getListId())) {
						String listId = metaInfo.getListId();
						Lists lists = setListIdAndEtcProp(listId,header);
						if(null != lists){
							coupon.setLists(lists);
						}
					}
					couponList.add(coupon);
				} // if
			} // for
		}
		commonResponse.setTotalCount(totalCount);
		
		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		
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

		// if (StringUtils.isEmpty(req.getUserKey())) {
		// throw new StorePlatformException("SAC_DSP_0002", "userKey", req.getUserKey());
		// }
		//
		// if (StringUtils.isEmpty(req.getDeviceKey())) {
		// throw new StorePlatformException("SAC_DSP_0002", "deviceKey", req.getDeviceKey());
		// }

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

		// CMPX_PROD_GRP_CD, Plus19Yn 알아오기
		MetaInfo beforeInfo = (MetaInfo) this.commonDAO.queryForObject("Voucher.selectChannelPlusYn",
				req.getProductId());
		if (null != beforeInfo) {
			req.setPlus19Yn(beforeInfo.getPlus19Yn());
		} else {
			req.setPlus19Yn("N");
		}

		productBasicInfoList = this.commonDAO
				.queryForList("Voucher.searchVoucherSpecific", req, ProductBasicInfo.class);
		int totalCnt = 0;
		// 정액제 상품 메타 조회
		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {

			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				// 판매중지는 기구매 체크대상
				if (productBasicInfo.getProdStatusCd().equals(DisplayConstants.DP_PASS_SALE_STAT_STOP)) {
					prodIdList.add(productBasicInfo.getProdId());
				}
			}

			if (StringUtils.isNotEmpty(req.getUserKey()) && StringUtils.isNotEmpty(req.getDeviceKey())) {
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
			}

			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				prodIdList.add(productBasicInfo.getProdId());
				if (productBasicInfo.getProdStatusCd().equals(DisplayConstants.DP_PASS_SALE_STAT_ING)) { // 판매중이면
																										 // 정상적으로
					totalCnt++;
					retMetaInfo = this.metaInfoService.getVoucherMetaInfo( productBasicInfo.getProdId() );
					coupon = this.responseInfoGenerateFacade.generateVoucherProduct(retMetaInfo);
					couponList.add(coupon);
					commonResponse.setTotalCount(totalCnt);
				} else {
					if (StringUtils.isNotEmpty(req.getUserKey()) && StringUtils.isNotEmpty(req.getDeviceKey())) {
						// 기구매 여부 조회
						for (ExistenceRes existenceRes : res.getExistenceListRes()) {
							// this.log.info("existenceRes.getProdId():::::" +
							// existenceRes.getProdId());
							if (existenceRes.getProdId().equals(productBasicInfo.getProdId())) {
								purchaseYn = true;
							}
						}
						this.log.info("구매 여부:purchaseYn=>" + purchaseYn +"::상품 id ::" + productBasicInfo.getProdId());
						if (purchaseYn) {
							totalCnt++;
							reqMap.put("productBasicInfo", productBasicInfo);
							retMetaInfo = this.metaInfoService.getVoucherMetaInfo( productBasicInfo.getProdId() );
							coupon = this.responseInfoGenerateFacade.generateVoucherProduct(retMetaInfo);
							couponList.add(coupon);
							commonResponse.setTotalCount(totalCnt);
						}
					}
				}
			}
		}

		responseVO = new VoucherListRes();
		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		return responseVO;
	}
	
	/**
	 * <pre>
	 * 특정 상품이 적용된 이용권 조회(V2).
	 * </pre>
	 * 
	 * @param req
	 *            VoucherSpecificReq
	 * @param header
	 *            SacRequestHeader
	 * @return VoucherListRes VoucherListRes
	 */

	@Override
	public VoucherListRes searchVoucherSpecificV2(VoucherSpecificReq req, SacRequestHeader header) {
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
		
		// idType은 필수값
		if (StringUtils.isEmpty(req.getIdType())) {
			throw new StorePlatformException("SAC_DSP_0002", "idType", req.getIdType());
		}

		if (!DisplayConstants.DP_EPISODE_IDENTIFIER_CD.equals(req.getIdType()) && !DisplayConstants.DP_CHANNEL_IDENTIFIER_CD.equals(req.getIdType())) {
			throw new StorePlatformException("SAC_DSP_0003", "idType", req.getIdType());
		}		
		
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

		// if (StringUtils.isEmpty(req.getUserKey())) {
		// throw new StorePlatformException("SAC_DSP_0002", "userKey", req.getUserKey());
		// }
		//
		// if (StringUtils.isEmpty(req.getDeviceKey())) {
		// throw new StorePlatformException("SAC_DSP_0002", "deviceKey", req.getDeviceKey());
		// }

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

		
		if(req.getIdType().equals(DisplayConstants.DP_EPISODE_IDENTIFIER_CD)){
			Map<String, String> map = new HashMap<String, String>();
			map.put("langCd", header.getTenantHeader().getLangCd());
			map.put("tenantId", header.getTenantHeader().getTenantId());	
			map.put("prodId", req.getProductId());
			
			MetaInfo prodInfo = this.commonDAO.queryForObject("Voucher.searchVocherProductInfo", map, MetaInfo.class);
			
	       if (prodInfo == null) {
	            throw new StorePlatformException("SAC_DSP_0005", "[일반상품 조회]" + req.getProductId());
	        }
	       
	       req.setPossLendClsfCd(prodInfo.getPossLendClsfCd());
	       req.setBookClsfCd(prodInfo.getBookClsfCd());
		}
		
		
		// CMPX_PROD_GRP_CD, Plus19Yn 알아오기
		MetaInfo beforeInfo = (MetaInfo) this.commonDAO.queryForObject("Voucher.selectChannelPlusYn",
				req.getProductId());
		if (null != beforeInfo) {
			req.setPlus19Yn(beforeInfo.getPlus19Yn());
		} else {
			req.setPlus19Yn("N");
		}

		productBasicInfoList = this.commonDAO
				.queryForList("Voucher.searchVoucherSpecific", req, ProductBasicInfo.class);
		int totalCnt = 0;
		// 정액제 상품 메타 조회
		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				// 판매중지는 기구매 체크대상
				if (productBasicInfo.getProdStatusCd().equals(DisplayConstants.DP_PASS_SALE_STAT_STOP)) {
					prodIdList.add(productBasicInfo.getProdId());
				}
			}
			if (StringUtils.isNotEmpty(req.getUserKey()) && StringUtils.isNotEmpty(req.getDeviceKey())) {
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
			}

			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {

				prodIdList.add(productBasicInfo.getProdId());

				if (productBasicInfo.getProdStatusCd().equals(DisplayConstants.DP_PASS_SALE_STAT_ING)) { // 판매중이면
																										 // 정상적으로
					totalCnt++;
					reqMap.put("productBasicInfo", productBasicInfo);
					retMetaInfo = this.metaInfoService.getVoucherMetaInfo( productBasicInfo.getProdId() );
					coupon = this.responseInfoGenerateFacade.generateVoucherProduct(retMetaInfo);
					couponList.add(coupon);
					commonResponse.setTotalCount(totalCnt);

				} else {
					if (StringUtils.isNotEmpty(req.getUserKey()) && StringUtils.isNotEmpty(req.getDeviceKey())) {
						// 기구매 여부 조회
						for (ExistenceRes existenceRes : res.getExistenceListRes()) {
							// this.log.info("existenceRes.getProdId():::::" +
							// existenceRes.getProdId());
							if (existenceRes.getProdId().equals(productBasicInfo.getProdId())) {
								purchaseYn = true;
							}
						}
						this.log.info("구매 여부:purchaseYn=>" + purchaseYn +"::상품 id ::" + productBasicInfo.getProdId());
						if (purchaseYn) {
							totalCnt++;
							reqMap.put("productBasicInfo", productBasicInfo);
							retMetaInfo = this.metaInfoService.getVoucherMetaInfo( productBasicInfo.getProdId() );
							coupon = this.responseInfoGenerateFacade.generateVoucherProduct(retMetaInfo);
							couponList.add(coupon);
							commonResponse.setTotalCount(totalCnt);
						}
					}
				}
			}
		}

		responseVO = new VoucherListRes();
		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		return responseVO;
	}	
	
	
	/**
	 * <pre>
	 * Lists Generate.
	 * </pre>
	 * @param listId
	 * @param header
	 * @return Lists
	 */
	private Lists setListIdAndEtcProp(String listId, SacRequestHeader header) {
		Lists response = null;
		String tenantId = header.getTenantHeader().getTenantId();
		int count = 1;

		DisplayListCriteria listCriteria = new DisplayListCriteria(tenantId, listId, "Y", count);

		List<DisplayListFromDB> listsFromDB = commonDAO.queryForList( "DisplayList.selectDisplayList", listCriteria, DisplayListFromDB.class);

		if(!listsFromDB.isEmpty()){
			response = new Lists();
    		response.setEtcProp(listsFromDB.get(0).getEtcProp());
    		response.setListId(listsFromDB.get(0).getListId());
    		response.setTitle(listsFromDB.get(0).getListNm());
		}
		return response;
	}

	/**
	 * 이용권 지원 여부 .
	 *
	 * @param header
	 *            header
	 * @return String
	 */
	private String commonSupportDeviceVocher(SacRequestHeader header,String topMenuId) {
		String result = "N";

		if(StringUtils.isEmpty(header.getDeviceHeader().getModel())){
			throw new StorePlatformException("SAC_DSP_0029");
		}

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(header.getDeviceHeader()
				.getModel());

		if(supportDevice == null){
			return result;
		}

		if(topMenuId.equals("DP17") || topMenuId.equals("DP18")){
			result = supportDevice.getSdVideoSprtYn();
		}else if(topMenuId.equals("DP13")) {
			result = supportDevice.getEbookSprtYn();
		}else if(topMenuId.equals("DP14")) {
			result = supportDevice.getComicSprtYn();
		}


		return result;
	}

}
