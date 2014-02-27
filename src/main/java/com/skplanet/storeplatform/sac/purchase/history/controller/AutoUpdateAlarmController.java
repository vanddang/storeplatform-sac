/**
 * 
 */
package com.skplanet.storeplatform.sac.purchase.history.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmSc;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.AutoUpdateAlarmScRes;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoUpdateAlarmSac;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoUpdateAlarmSacReq;
import com.skplanet.storeplatform.sac.client.purchase.vo.history.AutoUpdateAlarmSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.purchase.common.util.PurchaseCommonUtils;
import com.skplanet.storeplatform.sac.purchase.history.service.AutoUpdateAlarmSacService;

/**
 * 자동업데이트 거부/거부취소Controller.
 * 
 * Updated on : 2014. 2. 25. Updated by : 조용진, NTELS.
 */
@Controller
@RequestMapping(value = "/purchase")
public class AutoUpdateAlarmController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AutoUpdateAlarmSacService autoUpdateAlarmSacService;
	@Autowired
	private PurchaseCommonUtils purchaseCommonUtils;

	/**
	 * 자동업데이트 거부/거부취소 요청.
	 * 
	 * @param autoUpdateAlarmScReq
	 *            요청정보
	 * @return AutoUpdateAlarmScRes
	 */
	@RequestMapping(value = "/history/alarm/update/v1", method = RequestMethod.POST)
	@ResponseBody
	public AutoUpdateAlarmSacRes updateAlarm(@RequestBody @Validated AutoUpdateAlarmSacReq autoUpdateAlarmSacReq,
			SacRequestHeader requestHeader) {

		TenantHeader header = requestHeader.getTenantHeader();

		return this.resConvert(this.autoUpdateAlarmSacService.updateAlarm(this
				.reqConvert(autoUpdateAlarmSacReq, header)));
	}

	/**
	 * reqConvert.
	 * 
	 * @param autoPaymentCancelSacReq
	 *            요청정보
	 * @param header
	 *            테넌트 헤더정보
	 * @return AutoPaymentCancelScReq
	 */
	private AutoUpdateAlarmScReq reqConvert(AutoUpdateAlarmSacReq autoUpdateAlarmSacReq, TenantHeader header) {

		this.logger.debug("@@@@@@ AutoPaymentCancelSac reqConvert @@@@@@@");
		AutoUpdateAlarmScReq req = new AutoUpdateAlarmScReq();

		req.setTenantId(header.getTenantId());
		req.setSystemId(header.getSystemId());
		req.setUserKey(autoUpdateAlarmSacReq.getUserKey());

		List<AutoUpdateAlarmSc> list = new ArrayList<AutoUpdateAlarmSc>();

		for (AutoUpdateAlarmSac autoUpdateAlarmSac : autoUpdateAlarmSacReq.getProductList()) {
			AutoUpdateAlarmSc autoUpdateAlarmSc = new AutoUpdateAlarmSc();
			autoUpdateAlarmSc.setProdId(autoUpdateAlarmSac.getProdId());
			autoUpdateAlarmSc.setAlarmYn(autoUpdateAlarmSac.getAlarmYn());

			list.add(autoUpdateAlarmSc);
		}
		req.setProductList(list);
		return req;
	}

	/**
	 * resConvert.
	 * 
	 * @param autoPaymentCancelScRes
	 *            요청정보
	 * @return AutoPaymentCancelSacRes
	 */
	private AutoUpdateAlarmSacRes resConvert(AutoUpdateAlarmScRes autoUpdateAlarmScRes) {
		this.logger.debug("@@@@@@ AutoPaymentCancelSac resConvert @@@@@@@");
		AutoUpdateAlarmSacRes res = new AutoUpdateAlarmSacRes();
		res.setResultYn(autoUpdateAlarmScRes.getResultYn());

		return res;
	}
}
