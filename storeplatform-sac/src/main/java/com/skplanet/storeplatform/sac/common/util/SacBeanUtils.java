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

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * VO객체 Value 비교 유틸.
 * </p>
 * Updated on : 2014. 09. 17 Updated by : 정희원, SK 플래닛.
 */
public class SacBeanUtils {

    /**
     * VO에서 특정 속성을 이름으로 얻어온다. 문자열 응답을 다루는 경우가 대다수여서 fieldType이 String인 경우에 대응하는 메소드이다.
     * @param obj 대상 객체
     * @param fieldName 속성명
     * @return
     */
    public static String getProperty(Object obj, String fieldName) {
        return getProperty(obj, fieldName, String.class);
    }

    /**
     * VO에서 특정 속성을 이름으로 얻어온다. getter에 특정한 규칙이 있을때 일괄 조회하는 상황에서 쓸 수 있다.
     * @param obj 대상 객체
     * @param fieldName 속성명
     * @param fieldType 속성 유형
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Object obj, String fieldName, Class<T> fieldType) {
        if(obj == null || StringUtils.isEmpty(fieldName) || fieldType == null)
            throw new IllegalArgumentException();

        String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            return (T)obj.getClass().getMethod(getterName).invoke(obj);
        }
        catch (NoSuchMethodException e) {
            return null;
        }
        catch(InvocationTargetException e) {
            return null;
        }
        catch(IllegalAccessException e) {
            return null;
        }
    }

    public static void beanDiff(Object obj1, Object obj2) {
        if(obj1 == null ^ obj2 == null)
            throw new RuntimeException("> Object different (oldObj="+(obj1 == null ? "null" : "exist")+", newObj="+(obj2 == null ? "null" : "exist")+")");

        BeanMap map = new BeanMap(obj1);
        PropertyUtilsBean propUtils = new PropertyUtilsBean();
        List<String> errMsg = new ArrayList<String>();

        try
        {
            for (Object propNameObject : map.keySet()) {
                String propertyName = (String) propNameObject;
                Object property1 = propUtils.getProperty(obj1, propertyName);
                Object property2 = propUtils.getProperty(obj2, propertyName);
                if(property1 == null ^ property2 == null) {
                    errMsg.add("> " + propertyName + " is different (1=\"" + property1 + "\", 2=\"" + property2 + "\")");
                }
                else if (property1 != null && property2 != null) {
                    if(!property1.equals(property2))
                        errMsg.add("> " + propertyName + " is different (1=\"" + property1 + "\", 2=\"" + property2 + "\")");
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(errMsg.size() > 0) {
            String conMsg = StringUtils.join(errMsg, "\n");
            throw new RuntimeException(conMsg);
        }
    }

    public static void diffByJson(Object obj1, Object obj2) {
        ObjectMapper om = new ObjectMapper();
        try {
            String s1 = om.writeValueAsString(obj1);
            String s2 = om.writeValueAsString(obj2);

            if (!StringUtils.equals(s1, s2)) {
                throw new RuntimeException("obj1=" + s1 + "\nobj2=" + s2);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("JSON Parse Error", e);
        }

    }

}
