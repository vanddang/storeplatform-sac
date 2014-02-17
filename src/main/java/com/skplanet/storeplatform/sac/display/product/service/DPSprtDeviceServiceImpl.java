/*
 * Copyright (c) 2013 SK Planet, Inc.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK Planet, Inc.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you entered into with SK Planet.
 */
package com.skplanet.storeplatform.sac.display.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.icms.refactoring.deploy.DPSprtDeviceVO;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;

/**
 * <pre>
 *  전시 지원단말정보 관리
 * </pre>
 * 
 * created on : 2014-01-20 
 * created by : 차명호, ANB 
 */
@Service
public class DPSprtDeviceServiceImpl implements DPSprtDeviceService {

	private static final String NAMESPACE = "DP_SPRT_DEVICE";

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;

	/**
	 * 
	 * @param dpSprtHp
	 */
	public void insertDPSprtDevice(DPSprtDeviceVO dpSprtHp) {
		this.commonDAO.insert(NAMESPACE + ".insertDPSprtDevice", dpSprtHp);
	}

	/**
	 * 
	 * @param pid
	 */
	public void deleteDPSprtDevice(String pid, String phoneModelCd) {
		DPSprtDeviceVO parameter = new DPSprtDeviceVO();
		parameter.setProdId(pid);
		parameter.setDeviceModelCd(phoneModelCd);
		this.commonDAO.delete(NAMESPACE + ".deleteDPSprtDevice", parameter);
	}

}
