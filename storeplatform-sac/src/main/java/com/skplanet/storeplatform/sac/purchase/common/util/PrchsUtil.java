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
public class PrchsUtil {

	public static String getTopMenuID(String tenantGrpCd)
	{
		if(StringUtils.length(tenantGrpCd)>0)
			return StringUtils.substring(tenantGrpCd, 8, 12);
		else
			return null;
	}
}
