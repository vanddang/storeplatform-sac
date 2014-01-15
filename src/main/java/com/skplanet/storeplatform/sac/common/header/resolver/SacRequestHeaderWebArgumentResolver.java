package com.skplanet.storeplatform.sac.common.header.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;

public class SacRequestHeaderWebArgumentResolver implements WebArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		if (methodParameter.getParameterType().equals(SacRequestHeader.class)) {

			DeviceHeader device = new DeviceHeader();

			device.setDpi("dpi");
			device.setModel("model");
			device.setOsVersion("osVersion");
			device.setPkgVersion("pkgVersion");
			device.setResolution("resolution");

			NetworkHeader network = new NetworkHeader();

			network.setOperator("operator");
			network.setSimOperator("simOperator");
			network.setType("type");

			TenantHeader tenant = new TenantHeader();

			tenant.setSystemId("systemId");
			tenant.setTenantId("tenantId");

			SacRequestHeader sacHeader = new SacRequestHeader();

			sacHeader.setDeviceHeader(device);

			sacHeader.setNetworkHeader(network);

			sacHeader.setTenantHeader(tenant);

			return sacHeader;
		}
		return UNRESOLVED;

	}
}
