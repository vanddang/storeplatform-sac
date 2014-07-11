/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.shopping.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponItem;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponPublish;
import com.skplanet.storeplatform.external.client.shopping.vo.BizCouponReq;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.purchase.client.shopping.sci.ShoppingAsyncSCI;
import com.skplanet.storeplatform.purchase.client.shopping.vo.ShoppingAsyncItemSc;
import com.skplanet.storeplatform.purchase.constant.PurchaseConstants;

/**
 * 쇼핑쿠폰 Service Implements.
 * 
 * Updated on : 2014. 2. 3. Updated by : ntels_yjw, nTels.
 */
@Service
public class ShoppingAsyncServiceImpl implements ShoppingAsyncService {

	private static final Logger logger = LoggerFactory.getLogger(ShoppingAsyncServiceImpl.class);

	@Autowired
	private ShoppingAsyncSCI shoppingAsyncSCI;

	// @Override
	// public int getShoppingAsync(BizCouponReq request, String systemId, String tenantId) throws Exception {
	//
	// ShoppingAsyncItemSc item = null;
	//
	// int prchsDtlId = 0;
	// int updateCnt = 0;
	//
	// // 1:발급 0:취소
	// if ("1".equals(request.getPublishStatus())) {
	//
	// for (BizCouponPublish obj : request.getXmlData().getPublish()) {
	//
	// logger.info("### Biz coupon :: PrchsId : " + obj.getPrchsId());
	//
	// for (BizCouponItem objItem : obj.getItems()) {
	//
	// try {
	// prchsDtlId++;
	//
	// item = new ShoppingAsyncItemSc();
	//
	// item.setPrchsId(obj.getPrchsId());
	// item.setPrchsDtlId(prchsDtlId);
	// item.setAvailStartdate(obj.getAvail_startdate());
	// item.setAvailEnddate(obj.getAvail_enddate());
	// item.setMdn(obj.getMdn());
	// item.setMdn2(obj.getMdn2());
	//
	// item.setItemCode(objItem.getItemCode());
	// item.setPublishCode(objItem.getPublishCode());
	// item.setShippingUrl(objItem.getShippingUrl());
	// item.setExtraData(objItem.getExtraData());
	//
	// item.setSystemId(systemId);
	//
	// // 쿠폰정보 업데이트 및 구매상태 구매완료처리
	// updateCnt += this.shoppingAsyncSCI.updateShoppingAsyncItem(item);
	//
	// /******************************
	// * TLOG Setting Start
	// ******************************/
	// final String purchase_id = obj.getPrchsId();
	// final String use_start_time = obj.getAvail_startdate();
	// final String use_end_time = obj.getAvail_enddate();
	// final String coupon_publish_code = objItem.getPublishCode();
	// final String coupon_code = request.getCouponCode();
	// final String coupon_item_code = objItem.getItemCode();
	// final Map<String, String> link_system_result_code = new HashMap<String, String>();
	// link_system_result_code.put("SCPCMS", request.getPublishStatus());
	// final Map<String, String> link_system_result_message = new HashMap<String, String>();
	// link_system_result_message
	// .put("SCPCMS", request.getPublishStatus() == "1" ? "publish" : "fail");
	//
	// new TLogUtil().log(new ShuttleSetter() {
	// @Override
	// public void customize(TLogSentinelShuttle shuttle) {
	// shuttle.log_id(PurchaseConstants.INTERFACE_ID_TL_SAC_PUR_0006).purchase_id(purchase_id)
	// .use_start_time(use_start_time).use_end_time(use_end_time)
	// .download_expired_time(use_end_time).coupon_publish_code(coupon_publish_code)
	// .coupon_code(coupon_code).coupon_item_code(coupon_item_code)
	// .result_code("SUCC").result_message("")
	// .link_system_result_code(link_system_result_code)
	// .link_system_result_message(link_system_result_message);
	// }
	// });
	// /******************************
	// * TLOG Setting End
	// ******************************/
	//
	// } catch (Exception e) {
	//
	// /******************************
	// * TLOG Setting Start
	// ******************************/
	// final String purchase_id = obj.getPrchsId();
	// final String use_start_time = obj.getAvail_startdate();
	// final String use_end_time = obj.getAvail_enddate();
	// final String coupon_publish_code = objItem.getPublishCode();
	// final String coupon_code = request.getCouponCode();
	// final String coupon_item_code = objItem.getItemCode();
	// final Map<String, String> link_system_result_code = new HashMap<String, String>();
	// link_system_result_code.put("SCPCMS", request.getPublishStatus());
	// final Map<String, String> link_system_result_message = new HashMap<String, String>();
	// link_system_result_message
	// .put("SCPCMS", request.getPublishStatus() == "1" ? "publish" : "fail");
	// final String result_message = e.getMessage();
	//
	// new TLogUtil().log(new ShuttleSetter() {
	// @Override
	// public void customize(TLogSentinelShuttle shuttle) {
	// shuttle.log_id(PurchaseConstants.INTERFACE_ID_TL_SAC_PUR_0006).purchase_id(purchase_id)
	// .use_start_time(use_start_time).use_end_time(use_end_time)
	// .download_expired_time(use_end_time).coupon_publish_code(coupon_publish_code)
	// .coupon_code(coupon_code).coupon_item_code(coupon_item_code)
	// .result_code("FAIL").result_message(result_message)
	// .link_system_result_code(link_system_result_code)
	// .link_system_result_message(link_system_result_message);
	// }
	// });
	// /******************************
	// * TLOG Setting End
	// ******************************/
	//
	// throw e;
	// }
	// }
	// prchsDtlId = 0;
	// }
	// } else {
	//
	// if (request.getXmlData().getPublish() != null) {
	// String prchsId = request.getXmlData().getPublish().get(0).getPrchsId();
	// ShoppingAsyncItemSc shoppingAsyncItemSc = new ShoppingAsyncItemSc();
	// shoppingAsyncItemSc.setTenantId(tenantId);
	// shoppingAsyncItemSc.setSystemId(systemId);
	// shoppingAsyncItemSc.setPrchsId(prchsId);
	// shoppingAsyncItemSc.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
	// shoppingAsyncItemSc.setCancelReqPathCd(PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON);
	//
	// logger.info("### Biz쿠폰 발급 취소 요청 param {} : " + shoppingAsyncItemSc);
	//
	// this.shoppingAsyncSCI.updatePrchsStatus(shoppingAsyncItemSc);
	// }
	// }
	//
	// return updateCnt;
	//
	// }

	@Override
	public Map<String, Object> getShoppingAsync(BizCouponReq request, String systemId, String tenantId)
			throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", "0000");
		resultMap.put("msg", "성공");

		ShoppingAsyncItemSc item = null;
		int updateCnt = 0;

		// 1:발급 0:취소
		if ("1".equals(request.getPublishStatus())) {

			logger.info("----------------------------------------------------------------------------------");
			logger.info("### BizCoupon 비동기응답 쿠폰 발급 처리");
			logger.info("----------------------------------------------------------------------------------");

			String chkPrchsId = StringUtils.substring(request.getXmlData().getPublish().get(0).getPrchsId(), 0, 20);

			Map<String, Object> prchsParam = new HashMap<String, Object>();
			prchsParam.put("tenantId", tenantId);
			prchsParam.put("prchsId", chkPrchsId);

			int targetCnt = this.shoppingAsyncSCI.searchPrchsIdCnt(prchsParam);
			int itemCnt = request.getXmlData().getPublish().size();

			logger.info("----------------------------------------------------------------------------------");
			logger.info("### BizCoupon 대상 리스트 건수 : " + targetCnt + ", 전문 item 건수 : " + itemCnt);
			logger.info("----------------------------------------------------------------------------------");

			if (targetCnt != 0 && targetCnt == itemCnt) {
				for (BizCouponPublish obj : request.getXmlData().getPublish()) {

					logger.info("### Request Full PrchsId : " + obj.getPrchsId());

					if (!StringUtils.isBlank(obj.getPrchsId()) && obj.getPrchsId().length() == 24) {

						String prchsId = StringUtils.substring(obj.getPrchsId(), 0, 20);
						int prchsDtlId = Integer.parseInt(StringUtils.substring(obj.getPrchsId(), 20, 24));

						logger.info("### Real PrchsId : " + prchsId);
						logger.info("### Real PrchsDtlId : " + prchsDtlId);

						BizCouponItem objItem = obj.getItems().get(0);

						if (objItem != null) {
							try {

								item = new ShoppingAsyncItemSc();

								item.setPrchsId(prchsId);
								item.setPrchsDtlId(prchsDtlId);
								item.setAvailStartdate(obj.getAvail_startdate());
								item.setAvailEnddate(obj.getAvail_enddate());
								item.setMdn(obj.getMdn());
								item.setMdn2(obj.getMdn2());

								item.setItemCode(objItem.getItemCode());
								item.setPublishCode(objItem.getPublishCode());
								item.setShippingUrl(objItem.getShippingUrl());
								item.setExtraData(objItem.getExtraData());

								item.setSystemId(systemId);

								// 쿠폰정보 업데이트 및 구매상태 구매완료처리
								updateCnt += this.shoppingAsyncSCI.updateShoppingAsyncItem(item);

								/******************************
								 * TLOG Setting Start
								 ******************************/
								final String purchase_id = obj.getPrchsId();
								final String use_start_time = obj.getAvail_startdate();
								final String use_end_time = obj.getAvail_enddate();
								final String coupon_publish_code = objItem.getPublishCode();
								final String coupon_code = request.getCouponCode();
								final String coupon_item_code = objItem.getItemCode();
								final Map<String, String> link_system_result_code = new HashMap<String, String>();
								link_system_result_code.put("SCPCMS", request.getPublishStatus());
								final Map<String, String> link_system_result_message = new HashMap<String, String>();
								link_system_result_message.put("SCPCMS",
										request.getPublishStatus() == "1" ? "publish" : "fail");

								new TLogUtil().log(new ShuttleSetter() {
									@Override
									public void customize(TLogSentinelShuttle shuttle) {
										shuttle.log_id(PurchaseConstants.INTERFACE_ID_TL_SAC_PUR_0006)
												.purchase_id(purchase_id).use_start_time(use_start_time)
												.use_end_time(use_end_time).download_expired_time(use_end_time)
												.coupon_publish_code(coupon_publish_code).coupon_code(coupon_code)
												.coupon_item_code(coupon_item_code).result_code("SUCC")
												.result_message("").link_system_result_code(link_system_result_code)
												.link_system_result_message(link_system_result_message);
									}
								});
								/******************************
								 * TLOG Setting End
								 ******************************/

							} catch (Exception e) {

								logger.info("----------------------------------------------------------------------------------");
								logger.info("### BizCoupon 비동기응답 쿠폰번호 Update 실패");
								logger.info("----------------------------------------------------------------------------------");

								resultMap.put("code", "6004");
								resultMap.put("msg", "쿠폰발급(Tstore) 처리 오류");

								/******************************
								 * TLOG Setting Start
								 ******************************/
								final String purchase_id = obj.getPrchsId();
								final String use_start_time = obj.getAvail_startdate();
								final String use_end_time = obj.getAvail_enddate();
								final String coupon_publish_code = objItem.getPublishCode();
								final String coupon_code = request.getCouponCode();
								final String coupon_item_code = objItem.getItemCode();
								final Map<String, String> link_system_result_code = new HashMap<String, String>();
								link_system_result_code.put("SCPCMS", request.getPublishStatus());
								final Map<String, String> link_system_result_message = new HashMap<String, String>();
								link_system_result_message.put("SCPCMS",
										request.getPublishStatus() == "1" ? "publish" : "fail");
								final String result_message = e.getMessage();

								new TLogUtil().log(new ShuttleSetter() {
									@Override
									public void customize(TLogSentinelShuttle shuttle) {
										shuttle.log_id(PurchaseConstants.INTERFACE_ID_TL_SAC_PUR_0006)
												.purchase_id(purchase_id).use_start_time(use_start_time)
												.use_end_time(use_end_time).download_expired_time(use_end_time)
												.coupon_publish_code(coupon_publish_code).coupon_code(coupon_code)
												.coupon_item_code(coupon_item_code).result_code("FAIL")
												.result_message(result_message)
												.link_system_result_code(link_system_result_code)
												.link_system_result_message(link_system_result_message);
									}
								});
								/******************************
								 * TLOG Setting End
								 ******************************/

							}
						}

					} else {
						logger.info("----------------------------------------------------------------------------------");
						logger.info("### BizCoupon 비동기응답 PrchsId 검증 실패");
						logger.info("----------------------------------------------------------------------------------");

						resultMap.put("code", "6004");
						resultMap.put("msg", "쿠폰발급(Tstore) 처리 오류");

						return resultMap;

					}
				}
			} else {

				logger.info("----------------------------------------------------------------------------------");
				logger.info("### BizCoupon 비동기응답 대상 리스트 건수와 응답 전문의 item 건수 틀림");
				logger.info("----------------------------------------------------------------------------------");
				resultMap.put("code", "6003");
				resultMap.put("msg", "전문오류-쿠폰 처리 개수 다름");

				return resultMap;
			}

		} else {

			logger.info("----------------------------------------------------------------------------------");
			logger.info("### BizCoupon 비동기응답 쿠폰 취소 처리");
			logger.info("----------------------------------------------------------------------------------");

			if (request.getXmlData().getPublish() != null) {

				String prchsId = StringUtils.substring(request.getXmlData().getPublish().get(0).getPrchsId(), 0, 20);
				ShoppingAsyncItemSc shoppingAsyncItemSc = new ShoppingAsyncItemSc();
				shoppingAsyncItemSc.setTenantId(tenantId);
				shoppingAsyncItemSc.setSystemId(systemId);
				shoppingAsyncItemSc.setPrchsId(prchsId);
				shoppingAsyncItemSc.setPrchsStatusCd(PurchaseConstants.PRCHS_STATUS_CANCEL);
				shoppingAsyncItemSc.setCancelReqPathCd(PurchaseConstants.PRCHS_REQ_PATH_BIZ_COUPON);

				logger.info("### Biz쿠폰 발급 취소 요청 param {} : " + shoppingAsyncItemSc);

				this.shoppingAsyncSCI.updatePrchsStatus(shoppingAsyncItemSc);
			}
		}

		resultMap.put("updateCnt", updateCnt);
		return resultMap;

	}
}
