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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.purchase.common.vo.PaymentPromotion;

/**
 * 결제수단 프로모션 서비스
 * 
 * Updated on : 2014. 12. 9. Updated by : 이승택, nTels.
 */
@Service
public class PaymentPromotionServiceImpl implements PaymentPromotionService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	/**
	 * 
	 * <pre>
	 * 현재시각에 유효한 결제수단 프로모션 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트 ID
	 * @return 결제수단 프로모션 목록
	 */
	@Override
	public List<PaymentPromotion> searchPaymentPromotionList(String tenantId) {
		return this.commonDao.queryForList("PurchaseSacCommon.searchPaymentPromotionList", tenantId,
				PaymentPromotion.class);
	}
}
