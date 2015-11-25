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
 * - SimpleDateFormat은 Threadsafe하지 않아 문제가 발생하지 않도록 개선함
 * Updated on : 2014. 02. 05 Updated by : 정희원, SK 플래닛.
 */
public class DateUtils {

    private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private final static String[] ptns;

    static {
        /**
         * 적용하고 싶은 패턴을 나열한다. 콤마로 구분하며, 나열 순서대로 Try한다.
         * 패턴이 많아지면 차후 처리 패턴을 Properties 파일에 독립
         * FIXME yyyy-MM-dd -> yyyyMMdd 순서대로 기술해야 한다. yyyyMMdd가 yyyy-MM-dd형태를 그냥 처리 해버리기 때문
         */
        ptns = "yyyyMMddHHmmss,yyyy-MM-dd,yyyyMMdd"
                .split(",");
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

        for (String ptnStr : ptns) {

            try {
                SimpleDateFormat sdf = new SimpleDateFormat(ptnStr);
                return sdf.parse(str);
            }
            catch (IllegalArgumentException ae) {
//                ae.printStackTrace();
            }
            catch (ParseException pe) {
//                pe.printStackTrace();
            }
        }

        logger.warn("날짜 처리중 아무런 패턴도 사용되지 않음 (text: {})", str);
        return null;
    }

    public static Date parseDate(String str, String ptn) {
        if(StringUtils.isEmpty(str) || StringUtils.isEmpty(ptn))
            return null;

        try {
            SimpleDateFormat parser = new SimpleDateFormat(ptn);
            return parser.parse(str);
        }
        catch (IllegalArgumentException e) {
            logger.error("날짜 처리중 에러 (text: {}, pattern: {})", str, ptn, e);
            return null;
        }
        catch (ParseException pe) {
            logger.error("날짜 처리중 에러 (text: {}, pattern: {})", str, ptn, pe);
            return null;
        }
    }

    public static String format(Date dt) {
        if(dt == null)
            return null;

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        return fmt.format(dt);
    }
}
