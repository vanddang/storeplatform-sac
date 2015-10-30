/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.order.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateCompletePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateCompletePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateSapNotiScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.MakeFreePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.MakeFreePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScRes;

/**
 * 
 * 구매SC - 구매 연동 인터페이스
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
@SCI
public interface PurchaseOrderSCI {

	/**
	 * 
	 * <pre>
	 * 구매 예약.
	 * </pre>
	 * 
	 * @param req
	 *            구매예약 요청 VO
	 * @return 구매예약 결과 응답 VO
	 */
	public ReservePurchaseScRes reservePurchase(ReservePurchaseScReq req);

	/**
	 * 
	 * <pre>
	 * [하나의 구매단위] 구매완료 생성 (예약/결제 진행 없이 바로 구매 완료).
	 * </pre>
	 * 
	 * @param req
	 *            구매생성 요청 VO
	 * @return 구매생성 결과 응답 VO
	 */
	public MakeFreePurchaseScRes makeFreePurchase(MakeFreePurchaseScReq req);

	/**
	 * 
	 * <pre>
	 * 구매 확정.
	 * </pre>
	 * 
	 * @param req
	 *            구매확정 요청 VO
	 * @return 구매확정 결과 응답 VO
	 */
	public ConfirmPurchaseScRes confirmPurchase(ConfirmPurchaseScReq req);

	/**
	 * 
	 * <pre>
	 * 구매/결제 통합 구매이력 생성.
	 * </pre>
	 * 
	 * @param req
	 *            구매/결제 통합 구매이력 생성 요청 VO
	 * @return 구매/결제 통합 구매이력 생성 응답 VO
	 */
	public CreateCompletePurchaseScRes completePurchase(CreateCompletePurchaseScReq req);

	/**
	 * 
	 * <pre>
	 * SAP 결제완료Noti 생성.
	 * </pre>
	 * 
	 * @param req
	 *            SAP 결제완료Noti 생성 요청 VO
	 * @return SAP 결제완료Noti 생성 건수
	 */
	public int createSapNoti(CreateSapNotiScReq req);
}
