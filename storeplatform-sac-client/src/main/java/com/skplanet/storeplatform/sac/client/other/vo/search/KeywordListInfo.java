package com.skplanet.storeplatform.sac.client.other.vo.search;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 검색어 리스트 Value Object
 *
 * Updated on : 2015. 9. 03. Updated by : 심대진, 다모아 솔루션.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class KeywordListInfo extends CommonInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 순위.
     */
    private String rank;
    /**
     * 순위변동폭.
     */
    private String rankVariation;
    /**
     * 순위변동 코드.
     * DP013602 : 상승
     * DP013603 : 하락
     * DP013604 : 변화없음
     * DP013605 : 급상승
     * DP013606 : 신규진입
     */
    private String rankVariationCd;
    /**
     * 검색어.
     */
    private String keyword;

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
}
