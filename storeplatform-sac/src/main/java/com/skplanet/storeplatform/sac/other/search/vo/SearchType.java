package com.skplanet.storeplatform.sac.other.search.vo;

/**
 * 검색어 타입 enum.
 *
 * Updated on : 2015. 9. 03. Updated by : 심대진, 다모아 솔루션.
 */
public enum SearchType {

    /**
     * 인기 검색어.
     */
    POPULAR_KEYWORD("SCH0000001", "DP013701", "DP013801"),
    /**
     * 급상승 검색어.
     */
    RISING_KEYWORD("SCH0000002", "DP013701", "DP013802"),
    /**
     * 테마 검색어.
     */
    THEME_KEYWORD("*", "DP013702", "DP013803");

    private String id;
    private String code;
    private String keywordType;

    /**
     *
     * @param id
     * @param code
     * @param keywordType
     */
    SearchType(String id, String code, String keywordType) {
        this.id = id;
        this.code = code;
        this.keywordType = keywordType;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the keywordType
     */
    public String getKeywordType() {
        return keywordType;
    }

}
