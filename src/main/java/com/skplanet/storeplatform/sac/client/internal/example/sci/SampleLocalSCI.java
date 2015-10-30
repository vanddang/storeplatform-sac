/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.example.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.example.vo.SampleSacReq;
import com.skplanet.storeplatform.sac.client.internal.example.vo.SampleSacRes;

/**
 * Sample
 *
 * Updated on : 2014. 02. 04. Updated by : 김상호
 */
@SCI
public interface SampleLocalSCI {

	/**
	 * searchSample.
	 *
	 * @param sampleSacReq
	 *            sampleSacReq 요청 Value Object.
	 * @return SampleSacRes - SampleSacRes 응답 Value Object
	 */
	public SampleSacRes searchSample(SampleSacReq sampleSacReq);

}
