package com.skplanet.storeplatform.sac.product.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.product.vo.menu.CategoryListResponseVO;

/**
 * Category Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 20. Updated by : 윤주영, SK 플래닛.
 */
public interface CategoryService {

	/**
	 * <pre>
	 * Menu 리스트 조회.
	 * </pre>
	 * 
	 * @param String
	 *            tenantId, String systemId, String menuId Menu Request Value Object
	 * @return Menu 리스트
	 */
	public CategoryListResponseVO searchTopCategoryList(String tenantId, String systemId, String menuId)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * Menu 상세 조회.
	 * </pre>
	 * 
	 * @param String
	 *            tenantId, String systemId, String menuId Menu Request Value Object
	 * @return Menu 상세
	 */
	public CategoryListResponseVO searchDetailCategoryList(String tenantId, String systemId, String menuId)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;
}
