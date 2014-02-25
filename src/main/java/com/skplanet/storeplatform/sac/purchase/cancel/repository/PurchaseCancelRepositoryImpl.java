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

import com.skplanet.storeplatform.external.client.arm.sci.ArmSCI;
import com.skplanet.storeplatform.external.client.message.sci.MessageSCI;
import com.skplanet.storeplatform.external.client.message.vo.AomSendEcReq;
import com.skplanet.storeplatform.external.client.message.vo.AomSendEcRes;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PrchsProdDtl;

/**
 * 구매 취소 repository implements.
 * 
 * Updated on : 2014. 1. 16. Updated by : nTels_cswoo81, nTels.
 */
@Component
public class PurchaseCancelRepositoryImpl implements PurchaseCancelRepository {

	@Autowired
	private MessageSCI messageSCI;

	@Autowired
	private ArmSCI armSCI;

	@Override
	public String aomPush(PrchsProdDtl prchsProdDtl) {

		AomSendEcReq aomSendEcReq = new AomSendEcReq();

		aomSendEcReq.setDeviceId(prchsProdDtl.getDeviceId());
		aomSendEcReq.setClientType("AM000102");
		aomSendEcReq.setSvcType("AM000205");
		aomSendEcReq.setProdType("AM000301");
		aomSendEcReq.setMsg("KR_ARM_TS/A4/" + prchsProdDtl.getDeviceId() + "/" + prchsProdDtl.getDeviceId());
		aomSendEcReq.setAomStat("AM000401");

		AomSendEcRes aomSendEcRes = this.messageSCI.aomSend(aomSendEcReq);

		return aomSendEcRes.getResultStatus();

	}

	// aom message 발송.
	/*
	 * AomSendEcReq aomSendEcReq = new AomSendEcReq(); // this.aomModel.setMdn(purchaseCancel.getHandsetNo());
	 * aomSendEcReq.setDeviceId(purchaseCancelDetailSacParam.getPrchsDtl().get(0).getUseInsdDeviceId()); //
	 * this.aomModel.setClientType("AM000102"); // 클라이언트 구분 ARM Client aomSendEcReq.setClientType(""); //
	 * this.aomModel.setServiceType("AM000205"); // 서비스 구분 AM000205(ARM초기화) aomSendEcReq.setSvcType(""); //
	 * this.aomModel.setProdType("AM000301"); // 상품 구분 AM000301(어플) aomSendEcReq.setProdType(""); // String aomMessage =
	 * "KR_ARM_TS/A4/" + purchaseCancel.getHandsetNo() + "/" + appId; aomSendEcReq.setMsg(""); // if (tokenCnt > 0) {
	 * this.aomModel.setStat("AM000401"); or else { this.aomModel.setStat("AM000405"); // AM000401
	 * aomSendEcReq.setAomStat("");
	 */

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
