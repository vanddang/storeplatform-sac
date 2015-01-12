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

import com.skplanet.storeplatform.sac.client.display.vo.other.OtherArtistReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherArtistRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 아티스트별 정보 조회 Service
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public interface OtherArtistService {
	/**
	 * <pre>
	 * 아티스트별 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 * @return OtherArtistRes
	 */
	public OtherArtistRes searchArtistDetail(OtherArtistReq req, SacRequestHeader header);
}
