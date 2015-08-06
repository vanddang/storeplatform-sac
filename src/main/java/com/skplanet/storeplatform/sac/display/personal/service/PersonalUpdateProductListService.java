/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.personal.service;

import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateProductRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

import java.util.List;

/**
 * Updated on : 2015-08-06. Updated by : 양해엽, SK Planet.
 */
public interface PersonalUpdateProductListService {

	PersonalUpdateProductRes searchUpdateProductList(PersonalUpdateProductReq req, SacRequestHeader header,
													 List<String> packageInfoList);
}
