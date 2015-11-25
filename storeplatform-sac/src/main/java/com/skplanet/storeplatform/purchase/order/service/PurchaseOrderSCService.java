/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.order.service;

import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.SapNoti;
import com.skplanet.storeplatform.purchase.client.common.vo.UniqueTid;
import com.skplanet.storeplatform.purchase.client.order.vo.*;

import java.util.List;

/**
 * 
 * 구매SC - 구매 서비스 인터페이스
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
public interface PurchaseOrderSCService {

	/**
	 * 
	 * <pre>
	 * 구매 예약.
	 * </pre>
	 * 
	 * @param prchsDtlMoreList
	 *            구매예약 정보 목록
	 * @return 예약된 갯수
	 */
	public int executeReserve(List<PrchsDtlMore> prchsDtlMoreList);

	/**
	 * 
	 * <pre>
	 * 무료 구매.
	 * </pre>
	 * 
	 * @param makeFreePurchaseScReq
	 *            무료구매 요청 VO
	 * @return 무료구매 처리된 갯수
	 */
	public int executeFreePurchase(MakeFreePurchaseScReq makeFreePurchaseScReq);

	/**
	 * 
	 * <pre>
	 * 구매 확정.
	 * </pre>
	 * 
	 * @param confirmPurchaseScReq
	 *            구매확정 요청 VO
	 * @return 구매확정된 갯수
	 */
	public int executeConfirmPurchase(ConfirmPurchaseScReq confirmPurchaseScReq);

	/**
	 * 
	 * <pre>
	 * 구매/결제 통합 구매이력 생성.
	 * </pre>
	 * 
	 * @param createCompletePurchaseScReq
	 *            구매/결제 통합 구매이력 생성 요청 VO
	 * @return 구매/결제 통합 구매이력 생성 응답 VO
	 */
	public int executeCompletePurchase(CreateCompletePurchaseScReq createCompletePurchaseScReq);

	// =================================

	/**
	 * 
	 * <pre>
	 * 구매 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMore
	 *            구매 정보
	 * @return 생성한 갯수
	 */
	public int insertPrchs(PrchsDtlMore prchsDtlMore);

	/**
	 * 
	 * <pre>
	 * 구매상세 생성.
	 * </pre>
	 * 
	 * @param prchsDtlMoreList
	 *            구매 정보 리스트
	 * @return 생성한 갯수
	 */
	public int insertPrchsDtl(List<PrchsDtlMore> prchsDtlMoreList);

	/**
	 * 
	 * <pre>
	 * 결제 생성.
	 * </pre>
	 * 
	 * @param paymentList
	 *            결제 정보 리스트
	 * @return 생성된 건수
	 */
	public int insertPayment(List<Payment> paymentList);

	/**
	 * 
	 * <pre>
	 * 신규 자동구매 생성.
	 * </pre>
	 * 
	 * @param autoPrchsMoreList
	 *            자동구매 생성 목록
	 * @return 자동구매 생성 건수
	 */
	public int insertAutoPrchs(List<AutoPrchsMore> autoPrchsMoreList);

	String selectCurrentDate();

	/**
	 * 
	 * <pre>
	 * 결제TID Unique 정보 생성.
	 * </pre>
	 * 
	 * @param uniqueTid
	 *            결제TID Unique 정보
	 * 
	 * @return 결제TID Unique 정보 생성 건수
	 */
	public int insertUniqueTid(UniqueTid uniqueTid);

	/**
	 * <pre>
	 * 구매상세 변경.
	 * </pre>
	 * 
	 * @param prchsDtlMoreParam
	 *            변경할 구매상세 정보
	 * @param oldStatusCd
	 *            현재 구매상태코드
	 * @param newStatusCd
	 *            변경할 구매상태코드
	 * @param offeringId
	 *            오퍼링 ID
	 * @param shoppingCouponList
	 *            쇼핑발급 목록
	 * @param useTenantId
	 *            구매DB 파티션 처리용 - 이용자 테넌트ID
	 * @param useInsdUsermbrNo
	 *            구매DB 파티션 처리용 - 이용자 회원 내부관리번호
	 * @return 변경된 건수
	 */
	public int updatePrchsDtlByStatus(PrchsDtlMore prchsDtlMoreParam, String oldStatusCd, String newStatusCd,
			String offeringId, List<ShoppingCouponPublishInfo> shoppingCouponList, String useTenantId,
			String useInsdUsermbrNo);

	/**
	 * 
	 * <pre>
	 * SAP 결제완료Noti 생성.
	 * </pre>
	 * 
	 * @param sapNotiList
	 *            SAP 결제완료Noti 생성 목록
	 * @return SAP 결제완료Noti 생성 건수
	 */
	public int insertSapNoti(List<SapNoti> sapNotiList);
}
