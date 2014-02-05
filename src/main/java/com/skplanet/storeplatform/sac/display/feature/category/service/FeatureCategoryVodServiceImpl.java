/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.category.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryVodReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryVodRes;
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
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.category.service.CategoryAppServiceImpl;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.category.vo.FeatureCategoryVod;

/**
 * 
 * 
 * Updated on : 2014. 01. 27. Updated by : , GTSOFT.
 */
@Service
@Transactional
public class FeatureCategoryVodServiceImpl implements FeatureCategoryVodService {
	private transient Logger logger = LoggerFactory.getLogger(CategoryAppServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.feature.category.service.FeatureCategoryVodService#searchVodList(com.skplanet
	 * .storeplatform.sac.client.display.vo.feature.category.FeatureCategoryVodReq)
	 */
	@Override
	public FeatureCategoryVodRes searchVodList(FeatureCategoryVodReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchVodList Service started!!");
		this.logger.debug("----------------------------------------------------------------");

		FeatureCategoryVodRes vodRes = null;

		String topMenuId = req.getTopMenuId();
		String listId = req.getListId();
		String filteredBy = req.getFilteredBy();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(topMenuId) || StringUtils.isEmpty(listId)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("필수 파라미터 부족");
			this.logger.debug("----------------------------------------------------------------");

			vodRes = new FeatureCategoryVodRes();
			vodRes.setCommonResponse(new CommonResponse());
			return vodRes;
		}
		// 메뉴ID 유효값 체크
		if (!"DP17".equals(topMenuId) && !"DP18".equals(topMenuId)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("유효하지않은 탑메뉴ID");
			this.logger.debug("----------------------------------------------------------------");

			vodRes = new FeatureCategoryVodRes();
			vodRes.setCommonResponse(new CommonResponse());
			return vodRes;
		}
		// 리스트ID 유효값 체크
		if (!"ADM000000003".equals(listId) && !"ADM000000008".equals(listId)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("유효하지않은 리스트ID");
			this.logger.debug("----------------------------------------------------------------");

			vodRes = new FeatureCategoryVodRes();
			vodRes.setCommonResponse(new CommonResponse());
			return vodRes;
		}
		// 영화>추천, 영화>1000원관, 방송>카테고리별 추천, 방송>방송사별 최신Up API는 filteredBy 필수
		if (StringUtils.isEmpty(req.getMenuId()) && StringUtils.isEmpty(filteredBy)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("filteredBy 파라미터 정보 누락");
			this.logger.debug("----------------------------------------------------------------");

			vodRes = new FeatureCategoryVodRes();
			vodRes.setCommonResponse(new CommonResponse());
			return vodRes;
		}
		// 시작점 ROW Default 세팅
		if (req.getOffset() == null) {
			req.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (req.getCount() == null) {
			req.setCount(20);
		}

		// 헤더값 세팅
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setImageCd("DP000101");
		req.setLangCd(header.getTenantHeader().getLangCd());

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(req.getTenantId(), listId);

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("배치완료 기준일시 정보 누락");
			this.logger.debug("----------------------------------------------------------------");

			vodRes = new FeatureCategoryVodRes();
			vodRes.setCommonResponse(new CommonResponse());
			return vodRes;
		}
		req.setStdDt(stdDt);

		// prodGradeCd encode 처리(테넌트에서 인코딩하여 넘길 시 제거 필요)
		if (!StringUtils.isEmpty(req.getProdGradeCd())) {
			try {
				req.setProdGradeCd(URLEncoder.encode(req.getProdGradeCd(), "UTF-8"));
			} catch (Exception ex) {
				throw new StorePlatformException("EX_ERR_CD_9999", ex); // 코드 확인 후 변경 필요
			}

			// prodGradeCd 배열로 변경
			String[] prodGradeCdArr = req.getProdGradeCd().split("\\+");
			req.setProdGradeCdArr(prodGradeCdArr);
		}

		// DP17 : 영화, DP18 : 방송
		if ("DP17".equals(topMenuId)) {
			if ("recommend".equals(filteredBy)) {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("영화 > 추천 상품 조회");
				this.logger.debug("----------------------------------------------------------------");

				List<FeatureCategoryVod> vodList = this.commonDAO.queryForList(
						"FeatureCategory.selectFeatureMovieList", req, FeatureCategoryVod.class);

				vodRes = this.generateVO("movieRecommend", vodList);
			} else if ("movie1000".equals(filteredBy)) {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("영화 > 1000원관 상품 조회");
				this.logger.debug("----------------------------------------------------------------");

				List<FeatureCategoryVod> vodList = this.commonDAO.queryForList(
						"FeatureCategory.selectFeatureMovieList", req, FeatureCategoryVod.class);

				vodRes = this.generateVO("movie1000", vodList);
			} else {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("유효하지않은 조회유형");
				this.logger.debug("----------------------------------------------------------------");

				vodRes = new FeatureCategoryVodRes();
				vodRes.setCommonResponse(new CommonResponse());
				return vodRes;
			}
		} else {
			if ("ADM000000008".equals(listId)) {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("방송 > 카테고리별 추천 상품 조회");
				this.logger.debug("----------------------------------------------------------------");

				List<FeatureCategoryVod> vodList = this.commonDAO.queryForList(
						"FeatureCategory.selectFeatureBroadcastList", req, FeatureCategoryVod.class);

				vodRes = this.generateVO("broadcastRecommend", vodList);
			} else {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("방송 > 방송사별 최신Up 상품 조회");
				this.logger.debug("----------------------------------------------------------------");

				FeatureCategoryVod dto = new FeatureCategoryVod();
				List<FeatureCategoryVod> vodList = new ArrayList<FeatureCategoryVod>();
				vodList.add(dto);

				vodRes = this.generateVO("broadcastNew", vodList);
			}
		}

		return vodRes;
	}

	/*
	 * Feature VOD 카테고리 상품 VO 생성 Method.
	 */
	private FeatureCategoryVodRes generateVO(String apiGb, List<FeatureCategoryVod> vodList) {
		FeatureCategoryVodRes vodRes = new FeatureCategoryVodRes();
		CommonResponse commonRes = new CommonResponse();

		if (!vodList.isEmpty()) {
			FeatureCategoryVod vodDto = null;

			Identifier identifier = null;
			Support support = null;
			Menu menu = null;
			Contributor contributor = null;
			Date date = null;
			Accrual accrual = null;
			Rights rights = null;
			Title title = null;
			Source source = null;
			Source source1 = null;
			Price price = null;
			Product product = null;

			List<Menu> menuList = null;
			List<Source> sourceList = null;
			List<Support> supportList = null;
			List<Product> productList = new ArrayList<Product>();
			List<Identifier> identifierList;

			for (int i = 0; i < vodList.size(); i++) {
				product = new Product();
				identifierList = new ArrayList<Identifier>();
				vodDto = vodList.get(i);

				// 상품 정보 (상품ID)
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
				identifier.setText(vodDto.getProdId());
				identifierList.add(identifier);
				product.setIdentifierList(identifierList);

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
				title.setPostfix(vodDto.getChapter());
				product.setTitle(title);

				// 이미지 정보
				source = new Source();
				sourceList = new ArrayList<Source>();
				source.setType("thumbnail");
				source.setMediaType(DisplayCommonUtil.getMimeType(vodDto.getImgPath()));
				source.setUrl(vodDto.getImgPath());
				sourceList.add(source);

				// 미리보기 url
				source1 = new Source();
				source1.setType("preview");
				source1.setUrl(vodDto.getSamplUrl());
				sourceList.add(source1);
				product.setSourceList(sourceList);

				// 상품 유무료 여부
				product.setProdChrgYn(vodDto.getProdChrgYn());

				// 상품 정보 (상품설명)
				product.setProductExplain(vodDto.getProdBaseDesc());

				// 상품 정보 (상품가격)
				price = new Price();
				price.setText(vodDto.getProdAmt());
				product.setPrice(price);

				// 데이터 매핑
				productList.add(i, product);
			}

			commonRes.setTotalCount(vodDto.getTotalCount());
			vodRes.setProductList(productList);
			vodRes.setCommonResponse(commonRes);
		} else {
			// 조회 결과 없음
			vodRes.setCommonResponse(commonRes);
		}

		return vodRes;
	}
}
