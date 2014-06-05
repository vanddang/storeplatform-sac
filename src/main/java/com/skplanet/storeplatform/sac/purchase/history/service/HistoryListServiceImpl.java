/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.sci.HistorySCI;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryCountScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryCountScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.HistoryListScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.HistorySc;
import com.skplanet.storeplatform.purchase.client.history.vo.ProductCountSc;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserDeviceSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserDeviceInfoSac;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryCountSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistorySac;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.ProductCountSac;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.ProductListSac;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PurchaseTenantPolicy;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 구매내역 Implements
 * 
 * Updated on : 2014-01-10 Updated by : 양주원, 엔텔스.
 */
@Service
public class HistoryListServiceImpl implements HistoryListService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HistorySCI historySci;

	@Autowired
	private PurchaseTenantPolicyService purchaseTenantPolicyService;

	@Autowired
	private ProductInfoSCI productInfoSCI;

	@Autowired
	private SearchUserSCI searchUserSCI;

	/**
	 * 구매내역 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryListSacRes
	 */
	@Override
	public HistoryListSacRes searchHistoryList(HistoryListSacReq request) {
		this.logger.info("##### [SAC History CallTime] HistoryListServiceImpl.searchHistoryList START");
		long start = System.currentTimeMillis();
		this.logger.debug("HistoryListSacRes : {}", request);
		this.logger.info("HistoryListSac Request Param : {}", request);

		// SC request/response VO
		HistoryListScReq scRequest = new HistoryListScReq();
		HistoryListScRes scResponse = null;

		// SAC Response VO
		HistoryListSacRes response = new HistoryListSacRes();
		List<HistorySac> sacHistoryList = new ArrayList<HistorySac>();
		HistorySac historySac = null;

		List<String> prodIdList = new ArrayList<String>();
		List<String> fixProdIdList = new ArrayList<String>();

		List<SearchUserDeviceSac> deviceList = new ArrayList<SearchUserDeviceSac>();
		SearchUserDeviceSac deviceInfo;
		List<SearchUserDeviceSac> sendDeviceList = new ArrayList<SearchUserDeviceSac>();
		SearchUserDeviceSac sendDeviceInfo;

		List<String> mdnCategoryList = new ArrayList<String>();

		/*************************************************
		 * SC Request Setting Start
		 *************************************************/
		scRequest.setTenantId(request.getTenantId());
		scRequest.setUserKey(request.getUserKey());
		scRequest.setDeviceKey(request.getDeviceKey());
		scRequest.setStartDt(request.getStartDt());
		scRequest.setEndDt(request.getEndDt());
		scRequest.setTenantProdGrpCd(request.getTenantProdGrpCd());

		List<String> prodList = new ArrayList<String>();
		if (request.getProductList() != null && request.getProductList().size() > 0) {
			for (ProductListSac obj : request.getProductList()) {
				if (!StringUtils.isEmpty(obj.getProdId())) {
					prodList.add(obj.getProdId());
				}
			}
		}
		scRequest.setProductList(prodList);
		scRequest.setPrchsProdHaveYn(request.getPrchsProdHaveYn());
		scRequest.setPrchsProdType(request.getPrchsProdType());
		scRequest.setPrchsCaseCd(request.getPrchsCaseCd());
		scRequest.setPrchsReqPathCd(request.getPrchsReqPathCd());
		scRequest.setHidingYn(request.getHidingYn());
		scRequest.setPrchsStatusCd(request.getPrchsStatusCd());

		// 보유상품 조회일 때만 해당값이 조회 조건으로 사용된다.
		if (PurchaseConstants.USE_Y.equals(request.getPrchsProdHaveYn())) {
			scRequest.setUseFixrateProdId(request.getUseFixrateProdId());
		}
		scRequest.setGiftRecvConfYn(request.getGiftRecvConfYn());

		// pageInfo set
		scRequest.getPage().setNo(request.getOffset());
		scRequest.getPage().setRows(request.getCount());

		/**
		 * 구매정책을 조회하여 list에 셋팅
		 */
		long polscStTime = System.currentTimeMillis();

		List<PurchaseTenantPolicy> purchaseTenantPolicyList = this.purchaseTenantPolicyService
				.searchPurchaseTenantPolicyList(request.getTenantId(), request.getTenantProdGrpCd(),
						PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST, true);
		this.logger
				.info("##### [SAC History CallTime] HistorySC purchaseTenantPolicyService.searchPurchaseTenantPolicyList END takes {} ms",
						(System.currentTimeMillis() - polscStTime));

		// Device를 조회 조건으로 넣을지 여부
		String selectDeviceYn = "N";

		for (PurchaseTenantPolicy obj : purchaseTenantPolicyList) {
			mdnCategoryList.add(obj.getTenantProdGrpCd());

			if (!StringUtils.isBlank(request.getTenantProdGrpCd())
					&& obj.getTenantProdGrpCd().equals(request.getTenantProdGrpCd())) {
				selectDeviceYn = "Y"; // 디바이스정책 카테고리 조회일 경우 조회 조건으로 DeviceKey를 넣는다.
			}
		}
		this.logger.info("### Device Policy Category ### selectDeviceYn = " + selectDeviceYn);
		scRequest.setMdnCategoryList(mdnCategoryList);
		scRequest.setSelectDeviceYn(selectDeviceYn);
		/*************************************************
		 * SC Request Setting End
		 *************************************************/

		/**
		 * Purchase SC Call
		 */
		this.logger.debug("##### HistoryList SC Call Start");
		long scStTime = System.currentTimeMillis();
		scResponse = this.historySci.searchHistoryList(scRequest);
		this.logger.info("##### [SAC History CallTime] HistorySC historySci.searchHistoryList END takes {} ms",
				(System.currentTimeMillis() - scStTime));
		this.logger.debug("##### HistoryList SC Call End");

		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/
		for (HistorySc obj : scResponse.getHistoryList()) {

			historySac = new HistorySac();

			// 구매정보 set
			historySac.setTenantId(obj.getTenantId());
			historySac.setPrchsId(obj.getPrchsId());
			historySac.setPrchsDtlId(obj.getPrchsDtlId());
			historySac.setUseTenantId(obj.getUseTenantId());
			historySac.setUseUserKey(obj.getUseUserKey());
			historySac.setUseDeviceKey(obj.getUseDeviceKey());
			historySac.setPrchsDt(obj.getPrchsDt());
			historySac.setTotAmt(obj.getTotAmt());
			historySac.setSendUserKey(obj.getSendUserKey());
			historySac.setSendDeviceKey(obj.getSendDeviceKey());

			historySac.setRecvDt(obj.getRecvDt());
			historySac.setRecvConfPathCd(obj.getRecvConfPathCd());
			historySac.setTenantProdGrpCd(obj.getTenantProdGrpCd());
			historySac.setProdId(obj.getProdId());
			historySac.setProdAmt(obj.getProdAmt());
			historySac.setProdQty(obj.getProdQty());
			historySac.setSpcCpnAmt(obj.getSpcCpnAmt());
			historySac.setStatusCd(obj.getStatusCd());
			historySac.setUseStartDt(obj.getUseStartDt());
			historySac.setUseExprDt(obj.getUseExprDt());
			historySac.setPrchsReqPathCd(obj.getPrchsReqPathCd());
			historySac.setHidingYn(obj.getHidingYn());
			historySac.setCancelReqPathCd(obj.getCancelReqPathCd());
			historySac.setCancelDt(obj.getCancelDt());
			historySac.setPrchsCaseCd(obj.getPrchsCaseCd());
			historySac.setDwldStartDt(obj.getDwldStartDt());
			historySac.setDwldExprDt(obj.getDwldExprDt());
			historySac.setCpnPublishCd(obj.getCpnPublishCd());
			historySac.setCpnDlvUrl(obj.getCpnDlvUrl());
			historySac.setUseFixrateProdId(obj.getUseFixrateProdId());
			historySac.setPrchsProdType(obj.getPrchsProdType());
			historySac.setDrmYn(obj.getDrmYn());
			historySac.setAlarmYn(obj.getAlarmYn());
			historySac.setPermitDeviceYn(obj.getPermitDeviceYn());
			historySac.setCpnAddinfo(obj.getCpnAddinfo()); // 쇼핑쿠폰_추가정보
			historySac.setCpnBizProdSeq(obj.getCpnBizProdSeq()); // 쇼핑쿠폰_biz상품_순번
			historySac.setCpnBizOrderNo(obj.getCpnBizOrderNo()); // 쇼핑쿠폰_biz상품_주문번호

			historySac.setResvCol01(obj.getResvCol01());
			historySac.setResvCol02(obj.getResvCol02());
			historySac.setResvCol03(obj.getResvCol03());
			historySac.setResvCol04(obj.getResvCol04());
			historySac.setResvCol05(obj.getResvCol05());

			// 자동결제 정보 set
			historySac.setPaymentStartDt(obj.getPaymentStartDt());
			historySac.setPaymentEndDt(obj.getPaymentEndDt());
			historySac.setAfterPaymentDt(obj.getAfterPaymentDt());
			historySac.setPrchsTme(obj.getPrchsTme());
			historySac.setAutoPaymentStatusCd(obj.getAutoPaymentStatusCd());
			historySac.setClosedDt(obj.getClosedDt());
			historySac.setClosedReasonCd(obj.getClosedReasonCd());
			historySac.setClosedReqPathCd(obj.getClosedReqPathCd());

			historySac.setCurrencyCd(obj.getCurrencyCd());
			historySac.setTid(obj.getTid());
			historySac.setTxId(obj.getTxId());
			historySac.setParentProdId(obj.getParentProdId());
			historySac.setPartChrgVer(obj.getPartChrgVer());
			historySac.setPartChrgProdNm(obj.getPartChrgProdNm());
			historySac.setRnBillCd(obj.getRnBillCd());
			historySac.setInfoUseFee(obj.getInfoUseFee());
			historySac.setCid(obj.getCid());
			historySac.setContentsClsf(obj.getContentsClsf());
			historySac.setContentsType(obj.getContentsType());
			historySac.setPrchsType(obj.getPrchsType());
			historySac.setTimbreClsf(obj.getTimbreClsf());
			historySac.setTimbreSctn(obj.getTimbreSctn());
			historySac.setMenuId(obj.getMenuId());
			historySac.setGenreClsfCd(obj.getGenreClsfCd());

			sacHistoryList.add(historySac);

			// 상품정보 조회를 위한 상품ID 셋팅
			if (!StringUtils.isBlank(historySac.getProdId())) {
				prodIdList.add(historySac.getProdId());
			}

			if (!StringUtils.isBlank(historySac.getUseFixrateProdId())) {
				fixProdIdList.add(historySac.getUseFixrateProdId());
			}

			// DEVICE INFO 조회를 위한 deviceKey 셋팅
			if (!StringUtils.isEmpty(historySac.getUseUserKey()) && !StringUtils.isEmpty(historySac.getUseDeviceKey())
					&& !PurchaseConstants.NON_MEMBER.equals(historySac.getUseUserKey())) {
				deviceInfo = new SearchUserDeviceSac();
				deviceInfo.setUserKey(historySac.getUseUserKey());
				deviceInfo.setDeviceKey(historySac.getUseDeviceKey());
				deviceList.add(deviceInfo);
			}

			// DEVICE INFO 조회를 위한 deviceKey 셋팅
			if (!StringUtils.isEmpty(historySac.getSendUserKey())
					&& !StringUtils.isEmpty(historySac.getSendDeviceKey())
					&& !PurchaseConstants.NON_MEMBER.equals(historySac.getSendUserKey())) {
				sendDeviceInfo = new SearchUserDeviceSac();
				sendDeviceInfo.setUserKey(historySac.getSendUserKey());
				sendDeviceInfo.setDeviceKey(historySac.getSendDeviceKey());
				sendDeviceList.add(sendDeviceInfo);
			}

		}
		this.logger.info("##### [SAC History CallTime] HistoryListServiceImpl.searchHistoryList SC Result Setting");
		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/

		/*************************************************
		 * productInfo Mapping Start - SAC내부호출일 경우에는 상품정보를 조회하지 않는다.
		 **************************************************/
		this.logger.debug("##### HistoryList ProductInfo Start");
		if (!PurchaseConstants.USE_Y.equals(request.getInternalYn())) {

			ProductInfoSacReq productInfoSacReq = new ProductInfoSacReq();
			ProductInfoSacRes productInfoSacRes = new ProductInfoSacRes();

			ProductInfoSacReq fixProductInfoSacReq = new ProductInfoSacReq();
			ProductInfoSacRes fixProductInfoSacRes = new ProductInfoSacRes();

			if (prodIdList.size() > 0) {

				productInfoSacReq.setTenantId(request.getTenantId());
				productInfoSacReq.setDeviceModelNo(request.getModel());
				productInfoSacReq.setLang(request.getLangCd());
				productInfoSacReq.setList(prodIdList);

				this.logger.debug("### productInfoSacReq  : {}" + productInfoSacReq.toString());

				long prodTime = System.currentTimeMillis();
				productInfoSacRes = this.productInfoSCI.getProductList(productInfoSacReq);
				this.logger.info(
						"##### [SAC History CallTime] LOCAL SCI prod productInfoSCI.getProductList END takes {} ms",
						(System.currentTimeMillis() - prodTime));
			}

			if (fixProdIdList.size() > 0) {

				fixProductInfoSacReq.setTenantId(request.getTenantId());
				fixProductInfoSacReq.setDeviceModelNo(request.getModel());
				fixProductInfoSacReq.setLang(request.getLangCd());
				fixProductInfoSacReq.setList(fixProdIdList);

				long fixprodTime = System.currentTimeMillis();
				fixProductInfoSacRes = this.productInfoSCI.getProductList(fixProductInfoSacReq);
				this.logger.info(
						"##### [SAC History CallTime] LOCAL SCI fix productInfoSCI.getProductList END takes {} ms",
						(System.currentTimeMillis() - fixprodTime));

			}

			if (productInfoSacRes != null) {
				HashMap<String, Object> prodMap = new HashMap<String, Object>();
				for (HistorySac obj : sacHistoryList) {
					for (ProductInfo info : productInfoSacRes.getProductList()) {

						if (obj.getProdId().equals(info.getPartProdId())) {
							prodMap = new HashMap<String, Object>();
							prodMap.put("productMap", info);

							// 구매한 정액권 ID 상품 정보조회
							if (!StringUtils.isBlank(obj.getUseFixrateProdId())) {
								for (ProductInfo fixInfo : fixProductInfoSacRes.getProductList()) {
									if (obj.getUseFixrateProdId().equals(fixInfo.getPartProdId())) {
										prodMap.put("fixProductMap", fixInfo);
										break;
									}
								}
							} else {
								prodMap.put("fixProductMap", null);
							}

							obj.setProductInfo(prodMap);
							break;
						}
					}
				}
			}
			this.logger
					.info("##### [SAC History CallTime] HistoryListServiceImpl.searchHistoryList Product Result Setting");

		}
		this.logger.debug("##### HistoryList ProductInfo End");
		/*************************************************
		 * productInfo Mapping End
		 **************************************************/

		/*************************************************
		 * device Info Mapping Start
		 **************************************************/
		this.logger.debug("##### HistoryList DeviceInfo Start");
		SearchUserDeviceSacReq searchUserDeviceSacReq = new SearchUserDeviceSacReq();
		SearchUserDeviceSacRes searchUserDeviceSacRes = new SearchUserDeviceSacRes();

		Map<String, UserDeviceInfoSac> useDeviceMap = new HashMap<String, UserDeviceInfoSac>();
		Map<String, UserDeviceInfoSac> sendDeviceMap = new HashMap<String, UserDeviceInfoSac>();

		boolean useInfo = true; // 회원정보 조회 안되면 false
		boolean sendInfo = true; // 회원정보 조회 안되면 false

		this.logger.debug("### searchUserDeviceSacReq  : {}" + searchUserDeviceSacReq.toString());

		if (deviceList.size() > 0) {
			// member request parameter set
			searchUserDeviceSacReq.setSearchUserDeviceReqList(deviceList);

			try {
				// member InternalSCI Call
				long uDevicdTime = System.currentTimeMillis();
				searchUserDeviceSacRes = this.searchUserSCI.searchUserByDeviceKey(searchUserDeviceSacReq);
				this.logger.info(
						"##### [SAC History CallTime] LOCAL SCI searchUserSCI.searchUserByDeviceKey END takes {} ms",
						(System.currentTimeMillis() - uDevicdTime));

				useDeviceMap = searchUserDeviceSacRes.getUserDeviceInfo();
			} catch (Exception e) {
				useInfo = false;
				this.logger.info("---------------------------------------------------");
				this.logger.info("------Use Device Info null");
				this.logger.info("---------------------------------------------------");
			}
		}

		if (sendDeviceList.size() > 0) {
			// member request parameter set
			searchUserDeviceSacReq = new SearchUserDeviceSacReq();
			searchUserDeviceSacReq.setSearchUserDeviceReqList(sendDeviceList);

			try {
				// member InternalSCI Call
				long sDevicdTime = System.currentTimeMillis();
				searchUserDeviceSacRes = this.searchUserSCI.searchUserByDeviceKey(searchUserDeviceSacReq);
				this.logger.info(
						"##### [SAC History CallTime] LOCAL SCI searchUserSCI.searchUserByDeviceKey END takes {} ms",
						(sDevicdTime - System.currentTimeMillis()));
				sendDeviceMap = searchUserDeviceSacRes.getUserDeviceInfo();

			} catch (Exception e) {
				sendInfo = false;
				this.logger.info("---------------------------------------------------");
				this.logger.info("------Send Device Info null");
				this.logger.info("---------------------------------------------------");
			}
		}

		UserDeviceInfoSac deviceResult = null;
		UserDeviceInfoSac sendDeviceResult = null;

		// member response set
		for (HistorySac obj : sacHistoryList) {

			if (useInfo) {
				deviceResult = useDeviceMap.get(obj.getUseDeviceKey());

				if (deviceResult != null) {
					obj.setUseDeviceId(deviceResult.getDeviceId());
				}
			}

			if (sendInfo) {
				sendDeviceResult = sendDeviceMap.get(obj.getSendDeviceKey());

				if (sendDeviceResult != null) {
					obj.setSendDeviceId(sendDeviceResult.getDeviceId());
				}
			}
		}
		this.logger.debug("##### HistoryList DeviceInfo End");
		/*************************************************
		 * device Info Mapping End
		 **************************************************/

		response.setHistoryList(sacHistoryList);
		response.setTotalCnt(scResponse.getTotalCnt());

		long end = System.currentTimeMillis();
		this.logger.info("##### [SAC History CallTime] HistoryListServiceImpl.searchHistoryList END takes {} ms",
				(end - start));

		return response;
	}

	/**
	 * 구매건수 조회 기능을 제공한다.
	 * 
	 * @param request
	 *            구매내역요청
	 * @return HistoryCountSacRes
	 */
	@Override
	public HistoryCountSacRes searchHistoryCount(HistoryCountSacReq request) {
		long start = System.currentTimeMillis();
		// logger.debug("list : {}", historyListReq);

		// SC request/response VO
		HistoryCountScReq scRequest = new HistoryCountScReq();
		HistoryCountScRes scResponse = null;

		// SAC Response VO
		HistoryCountSacRes response = new HistoryCountSacRes();
		List<ProductCountSac> sacProdList = new ArrayList<ProductCountSac>();
		ProductCountSac productCountSac = null;

		/*************************************************
		 * SC Request Setting Start
		 *************************************************/
		scRequest.setTenantId(request.getTenantId());
		scRequest.setUserKey(request.getUserKey());
		scRequest.setDeviceKey(request.getDeviceKey());
		scRequest.setStartDt(request.getStartDt());
		scRequest.setEndDt(request.getEndDt());
		scRequest.setPrchsProdType(request.getPrchsProdType());
		scRequest.setPrchsStatusCd(request.getPrchsStatusCd());
		scRequest.setPrchsProdHaveYn(request.getPrchsProdHaveYn());
		scRequest.setPrchsReqPathCd(request.getPrchsReqPathCd());
		scRequest.setPrchsCaseCd(request.getPrchsCaseCd());
		scRequest.setTenantProdGrpCd(request.getTenantProdGrpCd());
		scRequest.setUseFixrateProdId(request.getUseFixrateProdId());

		List<String> prodList = new ArrayList<String>();
		if (request.getProductList() != null && request.getProductList().size() > 0) {
			for (ProductListSac obj : request.getProductList()) {
				if (!StringUtils.isEmpty(obj.getProdId())) {
					prodList.add(obj.getProdId());
				}
			}
		}
		scRequest.setProductList(prodList);
		scRequest.setHidingYn(request.getHidingYn());
		scRequest.setGiftRecvConfYn(request.getGiftRecvConfYn());

		/**
		 * 구매정책을 조회하여 list에 셋팅
		 */
		long polTime = System.currentTimeMillis();
		List<PurchaseTenantPolicy> purchaseTenantPolicyList = this.purchaseTenantPolicyService
				.searchPurchaseTenantPolicyList(request.getTenantId(), request.getTenantProdGrpCd(),
						PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST, true);
		this.logger.info(
				"##### [SAC History Count CallTime] HistoryListServiceImpl.searchHistoryCount Policy Call takes {} ms",
				(System.currentTimeMillis() - polTime));

		// Device를 조회 조건으로 넣을지 여부
		String selectDeviceYn = "N";

		for (PurchaseTenantPolicy obj : purchaseTenantPolicyList) {

			if (!StringUtils.isBlank(request.getTenantProdGrpCd())
					&& obj.getTenantProdGrpCd().equals(request.getTenantProdGrpCd())) {
				selectDeviceYn = "Y"; // 디바이스정책 카테고리 조회일 경우 조회 조건으로 DeviceKey를 넣는다.
			}
		}
		this.logger.info("### Device Policy Category ### selectDeviceYn = " + selectDeviceYn);
		scRequest.setSelectDeviceYn(selectDeviceYn);

		/*************************************************
		 * SC Request Setting End
		 *************************************************/

		// SC Call
		long scTime = System.currentTimeMillis();
		scResponse = this.historySci.searchHistoryCount(scRequest);
		this.logger.info(
				"##### [SAC History Count CallTime] HistoryListServiceImpl.searchHistoryCount SC Call takes {} ms",
				(System.currentTimeMillis() - scTime));

		if (scResponse.getCntList() != null && scResponse.getCntList().size() > 0) {
			for (ProductCountSc obj : scResponse.getCntList()) {
				productCountSac = new ProductCountSac();
				productCountSac.setProdId(obj.getProdId());
				productCountSac.setProdCount(obj.getProdCount());

				sacProdList.add(productCountSac);
			}
		}

		response.setTotalCnt(scResponse.getTotalCnt());
		response.setCntList(sacProdList);

		long end = System.currentTimeMillis();
		this.logger.info(
				"##### [SAC History Count CallTime] HistoryListServiceImpl.searchHistoryCount End takes {} ms",
				(end - start));

		// logger.debug("list : {}", historyListRes);
		return response;
	}
}
