/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.sample.sci.SampleSCI;
import com.skplanet.storeplatform.external.client.sample.vo.Sample;

/**
*
* 샘플 Repository (External Component 의 Sample API 호출)
*
* Updated on : 2014. 1. 2. Updated by : 서대영, SK Planet.
*/
@Component
public class SampleRepositoryImpl implements SampleRepository {

	@Autowired
	private SampleSCI sci;

	@Override
	public Sample getDetailFromEC(Sample sample) {
		int no = sample.getNo();
		return this.sci.detail(no);
	}
}
