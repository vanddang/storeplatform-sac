/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.stat.service;

import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.card.vo.PreferredCategoryInfo;
import com.skplanet.storeplatform.sac.display.stat.vo.StatLike;

/**
 * <p>
 * MemberSegmentService
 * </p>
 * Updated on : 2014. 11. 05 Updated by : 서대영, SK 플래닛.
 */
public interface StatMemberItemService {

	Object findItem(StatLike like, SacRequestHeader header, PreferredCategoryInfo preferredCategoryInfo);

	Card findCard(String cardId, String userKey, SacRequestHeader header, PreferredCategoryInfo preferredCategoryInfo);

	Product findProd(String prodId, SacRequestHeader header);

}
