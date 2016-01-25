package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProductCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 상품리스트 캐시 매니저 구현체
 *
 * @author 1002159
 * @since 2016-01-18
 */
@Service
public class ProductListCacheManagerImpl implements ProductListCacheManager {

    private static final Logger logger = LoggerFactory.getLogger( ProductListCacheManagerImpl.class );

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Cacheable(value = "sac:display:listProduct:v1", key = "#param.getCacheKey()", unless = "#result == null")
    public List<ListProduct> getListProducts( ListProductCriteria param ) {
        logger.trace( ">> cacheKey : {}", param.getCacheKey() );
        return commonDAO.queryForList( "ProductList.selectListProdList", param, ListProduct.class );
    }

}
