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

import com.skplanet.icms.refactoring.deploy.DPProductImgVO;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;

/**
 * <pre>
 *  전시 상품이미지정보 관리
 * </pre>
 * 
 * created on : 2014-01-20 
 * created by : 차명호, ANB 
 */
@Service
public class DPProductImgServiceImpl implements DPProductImgService {

	private static final String NAMESPACE = "DP_PRODUCT_IMG";

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;

	/**
	 * 
	 * @param dpProdImg
	 */
	public void insertDPProductImg(DPProductImgVO dpProdImg) {
		this.commonDAO.insert(NAMESPACE + ".insertDPProductImg", dpProdImg);
	}

	/**
	 * 
	 * @param prodId
	 * @param imgCls
	 * @param dpOrder
	 */
	public void deleteDPProductImg(String prodId, String imgCls, String dpOrder) {
		DPProductImgVO parameter = new DPProductImgVO();
		
		parameter.setProdId(prodId);
		parameter.setImgCd(imgCls);
		parameter.setExpoOrd(dpOrder);

		this.commonDAO.insert(NAMESPACE + ".deleteDPProductImg", parameter);
	}

}
