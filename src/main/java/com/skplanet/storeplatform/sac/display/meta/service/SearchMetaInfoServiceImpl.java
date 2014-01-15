package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.search.vo.SearchProductDTO;
import com.skplanet.storeplatform.sac.display.search.vo.SearchProductMetaInfoDTO;

@Service
@Transactional
public class SearchMetaInfoServiceImpl implements SearchMetaInfoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Override
	public List<SearchProductMetaInfoDTO> searchMetaInfoList(SearchProductDTO dto) {
		// dto.getProdList();
		List<SearchProductMetaInfoDTO> vodList = this.commonDAO.queryForList(
				"SearchVodBoxProductMeta.searchVodBoxMetaInfo", dto, SearchProductMetaInfoDTO.class);
		return vodList;
	}

	@Override
	public SearchProductMetaInfoDTO searchMeatInfo(SearchProductDTO dto) {
		return this.commonDAO.queryForObject("SearchVodBoxProductMeta.searchVodBoxMetaInfo", dto,
				SearchProductMetaInfoDTO.class);
	}

}
