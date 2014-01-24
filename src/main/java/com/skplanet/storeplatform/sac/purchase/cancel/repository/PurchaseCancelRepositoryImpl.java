/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.cancel.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.arms.sci.ArmsSCI;
import com.skplanet.storeplatform.external.client.arms.vo.RemoveLicenseReqDetailEC;
import com.skplanet.storeplatform.external.client.arms.vo.RemoveLicenseReqEC;
import com.skplanet.storeplatform.external.client.arms.vo.RemoveLicenseResEC;
import com.skplanet.storeplatform.purchase.client.cancel.sci.CancelSCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelReqSC;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelResSC;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseDtlHistoryListReqSC;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseDtlHistoryListResSC;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelParamDetail;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseDetail;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseCommonParam;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 구매 취소 repository implements.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
@Component
public class PurchaseCancelRepositoryImpl implements PurchaseCancelRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CancelSCI cancelSCI;

	@Autowired
	private ArmsSCI armsSCI;

	@Override
	public PurchaseCancelParamDetail getPurchaseDtlHistoryList(PurchaseCommonParam purchaseCommonParam,
			PurchaseCancelParamDetail purchaseCancelParamDetail) {

		PurchaseDtlHistoryListReqSC purchaseDtlHistoryListReqSC = new PurchaseDtlHistoryListReqSC();

		purchaseDtlHistoryListReqSC.setTenantId(purchaseCommonParam.getTenantId());
		purchaseDtlHistoryListReqSC.setSystemId(purchaseCommonParam.getSystemId());
		purchaseDtlHistoryListReqSC.setPrchsId(purchaseCancelParamDetail.getPrchsId());
		PurchaseDtlHistoryListResSC purchaseDtlHistoryListResSC = this.cancelSCI
				.getPurchaseDtlHistoryList(purchaseDtlHistoryListReqSC);

		List<PurchaseDetail> purchaseDetailList = new ArrayList<PurchaseDetail>();
		for (PrchsDtl prchsDtl : purchaseDtlHistoryListResSC.getPrchsDtlList()) {
			PurchaseDetail purchaseDetail = new PurchaseDetail();

			purchaseDetail.setTenantId(prchsDtl.getTenantId());
			purchaseDetail.setPrchsId(prchsDtl.getPrchsId());
			purchaseDetail.setPrchsDtlId(prchsDtl.getPrchsDtlId());
			purchaseDetail.setUseTenantId(prchsDtl.getUseTenantId());
			purchaseDetail.setUseInsdUsermbrNo(prchsDtl.getUseInsdUsermbrNo());
			purchaseDetail.setUseInsdDeviceId(prchsDtl.getUseInsdDeviceId());
			purchaseDetail.setTotAmt(prchsDtl.getTotAmt());
			purchaseDetail.setSendInsdUsermbrNo(prchsDtl.getSendInsdUsermbrNo());
			purchaseDetail.setSendInsdDeviceId(prchsDtl.getSendInsdDeviceId());
			purchaseDetail.setRecvDt(prchsDtl.getRecvDt());
			purchaseDetail.setRecvConfPathCd(prchsDtl.getRecvConfPathCd());
			purchaseDetail.setProdId(prchsDtl.getProdId());
			purchaseDetail.setProdAmt(prchsDtl.getProdAmt());
			purchaseDetail.setProdQty(prchsDtl.getProdQty());
			purchaseDetail.setProdGrpCd(prchsDtl.getTenantProdGrpCd());
			purchaseDetail.setStatusCd(prchsDtl.getStatusCd());
			purchaseDetail.setUseStartDt(prchsDtl.getUseStartDt());
			purchaseDetail.setUseExprDt(prchsDtl.getUseExprDt());
			purchaseDetail.setHidingYn(prchsDtl.getHidingYn());
			purchaseDetail.setCancelReqPathCd(prchsDtl.getCancelReqPathCd());
			purchaseDetail.setCancelDt(prchsDtl.getCancelDt());
			purchaseDetail.setCpnPublishCd(prchsDtl.getCpnPublishCd());
			purchaseDetail.setCpnDlvUrl(prchsDtl.getCpnDlvUrl());
			purchaseDetail.setPrchsCaseCd(prchsDtl.getPrchsCaseCd());
			purchaseDetail.setRePrchsPmtYn(prchsDtl.getRePrchsPmtYn());
			purchaseDetail.setDwldStartDt(prchsDtl.getDwldStartDt());
			purchaseDetail.setDwldExprDt(prchsDtl.getDwldExprDt());
			purchaseDetail.setPrchsProdType(prchsDtl.getPrchsProdType());

			purchaseDetailList.add(purchaseDetail);
		}

		purchaseCancelParamDetail.setPurchaseDetailList(purchaseDetailList);

		return purchaseCancelParamDetail;

	}

	@Override
	public PurchaseCancelParamDetail cancelPurchase(PurchaseCommonParam purchaseCommonParam,
			PurchaseCancelParamDetail purchaseCancelParamDetail) {

		PurchaseCancelReqSC purchaseCancelReqSC = new PurchaseCancelReqSC();

		purchaseCancelReqSC.setTenantId(purchaseCommonParam.getTenantId());
		purchaseCancelReqSC.setSystemId(purchaseCommonParam.getSystemId());
		purchaseCancelReqSC.setUserKey(purchaseCommonParam.getUserKey());
		purchaseCancelReqSC.setDeviceKey(purchaseCommonParam.getDeviceKey());

		purchaseCancelReqSC.setPrchsId(purchaseCancelParamDetail.getPrchsId());
		purchaseCancelReqSC.setCancelReqPathCd(purchaseCancelParamDetail.getCancelReqPathCd());
		purchaseCancelReqSC.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
		purchaseCancelReqSC.setPaymentStatusCd(PurchaseConstants.PAYMENT_STATUS_CANCEL);

		PurchaseCancelResSC purchaseCancelResSC = this.cancelSCI.cancelPurchaseSC(purchaseCancelReqSC);

		purchaseCancelParamDetail.setPrchsCancelCnt(purchaseCancelResSC.getPrchsCancelCnt());
		purchaseCancelParamDetail.setPrchsDtlCancelCnt(purchaseCancelResSC.getPrchsDtlCancelCnt());
		purchaseCancelParamDetail.setPrchsSendDtlCancelCnt(purchaseCancelResSC.getPrchsSendDtlCancelCnt());
		purchaseCancelParamDetail.setPaymentCancelCnt(purchaseCancelResSC.getPaymentCancelCnt());

		return purchaseCancelParamDetail;

	}

	@Override
	public PurchaseCancelParamDetail removeLicense(PurchaseCommonParam purchaseCommonParam,
			PurchaseCancelParamDetail purchaseCancelParamDetail) {

		RemoveLicenseReqEC removeLicenseReq = new RemoveLicenseReqEC();
		List<RemoveLicenseReqDetailEC> removeLicenseReqList = new ArrayList<RemoveLicenseReqDetailEC>();

		// for문 돌면서 purchaseCancelParamDetail여기의 appid값 추출하여 removeLicenseReqList여기에 셋팅

		removeLicenseReq.setRemoveLicenseReqList(removeLicenseReqList);

		RemoveLicenseResEC removeLicenseRes = this.armsSCI.removeLicense(removeLicenseReq);

		this.logger.debug("removeLicenseRes : {} ", removeLicenseRes);

		return purchaseCancelParamDetail;

	}

}
