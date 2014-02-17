package com.skplanet.storeplatform.sac.display.product.service;

import java.util.Map;

import com.skplanet.icms.deploy.DPProductVO;
import com.skplanet.icms.refactoring.deploy.DPTenantProductVO;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.display.product.vo.ProductVo;

public interface ProductService {
	
	public DPProductVO selectDpProd(DPProductVO vo) throws StorePlatformException;
	
	public long selectNewFreeDataCnt(ProductVo vo) throws StorePlatformException;
	
	public void registNewFreeData(ProductVo vo) throws StorePlatformException;
	
	public ProductVo selectMemberInfo(ProductVo vo) throws StorePlatformException;
	
	public Map<String, Object> selectDpProd(DPTenantProductVO vo) throws StorePlatformException;
	
	public long registSaleStatHis(ProductVo vo) throws StorePlatformException;
	
	public String registProdSettl(ProductVo vo) throws StorePlatformException;
	
	public void removeAdminRecommand(ProductVo vo) throws StorePlatformException;
	
	public void registWhiteList(String prodId) throws StorePlatformException;
	
	public void registTotalSalesHis(ProductVo vo) throws StorePlatformException;
}