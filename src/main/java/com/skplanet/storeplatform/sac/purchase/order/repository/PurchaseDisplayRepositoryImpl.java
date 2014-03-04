/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PaymentInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;

/**
 * 
 * 구매 시 필요한 전시Part SAC internal I/F repository
 * 
 * Updated on : 2014. 2. 27. Updated by : 이승택, nTels.
 */
@Component
public class PurchaseDisplayRepositoryImpl implements PurchaseDisplayRepository {

	@Autowired
	private PaymentInfoSCI purchaseProductSCI;

	/**
	 * 
	 * <pre>
	 * 상품 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param langCd
	 *            언어코드
	 * @param deviceModelCd
	 *            단말모델코드
	 * @param prodIdList
	 *            조회할 상품ID 목록
	 * @return 상품ID에 매핑되는 상품정보를 담은 Map
	 */
	@Override
	public Map<String, PurchaseProduct> searchPurchaseProductList(String tenantId, String langCd, String deviceModelCd,
			List<String> prodIdList) {
		PaymentInfoSacReq req = new PaymentInfoSacReq();
		req.setTenantId(tenantId);
		req.setLangCd(langCd);
		req.setProdIdList(prodIdList);

		PaymentInfoSacRes res = this.purchaseProductSCI.searchPaymentInfo(req);
		List<PaymentInfo> purchaseProductList = res.getPaymentInfoList();
		if (purchaseProductList.size() < 1) {
			return null;
		}

		Map<String, PurchaseProduct> purchaseProductMap = new HashMap<String, PurchaseProduct>();
		PurchaseProduct purchaseProduct = null;
		for (PaymentInfo displayInfo : purchaseProductList) {
			if (purchaseProductMap.containsKey(displayInfo.getProdId())) {
				continue;
			}

			purchaseProduct = new PurchaseProduct();
			// ////////////////////////// 상품 군 조회 변수 ////////////////////////////
			purchaseProduct.setTopMenuId(displayInfo.getTopMenuId());
			purchaseProduct.setSvcGrpCd(displayInfo.getSvcGrpCd());
			// ////////////////////////// APP,멀티미디어 상품 변수 ////////////////////////////
			purchaseProduct.setProdId(displayInfo.getProdId());
			purchaseProduct.setProdNm(displayInfo.getProdNm());
			purchaseProduct.setProdAmt(displayInfo.getProdAmt());
			purchaseProduct.setProdStatusCd(displayInfo.getProdStatusCd());
			purchaseProduct.setProdGrdCd(displayInfo.getProdGrdCd());
			purchaseProduct.setProdSprtYn(displayInfo.getProdSprtYn());
			purchaseProduct.setDrmYn(displayInfo.getDrmYn());
			purchaseProduct.setUsePeriodUnitCd(displayInfo.getUsePeriodUnitCd());
			purchaseProduct.setUsePeriod(displayInfo.getUsePeriod());
			purchaseProduct.setAid(displayInfo.getAid());
			purchaseProduct.setTenantProdGrpCd(displayInfo.getTenantProdGrpCd());
			purchaseProduct.setMallCd(displayInfo.getMallCd());
			purchaseProduct.setOutsdContentsId(displayInfo.getOutsdContentsId());
			purchaseProduct.setSellerMbrNo(displayInfo.getSellerMbrNo());
			purchaseProduct.setSellerNm(displayInfo.getSellerNm());
			purchaseProduct.setSellerEmail(displayInfo.getSellerEmail());
			purchaseProduct.setSellerTelno(displayInfo.getSellerTelno());
			// ////////////////////////// 쇼핑 상품 변수 ////////////////////////////
			purchaseProduct.setCouponCode(displayInfo.getCouponCode());
			purchaseProduct.setItemCode(displayInfo.getItemCode());
			purchaseProduct.setSpecialSaleAmt(displayInfo.getSpecialSaleAmt());
			purchaseProduct.setSpecialSaleStartDt(displayInfo.getSpecialSaleStartDt());
			purchaseProduct.setSpecialSaleEndDt(displayInfo.getSpecialSaleEndDt());
			purchaseProduct.setSpecialSaleCouponId(displayInfo.getSpecialSaleCouponId());
			purchaseProduct.setSpecialSaleMonthLimit(displayInfo.getSpecialSaleMonthLimit());
			purchaseProduct.setSpecialSaleDayLimit(displayInfo.getSpecialSaleDayLimit());
			purchaseProduct.setSpecialSaleMonthLimitPerson(displayInfo.getSpecialSaleMonthLimitPerson());
			purchaseProduct.setSpecialSaleDayLimitPerson(displayInfo.getSpecialSaleDayLimitPerson());
			// ////////////////////////// 정액제 상품 변수 ////////////////////////////
			purchaseProduct.setAvailableFixrateProdIdList(displayInfo.getAvailableFixrateProdIdList());
			purchaseProduct.setAutoPrchsYN(displayInfo.getAutoPrchsYN());
			purchaseProduct.setAutoPrchsPeriodUnitCd(displayInfo.getAutoPrchsPeriodUnitCd());
			purchaseProduct.setAutoPrchsPeriodValue(displayInfo.getAutoPrchsPeriodValue());
			purchaseProduct.setAutoPrchsLastDt(displayInfo.getAutoPrchsLastDt());
			purchaseProduct.setExclusiveFixrateProdExistYn(displayInfo.getExclusiveFixrateProdExistYn());
			purchaseProduct.setExclusiveFixrateProdIdList(displayInfo.getExclusiveFixrateProdIdList());

			purchaseProductMap.put(displayInfo.getProdId(), purchaseProduct);
		}

		return purchaseProductMap;
	}
}
