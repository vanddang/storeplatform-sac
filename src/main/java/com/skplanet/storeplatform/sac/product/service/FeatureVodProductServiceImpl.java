/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.product.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.common.ProductCommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.feature.FeatureVodProductRequest;
import com.skplanet.storeplatform.sac.client.product.vo.feature.FeatureVodProductResponse;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.product.vo.FeatureVodProductDTO;

/**
 * FeatureVodProductService Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@Service
public class FeatureVodProductServiceImpl implements FeatureVodProductService {
	private transient Logger logger = LoggerFactory.getLogger(FeatureVodProductServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ProductCommonService productCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.FeatureVodProductService#searchFeatureVodProductList(com.skplanet
	 * .storeplatform.sac.client.product.vo.feature.FeatureVodProductRequest)
	 */
	@Override
	public FeatureVodProductResponse searchFeatureVodProductList(FeatureVodProductRequest request) {
		String productClass = null;
		String listId = request.getListId();
		FeatureVodProductResponse response = null;

		// 메뉴 정보 조회
		ProductCommonResponse menuInfo = this.productCommonService.searchMenuInfo(request);

		if ("00".equals(menuInfo.getErrorCode())) {
			if ("MN17".equals(productClass)) {
				// 영화
				productClass = "movie";
			} else if ("MN18".equals(productClass)) {
				// 방송
				productClass = "tv";
			}

			this.logger.debug("--------------------------------------------------------------------------");
			this.logger.debug("### searchFeatureVodProductList productClass : {}", productClass);
			this.logger.debug("--------------------------------------------------------------------------");

			List<FeatureVodProductDTO> productList = null;

			if ("ADM000000008".equals(listId)) {
				this.logger.debug("--------------------------------------------------------------------------");
				this.logger.debug("Feature VOD 운영자 추천 상품");
				this.logger.debug("--------------------------------------------------------------------------");

				productList = this.commonDAO.queryForList("FeatureVodProduct.selectAdminNewProductList", request,
						FeatureVodProductDTO.class);

				// Response 생성
				response = this.generateResponse(productClass, productList);
			} else if ("ADM000000003".equals(listId)) {
				this.logger.debug("--------------------------------------------------------------------------");
				this.logger.debug("Feature VOD 운영자 신규 상품");
				this.logger.debug("--------------------------------------------------------------------------");

			} else {
				this.logger.debug("--------------------------------------------------------------------------");
				this.logger.debug("Feature VOD 카테고리 상품 아님");
				this.logger.debug("--------------------------------------------------------------------------");
			}
		} else {
			this.logger.debug("--------------------------------------------------------------------------");
			this.logger.debug("메뉴 정보 조회 오류 발생");
			this.logger.debug("--------------------------------------------------------------------------");
		}

		return response;
	}

	private FeatureVodProductResponse generateResponse(String productClass, List<FeatureVodProductDTO> resultList) {
		FeatureVodProductResponse response = new FeatureVodProductResponse();

		if (resultList != null) {
			Product product = null;
			Identifier identifier = null;
			Menu menu = null;
			Accrual accrual = null;
			Rights rights = null;
			Title title = null;
			Source source = null;
			Price price = null;

			Support support = null;
			Contributor contributor = null;
			Date date = null;
			Vod vod = null;
			VideoInfo videoInfo = null;

			List<Menu> menuList = null;
			List<Source> sourceList = null;
			List<Product> productList = new ArrayList<Product>();
			List<Support> supportList = new ArrayList<Support>();

			FeatureVodProductDTO productDto = null;

			for (int i = 0; i < resultList.size(); i++) {
				productDto = resultList.get(i);

				// 상품 정보 (상품ID)
				identifier = new Identifier();
				identifier.setText(productDto.getProdId());
				identifier.setType("channel");

				// 메뉴 정보
				menu = new Menu();
				menuList = new ArrayList<Menu>();
				menu.setType("topClass");
				menu.setId(productDto.getUpMenuId());
				menu.setName(productDto.getUpMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setId(productDto.getMenuId());
				menu.setName(productDto.getMenuNm());
				menuList.add(menu);

				// 평점 정보
				accrual = new Accrual();
				accrual.setVoterCount("1820");
				accrual.setDownloadCount("30");
				accrual.setScore(4.5);

				// 이용권한 정보
				rights = new Rights();
				rights.setGrade(productDto.getProdGrdCd());

				// 상품 정보 (상품명)
				title = new Title();
				title.setText(productDto.getProdNm());

				// 이미지 정보
				source = new Source();
				sourceList = new ArrayList<Source>();
				source.setType("thumbnail");
				source.setUrl(productDto.getImgFilePath());
				sourceList.add(source);

				// 상품 정보 (상품설명)
				product = new Product();
				product.setProductExplain(productDto.getProdBaseDesc());

				// 상품 정보 (상품가격)
				price = new Price();
				price.setText(Integer.parseInt(productDto.getProdAmt()));

				// 영화 상품
				if ("movie".equals(productClass)) {
					// 상품 지원 구분 정보
					support = new Support();
					supportList = new ArrayList<Support>();
					support.setType("hdv");
					support.setText(productDto.getHdvYn());
					supportList.add(support);
					product.setSupportList(supportList);

					// 메타클래스 정보
					menu = new Menu();
					menu.setType("metaClass");
					menu.setId("CT13");
					menuList.add(menu);

					// 저작권자 정보
					contributor = new Contributor();
					contributor.setDirector("홍길동");
					contributor.setArtist("임창정, 최다니엘, 오달수");

					date = new Date();
					date.setType("date/publish");
					date.setText("2013");
					contributor.setDate(date);
					product.setContributor(contributor);
				} else if ("tv".equals(productClass)) {
					vod = new Vod();
					videoInfo = new VideoInfo();
					videoInfo.setType("hd");
					vod.setVideoInfo(videoInfo);
					product.setVod(vod);

					date = new Date();
					date.setType("date/broadcast");
					date.setText("20121016T220000+0900");
					product.setDate(date);

					// MBC, KBS, SBS 방송사 정보가 product에 필요함
				}

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setAccrual(accrual);
				product.setRights(rights);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setPrice(price);
				productList.add(i, product);
			}

			response = new FeatureVodProductResponse();
			response.setProductList(productList);

			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setTotalCount(productDto.getTotalCount());
			response.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			CommonResponse commonResponse = new CommonResponse();
			commonResponse.setTotalCount(0);
			response.setCommonResponse(commonResponse);
		}

		return null;
	}
}
