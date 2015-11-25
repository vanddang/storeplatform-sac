/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.promotion;

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.util.StringUtils;

/**
 * <p>
 * PromotionEventUtils
 * </p>
 * Updated on : 2015. 10. 22 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEventUtils {

    /**
     * 프로모션 이벤트 조회에 필요한 키 파라메터를 생성한다.
     * @param prodId
     * @param menuId
     * @return
     */
    public static String[] makeKeys(String prodId, String menuId) {
        String menuOrTopMenuId = "",
                topMenuId = "";

        if(!Strings.isNullOrEmpty(menuId)) {

            menuOrTopMenuId = menuId;
            if(menuId.length() > 4) {
                topMenuId = menuOrTopMenuId.substring(0, 4);
            }
        }

        return new String[]{prodId, menuOrTopMenuId, topMenuId};
    }

    /**
     * 프로모션 이벤트 조회에 필요한 키 파라메터를 생성한다.
     * @param prodId
     * @param menuId
     * @return
     */
    public static String[] makeKeys(String iapProdId, String prodId, String menuId) {
        String menuOrTopMenuId = "",
                topMenuId = "";

        if(!Strings.isNullOrEmpty(menuId)) {

            menuOrTopMenuId = menuId;
            if(menuId.length() > 4) {
                topMenuId = menuOrTopMenuId.substring(0, 4);
            }
        }

        if(StringUtils.isNotEmpty(iapProdId))
            return new String[]{iapProdId, prodId, menuOrTopMenuId, topMenuId};
        else
            return new String[]{prodId, menuOrTopMenuId, topMenuId};
    }

}
