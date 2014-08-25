/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.Map;

import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * 특정 상품 Meta 정보 조회 서비스 인터페이스
 * 
 * Updated on : 2014. 8. 25. Updated by : 서대영, SK플래닛.
 */
public interface CategorySpecificMetaInfoService {

	public MetaInfo getSpecificEbookList(Map<String, Object> paramMap);

}
