/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.search.repository;

import com.skplanet.storeplatform.external.client.search.vo.EcSearchReq;
import com.skplanet.storeplatform.external.client.search.vo.EcSearchRes;

public interface SearchRepository {
	public EcSearchRes search(EcSearchReq ecSearchReq);
}
