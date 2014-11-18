/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.tstore.sci.TStoreCashSCI;
import com.skplanet.storeplatform.external.client.tstore.sci.TStoreCouponSCI;
import com.skplanet.storeplatform.external.client.tstore.sci.TStoreNotiSCI;
import com.skplanet.storeplatform.external.client.tstore.vo.Coupon;
import com.skplanet.storeplatform.external.client.tstore.vo.ProdId;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashBalanceDetailEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashBalanceEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashBalanceEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeCancelDetailEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeCancelEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeCancelEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeConfirmDetailEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeConfirmEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeConfirmEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeReserveDetailEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeReserveDetailEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeReserveEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeReserveEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashIntgBalanceDetailEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashIntgBalanceEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashIntgBalanceEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreNotiEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreNotiEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreNotiV2EcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.TStoreNotiV2EcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.UserCouponListEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.UserCouponListEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 
 * T store 서비스
 * 
 * Updated on : 2014. 5. 28. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderTstoreServiceImpl implements PurchaseOrderTstoreService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseOrderAssistService purchaseOrderAssistService;
	@Autowired
	private TStoreCouponSCI tStoreCouponSCI;
	@Autowired
	private TStoreCashSCI tStoreCashSCI;
	@Autowired
	private TStoreNotiSCI tStoreNotiSCI;

	/**
	 * 
	 * <pre>
	 * T Store 쿠폰 목록 조회.
	 * </pre>
	 * 
	 * @param userKey
	 *            내부 회원 NO
	 * 
	 * @param deviceId
	 *            MDN
	 * 
	 * @param prodIdList
	 *            구매상품ID 목록
	 * 
	 * @return T Store 쿠폰 목록
	 */
	@Override
	public String searchTstoreCouponList(String userKey, String deviceId, List<String> prodIdList) {
		// TAKTEST:: 상용 -> BMS 연동 불가로 Skip
		// if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_REAL)) {
		// return "NULL";
		// }

		List<ProdId> prodIdObjList = new ArrayList<ProdId>();
		ProdId prodIdObj = null;
		for (String prodId : prodIdList) {
			prodIdObj = new ProdId();
			prodIdObj.setProdId(prodId);
			prodIdObjList.add(prodIdObj);
		}

		UserCouponListEcReq userCouponListEcReq = new UserCouponListEcReq();
		userCouponListEcReq.setUserKey(userKey);
		userCouponListEcReq.setMdn(deviceId);
		// userCouponListEcReq.setCouponType("");
		userCouponListEcReq.setProdIdList(prodIdObjList);

		UserCouponListEcRes userCouponListEcRes = null;
		try {
			this.logger.info("PRCHS,ORDER,SAC,TSTORE,COUPON,SEARCH,REQ,{}",
					ReflectionToStringBuilder.toString(userCouponListEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
			userCouponListEcRes = this.tStoreCouponSCI.getUserCouponList(userCouponListEcReq);
			this.logger.info("PRCHS,ORDER,SAC,TSTORE,COUPON,SEARCH,RES,{}",
					ReflectionToStringBuilder.toString(userCouponListEcRes, ToStringStyle.SHORT_PREFIX_STYLE));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7210", e);
		}

		if (StringUtils.equals(userCouponListEcRes.getResultCd(), PurchaseConstants.TSTORE_COUPON_RESULT_CD_SUCCESS) == false) {
			throw new StorePlatformException("SAC_PUR_7206", userCouponListEcRes.getResultCd(),
					userCouponListEcRes.getResultMsg());
		}

		if (CollectionUtils.isNotEmpty(userCouponListEcRes.getCouponList())) {
			StringBuffer sbTstoreCoupon = new StringBuffer(256);

			for (Coupon coupon : userCouponListEcRes.getCouponList()) {
				if (sbTstoreCoupon.length() > 0) {
					sbTstoreCoupon.append(";");
				}
				sbTstoreCoupon.append(coupon.getCouponId()).append(":")
						.append(StringUtils.replace(StringUtils.replace(coupon.getCouponName(), ":", ""), ";", ""))
						.append(":").append(coupon.getCouponAmt()).append(":").append(coupon.getMakeHost()).append(":")
						.append(coupon.getCouponType());
			}

			return sbTstoreCoupon.toString();

		} else {
			return "NULL";
		}
	}

	/**
	 * 
	 * <pre>
	 * T Store Cash 통합 잔액 조회.
	 * </pre>
	 * 
	 * @param userKey
	 *            내부 회원 NO
	 * 
	 * @return T Store Cash 통합 잔액 정보
	 */
	@Override
	public String searchTstoreCashIntegrationAmt(String userKey) {
		// TAKTEST:: 상용 -> BMS 연동 불가로 Skip
		// if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_REAL)) {
		// return 0.0;
		// }

		TStoreCashIntgBalanceEcReq tStoreCashIntgBalanceEcReq = new TStoreCashIntgBalanceEcReq();
		tStoreCashIntgBalanceEcReq.setUserKey(userKey);

		TStoreCashIntgBalanceEcRes tStoreCashIntgBalanceEcRes = null;
		try {
			this.logger.info("PRCHS,ORDER,SAC,TSTORE,CASH,INTEGRATION,SEARCH,REQ,{}",
					ReflectionToStringBuilder.toString(tStoreCashIntgBalanceEcReq, ToStringStyle.SHORT_PREFIX_STYLE));

			tStoreCashIntgBalanceEcRes = this.tStoreCashSCI.getIntegrationBalance(tStoreCashIntgBalanceEcReq);

			this.logger.info("PRCHS,ORDER,SAC,TSTORE,CASH,INTEGRATION,SEARCH,RES,{}",
					ReflectionToStringBuilder.toString(tStoreCashIntgBalanceEcRes, ToStringStyle.SHORT_PREFIX_STYLE));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7211", e);
		}

		if (StringUtils.equals(tStoreCashIntgBalanceEcRes.getResultCd(),
				PurchaseConstants.TSTORE_CASH_RESULT_CD_SUCCESS) == false) {
			throw new StorePlatformException("SAC_PUR_7207", tStoreCashIntgBalanceEcRes.getResultCd(),
					tStoreCashIntgBalanceEcRes.getResultMsg());
		}

		List<TStoreCashIntgBalanceDetailEcRes> tstoreCashList = tStoreCashIntgBalanceEcRes.getCashList();

		StringBuffer sbCashIntgAmt = new StringBuffer();
		for (TStoreCashIntgBalanceDetailEcRes cash : tstoreCashList) {
			if (sbCashIntgAmt.length() > 0) {
				sbCashIntgAmt.append(";");
			}
			if (StringUtils.equals(cash.getProductGroup(), PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_TSTORE_CASH)) {
				sbCashIntgAmt.append(PurchaseConstants.PAYPLANET_PAYMENT_METHOD_TSTORE_CASH).append(":")
						.append(Double.parseDouble(cash.getAmt()));
			} else if (StringUtils.equals(cash.getProductGroup(),
					PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_TSTORE_GAMECASH)) {
				sbCashIntgAmt.append(PurchaseConstants.PAYPLANET_PAYMENT_METHOD_GAMECASH).append(":")
						.append(Double.parseDouble(cash.getAmt()));
			} else if (StringUtils
					.equals(cash.getProductGroup(), PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_TGAMEPASS)) {
				sbCashIntgAmt.append(PurchaseConstants.PAYPLANET_PAYMENT_METHOD_TGAMEPASS_POINT).append(":")
						.append(Double.parseDouble(cash.getAmt()));
			}
		}

		return sbCashIntgAmt.toString();
	}

	/**
	 * 
	 * <pre>
	 * T Store Cash 잔액 조회.
	 * </pre>
	 * 
	 * @param userKey
	 *            내부 회원 NO
	 * 
	 * @return T Store Cash 잔액
	 */
	@Override
	public double searchTstoreCashAmt(String userKey) {
		// TAKTEST:: 상용 -> BMS 연동 불가로 Skip
		// if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_REAL)) {
		// return 0.0;
		// }

		TStoreCashBalanceEcReq tStoreCashEcReq = new TStoreCashBalanceEcReq();
		tStoreCashEcReq.setUserKey(userKey);
		tStoreCashEcReq.setProductGroup(PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_TSTORE_CASH); // 상품군 : T store Cash

		TStoreCashBalanceEcRes tStoreCashEcRes = null;
		try {
			this.logger.info("PRCHS,ORDER,SAC,TSTORE,CASH,SEARCH,REQ,{}",
					ReflectionToStringBuilder.toString(tStoreCashEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
			tStoreCashEcRes = this.tStoreCashSCI.getBalance(tStoreCashEcReq);
			this.logger.info("PRCHS,ORDER,SAC,TSTORE,CASH,SEARCH,RES,{}",
					ReflectionToStringBuilder.toString(tStoreCashEcRes, ToStringStyle.SHORT_PREFIX_STYLE));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7211", e);
		}

		if (StringUtils.equals(tStoreCashEcRes.getResultCd(), PurchaseConstants.TSTORE_CASH_RESULT_CD_SUCCESS) == false) {
			throw new StorePlatformException("SAC_PUR_7207", tStoreCashEcRes.getResultCd(),
					tStoreCashEcRes.getResultMsg());
		}

		List<TStoreCashBalanceDetailEcRes> tstoreCashList = tStoreCashEcRes.getCashList();
		double cashAmt = 0.0;
		for (TStoreCashBalanceDetailEcRes cash : tstoreCashList) {
			cashAmt += Double.parseDouble(cash.getAmt());
		}

		return cashAmt;
	}

	/**
	 * 
	 * <pre>
	 * T game pass 잔액 조회.
	 * </pre>
	 * 
	 * @param userKey
	 *            내부 회원 NO
	 * 
	 * @return T game pass 잔액
	 */
	@Override
	public double searchTgamepassAmt(String userKey) {
		// TAKTEST:: 상용 -> BMS 연동 불가로 Skip
		// if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_REAL)) {
		// return 0.0;
		// }

		TStoreCashBalanceEcReq tStoreCashEcReq = new TStoreCashBalanceEcReq();
		tStoreCashEcReq.setUserKey(userKey);
		tStoreCashEcReq.setProductGroup(PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_TGAMEPASS); // 상품군 : T game pass

		TStoreCashBalanceEcRes tStoreCashEcRes = null;
		try {
			this.logger.info("PRCHS,ORDER,SAC,TSTORE,TGAMEPASS,SEARCH,REQ,{}",
					ReflectionToStringBuilder.toString(tStoreCashEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
			tStoreCashEcRes = this.tStoreCashSCI.getBalance(tStoreCashEcReq);
			this.logger.info("PRCHS,ORDER,SAC,TSTORE,TGAMEPASS,SEARCH,RES,{}",
					ReflectionToStringBuilder.toString(tStoreCashEcRes, ToStringStyle.SHORT_PREFIX_STYLE));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7211", e);
		}

		if (StringUtils.equals(tStoreCashEcRes.getResultCd(), PurchaseConstants.TSTORE_CASH_RESULT_CD_SUCCESS) == false) {
			throw new StorePlatformException("SAC_PUR_7207", tStoreCashEcRes.getResultCd(),
					tStoreCashEcRes.getResultMsg());
		}

		List<TStoreCashBalanceDetailEcRes> tstoreCashList = tStoreCashEcRes.getCashList();
		double cashAmt = 0.0;
		for (TStoreCashBalanceDetailEcRes cash : tstoreCashList) {
			cashAmt += Double.parseDouble(cash.getAmt());
		}

		return cashAmt;
	}

	/**
	 * 
	 * <pre>
	 * 게임캐쉬 잔액 조회.
	 * </pre>
	 * 
	 * @param userKey
	 *            내부 회원 NO
	 * 
	 * @return 게임캐쉬 잔액
	 */
	@Override
	public double searchGameCashAmt(String userKey) {
		// TAKTEST:: 상용 -> BMS 연동 불가로 Skip
		// if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_REAL)) {
		// return 0.0;
		// }

		TStoreCashBalanceEcReq tStoreCashEcReq = new TStoreCashBalanceEcReq();
		tStoreCashEcReq.setUserKey(userKey);
		tStoreCashEcReq.setProductGroup(PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_TSTORE_GAMECASH); // 상품군 : T store
																									  // Gamecash

		TStoreCashBalanceEcRes tStoreCashEcRes = null;
		try {
			this.logger.info("PRCHS,ORDER,SAC,TSTORE,GAMECASH,SEARCH,REQ,{}",
					ReflectionToStringBuilder.toString(tStoreCashEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
			tStoreCashEcRes = this.tStoreCashSCI.getBalance(tStoreCashEcReq);
			this.logger.info("PRCHS,ORDER,SAC,TSTORE,GAMECASH,SEARCH,RES,{}",
					ReflectionToStringBuilder.toString(tStoreCashEcRes, ToStringStyle.SHORT_PREFIX_STYLE));
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7211", e);
		}

		if (StringUtils.equals(tStoreCashEcRes.getResultCd(), PurchaseConstants.TSTORE_CASH_RESULT_CD_SUCCESS) == false) {
			throw new StorePlatformException("SAC_PUR_7207", tStoreCashEcRes.getResultCd(),
					tStoreCashEcRes.getResultMsg());
		}

		List<TStoreCashBalanceDetailEcRes> tstoreCashList = tStoreCashEcRes.getCashList();
		double cashAmt = 0.0;
		for (TStoreCashBalanceDetailEcRes cash : tstoreCashList) {
			cashAmt += Double.parseDouble(cash.getAmt());
		}

		return cashAmt;
	}

	/**
	 * 
	 * <pre>
	 * 게임캐쉬 충전 예약.
	 * </pre>
	 * 
	 * @param userKey
	 *            내부 회원 번호
	 * 
	 * @param cashAmt
	 *            충전할 Cash 금액
	 * 
	 * @param useStartDt
	 *            Cash 이용시작일시
	 * 
	 * @param bonusPointAmt
	 *            충전할 Point 금액
	 * 
	 * @param bonusPointUsePeriodUnitCd
	 *            보너스 Point 이용기간단위
	 * 
	 * @param bonusPointUsePeriod
	 *            보너스 Point 이용기간값
	 * 
	 * @return 충전 예약 결과 정보 목록
	 */
	@Override
	public List<TStoreCashChargeReserveDetailEcRes> reserveGameCashCharge(String userKey, double cashAmt,
			String useStartDt, double bonusPointAmt, String bonusPointUsePeriodUnitCd, String bonusPointUsePeriod) {
		List<TStoreCashChargeReserveDetailEcReq> cashReserveList = new ArrayList<TStoreCashChargeReserveDetailEcReq>();

		// 게임 Cash : Cash 유효기간은 5년
		TStoreCashChargeReserveDetailEcReq tStoreCashChargeReserveDetailEcReq = new TStoreCashChargeReserveDetailEcReq();
		tStoreCashChargeReserveDetailEcReq.setProductGroup(PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_TSTORE_GAMECASH);
		tStoreCashChargeReserveDetailEcReq.setAmt(String.valueOf((int) cashAmt));
		tStoreCashChargeReserveDetailEcReq.setDate(this.purchaseOrderAssistService.calculateUseDate(useStartDt,
				PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_YEAR, "5")); // 사용 유효기간(만료일시 - yyyyMMddHHmmss)
		tStoreCashChargeReserveDetailEcReq.setCashCls(PurchaseConstants.TSTORE_CASH_CLASS_CASH);
		cashReserveList.add(tStoreCashChargeReserveDetailEcReq);

		// 보너스 Point
		if (bonusPointAmt > 0.0) {
			tStoreCashChargeReserveDetailEcReq = new TStoreCashChargeReserveDetailEcReq();
			tStoreCashChargeReserveDetailEcReq
					.setProductGroup(PurchaseConstants.TSTORE_CASH_PRODUCT_GROUP_TSTORE_GAMECASH);
			tStoreCashChargeReserveDetailEcReq.setAmt(String.valueOf((int) bonusPointAmt));
			tStoreCashChargeReserveDetailEcReq.setDate(this.purchaseOrderAssistService.calculateUseDate(useStartDt,
					bonusPointUsePeriodUnitCd, bonusPointUsePeriod));
			tStoreCashChargeReserveDetailEcReq.setCashCls(PurchaseConstants.TSTORE_CASH_CLASS_POINT);
			cashReserveList.add(tStoreCashChargeReserveDetailEcReq);
		}

		TStoreCashChargeReserveEcReq tStoreCashChargeReserveEcReq = new TStoreCashChargeReserveEcReq();
		tStoreCashChargeReserveEcReq.setUserKey(userKey);
		tStoreCashChargeReserveEcReq.setCashList(cashReserveList);

		this.logger.info("PRCHS,ORDER,SAC,TSTORE,GAMECASH,RESERVE,REQ,{}",
				ReflectionToStringBuilder.toString(tStoreCashChargeReserveEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
		TStoreCashChargeReserveEcRes tStoreCashChargeReserveEcRes = this.tStoreCashSCI
				.reserveCharge(tStoreCashChargeReserveEcReq);
		this.logger.info("PRCHS,ORDER,SAC,TSTORE,GAMECASH,RESERVE,RES,{}",
				ReflectionToStringBuilder.toString(tStoreCashChargeReserveEcRes, ToStringStyle.SHORT_PREFIX_STYLE));

		if (StringUtils.equals(tStoreCashChargeReserveEcRes.getResultCd(),
				PurchaseConstants.TSTORE_CASH_RESULT_CD_SUCCESS) == false) {
			throw new StorePlatformException("SAC_PUR_7213", tStoreCashChargeReserveEcRes.getResultCd());
		}

		// 충전 확정 용도
		return tStoreCashChargeReserveEcRes.getCashList();
	}

	/**
	 * 
	 * <pre>
	 * 게임캐쉬 충전 확정.
	 * </pre>
	 * 
	 * @param userKey
	 *            내부 회원 번호
	 * 
	 * @param prchsId
	 *            구매ID
	 * 
	 * @param cashReserveResList
	 *            충전예약 결과 정보 목록
	 */
	@Override
	public void confirmGameCashCharge(String userKey, String prchsId,
			List<TStoreCashChargeReserveDetailEcRes> cashReserveResList) {
		List<TStoreCashChargeConfirmDetailEcReq> cashConfirmList = new ArrayList<TStoreCashChargeConfirmDetailEcReq>();

		for (TStoreCashChargeReserveDetailEcRes cashReserveRes : cashReserveResList) {
			TStoreCashChargeConfirmDetailEcReq tStoreCashChargeConfirmDetailEcReq = new TStoreCashChargeConfirmDetailEcReq();
			tStoreCashChargeConfirmDetailEcReq.setOrderNo(prchsId);
			tStoreCashChargeConfirmDetailEcReq.setIdentifier(cashReserveRes.getIdentifier());
			tStoreCashChargeConfirmDetailEcReq.setCashCls(cashReserveRes.getCashCls());
			tStoreCashChargeConfirmDetailEcReq.setProductGroup(cashReserveRes.getProductGroup());
			cashConfirmList.add(tStoreCashChargeConfirmDetailEcReq);
		}

		TStoreCashChargeConfirmEcReq tStoreCashChargeConfirmEcReq = new TStoreCashChargeConfirmEcReq();
		tStoreCashChargeConfirmEcReq.setUserKey(userKey);
		tStoreCashChargeConfirmEcReq.setCashList(cashConfirmList);

		this.logger.info("PRCHS,ORDER,SAC,TSTORE,GAMECASH,CONFIRM,REQ,{}",
				ReflectionToStringBuilder.toString(tStoreCashChargeConfirmEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
		TStoreCashChargeConfirmEcRes tStoreCashChargeConfirmEcRes = this.tStoreCashSCI
				.confirmCharge(tStoreCashChargeConfirmEcReq);
		this.logger.info("PRCHS,ORDER,SAC,TSTORE,GAMECASH,CONFIRM,RES,{}",
				ReflectionToStringBuilder.toString(tStoreCashChargeConfirmEcRes, ToStringStyle.SHORT_PREFIX_STYLE));

		if (StringUtils.equals(tStoreCashChargeConfirmEcRes.getResultCd(),
				PurchaseConstants.TSTORE_CASH_RESULT_CD_SUCCESS) == false) {
			throw new StorePlatformException("SAC_PUR_7214", tStoreCashChargeConfirmEcRes.getResultCd());
		}
	}

	/**
	 * 
	 * <pre>
	 * 게임캐쉬 충전 취소.
	 * </pre>
	 * 
	 * @param userKey
	 *            내부 회원 번호
	 * 
	 * @param prchsId
	 *            구매ID
	 * 
	 * @param cashReserveResList
	 *            충전예약 결과 정보 목록
	 */
	@Override
	public void cancelGameCashCharge(String userKey, String prchsId,
			List<TStoreCashChargeReserveDetailEcRes> cashReserveResList) {

		List<TStoreCashChargeCancelDetailEcReq> cashCancelList = new ArrayList<TStoreCashChargeCancelDetailEcReq>();
		for (TStoreCashChargeReserveDetailEcRes cashReserveRes : cashReserveResList) {
			TStoreCashChargeCancelDetailEcReq tStoreCashChargeCancelDetailEcReq = new TStoreCashChargeCancelDetailEcReq();
			tStoreCashChargeCancelDetailEcReq.setOrderNo(prchsId);
			tStoreCashChargeCancelDetailEcReq.setIdentifier(cashReserveRes.getIdentifier());
			tStoreCashChargeCancelDetailEcReq.setCashCls(cashReserveRes.getCashCls());
			tStoreCashChargeCancelDetailEcReq.setProductGroup(cashReserveRes.getProductGroup());
			cashCancelList.add(tStoreCashChargeCancelDetailEcReq);
		}

		TStoreCashChargeCancelEcReq tStoreCashChargeCancelEcReq = new TStoreCashChargeCancelEcReq();
		tStoreCashChargeCancelEcReq.setUserKey(userKey);
		tStoreCashChargeCancelEcReq.setCashList(cashCancelList);

		this.logger.info("PRCHS,ORDER,SAC,TSTORE,GAMECASH,CANCEL,REQ,{}",
				ReflectionToStringBuilder.toString(tStoreCashChargeCancelEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
		TStoreCashChargeCancelEcRes tStoreCashChargeCancelEcRes = this.tStoreCashSCI
				.cancelCharge(tStoreCashChargeCancelEcReq);
		this.logger.info("PRCHS,ORDER,SAC,TSTORE,GAMECASH,CANCEL,RES,{}",
				ReflectionToStringBuilder.toString(tStoreCashChargeCancelEcRes, ToStringStyle.SHORT_PREFIX_STYLE));

		if (StringUtils.equals(tStoreCashChargeCancelEcRes.getResultCd(),
				PurchaseConstants.TSTORE_CASH_RESULT_CD_SUCCESS) == false) {
			// TAKTODO:: 충전 취소 실패 시, 어떻게?
			;
		}
	}

	/**
	 * 
	 * <pre>
	 * Tstore 측으로 구매완료 알림: 이메일 발송, SMS / MMS 등등 처리.
	 * </pre>
	 * 
	 * @param prchsId
	 *            구매ID
	 * @param prchsDt
	 *            구매일시
	 * @param userKey
	 *            내부 회원 번호
	 * @param deviceKey
	 *            내부 디바이스 ID
	 * @param notiType
	 *            알림 타입
	 */
	@Override
	public void postTstoreNoti(String prchsId, String prchsDt, String userKey, String deviceKey, String notiType) {
		TStoreNotiEcReq tStoreNotiEcReq = new TStoreNotiEcReq();
		tStoreNotiEcReq.setPrchsId(prchsId);
		tStoreNotiEcReq.setPrchsDt(prchsDt);
		tStoreNotiEcReq.setUserKey(userKey);
		tStoreNotiEcReq.setDeviceKey(deviceKey);
		tStoreNotiEcReq.setPublishType(notiType);
		tStoreNotiEcReq.setType(PurchaseConstants.TSTORE_NOTI_TYPE_NORMALPAY);

		try {
			this.logger.info("PRCHS,ORDER,SAC,POST,TSTORE,NOTI,REQ,{}",
					ReflectionToStringBuilder.toString(tStoreNotiEcReq, ToStringStyle.SHORT_PREFIX_STYLE));
			TStoreNotiEcRes tStoreNotiEcRes = this.tStoreNotiSCI.postTStoreNoti(tStoreNotiEcReq);
			this.logger.info("PRCHS,ORDER,SAC,POST,TSTORE,NOTI,RES,{}",
					ReflectionToStringBuilder.toString(tStoreNotiEcRes, ToStringStyle.SHORT_PREFIX_STYLE));
		} catch (Exception e) {
			// 예외 throw 차단
			this.logger.info("PRCHS,ORDER,SAC,POST,TSTORE,NOTI,ERROR,{},{}", prchsId, e.getMessage());
		}
	}

	/**
	 * 
	 * <pre>
	 * Tstore 측으로 구매완료 알림: 이메일 발송, SMS / MMS 등등 처리.
	 * </pre>
	 * 
	 * @param prchsId
	 *            구매ID
	 * @param prchsDt
	 *            구매일시
	 * @param userKey
	 *            내부 회원 번호
	 * @param deviceKey
	 *            내부 디바이스 ID
	 * @param notiType
	 *            알림 타입
	 * @param bGift
	 *            선물여부
	 */
	@Override
	public void postTstoreNotiV2(String prchsId, String prchsDt, String userKey, String deviceKey, String notiType,
			boolean bGift) {
		TStoreNotiV2EcReq tStoreNotiV2EcReq = new TStoreNotiV2EcReq();
		tStoreNotiV2EcReq.setPrchsId(prchsId);
		tStoreNotiV2EcReq.setPrchsDt(prchsDt);
		tStoreNotiV2EcReq.setUserKey(userKey);
		tStoreNotiV2EcReq.setDeviceKey(deviceKey);
		tStoreNotiV2EcReq.setPublishType(notiType);
		tStoreNotiV2EcReq.setType(PurchaseConstants.TSTORE_NOTI_TYPE_NORMALPAY);
		tStoreNotiV2EcReq.setGiftYn(bGift ? "Y" : "N");

		try {
			this.logger.info("PRCHS,ORDER,SAC,POST,TSTORE,NOTI,REQ,{}",
					ReflectionToStringBuilder.toString(tStoreNotiV2EcReq, ToStringStyle.SHORT_PREFIX_STYLE));
			TStoreNotiV2EcRes tStoreNotiV2Res = this.tStoreNotiSCI.postTStoreNotiV2(tStoreNotiV2EcReq);
			this.logger.info("PRCHS,ORDER,SAC,POST,TSTORE,NOTI,RES,{}",
					ReflectionToStringBuilder.toString(tStoreNotiV2Res, ToStringStyle.SHORT_PREFIX_STYLE));
		} catch (Exception e) {
			// 예외 throw 차단
			this.logger.info("PRCHS,ORDER,SAC,POST,TSTORE,NOTI,ERROR,{},{}", prchsId, e.getMessage());
		}
	}
}
