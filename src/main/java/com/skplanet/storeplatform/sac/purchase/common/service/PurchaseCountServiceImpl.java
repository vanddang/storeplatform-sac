/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.purchase.client.order.vo.CreatePurchaseSc;
import com.skplanet.storeplatform.purchase.client.product.count.sci.PurchaseCountSCI;
import com.skplanet.storeplatform.purchase.client.product.count.vo.InsertPurchaseProductCountScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.InsertPurchaseProductCountScRes;
import com.skplanet.storeplatform.purchase.client.product.count.vo.PurchaseProductCount;
import com.skplanet.storeplatform.purchase.constant.PurchaseConstants;

/**
 * 
 * 구매 상품 건수 처리 서비스
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseCountServiceImpl implements PurchaseCountService {

	@Autowired
	private PurchaseCountSCI purchaseCountSCI;

	/**
	 * 
	 * <pre>
	 * 상품 건수 저장.
	 * </pre>
	 * 
	 * @param createPurchaseScList
	 *            구매 정보 목록
	 * @param prchsStatusCd
	 *            구매상태
	 * @return 추가한 상품 갯수
	 */
	@Override
	public int insertPurchaseProductCount(List<CreatePurchaseSc> createPurchaseScList, String prchsStatusCd) {
		List<String> procProdIdList = new ArrayList<String>();

		List<PurchaseProductCount> purchaseProductCountList = new ArrayList<PurchaseProductCount>();
		PurchaseProductCount purchaseProductCount = null;

		String tenantProdGrpCd = null;
		int multipleVal = StringUtils.equals(prchsStatusCd, PurchaseConstants.PRCHS_STATUS_COMPT) ? 1 : -1;

		for (CreatePurchaseSc createPurchaseSc : createPurchaseScList) {
			if (procProdIdList.contains(createPurchaseSc.getProdId())) {
				continue;
			}
			procProdIdList.add(createPurchaseSc.getProdId());

			purchaseProductCount = new PurchaseProductCount();

			purchaseProductCount.setTenantId(createPurchaseSc.getTenantId());
			purchaseProductCount.setUseUserKey(createPurchaseSc.getUseInsdUsermbrNo());
			purchaseProductCount.setUseDeviceKey(createPurchaseSc.getUseInsdDeviceId());
			purchaseProductCount.setRegId(createPurchaseSc.getSystemId());
			purchaseProductCount.setUpdId(createPurchaseSc.getSystemId());

			purchaseProductCount.setPrchsId(createPurchaseSc.getPrchsId());
			purchaseProductCount.setPrchsDt(createPurchaseSc.getPrchsDt());
			purchaseProductCount.setStatusCd(prchsStatusCd);

			purchaseProductCount.setProdId(createPurchaseSc.getProdId());
			purchaseProductCount.setProdQty(createPurchaseSc.getProdQty() * multipleVal);
			purchaseProductCount.setSprcProdYn(StringUtils.defaultString(createPurchaseSc.getSprcProdYn(),
					PurchaseConstants.USE_N));

			// 중복 구매 가능한 쇼핑상품 / 부분유료화 상품 처리
			tenantProdGrpCd = createPurchaseSc.getTenantProdGrpCd();
			if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)
					|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				purchaseProductCount.setProdGrpCd(createPurchaseSc.getTenantProdGrpCd().substring(0, 12)
						+ createPurchaseSc.getPrchsId());
			} else {
				purchaseProductCount.setProdGrpCd(createPurchaseSc.getTenantProdGrpCd().substring(0, 12));
			}

			purchaseProductCount.setCntProcStatus(PurchaseConstants.USE_N);

			purchaseProductCountList.add(purchaseProductCount);
		}

		InsertPurchaseProductCountScRes res = this.purchaseCountSCI
				.insertPurchaseProductCount(new InsertPurchaseProductCountScReq(purchaseProductCountList));
		return res.getCount();
	}
}
