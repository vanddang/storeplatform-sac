/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * <p>
 * JSON 처리용 유틸
 * </p>
 * Updated on : 2014. 10. 20 Updated by : 정희원, SK 플래닛.
 */
public class DisplayJsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(DisplayJsonUtils.class);

    /**
     * JSON 문자열을 객체로 변환한다.
     * notifyException값이 false인 경우 파싱 에러가 나면 기본 Map객체가 응답되므로 호출부에서 따로 null체크를 할 필요는 없다.
     * @param json JSON문자열
     * @param notifyException true 설정시 경우 파싱에러가 나는 경우 런타임 에러 발생
     * @return 결과 객체. 입력값에 따라 Map 또는 List 상속 객체가 응답된다.
     */
    public static Object parse(String json, boolean notifyException) {
        if(json == null)
            return new HashMap();

        JSONParser parser = new JSONParser();
        try {
            return parser.parse(json);
        }
        catch (ParseException e) {
            logger.error("JSON Parse Error. Value={}", json);
            if (notifyException) {
                throw new RuntimeException("JSON Parse Error");
            }
            return new HashMap();
        }
    }

    /**
     * JSON 문자열을 객체로 변환하며 파싱 오류를 무시한다.
     * @param json JSON문자열
     * @return 결과 객체. 입력값에 따라 Map 또는 List 상속 객체가 응답된다.
     */
    public static Object parse(String json) {
        return parse(json, false);
    }

    public static Map parseToMap(String json, boolean notifyException) {
        Object o = parse(json, notifyException);
        if(o == null)
            return new HashMap();

        if(o instanceof Map)
            return (Map)o;
        else {
            logger.error("JSON Convert Error (Map). Value={}", json);

            if(notifyException)
                throw new ClassCastException();

            return new HashMap();
        }
    }

    public static Map parseToMap(String json) {
        return parseToMap(json, false);
    }

    public static List parseToList(String json, boolean notifyException) {
        Object o = parse(json, notifyException);
        if(o == null)
            return new ArrayList();

        if(o instanceof List)
            return (List)o;
        else {
            logger.error("JSON Convert Error (List). Value={}", json);

            if(notifyException)
                throw new ClassCastException();

            return new ArrayList();
        }
    }

    public static List parseToList(String json) {
        return parseToList(json, false);
    }

    /**
     * List유형의 JSON 데이터를 Set형으로 변환한다.
     * @param json
     * @return
     */
    public static Set parseToSet(String json) {
        return new HashSet<Object>(parseToList(json));
    }
}

