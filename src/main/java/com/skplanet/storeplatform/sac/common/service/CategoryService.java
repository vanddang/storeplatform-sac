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

import java.util.List;

import com.skplanet.storeplatform.sac.client.common.vo.CmCategory;

public interface CategoryService {

	public String getCmCategoryEngNm(String topCatCd);

	public String getCmCategoryCd(String category);

	public List<CmCategory> getCmCategoryList();

}
