/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.controller;

import com.skplanet.storeplatform.external.client.shopping.inf.ItxTypeCode.TX_TYPE_CODE;
import com.skplanet.storeplatform.external.client.shopping.vo.*;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.inf.IcmsJobPrint;
import com.skplanet.storeplatform.sac.api.service.CouponItemService;
import com.skplanet.storeplatform.sac.api.service.CouponProcessService;
import com.skplanet.storeplatform.sac.api.service.ShoppingCouponService;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.ErrorData;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 쇼핑 컨트롤러
 * 
 * Updated on : 2014-01-20 Updated by : 김형식, SK플래닛.
 */
@Controller
@RequestMapping("/shopping")
public class ShoppingCouponSacController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShoppingCouponService shoppingCouponService;

	@Autowired
	private CouponItemService couponItemService;

	@Autowired
	private CouponProcessService couponProcessService;

	/**
	 * <pre>
	 * 쇼핑쿠폰 전처리– POST.
	 * </pre>
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return CouponRes
	 */
	@RequestMapping(value = "/api/couponInterface/v1", method = RequestMethod.POST)
	@ResponseBody
	public CouponRes apiCouponInterface(@RequestBody CouponReq couponReq) {
		this.log.info("################ [SAC Shopping] Start : "+ DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));
		this.log.debug("----------------------------------------------------------------");
		this.log.debug("apiCouponInterface Controller started!!");
		this.log.debug("----------------------------------------------------------------");
		CouponRes couponRes = new CouponRes();
		try {
			couponRes = this.dePloy(couponReq, couponRes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.log.info("################ [SAC Shopping] End : "+ DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));
		return couponRes;

	}


	/**
	 * <pre>
	 * dePloy.
	 * </pre>
	 * 
	 * @param couponReq
	 *            couponReq
	 * @param couponRes
	 *            couponRes
	 * @return boolean
	 * @throws Exception
	 *             Exception
	 */
	private CouponRes dePloy(CouponReq couponReq, CouponRes couponRes) throws Exception {

		this.log.info("<CouponControl> dePloy...");

		boolean result = false;
		ErrorData errorData = new ErrorData();
		Map<String, String> map = null;
		List<CouponRes> couponList = new ArrayList<CouponRes>();
		try {

			map = new HashMap<String, String>();
			map.put("TX_ID", couponReq.getTxId());

			result = this.doValidParameter1(couponReq, errorData); // 기본적인 validation 확인 및 parameter 정보 setting

			if (result) {
				boolean success = false;
				switch (TX_TYPE_CODE.get(couponReq.getTxType())) {

				case BD:
					// brand 작업을 호출한다.
					DpBrandInfo brandInfo = new DpBrandInfo();
					result = this.doValidParameterBD(couponReq, brandInfo, errorData); // 기본적인 validation 확인 및 parameter
																					   // 정보 setting
					IcmsJobPrint.printBrand(brandInfo, "브랜드");
					if (result) {
						success = this.insertBrandInfo(brandInfo);
					}
					if (success) {
						couponRes.setFlag(true);
						couponRes.setBrandId(brandInfo.getCreateBrandId());
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
						map.put("ERROR_MSG", errorData.getErrorMsg());
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", errorData.getErrorCode());
						map.put("ERROR_MSG", errorData.getErrorMsg());
					}
					break;
				case CT:
					// 카달로그 작업을 호출한다.
					DpCatalogInfo catalogInfo = new DpCatalogInfo();
					result = this.doValidParameterCT(couponReq, catalogInfo, errorData); // 기본적인 validation 확인 및
																						 // parameter 정보
					// setting
					IcmsJobPrint.printCatalog(catalogInfo, "카달로그");
					if (result) {
						success = this.insertCatalogInfo(catalogInfo);
					}
					if (success) {
						couponRes.setFlag(true);
						couponRes.setCatalogId(catalogInfo.getCreateCatalogId());
						couponRes.setBrandId(catalogInfo.getCreateBrandId());
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
						map.put("ERROR_MSG", errorData.getErrorMsg());
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", errorData.getErrorCode());
						map.put("ERROR_MSG", errorData.getErrorMsg());
					}
					break;
				case CP:
					// 쿠폰 정보 작업을 호출한다

					result = this.doValidParameterCP(couponReq, errorData);
					if (result) {
						success = this.insertCouponInfo(couponReq);
					}

					if (success) {
						couponRes.setFlag(true);
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
						map.put("ERROR_MSG", errorData.getErrorMsg());
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", errorData.getErrorCode());
						map.put("ERROR_MSG", errorData.getErrorMsg());
					}

					break;
				case ST:
					// 쿠폰 상태 변경 호출한다
					success = this.updateForCouponStatus(couponReq);
					if (success) {
						couponRes.setFlag(true);
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
						map.put("ERROR_MSG", errorData.getErrorMsg());
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
						map.put("ERROR_MSG", errorData.getErrorMsg());
					}
					break;
				case LS:
					// 특가 상품 목록 조회 작업을 호출한다.
					if (StringUtils.isEmpty(couponReq.getCouponCode())) {
						throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (couponCode)", null);
					}
					String[] couponCodes = couponReq.getCouponCode().split(",");
					couponList = this.getSpecialProductList(couponCodes);
					map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
					map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
					map.put("ERROR_MSG", errorData.getErrorMsg());
					break;
				case DT:
					// 특가 상품 상세 조회 작업을 호출한다.
					if (StringUtils.isEmpty(couponReq.getItemCodes())) {
						throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (itemsCodes)", null);
					}
					
					String[] itemsCodes = couponReq.getItemCodes().split(",");
					couponRes = this.getSpecialProductDetail(couponReq.getCouponCode(),itemsCodes);
					if (couponRes.getRCode().equals("")) {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", couponRes.getRCode());
					}

					break;
				case SS:
					// 특가 쿠폰 상태 변경 호출한다 ( // 1:쿠폰ID 가져오기, 3 : 특가상품 및 상품 중지 처리 )
					if(couponReq.getSpecialCouponStep().equals("1")){
						String couponId = this.getSpecialProductCouponId(couponReq);
						if(StringUtils.isNotBlank(couponId)){
							success = true;
							couponRes.setCouponId(couponId);
						}
					}else{
						success = this.updateForSpecialCouponStatus(couponReq);	
					}
					
					if (success) {
						couponRes.setFlag(true);
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
						map.put("ERROR_MSG", errorData.getErrorMsg());
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
						map.put("ERROR_MSG", errorData.getErrorMsg());
					}

					break;	
				case AS:
					// 특정 기간에 대한 특가 상품 상세 조회 작업을 호출한다.
					couponRes = this.getSpecialProductDetailForDate(couponReq);
					if (couponRes.getRCode().equals("")) {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", couponRes.getRCode());
					}

					break;
				case OSLS:
					// One Store 특가 상품 목록 조회 작업을 호출한다.
					if (StringUtils.isEmpty(couponReq.getItemCodeList())) {
						throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (itemCodeList)", null);
					}
					String[] itemCodes = couponReq.getItemCodeList().split(",");
					couponList = this.getOneBrandSpecialProductList(itemCodes);
					map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
					map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
					map.put("ERROR_MSG", errorData.getErrorMsg());

					break;
				case OSDT:
					// One Store 특가 상품 상세 조회 작업을 호출한다.
					if (StringUtils.isEmpty(couponReq.getItemCodeList())) {
						throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (itemCodeList)", null);
					}

					itemsCodes = couponReq.getItemCodeList().split(",");
					couponRes = this.getOneBrandSpecialProductDetail(couponReq.getCouponCode(),itemsCodes);
					if (couponRes.getRCode().equals("")) {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", couponRes.getRCode());
					}
					break;
				case OSSS:
					// One Store 특가 쿠폰 상태 변경 호출한다 ( // 1:쿠폰ID 가져오기, 3 : 특가상품 및 상품 중지 처리 )
					if(couponReq.getSpecialCouponStep().equals("1")){
						String couponId = this.getOneBrandSpecialProductCouponId(couponReq);
						if(StringUtils.isNotBlank(couponId)){
							success = true;
							couponRes.setCouponId(couponId);
						}
					}else{
						success = this.updateForOneBrandSpecialCouponStatus(couponReq);
					}

					if (success) {
						couponRes.setFlag(true);
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
						map.put("ERROR_MSG", errorData.getErrorMsg());
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
						map.put("ERROR_MSG", errorData.getErrorMsg());
					}

					break;
					case OSAS:
					// One Store 특정 기간에 대한 특가 상품 상세 조회 작업을 호출한다.
					couponRes = this.getOneBrandSpecialProductDetailForDate(couponReq);
					if (couponRes.getRCode().equals("")) {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", couponRes.getRCode());
					}

					break;
				}
				this.sendResponseData(couponReq, map, couponRes, couponList);

			} else {
				// Error Messag 를 작성한다.
				map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
				map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
				map.put("ERROR_MSG", errorData.getErrorMsg());
				this.sendResponseData(couponReq, map, couponRes, null);
			}

		} catch (CouponException ex) {
			// 1차 Logic 처리시 벌생한 Coupon Exception 처리.
			if (ex.getErrCode() != null) {
				map.put("ERROR_CODE", ex.getErrCode());
			}

			if (map != null) {
				if (map.get("ERROR_CODE") == null || map.get("ERROR_CODE").equals(""))
					map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR);
				map.put("ERROR_MSG", ex.getMessage());
				map.put("ERROR_VALUE", ex.getErrValue());
				this.sendResponseData(couponReq, map, couponRes, null);
			}

		} catch (Exception e) {
			// Exception 처리.
			if (map != null) {
				map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
				map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_SERVICE_STOP);
				map.put("ERROR_MSG", e.getMessage());
				this.sendResponseData(couponReq, map, couponRes, null);
			}
		}

		return couponRes;
	}

	/**
	 * 브랜드 정보를 추가한다.
	 * 
	 * @param dpBrandInfo
	 *            dpBrandInfo
	 * @return boolean
	 */
	private boolean insertBrandInfo(DpBrandInfo dpBrandInfo) {
		boolean result = this.shoppingCouponService.insertBrandInfo(dpBrandInfo);
		return result;

	}

	/**
	 * 카탈로그 정보를 추가한다.
	 * 
	 * @param dpCatalogInfo
	 *            dpCatalogInfo
	 * @return boolean
	 */
	private boolean insertCatalogInfo(DpCatalogInfo dpCatalogInfo) {
		boolean result = this.shoppingCouponService.insertCatalogInfo(dpCatalogInfo);
		return result;

	}

	/**
	 * 쿠폰 정보를 추가한다.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return boolean
	 */
	private boolean insertCouponInfo(CouponReq couponReq) {
		boolean result = this.couponProcessService.insertCouponInfo(couponReq);
		return result;

	}

	/**
	 * 상품 상태 변경한다.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return boolean
	 */
	private boolean updateForCouponStatus(CouponReq couponReq) {
		boolean result = this.couponProcessService.updateForCouponStatus(couponReq);
		return result;

	}
	
	/**
	 * 팅/특가 쿠폰 ID 조회 한다.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return String
	 */
	private String getSpecialProductCouponId(CouponReq couponReq) {
		String result = this.couponProcessService.getSpecialProductCouponId(couponReq);
		return result;

	}



	/**
	 * 팅/특가 상품 상태 변경 한다.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return boolean
	 */
	private boolean updateForSpecialCouponStatus(CouponReq couponReq) {
		boolean result = this.couponProcessService.updateForSpecialCouponStatus(couponReq);
		return result;

	}

	/**
	 * One Store 팅/특가 쿠폰 ID 조회 한다.
	 *
	 * @param couponReq
	 *            couponReq
	 * @return String
	 */
	private String getOneBrandSpecialProductCouponId(CouponReq couponReq) {
		String result = this.couponProcessService.getOneBrandSpecialProductCouponId(couponReq);
		return result;

	}

	/**
	 * One Store 팅/특가 상품 상태 변경 한다.
	 *
	 * @param couponReq
	 *            couponReq
	 * @return boolean
	 */
	private boolean updateForOneBrandSpecialCouponStatus(CouponReq couponReq) {
		boolean result = this.couponProcessService.updateForOneBrandSpecialCouponStatus(couponReq);
		return result;

	}

	/**
	 * 특가 상품 목록 조회 한다.
	 * 
	 * @param couponCodes
	 *            couponCodes
	 * @return List<CouponRes>
	 */
	private List<CouponRes> getSpecialProductList(String[] couponCodes) {
		List<CouponRes> result = this.couponProcessService.getSpecialProductList(couponCodes);
		return result;

	}

	/**
	 * One Store 특가 상품 목록 조회 한다.
	 *
	 * @param itemCodes
	 *            itemCodes
	 * @return List<CouponRes>
	 */
	private List<CouponRes> getOneBrandSpecialProductList(String[] itemCodes) {
		List<CouponRes> result = this.couponProcessService.getOneBrandSpecialProductList(itemCodes);
		return result;

	}


	/**
	 * 특가 상품 상세 조회 한다.
	 * 
	 * @param couponCode
	 *            couponCode
	 * @param itemsCodes
	 *            itemsCodes            
	 * @return CouponRes
	 */
	private CouponRes getSpecialProductDetail(String couponCode ,String[] itemsCodes) {
		CouponRes result = this.couponProcessService.getSpecialProductDetail(couponCode ,itemsCodes);
		return result;

	}

	/**
	 * One Store 특가 상품 상세 조회 한다.
	 *
	 * @param couponCode
	 *            couponCode
	 * @param itemsCodes
	 *            itemsCodes
	 * @return CouponRes
	 */
	private CouponRes getOneBrandSpecialProductDetail(String couponCode ,String[] itemsCodes) {
		CouponRes result = this.couponProcessService.getOneBrandSpecialProductDetail(couponCode, itemsCodes);
		return result;

	}
	
	/**
	 * 특정 기간에 대한 특가 상품 상세 조회 작업을 호출한다.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return CouponRes
	 */
	private CouponRes getSpecialProductDetailForDate(CouponReq couponReq) {
		CouponRes result = this.couponProcessService.getSpecialProductDetailForDate(couponReq);
		return result;

	}

	/**
	 * One Store 특정 기간에 대한 특가 상품 상세 조회 작업을 호출한다.
	 *
	 * @param couponReq
	 *            couponReq
	 * @return CouponRes
	 */
	private CouponRes getOneBrandSpecialProductDetailForDate(CouponReq couponReq) {
		CouponRes result = this.couponProcessService.getOneBrandSpecialProductDetailForDate(couponReq);
		return result;

	}

	/**
	 * 특가 상품 상세 조회 한다.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @param errorData
	 *            errorData
	 * @return boolean
	 */
	private boolean doValidParameter1(CouponReq couponReq, ErrorData errorData) {
		this.log.info("<CouponControl> doValidParameter1...");
		String message = "";
		boolean result = true;

		try {

			if (couponReq == null) {
				result = false;
				message="Parameter정보가 없습니다.\n";
			}else if(StringUtils.isBlank(couponReq.getTxId())){
				result = false;
				message="필수 파라미터 값이 없습니다. (txId)\n";
			}else if (!couponReq.checkTxId()) {
				result = false;
				message="txId 형식에 맞지 않습니다. [22자리]\n";
			}else if(StringUtils.isBlank(couponReq.getTxType())){
				result = false;
				message="필수 파라미터 값이 없습니다. (txType)\n";
			}

			if (!result) {
				errorData.setErrorMsg(message);
				errorData.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_TYPE);
			}
			return result;

		} catch (Exception e) {
			this.log.error("doValidParameter 작업중 예외 발생", e);
			message= e.getMessage() + "\n";
			errorData.setErrorMsg(message);
			errorData.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
			result = false;
			return result;

		}

	}

	/**
	 * 브랜드 파라미터 셋팅 조회 한다.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @param brandInfo
	 *            brandInfo
	 * @param errorData
	 *            errorData
	 * @return boolean
	 */
	private boolean doValidParameterBD(CouponReq couponReq, DpBrandInfo brandInfo, ErrorData errorData) {
		this.log.info("<CouponControl> doValidParameterBD...");
		String message = "";
		boolean result = true;
		try {
			brandInfo.setBrandNm(couponReq.getBrandName());
			brandInfo.setDpCatNo(couponReq.getBrandCategory());
			brandInfo.setBrandId(couponReq.getBrandCode());
			brandInfo.setBrandImgPath(couponReq.getBrandImage());
			brandInfo.setCudType(couponReq.getCudType());
			brandInfo.setTxType(couponReq.getTxType());
			brandInfo.setFileNameList(couponReq.getFileNameList());
			brandInfo.setIsList(couponReq.getIsList());
			brandInfo.setChargeBrandYn(couponReq.getChargeBrandYn());
			
			if (StringUtils.isEmpty(couponReq.getCudType())) {
				result = false;
				message="필수 파라미터 값이 없습니다. (cudType)\n";
			}else if (!couponReq.getCudType().equals("C") && !couponReq.getCudType().equals("U")) {
				message="cudType 값은 C or U 로만 가능합니다.\n";
				result = false;
			}
			if (StringUtils.isEmpty(brandInfo.getBrandId())) {
				result = false;
				message="필수 파라미터 값이 없습니다. (brandCode)\n";
			}
			if (StringUtils.isEmpty(brandInfo.getDpCatNo())) {
				result = false;
				message="필수 파라미터 값이 없습니다. (brandCategory)\n";
			}
			if (StringUtils.isEmpty(brandInfo.getBrandNm())) {
				result = false;
				message="필수 파라미터 값이 없습니다. (brandName)\n";
			} else if (brandInfo.getBrandNm().length() > 50) {
				result = false;
				message="brandName은 length 50을 가질수 없습니다.\n";
			}
			if (StringUtils.isEmpty(brandInfo.getBrandImgPath())) {
				result = false;
				message="필수 파라미터 값이 없습니다. (brandImage)\n";
			}
			if (StringUtils.isEmpty(couponReq.getChargeBrandYn())) {
				result = false;
				message="필수 파라미터 값이 없습니다. (chargeBrandYn)\n";
			}else if (!couponReq.getChargeBrandYn().equals("Y") && !couponReq.getChargeBrandYn().equals("N")) {
				message="chargeBrandYn 값은 Y or N 로만 가능합니다.\n";
				result = false;
			}
			
			if (!result) {
				errorData.setErrorMsg(message);
				errorData.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			}
			return result;

		} catch (Exception e) {
			this.log.error("doValidParameter 작업중 예외 발생", e);
			message = e.getMessage() + "\n";
			result = false;
			errorData.setErrorMsg(message);
			errorData.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
			return result;
		}

	}

	/**
	 * 카탈로그 파라미터 셋팅 조회 한다.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @param catalogInfo
	 *            catalogInfo
	 * @param errorData
	 *            errorData
	 * @return boolean
	 */
	private boolean doValidParameterCT(CouponReq couponReq, DpCatalogInfo catalogInfo, ErrorData errorData) {
		this.log.info("<CouponControl> doValidParameterCT...");
		String message = "";
		boolean result = true;
		try {
			catalogInfo.setCudType(couponReq.getCudType());
			catalogInfo.setCatalogId(couponReq.getCatalogCode());
			catalogInfo.setDpCatNo(couponReq.getCatalogCategory());
			catalogInfo.setBrandId(couponReq.getBrandCode());
			catalogInfo.setCatalogDesc(couponReq.getCatalogDescription());
			catalogInfo.setCatalogNm(couponReq.getCatalogName());
			catalogInfo.setTopImgPath(couponReq.getCatalogImage1());
			catalogInfo.setDtlImgPath(couponReq.getCatalogImage2());
			catalogInfo.setIntroText(couponReq.getIntro_text());
			catalogInfo.setCatalogTag(couponReq.getTag());
			catalogInfo.setFileNameList(couponReq.getFileNameList());
			catalogInfo.setIsList(couponReq.getIsList());
			catalogInfo.setCatalogVodUrl(couponReq.getCatalogVodUrl());
			catalogInfo.setCatalogVodThumbnail(couponReq.getCatalogVodThumbnail());
			
			if (StringUtils.isEmpty(couponReq.getCudType())) {
				result = false;
				message ="필수 파라미터 값이 없습니다. (cudType)\n";
			}else if (!couponReq.getCudType().equals("C") && !couponReq.getCudType().equals("U")) {
				message ="cudType 값은 C or U 로만 가능합니다.\n";
				result = false;
			}
			
			if (StringUtils.isEmpty(catalogInfo.getCatalogId())) {
				result = false;
				message ="필수 파라미터 값이 없습니다. (catalogCode)\n";
			}
			if (StringUtils.isEmpty(catalogInfo.getCatalogNm())) {
				result = false;
				message ="필수 파라미터 값이 없습니다. (catalogName)\n";
			} else if (catalogInfo.getCatalogNm().length() > 100) {
				result = false;
				message ="catalogName은 length 100을 가질수 없습니다.";
			}
			if (StringUtils.isEmpty(catalogInfo.getDpCatNo())) {
				result = false;
				message ="필수 파라미터 값이 없습니다. (catalogCategory)\n";
			}

			if (StringUtils.isEmpty(catalogInfo.getCatalogDesc())) {
				result = false;
				message ="필수 파라미터 값이 없습니다. (catalogDescription)\n";
			} else if (catalogInfo.getCatalogDesc().length() > 4000) {
				result = false;
				message ="catalogDescription은 length 4000 가질수 없습니다.\n";
			}

			if (StringUtils.isEmpty(catalogInfo.getTopImgPath())) {
				result = false;
				message ="필수 파라미터 값이 없습니다. (catalogImage1)\n";
			}
			if (StringUtils.isEmpty(catalogInfo.getDtlImgPath())) {
				result = false;
				message ="필수 파라미터 값이 없습니다. (catalogImage2)\n";
			}
			if (StringUtils.isEmpty(catalogInfo.getBrandId())) {
				result = false;
				message ="필수 파라미터 값이 없습니다. (brandCode)\n";
			}
			if (StringUtils.isEmpty(catalogInfo.getIntroText())) {
				result = false;
				message ="필수 파라미터 값이 없습니다. (intro_text)\n";
			} else if (catalogInfo.getIntroText().length() > 150) {
				result = false;
				message ="intro_text는 length 150을 가질수 없습니다.\n";
			}

			if (!result) {
				errorData.setErrorMsg(message);
				errorData.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			}

			return result;

		} catch (Exception e) {
			this.log.error("doValidParameter 작업중 예외 발생", e);
			message=e.getMessage() + "\n";
			result = false;
			errorData.setErrorMsg(message);
			errorData.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
			return result;
		}

	}

	/**
	 * 쿠폰,아이템 파라미터 셋팅 조회 한다.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @param errorData
	 *            errorData
	 * @return boolean
	 */
	private boolean doValidParameterCP(CouponReq couponReq, ErrorData errorData) {
		String couponProdId = "";
		String itemProdId = "";
		boolean result = true;

		DpCouponInfo couponInfo = couponReq.getDpCouponInfo(); // 쿠폰 정보
		List<DpItemInfo> itemInfoList = couponReq.getDpItemInfo();

		// 2014.04.02 이현남 매니저 요청 사항 C -> U로 수정 해달라고 요청옴.
		this.couponItemchangeCud(couponInfo, itemInfoList, couponReq);

		if ("C".equalsIgnoreCase(couponReq.getCudType())) {
			couponProdId = this.couponItemService.couponGenerateId(); // 쿠폰 ID 생성
		} else if ("U".equalsIgnoreCase(couponReq.getCudType())) {
			couponProdId = this.couponItemService.getCouponGenerateId(couponInfo.getCouponCode()); // 기존 쿠폰 가져오기
		}
		if (StringUtils.isBlank(couponProdId)) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "[couponProdId]를 생성하지 못했습니다.",
					"");
		}
		couponInfo.setProdId(couponProdId);

		for (DpItemInfo itemInfo : itemInfoList) {
			if ("C".equalsIgnoreCase(itemInfo.getCudType())) {
				itemProdId = this.couponItemService.itemGenerateId(); // 아이템 prodId 생성
				itemInfo.setProdId(itemProdId); // 아이템 prodId 생성
			} else if ("U".equalsIgnoreCase(itemInfo.getCudType())) {
				itemProdId = this.couponItemService.getItemGenerateId(itemInfo.getItemCode()); // 기존 아이템 ID 가져오기
				itemInfo.setProdId(itemProdId);
			}
			if (StringUtils.isBlank(itemProdId)) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
						"[itemProdId]를 생성하지 못했습니다.", "");
			}
		}

		if (!this.doValidateCouponInfo(couponInfo, errorData)) {
			result = false;
		}
		for (int i = 0; i < itemInfoList.size(); i++) {
			if (!this.doValidateItemInfo(itemInfoList.get(i), errorData)) {
				result = false;
			}
			break;
		}
		couponReq.setDpCouponInfo(couponInfo);
		couponReq.setDpItemInfo(itemInfoList);

		this.log.info("<<<<< MetaDefXMLParser.makeContentXMLMap >>>>> END");

		return result;

	}

	/**
	 * 쇼핑쿠폰 API 응답은 VO로 셋팅한다.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @param map
	 *            map
	 * @param couponRes
	 *            couponRes
	 * @param couponList
	 *            couponList
	 * @return boolean
	 */
	private boolean sendResponseData(CouponReq couponReq, Map<String, String> map, CouponRes couponRes,
			List<CouponRes> couponList) {
		this.log.info("<CouponControl> sendResponseData...");

		boolean success = true;
		try {
			if (map != null) {
				couponRes.setRCode(map.get("ERROR_CODE"));
				couponRes.setRMsg(CouponConstants.getCouponErrorMsg(map.get("ERROR_CODE"), map.get("ERROR_MSG")));
				if (map.get("ERROR_VALUE") != null && !map.get("ERROR_VALUE").equals("")) {
					couponRes.setRMsg(CouponConstants.getCouponErrorMsg(map.get("ERROR_CODE"), map.get("ERROR_MSG"))
							+ map.get("ERROR_VALUE"));
				}
			}
			switch (TX_TYPE_CODE.get(couponReq.getTxType())) {
			case BD:
				break;
			case CT:
				break;
			case CP:
				StringBuffer couponBuff = new StringBuffer();
				couponBuff.append(couponReq.getDpCouponInfo().getProdId());
				couponBuff.append(":");
				couponBuff.append(couponReq.getDpCouponInfo().getCouponCode());
				couponRes.setCouponId(couponBuff.toString());

				StringBuffer itemBuff = new StringBuffer();
				for (int i = 0; i < couponReq.getDpItemInfo().size(); i++) {
					if (i == 0) {
						itemBuff.append(couponReq.getDpItemInfo().get(i).getProdId());
						itemBuff.append(":");
						itemBuff.append(couponReq.getDpItemInfo().get(i).getItemCode());
					} else {
						itemBuff.append(",");
						itemBuff.append(couponReq.getDpItemInfo().get(i).getProdId());
						itemBuff.append(":");
						itemBuff.append(couponReq.getDpItemInfo().get(i).getItemCode());
					}
				}
				couponRes.setItemId(itemBuff.toString());
				break;
			case ST:
				break;
			case AT:
				break;
			case LS:

				String seperatorComma = "";
//				String eventList = "";
				StringBuffer eventList = new StringBuffer();
				int j = 0;
				if (couponList != null) {
					for (CouponRes couponInfo : couponList) {
						if (j > 0)
							seperatorComma = ",";
						eventList.append(seperatorComma).append(couponInfo.getCouponCode()).append(":").append(couponInfo.getSpecialYN());

						j++;
					}
				}
				couponRes.setEventList(eventList.toString());
				break;
			case OSLS:

				List<SprcInfo> sprcInfoList =  new ArrayList<SprcInfo>();
				int kk =0;
				List<ItemCodeInfo> itemCodeInfoList =  new ArrayList<ItemCodeInfo>();
				ItemCodeInfo itemCodeInfo = new ItemCodeInfo();
				SprcInfo sprcInfo = new SprcInfo();
				if (couponList != null) {
					for (CouponRes couponInfo : couponList) {
						kk++;
						itemCodeInfo.setItemCode(couponInfo.getCouponCode());
						sprcInfo = new SprcInfo();
						sprcInfo.setTenantId(couponInfo.getTenantId());
						sprcInfo.setSprcGubun(couponInfo.getSpecialYN());
						sprcInfoList.add(sprcInfo);
						if(kk%3==0){
							itemCodeInfo.setSprcInfo(sprcInfoList);
							itemCodeInfoList.add(itemCodeInfo);
							itemCodeInfo = new ItemCodeInfo();
							sprcInfoList =  new ArrayList<SprcInfo>();
						}
					}
				}
				couponRes.setItemCodeInfo(itemCodeInfoList);
				break;
			case DT:
				break;
			case SS:
				break;				
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * 쿠폰 정보 유효성 체크.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param errorData
	 *            errorData
	 * @return boolean
	 */
	private boolean doValidateCouponInfo(DpCouponInfo couponInfo, ErrorData errorData) {
		boolean result = true;
		String message = "";

		try {
			if (couponInfo.getProdId().equals("")) {
				message = "유효성 검사 실패 [couponCode : 상품코드] 이 XML에 존재하지 않습니다.\n";
				result = false;
			}
			if (couponInfo.getCouponName().equals("")) {
				message = "유효성 검사 실패 [couponName : 쿠폰명] 이 XML에 존재하지 않습니다.\n";
				result = false;
			}
			if (couponInfo.getCouponName().length() > 100) {
				message = "유효성 검사 실패 [couponName : 쿠폰명:" + couponInfo.getCouponName() + "]\n";
				result = false;
			}
			if (couponInfo.getIssueSDate().equals("")) {
				message = "유효성 검사 실패 [issueSDate : 발급시작일시] 이 XML에 존재하지 않습니다.\n";
				result = false;
			}
			if (!couponInfo.getIssueSDate().equals("")) {
				if (couponInfo.getIssueSDate().length() > 14 || !StringUtils.isNumeric(couponInfo.getIssueSDate())) {
					message = "유효성 검사 실패 [issueSDate : 발급시작일시:" + couponInfo.getIssueSDate() + "]\n";
					result = false;
				}
			}
			if (couponInfo.getIssueEDate().equals("")) {
				message = "유효성 검사 실패 [issueEDate : 발급종료일시] 이 XML에 존재하지 않습니다.\n";
				result = false;
			}
			if (!couponInfo.getIssueEDate().equals("")) {
				if (couponInfo.getIssueEDate().length() > 14 || !StringUtils.isNumeric(couponInfo.getIssueEDate())) {
					message = "유효성 검사 실패 [issueEDate : 발급종료일시:" + couponInfo.getIssueEDate() + "]\n";
					result = false;
				}
			}
			if (couponInfo.getValidSDate().equals("")) {
				message = "유효성 검사 실패 [validSDate : 유효시작일시] 이 XML에 존재하지 않습니다.\n";
				result = false;
			}
			if (!couponInfo.getValidSDate().equals("")) {
				if (couponInfo.getValidSDate().length() > 14 || !StringUtils.isNumeric(couponInfo.getValidSDate())) {
					message = "유효성 검사 실패 [validSDate : 유효시작일시:" + couponInfo.getValidSDate() + "]\n";
					result = false;
				}
			}
			if (couponInfo.getValidEDate().equals("")) {
				message = "유효성 검사 실패 [validEDate : 유효종료일시] 이 XML에 존재하지 않습니다.\n";
				result = false;
			}
			if (!couponInfo.getValidEDate().equals("")) {
				if (couponInfo.getValidEDate().length() > 14 || !StringUtils.isNumeric(couponInfo.getValidEDate())) {
					message = "유효성 검사 실패 [validEDate : 유효종료일시:" + couponInfo.getValidEDate() + "]\n";
					result = false;
				}
			}
			if (couponInfo.getValidUntil().equals("")) {
				message = "유효성 검사 실패 [validUntil : 유효일수] 이 XML에 존재하지 않습니다.\n";
				result = false;
			}
			if (!couponInfo.getValidUntil().equals("")) {
				if (!StringUtils.isNumeric(couponInfo.getValidUntil())) {
					message = "유효성 검사 실패 [validUntil : 유효일수:" + couponInfo.getValidUntil() + "]\n";
					result = false;
				}
			}
			if (!couponInfo.getDescription().equals("")) {
				if (couponInfo.getDescription().length() > 4000) {
					message = "유효성 검사 실패 [description : 쿠폰설명:" + couponInfo.getDescription() + "]\n";
					result = false;
				}
			}
			if (!couponInfo.getDirection().equals("")) {
				if (couponInfo.getDirection().length() > 4000) {
					message = "유효성 검사 실패 [direction : 사용장소:" + couponInfo.getDirection() + "]\n";
					result = false;
				}
			}
			if (!couponInfo.getUseCondition().equals("")) {
				if (couponInfo.getUseCondition().length() > 4000) {
					message = "유효성 검사 실패 [useCondition : 사용제한:" + couponInfo.getUseCondition() + "]\n";
					result = false;
				}
			}
			if (!couponInfo.getAddtionalInfo().equals("")) {
				if (couponInfo.getAddtionalInfo().length() > 1000) {
					message = "유효성 검사 실패 [addtionalInfo : 주의사항:" + couponInfo.getAddtionalInfo() + "]\n";
					result = false;
				}
			}
			if (!couponInfo.getRefundCondition().equals("")) {
				if (couponInfo.getRefundCondition().length() > 4000) {
					message = "유효성 검사 실패 [refundCondition : 구매취소(환불) 조건:" + couponInfo.getRefundCondition() + "]";
					result = false;
				}
			}

			if (!couponInfo.getStoreSaleType().equals("1") 
					&& !couponInfo.getStoreSaleType().equals("2")
					&& !couponInfo.getStoreSaleType().equals("3")
					&& !couponInfo.getStoreSaleType().equals("4")
					&& !couponInfo.getStoreSaleType().equals("5")
				) 
			{
				message = "유효성 검사 실패 [storeSaleType : 상품유형:" + couponInfo.getStoreSaleType() + "]";
				result = false;
			}
			if (couponInfo.getStoreSaleType().equals("")) {
				message = "유효성 검사 실패 [storeSaleType : 상품유형] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!couponInfo.getStoreb2bFlag().equals("Y") && !couponInfo.getStoreb2bFlag().equals("N")) {
				message = "유효성 검사 실패 [storeb2bFlag : B2B상품여부:" + couponInfo.getStoreb2bFlag() + "]";
				result = false;
			}
			if (couponInfo.getStoreb2bFlag().equals("")) {
				message = "유효성 검사 실패 [storeb2bFlag : B2B상품여부] 이 XML에 존재하지 않습니다.";
				result = false;
			}

			if (couponInfo.getStoreCatalogCode().equals("")) {
				message = "유효성 검사 실패 [storeCatalogCode : 카탈로그 번호] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getAccountingRate().equals("")) {
				message = "유효성 검사 실패 [accountingRate : 정산율] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!couponInfo.getAccountingRate().equals("")) {
				if (!StringUtils.isNumeric(couponInfo.getAccountingRate())) {
					message = "유효성 검사 실패 [accountingRate : 정산율:" + couponInfo.getAccountingRate() + "]";
					result = false;
				}
			}

			if (!couponInfo.getTaxType().equals("01") && !couponInfo.getTaxType().equals("02")
					&& !couponInfo.getTaxType().equals("03")) {
				message = "유효성 검사 실패 [taxType : 세금구분유형:" + couponInfo.getTaxType() + "]";
				result = false;
			}
			if (couponInfo.getTaxType().equals("")) {
				message = "유효성 검사 실패 [taxType : 세금구분유형] 이 XML에 존재하지 않습니다.";
				result = false;
			}

			if (!couponInfo.getSendMsgType().equals("LMS") && !couponInfo.getSendMsgType().equals("MMS")) {
				message = "유효성 검사 실패 [sendMsgType : 문자 전송 유형:" + couponInfo.getSendMsgType() + "]";
				result = false;
			}
			if (couponInfo.getSendMsgType().equals("")) {
				message = "유효성 검사 실패 [sendMsgType : 문자 전송 유형] 이 XML에 존재하지 않습니다.";
				result = false;
			}

			if (couponInfo.getBpId().equals("")) {
				message = "유효성 검사 실패 [bpId : 업체아이디] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getCoupnStatus().equals("")) {
				message = "유효성 검사 실패 [coupnStatus : 쿠폰상태] 이 XML에 존재하지 않습니다.";
				result = false;
			}

			if (!result) {
				errorData.setErrorMsg(message);
				errorData.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			} else {
				result = true;
			}
		} catch (Exception e) {
			this.log.error("유효성 검사 실패", e);
			message = e.getMessage() + "\n";
			result = false;
			errorData.setErrorMsg(message);
			errorData.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			return result;
		}

		return result;
	}

	/**
	 * 아이템 정보 유효성 체크.
	 * 
	 * @param itemInfo
	 *            itemInfo
	 * @param errorData
	 *            errorData
	 * @return boolean
	 */
	private boolean doValidateItemInfo(DpItemInfo itemInfo, ErrorData errorData) {
		boolean result = true;
		String message = "";
		try {
			if (itemInfo.getItemCode().equals("")) {
				message = "유효성 검사 실패 [itemCode : 단품코드 :이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getStoreLicenseCode().equals("")) {
				message = "유효성 검사 실패 [storeLicenseCode : 스토어 라이선스 번호 :이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getItemName().equals("")) {
				message = "유효성 검사 실패 [itemName : 단품명 :이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getItemName().length() > 250) {
				message = "유효성 검사 실패 [itemName : 단품명 :" + itemInfo.getItemName() + "]";
				result = false;
			}
			if (itemInfo.getOrgPrice().equals("")) {
				message = "유효성 검사 실패 [orgPrice : 정상가격 :이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!itemInfo.getOrgPrice().equals("")) {
				if (!StringUtils.isNumeric(itemInfo.getOrgPrice())) {
					message = "유효성 검사 실패 [orgPrice : 정상가격 :" + itemInfo.getOrgPrice() + "]";
					result = false;
				}
			}
			if (itemInfo.getSalePrice().equals("")) {
				message = "유효성 검사 실패 [salePrice : 할인가격 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!itemInfo.getSalePrice().equals("")) {
				if (!StringUtils.isNumeric(itemInfo.getSalePrice())) {
					message = "유효성 검사 실패 [salePrice : 할인가격 :" + itemInfo.getSalePrice() + "]";
					result = false;
				}
			}
			if (itemInfo.getItemPrice().equals("")) {
				message = "유효성 검사 실패 [itemPrice : 단품가격 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!itemInfo.getItemPrice().equals("")) {
				if (!StringUtils.isNumeric(itemInfo.getItemPrice())) {
					message = "유효성 검사 실패 [itemPrice : 단품가격 :" + itemInfo.getItemPrice() + "]";
					result = false;
				}
			}
			if (itemInfo.getDcRate().equals("")) {
				message = "유효성 검사 실패 [dcRate : 할인율 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!itemInfo.getDcRate().equals("")) {
				if (!StringUtils.isNumeric(itemInfo.getDcRate())) {
					message = "유효성 검사 실패 [dcRate : 할인율 :" + itemInfo.getDcRate() + "]";
					result = false;
				}
			}
			if (itemInfo.getMaxCount().equals("")) {
				message = "유효성 검사 실패 [maxCount : 판매개수 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!itemInfo.getMaxCount().equals("")) {
				if (!StringUtils.isNumeric(itemInfo.getMaxCount())) {
					message = "유효성 검사 실패 [maxCount : 판매개수 :" + itemInfo.getMaxCount() + "]";
					result = false;
				}
			}
			if (itemInfo.getMaxCountMonthly().equals("")) {
				message = "유효성 검사 실패 [maxCountMonthly : 월간 상품 최대 판매 수량 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!itemInfo.getMaxCountMonthly().equals("")) {
				if (!StringUtils.isNumeric(itemInfo.getMaxCountMonthly())) {
					message = "유효성 검사 실패 [maxCountMonthly : 월간 상품 최대 판매 수량 :" + itemInfo.getMaxCountMonthly() + "]";
					result = false;
				}
			}
			if (itemInfo.getMaxCountDaily().equals("")) {
				message = "유효성 검사 실패 [maxCountDaily : 일간 상품 최대 판매 수량 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!itemInfo.getMaxCountDaily().equals("")) {
				if (!StringUtils.isNumeric(itemInfo.getMaxCountDaily())) {
					message = "유효성 검사 실패 [maxCountDaily : 일간 상품 최대 판매 수량 :" + itemInfo.getMaxCountDaily() + "]";
					result = false;
				}
			}
			if (itemInfo.getMaxCountMonthlyUser().equals("")) {
				message = "유효성 검사 실패 [maxCountMonthlyUser : 1인 당월 최대 구매 수량 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!itemInfo.getMaxCountMonthlyUser().equals("")) {
				if (!StringUtils.isNumeric(itemInfo.getMaxCountMonthlyUser())) {
					message = "유효성 검사 실패 [maxCountMonthlyUser : 1인 당월 최대 구매 수량 :" + itemInfo.getMaxCountMonthlyUser()
							+ "]";
					result = false;
				}
			}
			if (itemInfo.getMaxCountDailyUser().equals("")) {
				message = "유효성 검사 실패 [maxCountDailyUser : 1인 당일 최대 구매 수량 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!itemInfo.getMaxCountDailyUser().equals("")) {
				if (!StringUtils.isNumeric(itemInfo.getMaxCountDailyUser())) {
					message = "유효성 검사 실패 [maxCountDailyUser : 1인 당일 최대 구매 수량 :" + itemInfo.getMaxCountDailyUser() + "]";
					result = false;
				}
			}
			if (itemInfo.getBuyMaxLimit().equals("")) {
				message = "유효성 검사 실패 [buyMaxLimit : 최대결제수량 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!itemInfo.getBuyMaxLimit().equals("")) {
				if (!StringUtils.isNumeric(itemInfo.getBuyMaxLimit())) {
					message = "유효성 검사 실패 [buyMaxLimit : 최대결제수량 :" + itemInfo.getBuyMaxLimit() + "]";
					result = false;
				}
			}

			if (!itemInfo.getBpManageId().equals("")) {
				if (itemInfo.getBpManageId().length() > 32) {
					message = "유효성 검사 실패 [bpManageId : BP관리ID :" + itemInfo.getBpManageId() + "]";
					result = false;
				}
			}

			if (!itemInfo.getCudType().equals("C") && !itemInfo.getCudType().equals("U")) {
				message = "유효성 검사 실패 [cudType : 추가수정플래그 :" + itemInfo.getCudType() + "]";
				result = false;
			}
			if (itemInfo.getCudType().equals("")) {
				message = "유효성 검사 실패 [cudType : 추가수정플래그 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getItemStatus().equals("")) {
				message = "유효성 검사 실패 [itemStatus : 단품상태 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!result) {
				errorData.setErrorMsg(message);
				errorData.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			} else {
				result = true;
			}
		} catch (Exception e) {
			this.log.error("유효성 검사 실패", e);
			message = e.getMessage() + "\n";
			result = false;
			errorData.setErrorMsg(message);
			errorData.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			return result;
		}
		return result;
	}

	/**
	 * 2014.04.02 이현남 매니저 요청 사항 C -> U로 수정 해달라고 요청옴.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param couponReq
	 *            couponReq
	 * @return
	 */
	private void couponItemchangeCud(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList, CouponReq couponReq) {

		if (couponReq.getCudType().equals("") || couponReq.getCudType() == null) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
					"필수 파라미터 값이 없습니다. (cudType)", "");
		}else if (!couponReq.getCudType().equals("C") && !couponReq.getCudType().equals("U")) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
					"cudType 값은 C or U 로만 가능합니다.", "");
		}
		
		if (StringUtils.equalsIgnoreCase("C", couponReq.getCudType())) {
			if (this.couponItemService.getCouponCountCudType(couponInfo.getCouponCode()) > 0) {
				couponReq.setCudType("U");
			}
		}
		for (int i = 0; i < itemInfoList.size(); i++) {
			if (StringUtils.equalsIgnoreCase("C", itemInfoList.get(i).getCudType())) {
				if (this.couponItemService.getItemCountCudType(itemInfoList.get(i).getItemCode()) > 0) {
					itemInfoList.get(i).setCudType("U");
					this.log.info("itemInfoList" + i + "번째::" + itemInfoList.get(i).getCudType());
				}
			}
		}
	}

}
