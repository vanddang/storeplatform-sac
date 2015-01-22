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

import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTenantProductMappingRes;

/**
 * <p>
 * OtherTenantProductMappingService
 * </p>
 * Updated on : 2015. 01. 19 Updated by : 정희원, SK 플래닛.
 */
public interface OtherTenantProductMappingService {

    OtherTenantProductMappingRes getTenantProductMapping(String prodId);

}
