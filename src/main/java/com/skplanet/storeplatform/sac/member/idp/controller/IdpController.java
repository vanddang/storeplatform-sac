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

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.member.idp.service.IdpProvisionService;
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
	private IdpService idpService;

	@Autowired
	private IdpProvisionService idpProvisionService;

	private static final Logger LOGGER = LoggerFactory.getLogger(IdpController.class);

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
	@RequestMapping(value = "/provisioning/v1", method = RequestMethod.POST)
	@ResponseBody
	public ProvisioningRes provisioning(@RequestBody ProvisioningReq provisioningReq) {
		ProvisioningRes provisioningRes = new ProvisioningRes();
		try {
			LOGGER.info("#### E/C => SAC [Inbound] Start ####");
			LOGGER.info("provisioning - cmd : {}, query string : {}", provisioningReq.getCmd(),
					provisioningReq.getReqParam());
			String cmd = provisioningReq.getCmd();
			boolean isIm = (cmd.indexOf("RX") == 0);
			if (isIm) {
				Method method = this.idpService.getClass().getMethod("execute" + cmd, HashMap.class);
				provisioningRes.setImResult((ImResult) method.invoke(this.idpService, provisioningReq.getReqParam()));
			} else {
				Method method = this.idpProvisionService.getClass().getMethod(
						"execute" + StringUtils.upperCase(cmd.substring(0, 1)) + cmd.substring(1), HashMap.class);
				provisioningRes.setResult((String) method.invoke(this.idpProvisionService,
						provisioningReq.getReqParam()));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// TODO : SAC 정의 Exception으로 변경 하기 - 임재호 2014.1.8
			throw new RuntimeException(e.getMessage());
		}
		LOGGER.info("#### E/C => SAC [Inbound] End ####");
		return provisioningRes;
	}
}
