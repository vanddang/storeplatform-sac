/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.menu.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.menu.vo.MenuCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MenuDataService 클래스
 * <pre>
 * Created on 2014. 07. 11. by 서대영, SK 플래닛
 * </pre>
 */
@Service
public class MenuDataServiceImpl implements MenuDataService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	@Override
	public List<MenuCategory> selectBestMenuList( TenantHeader tenant, String menuCategoryCd ) {

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("tenantId", tenant.getTenantId());
		paramMap.put("menuCategoryCd", menuCategoryCd);

		List<MenuCategory> menuCategories = this.commonDAO.queryForList( "Menu.selectBestMenuList", paramMap, MenuCategory.class );

		for( MenuCategory menuCategory : menuCategories ) {
			menuCategory.setTenantId( tenant.getTenantId() );
		}

		return menuCategories;

	}

}
