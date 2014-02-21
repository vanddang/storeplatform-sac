package com.skplanet.storeplatform.sac.display.menu.service;

import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuDetailSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * MenuList Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 12. Updated by : 유시혁.
 */
public interface MenuService {

	/**
	 * <pre>
	 * 테넌트 메뉴 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            MenuSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return MenuListSacRes
	 */
	public MenuListSacRes searchMenuList(MenuSacReq requestVO, SacRequestHeader requestHeader);

	/**
	 * <pre>
	 * 테넌트 메뉴 상세 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            MenuSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return MenuListSacRes
	 */
	public MenuDetailSacRes searchMenuDetail(MenuSacReq requestVO, SacRequestHeader requestHeader);
}
