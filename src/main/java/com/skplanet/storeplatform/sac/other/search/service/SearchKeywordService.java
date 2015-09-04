package com.skplanet.storeplatform.sac.other.search.service;

import com.skplanet.storeplatform.sac.client.other.vo.search.SearchKeywordReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchKeywordRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 검색어 관련 Service
 *
 * Updated on : 2015. 9. 03. Updated by : 심대진, 다모아 솔루션.
 */
public interface SearchKeywordService {

    /**
     * 인기 검색어, 급상승 검색어, 테마 검색어 조회.
     * @param searchKeywordReq
     * @param sacRequestHeader
     * @return
     */
    public SearchKeywordRes searchKeyword(SearchKeywordReq searchKeywordReq, SacRequestHeader sacRequestHeader);

}
