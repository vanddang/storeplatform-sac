package com.skplanet.storeplatform.sac.display.product.service;

import java.util.Map;

import com.skplanet.icms.deploy.DPProductVO;
import com.skplanet.icms.refactoring.deploy.DPTenantProductVO;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.display.product.vo.ProductVo;

public interface ProductService {
	
	public DPProductVO selectDpProd(DPProductVO vo);
	
	public long selectNewFreeDataCnt(String tenantId, String prodId);
	
	public void insertNewFreeData(String tenantId, String prodId, String stdDt);
	
	public ProductVo selectMemberInfo(String mbrNo);
	
	public Map<String, Object> selectDpProd(DPTenantProductVO vo);
	
	public long registSaleStatHis(ProductVo vo);
	
	public String registProdSettl(ProductVo vo);
	
	public void removeAdminRecommand(String tenantId, String prodId);
	
	public void insertWhiteList(String prodId);
	
	public void registTotalSalesHis(ProductVo vo);
}