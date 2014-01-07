/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;

/**
 * 공통 기능을 임시로 정의해서 사용한다.
 * 
 * Updated on : 2014. 1. 7. Updated by : 심대진, 다모아 솔루션.
 */
@Component
public class MemberCommonComponent {

	private static final Logger logger = LoggerFactory.getLogger(MemberCommonComponent.class);

	@Autowired
	private UAPSSCI uapsSCI;

	/**
	 * <pre>
	 * 모번호 조회 (989로 시작하는 MDN이면 실행한다.)
	 * </pre>
	 * 
	 * @param mdn
	 * @return
	 */
	public String getOpmdMdnInfo(String mdn) {

		if (StringUtils.substring(mdn, 0, 3).equals("989")) {

			OpmdRes opmdRes = this.uapsSCI.getOpmdInfo(mdn);
			if (opmdRes.getResultCode() == 0) {
				mdn = opmdRes.getMobileMdn();
			} else {
				throw new RuntimeException("UAPS 연동 오류");
			}

		}

		return mdn;
	}

}
