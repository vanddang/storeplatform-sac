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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
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
 * 특정 상품 Music 조회 Service 구현체
 * 
 * Updated on : 2014. 2. 3. Updated by : 이승훈, 엔텔스.
 */
@Service
@Transactional
public class CategorySpecificMusicServiceImpl implements CategorySpecificMusicService {
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
	 * @see com.skplanet.storeplatform.sac.display.category.service.CategorySpecificMusicService#getSpecificMusicList
	 * (com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificSacReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public CategorySpecificSacRes getSpecificMusicList(CategorySpecificSacReq req, SacRequestHeader header) {

		String tenantId = header.getTenantHeader().getTenantId();

		CategorySpecificSacRes res = new CategorySpecificSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Product product = null;
		MetaInfo metaInfo = null;
		List<Product> productList = new ArrayList<Product>();

		if (req.getDummy() == null) {

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(req.getList())) {
				this.log.debug("----------------------------------------------------------------");
				this.log.debug("필수 파라미터 부족");
				this.log.debug("----------------------------------------------------------------");

				res.setCommonResponse(commonResponse);
				return res;
			}

			List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
			if (prodIdList.size() > 50) {
				// TODO osm1021 에러 처리 추가 필요
				this.log.error("## prod id over 50 : {}" + prodIdList.size());
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

					// 음원 상품의 경우
					if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) {
						// 배치완료 기준일시 조회
						// TODO osm1021 기준 ListID가 없기 때문에 일단 멜론 Top 100으로 고정
						String stdDt = this.displayCommonService.getBatchStandardDateString(tenantId, "MELON_DP004901");
						// paramMap.put("stdDt", stdDt);
						productBasicInfo.setMenuId("DP004901");
						paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);

						// TODO osm1021 dummy data 꼭 삭제할것
						paramMap.put("stdDt", "20110806");

						this.log.debug("##### Search for Music specific product");
						metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getMusicMetaInfo", paramMap,
								MetaInfo.class);
						if (metaInfo != null) {
							product = this.responseInfoGenerateFacade.generateSpecificMusicProduct(metaInfo);
							productList.add(product);
						}
					}
				}
			}
			commonResponse.setTotalCount(productList.size());
			res.setCommonResponse(commonResponse);
			res.setProductList(productList);
			return res;
		} else {

			String DumnyProdId = "H900063559";
			List<String> prodIdList = Arrays.asList(StringUtils.split(DumnyProdId, "+"));

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

					// 음원 상품의 경우
					if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) {
						// 배치완료 기준일시 조회
						// TODO osm1021 기준 ListID가 없기 때문에 일단 멜론 Top 100으로 고정
						String stdDt = this.displayCommonService.getBatchStandardDateString(tenantId, "MELON_DP004901");
						// paramMap.put("stdDt", stdDt);
						productBasicInfo.setMenuId("DP004901");
						paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);

						// TODO osm1021 dummy data 꼭 삭제할것
						paramMap.put("stdDt", "20110806");

						this.log.debug("##### Search for Music specific product");
						metaInfo = this.commonDAO.queryForObject("CategorySpecificProduct.getMusicMetaInfo", paramMap,
								MetaInfo.class);
						if (metaInfo != null) {
							product = this.responseInfoGenerateFacade.generateSpecificMusicProduct(metaInfo);
							productList.add(product);
						}
					}
				}
			}
			commonResponse.setTotalCount(productList.size());
			res.setCommonResponse(commonResponse);
			res.setProductList(productList);
			return res;
			// return this.generateDummy();
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
		List<Identifier> identifierList;
		Support support = null;
		Menu menu = null;
		Contributor contributor = null;
		Date date = null;
		Accrual accrual = null;
		Rights rights = null;
		Title title = null;
		Source source = null;
		Price price = null;
		Music music = null;
		Distributor distributor = null;

		List<Menu> menuList = null;
		List<Source> sourceList = null;
		Product product = null;
		List<Product> productList = new ArrayList<Product>();
		CommonResponse commonResponse = new CommonResponse();
		CategorySpecificSacRes res = new CategorySpecificSacRes();

		productList = new ArrayList<Product>();
		menuList = new ArrayList<Menu>();
		sourceList = new ArrayList<Source>();

		product = new Product();
		identifier = new Identifier();
		contributor = new Contributor();
		accrual = new Accrual();
		rights = new Rights();
		title = new Title();
		source = new Source();
		price = new Price();
		support = new Support();
		music = new Music();
		distributor = new Distributor();

		// 상품ID
		identifierList = new ArrayList<Identifier>();
		identifier = new Identifier();
		identifier.setType("channelId");
		identifier.setText("H900063558");
		identifierList.add(identifier);
		identifier = new Identifier();
		identifier.setType("episode");
		identifier.setText("H900063559");
		identifierList.add(identifier);
		identifier = new Identifier();
		identifier.setType("song");
		identifier.setText("3436104");
		identifierList.add(identifier);

		// title 설정
		title.setText("MUSIC IMG RESIZE TEST UPDATE");

		// price 설정
		price.setText(500);

		// mene 설정
		menu = new Menu();
		menu.setId("DP16");
		menu.setName("음악");
		menu.setType("topClass");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("DP16002004");
		menu.setName("OST");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("CT25");
		menu.setName("metaClass");
		menuList.add(menu);

		// source 설정
		source.setMediaType("");
		source.setSize(0);
		source.setType("thumbnail");
		source.setUrl("/data/SMILE_DATA/PMUSIC/201207/05/0000107112/2/0000116514/2/");
		sourceList.add(source);

		// accrual 설정
		accrual.setVoterCount(51);
		accrual.setDownloadCount(5932);
		accrual.setScore(3.8);

		// rights 설정
		rights.setGrade("PD004401");

		// contributor 설정
		contributor.setDirector("김범수,박정현, 김범수");
		contributor.setArtist("폴라리스엔터테인먼트");
		contributor.setAgency("(주)로엔엔터테인먼트");
		contributor.setAlbum("Solista Part.2");

		// distributor 설정
		distributor.setName("ubivelox");
		distributor.setTel("0211112222");
		distributor.setEmail("signtest@yopmail.com");

		product = new Product();
		product.setIdentifier(identifier);
		product.setMenuList(menuList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setTitle(title);
		product.setSourceList(sourceList);
		product.setPrice(price);
		productList.add(product);

		commonResponse.setTotalCount(productList.size());
		res.setCommonResponse(commonResponse);
		res.setProductList(productList);
		return res;
	}
}
