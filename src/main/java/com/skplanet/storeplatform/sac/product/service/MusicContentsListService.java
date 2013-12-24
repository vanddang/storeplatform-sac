package com.skplanet.storeplatform.sac.product.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.product.vo.music.MusicContentsListResponse;
import com.skplanet.storeplatform.sac.client.product.vo.music.MusicContentsRequest;

/**
 * Music Contents List Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
public interface MusicContentsListService {

	/**
	 * <pre>
	 * Music Contents 리스트 조회.
	 * </pre>
	 * 
	 * @param MusicContentsRequest
	 *            requestVO
	 * @return MusicContentsListResponse
	 */
	public MusicContentsListResponse searchMusicContentsList(MusicContentsRequest requestVO)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

}
