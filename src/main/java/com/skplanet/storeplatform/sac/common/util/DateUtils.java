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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 날짜 변환 유틸
 * Updated on : 2014. 02. 05 Updated by : 정희원, SK 플래닛.
 */
public class DateUtils {

    /**
     * <p>문자열을 Date로 변환한다.</p>
     * <p>SAC에서 자주 사용되는 표현 패턴을 인식하여 처리해주며 현재 제공되는 패턴은 다음과 같다.</p>
     * <ul>
     *     <li>yyyyMMddHHmmss</li>
     * </ul>
     * <p>인식할 수 없는 패턴이거나 null이 주어지면 null을 리턴해준다.</p>
     * TODO SAC에서 사용중인 날짜표현 문자열 패턴을 수집하여 반영해야 함. 문의 및 요청은 정희원M에게 해주세요.
     * @param str the date to parse. nullable
     * @return the parsed date
     */
    public static Date parseDate(String str) {
        if(str == null)
            return null;

        SimpleDateFormat parser = new SimpleDateFormat();
        parser.applyPattern("yyyyMMddHHmmss");
        try {
            return parser.parse(str);
        }
        catch (ParseException pe) {
            return null;
        }

    }
}
