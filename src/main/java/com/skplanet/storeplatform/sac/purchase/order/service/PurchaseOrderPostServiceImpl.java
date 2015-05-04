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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.sap.sci.SapPurchaseSCI;
import com.skplanet.storeplatform.external.client.sap.vo.SendPurchaseNotiEcReq;
import com.skplanet.storeplatform.external.client.sap.vo.SendPurchaseNotiPaymentInfoEc;
import com.skplanet.storeplatform.external.client.sap.vo.SendPurchaseNotiProductInfoEc;
import com.skplanet.storeplatform.external.client.sap.vo.SendPurchaseNotiSellerInfoEc;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.common.sci.PurchaseCommonSCI;
import com.skplanet.storeplatform.purchase.client.common.vo.PurchaseCommonCode;
import com.skplanet.storeplatform.purchase.client.common.vo.SapNoti;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.CreateSapNotiScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.NotifyPaymentSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.PaymentInfo;
import com.skplanet.storeplatform.sac.purchase.common.service.PayPlanetShopService;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.interworking.service.InterworkingSacService;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.Interworking;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.InterworkingSacReq;
import com.skplanet.storeplatform.sac.purchase.order.PaymethodUtil;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseDisplayRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;

/**
 * 
 * 구매 후처리 서비스
 * 
 * Updated on : 2014. 5. 28. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderPostServiceImpl implements PurchaseOrderPostService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchaseOrderSCI purchaseOrderSCI;
	@Autowired
	private SapPurchaseSCI sapPurchaseSCI;
	@Autowired
	private PurchaseCommonSCI purchaseCommonSCI;

	@Autowired
	private PayPlanetShopService payPlanetShopService;
	@Autowired
	private PurchaseOrderTstoreService purchaseOrderTstoreService;
	@Autowired
	private PurchaseOrderMakeDataService purchaseOrderMakeDataService;
	@Autowired
	private InterworkingSacService interworkingSacService;

	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;
	@Autowired
	private PurchaseDisplayRepository purchaseDisplayRepository;

	/**
	 * 
	 * <pre>
	 * 구매 후처리( 인터파크/씨네21, Tstore Noti).
	 * </pre>
	 * 
	 * @param prchsDtlMoreList
	 *            구매정보 목록
	 * @param notifyPaymentReq
	 *            결제처리결과 Noti 정보
	 */
	@Override
	public void postPurchase(List<PrchsDtlMore> prchsDtlMoreList, NotifyPaymentSacReq notifyPaymentReq) {
		this.logger.info("PRCHS,ORDER,SAC,POST,START,{}", prchsDtlMoreList.get(0).getPrchsId());

		// ------------------------------------------------------------------------------------
		// 인터파크 / 씨네21

		this.createInterworking(prchsDtlMoreList);

		// ------------------------------------------------------------------------------------
		// 결제완료 Noti

		this.sendPurchaseNoti(prchsDtlMoreList, notifyPaymentReq);

		this.logger.info("PRCHS,ORDER,SAC,POST,END,{}", prchsDtlMoreList.get(0).getPrchsId());
	}

	/**
	 * 
	 * <pre>
	 * 인터파크/씨네21 전송 처리.
	 * </pre>
	 * 
	 * @param prchsDtlMoreList
	 */
	private void createInterworking(List<PrchsDtlMore> prchsDtlMoreList) {
		List<Interworking> interworkingList = new ArrayList<Interworking>();
		Interworking interworking = null;
		Map<String, String> reservedDataMap = null;

		for (PrchsDtlMore prchsDtlMore : prchsDtlMoreList) {
			reservedDataMap = this.purchaseOrderMakeDataService.parseReservedData(prchsDtlMore.getPrchsResvDesc());

			interworking = new Interworking();
			interworking.setProdId(prchsDtlMore.getProdId());
			interworking.setProdAmt(prchsDtlMore.getProdAmt());
			interworking.setSellermbrNo(reservedDataMap.get("sellerMbrNo"));
			interworking.setMallCd(reservedDataMap.get("mallCd"));
			interworking.setCompCid(reservedDataMap.get("outsdContentsId"));

			interworkingList.add(interworking);
		}

		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);
		InterworkingSacReq interworkingSacReq = new InterworkingSacReq();
		interworkingSacReq.setTenantId(prchsDtlMore.getTenantId());
		interworkingSacReq.setSystemId(prchsDtlMore.getSystemId());
		interworkingSacReq.setUserKey(prchsDtlMore.getUseInsdUsermbrNo());
		interworkingSacReq.setDeviceKey(prchsDtlMore.getUseInsdDeviceId());
		interworkingSacReq.setPrchsId(prchsDtlMore.getPrchsId());
		interworkingSacReq.setPrchsDt(prchsDtlMore.getPrchsDt());
		interworkingSacReq.setInterworkingList(interworkingList);

		try {
			this.interworkingSacService.createInterworking(interworkingSacReq);
		} catch (Exception e) {
			// 예외 throw 차단
			this.logger.info("PRCHS,ORDER,SAC,POST,INTER,ERROR,{},{}", prchsDtlMoreList.get(0).getPrchsId(),
					e.getMessage());
		}
	}

	/*
	 * 
	 * <pre> 결제완료Noti. </pre>
	 * 
	 * @param prchsDtlMoreList 구매정보 목록
	 * 
	 * @param notifyPaymentReq 결제처리결과 Noti 정보
	 */
	private void sendPurchaseNoti(List<PrchsDtlMore> prchsDtlMoreList, NotifyPaymentSacReq notifyPaymentReq) {
		Map<String, String> reservedDataMap = null;

		PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);

		if (StringUtils.equals(prchsDtlMore.getTenantId(), PurchaseConstants.TENANT_ID_TSTORE)) { // T store

			// PayPlanet 결제 건은 T store 측으로 구매완료 Noti: 이메일 발송, SMS / MMS 등등 처리
			// T store 결제 건은 결제처리결과 알림 API 응답 항목에 추가
			if (this.payPlanetShopService.startsWithPayPlanetMID(notifyPaymentReq.getPaymentInfoList().get(0).getTid())) {

				String notiType = null;
				String prchsResvData = prchsDtlMore.getPrchsResvDesc();
				int pos = StringUtils.indexOf(prchsResvData, "tstoreNotiPublishType=");
				if (pos >= 0) {
					int endPos = StringUtils.indexOf(prchsResvData, ";", pos + 22);

					if (endPos >= 0) {
						notiType = StringUtils.substring(prchsResvData, pos + 22, endPos);
					} else {
						notiType = StringUtils.substring(prchsResvData, pos + 22);
					}
				}

				String userKey = null;
				String deviceKey = null;

				boolean bGift = StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD);
				if (bGift) {
					userKey = prchsDtlMore.getSendInsdUsermbrNo();
					deviceKey = prchsDtlMore.getSendInsdDeviceId();
				} else {
					userKey = prchsDtlMore.getUseInsdUsermbrNo();
					deviceKey = prchsDtlMore.getUseInsdDeviceId();
				}

				StringBuffer sbProdIdInfo = new StringBuffer();
				List<String> prodIdList = new ArrayList<String>();
				for (PrchsDtlMore prchsInfo : prchsDtlMoreList) {
					if (prchsInfo.getPrchsDtlId() > 1
							&& StringUtils.isNotBlank(prchsInfo.getUseFixrateProdId())
							&& StringUtils.startsWith(prchsInfo.getTenantProdGrpCd(),
									PurchaseConstants.TENANT_PRODUCT_GROUP_EBOOKCOMIC)) {
						break; // 이북 전권/소장 상품 구매 경우는 에피소드들 skip
					}
					if (prodIdList.contains(prchsInfo.getProdId())) {
						continue; // 상품ID 중복 방지
					}

					if (sbProdIdInfo.length() > 0) {
						sbProdIdInfo.append(";");
					}
					sbProdIdInfo.append(prchsInfo.getProdId());

					prodIdList.add(prchsInfo.getProdId());
				}

				this.purchaseOrderTstoreService.postTstoreNotiV2(prchsDtlMore.getPrchsId(), prchsDtlMore.getPrchsDt(),
						userKey, deviceKey, notiType, bGift, sbProdIdInfo.toString());
			}

		} else { // SAP

			List<String> paymentMtdCdList = new ArrayList<String>();
			for (PaymentInfo paymentNotiReq : notifyPaymentReq.getPaymentInfoList()) {
				paymentMtdCdList.add(PaymethodUtil.convert2StoreCode(paymentNotiReq.getPaymentMtdCd()));
			}
			Map<String, PurchaseCommonCode> commonCodeMap = this.purchaseCommonSCI.searchCommonCodeMap(
					paymentMtdCdList, prchsDtlMore.getCurrencyCd());
			PurchaseCommonCode commonCode = null;

			// 결제정보
			List<SendPurchaseNotiPaymentInfoEc> paymentInfoList = new ArrayList<SendPurchaseNotiPaymentInfoEc>();
			SendPurchaseNotiPaymentInfoEc payment = null;
			for (PaymentInfo paymentNotiReq : notifyPaymentReq.getPaymentInfoList()) {
				payment = new SendPurchaseNotiPaymentInfoEc();
				payment.setPaymentMtdCd(PaymethodUtil.convert2StoreCode(paymentNotiReq.getPaymentMtdCd()));
				commonCode = commonCodeMap.get(payment.getPaymentMtdCd());
				if (commonCode != null) {
					payment.setPaymentMtdNm(commonCode.getCdNm());
				}
				payment.setPaymentAmt(paymentNotiReq.getPaymentAmt());
				paymentInfoList.add(payment);
			}

			// 상품정보
			List<SendPurchaseNotiProductInfoEc> productInfoList = new ArrayList<SendPurchaseNotiProductInfoEc>();
			SendPurchaseNotiProductInfoEc product = null;
			SendPurchaseNotiSellerInfoEc seller = null;
			for (PrchsDtlMore prchsInfo : prchsDtlMoreList) {

				String prodNm = null;

				List<String> prodIdList = new ArrayList<String>();
				prodIdList.add(prchsInfo.getProdId());

				Map<String, PurchaseProduct> purchaseProductMap = this.purchaseDisplayRepository
						.searchPurchaseProductList(prchsDtlMore.getTenantId(), prchsDtlMore.getCurrencyCd(), null,
								prodIdList, false);
				if (purchaseProductMap != null && purchaseProductMap.get(prchsInfo.getProdId()) != null) {
					prodNm = purchaseProductMap.get(prchsInfo.getProdId()).getProdNm();
				}

				reservedDataMap = this.purchaseOrderMakeDataService.parseReservedData(prchsInfo.getPrchsResvDesc());

				product = new SendPurchaseNotiProductInfoEc();
				product.setProdId(prchsInfo.getProdId());
				product.setProdNm(prodNm);
				product.setProdAmt(prchsInfo.getProdAmt());
				product.setAutoPrchsYn(reservedDataMap.get("autoPrchsYn"));
				if (StringUtils.equals(product.getAutoPrchsYn(), "Y")) {
					product.setAutoPrchsPeriodUnitCd(reservedDataMap.get("autoPrchsPeriodUnitCd"));
					product.setAutoPrchsPeriodValue(Integer.parseInt(reservedDataMap.get("autoPrchsPeriodValue")));
				}

				SellerMbrSac sellerMbrSac = this.purchaseMemberRepository.searchSellerInfo(reservedDataMap
						.get("sellerMbrNo"));
				if (sellerMbrSac != null) {
					seller = new SendPurchaseNotiSellerInfoEc();
					seller.setSellerCompany(sellerMbrSac.getSellerCompany());
					seller.setSellerName(sellerMbrSac.getSellerName());
					seller.setSellerEmail(sellerMbrSac.getSellerEmail());
					seller.setSellerAddress(sellerMbrSac.getSellerAddress());
					seller.setSellerPhone(sellerMbrSac.getRepPhone());
					seller.setBizRegNumber(sellerMbrSac.getBizRegNumber());

					product.setSellerInfo(seller);
				}

				productInfoList.add(product);
			}

			// 기본구매정보

			String userEmail = null;

			// PurchaseUserDevice purchaseUserDevice = null;
			// String payUserKey = null;
			// String payDeviceKey = null;
			// if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			// payUserKey = prchsDtlMore.getSendInsdUsermbrNo();
			// payDeviceKey = prchsDtlMore.getSendInsdDeviceId();
			// } else {
			// payUserKey = prchsDtlMore.getUseInsdUsermbrNo();
			// payDeviceKey = prchsDtlMore.getUseInsdDeviceId();
			// }
			//
			// purchaseUserDevice = this.purchaseMemberRepository.searchUserDeviceByKey(prchsDtlMore.getTenantId(),
			// payUserKey, payDeviceKey);
			// if (purchaseUserDevice != null) {
			// userEmail = purchaseUserDevice.getUserEmail();
			// }

			SendPurchaseNotiEcReq sendPurchaseNotiEcReq = new SendPurchaseNotiEcReq();
			sendPurchaseNotiEcReq.setTenantId(prchsDtlMore.getTenantId());
			sendPurchaseNotiEcReq.setDeviceId(reservedDataMap.get("deviceId"));
			sendPurchaseNotiEcReq.setDeviceKey(reservedDataMap.get("marketDeviceKey"));
			sendPurchaseNotiEcReq.setUserEmail(userEmail);
			sendPurchaseNotiEcReq.setPrchsId(prchsDtlMore.getPrchsId());
			sendPurchaseNotiEcReq.setPrchsDt(prchsDtlMore.getPrchsDt());
			sendPurchaseNotiEcReq.setStatusCd(PurchaseConstants.PRCHS_STATUS_COMPT);
			sendPurchaseNotiEcReq.setTotAmt(prchsDtlMore.getTotAmt());
			sendPurchaseNotiEcReq.setRequestId(notifyPaymentReq.getPaymentInfoList().get(0).getTid());

			sendPurchaseNotiEcReq.setPaymentInfo(paymentInfoList);
			sendPurchaseNotiEcReq.setProductInfo(productInfoList);

			// Noti
			this.logger.info("PRCHS,ORDER,SAC,POST,NOTI,SAP,REQ,ONLY,{}",
					ReflectionToStringBuilder.toString(sendPurchaseNotiEcReq, ToStringStyle.SHORT_PREFIX_STYLE));

			String errDesc = null;
			boolean bSucc = false;
			try {
				// 정상완료응답이 아닌경우 Exception 발생
				this.sapPurchaseSCI.sendPurchaseNoti(sendPurchaseNotiEcReq);
				bSucc = true;
			} catch (Exception e) {
				if (e instanceof StorePlatformException) {
					errDesc = ((StorePlatformException) e).getCode();
				} else {
					errDesc = e.getMessage();
				}
			}
			this.logger.info("PRCHS,ORDER,SAC,POST,NOTI,SAP,RESULT,{},{}", bSucc, errDesc);

			String procStatusCd = bSucc ? PurchaseConstants.SAP_PURCHASE_NOTI_PROC_STATUS_SUCCESS : PurchaseConstants.SAP_PURCHASE_NOTI_PROC_STATUS_RESERVE;

			// 정상완료응답이 아닌경우 - 배치 처리를 위한 테이블 INSERT
			this.createSapPurchaseNoti(prchsDtlMore, sendPurchaseNotiEcReq, procStatusCd, errDesc);
		}
	}

	/*
	 * 
	 * <pre> SAP 결제완료Noti 배치 테이블 INSERT. </pre>
	 * 
	 * @param prchsDtlMore 구매정보
	 * 
	 * @param sendPurchaseNotiEcReq 결제완료Noti 요청 정보
	 * 
	 * @param procStatusCd 처리상태코드
	 * 
	 * @param errDesc Noti실패내용
	 */
	private void createSapPurchaseNoti(PrchsDtlMore prchsDtlMore, SendPurchaseNotiEcReq sendPurchaseNotiEcReq,
			String procStatusCd, String errDesc) {
		List<SapNoti> sapNotiList = new ArrayList<SapNoti>();

		SapNoti sapNoti = null;

		String payUserKey = null;
		String payDeviceKey = null;

		if (StringUtils.equals(prchsDtlMore.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			payUserKey = prchsDtlMore.getSendInsdUsermbrNo();
			payDeviceKey = prchsDtlMore.getSendInsdDeviceId();
		} else {
			payUserKey = prchsDtlMore.getUseInsdUsermbrNo();
			payDeviceKey = prchsDtlMore.getUseInsdDeviceId();
		}

		StringBuffer sbPaymentInfo = new StringBuffer(); // 결제정보
		if (CollectionUtils.isNotEmpty(sendPurchaseNotiEcReq.getPaymentInfo())) {
			for (SendPurchaseNotiPaymentInfoEc payment : sendPurchaseNotiEcReq.getPaymentInfo()) {
				if (sbPaymentInfo.length() > 0) {
					sbPaymentInfo.append(";");
				}
				sbPaymentInfo.append(payment.getPaymentMtdCd()).append(":")
						.append(StringUtils.defaultString(payment.getPaymentMtdNm())).append(":")
						.append(payment.getPaymentAmt());
			}
		}

		SendPurchaseNotiSellerInfoEc seller = null;
		int prchsDtlId = 1;
		for (SendPurchaseNotiProductInfoEc product : sendPurchaseNotiEcReq.getProductInfo()) {
			sapNoti = new SapNoti();

			sapNoti.setTenantId(sendPurchaseNotiEcReq.getTenantId());
			sapNoti.setPrchsId(sendPurchaseNotiEcReq.getPrchsId());
			sapNoti.setPrchsDtlId(prchsDtlId++);
			sapNoti.setInsdUsermbrNo(payUserKey);
			sapNoti.setInsdDeviceId(payDeviceKey);
			sapNoti.setMarketDeviceKey(sendPurchaseNotiEcReq.getDeviceKey());
			sapNoti.setDeviceId(sendPurchaseNotiEcReq.getDeviceId());
			sapNoti.setUserEmail(sendPurchaseNotiEcReq.getUserEmail());
			sapNoti.setStatusCd(sendPurchaseNotiEcReq.getStatusCd());
			sapNoti.setPrchsCaseCd(prchsDtlMore.getPrchsCaseCd());
			sapNoti.setPrchsDt(sendPurchaseNotiEcReq.getPrchsDt());
			sapNoti.setCancelDt(sendPurchaseNotiEcReq.getCancelDt());
			sapNoti.setTotAmt(sendPurchaseNotiEcReq.getTotAmt());
			sapNoti.setPpTid(sendPurchaseNotiEcReq.getRequestId());
			sapNoti.setPaymentInfo(sbPaymentInfo.toString());
			sapNoti.setProdId(product.getProdId());
			sapNoti.setProdNm(product.getProdNm());
			sapNoti.setProdAmt(product.getProdAmt());
			sapNoti.setAutoPrchsYn(product.getAutoPrchsYn());
			sapNoti.setAutoPrchsPeriodUnitCd(product.getAutoPrchsPeriodUnitCd());
			sapNoti.setAutoPrchsPeriod(product.getAutoPrchsPeriodValue());

			seller = product.getSellerInfo();
			if (seller != null) {
				sapNoti.setSellerComp(seller.getSellerCompany());
				sapNoti.setSellerNm(seller.getSellerName());
				sapNoti.setSellerEmail(seller.getSellerEmail());
				sapNoti.setBizRegNo(seller.getBizRegNumber());
				sapNoti.setSellerAddr(seller.getSellerAddress());
				sapNoti.setSellerTelNo(seller.getSellerPhone());
			}

			sapNoti.setAddParamInfo("");

			sapNoti.setProcStatusCd(procStatusCd);
			sapNoti.setProcDesc(errDesc);
			sapNoti.setRegId(prchsDtlMore.getRegId());
			sapNoti.setUpdId(prchsDtlMore.getRegId());

			sapNotiList.add(sapNoti);
		}

		try {
			this.purchaseOrderSCI.createSapNoti(new CreateSapNotiScReq(sapNotiList));

		} catch (Exception e) {
			if (e instanceof StorePlatformException) {
				errDesc = ((StorePlatformException) e).getCode();
			} else {
				errDesc = e.getMessage();
			}
			this.logger.info("PRCHS,ORDER,SAC,POST,NOTI,SAP,INS,ERROR,{},{}", errDesc);
		}
	}
}
