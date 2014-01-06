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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.common.vo.CmCategory;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CommonService commonService;

	@Override
	public String getCmCategoryEngNm(String category) {

		CmCategory cmCategory = this.commonService.getCmCategory().get(category);

		return cmCategory.getCatEngNm();
	}

	@Override
	public String getCmCategoryCd(String category) {
		CmCategory cmCategory = this.commonService.getCmCategory().get(category);

		return cmCategory.getCatCd();
	}

	@Override
	public List<CmCategory> getCmCategoryList() {

		Iterator<Entry<String, CmCategory>> iterator = this.commonService.getCmCategory().entrySet().iterator();

		List<CmCategory> categoryList = new ArrayList<CmCategory>();

		while (iterator.hasNext()) {
			categoryList.add(iterator.next().getValue());
		}

		return categoryList;
	}

}
