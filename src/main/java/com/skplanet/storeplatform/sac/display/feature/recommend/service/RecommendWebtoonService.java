package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendWebtoonSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 
 * Updated on : 2013. 12. 23. Updated by : 김형식, SK 플래닛.
 */
public interface RecommendWebtoonService {

	/**
	 * <pre>
	 * 운영자 추천 웹툰 리스트 조회.
	 * </pre>
	 * 
	 * @param req
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return RecommendWebtoonRes 리스트
	 */
	public RecommendWebtoonSacRes searchWebtoonList(SacRequestHeader header, RecommendWebtoonSacReq req);

}
