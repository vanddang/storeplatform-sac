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

/**
 * <p>
 * 상품 부가정보 서비스.
 * 이것은 ICMS, Tenant에서 관리되지 않는 부가적인 관리정보들을 다루기 위해 존재한다.
 * </p>
 * Updated on : 2014. 10. 06 Updated by : 정희원, SK 플래닛.
 */
public interface ProductExtraInfoService {

    /**
     * 부가 정보 조회
     * @param prodId
     * @param infoClsfCd
     * @return 내용. 해당 내용이 없는 경우 null
     */
    String getInfo(String prodId, String infoClsfCd);

//    Object getInfoAsJSON(String prodId, String infoClsfCd);

}
