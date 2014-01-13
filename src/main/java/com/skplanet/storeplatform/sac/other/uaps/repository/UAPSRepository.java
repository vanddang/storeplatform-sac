/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.uaps.repository;

import com.skplanet.storeplatform.external.client.uaps.vo.LimitSvcRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdChildRes;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UafmapRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UserRes;

/**
 * 
 * UAPS 외부연동 Repository
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
public interface UAPSRepository {

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

	/**
	 * 
	 * <pre>
	 * 고객 정보조회(MDN-MIN Mapping정보조회).
	 * </pre>
	 * 
	 * @param deviceId
	 *            deviceId
	 * @param type
	 *            type
	 * @return UserRes
	 */
	public UserRes getMapping(String deviceId, String type);

	/**
	 * 
	 * <pre>
	 * 고객 정보조회(명의자 인증).
	 * </pre>
	 * 
	 * @param custId
	 *            custId
	 * @param deviceId
	 *            deviceId
	 * @param type
	 *            type
	 * @return UserRes
	 */
	public UserRes getAuthorize(String custId, String deviceId, String type);

	/**
	 * 
	 * <pre>
	 * 단말 제조모델 조회.
	 * </pre>
	 * 
	 * @param deviceId
	 *            deviceId
	 * @param type
	 *            type
	 * @return UafmapRes
	 */
	public UafmapRes getDevice(String deviceId, String type);

	/**
	 * 
	 * <pre>
	 * 한도 소진정보 조회.
	 * </pre>
	 * 
	 * @param deviceId
	 *            deviceId
	 * @param type
	 *            type
	 * @return LimitSvcRes
	 */
	public LimitSvcRes getLimitSvc(String deviceId, String type);
}
