/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import com.skplanet.storeplatform.sac.member.domain.mbr.QUserCaptcha;
import com.skplanet.storeplatform.sac.member.domain.mbr.UserCaptcha;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Captcha Repository
 * Updated on : 2016. 02. 07 Updated by : 임근대, SK 플래닛.
 */
@Repository
public class UserCaptchaRepositoryImpl implements UserCaptchaRepository {

    public static final QUserCaptcha $ = QUserCaptcha.userCaptcha;

    @PersistenceContext(unitName = "puMbr")
    private EntityManager em;

    @Override
    public void save(UserCaptcha userCaptcha) {
        em.persist(userCaptcha);
    }

    @Override
    public UserCaptcha findByImageSignAndSignData(String imageSign, String signData) {
        return new JPAQuery(em).from($)
                .where($.imageSign.eq(imageSign).and($.signData.eq(signData)))
                .uniqueResult($);
    }

    @Override
    public void remove(UserCaptcha userCaptcha) {
        em.remove(userCaptcha);
    }

}
