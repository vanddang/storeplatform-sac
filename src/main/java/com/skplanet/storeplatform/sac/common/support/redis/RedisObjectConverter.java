/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.support.redis;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>
 * RedisObjectConverter
 * Redis hash - Object 변환 제공
 * </p>
 * Updated on : 2015. 07. 06 Updated by : 정희원, SK 플래닛.
 */
public class RedisObjectConverter {

    public static <T> T hashToObject(Map<String, String> hash, Class<T> clazz) {
        return null;
    }

    public static Map<String, String> objectToHash(Object obj) {
        Map<String, String> res = new HashMap<String, String>();

        List<Method> methods = Arrays.asList(obj.getClass().getMethods());

        // getter를 조회
        Collection<Method> filteredMethods = Collections2.filter(methods, new Predicate<Method>() {
            @Override
            public boolean apply(Method input) {
                return input.getName().startsWith("get");
            }
        });
        for (Method mtd : filteredMethods) {

            try {
                Class<?> rtnTp = mtd.getReturnType();
                Object rawVal = mtd.invoke(obj);
                rtnTp.cast(rawVal);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
