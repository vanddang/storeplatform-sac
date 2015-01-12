package com.skplanet.storeplatform.sac.display.menu.service;

import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Category Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 02. 13. Updated by : 유시혁.
 */
public interface CategoryService {

	/**
	 * <pre>
	 * 대분류 전시 Menu 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            MenuSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return MenuListSacRes
	 */
	public MenuListSacRes searchTopCategoryList(MenuSacReq requestVO, SacRequestHeader requestHeader);

	/**
	 * <pre>
	 * 세분류 전시 메뉴 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            MenuSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return MenuListSacRes
	 */
	public MenuListSacRes searchSubCategoryList(MenuSacReq requestVO, SacRequestHeader requestHeader);
}
