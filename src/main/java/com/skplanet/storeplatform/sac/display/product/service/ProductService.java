package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.deploy.DPProductVO;
import com.skplanet.icms.refactoring.deploy.DPTenantProductVO;
import com.skplanet.storeplatform.sac.display.product.vo.ProductVo;

import java.util.Map;

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

    /**
     * 상품의 판매자ID를 조회한다.
     * @param prodId
     * @return
     */
    String getProductRegId(String prodId);
}