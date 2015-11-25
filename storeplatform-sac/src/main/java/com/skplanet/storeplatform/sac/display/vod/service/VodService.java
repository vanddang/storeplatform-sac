/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.vod.service;

import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailRes;

/**
 * VOD Controller
 *
 * Updated on : 2014-01-09
 * Updated by : 임근대, SK플래닛.
 */
public interface VodService {

	/**
	 * 채널ID를 조건으로 하여 VOD 상품 상세 정보를 조회한다.
	 * @param req
	 * 		요청 정보
     * @param supportFhdVideo Full HD 화질 지원 여부
	 * @return
	 * 		Vod Detail
	 */
	public VodDetailRes searchVod(VodDetailReq req, boolean supportFhdVideo);
	
	
	/**
	 * 채널ID를 조건으로 하여 VOD 상품 상세 정보를 조회한다.
	 * 
	 * @param req
	 * 		요청 정보
     * @param supportFhdVideo Full HD 화질 지원 여부
	 * @return
	 * 		Vod Detail
	 */
	public VodDetailRes searchVodV3(VodDetailReq req, boolean supportFhdVideo);	
}
