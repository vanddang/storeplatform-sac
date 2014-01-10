package com.skplanet.storeplatform.sac.display.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.SearchMetaInfoService;
import com.skplanet.storeplatform.sac.display.search.vo.SearchProductDTO;

@Service
@Transactional
public class SearchVodBoxProductServiceImpl implements SearchVodBoxProductService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private SearchMetaInfoService searchMetaInfoService;

	@Override
	public SearchProductRes searchVodBoxProdIdList(SearchProductReq req) {
		CommonResponse res = new CommonResponse();
		SearchProductRes searchProdIdRes = new SearchProductRes();

		// TODO osm1021 헤더값 세팅 꼭 삭제할것
		req.setDeviceModelNo("SHV-E210S");
		req.setTenantId("S01");

		// TODO osm1021 일단 에러로 더미 값 설정
		// 배치완료 기준일시 조회
		// String stdDt = "20130101000000";
		String stdDt = this.displayCommonService.getBatchStandardDateString(req.getTenantId(), req.getListId());
		req.setStdDt(stdDt);

		SearchProductDTO result = this.commonDAO.queryForObject("SearchVodBoxProduct.searchVodBoxProdId", req,
				SearchProductDTO.class);
		// List<SearchProdIdDTO> searchProdIdDTOs =
		// this.commonDAO.queryForList("SearchVodBoxProduct.searchVodBoxProdId", req, SearchProdIdDTO.class);

		return searchProdIdRes;
	}
}
