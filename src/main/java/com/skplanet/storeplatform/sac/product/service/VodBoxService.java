package com.skplanet.storeplatform.sac.product.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.product.vo.vod.VodBoxListResponse;
import com.skplanet.storeplatform.sac.client.product.vo.vod.VodBoxRequest;

/**
 * Music Contents List Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
public interface VodBoxService {

	/**
	 * <pre>
	 * Music Contents 리스트 조회.
	 * </pre>
	 * 
	 * @param MusicContentsRequest
	 *            requestVO
	 * @return MusicContentsListResponse
	 */
	public VodBoxListResponse searchVodBoxList(VodBoxRequest requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception;

}
