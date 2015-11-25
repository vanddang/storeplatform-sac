/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.stat.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;
import com.skplanet.storeplatform.sac.display.stat.vo.StatLike;

/**
 * <p>
 * StatMemberDataServiceImpl
 * </p>
 * Updated on : 2014. 11. 04 Updated by : 서대영, SK 플래닛.
 */
@Service
public class StatMemberDataServiceImpl implements StatMemberDataService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	
	@Override
	public List<StatLike> selectStatLikeList(StatLike param) {
		List<StatLike> list = commonDAO.queryForList("StatLike.selectList", param, StatLike.class);
		if (list ==  null) {
			list = Collections.emptyList();
		}
		return list;
	}

	@Override
	public ListProduct selectProdcut(String prodId) {
		return commonDAO.queryForObject("ProductList.selectListProd", prodId, ListProduct.class);
	}
	
}
