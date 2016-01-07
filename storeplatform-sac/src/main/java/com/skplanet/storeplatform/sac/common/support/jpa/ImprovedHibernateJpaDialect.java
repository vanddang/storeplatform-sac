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

import org.hibernate.Session;
import org.springframework.jdbc.datasource.ConnectionHandle;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>
 * ImprovedHibernateJpaDialect
 * </p>
 * Updated on : 2016. 01. 07 Updated by : 정희원, SK 플래닛.
 */
public class ImprovedHibernateJpaDialect extends HibernateJpaDialect {

    @Override
    public ConnectionHandle getJdbcConnection(EntityManager entityManager, boolean readOnly) throws PersistenceException, SQLException {
        Session session = getSession(entityManager);
        return new HibernateConnectionHandle(session);
    }

    // Codes from HibernateJpaDialect
    private static class HibernateConnectionHandle implements ConnectionHandle {

        private final Session session;

        private static volatile Method connectionMethod;

        public HibernateConnectionHandle(Session session) {
            this.session = session;
        }

        public Connection getConnection() {
            try {
                if (connectionMethod == null) {
                    // reflective lookup to bridge between Hibernate 3.x and 4.x
                    connectionMethod = this.session.getClass().getMethod("connection");
                }
                return (Connection) ReflectionUtils.invokeMethod(connectionMethod, this.session);
            }
            catch (NoSuchMethodException ex) {
                throw new IllegalStateException("Cannot find connection() method on Hibernate session", ex);
            }
        }

        // Edited
        public void releaseConnection(Connection con) {
            /**
             * ## Comments from spring-orm:4.2.0.RELEASE
             *
             * Need to explicitly call close() with Hibernate 3.x in order to allow
             * for eager release of the underlying physical Connection if necessary.
             * However, do not do this on Hibernate 4.2+ since it would return the
             * physical Connection to the pool right away, making it unusable for
             * further operations within the current transaction!
             * TODO upgrade spring framework and deprecate this class
             */
        }
    }
}
