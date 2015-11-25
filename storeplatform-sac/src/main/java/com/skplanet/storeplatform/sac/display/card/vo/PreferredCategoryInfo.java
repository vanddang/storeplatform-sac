/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.card.vo;

import com.skplanet.storeplatform.sac.client.display.vo.card.PreferredCategoryReq;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * PreferredCategoryInfo
 * PreferredCategory를 로직 안에서 편하게 접근 가능하도록 만든 객체
 * </p>
 * Updated on : 2014. 11. 06 Updated by : 정희원, SK 플래닛.
 */
public class PreferredCategoryInfo {

    private Map<String, List<String>> preferredCategpryMap;

    public PreferredCategoryInfo(List<PreferredCategoryReq> req) {
        preferredCategpryMap = new HashMap<String, List<String>>();
        if(req == null)
            return;

        if (CollectionUtils.isNotEmpty(req)) {
            for (PreferredCategoryReq pc : req) {
                if(CollectionUtils.isEmpty(pc.getPrefer()))
                    continue;

                List<String> menuList = new ArrayList<String>(pc.getPrefer().size());
                for (PreferredCategoryReq pc2 : pc.getPrefer()) {
                    menuList.add(pc2.getMenuId());
                }

                preferredCategpryMap.put(pc.getMenuId(), menuList);
            }
        }
    }

    public String getPreferMenu(String menuId, int pos) {
        List<String> cat = preferredCategpryMap.get(menuId);
        if(cat == null)
            return null;

        if(cat.size() <= pos)
            return null;

        return cat.get(pos);
    }
}
