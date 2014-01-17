package com.skplanet.storeplatform.sac.display.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
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
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

@Service
@Transactional
public class SearchVodBoxProductServiceImpl implements SearchVodBoxProductService {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private MetaInfoService metaInfoService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public SearchProductRes searchVodBoxProduct(SearchProductReq req, SacRequestHeader header) {
		CommonResponse commonResponse = new CommonResponse();
		SearchProductRes res = new SearchProductRes();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		String tenantId = tenantHeader.getTenantId();
		String listId = req.getListId();

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(tenantId, listId);

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", stdDt);

		// TODO osm1021 review TB_DP_MULTIMDA_PROD Table 삭제
		reqMap.put("svgGrpCd", DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD);
		reqMap.put("etcCd", DisplayConstants.DP_MOVIE_ETC_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

		// ID list 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
				"SearchVodBoxProduct.searchVodBoxProdId", reqMap, ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();

		if (productBasicInfoList != null) {
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

			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				MetaInfo retMetaInfo = this.metaInfoService.getVODMetaInfo(productBasicInfo);

				product = new Product();

				// 상품 정보 (상품ID)
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
				identifier.setText(retMetaInfo.getProdId());
				product.setIdentifier(identifier);

				// 상품 지원 정보
				support = new Support();
				supportList = new ArrayList<Support>();
				support.setType("hd");
				support.setText(retMetaInfo.getHdvYn());
				supportList.add(support);
				product.setSupportList(supportList);

				// 메뉴 정보
				menu = new Menu();
				menuList = new ArrayList<Menu>();
				menu.setType("topClass");
				menu.setId(retMetaInfo.getTopMenuId());
				menu.setName(retMetaInfo.getTopMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setId(retMetaInfo.getMenuId());
				menu.setName(retMetaInfo.getMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setType("metaClass");
				menu.setId(retMetaInfo.getMetaClsfCd());
				menuList.add(menu);
				product.setMenuList(menuList);

				// 저작자 정보
				contributor = new Contributor();
				contributor.setArtist(retMetaInfo.getArtist1Nm());
				contributor.setDirector(retMetaInfo.getArtist2Nm());

				date = new Date();
				date.setText(retMetaInfo.getIssueDay());
				contributor.setDate(date);
				product.setContributor(contributor);

				// 평점 정보
				accrual = new Accrual();
				accrual.setDownloadCount(retMetaInfo.getPrchsCnt());
				accrual.setScore(retMetaInfo.getAvgEvluScore());
				accrual.setVoterCount(retMetaInfo.getPaticpersCnt());
				product.setAccrual(accrual);

				// 이용권한 정보
				rights = new Rights();
				rights.setGrade(retMetaInfo.getProdGrdCd());
				product.setRights(rights);

				// 상품 정보 (상품명)
				title = new Title();
				title.setPrefix(retMetaInfo.getVodTitlNm());
				title.setText(retMetaInfo.getProdNm());
				product.setTitle(title);

				// 이미지 정보
				source = new Source();
				sourceList = new ArrayList<Source>();
				source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
				source.setMediaType(DisplayCommonUtil.getMimeType(retMetaInfo.getFileNm()));
				source.setUrl(retMetaInfo.getFilePath() + retMetaInfo.getFileNm());
				sourceList.add(source);
				product.setSourceList(sourceList);

				// 상품 정보 (상품설명)
				product.setProductExplain(retMetaInfo.getProdBaseDesc());

				// 상품 정보 (상품가격)
				price = new Price();
				price.setText(retMetaInfo.getProdAmt());
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
			res.setProductList(productList);
			res.setCommonResponse(commonResponse);
		}
		return res;
	}
}
