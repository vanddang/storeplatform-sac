/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.example.sample.localsci;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.example.sci.SampleLocalSCI;
import com.skplanet.storeplatform.sac.client.internal.example.vo.SampleSacReq;
import com.skplanet.storeplatform.sac.client.internal.example.vo.SampleSacRes;

/**
 * SampleSCIController
 *
 * Created on 15/07/03 by Dale
 */
@LocalSCI
public class SampleLocalSCIController implements SampleLocalSCI {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SampleLocalSCIController.class);
	
	private static final Map<Integer, SampleSacRes> map;
	
	static {
		map = new HashMap<Integer, SampleSacRes>();
		for (int i = 1; i <= 25; i++) {
			SampleSacRes res = new SampleSacRes();
			res.setNo(i);
			res.setId("#" + i);
			res.setName("name " + i);
			res.setDescription("I'm " + i + "th element.");
			res.setDate(new Date());
			map.put(i, res);
		}
	}
	
	@Override
	public SampleSacRes searchSample(SampleSacReq sampleSacReq) {
		LOGGER.debug("# req : {}", sampleSacReq);
		int no = sampleSacReq.getNo();

		if (no == 0) {
			throw new StorePlatformException("SAC_CMN_9998");
		}
		
		SampleSacRes res = map.get(no);
		LOGGER.debug("# res : {}", res);
		return res;
	}

}
