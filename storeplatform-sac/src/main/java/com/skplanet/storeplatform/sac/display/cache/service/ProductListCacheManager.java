package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProductCriteria;

import java.util.List;

/**
 * 상품리스트 조회 API용 캐시 매니저
 *
 * @author 1002159
 * @since 2016-01-18
 */
public interface ProductListCacheManager {

    /**
     * 상품리스트 조회 API에서 사용하는 상품메타 조립 전 상품리스트 정보를 조회한다.
     *
     * @param param 상품리스트 조회조건
     * @return 메타 조립전 상품리스트 정보
     */
    List<ListProduct> getListProducts( ListProductCriteria param );

}
