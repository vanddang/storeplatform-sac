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

import com.skplanet.icms.refactoring.deploy.DPSeedMappVO;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;

/**
 * <pre>
 *  Seed Mapp Service 구현체
 * </pre>
 * 
 * created on : 2014-01-20 
 * created by : 차명호, ANB 
 */
@Service
public class DPSeedMappServiceImpl implements DPSeedMappService {

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;
	
	
	private static final String NAMESPACE ="DP_SEED_MAPP";

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPSeedMappService#insertDPSeedMapp(com.skplanet.icms.deploy.DPSeedMappVO)
	 */
	public void insertDPSeedMapp(DPSeedMappVO dpSeedMapp) {
		this.commonDAO.insert(NAMESPACE + ".insertDPSeedMapp", dpSeedMapp);
	}

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPSeedMappService#deleteDPSeedMapp(java.lang.String, java.lang.String)
	 */
	public void deleteDPSeedMapp(String prodId, String kindOpCd) {
		DPSeedMappVO parameter = new DPSeedMappVO();
		
		parameter.setProdId(prodId);
		parameter.setCaseRefCd(kindOpCd);
		
		this.commonDAO.delete(NAMESPACE + ".deleteDPSeedMapp", parameter);		
	}
	
	

}
