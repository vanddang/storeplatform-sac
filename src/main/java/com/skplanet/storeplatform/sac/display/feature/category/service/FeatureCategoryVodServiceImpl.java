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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.skplanet.storeplatform.sac.display.category.service.CategoryAppServiceImpl;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.category.vo.FeatureCategoryVodDTO;

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
	public FeatureCategoryVodRes searchVodList(FeatureCategoryVodReq req) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchVodList Service started!!");
		this.logger.debug("----------------------------------------------------------------");

		FeatureCategoryVodRes vodRes = new FeatureCategoryVodRes();
		CommonResponse commonResponse = new CommonResponse();

		// 헤더값 세팅
		req.setDeviceModelCd("SHV-E210S");
		req.setTenantId("S01");
		req.setImageCd("DP000101");

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(req.getTenantId(), req.getListId());
		req.setStdDt(stdDt);

		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("stdDt : {}", stdDt);
		this.logger.debug("----------------------------------------------------------------");

		List<FeatureCategoryVodDTO> vodList = null;

		// DP17 : 영화, DP18 : 방송
		if ("DP17".equals(req.getTopMenuId())) {
			if ("recommend".equals(req.getFilteredBy())) {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("영화 추천 상품 조회");
				this.logger.debug("----------------------------------------------------------------");
			} else {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("영화 1000원관");
				this.logger.debug("----------------------------------------------------------------");
			}

			vodList = this.commonDAO.queryForList("FeatureCategory.selectFeatureMovieList", req,
					FeatureCategoryVodDTO.class);

			if (!vodList.isEmpty()) {
				FeatureCategoryVodDTO vodDto = null;

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

				for (int i = 0; i < vodList.size(); i++) {
					product = new Product();
					vodDto = vodList.get(i);

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
					productList.add(i, product);
				}

				commonResponse.setTotalCount(vodDto.getTotalCount());
				vodRes.setProductList(productList);
				vodRes.setCommonResponse(commonResponse);
			} else {
				// 조회 결과 없음
				commonResponse.setTotalCount(0);
				vodRes.setCommonResponse(commonResponse);
			}
		} else {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("방송 추천 상품 조회");
			this.logger.debug("----------------------------------------------------------------");

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

			product = new Product();

			// 상품 정보 (상품ID)
			identifier = new Identifier();
			identifier.setType("channel");
			identifier.setText("H900716008");
			product.setIdentifier(identifier);

			// 상품 지원 정보
			support = new Support();
			supportList = new ArrayList<Support>();
			support.setType("hd");
			support.setText("Y");
			supportList.add(support);
			product.setSupportList(supportList);

			// 메뉴 정보
			menu = new Menu();
			menuList = new ArrayList<Menu>();
			menu.setType("topClass");
			menu.setId("DP18");
			menu.setName("방송");
			menuList.add(menu);

			menu = new Menu();
			menu.setId("DP18008");
			menu.setName("문화/생활");
			menuList.add(menu);

			menu = new Menu();
			menu.setType("metaClass");
			menu.setId("CT16");
			menuList.add(menu);
			product.setMenuList(menuList);

			// 저작자 정보
			contributor = new Contributor();
			contributor.setArtist("TV영어유치원 시즌4(상)");
			contributor.setDirector("써니");

			date = new Date();
			date.setText("20131218");
			contributor.setDate(date);
			product.setContributor(contributor);

			// 평점 정보
			accrual = new Accrual();
			accrual.setDownloadCount("81");
			accrual.setScore(5);
			accrual.setVoterCount("1");
			product.setAccrual(accrual);

			// 이용권한 정보
			rights = new Rights();
			rights.setGrade("PD004401");
			product.setRights(rights);

			// 상품 정보 (상품명)
			title = new Title();
			title.setText("TV영어유치원 시즌4(상)");
			product.setTitle(title);

			// 이미지 정보
			source = new Source();
			sourceList = new ArrayList<Source>();
			source.setType("thumbnail");
			source.setMediaType("image/png");
			source.setUrl("/SMILE_DATA5/PVODS/201212/24/0000649038/1/0000721239/1/P_130x186.PNG");
			sourceList.add(source);
			product.setSourceList(sourceList);

			// 상품 정보 (상품설명)
			product.setProductExplain("피노키오 애니메이션과 그림일기를 통해 영어의 기초를 확실하게!");

			// 상품 정보 (상품가격)
			price = new Price();
			price.setText(300);
			product.setPrice(price);

			// 데이터 매핑
			productList.add(0, product);

			commonResponse.setTotalCount(1);
			vodRes.setProductList(productList);
			vodRes.setCommonResponse(commonResponse);
		}

		return vodRes;
	}
}
