/*
 * Copyright (c) 2013 SK Planet, Inc.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK Planet, Inc.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you entered into with SK Planet.
 */
package com.skplanet.storeplatform.sac.display.product.service;

import java.util.List;

import com.skplanet.icms.refactoring.deploy.DPProductRshpVO;

public interface DPProductRshpService {

	/**
	 * 
	 * @param pid
	 * @param epsdProdId
	 * @param dpYn
	 * @param dpCatNo
	 * @return
	 */
	List<DPProductRshpVO> getDPProductRshpList(String prodId, String partProdId, String dpYn, String prodRshpCd);
	
	/**
	 * 
	 * @param dpProdCat
	 */
	void insertDPProductRshp(DPProductRshpVO dpProductRshp);

	/**
	 * 
	 * @param chnlPid
	 * @param epsdProdId
	 * @param dpCatNo
	 */
	void deleteDPProductRshp(String prodId);
}
