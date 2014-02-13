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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.arms.sci.ArmsSCI;
import com.skplanet.storeplatform.purchase.client.cancel.sci.PurchaseCancelSCI;

/**
 * 구매 취소 repository implements.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
@Component
public class PurchaseCancelRepositoryImpl implements PurchaseCancelRepository {

	@Autowired
	private PurchaseCancelSCI purchaseCancelSCI;

	@Autowired
	private ArmsSCI armsSCI;

	/*
	 * public List<PurchaseDetail> getPurchaseDtlList(PurchaseCommonSacParam purchaseCommonSacParam,
	 * PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {
	 * 
	 * PurchaseScReq purchaseScReq = new PurchaseScReq();
	 * 
	 * purchaseScReq.setTenantId(purchaseCommonSacParam.getTenantId());
	 * purchaseScReq.setSystemId(purchaseCommonSacParam.getSystemId());
	 * purchaseScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
	 * 
	 * PurchaseScRes purchaseScRes = this.purchaseCancelSCI.getPurchase(purchaseScReq);
	 * 
	 * List<PurchaseDetail> purchaseDetailList = new ArrayList<PurchaseDetail>(); for (PrchsDtl prchsDtl :
	 * purchaseDtlListScRes.getPrchsDtlList()) {
	 * 
	 * PurchaseDetail purchaseDetail = new PurchaseDetail();
	 * 
	 * purchaseDetail.setTenantId(prchsDtl.getTenantId()); purchaseDetail.setPrchsDtlId(prchsDtl.getPrchsDtlId());
	 * purchaseDetail.setUseTenantId(prchsDtl.getUseTenantId());
	 * purchaseDetail.setUseInsdUsermbrNo(prchsDtl.getUseInsdUsermbrNo());
	 * purchaseDetail.setUseInsdDeviceId(prchsDtl.getUseInsdDeviceId());
	 * purchaseDetail.setRecvTenantId(prchsDtl.getRecvTenantId());
	 * purchaseDetail.setRecvInsdUsermbrNo(prchsDtl.getRecvInsdUsermbrNo());
	 * purchaseDetail.setRecvInsdDeviceId(prchsDtl.getRecvInsdDeviceId());
	 * purchaseDetail.setPrchsDt(prchsDtl.getPrchsDt()); purchaseDetail.setTotAmt(prchsDtl.getTotAmt());
	 * purchaseDetail.setPrchsReqPathCd(prchsDtl.getPrchsReqPathCd());
	 * purchaseDetail.setClientIp(prchsDtl.getClientIp());
	 * purchaseDetail.setSendInsdUsermbrNo(prchsDtl.getSendInsdUsermbrNo());
	 * purchaseDetail.setSendInsdDeviceId(prchsDtl.getSendInsdDeviceId());
	 * purchaseDetail.setRecvDt(prchsDtl.getRecvDt()); purchaseDetail.setRecvConfPathCd(prchsDtl.getRecvConfPathCd());
	 * purchaseDetail.setProdId(prchsDtl.getProdId()); purchaseDetail.setProdAmt(prchsDtl.getProdAmt());
	 * purchaseDetail.setProdQty(prchsDtl.getProdQty());
	 * purchaseDetail.setTenantProdGrpCd(prchsDtl.getTenantProdGrpCd());
	 * purchaseDetail.setStatusCd(prchsDtl.getStatusCd()); purchaseDetail.setUseStartDt(prchsDtl.getUseStartDt());
	 * purchaseDetail.setUseExprDt(prchsDtl.getUseExprDt()); purchaseDetail.setHidingYn(prchsDtl.getHidingYn());
	 * purchaseDetail.setCancelReqPathCd(prchsDtl.getCancelReqPathCd());
	 * purchaseDetail.setCancelDt(prchsDtl.getCancelDt()); purchaseDetail.setCpnPublishCd(prchsDtl.getCpnPublishCd());
	 * purchaseDetail.setCpnDlvUrl(prchsDtl.getCpnDlvUrl()); purchaseDetail.setPrchsCaseCd(prchsDtl.getPrchsCaseCd());
	 * purchaseDetail.setRePrchsPmtYn(prchsDtl.getRePrchsPmtYn());
	 * purchaseDetail.setDwldStartDt(prchsDtl.getDwldStartDt()); purchaseDetail.setDwldExprDt(prchsDtl.getDwldExprDt());
	 * purchaseDetail.setRegId(prchsDtl.getRegId()); purchaseDetail.setRegDt(prchsDtl.getRegDt());
	 * purchaseDetail.setUpdId(prchsDtl.getUpdId()); purchaseDetail.setUpdDt(prchsDtl.getUpdDt());
	 * purchaseDetail.setResvCol01(prchsDtl.getResvCol01()); purchaseDetail.setResvCol02(prchsDtl.getResvCol02());
	 * purchaseDetail.setResvCol03(prchsDtl.getResvCol03()); purchaseDetail.setResvCol04(prchsDtl.getResvCol04());
	 * purchaseDetail.setResvCol05(prchsDtl.getResvCol05());
	 * purchaseDetail.setPrchsProdType(prchsDtl.getPrchsProdType());
	 * purchaseDetail.setUseFixrateProdId(prchsDtl.getUseFixrateProdId());
	 * 
	 * purchaseDetailList.add(purchaseDetail);
	 * 
	 * }
	 * 
	 * return purchaseDetailList;
	 * 
	 * }
	 * 
	 * public PurchaseCancelDetailSacParam updatePurchaseCancel(PurchaseCommonSacParam purchaseCommonSacParam,
	 * PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {
	 * 
	 * PurchaseCancelScReq purchaseCancelScReq = new PurchaseCancelScReq();
	 * 
	 * // 인입 된 사람의 정보를 넣어준다. purchaseCancelScReq.setTenantId(purchaseCommonSacParam.getTenantId());
	 * purchaseCancelScReq.setSystemId(purchaseCommonSacParam.getSystemId()); // 구매 취소 정보를 넣어준다.
	 * purchaseCancelScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
	 * purchaseCancelScReq.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
	 * purchaseCancelScReq.setPaymentStatusCd(purchaseCancelDetailSacParam.getPaymentStatusCd());
	 * purchaseCancelScReq.setCancelReqPathCd(purchaseCancelDetailSacParam.getCancelReqPathCd());
	 * 
	 * return purchaseCancelDetailSacParam;
	 * 
	 * }
	 */

	/*
	 * @Override public PurchaseCancelParamDetail cancelPurchase(PurchaseCommonSacParam purchaseCommonParam,
	 * PurchaseCancelParamDetail purchaseCancelParamDetail) {
	 * 
	 * PurchaseCancelReqSC purchaseCancelReqSC = new PurchaseCancelReqSC();
	 * 
	 * purchaseCancelReqSC.setTenantId(purchaseCommonParam.getTenantId());
	 * purchaseCancelReqSC.setSystemId(purchaseCommonParam.getSystemId());
	 * purchaseCancelReqSC.setUserKey(purchaseCommonParam.getUserKey());
	 * purchaseCancelReqSC.setDeviceKey(purchaseCommonParam.getDeviceKey());
	 * 
	 * purchaseCancelReqSC.setPrchsId(purchaseCancelParamDetail.getPrchsId());
	 * purchaseCancelReqSC.setCancelReqPathCd(purchaseCancelParamDetail.getCancelReqPathCd());
	 * purchaseCancelReqSC.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
	 * purchaseCancelReqSC.setPaymentStatusCd(PurchaseConstants.PAYMENT_STATUS_CANCEL);
	 * 
	 * PurchaseCancelResSC purchaseCancelResSC = this.cancelSCI.cancelPurchaseSC(purchaseCancelReqSC);
	 * 
	 * purchaseCancelParamDetail.setPrchsCancelCnt(purchaseCancelResSC.getPrchsCancelCnt());
	 * purchaseCancelParamDetail.setPrchsDtlCancelCnt(purchaseCancelResSC.getPrchsDtlCancelCnt());
	 * purchaseCancelParamDetail.setPrchsSendDtlCancelCnt(purchaseCancelResSC.getPrchsSendDtlCancelCnt());
	 * purchaseCancelParamDetail.setPaymentCancelCnt(purchaseCancelResSC.getPaymentCancelCnt());
	 * 
	 * return purchaseCancelParamDetail;
	 * 
	 * }
	 * 
	 * @Override public PurchaseCancelParamDetail removeLicense(PurchaseCommonSacParam purchaseCommonParam,
	 * PurchaseCancelParamDetail purchaseCancelParamDetail) {
	 * 
	 * RemoveLicenseReqEC removeLicenseReq = new RemoveLicenseReqEC(); List<RemoveLicenseReqDetailEC>
	 * removeLicenseReqList = new ArrayList<RemoveLicenseReqDetailEC>();
	 * 
	 * // for문 돌면서 purchaseCancelParamDetail여기의 appid값 추출하여 removeLicenseReqList여기에 셋팅
	 * 
	 * removeLicenseReq.setRemoveLicenseReqList(removeLicenseReqList);
	 * 
	 * RemoveLicenseResEC removeLicenseRes = this.armsSCI.removeLicense(removeLicenseReq);
	 * 
	 * this.logger.debug("removeLicenseRes : {} ", removeLicenseRes);
	 * 
	 * return purchaseCancelParamDetail;
	 * 
	 * }
	 */

}
