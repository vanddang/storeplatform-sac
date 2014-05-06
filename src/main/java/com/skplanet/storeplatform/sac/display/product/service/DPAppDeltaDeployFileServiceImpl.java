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

import com.skplanet.icms.refactoring.deploy.DPAppDeltaDeployFileVO;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;

/**
 * <pre>
 *  전시 상품 카테고리 정보 관리
 * </pre>
 * 
 * created on : 2014-01-20 
 * created by : 차명호, ANB 
 */
@Service
public class DPAppDeltaDeployFileServiceImpl implements DPAppDeltaDeployFileService {

	private static final String NAMESPACE = "DP_APP_DELTA_DEPLOY_FILE";

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;

	public void insertDPAppDeltaDeployFile(DPAppDeltaDeployFileVO dpAppDeltaDeployFile) {
		this.commonDAO.insert(NAMESPACE + ".insertDPAppDeltaDeployFile", dpAppDeltaDeployFile);
	}

	public void deleteDPAppDeltaDeployFile(String cid) {
		DPAppDeltaDeployFileVO parameter = new DPAppDeltaDeployFileVO();

		parameter.setCid(cid);

		this.commonDAO.delete(NAMESPACE + ".deleteDPAppDeltaDeployFile", parameter);
	}
}
