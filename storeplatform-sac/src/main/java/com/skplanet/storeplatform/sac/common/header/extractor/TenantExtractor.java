/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.header.extractor;

import org.springframework.web.context.request.NativeWebRequest;

import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;

/**
 * 헤더에서 테넌트 정보를 추출해주는 인터페이스
 *
 * Updated on : 2014. 4. 7.
 * Updated by : 서대영, SK 플래닛
 */
public interface TenantExtractor {

	TenantHeader extract(NativeWebRequest webRequest);

}
