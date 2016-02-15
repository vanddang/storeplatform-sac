/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.member.client.common.vo.*;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyKeyRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyKeyResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyValueRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdatePolicyValueResponse;

import java.util.List;

/**
 * 서비스 제한 정책
 * 
 * Updated on : 2015. 1. 7. Updated by : 임근대, SKP.
 */
public interface LimitTargetService {

	/**
	 * <pre>
	 * 제한 정책 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param limtPolicyKey
	 * @param limtPolicyCdList
	 * @return searchPolicyResponse - 제한 정책 목록 조회 응답 Value Object
	 */
	List<LimitTarget> searchPolicyList(String limtPolicyKey, List<String> limtPolicyCdList);

    /**
     * <pre>
     * 제한 정책 목록을 조회하는 기능을 제공한다.
     * </pre>
     *
     * @param mnoCd
     * @param limtPolicyKey
     * @param limtPolicyCdList
     * @return searchPolicyResponse - 제한 정책 목록 조회 응답 Value Object
     */
    List<LimitTarget> searchPolicyList(String mnoCd, String limtPolicyKey, List<String> limtPolicyCdList);

	/**
	 * <pre>
	 * 제한 정책 목록을 조회하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param searchPolicyRequest
	 *            제한 정책 목록 조회 요청 Value Object
	 * @return searchPolicyResponse - 제한 정책 목록 조회 응답 Value Object
	 */
	SearchPolicyResponse searchPolicyList(SearchPolicyRequest searchPolicyRequest);


	/**
	 * 서비스 제한 정책 등록
	 * @param limitTarget 서비스 제한 정책
	 * @return 저장 건수
	 */
	Integer saveLimitPolicy(LimitTarget limitTarget);

	/**
	 * <pre>
	 * 제한 정책정보를 등록/수정하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param updatePolicyRequest
	 *            제한 정책정보 등록/수정 요청 Value Object
	 * @return updatePolicyResponse - 제한 정책정보 등록/수정 응답 Value Object
	 */
	UpdatePolicyResponse updatePolicy(UpdatePolicyRequest updatePolicyRequest);

	/**
	 * <pre>
	 * 제한 정책정보 히스토리를 업데이트하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param updatePolicyRequest
	 *            UpdatePolicyRequest
	 * @return UpdatePolicyResponse
	 */
	UpdatePolicyResponse updatePolicyHistory(UpdatePolicyRequest updatePolicyRequest);

	/**
	 * <pre>
	 * 제한 정책정보를 등록하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param updatePolicyRequest
	 *            UpdatePolicyRequest
	 * @return UpdatePolicyResponse
	 */
	UpdatePolicyResponse insertPolicy(UpdatePolicyRequest updatePolicyRequest);

	/**
	 * <pre>
	 * 제한 정책정보 삭제하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param removePolicyRequest
	 *            제한 정책정보 삭제 요청 Value Object
	 * @return removePolicyResponse - 제한 정책정보 삭제 응답 Value Object
	 */
	RemovePolicyResponse removePolicy(RemovePolicyRequest removePolicyRequest);

	/**
	 * <pre>
	 * 제한 정책key 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param updatePolicyKeyRequest
	 *            제한 정책정보 key 수정 요청 Value Object
	 * @return updatePolicyKeyResponse - 제한 정책정보 key 수정 응답 Value Object
	 */
	UpdatePolicyKeyResponse updatePolicyKey(UpdatePolicyKeyRequest updatePolicyKeyRequest);

	/**
	 * <pre>
	 * 제한 정책 value 정보를 수정하는 기능을 제공한다.
	 * </pre>
	 *
	 * @param updatePolicyValueRequest
	 *            제한 정책정보 Value 수정 요청 Value Object
	 * @return updatePolicyValueResponse - 제한 정책정보 Value 수정 응답 Value Object
	 */
	UpdatePolicyValueResponse updatePolicyValue(UpdatePolicyValueRequest updatePolicyValueRequest);


    /**
     * <pre>
     * userKey, deviceKey로 제한 정책 목록을 조회하는 기능을 제공한다.
     * </pre>
     *
     * @param searchPolicyRequest
     *            제한 정책 목록 조회 요청 Value Object
     * @return searchPolicyResponse - 제한 정책 목록 조회 응답 Value Object
     */
    SearchPolicyResponse searchPolicyListByKey(SearchPolicyRequest searchPolicyRequest);

}
