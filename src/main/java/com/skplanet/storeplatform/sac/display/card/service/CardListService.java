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

import com.skplanet.storeplatform.sac.client.product.vo.Panel;
import com.skplanet.storeplatform.sac.display.card.vo.PreferredCategoryInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.SegmentInfo;

/**
 * <p>
 * CardListService
 * </p>
 * Updated on : 2014. 10. 08 Updated by : 정희원, SK 플래닛.
 */
public interface CardListService {

    Panel listInPanel(String tenantId, String langCd, String pannelId, SegmentInfo sgmtKey, PreferredCategoryInfo preferredCategoryInfo, boolean disableCardLimit);
}
