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
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.idp.service.IdpProvisionService;
import com.skplanet.storeplatform.sac.member.idp.service.IdpService;
import com.skplanet.storeplatform.sac.member.idp.vo.ImResult;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningReq;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningRes;
import com.skplanet.storeplatform.sac.member.idp.vo.ProvisioningResult;

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
	 * <pre>
	 * IDP에서 수신된 'cmd'값을 기준으로 Service Method를 호출.
	 * </pre>
	 * 
	 * @param provisioningReq
	 * @return ProvisioningRes
	 */
	@RequestMapping(value = "/provisioning/v1", method = RequestMethod.POST)
	@ResponseBody
	public ProvisioningRes provisioning(@RequestBody ProvisioningReq provisioningReq) {
		ProvisioningRes provisioningRes = new ProvisioningRes();
		try {
			LOGGER.debug("#### E/C => SAC [Inbound] Start ####");
			LOGGER.debug("provisioning - cmd : {}, query string : {}", provisioningReq.getCmd(),
					provisioningReq.getReqParam());
			LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(provisioningReq));
			String cmd = provisioningReq.getCmd();
			boolean isIm = (cmd.indexOf("RX") == 0);
			if (isIm) {
				Method method = this.idpService.getClass().getMethod(
						StringUtils.lowerCase(cmd.substring(0, 1)) + cmd.substring(1), HashMap.class);
				provisioningRes.setImResult((ImResult) method.invoke(this.idpService, provisioningReq.getReqParam()));
			} else {
				Method method = this.idpProvisionService.getClass().getMethod(cmd, HashMap.class);
				if (StringUtils.equals(cmd, "checkDeactivateStatusForSP")) {
					provisioningRes.setProvisioningResult((ProvisioningResult) method.invoke(this.idpProvisionService,
							provisioningReq.getReqParam()));
				} else {
					provisioningRes.setResult((String) method.invoke(this.idpProvisionService,
							provisioningReq.getReqParam()));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		LOGGER.debug("#### E/C => SAC [Inbound] End ####");
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(provisioningRes));
		return provisioningRes;
	}
}
