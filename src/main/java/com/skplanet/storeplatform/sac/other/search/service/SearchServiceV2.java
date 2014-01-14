/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.search.service;

import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchRes;
import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchV2Req;

public interface SearchServiceV2 {
	public TstoreSearchRes search(TstoreSearchV2Req tstoreSearchV2Req);
}
