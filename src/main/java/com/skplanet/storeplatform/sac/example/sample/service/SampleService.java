/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.example.sample.service;

import java.util.List;

import com.skplanet.storeplatform.external.client.example.sample.vo.Sample;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.example.vo.SampleSacReq;
import com.skplanet.storeplatform.sac.client.internal.example.vo.SampleSacRes;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;

/**
*
* 샘플 Service (비즈니스 로직 구현)
*
* Updated on : 2014. 1. 2. Updated by : 서대영, SK Planet.
*/
public interface SampleService {

	Tenant findTenantFromDb(String tenantId);
	
	SampleSacRes findDummyFromLocalSci(SampleSacReq req);
	
	BannerInfoSacRes findBannerFromLocalSci(BannerInfoSacReq req);

	List<Sample> findListFromRemoteSci(Sample sample);

	Sample findOneFromRemoteSci(Integer no);
	
	SmsSendEcRes sendSms(SmsSendEcReq smsSendEcReq);

}
