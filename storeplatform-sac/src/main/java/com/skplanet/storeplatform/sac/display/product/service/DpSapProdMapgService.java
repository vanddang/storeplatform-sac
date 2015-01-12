/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.refactoring.deploy.DPSapMappingVO;

/**
 * <p>
 * DpSapProdMapg - insert, delete
 * </p>
 * Updated on : 2014. 11. 04 Updated by : 정희원, SK 플래닛.
 */
public interface DpSapProdMapgService {

    void insertDPSapProdMapg(DPSapMappingVO vo);

    void deleteDPSapProdMapg(String prodId);

}
