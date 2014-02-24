/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 특정 상품 ebook/코믹 조회 Service 구현체
 * 
 * Updated on : 2014. 2. 3. Updated by : 이승훈, 엔텔스.
 */
@Service
public class CategorySpecificEbookServiceImpl implements CategorySpecificEbookService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private DisplayCommonService displayCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.category.service.CategorySpecificEbookService#getSpecificEbookList
	 * (com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public CategorySpecificSacRes getSpecificEbookList(CategorySpecificSacReq req, SacRequestHeader header) {

		CategorySpecificSacRes res = new CategorySpecificSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Product product = null;
		MetaInfo metaInfo = null;
		List<Product> productList = new ArrayList<Product>();

		if (req.getDummy() == null) {

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(req.getList())) {
				throw new StorePlatformException("SAC_DSP_0002", "pid", req.getList());
			}

			List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
			if (prodIdList.size() > DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT) {
				throw new StorePlatformException("SAC_DSP_0004", "list",
						DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT);
			}

			// 상품 기본 정보 List 조회
			List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
					"CategorySpecificProduct.selectProductInfoList", prodIdList, ProductBasicInfo.class);

			this.log.debug("##### parameter cnt : {}", prodIdList.size());
			this.log.debug("##### selected product basic info cnt : {}", productBasicInfoList.size());
			if (productBasicInfoList != null) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("tenantHeader", header.getTenantHeader());
				paramMap.put("deviceHeader", header.getDeviceHeader());
				paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
				paramMap.put("lang", "ko");

				for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
					String topMenuId = productBasicInfo.getTopMenuId();
					String svcGrpCd = productBasicInfo.getSvcGrpCd();
					paramMap.put("productBasicInfo", productBasicInfo);

					this.log.debug("##### Top Menu Id : {}", topMenuId);
					this.log.debug("##### Service Group Cd : {}", svcGrpCd);

					// 상품 SVC_GRP_CD 조회
					// DP000203 : 멀티미디어
					// DP000206 : Tstore 쇼핑
					// DP000205 : 소셜쇼핑
					// DP000204 : 폰꾸미기
					// DP000201 : 애플리캐이션

					// ebook/코믹 상품의 경우
					if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
						if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
								|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의
																							  // 경우

							paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
							this.log.debug("##### Search for EbookComic specific product");
							metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getEbookComicMetaInfo",
									paramMap, MetaInfo.class);
							if (metaInfo != null) {
								if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
									product = this.responseInfoGenerateFacade.generateSpecificEbookProduct(metaInfo);
								} else {
									product = this.responseInfoGenerateFacade.generateSpecificComicProduct(metaInfo);
								}
								productList.add(product);
							}
						}
					}
				}
			}
			commonResponse.setTotalCount(productList.size());
			res.setCommonResponse(commonResponse);
			res.setProductList(productList);
			return res;
		} else {
			return this.generateDummy();
		}
	}

	/**
	 * <pre>
	 * 더미 데이터 생성.
	 * </pre>
	 * 
	 * @return CategorySpecificSacRes
	 */
	private CategorySpecificSacRes generateDummy() {
		Identifier identifier = null;
		List<Identifier> identifierList = new ArrayList<Identifier>();
		Support support = null;
		Menu menu = null;
		Contributor contributor = null;
		Accrual accrual = null;
		Rights rights = null;
		Title title = null;
		Source source = null;
		Price price = null;
		App app = null;
		Distributor distributor = null;
		Book book = null;
		Chapter chapter = null;

		List<Menu> menuList = null;
		List<Source> sourceList = null;
		List<Support> supportList = null;
		Product product = new Product();
		List<Product> productList = new ArrayList<Product>();
		CommonResponse commonResponse = new CommonResponse();
		CategorySpecificSacRes res = new CategorySpecificSacRes();

		menuList = new ArrayList<Menu>();
		sourceList = new ArrayList<Source>();
		supportList = new ArrayList<Support>();

		identifier = new Identifier();
		app = new App();
		accrual = new Accrual();
		rights = new Rights();
		source = new Source();
		price = new Price();
		title = new Title();
		support = new Support();
		distributor = new Distributor();
		book = new Book();
		chapter = new Chapter();
		contributor = new Contributor();

		// Identifier 설정
		identifier.setType("episodeId");
		identifier.setText("H900063306");
		identifierList.add(identifier);

		// support (지원구분) 설정
		support = new Support();
		support.setType("play");
		support.setText("N");
		supportList.add(support);
		support = new Support();
		support.setType("store");
		support.setText("Y");
		supportList.add(support);

		// mene 설정
		menu = new Menu();
		menu.setId("DP14");
		menu.setName("만화");
		menu.setType("topClass");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("DP14009");
		menu.setName("잡지");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("CT22");
		menu.setType("metaClass");
		menuList.add(menu);

		// accrual 설정
		accrual.setVoterCount(3);
		accrual.setDownloadCount(2);

		// rights 설정
		rights.setGrade("PD004404");

		// title 설정
		title.setText("[0728_1]20대벼락부자");

		// source 설정
		source.setMediaType("image/jpeg");
		source.setType("thumbnail");
		source.setSize(659069);
		source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");
		sourceList.add(source);

		// price 설정
		price.setText(0);

		// distributor 설정
		distributor.setName("ubivelox");
		distributor.setTel("0211112222");
		distributor.setEmail("signtest@yopmail.com");

		// contributor 설정
		contributor.setName("김기백");
		contributor.setPublisher("코믹플러스");

		// book 설정
		chapter.setUnit("4");
		book.setChapter(chapter);
		book.setSupportList(supportList);

		product.setIdentifierList(identifierList);
		product.setSupportList(supportList);
		product.setMenuList(menuList);
		product.setApp(app);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setTitle(title);
		product.setSourceList(sourceList);
		product.setProductExplain("돈 벼락을 꿈꾸는 한 20대의 인생역전기. 돈 한 푼 없는 빈털터리 거창은 횡");
		product.setPrice(price);
		product.setDistributor(distributor);
		product.setContributor(contributor);

		productList.add(product);

		commonResponse.setTotalCount(productList.size());
		res.setCommonResponse(commonResponse);
		res.setProductList(productList);
		return res;
	}
}
