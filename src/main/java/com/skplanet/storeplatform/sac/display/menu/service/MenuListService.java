package com.skplanet.storeplatform.sac.display.menu.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.product.vo.menu.MenuDetailResponse;
import com.skplanet.storeplatform.sac.client.product.vo.menu.MenuListResponse;
import com.skplanet.storeplatform.sac.client.product.vo.menu.MenuRequest;

/**
 * MenuList Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 19. Updated by : 윤주영, SK 플래닛.
 */
public interface MenuListService {

	/**
	 * <pre>
	 * Menu 리스트 조회.
	 * </pre>
	 * 
	 * @param String
	 *            tenantId, String systemId, String menuId Menu Request Value Object
	 * @return Menu 리스트
	 */
	public MenuListResponse searchMenuList(MenuRequest reqeustVO) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;

	/**
	 * <pre>
	 * Menu 상세 조회.
	 * </pre>
	 * 
	 * @param String
	 *            tenantId, String systemId, String menuId Menu Request Value Object
	 * @return Menu 상세
	 */
	public MenuDetailResponse searchMenu(MenuRequest reqeustVO) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;
}
