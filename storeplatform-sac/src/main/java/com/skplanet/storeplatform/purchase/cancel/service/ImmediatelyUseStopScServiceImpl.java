/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.cancel.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.cancel.vo.PurchaseRefundReason;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.ImmediatelyUseStopScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.purchase.common.service.PurchaseExtraInfoService;
import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 즉시이용정지 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 * Updated on : 2016-01-16 Updated by : eastaim, SK planet
 */
@Service
public class ImmediatelyUseStopScServiceImpl implements ImmediatelyUseStopScService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDAO;

    @Autowired
    private PurchaseExtraInfoService purchaseExtraInfoService;

	/**
	 * 구매이력을 조회한다.
	 * 
	 * @param request
	 *            즉시이용정지 요청
	 * @return PrchsDtl
	 */
	@Override
	public List<PrchsDtl> searchPrchsDtl(ImmediatelyUseStopScReq request) {
		return this.commonDAO.queryForList("UseStop.searchPrchsDtl", request, PrchsDtl.class);
	}

	/**
	 * 즉시이용정지 UPDATE 기능 제공한다.
	 * 
	 * @param request
	 *            즉시이용정지
	 * @return HistoryListScRes
	 */
	@Override
	public ImmediatelyUseStopScRes updateUseStop(ImmediatelyUseStopScReq request) {

		this.logger.debug("ImmediatelyUseStop Sc Request Param : {}" + request);

		request.setPrchsStatusCd(PurchaseCDConstants.PRCHS_STATUS_DRBK); // 구매상태(환불:OR000305)
		request.setPaymentMtdCd(PurchaseCDConstants.PAYMENT_METHOD_FIXRATE_REFUND); // 결제수단코드(정액권 환불:OR000692)
		request.setStatusCd(PurchaseCDConstants.PRCHS_STATUS_COMPT); // 구매상태(완료:OR000301)

		this.commonDAO.update("UseStop.updatePrchsUseContent", request); // 구매 - 정액권으로 이용한 상품 update
		this.commonDAO.update("UseStop.updatePrchsDtlUseContent", request); // 구매상세 - 정액권으로 이용한 상품 update

		this.commonDAO.update("UseStop.updatePrchs", request); // 구매Update
		this.commonDAO.update("UseStop.updatePrchsDtl", request);// 구매상세Update
		this.commonDAO.update("UseStop.updateAutoPrchs", request);// 자동구매Update
		this.commonDAO.insert("UseStop.insertPayment", request);// 결제Insert

        insertRefundReason(request);

		ImmediatelyUseStopScRes response = new ImmediatelyUseStopScRes();
		response.setPrchsId(request.getPrchsId());

		return response;
	}

    /**
     * 환불 사유 저장
     * @param request
     */
    private void insertRefundReason(ImmediatelyUseStopScReq request){
        // 환불 사유 저장
        PurchaseRefundReason purchaseRefundReason = new PurchaseRefundReason();
        purchaseRefundReason.setTenantId(request.getTenantId());
        purchaseRefundReason.setPrchsId(request.getPrchsId());
        purchaseRefundReason.setInfoTypeCd(PurchaseCDConstants.EXTRA_INFO_TYPE_REFUND_REASON);
        purchaseRefundReason.setInfoSeq(1);
        purchaseRefundReason.setRegId(request.getAdminId());
        purchaseRefundReason.setReasonCd(request.getReasonCd());
        purchaseRefundReason.setReasonMsg(request.getReasonMsg());

        purchaseExtraInfoService.createExtraInfo(purchaseRefundReason);
    }
}
