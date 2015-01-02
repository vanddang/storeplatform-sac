/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.meta.service;

import com.skplanet.storeplatform.sac.display.meta.vo.CidPrice;

/**
 * <p>
 * 상품 메타 이외의 부가적인 데이터들의 캐시를 관리
 * </p>
 * Updated on : 2014. 12. 20 Updated by : 정희원, SK 플래닛.
 */
public interface ProductSubInfoManager {

    /**
     * CID를 이용하여 VOD, Ebook, Comic 상품의 가격을 조회한다.
     * @param tenantId
     * @param cid
     * @return
     */
    public CidPrice getCidPrice(String tenantId, String cid);
}
