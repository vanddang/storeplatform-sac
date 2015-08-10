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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.example.sample.sci.SampleSCI;
import com.skplanet.storeplatform.external.client.example.sample.vo.Sample;
import com.skplanet.storeplatform.external.client.message.sci.MessageSCI;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcRes;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.BannerInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.BannerInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.example.sci.SampleLocalSCI;
import com.skplanet.storeplatform.sac.client.internal.example.vo.SampleSacReq;
import com.skplanet.storeplatform.sac.client.internal.example.vo.SampleSacRes;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;

/**
*
* 샘플 Service (비즈니스 로직 구현)
*
* Updated on 2014/01/02 by 서대영, SK Planet.
* Updated on 2015/07/02 by 서대영 : DB 연동, LocalSCI 테스트를 위한 메서드 추가  
* Updated on 2015/07/03 by 서대영 : RemoteSCI 테스트를 위한 메서드 추가
* Updated on 2015/07/06 by 서대영 : ArgumentResolver 테스트를 위한 메서드 추가
*/
@Service
public class SampleServiceImpl implements SampleService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	
	@Autowired
	private SampleLocalSCI dummyLocalSci;
	
	@Autowired
	private BannerInfoSCI bannerLocalSci;

	@Autowired
	private SampleSCI dummyRemoteSci;
	
	@Autowired
	private MessageSCI messageRemoteSci;

	@Override
	public Tenant findTenantFromDb(String tenantId) {
		return commonDAO.queryForObject("AclTenant.selectTenant", tenantId, Tenant.class);
	}
	
	@Override
	public SampleSacRes findDummyFromLocalSci(SampleSacReq req) {
		return dummyLocalSci.searchSample(req);
	}

	@Override
	public BannerInfoSacRes findBannerFromLocalSci(BannerInfoSacReq req) {
		return bannerLocalSci.getBannerInfoList(req);
	}
	
	@Override
	public List<Sample> findListFromRemoteSci(Sample sample) {
		return dummyRemoteSci.search(sample);
	}

	@Override
	public Sample findOneFromRemoteSci(Integer no) {
		return dummyRemoteSci.detail(no);
	}

	@Override
	public SmsSendEcRes sendSms(SmsSendEcReq smsSendEcReq) {
		return messageRemoteSci.smsSend(smsSendEcReq);
	}

}
