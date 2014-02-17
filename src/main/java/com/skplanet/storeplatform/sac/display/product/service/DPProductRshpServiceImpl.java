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

import com.skplanet.icms.refactoring.deploy.DPProductRshpVO;
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
public class DPProductRshpServiceImpl implements DPProductRshpService {

	private static final String NAMESPACE = "DP_PRODUCT_RSHP";

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductCatService#getDPProductCatList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<DPProductRshpVO> getDPProductRshpList(String prodId, String partProdId, String dpYn, String prodRshpCd) {
		DPProductRshpVO parameter = new DPProductRshpVO();

		parameter.setProdId(prodId);
		parameter.setPartProdId(partProdId);
		parameter.setProdRshpCd(prodRshpCd);

		return this.commonDAO.queryForList(NAMESPACE + ".selectDPProductRshp", parameter, DPProductRshpVO.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductCatService#insertDPProductCat(com.skplanet.icms.deploy.DPProductCatVO)
	 */
	public void insertDPProductRshp(DPProductRshpVO dpProdCat) {
		this.commonDAO.insert(NAMESPACE + ".insertDPProductRshp", dpProdCat);
	}

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductCatService#deleteDPProductCat(java.lang.String, java.lang.String, java.lang.String)
	 */
//	public void deleteDPProductRshp(String prodId, String partProdId, String dpYn, String prodRshpCd) {
//		DPProductRshpVO parameter = new DPProductRshpVO();
//
//		parameter.setProdId(prodId);
//		parameter.setPartProdId(partProdId);
//		parameter.setProdRshpCd(prodRshpCd);
//
//		this.tstoreDAO.delete(NAMESPACE + ".deleteDPProductRshp", parameter);
//	}
	public void deleteDPProductRshp(String prodId) {
		DPProductRshpVO parameter = new DPProductRshpVO();

		parameter.setProdId(prodId);

		this.commonDAO.delete(NAMESPACE + ".deleteDPProductRshp", parameter);
	}
}
