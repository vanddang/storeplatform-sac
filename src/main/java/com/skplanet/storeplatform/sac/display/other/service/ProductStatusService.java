/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.service;

import com.skplanet.storeplatform.sac.display.other.vo.ParentAppInfo;

/**
 * <p>
 * 상품 상태 조회 API
 * </p>
 * Updated on : 2014. 02. 17 Updated by : 정희원, SK 플래닛.
 */
public interface ProductStatusService {

    public ParentAppInfo selectParentInfo(String tenantId, String partProdId);
}
