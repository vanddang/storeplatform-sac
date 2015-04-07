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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.common.vo.PpProperty;
import com.skplanet.storeplatform.purchase.client.payplanet.sci.PurchasePayPlanetSCI;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 
 * Pay Planet 가맹점 정보 서비스
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
@Service
public class PayPlanetShopServiceImpl implements PayPlanetShopService {
	@Autowired
	private PurchasePayPlanetSCI PurchasePayPlanetSCI;

	/**
	 * 
	 * <pre>
	 * P/P 가맹점 ID 여부 확인.
	 * </pre>
	 * 
	 * @param mid
	 * @return
	 */
	@Override
	public boolean startsWithPayPlanetMID(String mid) {
		for (String prefix : PurchaseConstants.PAYPLANET_MID_PREFIX_LIST) {
			if (StringUtils.startsWith(mid, prefix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * <pre>
	 * SKT 결제/결제취소 용 SYSTEM DIVISION 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            조회할 가맹점의 테넌트 ID
	 * @param prodId
	 *            상품 ID
	 * @param parentProdId
	 *            인앱 모상품 APP ID
	 * @param tenantProdGrpCd
	 *            테넌트 상품 분류 코드
	 * @return 결제/결제취소 용 SYSTEM DIVISION 정보
	 */
	@Override
	public PpProperty getDcbSystemDivision(String tenantId, String prodId, String parentProdId, String tenantProdGrpCd) {
		PpProperty ppProperty = new PpProperty();
		ppProperty.setTenantId(tenantId);
		ppProperty.setApiTypeCd(PurchaseConstants.PAYPLANET_API_TYPE_DCB_SYSTEM_DIVISION);
		ppProperty.setProdId(prodId);
		ppProperty.setParentProdId(parentProdId);
		ppProperty.setTenantProdGrpCd(tenantProdGrpCd);

		return this.PurchasePayPlanetSCI.searchDcbSystemDivision(ppProperty);
	}

	/**
	 * 
	 * <pre>
	 * Pay Planet 가맹점 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            조회할 가맹점의 테넌트 ID
	 * @param apiTypeCd
	 *            P/P 연동 API 타입 코드
	 * @param prchsReqPathCd
	 *            구매 요청 경로 코드
	 * @return Pay Planet 가맹점 정보
	 */
	@Override
	public PpProperty getPayPlanetShopInfo(String tenantId, String apiTypeCd, String prchsReqPathCd) {

		PpProperty ppProperty = new PpProperty();
		ppProperty.setTenantId(tenantId);
		ppProperty.setApiTypeCd(apiTypeCd);
		ppProperty.setReqPathCd(prchsReqPathCd);

		PpProperty payPlanetShop = this.PurchasePayPlanetSCI.searchPayPlanetShopInfo(ppProperty);
		if (payPlanetShop == null) {
			throw new StorePlatformException("SAC_PUR_7102");
		}

		return payPlanetShop;
	}

}
