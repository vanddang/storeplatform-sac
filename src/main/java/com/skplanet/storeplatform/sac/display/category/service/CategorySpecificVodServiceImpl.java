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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 특정 상품 Vod 조회 Service 구현체
 * 
 * Updated on : 2014. 2. 3. Updated by : 이승훈, 엔텔스.
 */
@Service
public class CategorySpecificVodServiceImpl implements CategorySpecificVodService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
    private MemberBenefitService memberBenefitService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.category.service.CategorySpecificVodService#getSpecificVodList
	 * (com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public CategorySpecificSacRes getSpecificVodList(CategorySpecificSacReq req, SacRequestHeader header) {

		CategorySpecificSacRes res = new CategorySpecificSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Product product = null;
		MetaInfo metaInfo = null;
		List<Product> productList = new ArrayList<Product>();

		if (req.getDummy() == null) {

			List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
			if (prodIdList.size() > DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT) {
				throw new StorePlatformException("SAC_DSP_0004", "list",
						DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT);
			}

			// 상품 기본 정보 List 조회
			List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList(
					"CategorySpecificProduct.selectProductInfoList", prodIdList, ProductBasicInfo.class);

			if (productBasicInfoList != null) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("tenantHeader", header.getTenantHeader());
				paramMap.put("deviceHeader", header.getDeviceHeader());
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

					// vod 상품의 경우
					if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
						// 영화/방송 상품의 경우
						paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
						if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
								|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
							this.log.debug("##### Search for Vod specific product");
							metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getVODMetaInfo",
									paramMap, MetaInfo.class);

							// metaInfo = this.metaInfoService.getVODMetaInfo(paramMap);
							if (metaInfo != null) {
								// Tstore멤버십 적립율 정보
		                    	metaInfo.setMileageInfo(memberBenefitService.getMileageInfo(header.getTenantHeader().getTenantId(), metaInfo.getTopMenuId(), metaInfo.getProdId(), metaInfo.getProdAmt()));
		                    	
								if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
									product = this.responseInfoGenerateFacade.generateSpecificMovieProduct(metaInfo);
								} else {
									product = this.responseInfoGenerateFacade
											.generateSpecificBroadcastProduct(metaInfo);
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
		Identifier identifier = new Identifier();
		List<Identifier> identifierList;
		Support support = null;
		Menu menu = null;
		Contributor contributor = null;
		Accrual accrual = null;
		Rights rights = null;
		Title title = null;
		Source source = null;
		Price price = null;
		Play play = null;
		Store store = null;
		Date date = null;
		Distributor distributor = null;

		List<Menu> menuList = new ArrayList<Menu>();
		List<Source> sourceList = new ArrayList<Source>();
		List<Support> supportList = new ArrayList<Support>();
		Product product = new Product();
		List<Product> productList = new ArrayList<Product>();
		CommonResponse commonResponse = new CommonResponse();
		CategorySpecificSacRes res = new CategorySpecificSacRes();

		contributor = new Contributor();
		accrual = new Accrual();
		rights = new Rights();
		title = new Title();
		source = new Source();
		price = new Price();
		support = new Support();
		play = new Play();
		store = new Store();
		distributor = new Distributor();

		// Identifier 설정
		identifierList = new ArrayList<Identifier>();
		identifier.setType("channelId");
		identifier.setText("H001540562");
		identifierList.add(identifier);

		// support (지원구분) 설정
		support.setType("hd");
		support.setText("Y");
		supportList.add(support);

		// menu 설정
		menu = new Menu();
		menu.setId("DP17");
		menu.setName("영화");
		menu.setType("topClass");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("DP17002");
		menu.setName("액션");
		menuList.add(menu);

		// title 설정
		title.setText("[20%할인]친구 2");

		// accrual 설정
		accrual.setVoterCount(51);
		accrual.setDownloadCount(5932);
		accrual.setScore(3.8);

		// rights 설정
		rights.setGrade("PD004401");
		support = new Support();
		support.setType("play");
		support.setText("N");
		play.setSupport(support);
		support = new Support();
		support.setType("store");
		support.setText("N");
		store.setSupport(support);
		rights.setPlay(play);
		rights.setStore(store);

		// contributor 설정
		contributor.setDirector("청출어람");
		contributor.setArtist("유오성");
		date = new Date();
		date.setText("20140206");
		contributor.setDate(date);

		// distributor 설정
		distributor.setName("ubivelox");
		distributor.setTel("0211112222");
		distributor.setEmail("signtest@yopmail.com");

		// source 설정
		source.setUrl("http://wap.tstore.co.kr/SMILE_DATA7/PVOD/201401/02/0002057676/3/0003876930/3/RT1_02000024893_1_0921_182x261_130x186.PNG");
		sourceList.add(source);

		// price 설정
		price.setText(3200);

		product.setIdentifier(identifier);
		product.setSupportList(supportList);
		product.setMenuList(menuList);
		product.setAccrual(accrual);
		product.setTitle(title);
		product.setSourceList(sourceList);
		product.setProductExplain("니 내랑 부산 접수할래? ...");
		product.setPrice(price);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setDistributor(distributor);
		productList.add(product);

		commonResponse.setTotalCount(productList.size());
		res.setCommonResponse(commonResponse);
		res.setProductList(productList);
		return res;
	}
}
