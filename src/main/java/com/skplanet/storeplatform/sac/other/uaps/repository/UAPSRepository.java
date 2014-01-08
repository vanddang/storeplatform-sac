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

public interface UAPSRepository {

	public OpmdRes getOpmd(String opmdMdn);

	public OpmdChildRes getOpmdChild(String deviceId, String type);

	public UserRes getMapping(String deviceId, String type);

	public UserRes getAuthorize(String custId, String deviceId, String type);

	public UafmapRes getDevice(String deviceId, String type);

	public LimitSvcRes getLimitSvc(String deviceId, String type);
}
