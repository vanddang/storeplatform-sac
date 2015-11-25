/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.display.localsci.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassBasicInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassBasicInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassDrmInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfoSacReq;

/**
 * 정액제 상품 DRM 정보 조회.
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
@SCI
public interface FreePassInfoSCI {

	/**
	 * <pre>
	 * 정액제 상품 DRM 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return PaymentInfoSacRes 상품 메타 정보 리스트
	 */
	FreePassInfo searchFreePassDrmInfo(FreePassInfoSacReq req);
	
	
	/**
	 * <pre>
	 * 정액제 상품 DRM 정보 조회 V2.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return PaymentInfoSacRes 상품 메타 정보 리스트
	 */
	FreePassDrmInfo searchFreePassDrmInfoV2(FreePassInfoSacReq req);	

	/**
	 * <pre>
	 * 정액권의 에피소드 상품 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return EpisodeInfoSacRes 상품 메타 정보 리스트
	 */
	EpisodeInfoSacRes searchEpisodeList(EpisodeInfoReq req);

	/**
	 * <pre>
	 * 정액권 기본 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return EpisodeInfoSacRes 상품 메타 정보 리스트
	 */
	FreePassBasicInfoSacRes searchFreepassBasicList(FreePassBasicInfoSacReq req);

}
