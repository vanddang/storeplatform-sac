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

import com.skplanet.icms.refactoring.deploy.DPTagInfoVO;
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
public class DPTagInfoServiceImpl implements DPTagInfoService {

	private static final String NAMESPACE = "DP_TAG_INFO";

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;

	/**
	 * 
	 * @param dpTagInfo
	 */
	public void insertDPTagInfo(DPTagInfoVO dpTagInfo) {
		this.commonDAO.insert(NAMESPACE + ".insertDPTagInfo", dpTagInfo);
	}

	/**
	 * 
	 * @param cid
	 */
	public void deleteDPTagInfo(String cid, String tagInfoSeq) {
		DPTagInfoVO parameter = new DPTagInfoVO();

		parameter.setCid(cid);
		parameter.setTagSeq(tagInfoSeq);

		this.commonDAO.delete(NAMESPACE + ".deleteDPTagInfo", parameter);
	}
}
