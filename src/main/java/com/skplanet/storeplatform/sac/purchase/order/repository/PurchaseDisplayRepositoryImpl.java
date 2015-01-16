/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.FreePassInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.IapProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PaymentInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PossLendProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.UpdateSpecialPriceSoldOutSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.SpecialPriceSoldOutReq;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;

/**
 * 
 * 구매 시 필요한 전시Part SAC internal I/F repository
 * 
 * Updated on : 2014. 2. 27. Updated by : 이승택, nTels.
 */
@Component
public class PurchaseDisplayRepositoryImpl implements PurchaseDisplayRepository {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentInfoSCI purchaseProductSCI;
	@Autowired
	private PossLendProductInfoSCI possLendProductInfoSCI;
	@Autowired
	private IapProductInfoSCI iapProductInfoSCI;
	@Autowired
	private FreePassInfoSCI freepassInfoSCI;
	@Autowired
	private UpdateSpecialPriceSoldOutSCI updateSpecialPriceSoldOutSCI;

	/**
	 * 
	 * <pre>
	 * 상품 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param langCd
	 *            언어코드
	 * @param deviceModelCd
	 *            단말모델코드
	 * @param prodIdList
	 *            조회할 상품ID 목록
	 * @param bFlat
	 *            정액상품 여부
	 * @return 상품ID에 매핑되는 상품정보를 담은 Map
	 */
	@Override
	public Map<String, PurchaseProduct> searchPurchaseProductList(String tenantId, String langCd, String deviceModelCd,
			List<String> prodIdList, boolean bFlat) {
		PaymentInfoSacReq req = new PaymentInfoSacReq();
		req.setTenantId(tenantId);
		req.setLangCd(langCd);
		req.setDeviceModelCd(deviceModelCd);
		req.setProdIdList(prodIdList);

		PaymentInfoSacRes res = this.purchaseProductSCI.searchPaymentInfo(req);
		List<PaymentInfo> purchaseProductList = res.getPaymentInfoList();
		if (purchaseProductList.size() < 1) {
			return null;
		}

		Map<String, PurchaseProduct> purchaseProductMap = new HashMap<String, PurchaseProduct>();
		PurchaseProduct purchaseProduct = null;
		for (PaymentInfo displayInfo : purchaseProductList) {
			if (purchaseProductMap.containsKey(displayInfo.getProdId())) {
				continue;
			}

			purchaseProduct = new PurchaseProduct();

			// ////////////////////////// 상품 군 조회 변수 ////////////////////////////
			purchaseProduct.setTopMenuId(displayInfo.getTopMenuId());
			purchaseProduct.setSvcGrpCd(displayInfo.getSvcGrpCd());
			purchaseProduct.setInAppYn(displayInfo.getInAppYn());
			// ////////////////////////// APP,멀티미디어 상품 변수 ////////////////////////////
			purchaseProduct.setProdId(displayInfo.getProdId());
			purchaseProduct.setProdNm(displayInfo.getProdNm());
			purchaseProduct.setProdAmt(displayInfo.getProdAmt());
			purchaseProduct.setProdStatusCd(displayInfo.getProdStatusCd());
			purchaseProduct.setProdGrdCd(displayInfo.getProdGrdCd());
			// 허용 연령 (상품등급이 청소년이용불가 등급일 경우에 18/19 판별을 위해 사용)
			purchaseProduct.setAgeAllowedFrom(displayInfo.getAgeAllowedFrom());
			if (StringUtils.equals(displayInfo.getProdGrdCd(), PurchaseConstants.PRODUCT_GRADE_19)
					&& (displayInfo.getAgeAllowedFrom() == null || displayInfo.getAgeAllowedFrom() < 18)) {
				throw new StorePlatformException("SAC_PUR_5119", displayInfo.getAgeAllowedFrom());
			}
			purchaseProduct.setProdSprtYn(displayInfo.getProdSprtYn());
			purchaseProduct.setDrmYn(StringUtils.defaultString(displayInfo.getDrmYn(), PurchaseConstants.USE_N));
			if (StringUtils.equals(displayInfo.getAutoPrchsYN(), PurchaseConstants.USE_Y)) {
				if (StringUtils.isBlank(displayInfo.getUsePeriodUnitCd())
						|| StringUtils.isBlank(displayInfo.getUsePeriod())) {
					throw new StorePlatformException("SAC_PUR_5114", displayInfo.getProdId(),
							displayInfo.getUsePeriodUnitCd(), displayInfo.getUsePeriod());
				}
			}
			if (StringUtils.isBlank(displayInfo.getUsePeriodUnitCd())
					|| ((StringUtils.equals(displayInfo.getUsePeriodUnitCd(),
							PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED) == false) && StringUtils
							.isBlank(displayInfo.getUsePeriod()))) {
				throw new StorePlatformException("SAC_PUR_5115", displayInfo.getProdId(),
						displayInfo.getUsePeriodUnitCd(), displayInfo.getUsePeriod());
			}

			purchaseProduct.setUsePeriodUnitCd(displayInfo.getUsePeriodUnitCd());
			purchaseProduct.setUsePeriod(StringUtils.equals(displayInfo.getUsePeriodUnitCd(),
					PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED) ? "0" : displayInfo.getUsePeriod());

			purchaseProduct.setAid(displayInfo.getAid());
			purchaseProduct.setTenantProdGrpCd(displayInfo.getTenantProdGrpCd());
			purchaseProduct.setMallCd(displayInfo.getMallCd());
			purchaseProduct.setOutsdContentsId(displayInfo.getOutsdContentsId());
			purchaseProduct.setSellerMbrNo(displayInfo.getSellerMbrNo());
			purchaseProduct.setSellerNm(displayInfo.getSellerNm());
			purchaseProduct.setSellerEmail(displayInfo.getSellerEmail());
			purchaseProduct.setSellerTelno(displayInfo.getSellerTelno());
			purchaseProduct.setPossLendClsfCd(displayInfo.getPossLendClsfCd());
			// 회차정보
			purchaseProduct.setBookClsfCd(displayInfo.getBookClsfCd());
			purchaseProduct.setChapter(displayInfo.getChapter());
			purchaseProduct.setChapterText(displayInfo.getChapterText());
			purchaseProduct.setChapterUnit(displayInfo.getChapterUnit());
			// ////////////////////////// 쇼핑 상품 변수 ////////////////////////////
			purchaseProduct.setProdCaseCd(displayInfo.getProdCaseCd()); // DP006301-상품권, DP006302-교환권, DP006303-배송상품
			purchaseProduct.setCouponCode(displayInfo.getCouponCode());
			purchaseProduct.setItemCode(displayInfo.getItemCode());
			purchaseProduct.setSpecialSaleAmt(displayInfo.getSpecialSaleAmt());
			purchaseProduct.setSpecialSaleStartDt(displayInfo.getSpecialSaleStartDt());
			purchaseProduct.setSpecialSaleEndDt(displayInfo.getSpecialSaleEndDt());
			purchaseProduct.setSpecialSaleCouponId(displayInfo.getSpecialSaleCouponId());
			purchaseProduct.setSpecialSaleMonthLimit(displayInfo.getSpecialSaleMonthLimit());
			purchaseProduct.setSpecialSaleDayLimit(displayInfo.getSpecialSaleDayLimit());
			purchaseProduct.setSpecialSaleMonthLimitPerson(displayInfo.getSpecialSaleMonthLimitPerson());
			purchaseProduct.setSpecialSaleDayLimitPerson(displayInfo.getSpecialSaleDayLimitPerson());
			// purchaseProduct.setSpecialSaleOncePrchsLimit(displayInfo.getSpecialSaleOncePrchsLimit());
			// ////////////////////////// 정액제 상품 변수 ////////////////////////////
			purchaseProduct.setAvailableFixrateProdIdList(displayInfo.getAvailableFixrateProdIdList());
			purchaseProduct.setAvailableFixrateInfoList(displayInfo.getAvailableFixrateInfoList());
			purchaseProduct.setAutoPrchsYN(displayInfo.getAutoPrchsYN());
			if (StringUtils.equals(displayInfo.getAutoPrchsYN(), PurchaseConstants.USE_Y)) {
				purchaseProduct.setAutoPrchsPeriodUnitCd(displayInfo.getAutoPrchsPeriodUnitCd());
				purchaseProduct.setAutoPrchsPeriodValue(displayInfo.getAutoPrchsPeriodValue() == null ? 0 : displayInfo
						.getAutoPrchsPeriodValue());
			}
			purchaseProduct.setAutoPrchsLastDt(displayInfo.getAutoPrchsLastDt());
			purchaseProduct.setExclusiveFixrateProdExistYn(displayInfo.getExclusiveFixrateProdExistYn());
			purchaseProduct.setExclusiveFixrateProdIdList(displayInfo.getExclusiveFixrateProdIdList());
			purchaseProduct.setCmpxProdClsfCd(displayInfo.getCmpxProdClsfCd());
			// 게임캐쉬
			purchaseProduct.setBnsCashAmt(displayInfo.getBnsCashAmt());
			purchaseProduct.setBnsUsePeriodUnitCd(displayInfo.getBnsUsePeriodUnitCd());
			purchaseProduct.setBnsUsePeriod(displayInfo.getBnsUsePeriod() == null ? 0 : displayInfo.getBnsUsePeriod());
			// T멤버쉽 적립율
			purchaseProduct.setMileageRateMap(displayInfo.getMileageRateMap());
			// S2S
			purchaseProduct.setSearchPriceUrl(displayInfo.getSearchPriceUrl());

			purchaseProductMap.put(displayInfo.getProdId(), purchaseProduct);
		}

		return purchaseProductMap;
	}

	/**
	 * 
	 * <pre>
	 * 소장/대여 상품 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param langCd
	 *            언어코드
	 * @param prodId
	 *            조회할 상품ID
	 * @param possLendClsfCd
	 *            해당 상품ID의 소장/대여 구분 코드
	 * @return 소장/대여 상품 정보 VO
	 */
	@Override
	public PossLendProductInfo searchPossLendProductInfo(String tenantId, String langCd, String prodId,
			String possLendClsfCd) {
		PossLendProductInfoSacReq req = new PossLendProductInfoSacReq();
		req.setTenantId(tenantId);
		req.setLangCd(langCd);
		List<String> possLendClsfCdList = new ArrayList<String>();
		possLendClsfCdList.add(possLendClsfCd);
		req.setPossLendClsfCdList(possLendClsfCdList);
		List<String> prodIdList = new ArrayList<String>();
		prodIdList.add(prodId);
		req.setProdIdList(prodIdList);

		PossLendProductInfoSacRes res = this.possLendProductInfoSCI.searchPossLendProductInfo(req);
		PossLendProductInfo possLendProductInfo = res.getPossLendProductInfoList().get(0);

		if (StringUtils.isEmpty(possLendProductInfo.getPossProdId())
				|| StringUtils.isEmpty(possLendProductInfo.getLendProdId())) {
			return null;

		} else {
			return possLendProductInfo;
		}
	}

	/**
	 * 
	 * <pre>
	 * IAP 상품정보 조회.
	 * </pre>
	 * 
	 * @param partProdId
	 *            IAP 상품ID
	 * @param tenantId
	 *            테넌트ID
	 * @return IAP 상품정보 VO
	 */
	@Override
	public IapProductInfoRes searchIapProductInfo(String partProdId, String tenantId) {
		return this.iapProductInfoSCI.getIapProductInfo(new IapProductInfoReq(partProdId, tenantId));
	}

	/**
	 * 
	 * <pre>
	 * 정액제 상품 DRM 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param langCd
	 *            언어코드
	 * @param fixrateProdId
	 *            정액제 상품 ID
	 * @param episodeProdId
	 *            해당 정액제 상품을 이용하여 구매할 상품 ID
	 * @return 정액제 상품 DRM 정보
	 */
	@Override
	public FreePassInfo searchFreePassDrmInfo(String tenantId, String langCd, String fixrateProdId, String episodeProdId) {
		FreePassInfoSacReq req = new FreePassInfoSacReq();
		req.setTenantId(tenantId);
		req.setLangCd(langCd);
		req.setProdId(fixrateProdId);
		req.setEpisodeProdId(episodeProdId);

		return this.freepassInfoSCI.searchFreePassDrmInfo(req);
	}

	/**
	 * 
	 * <pre>
	 * 이북/코믹 전권 소장/대여 상품에 딸린 에피소드 상품 목록 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param langCd
	 *            언어코드
	 * @param deviceModelCd
	 *            디바이스 모델 코드
	 * @param prodId
	 *            이북/코믹 전권 소장/대여 상품ID
	 * @param cmpxProdClsfCd
	 *            복합 상품 구분 코드
	 * @return 에피소드 상품 목록
	 */
	@Override
	public List<EpisodeInfoRes> searchEbookComicEpisodeList(String tenantId, String langCd, String deviceModelCd,
			String prodId, String cmpxProdClsfCd) {
		EpisodeInfoReq episodeInfoReq = new EpisodeInfoReq();
		episodeInfoReq.setTenantId(tenantId);
		episodeInfoReq.setLangCd(langCd);
		episodeInfoReq.setDeviceModelCd(deviceModelCd);
		episodeInfoReq.setProdId(prodId);
		episodeInfoReq.setCmpxProdClsfCd(cmpxProdClsfCd);
		EpisodeInfoSacRes episodeInfoSacRes = this.freepassInfoSCI.searchEpisodeList(episodeInfoReq);

		List<EpisodeInfoRes> episodeList = new ArrayList<EpisodeInfoRes>();
		for (EpisodeInfoRes episode : episodeInfoSacRes.getFreePassInfoRes()) {
			if (StringUtils.equals(episode.getProdStatusCd(), PurchaseConstants.PRODUCT_STATUS_SALE) == false) {
				continue;
			}

			episodeList.add(episode);
		}

		return episodeList;
	}

	/**
	 * 
	 * <pre>
	 * 쇼핑 특가 상품에 대해 품절 등록.
	 * </pre>
	 * 
	 * @param tenantId
	 *            테넌트ID
	 * @param prodId
	 *            품절상태로 등록할 쇼핑특가상품ID
	 */
	@Override
	public void updateSpecialPriceSoldOut(String tenantId, String prodId) {
		SpecialPriceSoldOutReq specialPriceSoldOutReq = new SpecialPriceSoldOutReq();
		specialPriceSoldOutReq.setTenantId(tenantId);
		specialPriceSoldOutReq.setProductId(prodId);

		this.logger.info("PRCHS,ORDER,SAC,DISP,SOLDOUT,REQ,ONLY,{}",
				ReflectionToStringBuilder.toString(specialPriceSoldOutReq, ToStringStyle.SHORT_PREFIX_STYLE));

		try {
			this.updateSpecialPriceSoldOutSCI.updateSpecialPriceSoldOut(specialPriceSoldOutReq);
		} catch (Exception e) {
			this.logger.info("PRCHS,ORDER,SAC,DISP,SOLDOUT,RES,ERROR,{}", e); // throw 는 하지 않음
		}
	}
}
