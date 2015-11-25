package com.skplanet.storeplatform.purchase.promotion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.common.vo.PaymentPromotion;

@Service
public class PurchasePromotionServiceImpl implements PurchasePromotionService {

	@Autowired
	@Qualifier("scPurchase")
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
		return this.commonDao.queryForList("PurchasePromotion.searchPaymentPromotionList", tenantId,
				PaymentPromotion.class);
	}
}
