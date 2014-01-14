package com.skplanet.storeplatform.sac.purchase.prototype.service;

import com.skplanet.storeplatform.purchase.client.prototype.vo.OwnProductList;
import com.skplanet.storeplatform.purchase.client.prototype.vo.RequestCheckOwnProduct;
import com.skplanet.storeplatform.purchase.client.prototype.vo.RequestPurchaseHistory;
import com.skplanet.storeplatform.sac.client.purchase.vo.prototype.MyPagePurchaseHistory;

/**
 * SAC 구매/구매내역 서비스 인터페이스.
 * 
 * Updated on : 2013. 12. 10. Updated by : 이승택, nTels.
 */
public interface PurchasePrototypeService {

	/**
	 * <pre>
	 * MyPage 구매내역 조회.
	 * </pre>
	 * 
	 * @param paramVO
	 *            구매내역 조회조건
	 * @return MyPage 구매내역
	 */
	public MyPagePurchaseHistory searchPurchaseList(RequestPurchaseHistory paramVO);

	/**
	 * <pre>
	 * 기구매 체크.
	 * </pre>
	 * 
	 * @param paramVO
	 *            구매내역 조회조건
	 * @return MyPage 구매내역
	 */
	public OwnProductList checkPurchase(RequestCheckOwnProduct paramVO);
}
