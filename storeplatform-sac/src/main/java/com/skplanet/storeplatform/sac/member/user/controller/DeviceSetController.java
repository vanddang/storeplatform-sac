package com.skplanet.storeplatform.sac.member.user.controller;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.sac.client.member.vo.user.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.common.util.ValidationCheckUtils;
import com.skplanet.storeplatform.sac.member.domain.shared.UserDeviceSetting;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.PinService;
import com.skplanet.storeplatform.sac.member.user.service.DeviceSetService;
import com.skplanet.storeplatform.sac.member.user.service.DeviceSettingService;
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
 * 휴대기기 설정 관련 Controller
 * 
 * Updated on : 2015. 10. 29. Updated by : 최진호, 보고지티.
 */
@RequestMapping(value = "/member/user")
@Controller
public class DeviceSetController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSetController.class);

	/** DeviceSet Service InterFace. */
	@Autowired
	private DeviceSetService deviceSetService;

	@Autowired
	private PinService pinService;

    @Autowired
    private DeviceSettingService settingService;

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

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_MEM_0008");
			}
		});

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		CreateDevicePinSacRes res = this.deviceSetService.regDevicePin(header, req);
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

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));

		ModifyDevicePinSacRes res = this.deviceSetService.modDevicePin(header, req);
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

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_MEM_0010");
			}
		});
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		CheckDevicePinSacRes res = this.deviceSetService.checkDevicePin(header, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * [I01000106] 2.1.47. 휴대기기 설정 정보 조회.
	 * </pre>
	 * @param req SearchDeviceSetInfoSacReq
	 * @return SearchDeviceSetInfoSacRes
	 */
	@RequestMapping(value = "/searchDeviceSetInfo/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchDeviceSetInfoSacRes searchDeviceSetInfo(@RequestBody @Validated SearchDeviceSetInfoSacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
        UserDeviceSetting deviceSetting = settingService.find(req.getUserKey(), req.getDeviceKey());
        SearchDeviceSetInfoSacRes res = deviceSetting.convertToResponse();

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

    /**
     * <pre>
     * [I01000148] 2.1.81. 휴대기기 설정 정보 조회 V2
     * </pre>
     * @param req SearchDeviceSetInfoSacReq
     * @return SearchDeviceSetInfoSacRes
     */
    @RequestMapping(value = "/searchDeviceSetInfo/v2", method = RequestMethod.POST)
    @ResponseBody
    public SearchDeviceSetInfoSacRes searchDeviceSetInfoV2(@RequestBody @Validated SearchDeviceSetInfoSacReq req) {

        LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
        UserDeviceSetting deviceSetting = settingService.find(req.getUserKey(), req.getDeviceKey(), true);
        SearchDeviceSetInfoSacRes res = deviceSetting.convertToResponseV2();

        LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
        return res;
    }

	/**
	 * <pre>
	 * 2.1.48. 휴대기기 설정 정보 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyDeviceSetInfoSacReq
	 * @return ModifyDeviceSetInfoSacRes
	 */
	@RequestMapping(value = "/modifyDeviceSetInfo/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyDeviceSetInfoSacRes modifyDeviceSetInfo(SacRequestHeader header,
			@RequestBody @Validated ModifyDeviceSetInfoSacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		ModifyDeviceSetInfoSacRes res = this.deviceSetService.modDeviceSetInfo(header, req);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(res));
		return res;
	}

	/**
	 * <pre>
	 * 2.1.60.휴대기기 PIN 번호 인증 여부 확인
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param request
	 *            ConfirmPhoneAuthorizationCheckReq
	 * @return ConfirmPhoneAuthorizationCheckRes
	 */
	@RequestMapping(value = "/pinAuthorizationCheck/v1", method = RequestMethod.POST)
	@ResponseBody
	public PinAuthorizationCheckRes pinAuthorizationCheck(SacRequestHeader header,
			@RequestBody @Validated PinAuthorizationCheckReq request) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		if (!ValidationCheckUtils.isMdn(request.getDeviceId())) {
			throw new StorePlatformException("SAC_MEM_3004");
		}

		PinAuthorizationCheckRes response = this.pinService.pinAuthorizationCheck(header, request);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;
	}
}
