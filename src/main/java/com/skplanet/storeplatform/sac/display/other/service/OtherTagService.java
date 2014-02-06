/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.service;

import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTagReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTagRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 태그 목록 조회 Service
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public interface OtherTagService {
	/**
	 * <pre>
	 * 태그 목록 조회
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return OtherTagRes
	 */
	public OtherTagRes searchTagList(OtherTagReq req, SacRequestHeader header);
}
