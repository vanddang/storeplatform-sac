package com.skplanet.storeplatform.sac.display.product.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.icms.deploy.DPProductVO;
import com.skplanet.icms.refactoring.deploy.DPTenantProductVO;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.product.vo.ProductVo;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	private static Log log = LogFactory.getLog(ProductServiceImpl.class);

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;
	
	
	@Override
	public DPProductVO selectDpProd(DPProductVO vo) throws StorePlatformException {
		return (DPProductVO) commonDAO.queryForObject("Display_Product.selectDpProd", vo);
	}
	
	
	@Override
	public long selectNewFreeDataCnt(ProductVo vo) throws StorePlatformException {
		return (Long) this.commonDAO.queryForLong("Display_Product.selectNewFreeDataCnt", vo);
	};

	@Override
	public void registNewFreeData(ProductVo vo) throws StorePlatformException {
		long newFreeCnt = selectNewFreeDataCnt(vo);
		log.info("newFreeCnt = " + newFreeCnt);
		if (0 == newFreeCnt) {
			log.info("newFree Insert = " + vo.getProdId());
			this.commonDAO.insert("Display_Product.insertNewFreeData", vo);
		}
	}

	@Override
	public long registSaleStatHis(ProductVo vo) throws StorePlatformException {
		this.commonDAO.insert("Display_Product.insertSaleStatHis", vo);
		return 0;
	}

	@Override
	public ProductVo selectMemberInfo(ProductVo vo) throws StorePlatformException {
		return (ProductVo) this.commonDAO.queryForObject("Display_Product.selectMemberInfo", vo);
	};

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectDpProd(DPTenantProductVO vo) throws StorePlatformException {
		return (Map<String, Object>) this.commonDAO.queryForObject("Display_Product.selectDpProd", vo);
	};

	@Override
	public String registProdSettl(ProductVo vo) throws StorePlatformException {
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
	public void registWhiteList(String prodId) throws StorePlatformException {
		this.commonDAO.insert("Display_Product.insertWhiteList", prodId);
	};

	@Override
	public void registTotalSalesHis(ProductVo vo) throws StorePlatformException {
		this.commonDAO.insert("Display_Product.updateTotalSalesHis", vo);
	};
}