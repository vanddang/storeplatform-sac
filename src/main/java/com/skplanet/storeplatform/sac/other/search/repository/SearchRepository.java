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

import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchReq;
import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchRes;

/**
 * 
 * Search Repository
 * 
 * Updated on : 2014. 1. 13. Updated by : 김현일, 인크로스.
 */
public interface SearchRepository {

	/**
	 * 
	 * <pre>
	 * Search Repository 기능.
	 * </pre>
	 * 
	 * @param tstoreSearchReq
	 *            tstoreSearchReq
	 * @return TstoreSearchRes
	 */
	public TstoreSearchRes search(TstoreSearchReq tstoreSearchReq);
}
