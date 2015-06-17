package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.ProductInfo;
import com.skplanet.storeplatform.sac.display.feature.product.service.ProductListService;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;
import com.skplanet.storeplatform.sac.display.related.vo.RelatedProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatedProductServiceImpl implements RelatedProductService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private ProductListService productListService;

    @Autowired
    private DisplayCommonService displayCommonService;


    @Override
    public RelatedProductSacRes searchRelatedProductList(RelatedProductSacReq requestVO, SacRequestHeader requestHeader) {
        log.debug("연관 상품 조회");

        // 연관 상품 ProdId 리스트 조회.
        Map<String, Object> reqMap = getRequestMapForRelatedProduct(requestVO, requestHeader);
        RelatedProduct relatedProduct = this.commonDAO.queryForObject(
                "RelatedProduct.selectRelatedProduct", reqMap, RelatedProduct.class);
        relatedProduct = relatedProduct == null ? new RelatedProduct() : relatedProduct;

        String[] prodIds = relatedProduct.getProdIdListAsArray();
        List<Product> productList = new ArrayList<Product>();
        for(String prodId : prodIds){
            log.debug("add prodId={}",prodId);
            ListProduct lp = newListProduct(prodId);
            Product p = productListService.getProduct(requestHeader, lp);
            if(p!=null && onSale(p) && requestedGrade(p,parseProdGradeCd(requestVO.getProdGradeCd()))) {
                productList.add(p);
            }
        }

        RelatedProductSacRes relatedProductSacRes = new RelatedProductSacRes();
        relatedProductSacRes.setProductList(productList);
        return relatedProductSacRes;
    }

    private ListProduct newListProduct(String prodId) {
        ProductInfo pi = displayCommonService.getProductInfo(prodId);
        if (pi == null) return null;

        ListProduct lp = new ListProduct();
        lp.setProdId(pi.getProdId());
        lp.setTopMenuId(pi.getTopMenuId());
        lp.setSvcGrpCd(pi.getSvcGrpCd());
        lp.setContentsTypeCd(pi.getContentsTypeCd());
        return lp;
    }

    private boolean requestedGrade(Product p, String[] prodGradeCds) {
        if(prodGradeCds==null)
            return true;
        for(String gradeCd:prodGradeCds)
            if(p.getRights()!=null && p.getRights().getGrade().equals(gradeCd))
                return true;
        return false;
    }

    private boolean onSale(Product product) {
        return product.getSalesStatus().equals(DisplayConstants.DP_SALE_STAT_ING);
    }

    private Map<String, Object> getRequestMapForRelatedProduct(RelatedProductSacReq requestVO, SacRequestHeader requestHeader) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("tenantId", requestHeader.getTenantHeader().getTenantId());
        reqMap.put("prodId", requestVO.getProductId());
        reqMap.put("relaType", requestVO.getRelatedType());
        return reqMap;
    }


    private String[] parseProdGradeCd(String prodGradeCd) {
        if (prodGradeCd == null) {
            return null;
        }
        String[] prodGradeCds = prodGradeCd.split("\\+");
        validateProdGradeCd(prodGradeCds);
        return prodGradeCds;
    }

    private void validateProdGradeCd(String[] prodGradeCds) {
        for (int i = 0; i < prodGradeCds.length; i++) {
            if (!"PD004401".equals(prodGradeCds[i]) && !"PD004402".equals(prodGradeCds[i])
                    && !"PD004403".equals(prodGradeCds[i]) && !"PD004404".equals(prodGradeCds[i])) {
                log.debug("----------------------------------------------------------------");
                log.debug("유효하지않은 상품 등급 코드 : " + prodGradeCds[i]);
                log.debug("----------------------------------------------------------------");

                throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
                        prodGradeCds[i]);
            }
        }
    }

}
