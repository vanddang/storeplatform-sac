package com.skplanet.storeplatform.sac.purchase.order.dummy.service;

import com.skplanet.storeplatform.sac.purchase.order.dummy.vo.DummyProduct;

public class DummyProductServiceImpl {
	/**
	 * 상품 정보 조회.
	 */
	public DummyProduct getProductInfo(String tenantId, String prodId, String deviceModelCd) {
		DummyProduct dummy = new DummyProduct();
		dummy.setProdId(prodId);
		dummy.setProdAmt(0.0);
		dummy.setProdGrdCd("PD004401");
		dummy.setAvailableLimitPrchsPathCd(null);
		dummy.setSvcGrpCd("DP000201");
		dummy.setSvcTypeCd(null);
		dummy.setTopCategoryNo(null);
		dummy.setAvailableLimitCnt(0);
		dummy.setUsePeriodUnit("PD00310");
		dummy.setUsePeriodCnt(0);
		dummy.setbSell(true);
		dummy.setbLimitProd(false);
		dummy.setbDupleProd(false);
		dummy.setbMdnProd(false);
		dummy.setbSupport(true);

		return dummy;
	}
}
