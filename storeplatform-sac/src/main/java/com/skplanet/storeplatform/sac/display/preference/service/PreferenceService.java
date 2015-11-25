/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.preference.service;

import com.skplanet.storeplatform.sac.client.display.vo.preference.ListProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.preference.ListProductRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * <p>
 * PreferenceService
 * </p>
 * Updated on : 2014. 10. 31 Updated by : 서대영, SK 플래닛.
 */
public interface PreferenceService {

	ListProductRes listProduct(ListProductReq req, SacRequestHeader header);

}
