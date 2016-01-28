/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.purchase.common.util;

import com.skplanet.storeplatform.framework.core.util.StringUtils;

/**
 * 구매 관련 유틸.
 *
 * Updated on : 15. 12. 15.
 * Updated by : 황민규, SK 플래닛.
 */
public class PrchsUtils {

	/**
	 * 테넌트 그룹코드에서 Top menu ID를 알려준다.
	 *
	 * @param tenantGrpCd the tenant grp cd
	 * @return the top menu id
	 */
	public static String getTopMenuID(String tenantGrpCd)
	{
		if(StringUtils.length(tenantGrpCd)>0)
			return StringUtils.substring(tenantGrpCd, 8, 12);
		else
			return null;
	}

	/**
	 * 오프셋과 카운트 기준으로 시작row와 종료row를 계산한다.
	 *
	 * @param offset the offset
	 * @param count  the count
	 * @return the int [ ]
	 */
	public static int [] calRowcount(int offset, int count, int maxCount)
	{
		count = count > maxCount ? maxCount : count;

		int endRow = count * offset;
		int startRow = endRow - count + 1;

		return new int [] {startRow, endRow};
	}
}
