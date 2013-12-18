/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.cache.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.runtime.cache.vo.BypassVO;
import com.skplanet.storeplatform.sac.runtime.cache.vo.InterfaceVO;
import com.skplanet.storeplatform.sac.runtime.cache.vo.ServiceVO;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 26. Updated by : 김현일, 인크로스.
 */
@Service
@Transactional
public class InterfaceServiceImpl implements InterfaceService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.runtime.cache.service.InterfaceService#searchDetail(java.util.Map)
	 */
	@Override
	public InterfaceVO searchDetail(Map<String, String> params) {
		return this.commonDAO.queryForObject("Interface.searchDetail", params, InterfaceVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.runtime.cache.service.InterfaceService#searchChannelName(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public String searchChannelName(String bypassYn, Map<String, String> params) {

		if (StringUtils.isEmpty(bypassYn))
			return "";
		if (bypassYn.equals("Y")) {
			return this.commonDAO.queryForObject("Interface.searchBypassChannelName", params, String.class);
		} else if (bypassYn.equals("N")) {
			return this.commonDAO.queryForObject("Interface.searchServiceChannelName", params, String.class);
		} else {
			return "";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.runtime.cache.service.InterfaceService#searchBypassUrl(java.util.Map)
	 */
	@Override
	public BypassVO searchBypassUrl(Map<String, String> params) {
		return this.commonDAO.queryForObject("Interface.searchBypassUrl", params, BypassVO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.runtime.cache.service.InterfaceService#searchServiceMethod(java.util.Map)
	 */
	@Override
	public ServiceVO searchServiceMethod(Map<String, String> params) {
		return this.commonDAO.queryForObject("Interface.searchServiceMethod", params, ServiceVO.class);
	}

}
