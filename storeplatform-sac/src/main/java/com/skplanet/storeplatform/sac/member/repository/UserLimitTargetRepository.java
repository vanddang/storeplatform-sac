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

import com.skplanet.storeplatform.sac.member.domain.mbr.UserLimitTarget;

import java.util.List;

/**
 * <p>
 * 서비스 제한 정책
 * </p>
 * Updated on : 2016. 01. 05. Updated by : 임근대, SK 플래닛.
 */
public interface UserLimitTargetRepository {

    /**
     * Seq 값으로 서비스 제한 정책 조회
     * @param seq 제한정책 seq
     * @return LimitTarget 서비스 제한 정책
     */
    UserLimitTarget findOne(Integer seq);

    /**
     * 서비스 제한 정책 존재 여부 조회
     * @param limtPolicyKey 제한정책 키
     * @param limtPolicyCd 제한정책 코드
     * @return 서비스 제한 정책 존재 여부
     */
    boolean existsByLimitPolicyKeyAndLimtPolicyCd(String limtPolicyKey, String limtPolicyCd);

    /**
     * 서비스 제한 정책 목록 조회
     * @param limtPolicyKey 제한정책 키
     * @param limtPolicyCdList 제한정책 코드 목록
     * @return List 서비스 제한 정책 목록
     */
    List<UserLimitTarget> findByLimitPolicyKeyAndLimtPolicyCdIn(String limtPolicyKey, List<String> limtPolicyCdList);

    /**
     * 서비스 제한 정책정보를 등록/수정
     * @param userLimitTarget 서비스 제한 정책
     */
    void saveLimitPolicy(UserLimitTarget userLimitTarget);

    /**
     * 서비스 제한 정책 업데이트 History
     * @param seq 제한정책 seq
     * @param updId 업데이트 사용자 Id
     * @return 업데이트 건수
     */
    Integer updateLimitPolicyHistory(Integer seq, String updId);

    /**
     * Policy Key 일괄 업데이트
     * @param orgLimitPolicyKey 원본 Policy Key
     * @param newLimitPolicyKey 신규 Policy Key
     * @return 업데이트 건수
     */
    Integer updateLimitPolicyKey(String orgLimitPolicyKey, String newLimitPolicyKey);

    /**
     * Policy Value 일괄 업데이트
     * @param orgPolicyApplyValue 원본 Policy Value
     * @param newPolicyApplyValue 신규 Policy Value
     * @return 업데이트 건수
     */
    Integer updatePolicyApplyValue(String orgPolicyApplyValue, String newPolicyApplyValue);

    /**
     * 서비스 제한 정책 삭제
     * endDt 를 오늘날짜로 설정한다
     * @param limtPolicyKey Policy Key
     * @param limtPolicyCd Policy Code
     * @return 결과 건수
     */
    Integer removePolicy(String limtPolicyKey, String limtPolicyCd);
}
