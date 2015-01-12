/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * MQ설정에 필요한 유틸리티 메소드 제공
 * </p>
 * Updated on : 2014. 03. 12 Updated by : 정희원, SK 플래닛.
 */
public class MqUtils {

    /**
     * local환경의 경우 큐 이름에 로그온 사용자 이름을 접두어로 붙여준다.
     * @param nameList MQ이름들
     * @return 콤마로 연결된 MQ이름
     */
    public static String buildQueueName(final List<String> nameList) {
        List<String> resNameList;
        if(System.getProperty("spring.profiles.active", "local").equals("local")) {
            String prefix = System.getProperty("user.name", "local");
            resNameList = new ArrayList<String>();
            for(String name : nameList) {
                resNameList.add(prefix + "." + name);
            }
        }
        else {
            resNameList = nameList;
        }

        return StringUtils.join(resNameList, ",");
    }

    /**
     * local환경의 경우 큐 이름에 로그온 사용자 이름을 접두어로 붙여준다.
     * @param name MQ이름
     * @return 필요시 접두어가 적용된 MQ이름
     */
    public static String buildQueueName(final String name) {
        if(System.getProperty("spring.profiles.active", "local").equals("local")) {
            String prefix = System.getProperty("user.name", "local");
            return prefix + "." + name;
        }
        else {
            return name;
        }
    }
}
