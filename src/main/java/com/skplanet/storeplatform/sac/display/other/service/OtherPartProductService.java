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

import java.util.List;

import com.skplanet.storeplatform.sac.display.other.vo.PartProduct;

/**
 * <p>
 * DESC
 * </p>
 * Updated on : 2014. 03. 25 Updated by : 정희원, SK 플래닛.
 */
public interface OtherPartProductService {

    List<PartProduct> getPartProductList(String aid, String partProdId, String tenantId, String langCd);
}
