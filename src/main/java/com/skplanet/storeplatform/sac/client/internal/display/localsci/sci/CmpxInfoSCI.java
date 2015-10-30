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
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxBasicInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxBasicInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductListRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductSacReq;

/**
 * 이용권 정보 조회.
 * 
 * Updated on : 2015. 5. 07. Updated by : 김형식 , 지티소프트
 */
@SCI
public interface CmpxInfoSCI {

	/**
	 * <pre>
	 * 이용권 기본 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxBasicInfoSacRes 상품 메타 정보 리스트
	 */
	CmpxBasicInfoSacRes searchCmpxBasicInfoList(CmpxBasicInfoSacReq req);
	
	/**
	 * <pre>
	 * 이용권에 등록된 상품 정보 조회 .
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxProductSacReq 상품 메타 정보 리스트
	 */
	CmpxProductListRes searchCmpxProductList(CmpxProductSacReq req);
	
	
	/**
	 * <pre>
	 * 이용권 및 에피소드 상품 정보 조회 .
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxProductInfoSacReq 상품 메타 정보 리스트
	 */
	CmpxProductInfo searchCmpxProductInfo(CmpxProductInfoSacReq req);
	

}
