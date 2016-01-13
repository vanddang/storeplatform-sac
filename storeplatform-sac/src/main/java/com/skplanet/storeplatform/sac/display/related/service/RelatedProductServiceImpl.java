package com.skplanet.storeplatform.sac.display.related.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.RelatedProductSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.cache.service.CachedExtraInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.GetProductBaseInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductBaseInfo;
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
    private CachedExtraInfoManager cachedExtraInfoManager;


    @Override
    public RelatedProductSacRes searchRelatedProductList(RelatedProductSacReq requestVO, SacRequestHeader requestHeader) {
        log.debug("연관 상품 조회");

        // 연관 상품 ProdId 리스트 조회.
        Map<String, Object> reqMap = getRequestMapForRelatedProduct(requestVO);
        RelatedProduct relatedProduct = this.commonDAO.queryForObject(
                "RelatedProduct.selectRelatedProduct", reqMap, RelatedProduct.class);
        relatedProduct = relatedProduct == null ? new RelatedProduct() : relatedProduct;

        String[] prodIds = relatedProduct.getProdIdListAsArray();
        List<Product> productList = new ArrayList<Product>();
        boolean hasNext = false;
        int nextStartKey = 0;
        int count = 0;
        int startKey = Integer.valueOf(requestVO.getStartKey());
        for(String prodId : prodIds){
            log.debug("add prodId={}",prodId);
            ListProduct lp = newListProduct(prodId);
            Product p = productListService.getProduct( lp );
            if (!isValidToDisplay(p, requestVO.getProdGradeCd())) {
                continue;
            }

            if (nextStartKey < startKey) {
                nextStartKey++;
                continue;
            }

            if (count >= requestVO.getCount()) {
                hasNext = true;
                break;
            }

            productList.add(p);
            count++;
            nextStartKey++;
        }

        RelatedProductSacRes relatedProductSacRes = new RelatedProductSacRes();
        relatedProductSacRes.setHasNext(hasNext ? "Y" : "N");
        relatedProductSacRes.setStartKey(hasNext ? String.valueOf(nextStartKey) : null);
        relatedProductSacRes.setCount(count);
        relatedProductSacRes.setProductList(productList);
        return relatedProductSacRes;
    }

    private ListProduct newListProduct(String prodId) {
        ProductBaseInfo baseInfo = cachedExtraInfoManager.getProductBaseInfo(new GetProductBaseInfoParam(prodId));
        if (baseInfo == null)
            return null;

        ListProduct lp = new ListProduct();
        lp.setProdId(prodId);
        lp.setTopMenuId(baseInfo.getTopMenuId());
        lp.setSvcGrpCd(baseInfo.getSvcGrpCd());
        lp.setContentsTypeCd(baseInfo.getContentsTypeCd());
        return lp;
    }



    private Map<String, Object> getRequestMapForRelatedProduct(RelatedProductSacReq requestVO) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("prodId", requestVO.getProductId());
        reqMap.put("relaType", requestVO.getRelatedType());
        return reqMap;
    }

    private boolean isValidToDisplay(Product product, String gradesSplitedByPlusSign) {
        if (product == null) {
            return false;
        }

        if (!product.getSalesStatus().equals("PD000403")) {
            return false;
        }

        if (gradesSplitedByPlusSign == null) {
            return true;
        }

        String[] prodGradeCds = parseProdGradeCd(gradesSplitedByPlusSign);
        validateProdGradeCd(prodGradeCds);
        for(String gradeCd : prodGradeCds) {
            if (product.getRights() != null && product.getRights().getGrade().equals(gradeCd))
                return true;
        }

        return false;
    }

    private String[] parseProdGradeCd(String prodGradeCd) {
        if (prodGradeCd == null) {
            return new String[]{};
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
