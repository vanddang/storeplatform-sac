/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.extend.url;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * SAC 외부 호출용 URL 생성기 (EC 호출)
 *
 * Created on 2014. 05. 29. by 서대영, SK 플래닛.
 */
public interface SacExternalUrlService {

	UriComponentsBuilder buildUrl(String innerRequestURI, String interfaceId);

}
