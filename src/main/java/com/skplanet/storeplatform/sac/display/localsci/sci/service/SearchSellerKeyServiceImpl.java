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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;

/**
 * SearchSellerKey Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 2. 13. Updated by : 이석희, 아이에스플러스.
 */
@Service
public class SearchSellerKeyServiceImpl implements SearchSellerKeyService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.display.localsci.sci.service.SearchSellerKeyService#SearchSellerKeyService
	 * (String aid)
	 */
	@Override
	public String searchSellerKeyForAid(String aid, String tenantId) {

		if (StringUtils.isEmpty(aid)) {
			throw new StorePlatformException("SAC_DSP_0002", "aid", aid);
		}
		Map<String, String> map = new HashMap<String, String>();

		map.put("aid", aid);
		map.put("tenantId", tenantId);
		String sellerKey = (String) this.commonDAO.queryForObject("LocalSci.getSellerKey", map);

		if (StringUtil.isEmpty(sellerKey)) {
			throw new StorePlatformException("SAC_DSP_0017");
		}
		return sellerKey;
	}
}
