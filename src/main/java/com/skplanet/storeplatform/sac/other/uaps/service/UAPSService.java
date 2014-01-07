/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.uaps.service;

import com.skplanet.storeplatform.external.client.uaps.vo.OpmdChildRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2014. 1. 7. Updated by : 홍길동, SK 플래닛.
 */
public interface UAPSService {

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param opmdMdn
	 * @return
	 */
	public OpmdRes getOpmd(String opmdMdn);

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param deviceId
	 * @param type
	 * @return
	 */
	public OpmdChildRes getOpmdChild(String deviceId, String type);
}
