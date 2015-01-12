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

import com.skplanet.storeplatform.sac.display.common.vo.ProductSubInfo;

/**
 * <p>
 * 프로비저닝 서비스.
 * 관련 속성들을 입력해주면 이용 가능 여부 체크
 * </p>
 * Updated on : 2014. 07. 09 Updated by : 정희원, SK 플래닛.
 */
public interface ProvisioningService {

    /**
     * 다운로드 가능여부 확인
     * 기구매 상품이 아닌 경우 에피소드ID로 가격 정보를 조회한다.
     *
     * @param tenantId
     * @param epsdId 에피소드ID
     * @param chnlProdStat 채널상품 상태
     * @param epsdProdStat 에피소드상품 상태
     * @param isPurchased 기구매 여부
     * @return 다운로드 가능한 경우 true
     */
    boolean selectDownloadable(String tenantId, String epsdId, String chnlProdStat, String epsdProdStat, boolean isPurchased);

    /**
     * 상품 부가정보 조회. SubContents, 테넌트 가격 정보를 조회한다.
     * @param tenantId
     * @param chnlId
     * @return
     */
    ProductSubInfo selectSubInfo(String tenantId, String chnlId);
}
