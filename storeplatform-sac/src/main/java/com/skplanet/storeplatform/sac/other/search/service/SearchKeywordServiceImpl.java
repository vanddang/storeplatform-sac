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

//        /**
//         * 인기 검색어.
//         */
//        if(searchKeywordReq.getKeywordType().contains(SearchType.POPULAR_KEYWORD.getKeywordType())) {
//            SearchKeywordListInfo popularInfo = getKeyword(sacRequestHeader.getTenantHeader().getTenantId(), SearchType.POPULAR_KEYWORD, searchKeywordReq.getCount());
//            searchKeywordList.add(popularInfo);
//        }

        /**
         * 급상승 검색어.
         */
        if(searchKeywordReq.getKeywordType().contains(SearchType.RISING_KEYWORD.getKeywordType())) {
            SearchKeywordListInfo risingInfo = getKeyword(sacRequestHeader.getTenantHeader().getTenantId(), SearchType.RISING_KEYWORD, searchKeywordReq.getCount());
            searchKeywordList.add(risingInfo);
        }

        /**
         * 테마 검색어.
         */
        if(searchKeywordReq.getKeywordType().contains(SearchType.THEME_KEYWORD.getKeywordType())) {
            SearchKeywordListInfo themeInfo = getKeyword(sacRequestHeader.getTenantHeader().getTenantId(), SearchType.THEME_KEYWORD, searchKeywordReq.getCount());
            searchKeywordList.add(themeInfo);
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
        SearchKeyword keyword = null;
        List<SearchKeyword> list = null;

        /**
         * SearchType에 따라 (인기, 급상승), 테마 검색어 조회.
         */
        LOGGER.info("====>> {}", searchType.name());

        // 테마 검색어
        if(StringUtils.equals(SearchType.THEME_KEYWORD.name(), searchType.name())) {
            String randomId = (String) commonDAO.queryForObject("SearchKeyword.getThemeRandomId", new SearchKeyword(tenantId, searchType.getCode()));
            list = commonDAO.queryForList("SearchKeyword.getSearchKeyword", new SearchKeyword(tenantId, randomId, searchType.getCode(), count), SearchKeyword.class);
        // 급상승 검색어
        } else {
            // White 리스트 조회
            list = commonDAO.queryForList("SearchKeyword.getWhiteKeywordList", new SearchKeyword(tenantId, searchType.getId(), searchType.getCode(), count), SearchKeyword.class);

            if(Integer.parseInt(count) > list.size()) {
                // White 리스트가 요청 건수보다 부족하면 급상승 검색어로 대체
                count = String.valueOf(Integer.parseInt(count) - list.size());
                List<SearchKeyword> keywordList = commonDAO.queryForList("SearchKeyword.getSearchKeyword", new SearchKeyword(tenantId, searchType.getId(), searchType.getCode(), count), SearchKeyword.class);

                for(SearchKeyword info : keywordList) {
                    list.add(info);
                }
            } else {
                // 검색어 기본정보 조회 (작업일시, 검색어명 등 White 리스트에 없는 검색어 기본정보를 조회)
                keyword = commonDAO.queryForObject("SearchKeyword.getWhiteKeywordInfo", new SearchKeyword(tenantId, searchType.getId(), searchType.getCode(), count), SearchKeyword.class);
            }
        }

        /**
         * Response 응답 데이터.
         */
        SearchKeywordListInfo searchKeywordListInfo = new SearchKeywordListInfo();
        searchKeywordListInfo.setKeywordType(searchType.getKeywordType()); // 검색어 유형

        if(list.size() > 0) {
            Integer rank = 0;
            List<KeywordListInfo> keywordList = new ArrayList<KeywordListInfo>(); // 검색어 List

            for(SearchKeyword info : list) {
                rank++;
                searchKeywordListInfo.setOperationDt(info.getOperationDt()); // 기준일시 (데이타가 동일하다는 판단으로 맨마지막 데이타가 세팅됨.)
                searchKeywordListInfo.setSearchNm(info.getSearchNm()); // 검색어명
                searchKeywordListInfo.setSearchDesc(info.getSearchDesc()); // 검색어 설명

                KeywordListInfo keywordListInfo = new KeywordListInfo();
                keywordListInfo.setRank(String.valueOf(rank)); // 순위
                keywordListInfo.setRankVariation(info.getRankVariation()); // 순위변동폭
                keywordListInfo.setRankVariationCd(info.getRankVariationCd()); // 순위변동 코드
                keywordListInfo.setKeyword(info.getKeyword()); // 순위변동 코드
                keywordList.add(keywordListInfo);
            }

            if(keyword != null) {
                // White 리스트의 검색어 기본정보 세팅
                searchKeywordListInfo.setOperationDt(keyword.getOperationDt()); // 기준일시 (데이타가 동일하다는 판단으로 맨마지막 데이타가 세팅됨.)
                searchKeywordListInfo.setSearchNm(keyword.getSearchNm()); // 검색어명
                searchKeywordListInfo.setSearchDesc(keyword.getSearchDesc()); // 검색어 설명
            }

            searchKeywordListInfo.setKeywordList(keywordList); // 검색어 List set
        }

        return searchKeywordListInfo;

    }

}