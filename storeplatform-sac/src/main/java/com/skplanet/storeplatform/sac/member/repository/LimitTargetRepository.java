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

import com.skplanet.storeplatform.sac.member.domain.LimitTarget;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 * 서비스 제한 정책
 * </p>
 * Updated on : 2016. 01. 05. Updated by : 임근대, SK 플래닛.
 */
public interface LimitTargetRepository {

    /**
     * Seq 값으로 서비스 정책 조회
     * @param seq
     * @return
     */
    LimitTarget findOne(Integer seq);

    /**
     * 서비스 정책 조회
     * @param limtPolicyKey 제한정책 키
     * @param limtPolicyCd 제한정책 코드
     * @return LimitTarget 서비스 제한 정책
     */
    LimitTarget findByLimitPolicyKeyAndLimtPolicyCd(String limtPolicyKey, String limtPolicyCd);

    /**
     * 서비스 정책 목록 조회
     * @param limtPolicyKey 제한정책 키
     * @param limtPolicyCdList 제한정책 코드 목록
     * @return List 서비스 제한 정책 목록
     */
    List<LimitTarget> findByLimitPolicyKeyAndLimtPolicyCdIn(String limtPolicyKey, List<String> limtPolicyCdList);

    /**
     * 서비스 제한 정책정보를 등록/수정
     * @param limitTargets 서비스 제하 정책 목록
     */
    void saveLimitPolicy(List<LimitTarget> limitTargets) throws ParseException;

    /**
     * 서비스 제한 정책 업데이트 History
     * @param seq 제한정책 키
     * @param updId 업데이트 사용자 Id
     * @return 업데이트 건수
     */
    Long updateLimitPolicyHistory(Integer seq, String updId);

    /**
     * Policy Key 일괄 업데이트
     * @param orgLimitPolicyKey 원본 Policy Key
     * @param newLimitPolicyKey 신규 Policy Key
     * @return 업데이트 건수
     */
    Long updateLimtPolicyKey(String orgLimitPolicyKey, String newLimitPolicyKey);

    /**
     * Policy Value 일괄 업데이트
     * @param orgPolicyApplyValue 원본 Policy Value
     * @param newPolicyApplyValue 신규 Policy Value
     * @return 업데이트 건수
     */
    Long updatePolicyApplyValue(String orgPolicyApplyValue, String newPolicyApplyValue);
}
