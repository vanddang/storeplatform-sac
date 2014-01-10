package com.skplanet.storeplatform.sac.display.meta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductRes;
import com.skplanet.storeplatform.sac.display.search.vo.SearchProductDTO;

@Service
@Transactional
public class SearchMetaInfoServiceImpl implements SearchMetaInfoService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public SearchProductRes searchMetaInfoList(SearchProductDTO dto) {
		dto.getProdList();
		// List<SearchProdIdDTO> searchProdIdDTOs = this.commonDAO.queryForList("SearchVodBoxProdId.searchVodBoxProdId",
		// searchProdIds, SearchProdIdDTO.class);
		return null;
	}
	// @Override
	// public SearchProductRes searchMetaInfoList(List<SearchProdIdDTO> searchProdIds) {
	// CommonResponse res = new CommonResponse();
	// SearchProductRes searchProdIdRes = new SearchProductRes();
	//
	// List<SearchProdIdDTO> searchProdIdDTOs = this.commonDAO.queryForList("SearchVodBoxProdId.searchVodBoxProdId",
	// searchProdIds, SearchProdIdDTO.class);
	//
	// return searchProdIdRes;
	// }
}
