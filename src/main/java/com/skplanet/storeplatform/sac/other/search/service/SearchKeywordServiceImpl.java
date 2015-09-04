package com.skplanet.storeplatform.sac.other.search.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.other.vo.search.KeywordListInfo;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchKeywordListInfo;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchKeywordReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchKeywordRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.other.search.vo.SearchKeyword;
import com.skplanet.storeplatform.sac.other.search.vo.SearchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 검색어 관련 Service
 *
 * Updated on : 2015. 9. 03. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class SearchKeywordServiceImpl implements SearchKeywordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchKeywordServiceImpl.class);

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public SearchKeywordRes searchKeyword(SearchKeywordReq searchKeywordReq, SacRequestHeader sacRequestHeader) {

        /**
         * 응답 세팅.
         */
        SearchKeywordRes res = new SearchKeywordRes();
        List<SearchKeywordListInfo> searchKeywordList = new ArrayList<SearchKeywordListInfo>();

        /**
         * 인기 검색어.
         */
        if(searchKeywordReq.getKeywordType().contains(SearchType.POPULAR_KEYWORD.getKeywordType())) {
            SearchKeywordListInfo popularInfo = getKeyword(sacRequestHeader.getTenantHeader().getTenantId(), SearchType.POPULAR_KEYWORD, searchKeywordReq.getCount());
            if(popularInfo != null) {
                searchKeywordList.add(popularInfo);
            }
        }

        /**
         * 급상승 검색어.
         */
        if(searchKeywordReq.getKeywordType().contains(SearchType.RISING_KEYWORD.getKeywordType())) {
            SearchKeywordListInfo risingInfo = getKeyword(sacRequestHeader.getTenantHeader().getTenantId(), SearchType.RISING_KEYWORD, searchKeywordReq.getCount());
            if(risingInfo != null) {
                searchKeywordList.add(risingInfo);
            }
        }

        /**
         * 테마 검색어.
         */
        if(searchKeywordReq.getKeywordType().contains(SearchType.THEME_KEYWORD.getKeywordType())) {
            SearchKeywordListInfo themeInfo = getKeyword(sacRequestHeader.getTenantHeader().getTenantId(), SearchType.THEME_KEYWORD, searchKeywordReq.getCount());
            if(themeInfo != null) {
                searchKeywordList.add(themeInfo);
            }
        }

        /**
         * 검색결과 없음.
         */
        if(searchKeywordList.size() <= 0) {
            throw new StorePlatformException("SAC_OTH_9001");
        }

        res.setSearchKeywordList(searchKeywordList);

        return res;

    }

    /**
     * 검색 타입에 따라 인기검색어, 급상승검색어, 테마검색어 조회 및 세팅.
     * @param tenantId
     * @param searchType
     * @param count
     * @return
     */
    private SearchKeywordListInfo getKeyword(String tenantId, SearchType searchType, String count) {

        /**
         * DB 응답 데이터.
         */
        List<SearchKeyword> list = null;

        /**
         * SearchType에 따라 (인기, 급상승), 테마 검색어 조회.
         */
        LOGGER.info("====>> {}", searchType.name());
        if(StringUtils.equals(SearchType.THEME_KEYWORD.name(), searchType.name())) { // 테마

            String randomId = (String) commonDAO.queryForObject("SearchKeyword.getThemeRandomId", new SearchKeyword(tenantId, searchType.getCode()));
            list = commonDAO.queryForList("SearchKeyword.getSearchKeyword", new SearchKeyword(tenantId, randomId, searchType.getCode(), count), SearchKeyword.class);

        } else { // 인기, 급상승

            list = commonDAO.queryForList("SearchKeyword.getSearchKeyword", new SearchKeyword(tenantId, searchType.getId(), searchType.getCode(), count), SearchKeyword.class);

        }

        /**
         * Response 응답 데이터.
         */
        SearchKeywordListInfo searchKeywordListInfo = new SearchKeywordListInfo();

        if(list.size() > 0) {

            List<KeywordListInfo> keywordList = new ArrayList<KeywordListInfo>(); // 검색어 List
            for(SearchKeyword info : list) {
                searchKeywordListInfo.setTotalCount(info.getTotalCount()); // 전체 건수
                searchKeywordListInfo.setOperationDt(info.getOperationDt()); // 기준일시 (데이타가 동일하다는 판단으로 맨마지막 데이타가 세팅됨.)
                searchKeywordListInfo.setSearchNm(info.getSearchNm()); // 검색어명
                searchKeywordListInfo.setSearchDesc(info.getSearchDesc()); // 검색어 설명
                searchKeywordListInfo.setKeywordType(searchType.getKeywordType()); // 검색어 유형

                KeywordListInfo keywordListInfo = new KeywordListInfo();
                keywordListInfo.setRank(info.getRank()); // 순위
                keywordListInfo.setRankVariation(info.getRankVariation()); // 순위변동폭
                keywordListInfo.setRankVariationCd(info.getRankVariationCd()); // 순위변동 코드
                keywordListInfo.setKeyword(info.getKeyword()); // 순위변동 코드
                keywordList.add(keywordListInfo);
            }

            searchKeywordListInfo.setKeywordList(keywordList); // 검색어 List set

        } else {
            searchKeywordListInfo = null;
        }

        return searchKeywordListInfo;

    }

}