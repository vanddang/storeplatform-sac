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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.purchase.client.common.vo.TenantSalePolicy;
import com.skplanet.storeplatform.purchase.client.history.sci.ExistenceSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScV2Req;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceUserInfoScV2;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceInfoSac;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceListSacResV2;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacReqV2;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacResV2;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceUserInfoSacV2;
import com.skplanet.storeplatform.sac.purchase.common.service.PurchaseTenantPolicyService;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;

/**
 * 기구매 SAC Service 인터페이스 구현체
 * 
 * Updated on : 2013-09-01 Updated by :조용진, 엔텔스.
 */
@Service
public class ExistenceSacServiceImpl implements ExistenceSacService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceSCI existenceSCI;

	@Autowired
	private PurchaseTenantPolicyService purchaseTenantPolicyService;

	/**
	 * 기구매 체크 SAC Service.
	 * 
	 * @param existenceScReq
	 *            요청
	 * @param inputValue
	 *            내외부 사용구분 내부 true 요청정보
	 * @return List<ExistenceScRes>
	 */
	@Override
	public List<ExistenceScRes> searchExistenceList(final ExistenceScReq existenceScReq, final String networkType) {
		ErrorInfo errorInfo = null;
		// 구매상태가 구매완료건만을 넣기 위한 리스트
		List<ExistenceScRes> existenceListScRes = new ArrayList<ExistenceScRes>();
		// 기구매내역 조회함
		final List<ExistenceScRes> resultList = this.existenceSCI.searchExistenceList(existenceScReq);
		// 내부구매처리시 기구매 체크는 inputValue = true

		// TenantProdGrpCd(Device기반 모든정책 조회 )
		List<TenantSalePolicy> purchaseTenantPolicyList = this.purchaseTenantPolicyService.searchTenantSalePolicyList(
				existenceScReq.getTenantId(), "", PurchaseConstants.POLICY_PATTERN_DEVICE_BASED_PRCHSHST, true);

		for (final ExistenceScRes existenceScRes : resultList) {
			try {
				String flag = "";
				this.logger.debug("existenceScRes.getStatusCd() : {}", existenceScRes.getStatusCd());

				if (existenceScRes.getStatusCd() != null
						&& existenceScRes.getStatusCd().equals(PurchaseConstants.PRCHS_STATUS_COMPT)) {

					// TenantProdGrpCd가 null일때는 ID기반으로 체크한다.
					if (existenceScRes.getTenantProdGrpCd() != null) {

						this.logger.info("############### existenceScRes.getTenantProdGrpCd() != null");

						flag = this.checkMdn(existenceScReq, existenceScRes, purchaseTenantPolicyList, flag);
						this.logger.info("리턴 FLAG : {}", flag);
						// flag가 ID 이거나 MDN일 경우에만 기구매셋팅
						if (flag.equals("ID") || flag.equals("MDN")) {
							existenceListScRes.add(existenceScRes);
						}
					} else {

						this.logger.info("############### existenceScRes.getTenantProdGrpCd() == null");

						// TenantProdGrpCd가 null일때는 ID기반으로 체크한다.
						existenceListScRes.add(existenceScRes);
					}
				}

				// TLog
				final List<String> prodIdList = new ArrayList<String>();
				final List<Long> prodAmtList = new ArrayList<Long>();
				prodIdList.add(existenceScRes.getProdId());
				prodAmtList.add((long) existenceScRes.getProdAmt());

				new TLogUtil().logger(LoggerFactory.getLogger("TLOG_SAC_LOGGER")).log(new ShuttleSetter() {

					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id("TL_SAC_PUR_0002").system_id(existenceScReq.getSystemId())
								.insd_device_id(existenceScReq.getDeviceKey())
								.insd_usermbr_no(existenceScReq.getUserKey())
								.purchase_channel(existenceScRes.getPrchsReqPathCd())
								.purchase_inflow_channel(existenceScRes.getPrchsCaseCd()).network_type(networkType)
								.product_id(prodIdList).product_price(prodAmtList).result_code("SUCC");
					}
				});
			} catch (StorePlatformException e) {

				final List<String> prodIdList = new ArrayList<String>();
				final List<Long> prodAmtList = new ArrayList<Long>();
				prodIdList.add(existenceScRes.getProdId());
				prodAmtList.add((long) existenceScRes.getProdAmt());

				errorInfo = e.getErrorInfo();
				final String resultCode = errorInfo.getCode();
				final String resultMessage = errorInfo.getMessage();

				new TLogUtil().logger(LoggerFactory.getLogger("TLOG_SAC_LOGGER")).log(new ShuttleSetter() {

					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id("TL_SAC_PUR_0002").system_id(existenceScReq.getSystemId())
								.insd_device_id(existenceScReq.getDeviceKey())
								.insd_usermbr_no(existenceScReq.getUserKey())
								.purchase_channel(existenceScRes.getPrchsReqPathCd())
								.purchase_inflow_channel(existenceScRes.getPrchsCaseCd()).product_id(prodIdList)
								.product_price(prodAmtList).result_code(resultCode).result_message(resultMessage);
					}
				});

				throw e;
			}
		}
		return existenceListScRes;
	}

	/**
	 * 기구매 체크 SAC Service.
	 * 
	 * @param existenceScReq
	 *            요청
	 * @param inputValue
	 *            내외부 사용구분 내부 true 요청정보
	 * @return List<ExistenceScRes>
	 */
	@Override
	public ExistenceSacResV2 searchExistenceListV2(final ExistenceSacReqV2 existenceSacReqV2, final String networkType) {

		ExistenceScV2Req scReqyest = new ExistenceScV2Req();
		ExistenceUserInfoScV2 scUserItem;

		List<ExistenceUserInfoScV2> scUserList = new ArrayList<ExistenceUserInfoScV2>();

		List<String> scDeviceList;
		List<String> scProdList = new ArrayList<String>();

		/********** SC Request Setting Start **********/
		for (ExistenceUserInfoSacV2 reqUserItem : existenceSacReqV2.getUserList()) {

			scDeviceList = new ArrayList<String>();
			scUserItem = new ExistenceUserInfoScV2();
			scUserItem.setTenantId(reqUserItem.getTenantId());
			scUserItem.setUserKey(reqUserItem.getUserKey());

			for (ExistenceInfoSac existenceInfoSac : reqUserItem.getDeviceList()) {
				scDeviceList.add(existenceInfoSac.getDeviceKey());
			}

			scUserItem.setDeviceList(scDeviceList);
			scUserList.add(scUserItem);
		}

		for (ExistenceInfoSac existenceInfoSac : existenceSacReqV2.getProdList()) {
			scProdList.add(existenceInfoSac.getProdId());
		}

		scReqyest.setUserList(scUserList);
		scReqyest.setProdList(scProdList);
		/********** SC Request Setting End **********/

		// 기구매 목록조회
		List<ExistenceScRes> resultList = this.existenceSCI.searchExistenceListV2(scReqyest);

		ExistenceSacRes prodItem = null;
		List<ExistenceSacRes> prodList = null;
		ExistenceListSacResV2 userItem = null;
		List<ExistenceListSacResV2> userList = new ArrayList<ExistenceListSacResV2>();
		ExistenceSacResV2 response = new ExistenceSacResV2();

		for (ExistenceUserInfoSacV2 reqUserItem : existenceSacReqV2.getUserList()) {

			for (ExistenceInfoSac existenceInfoSac : reqUserItem.getDeviceList()) {

				userItem = new ExistenceListSacResV2();
				prodItem = new ExistenceSacRes();
				prodList = new ArrayList<ExistenceSacRes>();

				final List<String> tLogProdId = new ArrayList<String>();
				final List<Long> tLogProdAmt = new ArrayList<Long>();

				for (final ExistenceScRes item : resultList) {

					if (StringUtil.equals(reqUserItem.getUserKey(), item.getUseInsdUsermbrNo())
							&& StringUtil.equals(existenceInfoSac.getDeviceKey(), item.getUseInsdDeviceId())) {

						try {

							prodItem = new ExistenceSacRes();

							userItem.setUserKey(item.getUseInsdUsermbrNo());
							userItem.setDeviceKey(item.getUseInsdDeviceId());

							prodItem.setPrchsId(item.getPrchsId());
							prodItem.setProdId(item.getProdId());
							prodList.add(prodItem);

							tLogProdId.add(item.getProdId());
							tLogProdAmt.add((long) item.getProdAmt());

							new TLogUtil().logger(LoggerFactory.getLogger("TLOG_SAC_LOGGER")).log(new ShuttleSetter() {

								@Override
								public void customize(TLogSentinelShuttle shuttle) {
									shuttle.log_id("TL_SAC_PUR_0002").system_id(existenceSacReqV2.getSystemId())
											.insd_device_id(item.getUseInsdUsermbrNo())
											.insd_usermbr_no(item.getUseInsdDeviceId())
											.purchase_channel(item.getPrchsReqPathCd())
											.purchase_inflow_channel(item.getPrchsCaseCd()).network_type(networkType)
											.product_id(tLogProdId).product_price(tLogProdAmt).result_code("SUCC");
								}
							});

						} catch (StorePlatformException e) {
							ErrorInfo errorInfo = e.getErrorInfo();
							final String resultCode = errorInfo.getCode();
							final String resultMessage = errorInfo.getMessage();

							new TLogUtil().logger(LoggerFactory.getLogger("TLOG_SAC_LOGGER")).log(new ShuttleSetter() {

								@Override
								public void customize(TLogSentinelShuttle shuttle) {
									shuttle.log_id("TL_SAC_PUR_0002").system_id(existenceSacReqV2.getSystemId())
											.insd_device_id(item.getUseInsdUsermbrNo())
											.insd_usermbr_no(item.getUseInsdDeviceId())
											.purchase_channel(item.getPrchsReqPathCd())
											.purchase_inflow_channel(item.getPrchsCaseCd()).network_type(networkType)
											.product_id(tLogProdId).product_price(tLogProdAmt).result_code(resultCode)
											.result_message(resultMessage);
								}
							});

							throw e;
						}

					}
				}

				// 데이터가 존재하면 셋팅한다.
				if (prodList != null && prodList.size() > 0) {
					userItem.setExistenceList(prodList);
					userList.add(userItem);
				}
			}
		}

		response.setUserList(userList);

		return response;
	}

	/**
	 * 기구매 체크 SAC Service.
	 * 
	 * @param existenceScReq
	 *            요청
	 * @param inputValue
	 *            내외부 사용구분 내부 true 요청정보
	 * @return List<ExistenceScRes>
	 */
	// @Override
	// public ExistenceSacResV2 searchExistenceListV2(ExistenceSacReqV2 existenceSacReqV2, final String networkType) {
	//
	// ExistenceScReq existenceScReq = null;
	// List<ExistenceItemSc> productList = null;
	// List<ExistenceScReq> userScList = new ArrayList<ExistenceScReq>();
	// ExistenceScV2Req scRequest = new ExistenceScV2Req();
	//
	// /******************** SC request Data Set Start ********************/
	// for (ExistenceSacReq existenceSacReq : existenceSacReqV2.getUserList()) {
	//
	// existenceScReq = new ExistenceScReq();
	// productList = new ArrayList<ExistenceItemSc>();
	//
	// existenceScReq.setTenantId(existenceSacReqV2.getTenantId());
	// existenceScReq.setSystemId(existenceSacReqV2.getSystemId());
	// existenceScReq.setUserKey(existenceSacReq.getUserKey());
	// existenceScReq.setDeviceKey(existenceSacReq.getDeviceKey());
	// existenceScReq.setPrchsId(existenceSacReq.getPrchsId());
	// existenceScReq.setCheckValue(false);
	//
	// // 상품리스트가 없을시 제외
	// if (existenceSacReq.getProductList() != null) {
	// for (ExistenceItemSac existenceItemSac : existenceSacReq.getProductList()) {
	// ExistenceItemSc existenceItemSc = new ExistenceItemSc();
	// existenceItemSc.setProdId(existenceItemSac.getProdId());
	// existenceItemSc.setTenantProdGrpCd(existenceItemSac.getTenantProdGrpCd());
	// productList.add(existenceItemSc);
	// }
	// }
	//
	// existenceScReq.setProductList(productList);
	// userScList.add(existenceScReq);
	// }
	// scRequest.setUserList(userScList);
	// /******************** SC request Data Set End ********************/
	//
	// // 기구매 목록조회
	// List<ExistenceScRes> resultList = this.existenceSCI.searchExistenceListV2(scRequest);
	//
	// ExistenceSacRes prodItem = null;
	// List<ExistenceSacRes> prodList = null;
	// ExistenceListSacResV2 userItem = null;
	// List<ExistenceListSacResV2> userList = new ArrayList<ExistenceListSacResV2>();
	// ExistenceSacResV2 response = new ExistenceSacResV2();
	//
	// for (ExistenceSacReq existenceSacReq : existenceSacReqV2.getUserList()) {
	// userItem = new ExistenceListSacResV2();
	// prodItem = new ExistenceSacRes();
	// prodList = new ArrayList<ExistenceSacRes>();
	// for (ExistenceScRes item : resultList) {
	//
	// if (StringUtil.equals(existenceSacReq.getUserKey(), item.getUseInsdUsermbrNo())) {
	//
	// prodItem = new ExistenceSacRes();
	//
	// userItem.setUserKey(item.getUseInsdUsermbrNo());
	// userItem.setDeviceKey(item.getUseInsdDeviceId());
	//
	// prodItem.setPrchsId(item.getPrchsId());
	// prodItem.setProdId(item.getProdId());
	// prodList.add(prodItem);
	// }
	// }
	// userItem.setExistenceList(prodList);
	// userList.add(userItem);
	// }
	//
	// response.setUserList(userList);
	//
	// return response;
	// }

	/**
	 * <pre>
	 * 기구매상품이 ID기반인지 MDN기반 인지 체크한다.
	 * </pre>
	 * 
	 * @param existenceScReq
	 *            구매요청값
	 * @param existenceScRes
	 *            구매응답값
	 * @param purchaseTenantPolicyList
	 *            정책리스트
	 * @param flag
	 *            정책구분
	 * @return String
	 */
	private String checkMdn(ExistenceScReq existenceScReq, ExistenceScRes existenceScRes,
			List<TenantSalePolicy> purchaseTenantPolicyList, String flag) {

		// flag의 값은 ID, MDN, NOT_MDN를 가진다
		// flag : ID => ID기반
		// flag : MDN => MDN기반
		// flag : NOT_MDN => 정책과 조회한 tenantProdGrpCd는 같지만 DeviceKey가 다른 경우는기구매체크에서 제외한다.
		// 정책조회된 것이 없으면 ID기반으로 정의한다.(2014-03-10)
		// 구매아이디로 조회시 devicekey가 null이면 ID기반으로 정의한다.(2014-03-10)
		if (existenceScReq.getPrchsId() != null) {
			flag = "ID";
		} else {
			if (purchaseTenantPolicyList.size() > 0) {
				for (TenantSalePolicy purchaseTenantPolicy : purchaseTenantPolicyList) {

					String tenantProdGrpCd = existenceScRes.getTenantProdGrpCd();
					// 조회한 tenantProdGrpCd의 시작 코드와 정책코드가 같다면 MDN기반
					if (tenantProdGrpCd.startsWith(purchaseTenantPolicy.getTenantProdGrpCd())) {
						// 정책이 같을 경우에는 조회한 DeviceKey와 입력받은 DeviceKey를 비교하여 flag 셋팅
						if (existenceScRes.getUseInsdDeviceId().equals(existenceScReq.getDeviceKey())) {
							// MDN기반이면 리턴을 한다.
							return flag = "MDN";
						} else {
							flag = "NOT_MDN";
						}
					} else {
						// 조회한 tenantProdGrpCd의 시작 코드와 정책코드가 다르면 ID기반
						if (!flag.equals("NOT_MDN")) {
							flag = "ID";
						}
					}
				}
			} else {
				// 정책 조회시 정책이 없는경우
				flag = "ID";
			}
		}
		return flag;
	}
}
