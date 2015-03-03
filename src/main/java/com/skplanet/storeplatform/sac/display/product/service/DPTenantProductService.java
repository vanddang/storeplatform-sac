/*
 * Copyright (c) 2013 SK Planet, Inc.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK Planet, Inc.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you entered into with SK Planet.
 */
package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.refactoring.deploy.DPTenantProductVO;

import java.util.List;

public interface DPTenantProductService {

	/**
	 * 
	 * @param pid
	 * @param epsdProdId
	 * @param dpYn
	 * @param dpCatNo
	 * @return
	 */
	List<DPTenantProductVO> getDPTenant(String prodId, String TenantId);

    /**
     * @param dpProdCat
     * @param mapgProdId
     * @param mapgPkgNm
     */
    void insertDPTenant(DPTenantProductVO dpTenantProduct, String mapgProdId, String mapgPkgNm);

	/**
	 * 
	 * @param chnlPid
	 * @param epsdProdId
	 * @param dpCatNo
	 */
	void deleteDPTenant(String prodId);

    /**
     * 상품에 대해 배포된 테넌트 목록을 조회한다.
     * @param prodId 상품ID
     * @return 테넌트ID 목록
     */
    List<String> getDPTenantId(String prodId);
}
