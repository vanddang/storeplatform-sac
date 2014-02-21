/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * CategoryApp Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 01. 03. Updated by : 이태희, SK 플래닛.
 */
public interface CategoryAppService {
	/**
	 * <pre>
	 * 일반 카테고리 앱 상품 조회.
	 * </pre>
	 * 
	 * @param req
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return 일반 카테고리 앱 상품 리스트
	 */
	CategoryAppSacRes searchAppList(CategoryAppSacReq req, SacRequestHeader header);
}
