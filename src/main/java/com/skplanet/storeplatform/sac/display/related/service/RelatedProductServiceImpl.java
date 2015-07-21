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
        boolean hasNext = false;
        int nextOffset = 0;
        int count = 0;
        for(String prodId : prodIds){
            log.debug("add prodId={}",prodId);
            ListProduct lp = newListProduct(prodId);
            Product p = productListService.getProduct(requestHeader, lp);
            if (p == null || !p.isValidToDisplay(requestVO.getProdGradeCd())) {
                continue;
            }

            if (nextOffset < requestVO.getOffset()) {
                nextOffset++;
                continue;
            }

            if (count >= requestVO.getCount()) {
                hasNext = true;
                break;
            }

            productList.add(p);
            count++;
            nextOffset++;
        }

        RelatedProductSacRes relatedProductSacRes = new RelatedProductSacRes();
        relatedProductSacRes.setHasNext(hasNext);
        relatedProductSacRes.setNextOffset(hasNext ? nextOffset : null);
        relatedProductSacRes.setCount(count);
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



    private Map<String, Object> getRequestMapForRelatedProduct(RelatedProductSacReq requestVO, SacRequestHeader requestHeader) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("tenantId", requestHeader.getTenantHeader().getTenantId());
        reqMap.put("prodId", requestVO.getProductId());
        reqMap.put("relaType", requestVO.getRelatedType());
        return reqMap;
    }




}
