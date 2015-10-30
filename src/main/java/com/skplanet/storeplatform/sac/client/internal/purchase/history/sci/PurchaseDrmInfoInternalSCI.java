/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.purchase.history.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.PurchaseDrmInfoSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.PurchaseDrmInfoSacInRes;

/**
 * 구매DRM정보 Interface
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
@SCI
public interface PurchaseDrmInfoInternalSCI {

	/**
	 * 다운로드 여부에 따른 DRM 정보 수정.
	 * 
	 * @param request
	 *            DRM요청
	 * @return PurchaseDrmInfoSacInRes
	 */
	public PurchaseDrmInfoSacInRes updatePrchaseDrm(PurchaseDrmInfoSacInReq request);

}
