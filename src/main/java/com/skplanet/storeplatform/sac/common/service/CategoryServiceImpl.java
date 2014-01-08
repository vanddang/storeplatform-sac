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

import com.skplanet.storeplatform.sac.common.vo.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CommonService commonService;

	@Override
	public String getCategoryEngNm(String category) {

		Category cmCategory = this.commonService.getCategory().get(category);

		if (cmCategory == null)
			return "";

		return cmCategory.getCatEngNm();
	}

	@Override
	public String getCategoryCd(String category) {
		Category cmCategory = this.commonService.getCategory().get(category);

		if (cmCategory == null)
			return "";

		return cmCategory.getCatCd();
	}

	@Override
	public String getCategoryDesc(String category) {
		Category cmCategory = this.commonService.getCategory().get(category);

		if (cmCategory == null)
			return "";
		return cmCategory.getCatDesc();
	}

	@Override
	public List<Category> getCategoryList() {

		Iterator<Entry<String, Category>> iterator = this.commonService.getCategory().entrySet().iterator();

		List<Category> categoryList = new ArrayList<Category>();

		while (iterator.hasNext()) {
			categoryList.add(iterator.next().getValue());
		}

		return categoryList;
	}

}
