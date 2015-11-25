/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.internal.purchase.sci;

import com.skplanet.storeplatform.framework.core.proxy.SCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceInV2Req;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceInV2Res;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;

/**
 * 기구매체크.
 * 
 * Updated on : 2013. 12. 20. Updated by : 조용진, 엔텔스.
 */
@SCI
public interface ExistenceInternalSacSCI {

	/**
	 * 
	 * <pre>
	 * SAC제공 기구매체크 SCI.
	 * </pre>
	 * 
	 * @param existenceReq
	 *            요청
	 * @return ExistenceListRes
	 */
	public ExistenceListRes searchExistenceList(ExistenceReq existenceReq);

	/**
	 * 
	 * <pre>
	 * SAC제공 기구매체크 SCI.
	 * </pre>
	 * 
	 * @param existenceReq
	 *            요청
	 * @return ExistenceListRes
	 */
	public ExistenceInV2Res searchExistenceListV2(ExistenceInV2Req existenceInV2Req);
}
