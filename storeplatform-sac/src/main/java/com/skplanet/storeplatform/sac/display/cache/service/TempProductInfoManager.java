/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.service;

/**
 * <p>
 * 임시용 ProductInfoManager.
 * 이전 키 해제용
 * </p>
 * Updated on : 2014. 04. 07 Updated by : 정희원, SK 플래닛.
 */
public interface TempProductInfoManager {

    public void evictAllOldAppMeta();

}
