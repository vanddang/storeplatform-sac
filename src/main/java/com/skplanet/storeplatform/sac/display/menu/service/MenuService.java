package com.skplanet.storeplatform.sac.display.menu.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

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
	 * 테넌트 메뉴 조회
	 * </pre>
	 * 
	 * @param MenuSacReq
	 *            reqeustVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return MenuListSacRes
	 */
	public MenuListSacRes searchMenuList(MenuSacReq reqeustVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 테넌트 메뉴 상세 조회
	 * </pre>
	 * 
	 * @param MenuSacReq
	 *            reqeustVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return MenuListSacRes
	 */
	public MenuDetailSacRes searchMenuDetail(MenuSacReq reqeustVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;
}
