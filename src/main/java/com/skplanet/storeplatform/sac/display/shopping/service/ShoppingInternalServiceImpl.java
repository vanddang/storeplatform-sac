/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.shopping.service;

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
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingReq;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SelectOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SubSelectOption;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.ShoppingInfoGenerator;

/**
 * 쇼핑 판매건수 Implements
 * 
 * Updated on : 2014. 02. 13. Updated by : 김형식
 */
@Service
public class ShoppingInternalServiceImpl implements ShoppingInternalService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	HistoryInternalSCI historyInternalSCI;

	@Autowired
	private SellerSearchSCI sellerSearchSCI;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private ShoppingInfoGenerator shoppingGenerator;

	@Override
	public ShoppingRes searchShoppingInternal(ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		req.setTenantId(req.getTenantId());
		req.setDeviceModelCd(req.getDeviceModelCd());
		req.setLangCd(req.getLangCd());
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANDROID_STANDARD2_NM);
		req.setProdRshpCd(DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		// 필수 파라미터 체크
		if (StringUtils.isEmpty(req.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (!"channel".equals(req.getType()) && !"episode".equals(req.getType())) {
			throw new StorePlatformException("SAC_DSP_0003", "type", req.getType());
		}
		if (StringUtils.isEmpty(req.getProdId())) {
			throw new StorePlatformException("SAC_DSP_0002", "prodId", req.getProdId());
		}

		if (StringUtils.equals("episode", req.getType())) {
			MetaInfo channelByepisode = this.commonDAO.queryForObject("Shopping.getChannelByepisode", req,
					MetaInfo.class);
			if (channelByepisode != null) {
				req.setSpecialProdId(req.getProdId());
				req.setProdId(channelByepisode.getCatalogId());
			} else {
				throw new StorePlatformException("SAC_DSP_0005", req.getProdId());
			}
		}

		// OPTIONAL 파라미터 체크
		if (StringUtils.isEmpty(req.getSpecialProdId())) {
			req.setSpecialProdId(null);
		}
		if (StringUtils.isEmpty(req.getUserKey())) {
			req.setUserKey(null);
		}
		if (StringUtils.isEmpty(req.getDeviceKey())) {
			req.setDeviceKey(null);
		}

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("req", req);
		reqMap.put("lang", req.getLangCd());

		reqMap.put("imageCd", req.getImageCd());
		reqMap.put("svcGrpCd", DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD);
		reqMap.put("contentTypeCd", DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

		// ID list 조회
		List<MetaInfo> resultChannelList = this.commonDAO.queryForList("Shopping.getShoppingChannelDetail", reqMap,
				MetaInfo.class);

		if (resultChannelList == null) {
			throw new StorePlatformException("SAC_DSP_0005", req.getProdId());
		} else {
			if (resultChannelList.size() > 0) {

				// Response VO를 만들기위한 생성자
				Product product = null;
				Source source = null;

				// / 에피소드용
				Product episodeProduct = null;
				List<Identifier> episodeIdentifierList = null;
				Menu episodeMenu = null;
				List<Menu> episodeMenuList = new ArrayList<Menu>();
				Rights episodeRights = null;
				Date episodeDate = null;
				Distributor distributor = null;
				List<Date> episodeDateList = null;
				episodeDateList = new ArrayList<Date>();
				SalesOption episodeSaleOption = null;

				// / 옵션용
				SelectOption selectOption = null;
				SubSelectOption subSelectOption = null;
				List<SelectOption> selectOptionList = new ArrayList<SelectOption>();
				List<SubSelectOption> subSelectOptionList = new ArrayList<SubSelectOption>();
				Title option2Title = null;
				List<Product> subProductList = new ArrayList<Product>();

				List<Product> productList = new ArrayList<Product>();
				// 채널 list 조회
				for (int i = 0; i < resultChannelList.size(); i++) {
					MetaInfo shopping = resultChannelList.get(i);

					product = new Product();
					// 쿠폰코드 입력
					product.setCouponCode(shopping.getSrcContentId());

					// 상품 정보 (상품ID)
					product.setIdentifierList(this.commonGenerator.generateIdentifierList(shopping));

					// MenuList 생성
					List<Menu> menuList = this.commonGenerator.generateMenuList(shopping);

					// Title 생성
					Title title = this.commonGenerator.generateTitle(shopping);

					// SourceList 생성
					List<Source> sourceList = this.commonGenerator.generateSourceList(shopping);
					// 이미지 정보
					String detailImgCd = "";
					// 이미지 정보 (상세 이미지 가져오기)
					for (int qq = 0; qq < 2; qq++) {
						if (qq == 0) {
							detailImgCd = DisplayConstants.DP_SHOPPING_REPRESENT_DETAIL_IMAGE_CD;
						} else {
							detailImgCd = DisplayConstants.DP_SHOPPING_REPRESENT_CUT_DETAIL_IMAGE_CD;
						}
						reqMap.put("cutDetailImageCd", detailImgCd);
						List<MetaInfo> resultImgDetailList = this.commonDAO.queryForList(
								"Shopping.getShoppingImgDetailList", reqMap, MetaInfo.class);
						for (int pp = 0; pp < resultImgDetailList.size(); pp++) {
							source = new Source();
							if (qq == 0) {
								source.setType(DisplayConstants.DP_SOURCE_TYPE_DETAIL);
							} else {
								source.setExpoOrd(resultImgDetailList.get(pp).getExpoOrd());
								source.setType(DisplayConstants.DP_SOURCE_TYPE_CUT_DETAIL);
							}
							source.setUrl(resultImgDetailList.get(pp).getFilePath());
							sourceList.add(source);
						}
					}
					// Accrual 생성
					Accrual accrual = this.shoppingGenerator.generateAccrual(shopping);
					// Shopping용 Contributor 생성
					Contributor contributor = this.shoppingGenerator.generateContributor(shopping);

					String deliveryValue = shopping.getProdCaseCd();
					// 배송상품은 단품이며 기본정보는 동일
					if (deliveryValue.equals("delivery")) {
						reqMap.put("endRow", "1");
					}

					// 에피소드 list 조회
					List<MetaInfo> resultEpisodeList = this.commonDAO.queryForList("Shopping.getShoppingEpisodeDetail",
							reqMap, MetaInfo.class);
					if (resultEpisodeList != null) {
						for (int kk = 0; kk < resultEpisodeList.size(); kk++) {
							MetaInfo episodeShopping = resultEpisodeList.get(kk);

							episodeProduct = new Product();
							// 아이템코드 입력
							episodeProduct.setItemCode(episodeShopping.getSrcContentId());

							// 특가 상품일 경우
							if (!StringUtils.equals("episode", req.getType())) {
								episodeMenu = new Menu();
								episodeMenuList = new ArrayList<Menu>();
								episodeMenu.setType(episodeShopping.getSpecialSale());
								episodeMenuList.add(episodeMenu);
								episodeProduct.setMenuList(episodeMenuList);
							}

							// 채널 상품 정보 (상품ID)
							episodeIdentifierList = new ArrayList<Identifier>();
							episodeIdentifierList.add(this.commonGenerator.generateIdentifier(
									DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, episodeShopping.getProdId()));

							// 에피소드 상품 정보 (상품ID)
							episodeIdentifierList.add(this.commonGenerator.generateIdentifier(
									DisplayConstants.DP_EPISODE_IDENTIFIER_CD, episodeShopping.getPartProdId()));
							episodeProduct.setIdentifierList(episodeIdentifierList);

							// 에피소드 상품 가격 정보
							Price episodePrice = this.shoppingGenerator.generatePrice(episodeShopping);
							episodeProduct.setPrice(episodePrice);

							// 에피소드 날짜 권한 정보
							episodeDateList = new ArrayList<Date>();
							episodeRights = new Rights();

							episodeDate = new Date(DisplayConstants.DP_SHOPPING_RIGHTS_TYPE_NM,
									DateUtils.parseDate(episodeShopping.getApplyStartDt()),
									DateUtils.parseDate(episodeShopping.getApplyEndDt()));
							episodeDateList.add(episodeDate);

							episodeDate = new Date();
							episodeDate.setType(DisplayConstants.DP_SHOPPING_RIGHTS_TYPE_UNIT_NM);
							episodeDate.setText(episodeShopping.getUsePeriod());

							episodeDateList.add(episodeDate);

							episodeRights.setGrade(episodeShopping.getProdGrdCd());
							episodeRights.setDateList(episodeDateList);
							episodeProduct.setRights(episodeRights);

							// saleOption 셋팅
							episodeSaleOption = new SalesOption();
							episodeSaleOption.setBtob(episodeShopping.getB2bProdYn()); // B2B_상품_여부
							episodeSaleOption.setType(shopping.getProdCaseCd());

							if (episodeShopping.getSoldOut().equals("Y")) {
								episodeSaleOption.setSatus(DisplayConstants.DP_SOLDOUT);
							} else {
								episodeSaleOption.setSatus(DisplayConstants.DP_CONTINUE);
							}

							episodeSaleOption.setMaxMonthlySale(episodeShopping.getMthMaxCnt()); // 월_최대_판매_수량
							episodeSaleOption.setMaxDailySale(episodeShopping.getDlyMaxCnt()); // 일_최대_판매_수량
							episodeSaleOption.setMaxMonthlyBuy(episodeShopping.getMthUsrMaxCnt()); // 월_회원_최대_구매_수량
							episodeSaleOption.setMaxDailyBuy(episodeShopping.getDlyUsrMaxCnt()); // 일_회원_최대_구매_수량
							episodeSaleOption.setMaxOnceBuy(episodeShopping.getEachMaxCnt()); // 1차_최대_구매_수량
							episodeSaleOption.setPlaceUsage(episodeShopping.getUsePlac()); // 사용_장소
							episodeSaleOption.setRestrictUsage(episodeShopping.getUseLimtDesc()); // 사용_제한_설명
							episodeSaleOption.setPrincipleUsage(episodeShopping.getNoticeMatt()); // 공지_사항
							episodeSaleOption.setRefundUsage(episodeShopping.getPrchsCancelDrbkReason()); // 구매_취소_환불_사유
							episodeProduct.setSalesOption(episodeSaleOption);
							boolean nextFlag = false;
							int indexOption = 1;
							// Option 정보조회( 배송상품일 경우만 존재 )
							if (deliveryValue.equals("delivery")) {
								reqMap.put("chnlProdId", episodeShopping.getProdId());
								// 에피소드 list 조회
								List<MetaInfo> resultOptionList = this.commonDAO.queryForList(
										"Shopping.getShoppingOption", reqMap, MetaInfo.class);
								if (resultOptionList != null) {
									for (int mm = 0; mm < resultOptionList.size(); mm++) {
										MetaInfo optionShopping = resultOptionList.get(mm);
										if (optionShopping.getSubYn().equals("Y")) { // 옵션 1 인 경우
											selectOption = new SelectOption();
											// 옵션1 상품 ID
											selectOption.setId(optionShopping.getPartProdId());
											if (optionShopping.getExpoOrd().equals("1")) {
												selectOption.setIndex(String.valueOf(indexOption));
												indexOption++;
											}

											// 옵션1 상품 정보 (상품명)
											Title option1Title = this.commonGenerator.generateTitle(optionShopping);
											selectOption.setTitle(option1Title);
											// 옵션1 상품 가격정보
											Price option1Price = this.shoppingGenerator.generatePrice(optionShopping);
											selectOption.setPrice(option1Price);
										}
										if (optionShopping.getSubYn().equals("N")) { // 옵션 2 인 경우
											subSelectOption = new SubSelectOption();
											// 옵션2 상품 ID
											subSelectOption.setSubId(optionShopping.getProdNm());

											// 옵션2 상품 정보 (상품명)
											option2Title = new Title();
											option2Title.setText(optionShopping.getOptPdNm());
											subSelectOption.setSubTitle(option2Title);
											// 옵션2 상품 가격정보
											Price option2Price = this.shoppingGenerator.generatePrice(optionShopping);
											subSelectOption.setSubPrice(option2Price);

											subSelectOptionList.add(subSelectOption);
										}
										if (mm < resultOptionList.size() - 1) {
											if (resultOptionList.get(mm + 1).getSubYn().equals("Y")) { // 다음건이 Y 이면 값을
												nextFlag = true;
											} else {
												nextFlag = false;
											}
										} else { // 마지막에는 값을 셋팅
											nextFlag = true;
										}

										if (nextFlag) {
											selectOption.setSubSelectOptionList(subSelectOptionList);
											subSelectOptionList = new ArrayList<SubSelectOption>();
											selectOptionList.add(selectOption);
										}
									}
								}
							}

							episodeProduct.setSelectOptionList(selectOptionList);

							subProductList.add(episodeProduct);
						}
					}
					// 데이터 매핑
					// product.setPrice(episodePrice);
					product.setSalesOption(episodeSaleOption);
					// product.setSelectOptionList(selectOptionList);
					// product.setIdentifierList(episodeIdentifierList);

					// product.setMenuList(menuList);
					// product.setTitle(title);
					// product.setSourceList(sourceList);
					// product.setAccrual(accrual);
					// product.setContributor(contributor);
					// product.setSubProductTotalCount(subProductList.size());
					// product.setSubProductList(subProductList);

					productList.add(i, product);
				}
				res.setProductList(productList);
			} else {
				throw new StorePlatformException("SAC_DSP_0001", "쇼핑 상세 확인 ");
			}
		}
		return res;

	}

}
