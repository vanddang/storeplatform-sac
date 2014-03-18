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

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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
		this.LOGGER.debug("HistoryListSacRes : {}", request);

		// SC request/response VO
		HistoryListScReq scRequest = new HistoryListScReq();
		HistoryListScRes scResponse = new HistoryListScRes();

		// SAC Response VO
		HistoryListSacRes response = new HistoryListSacRes();
		List<HistorySac> sacHistoryList = new ArrayList<HistorySac>();
		HistorySac historySac = new HistorySac();

		List<String> prodIdList = new ArrayList<String>();
		List<String> deviceList = new ArrayList<String>();
		List<String> sendDeviceList = new ArrayList<String>();

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
		List<PurchaseTenantPolicy> purchaseTenantPolicyList = this.purchaseTenantPolicyService
				.searchPurchaseTenantPolicyList(request.getTenantId(), request.getTenantProdGrpCd(),
						PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST, true);

		for (PurchaseTenantPolicy obj : purchaseTenantPolicyList) {
			mdnCategoryList.add(obj.getTenantProdGrpCd());
		}
		scRequest.setMdnCategoryList(mdnCategoryList);
		/*************************************************
		 * SC Request Setting End
		 *************************************************/

		/**
		 * Purchase SC Call
		 */
		scResponse = this.historySci.searchHistoryList(scRequest);

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
			historySac.setVer(obj.getVer());
			historySac.setSubNm(obj.getSubNm());
			historySac.setRnPid(obj.getRnPid());
			historySac.setIsuAmtAdd(obj.getIsuAmtAdd());
			historySac.setCid(obj.getCid());
			historySac.setContentsCls(obj.getContentsCls());
			historySac.setContentsType(obj.getContentsType());
			historySac.setPrchsType(obj.getPrchsType());
			historySac.setSundCls(obj.getSundCls());
			historySac.setSundSec(obj.getSundSec());
			historySac.setMenuId(obj.getMenuId());
			historySac.setDpCatSubNo(obj.getDpCatSubNo());

			sacHistoryList.add(historySac);

			// 상품정보 조회를 위한 상품ID 셋팅
			if (!StringUtils.isEmpty(historySac.getProdId())) {
				prodIdList.add(historySac.getProdId());
			}

			// DEVICE INFO 조회를 위한 deviceKey 셋팅
			if (!StringUtils.isEmpty(historySac.getUseDeviceKey())) {
				deviceList.add(historySac.getUseDeviceKey());
			}

			// DEVICE INFO 조회를 위한 deviceKey 셋팅
			if (!StringUtils.isEmpty(historySac.getSendDeviceKey())) {
				sendDeviceList.add(historySac.getSendDeviceKey());
			}

		}
		/*************************************************
		 * SC -> SAC Response Setting Start
		 *************************************************/

		/*************************************************
		 * productInfo Mapping Start - SAC내부호출일 경우에는 상품정보를 조회하지 않는다.
		 **************************************************/
		if (!PurchaseConstants.USE_Y.equals(request.getInternalYn())) {
			if (prodIdList.size() > 0) {

				ProductInfoSacReq productInfoSacReq = new ProductInfoSacReq();
				ProductInfoSacRes productInfoSacRes = new ProductInfoSacRes();

				productInfoSacReq.setTenantId(request.getTenantId());
				productInfoSacReq.setDeviceModelNo(request.getModel());
				productInfoSacReq.setLang(request.getLangCd());
				productInfoSacReq.setList(prodIdList);

				this.LOGGER.debug("### productInfoSacReq  : {}" + productInfoSacReq.toString());

				productInfoSacRes = this.productInfoSCI.getProductList(productInfoSacReq);

				if (productInfoSacRes != null) {
					HashMap<String, Object> prodMap = new HashMap<String, Object>();
					for (HistorySac obj : sacHistoryList) {
						for (ProductInfo info : productInfoSacRes.getProductList()) {
							if (obj.getProdId().equals(info.getProdId())) {
								prodMap = new HashMap<String, Object>();
								prodMap.put("productMap", info);
								obj.setProductInfo(prodMap);
								break;
							}
						}
					}
				}
			}
		}
		/*************************************************
		 * productInfo Mapping End
		 **************************************************/

		/*************************************************
		 * device Info Mapping Start
		 **************************************************/
		try {
			SearchUserDeviceSacReq searchUserDeviceSacReq = new SearchUserDeviceSacReq();
			SearchUserDeviceSacRes searchUserDeviceSacRes = new SearchUserDeviceSacRes();

			this.LOGGER.debug("### searchUserDeviceSacReq  : {}" + searchUserDeviceSacReq.toString());

			if (deviceList.size() > 0) {
				// member request parameter set
				searchUserDeviceSacReq.setDeviceKeyList(deviceList);
				// member InternalSCI Call
				searchUserDeviceSacRes = this.searchUserSCI.searchUserByDeviceKey(searchUserDeviceSacReq);
			}

			Map<String, UserDeviceInfoSac> deviceMap = searchUserDeviceSacRes.getUserDeviceInfo();

			if (sendDeviceList.size() > 0) {
				searchUserDeviceSacReq = new SearchUserDeviceSacReq();
				searchUserDeviceSacReq.setDeviceKeyList(sendDeviceList);
				// member InternalSCI Call
				searchUserDeviceSacRes = this.searchUserSCI.searchUserByDeviceKey(searchUserDeviceSacReq);
			}

			Map<String, UserDeviceInfoSac> sendDeviceMap = searchUserDeviceSacRes.getUserDeviceInfo();

			UserDeviceInfoSac deviceResult;
			UserDeviceInfoSac sendDeviceResult;

			// member response set
			for (HistorySac obj : sacHistoryList) {

				deviceResult = new UserDeviceInfoSac();
				deviceResult = deviceMap.get(obj.getUseDeviceKey());

				sendDeviceResult = new UserDeviceInfoSac();
				sendDeviceResult = sendDeviceMap.get(obj.getSendDeviceKey());

				if (deviceResult != null) {
					obj.setUseDeviceId(deviceResult.getDeviceId());
				}

				if (sendDeviceResult != null) {
					obj.setSendDeviceId(sendDeviceResult.getDeviceId());
				}
			}
		} catch (Exception e) {

		}

		/*************************************************
		 * device Info Mapping End
		 **************************************************/

		response.setHistoryList(sacHistoryList);
		response.setTotalCnt(scResponse.getTotalCnt());

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
		// logger.debug("list : {}", historyListReq);

		// SC request/response VO
		HistoryCountScReq scRequest = new HistoryCountScReq();
		HistoryCountScRes scResponse = new HistoryCountScRes();

		// SAC Response VO
		HistoryCountSacRes response = new HistoryCountSacRes();
		List<ProductCountSac> sacProdList = new ArrayList<ProductCountSac>();
		ProductCountSac productCountSac = new ProductCountSac();

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

		/*************************************************
		 * SC Request Setting End
		 *************************************************/

		// SC Call
		scResponse = this.historySci.searchHistoryCount(scRequest);

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

		// logger.debug("list : {}", historyListRes);
		return response;
	}
}
