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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SelectOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SubSelectOption;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.shopping.vo.Shopping;

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

	@Override
	public ShoppingRes searchShoppingInternal(ShoppingReq req) {
		// 공통 응답 변수 선언
		ShoppingRes res = new ShoppingRes();
		req.setTenantId(req.getTenantId());
		req.setDeviceModelCd(req.getDeviceModelCd());
		req.setLangCd(req.getLangCd());
		req.setImageCd(DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		req.setVirtualDeviceModelNo(DisplayConstants.DP_ANDROID_STANDARD2_NM);

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(req.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getProdId())) {
			throw new StorePlatformException("SAC_DSP_0002", "prodId", req.getProdId());
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
		List<Shopping> resultChannelList = this.commonDAO.queryForList("Shopping.getShoppingChannelDetail", reqMap,
				Shopping.class);

		if (resultChannelList == null) {
			throw new StorePlatformException("SAC_DSP_0001", "쇼핑 상세 확인 ");
		} else {
			if (resultChannelList.size() > 0) {

				// Response VO를 만들기위한 생성자
				Product product = null;
				Identifier identifier = null;

				List<Identifier> identifierList = null;

				// / 에피소드용
				Identifier episodeIdentifier = null;
				List<Identifier> episodeIdentifierList = null;

				Product episodeProduct = null;
				Price episodePrice = null;

				SalesOption episodeSaleOption = null;

				// / 옵션용
				SelectOption selectOption = null;
				SubSelectOption subSelectOption = null;
				List<SelectOption> selectOptionList = new ArrayList<SelectOption>();
				List<SubSelectOption> subSelectOptionList = new ArrayList<SubSelectOption>();
				Title option1Title = null;
				Price option1Price = null;
				Title option2Title = null;
				Price option2Price = null;

				List<Product> subProductList = new ArrayList<Product>();

				List<Product> productList = new ArrayList<Product>();
				// 채널 list 조회
				for (int i = 0; i < resultChannelList.size(); i++) {
					Shopping shopping = resultChannelList.get(i);

					// 상품 정보 (상품ID)
					product = new Product();
					identifierList = new ArrayList<Identifier>();
					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_CATALOG_IDENTIFIER_CD);
					identifier.setText(shopping.getCatalogId());
					identifierList.add(identifier);

					String deliveryValue = shopping.getProdCaseCd();
					// 배송상품은 단품이며 기본정보는 동일
					if (deliveryValue.equals("delivery")) {
						reqMap.put("endRow", "1");
					}

					// 에피소드 list 조회
					List<Shopping> resultEpisodeList = this.commonDAO.queryForList("Shopping.getShoppingEpisodeDetail",
							reqMap, Shopping.class);
					if (resultEpisodeList != null) {
						for (int kk = 0; kk < resultEpisodeList.size(); kk++) {
							Shopping episodeShopping = resultEpisodeList.get(kk);

							episodeProduct = new Product();

							// 채널 상품 정보 (상품ID)
							episodeIdentifierList = new ArrayList<Identifier>();
							episodeIdentifier = new Identifier();
							episodeIdentifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
							episodeIdentifier.setText(episodeShopping.getProdId());
							episodeIdentifierList.add(episodeIdentifier);

							// 에피소드 상품 정보 (상품ID)
							episodeIdentifier = new Identifier();
							episodeIdentifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
							episodeIdentifier.setText(episodeShopping.getPartProdId());
							episodeIdentifierList.add(episodeIdentifier);

							// 에피소드 상품 가격 정보
							episodePrice = new Price();
							episodePrice.setFixedPrice(episodeShopping.getProdNetAmt());
							episodePrice.setDiscountRate(episodeShopping.getDcRate());
							episodePrice.setText(episodeShopping.getProdAmt());
							episodeProduct.setPrice(episodePrice);

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
								List<Shopping> resultOptionList = this.commonDAO.queryForList(
										"Shopping.getShoppingOption", reqMap, Shopping.class);
								if (resultOptionList != null) {
									for (int mm = 0; mm < resultOptionList.size(); mm++) {
										Shopping optionShopping = resultOptionList.get(mm);
										if (optionShopping.getSubYn().equals("Y")) { // 옵션 1 인 경우
											selectOption = new SelectOption();
											// 옵션1 상품 ID
											selectOption.setId(optionShopping.getPartProdId());
											if (optionShopping.getExpoOrd().equals("1")) {
												selectOption.setIndex(String.valueOf(indexOption));
												indexOption++;
											}

											// 옵션1 상품 정보 (상품명)
											option1Title = new Title();
											option1Title.setText(optionShopping.getOptPdNm());
											selectOption.setTitle(option1Title);
											// 옵션1 상품 가격정보
											option1Price = new Price();
											option1Price.setFixedPrice(optionShopping.getProdNetAmt());
											option1Price.setDiscountRate(optionShopping.getDcRate());
											option1Price.setText(optionShopping.getProdAmt());
											selectOption.setPrice(option1Price);
										}
										if (optionShopping.getSubYn().equals("N")) { // 옵션 2 인 경우
											subSelectOption = new SubSelectOption();
											// 옵션2 상품 ID
											subSelectOption.setSubId(optionShopping.getOpt1Nm());

											// 옵션2 상품 정보 (상품명)
											option2Title = new Title();
											option2Title.setText(optionShopping.getOptPdNm());
											subSelectOption.setSubTitle(option2Title);
											// 옵션2 상품 가격정보
											option2Price = new Price();
											option2Price.setFixedPrice(optionShopping.getProdNetAmt());
											option2Price.setDiscountRate(optionShopping.getDcRate());
											option2Price.setText(optionShopping.getProdAmt());
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
							subProductList.add(episodeProduct);
						}
					}
					// 데이터 매핑
					product.setPrice(episodePrice);
					product.setSalesOption(episodeSaleOption);
					product.setSelectOptionList(selectOptionList);
					product.setIdentifierList(episodeIdentifierList);
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
