package com.skplanet.storeplatform.sac.member.user.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.SacRequestHeaderHolder;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.user.sci.service.DeviceSCIService;

/**
 * 단말 정보 조회 내부메소드 호출 Controller.
 * 
 * Updated on : 2014. 2. 11. Updated by : 김다슬, 인크로스.
 */
@LocalSCI
@RequestMapping(value = "/member/user/sci")
public class DeviceSCIController implements DeviceSCI {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSCIController.class);

	@Autowired
	private DeviceSCIService deviceService; // 휴대기기 관련 SAC 내부 인터페이스.

	/**
	 * <pre>
	 * 단말 ID 정보(msisdn|uuid|mac) 조회.
	 * </pre>
	 * 
	 * @param request
	 *            SearchDeviceIdSacReq
	 * @return SearchDeviceIdSacRes
	 */
	@Override
	@RequestMapping(value = "/searchDeviceId", method = RequestMethod.POST)
	@ResponseBody
	public SearchDeviceIdSacRes searchDeviceId(@RequestBody @Validated SearchDeviceIdSacReq request) {

		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		TenantHeader tenant = requestHeader.getTenantHeader();
		if (StringUtils.isBlank(request.getTenantId())) { // tenantId 없는경우 default S01 셋팅
			tenant.setTenantId(MemberConstants.TENANT_ID_TSTORE);
		} else {
			tenant.setTenantId(request.getTenantId());
		}
		requestHeader.setTenantHeader(tenant);

		DeviceInfo deviceInfo = this.deviceService.srhDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID,
				request.getDeviceKey(), request.getUserKey());

		SearchDeviceIdSacRes response = new SearchDeviceIdSacRes();
		if (deviceInfo != null && StringUtils.isNotBlank(deviceInfo.getDeviceId())) {
			response.setDeviceId(deviceInfo.getDeviceId());
			response.setDeviceTelecom(deviceInfo.getDeviceTelecom());
			response.setAuthYn(deviceInfo.getIsAuthenticated());
			response.setTenantId(deviceInfo.getTenantId());
		} else {
			throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
		}

		LOGGER.info("Response : deviceId : {}", response.getDeviceId());
		return response;
	}

	/**
	 * <pre>
	 * 기기변경 이력 조회.
	 * </pre>
	 * 
	 * @param request
	 *            ChangedDeviceHistorySacReq
	 * @return ChangedDeviceHistorySacRes
	 */
	@Override
	@RequestMapping(value = "/searchChangedDeviceHistory", method = RequestMethod.POST)
	@ResponseBody
	public ChangedDeviceHistorySacRes searchChangedDeviceHistory(
			@RequestBody @Validated ChangedDeviceHistorySacReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		// 공통 파라미터 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		if (StringUtils.isBlank(request.getDeviceId()) && StringUtils.isBlank(request.getDeviceKey())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceId 또는 deviceKey");
		}

		ChangedDeviceHistorySacRes response = this.deviceService.srhChangedDeviceHistory(requestHeader, request);
		LOGGER.info("Response : changed Yn : {}", response.getIsChanged());
		return response;

	}

	/**
	 * <pre>
	 * @TODO [QA] 2014-10-07 적용 예정.
	 * 2.1.9.	등록된 단말 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            SearchOrderDeviceIdSacReq
	 * @return SearchOrderDeviceIdSacRes
	 */
	@Override
	@RequestMapping(value = "/searchOrderDeviceId/", method = RequestMethod.POST)
	@ResponseBody
	public SearchOrderDeviceIdSacRes searchOrderDeviceId(@RequestBody @Validated SearchOrderDeviceIdSacReq req) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(req));
		// 공통 파라미터 셋팅
		SacRequestHeader requestHeader = SacRequestHeaderHolder.getValue();

		TenantHeader tenant = requestHeader.getTenantHeader();
		if (StringUtils.isBlank(req.getTenantId())) { // tenantId 없는경우 default S01 셋팅
			tenant.setTenantId(MemberConstants.TENANT_ID_TSTORE);
		} else {
			tenant.setTenantId(req.getTenantId());
		}
		requestHeader.setTenantHeader(tenant);

		SearchOrderDeviceIdSacRes res = this.deviceService.searchOrderDeviceId(requestHeader, req);

		LOGGER.info("Response : {}", res.getDeviceId());
		return res;
	}
}
