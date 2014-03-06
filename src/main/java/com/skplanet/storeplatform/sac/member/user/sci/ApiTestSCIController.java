/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;

/**
 * 내부 메소드 호출 기능 확인을 위한 Controller.
 * 
 * Updated on : 2014. 3. 6. Updated by : 김다슬, 인크로스.
 */
@Controller
@RequestMapping(value = "/member/internalSci/Test")
public class ApiTestSCIController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiTestSCIController.class);

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private SearchUserSCI searchUserSCI;

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchDeviceId/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchDeviceIdSacRes searchDeviceId(@RequestBody @Validated SearchDeviceIdSacReq request) {

		LOGGER.info("[ApiTestSCIController.searchDeviceId] Request :{} ", request);
		SearchDeviceIdSacRes response = this.deviceSCI.searchDeviceId(request);
		LOGGER.info("[ApiTestSCIController.searchDeviceId] Response :{} ", request);
		return response;

	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchChangedDeviceHistory/v1", method = RequestMethod.POST)
	@ResponseBody
	public ChangedDeviceHistorySacRes searchChangedDeviceHistory(
			@RequestBody @Validated ChangedDeviceHistorySacReq request) {

		LOGGER.info("[ApiTestSCIController.searchChangedDeviceHistory] Request :{} ", request);
		ChangedDeviceHistorySacRes response = this.deviceSCI.searchChangedDeviceHistory(request);
		LOGGER.info("[ApiTestSCIController.searchChangedDeviceHistory] Response :{} ", request);
		return response;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchUserByUserKey/v1", method = RequestMethod.POST)
	@ResponseBody
	public SearchUserSacRes searchUserByUserKey(@RequestBody @Validated SearchUserSacReq request) {

		LOGGER.info("[ApiTestSCIController.searchUserByUserKey] Request :{} ", request);
		SearchUserSacRes response = this.searchUserSCI.searchUserByUserKey(request);
		LOGGER.info("[ApiTestSCIController.searchUserByUserKey] Request :{} ", request);

		return response;
	}

}
