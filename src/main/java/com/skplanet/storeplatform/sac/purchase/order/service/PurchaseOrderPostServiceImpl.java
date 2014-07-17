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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.order.vo.PrchsDtlMore;
import com.skplanet.storeplatform.sac.purchase.interworking.service.InterworkingSacService;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.Interworking;
import com.skplanet.storeplatform.sac.purchase.interworking.vo.InterworkingSacReq;

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
	private PurchaseOrderTstoreService purchaseOrderTstoreService;
	@Autowired
	private PurchaseOrderMakeDataService purchaseOrderMakeDataService;
	@Autowired
	private InterworkingSacService interworkingSacService;

	/**
	 * 
	 * <pre>
	 * 구매 후처리( 인터파크/씨네21, Tstore Noti).
	 * </pre>
	 * 
	 * @param prchsDtlMoreList
	 *            구매정보 목록
	 */
	@Override
	public void postPurchase(List<PrchsDtlMore> prchsDtlMoreList) {
		this.logger.info("PRCHS,ORDER,SAC,POST,START,{}", prchsDtlMoreList.get(0).getPrchsId());

		// ------------------------------------------------------------------------------------
		// 인터파크 / 씨네21

		this.createInterworking(prchsDtlMoreList);

		// ------------------------------------------------------------------------------------
		// Tstore 측으로 구매완료 알림: 이메일 발송, SMS / MMS 등등 처리
		// 구매완료Noti처리 변경 : 결제처리결과 알림 API 응답 항목에 추가
		/*
		 * PrchsDtlMore prchsDtlMore = prchsDtlMoreList.get(0);
		 * 
		 * // IAP 은 skip if (StringUtils.startsWith(prchsDtlMore.getTenantProdGrpCd(),
		 * PurchaseConstants.TENANT_PRODUCT_GROUP_IAP) == false) { Map<String, String> reservedDataMap =
		 * this.purchaseOrderMakeDataService.parseReservedData(prchsDtlMore .getPrchsResvDesc());
		 * 
		 * this.purchaseOrderTstoreService.postTstoreNoti(prchsDtlMore.getPrchsId(), prchsDtlMore.getPrchsDt(),
		 * prchsDtlMore.getUseInsdUsermbrNo(), prchsDtlMore.getUseInsdDeviceId(),
		 * reservedDataMap.get("tstoreNotiPublishType")); }
		 */
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

}
