package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarMovieSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarMovieSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * Class 설명
 *
 * Updated on : 2015. 3. 16.
 * Updated by : 문동선M
 */
public interface SimilarMovieService {

	/**
	 * <pre>
	 * TB_DP_PROD_SIMILAR 에서 특정 상품의 유사 영화 목록 조회
	 * </pre>
	 * @param requestVO
	 * @param requestHeader
	 * @return
	 */
	SimilarMovieSacRes searchSimilarMovieList(SimilarMovieSacReq requestVO, SacRequestHeader requestHeader);

}
