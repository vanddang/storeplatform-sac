/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.service.menu;

/**
 * <p>
 * MenuInfoService
 * </p>
 * Updated on : 2014. 11. 06 Updated by : 정희원, SK 플래닛.
 */
public interface MenuInfoService {

    /**
     * 메뉴 이름을 조회한다.
     * menuId에 해당하는 메뉴 정보가 존재하지 않는 경우 빈 문자열이 리턴되며,
     * menuId에 해당하는 메뉴 정보가 존재하나 요청하는 언어가 존재하지 않는 경우 서버에서 기본으로 설정된 언어로 리턴한다.
     * @param menuId
     * @param langCd
     * @return
     */
    String getMenuName(String menuId, String langCd);

    /**
     * 메뉴 상세 설명을 조회한다.
     * menuId에 해당하는 메뉴 정보가 존재하지 않는 경우 빈 문자열이 리턴되며,
     * menuId에 해당하는 메뉴 정보가 존재하나 요청하는 언어가 존재하지 않는 경우 서버에서 기본으로 설정된 언어로 리턴한다.
     * @param menuId
     * @param langCd
     * @return
     */
    String getMenuDesc(String menuId, String langCd);

    MenuInfo getMenuInfo(String menuId);
}
