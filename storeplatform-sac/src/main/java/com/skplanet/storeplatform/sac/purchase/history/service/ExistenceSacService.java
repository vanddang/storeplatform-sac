/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import java.util.List;

import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacReqV2;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacResV2;

/**
 * 기구매체크 SAC Service 인터페이스
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
public interface ExistenceSacService {

	/**
	 * 기구매 체크 SAC Service.
	 * 
	 * @param existenceScReq
	 *            요청
	 * @param inputValue
	 *            내외부 사용구분 내부 true 요청정보
	 * @return List<ExistenceScRes>
	 */
	public List<ExistenceScRes> searchExistenceList(ExistenceScReq existenceScReq, String networkType);

	/**
	 * 기구매 체크 V2 SAC Service.
	 * 
	 * @param existenceScReq
	 *            요청
	 * @param inputValue
	 *            내외부 사용구분 내부 true 요청정보
	 * @return List<ExistenceScRes>
	 */
	public ExistenceSacResV2 searchExistenceListV2(ExistenceSacReqV2 existenceSacReqV2, String networkType);

}
