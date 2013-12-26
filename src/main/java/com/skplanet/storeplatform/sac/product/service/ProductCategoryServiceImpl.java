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
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.product.vo.category.ProductCategoryRequest;
import com.skplanet.storeplatform.sac.client.product.vo.category.ProductCategoryResponse;
import com.skplanet.storeplatform.sac.client.product.vo.common.ProductCommonResponse;
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
import com.skplanet.storeplatform.sac.product.vo.ProductCategoryDTO;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
	private transient Logger logger = LoggerFactory.getLogger(ProductCommonServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ProductCommonService productCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.ProductCategoryService#searchCategoryProductList(com.skplanet.
	 * storeplatform.sac.client.product.vo.category.ProductCategoryRequest)
	 */
	@Override
	public ProductCategoryResponse searchCategoryProductList(ProductCategoryRequest request) {
		ProductCategoryResponse response = null;

		// 메뉴 정보 조회
		ProductCommonResponse menuInfo = this.productCommonService.searchMenuInfo(request);

		// 메뉴 정보 조회 성공
		if ("00".equals(menuInfo.getErrorCode())) {
			List<ProductCategoryDTO> productList = null;

			if ("MN01".equals(menuInfo.getUpMenuId()) || "MN02".equals(menuInfo.getUpMenuId())
					|| "MN03".equals(menuInfo.getUpMenuId()) || "MN04".equals(menuInfo.getUpMenuId())) {
				this.logger.debug("--------------------------------------------------------------------------");
				this.logger.debug("어플리케이션 상품 조회");
				this.logger.debug("--------------------------------------------------------------------------");

				productList = this.commonDAO.queryForList("ProductCategory.selectCategoryList", request,
						ProductCategoryDTO.class);

				// Response 생성
				response = this.generateResponse("app", productList);
			} else if ("MN13".equals(menuInfo.getUpMenuId())) {
				this.logger.debug("--------------------------------------------------------------------------");
				this.logger.debug("이북 상품 조회");
				this.logger.debug("--------------------------------------------------------------------------");

				ProductCategoryDTO productDto = new ProductCategoryDTO();
				productDto.setProdId("0000633368");
				productDto.setUpMenuId("MN13");
				productDto.setUpMenuNm("eBook");
				productDto.setMenuId("MN13001");
				productDto.setMenuNm("경제경영");
				productDto.setDownMenuId("MN13001011");
				productDto.setDownMenuNm("MN13001011");
				productDto.setProdNm("광해 왕이 된 남자");
				productDto.setProdBaseDesc("백성을 섬기는 진짜 왕을 만나다!");
				productDto
						.setImgFilePath("/data4/android/201309/23/IF1023501443320130904133732/0000633341/img/original/0000633341_DP000101.jpg");
				productDto.setProdAmt("3000");
				productDto.setProdGrdCd("PD004403");

				productList = new ArrayList<ProductCategoryDTO>();
				productList.add(productDto);

				// Response 생성
				response = this.generateResponse("ebook", productList);
			} else if ("MN14".equals(menuInfo.getUpMenuId())) {
				this.logger.debug("--------------------------------------------------------------------------");
				this.logger.debug("만화 상품 조회");
				this.logger.debug("--------------------------------------------------------------------------");

				ProductCategoryDTO productDto = new ProductCategoryDTO();
				productDto.setProdId("0000633368");
				productDto.setUpMenuId("MN14");
				productDto.setUpMenuNm("만화");
				productDto.setMenuId("MN14001");
				productDto.setMenuNm("무협");
				productDto.setProdNm("적사풍");
				productDto.setProdBaseDesc("고독을 사랑하는 자는 신이던가, 아니면 야수이다.");
				productDto.setImgFilePath("/data4/img/original/0000633341_DP000101.jpg");
				productDto.setProdAmt("5000");
				productDto.setProdGrdCd("PD004403");

				productList = new ArrayList<ProductCategoryDTO>();
				productList.add(productDto);

				// Response 생성
				response = this.generateResponse("comic", productList);
			} else {
				this.logger.debug("--------------------------------------------------------------------------");
				this.logger.debug("일반 카테고리 상품이 아님");
				this.logger.debug("--------------------------------------------------------------------------");
			}

		} else {
			this.logger.debug("메뉴 정보 조회 오류 발생");
		}

		return response;
	}

	/**
	 * <pre>
	 * 일반 카테고리 상품 (어플리케이션, 이북, 만화) Response 생성.
	 * </pre>
	 * 
	 * @param productClass
	 * @param resultList
	 * @return ProductCategoryResponse
	 */
	private ProductCategoryResponse generateResponse(String productClass, List<ProductCategoryDTO> resultList) {
		ProductCategoryResponse response = new ProductCategoryResponse();

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

			ProductCategoryDTO productDto = null;

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
				if ("app".equals(productClass)) {
					// 상품 타입 (에피소드상품)
					identifier.setType("episode");

					// 상품 지원 구분 정보
					support = new Support();
					supportList = new ArrayList<Support>();
					support.setType("drm");
					support.setText(productDto.getDrmYn());
					supportList.add(support);

					support = new Support();
					support.setType("inApp");
					support.setText(productDto.getPartParentClsfCd());
					supportList.add(support);
					product.setSupportList(supportList);

					// 어플리케이션 정보
					app = new App();
					app.setAid(productDto.getAid());
					app.setPackageName(productDto.getApkPkgNm());
					app.setVersionCode(productDto.getApkVer());
					app.setVersion(productDto.getProdVer());
					product.setApp(app);
				} else if ("ebook".equals(productClass)) {
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
				} else if ("comic".equals(productClass)) {
					// 상품 타입 (채널상품)
					identifier.setType("channel");

					// 메타클래스 정보
					menu = new Menu();
					menu.setType("metaClass");
					menu.setId("CT20");
					menuList.add(menu);

					// 도서 정보
					book = new Book();
					book.setType("serial");
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

			response = new ProductCategoryResponse();
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

		return response;
	}
}
