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

import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * <p>
 * ImprovedJpaVendorAdapter
 * </p>
 * Updated on : 2016. 01. 07 Updated by : 정희원, SK 플래닛.
 */
public class ImprovedJpaVendorAdapter extends HibernateJpaVendorAdapter {

    private final JpaDialect jpaDialect = new ImprovedHibernateJpaDialect();

    @Override
    public JpaDialect getJpaDialect() {
        return this.jpaDialect;
    }

}
