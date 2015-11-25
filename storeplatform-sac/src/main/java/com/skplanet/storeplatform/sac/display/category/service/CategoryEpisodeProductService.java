/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryEpisodeProductSacRes;
import com.skplanet.storeplatform.sac.display.category.vo.SearchEpisodeListParam;

/**
 * Updated on : 2015-07-30. Updated by : 양해엽, SK Planet.
 */
public interface CategoryEpisodeProductService {

	CategoryEpisodeProductSacRes searchEpisodeProductList(SearchEpisodeListParam param);
}
