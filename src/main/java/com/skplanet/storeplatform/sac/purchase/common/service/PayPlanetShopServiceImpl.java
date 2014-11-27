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

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.purchase.common.vo.PayPlanetShop;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 
 * Pay Planet 가맹점 정보 서비스
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
@Service
public class PayPlanetShopServiceImpl implements PayPlanetShopService {
	private static final String PAYPLANET_CODE_PREFIX = "PP0";
	private static final String SERVER_LEVEL_DEV = "DV"; // 조회대상 서버 단계: 개발기
	private static final String SERVER_LEVEL_QA = "QA"; // 조회대상 서버 단계: QA
	private static final String SERVER_LEVEL_REAL = "LV"; // 조회대상 서버 단계: 상용

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	@Value("#{systemProperties['spring.profiles.active']}")
	private String envServerLevel;

	private String serverLevel;

	@PostConstruct
	public void initServerLevel() {
		if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_REAL)) {
			this.serverLevel = SERVER_LEVEL_REAL;
		} else if (StringUtils.equals(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_QA)) {
			this.serverLevel = SERVER_LEVEL_QA;
		} else {
			this.serverLevel = SERVER_LEVEL_DEV;
		}
	}

	/**
	 * 
	 * <pre>
	 * Pay Planet 가맹점 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            조회할 가맹점의 테넌트 ID
	 * @return Pay Planet 가맹점 정보
	 */
	@Override
	public PayPlanetShop getPayPlanetShopInfo(String tenantId) {
		StringBuffer sb = new StringBuffer();
		sb.append(PAYPLANET_CODE_PREFIX).append(tenantId).append(this.serverLevel);

		PayPlanetShop shopInfo = this.commonDao.queryForObject("PurchaseSacCommon.searchPayPlanetShopInfoDetail",
				sb.toString(), PayPlanetShop.class);
		if (shopInfo == null) {
			throw new StorePlatformException("SAC_PUR_7102");
		}

		shopInfo.setTenantId(tenantId);
		return shopInfo;
	}

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
}
