package com.skplanet.storeplatform.sac.display.category.service;

import java.util.Map;

import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

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

	/**
	 * <pre>
	 * 음원 Meta 정보 조회 챠트리스트 제외를 위해 메타 조회 신규 생성.
	 * </pre>
	 * 
	 * @param paramMap
	 *            paramMap
	 * @return MetaInfo
	 */
	public MetaInfo getMusicMetaInfo(Map<String, Object> paramMap);

}
