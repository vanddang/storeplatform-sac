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

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveGetScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveGetScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.MileageSaveScRes;

/**
 * Class 마일리지 적립정보 조회 기능을 제공하는 Interface
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
@SCI
public interface MileageSaveSCI {

	/**
	 * 마일리지 적립정보 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            마일리지 적립정보 요청
	 * @return MileageSaveScRes
	 */
	public MileageSaveScRes searchMileageSave(MileageSaveScReq request);

	/**
	 * 마일리지 적립정보 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            마일리지 적립정보 요청
	 * @return MileageSaveScRes
	 */
	public MileageSaveGetScRes getMileageSave(MileageSaveGetScReq request);

}
