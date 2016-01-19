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
import com.skplanet.storeplatform.sac.member.domain.QUserLimitTarget;
import com.skplanet.storeplatform.sac.member.domain.UserLimitTarget;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 서비스 제한 정책 Repository
 * </p>
 * Updated on : 2016. 01. 05 Updated by : 임근대, SK 플래닛.
 */
@Repository
public class UserLimitTargetRepositoryImpl implements UserLimitTargetRepository {

    public static final QUserLimitTarget $ = QUserLimitTarget.userLimitTarget;

    @PersistenceContext(unitName = "puMbr")
    private EntityManager em;

    @Override
    public UserLimitTarget findOne(Integer seq) {
        return em.find(UserLimitTarget.class, seq);
    }

    @Override
    public boolean existsByLimitPolicyKeyAndLimtPolicyCd(String limtPolicyKey, String limtPolicyCd) {
        if(StringUtils.isEmpty(limtPolicyKey) || StringUtils.isEmpty(limtPolicyCd)) return false;
        return new JPAQuery(em)
                .from($)
                .where($.limtPolicyKey.eq(limtPolicyKey),
                        $.limtPolicyCd.eq(limtPolicyCd))
                .exists();
    }

    @Override
    public List<UserLimitTarget> findByLimitPolicyKeyAndLimtPolicyCdIn(String limtPolicyKey, List<String> limtPolicyCdList) {
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
    public void saveLimitPolicy(UserLimitTarget userLimitTarget)  {
        //UserMapper.xml updatePolicy

        // merge 쿼리 변환
        if(!existsByLimitPolicyKeyAndLimtPolicyCd(userLimitTarget.getLimtPolicyKey(), userLimitTarget.getLimtPolicyCd()))
            //insert
            em.persist(userLimitTarget);
        else {
            //update
            DateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmSS");
            Date endDt = null;
            try { endDt = sdFormat.parse("29991231235959"); } catch (ParseException ignore) {}

            List<UserLimitTarget> userLimitTargetList = findByLimitPolicyKeyAndLimtPolicyCdIn(userLimitTarget.getLimtPolicyKey(), Arrays.asList(userLimitTarget.getLimtPolicyCd()));
            for(UserLimitTarget findUserLimitTarget : userLimitTargetList) {
                findUserLimitTarget.setEndDt(endDt);
                if(StringUtils.isNotEmpty(userLimitTarget.getPolicyApplyValue())) findUserLimitTarget.setPolicyApplyValue(userLimitTarget.getPolicyApplyValue());
                if(StringUtils.isNotEmpty(userLimitTarget.getUpdId())) findUserLimitTarget.setUpdId(userLimitTarget.getUpdId());
                if(userLimitTarget.getPmtType() != null) findUserLimitTarget.setPmtType(userLimitTarget.getPmtType());
                if(userLimitTarget.getUseYn() != null) findUserLimitTarget.setPmtType(userLimitTarget.getUseYn());
                if(StringUtils.isNotEmpty(userLimitTarget.getLimtAmt())) {
                    findUserLimitTarget.setPreLimtAmt(findUserLimitTarget.getLimtAmt());
                    findUserLimitTarget.setLimtAmt(userLimitTarget.getLimtAmt());
                }}
        }
    }

    public Integer removePolicy(String limtPolicyKey, String limtPolicyCd) {
        //UserMapper.xml removePolicy
        Date now = new Date();
        return (int) new JPAUpdateClause(em, $)
                .where($.limtPolicyKey.eq(limtPolicyKey)
                    ,$.limtPolicyCd.eq(limtPolicyCd))
                .set($.endDt, now)
                .execute();

    }

    public Integer updateLimitPolicyHistory(Integer seq, String updId) {
        //UserMapper.xml updatePolicyHistory
        Date now = new Date();
        return (int) new JPAUpdateClause(em, $)
                .where($.seq.eq(seq))
                .set($.updId, updId)
                .set($.endDt, now)
                .set($.updDt, now)
                .execute();
    }

    public Integer updateLimitPolicyKey(String orgLimitPolicyKey, String newLimtPolicyKey) {
        assert orgLimitPolicyKey != null && orgLimitPolicyKey != null;
        //UserMapper.xml updatePolicyKey
        return (int) new JPAUpdateClause(em, $)
                .where($.limtPolicyKey.eq(orgLimitPolicyKey))
                .set($.limtPolicyKey, newLimtPolicyKey)
                .execute();
    }

    public Integer updatePolicyApplyValue(String orgPolicyApplyValue, String newPolicyApplyValue) {
        assert orgPolicyApplyValue != null && newPolicyApplyValue != null;
        //UserMapper.xml updatePolicyValue
        return (int) new JPAUpdateClause(em, $)
                .where($.policyApplyValue.eq(orgPolicyApplyValue))
                .set($.policyApplyValue, newPolicyApplyValue)
                .execute();
    }
}
