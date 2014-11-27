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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.DateUtils;
import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;
import com.skplanet.storeplatform.purchase.client.order.vo.AutoPrchsMore;
import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.purchase.client.order.vo.PurchaseUserInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PaymentInfo;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.PaymethodUtil;
import com.skplanet.storeplatform.sac.purchase.order.vo.MileageSubInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;

/**
 * 
 * 구매정보 생성 서비스
 * 
 * Updated on : 2014. 5. 29. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderMakeDataServiceImpl implements PurchaseOrderMakeDataService {
	@Value("#{systemProperties['spring.profiles.active']}")
	private String envServerLevel;

	/**
	 * <pre>
	 * 구매생성을 위한 데이터 목록 생성.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 VO
	 * 
	 * @return 구매생성을 위한 데이터 목록
	 */
	@Override
	public List<PrchsDtlMore> makePrchsDtlMoreList(PurchaseOrderInfo purchaseOrderInfo) {

		// 구매생성 요청 데이터 세팅
		List<PrchsDtlMore> prchsDtlMoreList = new ArrayList<PrchsDtlMore>();
		PrchsDtlMore prchsDtlMore = null;

		// 구매이력 추가 생성 건
		List<PurchaseProduct> workProductList = new ArrayList<PurchaseProduct>(
				purchaseOrderInfo.getPurchaseProductList());
		for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
			if (product.getFullIapProductInfo() != null) { // IAP 정식판 전환 상품
				workProductList.add(product.getFullIapProductInfo());
			}
		}

		int prchsDtlCnt = 1, i = 0;

		// 수신자 만큼 - 상품 갯수 만큼 반복 : Biz쿠폰은 예외 (효율을 위해 ReceiverList만 저장)

		List<PurchaseUserDevice> useUserList = new ArrayList<PurchaseUserDevice>();
		if (purchaseOrderInfo.isGift()) {
			if (purchaseOrderInfo.isBizShopping()) {
				useUserList.add(purchaseOrderInfo.getPurchaseUser());
			} else {
				useUserList = purchaseOrderInfo.getReceiveUserList();
			}

		} else {
			useUserList.add(purchaseOrderInfo.getPurchaseUser());
		}

		for (PurchaseUserDevice useUser : useUserList) {

			for (PurchaseProduct product : workProductList) {

				if (StringUtils.isNotBlank(product.getResultCd())) { // CLINK 예외 처리용
					continue;
				}

				for (i = 0; i < product.getProdQty(); i++) {
					prchsDtlMore = new PrchsDtlMore();

					prchsDtlMore.setPrchsDtlId(prchsDtlCnt++);

					prchsDtlMore.setTenantId(purchaseOrderInfo.getTenantId());
					prchsDtlMore.setSystemId(purchaseOrderInfo.getSystemId());
					prchsDtlMore.setPrchsId(purchaseOrderInfo.getPrchsId());
					prchsDtlMore.setPrchsDt(purchaseOrderInfo.getPrchsDt());

					// 발신자/수신자 세팅
					if (purchaseOrderInfo.isGift()) {
						prchsDtlMore.setSendInsdUsermbrNo(purchaseOrderInfo.getUserKey());
						prchsDtlMore.setSendInsdDeviceId(purchaseOrderInfo.getDeviceKey());
						prchsDtlMore.setUseTenantId(purchaseOrderInfo.getTenantId());

						// Biz 쿠폰 예외 처리
						if (purchaseOrderInfo.isBizShopping()
								&& CollectionUtils.isNotEmpty(purchaseOrderInfo.getReceiveUserList())) {
							List<PurchaseUserInfo> receiverList = new ArrayList<PurchaseUserInfo>();
							PurchaseUserInfo receiver = null;
							for (PurchaseUserDevice receiveUser : purchaseOrderInfo.getReceiveUserList()) {
								receiver = new PurchaseUserInfo();
								receiver.setUserKey(receiveUser.getUserKey());
								receiver.setDeviceKey(receiveUser.getDeviceKey());
								receiverList.add(receiver);
							}
							prchsDtlMore.setReceiverList(receiverList);

						} else {
							prchsDtlMore.setUseInsdUsermbrNo(useUser.getUserKey());
							prchsDtlMore.setUseInsdDeviceId(useUser.getDeviceKey());
						}
					} else {
						prchsDtlMore.setUseTenantId(purchaseOrderInfo.getTenantId());
						prchsDtlMore.setUseInsdUsermbrNo(purchaseOrderInfo.getUserKey());
						prchsDtlMore.setUseInsdDeviceId(purchaseOrderInfo.getDeviceKey());
					}
					prchsDtlMore.setTotAmt(purchaseOrderInfo.getRealTotAmt());
					if (product.isFullProd()) {
						prchsDtlMore.setTenantProdGrpCd(PurchaseConstants.TENANT_PRODUCT_GROUP_APP
								+ purchaseOrderInfo.getTenantProdGrpCd().substring(8, 12)
								+ PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_UNIT);
						prchsDtlMore.setPrchsReqPathCd(PurchaseConstants.PRCHS_REQ_PATH_IAP_COMMERCIAL_CONVERTED);
					} else {
						prchsDtlMore.setTenantProdGrpCd(purchaseOrderInfo.getTenantProdGrpCd());
						prchsDtlMore.setPrchsReqPathCd(purchaseOrderInfo.getPrchsReqPathCd());
					}
					prchsDtlMore.setClientIp(purchaseOrderInfo.getClientIp());
					prchsDtlMore.setUseHidingYn(PurchaseConstants.USE_N);
					prchsDtlMore.setSendHidingYn(PurchaseConstants.USE_N);
					prchsDtlMore.setRegId(purchaseOrderInfo.getSystemId());
					prchsDtlMore.setUpdId(purchaseOrderInfo.getSystemId());
					prchsDtlMore.setPrchsCaseCd(purchaseOrderInfo.getPrchsCaseCd());
					prchsDtlMore.setCurrencyCd(purchaseOrderInfo.getCurrencyCd()); // PRCHS
					prchsDtlMore.setNetworkTypeCd(purchaseOrderInfo.getNetworkTypeCd()); // PRCHS

					if (purchaseOrderInfo.isFlat()) {
						prchsDtlMore.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_AUTH); // 권한 상품
					} else {
						prchsDtlMore.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_UNIT); // 단위 상품
					}
					prchsDtlMore.setProdId(product.getProdId());
					prchsDtlMore.setProdAmt(product.getProdAmt());
					prchsDtlMore.setProdQty(product.getProdQty());
					prchsDtlMore.setResvCol01(product.getResvCol01());
					prchsDtlMore.setResvCol02(product.getResvCol02());
					prchsDtlMore.setResvCol03(product.getResvCol03());
					prchsDtlMore.setResvCol04(product.getResvCol04());
					prchsDtlMore.setResvCol05(product.getResvCol05());
					prchsDtlMore.setUsePeriodUnitCd(product.getUsePeriodUnitCd());
					prchsDtlMore.setUsePeriod(product.getUsePeriod() == null ? "0" : product.getUsePeriod());
					// 비과금 구매요청 시, 이용종료일시 세팅
					if (purchaseOrderInfo.isFreeChargeReq() && StringUtils.isNotBlank(product.getUseExprDt())) {
						prchsDtlMore
								.setUseExprDt(product.getUseExprDt().length() == 14 ? product.getUseExprDt() : product
										.getUseExprDt() + "235959");
						prchsDtlMore.setDwldExprDt(prchsDtlMore.getUseExprDt());
					}
					// 정액권으로 에피소드 이용시, 다운로드 종료 일시
					if (StringUtils.isNotBlank(product.getUseFixrateProdId())
							&& StringUtils.isNotBlank(product.getDwldExprDt())) {
						prchsDtlMore.setDwldExprDt(product.getDwldExprDt());
					}

					prchsDtlMore.setUseFixrateProdId(product.getUseFixrateProdId());
					prchsDtlMore.setUseFixrateProdClsfCd(product.getUseFixrateProdClsfCd());
					prchsDtlMore.setDrmYn(product.getDrmYn());
					prchsDtlMore.setAlarmYn(PurchaseConstants.USE_Y);
					/* IAP */
					prchsDtlMore.setTid(product.getTid()); // 부분유료화 개발사 구매Key
					prchsDtlMore.setTxId(product.getTxId()); // 부분유료화 전자영수증 번호
					prchsDtlMore.setParentProdId(product.getParentProdId()); // 부모_상품_ID
					prchsDtlMore.setContentsType(product.getContentsType()); // 컨텐츠_타입
					prchsDtlMore.setPartChrgVer(product.getPartChrgVer()); // 부분_유료_버전
					prchsDtlMore.setPartChrgProdNm(product.getPartChrgProdNm()); // 부분_유료_상품_명
					/* Ring & Bell */
					prchsDtlMore.setRnBillCd(product.getRnBillCd()); // RN_과금_코드
					prchsDtlMore.setInfoUseFee(product.getInfoUseFee()); // 정보_이용_요금 (ISU_AMT_ADD)
					prchsDtlMore.setCid(product.getCid()); // 컨텐츠ID
					prchsDtlMore.setContentsClsf(product.getContentsClsf()); // 컨텐츠_구분
					prchsDtlMore.setPrchsType(product.getPrchsType()); // 구매_타입
					prchsDtlMore.setTimbreClsf(product.getTimbreClsf()); // 음질_구분
					prchsDtlMore.setTimbreSctn(product.getTimbreSctn()); // 음질_구간
					prchsDtlMore.setMenuId(product.getMenuId()); // 메뉴_ID
					prchsDtlMore.setGenreClsfCd(product.getGenreClsfCd()); // 장르_구분_코드

					prchsDtlMore.setMediId(purchaseOrderInfo.getMediaId());

					prchsDtlMore.setGiftMsg(useUser.getGiftMsg());

					prchsDtlMoreList.add(prchsDtlMore);
				}
			}
		}

		return prchsDtlMoreList;
	}

	/**
	 * 
	 * <pre>
	 * 결제내역 생성 목록 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMore
	 *            구매정보
	 * 
	 * @param paymentInfoList
	 *            결제이력 생성 정보
	 * 
	 * @param statusCd
	 *            구매상태코드
	 * 
	 * @return 결제내역 생성 목록
	 */
	@Override
	public List<Payment> makePaymentList(PrchsDtlMore prchsDtlMore, List<PaymentInfo> paymentInfoList, String statusCd) {

		String tenantId = prchsDtlMore.getTenantId();
		String systemId = prchsDtlMore.getSystemId();
		String prchsId = prchsDtlMore.getPrchsId();
		String prchsDt = prchsDtlMore.getPrchsDt();
		Double totAmt = prchsDtlMore.getTotAmt();
		String payUserKey = null;
		String payDeviceKey = null;
		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			payUserKey = prchsDtlMore.getSendInsdUsermbrNo();
			payDeviceKey = prchsDtlMore.getSendInsdDeviceId();
		} else {
			payUserKey = prchsDtlMore.getUseInsdUsermbrNo();
			payDeviceKey = prchsDtlMore.getUseInsdDeviceId();
		}

		List<Payment> paymentList = new ArrayList<Payment>();
		Payment payment = null;
		int dtlIdCnt = 1;

		for (PaymentInfo paymentInfo : paymentInfoList) {
			payment = new Payment();
			payment.setTenantId(tenantId);
			payment.setPrchsId(prchsId);
			payment.setPaymentDtlId(dtlIdCnt++);
			payment.setInsdUsermbrNo(payUserKey);
			payment.setInsdDeviceId(payDeviceKey);
			payment.setPrchsDt(prchsDt);
			payment.setPaymentMtdCd(PaymethodUtil.convert2StoreCode(paymentInfo.getPaymentMtdCd()));
			payment.setTotAmt(totAmt);
			payment.setApprNo(paymentInfo.getApprNo());
			payment.setBillKey(paymentInfo.getBillKey());
			payment.setCpnId(paymentInfo.getCpnId());
			payment.setCpnMakeHost(paymentInfo.getCpnMakeHost());
			if (StringUtils.equals(payment.getPaymentMtdCd(), PurchaseConstants.PAYMENT_METHOD_COUPON)) {
				payment.setCpnType(paymentInfo.getCpnType());
			} else if (StringUtils.equals(payment.getPaymentMtdCd(), PurchaseConstants.PAYMENT_METHOD_OCB)) {
				payment.setCpnType(paymentInfo.getOcbType());
			}
			payment.setMoid(paymentInfo.getMoid());

			if (StringUtils.equals(payment.getPaymentMtdCd(), PurchaseConstants.PAYMENT_METHOD_SKT_CARRIER)
					&& StringUtils.equals(paymentInfo.getSktTestDeviceYn(), PurchaseConstants.USE_Y)) {
				payment.setPaymentMtdCd(PurchaseConstants.PAYMENT_METHOD_SKT_TEST_DEVICE);
			}
			payment.setPaymentAmt(paymentInfo.getPaymentAmt());
			payment.setPaymentDt(paymentInfo.getPaymentDt());
			payment.setStatusCd(statusCd);

			payment.setTid(paymentInfo.getTid());

			payment.setRegId(systemId);
			payment.setUpdId(systemId);

			paymentList.add(payment);
		}

		return paymentList;
	}

	/**
	 * 
	 * <pre>
	 * 상품 건수 저장을 위한 목록 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMoreList
	 *            구매 정보 목록
	 * 
	 * @param statusCd
	 *            구매상태코드
	 * 
	 * @return 상품 건수 저장을 위한 목록
	 */
	@Override
	public List<PrchsProdCnt> makePrchsProdCntList(List<PrchsDtlMore> prchsDtlMoreList, String statusCd) {
		List<PrchsProdCnt> prchsProdCntList = new ArrayList<PrchsProdCnt>();
		PrchsProdCnt prchsProdCnt = null;

		List<String> procKeyList = new ArrayList<String>();
		String tenantProdGrpCd = null;

		String procKey = null;
		for (PrchsDtlMore prchsDtlMore : prchsDtlMoreList) {
			prchsProdCnt = new PrchsProdCnt();

			// 1:N 선물 지원 - 중복 구매 가능한 쇼핑상품 / 부분유료화 소멸성 상품 처리
			tenantProdGrpCd = prchsDtlMore.getTenantProdGrpCd();
			if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)
					|| (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_IAP) && StringUtils
							.equals(prchsDtlMore.getContentsType(), "PK0002"))) {

				if (prchsDtlMore.getProdQty() > 1) { // 1개 상품을 복수구매 경우
					procKey = prchsDtlMore.getProdId() + prchsDtlMore.getUseInsdDeviceId();

				} else { // 1개 상품을 1개씩 복수선물 경우 포함
					procKey = prchsDtlMore.getProdId() + prchsDtlMore.getUseInsdDeviceId()
							+ prchsDtlMore.getPrchsDtlId();
				}

				prchsProdCnt.setProdGrpCd(prchsDtlMore.getTenantProdGrpCd() + prchsDtlMore.getPrchsId()
						+ prchsDtlMore.getPrchsDtlId());

			} else {
				// 1:N 선물 지원 & 중복 구매 불가한 상품에 대한 중복 체크
				procKey = prchsDtlMore.getProdId() + prchsDtlMore.getUseInsdDeviceId();

				prchsProdCnt.setProdGrpCd(prchsDtlMore.getTenantProdGrpCd());
			}

			if (procKeyList.contains(procKey)) {
				continue;
			}
			procKeyList.add(procKey);

			prchsProdCnt.setTenantId(prchsDtlMore.getTenantId());
			prchsProdCnt.setUseUserKey(prchsDtlMore.getUseInsdUsermbrNo());
			prchsProdCnt.setUseDeviceKey(prchsDtlMore.getUseInsdDeviceId());
			prchsProdCnt.setRegId(prchsDtlMore.getSystemId());
			prchsProdCnt.setUpdId(prchsDtlMore.getSystemId());

			prchsProdCnt.setPrchsId(prchsDtlMore.getPrchsId());
			prchsProdCnt.setPrchsDt(prchsDtlMore.getPrchsDt());
			prchsProdCnt.setPrchsClas(prchsDtlMore.getPrchsReqPathCd());
			prchsProdCnt.setStatusCd(statusCd);

			prchsProdCnt.setProdId(prchsDtlMore.getProdId());
			prchsProdCnt.setProdAmt(prchsDtlMore.getProdAmt());
			prchsProdCnt.setProdQty(prchsDtlMore.getProdQty());
			prchsProdCnt
					.setSprcProdYn(StringUtils.defaultString(prchsDtlMore.getSprcProdYn(), PurchaseConstants.USE_N));
			prchsProdCnt.setUseFixrateProdId(prchsDtlMore.getUseFixrateProdId());

			prchsProdCnt.setCntProcStatus(PurchaseConstants.USE_N);

			prchsProdCntList.add(prchsProdCnt);
		}

		// TAKTEST:: QA 테스트
		// if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_QA)
		// || StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_LOCAL)) {
		if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_QA)) {
			return null;
		}

		return prchsProdCntList;
	}

	/**
	 * 
	 * <pre>
	 * 자동구매 생성을 위한 목록 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMore
	 *            구매생성 정보
	 * 
	 * @param deviceModelCd
	 *            구매 단말 모델 코드
	 * 
	 * @param autoLastPeriod
	 *            자동결제 지속일 수
	 * 
	 * @return 자동구매 생성을 위한 목록
	 */
	@Override
	public List<AutoPrchsMore> makeAutoPrchsMoreList(PrchsDtlMore prchsDtlMore, String deviceModelCd,
			String autoLastPeriod) {
		List<AutoPrchsMore> autoPrchsMoreList = new ArrayList<AutoPrchsMore>();

		AutoPrchsMore autoPrchsMore = new AutoPrchsMore();
		autoPrchsMore.setTenantId(prchsDtlMore.getTenantId());
		autoPrchsMore.setFstPrchsId(prchsDtlMore.getPrchsId());
		autoPrchsMore.setFstPrchsDtlId(1);
		autoPrchsMore.setInsdUsermbrNo(prchsDtlMore.getInsdUsermbrNo());
		autoPrchsMore.setInsdDeviceId(prchsDtlMore.getInsdDeviceId());
		autoPrchsMore.setProdId(prchsDtlMore.getProdId());
		autoPrchsMore.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
		autoPrchsMore.setPaymentStartDt(prchsDtlMore.getPrchsDt());
		if (StringUtils.isBlank(autoLastPeriod) || Integer.parseInt(autoLastPeriod) <= 0) {
			autoPrchsMore.setPaymentEndDt("99991231235959");
		} else {
			autoPrchsMore.setAutoPrchsLastPeriod(Integer.parseInt(autoLastPeriod));
		}
		try {
			autoPrchsMore.setAfterPaymentDt(DateFormatUtils.format(DateUtils.truncate(
					DateUtils.parseDate(prchsDtlMore.getUseExprDt(), "yyyyMMddHHmmss"), Calendar.DATE),
					"yyyyMMddHHmmss")); // 00시 00분 00초
		} catch (ParseException e) {
			throw new StorePlatformException("SAC_PUR_7217", prchsDtlMore.getUseExprDt());
		}
		autoPrchsMore.setReqPathCd(prchsDtlMore.getPrchsReqPathCd());
		autoPrchsMore.setClientIp(prchsDtlMore.getClientIp());
		autoPrchsMore.setPrchsTme(0);
		autoPrchsMore.setLastPrchsId(prchsDtlMore.getPrchsId());
		autoPrchsMore.setLastPrchsDtlId(1);
		autoPrchsMore.setRegId(prchsDtlMore.getSystemId());
		autoPrchsMore.setUpdId(prchsDtlMore.getSystemId());
		autoPrchsMore.setAutoPaymentStatusCd(PurchaseConstants.AUTO_PRCHS_STATUS_AUTO);
		autoPrchsMore.setResvCol01(deviceModelCd); // 구매한 단말 모델 코드

		autoPrchsMoreList.add(autoPrchsMore);

		return autoPrchsMoreList;
	}

	/**
	 * 
	 * <pre>
	 * 멤버쉽 적립을 위한 목록 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMore
	 *            구매생성 정보
	 * 
	 * @param mileageSubInfo
	 *            멤버쉽 정보
	 * 
	 * @return 멤버쉽 적립을 위한 목록
	 */
	@Override
	public List<MembershipReserve> makeMembershipReserveList(List<PrchsDtlMore> prchsDtlMoreList,
			MileageSubInfo mileageSubInfo) {
		List<MembershipReserve> membershipReserveList = new ArrayList<MembershipReserve>();

		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);

		MembershipReserve membershipReserve = new MembershipReserve();

		membershipReserve.setTenantId(prchsDtlMore.getTenantId());
		membershipReserve.setTypeCd(mileageSubInfo.getTypeCd());
		membershipReserve.setPrchsId(prchsDtlMore.getPrchsId());
		membershipReserve.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			membershipReserve.setInsdUsermbrNo(prchsDtlMore.getSendInsdUsermbrNo());
			membershipReserve.setInsdDeviceId(prchsDtlMore.getSendInsdDeviceId());
		} else {
			membershipReserve.setInsdUsermbrNo(prchsDtlMore.getUseInsdUsermbrNo());
			membershipReserve.setInsdDeviceId(prchsDtlMore.getUseInsdDeviceId());
		}
		// membershipReserve.setTargetDt(prchsDtlMore.getPrchsDt());
		membershipReserve.setTargetDt("20" + prchsDtlMore.getPrchsId().substring(0, 12));
		membershipReserve.setPrchsDt(prchsDtlMore.getPrchsDt());
		membershipReserve.setCurrencyCd(prchsDtlMore.getCurrencyCd());
		membershipReserve.setTotAmt(prchsDtlMore.getTotAmt());
		if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)) {
			membershipReserve.setPrchsProdCnt(1);
		} else {
			membershipReserve.setPrchsProdCnt(prchsDtlMoreList.size());
		}
		membershipReserve.setProdId(prchsDtlMore.getProdId());
		membershipReserve.setProdAmt(prchsDtlMore.getProdAmt());
		membershipReserve.setProdQty(prchsDtlMore.getProdQty());
		membershipReserve.setProdNm(mileageSubInfo.getProdNm()); // IAP 부분상품명
		membershipReserve.setUserGrdCd(mileageSubInfo.getUserGrdCd()); // 회원등급코드
		membershipReserve.setProdSaveRate(mileageSubInfo.getProdSaveRate()); // 상품적립율
		membershipReserve.setTargetPaymentAmt(mileageSubInfo.getTargetPaymentAmt()); // 적립대상결제금액
		membershipReserve.setSaveExpectAmt(mileageSubInfo.getSaveExpectAmt()); // 적립예정금액
		membershipReserve.setSaveResultAmt(mileageSubInfo.getSaveResultAmt()); // 적립결과금액
		membershipReserve.setPrchsReqPathCd(mileageSubInfo.getPrchsReqPathCd()); // 적립금요청경로
		membershipReserve.setSaveTypeCd(mileageSubInfo.getSaveTypeCd()); // 처리타입코드
		membershipReserve.setProcStatusCd(mileageSubInfo.getProcStatusCd()); // 처리상태코드
		membershipReserve.setRegId(prchsDtlMore.getSystemId());
		membershipReserve.setUpdId(prchsDtlMore.getSystemId());

		membershipReserveList.add(membershipReserve);

		return membershipReserveList;
	}

	/**
	 * <pre>
	 * 이북/코믹 전권 소장/대여 에피소드 상품 - 구매이력 생성 요청 데이터 목록 생성.
	 * </pre>
	 * 
	 * @param ebookflatInfo
	 *            이북/코믹 전권 소장/대여 구매 정보 VO
	 * 
	 * @param episodeList
	 *            이북/코믹 전권 소장/대여 에피소드 상품 정보 VO
	 * 
	 * @param cmpxProdClsfCd
	 *            정액권 타입 : 전권소장 / 전권대여
	 * 
	 * @return 이북/코믹 전권 소장/대여 에피소드 상품 - 구매이력 생성 요청 데이터 목록
	 */
	@Override
	public List<PrchsDtlMore> makeEbookComicEpisodeList(PrchsDtlMore ebookflatInfo, List<EpisodeInfoRes> episodeList,
			String cmpxProdClsfCd) {

		// 구매생성 요청 데이터 세팅
		List<PrchsDtlMore> prchsDtlMoreList = new ArrayList<PrchsDtlMore>();
		PrchsDtlMore prchsDtlMore = null;

		// true-전권 소장, false-전권 대여
		boolean bOwn = StringUtils.equals(cmpxProdClsfCd, PurchaseConstants.FIXRATE_PROD_TYPE_EBOOKCOMIC_OWN);

		int prchsDtlCnt = 2; // 전권 소장/대여 상품 자체가 1, 에피소드는 2부터
		for (EpisodeInfoRes episode : episodeList) {
			prchsDtlMore = new PrchsDtlMore();

			prchsDtlMore.setSystemId(ebookflatInfo.getSystemId());
			prchsDtlMore.setUseTenantId(ebookflatInfo.getUseTenantId());
			prchsDtlMore.setUseInsdUsermbrNo(ebookflatInfo.getUseInsdUsermbrNo());
			prchsDtlMore.setProdId(episode.getProdId());
			prchsDtlMore.setCid(episode.getCid());

			// 임시 정보 경우 (전권 소장 상품 구매 시 에피소드 대여 상품, 전권 대여 상품 구매 시 에피소드 소장 상품)
			// 에피소드 기구매 건 기간만료 처리를 위한 정보만 세팅
			if ((bOwn && (StringUtils.equals(episode.getUsePeriodUnitCd(),
					PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED) == false))
					|| ((bOwn == false) && StringUtils.equals(episode.getUsePeriodUnitCd(),
							PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED))) {
				prchsDtlMore.setTemporary(true); // 임시 정보 여부 세팅
				prchsDtlMoreList.add(prchsDtlMore);
				continue;
			}

			prchsDtlMore.setPrchsDtlId(prchsDtlCnt++);

			prchsDtlMore.setTenantId(ebookflatInfo.getTenantId());
			prchsDtlMore.setPrchsId(ebookflatInfo.getPrchsId());
			prchsDtlMore.setPrchsDt(ebookflatInfo.getPrchsDt());
			prchsDtlMore.setUseInsdDeviceId(ebookflatInfo.getUseInsdDeviceId());
			prchsDtlMore.setSendInsdUsermbrNo(ebookflatInfo.getSendInsdUsermbrNo());
			prchsDtlMore.setSendInsdDeviceId(ebookflatInfo.getSendInsdDeviceId());
			prchsDtlMore.setTotAmt(ebookflatInfo.getTotAmt());
			prchsDtlMore.setPrchsReqPathCd(ebookflatInfo.getPrchsReqPathCd());
			prchsDtlMore.setClientIp(ebookflatInfo.getClientIp());
			prchsDtlMore.setUseHidingYn(PurchaseConstants.USE_N);
			prchsDtlMore.setSendHidingYn(PurchaseConstants.USE_N);
			prchsDtlMore.setPrchsCaseCd(ebookflatInfo.getPrchsCaseCd());
			prchsDtlMore.setTenantProdGrpCd(ebookflatInfo.getTenantProdGrpCd().substring(0, 12)
					+ PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_UNIT);
			prchsDtlMore.setCurrencyCd(ebookflatInfo.getCurrencyCd());
			prchsDtlMore.setNetworkTypeCd(ebookflatInfo.getNetworkTypeCd());
			prchsDtlMore.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_UNIT); // 단위 상품
			prchsDtlMore.setProdAmt(episode.getProdAmt());
			prchsDtlMore.setProdQty(1);
			prchsDtlMore.setUseStartDt(ebookflatInfo.getUseStartDt());
			prchsDtlMore.setUseExprDt(ebookflatInfo.getUseExprDt());
			prchsDtlMore.setDwldStartDt(ebookflatInfo.getDwldStartDt());
			prchsDtlMore.setDwldExprDt(ebookflatInfo.getDwldExprDt());
			prchsDtlMore.setUseFixrateProdId(ebookflatInfo.getProdId());
			prchsDtlMore.setDrmYn(ebookflatInfo.getDrmYn());
			prchsDtlMore.setAlarmYn(PurchaseConstants.USE_Y);
			prchsDtlMore.setRegId(ebookflatInfo.getRegId());
			prchsDtlMore.setRegDt(ebookflatInfo.getRegDt());
			prchsDtlMore.setUpdId(ebookflatInfo.getUpdId());
			prchsDtlMore.setUpdDt(ebookflatInfo.getUpdDt());

			prchsDtlMoreList.add(prchsDtlMore);
		}

		return prchsDtlMoreList;
	}

	/**
	 * <pre>
	 * 구매생성을 위한 데이터 목록 생성.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 VO
	 * 
	 * @return 구매생성을 위한 데이터 목록
	 */
	@Override
	public void buildReservedData(PurchaseOrderInfo purchaseOrderInfo, List<PrchsDtlMore> prchsDtlMoreList) {
		if (purchaseOrderInfo.getRealTotAmt() <= 0.0) { // 무료구매 시에는 처리 안 함
			return;
		}

		/*
		 * 구매예약 시 저장할 데이터 (공통) - systemId, networkTypeCd - 결제자: userId, deviceId, marketDeviceKey, deviceModelCd,
		 * telecom, imei, oneId - 보유자: useDeviceId, useDeviceModelCd
		 */
		// PurchaseUserDevice useUser = purchaseOrderInfo.isGift() ? purchaseOrderInfo.getReceiveUser() :
		// purchaseOrderInfo
		// .getPurchaseUser();

		StringBuffer sbReserveData = new StringBuffer(1024);
		sbReserveData
				.append("apiVer=")
				.append(purchaseOrderInfo.getApiVer())
				.append("&systemId=")
				.append(purchaseOrderInfo.getSystemId())
				.append("&userId=")
				.append(purchaseOrderInfo.getPurchaseUser().getUserId())
				.append("&deviceId=")
				.append(purchaseOrderInfo.getPurchaseUser().getDeviceId())
				.append("&marketDeviceKey=")
				.append(StringUtils.defaultString(purchaseOrderInfo.getPurchaseUser().getMarketDeviceKey()))
				.append("&deviceModelCd=")
				.append(purchaseOrderInfo.getPurchaseUser().getDeviceModelCd())
				.append("&telecom=")
				.append(purchaseOrderInfo.getPurchaseUser().getTelecom())
				.append("&imei=")
				.append(StringUtils.defaultString(purchaseOrderInfo.getImei()))
				.append("&oneId=")
				.append(StringUtils.equals(purchaseOrderInfo.getPurchaseUser().getUserType(),
						PurchaseConstants.USER_TYPE_ONEID) ? purchaseOrderInfo.getPurchaseUser().getUserId() : "")
				.append("&networkTypeCd=").append(purchaseOrderInfo.getNetworkTypeCd()).append("&mediaId=")
				.append(StringUtils.defaultString(purchaseOrderInfo.getMediaId())).append("&dupleYn=")
				.append(purchaseOrderInfo.isPossibleDuplication() ? "Y" : "N");

		// T멤버쉽 적립율
		Map<String, Integer> tMileageRateMap = purchaseOrderInfo.getPurchaseProductList().get(0).getMileageRateMap();
		if (tMileageRateMap == null) {
			tMileageRateMap = new HashMap<String, Integer>();
			tMileageRateMap.put(PurchaseConstants.USER_GRADE_PLATINUM, 0);
			tMileageRateMap.put(PurchaseConstants.USER_GRADE_GOLD, 0);
			tMileageRateMap.put(PurchaseConstants.USER_GRADE_SILVER, 0);
		}

		sbReserveData.append("&tMileageRateInfo=");
		for (String key : tMileageRateMap.keySet()) {
			sbReserveData.append(key).append(":").append(tMileageRateMap.get(key)).append(";");
		}
		sbReserveData.setLength(sbReserveData.length() - 1);

		// 구매이력 추가 생성 건
		List<PurchaseProduct> workProductList = new ArrayList<PurchaseProduct>(
				purchaseOrderInfo.getPurchaseProductList());
		for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
			if (product.getFullIapProductInfo() != null) { // IAP 정식판 전환 상품
				workProductList.add(product.getFullIapProductInfo());
			}
		}

		int commonReserveDataLen = sbReserveData.length();
		int i = 0;
		int idx = 0;

		// 수신자 만큼 - 상품 갯수 만큼 반복 : Biz쿠폰은 예외 (효율을 위해 ReceiverList만 저장)
		List<PurchaseUserDevice> useUserList = new ArrayList<PurchaseUserDevice>();
		if (purchaseOrderInfo.isGift()) {
			if (purchaseOrderInfo.isBizShopping()) {
				useUserList.add(purchaseOrderInfo.getPurchaseUser());
			} else {
				useUserList = purchaseOrderInfo.getReceiveUserList();
			}

		} else {
			useUserList.add(purchaseOrderInfo.getPurchaseUser());
		}

		for (PurchaseUserDevice useUser : useUserList) {
			for (PurchaseProduct product : workProductList) {
				for (i = 0; i < product.getProdQty(); i++) {

					sbReserveData.setLength(commonReserveDataLen);

					if (purchaseOrderInfo.isGift()) {
						// 선물수신자 성명
						sbReserveData.append("&receiveName=").append(StringUtils.defaultString(useUser.getUserName()));
					}

					sbReserveData
							.append("&useDeviceId=")
							.append(useUser.getDeviceId())
							.append("&useDeviceModelCd=")
							.append(useUser.getDeviceModelCd())
							.append("&aid=")
							.append(StringUtils.defaultString(product.getAid()))
							.append("&couponCode=")
							.append(StringUtils.defaultString(product.getCouponCode()))
							.append("&itemCode=")
							.append(StringUtils.defaultString(product.getItemCode()))
							.append("&bonusPoint=")
							.append(StringUtils.defaultString(product.getBnsCashAmt(), "0"))
							.append("&bonusPointUsePeriodUnitCd=")
							.append(StringUtils.defaultString(product.getBnsUsePeriodUnitCd()))
							.append("&bonusPointUsePeriod=")
							.append(StringUtils.defaultString(String.valueOf(product.getBnsUsePeriod()), "0"))
							.append("&bonusPointUsableDayCnt=")
							.append(StringUtils.defaultString(String.valueOf(product.getBnsUsePeriod()), "0"))
							.append("&autoPrchsPeriodUnitCd=")
							.append(StringUtils.defaultString(product.getAutoPrchsPeriodUnitCd()))
							.append("&autoPrchsPeriodValue=")
							.append(StringUtils.defaultString(String.valueOf(product.getAutoPrchsPeriodValue()), "0"))
							.append("&afterAutoPayDt=")
							.append(StringUtils.defaultString(product.getAfterAutoPayDt()))
							.append("&sellerMbrNo=")
							.append(StringUtils.defaultString(product.getSellerMbrNo()))
							.append("&mallCd=")
							.append(StringUtils.defaultString(product.getMallCd()))
							.append("&outsdContentsId=")
							.append(StringUtils.defaultString(product.getOutsdContentsId()))
							.append("&autoPrchsYn=")
							.append(StringUtils.defaultString(product.getAutoPrchsYN()))
							.append("&autoLastPeriod=")
							.append(product.getAutoPrchsLastPeriodValue() == null ? 0 : product
									.getAutoPrchsLastPeriodValue()).append("&specialCouponId=")
							.append(StringUtils.defaultString(product.getSpecialSaleCouponId()))
							.append("&specialCouponAmt=").append(product.getSpecialCouponAmt())
							.append("&cmpxProdClsfCd=").append(StringUtils.defaultString(product.getCmpxProdClsfCd()))
							.append("&prodCaseCd=").append(StringUtils.defaultString(product.getProdCaseCd()))
							.append("&s2sAutoYn=").append(StringUtils.defaultString(product.getS2sAutoPrchsYn()));

					// 소장/대여 상품 정보 조회: VOD/이북 단건, 유료 결제 요청 시
					if (purchaseOrderInfo.getPurchaseProductList().size() == 1
							&& purchaseOrderInfo.getRealTotAmt() > 0.0
							&& (purchaseOrderInfo.isVod() || purchaseOrderInfo.isEbookcomic())
							&& purchaseOrderInfo.isFlat() == false) {

						if (StringUtils.equals(StringUtils.defaultIfBlank(product.getPossLendClsfCd(),
								PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_POSSESION),
								PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_POSSESION)) {
							sbReserveData.append("&dwldAvailableDayCnt=&usePeriodCnt=");
						} else {
							sbReserveData.append("&dwldAvailableDayCnt=").append(product.getUsePeriod())
									.append("&usePeriodCnt=").append(product.getUsePeriod());
						}

					} else {
						sbReserveData.append("&dwldAvailableDayCnt=&usePeriodCnt=");
					}

					// IAP P/P 처리
					if (purchaseOrderInfo.isIap()) {
						sbReserveData.append("&iapYn=Y").append("&iapPostbackUrl=")
								.append(StringUtils.defaultString(product.getIapPostbackUrl())).append("&iapProdKind=")
								.append(StringUtils.defaultString(product.getIapProdKind())).append("&iapProdCase=")
								.append(StringUtils.defaultString(product.getIapProdCase()));
						if (product.getIapUsePeriod() != null) {
							sbReserveData.append("&iapUsePeriod=").append(product.getIapUsePeriod());
						}
					}

					prchsDtlMoreList.get(idx++).setPrchsResvDesc(sbReserveData.toString());
				}
			}
		}
	}

	/**
	 * <pre>
	 * 구매예약시 예약컬럼에 저장해뒀던 추가 데이터들.
	 * </pre>
	 * 
	 * @param reservedData
	 *            구매예약시 저장해뒀던 추가 데이터들
	 * 
	 * @return 분리된 파라미터 Map
	 */
	@Override
	public Map<String, String> parseReservedData(String reservedData) {
		Map<String, String> reservedDataMap = new HashMap<String, String>();
		String[] arReservedData = null;
		String[] arParamKeyValue = null;

		if (StringUtils.isNotEmpty(reservedData)) {
			arReservedData = reservedData.split("&");

			for (String param : arReservedData) {
				arParamKeyValue = param.split("=", 2);
				reservedDataMap.put(arParamKeyValue[0], arParamKeyValue[1]);
			}
		}

		return reservedDataMap;
	}
}
