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

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.DateUtils;
import com.skplanet.storeplatform.purchase.client.common.vo.MembershipReserve;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;
import com.skplanet.storeplatform.purchase.client.order.vo.AutoPrchsMore;
import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.purchase.client.order.vo.PurchaseUserInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductInfoList;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PaymentInfo;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.PaymethodUtil;
import com.skplanet.storeplatform.sac.purchase.order.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

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

	@Autowired
	private PurchaseOrderAssistService purchaseOrderAssistService;

	/**
	 * <pre>
	 * 구매생성을 위한 데이터 목록 생성.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 VO
	 * 
	 * @param statusCd
	 *            구매상태코드
	 * 
	 * @return 구매생성을 위한 데이터 목록
	 */
	@Override
	public List<PrchsDtlMore> makePrchsDtlMoreList(PurchaseOrderInfo purchaseOrderInfo, String statusCd) {

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

		boolean bGift = purchaseOrderInfo.isGift();

		// 수신자 만큼 - 상품 갯수 만큼 반복 : Biz쿠폰은 예외 (효율을 위해 ReceiverList만 저장)

		List<PurchaseUserDevice> useUserList = new ArrayList<PurchaseUserDevice>();
		if (bGift) {
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
					prchsDtlMore.setStatusCd(statusCd);

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
					prchsDtlMore.setUsePeriodSetCd(product.getUsePeriodSetCd());

					// ############# 아래 이용기간 세팅 순서 주의 ###################

					if (purchaseOrderInfo.isFlat()) { // 정액상품 자체에 대해서는 구매시점/처리완료 처리
						// 이용기간 설정 타입: 구매시점
						prchsDtlMore.setUsePeriodSetCd(PurchaseConstants.PRODUCT_USE_PERIOD_SET_PURCHASE);
						// 기간 재산정: 처리완료
						prchsDtlMore.setUsePeriodRedateCd(PurchaseConstants.PROCESSING_STATUS_COMPLETE);

					} else {
						if (bGift) { // 선물 시
							prchsDtlMore.setDwldExprDt(PurchaseConstants.UNLIMITED_DATE); // 초기 재다운로드 종료일시: 무제한
							prchsDtlMore.setUsePeriodRedateCd(PurchaseConstants.PROCESSING_STATUS_STANDBY); // 기간 재산정:
																											// 처리대기

						} else {
							if (StringUtils.equals(product.getUsePeriodSetCd(),
									PurchaseConstants.PRODUCT_USE_PERIOD_SET_DOWNLOAD)) { // 이용기간 설정 타입: 이용시점
								prchsDtlMore.setDwldExprDt(PurchaseConstants.UNLIMITED_DATE); // 초기 재다운로드 종료일시: 무제한
								prchsDtlMore.setUsePeriodRedateCd(PurchaseConstants.PROCESSING_STATUS_STANDBY); // 기간
																												// 재산정:
																												// 처리대기

							} else if (StringUtils.equals(product.getUsePeriodSetCd(),
									PurchaseConstants.PRODUCT_USE_PERIOD_SET_PURCHASE)) { // 이용기간 설정 타입: 구매시점
								prchsDtlMore.setUsePeriodRedateCd(PurchaseConstants.PROCESSING_STATUS_COMPLETE); // 기간
																												 // 재산정:
																												 // 처리완료
							}
							// else {} 2015.06.전시: 100% 확률로 위 2개 값 중에 하나가 내려갈거임 (null 경우도 없음)
						}
					}

					if (product.isFullProd()) { // 정식판 상품
						// 이용기간 설정 타입: 구매시점
						prchsDtlMore.setUsePeriodSetCd(PurchaseConstants.PRODUCT_USE_PERIOD_SET_PURCHASE);
						// 기간 재산정: 처리완료
						prchsDtlMore.setUsePeriodRedateCd(PurchaseConstants.PROCESSING_STATUS_COMPLETE);
					}

					// 정액권으로 에피소드 이용시
					if (StringUtils.isNotBlank(product.getUseFixrateProdId())) {
						prchsDtlMore.setUseExprDt(product.getUseExprDt());
						prchsDtlMore.setDwldExprDt(product.getDwldExprDt());

						// 이용기간 설정 타입: 구매시점
						prchsDtlMore.setUsePeriodSetCd(PurchaseConstants.PRODUCT_USE_PERIOD_SET_PURCHASE);
						// 기간 재산정: 처리완료
						prchsDtlMore.setUsePeriodRedateCd(PurchaseConstants.PROCESSING_STATUS_COMPLETE);
					}

					if (purchaseOrderInfo.isFreeChargeReq()) {
						// 비과금 구매요청 시 - 종료일자가 있는 경우와 T프리미엄인 경우 이용일자 재산정 안함
						if (StringUtil.equals(purchaseOrderInfo.getPrchsReqPathCd(),
								PurchaseConstants.PRCHS_REQ_PATH_T_FREEMIUM)
								|| StringUtils.isNotBlank(product.getUseExprDt())
								|| StringUtils.isNotBlank(product.getDwldExprDt())) {
							prchsDtlMore.setUsePeriodRedateCd(PurchaseConstants.PROCESSING_STATUS_COMPLETE);
						}

						if (StringUtils.isNotBlank(product.getUseExprDt()) // 이용 종료기간, 다운로드 종료기간이 모두 있는 경우
								&& StringUtils.isNotBlank(product.getDwldExprDt())) {
							prchsDtlMore
									.setUseExprDt(product.getUseExprDt().length() == 14 ? product.getUseExprDt() : product
											.getUseExprDt() + "235959");
							prchsDtlMore
									.setDwldExprDt(product.getDwldExprDt().length() == 14 ? product.getDwldExprDt() : 
										product.getDwldExprDt() + "235959");
						} else if (StringUtils.isNotBlank(product.getUseExprDt()) // 이용 종료기간만 있는 경우
								&& StringUtils.isBlank(product.getDwldExprDt())) {
							prchsDtlMore
									.setUseExprDt(product.getUseExprDt().length() == 14 ? product.getUseExprDt() : product
											.getUseExprDt() + "235959");
							prchsDtlMore.setDwldExprDt(prchsDtlMore.getUseExprDt());
						} else if (StringUtils.isBlank(product.getUseExprDt()) // 다운로드 종료기간만 있는 경우
								&& StringUtils.isNotBlank(product.getDwldExprDt())) {
							prchsDtlMore
									.setDwldExprDt(product.getDwldExprDt().length() == 14 ? product.getDwldExprDt() : product
											.getDwldExprDt() + "235959");
							prchsDtlMore.setUseExprDt(prchsDtlMore.getDwldExprDt());
						}
					}

					// ######################################################################

					prchsDtlMore.setDrmYn(product.getDrmYn());

					prchsDtlMore.setUseFixrateProdId(product.getUseFixrateProdId());
					prchsDtlMore.setUseFixratePrchsId(product.getUseFixratePrchsId());
					prchsDtlMore.setUseFixrateProdClsfCd(product.getUseFixrateProdClsfCd());
					prchsDtlMore.setAlarmYn(PurchaseConstants.USE_Y);
					/* IAP */
					prchsDtlMore.setTid(product.getTid()); // 부분유료화 개발사 구매Key
					prchsDtlMore.setTxId(product.getTxId()); // 부분유료화 전자영수증 번호
					prchsDtlMore.setParentProdId(product.getParentProdId()); // 부모_상품_ID
					prchsDtlMore.setContentsType(product.getContentsType()); // 컨텐츠_타입
					prchsDtlMore.setPartChrgVer(product.getPartChrgVer()); // 부분_유료_버전
					// <DB 컬럼 의미 확장>
					// IAP 상품 경우-부분유료화 상품, 정액 상품 경우-회차 정보
					if (purchaseOrderInfo.isIap()) {
						prchsDtlMore.setPartChrgProdNm(product.getPartChrgProdNm()); // 부분_유료_상품_명
					} else if (purchaseOrderInfo.isFlat()) {
						prchsDtlMore.setPartChrgProdNm(product.getChapterText()); // 이용권 회차 정보
					}
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

					prchsDtlMore.setAutoPrchsYn(product.getAutoPrchsYN());

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

			payment.setMnoCd(paymentInfo.getMnoCd());
			payment.setLimtMbrYn(paymentInfo.getLimitMemberYn());

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
		// if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_QA)) {
		// return null;
		// }

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
			autoPrchsMore.setPaymentEndDt(PurchaseConstants.UNLIMITED_DATE);
		} else {
			autoPrchsMore.setAutoPrchsLastPeriod(Integer.parseInt(autoLastPeriod));
		}
		try {
			String afterPaymentDt = DateFormatUtils.format(DateUtils.truncate(
					DateUtils.parseDate(prchsDtlMore.getUseExprDt(), "yyyyMMddHHmmss"), Calendar.DATE),
					"yyyyMMddHHmmss");
			autoPrchsMore.setAfterPaymentDt(afterPaymentDt.substring(0, 8) + "100000"); // 10시 00분 00초
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
	 * @param prchsDtlMoreList
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
		membershipReserve.setPromId(mileageSubInfo.getPromId()); // 프로모션 ID
		membershipReserve.setSaveDt(mileageSubInfo.getSaveDt());

		membershipReserveList.add(membershipReserve);

		return membershipReserveList;
	}

	/**
	 * <pre>
	 * 정액제 상품의 하위 에피소드 상품 - 일괄 구매이력 생성 요청 데이터 목록 생성.
	 * </pre>
	 * 
	 * @param fixrateInfo
	 *            이용할 정액제 상품 구매 정보 VO
	 * 
	 * @param episodeList
	 *            이용할 정액제 상품의 하위 에피소드 상품 정보 VO
	 * 
	 * @param statusCd
	 *            구매상태코드
	 * 
	 * @return 정액제 상품의 하위 에피소드 상품 - 일괄 구매이력 생성 요청 데이터 목록
	 */
	@Override
	public List<PrchsDtlMore> makePackageEpisodeList(PrchsDtlMore fixrateInfo, List<CmpxProductInfoList> episodeList,
			String statusCd) {

		// 구매생성 요청 데이터 세팅
		List<PrchsDtlMore> prchsDtlMoreList = new ArrayList<PrchsDtlMore>();
		PrchsDtlMore prchsDtlMore = null;

		int prchsDtlCnt = 2; // 정액제 상품 자체가 1, 에피소드는 2부터
		for (CmpxProductInfoList episode : episodeList) {

			if (StringUtils.isBlank(episode.getUsePeriodUnitCd())) {
				throw new StorePlatformException("SAC_PUR_7215", "정액제 에피소드 <null>");
			}

			prchsDtlMore = new PrchsDtlMore();

			prchsDtlMore.setStatusCd(statusCd);
			prchsDtlMore.setSystemId(fixrateInfo.getSystemId());
			prchsDtlMore.setUseTenantId(fixrateInfo.getUseTenantId());
			prchsDtlMore.setUseInsdUsermbrNo(fixrateInfo.getUseInsdUsermbrNo());
			prchsDtlMore.setProdId(episode.getProdId());
			prchsDtlMore.setCid(episode.getCid());

			prchsDtlMore.setPrchsDtlId(prchsDtlCnt++);

			prchsDtlMore.setTenantId(fixrateInfo.getTenantId());
			prchsDtlMore.setPrchsId(fixrateInfo.getPrchsId());
			prchsDtlMore.setPrchsDt(fixrateInfo.getPrchsDt());
			prchsDtlMore.setUseInsdDeviceId(fixrateInfo.getUseInsdDeviceId());
			prchsDtlMore.setSendInsdUsermbrNo(fixrateInfo.getSendInsdUsermbrNo());
			prchsDtlMore.setSendInsdDeviceId(fixrateInfo.getSendInsdDeviceId());
			prchsDtlMore.setTotAmt(fixrateInfo.getTotAmt());
			prchsDtlMore.setPrchsReqPathCd(fixrateInfo.getPrchsReqPathCd());
			prchsDtlMore.setClientIp(fixrateInfo.getClientIp());
			prchsDtlMore.setUseHidingYn(PurchaseConstants.USE_N);
			prchsDtlMore.setSendHidingYn(PurchaseConstants.USE_N);
			prchsDtlMore.setPrchsCaseCd(fixrateInfo.getPrchsCaseCd());
			prchsDtlMore.setTenantProdGrpCd(fixrateInfo.getTenantProdGrpCd().substring(0, 12)
					+ PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_UNIT);
			prchsDtlMore.setCurrencyCd(fixrateInfo.getCurrencyCd());
			prchsDtlMore.setNetworkTypeCd(fixrateInfo.getNetworkTypeCd());
			prchsDtlMore.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_UNIT); // 단위 상품
			prchsDtlMore.setProdAmt(episode.getProdAmt());
			prchsDtlMore.setProdQty(1);
			prchsDtlMore.setUseStartDt(fixrateInfo.getUseStartDt());
			prchsDtlMore.setDwldStartDt(fixrateInfo.getDwldStartDt());
			prchsDtlMore.setUsePeriodUnitCd(episode.getUsePeriodUnitCd());
			prchsDtlMore.setUsePeriod(String.valueOf(episode.getUsePeriod() == null ? 0 : episode.getUsePeriod()));
			prchsDtlMore.setDrmYn(episode.getDrmYn());
			// 이용기간 설정 타입: 구매시점
			prchsDtlMore.setUsePeriodSetCd(PurchaseConstants.PRODUCT_USE_PERIOD_SET_PURCHASE);
			// 기간 재산정: 처리완료
			prchsDtlMore.setUsePeriodRedateCd(PurchaseConstants.PROCESSING_STATUS_COMPLETE);

			prchsDtlMore.setUseFixrateProdId(fixrateInfo.getProdId());
			prchsDtlMore.setUseFixratePrchsId(fixrateInfo.getPrchsId());
			prchsDtlMore.setAlarmYn(PurchaseConstants.USE_Y);
			prchsDtlMore.setRegId(fixrateInfo.getRegId());
			prchsDtlMore.setRegDt(fixrateInfo.getRegDt());
			prchsDtlMore.setUpdId(fixrateInfo.getUpdId());
			prchsDtlMore.setUpdDt(fixrateInfo.getUpdDt());

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
		// for (String key : tMileageRateMap.keySet()) {
		// sbReserveData.append(key).append(":").append(tMileageRateMap.get(key)).append(";");
		// }
		// 2015.07.23 sonarQube 수정
		for (Entry<String, Integer> entry : tMileageRateMap.entrySet()) {
			sbReserveData.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
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
							.append("&possLendClsfCd=").append(StringUtils.defaultString(product.getPossLendClsfCd()))
							.append("&prodCaseCd=").append(StringUtils.defaultString(product.getProdCaseCd()))
							.append("&s2sAutoYn=").append(StringUtils.defaultString(product.getS2sAutoPrchsYn()))
							.append("&s2sYn=").append(StringUtils.isNotBlank(product.getSearchPriceUrl()) ? "Y" : "N")
							.append("&svcGrpCd=").append(StringUtils.defaultString(product.getSvcGrpCd()))
							.append(this.appendResvData(PurchaseConstants.IF_DISPLAY_RES_PROM_ID,
									product.getPromId() == null ? "0" : String.valueOf(product.getPromId()))) // 이벤트 프로모션 ID
							.append(this.appendResvData(PurchaseConstants.IF_DISPLAY_RES_ACLMETHOD_CD,product.getAcmlMethodCd())) // 프로모션 적립 방법
							.append(this.appendResvData(PurchaseConstants.IF_DISPLAY_RES_ACML_DT, product.getAcmlDt())) // 프로모션 적립 방법
							.append(this.appendResvData(PurchaseConstants.IF_DISPLAY_RES_SPECIALTYPE_CD, product.getSpecialTypeCd())); // 특가상품 유형코드, 팅요금제 상품 유형 코드

					// 대여정보: VOD/이북 단건, 유료 결제 요청 시
					if (purchaseOrderInfo.getPurchaseProductList().size() == 1
							&& purchaseOrderInfo.getRealTotAmt() > 0.0
							&& (purchaseOrderInfo.isVod() || purchaseOrderInfo.isEbookcomic())
							&& purchaseOrderInfo.isFlat() == false) {

						if (StringUtils.equals(StringUtils.defaultIfBlank(product.getPossLendClsfCd(),
								PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_POSSESION),
								PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_RENTAL)) {
							sbReserveData.append("&dwldAvailableDayCnt=").append(product.getUsePeriod())
									.append("&usePeriodCnt=").append(product.getUsePeriod());
						} else {
							sbReserveData.append("&dwldAvailableDayCnt=&usePeriodCnt=");
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

					// 정액제 상품
					if (purchaseOrderInfo.isFlat()) {
						sbReserveData.append("&packagePrchsYn=")
								.append(StringUtils.defaultString(product.getPackagePrchsYn()))
								.append("&cmpxProdClsfCd=")
								.append(StringUtils.defaultString(product.getCmpxProdClsfCd()))
								.append("&cmpxProdBookClsfCd=")
								.append(StringUtils.defaultString(product.getCmpxProdBookClsfCd()));
					}

					// 부정결제 참조용 데이터 추가
					sbReserveData.append(this.appendResvData(PurchaseConstants.IF_PUR_ORDER_REQ_FLAG,purchaseOrderInfo.getFlag()));
					prchsDtlMoreList.get(idx++).setPrchsResvDesc(sbReserveData.toString());
				}
			}
		}
	}

	private String appendResvData(String key, String value) {
		StringBuffer sb = new StringBuffer("&");
		sb.append(key).append("=").append(StringUtils.defaultString(value));
		return sb.toString();
	}

	/**
	 * <pre>
	 * 구매예약시 예약컬럼에 저장해뒀던 추가 데이터들.
	 * </pre>
	 * 
	 * @param reservedData
	 *            구매예약시 저장해뒀던 추가 데이터들
	 * 
	 * @return 구매 예약 데이터
	 */
	@Override
	public PurchaseReservedData parseReservedData(String reservedData) {
		Map<String, String> reservedDataMap = this.parseReservedDataByMap(reservedData);

		// --#
		PurchaseReservedData purchaseReservedData = new PurchaseReservedData();
		purchaseReservedData.setApiVer(reservedDataMap.get("apiVer"));
		purchaseReservedData.setSystemId(reservedDataMap.get("systemId"));
		purchaseReservedData.setUserId(reservedDataMap.get("userId"));
		purchaseReservedData.setDeviceId(reservedDataMap.get("deviceId"));
		purchaseReservedData.setMarketDeviceKey(reservedDataMap.get("marketDeviceKey"));
		purchaseReservedData.setDeviceModelCd(reservedDataMap.get("deviceModelCd"));
		purchaseReservedData.setTelecom(reservedDataMap.get("telecom"));
		purchaseReservedData.setImei(reservedDataMap.get("imei"));
		purchaseReservedData.setOneId(reservedDataMap.get("oneId"));
		purchaseReservedData.setNetworkTypeCd(reservedDataMap.get("networkTypeCd"));
		purchaseReservedData.setMediaId(reservedDataMap.get("mediaId"));
		purchaseReservedData.setDupleYn(reservedDataMap.get("dupleYn"));
		purchaseReservedData.settMileageRateInfo(reservedDataMap.get("tMileageRateInfo"));
		// --#
		purchaseReservedData.setUseDeviceId(reservedDataMap.get("useDeviceId"));
		purchaseReservedData.setUseDeviceModelCd(reservedDataMap.get("useDeviceModelCd"));
		purchaseReservedData.setAid(reservedDataMap.get("aid"));
		purchaseReservedData.setCouponCode(reservedDataMap.get("couponCode"));
		purchaseReservedData.setItemCode(reservedDataMap.get("itemCode"));
		purchaseReservedData.setBonusPoint(reservedDataMap.get("bonusPoint"));
		purchaseReservedData.setBonusPointUsePeriodUnitCd(reservedDataMap.get("bonusPointUsePeriodUnitCd"));
		purchaseReservedData.setBonusPointUsePeriod(reservedDataMap.get("bonusPointUsePeriod"));
		purchaseReservedData.setBonusPointUsableDayCnt(reservedDataMap.get("bonusPointUsableDayCnt"));
		purchaseReservedData.setAutoPrchsPeriodUnitCd(reservedDataMap.get("autoPrchsPeriodUnitCd"));
		purchaseReservedData.setAutoPrchsPeriodValue(reservedDataMap.get("autoPrchsPeriodValue"));
		purchaseReservedData.setAfterAutoPayDt(reservedDataMap.get("afterAutoPayDt"));
		purchaseReservedData.setSellerMbrNo(reservedDataMap.get("sellerMbrNo"));
		purchaseReservedData.setMallCd(reservedDataMap.get("mallCd"));
		purchaseReservedData.setOutsdContentsId(reservedDataMap.get("outsdContentsId"));
		purchaseReservedData.setAutoPrchsYn(reservedDataMap.get("autoPrchsYn"));
		purchaseReservedData.setAutoLastPeriod(reservedDataMap.get("autoLastPeriod"));
		purchaseReservedData.setSpecialCouponId(reservedDataMap.get("specialCouponId"));
		purchaseReservedData.setSpecialCouponAmt(reservedDataMap.get("specialCouponAmt"));
		purchaseReservedData.setPossLendClsfCd(reservedDataMap.get("possLendClsfCd"));
		purchaseReservedData.setCmpxProdBookClsfCd(reservedDataMap.get("cmpxProdBookClsfCd"));
		purchaseReservedData.setCmpxProdClsfCd(reservedDataMap.get("cmpxProdClsfCd"));
		purchaseReservedData.setProdCaseCd(reservedDataMap.get("prodCaseCd"));
		purchaseReservedData.setS2sAutoYn(reservedDataMap.get("s2sAutoYn"));
		purchaseReservedData.setS2sYn(reservedDataMap.get("s2sYn"));
		purchaseReservedData.setDwldAvailableDayCnt(reservedDataMap.get("dwldAvailableDayCnt"));
		purchaseReservedData.setUsePeriodCnt(reservedDataMap.get("usePeriodCnt"));
		purchaseReservedData.setIapYn(reservedDataMap.get("iapYn"));
		purchaseReservedData.setIapPostbackUrl(reservedDataMap.get("iapPostbackUrl"));
		purchaseReservedData.setIapProdKind(reservedDataMap.get("iapProdKind"));
		purchaseReservedData.setIapProdCase(reservedDataMap.get("iapProdCase"));
		purchaseReservedData.setIapUsePeriod(reservedDataMap.get("iapUsePeriod"));
		purchaseReservedData.setPackagePrchsYn(reservedDataMap.get("packagePrchsYn"));

		return purchaseReservedData;
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
	public Map<String, String> parseReservedDataByMap(String reservedData) {
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
