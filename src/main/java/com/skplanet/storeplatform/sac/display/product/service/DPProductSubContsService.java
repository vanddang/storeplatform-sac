/*
 * Copyright (c) 2013 SK Planet, Inc.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK Planet, Inc.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you entered into with SK Planet.
 */
package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.refactoring.deploy.DPProductSubContsVO;

/**
 * 
 * @author 
 *
 */
public interface DPProductSubContsService {

	/**
	 * 
	 * @param dpSubConts
	 */
	void insertDPProductSubconts(DPProductSubContsVO dpSubConts);

	/**
	 * 
	 * @param pid
	 */
	void deleteDPProductSubconts(String subContsId, String pid);

}
