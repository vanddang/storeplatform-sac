package com.skplanet.storeplatform.sac.client.other.vo.search;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

/**
 * 인기 검색어, 급상승 검색어, 테마 검색어 Value Object
 *
 * Updated on : 2015. 9. 03. Updated by : 심대진, 다모아 솔루션.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SearchKeywordListInfo extends CommonInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 전체 건수.
     */
    private String totalCount;
    /**
     * 기준일시.
     */
    private String operationDt;
    /**
     * 검색어명.
     */
    private String searchNm;
    /**
     * 검색어설명.
     */
    private String searchDesc;
    /**
     * 검색어유형.
     * DP013801 : 인기검색어
     * DP013802 : 급상승검색어
     * DP013803 : 테마검색어
     */
    private String keywordType;
    /**
     * 검색어 List.
     */
    private List<KeywordListInfo> keywordList;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getOperationDt() {
        return operationDt;
    }

    public void setOperationDt(String operationDt) {
        this.operationDt = operationDt;
    }

    public String getSearchNm() {
        return searchNm;
    }

    public void setSearchNm(String searchNm) {
        this.searchNm = searchNm;
    }

    public String getSearchDesc() {
        return searchDesc;
    }

    public void setSearchDesc(String searchDesc) {
        this.searchDesc = searchDesc;
    }

    public String getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(String keywordType) {
        this.keywordType = keywordType;
    }

    public List<KeywordListInfo> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(List<KeywordListInfo> keywordList) {
        this.keywordList = keywordList;
    }
}
