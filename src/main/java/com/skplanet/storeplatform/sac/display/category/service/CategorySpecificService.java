package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificRes;

public interface CategorySpecificService {
	public CategorySpecificRes getSpecificProductList(CategorySpecificReq req);
}
