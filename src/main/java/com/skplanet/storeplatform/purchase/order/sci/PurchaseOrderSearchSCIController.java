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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseListByStatusScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseListByStatusScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchPurchaseSequenceAndDateRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScRes;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchSktPaymentScRes;
import com.skplanet.storeplatform.purchase.order.service.PurchaseOrderSearchSCService;

/**
 * 
 * 구매SC - 구매 (조회) 연동 컨트롤러
 * 
 * Updated on : 2014. 1. 20. Updated by : 이승택, nTels.
 */
@LocalSCI
public class PurchaseOrderSearchSCIController implements PurchaseOrderSearchSCI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseOrderSearchSCService orderSearchScService;

	/**
	 * 
	 * <pre>
	 * 구매ID 생성을 위한 (Next) 시퀀스 값 및 현재일시 조회.
	 * </pre>
	 * 
	 * @return (Next) 시퀀스 값 및 현재일시
	 */
	@Override
	public SearchPurchaseSequenceAndDateRes searchPurchaseSequenceAndDate() {

		return this.orderSearchScService.searchPurchaseSequenceAndDate();
	}

	/**
	 * 
	 * <pre>
	 * SKT 후불 결제 제한 조건구분값 조회.
	 * </pre>
	 * 
	 * @param req
	 *            SKT 후불 결제 제한 조건구분값 조회 요청 정보
	 * @return SKT 후불 결제 제한 조건구분값 조회 결과
	 */
	@Override
	public SearchSktPaymentScRes searchSktLimitCondDetail(SearchSktPaymentScReq req) {
		Object condVal = null;

		condVal = this.orderSearchScService.searchSktLimitCondDetail(req);

		// if (StringUtils.isNotBlank(req.getTenantProdGrpCd())) {
		// condVal = this.orderSearchScService.searchSktLimitCondDetail(req);
		// } else {
		// condVal = this.orderSearchScService.searchSktLimitCondOnPaymentDetail(req);
		// }

		return new SearchSktPaymentScRes(condVal);

	}

	/**
	 * 
	 * <pre>
	 * SKT 후불 결제 금액 조회.
	 * </pre>
	 * 
	 * @param req
	 *            SKT 후불 결제 금액 조회 요청 정보
	 * @return SKT 후불 결제 금액 조회 결과
	 */
	@Override
	public SearchSktPaymentScRes searchSktAmountDetail(SearchSktPaymentScReq req) {
		Object condVal = null;

		if (StringUtils.isNotBlank(req.getTenantProdGrpCd()) || StringUtils.isNotBlank(req.getExceptTenantProdGrpCd())) {
			condVal = this.orderSearchScService.searchSktAmountDetail(req);
		} else {
			condVal = this.orderSearchScService.searchSktAmountOnPaymentDetail(req);
		}

		return new SearchSktPaymentScRes(condVal);
	}

	/**
	 * 
	 * <pre>
	 * SKT 후불 선물수신 금액 조회.
	 * </pre>
	 * 
	 * @param req
	 *            SKT 후불 선물수신 금액 조회 요청 정보
	 * @return SKT 후불 선물수신 금액 조회 결과
	 */
	@Override
	public SearchSktPaymentScRes searchSktRecvAmountDetail(SearchSktPaymentScReq req) {
		Object condVal = this.orderSearchScService.searchSktRecvAmountDetail(req);

		return new SearchSktPaymentScRes(condVal);
	}

	/**
	 * 
	 * <pre>
	 * 쇼핑 특가상품 구매된 건수 조회.
	 * </pre>
	 * 
	 * @param req
	 *            쇼핑 특가상품 구매된 건수 조회 요청 정보
	 * @return 쇼핑 특가상품 구매된 건수 조회 결과
	 */
	@Override
	public SearchShoppingSpecialCountScRes searchShoppingSpecialCount(SearchShoppingSpecialCountScReq req) {
		List<Integer> countInfoList = this.orderSearchScService.searchShoppingSpecialCount(req);
		SearchShoppingSpecialCountScRes res = new SearchShoppingSpecialCountScRes();
		res.setDayCount(countInfoList.get(0));
		res.setDayUserCount(countInfoList.get(1));
		res.setMonthCount(countInfoList.get(2));
		res.setMonthUserCount(countInfoList.get(3));

		return res;
	}

	// =====

	/**
	 * 
	 * <pre>
	 * 구매상태 기준의 구매정보 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            조회정보
	 * @return 요청한 구매상태 기준의 구매정보 목록
	 */
	@Override
	public SearchPurchaseListByStatusScRes searchPurchaseListByStatus(SearchPurchaseListByStatusScReq req) {
		this.logger.info("PRCHS,ORDER,SC,SEARCH,START,{},{}", req.getPrchsId(), req.getStatusCd());

		List<PrchsDtlMore> prchsDtlMoreList = this.orderSearchScService
				.searchPurchaseDtlListByStatus(req.getTenantId(), req.getPrchsId(), req.getUseTenantId(),
						req.getUseInsdUsermbrNo(), req.getStatusCd());

		this.logger.info("PRCHS,ORDER,SC,SEARCH,END,{},{},{}", req.getPrchsId(), req.getStatusCd(),
				prchsDtlMoreList.size());
		return new SearchPurchaseListByStatusScRes(prchsDtlMoreList);
	}
}
