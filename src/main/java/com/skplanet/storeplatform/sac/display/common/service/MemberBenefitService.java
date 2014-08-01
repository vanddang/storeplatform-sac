/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.service;

import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;

/**
 * <p>
 * 마일리지, 할인율 등 사용자 혜택 정보 조회 서비스
 * </p>
 * Updated on : 2014. 07. 25 Updated by : 정희원, SK 플래닛.
 */
public interface MemberBenefitService {

    /**
     * 마일리지 정보를 조회한다.
     *
     * @param tenantId
     * @param topMenuId
     * @param chnlId
     * @return
     */
    MileageInfo getMileageInfo(String tenantId, String topMenuId, String chnlId);

}
