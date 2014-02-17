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

import com.skplanet.icms.refactoring.deploy.DPTenantProductPriceVO;
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
public class DPTenantProductPriceServiceImpl implements DPTenantProductPriceService {

	private static final String NAMESPACE = "DP_TENANT_PRICE";

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductCatService#getDPProductCatList(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<DPTenantProductPriceVO> getDPTenantPrice(String prodId, String tenantId, String applyStartDt, String seq) {
		DPTenantProductPriceVO parameter = new DPTenantProductPriceVO();

		parameter.setProdId(prodId);
		parameter.setTenantId(tenantId);
		parameter.setApplyStartDt(applyStartDt);
		parameter.setSeq(seq);

		return this.commonDAO.queryForList(NAMESPACE + ".selectDPTenantPrice", parameter, DPTenantProductPriceVO.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductCatService#insertDPProductCat(com.skplanet.icms.deploy.DPProductCatVO)
	 */
	public void insertDPTenantPrice(DPTenantProductPriceVO dpTenantProductPrice) {
		this.commonDAO.insert(NAMESPACE + ".insertDPTenantPrice", dpTenantProductPrice);
	}

	/*
	 * (non-Javadoc)
	 * @see com.skplanet.icms.deploy.tstore.service.DPProductCatService#deleteDPProductCat(java.lang.String, java.lang.String, java.lang.String)
	 */
//	public void deleteDPTenantPrice(String prodId, String tenantId, String applyStartDt, String seq) {
	public void deleteDPTenantPrice(String prodId) {
		DPTenantProductPriceVO parameter = new DPTenantProductPriceVO();

		parameter.setProdId(prodId);

		this.commonDAO.delete(NAMESPACE + ".deleteDPTenantPrice", parameter);
	}
}
