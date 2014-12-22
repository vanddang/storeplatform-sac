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

import java.util.List;

import com.skplanet.storeplatform.external.client.tstore.vo.TStoreCashChargeReserveDetailEcRes;

/**
 * 
 * T store 서비스
 * 
 * Updated on : 2014. 5. 28. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderTstoreService {
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
	 * @param purchaseQty
	 *            구매 갯수
	 * 
	 * @return T Store 쿠폰 목록
	 */
	public String searchTstoreCouponList(String userKey, String deviceId, List<String> prodIdList, int purchaseQty);

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
	public String searchTstoreCashIntegrationAmt(String userKey);

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
	public double searchTstoreCashAmt(String userKey);

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
	public double searchTgamepassAmt(String userKey);

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
	public double searchGameCashAmt(String userKey);

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
	public List<TStoreCashChargeReserveDetailEcRes> reserveGameCashCharge(String userKey, double cashAmt,
			String useStartDt, double bonusPointAmt, String bonusPointUsePeriodUnitCd, String bonusPointUsePeriod);

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
	public void confirmGameCashCharge(String userKey, String prchsId,
			List<TStoreCashChargeReserveDetailEcRes> cashReserveResList);

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
	public void cancelGameCashCharge(String userKey, String prchsId,
			List<TStoreCashChargeReserveDetailEcRes> cashReserveResList);

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
	public void postTstoreNoti(String prchsId, String prchsDt, String userKey, String deviceKey, String notiType);

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
	 * @param prodIdInfo
	 *            구매한 상품ID 정보 (;로 연결)
	 */
	public void postTstoreNotiV2(String prchsId, String prchsDt, String userKey, String deviceKey, String notiType,
			boolean bGift, String prodIdInfo);
}
