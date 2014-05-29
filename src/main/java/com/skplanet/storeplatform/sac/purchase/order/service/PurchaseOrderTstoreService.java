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
	 * @return T Store 쿠폰 목록
	 */
	public String searchTstoreCouponList(String userKey, String deviceId, List<String> prodIdList);

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
	 * @param cashUseExprDt
	 *            Cash 이용종료일시
	 * 
	 * @param bonusPointAmt
	 *            충전할 Point 금액
	 * 
	 * @param bonusPointUseExprDt
	 *            Point 이용종료일시
	 * 
	 * @return 충전 예약 결과 정보 목록
	 */
	public List<TStoreCashChargeReserveDetailEcRes> reserveGameCashCharge(String userKey, double cashAmt,
			String cashUseExprDt, double bonusPointAmt, String bonusPointUseExprDt);

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
}
