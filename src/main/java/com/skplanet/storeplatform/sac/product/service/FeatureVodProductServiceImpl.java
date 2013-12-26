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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.FeatureVodProductService#searchFeatureVodProductList(com.skplanet
	 * .storeplatform.sac.client.product.vo.feature.FeatureVodProductRequest)
	 */
	@Override
	public FeatureVodProductResponse searchFeatureVodProductList(FeatureVodProductRequest request) {
		String listId = request.getListId();
		// 메뉴 정보 조회
		// ProductCommonResponse menuInfo = this.productCommonService.searchMenuInfo(request);

		List<FeatureVodProductDTO> productList = null;
		FeatureVodProductResponse response = null;

		if ("ADM000000008".equals(listId)) {
			this.logger.debug("--------------------------------------------------------------------------");
			this.logger.debug("Feature VOD 운영자 추천 상품");
			this.logger.debug("--------------------------------------------------------------------------");

			productList = this.commonDAO.queryForList("FeatureVodProduct.selectAdminNewProductList", request,
					FeatureVodProductDTO.class);

			// Response 생성
			response = this.generateResponse(listId, productList);
		} else if ("ADM000000003".equals(listId)) {
			this.logger.debug("--------------------------------------------------------------------------");
			this.logger.debug("Feature VOD 운영자 신규 상품");
			this.logger.debug("--------------------------------------------------------------------------");

		} else {
			this.logger.debug("--------------------------------------------------------------------------");
			this.logger.debug("Feature VOD 카테고리 상품 아님");
			this.logger.debug("--------------------------------------------------------------------------");
		}

		return response;
	}

	private FeatureVodProductResponse generateResponse(String listId, List<FeatureVodProductDTO> resultList) {
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

			App app = null;
			Support support = null;
			Contributor contributor = null;
			Date date = null;
			Book book = null;

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

				// 어플리케이션 상품
				if ("ADM000000008".equals(listId)) {
					// 상품 타입 (에피소드상품)
					identifier.setType("episode");

					// 상품 지원 구분 정보
					support = new Support();
					supportList = new ArrayList<Support>();
					support.setType("drm");
					// support.setText(productDto.getDrmYn());
					supportList.add(support);

					support = new Support();
					support.setType("inApp");
					// support.setText(productDto.getPartParentClsfCd());
					supportList.add(support);
					product.setSupportList(supportList);
					product.setApp(app);
				} else if ("ebook".equals(listId)) {
					// 상품 타입 (채널상품)
					identifier.setType("channel");

					// 메타클래스 정보
					menu = new Menu();
					menu.setType("metaClass");
					menu.setId("CT20");
					menuList.add(menu);

					// 저작권자 정보
					contributor = new Contributor();
					contributor.setName("홍길동");
					contributor.setCompany("티스토어");

					date = new Date();
					date.setType("date/publish");
					date.setText("20131224");
					contributor.setDate(date);
					product.setContributor(contributor);

					// 도서 정보
					book = new Book();
					book.setTotalPages("30");
					book.setStatus("continue");

					// 상품 지원 구분 정보
					support = new Support();
					supportList = new ArrayList<Support>();
					support.setType("play");
					support.setText("Y");
					supportList.add(support);

					support = new Support();
					support.setType("store");
					support.setText("Y");
					supportList.add(support);
					book.setSupportList(supportList);
					product.setBook(book);

					product.setLatestIssue("에스콰이어 9월호");
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
