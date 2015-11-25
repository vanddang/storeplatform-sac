package com.skplanet.storeplatform.sac.client.other.vo.search;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

/**
 * 검색어 조회 응답 Value Object
 *
 * Updated on : 2015. 8. 03. Updated by : 심대진, 다모아 솔루션.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SearchKeywordRes extends CommonInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 검색어 조회 응답 리스트.
     */
    private List searchKeywordList;

    public List getSearchKeywordList() {
        return searchKeywordList;
    }

    public void setSearchKeywordList(List searchKeywordList) {
        this.searchKeywordList = searchKeywordList;
    }
}