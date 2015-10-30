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
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacRes;

/**
 * 소장/대여 상품 정보 조회
 * 
 * Updated on : 2014. 4. 15. Updated by : 홍지호, 엔텔스
 */
@SCI
public interface PossLendProductInfoSCI {

	/**
	 * <pre>
	 * 소장/대여 상품 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return PossLendProductInfoSacRes 소장/대여 상품 정보 리스트
	 */
	PossLendProductInfoSacRes searchPossLendProductInfo(PossLendProductInfoSacReq req);

}
