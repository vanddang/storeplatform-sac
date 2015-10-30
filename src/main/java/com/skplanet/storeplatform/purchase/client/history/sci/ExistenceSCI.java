/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.history.sci;

import java.util.List;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScV2Req;

/**
 * 구매 컴포넌트 인터페이스
 * 
 * Updated on : 2013-12-10 Updated by : 조용진, 엔텔스.
 */
@SCI
public interface ExistenceSCI {

	/**
	 * 기구매 체크.
	 * 
	 * @param existenceScRequest
	 *            기구매 체크
	 * @return List<ExistenceScResponse>
	 */
	public List<ExistenceScRes> searchExistenceList(ExistenceScReq existenceScRequest);

	/**
	 * 기구매 체크.
	 * 
	 * @param existenceScRequest
	 *            기구매 체크
	 * @return List<ExistenceScResponse>
	 */
	public List<ExistenceScRes> searchExistenceListV2(ExistenceScV2Req existenceScV2Req);

}
