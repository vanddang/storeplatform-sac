/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.meta.util;

import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

import java.lang.reflect.Method;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 04. 09 Updated by : 정희원, SK 플래닛.
 */
public class MetaBeanUtils {

    public static <T> void setProperties(T prop, MetaInfo meta) {
        if(prop == null || meta == null)
            return;

        for(Method mtd : prop.getClass().getDeclaredMethods()) {
            if(mtd.getName().startsWith("get")) {
                String fld = mtd.getName().substring(3);
                try {
                    Object v = mtd.invoke(prop);
                    // value가 null인 경우 Assign을 하지 않음
                    if(v != null) {
                        Method setMtd = MetaInfo.class.getDeclaredMethod("set"+fld, mtd.getReturnType());
                        setMtd.invoke(meta, mtd.invoke(prop));
                    }
                }
                catch(NoSuchMethodException nsme) {

                }
                catch(Exception e) {
                    throw new RuntimeException();
                }
            }
        }
    }
}
