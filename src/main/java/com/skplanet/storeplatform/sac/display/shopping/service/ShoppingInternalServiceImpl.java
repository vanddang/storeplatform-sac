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
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SelectOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SubSelectOption;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
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
				Identifier identifier1 = null;
				Menu menu = null;
				Title title = null;
				Source source = null;
				Contributor contributor = null;
				Accrual accrual = null;

				List<Identifier> identifierList = null;

				// / 에피소드용
				Product episodeProduct = null;
				Identifier episodeIdentifier = null;
				List<Identifier> episodeIdentifierList = null;
				Menu episodeMenu = null;
				List<Menu> episodeMenuList = new ArrayList<Menu>();
				Price episodePrice = null;
				Rights episodeRights = null;
				Date episodeDate = null;
				Distributor distributor = null;
				Purchase purchase = null;
				Identifier purchaseIdentifier = null;
				List<Identifier> purchaseIdentifierList = new ArrayList<Identifier>();
				Date purchaseDate = null;
				List<Date> episodeDateList = new ArrayList<Date>();
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

				List<Menu> menuList = null;
				List<Source> sourceList = null;
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

					// 메뉴 정보
					menuList = new ArrayList<Menu>();
					menu = new Menu();
					menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
					menu.setId(shopping.getUpMenuId());
					menu.setName(shopping.getUpMenuName());
					menuList.add(menu);

					menu = new Menu();
					menu.setId(shopping.getMenuId());
					menu.setName(shopping.getMenuName());
					menuList.add(menu);

					// 상품 정보 (상품명)
					title = new Title();
					title.setText(shopping.getCatalogName());

					// 이미지 정보
					sourceList = new ArrayList<Source>();
					source = new Source();
					source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
					source.setUrl(shopping.getFilePos());
					sourceList.add(source);

					// 다운로드 수
					accrual = new Accrual();
					accrual.setDownloadCount(shopping.getPrchsQty());

					// contributor
					contributor = new Contributor();
					identifier1 = new Identifier();
					identifier1.setType(DisplayConstants.DP_BRAND_IDENTIFIER_CD);
					identifier1.setText(shopping.getBrandId());
					contributor.setName(shopping.getBrandName());
					contributor.setIdentifier(identifier1);

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

							// 특가 상품일 경우
							episodeMenu = new Menu();
							episodeMenu.setType(episodeShopping.getSpecialSale());
							episodeMenuList.add(episodeMenu);
							episodeProduct.setMenuList(episodeMenuList);

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

							episodeProduct.setIdentifierList(episodeIdentifierList);

							// 에피소드 상품 가격 정보
							episodePrice = new Price();
							episodePrice.setFixedPrice(episodeShopping.getProdNetAmt());
							episodePrice.setDiscountRate(episodeShopping.getDcRate());
							episodePrice.setText(episodeShopping.getProdAmt());
							episodeProduct.setPrice(episodePrice);

							// 에피소드 구매내역 정보
							String prchsId = null;
							String prchsDt = null;
							String prchsState = null;
							int purchseCount = 0;

							try {
								// 구매내역 조회를 위한 생성자
								ProductListSacIn productListSacIn = new ProductListSacIn();
								List<ProductListSacIn> productEpisodeList = new ArrayList<ProductListSacIn>();

								productListSacIn.setProdId(episodeShopping.getPartProdId());
								productEpisodeList.add(productListSacIn);

								HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
								historyListSacReq.setTenantId(req.getTenantId());
								historyListSacReq.setUserKey(req.getUserKey());
								historyListSacReq.setDeviceKey(req.getDeviceKey());
								historyListSacReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_OWN);
								historyListSacReq.setStartDt("19000101000000");
								historyListSacReq.setEndDt(episodeShopping.getSysDate());
								historyListSacReq.setOffset(1);
								historyListSacReq.setCount(1);
								historyListSacReq.setProductList(productEpisodeList);
								// 구매내역 조회 실행
								if (!StringUtils.isEmpty(req.getUserKey())) {
									HistoryListSacInRes historyListSacRes = this.historyInternalSCI
											.searchHistoryList(historyListSacReq);

									this.log.debug("----------------------------------------------------------------");
									this.log.debug("[getShoppingInfo] purchase count : {}",
											historyListSacRes.getTotalCnt());
									this.log.debug("----------------------------------------------------------------");
									purchseCount = historyListSacRes.getTotalCnt();
									if (historyListSacRes.getTotalCnt() > 0) {
										prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
										prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
										prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();

										if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
											prchsState = "payment";
										} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
											prchsState = "gift";
										}

										this.log.debug("----------------------------------------------------------------");
										this.log.debug("[getShoppingInfo] prchsId : {}", prchsId);
										this.log.debug("[getShoppingInfo] prchsDt : {}", prchsDt);
										this.log.debug("[getShoppingInfo] prchsState : {}", prchsState);
										this.log.debug("----------------------------------------------------------------");
									}
								}
							} catch (Exception ex) {
								throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
							}

							purchase = new Purchase();
							purchaseDate = new Date();
							purchaseIdentifier = new Identifier();
							purchaseIdentifierList = new ArrayList<Identifier>();
							purchaseIdentifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
							purchaseIdentifier.setText(prchsId);
							purchaseIdentifierList.add(purchaseIdentifier);
							purchase.setIdentifierList(purchaseIdentifierList);
							purchase.setState(prchsState);
							if (prchsDt != null) {
								purchaseDate = new Date(DisplayConstants.DP_SHOPPING_PURCHASE_TYPE_NM,
										DateUtils.parseDate(prchsDt));
							}
							purchase.setDate(purchaseDate);
							if (!StringUtils.isEmpty(req.getUserKey())) {// 사용자키가 있을 경우
								episodeProduct.setPurchase(purchase);
							}

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

							// 상품 구매가 있고 후기가 없으면 feedback값을 내려줘야 함
							if (purchseCount > 0) {
								episodeRights.setAllow(episodeShopping.getAllow());
							}

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

							episodeProduct.setSelectOptionList(selectOptionList);

							// 판매자정보 셋팅
							DetailInformationSacReq memberReq = new DetailInformationSacReq();
							DetailInformationSacRes memberRes = new DetailInformationSacRes();
							try {
								memberReq.setSellerKey(episodeShopping.getSellerMbrNo());
								memberReq.setSellerId("");
								memberRes = this.sellerSearchSCI.detailInformation(memberReq);
								if (memberRes != null) {
									memberRes.getSellerMbr().getSellerCompany();
									distributor = new Distributor();
									distributor.setType(DisplayConstants.DP_CORPORATION_IDENTIFIER_CD);
									distributor.setIdentifier(memberRes.getSellerMbr().getSellerId());
									distributor.setName(memberRes.getSellerMbr().getSellerName());
									distributor.setCompany(memberRes.getSellerMbr().getSellerCompany());
									distributor.setTel(memberRes.getSellerMbr().getRepPhone());
									distributor.setEmail(memberRes.getSellerMbr().getSellerEmail());
									distributor.setAddress(memberRes.getSellerMbr().getSellerAddress()
											+ memberRes.getSellerMbr().getSellerDetailAddress());
									distributor.setRegNo(memberRes.getSellerMbr().getBizRegNumber());
									episodeProduct.setDistributor(distributor);

								}
							} catch (Exception e) {
								throw new StorePlatformException("SAC_DSP_0001", "멤버 정보 조회 ", e);
							}

							// 에피소드 특가상품
							if (episodeShopping.getSpecialSale() != null) {
								episodeProduct.setId(episodeShopping.getPartProdId());
							}
							subProductList.add(episodeProduct);
						}
					}
					// 데이터 매핑
					product.setIdentifierList(identifierList);
					product.setMenuList(menuList);
					product.setTitle(title);
					product.setSourceList(sourceList);
					product.setAccrual(accrual);
					product.setContributor(contributor);
					product.setSubProductList(subProductList);
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
