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

import com.skplanet.storeplatform.purchase.client.common.vo.PrchsProdCnt;
import com.skplanet.storeplatform.purchase.client.order.vo.CreatePurchaseSc;
import com.skplanet.storeplatform.purchase.client.product.count.sci.PurchaseCountSCI;
import com.skplanet.storeplatform.purchase.client.product.count.vo.InsertPurchaseProductCountScReq;
import com.skplanet.storeplatform.purchase.client.product.count.vo.InsertPurchaseProductCountScRes;
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

		List<PrchsProdCnt> prchsProdCntList = new ArrayList<PrchsProdCnt>();
		PrchsProdCnt prchsProdCnt = null;

		String tenantProdGrpCd = null;
		int multipleVal = StringUtils.equals(prchsStatusCd, PurchaseConstants.PRCHS_STATUS_COMPT) ? 1 : -1;

		for (CreatePurchaseSc createPurchaseSc : createPurchaseScList) {
			if (procProdIdList.contains(createPurchaseSc.getProdId())) {
				continue;
			}
			procProdIdList.add(createPurchaseSc.getProdId());

			prchsProdCnt = new PrchsProdCnt();

			prchsProdCnt.setTenantId(createPurchaseSc.getTenantId());
			prchsProdCnt.setUseUserKey(createPurchaseSc.getUseInsdUsermbrNo());
			prchsProdCnt.setUseDeviceKey(createPurchaseSc.getUseInsdDeviceId());
			prchsProdCnt.setRegId(createPurchaseSc.getSystemId());
			prchsProdCnt.setUpdId(createPurchaseSc.getSystemId());

			prchsProdCnt.setPrchsId(createPurchaseSc.getPrchsId());
			prchsProdCnt.setPrchsDt(createPurchaseSc.getPrchsDt());
			prchsProdCnt.setStatusCd(prchsStatusCd);

			prchsProdCnt.setProdId(createPurchaseSc.getProdId());
			prchsProdCnt.setProdQty(createPurchaseSc.getProdQty() * multipleVal);
			prchsProdCnt.setSprcProdYn(StringUtils.defaultString(createPurchaseSc.getSprcProdYn(),
					PurchaseConstants.USE_N));

			// 중복 구매 가능한 쇼핑상품 / 부분유료화 상품 처리
			tenantProdGrpCd = createPurchaseSc.getTenantProdGrpCd();
			if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)
					|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				prchsProdCnt.setProdGrpCd(createPurchaseSc.getTenantProdGrpCd().substring(0, 12)
						+ createPurchaseSc.getPrchsId());
			} else {
				prchsProdCnt.setProdGrpCd(createPurchaseSc.getTenantProdGrpCd().substring(0, 12));
			}

			prchsProdCnt.setCntProcStatus(PurchaseConstants.USE_N);

			prchsProdCntList.add(prchsProdCnt);
		}

		InsertPurchaseProductCountScRes res = this.purchaseCountSCI
				.insertPurchaseProductCount(new InsertPurchaseProductCountScReq(prchsProdCntList));
		return res.getCount();
	}

	/**
	 * 
	 * <pre>
	 * [DUMMY] 상품 건수 저장.
	 * </pre>
	 * 
	 * @param createPurchaseScList
	 *            구매 정보 목록
	 * @param prchsStatusCd
	 *            구매상태
	 * @return 추가한 상품 갯수
	 */
	@Override
	public int dummyPurchaseProductCount(List<CreatePurchaseSc> createPurchaseScList, String prchsStatusCd) {
		List<String> procProdIdList = new ArrayList<String>();

		List<PrchsProdCnt> prchsProdCntList = new ArrayList<PrchsProdCnt>();
		PrchsProdCnt prchsProdCnt = null;

		String tenantProdGrpCd = null;
		int multipleVal = StringUtils.equals(prchsStatusCd, PurchaseConstants.PRCHS_STATUS_COMPT) ? 1 : -1;

		for (CreatePurchaseSc createPurchaseSc : createPurchaseScList) {
			if (procProdIdList.contains(createPurchaseSc.getProdId())) {
				continue;
			}
			procProdIdList.add(createPurchaseSc.getProdId());

			prchsProdCnt = new PrchsProdCnt();

			prchsProdCnt.setTenantId(createPurchaseSc.getTenantId());
			prchsProdCnt.setUseUserKey(createPurchaseSc.getUseInsdUsermbrNo());
			prchsProdCnt.setUseDeviceKey(createPurchaseSc.getUseInsdDeviceId());
			prchsProdCnt.setRegId(createPurchaseSc.getSystemId());
			prchsProdCnt.setUpdId(createPurchaseSc.getSystemId());

			prchsProdCnt.setPrchsId(createPurchaseSc.getPrchsId());
			prchsProdCnt.setPrchsDt(createPurchaseSc.getPrchsDt());
			prchsProdCnt.setStatusCd(prchsStatusCd);

			prchsProdCnt.setProdId(createPurchaseSc.getProdId());
			prchsProdCnt.setProdQty(createPurchaseSc.getProdQty() * multipleVal);
			prchsProdCnt.setSprcProdYn(StringUtils.defaultString(createPurchaseSc.getSprcProdYn(),
					PurchaseConstants.USE_N));

			// 중복 구매 가능한 쇼핑상품 / 부분유료화 상품 처리
			tenantProdGrpCd = createPurchaseSc.getTenantProdGrpCd();
			if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)
					|| StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
				prchsProdCnt.setProdGrpCd(createPurchaseSc.getTenantProdGrpCd().substring(0, 12)
						+ createPurchaseSc.getPrchsId());
			} else {
				prchsProdCnt.setProdGrpCd(createPurchaseSc.getTenantProdGrpCd().substring(0, 12));
			}

			prchsProdCnt.setCntProcStatus(PurchaseConstants.USE_N);

			prchsProdCntList.add(prchsProdCnt);
		}

		InsertPurchaseProductCountScRes res = this.purchaseCountSCI
				.dummyPurchaseProductCount(new InsertPurchaseProductCountScReq(prchsProdCntList));
		return res.getCount();
	}
}
