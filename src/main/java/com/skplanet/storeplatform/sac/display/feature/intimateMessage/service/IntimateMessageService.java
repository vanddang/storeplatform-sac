package com.skplanet.storeplatform.sac.display.feature.intimateMessage.service;

import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * IntimateMessage Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 2. 06. Updated by : 이태희
 */
public interface IntimateMessageService {
	/**
	 * <pre>
	 * IntimateMessage 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            requestHeader
	 * @param intimateMessageReq
	 *            intimateMessageReq
	 * @return IntimateMessageSacRes
	 */
	IntimateMessageSacRes searchIntimateMessageList(SacRequestHeader requestHeader,
			IntimateMessageSacReq intimateMessageReq);
}
