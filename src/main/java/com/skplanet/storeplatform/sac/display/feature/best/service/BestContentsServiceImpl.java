package com.skplanet.storeplatform.sac.display.feature.best.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestContentsSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestContentsSacRes;
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
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */
@Service
public class BestContentsServiceImpl implements BestContentsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService commonService;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/**
	 * 
	 * <pre>
	 * BEST 컨텐츠 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            공통헤더
	 * @param bestContentsReq
	 *            파라미터
	 * @return BEST 컨텐츠 리스트
	 */
	@Override
	public BestContentsSacRes searchBestContentsList(SacRequestHeader requestheader, BestContentsSacReq bestContentsReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		this.log.debug("########################################################");
		this.log.debug("tenantHeader.getTenantId()	:	" + tenantHeader.getTenantId());
		this.log.debug("tenantHeader.getLangCd()	:	" + tenantHeader.getLangCd());
		this.log.debug("deviceHeader.getModel()		:	" + deviceHeader.getModel());
		this.log.debug("########################################################");

		bestContentsReq.setTenantId(tenantHeader.getTenantId());
		bestContentsReq.setLangCd(tenantHeader.getLangCd());
		bestContentsReq.setDeviceModelCd(deviceHeader.getModel());

		BestContentsSacRes response = new BestContentsSacRes();
		CommonResponse commonResponse = new CommonResponse();

		List<Product> productList = new ArrayList<Product>();
		List<Identifier> identifierList = null;
		List<Menu> menuList = null;
		List<Source> sourceList = null;
		List<Support> supportList = null;

		String listId = bestContentsReq.getListId();
		String filteredBy = bestContentsReq.getFilteredBy();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(listId)) {
			throw new StorePlatformException("SAC_DSP_0002", "listId", listId);
		}
		if (StringUtils.isEmpty(filteredBy)) {
			throw new StorePlatformException("SAC_DSP_0002", "filteredBy", filteredBy);
		}

		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(bestContentsReq.getProdGradeCd())) {
			String[] arrayProdGradeCd = bestContentsReq.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}
		if (!"movie+broadcast".equals(filteredBy) && !"movie".equals(filteredBy) && !"broadcast".equals(filteredBy)
				&& !"ebook+comic".equals(filteredBy) && !"ebook".equals(filteredBy) && !"comic".equals(filteredBy)) {
			throw new StorePlatformException("SAC_DSP_0003", "filteredBy", filteredBy);
		}

		int offset = 1; // default
		int count = 20; // default

		if (bestContentsReq.getOffset() != null) {
			offset = bestContentsReq.getOffset();
		}
		bestContentsReq.setOffset(offset); // set offset

		if (bestContentsReq.getCount() != null) {
			count = bestContentsReq.getCount();
		}
		count = offset + count - 1;
		bestContentsReq.setCount(count); // set count

		String stdDt = this.commonService.getBatchStandardDateString(tenantHeader.getTenantId(),
				bestContentsReq.getListId());
		bestContentsReq.setStdDt(stdDt); // 2014.01.28 이석희 수정 S01 하드코딩에서 헤더에서 get 한 TenantId

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(bestContentsReq.getProdGradeCd())) {
			String[] arrayProdGradeCd = bestContentsReq.getProdGradeCd().split("\\+");
			bestContentsReq.setArrayProdGradeCd(arrayProdGradeCd);
		}

		if (bestContentsReq.getDummy() == null) { // dummy 호출이 아닐때
			// ID list 조회
			List<ProductBasicInfo> productBasicInfoList = null;
			if ("movie".equals(bestContentsReq.getFilteredBy()) || "boardcast".equals(bestContentsReq.getFilteredBy())
					|| "movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
				// 영화, 방송 상품List 조회 (Step 1)
				productBasicInfoList = this.commonDAO.queryForList("BestContents.selectBestContentsVodList",
						bestContentsReq, ProductBasicInfo.class);
			} else {
				// 이북, 코믹 상품List 조회 (Step 1)
				productBasicInfoList = this.commonDAO.queryForList("BestContents.selectBestContentsBookList",
						bestContentsReq, ProductBasicInfo.class);
			}

			if (!productBasicInfoList.isEmpty()) {
				Map<String, Object> reqMap = new HashMap<String, Object>();
				reqMap.put("tenantHeader", tenantHeader);
				reqMap.put("deviceHeader", deviceHeader);
				reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					MetaInfo retMetaInfo = null;
					if ("movie".equals(bestContentsReq.getFilteredBy())
							|| "boardcast".equals(bestContentsReq.getFilteredBy())
							|| "movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
						// 영화, 방송 Meta 정보 조회 (Step 2)
						reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
					} else {
						// 이북, 코믹 Meta 정보 조회 (Step 2)
						reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
					}

					if (retMetaInfo != null) {
						if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
							Product product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
							productList.add(product);
						} else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
							Product product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
							productList.add(product);
						} else if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
							Product product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
							productList.add(product);
						} else if (DisplayConstants.DP_TV_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
							Product product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
							productList.add(product);
						}
					}
				}
				commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());
				response.setProductList(productList);
				response.setCommonResponse(commonResponse);
			} else {
				// 조회 결과 없음
				commonResponse.setTotalCount(0);
				response.setProductList(productList);
				response.setCommonResponse(commonResponse);
			}

		} else {
			Product product = new Product();
			Identifier identifier = new Identifier();
			Contributor contributor = new Contributor();
			Accrual accrual = new Accrual();
			Rights rights = new Rights();
			Title title = new Title();
			Source source = new Source();
			Price price = new Price();
			Support support = new Support();

			// 상품ID
			identifierList = new ArrayList<Identifier>();
			identifier.setType("channel");
			identifier.setText("H001540562");
			identifierList.add(identifier);

			supportList = new ArrayList<Support>();
			support.setType("hd");
			support.setText("Y");
			supportList.add(support);

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			menuList = new ArrayList<Menu>();
			Menu menu = new Menu();
			menu.setId("DP000517");
			menu.setName("영화");
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("DP17002");
			menu.setName("액션");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("CT13");
			menu.setName("movie");
			menu.setType("metaClass");
			menuList.add(menu);

			// fiteredBy = ebook
			// fiteredBy = comic
			// fiteredBy = ebook+comic

			if ("movie".equals(bestContentsReq.getFilteredBy())
					|| "movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
				contributor.setDirector("곽경택");
				contributor.setArtist("유오성,주진모,김우빈,박아인,강한나,한수아");
				Date date = new Date();
				date.setText("20131114");
				contributor.setDate(date);
			} else if ("broadcast".equals(bestContentsReq.getFilteredBy())) {
				contributor.setArtist("유재석,지석진,김종국,하하,개리,이광수");
			} else if ("ebook".equals(bestContentsReq.getFilteredBy())) {
				contributor.setName("정현웅");
				contributor.setPublisher("L&amp;B BOOKS");
				Date date = new Date();
				date.setText("20130322");
				contributor.setDate(date);
			} else if ("comic".equals(bestContentsReq.getFilteredBy())
					|| "ebook+comic".equals(bestContentsReq.getFilteredBy())) {
				contributor.setName("황성");
				contributor.setPainter("황성");
				contributor.setPublisher("미스터블루");
			}

			/*
			 * Accrual - voterCount (참여자수) DownloadCount (다운로드 수) score(평점)
			 */
			accrual.setVoterCount(51);
			accrual.setDownloadCount(5932);
			accrual.setScore(3.8);

			/*
			 * Rights - grade
			 */
			rights.setGrade("4");

			title.setPrefix("[20%할인]");
			title.setText("친구");
			// title.setPostfix("2");

			/*
			 * source mediaType - url
			 */
			sourceList = new ArrayList<Source>();
			// 2014.01.28 이석희 추가
			source.setMediaType("image/png");
			source.setSize(4325);
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			// 2014.01.28 이석희 추가 끝
			source.setUrl("/SMILE_DATA7/PVOD/201401/02/0002057676/3/0003876930/3/RT1_02000024893_1_0921_182x261_130x186.PNG");
			sourceList.add(source);

			/*
			 * Price text
			 */
			price.setText(3200);

			product = new Product();
			product.setIdentifierList(identifierList);
			product.setSupportList(supportList);
			product.setMenuList(menuList);
			product.setContributor(contributor);
			product.setAccrual(accrual);
			product.setRights(rights);
			product.setTitle(title);
			product.setSourceList(sourceList);
			product.setProductExplain("니 내랑 부산 접수할래? ...");
			product.setPrice(price);

			// BestContentsVO = new BestContentsVO();
			// BestContentsVO.setProduct(product);
			// listVO.add(BestContentsVO);

			productList.add(product);
		}

		response.setCommonResponse(commonResponse);
		response.setProductList(productList);

		return response;
	}
}
