/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.common.vo.PayPlanetShop;
import com.skplanet.storeplatform.purchase.client.common.vo.PpProperty;
import com.skplanet.storeplatform.purchase.client.payplanet.sci.PurchasePayPlanetSCI;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 
 * Pay Planet 가맹점 정보 서비스
 * 
 * Updated on : 2014. 3. 25. Updated by : 이승택, nTels.
 */
@Service
public class PayPlanetShopServiceImpl implements PayPlanetShopService {
	private static final String SERVER_LEVEL_DEV = "DV"; // 조회대상 서버 단계: 개발기
	private static final String SERVER_LEVEL_QA = "QA"; // 조회대상 서버 단계: QA
	private static final String SERVER_LEVEL_REAL = "LV"; // 조회대상 서버 단계: 상용
	private static final String SERVER_LEVEL_STAGING = "ST"; // 조회대상 서버 단계: 상용 스테이징

	@Autowired
	private PurchasePayPlanetSCI PurchasePayPlanetSCI;

	@Value("#{systemProperties['spring.profiles.active']}")
	private String envServerLevel;

	private String serverLevel;

	@PostConstruct
	public void initServerLevel() {
		if (StringUtils.equalsIgnoreCase(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_REAL)) {
			this.serverLevel = SERVER_LEVEL_REAL;
		} else if (StringUtils.equals(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_STAGING)) {
			this.serverLevel = SERVER_LEVEL_STAGING;
		} else if (StringUtils.equals(this.envServerLevel, PurchaseConstants.ENV_SERVER_LEVEL_QA)) {
			this.serverLevel = SERVER_LEVEL_QA;
		} else {
			this.serverLevel = SERVER_LEVEL_DEV;
		}
	}

	/**
	 * 
	 * <pre>
	 * P/P 가맹점 ID 여부 확인.
	 * </pre>
	 * 
	 * @param mid
	 * @return
	 */
	@Override
	public boolean startsWithPayPlanetMID(String mid) {
		for (String prefix : PurchaseConstants.PAYPLANET_MID_PREFIX_LIST) {
			if (StringUtils.startsWith(mid, prefix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * TAKTODO:: 임시
	 */
	public static Map<String, String> PP_PAY_URL_MAP = new HashMap<String, String>();
	static {
		PP_PAY_URL_MAP.put(SERVER_LEVEL_REAL, "https://pg.payplanet.co.kr/sc/pay");
		PP_PAY_URL_MAP.put(SERVER_LEVEL_STAGING, "https://pg.payplanet.co.kr/cbt/pay");
		PP_PAY_URL_MAP.put(SERVER_LEVEL_QA, "http://211.188.207.153/sc/pay");
		PP_PAY_URL_MAP.put(SERVER_LEVEL_DEV, "http://211.188.207.139/sc/pay");
	}

	/**
	 * 
	 * <pre>
	 * SKT 결제/결제취소 용 SYSTEM DIVISION 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            조회할 가맹점의 테넌트 ID
	 * @param prodId
	 *            상품 ID
	 * @param parentProdId
	 *            인앱 모상품 APP ID
	 * @param tenantProdGrpCd
	 *            테넌트 상품 분류 코드
	 * @return 결제/결제취소 용 SYSTEM DIVISION 정보
	 */
	@Override
	public PpProperty getDcbSystemDivision(String tenantId, String prodId, String parentProdId, String tenantProdGrpCd) {
		PpProperty ppProperty = new PpProperty();
		ppProperty.setTenantId(tenantId);
		ppProperty.setApiTypeCd(PurchaseConstants.PAYPLANET_API_TYPE_DCB_SYSTEM_DIVISION);
		ppProperty.setProdId(prodId);
		ppProperty.setParentProdId(parentProdId);
		ppProperty.setTenantProdGrpCd(tenantProdGrpCd);

		return this.PurchasePayPlanetSCI.searchDcbSystemDivision(ppProperty);
	}

	/**
	 * 
	 * <pre>
	 * Pay Planet 가맹점 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            조회할 가맹점의 테넌트 ID
	 * @param apiTypeCd
	 *            P/P 연동 API 타입 코드
	 * @param prchsReqPathCd
	 *            구매 요청 경로 코드
	 * @return Pay Planet 가맹점 정보
	 */
	@Override
	public PpProperty getPayPlanetShopInfo(String tenantId, String apiTypeCd, String prchsReqPathCd) {

		PpProperty ppProperty = new PpProperty();
		ppProperty.setTenantId(tenantId);
		ppProperty.setApiTypeCd(apiTypeCd);
		ppProperty.setReqPathCd(prchsReqPathCd);

		PpProperty payPlanetShop = this.PurchasePayPlanetSCI.searchPayPlanetShopInfo(ppProperty);
		if (payPlanetShop == null) {
			throw new StorePlatformException("SAC_PUR_7102");
		}

		return payPlanetShop;
	}

	/**
	 * 
	 * <pre>
	 * Pay Planet 가맹점 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            조회할 가맹점의 테넌트 ID
	 * @param apiTypeCd
	 *            P/P 연동 API 타입 코드
	 * @param prchsReqPathCd
	 *            구매요청경로코드
	 * @param mid
	 *            P/P 가맹점ID
	 * @return Pay Planet 가맹점 정보
	 */
	@Override
	public PpProperty getPayPlanetShopInfoByMid(String tenantId, String apiTypeCd, String prchsReqPathCd, String mid) {
		return this.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
	}

	/**
	 * 
	 * <pre>
	 * [TAKTODO:: 제거대상] Pay Planet 가맹점 정보 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            조회할 가맹점의 테넌트 ID
	 * @param apiTypeCd
	 *            P/P 연동 API 타입 코드
	 * @param mid
	 *            P/P 가맹점ID
	 * @return Pay Planet 가맹점 정보
	 */
	@Override
	public PayPlanetShop getPayPlanetShopInfoByMid(String tenantId, String apiTypeCd, String mid) {
		// TAKTODO:: 관리 테이블 이전 하드코딩
		PayPlanetShop shopInfo = new PayPlanetShop();
		shopInfo.setTenantId(tenantId);
		shopInfo.setMid(mid);

		if (StringUtils.equals(tenantId, PurchaseConstants.TENANT_ID_TSTORE)) { // S01

			if (StringUtils.equals(apiTypeCd, PurchaseConstants.PAYPLANET_API_TYPE_PURCHASE)) { // 구매결제요청

				if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_TSTORE_IAP)) { // IAP
					shopInfo.setAuthKey("6b6fa0a99e621f5b0fc9a77622c42b67e7a3317c");
					shopInfo.setEncKey("qnqnsdbfyghk0001");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				} else if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_TSTORE_EBOOKSTORE)) { // 이북보관함
					shopInfo.setAuthKey("6b6fa0a99e621f5b0fc9a77622c42b67e7a3317c");
					shopInfo.setEncKey("qnqnsdbfyghk0001");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				} else { // 일반 (S/C)
					shopInfo.setAuthKey("6b6fa0a99e621f5b0fc9a77622c42b67e7a3317c");
					shopInfo.setEncKey("qnqnsdbfyghk0001");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				}

			} else if (StringUtils.equals(apiTypeCd, PurchaseConstants.PAYPLANET_API_TYPE_CANCEL)) { // 결제취소

				if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_TSTORE_IAP)) { // IAP
					shopInfo.setAuthKey("6b6fa0a99e621f5b0fc9a77622c42b67e7a3317c");
					shopInfo.setEncKey("qnqnsdbfyghk0001");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				} else if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_TSTORE_EBOOKSTORE)) { // 이북보관함
					shopInfo.setAuthKey("6b6fa0a99e621f5b0fc9a77622c42b67e7a3317c");
					shopInfo.setEncKey("qnqnsdbfyghk0001");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				} else { // 일반 (S/C)
					shopInfo.setAuthKey("6b6fa0a99e621f5b0fc9a77622c42b67e7a3317c");
					shopInfo.setEncKey("qnqnsdbfyghk0001");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				}
			}

		} else if (StringUtils.equals(tenantId, PurchaseConstants.TENANT_ID_OLLEH)) {

			if (StringUtils.equals(apiTypeCd, PurchaseConstants.PAYPLANET_API_TYPE_PURCHASE)) { // 구매결제요청

				if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_OLLEH_IAP)) { // IAP
					shopInfo.setAuthKey("c87fb588abc7efc58367cafc15b4d9661291286f");
					shopInfo.setEncKey("a27880e386bbb5e2");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				} else if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_OLLEH)) { // 일반 (S/C)
					shopInfo.setAuthKey("456d99087506813ac75ffe99b71a08ded9250d41");
					shopInfo.setEncKey("3be59250f04d12b5");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				}

			} else if (StringUtils.equals(apiTypeCd, PurchaseConstants.PAYPLANET_API_TYPE_CANCEL)) { // 결제취소

				if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_OLLEH_IAP)) { // IAP
					shopInfo.setAuthKey("c87fb588abc7efc58367cafc15b4d9661291286f");
					shopInfo.setEncKey("a27880e386bbb5e2");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				} else if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_OLLEH)) { // 일반 (S/C)
					shopInfo.setAuthKey("456d99087506813ac75ffe99b71a08ded9250d41");
					shopInfo.setEncKey("3be59250f04d12b5");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				}
			}

		} else if (StringUtils.equals(tenantId, PurchaseConstants.TENANT_ID_UPLUS)) {

			if (StringUtils.equals(apiTypeCd, PurchaseConstants.PAYPLANET_API_TYPE_PURCHASE)) { // 구매결제요청

				if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_UPLUS_IAP)) { // IAP
					shopInfo.setAuthKey("203168e6c618d034da53a6946fcc186b372ddfdf");
					shopInfo.setEncKey("cadea142602bfb4c");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				} else if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_UPLUS)) { // 일반 (S/C)
					shopInfo.setAuthKey("d29bc3cddc8ec39d7dba6d91b00605f47dc9b040");
					shopInfo.setEncKey("3e99ee0c23492e07");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				}

			} else if (StringUtils.equals(apiTypeCd, PurchaseConstants.PAYPLANET_API_TYPE_CANCEL)) { // 결제취소

				if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_UPLUS_IAP)) { // IAP
					shopInfo.setAuthKey("203168e6c618d034da53a6946fcc186b372ddfdf");
					shopInfo.setEncKey("cadea142602bfb4c");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				} else if (StringUtils.equals(mid, PurchaseConstants.PAYPLANET_MID_UPLUS)) { // 일반 (S/C)
					shopInfo.setAuthKey("d29bc3cddc8ec39d7dba6d91b00605f47dc9b040");
					shopInfo.setEncKey("3e99ee0c23492e07");
					shopInfo.setUrl(PP_PAY_URL_MAP.get(this.serverLevel));
				}
			}

		}

		return shopInfo;
	}

}
