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

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Set;

/**
 * <p>
 * ObjectHashGenerator
 * VO의 구조에 따른 해시값을 생성한다.
 * </p>
 * Updated on : 2015. 08. 18 Updated by : 정희원, SK 플래닛.
 */
public class ObjectHashGenerator {

    private static final int HASH_CUT = 4;

    public static <T> String hash(Class<T> clazz) {
        if(clazz == null)
            throw new IllegalArgumentException();

        String objectTypeHash;
        Method[] methods = clazz.getMethods();
        Set<String> values = Sets.newTreeSet();

        for (Method mtd : methods) {
            String name = mtd.getName();

            if(!name.startsWith("get"))
                continue;

            if(Modifier.isPrivate(mtd.getModifiers()))
                continue;

            String fldNm = name.substring(3);
            if(Strings.isNullOrEmpty(fldNm))
                continue;

            Class<?> rtnTp = mtd.getReturnType();
            if(rtnTp == String.class || rtnTp == Integer.class || rtnTp == Date.class || rtnTp == Long.class)
                values.add(rtnTp.getSimpleName() + "_" + fldNm);
        }

        objectTypeHash = Integer.toHexString(StringUtils.join(values, "+").hashCode());
        objectTypeHash = objectTypeHash.substring(objectTypeHash.length() - HASH_CUT);

        return objectTypeHash;

    }
}
