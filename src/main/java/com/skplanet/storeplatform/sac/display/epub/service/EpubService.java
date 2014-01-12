/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.epub.service;

import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubSeriesReq;
import com.skplanet.storeplatform.sac.client.display.vo.epub.EpubSeriesRes;

/**
 * EPUB Controller
 *
 * Updated on : 2014-01-09
 * Updated by : 임근대, SK플래닛.
 */
public interface EpubService {

	/**
	 * 채널 상품ID를 조건으로 하여 eBook/코믹 상품 상세 정보를 조회한다.
	 * @param req
	 * 		요청 정보
	 * @return
	 * 		EpubDetailRes EpubDetail
	 */
	public EpubDetailRes searchEpub(EpubDetailReq req);

	/**
	 * 채널 상품ID를 조건으로 하여 시리즈 상품 상세 정보를 조회한다.
	 * @param req
	 * 		요청 정보
	 * @return
	 * 		EpubSeriesRes EpubSeries
	 */
	public EpubSeriesRes searchEpubSeries(EpubSeriesReq req);

}
