package com.skplanet.storeplatform.sac.common.header.resolver;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;

/**
 * SAC Custom Header 값들을 Spring Controller에서 쓰기 위한 WebArgumentResolver 구현체 (임시적으로 상위 규격서의 샘플 값들을 넣어놓았으며, 추후 ACL Flow가
 * 완성되면 보완한다.)
 * 
 * Updated on : 2014. 1. 15. Updated by : 서대영, SK 플래닛.
 */
public class SacRequestHeaderWebArgumentResolver implements WebArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		SacRequestHeader sacHeader = new SacRequestHeader();

		TenantHeader tenant = this.extractTenant(webRequest);
		DeviceHeader device = this.extractDevice(webRequest);
		NetworkHeader network = this.extractNetwork(webRequest);

		sacHeader.setTenantHeader(tenant);
		sacHeader.setDeviceHeader(device);
		sacHeader.setNetworkHeader(network);

		RequestContextHolder.getRequestAttributes().setAttribute(SacRequestHeader.class.getName(), sacHeader,
				RequestAttributes.SCOPE_REQUEST);

		if (methodParameter.getParameterType().equals(SacRequestHeader.class)) {
			return sacHeader;
		}

		return UNRESOLVED;
	}

	/**
	 * <pre>
	 * Tenant 정보 추출. (더미 구현)
	 * </pre>
	 * 
	 * @param webRequest
	 * @return
	 */
	private TenantHeader extractTenant(NativeWebRequest webRequest) {
		TenantHeader tenant = new TenantHeader();

		tenant.setTenantId("S01");

		String systemId = webRequest.getHeader(CommonConstants.HEADER_SYSTEM_ID);
		if (StringUtils.isEmpty(systemId)) {
			tenant.setSystemId("S01-01002");
		} else {
			tenant.setSystemId(systemId);
		}

		String langCd = "ko";
		// String acceptLanguage = webRequest.getHeader(CommonConstants.HEADER_ACCEPT_LANGUATE);
		// String langCd = SomeUtils.acceptLanguageToLandCd(acceptLanguage);
		tenant.setLangCd(langCd);

		return tenant;
	}

	/**
	 * <pre>
	 * Device 정보 추출. (더미 구현)
	 * </pre>
	 * 
	 * @param webRequest
	 * @return
	 */
	private DeviceHeader extractDevice(NativeWebRequest webRequest) {
		DeviceHeader device = new DeviceHeader();
		device.setDpi("320");
		device.setModel("SHW-M110S");
		device.setResolution("480*720");
		device.setOsVersion("Android/4.0.4");
		device.setPkgVersion("store.skplanet.com/0.1");
		device.setSvcVersion("SAC_Client/4.3");
		return device;
	}

	/**
	 * <pre>
	 * Network 정보 추출. (더미 구현)
	 * </pre>
	 * 
	 * @param webRequest
	 * @return
	 */
	private NetworkHeader extractNetwork(NativeWebRequest webRequest) {
		NetworkHeader network = new NetworkHeader();
		network.setOperator("unknown/unknown");
		network.setSimOperator("450/05");
		network.setType("wifi");
		return network;
	}

}
