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

import com.skplanet.storeplatform.sac.runtime.cache.vo.BypassVO;
import com.skplanet.storeplatform.sac.runtime.cache.vo.InterfaceInfo;
import com.skplanet.storeplatform.sac.runtime.cache.vo.ServiceInfo;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 26. Updated by : 김현일, 인크로스.
 */
public interface InterfaceService {

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param params
	 *            params
	 * @return InterfaceVO
	 */
	public InterfaceInfo searchDetail(Map<String, String> params);

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param bypassYn
	 *            bypassYn
	 * @param params
	 *            params
	 * @return String
	 */
	public String searchChannelName(String bypassYn, Map<String, String> params);

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param params
	 *            params
	 * @return BypassVO
	 */
	public BypassVO searchBypassUrl(Map<String, String> params);

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param params
	 *            params
	 * @return ServiceVO
	 */
	public ServiceInfo searchServiceMethod(Map<String, String> params);
}
