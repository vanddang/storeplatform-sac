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

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.skplanet.storeplatform.sac.member.domain.LimitTarget;
import com.skplanet.storeplatform.sac.member.domain.QLimitTarget;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 서비스 제한 정책 Repository
 * </p>
 * Updated on : 2016. 01. 05 Updated by : 임근대, SK 플래닛.
 */
@Repository
public class LimitTargetRepositoryImpl implements LimitTargetRepository {

    public static final QLimitTarget $ = QLimitTarget.limitTarget;

    @PersistenceContext(unitName = "puMbr")
    private EntityManager em;

    @Override
    public List<LimitTarget> findByLimitPolicyKeyAndLimtPolicyCdIn(String limtPolicyKey, List<String> limtPolicyCdList) {
        //UserMapper.xml retrievePolicyList
        JPAQuery query = new JPAQuery(em)
                .from($)
                .where($.endDt.gt(new Date()));

        if(StringUtils.isNotEmpty(limtPolicyKey))
            query.where($.limtPolicyKey.eq(limtPolicyKey));

        if(limtPolicyCdList != null && limtPolicyCdList.size() > 0) {
            BooleanBuilder builder = new BooleanBuilder();
            for(String limtPolicyCd : limtPolicyCdList) {
                builder.or($.limtPolicyCd.eq(limtPolicyCd));
            }
            query.where(builder);
        }

        return query.list($);
    }

    @Override
    public LimitTarget findOne(Integer seq) {
        return new JPAQuery(em)
                .from($)
                .where($.seq.eq(seq))
                .uniqueResult($);
    }

    @Override
    public LimitTarget findByLimitPolicyKeyAndLimtPolicyCd(String limtPolicyKey, String limtPolicyCd) {
        return new JPAQuery(em)
                .from($)
                .where($.limtPolicyKey.eq(limtPolicyKey),
                        $.limtPolicyCd.eq(limtPolicyCd))
                .uniqueResult($);
    }


    @Override
    public void saveLimitPolicy(List<LimitTarget> limitTargets) throws ParseException {
        //UserMapper.xml updatePolicy

        // merge 쿼리 변환
        for(LimitTarget limitTarget : limitTargets) {
            LimitTarget findLimitTarget = findByLimitPolicyKeyAndLimtPolicyCd(limitTarget.getLimtPolicyKey(), limitTarget.getLimtPolicyCd());
            if(findLimitTarget == null) em.persist(limitTarget);
            else {
                DateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
                findLimitTarget.setEndDt(sdFormat.parse("29991231235959"));
                if(StringUtils.isNotEmpty(limitTarget.getPolicyApplyValue())) findLimitTarget.setPolicyApplyValue(limitTarget.getPolicyApplyValue());
                if(StringUtils.isNotEmpty(limitTarget.getUpdId())) findLimitTarget.setUpdId(limitTarget.getUpdId());
                if(limitTarget.getPmtType() != null) findLimitTarget.setPmtType(limitTarget.getPmtType());
                if(limitTarget.getUseYn() != null) findLimitTarget.setPmtType(limitTarget.getUseYn());
                if(StringUtils.isNotEmpty(limitTarget.getLimtAmt())) {
                    findLimitTarget.setPreLimtAmt(findLimitTarget.getLimtAmt());
                    findLimitTarget.setLimtAmt(limitTarget.getLimtAmt());
                }
            }
        }
    }

    public Long updateLimitPolicyHistory(Integer seq, String updId) {
        //UserMapper.xml updatePolicyHistory
        Date now = new Date();
        return new JPAUpdateClause(em, $)
                .where($.seq.eq(seq))
                .set($.updId, updId)
                .set($.endDt, now)
                .set($.updDt, now)
                .execute();
    }

    public Long updateLimtPolicyKey(String orgLimitPolicyKey, String newLimtPolicyKey) {
        //UserMapper.xml updatePolicyKey
        return new JPAUpdateClause(em, $)
                .where($.limtPolicyKey.eq(orgLimitPolicyKey))
                .set($.limtPolicyKey, newLimtPolicyKey)
                .execute();
    }

    public Long updatePolicyApplyValue(String orgPolicyApplyValue, String newPolicyApplyValue) {
        //UserMapper.xml updatePolicyValue
        return new JPAUpdateClause(em, $)
                .where($.policyApplyValue.eq(orgPolicyApplyValue))
                .set($.policyApplyValue, newPolicyApplyValue)
                .execute();
    }
}
