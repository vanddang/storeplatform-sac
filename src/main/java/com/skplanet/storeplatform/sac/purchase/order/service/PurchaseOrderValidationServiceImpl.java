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

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.purchase.client.history.sci.ExistenceSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.purchase.client.order.sci.PurchaseOrderSearchSCI;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScReq;
import com.skplanet.storeplatform.purchase.client.order.vo.SearchShoppingSpecialCountScRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePass;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.IapProductInfoRes;
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
import com.skplanet.storeplatform.sac.purchase.shopping.vo.CouponPublishAvailableSacV2Param;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
	private MessageSourceAccessor messageSourceAccessor;
	@Autowired
	@Qualifier("scPurchase")
	private MessageSourceAccessor scMessageSourceAccessor;

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

		// TAKTODO:: 링&벨 필수 항목 체크 확인 처리
		// if( StringUtils.startsWith(req.getTenantProdGrpCd(), PurchaseCDConstants.TENANT_PRODUCT_GROUP_RINGBELL)) {
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
			// 통합뮤직 : MP3, 컬러링&벨소리
			if (StringUtils.equals(reqMenuId, "DP16") == false && StringUtils.equals(reqMenuId, "DP02") == false
					&& StringUtils.equals(reqMenuId, "DP05") == false) {
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
			throw new StorePlatformException("SAC_PUR_4101", purchaseOrderInfo.getTenantId(),
					purchaseOrderInfo.getUserKey(), purchaseOrderInfo.getDeviceKey());
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
						throw new StorePlatformException("SAC_PUR_4103", receiver.getTenantId(), receiver.getUserKey(),
								receiver.getDeviceKey());
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

		// 전시 연동 & 연동 결과 validation check
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

			purchaseProduct.setTenantProdGrpCd(purchaseOrderInfo.getTenantProdGrpCd());

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

				final List<String> tLogProdIdList = new ArrayList<String>();
				tLogProdIdList.add(purchaseProduct.getProdId());
				final List<String> aidList = new ArrayList<String>();
				aidList.add(purchaseProduct.getAid());
				final String tid = reqProdList.get(0).getTid();
				final String tx_id = reqProdList.get(0).getTxId();
				final List<Long> prodPriceList = new ArrayList<Long>();
				final String mbr_id = purchaseOrderInfo.getPurchaseUser().getUserId();
				final String device_id = purchaseOrderInfo.getPurchaseUser().getDeviceId();
				final String search_price_url = purchaseProduct.getSearchPriceUrl();

				StorePlatformException checkException = null;

				double price = 0.0;
				try {
					String reqTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

					price = this.purchaseIapRepository.searchIapS2SPrice(purchaseProduct.getSearchPriceUrl(), reqTime,
							purchaseProduct.getAid(), purchaseProduct.getProdId(), tid);

					prodPriceList.add((long) price);

				} catch (StorePlatformException e) {
					checkException = e;
					throw checkException;
				} catch (Exception e) {
					checkException = new StorePlatformException("SAC_PUR_7222", e);
					throw checkException;
				} finally {
					// S2S 상품가격 조회 결과 로깅
					ErrorInfo errorInfo = (checkException != null ? checkException.getErrorInfo() : null);

					String msg = "";
					if (errorInfo != null) {
						if (StringUtils.isNotBlank(errorInfo.getMessage())) {
							msg = errorInfo.getMessage();
						} else {
							try {
								if (StringUtils.startsWith(errorInfo.getCode(), "SC_")) {
									msg = this.scMessageSourceAccessor.getMessage(errorInfo.getCode());
								} else {
									msg = this.messageSourceAccessor.getMessage(errorInfo.getCode());
								}
							} catch (NoSuchMessageException e) {
								msg = "";
							}
						}
					}

					final String result_code = (errorInfo != null ? errorInfo.getCode() : PurchaseConstants.TLOG_RESULT_CODE_SUCCESS);
					final String result_message = msg;
					final String exception_log = (errorInfo != null ? (errorInfo.getCause() == null ? "" : errorInfo
							.getCause().toString()) : "");

					new TLogUtil().log(new ShuttleSetter() {
						@Override
						public void customize(TLogSentinelShuttle shuttle) {
							shuttle.log_id(PurchaseConstants.TLOG_ID_PURCHASE_ORDER_S2S_SEARCHPRICE).mbr_id(mbr_id)
									.device_id(device_id).tx_id(tx_id).tid(tid).product_id(tLogProdIdList)
									.app_id(aidList).product_price(prodPriceList).search_price_url(search_price_url)
									.result_code(result_code);
							if (StringUtils.equals(result_code, PurchaseConstants.TLOG_RESULT_CODE_SUCCESS) == false) {
								shuttle.result_message(result_message).exception_log(exception_log);
							}
						}
					});
				}

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

				if (StringUtils.equals(iapInfo.getProdKind(), "PK0002")
						&& (StringUtils.equals(iapInfo.getProdCase(), "PB0001") || StringUtils.equals(
								iapInfo.getProdCase(), "PB0003"))) { // 건당상품 & 소멸성
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
				purchaseProduct.setDwldExprDt(reqProduct.getDwldExprDt());
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

		// 상품 금액 마이너스 체크
		if (checkTotAmt < 0) {
			throw new StorePlatformException("SAC_PUR_5106", checkTotAmt, checkTotAmt); // 구매요청 금액이 정상적이지 않습니다.
		}

		// 결제 총 금액 & 상품 가격 총합 체크 : // 비과금 요청 경우, 결제금액 체크 생략
		if (purchaseOrderInfo.isFreeChargeReq()) {
			purchaseOrderInfo.setRealTotAmt(0.0);

		} else {
			if (CollectionUtils.isNotEmpty(purchaseOrderInfo.getReceiveUserList())) {
				checkTotAmt *= purchaseOrderInfo.getReceiveUserList().size(); // 결제총금액 계산: 수신자 수 만큼 상품 가격 반영
			}

			// if (StringUtils.equals(purchaseOrderInfo.getSaleAmtProcType(),
			// PurchaseCDConstants.SALE_AMT_PROC_TYPE_SERVER) == false
			// && checkTotAmt != purchaseOrderInfo.getCreatePurchaseReq().getTotAmt().doubleValue()) {
			// throw new StorePlatformException("SAC_PUR_5106", purchaseOrderInfo.getCreatePurchaseReq().getTotAmt()
			// .doubleValue(), checkTotAmt);
			// }
			// 2015.07.23 sonarQube 수정
			if (StringUtils.equals(purchaseOrderInfo.getSaleAmtProcType(), PurchaseConstants.SALE_AMT_PROC_TYPE_SERVER) == false
					&& Double.compare(checkTotAmt, purchaseOrderInfo.getCreatePurchaseReq().getTotAmt().doubleValue()) != 0) {
				throw new StorePlatformException("SAC_PUR_5106", purchaseOrderInfo.getCreatePurchaseReq().getTotAmt()
						.doubleValue(), checkTotAmt);
			}

			purchaseOrderInfo.setRealTotAmt(checkTotAmt);
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
		Set<String> existenceProdIdList = new LinkedHashSet<String>();

		// CLINK 예외처리용
		boolean bClink = purchaseOrderInfo.isClink();

		// 연령체크 용
		int purchaserAge = purchaseOrderInfo.getPurchaseUser().getAge();
		boolean bPurchaserAgeLimited = false;
		boolean bReceiverAgeLimited = false;
		boolean bPurchaserAgeNotExist = false;
		boolean bReceiverAgeNotExist = false;
		boolean bPurchaserNotRealName = false;
		// boolean bReceiverNotRealName = false;

		for (PurchaseProduct product : purchaseOrderInfo.getPurchaseProductList()) {
			// 연령체크 안함: 모바일 회원은 생년월일 저장X, 생년월일도 * 문자 포함으로 확인불가
			// if (StringUtils.equals(product.getProdGrdCd(), PurchaseCDConstants.PRODUCT_GRADE_19) && useUser.getAge() <
			// 20) {
			// throw new StorePlatformException("SAC_PUR_5110");
			// }

			// 연령체크
			bPurchaserAgeLimited = false;
			bReceiverAgeLimited = false;
			bPurchaserAgeNotExist = false;
			bReceiverAgeNotExist = false;
			bPurchaserNotRealName = false;
			// bReceiverNotRealName = false;

			if (StringUtils.equals(product.getProdGrdCd(), PurchaseConstants.PRODUCT_GRADE_12)) {
				if (purchaserAge > 0 && purchaserAge < 12) {
					bPurchaserAgeLimited = true;
				}

				if (purchaseOrderInfo.isGift()) {
					if (bPurchaserAgeLimited == false) {
						for (PurchaseUserDevice receiver : purchaseOrderInfo.getReceiveUserList()) {
							if (receiver.getAge() > 0 && receiver.getAge() < 12) {
								bReceiverAgeLimited = true;
								break;
							}
						}
					}
				}

			} else if (StringUtils.equals(product.getProdGrdCd(), PurchaseConstants.PRODUCT_GRADE_15)) {
				if (purchaserAge > 0 && purchaserAge < 15) {
					bPurchaserAgeLimited = true;
				}

				if (purchaseOrderInfo.isGift()) {
					if (bPurchaserAgeLimited == false) {
						for (PurchaseUserDevice receiver : purchaseOrderInfo.getReceiveUserList()) {
							if (receiver.getAge() > 0 && receiver.getAge() < 15) {
								bReceiverAgeLimited = true;
								break;
							}
						}
					}
				}

			} else if (StringUtils.equals(product.getProdGrdCd(), PurchaseConstants.PRODUCT_GRADE_19)) {
				int allowAge = product.getAgeAllowedFrom();

				if (purchaseOrderInfo.getPurchaseUser().isRealName() == false) {
					bPurchaserNotRealName = true;
				} else if (purchaserAge <= 0) {
					bPurchaserAgeNotExist = true;
				} else if (purchaserAge < allowAge) {
					bPurchaserAgeLimited = true;
				}

				if (purchaseOrderInfo.isGift()) {
					if (bPurchaserNotRealName == false && bPurchaserAgeNotExist == false
							&& bPurchaserAgeLimited == false) {
						for (PurchaseUserDevice receiver : purchaseOrderInfo.getReceiveUserList()) {
							// if (receiver.isRealName() == false) { //(2015.06.01 수신자의 실명 인증 여부 체크하지 않음)
							// bReceiverNotRealName = true;
							// break;
							// } else
							if (receiver.getAge() <= 0) {
								bReceiverAgeNotExist = true;
								break;
							} else if (receiver.getAge() < allowAge) {
								bReceiverAgeLimited = true;
								break;
							}
						}
					}
				}
			}

			// if (bClink) { // CLINK 예외 처리
			// product.setResultCd("4105");
			// continue;
			// } else {
			// // T-Freemium 19금 상품에 대한 실명(성인)인증 없이 처리 : 2014.06.25 반영
			// // T-Freemium 은 OR000420, OR000413 2개의 요청경로로 유입됨.
			// if ((StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(),
			// PurchaseCDConstants.PRCHS_REQ_PATH_T_FREEMIUM) == false)
			// && (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(),
			// PurchaseCDConstants.PRCHS_REQ_PATH_T_BENEFIT_EVENT) == false)) {
			// throw new StorePlatformException("SAC_PUR_4105");
			// }
			// }

			if (bPurchaserNotRealName) { // 실명 인증 회원이 아닙니다.
				if (bClink) { // CLINK 예외 처리
					product.setResultCd("4105");
					continue;
				} else {
					throw new StorePlatformException("SAC_PUR_4105");
				}
			} else if (bPurchaserAgeNotExist) { // 연령 정보가 없습니다.
				if (bClink) {
					product.setResultCd("4109");
					continue;
				} else {
					throw new StorePlatformException("SAC_PUR_4109");
				}
			} else if (bPurchaserAgeLimited) { // 연령제한으로 이용할 수 없는 상품입니다.
				if (bClink) {
					product.setResultCd("5110");
					continue;
				} else {
					throw new StorePlatformException("SAC_PUR_5110");
				}
			}
			// else if (bReceiverNotRealName) { // 선물 수신자가 실명 인증 회원이 아닙니다. //(2015.06.01 수신자의 실명 인증 여부 체크하지 않음)
			// if (bClink) {
			// product.setResultCd("4108");
			// continue;
			// } else {
			// throw new StorePlatformException("SAC_PUR_4108");
			// }
			// }
			else if (bReceiverAgeNotExist) { // 선물 수신자 연령 정보가 없습니다.
				if (bClink) {
					product.setResultCd("4110");
					continue;
				} else {
					throw new StorePlatformException("SAC_PUR_4110");
				}
			} else if (bReceiverAgeLimited) { // 선물 수신자의 연령제한으로 이용할 수 없는 상품입니다.
				if (bClink) {
					product.setResultCd("5120");
					continue;
				} else {
					throw new StorePlatformException("SAC_PUR_5120");
				}
			}

			// 쇼핑 쿠폰발급 가능 여부 확인
			if (purchaseOrderInfo.isShopping()) {
				int publishQty = purchaseOrderInfo.isGift() ? purchaseOrderInfo.getReceiveUserList().size()
						* product.getProdQty() : product.getProdQty();

				this.checkAvailableCouponPublish(purchaseOrderInfo.getPurchaseUser(), product, publishQty,
						purchaseOrderInfo.isGift(), purchaseOrderInfo.getApiVer());
			}

			// ---------------------- 구매자 혹은 수신자 수 만큼 반복 체크

			for (PurchaseUserDevice useUser : useUserList) {

				// (정액권) 베타 상품 체크
				if (CollectionUtils.isNotEmpty(product.getExclusiveFixrateProdIdList())) {

					Set<String> existenceProdIdSet = new LinkedHashSet<String>();
					for (String exclusiveProdId : product.getExclusiveFixrateProdIdList()) {
						existenceProdIdSet.add(exclusiveProdId);
					}

					List<ExistenceScRes> checkPurchaseResultList = this.searchExistence(useUser.getTenantId(),
							useUser.getUserKey(),
							deviceBasedMap.get(useUser.getTenantId()) ? useUser.getDeviceKey() : null,
							existenceProdIdSet);

					for (ExistenceScRes checkRes : checkPurchaseResultList) {
						if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT)) {
							throw new StorePlatformException("SAC_PUR_6109");
						}
					}
				}

				// 이용 가능한 정액권 기구매 확인 처리 : T프리미엄 요청 경로에 대해서는 정액권 이용 제외
				if (CollectionUtils.isNotEmpty(product.getAvailableFixrateInfoList())
						&& (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(),
								PurchaseConstants.PRCHS_REQ_PATH_T_FREEMIUM) == false)
						&& (StringUtils.equals(purchaseOrderInfo.getPrchsReqPathCd(),
								PurchaseConstants.PRCHS_REQ_PATH_T_BENEFIT_EVENT) == false)) {

					Map<String,String> tempExistenceProdIdList = new LinkedHashMap<String,String>();

					for (FreePass freepass : product.getAvailableFixrateInfoList()) {
						if (StringUtils.equals(PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_ALL,
								freepass.getPossLendClsfCd())) {
							tempExistenceProdIdList.put(freepass.getProdId(), freepass.getCmpxProdClsfCd());
						} else if (StringUtils.equals(PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_POSSESION,
								freepass.getPossLendClsfCd())) {
							if (StringUtils.equals(PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_POSSESION,
									product.getPossLendClsfCd())) {
								tempExistenceProdIdList.put(freepass.getProdId(), freepass.getCmpxProdClsfCd());
							}
						} else if (StringUtils.equals(PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_RENTAL,
								freepass.getPossLendClsfCd())) {
							if (StringUtils.equals(PurchaseConstants.PRODUCT_POSS_RENTAL_TYPE_RENTAL,
									product.getPossLendClsfCd())) {
								tempExistenceProdIdList.put(freepass.getProdId(), freepass.getCmpxProdClsfCd());
							}
						} else {
							throw new StorePlatformException("SAC_PUR_5121", freepass.getPossLendClsfCd());
						}
					}

					if (CollectionUtils.isEmpty(tempExistenceProdIdList.keySet())) {
						continue;
					}

					List<ExistenceScRes> checkPurchaseResultList = this.searchExistence(useUser.getTenantId(),
							useUser.getUserKey(),
							deviceBasedMap.get(useUser.getTenantId()) ? useUser.getDeviceKey() : null,
							tempExistenceProdIdList.keySet());

					ExistenceScRes useExistenceScRes = null;
					for (ExistenceScRes checkRes : checkPurchaseResultList) {
						// 구매 완료된 건만 체크
						if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT) == false) {
							continue;
						}

						// 회차 비교: 정액제 상품의 max회차 값이 없는 경우는 모두 허용
						if (NumberUtils.toInt(product.getChapter(), 0) > NumberUtils
								.toInt(checkRes.getPartChrgProdNm(), Integer.MAX_VALUE)) {
							continue;
						}

						if (StringUtils.equals(tempExistenceProdIdList.get(checkRes.getProdId()),
								PurchaseConstants.FIXRATE_PROD_TYPE_SERIESPASS)) {
							useExistenceScRes = checkRes;
							break; // 시리즈일 경우 즉시 적용
						}

						if (useExistenceScRes == null) {
							useExistenceScRes = checkRes;
						}  else if (checkRes.getPrchsDt().compareTo(useExistenceScRes.getPrchsDt()) > 0) {
							useExistenceScRes = checkRes;
						}
					}

					if (useExistenceScRes != null) {
						// 선물 수신자가 정액권 소유했을 경우 기구매 처리
						if (purchaseOrderInfo.isGift()) {
							throw new StorePlatformException("SAC_PUR_6112");
						}

						// 이북/코믹 경우, 이용 가능한 정액권이 나오면 구매 불가 처리 (구매가 가능한 상황이 나오지 못함: 2014.05. 현재)
						// 2015.05. 정액제 고도화 적용 : 이북/코믹이 시리즈패스 로 흡수 되면서 단품 구매 가능
						// if (StringUtils.startsWith(purchaseOrderInfo.getTenantProdGrpCd(),
						// PurchaseCDConstants.TENANT_PRODUCT_GROUP_EBOOKCOMIC)) {
						// throw new StorePlatformException("SAC_PUR_6111");
						// }

						// 정액제 상품으로 이용할 에피소드 상품에 적용할 DRM/이용기간 정보 조회 및 반영
						if (this.setEpisodeDrmInfo(product, useExistenceScRes, useUser.getTenantId(),
								purchaseOrderInfo.getLangCd())) {
							purchaseOrderInfo.setRealTotAmt(0.0); // 정상적으로 정액제 상품 이용하는 경우: 무료구매 처리 데이터 세팅
						}
					}
				} // #END 이용 가능한 정액권 기구매 확인 처리
			}

			// 기구매 체크 대상 추가
			if (product.getFullIapProductInfo() != null) { // IAP 정식판 전환 상품 존재 경우, 해당 상품으로 기구매체크 대신
				existenceProdIdList.add(product.getFullIapProductInfo().getProdId());
			} else if (purchaseOrderInfo.isPossibleDuplication() == false) {
				existenceProdIdList.add(product.getProdId());
			}
		}

		// 기구매 체크
		if (existenceProdIdList.size() > 0) {

			List<ExistenceScRes> checkPurchaseResultList = null;

			for (PurchaseUserDevice useUser : useUserList) {

				checkPurchaseResultList = this.searchExistence(useUser.getTenantId(), useUser.getUserKey(),
						deviceBasedMap.get(useUser.getTenantId()) ? useUser.getDeviceKey() : null, existenceProdIdList);

				for (ExistenceScRes checkRes : checkPurchaseResultList) {
					if (StringUtils.equals(checkRes.getStatusCd(), PurchaseConstants.PRCHS_STATUS_COMPT)) {
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

		// ------------------------------------------------------------------------------------------------------
		// 구매(결제)차단 여부 체크

		if (purchaseOrderInfo.getRealTotAmt() > 0) {
			if (this.purchaseOrderPolicyService.isBlockPayment(purchaseOrderInfo.getTenantId(), purchaseOrderInfo
					.getPurchaseUser().getDeviceId(), purchaseOrderInfo.getTenantProdGrpCd())) {
				throw new StorePlatformException("SAC_PUR_6103");
			}
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

		if (StringUtils.equals(prodCase, "PB0001") || StringUtils.equals(prodCase, "PB0003")) { // 건당상품 / 건당스트리밍상품

			if (StringUtils.equals(prodKind, "PK0001")) { // 영구
				usePeriodUnitCd = PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED;
				usePeriod = "0"; // dummy

			} else if (StringUtils.equals(prodKind, "PK0002")) { // 소멸
				usePeriodUnitCd = null; // 쿼리에서 구매일시로 세팅
				usePeriod = "0"; // dummy
			}

		} else if (StringUtils.equals(prodCase, "PB0002") || StringUtils.equals(prodCase, "PB0004")) { // 기간상품 /
																									   // 기간스트리밍상품
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
			boolean bGift, int apiVer) {

		// 특가상품 구매가능 건수 체크
		if (StringUtils.isNotBlank(product.getSpecialSaleCouponId())) {

			// 1회 구매 가능 수량 체크: 특가 상품 선물 시에는 생략
			if (bGift == false && publishQty > product.getSpecialSaleOncePrchsLimit()) {
				throw new StorePlatformException("SAC_PUR_6113");
			}

			// 구매 건수 체크
			SearchShoppingSpecialCountScReq specialReq = new SearchShoppingSpecialCountScReq();
			specialReq.setTenantId(purchaseUser.getTenantId());
			specialReq.setUserKey(purchaseUser.getUserKey());
			specialReq.setDeviceKey(purchaseUser.getDeviceKey());
			specialReq.setSpecialCouponId(product.getSpecialSaleCouponId());
			specialReq.setSpecialCouponAmt(product.getProdAmt().doubleValue()
					- product.getSpecialSaleAmt().doubleValue());

			SearchShoppingSpecialCountScRes specialRes = this.purchaseOrderSearchSCI
					.searchShoppingSpecialCount(specialReq);

			if (specialRes.getMonthUserCount() + publishQty > product.getSpecialSaleMonthLimitPerson()) {
				throw new StorePlatformException("SAC_PUR_6107");
			}
			if (specialRes.getMonthCount() + publishQty > product.getSpecialSaleMonthLimit()) {
				throw new StorePlatformException("SAC_PUR_6105");
			}
			if (specialRes.getDayUserCount() + publishQty > product.getSpecialSaleDayLimitPerson()) {
				throw new StorePlatformException("SAC_PUR_6106");
			}
			if (specialRes.getDayCount() + publishQty > product.getSpecialSaleDayLimit()) {
				throw new StorePlatformException("SAC_PUR_6104");
			}
		}

		// CMS 발급 가능 여부 확인: 구매요청 버전 V2 부터는 신규 쿠폰발급요청 규격 이용 (N개, 1:N 선물 지원)
		if (apiVer > 1) {
			CouponPublishAvailableSacV2Param shoppingReq = new CouponPublishAvailableSacV2Param();
			shoppingReq.setCouponCode(product.getCouponCode());
			shoppingReq.setItemCode(product.getItemCode());
			shoppingReq.setItemCount(publishQty);
			shoppingReq.setMdn(purchaseUser.getDeviceId());
			shoppingReq.setGiftFlag(bGift ? "Y" : "N");

			this.logger.info("PRCHS,SAC,ORDER,VALID,SHOPPING,REQ,{},",
					ReflectionToStringBuilder.toString(shoppingReq, ToStringStyle.SHORT_PREFIX_STYLE));

			try {
				// 정상 응답이 아니면 Exception 발생됨
				this.shoppingRepository.getCouponPublishAvailableV2(shoppingReq);
			} catch (StorePlatformException e) {
				this.logger.info("PRCHS,SAC,ORDER,VALID,SHOPPING,ERROR,{},{},", e.getCode(), e.getMessage());

				// 쇼핑 특가 상품 품절 경우, 품절 처리
				if (StringUtils.equals(e.getCode(), PurchaseConstants.COUPON_CMS_RESULT_SOLDOUT)) {
					if (StringUtils.isNotBlank(product.getSpecialSaleCouponId())) {
						this.purchaseDisplayRepository.updateSpecialPriceSoldOut(purchaseUser.getTenantId(),
								product.getProdId());
					}
				}

				throw e;
			}

		} else {
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

				// 쇼핑 특가 상품 품절 경우, 품절 처리
				if (StringUtils.equals(e.getCode(), PurchaseConstants.COUPON_CMS_RESULT_SOLDOUT)) {
					if (StringUtils.isNotBlank(product.getSpecialSaleCouponId())) {
						this.purchaseDisplayRepository.updateSpecialPriceSoldOut(purchaseUser.getTenantId(),
								product.getProdId());
					}
				}

				throw e;
			}
		}
	}

	/*
	 * 정액권으로 이용할 에피소드 상품에 적용할 DRM 정보 조회 및 반영
	 */
	private boolean setEpisodeDrmInfo(PurchaseProduct episodeProduct, ExistenceScRes fixrateExistence, String tenantId,
			String langCd) {
		CmpxProductInfo cmpxProductInfo = this.purchaseDisplayRepository.searchCmpxProductInfo(tenantId, langCd,
				fixrateExistence.getProdId(), episodeProduct.getProdId(), episodeProduct.getChapter());

		if (cmpxProductInfo != null) {
			if (StringUtils.isBlank(cmpxProductInfo.getUsePeriodUnitCd())
					|| ((StringUtils.equals(cmpxProductInfo.getUsePeriodUnitCd(),
							PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED) == false) && (cmpxProductInfo
							.getUsePeriod() == null || cmpxProductInfo.getUsePeriod() == 0))) {
				throw new StorePlatformException("SAC_PUR_5116", fixrateExistence.getProdId(),
						episodeProduct.getProdId(), episodeProduct.getChapter(), cmpxProductInfo.getUsePeriodUnitCd(),
						cmpxProductInfo.getUsePeriod());
			}

			episodeProduct.setUsePeriodUnitCd(cmpxProductInfo.getUsePeriodUnitCd());
			episodeProduct.setUsePeriod(StringUtils.equals(cmpxProductInfo.getUsePeriodUnitCd(),
					PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED) ? "0" : String.valueOf(cmpxProductInfo
					.getUsePeriod()));
			episodeProduct.setDrmYn(StringUtils.defaultString(cmpxProductInfo.getDrmYn(), PurchaseConstants.USE_N));

			// 다운로드 만료일시의 최대값은 정액권의 이용종료일시
			// 2015.05: 이용 만료일시도 동일 처리
			Date currDateObj = new Date();
			String currDt = new SimpleDateFormat("yyyyMMddHHmmss").format(currDateObj);
			String exprDt = this.purchaseOrderAssistService.calculateUseDate(currDt,
					episodeProduct.getUsePeriodUnitCd(), episodeProduct.getUsePeriod());
			if (exprDt.compareTo(fixrateExistence.getUseExprDt()) > 0) {
				episodeProduct.setUseExprDt(fixrateExistence.getUseExprDt());
				episodeProduct.setDwldExprDt(fixrateExistence.getUseExprDt());
			}

			episodeProduct.setUseFixrateProdId(fixrateExistence.getProdId()); // 사용할 정액권 상품ID 세팅
			episodeProduct.setUseFixratePrchsId(fixrateExistence.getPrchsId()); // 사용할 정액권 구매ID 세팅
			episodeProduct.setUseFixrateProdClsfCd(cmpxProductInfo.getCmpxProdClsfCd()); // 사용할 정액권 타입

			return true;

		} else {
			return false;
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
			Set<String> prodIdSet) {

		List<ExistenceItemSc> existenceItemScList = new ArrayList<ExistenceItemSc>();
		ExistenceItemSc existenceItemSc = null;
		for (String prodId : prodIdSet) {
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
