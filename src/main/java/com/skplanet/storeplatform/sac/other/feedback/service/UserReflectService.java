/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.service;

import java.util.List;

import com.skplanet.storeplatform.sac.other.feedback.vo.UserToBeReflected;

/**
 * 회원 파트에서 변경되는 회원 정보를 전달받기 위한 서비스 인터페이스
 *
 * Updated on : 2014. 02. 12. Updated by : 서대영
 */
public interface UserReflectService {

	/**
	 * 회원 정보를 전달 받아 기타 쪽 테이블을 업데이트 한다.
	 * @param users
	 * 			변경되야 할 회원 목록
	 */
	void reflectUsers(List<UserToBeReflected> users);

	void reflectUser(UserToBeReflected user);

}
