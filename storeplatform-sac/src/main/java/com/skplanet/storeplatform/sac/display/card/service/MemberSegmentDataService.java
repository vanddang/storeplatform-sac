/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.card.service;

import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSegmentSacRes;
import com.skplanet.storeplatform.sac.display.card.vo.MemberSegment;

/**
 * <p>
 * MemberSegmentDataService
 * </p>
 * Updated on : 2014. 11. 13 Updated by : 서대영, SK 플래닛.
 */
public interface MemberSegmentDataService {

	SearchUserSegmentSacRes searchUserSegment(String userKey, String deviceKey);

	MemberSegment selectMemberSegment(String tenantId, String userKey);

	/**
	 * <pre>
	 * 접속한 사용자가 카드를 테스트하기 위한 테스트 유저인지 여부를 확인한다.
	 * </pre>
	 * @param tenantId 테넌트 ID
	 * @param userMdn  사용자 MDN
	 * @return 카드 테스트 유저 여부
	 */
    String selectTestMdnYn( String tenantId, String userMdn );

}
