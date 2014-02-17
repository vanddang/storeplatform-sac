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

import com.skplanet.icms.refactoring.deploy.DPProductSubContsVO;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;

/**
 * <pre>
 *  전시 서브 컨텐츠 정보 관리
 * </pre>
 * 
 * created on : 2014-01-20 
 * created by : 차명호, ANB 
 */
@Service
public class DPProductSubContsServiceImpl implements DPProductSubContsService {

	private static final String NAMESPACE = "DP_PRODUCT_SUBCONTS";

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPSubContentsService#insertDPSubContents(com.skplanet.icms.deploy.DPSubContentsVO)
	 */
	public void insertDPProductSubconts(DPProductSubContsVO dpSubConts) {
		commonDAO.insert(NAMESPACE + ".insertDPProductSubconts", dpSubConts);
	}

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPSubContentsService#deleteDPSubContents(java.lang.String, java.lang.String)
	 */
	public void deleteDPProductSubconts(String subContsId, String pid) {
		DPProductSubContsVO parameter = new DPProductSubContsVO();

		parameter.setSubContentsId(subContsId);
		parameter.setProdId(pid);

		this.commonDAO.delete(NAMESPACE + ".deleteDPProductSubconts", parameter);
	}

}
