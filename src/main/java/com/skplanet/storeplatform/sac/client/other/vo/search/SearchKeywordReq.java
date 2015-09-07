package com.skplanet.storeplatform.sac.client.other.vo.search;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 검색어 조회 Value Object
 *
 * Updated on : 2015. 9. 03. Updated by : 심대진, 다모아 솔루션.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SearchKeywordReq extends CommonInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 검색 유형.
     * DP013801 : 인기검색어
     * DP013802 : 급상승검색어
     * DP013803 : 테마검색어
     */
    @NotBlank
    @Pattern(regexp = "^(DP01380){1}?[1-3]$|^(DP01380){1}?[1-3][+](DP01380){1}?[1-3]$|^(DP01380){1}?[1-3][+](DP01380){1}?[1-3][+](DP01380){1}?[1-3]$")
    private String keywordType;
    /**
     * 조회할 검색어 개수.
     */
    private String count;

    public String getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(String keywordType) {
        this.keywordType = keywordType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}