/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.example.sample.repository;

import java.util.List;

import com.skplanet.storeplatform.external.client.example.sample.vo.Sample;

/**
*
* 샘플 Repository (External Component 의 Sample API 호출)
*
* Updated on : 2014. 1. 2. Updated by : 서대영, SK Planet.
*/
public interface SampleRepository {

	List<Sample> searchFromEC(Sample sample);

	Sample detailFromEC(Integer no);

}
