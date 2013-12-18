/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.flow.service;

import com.skplanet.storeplatform.sac.runtime.flow.vo.HeaderVO;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2013. 11. 26. Updated by : 김현일, 인크로스.
 */
public interface AuthService {

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param headerVO
	 *            headerVO
	 * @return boolean
	 */
	public boolean authentication(HeaderVO headerVO);

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param headerVO
	 *            headerVO
	 * @return boolean
	 */
	public boolean authorization(HeaderVO headerVO);

}
