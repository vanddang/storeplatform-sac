/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.common.vo.CmCategory;
import com.skplanet.storeplatform.sac.client.common.vo.CmDevice;

@Service
public class CommonServiceImpl implements CommonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public CmDevice getCmDevice(String deviceModelCd) {

		LOGGER.debug("deviceModelCd : {}", deviceModelCd);

		return this.commonDAO.queryForObject("common.getDevice", deviceModelCd, CmDevice.class);
	}

	@Override
	public Map<String, CmCategory> getCmCategory() {
		Map<String, CmCategory> categoryMap = new HashMap<String, CmCategory>();
		categoryMap.put("DP000501", new CmCategory("DP000501", "게임", "game", "game"));
		categoryMap.put("DP000503", new CmCategory("DP000503", "FUN", "fun", "fun"));
		categoryMap.put("DP000504", new CmCategory("DP000504", "생활/위치", "living", "living"));
		categoryMap.put("DP000508", new CmCategory("DP000508", "어학/교육", "languageEducation", "languageEducation"));
		categoryMap.put("DP000513", new CmCategory("DP000513", "eBook", "ebook", "ebook"));
		categoryMap.put("DP000514", new CmCategory("DP000514", "Comic", "cartoon", "cartoon"));
		categoryMap.put("DP000515", new CmCategory("DP000515", "쇼셜쇼핑", "shoppingCoupon", "shoppingCoupon"));
		categoryMap.put("DP000516", new CmCategory("DP000516", "뮤직", "music", "music"));
		categoryMap.put("DP000517", new CmCategory("DP000517", "영화", "movie", "movie"));
		categoryMap.put("DP000518", new CmCategory("DP000518", "방송", "broadcast", "broadcast"));
		categoryMap.put("DP000528", new CmCategory("DP000528", "쇼핑", "shoppingStore", "shoppingStore"));
		return categoryMap;
	}
}
