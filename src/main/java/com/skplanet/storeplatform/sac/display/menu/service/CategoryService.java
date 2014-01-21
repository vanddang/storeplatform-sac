package com.skplanet.storeplatform.sac.display.menu.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.CategoryListRes;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Category Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 20. Updated by : 윤주영, SK 플래닛.
 */
public interface CategoryService {

	/**
	 * <pre>
	 * 대분류 전시 Menu 조회.
	 * </pre>
	 * 
	 * @param MenuReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return CategoryListRes
	 */
	public CategoryListRes searchTopCategoryList(MenuReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 세분류 전시 메뉴 조회.
	 * </pre>
	 * 
	 * @param MenuReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return CategoryDetailRes
	 */
	public CategoryDetailRes searchDetailCategoryList(MenuReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

	/**
	 * <pre>
	 * 세분류 전시 메뉴 조회.
	 * </pre>
	 * 
	 * @param MenuReq
	 *            requestVO
	 * @param SacRequestHeader
	 *            requestHeader
	 * @return CategoryDetailRes
	 */
	public CategoryDetailRes searchSubCategoryList(MenuReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;
}
