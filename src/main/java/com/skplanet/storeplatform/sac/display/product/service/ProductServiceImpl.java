package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.deploy.DPProductVO;
import com.skplanet.icms.refactoring.deploy.DPTenantProductVO;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.product.vo.ProductVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;
	
	
	@Override
	public DPProductVO selectDpProd(DPProductVO vo) {
		return (DPProductVO) commonDAO.queryForObject("Display_Product.selectDpProd", vo);
	}
	
	@Override
	public long selectNewFreeDataCnt(ProductVo vo) {
		return this.commonDAO.queryForLong("Display_Product.selectNewFreeDataCnt", vo);
	}

	@Override
	public void insertNewFreeData(ProductVo vo, String stdDt) {
		long newFreeCnt = selectNewFreeDataCnt(vo);
		logger.info("newFreeCnt = " + newFreeCnt);
		if (0 == newFreeCnt) {
			logger.info("newFree Insert = " + vo.getProdId());

            Map<String, String> req = new HashMap<String, String>();
            req.put("tenantId", vo.getTenantId());
            req.put("prodId", vo.getProdId());
            req.put("stdDt", stdDt);

            this.commonDAO.insert("Display_Product.insertNewFreeData", req);
		}
	}

	@Override
	public long registSaleStatHis(ProductVo vo) {
		this.commonDAO.insert("Display_Product.insertSaleStatHis", vo);
		return 0;
	}

	@Override
	public ProductVo selectMemberInfo(ProductVo vo) {
		return (ProductVo) this.commonDAO.queryForObject("Display_Product.selectMemberInfo", vo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectDpProd(DPTenantProductVO vo) {
		return (Map<String, Object>) this.commonDAO.queryForObject("Display_Product.selectDpProd", vo);
	}

	@Override
	public String registProdSettl(ProductVo vo) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("p_prod_id", vo.getProdId());
    	map.put("p_settl_rt", null);
    	map.put("p_reg_id", vo.getMbrId());
    	map.put("rtn", "");
		this.commonDAO.update("Display_Product.sp_regist_prod", map);
		return map.get("rtn");
	}
	
	@Override
	public void removeAdminRecommand(ProductVo vo) throws StorePlatformException {
		this.commonDAO.delete("Display_Product.deleteAdminRecommand", vo);
	}
	
	@Override
	public void insertWhiteList(String prodId) throws StorePlatformException {
		this.commonDAO.insert("Display_Product.insertWhiteList", prodId);
	};

	@Override
	public void registTotalSalesHis(ProductVo vo) throws StorePlatformException {
		this.commonDAO.insert("Display_Product.updateTotalSalesHis", vo);
	};
}