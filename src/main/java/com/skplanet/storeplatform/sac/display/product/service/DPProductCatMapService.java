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

import com.skplanet.icms.refactoring.deploy.DPProductCatMapVO;

public interface DPProductCatMapService {

	/**
	 * 
	 * @param pid
	 * @param epsdProdId
	 * @param dpYn
	 * @param dpCatNo
	 * @return
	 */
	List<DPProductCatMapVO> getDPProductCatList(String pid, String epsdProdId, String dpYn, String dpCatNo);
	
	/**
	 * 
	 * @param dpProdCat
	 */
	void insertDPProductCat(DPProductCatMapVO dpProdCat);

	/**
	 * 
	 * @param chnlPid
	 * @param epsdProdId
	 * @param dpCatNo
	 */
	void deleteDPProductCat(String prodId, String categoryNo);
}
