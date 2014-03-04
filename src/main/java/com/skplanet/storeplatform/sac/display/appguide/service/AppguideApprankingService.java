/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.appguide.service;

import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideApprankingSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.appguide.AppguideApprankingSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 
 * Updated on : 2014. 3. 3. Updated by : 이승훈, 엔텔스.
 */
public interface AppguideApprankingService {
	/**
	 * <pre>
	 * Feature VOD 카테고리 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return AppguideApprankingRes
	 */
	AppguideApprankingSacRes searchApprankingList(AppguideApprankingSacReq req, SacRequestHeader header);
}
