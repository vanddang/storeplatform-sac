/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.purchase.common.util.MD5Utils;
import com.skplanet.storeplatform.sac.purchase.common.util.PayPlanetUtils;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;
import com.skplanet.storeplatform.sac.purchase.order.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 
 * 결제Page 서비스
 * 
 * Updated on : 2014. 6. 2. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderPaymentPageServiceImpl implements PurchaseOrderPaymentPageService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String PAYMENT_PAGE_PARAM_VER = "1.0";
	public static final String PP_RETURN_FORMAT_JSON = "1";

	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;

	/**
	 * 
	 * <pre>
	 * 유료구매 - 결제Page 파라미터 세팅.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매요청 정보
	 */
	@Override
	public void buildPaymentPageUrlParam(PurchaseOrderInfo purchaseOrderInfo) {
		this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,START,{}", purchaseOrderInfo.getPrchsId());

		// eData(암호화 데이터)
		PurchaseProduct product = purchaseOrderInfo.getPurchaseProductList().get(0);

		PaymentPageParam paymentPageParam = new PaymentPageParam();
		paymentPageParam.setTenantId(purchaseOrderInfo.getTenantId());
		paymentPageParam.setMid(purchaseOrderInfo.getMid()); // MID 세팅
		paymentPageParam.setAuthKey(purchaseOrderInfo.getAuthKey());
		paymentPageParam.setOrderId(purchaseOrderInfo.getPrchsId());
		paymentPageParam.setMctTrDate(purchaseOrderInfo.getPrchsDt());
		paymentPageParam.setAmtPurchase(String.valueOf(purchaseOrderInfo.getRealTotAmt()));
		paymentPageParam.setPid(product.getProdId());
		paymentPageParam.setHasFullProdYn(purchaseOrderInfo.isExistCommercialIap() ? "Y" : "N");
		if (purchaseOrderInfo.getPurchaseProductList().size() > 1) {
			paymentPageParam.setpName(product.getProdNm() + " 포함 " + purchaseOrderInfo.getPurchaseProductList().size()
					+ "개");
		} else {
			paymentPageParam.setpName(this.makeProductName(product));
		}
		paymentPageParam.setAid(product.getAid());
		paymentPageParam.setReturnFormat(PP_RETURN_FORMAT_JSON);
		paymentPageParam.setFlgMchtAuth(PurchaseConstants.USE_Y);
		paymentPageParam.setMctSpareParam(this.makeMctSpareParam(purchaseOrderInfo));
		paymentPageParam.setMdn(purchaseOrderInfo.getPurchaseUser().getDeviceId());
		paymentPageParam.setNmDevice(purchaseOrderInfo.getPurchaseUser().getDeviceModelCd());
		paymentPageParam.setImei(purchaseOrderInfo.getImei());
		paymentPageParam.setUacd(purchaseOrderInfo.getUacd());
		if (StringUtils.equals(purchaseOrderInfo.getNetworkTypeCd(), PurchaseConstants.NETWORK_TYPE_3G)
				|| StringUtils.equals(purchaseOrderInfo.getNetworkTypeCd(), PurchaseConstants.NETWORK_TYPE_LTE)) {
			paymentPageParam.setTypeNetwork(PurchaseConstants.PAYPLANET_NETWORK_TYPE_3GLTE); // 3G, LTE
		} else if (StringUtils.equals(purchaseOrderInfo.getNetworkTypeCd(), PurchaseConstants.NETWORK_TYPE_WIFI)) {
			paymentPageParam.setTypeNetwork(PurchaseConstants.PAYPLANET_NETWORK_TYPE_WIFI); // WIFI
		} else {
			paymentPageParam.setTypeNetwork(PurchaseConstants.PAYPLANET_NETWORK_TYPE_UNKNOWN);
		}
		if (StringUtils.equals(purchaseOrderInfo.getPurchaseUser().getTelecom(), PurchaseConstants.TELECOM_SKT)) {
			paymentPageParam.setCarrier(PurchaseConstants.PAYPLANET_TELECOM_SKT); // SKT
		} else if (StringUtils
				.equals(purchaseOrderInfo.getPurchaseUser().getTelecom(), PurchaseConstants.TELECOM_UPLUS)) {
			paymentPageParam.setCarrier(PurchaseConstants.PAYPLANET_TELECOM_LGT); // LGT
		} else if (StringUtils.equals(purchaseOrderInfo.getPurchaseUser().getTelecom(), PurchaseConstants.TELECOM_KT)) {
			paymentPageParam.setCarrier(PurchaseConstants.PAYPLANET_TELECOM_KT); // KT
		} else if (StringUtils.equals(purchaseOrderInfo.getPurchaseUser().getTelecom(), PurchaseConstants.TELECOM_KCT)) {
			paymentPageParam.setCarrier(PurchaseConstants.PAYPLANET_TELECOM_KCT); // KCT
		} else {
			paymentPageParam.setCarrier(PurchaseConstants.PAYPLANET_TELECOM_UNKNOWN); // UKNOWN
		}
		paymentPageParam.setNoSim(purchaseOrderInfo.getSid());
		paymentPageParam.setFlag(purchaseOrderInfo.getFlag());

		if (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(), PurchaseConstants.PRCHS_REQ_PATH_IAP)) {
			paymentPageParam.setServiceId(PurchaseConstants.PAYMENT_PAGE_SERVICE_ID_IAP);
		} else if (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(),
				PurchaseConstants.PRCHS_REQ_PATH_EBOOK_STORAGE)) {
			paymentPageParam.setServiceId(PurchaseConstants.PAYMENT_PAGE_SERVICE_ID_EBOOKSTORE);
		} else {
			paymentPageParam.setServiceId(PurchaseConstants.PAYMENT_PAGE_SERVICE_ID_SHOPCLIENT);
		}

		paymentPageParam.setOPMDLineNo(purchaseOrderInfo.getOpmdNo());
		paymentPageParam.setUserKey(purchaseOrderInfo.getUserKey());
		paymentPageParam.setOfferingId(purchaseOrderInfo.getOfferingId());
		paymentPageParam.setNmDelivery(purchaseOrderInfo.getNmDelivery());
		paymentPageParam.setNoMdnDelivery(purchaseOrderInfo.getNoMdnDelivery());

		// 판매자 정보 세팅
		PurchaseProduct purchaseProduct = purchaseOrderInfo.getPurchaseProductList().get(0);

		SellerMbrAppSacParam sellerInfo = this.purchaseMemberRepository.detailInformationListForProduct(
				purchaseProduct.getSellerMbrNo(), purchaseOrderInfo.getTenantProdGrpCd());
		paymentPageParam.setSellerType(sellerInfo.getSellerClass());

		if (StringUtils.startsWith(purchaseOrderInfo.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_APP)
				|| StringUtils.startsWith(purchaseOrderInfo.getTenantProdGrpCd(),
						PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)) { // APP/IAP 상품
			// 상품 정보의 판매자명은 회사명에만 세팅: 2015.01.05.염동환M
			paymentPageParam.setNmSellerCompany(purchaseProduct.getSellerNm());
			paymentPageParam.setNmSeller(null);
			paymentPageParam.setEmailSeller(purchaseProduct.getSellerEmail());
			paymentPageParam.setNoTelSeller(purchaseProduct.getSellerTelno());
			if (StringUtils.isBlank(paymentPageParam.getNmSellerCompany())
					|| StringUtils.isBlank(paymentPageParam.getEmailSeller())
					|| StringUtils.isBlank(paymentPageParam.getNoTelSeller())) {

				if (StringUtils.isBlank(paymentPageParam.getNmSellerCompany())) {
					paymentPageParam.setNmSellerCompany(sellerInfo.getSellerCompany());
				}
				// paymentPageParam.setNmSeller(sellerInfo.getSellerName());
				if (StringUtils.isBlank(paymentPageParam.getEmailSeller())) {
					paymentPageParam.setEmailSeller(sellerInfo.getSellerEmail());
				}
				if (StringUtils.isBlank(paymentPageParam.getNoTelSeller())) {
					paymentPageParam.setNoTelSeller(sellerInfo.getSellerPhone());
				}
			}

		} else {
			paymentPageParam.setNmSellerCompany(sellerInfo.getSellerCompany());
			paymentPageParam.setNmSeller(sellerInfo.getSellerName());
			paymentPageParam.setEmailSeller(sellerInfo.getSellerEmail());
			paymentPageParam.setNoTelSeller(sellerInfo.getSellerPhone());
		}
		paymentPageParam.setSellerAddress(sellerInfo.getSellerAddress());
		paymentPageParam.setSellerBizNumber(sellerInfo.getSellerBizNumber());

		// 판매자가 개인일 경우 해당 내용 삭제
		if (StringUtils.equals(sellerInfo.getSellerClass(), PurchaseConstants.SELLER_TYPE_INDIVISUAL)) {
			paymentPageParam.setNoTelSeller(null);
			paymentPageParam.setSellerAddress(null);
			paymentPageParam.setSellerBizNumber(null);
		}

		// pDescription
		paymentPageParam.setpDescription(this.makeProductDescription(purchaseOrderInfo.getTenantProdGrpCd(),
				purchaseOrderInfo.getPurchaseProductList()));

		// 암호화
		paymentPageParam.setEData(this.encryptPaymentData(paymentPageParam, purchaseOrderInfo.getEncKey()));

		// Token
		paymentPageParam.setToken(this.makeToken(paymentPageParam));

		// 버전
		paymentPageParam.setVersion(PAYMENT_PAGE_PARAM_VER);

		// 결제Page 요청 URL
		purchaseOrderInfo.setPaymentPageUrlParam(this.makePaymentPageUrlParam(paymentPageParam));

		this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,END,{}", purchaseOrderInfo.getPrchsId());
	}

	/*
	 * 
	 * <pre> 가맹점 파라미터 구성: 구매인증/결제처리결과알림 요청 시 그대로 전달받음. </pre>
	 * 
	 * @param purchaseOrderInfo 구매요청 정보
	 * 
	 * @return 구성된 가맹점 파라미터
	 */
	private String makeMctSpareParam(PurchaseOrderInfo purchaseOrderInfo) {
		/*
		 * 구매 DB 파티션 처리를 위해, USE_ 회원이 1명인 경우에만 파티션 조건 값 세팅 버전:값1:값2:...:값N
		 */

		// 1:useTenantId:useInsdUsermbrNo

		String useTenantId = "";
		String useInsdUsermbrNo = "";

		if (purchaseOrderInfo.isGift()) {
			if (purchaseOrderInfo.getReceiveUserList().size() == 1) {
				useTenantId = purchaseOrderInfo.getReceiveUserList().get(0).getTenantId();
				useInsdUsermbrNo = purchaseOrderInfo.getReceiveUserList().get(0).getUserKey();
			}

		} else {
			useTenantId = purchaseOrderInfo.getTenantId();
			useInsdUsermbrNo = purchaseOrderInfo.getPurchaseUser().getUserKey();
		}

		MctSpareParam mctSpareParam = new MctSpareParam();
		mctSpareParam.setVersion(1);
		mctSpareParam.setUseTenantId(useTenantId);
		mctSpareParam.setUseUserKey(useInsdUsermbrNo);

		return mctSpareParam.makeMctSpareParam();
	}

	/*
	 * 
	 * <pre> 결제Page 노출 상품명 정보 생성. </pre>
	 * 
	 * @param purchaseProduct 구매할 상품 정보
	 * 
	 * @return 결제Page에 노출할 상품명
	 */
	private String makeProductName(PurchaseProduct purchaseProduct) {
		if (StringUtils.equals(purchaseProduct.getCmpxProdClsfCd(), PurchaseConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS)) {
			// 시리즈 전회차
			if (StringUtils
					.startsWith(purchaseProduct.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_VOD)) {
				// VOD
				if (StringUtils.equals(purchaseProduct.getUsePeriodUnitCd(),
						PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED)) {
					return "'" + purchaseProduct.getProdNm() + "' 시리즈 전회차 영구소장"; // 무제한
				} else {
					return "'" + purchaseProduct.getProdNm() + "' 시리즈 전회차";
				}

			} else if (StringUtils.startsWith(purchaseProduct.getTenantProdGrpCd(),
					PurchaseConstants.TENANT_PRODUCT_GROUP_EBOOKCOMIC)) {
				// 이북/코믹
				return "'" + purchaseProduct.getProdNm() + "' 전권";

			} else {
				return purchaseProduct.getProdNm();
			}

			// } else if (StringUtils.equals(purchaseProduct.getCmpxProdClsfCd(),
			// PurchaseConstants.FIXRATE_PROD_TYPE_EBOOKCOMIC_OWN)) {
			// // 전권 소장
			// return "'" + purchaseProduct.getProdNm() + "' 전"
			// + StringUtils.defaultIfBlank(purchaseProduct.getChapterUnit(), "권");
			//
			// } else if (StringUtils.equals(purchaseProduct.getCmpxProdClsfCd(),
			// PurchaseConstants.FIXRATE_PROD_TYPE_EBOOKCOMIC_LOAN)) {
			// // 전권 대여
			// return "'" + purchaseProduct.getProdNm() + "' 전"
			// + StringUtils.defaultIfBlank(purchaseProduct.getChapterUnit(), "권");

		} else {
			// 상품명은 전시에서 회차 정보까지 구성해서 넘겨주는 값 그대로 사용. 2014.09.02 반영
			return purchaseProduct.getProdNm();
			// if (StringUtils.isNotBlank(purchaseProduct.getChapterText())) {
			// return purchaseProduct.getProdNm() + " " + purchaseProduct.getChapterText()
			// + purchaseProduct.getChapterUnit();
			// } else {
			// return purchaseProduct.getProdNm();
			// }
		}
	}

	/*
	 * 
	 * <pre> 구매 상품설명 생성. </pre>
	 * 
	 * @param tenantProdGrpCd 테넌트 상품 분류 코드
	 * 
	 * @param purchaseProductList 구매 기본상품 목록
	 * 
	 * @return 구매 상품설명
	 */
	private String makeProductDescription(String tenantProdGrpCd, List<PurchaseProduct> purchaseProductList) {
		PurchaseProduct purchaseProduct = purchaseProductList.get(0);

		// IAP
		if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)) {

			String prodCase = purchaseProduct.getIapProdCase();
			String prodKind = purchaseProduct.getIapProdKind();

			if (StringUtils.equals(prodCase, "PB0001") || StringUtils.equals(prodCase, "PB0003")) { // 건당상품 / 건당스트리밍상품
				if (StringUtils.equals(prodKind, "PK0001")) { // 영구
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_IAP_UNIT_UNLIMITED;

				} else if (StringUtils.equals(prodKind, "PK0002")) { // 소멸
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_IAP_UNIT_VOLATILE;
				}

			} else if (StringUtils.equals(prodCase, "PB0002") || StringUtils.equals(prodCase, "PB0004")) { // 기간상품 /
																										   // 기간스트리밍상품
				if (StringUtils.equals(prodKind, "PK0003")) { // 일간
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_IAP_PERIOD_PREFIX
							+ purchaseProduct.getUsePeriod()
							+ PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_IAP_PERIOD_SUFFIX;

				} else if (StringUtils.equals(prodKind, "PK0004")) { // 주간
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_IAP_PERIOD_PREFIX
							+ purchaseProduct.getUsePeriod()
							+ PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_IAP_PERIOD_SUFFIX;

				} else if (StringUtils.equals(prodKind, "PK0005")) { // 월간
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_IAP_PERIOD_PREFIX
							+ purchaseProduct.getUsePeriod()
							+ PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_IAP_PERIOD_SUFFIX;
				}

			} else if (StringUtils.equals(prodCase, "PB0005")) { // 정식판전환상품
				if (StringUtils.equals(prodKind, "PK0001")) {
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_IAP_COMMERCIAL;
				}

			} else if (StringUtils.equals(prodCase, "PB0006")) { // 자동결제
				if (StringUtils.equals(prodKind, "PK0005")) { // 월별자동결제
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_IAP_AUTOMONTH;
				}
			}

			// 소장/대여
		} else if (StringUtils.endsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_UNIT)
				&& purchaseProductList.size() == 1
				&& (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_VOD) || StringUtils
						.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_EBOOKCOMIC))) {

			// 소장/대여 중 하나만 존재하는 경우에만 세팅 : 소장이 디폴트
			if (StringUtils.equals(purchaseProduct.getPossLendClsfCd(),
					PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_RENTAL)) {
				return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_LOAN;
			} else {
				return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_OWN;
			}

			// 쇼핑
		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			if (StringUtils.equals(purchaseProduct.getProdCaseCd(), PurchaseConstants.SHOPPING_TYPE_DELIVERY)) {
				return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_DELIVERY;
			} else {
				return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_COUPON;
			}

			// MP3, 벨소리&컬러링
		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_MUSIC)) {

			// 벨소리&컬러링
			if (StringUtils.startsWith(tenantProdGrpCd.substring(8), PurchaseConstants.DISPLAY_TOP_MENU_ID_PHONEDECO)) {
				if (StringUtils.equals(purchaseProduct.getTimbreClsf(), PurchaseConstants.RINGBELL_CLASS_BELL_HIGH)) {
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_BELL_HIGH;
				} else if (StringUtils.equals(purchaseProduct.getTimbreClsf(),
						PurchaseConstants.RINGBELL_CLASS_BELL_BASIC)) {
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_BELL_BASIC;
				} else if (StringUtils.equals(purchaseProduct.getTimbreClsf(),
						PurchaseConstants.RINGBELL_CLASS_RING_LONG)) {
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_RING_HIGH;
				} else if (StringUtils.equals(purchaseProduct.getTimbreClsf(),
						PurchaseConstants.RINGBELL_CLASS_RING_BASIC)) {
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_RING_BASIC;
				}

			} else {
				if (StringUtils.equals(purchaseProduct.getResvCol03(), "128")) {
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_MP3_NORMAL;
				} else {
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_MP3_HIGH;
				}
			}

			// 벨소리&컬러링
		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_RINGBELL)) {
			if (StringUtils.equals(purchaseProduct.getTimbreClsf(), PurchaseConstants.RINGBELL_CLASS_BELL_HIGH)) {
				return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_BELL_HIGH;
			} else if (StringUtils.equals(purchaseProduct.getTimbreClsf(), PurchaseConstants.RINGBELL_CLASS_BELL_BASIC)) {
				return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_BELL_BASIC;
			} else if (StringUtils.equals(purchaseProduct.getTimbreClsf(), PurchaseConstants.RINGBELL_CLASS_RING_LONG)) {
				return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_RING_HIGH;
			} else if (StringUtils.equals(purchaseProduct.getTimbreClsf(), PurchaseConstants.RINGBELL_CLASS_RING_BASIC)) {
				return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_RING_BASIC;
			}

			// 게임캐쉬 정액제
		} else if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
			return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_AUTO;

			// VOD정액권, 이북/코믹 전권
		} else if (StringUtils.endsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_FIXRATE)) {
			if (StringUtils.equals(purchaseProduct.getCmpxProdClsfCd(),
					PurchaseConstants.FIXRATE_PROD_TYPE_VOD_SERIESPASS)) {
				if (StringUtil.equals(purchaseProduct.getPossLendClsfCd(),
						PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_POSSESION))
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_OWN; // 소장
				else if (StringUtil.equals(purchaseProduct.getPossLendClsfCd(),
						PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_RENTAL))
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_LOAN; // 대여
				else
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_SERIES; // 구매

			} else if (StringUtils.equals(purchaseProduct.getCmpxProdClsfCd(),
					PurchaseConstants.FIXRATE_PROD_TYPE_VOD_FIXRATE)) {
				if (StringUtils.equals(purchaseProduct.getAutoPrchsYN(), PurchaseConstants.USE_Y)) {
					return PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_AUTO;
				} else {
					if (StringUtils.equals(purchaseProduct.getUsePeriodUnitCd(),
							PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_DATE)) {
						return purchaseProduct.getUsePeriod() + PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_DATE_SUFFIX;

					} else if (StringUtils.equals(purchaseProduct.getUsePeriodUnitCd(),
							PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_HOUR)) {
						return purchaseProduct.getUsePeriod() + PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_HOUR_SUFFIX;

					} else if (StringUtils.equals(purchaseProduct.getUsePeriodUnitCd(),
							PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_MONTH)) {
						return purchaseProduct.getUsePeriod()
								+ PurchaseConstants.PAYMENT_PAGE_PRODUCT_DESC_MONTH_SUFFIX;
					}
				}
			} else if (StringUtils.equals(purchaseProduct.getCmpxProdClsfCd(),
					PurchaseConstants.FIXRATE_PROD_TYPE_EBOOKCOMIC_OWN)) {
				return "소장";
			} else if (StringUtils.equals(purchaseProduct.getCmpxProdClsfCd(),
					PurchaseConstants.FIXRATE_PROD_TYPE_EBOOKCOMIC_LOAN)) {
				return "대여";
			}
		}

		return "";
	}

	/*
	 * 
	 * <pre> 결제Page 데이터 암호화. </pre>
	 * 
	 * @param paymentPageParam 결제Page 데이터
	 * 
	 * @param encKey 암호화 키
	 * 
	 * @return 암호화된 데이터
	 */
	private String encryptPaymentData(PaymentPageParam paymentPageParam, String encKey) {
		String pNameWithUrlEncoding = paymentPageParam.getpName();
		String pDescriptionWithUrlEncoding = paymentPageParam.getpDescription();
		String nmDeliveryWithUrlEncoding = paymentPageParam.getNmDelivery();
		String nmSellerCompanyWithUrlEncoding = paymentPageParam.getNmSellerCompany();
		String nmSellerWithUrlEncoding = paymentPageParam.getNmSeller();
		String nmsellerAddressEncoding = paymentPageParam.getSellerAddress();
		String nmsellerBizRegNumberEncoding = paymentPageParam.getSellerBizNumber();

		// P/P 적용 이후 반영
		try {
			if (StringUtils.isNotBlank(paymentPageParam.getpName())) {
				pNameWithUrlEncoding = URLEncoder.encode(paymentPageParam.getpName(), "UTF-8");
			}
			if (StringUtils.isNotBlank(paymentPageParam.getpDescription())) {
				pDescriptionWithUrlEncoding = URLEncoder.encode(paymentPageParam.getpDescription(), "UTF-8");
			}
			if (StringUtils.isNotBlank(paymentPageParam.getNmDelivery())) {
				nmDeliveryWithUrlEncoding = URLEncoder.encode(paymentPageParam.getNmDelivery(), "UTF-8");
			}
			if (StringUtils.isNotBlank(paymentPageParam.getNmSellerCompany())) {
				nmSellerCompanyWithUrlEncoding = URLEncoder.encode(paymentPageParam.getNmSellerCompany(), "UTF-8");
			}
			if (StringUtils.isNotBlank(paymentPageParam.getNmSeller())) {
				nmSellerWithUrlEncoding = URLEncoder.encode(paymentPageParam.getNmSeller(), "UTF-8");
			}
			if (StringUtils.isNotBlank(paymentPageParam.getSellerAddress())) {
				nmsellerAddressEncoding = URLEncoder.encode(paymentPageParam.getSellerAddress(), "UTF-8");
			}
			if (StringUtils.isNotBlank(paymentPageParam.getSellerBizNumber())) {
				nmsellerBizRegNumberEncoding = URLEncoder.encode(paymentPageParam.getSellerBizNumber(), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			throw new StorePlatformException("SAC_PUR_7201", e);
		}
		this.logger
				.info("PRCHS,ORDER,SAC,PAYPAGE,EDATA,SRC,KOR,pName={},pDescription={},nmDelivery={},nmSellerCompany={},nmSeller={}",
						paymentPageParam.getpName(), paymentPageParam.getpDescription(),
						paymentPageParam.getNmDelivery(), paymentPageParam.getNmSellerCompany(),
						paymentPageParam.getNmSeller());

		// 암호화 데이터 구성
		StringBuffer sb = new StringBuffer(1024);
		sb.append("tenantId=").append(paymentPageParam.getTenantId());
		sb.append("&mid=").append(paymentPageParam.getMid());
		sb.append("&orderId=").append(paymentPageParam.getOrderId());
		sb.append("&mctTrDate=").append(paymentPageParam.getMctTrDate());
		sb.append("&amtPurchase=").append(paymentPageParam.getAmtPurchase());
		sb.append("&pid=").append(paymentPageParam.getPid());
		sb.append("&pName=").append(pNameWithUrlEncoding);
		sb.append("&pDescription=").append(pDescriptionWithUrlEncoding);
		sb.append("&aid=").append(StringUtils.defaultString(paymentPageParam.getAid()));
		sb.append("&returnFormat=").append(paymentPageParam.getReturnFormat());
		sb.append("&flgMchtAuth=").append(paymentPageParam.getFlgMchtAuth());
		sb.append("&mctSpareParam=").append(paymentPageParam.getMctSpareParam());
		sb.append("&mdn=").append(paymentPageParam.getMdn());
		sb.append("&nmDevice=").append(StringUtils.defaultString(paymentPageParam.getNmDevice()));
		sb.append("&imei=").append(StringUtils.defaultString(paymentPageParam.getImei()));
		sb.append("&typeNetwork=").append(paymentPageParam.getTypeNetwork());
		sb.append("&carrier=").append(StringUtils.defaultString(paymentPageParam.getCarrier()));
		sb.append("&noSim=").append(StringUtils.defaultString(paymentPageParam.getNoSim()));
		sb.append("&serviceId=").append(StringUtils.defaultString(paymentPageParam.getServiceId()));
		sb.append("&OPMDLineNo=").append(StringUtils.defaultString(paymentPageParam.getOPMDLineNo()));
		sb.append("&userKey=").append(paymentPageParam.getUserKey());
		sb.append("&offeringId=").append(StringUtils.defaultString(paymentPageParam.getOfferingId()));
		sb.append("&nmDelivery=").append(StringUtils.defaultString(nmDeliveryWithUrlEncoding));
		sb.append("&noMdnDelivery=").append(StringUtils.defaultString(paymentPageParam.getNoMdnDelivery()));
		sb.append("&nmSellerCompany=").append(StringUtils.defaultString(nmSellerCompanyWithUrlEncoding));
		sb.append("&nmSeller=").append(StringUtils.defaultString(nmSellerWithUrlEncoding));
		sb.append("&emailSeller=").append(StringUtils.defaultString(paymentPageParam.getEmailSeller()));
		sb.append("&noTelSeller=").append(StringUtils.defaultString(paymentPageParam.getNoTelSeller()));
		sb.append("&sellerType=").append(StringUtils.defaultString(paymentPageParam.getSellerType()));
		sb.append("&flag=").append(StringUtils.defaultString(paymentPageParam.getFlag()));
		sb.append("&sellerAddress=").append(StringUtils.defaultString(nmsellerAddressEncoding));
		sb.append("&bizRegNumber=").append(StringUtils.defaultString(nmsellerBizRegNumberEncoding));
		sb.append("&hasFullProdYn=").append(paymentPageParam.getHasFullProdYn());

		String plainData = sb.toString();
		this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,EDATA,SRC,{}", plainData);

		// 암호화
		String encData = null;
		try {
			encData = PayPlanetUtils.encrypt(plainData, encKey);
			this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,EDATA,ENC,{}", encData);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7201", e);
		}
		return encData;
	}

	/*
	 * 
	 * <pre> 결제Page 토큰 생성. </pre>
	 * 
	 * @param paymentPageParam 결제Page 데이터
	 * 
	 * @return 생성된 토큰값
	 */
	private String makeToken(PaymentPageParam paymentPageParam) {
		// 토큰 구성 : AuthKey+OrderID+AmtPurchase+MID
		StringBuffer sb = new StringBuffer(128);
		sb.append(paymentPageParam.getAuthKey()).append(paymentPageParam.getOrderId())
				.append(paymentPageParam.getAmtPurchase()).append(paymentPageParam.getMid());

		String plainToken = sb.toString();
		this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,TOKEN,SRC,{}", plainToken);

		// Digest MD5
		String md5Token = null;
		try {
			md5Token = MD5Utils.digestInHexFormat(plainToken);
			this.logger.info("PRCHS,ORDER,SAC,PAYPAGE,TOKEN,ENC,{}", md5Token);
		} catch (Exception e) {
			throw new StorePlatformException("SAC_PUR_7201", e);
		}
		return md5Token;
	}

	/*
	 * 
	 * <pre> 결제Page URL 파라미터 생성. </pre>
	 * 
	 * @param paymentPageParam 결제Page 파라미터 값 정보
	 * 
	 * @return 결제Page URL 파라미터
	 */
	private String makePaymentPageUrlParam(PaymentPageParam paymentPageParam) {
		StringBuffer sbParam = new StringBuffer(paymentPageParam.getEData().length()
				+ paymentPageParam.getToken().length() + 25);

		sbParam.append("version=").append(paymentPageParam.getVersion()).append("&token=")
				.append(paymentPageParam.getToken()).append("&EData=").append(paymentPageParam.getEData());

		return sbParam.toString();
	}

	/**
	 * 
	 * <pre>
	 * 결제Page 템플릿 코드 정의.
	 * </pre>
	 * 
	 * @param prchsCaseCd
	 *            구매/선물 구분 코드
	 * 
	 * @param tenantProdGrpCd
	 *            테넌트 상품 그룹 코드
	 * 
	 * @param cmpxProdClsfCd
	 *            정액상품 구분 코드
	 * 
	 * @param bAutoPrchs
	 *            자동결제 상품 여부
	 * 
	 * @param bS2sAutoPrchs
	 *            IAP S2S 월자동결제 상품 여부
	 * 
	 * @param bS2s
	 *            IAP S2S 상품 여부
	 * 
	 * @param prchsProdCnt
	 *            구매하는 상품 갯수
	 * 
	 * @return 결제Page 템플릿 코드
	 */
	@Override
	public String adjustPaymentPageTemplate(String prchsCaseCd, String tenantProdGrpCd, String cmpxProdClsfCd,
			boolean bAutoPrchs, boolean bS2sAutoPrchs, boolean bS2s, int prchsProdCnt) {

		if (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) {
			return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_SHOPPING; // 쇼핑: TC05

		} else if (StringUtils.equals(prchsCaseCd, PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_GIFT; // 선물: TC06

		} else {
			if (bS2sAutoPrchs) { // IAP S2S 월자동결제 상품
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_IAP_S2S_AUTOPAY; // S2S 자동결제: TC08

			} else if (bAutoPrchs
					&& StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)) {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_IAP_AUTOPAY; // IAP 자동결제: TC07

			} else if (bS2s) {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_IAP_S2S_UNIT; // S2S 단품: TC09

			} else if (bAutoPrchs
					&& (StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_VOD) || StringUtils.startsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_EBOOKCOMIC))
					&& StringUtils.endsWith(tenantProdGrpCd, PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_FIXRATE)) {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_AUTOPAY; // 자동결제: TC04

			} else if (StringUtils.startsWith(tenantProdGrpCd,
					PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE)) {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_GAMECASH_FIXRATE; // 정액제(게임캐쉬): TC02

			} else {
				return PurchaseConstants.PAYMENT_PAGE_TEMPLATE_NORMAL; // 일반: TC01
			}
		}
	}
}
