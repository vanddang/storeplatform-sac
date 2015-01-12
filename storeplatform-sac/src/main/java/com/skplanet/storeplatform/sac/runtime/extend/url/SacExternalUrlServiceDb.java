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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.common.service.RoutingDataService;
import com.skplanet.storeplatform.sac.runtime.common.vo.Bypass;

/**
 * SAC 외부 호출용 URL 생성기 (EC 호출)
 *
 * Created on 2014. 05. 29. by 서대영, SK 플래닛.
 */
@Service // (SacExternalUrlServiceDb 적용 후 문제가 생기면 비활성화 요망)
public class SacExternalUrlServiceDb implements SacExternalUrlService {

	@Autowired
	private RoutingDataService dataSvc;

	@Override
	public UriComponentsBuilder buildUrl(String innerRequestURI, String interfaceId) {
		Interface intf = new Interface(interfaceId);
		Bypass bypass;

		try {
			bypass = this.dataSvc.selectBypassByInterface(intf);
		} catch (Exception e) {
			// 인터페이스({0})의 바이패스 데이터 조회에 문제가 발생하였습니다.
			throw new StorePlatformException("SAC_CMN_0091", interfaceId, e);
		}

		if (bypass == null || bypass.getComponent() == null) {
			// 인터페이스({0})의 바이패스 데이터가 존재하지 않습니다.
			throw new StorePlatformException("SAC_CMN_0092", interfaceId);
		}

		try {
			SacExternalUrlBuilder.buildUrl(bypass);
		} catch (Exception e) {
			// 인터페이스({0})의 바이패스 URL 생성에 문제가 발생하였습니다.
			throw new StorePlatformException("SAC_CMN_0093", interfaceId);
		}

		return SacExternalUrlBuilder.buildUrl(bypass);
	}

}
