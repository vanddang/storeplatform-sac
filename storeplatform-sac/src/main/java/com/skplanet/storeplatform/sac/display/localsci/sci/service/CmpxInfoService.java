package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.*;

/**
 * 
 * 이용권 상품 정보 조회.
 * 
 * Updated on : 2015. 5. 07. Updated by : 김형식 , 지티소프트
 */
public interface CmpxInfoService {

	/**
	 * <pre>
	 * 이용권 기본 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxBasicInfoSacRes 
	 */
	CmpxBasicInfoSacRes searchCmpxBasicInfoList(CmpxBasicInfoSacReq req);
	
	/**
	 * <pre>
	 * 이용권에 등록된 상품 정보 조회 .
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxProductListRes 
	 */
	CmpxProductListRes searchCmpxProductList(CmpxProductSacReq req);
	
	
	/**
	 * <pre>
	 * 이용권 및 에피소드 상품 정보 조회 .
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxProductInfoSacReq 
	 */
	CmpxProductInfo searchCmpxProductInfo(CmpxProductInfoSacReq req);
}
