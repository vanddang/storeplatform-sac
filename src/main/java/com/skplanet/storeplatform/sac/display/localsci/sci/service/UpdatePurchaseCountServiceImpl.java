/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UpdatePurchaseCountSacReq;

/**
 * UpdatePurchaseCount Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 2. 14. Updated by : 이석희, 아이에스플러스.
 */
@Service
public class UpdatePurchaseCountServiceImpl implements UpdatePurchaseCountService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.display.localsci.sci.service.UpdatePurchaseCountService#UpdatePurchaseCountService
	 * (UpdatePurchaseCountSacReq req)
	 */
	@Override
	public void updatePurchaseCount(List<UpdatePurchaseCountSacReq> reqList) {
		Map<String, String> map = null;
		List<Map> productList = null;

		int prchsCnt = 0;
		for (int i = 0; i < reqList.size(); i++) {
			map = new HashMap<String, String>();
			if (i > 99) {
				throw new StorePlatformException("SAC_DSP_0007", "100");
			}

			map.put("tenantId", reqList.get(i).getTenantId());
			map.put("productId", reqList.get(i).getProductId());
			map.put("purchaseCount", reqList.get(i).getPurchaseCount().toString());

			prchsCnt = (Integer) this.commonDAO.queryForObject("LocalSci.getPurchaseCount", map);

			// 해당 상품의 구매수가 0이면 업데이트 할 구매건수도 0으로 SET
			if (prchsCnt > 0) {
				if (this.commonDAO.update("LocalSci.updatePurchaseCount", map) <= 0) {
					this.commonDAO.update("LocalSci.insertPurchaseProd", map);
				}
			}
		}
	}
}
