/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.support.jpa;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.function.StandardSQLFunction;

/**
 * <p>
 * SacOracle10gDialect
 * </p>
 * Updated on : 2016. 02. 02 Updated by : 정희원, SK 플래닛.
 */
public class SacOracle10gDialect extends Oracle10gDialect {

    public SacOracle10gDialect() {
        super();

        // 업무에 사용하는 DB 함수들을 등록한다.
        registerFunction("encrypt", new StandardSQLFunction("PKG_CRYPTO.ENCRYPT"));
        registerFunction("decrypt", new StandardSQLFunction("PKG_CRYPTO.DECRYPT"));
    }
}
