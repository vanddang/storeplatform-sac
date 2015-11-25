package com.skplanet.storeplatform.sac.other.search.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.framework.core.util.StringUtils;

/**
 * 검색어 조회 DB 조회 Value Object
 *
 * Updated on : 2015. 9. 03. Updated by : 심대진, 다모아 솔루션.
 */
public class SearchKeyword extends CommonInfo {

    private static final long serialVersionUID = 1L;

    public SearchKeyword() {}

    public SearchKeyword(String tenantId, String searchClsfCd) {
        this.tenantId = tenantId;
        this.searchClsfCd = searchClsfCd;
    }

    public SearchKeyword(String tenantId, String searchId, String searchClsfCd, String count) {
        this.tenantId = tenantId;
        this.searchId = searchId;
        this.searchClsfCd = searchClsfCd;
        /**
         * Default setting.
         */
        if(StringUtils.isBlank(count)) {
            this.count = "10";
        }else {
            this.count = count;
        }
    }

    /**
     * 테넌트 아이디.
     */
    private String tenantId;
    /**
     * 검색 아이디.
     */
    private String searchId;
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
     * 검색어 설명.
     */
    private String searchDesc;
    /**
     * 검색어 유형.
     *
     * DP013801 : 인기검색어
     * DP013802 : 급상승검색어
     * DP013803 : 테마검색어
     */
    private String searchClsfCd;
    /**
     * 순위.
     */
    private String rank;
    /**
     * 순위 변동 폭.
     */
    private String rankVariation;
    /**
     * 순위 변동 코드.
     */
    private String rankVariationCd;
    /**
     * 검색어.
     */
    private String keyword;
    /**
     * 조회할 검색어 개수.
     */
    private String count;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

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

    public String getSearchClsfCd() {
        return searchClsfCd;
    }

    public void setSearchClsfCd(String searchClsfCd) {
        this.searchClsfCd = searchClsfCd;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRankVariation() {
        return rankVariation;
    }

    public void setRankVariation(String rankVariation) {
        this.rankVariation = rankVariation;
    }

    public String getRankVariationCd() {
        return rankVariationCd;
    }

    public void setRankVariationCd(String rankVariationCd) {
        this.rankVariationCd = rankVariationCd;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
