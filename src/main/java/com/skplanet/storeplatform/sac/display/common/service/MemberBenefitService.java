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
     * 마일리지 적립율 정보를 조회한다.
     * 상품에 맞는 정보를 찾고 없으면 해당 카테고리의 정보를 응답해준다.
     * @param tenantId		Tenant Id
     * @param menuId		topMenuId 또는 2-Depth menuId
     * @param chnlId 		채널ID
     * @param prodAmt		상품 가격 (예외 상품이 아닌 경우 무료 상품은 적립율을 노출하지 않는다.)
     * @return 마일리지 적립율 정보
     */
    MileageInfo getMileageInfo(String tenantId, String menuId, String chnlId, Integer prodAmt);

    /**
     * 마일리지 적립율 정보를 조회한다.
     * 상품에 맞는 정보를 찾고 없으면 해당 카테고리의 정보를 응답해준다.
     * @param tenantId		Tenant Id
     * @param menuId		topMenuId 또는 2-Depth menuId
     * @param chnlId 		채널ID
     * @param prodAmt		상품 가격 (예외 상품이 아닌 경우 무료 상품은 적립율을 노출하지 않는다.)
     * @return 마일리지 적립율 정보
     */
    MileageInfo getMileageInfo(String tenantId, String menuId, String chnlId, String userKey);

    /**
	 * 마일리지 적립율 무료 상품 여부 체크
	 * @param mileageInfo
	 * @param prodAmt
	 */
    MileageInfo checkFreeProduct(MileageInfo mileageInfo, Integer prodAmt);

}
