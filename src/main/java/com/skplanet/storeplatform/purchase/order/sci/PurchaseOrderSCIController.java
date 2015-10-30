/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.order.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ConfirmPurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateCompletePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateCompletePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateSapNotiScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.MakeFreePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.MakeFreePurchaseScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.ReservePurchaseScRes;
import com.skplanet.storeplatform.purchase.order.service.PurchaseOrderSCService;
import com.skplanet.storeplatform.purchase.order.service.PurchaseOrderSearchSCService;

/**
 * 
 * 구매SC - 구매 연동 컨트롤러
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
@LocalSCI
public class PurchaseOrderSCIController implements PurchaseOrderSCI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseOrderSCService orderScService;
	@Autowired
	private PurchaseOrderSearchSCService orderSearchScService;

	// ===========================================================================================================

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
	@Override
	public ReservePurchaseScRes reservePurchase(ReservePurchaseScReq req) {
		this.logger.info("PRCHS,ORDER,SC,RESERVE,START,{}", req.getPrchsDtlMoreList().size());

		// TB_구매상세 생성 (구매예약)
		int count = this.orderScService.executeReserve(req.getPrchsDtlMoreList());

		// Response
		this.logger.info("PRCHS,ORDER,SC,RESERVE,END,{},{}", req.getPrchsDtlMoreList().size(), count);
		return new ReservePurchaseScRes(count);
	}

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
	@Override
	public MakeFreePurchaseScRes makeFreePurchase(MakeFreePurchaseScReq req) {
		this.logger.info("PRCHS,ORDER,SC,CREATE_COMPLETE,START,{}", req.getPrchsDtlMoreList().size());

		// 구매완료 생성
		int count = this.orderScService.executeFreePurchase(req);

		// Response
		this.logger.info("PRCHS,ORDER,SC,CREATE_COMPLETE,END,{},{}", req.getPrchsDtlMoreList().size(), count);
		return new MakeFreePurchaseScRes(count);
	}

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
	@Override
	public ConfirmPurchaseScRes confirmPurchase(ConfirmPurchaseScReq req) {
		this.logger.info("PRCHS,ORDER,SC,CONFIRM,START,{}", req.getPrchsId());

		// 구매확정
		int count = this.orderScService.executeConfirmPurchase(req);

		// Response
		this.logger.info("PRCHS,ORDER,SC,CONFIRM,END,{},{}", req.getPrchsId(), count);
		return new ConfirmPurchaseScRes(count);
	}

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
	@Override
	public CreateCompletePurchaseScRes completePurchase(CreateCompletePurchaseScReq req) {
		this.logger.info("PRCHS,ORDER,SC,CONFIRM,START,{}", req.getPrchsDtlMoreList().get(0).getPrchsId());

		// 구매확정
		int count = this.orderScService.executeCompletePurchase(req);

		// Response
		this.logger.info("PRCHS,ORDER,SC,CONFIRM,END,{},{}", req.getPrchsDtlMoreList().get(0).getPrchsId(), count);
		return new CreateCompletePurchaseScRes();
	}

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
	@Override
	public int createSapNoti(CreateSapNotiScReq req) {
		this.logger.info("PRCHS,ORDER,SC,CREATE_SAP_NOTI,START,{}", req.getSapNotiList().get(0).getPrchsId());

		// 구매확정
		int count = this.orderScService.insertSapNoti(req.getSapNotiList());

		// Response
		this.logger.info("PRCHS,ORDER,SC,CREATE_SAP_NOTI,END,{},{}", req.getSapNotiList().get(0).getPrchsId(), count);
		return count;
	}

}
