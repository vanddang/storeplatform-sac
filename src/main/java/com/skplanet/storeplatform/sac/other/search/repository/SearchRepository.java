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

/**
 * 
 * 검색 E/C를 연동하는 Repository
 * 
 * Updated on : 2014. 1. 7. Updated by : 김현일, 인크로스
 */
public interface SearchRepository {
	/**
	 * 
	 * <pre>
	 * 검색 Repository 를 연동한다.
	 * </pre>
	 * 
	 * @param ecSearchReq
	 *            ecSearchReq
	 * @return EcSearchRes
	 */
	public EcSearchRes search(EcSearchReq ecSearchReq);
}
