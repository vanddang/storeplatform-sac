/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.history.sci;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.sci.ExistenceInternalSacSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceInV2Req;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceInV2Res;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListInV2Res;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceUserInfoInV2;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceInfoSac;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceListSacResV2;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacReqV2;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceSacResV2;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceUserInfoSacV2;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;

/**
 * 구매 SAC 컨트롤러
 * 
 * Updated on : 2014-01-14 Updated by : 조용진, 엔텔스.
 */
@LocalSCI
public class ExistenceInternalSCIController implements ExistenceInternalSacSCI {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExistenceSacService existenceSacService;

	/**
	 * 기구매 체크 SAC.
	 * 
	 * @param existenceReq
	 *            요청정보
	 * @return ExistenceListRes 응답정보
	 */
	@Override
	@ResponseBody
	public ExistenceListRes searchExistenceList(ExistenceReq existenceReq) {
		this.logger.debug("PRCHS,ExistenceInternalSCIController,SAC,REQ,{}", existenceReq);

		// 필수값 체크
		if (StringUtils.isBlank(existenceReq.getTenantId())) {
			throw new StorePlatformException("SAC_PUR_0001", "TenantId");
		}

		if (StringUtils.isBlank(existenceReq.getPrchsId())) {
			if (StringUtils.isBlank(existenceReq.getUserKey())) {
				throw new StorePlatformException("SAC_PUR_0001", "UserKey");
			}
			if (StringUtils.isBlank(existenceReq.getDeviceKey())) {
				throw new StorePlatformException("SAC_PUR_0001", "DeviceKey");
			}
			if (existenceReq.getExistenceItem() == null) {
				throw new StorePlatformException("SAC_PUR_0001", "ProductList");
			} else if (existenceReq.getExistenceItem().size() < 1) {
				throw new StorePlatformException("SAC_PUR_0001", "ProdId");
			}
		}
		ExistenceScReq req = this.reqConvert(existenceReq);
		// 로컬SCI는 빈값셋팅
		String networkType = "";
		List<ExistenceRes> res = this.resConvert(this.existenceSacService.searchExistenceList(req, networkType));

		ExistenceListRes existenceListRes = new ExistenceListRes();
		existenceListRes.setExistenceListRes(res);

		return existenceListRes;
	}

	/**
	 * 기구매 체크 SAC. V2
	 * 
	 * @param existenceReq
	 *            요청정보
	 * @return ExistenceListRes 응답정보
	 */
	@Override
	@ResponseBody
	public ExistenceInV2Res searchExistenceListV2(ExistenceInV2Req existenceInV2Req) {

		this.logger.debug("PRCHS,ExistenceInternalSCIController,SAC,REQ,V2{}", existenceInV2Req);

		ExistenceSacReqV2 sacRequest = this.reqConvertV2(existenceInV2Req);
		ExistenceSacResV2 sacResponse = this.existenceSacService.searchExistenceListV2(sacRequest, "");

		List<ExistenceListInV2Res> userList = new ArrayList<ExistenceListInV2Res>();
		ExistenceListInV2Res userItem;
		List<ExistenceRes> prodList;
		ExistenceRes prodItem;

		for (ExistenceUserInfoInV2 reqUserItem : existenceInV2Req.getUserList()) {

			for (String device : reqUserItem.getDeviceList()) {

				userItem = new ExistenceListInV2Res();
				prodList = new ArrayList<ExistenceRes>();

				for (ExistenceListSacResV2 item : sacResponse.getUserList()) {

					if (StringUtils.equals(reqUserItem.getUserKey(), item.getUserKey())
							&& StringUtils.equals(device, item.getDeviceKey())) {

						prodItem = new ExistenceRes();

						userItem.setUserKey(item.getUserKey());
						userItem.setDeviceKey(item.getDeviceKey());

						for (ExistenceSacRes prodInfo : item.getExistenceList()) {
							prodItem.setPrchsId(prodInfo.getPrchsId());
							prodItem.setProdId(prodInfo.getProdId());
							prodList.add(prodItem);
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

		ExistenceInV2Res existenceInV2Res = new ExistenceInV2Res();
		existenceInV2Res.setUserList(userList);

		this.logger.info("PRCHS,ExistenceController,SAC,existenceInV2Res,{}", sacResponse);
		return existenceInV2Res;
	}

	/**
	 * reqConvert.
	 * 
	 * @param existenceReq
	 *            요청정보
	 * @return ExistenceScReq
	 */
	private ExistenceScReq reqConvert(ExistenceReq existenceReq) {

		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		this.logger.debug("@@@@@@ Start Internal reqConvert @@@@@@");
		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		ExistenceScReq req = new ExistenceScReq();
		List<ExistenceItemSc> existenceItemListSc = new ArrayList<ExistenceItemSc>();

		req.setTenantId(existenceReq.getTenantId());
		req.setUserKey(existenceReq.getUserKey());
		req.setDeviceKey(existenceReq.getDeviceKey());
		req.setPrchsId(existenceReq.getPrchsId());
		req.setCheckValue(false);

		if (StringUtils.equals("Y", existenceReq.getDeviceHistoryYn())) {
			if (StringUtils.isBlank(existenceReq.getDeviceKey())) {
				throw new StorePlatformException("SAC_PUR_0001", "DeviceKey");
			}
			req.setCheckValue(true);
		}

		// 상품리스트가 없을시 제외
		if (existenceReq.getExistenceItem() != null) {
			int size = existenceReq.getExistenceItem().size();
			for (int i = 0; i < size; i++) {
				ExistenceItemSc existenceItemSc = new ExistenceItemSc();
				if (StringUtils.isBlank(existenceReq.getExistenceItem().get(i).getProdId())) {
					throw new StorePlatformException("SAC_PUR_0001", "ProdId");
				}
				existenceItemSc.setProdId(existenceReq.getExistenceItem().get(i).getProdId());
				existenceItemSc.setTenantProdGrpCd(existenceReq.getExistenceItem().get(i).getTenantProdGrpCd());
				existenceItemListSc.add(existenceItemSc);
			}
		}
		req.setProductList(existenceItemListSc);

		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param existenceListScRes
	 *            요청정보
	 * @return List<ExistenceRes>
	 */
	private List<ExistenceRes> resConvert(List<ExistenceScRes> existenceListScRes) {

		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		this.logger.debug("@@@@@@ Start Internal resConvert @@@@@@");
		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		List<ExistenceRes> res = new ArrayList<ExistenceRes>();
		for (ExistenceScRes existenceScRes : existenceListScRes) {
			ExistenceRes existenceRes = new ExistenceRes();
			existenceRes.setPrchsId(existenceScRes.getPrchsId());
			existenceRes.setProdId(existenceScRes.getProdId());

			res.add(existenceRes);
		}
		// this.logger.debug("PRCHS,ExistenceInternalSCIController,SAC,RES,{}", res);
		return res;
	}

	private ExistenceSacReqV2 reqConvertV2(ExistenceInV2Req existenceInV2Req) {

		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		this.logger.debug("@@@@@@ Start reqConvert @@@@@@");
		this.logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		ExistenceSacReqV2 sacReqyest = new ExistenceSacReqV2();
		ExistenceUserInfoSacV2 sacUserItem;
		List<ExistenceUserInfoSacV2> sacUserList = new ArrayList<ExistenceUserInfoSacV2>();

		List<ExistenceInfoSac> deviceList = new ArrayList<ExistenceInfoSac>();
		List<ExistenceInfoSac> prodList = new ArrayList<ExistenceInfoSac>();
		ExistenceInfoSac deviceInfo;
		ExistenceInfoSac prodInfo;

		/********** SC Request Setting Start **********/
		for (ExistenceUserInfoInV2 reqUserItem : existenceInV2Req.getUserList()) {

			sacUserItem = new ExistenceUserInfoSacV2();
			sacUserItem.setUserKey(reqUserItem.getUserKey());
			sacUserItem.setTenantId(reqUserItem.getTenantId());

			for (String device : reqUserItem.getDeviceList()) {
				deviceInfo = new ExistenceInfoSac();
				deviceInfo.setDeviceKey(device);
				deviceList.add(deviceInfo);
			}

			sacUserItem.setDeviceList(deviceList);
			sacUserList.add(sacUserItem);
		}

		for (String prod : existenceInV2Req.getProdList()) {
			prodInfo = new ExistenceInfoSac();
			prodInfo.setProdId(prod);
			prodList.add(prodInfo);
		}

		sacReqyest.setUserList(sacUserList);
		sacReqyest.setProdList(prodList);
		/********** SC Request Setting End **********/

		return sacReqyest;
	}
}
