package com.skplanet.storeplatform.sac.display.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.search.SearchProductRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.SearchMetaInfoService;
import com.skplanet.storeplatform.sac.display.search.vo.SearchProductDTO;
import com.skplanet.storeplatform.sac.display.search.vo.SearchProductMetaInfoDTO;

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

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public SearchProductRes searchVodBoxProdIdList(SearchProductReq req) {
		CommonResponse commonResponse = new CommonResponse();
		SearchProductRes res = new SearchProductRes();

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> reqMap = mapper.convertValue(req, Map.class);

		// TODO osm1021 헤더값 세팅 꼭 삭제할것 & Map 설정 필요
		reqMap.put("deviceModelNo", "SHV-E210S");
		reqMap.put("tenantId", "S01");
		// req.setDeviceModelNo("SHV-E210S");
		// req.setTenantId("S01");

		// 배치완료 기준일시 조회
		// String stdDt = this.displayCommonService.getBatchStandardDateString((String) reqMap.get("tenantId"),
		// (String) reqMap.get("listId"));
		// TODO osm1021 배치 완료일 문제로 더미 값 추가
		String stdDt = "20140101000000";
		reqMap.put("stdDt", stdDt);

		// ID list 조회
		SearchProductDTO searchProductDTO = this.commonDAO.queryForObject("SearchVodBoxProduct.searchVodBoxProdId",
				reqMap, SearchProductDTO.class);

		List<SearchProductMetaInfoDTO> vodList = null;
		if (searchProductDTO != null) {
			// Meta 정보 조회
			vodList = this.searchMetaInfoService.searchMetaInfoList(req, searchProductDTO);
		}

		// DP17 : 영화, DP18 : 방송
		if ("DP17".equals(req.getTopMenuId())) {
			if ("recommend".equals(req.getFilterdby())) {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("영화 추천 상품 조회");
				this.logger.debug("----------------------------------------------------------------");
			} else {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("영화 1000원관");
				this.logger.debug("----------------------------------------------------------------");
			}

			if (vodList != null) {
				Identifier identifier = null;
				Support support = null;
				Menu menu = null;
				Contributor contributor = null;
				Date date = null;
				Accrual accrual = null;
				Rights rights = null;
				Title title = null;
				Source source = null;
				Price price = null;
				Product product = null;

				List<Menu> menuList = null;
				List<Source> sourceList = null;
				List<Support> supportList = null;
				List<Product> productList = new ArrayList<Product>();

				for (SearchProductMetaInfoDTO vodDto : vodList) {
					product = new Product();

					// 상품 정보 (상품ID)
					identifier = new Identifier();
					identifier.setType("channel");
					identifier.setText(vodDto.getProdId());
					product.setIdentifier(identifier);

					// 상품 지원 정보
					support = new Support();
					supportList = new ArrayList<Support>();
					support.setType("hd");
					support.setText(vodDto.getHdvYn());
					supportList.add(support);
					product.setSupportList(supportList);

					// 메뉴 정보
					menu = new Menu();
					menuList = new ArrayList<Menu>();
					menu.setType("topClass");
					menu.setId(vodDto.getTopMenuId());
					menu.setName(vodDto.getTopMenuNm());
					menuList.add(menu);

					menu = new Menu();
					menu.setId(vodDto.getMenuId());
					menu.setName(vodDto.getMenuNm());
					menuList.add(menu);

					menu = new Menu();
					menu.setType("metaClass");
					menu.setId(vodDto.getMetaClsfCd());
					menuList.add(menu);
					product.setMenuList(menuList);

					// 저작자 정보
					contributor = new Contributor();
					contributor.setArtist(vodDto.getArtist1Nm());
					contributor.setDirector(vodDto.getArtist2Nm());

					date = new Date();
					date.setText(vodDto.getIssueDay());
					contributor.setDate(date);
					product.setContributor(contributor);

					// 평점 정보
					accrual = new Accrual();
					accrual.setDownloadCount(vodDto.getPrchsCnt());
					accrual.setScore(vodDto.getAvgEvluScore());
					accrual.setVoterCount(vodDto.getPaticpersCnt());
					product.setAccrual(accrual);

					// 이용권한 정보
					rights = new Rights();
					rights.setGrade(vodDto.getProdGrdCd());
					product.setRights(rights);

					// 상품 정보 (상품명)
					title = new Title();
					title.setPrefix(vodDto.getVodTitlNm());
					title.setText(vodDto.getProdNm());
					product.setTitle(title);

					// 이미지 정보
					source = new Source();
					sourceList = new ArrayList<Source>();
					source.setType("thumbnail");
					source.setMediaType("image/png");
					source.setUrl(vodDto.getImgPath());
					sourceList.add(source);
					product.setSourceList(sourceList);

					// 상품 정보 (상품설명)
					product.setProductExplain(vodDto.getProdBaseDesc());

					// 상품 정보 (상품가격)
					price = new Price();
					price.setText(Integer.parseInt(vodDto.getProdAmt()));
					product.setPrice(price);

					// 데이터 매핑
					productList.add(product);
				}

				commonResponse.setTotalCount(productList.size());
				res.setProductList(productList);
				res.setCommonResponse(commonResponse);
			} else {
				// 조회 결과 없음
				commonResponse.setTotalCount(0);
				res.setCommonResponse(commonResponse);
			}
		} else {
			// 방송 추천 상품 조회
		}
		return res;
	}
}
