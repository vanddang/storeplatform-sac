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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.arm.sci.ArmSCI;
import com.skplanet.storeplatform.external.client.arm.vo.RemoveLicenseEcReq;
import com.skplanet.storeplatform.external.client.arm.vo.RemoveLicenseEcRes;
import com.skplanet.storeplatform.external.client.message.sci.MessageSCI;
import com.skplanet.storeplatform.external.client.message.vo.AomSendEcReq;
import com.skplanet.storeplatform.external.client.message.vo.AomSendEcRes;
import com.skplanet.storeplatform.external.client.payplanet.sci.CancelSCI;
import com.skplanet.storeplatform.external.client.payplanet.vo.CancelEcReq;
import com.skplanet.storeplatform.external.client.payplanet.vo.CancelEcRes;
import com.skplanet.storeplatform.external.client.tstore.sci.TStorePaymentSCI;
import com.skplanet.storeplatform.external.client.tstore.vo.Pay;
import com.skplanet.storeplatform.external.client.tstore.vo.PayCancelResult;
import com.skplanet.storeplatform.external.client.tstore.vo.PaymentCancel;
import com.skplanet.storeplatform.external.client.tstore.vo.PaymentCancelEcReq;
import com.skplanet.storeplatform.external.client.tstore.vo.PaymentCancelEcRes;
import com.skplanet.storeplatform.external.client.tstore.vo.PaymentCancelResult;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.cancel.sci.PurchaseCancelSCI;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelPaymentDetailScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseCancelScRes;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScReq;
import com.skplanet.storeplatform.purchase.client.cancel.vo.PurchaseScRes;
import com.skplanet.storeplatform.purchase.client.common.vo.Payment;
import com.skplanet.storeplatform.purchase.client.common.vo.Prchs;
import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdatePurchaseCountSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UpdatePurchaseCountSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PaymentSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PrchsDtlSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PrchsSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelDetailSacParam;
import com.skplanet.storeplatform.sac.purchase.cancel.vo.PurchaseCancelSacParam;
import com.skplanet.storeplatform.sac.purchase.common.util.PayPlanetUtils;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

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
	private MessageSCI messageSCI;

	@Autowired
	private ArmSCI armSCI;

	@Autowired
	private CancelSCI cancelSCI;

	@Autowired
	private TStorePaymentSCI tStorePaymentSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private UpdatePurchaseCountSCI updatePurchaseCountSCI;

	@Override
	public PurchaseCancelDetailSacParam setPurchaseDetailInfo(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PurchaseScReq purchaseScReq = new PurchaseScReq();

		// 인입 된 사람의 정보 넣어준다.
		purchaseScReq.setTenantId(purchaseCancelSacParam.getTenantId());
		purchaseScReq.setSystemId(purchaseCancelSacParam.getSystemId());

		// 취소 할 구매ID를 넣어준다.
		purchaseScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());

		PurchaseScRes purchaseScRes = this.purchaseCancelSCI.getPurchase(purchaseScReq);
		if (purchaseScRes == null || purchaseScRes.getPrchs() == null
				|| StringUtils.isEmpty(purchaseScRes.getPrchs().getPrchsId())
				|| purchaseScRes.getPrchsDtlList() == null || purchaseScRes.getPrchsDtlList().size() < 1) {
			// 구매 정보가 존재하지 않을 경우 취소 불가.
			throw new StorePlatformException("SAC_PUR_8100");
		}

		// 구매 정보 가져와서 셋팅.
		// 구매 정보.
		purchaseCancelDetailSacParam.setPrchsSacParam(this.convertResForPrchsSacParam(purchaseScRes.getPrchs()));

		// 구매 상세 정보.
		List<PrchsDtlSacParam> prchsDtlSacParamList = new ArrayList<PrchsDtlSacParam>();
		for (PrchsDtl prchsDtl : purchaseScRes.getPrchsDtlList()) {
			PrchsDtlSacParam prchsDtlSacParam = this.converResForPrchsDtlSacParam(prchsDtl);
			prchsDtlSacParamList.add(prchsDtlSacParam);
		}
		purchaseCancelDetailSacParam.setPrchsDtlSacParamList(prchsDtlSacParamList);

		// 결제 상세 정보.
		List<PaymentSacParam> paymentSacParamList = new ArrayList<PaymentSacParam>();
		if (purchaseScRes.getPaymentList() != null) {
			for (Payment payment : purchaseScRes.getPaymentList()) {
				PaymentSacParam paymentSacParam = this.convertResForPaymentSacParam(payment);
				paymentSacParamList.add(paymentSacParam);
			}
		}
		purchaseCancelDetailSacParam.setPaymentSacParamList(paymentSacParamList);

		return purchaseCancelDetailSacParam;

	}

	@Override
	public String getDeviceId(String userKey, String deviceKey) {

		SearchDeviceIdSacReq searchDeviceIdSacReq = new SearchDeviceIdSacReq();
		searchDeviceIdSacReq.setUserKey(userKey);
		searchDeviceIdSacReq.setDeviceKey(deviceKey);
		SearchDeviceIdSacRes searchDeviceIdSacRes = this.deviceSCI.searchDeviceId(searchDeviceIdSacReq);
		if (searchDeviceIdSacRes == null || StringUtils.isEmpty(searchDeviceIdSacRes.getDeviceId())) {
			throw new StorePlatformException("SAC_PUR_4101");
		}

		return searchDeviceIdSacRes.getDeviceId();

	}

	@Override
	public CancelEcRes cancelPaymentToPayPlanet(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PaymentSacParam paymentSacParam = purchaseCancelDetailSacParam.getPaymentSacParamList().get(0);

		CancelEcReq cancelEcReq = new CancelEcReq();
		// TODO : CSWOO8101 AUTHKEY, MID SETTING!
		cancelEcReq.setToken(PayPlanetUtils.makeToken("6b6fa0a99e621f5b0fc9a77622c42b67e7a3317c",
				paymentSacParam.getPrchsId(), String.valueOf(paymentSacParam.getTotAmt()), "SKTstore01"));
		cancelEcReq.setTid(paymentSacParam.getTid());

		return this.cancelSCI.cancelPayment(cancelEcReq);

	}

	@Override
	public List<PayCancelResult> cancelPaymentToTStore(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PrchsSacParam prchsSacParam = purchaseCancelDetailSacParam.getPrchsSacParam();

		PaymentCancelEcReq paymentCancelEcReq = new PaymentCancelEcReq();
		List<PaymentCancel> cancelList = new ArrayList<PaymentCancel>();
		PaymentCancel paymentCancel = new PaymentCancel();
		paymentCancel.setPrchsId(prchsSacParam.getPrchsId());
		paymentCancel.setChannel(PurchaseConstants.TSTORE_CASH_SVC_CHANNEL_SAC); // SAC 고정값.
		paymentCancel.setUserKey(prchsSacParam.getInsdUsermbrNo());
		paymentCancel.setDeviceKey(prchsSacParam.getInsdDeviceId());
		paymentCancel.setMdn(prchsSacParam.getDeviceId());

		List<Pay> payList = new ArrayList<Pay>();
		for (PaymentSacParam paymentSacParam : purchaseCancelDetailSacParam.getPaymentSacParamList()) {
			Pay pay = new Pay();
			pay.setPayCls(paymentSacParam.getPaymentMtdCd());
			pay.setTradeId(paymentSacParam.getTid());
			pay.setOrderNo(paymentSacParam.getMoid());
			pay.setAmt(String.valueOf(paymentSacParam.getPaymentAmt()));

			pay.setApplyNum(paymentSacParam.getApprNo());
			pay.setApplyDate(paymentSacParam.getPaymentDt().substring(0, 8));
			pay.setApplyTime(paymentSacParam.getPaymentDt().substring(8));

			payList.add(pay);
		}

		paymentCancel.setPayList(payList);

		cancelList.add(paymentCancel);
		paymentCancelEcReq.setCancelList(cancelList);

		PaymentCancelEcRes paymentCancelEcRes = this.tStorePaymentSCI.cancelPayment(paymentCancelEcReq);

		PaymentCancelResult paymentCancelResult = paymentCancelEcRes.getCancelList().get(0);

		return paymentCancelResult.getPayCancelList();

	}

	@Override
	public PurchaseCancelDetailSacParam updatePurchaseCancel(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		PurchaseCancelScReq purchaseCancelScReq = new PurchaseCancelScReq();
		// 결제 취소 정보를 넣어준다.
		List<PurchaseCancelPaymentDetailScReq> purchaseCancelPaymentDetailScReqList = new ArrayList<PurchaseCancelPaymentDetailScReq>();
		for (PaymentSacParam paymentSacParam : purchaseCancelDetailSacParam.getPaymentSacParamList()) {
			PurchaseCancelPaymentDetailScReq purchaseCancelPaymentDetailScReq = new PurchaseCancelPaymentDetailScReq();
			purchaseCancelPaymentDetailScReq.setTenantId(purchaseCancelSacParam.getTenantId());
			purchaseCancelPaymentDetailScReq.setSystemId(purchaseCancelSacParam.getSystemId());
			purchaseCancelPaymentDetailScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
			purchaseCancelPaymentDetailScReq.setPaymentDtlId(paymentSacParam.getPaymentDtlId());
			if (purchaseCancelDetailSacParam.getPayPlanetCancelEcRes() != null
					&& StringUtils.equals(paymentSacParam.getTid(), purchaseCancelDetailSacParam
							.getPayPlanetCancelEcRes().getTid())) {
				purchaseCancelPaymentDetailScReq.setPaymentStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
			} else if (purchaseCancelDetailSacParam.gettStorePayCancelResultList() != null
					&& purchaseCancelDetailSacParam.gettStorePayCancelResultList().size() > 0) {
				boolean resultFlag = false;
				for (PayCancelResult payCancelResult : purchaseCancelDetailSacParam.gettStorePayCancelResultList()) {
					if (StringUtils.equals(payCancelResult.getPayCls(), paymentSacParam.getPaymentMtdCd())) {
						purchaseCancelPaymentDetailScReq.setPaymentStatusCd(payCancelResult.getPayCancelResultCd());
						resultFlag = true;
					}
				}
				if (!resultFlag) {
					throw new StorePlatformException("SAC_PUR_8403");
				}
			}

			purchaseCancelPaymentDetailScReqList.add(purchaseCancelPaymentDetailScReq);
		}

		// 인입 된 사람의 정보를 넣어준다.
		purchaseCancelScReq.setTenantId(purchaseCancelSacParam.getTenantId());
		purchaseCancelScReq.setSystemId(purchaseCancelSacParam.getSystemId());
		// 구매 취소 정보를 넣어준다.
		purchaseCancelScReq.setPrchsId(purchaseCancelDetailSacParam.getPrchsId());
		purchaseCancelScReq.setCancelReqPathCd(purchaseCancelSacParam.getCancelReqPathCd());
		purchaseCancelScReq.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
		purchaseCancelScReq.setPurchaseCancelPaymentDetailScReqList(purchaseCancelPaymentDetailScReqList);

		PurchaseCancelScRes purchaseCancelScRes = this.purchaseCancelSCI.updatePurchaseCancel(purchaseCancelScReq);

		purchaseCancelDetailSacParam.setPaymentCancelCnt(purchaseCancelScRes.getPaymentCancelCnt());
		purchaseCancelDetailSacParam.setPrchsDtlCancelCnt(purchaseCancelScRes.getPrchsDtlCancelCnt());
		purchaseCancelDetailSacParam.setPrchsCancelCnt(purchaseCancelScRes.getPrchsCancelCnt());

		return purchaseCancelDetailSacParam;

	}

	@Override
	public void updatePurchaseCount(PurchaseCancelSacParam purchaseCancelSacParam,
			PurchaseCancelDetailSacParam purchaseCancelDetailSacParam) {

		List<UpdatePurchaseCountSacReq> updatePurchaseCountSacReqList = new ArrayList<UpdatePurchaseCountSacReq>();

		for (PrchsDtlSacParam prchsDtlSacParam : purchaseCancelDetailSacParam.getPrchsDtlSacParamList()) {
			UpdatePurchaseCountSacReq updatePurchaseCountSacReq = new UpdatePurchaseCountSacReq();
			updatePurchaseCountSacReq.setTenantId(prchsDtlSacParam.getUseTenantId());
			updatePurchaseCountSacReq.setProductId(prchsDtlSacParam.getProdId());
			updatePurchaseCountSacReq.setPurchaseCount(-prchsDtlSacParam.getProdQty());

			updatePurchaseCountSacReqList.add(updatePurchaseCountSacReq);
		}

		this.updatePurchaseCountSCI.updatePurchaseCount(updatePurchaseCountSacReqList);

	}

	@Override
	public String aomPush(String deviceId, String appId) {

		AomSendEcReq aomSendEcReq = new AomSendEcReq();

		aomSendEcReq.setDeviceId(deviceId);
		aomSendEcReq.setClientType("AM000102");
		aomSendEcReq.setSvcType("AM000205");
		aomSendEcReq.setProdType("AM000301");
		aomSendEcReq.setMsg("KR_ARM_TS/A4/" + deviceId + "/" + appId);
		aomSendEcReq.setAomStat("AM000401");

		AomSendEcRes aomSendEcRes = this.messageSCI.aomSend(aomSendEcReq);

		return aomSendEcRes.getResultStatus();

	}

	@Override
	public String armRemoveLicense(String deviceId, String appId) {

		RemoveLicenseEcReq removeLicenseEcReq = new RemoveLicenseEcReq();

		removeLicenseEcReq.setAppId(appId);
		removeLicenseEcReq.setMdn(deviceId);
		RemoveLicenseEcRes removeLicenseEcRes = this.armSCI.removeLicense(removeLicenseEcReq);

		return removeLicenseEcRes.getResultCd();

	}

	/**
	 * <pre>
	 * SC Prchs VO에서 SAC PrchsSacParam으로 변환.
	 * </pre>
	 * 
	 * @param prchs
	 *            prchs
	 * @return PrchsSacParam
	 */
	private PrchsSacParam convertResForPrchsSacParam(Prchs prchs) {

		PrchsSacParam prchsSacParam = new PrchsSacParam();

		prchsSacParam.setTenantId(prchs.getTenantId());
		prchsSacParam.setPrchsId(prchs.getPrchsId());
		prchsSacParam.setInsdUsermbrNo(prchs.getInsdUsermbrNo());
		prchsSacParam.setInsdDeviceId(prchs.getInsdDeviceId());
		prchsSacParam.setPrchsDt(prchs.getPrchsDt());
		prchsSacParam.setStatusCd(prchs.getStatusCd());
		prchsSacParam.setTotAmt(prchs.getTotAmt());
		prchsSacParam.setCancelReqPathCd(prchs.getCancelReqPathCd());
		prchsSacParam.setCancelDt(prchs.getCancelDt());

		return prchsSacParam;

	}

	/**
	 * <pre>
	 * SC PrchsDtl VO에서 SAC PrchsDtlSacParam으로 변환.
	 * </pre>
	 * 
	 * @param prchsDtl
	 *            prchsDtl
	 * @return PrchsDtlSacParam
	 */
	private PrchsDtlSacParam converResForPrchsDtlSacParam(PrchsDtl prchsDtl) {

		PrchsDtlSacParam prchsDtlSacParam = new PrchsDtlSacParam();

		prchsDtlSacParam.setTenantId(prchsDtl.getTenantId());
		prchsDtlSacParam.setPrchsId(prchsDtl.getPrchsId());
		prchsDtlSacParam.setPrchsDtlId(prchsDtl.getPrchsDtlId());
		prchsDtlSacParam.setUseTenantId(prchsDtl.getUseTenantId());
		prchsDtlSacParam.setUseInsdUsermbrNo(prchsDtl.getUseInsdUsermbrNo());
		prchsDtlSacParam.setUseInsdDeviceId(prchsDtl.getUseInsdDeviceId());
		prchsDtlSacParam.setPrchsDt(prchsDtl.getPrchsDt());
		prchsDtlSacParam.setTotAmt(prchsDtl.getTotAmt());
		prchsDtlSacParam.setSendInsdUsermbrNo(prchsDtl.getSendInsdUsermbrNo());
		prchsDtlSacParam.setSendInsdDeviceId(prchsDtl.getSendInsdDeviceId());
		prchsDtlSacParam.setRecvDt(prchsDtl.getRecvDt());
		prchsDtlSacParam.setRecvConfPathCd(prchsDtl.getRecvConfPathCd());
		prchsDtlSacParam.setProdId(prchsDtl.getProdId());
		prchsDtlSacParam.setProdAmt(prchsDtl.getProdAmt());
		prchsDtlSacParam.setProdQty(prchsDtl.getProdQty());
		prchsDtlSacParam.setTenantProdGrpCd(prchsDtl.getTenantProdGrpCd());
		prchsDtlSacParam.setStatusCd(prchsDtl.getStatusCd());
		prchsDtlSacParam.setUseStartDt(prchsDtl.getUseStartDt());
		prchsDtlSacParam.setUseExprDt(prchsDtl.getUseExprDt());
		prchsDtlSacParam.setCancelReqPathCd(prchsDtl.getCancelReqPathCd());
		prchsDtlSacParam.setCancelDt(prchsDtl.getCancelDt());
		prchsDtlSacParam.setCpnPublishCd(prchsDtl.getCpnPublishCd());
		prchsDtlSacParam.setCpnDlvUrl(prchsDtl.getCpnDlvUrl());
		prchsDtlSacParam.setCpnAddInfo(prchsDtl.getCpnAddInfo());
		prchsDtlSacParam.setCpnBizOrderNo(prchsDtl.getCpnBizOrderNo());
		prchsDtlSacParam.setCpnBizProdSeq(prchsDtl.getCpnBizProdSeq());
		prchsDtlSacParam.setPrchsCaseCd(prchsDtl.getPrchsCaseCd());
		prchsDtlSacParam.setPrchsProdType(prchsDtl.getPrchsProdType());
		prchsDtlSacParam.setUseFixrateProdId(prchsDtl.getUseFixrateProdId());
		prchsDtlSacParam.setPrchsResvInfo(prchsDtl.getPrchsResvInfo());

		return prchsDtlSacParam;

	}

	/**
	 * <pre>
	 * SC Payment VO에서 SAC PaymentSacParam으로 변환.
	 * </pre>
	 * 
	 * @param payment
	 *            payment
	 * @return PaymentSacParam
	 */
	private PaymentSacParam convertResForPaymentSacParam(Payment payment) {

		PaymentSacParam paymentSacParam = new PaymentSacParam();

		paymentSacParam.setTenantId(payment.getTenantId());
		paymentSacParam.setPrchsId(payment.getPrchsId());
		paymentSacParam.setPaymentDtlId(payment.getPaymentDtlId());
		paymentSacParam.setInsdUsermbrNo(payment.getInsdUsermbrNo());
		paymentSacParam.setInsdDeviceId(payment.getInsdDeviceId());
		paymentSacParam.setPrchsDt(payment.getPrchsDt());
		paymentSacParam.setTotAmt(payment.getTotAmt());
		paymentSacParam.setPaymentMtdCd(payment.getPaymentMtdCd());
		paymentSacParam.setStatusCd(payment.getStatusCd());
		paymentSacParam.setPaymentDt(payment.getPaymentDt());
		paymentSacParam.setPaymentAmt(payment.getPaymentAmt());
		paymentSacParam.setPaymentCancelDt(payment.getPaymentCancelDt());
		paymentSacParam.setTid(payment.getTid());
		paymentSacParam.setResvCol01(payment.getResvCol01());
		paymentSacParam.setResvCol02(payment.getResvCol02());
		paymentSacParam.setResvCol03(payment.getResvCol03());
		paymentSacParam.setResvCol04(payment.getResvCol04());
		paymentSacParam.setResvCol05(payment.getResvCol05());
		paymentSacParam.setApprNo(payment.getApprNo());
		paymentSacParam.setBillKey(payment.getBillKey());
		paymentSacParam.setCpnId(payment.getCpnId());
		paymentSacParam.setMoid(payment.getMoid());

		return paymentSacParam;

	}

}
