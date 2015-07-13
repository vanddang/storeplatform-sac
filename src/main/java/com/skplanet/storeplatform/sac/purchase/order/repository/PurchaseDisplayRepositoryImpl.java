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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.*;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.*;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private CmpxInfoSCI cmpxInfoSCI;
	@Autowired
	private UpdateSpecialPriceSoldOutSCI updateSpecialPriceSoldOutSCI;
	@Autowired
	private UpdateSpecialPurchaseCountSCI updateSpecialPurchaseCountSCI;

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

		/** 전시 연동 **/
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

			purchaseProduct = new PurchaseProduct(displayInfo);

			/**
			 * 청소년 이용불가 상품의 연령정보 validation
			 */
			if (StringUtils.equals(purchaseProduct.getProdGrdCd(), PurchaseConstants.PRODUCT_GRADE_19)
					&& (purchaseProduct.getAgeAllowedFrom() == null || purchaseProduct.getAgeAllowedFrom() < 18)) {
				throw new StorePlatformException("SAC_PUR_5119", purchaseProduct.getAgeAllowedFrom());
			}

			/**
			 * 이용기간 정보 validation
			 */
			if (StringUtils.isBlank(purchaseProduct.getUsePeriodUnitCd())
					|| ((StringUtils.equals(purchaseProduct.getUsePeriodUnitCd(),
							PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED) == false) && StringUtils
							.isBlank(purchaseProduct.getUsePeriod()))) {
				throw new StorePlatformException("SAC_PUR_5115", purchaseProduct.getProdId(),
						purchaseProduct.getUsePeriodUnitCd(), purchaseProduct.getUsePeriod());
			}

			/**
			 * 자동 결제 상품의 이용기간 validation
			 */
			if (StringUtils.equals(purchaseProduct.getAutoPrchsYN(), PurchaseConstants.USE_Y)) {
				if (StringUtils.equals(purchaseProduct.getUsePeriodUnitCd(),
						PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED)) {
					throw new StorePlatformException("SAC_PUR_5114", purchaseProduct.getProdId(),
							purchaseProduct.getUsePeriodUnitCd(), purchaseProduct.getUsePeriod());
				}
			}

			purchaseProductMap.put(displayInfo.getProdId(), purchaseProduct);
		}

		return purchaseProductMap;
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
	 * 이용권에 등록된 상품 목록 조회.
	 * </pre>
	 * 
	 * @param cmpxProductSacReq
	 *            이용권에 등록된 상품 목록 조회 요청VO
	 * @return 이용권에 등록된 상품 목록
	 */
	@Override
	public List<CmpxProductInfoList> searchCmpxProductList(CmpxProductSacReq cmpxProductSacReq) {
		// SAC에서는 구매시 판매중인 상품만 조회한다
		List<String> episodeProdStatusCdList = new ArrayList<String>();
		episodeProdStatusCdList.add(PurchaseConstants.PRODUCT_STATUS_SALE);
		cmpxProductSacReq.setEpisodeProdStatusCdList(episodeProdStatusCdList);

		CmpxProductListRes cmpxProductListRes = this.cmpxInfoSCI.searchCmpxProductList(cmpxProductSacReq);
		List<CmpxProductInfoList> cmpxProductInfoList = cmpxProductListRes.getCmpxProductInfoList();
		if (cmpxProductInfoList == null) {
			cmpxProductInfoList = new ArrayList<CmpxProductInfoList>();
		}
		return cmpxProductInfoList;
	}

	/**
	 * 
	 * <pre>
	 * 이용권 및 에피소드 상품 정보 조회.
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
	 * @param chapter
	 *            회차 정보 (이용권에 매핑된 상품 중 요청한 회차만 조회)
	 * @return 이용권 및 에피소드 상품 정보
	 */
	@Override
	public CmpxProductInfo searchCmpxProductInfo(String tenantId, String langCd, String fixrateProdId,
			String episodeProdId, String chapter) {
		CmpxProductInfoSacReq cmpxProductInfoSacReq = new CmpxProductInfoSacReq();
		cmpxProductInfoSacReq.setTenantId(tenantId);
		cmpxProductInfoSacReq.setLangCd(langCd);
		cmpxProductInfoSacReq.setProdId(fixrateProdId);
		cmpxProductInfoSacReq.setEpisodeProdId(episodeProdId);
		cmpxProductInfoSacReq.setChapter(chapter);

		return this.cmpxInfoSCI.searchCmpxProductInfo(cmpxProductInfoSacReq);
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

	/**
	 * @param tenentId
	 *            테넌트ID
	 * @param purchaseId
	 *            구매ID
	 * @param productId
	 *            상품ID
	 * @param purchaseStatusCd
	 *            구매상태코드
	 * @param purchaseCount
	 *            구매건수
	 * @param purchaseDate
	 *            구매일시
	 * @param purchaseCancelDate
	 *            구매취소 일시
	 */
	@Override
	public void updateSpecialPurchaseCount(String tenentId, String purchaseId, String productId,
			String purchaseStatusCd, Integer purchaseCount, String purchaseDate, String purchaseCancelDate) {

		SpecialPurchaseCountSacReq specialPurchaseCountSacReq = new SpecialPurchaseCountSacReq();
		specialPurchaseCountSacReq.setTenantId(tenentId);
		specialPurchaseCountSacReq.setPurchaseId(purchaseId);
		specialPurchaseCountSacReq.setProductId(productId);
		specialPurchaseCountSacReq.setPurchaseStatusCd(purchaseStatusCd);
		specialPurchaseCountSacReq.setPurchaseCount(purchaseCount);
		specialPurchaseCountSacReq.setPurchaseDate(purchaseDate);
		specialPurchaseCountSacReq.setPurchaseCancelDate(purchaseCancelDate);

		this.logger.info("PRCHS,ORDER,SAC,DISP,SPECIALCNT,REQ,ONLY,{}",
				ReflectionToStringBuilder.toString(specialPurchaseCountSacReq, ToStringStyle.SHORT_PREFIX_STYLE));
		try {
			this.updateSpecialPurchaseCountSCI.updateSpecialPurchaseCount(specialPurchaseCountSacReq);
		} catch (Exception e) {
			this.logger.info("PRCHS,ORDER,SAC,DISP,SPECIALCNT,RES,ERROR,{}", e); // throw 는 하지 않음
		}
	}
}
