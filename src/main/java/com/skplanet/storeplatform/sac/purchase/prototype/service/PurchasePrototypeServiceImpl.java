package com.skplanet.storeplatform.sac.purchase.prototype.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.purchase.client.prototype.sci.PurchasePrototypeSCI;
import com.skplanet.storeplatform.purchase.client.prototype.vo.OwnProductList;
import com.skplanet.storeplatform.purchase.client.prototype.vo.Purchase;
import com.skplanet.storeplatform.purchase.client.prototype.vo.PurchaseHistory;
import com.skplanet.storeplatform.purchase.client.prototype.vo.RequestCheckOwnProduct;
import com.skplanet.storeplatform.purchase.client.prototype.vo.RequestPurchaseHistory;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPageProduct;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseHistory;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseProduct;

/**
 * SAC 구매/구매내역 서비스 구현체
 * 
 * Updated on : 2013. 12. 10. Updated by : 이승택, nTels.
 */
@Service
@Transactional
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
	public MyPagePurchaseHistory searchPurchaseList(RequestPurchaseHistory paramVO) {
		this.log.debug("PurchasePrototypeServiceImpl.searchPurchaseList,START,{}", paramVO);

		PurchaseHistory purchaseHistory = this.purchaseSCI.searchList(paramVO);

		List<MyPagePurchaseProduct> myPagePurchaseProductList = new ArrayList<MyPagePurchaseProduct>();

		MyPagePurchase myPagePurchase = null;
		MyPageProduct myPageProduct = null;
		for (Purchase purchaseVO : purchaseHistory.getPurchaseList()) {
			// -
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

			// -
			myPageProduct = new MyPageProduct();
			myPageProduct.setProdId(purchaseVO.getProdId());

			// -
			myPagePurchaseProductList.add(new MyPagePurchaseProduct(myPagePurchase, myPageProduct));
		}

		MyPagePurchaseHistory myPagePurchaseHistory = new MyPagePurchaseHistory();
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
	public OwnProductList checkPurchase(RequestCheckOwnProduct paramVO) {
		OwnProductList ownPorductList = null;

		return ownPorductList;
	}

}
