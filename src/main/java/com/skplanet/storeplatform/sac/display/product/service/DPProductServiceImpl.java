/*
 * Copyright (c) 2013 SK Planet, Inc.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK Planet, Inc.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you entered into with SK Planet.
 */
package com.skplanet.storeplatform.sac.display.product.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.icms.refactoring.deploy.DPProductVO;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;

/**
 * <pre>
 *  전시 상품정보 관리
 * </pre>
 * 
 * created on : 2014-01-20 
 * created by : 차명호, ANB 
 */
@Service
public class DPProductServiceImpl implements DPProductService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductService#insertDPProduct(com.skplanet.icms.deploy.DPProductVO)
	 */
	
	public void insertDPProduct(DPProductVO dpProd) {
		
		log.debug("*****DPProductServiceImpl insert***********************  {}",dpProd.getProdId());
		this.commonDAO.insert("DP_PRODUCT.insertDPProduct", dpProd);
	}

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductService#deleteDPProduct(java.lang.String)
	 */
	public void deleteDPProduct(String pid) {
		String prodId = pid;
		log.debug("*****DPProductServiceImpl delete***********************  {}",prodId);
		
		this.commonDAO.delete("DP_PRODUCT.deleteDPProduct", prodId);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductService#deleteDPPartProduct(java.lang.String)
	 */
	public void deleteDPPartProduct(String prodId) {
		DPProductVO parameter = new DPProductVO();
		log.debug("*****DPProductServiceImpl delete***********************  {}",prodId);
		parameter.setProdId(prodId);
		
		this.commonDAO.delete("DP_PRODUCT.deleteDPProduct", prodId);
	}	

	
}
