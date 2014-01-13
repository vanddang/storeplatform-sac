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
 * UAPS 조회 Service
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
public interface UAPSService {

	/**
	 * 
	 * <pre>
	 * 자번호를 이용하여 모번호 정보 조회.
	 * </pre>
	 * 
	 * @param opmdMdn
	 *            opmdMdn
	 * @return OpmdRes
	 */
	public OpmdRes getOpmd(String opmdMdn);

	/**
	 * 
	 * <pre>
	 * 모번호를 이용하여 자번호 리스트 조회.
	 * </pre>
	 * 
	 * @param deviceId
	 *            deviceId
	 * @param type
	 *            type
	 * @return OpmdChildRes
	 */
	public OpmdChildRes getOpmdChild(String deviceId, String type);

}
