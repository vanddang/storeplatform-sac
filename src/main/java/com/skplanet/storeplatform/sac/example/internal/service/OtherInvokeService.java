/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.example.internal.service;

import java.util.List;

import com.skplanet.storeplatform.sac.client.internal.other.feedback.vo.UserReflectSacReq;

/**
 * 기타 파트 내부 API 호출 서비스
 *
 * Updated on : 2014. 2. 12.
 * Updated by : 서대영, SK 플래닛.
 */
public interface OtherInvokeService {

	void reflectOne(UserReflectSacReq req);

	void reflectList(List<UserReflectSacReq> reqList);

}


