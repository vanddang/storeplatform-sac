/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.BoughtTogetherProductSacV2Req;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.related.vo.BoughtTogetherProduct;

/*
 * BoughtTogetherProductTypeService 인터페이스
 * <pre>
 * Updated on 2014. 08. 07. by 서대영, SK 플래닛
 * </pre>
 */
public interface BoughtTogetherProductTypeService {

	/*
	 * QuryString과 Header를 이용하여 VO를 만든다.
	 */
	BoughtTogetherProduct fromReq(BoughtTogetherProductSacReq req, SacRequestHeader header);

}
