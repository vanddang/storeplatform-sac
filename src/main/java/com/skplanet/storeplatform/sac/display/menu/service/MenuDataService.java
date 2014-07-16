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

import java.util.List;

import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.menu.vo.Menu;

/**
 * MenuDataService 인터페이스
 * <pre>
 * Created on 2014. 07. 11. by 서대영, SK 플래닛
 * </pre>
 */
public interface MenuDataService {

    /**
     * Best 카테고리 조회
     */
	public List<Menu> selectBestMenuList(TenantHeader tenant, String menuCategoryCd);

}
