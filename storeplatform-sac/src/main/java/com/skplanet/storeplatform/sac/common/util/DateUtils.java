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

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 날짜 변환 유틸
 * Updated on : 2014. 02. 05 Updated by : 정희원, SK 플래닛.
 */
public class DateUtils {

    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private final static Map<String, SimpleDateFormat> MAP_SDF = new LinkedHashMap<String, SimpleDateFormat>();

    static {
        /**
         * 적용하고 싶은 패턴을 나열한다. 콤마로 구분하며, 나열 순서대로 Try한다.
         * 패턴이 많아지면 차후 처리 패턴을 Properties 파일에 독립
         * FIXME yyyy-MM-dd -> yyyyMMdd 순서대로 기술해야 한다. yyyyMMdd가 yyyy-MM-dd형태를 그냥 처리 해버리기 때문
         */
        String[] ptns = "yyyyMMddHHmmss,yyyy-MM-dd,yyyyMMdd"
                        .split(",");

        for (String ptn : ptns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(ptn);
                MAP_SDF.put(ptn, sdf);
            }
            catch (IllegalArgumentException e) {
                logger.error("날짜유틸 {} 패턴 초기화시 오류가 발생했습니다.", ptn);
            }
        }
    }

    /**
     * <p>문자열을 Date로 변환한다.</p>
     * <p>SAC에서 자주 사용되는 표현 패턴을 인식하여 처리해주며 현재 제공되는 패턴은 다음과 같다.</p>
     * <ul>
     *     <li>yyyyMMddHHmmss</li>
     *     <li>yyyy-MM-dd</li>
     *     <li>yyyyMMdd</li>
     * </ul>
     * <p>인식할 수 없는 패턴이거나 null이 주어지면 null을 리턴해준다.</p>
     * @param str the date to parse. nullable
     * @return the parsed date
     */
    public static Date parseDate(String str) {
        if(StringUtils.isEmpty(str))
            return null;

        for (SimpleDateFormat sdf : MAP_SDF.values()) {
            try {
                return sdf.parse(str);
            }
            catch (IllegalArgumentException ae) {
//                ae.printStackTrace();
            }
            catch (ParseException pe) {
//                pe.printStackTrace();
            }
        }
        return null;
    }

    public static Date parseDate(String str, String ptn) {
        if(StringUtils.isEmpty(str) || StringUtils.isEmpty(ptn))
            return null;

        SimpleDateFormat parser = MAP_SDF.get(ptn);

        if (parser == null) {
            try {
                parser = new SimpleDateFormat(ptn);
                MAP_SDF.put(ptn, parser);
            }
            catch (IllegalArgumentException ie) {
                logger.error("날짜유틸 {} 패턴 초기화시 오류가 발생했습니다.", ptn);
            }
        }

        if (parser == null)
            return null;

        try {
            return parser.parse(str);
        }
        catch (ParseException pe) {
            return null;
        }
    }
}
