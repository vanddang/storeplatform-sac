package com.skplanet.storeplatform.sac.display.category.service;

import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsSacRes;
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
	 * @param requestVO
	 *            UserDefine 파라미터
	 * @param requestHeader
	 *            공통헤더
	 * @return MusicContentsListResponse
	 */
	public MusicContentsSacRes searchMusicContentsList(MusicContentsSacReq requestVO, SacRequestHeader requestHeader);

}
