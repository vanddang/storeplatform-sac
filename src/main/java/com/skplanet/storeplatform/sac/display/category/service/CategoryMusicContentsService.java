package com.skplanet.storeplatform.sac.display.category.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsListRes;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Music Contents List Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
public interface CategoryMusicContentsService {

	/**
	 * <pre>
	 * Music Contents 리스트 조회.
	 * </pre>
	 * 
	 * @param MusicContentsRequest
	 *            requestVO
	 * @return MusicContentsListResponse
	 */
	public MusicContentsListRes searchMusicContentsList(MusicContentsReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception;

}
