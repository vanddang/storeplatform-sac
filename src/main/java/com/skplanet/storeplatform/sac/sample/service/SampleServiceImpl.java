/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.sample.vo.Sample;
import com.skplanet.storeplatform.sac.sample.repository.SampleRepository;

/**
*
* 샘플 Service (비즈니스 로직 구현)
*
* Updated on : 2014. 1. 2. Updated by : 서대영, SK Planet.
*/
@Service
public class SampleServiceImpl implements SampleService {

	@Autowired
	private SampleRepository repository;

	@Override
	public Sample getDetail(Sample sample) {
		return this.repository.getDetailFromEC(sample);
	}

}
