package com.skplanet.storeplatform.sac.member.idp.controller;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.member.idp.service.IdpService;
import com.skplanet.storeplatform.sac.member.idp.vo.ImResult;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningReq;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningRes;

/**
 * 
 * IDP에서 전달되는 Provisioning 및 Rx 처리를 위한 Controller
 * 
 * Updated on : 2014. 1. 8. Updated by : 임재호, 인크로스.
 */
@RequestMapping(value = "/member/idp")
@Controller
public class IdpController {

	@Autowired
	private IdpService service;

	private static final Logger logger = LoggerFactory.getLogger(IdpController.class);

	/**
	 * 
	 * <pre>
	 * IDP에서 수신된 'cmd'값을 기준으로 Service Method를 호출
	 * </pre>
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/provisioning", method = RequestMethod.POST)
	@ResponseBody
	public ProvisioningRes provisioning(@RequestBody ProvisioningReq provisioningReq) {
		ProvisioningRes provisioningRes = new ProvisioningRes();
		try {
			logger.debug("provisioning - cmd : {}, query string : {}", provisioningReq.getCmd(),
					provisioningReq.getReqParam());
			String cmd = provisioningReq.getCmd();
			boolean isIm = (cmd.indexOf("RX") == 0);
			Method method = this.service.getClass().getMethod(isIm ? "rX" + cmd.substring(2) : cmd, HashMap.class);
			// TODO : Reflection에 대한 성능 이슈 있으니 사용할지 한번더 고려 해보기 - 임재호 2014.1.8
			if (isIm) {
				provisioningRes.setImResult((ImResult) method.invoke(this.service, provisioningReq.getReqParam()));
			} else {
				provisioningRes.setResult((String) method.invoke(this.service, provisioningReq.getReqParam()));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// TODO : SAC 정의 Exception으로 변경 하기 - 임재호 2014.1.8
			throw new RuntimeException(e.getMessage());
		}
		return provisioningRes;
	}
}
