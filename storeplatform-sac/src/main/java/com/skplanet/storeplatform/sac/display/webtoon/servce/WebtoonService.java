/**
 * 
 */
package com.skplanet.storeplatform.sac.display.webtoon.servce;

import com.skplanet.storeplatform.sac.client.display.vo.webtoon.WebtoonDetailSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.webtoon.WebtoonDetailSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 웹툰 상품 상세 조회
 * 
 * Updated on : 2014. 2. 19. Updated by : 조준일, nTels.
 */
public interface WebtoonService {

	/**
	 * <pre>
	 * 웹툰 상품 상세 조회.
	 * </pre>
	 * 
	 * @param req
	 *            UserDefine 파라미터
	 * @param header
	 *            공통헤더
	 * @return WebtoonDetailSacRes
	 */
	WebtoonDetailSacRes searchWebtoonDetail(WebtoonDetailSacReq req, SacRequestHeader header);

}
