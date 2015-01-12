package com.skplanet.storeplatform.sac.display.menu.service;

import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuDetailSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * MenuServiceImpl 인터페이스
 * <pre>
 * Created on 2014. 02. 12. by 유시혁.
 * Updated on 2014. 07. 11. by 서대영, SK 플래닛 : 메서드 추가 (searchBestMenuList)
 * </pre>
 */
public interface MenuService {

    /**
     * Best 카테고리 조회
     */
	public MenuListSacRes searchBestMenuList(String menuCategoryCd, SacRequestHeader requestHeader);

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
