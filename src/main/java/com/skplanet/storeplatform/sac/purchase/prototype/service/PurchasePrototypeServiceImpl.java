/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.prototype.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.prototype.sci.PurchasePrototypeSCI;
import com.skplanet.storeplatform.purchase.client.prototype.vo.CheckPurchaseRequest;
import com.skplanet.storeplatform.purchase.client.prototype.vo.CheckPurchaseResponse;
import com.skplanet.storeplatform.purchase.client.prototype.vo.ProductOwnInfo;
import com.skplanet.storeplatform.purchase.client.prototype.vo.Purchase;
import com.skplanet.storeplatform.purchase.client.prototype.vo.PurchaseHistoryRequest;
import com.skplanet.storeplatform.purchase.client.prototype.vo.PurchaseHistoryResponse;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.CheckPurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.CheckPurchaseReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.CheckPurchaseRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPageProduct;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseHistoryReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseHistoryRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseProduct;

/**
 * [Prototype] 구매내역 서비스 구현체
 * 
 * Updated on : 2013. 12. 10. Updated by : 이승택, nTels.
 */
@Service
public class PurchasePrototypeServiceImpl implements PurchasePrototypeService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PurchasePrototypeSCI purchaseSCI;

	/**
	 * <pre>
	 * MyPage 구매내역 조회.
	 * </pre>
	 * 
	 * @param paramVO
	 *            구매내역 조회조건
	 * @return MyPage 구매내역
	 */
	@Override
	public MyPagePurchaseHistoryRes searchPurchaseList(MyPagePurchaseHistoryReq paramVO) {
		this.log.debug("PurchasePrototypeServiceImpl.searchPurchaseList,START,{}", paramVO);

		// SC(Purchase) REQ
		PurchaseHistoryRequest sciReq = new PurchaseHistoryRequest();
		sciReq.setTenantId(paramVO.getTenantId());
		sciReq.setDeviceNo(paramVO.getDeviceNo());
		sciReq.setMbrNo(paramVO.getMbrNo());
		sciReq.setPrchsId(paramVO.getPrchsId());
		sciReq.setPrchsDtlId(paramVO.getPrchsDtlId());
		sciReq.setProdId(paramVO.getProdId());
		sciReq.setProdOwnType(paramVO.getProdOwnType());
		sciReq.setStartDt(paramVO.getStartDt());
		sciReq.setEndDt(paramVO.getEndDt());
		sciReq.setStartRow(paramVO.getStartRow());
		sciReq.setEndRow(paramVO.getEndRow());
		sciReq.setProdGrpCd(paramVO.getProdGrpCd());
		sciReq.setTarget(paramVO.getTarget());
		sciReq.setPrchsStatus(paramVO.getPrchsStatus());
		sciReq.setHiding(paramVO.getHiding());

		// 구매내역
		PurchaseHistoryResponse sciRes = this.purchaseSCI.searchList(sciReq);

		// 구매&상품 결합
		List<MyPagePurchaseProduct> myPagePurchaseProductList = new ArrayList<MyPagePurchaseProduct>();
		MyPagePurchase myPagePurchase = null;
		MyPageProduct myPageProduct = null;
		for (Purchase purchaseVO : sciRes.getPurchaseList()) {
			// 구매 정보 세팅
			myPagePurchase = new MyPagePurchase();
			myPagePurchase.setUseTenantId(purchaseVO.getUseTenantId());
			myPagePurchase.setUseMbrNo(purchaseVO.getUseMbrNo());
			myPagePurchase.setUseDeviceNo(purchaseVO.getUseDeviceNo());
			myPagePurchase.setSendTenantId(purchaseVO.getSendTenantId());
			myPagePurchase.setSendMbrNo(purchaseVO.getSendMbrNo());
			myPagePurchase.setSendDeviceNo(purchaseVO.getSendDeviceNo());
			myPagePurchase.setPrchsId(purchaseVO.getPrchsId());
			myPagePurchase.setPrchsDtlId(purchaseVO.getPrchsDtlId());
			myPagePurchase.setPrchsDt(purchaseVO.getPrchsDt());
			myPagePurchase.setTotAmt(purchaseVO.getTotAmt());
			myPagePurchase.setProdAmt(purchaseVO.getProdAmt());
			myPagePurchase.setProdGrpCd(purchaseVO.getProdGrpCd());
			myPagePurchase.setStatusCd(purchaseVO.getStatusCd());
			myPagePurchase.setUseStartDt(purchaseVO.getUseStartDt());
			myPagePurchase.setUseExprDt(purchaseVO.getUseExprDt());
			myPagePurchase.setDwldStartDt(purchaseVO.getDwldStartDt());
			myPagePurchase.setDwldExprDt(purchaseVO.getDwldExprDt());
			myPagePurchase.setCancelDt(purchaseVO.getCancelDt());
			myPagePurchase.setRecvDt(purchaseVO.getRecvDt());
			myPagePurchase.setRePrchsPmtYn(purchaseVO.getRePrchsPmtYn());
			myPagePurchase.setHidingYn(purchaseVO.getHidingYn());
			myPagePurchase.setExpiredYn(purchaseVO.getExpiredYn());

			// 상품 정보 조회
			myPageProduct = new MyPageProduct();
			myPageProduct.setProdId(purchaseVO.getProdId());

			// 구매&상품 내역 추가
			myPagePurchaseProductList.add(new MyPagePurchaseProduct(myPagePurchase, myPageProduct));
		}

		MyPagePurchaseHistoryRes myPagePurchaseHistory = new MyPagePurchaseHistoryRes();
		myPagePurchaseHistory.setHistory(myPagePurchaseProductList);

		this.log.debug("PurchaseServiceImpl.searchPurchaseList,END,{}", myPagePurchaseHistory.getHistory().size());
		return myPagePurchaseHistory;
	}

	/**
	 * <pre>
	 * 기구매 체크.
	 * </pre>
	 * 
	 * @param paramVO
	 *            구매내역 조회조건
	 * @return MyPage 구매내역
	 */
	@Override
	public CheckPurchaseRes checkPurchase(CheckPurchaseReq paramVO) {
		this.log.debug("PurchaseServiceImpl.checkPurchase,START,{}", paramVO);

		// SC(Purchase) REQ
		CheckPurchaseRequest sciReq = new CheckPurchaseRequest();
		sciReq.setTenantId(paramVO.getTenantId());
		sciReq.setMbrNo(paramVO.getMbrNo());
		sciReq.setDeviceNo(paramVO.getDeviceNo());
		sciReq.setPrchsId(paramVO.getPrchsId());

		List<ProductOwnInfo> productOwnInfoList = new ArrayList<ProductOwnInfo>();
		for (String prodId : paramVO.getProdIdList()) {
			productOwnInfoList.add(new ProductOwnInfo(prodId, this.getOwnType(prodId))); // 상품소유타입 조회 : id | mdn 기반
		}
		sciReq.setProductOwnInfoList(productOwnInfoList);

		// 기구매체크
		CheckPurchaseResponse checkPurchaseResponse = this.purchaseSCI.checkOwnProduct(sciReq);

		// RES
		List<CheckPurchase> checkPurchaseList = new ArrayList<CheckPurchase>();
		for (ProductOwnInfo productOwnInfo : checkPurchaseResponse.getProductOwnInfoList()) {
			checkPurchaseList.add(new CheckPurchase(productOwnInfo.getProdId(), productOwnInfo.getPrchsId()));
		}

		CheckPurchaseRes res = new CheckPurchaseRes();
		res.setCheckPurchaseList(checkPurchaseList);

		this.log.debug("PurchaseServiceImpl.checkPurchase,END,{}", checkPurchaseList.size());
		return res;
	}

	/**
	 * 
	 * <pre>
	 * [Prototype] 상품의 소유타입(id/mdn 기반) 조회.
	 * </pre>
	 * 
	 * @param prodId
	 *            상품ID
	 * @return id-id기반,mdn-mdn기반
	 */
	private String getOwnType(String prodId) {
		return prodId.startsWith("0") ? "id" : "mdn";
	}

}
