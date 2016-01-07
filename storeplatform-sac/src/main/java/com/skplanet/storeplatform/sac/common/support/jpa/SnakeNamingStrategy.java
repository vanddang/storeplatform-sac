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

import com.google.common.base.CaseFormat;
import org.hibernate.boot.model.naming.EntityNaming;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.source.spi.AttributePath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * SnakeNamingStrategy
 * - Hibernate 5.x 호환
 * </p>
 * Updated on : 2015. 12. 24 Updated by : 정희원, SK 플래닛.
 */
public class SnakeNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    private static Pattern RX_CHECK = Pattern.compile("[A-Z0-9_]+");

    @Override
    protected String transformEntityName(EntityNaming entityNaming) {
        String name = super.transformEntityName(entityNaming);
        return toSnakeCase(name);
    }

    @Override
    protected String transformAttributePath(AttributePath attributePath) {
        return toSnakeCase(attributePath.getProperty());
    }

    protected String toSnakeCase(String name) {
        Matcher m = RX_CHECK.matcher(name);
        if(m.matches())
            return name;


        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, name);
    }

}
