package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppRes;

public interface CategoryAppService {
	CategoryAppRes searchCategoryAppList(CategoryAppReq req);
}
