package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.common.util.ValidationCheckUtils;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.DeviceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Device info 기능 관련 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 Device info 관련 기능 클래스 분리
 */
@Controller
@RequestMapping(value = "/member/miscellaneous")
public class DeviceInfoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceInfoController.class);

	@Autowired
	private DeviceInfoService service;

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetUaCodeReq
	 * @return GetUaCodeRes
	 */
	@RequestMapping(value = "/getUaCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetUaCodeRes getUaCode(SacRequestHeader requestHeader, @Validated @RequestBody GetUaCodeReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		// 필수 파라미터 확인. 둘 중 하나는 필수로 입력해야함.
		if (StringUtils.isBlank(request.getDeviceModelNo()) && StringUtils.isBlank(request.getMsisdn())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceModel 또는 msisdn");
		}
		if (StringUtils.isNotBlank(request.getMsisdn())) {
			if (!ValidationCheckUtils.isMdn(request.getMsisdn()))
				throw new StorePlatformException("SAC_MEM_3004");
		}

		GetUaCodeRes response = this.service.getUaCode(requestHeader, request);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;
	}

	/**
	 * <pre>
	 * OPMD 모회선 번호 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetOpmdReq
	 * @return GetOpmdRes
	 */
	@RequestMapping(value = "/getOpmd/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetOpmdRes getOpmd(SacRequestHeader requestHeader, @Validated @RequestBody GetOpmdReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		// CommonComponent 호출 시에는 mdn Check 불필요하므로, Controller에서 유효성 검사.
		if (!ValidationCheckUtils.isMdn(request.getMsisdn())) {
			throw new StorePlatformException("SAC_MEM_3004");
		}

		GetOpmdRes response = this.service.getOpmd(request);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;
	}

	/**
	 * <pre>
	 * 단말 모델코드 조회.
	 * </pre>
	 * 
	 * @param request
	 *            GetModelCodeReq
	 * @return GetModelCodeRes
	 */
	@RequestMapping(value = "/getModelCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetModelCodeRes getModelCode(@RequestBody @Validated GetModelCodeReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		// 필수 파라미터 확인. 둘 중 하나는 필수로 입력해야함.
		if (StringUtils.isBlank(request.getUaCd()) && StringUtils.isBlank(request.getMsisdn())) {
			throw new StorePlatformException("SAC_MEM_0001", "uaCd 또는 msisdn");
		}
		if (StringUtils.isNotBlank(request.getMsisdn())) {
			if (!ValidationCheckUtils.isMdn(request.getMsisdn()))
				throw new StorePlatformException("SAC_MEM_3004");
		}
		GetModelCodeRes response = this.service.getModelCode(request);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;
	}

}
