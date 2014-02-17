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

import com.skplanet.icms.refactoring.deploy.DPAppProductVO;

public interface DPAppProductService {

	/**
	 * 
	 * @param pid
	 * @param epsdProdId
	 * @param dpYn
	 * @param dpCatNo
	 * @return
	 */
	List<DPAppProductVO> getDPAppProductList(String pid);
	
	/**
	 * 
	 * @param dpProdCat
	 */
	void insertDPAppProduct(DPAppProductVO dpAppProduct);

	/**
	 * 
	 * @param chnlPid
	 * @param epsdProdId
	 * @param dpCatNo
	 */
	void deleteDPAppProduct(String prodId);
}
