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
import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategorySpecificRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.category.vo.CategorySpecificProduct;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoGenerateProxyService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

@Service
@Transactional
public class CategorySpecificProductServiceImpl implements CategorySpecificProductService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoGenerateProxyService metaInfoGenerateProxyService;

	@Override
	public CategorySpecificRes getSpecificProductList(CategorySpecificReq req, SacRequestHeader header) {
		String tenantId = header.getTenantHeader().getTenantId();

		CategorySpecificRes res = new CategorySpecificRes();
		CommonResponse commonResponse = new CommonResponse();
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
		App app = null;

		List<Menu> menuList = null;
		List<Source> sourceList = null;
		List<Support> supportList = null;
		List<Product> productList = new ArrayList<Product>();

		if (req.getDummy() == null) {
			List<String> prodIdList = Arrays.asList(StringUtils.split(req.getList(), "+"));
			if (prodIdList == null || prodIdList.size() == 0) {
				// TODO osm1021 에러 처리 추가 필요
			} else if (prodIdList.size() > 50) {
				// TODO osm1021 에러 처리 추가 필요
			}

			List<CategorySpecificProduct> svcGrpCdList = this.commonDAO.queryForList(
					"CategorySpecificProduct.selectProductInfoList", prodIdList, CategorySpecificProduct.class);

			for (CategorySpecificProduct categorySpecificProduct : svcGrpCdList) {
				String prodId = categorySpecificProduct.getProdId();
				String svcGrpCd = categorySpecificProduct.getSvcGrpCd();
				String svcTypeCd = categorySpecificProduct.getSvcTypeCd();
				String topMenuId = categorySpecificProduct.getTopMenuId();
				String contentsTypeCd = categorySpecificProduct.getContentsTypeCd();

				// TODO osm1021 더미 데이터 꼭 삭제할것
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("deviceModelCd", "SHV-E210S");
				paramMap.put("prodId", prodId);
				paramMap.put("tenantId", "S01");
				paramMap.put("imageCd", "DP000101");

				ProductBasicInfo productBasicInfo = new ProductBasicInfo();

				// 상품 SVC_GRP_CD 조회
				// DP000203 : 멀티미디어
				// DP000206 : Tstore 쇼핑
				// DP000205 : 소셜쇼핑
				// DP000204 : 폰꾸미기
				// DP000201 : 애플리캐이션

				// APP 상품의 경우
				if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
					MetaInfo retMetaInfo = this.commonDAO.queryForObject("MetaInfo.getAppMetaInfo", productBasicInfo,
							MetaInfo.class);
					if (retMetaInfo != null) {
						product = this.metaInfoGenerateProxyService.generateAppProductProxy(retMetaInfo);
						productList.add(product);
					}
				}
				// 멀티미디어 타입일 경우
				else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) {

					productBasicInfo.setProdId(prodId);
					productBasicInfo.setTenantId(tenantId);

					// VOD 상품의 경우
					if (DisplayConstants.DP_VOD_TOP_MENU_ID.equals(topMenuId)) {
						MetaInfo retMetaInfo = this.commonDAO.queryForObject("MetaInfo.getVODMetaInfo",
								productBasicInfo, MetaInfo.class);

						if (retMetaInfo != null) {
							if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
								product = this.metaInfoGenerateProxyService.generateMovieProductProxy(retMetaInfo);
							} else {
								product = this.metaInfoGenerateProxyService
										.generateBroadCastingProductProxy(retMetaInfo);
							}

							productList.add(product);
						}
					}
					// Ebook / Comic 상품의 경우
					else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) {
						MetaInfo retMetaInfo = this.commonDAO.queryForObject("MetaInfo.getEbookComidMetaInfo",
								productBasicInfo, MetaInfo.class);
						if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
							product = this.metaInfoGenerateProxyService.generateEbookProductProxy(retMetaInfo);
						} else {
							product = this.metaInfoGenerateProxyService.generateComicProductProxy(retMetaInfo);
						}

						productList.add(product);
					}
					// 음원 상품의 경우
					else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) {
						MetaInfo retMetaInfo = this.commonDAO.queryForObject("MetaInfo.getMusicMetaInfo",
								productBasicInfo, MetaInfo.class);
						product = this.metaInfoGenerateProxyService.generateMusicProductProxy(retMetaInfo);
						productList.add(product);
					}
				}
				// 쇼핑 상품의 경우
				else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
					MetaInfo retMetaInfo = this.commonDAO.queryForObject("MetaInfo.getMusicMetaInfo", productBasicInfo,
							MetaInfo.class);
				}
			}
			res.setProductList(productList);
		} else {
			// TODO osm1021 Dummy data 작업이 종료되면 삭제할것
			productList = new ArrayList<Product>();
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			supportList = new ArrayList<Support>();
			for (int i = 1; i <= 1; i++) {
				product = new Product();
				identifier = new Identifier();
				app = new App();
				accrual = new Accrual();
				rights = new Rights();
				source = new Source();
				price = new Price();
				title = new Title();
				support = new Support();

				// 상품ID
				identifier = new Identifier();
				identifier.setType("episodeId");
				identifier.setText("0000643818");

				support.setType("iab");
				support.setText("PD012301");
				supportList.add(support);

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				menu = new Menu();
				menu.setId("DP000501");
				menu.setName("게임");
				menu.setType("topClass");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("DP01004");
				menu.setName("RPG");
				menuList.add(menu);

				/*
				 * App aid, packagename, versioncode, version
				 */
				app.setAid("OA00643818");
				app.setPackageName("proj.syjt.tstore");
				app.setVersionCode("11000");
				app.setVersion("1.1");

				/*
				 * Accrual voterCount (참여자수) DownloadCount (다운로드 수) score(평점)
				 */
				accrual.setVoterCount(14305);
				accrual.setDownloadCount(513434);
				accrual.setScore(4.8);

				/*
				 * Rights grade
				 */
				rights.setGrade("0");

				title.setText("워밸리 온라인");

				/*
				 * source mediaType, size, type, url
				 */
				source.setType("thumbnail");
				source.setUrl("http://wap.tstore.co.kr/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");
				sourceList.add(source);

				/*
				 * Price text
				 */
				price.setText(0);

				product = new Product();
				product.setIdentifier(identifier);
				// product.setSupport("y|iab");
				product.setSupportList(supportList);
				product.setMenuList(menuList);
				product.setApp(app);
				product.setAccrual(accrual);
				product.setRights(rights);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setProductExplain("★이벤트★세상에 없던 모바일 MMORPG!");
				product.setPrice(price);

				productList.add(product);
			}

			for (int i = 1; i <= 1; i++) {

				menuList = new ArrayList<Menu>();
				sourceList = new ArrayList<Source>();
				supportList = new ArrayList<Support>();

				product = new Product();
				identifier = new Identifier();
				contributor = new Contributor();
				accrual = new Accrual();
				rights = new Rights();
				title = new Title();
				source = new Source();
				price = new Price();
				support = new Support();

				// 상품ID
				identifier = new Identifier();
				identifier.setType("channelId");
				identifier.setText("H001540562");

				support.setType("hd");
				support.setText("Y");
				supportList.add(support);

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				menu = new Menu();
				menu.setId("DP000517");
				menu.setName("영화");
				menu.setType("topClass");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("DP17002");
				menu.setName("액션");
				menuList.add(menu);

				// fiteredBy = ebook
				// fiteredBy = comic
				// fiteredBy = ebook+comic

				// contributor.setDirector("곽경택");
				// contributor.setArtist("유오성,주진모,김우빈,박아인,강한나,한수아");
				// Date date = new Date();
				// date.setText("20131114");
				// contributor.setDate(date);
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

				title.setText("[20%할인]친구 2");

				/*
				 * source mediaType - url
				 */
				source.setUrl("http://wap.tstore.co.kr/SMILE_DATA7/PVOD/201401/02/0002057676/3/0003876930/3/RT1_02000024893_1_0921_182x261_130x186.PNG");
				sourceList.add(source);

				/*
				 * Price text
				 */
				price.setText(3200);

				product = new Product();
				product.setIdentifier(identifier);
				product.setSupportList(supportList);
				// product.setSupport("hd");
				product.setMenuList(menuList);
				// product.setContributor(contributor);
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

			for (int i = 1; i <= 1; i++) {

				menuList = new ArrayList<Menu>();
				sourceList = new ArrayList<Source>();
				supportList = new ArrayList<Support>();

				product = null;
				identifier = null;
				menu = null;
				rights = null;
				title = null;
				source = null;
				accrual = null;
				price = null;
				contributor = null;
				accrual = null;
				date = null;
				SalesOption saleoption = null;

				// 상품 정보 (상품ID)
				product = new Product();
				identifier = new Identifier();
				identifier.setType("catagoryId");
				identifier.setText("1234");

				// 메뉴 정보
				menuList = new ArrayList<Menu>();
				menu = new Menu();
				menu.setType("topClass");
				menu.setId("DP000528");
				menu.setName("쇼핑");
				menuList.add(menu);

				menu = new Menu();
				menu.setType("topClass");
				menu.setId("DP28013");
				menu.setName("버거/치킨/피자");
				menuList.add(menu);

				// 상품 정보 (상품명)
				title = new Title();
				title.setText("커피/음료/아이스크림");

				// 상품 정보 (상품가격)
				price = new Price();
				price.setFixedPrice(17500);
				price.setDiscountRate(10d);
				price.setText(15750);

				// 이미지 정보
				sourceList = new ArrayList<Source>();
				source = new Source();
				source.setType("thumbnail");
				source.setUrl("inst_thumbnail_20111216154840.jpg");
				sourceList.add(source);

				// 다운로드 수
				accrual = new Accrual();
				accrual.setDownloadCount(6229);

				// 이용권한 정보
				rights = new Rights();
				date = new Date();
				date.setText("20130820190000/20131231235959");
				rights.setGrade("PD004401");
				rights.setDate(date);

				// contributor
				contributor = new Contributor();
				identifier = new Identifier();
				identifier.setType("brandId");
				identifier.setText("세븐일레븐 바이더웨이");
				contributor.setIdentifier(identifier);

				// saleoption
				saleoption = new SalesOption();

				// 데이터 매핑
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setPrice(price);
				product.setSourceList(sourceList);
				product.setAccrual(accrual);
				product.setRights(rights);
				product.setContributor(contributor);
				productList.add(product);
			}
			commonResponse.setTotalCount(productList.size());
			res.setCommonResponse(commonResponse);
			res.setProductList(productList);
		}
		return res;
	}
}
