package com.skplanet.storeplatform.sac.common.header.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;

/**
 * SAC Custom Header 값들을 Spring Controller에서 쓰기 위한 WebArgumentResolver 구현체 (임시적으로 상위 규격서의 샘플 값들을 넣어놓았으며, 추후 ACL Flow가
 * 완성되면 보완한다.) Updated on : 2014. 1. 15. Updated by : 서대영, SK 플래닛.
 */
public class SacRequestHeaderWebArgumentResolver implements WebArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		if (methodParameter.getParameterType().equals(SacRequestHeader.class)) {
			DeviceHeader device = new DeviceHeader();

			device.setDpi("320");
			device.setModel("SHW-M110S");
			device.setOsVersion("Android/4.0.4");
			device.setPkgVersion("store.skplanet.com/0.1");
			device.setResolution("480*720");

			NetworkHeader network = new NetworkHeader();

			network.setOperator("unknown/unknown");
			network.setSimOperator("450/05");
			network.setType("wifi");

			TenantHeader tenant = new TenantHeader();

			tenant.setSystemId("S001");
			tenant.setTenantId("S01");

			SacRequestHeader sacHeader = new SacRequestHeader();

			sacHeader.setDeviceHeader(device);

			sacHeader.setNetworkHeader(network);

			sacHeader.setTenantHeader(tenant);

			return sacHeader;
		}
		return UNRESOLVED;
	}
}
