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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.icms.refactoring.deploy.DPProductCatMapVO;
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
public class DPProductCatMapServiceImpl implements DPProductCatMapService {

	private static final String NAMESPACE = "DP_PRODUCT_CAT";

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductCatService#getDPProductCatList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<DPProductCatMapVO> getDPProductCatList(String prodId, String epsdProdId, String dpYn, String categoryNo) {
		DPProductCatMapVO parameter = new DPProductCatMapVO();

		parameter.setProdId(prodId);
//		parameter.setEpsdProdId(epsdProdId);
		parameter.setUseYn(dpYn);
		parameter.setCategoryNo(categoryNo);
														 
		return this.commonDAO.queryForList(NAMESPACE + ".selectDPProductCat", parameter, DPProductCatMapVO.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductCatService#insertDPProductCat(com.skplanet.icms.deploy.DPProductCatVO)
	 */
	public void insertDPProductCat(DPProductCatMapVO dpProdCat) {
		this.commonDAO.insert(NAMESPACE + ".insertDPProductCat", dpProdCat);
	}

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductCatService#deleteDPProductCat(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void deleteDPProductCat(String prodId, String categoryNo) {
		DPProductCatMapVO parameter = new DPProductCatMapVO();

		parameter.setProdId(prodId);
//		parameter.setEpsdProdId(epsdProdId);
		parameter.setCategoryNo(categoryNo);

		this.commonDAO.delete(NAMESPACE + ".deleteDPProductCat", parameter);
	}
}
