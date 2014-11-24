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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.purchase.client.history.sci.ExistenceSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReqProduct;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseDisplayRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseIapRepository;
import com.skplanet.storeplatform.sac.purchase.order.repository.PurchaseMemberRepository;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderInfo;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseProduct;
import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseUserDevice;
import com.skplanet.storeplatform.sac.purchase.shopping.repository.ShoppingRepository;
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacParam;

/**
 * 
 * 구매 적합성 체크 서비스 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderValidationServiceImpl implements PurchaseOrderValidationService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("#{systemProperties['spring.profiles.active']}")
	private String envServerLevel;

	@Autowired
	private ExistenceSCI existenceSCI;
	@Autowired
	private PurchaseOrderSearchSCI purchaseOrderSearchSCI;
	@Autowired
	private PurchaseOrderAssistService purchaseOrderAssistService;
	@Autowired
	private PurchaseOrderPolicyService purchaseOrderPolicyService;
	@Autowired
	private ExistenceSacService existenceSacService;
	@Autowired
	private PurchaseMemberRepository purchaseMemberRepository;
	@Autowired
	private PurchaseDisplayRepository purchaseDisplayRepository;
	@Autowired
	private PurchaseIapRepository purchaseIapRepository;
	@Autowired
	private ShoppingRepository shoppingRepository;

	private final List<String> freeChargeReqCdList;

	public PurchaseOrderValidationServiceImpl() {
		// TAKTODO:: 비과금 요청 허용 코드 목록 관리
		this.freeChargeReqCdList = new ArrayList<String>();
		this.freeChargeReqCdList.add(PurchaseConstants.PRCHS_REQ_PATH_IAP_COMMERCIAL_CONVERTED); // OR000408-정식판전환
		this.freeChargeReqCdList.add(PurchaseConstants.PRCHS_REQ_PATH_B2B_BALANCE); // OR000421-B2B Gateway(정산)
		this.freeChargeReqCdList.add(PurchaseConstants.PRCHS_REQ_PATH_B2B_NON_BALANCE); // OR000422-B2B Gateway(비정산)
		this.freeChargeReqCdList.add(PurchaseConstants.PRCHS_REQ_PATH_T_FREEMIUM); // OR000420-T freemium(DRM)
		this.freeChargeReqCdList.add(PurchaseConstants.PRCHS_REQ_PATH_T_BENEFIT_EVENT); // OR000413-T혜택 이벤트
		this.freeChargeReqCdList.add(PurchaseConstants.PRCHS_REQ_PATH_AOM_AGREE); // OR000446-AOM 수신동의
	}

	/**
	 * 
	 * <pre>
	 * 구매요청 파라미터 적합성 체크.
	 * </pre>
	 * 
	 * @param req
	 *            구매요청 VO
	 */
	@Override
	public void validatePurchaseRequestParameter(CreatePurchaseSacReq req) {

		// 선물 요청: 수신자 정보 필수
		if (StringUtils.equals(req.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
			if ((StringUtils.isBlank(req.getRecvUserKey()) || StringUtils.isBlank(req.getRecvDeviceKey()))
					&& CollectionUtils.isEmpty(req.getReceiverList())) {
				throw new StorePlatformException("SAC_PUR_5100", "수신자 정보 누락");
			}
		}

		// 부분유료화 정보
		if (StringUtils.startsWith(req.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_IAP)) {
			for (CreatePurchaseSacReqProduct product : req.getProductList()) {
				// if (StringUtils.isBlank(product.getTxId()) || StringUtils.isBlank(product.getPartChrgVer())) {
				if (StringUtils.isBlank(product.getPartChrgVer())) {
					throw new StorePlatformException("SAC_PUR_5100", "IAP 필수정보 누락");
				}
			}
		}

		// TAKTODO:: 링&벨
		// if( StringUtils.startsWith(req.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_RINGBELL)) {
		// for(CreatePurchaseSacReqProduct product : req.getProductList()) {
		// if( StringUtils.isBlank(product.getTimbreClsf()) ) {
		// throw new StorePlatformException("SAC_PUR_5100", "링&벨 필수정보 누락");
		// }
		// }
		// }

		if (CollectionUtils.isNotEmpty(req.getProductList())) {
			// 정액상품 경우, 한 건만 구매 가능
			if (req.getProductList().size() > 1
					&& (StringUtils.endsWith(req.getTenantProdGrpCd(),
							PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_FIXRATE) || StringUtils.endsWith(
							req.getTenantProdGrpCd(), PurchaseConstants.TENANT_PRODUCT_GROUP_DTL_GAMECASH_FIXRATE))) {
				throw new StorePlatformException("SAC_PUR_5100", "정액상품은 한 건만 구매가능");
			}

			// 상품ID 중복 체크
			Set<String> prodIdSet = new HashSet<String>();
			for (CreatePurchaseSacReqProduct product : req.getProductList()) {
				if (prodIdSet.add(product.getProdId()) == false) {
					throw new StorePlatformException("SAC_PUR_5100", "중복된 상품ID");
				}
			}
		}

		// 테넌트 상품 분류 코드 체크
		String reqGroup = req.getTenantProdGrpCd().substring(0, 8);
		String reqMenuId = req.getTenantProdGrpCd().substring(8, 12);
		String reqSuffix = req.getTenantProdGrpCd().substring(12);

		if (StringUtils.startsWith(reqGroup, PurchaseConstants.TENANT_PRODUCT_GROUP_APP)) {
			// 어플리케이션 : 게임, FUN, 생활/위치, 어학/교육, Android
			if (StringUtils.equals(reqMenuId, "DP01") == false && StringUtils.equals(reqMenuId, "DP03") == false
					&& StringUtils.equals(reqMenuId, "DP04") == false && StringUtils.equals(reqMenuId, "DP08") == false
					&& StringUtils.equals(reqMenuId, "DP12") == false) {
				throw new StorePlatformException("SAC_PUR_5100", "잘못된 상품 분류 코드");
			}

		} else if (StringUtils.startsWith(reqGroup, PurchaseConstants.TENANT_PRODUCT_GROUP_VOD)) {
			// VOD : 영화, TV방송, 방송/영화, 동영상
			if (StringUtils.equals(reqMenuId, "DP17") == false && StringUtils.equals(reqMenuId, "DP18") == false
					&& StringUtils.equals(reqMenuId, "DP07") == false && StringUtils.equals(reqMenuId, "DP09") == false) {
				throw new StorePlatformException("SAC_PUR_5100", "잘못된 상품 분류 코드");
			}

		} else if (StringUtils.startsWith(reqGroup, PurchaseConstants.TENANT_PRODUCT_GROUP_MUSIC)) {
			// 뮤직(MP3) : 통합뮤직, 뮤직
			if (StringUtils.equals(reqMenuId, "DP16") == false && StringUtils.equals(reqMenuId, "DP05") == false) {
				throw new StorePlatformException("SAC_PUR_5100", "잘못된 상품 분류 코드");
			}

		} else if (StringUtils.startsWith(reqGroup, PurchaseConstants.TENANT_PRODUCT_GROUP_EBOOKCOMIC)) {
			// 이북/코믹 : eBook, Comic, 웹툰, 연재소설, 만화
			if (StringUtils.equals(reqMenuId, "DP13") == false && StringUtils.equals(reqMenuId, "DP14") == false
					&& StringUtils.equals(reqMenuId, "DP26") == false && StringUtils.equals(reqMenuId, "DP29") == false
					&& StringUtils.equals(reqMenuId, "DP06") == false) {
				throw new StorePlatformException("SAC_PUR_5100", "잘못된 상품 분류 코드");
			}

		} else if (StringUtils.startsWith(reqGroup, PurchaseConstants.TENANT_PRODUCT_GROUP_SHOPPING)) { // 쇼핑
			// 쇼핑 : 쇼핑
			if (StringUtils.equals(reqMenuId, "DP28") == false
					|| StringUtils.equals(reqSuffix, PurchaseConstants.TENANT_PRODUCT_GROUP_SUFFIX_UNIT) == false) {
				throw new StorePlatformException("SAC_PUR_5100", "잘못된 상품 분류 코드");
			}
		} else if (StringUtils.startsWith(reqGroup, PurchaseConstants.TENANT_PRODUCT_GROUP_RINGBELL)) {
			// 벨소리/컬러링 : 폰꾸미기
			if (StringUtils.equals(reqMenuId, "DP02") == false) {
				throw new StorePlatformException("SAC_PUR_5100", "잘못된 상품 분류 코드");
			}

		} // else IAP ??

		// CLINK
		if (StringUtils.equals(req.getPrchsReqPathCd(), PurchaseConstants.PRCHS_REQ_PATH_CLINK)) {
			if (req.getTotAmt().doubleValue() != 0) {
				throw new StorePlatformException("SAC_PUR_5100", "CLINK는 유료상품 구매 불가");
			}

			for (CreatePurchaseSacReqProduct product : req.getProductList()) {
				if (product.getProdAmt().doubleValue() != 0) {
					throw new StorePlatformException("SAC_PUR_5100", "CLINK는 유료상품 구매 불가");
				}
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * 비과금 구매요청 권한 체크.
	 * </pre>
	 * 
	 * @param prchsReqPathCd
	 *            구매 요청 경로 코드
	 */
	@Override
	public void validateFreeChargeAuth(String prchsReqPathCd) {
		if (this.freeChargeReqCdList.contains(prchsReqPathCd) == false) {
			throw new StorePlatformException("SAC_PUR_5201");
		}
	}

	/**
	 * 
	 * <pre>
	 * Biz 쿠폰 발급 요청 권한 체크.
	 * </pre>
	 * 
	 * @param prchsReqPathCd
	 *            구매 요청 경로 코드
	 */
	@Override
	public void validateBizAuth(String prchsReqPathCd) {
		if (StringUtils.equals(prchsReqPathCd, PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON) == false) { // OR000432-Biz쿠폰
			throw new StorePlatformException("SAC_PUR_5201");
		}
	}

	/**
	 * 
	 * <pre>
	 * 회원 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	@Override
	public void validateMember(PurchaseOrderInfo purchaseOrderInfo) {
		// --------------------------------------------------------------------------------------
		// 구매(선물발신) 회원/기기

		// 조회
		PurchaseUserDevice purchaseUserDevice = this.purchaseMemberRepository.searchUserDeviceByKey(
				purchaseOrderInfo.getTenantId(), purchaseOrderInfo.getUserKey(), purchaseOrderInfo.getDeviceKey());
		if (purchaseUserDevice == null) {
			throw new StorePlatformException("SAC_PUR_4101", purchaseOrderInfo.getUserKey(),
					purchaseOrderInfo.getDeviceKey());
		}

		// 회원상태 체크 : 정상 상태 또는 모바일회원이 가가입 상태 (Save&Sync)
		if ((StringUtils.equals(purchaseUserDevice.getUserMainStatus(), PurchaseConstants.USER_STATUS_NORMAL) == false)
				&& ((StringUtils
						.equals(purchaseUserDevice.getUserMainStatus(), PurchaseConstants.USER_STATUS_TEMP_JOIN) && StringUtils
						.equals(purchaseUserDevice.getUserType(), PurchaseConstants.USER_TYPE_MOBILE)) == false)) {
			throw new StorePlatformException("SAC_PUR_4102", purchaseUserDevice.getUserMainStatus());
		}

		// 디바이스 모델 코드, 통신사 코드 : 요청 값이 있는 경우에는 요청 값으로 사용
		if (StringUtils.isNotBlank(purchaseOrderInfo.getDeviceModelCd())) {
			purchaseUserDevice.setDeviceModelCd(purchaseOrderInfo.getDeviceModelCd());
		}
		if (StringUtils.isNotBlank(purchaseOrderInfo.getTelecomCd())) {
			purchaseUserDevice.setTelecom(purchaseOrderInfo.getTelecomCd());
		}

		purchaseOrderInfo.setPurchaseUser(purchaseUserDevice);

		// ----------------------------------------------------------------------------------------------
		// 선물 수신 회원/기기

		// Biz 쿠폰 경우 수신자 체크 Skip
		if (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(), PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON)) {
			return;
		}

		if (purchaseOrderInfo.isGift()) {

			List<PurchaseUserDevice> receiveUserList = new ArrayList<PurchaseUserDevice>();
			PurchaseUserDevice receiveUserDevice = null;

			for (PurchaseUserDevice receiver : purchaseOrderInfo.getReceiveUserList()) {

				if (StringUtils.equals(receiver.getUserKey(), PurchaseConstants.NONMEMBER_COMMON_USERKEY)) {
					// 비회원 경우
					receiveUserDevice = new PurchaseUserDevice();
					receiveUserDevice.setTenantId(receiver.getTenantId());
					receiveUserDevice.setUserKey(receiver.getUserKey());
					receiveUserDevice.setDeviceId(receiver.getDeviceKey());
					receiveUserDevice.setDeviceKey(receiver.getDeviceKey());

				} else {

					// 조회
					receiveUserDevice = this.purchaseMemberRepository.searchUserDeviceByKey(receiver.getTenantId(),
							receiver.getUserKey(), receiver.getDeviceKey());

					if (receiveUserDevice == null) {
						throw new StorePlatformException("SAC_PUR_4103", receiver.getUserKey(), receiver.getDeviceKey());
					}

					// 회원상태 체크
					if (StringUtils.equals(receiveUserDevice.getUserMainStatus(), PurchaseConstants.USER_STATUS_NORMAL) == false) {
						throw new StorePlatformException("SAC_PUR_4104", receiveUserDevice.getUserMainStatus());
					}
				}

				receiveUserDevice.setGiftMsg(receiver.getGiftMsg());

				receiveUserList.add(receiveUserDevice);
			}

			purchaseOrderInfo.setReceiveUserList(receiveUserList);
		}
	}

	/**
	 * 
	 * <pre>
	 * 상품 적합성 체크.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	@Override
	public void validateProduct(PurchaseOrderInfo purchaseOrderInfo) {

		String tenantId = purchaseOrderInfo.getTenantId();
		// String systemId = purchaseOrderInfo.getSystemId();
		String langCd = purchaseOrderInfo.getLangCd();
		String useDeviceModelCd = null;
		if (purchaseOrderInfo.isShopping()) {
			useDeviceModelCd = purchaseOrderInfo.getPurchaseUser().getDeviceModelCd();
		} else {
			useDeviceModelCd = purchaseOrderInfo.isGift() ? purchaseOrderInfo.getReceiveUserList().get(0)
					.getDeviceModelCd() : purchaseOrderInfo.getPurchaseUser().getDeviceModelCd();
		}

		// 상품 정보 조회

		List<CreatePurchaseSacReqProduct> reqProdList = null;
		List<String> prodIdList = new ArrayList<String>();

		if (CollectionUtils.isNotEmpty(purchaseOrderInfo.getCreatePurchaseReq().getProductList())) {
			for (CreatePurchaseSacReqProduct reqProduct : purchaseOrderInfo.getCreatePurchaseReq().getProductList()) {
				prodIdList.add(reqProduct.getProdId());
			}

			reqProdList = purchaseOrderInfo.getCreatePurchaseReq().getProductList();

		} else {
			prodIdList.add(purchaseOrderInfo.getCreatePurchaseReq().getProdId());

			CreatePurchaseSacReqProduct reqProduct = new CreatePurchaseSacReqProduct();
			reqProduct.setProdId(purchaseOrderInfo.getCreatePurchaseReq().getProdId());
			reqProdList = new ArrayList<CreatePurchaseSacReqProduct>();
			reqProdList.add(reqProduct);
		}

		Map<String, PurchaseProduct> purchaseProductMap = this.purchaseDisplayRepository.searchPurchaseProductList(
				tenantId, langCd, useDeviceModelCd, prodIdList, purchaseOrderInfo.isFlat());
		if (purchaseProductMap == null || purchaseProductMap.size() < 1) {
			throw new StorePlatformException("SAC_PUR_5101");
		}

		this.logger.info("PRCHS,SAC,ORDER,VALID,PRODUCT,MILEAGE,RATEMAP,{}",
				purchaseProductMap.get(reqProdList.get(0).getProdId()).getMileageRateMap());

		// 상품 체크

		String reqMenuId = purchaseOrderInfo.getTenantProdGrpCd().substring(8, 12);

		boolean bClink = purchaseOrderInfo.isClink();

		List<PurchaseProduct> purchaseProductList = purchaseOrderInfo.getPurchaseProductList();
		double checkTotAmt = 0.0;
		PurchaseProduct purchaseProduct = null;
		for (CreatePurchaseSacReqProduct reqProduct : reqProdList) {
			purchaseProduct = purchaseProductMap.get(reqProduct.getProdId());

			// 상품정보 조회 실패
			if (purchaseProduct == null) {
				if (bClink) { // CLINK 예외 처리
					purchaseProduct = new PurchaseProduct();
					purchaseProduct.setProdId(reqProduct.getProdId());
					purchaseProduct.setResultCd("5101");
					purchaseProductList.add(purchaseProduct);
					continue;
				} else {
					throw new StorePlatformException("SAC_PUR_5101", reqProduct.getProdId());
				}
			}

			// 상품 판매상태 체크
			if (StringUtils.equals(purchaseProduct.getProdStatusCd(), PurchaseConstants.PRODUCT_STATUS_SALE) == false
					&& StringUtils.equals(purchaseProduct.getProdStatusCd(),
							PurchaseConstants.PRODUCT_STATUS_FIXRATE_SALE) == false) {
				if (bClink) { // CLINK 예외 처리
					purchaseProduct.setResultCd("5102");
					purchaseProductList.add(purchaseProduct);
					continue;
				} else {
					throw new StorePlatformException("SAC_PUR_5102", purchaseProduct.getProdStatusCd());
				}
			}

			// 메뉴ID 체크
			if (StringUtils.equals(reqMenuId, purchaseProduct.getTopMenuId()) == false
					&& purchaseOrderInfo.isIap() == false) {
				if (bClink) { // CLINK 예외 처리
					purchaseProduct.setResultCd("5113");
					purchaseProductList.add(purchaseProduct);
					continue;
				} else {
					throw new StorePlatformException("SAC_PUR_5113", reqProduct.getProdId(), reqMenuId,
							purchaseProduct.getTopMenuId());
				}
			}

			// Biz 쿠폰 경우 이하 상품 체크 Skip
			if (purchaseOrderInfo.isBizShopping()) {
				purchaseProduct.setProdQty(1);
				purchaseProductList.add(purchaseProduct);
				continue;
			}

			// IAP 여부 체크
			if (purchaseOrderInfo.isIap()
					&& StringUtils.equals(purchaseProduct.getInAppYn(), PurchaseConstants.USE_Y) == false) {
				throw new StorePlatformException("SAC_PUR_5111");
			}

			// 상품 지원 여부 체크 : IAP/쇼핑은 프로비저닝 skip
			if ((purchaseOrderInfo.isShopping() == false) && (purchaseOrderInfo.isIap() == false)) {
				if (StringUtils.equals(purchaseProduct.getProdSprtYn(), PurchaseConstants.USE_Y) == false) {
					if (StringUtils.equals(purchaseOrderInfo.getPrchsCaseCd(), PurchaseConstants.PRCHS_CASE_GIFT_CD)) {
						throw new StorePlatformException("SAC_PUR_5104", reqProduct.getProdId(), useDeviceModelCd);
					} else {
						if (bClink) { // CLINK 예외 처리
							purchaseProduct.setResultCd("5103");
							purchaseProductList.add(purchaseProduct);
							continue;
						} else {
							throw new StorePlatformException("SAC_PUR_5103", reqProduct.getProdId(), useDeviceModelCd);
						}
					}
				}
			}

			// S2S 상품 가격 조회
			if (StringUtils.isNotBlank(purchaseProduct.getSearchPriceUrl())) {
				String reqTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				double price = this.purchaseIapRepository.searchIapS2SPrice(purchaseProduct.getSearchPriceUrl(),
						reqTime, purchaseProduct.getAid(), purchaseProduct.getProdId(), purchaseOrderInfo
								.getPurchaseProductList().get(0).getTid());
				purchaseProduct.setProdAmt(price);
			}

			// 요청한 가격으로 세팅하는 경우
			if (StringUtils
					.equals(purchaseOrderInfo.getSaleAmtProcType(), PurchaseConstants.SALE_AMT_PROC_TYPE_REQUEST)
					|| purchaseOrderInfo.isRingbell()) {
				purchaseProduct.setProdAmt(reqProduct.getProdAmt().doubleValue());
			}

			// 상품 가격 체크: 요청 금액 무시(서버 금액 사용) 경우는 제외
			if (reqProduct.getProdAmt().doubleValue() != purchaseProduct.getProdAmt().doubleValue()
					&& StringUtils.equals(purchaseOrderInfo.getSaleAmtProcType(),
							PurchaseConstants.SALE_AMT_PROC_TYPE_SERVER) == false) {
				if (bClink) { // CLINK 예외 처리
					purchaseProduct.setResultCd("5105");
					purchaseProductList.add(purchaseProduct);
					continue;
				} else {
					throw new StorePlatformException("SAC_PUR_5105", reqProduct.getProdId(), reqProduct.getProdAmt()
							.doubleValue(), purchaseProduct.getProdAmt().doubleValue());
				}
			}

			purchaseProduct.setProdQty(reqProduct.getProdQty());
			checkTotAmt += (purchaseProduct.getProdAmt().doubleValue() * reqProduct.getProdQty());

			purchaseProduct.setResvCol01(reqProduct.getResvCol01());
			purchaseProduct.setResvCol02(reqProduct.getResvCol02());
			purchaseProduct.setResvCol03(reqProduct.getResvCol03());
			purchaseProduct.setResvCol04(reqProduct.getResvCol04());
			purchaseProduct.setResvCol05(reqProduct.getResvCol05());

			// 요청 시 받은 상품 정보 세팅
			/* IAP */
			if (purchaseOrderInfo.isIap()) {
				purchaseProduct.setTid(reqProduct.getTid()); // 부분유료화 개발사 구매Key
				purchaseProduct.setTxId(reqProduct.getTxId()); // 부분유료화 전자영수증 번호
				purchaseProduct.setPartChrgVer(reqProduct.getPartChrgVer()); // 부분_유료_버전
				purchaseProduct.setPartChrgProdNm(StringUtils.isBlank(reqProduct.getPartChrgProdNm()) ? purchaseProduct
						.getProdNm() : reqProduct.getPartChrgProdNm()); // 부분_유료_상품_명

				// IAP상품 정보 조회
				IapProductInfoRes iapInfo = this.purchaseDisplayRepository.searchIapProductInfo(reqProduct.getProdId(),
						purchaseOrderInfo.getTenantId());
				if (iapInfo == null) {
					throw new StorePlatformException("SAC_PUR_5101", reqProduct.getProdId());
				}

				// IAP 상품속성에 따른 이용기간/자동결제 정보 세팅
				this.buildIapUsePeriodInfo(purchaseProduct, iapInfo);

				purchaseProduct.setParentProdId(iapInfo.getParentProdId()); // 부모 상품ID
				purchaseProduct.setContentsType(iapInfo.getProdKind()); // 상품 유형 (컨텐츠_타입)

				purchaseProduct.setS2sAutoPrchsYn(iapInfo.getS2sMonthlyFreepassYn());
				purchaseProduct.setIapPostbackUrl(iapInfo.getPostbackUrl());
				purchaseProduct.setIapProdKind(iapInfo.getProdKind());
				purchaseProduct.setIapProdCase(iapInfo.getProdCase());
				purchaseProduct.setIapUsePeriod(iapInfo.getUsePeriod());

				if (StringUtils.equals(iapInfo.getProdCase(), "PB0001")
						&& StringUtils.equals(iapInfo.getProdKind(), "PK0002")) { // 건당상품 & 소멸성
					purchaseOrderInfo.setPossibleDuplication(true); // 중복 구매 가능 여부
				}
				purchaseOrderInfo.setTenantProdGrpCd(purchaseOrderInfo.getTenantProdGrpCd().replaceAll("DP00",
						iapInfo.getMenuId().substring(0, 4))); // IAP 테넌트 상품 분류 코드 세팅

				// 정식판 전환 상품 조회
				if (StringUtils.equals(iapInfo.getHasFullProdYn(), PurchaseConstants.USE_Y)
						&& StringUtils.isNotBlank(iapInfo.getFullProdId())) {
					purchaseOrderInfo.setExistCommercialIap(true); // IAP 정식판 전환상품 존재 여부

					List<String> fullProdIdList = new ArrayList<String>();
					fullProdIdList.add(iapInfo.getFullProdId());
					Map<String, PurchaseProduct> fullProductMap = this.purchaseDisplayRepository
							.searchPurchaseProductList(tenantId, langCd, useDeviceModelCd, fullProdIdList, false);
					if (fullProductMap == null || fullProductMap.size() < 1) {
						throw new StorePlatformException("SAC_PUR_5101", iapInfo.getFullProdId());
					}

					PurchaseProduct fullProd = fullProductMap.get(iapInfo.getFullProdId());
					fullProd.setFullProd(true);
					fullProd.setProdQty(1);
					purchaseProduct.setFullIapProductInfo(fullProd);

				}

			} else if (purchaseOrderInfo.isRingbell()) {
				/* Ring & Bell */
				purchaseProduct.setRnBillCd(reqProduct.getRnPid()); // RN 상품 ID
				purchaseProduct.setInfoUseFee(reqProduct.getInfoUseFee()); // 정보_이용_요금 (ISU_AMT_ADD)
				purchaseProduct.setCid(reqProduct.getCid()); // 컨텐츠 ID (SONG ID)
				purchaseProduct.setContentsClsf(reqProduct.getContentsClsf()); // 컨텐츠 구분
				purchaseProduct.setTimbreClsf(reqProduct.getTimbreClsf()); // 음질_구분
				purchaseProduct.setTimbreSctn(reqProduct.getTimbreSctn()); // 음질_구간
			}

			// 비과금 구매요청 경우, 이용종료일시 세팅
			if (purchaseOrderInfo.isFreeChargeReq()) {
				purchaseProduct.setUseExprDt(reqProduct.getUseExprDt());
			}

			// 쇼핑특가 쿠폰 정보 저장
			if (StringUtils.isNotBlank(purchaseProduct.getSpecialSaleCouponId())) {
				purchaseOrderInfo.setSpecialCouponId(purchaseProduct.getSpecialSaleCouponId());
				purchaseOrderInfo.setSpecialCouponAmt((purchaseProduct.getProdAmt().doubleValue() - purchaseProduct
						.getSpecialSaleAmt().doubleValue()) * purchaseProduct.getProdQty());

				purchaseProduct.setSpecialCouponAmt(purchaseProduct.getProdAmt().doubleValue()
						- purchaseProduct.getSpecialSaleAmt().doubleValue());
			}

			purchaseProductList.add(purchaseProduct);
		}

		// 상품 조회 갯수 체크
		if (purchaseOrderInfo.getPurchaseProductList().size() < 1) {
			throw new StorePlatformException("SAC_PUR_5112"); // 조회된 상품 개수가 정상적이지 않습니다.
		}

		// 결제 총 금액 & 상품 가격 총합 체크 : // 비과금 요청 경우, 결제금액 체크 생략
		if (purchaseOrderInfo.isFreeChargeReq()) {
			purchaseOrderInfo.setRealTotAmt(0.0);

		} else {
			if (CollectionUtils.isNotEmpty(purchaseOrderInfo.getReceiveUserList())) {
				checkTotAmt *= purchaseOrderInfo.getReceiveUserList().size(); // 결제총금액 계산: 수신자 수 만큼 상품 가격 반영
			}

			if (StringUtils.equals(purchaseOrderInfo.getSaleAmtProcType(), PurchaseConstants.SALE_AMT_PROC_TYPE_SERVER) == false
					&& checkTotAmt != purchaseOrderInfo.getCreatePurchaseReq().getTotAmt().doubleValue()) {
				throw new StorePlatformException("SAC_PUR_5106", purchaseOrderInfo.getCreatePurchaseReq().getTotAmt()
						.doubleValue(), checkTotAmt);
			}

			purchaseOrderInfo.setRealTotAmt(checkTotAmt);
		}

		// 소장/대여 상품 정보 조회: VOD/이북 단건, 유료 결제 요청 시
		if (purchaseOrderInfo.getPurchaseProductList().size() == 1 && purchaseOrderInfo.getRealTotAmt() > 0.0
				&& (purchaseOrderInfo.isVod() || purchaseOrderInfo.isEbookcomic())
				&& purchaseOrderInfo.isFlat() == false) {

			purchaseProduct = purchaseOrderInfo.getPurchaseProductList().get(0);

			PossLendProductInfo possLendProductInfo = this.purchaseDisplayRepository.searchPossLendProductInfo(
					tenantId, langCd, purchaseProduct.getProdId(), purchaseProduct.getPossLendClsfCd());
			if (possLendProductInfo != null) {
				purchaseProduct.setPossLendProductInfo(possLendProductInfo);
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * 구매 적합성(&가능여부) 체크: 상품&회원 결합 체크, 기구매체크, 쇼핑쿠폰 발급 가능여부 체크 등.
	 * </pre>
	 * 
	 * @param purchaseOrderInfo
	 *            구매 주문 정보
	 */
	@Override
	public void validatePurchase(PurchaseOrderInfo purchaseOrderInfo) {

		// Biz 쿠폰 경우 이하 체크 Skip
		if (purchaseOrderInfo.isBizShopping()) {
			return;
		}

		// 이용자(구매자/선물 수신자) 목록
		List<PurchaseUserDevice> useUserList = new ArrayList<PurchaseUserDevice>();
		if (purchaseOrderInfo.isGift()) {
			useUserList = purchaseOrderInfo.getReceiveUserList();
		} else {
			useUserList.add(purchaseOrderInfo.getPurchaseUser());
		}

		// 테넌트 별 디바이스(mdn) 기반 상품 여부 조회
		Map<String, Boolean> deviceBasedMap = new HashMap<String, Boolean>();
		for (PurchaseUserDevice useUser : useUserList) {
			if (deviceBasedMap.containsKey(useUser.getTenantId())) {
				continue;
			}

			deviceBasedMap.put(useUser.getTenantId(), this.purchaseOrderPolicyService.isDeviceBasedPurchaseHistory(
					useUser.getTenantId(), purchaseOrderInfo.getTenantProdGrpCd()));
		}

		// 기구매 체크 처리 용 변수
		List<String> existenceProdIdList = new ArrayList<String>();
		List<String> tempExistenceProdIdList = null;

		String removeWhenExistPossLendProdId = null; // 소장/대여 상품 정보 중 구매요청 상품 외 상품은 기구매 시 제외하고 구매 진행

		// CLINK 예외처리용
		boolean bClink = purchaseOrderInfo.isClink();

		for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
			// 연령체크 안함: 모바일 회원은 생년월일 저장X, 생년월일도 * 문자 포함으로 확인불가
			// if (StringUtils.equals(product.getProdGrdCd(), PurchaseConstants.PRODUCT_GRADE_19) && useUser.getAge() <
			// 20) {
			// throw new StorePlatformException("SAC_PUR_5110");
			// }

			// 실명인증 체크
			if (StringUtils.equals(product.getProdGrdCd(), PurchaseConstants.PRODUCT_GRADE_19)
					&& purchaseOrderInfo.getPurchaseUser().isRealName() == false) {
				if (bClink) { // CLINK 예외 처리
					product.setResultCd("4105");
					continue;
				} else {
					// T-Freemium 19금 상품에 대한 실명(성인)인증 없이 처리 : 2014.06.25 반영
					// T-Freemium 은 OR000420, OR000413 2개의 요청경로로 유입됨.
					if ((StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(),
							PurchaseConstants.PRCHS_REQ_PATH_T_FREEMIUM) == false)
							&& (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(),
									PurchaseConstants.PRCHS_REQ_PATH_T_BENEFIT_EVENT) == false)) {
						throw new StorePlatformException("SAC_PUR_4105");
					}
				}
			}

			// 쇼핑 쿠폰발급 가능 여부 확인
			if (purchaseOrderInfo.isShopping()) {
				int publishQty = purchaseOrderInfo.isGift() ? purchaseOrderInfo.getReceiveUserList().size()
						* product.getProdQty() : product.getProdQty();

				this.checkAvailableCouponPublish(purchaseOrderInfo.getPurchaseUser(), product, publishQty,
						purchaseOrderInfo.isGift());
			}

			// ---------------------- 구매자 혹은 수신자 수 만큼 반복 체크

			for (PurchaseUserDevice useUser : useUserList) {

				// (정액권) 베타 상품 체크
				if (CollectionUtils.isNotEmpty(product.getExclusiveFixrateProdIdList())) {

					tempExistenceProdIdList = new ArrayList<String>();
					for (String exclusiveProdId : product.getExclusiveFixrateProdIdList()) {
						tempExistenceProdIdList.add(exclusiveProdId);
					}

					List<ExistenceScRes> checkPurchaseResultList = this.searchExistence(useUser.getTenantId(),
							useUser.getUserKey(),
							deviceBasedMap.get(useUser.getTenantId()) ? useUser.getDeviceKey() : null,
							tempExistenceProdIdList);

					for (ExistenceScRes checkRes : checkPurchaseResultList) {
						if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT)) {
							throw new StorePlatformException("SAC_PUR_6109");
						}
					}
				}

				// 이용 가능한 정액권 기구매 확인 처리 : T프리미엄 요청 경로에 대해서는 정액권 이용 제외
				if (CollectionUtils.isNotEmpty(product.getAvailableFixrateProdIdList())
						&& (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(),
								PurchaseConstants.PRCHS_REQ_PATH_T_FREEMIUM) == false)
						&& (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(),
								PurchaseConstants.PRCHS_REQ_PATH_T_BENEFIT_EVENT) == false)) {

					tempExistenceProdIdList = new ArrayList<String>();
					for (String fixrateProdId : product.getAvailableFixrateProdIdList()) {
						tempExistenceProdIdList.add(fixrateProdId);
					}

					List<ExistenceScRes> checkPurchaseResultList = this.searchExistence(useUser.getTenantId(),
							useUser.getUserKey(),
							deviceBasedMap.get(useUser.getTenantId()) ? useUser.getDeviceKey() : null,
							tempExistenceProdIdList);

					ExistenceScRes useExistenceScRes = null;
					for (ExistenceScRes checkRes : checkPurchaseResultList) {
						if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT) == false) {
							continue;
						}

						if (useExistenceScRes == null) {
							useExistenceScRes = checkRes;

						} else if (checkRes.getPrchsDt().compareTo(useExistenceScRes.getPrchsDt()) > 0) {
							useExistenceScRes = checkRes;
						}
					}

					if (useExistenceScRes != null) {
						// 선물 수신자가 정액권 소유했을 경우 기구매 처리
						if (purchaseOrderInfo.isGift()) {
							throw new StorePlatformException("SAC_PUR_6112");
						}

						// 이북/코믹 경우, 이용 가능한 정액권이 나오면 구매 불가 처리 (구매가 가능한 상황이 나오지 못함: 2014.05. 현재)
						if (StringUtils.startsWith(purchaseOrderInfo.getTenantProdGrpCd(),
								PurchaseConstants.TENANT_PRODUCT_GROUP_EBOOKCOMIC)) {
							throw new StorePlatformException("SAC_PUR_6111");
						}

						// 정액권으로 이용할 에피소드 상품에 적용할 DRM 정보 조회 및 반영
						FreePassInfo freepassInfo = this.purchaseDisplayRepository.searchFreePassDrmInfo(
								useUser.getTenantId(), purchaseOrderInfo.getLangCd(), useExistenceScRes.getProdId(),
								product.getProdId());

						if (freepassInfo != null) {
							if (StringUtils.isBlank(freepassInfo.getUsePeriodUnitCd())
									|| ((StringUtils.equals(freepassInfo.getUsePeriodUnitCd(),
											PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED) == false) && StringUtils
											.isBlank(freepassInfo.getUsePeriod()))) {
								throw new StorePlatformException("SAC_PUR_5116", useExistenceScRes.getProdId(),
										product.getProdId(), freepassInfo.getUsePeriodUnitCd(),
										freepassInfo.getUsePeriod());
							}
							product.setUsePeriodUnitCd(freepassInfo.getUsePeriodUnitCd());
							product.setUsePeriod(StringUtils.equals(freepassInfo.getUsePeriodUnitCd(),
									PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED) ? "0" : freepassInfo
									.getUsePeriod());
							product.setDrmYn(StringUtils.defaultString(freepassInfo.getDrmYn(), PurchaseConstants.USE_N));

							// 다운로드 만료일시의 최대값은 정액권의 이용종료일시
							Date currDateObj = new Date();
							String currDt = new SimpleDateFormat("yyyyMMddHHmmss").format(currDateObj);
							String exprDt = this.purchaseOrderAssistService.calculateUseDate(currDt,
									product.getUsePeriodUnitCd(), product.getUsePeriod());
							if (exprDt.compareTo(useExistenceScRes.getUseExprDt()) > 0) {
								product.setDwldExprDt(useExistenceScRes.getUseExprDt());
							}

							product.setUseFixrateProdId(useExistenceScRes.getProdId()); // 사용할 정액권ID 세팅
							product.setUseFixrateProdClsfCd(freepassInfo.getCmpxProdClsfCd()); // 사용할 정액권 타입

							// 무료구매 처리 데이터 세팅
							purchaseOrderInfo.setRealTotAmt(0.0);
						}
					}
				} // #END 이용 가능한 정액권 기구매 확인 처리
			}

			// 기구매 체크 대상 추가
			if (product.getFullIapProductInfo() != null) { // IAP 정식판 전환 상품 존재 경우, 해당 상품으로 기구매체크 대신
				existenceProdIdList.add(product.getFullIapProductInfo().getProdId());

			} else if (purchaseOrderInfo.isPossibleDuplication() == false) {

				// 소장/대여 상품 기구매 처리
				PossLendProductInfo possLendProductInfo = product.getPossLendProductInfo();

				if (possLendProductInfo == null) {
					existenceProdIdList.add(product.getProdId());

				} else {
					existenceProdIdList.add(possLendProductInfo.getPossProdId());
					existenceProdIdList.add(possLendProductInfo.getLendProdId());

					removeWhenExistPossLendProdId = StringUtils.equals(product.getPossLendClsfCd(),
							PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_POSSESION) ? possLendProductInfo.getLendProdId() : possLendProductInfo
							.getPossProdId();
				}
			}
		}

		boolean bRemovePossLend = false;

		// 기구매 체크
		if (existenceProdIdList.size() > 0) {

			List<ExistenceScRes> checkPurchaseResultList = null;

			for (PurchaseUserDevice useUser : useUserList) {

				checkPurchaseResultList = this.searchExistence(useUser.getTenantId(), useUser.getUserKey(),
						deviceBasedMap.get(useUser.getTenantId()) ? useUser.getDeviceKey() : null, existenceProdIdList);

				for (ExistenceScRes checkRes : checkPurchaseResultList) {
					if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT)) {
						if (StringUtils.equals(checkRes.getProdId(), removeWhenExistPossLendProdId)) {
							bRemovePossLend = true;
							continue;
						}

						if (bClink) { // CLINK 예외 처리
							for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
								if (StringUtils.equals(checkRes.getProdId(), product.getProdId())) {
									product.setResultCd("6101");
									continue;
								}
							}
						} else {
							throw new StorePlatformException("SAC_PUR_6101");
						}
					}
					// TAKTODO:: 예약 상태 경우 해당 구매ID 사용... 복수 구매 시 일부 예약상태일 때 처리 방안?
				}
			}
		}

		if (bRemovePossLend) {
			purchaseOrderInfo.getPurchaseProductList().get(0).setPossLendProductInfo(null);
		}
	}

	/*
	 * 
	 * <pre> IAP상품 속성에 따른 이용기간 정보 세팅. </pre>
	 * 
	 * @param purchaseProduct 구매상품정보
	 * 
	 * @param iapInfo IAP상품속성정보
	 */
	private void buildIapUsePeriodInfo(PurchaseProduct purchaseProduct, IapProductInfoRes iapInfo) {
		String usePeriodUnitCd = null;
		String usePeriod = null;
		String autoPrchsYN = null;
		String autoPrchsLastDt = null;
		String autoPrchsPeriodUnitCd = null;
		int autoPrchsPeriodValue = 0;
		int autoPrchsLastPeriodValue = 0;

		String prodCase = iapInfo.getProdCase();
		String prodKind = iapInfo.getProdKind();

		if (StringUtils.equals(prodCase, "PB0001")) { // 건당상품

			if (StringUtils.equals(prodKind, "PK0001")) { // 영구
				usePeriodUnitCd = PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED;
				usePeriod = "0"; // dummy

			} else if (StringUtils.equals(prodKind, "PK0002")) { // 소멸
				usePeriodUnitCd = null; // 쿼리에서 구매일시로 세팅
				usePeriod = "0"; // dummy
			}

		} else if (StringUtils.equals(prodCase, "PB0002")) { // 기간상품
			if (iapInfo.getUsePeriod() == null || iapInfo.getUsePeriod() <= 0) {
				throw new StorePlatformException("SAC_PUR_5118", iapInfo.getUsePeriod());
			}

			if (StringUtils.equals(prodKind, "PK0003")) { // 일간
				usePeriodUnitCd = PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_DATE;
				usePeriod = String.valueOf(iapInfo.getUsePeriod());

			} else if (StringUtils.equals(prodKind, "PK0004")) { // 주간
				usePeriodUnitCd = PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_DATE;
				usePeriod = String.valueOf(iapInfo.getUsePeriod());

			} else if (StringUtils.equals(prodKind, "PK0005")) { // 월간
				usePeriodUnitCd = PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_DATE;
				usePeriod = String.valueOf(iapInfo.getUsePeriod());
			}

		} else if (StringUtils.equals(prodCase, "PB0005")) { // 정식판전환상품

			if (StringUtils.equals(prodKind, "PK0001")) {
				usePeriodUnitCd = PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED;
				usePeriod = "0"; // dummy
			}

		} else if (StringUtils.equals(prodCase, "PB0006")) { // 자동결제

			if (StringUtils.equals(prodKind, "PK0005")) { // 월별자동결제 : 한달 기준 TAKCHECK

				if (iapInfo.getUsePeriod() == null || iapInfo.getUsePeriod() <= 0) {
					throw new StorePlatformException("SAC_PUR_5118", iapInfo.getUsePeriod());
				}

				usePeriodUnitCd = PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_MONTH;
				usePeriod = "1";

				autoPrchsYN = "Y";
				autoPrchsPeriodUnitCd = PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_MONTH;
				autoPrchsPeriodValue = 1;

				autoPrchsLastPeriodValue = iapInfo.getUsePeriod();
			}

		} else {
			throw new StorePlatformException("SAC_PUR_5117", prodCase, prodKind);
		}

		purchaseProduct.setUsePeriodUnitCd(usePeriodUnitCd);
		purchaseProduct.setUsePeriod(usePeriod);
		purchaseProduct.setAutoPrchsYN(autoPrchsYN);
		purchaseProduct.setAutoPrchsPeriodUnitCd(autoPrchsPeriodUnitCd);
		purchaseProduct.setAutoPrchsPeriodValue(autoPrchsPeriodValue);
		purchaseProduct.setAutoPrchsLastDt(autoPrchsLastDt);
		purchaseProduct.setAutoPrchsLastPeriodValue(autoPrchsLastPeriodValue);
	}

	/*
	 * <pre> 쇼핑 쿠폰 발급 가능여부 확인: 결제자 MDN 기준 </pre>
	 * 
	 * @param purchaseUser 결제자 회원 정보
	 * 
	 * @param product 구매 상품 정보
	 * 
	 * @param publishQty 총 구매 수량
	 * 
	 * @param bGift 선물여부
	 */
	private void checkAvailableCouponPublish(PurchaseUserDevice purchaseUser, PurchaseProduct product, int publishQty,
			boolean bGift) {

		// 특가상품 구매가능 건수 체크
		if (StringUtils.isNotBlank(product.getSpecialSaleCouponId())) {
			SearchShoppingSpecialCountScReq specialReq = new SearchShoppingSpecialCountScReq();
			specialReq.setTenantId(purchaseUser.getTenantId());
			specialReq.setUserKey(purchaseUser.getUserKey());
			specialReq.setDeviceKey(purchaseUser.getDeviceKey());
			specialReq.setSpecialCouponId(product.getSpecialSaleCouponId());
			specialReq.setSpecialCouponAmt(product.getProdAmt().doubleValue()
					- product.getSpecialSaleAmt().doubleValue());

			SearchShoppingSpecialCountScRes specialRes = this.purchaseOrderSearchSCI
					.searchShoppingSpecialCount(specialReq);

			if (specialRes.getDayCount() + publishQty > product.getSpecialSaleDayLimit()) {
				throw new StorePlatformException("SAC_PUR_6104");
			}
			if (specialRes.getDayUserCount() + publishQty > product.getSpecialSaleDayLimitPerson()) {
				throw new StorePlatformException("SAC_PUR_6106");
			}
			if (specialRes.getMonthCount() + publishQty > product.getSpecialSaleMonthLimit()) {
				throw new StorePlatformException("SAC_PUR_6105");
			}
			if (specialRes.getMonthUserCount() + publishQty > product.getSpecialSaleMonthLimitPerson()) {
				throw new StorePlatformException("SAC_PUR_6107");
			}
		}

		// CMS 발급 가능 여부 확인
		CouponPublishAvailableSacParam shoppingReq = new CouponPublishAvailableSacParam();
		shoppingReq.setCouponCode(product.getCouponCode());
		shoppingReq.setItemCode(product.getItemCode());
		shoppingReq.setItemCount(publishQty);
		shoppingReq.setMdn(purchaseUser.getDeviceId());
		shoppingReq.setGiftFlag(bGift ? "Y" : "N");

		this.logger.info("PRCHS,SAC,ORDER,VALID,SHOPPING,REQ,{},",
				ReflectionToStringBuilder.toString(shoppingReq, ToStringStyle.SHORT_PREFIX_STYLE));
		try {
			// 정상 응답이 아니면 Exception 발생됨
			this.shoppingRepository.getCouponPublishAvailable(shoppingReq);
		} catch (StorePlatformException e) {
			this.logger.info("PRCHS,SAC,ORDER,VALID,SHOPPING,ERROR,{},{},", e.getCode(), e.getMessage());
			throw e;
		}
	}

	/*
	 * 
	 * <pre> 기구매 체크. </pre>
	 * 
	 * @param tenantId 테넌트 ID
	 * 
	 * @param userKey 내부 회원 NO
	 * 
	 * @param deviceKey 내부 디바이스 ID
	 * 
	 * @param prodIdList 기구매 체크 대상 상품ID 목록
	 * 
	 * @return 기구매 체크 결과
	 */
	private List<ExistenceScRes> searchExistence(String tenantId, String userKey, String deviceKey,
			List<String> prodIdList) {

		List<ExistenceItemSc> existenceItemScList = new ArrayList<ExistenceItemSc>();
		ExistenceItemSc existenceItemSc = null;
		for (String prodId : prodIdList) {
			existenceItemSc = new ExistenceItemSc();
			existenceItemSc.setProdId(prodId);
			existenceItemScList.add(existenceItemSc);
		}

		ExistenceScReq existenceScReq = new ExistenceScReq();
		existenceScReq.setTenantId(tenantId);
		existenceScReq.setUserKey(userKey);
		if (deviceKey != null) {
			existenceScReq.setCheckValue(true);
			existenceScReq.setDeviceKey(deviceKey); // 디바이스(mdn) 기반
		}
		existenceScReq.setProductList(existenceItemScList);

		return this.existenceSCI.searchExistenceList(existenceScReq);
	}
}
