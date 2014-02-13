package com.skplanet.storeplatform.sac.display.category.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryVodBoxSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

public interface CategoryVodBoxService {

	/**
	 * <pre>
	 * Vod Box 리스트 조회.
	 * </pre>
	 * 
	 * @param CategoryVodBoxSacReq
	 *            requestVO
	 * @return CategoryVodBoxSacRes
	 */
	public CategoryVodBoxSacRes searchVodBoxList(CategoryVodBoxSacReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;
}
