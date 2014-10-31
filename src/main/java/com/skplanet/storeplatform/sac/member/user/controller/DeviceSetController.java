package com.skplanet.storeplatform.sac.member.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckDevicePinSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDevicePinSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDevicePinSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchDevicePinSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.service.DeviceSetService;

/**
 * 휴대기기 설정 관련 Controller
 * 
 * Updated on : 2014. 10. 28. Updated by : Rejoice, Burkhan
 */
@RequestMapping(value = "/member/user")
@Controller
public class DeviceSetController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSetController.class);

	@Autowired
	private DeviceSetService deviceSetService;

	/**
	 * <pre>
	 * 2.1.44. 휴대기기 PIN 번호 등록.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateDevicePinSacReq
	 * @return CreateDevicePinSacRes
	 */
	@RequestMapping(value = "/createDevicePin/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateDevicePinSacRes createDevicePin(SacRequestHeader header,
			@RequestBody @Validated CreateDevicePinSacReq req) {

		if (StringUtil.isBlank(req.getDeviceKey()) && StringUtil.isBlank(req.getDeviceId())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId || deviceKey");
		}

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		CreateDevicePinSacRes res = this.deviceSetService.createDevicePin(header, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * 2.1.45. 휴대기기 PIN 번호 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyDevicePinSacReq
	 * @return ModifyDevicePinSacRes
	 */
	@RequestMapping(value = "/modifyDevicePin/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyDevicePinSacRes modifyDevicePin(SacRequestHeader header,
			@RequestBody @Validated ModifyDevicePinSacReq req) {
		if (StringUtil.isBlank(req.getDeviceKey()) && StringUtil.isBlank(req.getDeviceId())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId || deviceKey");
		}
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		ModifyDevicePinSacRes res = this.deviceSetService.modifyDevicePin(header, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * 2.1.46. 휴대기기 PIN 번호 찾기.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchDevicePinSacReq
	 * @return SearchDevicePinSacRes
	 */
	@RequestMapping(value = "/searchDevicePin/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchDevicePinSacRes searchDevicePin(SacRequestHeader header,
			@RequestBody @Validated SearchDevicePinSacReq req) {
		if (StringUtil.isBlank(req.getDeviceKey()) && StringUtil.isBlank(req.getDeviceId())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId || deviceKey");
		}
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		SearchDevicePinSacRes res = this.deviceSetService.searchDevicePin(header, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * 2.1.46. 휴대기기 PIN 번호 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CheckDevicePinSacReq
	 * @return CheckDevicePinSacRes
	 */
	@RequestMapping(value = "/checkDevicePin/v1", method = RequestMethod.POST)
	@ResponseBody
	public CheckDevicePinSacRes checkDevicePin(SacRequestHeader header, @RequestBody @Validated CheckDevicePinSacReq req) {
		if (StringUtil.isBlank(req.getDeviceKey()) && StringUtil.isBlank(req.getDeviceId())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId || deviceKey");
		}
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		CheckDevicePinSacRes res = this.deviceSetService.checkDevicePin(header, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

}
