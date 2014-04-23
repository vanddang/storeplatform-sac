/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.service;

import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTMembershipReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTMembershipRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTMembershipUseStatusRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * 
 * Updated on : 2014. 3. 31. Updated by : 윤주영, SK 플래닛.
 */
public interface OtherTMembershipService {
	/**
	 * <pre>
	 * T Membership 할인율 조회
	 * </pre>
	 * 
	 * @param OtherTMembershipReq
	 * @return OtherTMembershipRes
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 */
	OtherTMembershipRes searchTMembership(OtherTMembershipReq req, SacRequestHeader header);

	/**
	 * <pre>
	 * T맴버십 할인 사용여부 조회
	 * </pre>
	 * 
	 * @return OtherTMembershipUseStatusRes
	 * 
	 * @param header
	 *            header
	 */
	OtherTMembershipUseStatusRes searchTMembershipUseStatus(SacRequestHeader header);

}
