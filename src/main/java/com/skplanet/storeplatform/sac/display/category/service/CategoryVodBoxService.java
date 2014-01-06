package com.skplanet.storeplatform.sac.display.category.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.vod.VodBoxListRes;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodBoxReq;

public interface CategoryVodBoxService {

	/**
	 * <pre>
	 * Vod Box 리스트 조회.
	 * </pre>
	 * 
	 * @param MusicContentsRequest
	 *            requestVO
	 * @return MusicContentsListResponse
	 */
	public VodBoxListRes searchVodBoxList(VodBoxReq requestVO) throws JsonGenerationException, JsonMappingException,
			IOException, Exception;
}
